package me.choicore.samples.pms.charge.domain

import java.time.LocalDate

class SpecifiedDateChargingStrategies : AbstractChargingStrategiesContainer<LocalDate, OneTimeSchedule>() {
    override fun getChargingStrategies(date: LocalDate): List<OneTimeSchedule> = super.strategies[date] ?: emptyList()

    override fun getKey(strategy: ChargingStrategy): LocalDate = (strategy as OneTimeSchedule).specifiedDate
}
