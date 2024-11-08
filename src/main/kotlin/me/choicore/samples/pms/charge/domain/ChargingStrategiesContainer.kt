package me.choicore.samples.pms.charge.domain

import java.time.LocalDate

sealed interface ChargingStrategiesContainer<K, S : ChargingStrategy> {
    val strategies: Map<K, List<S>>

    fun register(strategy: S)

    fun register(vararg strategies: S) {
        strategies.forEach { register(it) }
    }

    fun register(strategies: Collection<S>) {
        strategies.forEach { register(it) }
    }

    fun getChargingStrategies(date: LocalDate): List<S>

    fun getKey(strategy: ChargingStrategy): K
}
