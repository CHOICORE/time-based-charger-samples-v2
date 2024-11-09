package me.choicore.samples.pms.charge.domain

import java.time.DayOfWeek
import java.time.LocalDate

class RecurringChargingStrategies() : AbstractChargingStrategies<DayOfWeek, RecurringChargingStrategy>() {
    constructor(schedules: List<RecurringChargingStrategy>) : this() {
        super.register(schedules)
    }

    override fun getChargingStrategies(date: LocalDate): List<RecurringChargingStrategy> = getStrategiesForDay(date.dayOfWeek)

    override fun getKey(strategy: ChargingStrategy): DayOfWeek = (strategy as RecurringChargingStrategy).dayOfWeek

    val sunday: List<RecurringChargingStrategy> = getStrategiesForDay(DayOfWeek.SUNDAY)

    val monday: List<RecurringChargingStrategy> = getStrategiesForDay(DayOfWeek.MONDAY)

    val tuesday: List<RecurringChargingStrategy> = getStrategiesForDay(DayOfWeek.TUESDAY)

    val wednesday: List<RecurringChargingStrategy> = getStrategiesForDay(DayOfWeek.WEDNESDAY)

    val thursday: List<RecurringChargingStrategy> = getStrategiesForDay(DayOfWeek.THURSDAY)

    val friday: List<RecurringChargingStrategy> = getStrategiesForDay(DayOfWeek.FRIDAY)

    val saturday: List<RecurringChargingStrategy> = getStrategiesForDay(DayOfWeek.SATURDAY)

    private fun getStrategiesForDay(dayOfWeek: DayOfWeek): List<RecurringChargingStrategy> = super.strategies[dayOfWeek] ?: emptyList()
}
