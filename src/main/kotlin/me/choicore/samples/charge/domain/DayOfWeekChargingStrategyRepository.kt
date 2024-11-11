package me.choicore.samples.charge.domain

import java.time.DayOfWeek

interface DayOfWeekChargingStrategyRepository : ChargingStrategyRepository<DayOfWeekChargingStrategy> {
    fun findByDayOfWeek(dayOfWeek: DayOfWeek): List<DayOfWeekChargingStrategy>
}
