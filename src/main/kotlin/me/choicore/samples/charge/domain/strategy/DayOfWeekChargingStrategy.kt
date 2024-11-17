package me.choicore.samples.charge.domain.strategy

import me.choicore.samples.charge.domain.core.ChargingMode
import me.choicore.samples.charge.domain.core.Timeline
import me.choicore.samples.charge.domain.strategy.ChargingStrategy.DayOfWeekChargingStrategy
import java.time.DayOfWeek
import java.time.LocalDate

data class DayOfWeekChargingStrategy(
    override val strategyId: Long = 0,
    override val complexId: Long = 0,
    override val stationId: Long = 0,
    override val dayOfWeek: DayOfWeek,
    override val mode: ChargingMode,
    override val timeline: Timeline,
) : DayOfWeekChargingStrategy {
    override fun supports(date: LocalDate): Boolean = date.dayOfWeek == this.dayOfWeek
}
