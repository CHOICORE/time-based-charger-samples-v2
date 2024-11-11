package me.choicore.samples.charge.domain

import java.time.LocalDate

sealed interface ChargingStrategies<S : ChargingStrategy> {
    fun register(strategy: S)

    fun register(strategies: List<S>) {
        strategies.forEach { this.register(it) }
    }

    fun getChargingStrategies(date: LocalDate): List<S>
}
