package me.choicore.samples.charge.application

import me.choicore.samples.charge.domain.core.ChargingStatus.REGISTERED
import me.choicore.samples.charge.domain.target.ChargingTargetCriteria
import java.time.LocalDateTime

data class AccessEvent(
    val accessId: Long,
    val complexId: Long,
    val building: String,
    val unit: String,
    val licensePlate: String,
    val type: Type,
    val accessedAt: LocalDateTime,
) {
    enum class Type {
        ARRIVAL,
        DEPARTURE,
    }

    fun toChargingTargetCriteria(): ChargingTargetCriteria =
        ChargingTargetCriteria(
            complexId = complexId,
            building = building,
            unit = unit,
            licensePlate = licensePlate,
            statuses = setOf(REGISTERED),
        )
}
