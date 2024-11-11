package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingStatus.CHARGED
import me.choicore.samples.charge.domain.ChargingStatus.CHARGING
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class Charger(
    private val chargingStationSelectorProvider: ChargingStationSelectorProvider,
    private val specifiedDateChargingStrategyRepository: SpecifiedDateChargingStrategyRepository,
    private val chargingTargetRepository: ChargingTargetRepository,
) {
    fun charge(
        complexId: Long,
        chargedOn: LocalDate,
    ) {
        val chargingTargets: List<ChargingTarget> =
            chargingTargetRepository.findByComplexIdAndLastChargedOnLessThanEqualOrNull(
                complexId = complexId,
                lastChargedOn = chargedOn,
            )

        val chargingStationSelector: ChargingStationSelector =
            chargingStationSelectorProvider.getChargingStationSelector(complexId = complexId)
        val specifies: List<SpecifiedDateChargingStrategy> =
            specifiedDateChargingStrategyRepository.findAllByComplexId(complexId = complexId)
        val specifiedDateChargingStrategies = SpecifiedDateChargingStrategies(specifies)

        for (chargingTarget: ChargingTarget in chargingTargets) {
            var currentChargedOn: LocalDate = chargingTarget.lastChargedOn ?: chargingTarget.arrivedOn
            while (currentChargedOn <= chargedOn) {
                val chargingUnit: ChargingUnit = chargingTarget.getChargingUnit(chargedOn = currentChargedOn)
                val chargingStrategies: List<SpecifiedDateChargingStrategy> =
                    specifiedDateChargingStrategies.getChargingStrategies(date = currentChargedOn)
                if (chargingStrategies.isNotEmpty()) {
                    chargingStrategies.forEach { chargingStrategy: SpecifiedDateChargingStrategy ->
                        chargingStrategy.attempt(chargingUnit = chargingUnit)
                    }
                } else {
                    val chargingStation: ChargingStation = chargingStationSelector.select(chargedOn = chargedOn)
                    val dayOfWeekChargingStrategies: DayOfWeekChargingStrategies =
                        chargingStation.dayOfWeekChargingStrategies
                }

                chargingTarget.lastChargedOn = currentChargedOn
                chargingTarget.status = if (chargingTarget.departedOn == currentChargedOn) CHARGED else CHARGING
                currentChargedOn = currentChargedOn.plusDays(1)
            }
        }
    }
}
