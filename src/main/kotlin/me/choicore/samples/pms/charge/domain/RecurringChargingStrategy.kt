package me.choicore.samples.pms.charge.domain

import me.choicore.samples.bak.ChargingMode
import java.time.DayOfWeek

data class RecurringChargingStrategy(
    val dayOfWeek: DayOfWeek,
    override val mode: ChargingMode,
    override val rate: Int,
    override val timeline: Timeline,
) : ChargingStrategy
