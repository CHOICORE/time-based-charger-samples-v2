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
    val originalAmount: Long,
    val chargedAmount: Long,
    var active: Boolean = true,
) : AutoIncrement() {
    constructor(chargingUnit: ChargingUnit) : this(
        targetId = chargingUnit.identifier.targetId,
        chargedOn = chargingUnit.chargedOn,
        startTime = chargingUnit.startTime,
        endTime = chargingUnit.endTime,
        active = chargingUnit.active,
        originalAmount = chargingUnit.originalAmount,
        chargedAmount = chargingUnit.chargedAmount,
    )

    fun toChargingUnit(): ChargingUnit =
        ChargingUnit(
            identifier = ChargingUnit.ChargingUnitIdentifier.unregistered(this.targetId),
            chargedOn = this.chargedOn,
            startTime = this.startTime,
            endTime = this.endTime,
            active = this.active,
        )
}
