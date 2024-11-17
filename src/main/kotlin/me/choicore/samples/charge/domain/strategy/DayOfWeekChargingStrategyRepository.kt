package me.choicore.samples.charge.domain.strategy

import java.time.DayOfWeek

interface DayOfWeekChargingStrategyRepository : ChargingStrategyRepository<DayOfWeekChargingStrategy> {
    fun findByDayOfWeek(dayOfWeek: DayOfWeek): List<DayOfWeekChargingStrategy>
}
