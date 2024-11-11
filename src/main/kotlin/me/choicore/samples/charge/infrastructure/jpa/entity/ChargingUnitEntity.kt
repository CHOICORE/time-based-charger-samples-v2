package me.choicore.samples.charge.infrastructure.jpa.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.choicore.samples.charge.domain.ChargingUnit
import me.choicore.samples.common.jpa.AutoIncrement
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "charging_units")
class ChargingUnitEntity(
    val targetId: Long,
    val chargedOn: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    var deleted: Boolean = false,
) : AutoIncrement() {
    constructor(chargingUnit: ChargingUnit) : this(
        targetId = chargingUnit.identifier.targetId,
        chargedOn = chargingUnit.chargedOn,
        startTime = chargingUnit.startTime,
        endTime = chargingUnit.endTime,
        deleted = chargingUnit.deleted,
    )
}
