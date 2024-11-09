package me.choicore.samples.pms.charge.domain

import me.choicore.samples.bak.ChargingMode
import java.time.LocalDate

data class OneTimeChargingStrategy(
    val specifiedDate: LocalDate,
    override val mode: ChargingMode,
    override val rate: Int,
    override val timeline: Timeline,
) : ChargingStrategy
