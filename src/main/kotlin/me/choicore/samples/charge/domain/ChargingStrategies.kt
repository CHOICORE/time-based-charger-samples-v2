package me.choicore.samples.charge.domain

import java.time.LocalDate

sealed interface ChargingStrategies<S : ChargingStrategy> {
    fun register(strategy: S)

    fun register(strategies: List<S>) {
        strategies.forEach { this.register(it) }
    }

    fun charge(unit: ChargingUnit) {
        val chargingStrategies: List<S> = this.getChargingStrategies(date = unit.chargedOn)
        for (strategy: S in chargingStrategies) {
            strategy.attempt(unit = unit)
        }
    }

    fun getChargingStrategies(date: LocalDate): List<S>
}
