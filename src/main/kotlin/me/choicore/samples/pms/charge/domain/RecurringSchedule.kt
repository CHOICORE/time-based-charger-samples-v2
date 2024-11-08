package me.choicore.samples.pms.charge.domain

import java.time.DayOfWeek

data class RecurringSchedule(
    val dayOfWeek: DayOfWeek,
    override val mode: ChargingMode,
    override val rate: Int,
    override val timeline: Timeline,
) : ChargingStrategy
