package me.choicore.samples.charge.domain

import java.util.EnumSet

data class ChargingTargetCriteria(
    val complexId: Long,
    val building: String? = null,
    val unit: String? = null,
    val licensePlate: String,
    val statuses: Set<ChargingStatus> = EnumSet.allOf(ChargingStatus::class.java),
)