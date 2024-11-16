package me.choicore.samples.charge.infrastructure.jpa.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import me.choicore.samples.charge.domain.ChargingStatus
import me.choicore.samples.charge.domain.ChargingTarget
import me.choicore.samples.common.jpa.AutoIncrement
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "charging_targets")
class ChargingTargetEntity(
    val accessId: Long? = null,
    val complexId: Long,
    val building: String,
    val unit: String,
    val licensePlate: String,
    val arrivedAt: LocalDateTime,
    var departedAt: LocalDateTime?,
    @Enumerated(STRING)
    var status: ChargingStatus,
    val commited: Boolean = false,
    var lastChargedOn: LocalDate? = null,
) : AutoIncrement() {
    constructor(chargingTarget: ChargingTarget) : this(
        accessId = if (chargingTarget.accessId == 0L) null else chargingTarget.accessId,
        complexId = chargingTarget.complexId,
        building = chargingTarget.building,
        unit = chargingTarget.unit,
        licensePlate = chargingTarget.licensePlate,
        arrivedAt = chargingTarget.arrivedAt,
        departedAt = chargingTarget.departedAt,
        status = chargingTarget.status,
        lastChargedOn = chargingTarget.lastChargedOn,
    )

    fun toChargingTarget(): ChargingTarget =
        ChargingTarget(
            targetId = this.id,
            accessId = this.accessId ?: 0,
            complexId = this.complexId,
            building = this.building,
            unit = this.unit,
            licensePlate = licensePlate,
            arrivedAt = arrivedAt,
            departedAt = departedAt,
            status = status,
            lastChargedOn = lastChargedOn,
        )

    fun update(chargingTarget: ChargingTarget) {
        departedAt = chargingTarget.departedAt
        status = chargingTarget.status
        lastChargedOn = chargingTarget.lastChargedOn
    }
}
