package me.choicore.samples.charge.infrastructure.jpa.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.choicore.samples.charge.domain.ChargingMethod
import me.choicore.samples.charge.domain.ChargingUnit.ChargingDetail
import me.choicore.samples.charge.domain.TimeSlot
import me.choicore.samples.common.jpa.AutoIncrement
import java.time.LocalTime

@Entity
@Table(name = "charging_details")
class ChargingDetailEntity(
    val unitId: Long,
    val strategyId: Long?,
    val basisStartTime: LocalTime,
    val basisEndTime: LocalTime,
    val method: ChargingMethod,
    val rate: Int,
    val appliedStartTime: LocalTime,
    val appliedEndTime: LocalTime,
    val chargedAmount: Long,
    var active: Boolean = true,
) : AutoIncrement() {
    constructor(unitId: Long, chargingDetail: ChargingDetail) : this(
        unitId = unitId,
        strategyId = chargingDetail.strategyId,
        basisStartTime = chargingDetail.basis.startTimeInclusive,
        basisEndTime = chargingDetail.basis.endTimeInclusive,
        method = chargingDetail.mode.method,
        rate = chargingDetail.mode.rate,
        appliedStartTime = chargingDetail.applied.startTimeInclusive,
        appliedEndTime = chargingDetail.applied.endTimeInclusive,
        chargedAmount = chargingDetail.chargedAmount,
    )

    fun toChargingDetail(): ChargingDetail =
        ChargingDetail(
            unitId = this.unitId,
            strategyId = this.strategyId!!,
            basis = TimeSlot(this.basisStartTime, this.basisEndTime),
            mode = this.method.toChargingMode(rate = this.rate),
            applied = TimeSlot(this.appliedStartTime, this.appliedEndTime),
        )
}