package me.choicore.samples.charge.domain.core

import java.time.LocalTime

class Timeline {
    constructor()
    constructor(slots: List<TimeSlot>) {
        slots.forEach(this::addSlot)
    }

    private val _slots: MutableList<TimeSlot> = mutableListOf()
    val slots: List<TimeSlot> get() = this._slots
    val empty: Boolean get() = this._slots.isEmpty()

    fun addSlot(
        startTimeInclusive: LocalTime,
        endTimeInclusive: LocalTime,
    ) {
        this.addSlot(slot = TimeSlot(startTimeInclusive, endTimeInclusive))
    }

    fun addSlot(slot: TimeSlot) {
        check(!this.isOverlappingWithExistingTimeSlots(other = slot)) {
            "The specified time slot overlaps with an existing time slot."
        }

        this._slots += slot
        this._slots.sortBy { it.startTimeInclusive }
    }

    fun overlapsWith(other: Timeline): Boolean = other._slots.any(this::isOverlappingWithExistingTimeSlots)

    private fun isOverlappingWithExistingTimeSlots(other: TimeSlot): Boolean {
        // 새로운 슬롯이 들어갈 위치를 찾음
        val position: Int = determineTimeSlotPosition(timeline = this, timeSlot = other)

        // 이전 슬롯과 겹치는지 확인
        val overlapWithPrevious: Boolean =
            position > 0 && this._slots[position - 1].endTimeInclusive > other.startTimeInclusive

        // 다음 슬롯과 겹치는지 확인
        val overlapWithNext: Boolean =
            position < this._slots.size && this._slots[position].startTimeInclusive < other.endTimeInclusive

        return overlapWithPrevious || overlapWithNext
    }

    private fun determineTimeSlotPosition(
        timeline: Timeline,
        timeSlot: TimeSlot,
    ): Int =
        timeline._slots
            .binarySearch { it.startTimeInclusive.compareTo(timeSlot.startTimeInclusive) }
            .let { if (it < 0) -(it + 1) else it }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Timeline

        return this._slots == other._slots
    }

    override fun hashCode(): Int = this._slots.hashCode()

    override fun toString(): String = "Timeline(slots=${this._slots})"

    companion object {
        fun remain(excludedTimeSlots: List<TimeSlot>): Timeline {
            val existingTimeSlots: List<TimeSlot> = excludedTimeSlots.sortedBy { it.startTimeInclusive }
            val remainingTimeline = Timeline()
            var previous: LocalTime = LocalTime.MIDNIGHT
            existingTimeSlots.forEach { slot ->
                if (slot.startTimeInclusive > previous) {
                    remainingTimeline.addSlot(startTimeInclusive = previous, endTimeInclusive = slot.startTimeInclusive)
                }
                previous = slot.endTimeInclusive
            }
            val endTimeInclusive: LocalTime = TimeUtils.MAX_TIME
            if (previous < endTimeInclusive) {
                remainingTimeline.addSlot(
                    startTimeInclusive = previous,
                    endTimeInclusive = endTimeInclusive,
                )
            }
            return remainingTimeline
        }
    }
}
