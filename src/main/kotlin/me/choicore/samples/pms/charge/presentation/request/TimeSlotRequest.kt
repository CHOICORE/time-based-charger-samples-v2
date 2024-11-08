package me.choicore.samples.pms.charge.presentation.request

import me.choicore.samples.pms.charge.domain.TimeSlot
import java.time.LocalTime

data class TimeSlotRequest(
    val startTime: LocalTime,
    val endTime: LocalTime,
) {
    init {
        require(startTime.isBefore(endTime)) { "startTime must be before endTime" }
    }

    fun toTimeSlot(): TimeSlot = TimeSlot(startTime, endTime)
}
