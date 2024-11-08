package me.choicore.samples.pms.charge.domain

import java.time.DayOfWeek
import java.time.LocalDate

class DayOfWeekChargingStrategies() : AbstractChargingStrategiesContainer<DayOfWeek, RecurringSchedule>() {
    constructor(schedules: Collection<RecurringSchedule>) : this() {
        super.register(schedules)
    }

    override fun getChargingStrategies(date: LocalDate): List<RecurringSchedule> = super.strategies[date.dayOfWeek] ?: emptyList()

    override fun getKey(strategy: ChargingStrategy): DayOfWeek = (strategy as RecurringSchedule).dayOfWeek
}
