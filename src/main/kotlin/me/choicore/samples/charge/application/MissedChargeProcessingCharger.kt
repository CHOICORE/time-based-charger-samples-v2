package me.choicore.samples.charge.application

import me.choicore.samples.charge.domain.Charger
import me.choicore.samples.charge.domain.ChargingStation
import me.choicore.samples.charge.domain.ChargingStationSelector
import me.choicore.samples.charge.domain.ChargingStationSelectorProvider
import me.choicore.samples.charge.domain.ChargingStatus.CHARGED
import me.choicore.samples.charge.domain.ChargingStatus.CHARGING
import me.choicore.samples.charge.domain.ChargingTarget
import me.choicore.samples.charge.domain.ChargingTargetRepository
import me.choicore.samples.charge.domain.ChargingUnit
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategies
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategies
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategy
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategyRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MissedChargeProcessingCharger(
    private val chargingStationSelectorProvider: ChargingStationSelectorProvider,
    private val specifiedDateChargingStrategyRepository: SpecifiedDateChargingStrategyRepository,
    private val chargingTargetRepository: ChargingTargetRepository,
    private val charger: Charger,
) {
    fun charge(
        complexId: Long,
        chargedOn: LocalDate,
    ) {
        val chargingStationSelector: ChargingStationSelector =
            chargingStationSelectorProvider.getChargingStationSelector(complexId = complexId)
        val specifies: List<SpecifiedDateChargingStrategy> =
            specifiedDateChargingStrategyRepository.findAllByComplexId(complexId = complexId)
        val specifiedDateChargingStrategies = SpecifiedDateChargingStrategies(specifies)

        val chargingTargets: List<ChargingTarget> =
            chargingTargetRepository.getChargingTargetsByComplexIdAndChargedOn(
                complexId = complexId,
                chargedOn = chargedOn,
            )

        for (chargingTarget: ChargingTarget in chargingTargets) {
            var currentChargedOn: LocalDate =
                if (chargingTarget.lastChargedOn == null) {
                    chargingTarget.arrivedOn
                } else {
                    chargingTarget.lastChargedOn!!.plusDays(1)
                }

            while (currentChargedOn <= chargedOn) {
                val chargingUnit: ChargingUnit = chargingTarget.getChargingUnit(chargedOn = currentChargedOn)
                val chargingStrategies: List<SpecifiedDateChargingStrategy> =
                    specifiedDateChargingStrategies.getChargingStrategies(date = currentChargedOn)
                if (chargingStrategies.isNotEmpty()) {
                    specifiedDateChargingStrategies.charge(unit = chargingUnit)
                } else {
                    val chargingStation: ChargingStation = chargingStationSelector.select(chargedOn = chargedOn)
                    val dayOfWeekChargingStrategies: DayOfWeekChargingStrategies =
                        chargingStation.dayOfWeekChargingStrategies
                    dayOfWeekChargingStrategies.charge(unit = chargingUnit)
                }
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
}
