package me.choicore.samples.charge.domain

import java.time.LocalDate

sealed interface ChargingStrategies<K, S : ChargingStrategy> {
    val strategies: Map<K, List<S>>

    fun register(strategy: S)

    fun register(strategies: List<S>) {
        strategies.forEach { this.register(it) }
    }

    fun getChargingStrategies(date: LocalDate): List<S>

    fun getAll(): List<S> = strategies.values.flatten()
}
