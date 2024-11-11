package me.choicore.samples.charge.presentation.dto

import me.choicore.samples.charge.domain.TimeSlot
import java.time.LocalTime

data class TimeSlotDto(
    val startTime: LocalTime,
    val endTime: LocalTime,
) {
    init {
        require(startTime.isBefore(endTime)) { "startTime must be before endTime" }
    }

    fun toTimeSlot(): TimeSlot = TimeSlot(startTime, endTime)

    companion object {
        fun from(timeSlot: TimeSlot): TimeSlotDto = TimeSlotDto(timeSlot.startTimeInclusive, timeSlot.endTimeInclusive)
    }
}
