package me.choicore.samples.charge.domain

import java.time.DayOfWeek
import java.time.LocalDate

class DayOfWeekChargingStrategies() : AbstractChargingStrategies<DayOfWeek, DayOfWeekChargingStrategy>() {
    constructor(schedules: List<DayOfWeekChargingStrategy>) : this() {
        super.register(schedules)
    }

    override fun getChargingStrategies(date: LocalDate): List<DayOfWeekChargingStrategy> = getStrategiesForDay(date.dayOfWeek)

    override fun getKey(strategy: DayOfWeekChargingStrategy): DayOfWeek = strategy.dayOfWeek

    val sunday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.SUNDAY)

    val monday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.MONDAY)

    val tuesday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.TUESDAY)

    val wednesday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.WEDNESDAY)

    val thursday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.THURSDAY)

    val friday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.FRIDAY)

    val saturday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.SATURDAY)

    private fun getStrategiesForDay(dayOfWeek: DayOfWeek): List<DayOfWeekChargingStrategy> = super.strategies[dayOfWeek] ?: emptyList()
}
