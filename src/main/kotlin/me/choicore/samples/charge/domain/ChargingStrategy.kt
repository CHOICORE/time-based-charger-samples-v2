package me.choicore.samples.charge.domain

import java.time.DayOfWeek
import java.time.LocalDate

sealed interface ChargingStrategy {
    val identifier: ChargingStrategyIdentifier
    val mode: ChargingMode
    val timeline: Timeline
    val period: Period

    sealed interface ChargingStrategyIdentifier {
        val strategyId: Long
        val complexId: Long
    }

    fun supports(date: LocalDate): Boolean

    fun attempt(unit: ChargingUnit) {
        require(value = this.supports(date = unit.chargedOn)) { "The specified date does not satisfy the timeline." }
        unit.adjust(strategy = this)
    }

    interface DayOfWeekChargingStrategy : ChargingStrategy {
        val dayOfWeek: DayOfWeek
        override val period: Period get() = Period.REPEATABLE

        override fun supports(date: LocalDate): Boolean = date.dayOfWeek == this.dayOfWeek
    }

    interface SpecifiedDateChargingStrategy : ChargingStrategy {
        val specifiedDate: LocalDate
        override val period: Period get() = Period.ONCE

        override fun supports(date: LocalDate): Boolean = date == this.specifiedDate
    }

    enum class Period {
        REPEATABLE,
        ONCE,
    }
}
