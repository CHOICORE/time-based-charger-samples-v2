package me.choicore.samples.pms.charge.presentation.request

import me.choicore.samples.pms.charge.domain.ChargingMode
import me.choicore.samples.pms.charge.domain.RecurringSchedule
import me.choicore.samples.pms.charge.domain.Timeline
import java.time.DayOfWeek

data class StandardScheduleRequest(
    val sun: List<RecurringScheduleDto>? = emptyList(),
    val mon: List<RecurringScheduleDto>? = emptyList(),
    val tue: List<RecurringScheduleDto>? = emptyList(),
    val wed: List<RecurringScheduleDto>? = emptyList(),
    val thu: List<RecurringScheduleDto>? = emptyList(),
    val fri: List<RecurringScheduleDto>? = emptyList(),
    val sat: List<RecurringScheduleDto>? = emptyList(),
) {
    data class RecurringScheduleDto(
        val dayOfWeek: DayOfWeek,
        val mode: ChargingMode,
        val rate: Int,
        val timeline: List<TimeSlotRequest>,
    ) {
        fun toRecurringSchedule(): RecurringSchedule =
            RecurringSchedule(
                dayOfWeek = this.dayOfWeek,
                mode = this.mode,
                rate = this.rate,
                timeline = Timeline(timeline.map { it.toTimeSlot() }),
            )
    }

    fun toRecurringSchedules(): List<RecurringSchedule> =
        listOfNotNull(this.sun, this.mon, this.tue, this.wed, this.thu, this.fri, this.sat)
            .flatten()
            .map { it.toRecurringSchedule() }
}
