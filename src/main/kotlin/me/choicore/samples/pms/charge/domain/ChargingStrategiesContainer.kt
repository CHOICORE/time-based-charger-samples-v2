package me.choicore.samples.pms.charge.domain

import java.time.LocalDate

sealed interface ChargingStrategiesContainer<K, S : ChargingStrategy> {
    val strategies: Map<K, List<S>>

    fun register(strategy: S)

    fun register(vararg strategies: S) {
        strategies.forEach { this.register(it) }
    }

    fun register(strategies: List<S>) {
        strategies.forEach { this.register(it) }
    }

    fun getChargingStrategies(date: LocalDate): List<S>

    fun getAll(): List<S> = strategies.values.flatten()
}
