package me.choicore.samples.charge.presentation.dto

import me.choicore.samples.charge.domain.core.TimeSlot
import me.choicore.samples.charge.domain.core.TimeUtils
import java.time.LocalTime

data class TimeSlotDto(
    val startTime: LocalTime,
    val endTime: LocalTime,
) {
    init {
        require(startTime.isBefore(endTime)) { "startTime must be before endTime" }
    }

    fun toTimeSlot(): TimeSlot = TimeSlot(this.startTime, minOf(this.endTime, TimeUtils.MAX_TIME))

    companion object {
        fun from(timeSlot: TimeSlot): TimeSlotDto =
            TimeSlotDto(timeSlot.startTimeInclusive, minOf(timeSlot.endTimeInclusive, TimeUtils.MAX_TIME))
    }
}
