package me.choicore.samples.charge.domain

import java.time.LocalDate
import java.time.LocalTime

data class ChargingUnit(
    val identifier: ChargingUnitIdentifier,
    val chargedOn: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    var deleted: Boolean = false,
    var adjustable: Boolean = true,
) {
    init {
        require(this.startTime.isBefore(this.endTime)) { "The start time must be before the end time." }
    }

    data class ChargingUnitIdentifier(
        private val _unitId: Long? = null,
        val targetId: Long,
    ) {
        val unitId: Long = _unitId ?: throw IllegalStateException("The unit ID must be provided.")

        companion object {
            fun unregistered(targetId: Long): ChargingUnitIdentifier =
                ChargingUnitIdentifier(
                    targetId = targetId,
                )

            fun registered(
                unitId: Long,
                targetId: Long,
            ): ChargingUnitIdentifier =
                ChargingUnitIdentifier(
                    _unitId = unitId,
                    targetId = targetId,
                )
        }
    }

    private val _adjustments: MutableList<Adjustment> = mutableListOf()
    val adjustments: List<Adjustment> get() = this._adjustments.toList()

    private fun addAdjustment(adjustment: Adjustment) {
        this._adjustments.add(adjustment)
        this._adjustments.sortBy { it.basis.startTimeInclusive }
    }

    data class Adjustment(
        val mode: ChargingMode,
        val rate: Int,
        val basis: TimeSlot,
        val applied: TimeSlot,
    )
}
