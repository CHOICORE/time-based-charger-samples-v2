package me.choicore.samples.charge.domain

import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit.MINUTES
import java.time.temporal.ChronoUnit.SECONDS

data class ChargingUnit(
    val identifier: ChargingUnitIdentifier,
    val chargedOn: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    var active: Boolean = true,
    var adjustable: Boolean = true,
) {
    val originalAmount: Long = TimeUtils.duration(this.startTime, this.endTime, SECONDS) / 60

    val chargedAmount: Long get() = this.details.sumOf { it.chargedAmount }

    init {
        require(
            this.startTime.isBefore(this.endTime) || this.startTime >= TimeUtils.MAX_TIME,
        ) { "The start time must be before the end time." }
    }

    fun adjust(strategy: ChargingStrategy) {
        require(strategy.supports(this.chargedOn)) { "The specified date does not satisfy the timeline." }

        strategy.timeline.slots.forEach { slot ->
            val basis: TimeSlot = slot
            val applied: TimeSlot? = slot.extractWithin(this.startTime, this.endTime)
            if (applied != null) {
                this.addDetail(
                    ChargingDetail(
                        unitId = null,
                        strategyId = strategy.identifier.strategyId,
                        mode = strategy.mode,
                        basis = basis,
                        applied = applied,
                    ),
                )
            }
        }
    }

    data class ChargingUnitIdentifier(
        private val _unitId: Long? = null,
        val targetId: Long,
    ) {
        val unitId: Long get() = _unitId ?: throw IllegalStateException("The unit ID must be provided.")

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

    private val _details: MutableList<ChargingDetail> = mutableListOf()
    val details: List<ChargingDetail> get() = this._details.toList()

    fun addDetail(chargingDetail: ChargingDetail) {
        this._details.add(chargingDetail)
        this._details.sortBy { it.basis.startTimeInclusive }
    }

    fun addDetails(chargingDetails: List<ChargingDetail>) {
        for (detail: ChargingDetail in chargingDetails) {
            this.addDetail(detail)
        }
    }

    data class ChargingDetail(
        val detailId: Long? = null,
        val unitId: Long? = null,
        val strategyId: Long,
        val mode: ChargingMode,
        val basis: TimeSlot,
        val applied: TimeSlot,
    ) {
        val originalAmount: Long
            get() =
                TimeUtils.duration(
                    start = basis.startTimeInclusive,
                    end = basis.endTimeInclusive,
                    unit = MINUTES,
                )
        val chargedAmount: Long get() = this.mode.charge(amount = this.applied.duration(unit = MINUTES)).toLong()
    }
}
