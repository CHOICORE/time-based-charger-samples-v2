package me.choicore.samples.charge.presentation.dto

import me.choicore.samples.charge.domain.TimeSlot
import me.choicore.samples.charge.domain.TimeUtils
import java.time.LocalTime

data class TimeSlotDto(
    val startTime: LocalTime,
    val endTime: LocalTime,
) {
    init {
        require(startTime.isBefore(endTime)) { "startTime must be before endTime" }
    }

    fun toTimeSlot(): TimeSlot =
        if (this.endTime >= TimeUtils.MAX_TIME) {
            TimeSlot(this.startTime, TimeUtils.MAX_TIME)
        } else {
            TimeSlot(this.startTime, this.endTime)
        }

    companion object {
        fun from(timeSlot: TimeSlot): TimeSlotDto = TimeSlotDto(timeSlot.startTimeInclusive, timeSlot.endTimeInclusive)
    }
}
