package me.choicore.samples.pms.charge.domain

sealed interface ChargingStrategy {
    val mode: ChargingMode
    val rate: Int
    val timeline: Timeline
}
