package me.choicore.samples.pms.charge.presentation.request

import me.choicore.samples.bak.ChargingMode
import me.choicore.samples.pms.charge.domain.RecurringChargingStrategies
import me.choicore.samples.pms.charge.domain.RecurringChargingStrategy
import me.choicore.samples.pms.charge.domain.Timeline
import java.time.DayOfWeek

data class DayOfWeekChargingStrategiesRequest(
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
        fun toRecurringSchedule(): RecurringChargingStrategy =
            RecurringChargingStrategy(
                dayOfWeek = this.dayOfWeek,
                mode = this.mode,
                rate = this.rate,
                timeline = Timeline(timeline.map { it.toTimeSlot() }),
            )
    }

    fun toDayOfWeekChargingStrategies(): RecurringChargingStrategies {
        val settings: List<RecurringChargingStrategy> =
            listOfNotNull(this.sun, this.mon, this.tue, this.wed, this.thu, this.fri, this.sat)
                .flatten()
                .map { it.toRecurringSchedule() }
        return RecurringChargingStrategies(settings)
    }
}
