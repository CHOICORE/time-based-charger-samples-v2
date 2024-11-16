package me.choicore.samples.charge.infrastructure.jpa.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import me.choicore.samples.charge.domain.ChargingMethod
import me.choicore.samples.charge.domain.ChargingUnit.ChargingDetail
import me.choicore.samples.charge.domain.TimeSlot
import me.choicore.samples.charge.domain.TimeUtils
import me.choicore.samples.common.jpa.AutoIncrement
import java.time.LocalTime

@Entity
@Table(name = "charging_details")
class ChargingDetailEntity(
    val unitId: Long,
    val strategyId: Long?,
    val basisStartTime: LocalTime,
    val basisEndTime: LocalTime,
    @Enumerated(EnumType.STRING)
    val method: ChargingMethod,
    val rate: Int,
    val appliedStartTime: LocalTime,
    val appliedEndTime: LocalTime,
    val chargedAmount: Long,
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
            basis =
                TimeSlot(
                    startTimeInclusive = this.basisStartTime,
                    endTimeInclusive = minOf(this.basisEndTime, TimeUtils.MAX_TIME),
                ),
            mode = this.method.toChargingMode(rate = this.rate),
            applied =
                TimeSlot(
                    startTimeInclusive = this.appliedStartTime,
                    endTimeInclusive = minOf(this.appliedEndTime, TimeUtils.MAX_TIME),
                ),
        )
}
