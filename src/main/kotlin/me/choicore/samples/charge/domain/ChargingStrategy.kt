package me.choicore.samples.charge.domain

import java.time.DayOfWeek
import java.time.LocalDate

sealed interface ChargingStrategy {
    val identifier: ChargingStrategyIdentifier
    val mode: ChargingMode
    val timeline: Timeline

    sealed interface ChargingStrategyIdentifier {
        val strategyId: Long
        val complexId: Long
    }

    fun supports(date: LocalDate): Boolean

    interface DayOfWeekChargingStrategy : ChargingStrategy {
        val dayOfWeek: DayOfWeek

        override fun supports(date: LocalDate): Boolean = date.dayOfWeek == this.dayOfWeek
    }

    interface SpecifiedDateChargingStrategy : ChargingStrategy {
        val specifiedDate: LocalDate

        override fun supports(date: LocalDate): Boolean = date == this.specifiedDate
    }
}
