package me.choicore.samples.charge.domain

import java.time.LocalDate

data class ChargingContext(
    val chargingStationSelector: ChargingStationSelector,
    val specifiedDateChargingStrategies: SpecifiedDateChargingStrategies,
) {
    fun charge(
        unit: ChargingUnit,
        chargedOn: LocalDate,
    ) {
        if (!specifiedDateChargingStrategies.isEmpty(date = chargedOn)) {
            specifiedDateChargingStrategies.charge(unit = unit)
        } else {
            determineDayOfWeekChargingStrategies(chargedOn).charge(unit = unit)
        }
    }

    private fun determineDayOfWeekChargingStrategies(chargedOn: LocalDate): DayOfWeekChargingStrategies =
        chargingStationSelector.select(chargedOn = chargedOn).dayOfWeekChargingStrategies
}