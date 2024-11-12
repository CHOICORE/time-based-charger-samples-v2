package me.choicore.samples.charge.application

import me.choicore.samples.charge.domain.Charger
import me.choicore.samples.charge.domain.ChargingContext
import me.choicore.samples.charge.domain.ChargingStation
import me.choicore.samples.charge.domain.ChargingStationSelector
import me.choicore.samples.charge.domain.ChargingStationSelectorProvider
import me.choicore.samples.charge.domain.ChargingStatus.CHARGED
import me.choicore.samples.charge.domain.ChargingStatus.CHARGING
import me.choicore.samples.charge.domain.ChargingStatus.EXEMPTED
import me.choicore.samples.charge.domain.ChargingTarget
import me.choicore.samples.charge.domain.ChargingTargetReader
import me.choicore.samples.charge.domain.ChargingUnit
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategies
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategy
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategyReader
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class MissingChargeBatchProcessor(
    private val chargingStationSelectorProvider: ChargingStationSelectorProvider,
    private val specifiedDateChargingStrategyReader: SpecifiedDateChargingStrategyReader,
    private val chargingTargetReader: ChargingTargetReader,
    private val charger: Charger,
) {
    fun charge(
        complexId: Long,
        chargedOn: LocalDate,
    ) {
        val chargingContext: ChargingContext = createChargingContext(complexId)
        val chargingTargets: List<ChargingTarget> =
            chargingTargetReader.getChargingTargetsByComplexIdAndChargedOn(
                complexId = complexId,
                chargedOn = chargedOn,
            )

        for (chargingTarget: ChargingTarget in chargingTargets) {
            var currentChargedOn: LocalDate = chargingTarget.currentChargedOn

            val chargingStation: ChargingStation = chargingContext.chargingStationSelector.select(chargedOn = chargedOn)
            val exemptionThreshold: Long = chargingStation.exemptionThreshold
            if (chargingTarget.departed) {
                val arrivedAt: LocalDateTime = chargingTarget.arrivedAt
                val departedAt: LocalDateTime = chargingTarget.departedAt!!
                if (Duration.between(arrivedAt, departedAt).toMinutes() <= exemptionThreshold) {
                    chargingTarget.status = EXEMPTED
                    chargingTarget.lastChargedOn = chargingTarget.departedOn
                    charger.exempt(chargingTarget)
                    continue
                }
            } else {
                val arrivedAt: LocalDateTime = chargingTarget.arrivedAt
                val nextMidnight = arrivedAt.toLocalDate().plusDays(1).atStartOfDay()
                if (arrivedAt.plusMinutes(exemptionThreshold) > nextMidnight) {
                    continue
                }
            }

            while (currentChargedOn <= chargedOn) {
                val chargingUnit: ChargingUnit = chargingTarget.getChargingUnit(chargedOn = currentChargedOn)
                chargingContext.charge(unit = chargingUnit, chargedOn = currentChargedOn)
                chargingTarget.lastChargedOn = currentChargedOn
                chargingTarget.status = if (chargingTarget.departedOn == currentChargedOn) CHARGED else CHARGING
                charger.charge(target = chargingTarget, unit = chargingUnit)
                if (chargingTarget.status == CHARGED) {
                    break
                }

                currentChargedOn = currentChargedOn.plusDays(1)
            }
        }
    }

    private fun createChargingContext(complexId: Long): ChargingContext {
        val chargingStationSelector: ChargingStationSelector =
            chargingStationSelectorProvider.getChargingStationSelector(complexId = complexId)
        val specifies: List<SpecifiedDateChargingStrategy> =
            specifiedDateChargingStrategyReader.getSpecifiedDateChargingStrategiesByComplexId(complexId = complexId)
        val specifiedDateChargingStrategies = SpecifiedDateChargingStrategies(specifies)
        return ChargingContext(chargingStationSelector, specifiedDateChargingStrategies)
    }
}
