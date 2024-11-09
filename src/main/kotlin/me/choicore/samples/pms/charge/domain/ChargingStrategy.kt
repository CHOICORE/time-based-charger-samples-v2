package me.choicore.samples.pms.charge.domain

import me.choicore.samples.bak.ChargingMode

sealed interface ChargingStrategy {
    val mode: ChargingMode
    val rate: Int
    val timeline: Timeline
}
