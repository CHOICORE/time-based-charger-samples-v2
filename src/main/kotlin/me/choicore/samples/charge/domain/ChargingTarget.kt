package me.choicore.samples.charge.domain

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ChargingTarget(
    val identifier: ChargingTargetIdentifier,
    val arrivedAt: LocalDateTime,
    val departedAt: LocalDateTime?,
    var status: ChargingStatus,
    var lastChargedOn: LocalDate? = null,
) {
    class ChargingTargetIdentifier private constructor(
        private val _targetId: Long? = null,
        val complexId: Long,
        val building: String,
        val unit: String,
        val licensePlate: String,
    ) {
        val targetId: Long get() = _targetId ?: throw IllegalStateException("The target ID must be provided.")

        companion object {
            fun unregistered(
                complexId: Long,
                building: String,
                unit: String,
                licensePlate: String,
            ): ChargingTargetIdentifier =
                ChargingTargetIdentifier(
                    complexId = complexId,
                    building = building,
                    unit = unit,
                    licensePlate = licensePlate,
                )

            fun registered(
                targetId: Long,
                complexId: Long,
                building: String,
                unit: String,
                licensePlate: String,
            ): ChargingTargetIdentifier =
                ChargingTargetIdentifier(
                    _targetId = targetId,
                    complexId = complexId,
                    building = building,
                    unit = unit,
                    licensePlate = licensePlate,
                )
        }
    }

    val arrivedOn: LocalDate = arrivedAt.toLocalDate()
    val departedOn: LocalDate? = departedAt?.toLocalDate()

    fun getChargingUnit(chargedOn: LocalDate): ChargingUnit {
        val startTime: LocalTime =
            if (this.arrivedOn == chargedOn) {
                this.arrivedAt.toLocalTime()
            } else {
                LocalTime.MIN
            }

        val endTime: LocalTime =
            if (this.departedOn == chargedOn) {
                this.departedAt!!.toLocalTime()
            } else {
                LocalTime.of(23, 59, 59)
            }

        return ChargingUnit(
            identifier = ChargingUnit.ChargingUnitIdentifier.unregistered(targetId = this.identifier.targetId),
            chargedOn = chargedOn,
            startTime = startTime,
            endTime = endTime,
        )
    }
}
