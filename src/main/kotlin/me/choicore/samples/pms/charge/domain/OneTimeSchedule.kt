package me.choicore.samples.pms.charge.domain

import java.time.LocalDate

data class OneTimeSchedule(
    val specifiedDate: LocalDate,
    override val mode: ChargingMode,
    override val rate: Int,
    override val timeline: Timeline,
) : ChargingStrategy
