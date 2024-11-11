package me.choicore.samples.charge.domain

import java.time.LocalDate

class SpecifiedDateChargingStrategies() : AbstractChargingStrategies<LocalDate, SpecifiedDateChargingStrategy>() {
    constructor(strategies: List<SpecifiedDateChargingStrategy>) : this() {
        super.register(strategies)
    }

    override fun getChargingStrategies(date: LocalDate): List<SpecifiedDateChargingStrategy> = super.strategies[date] ?: emptyList()

    override fun getKey(strategy: SpecifiedDateChargingStrategy): LocalDate = strategy.specifiedDate

    companion object {
        val EMPTY = SpecifiedDateChargingStrategies()
    }
}
