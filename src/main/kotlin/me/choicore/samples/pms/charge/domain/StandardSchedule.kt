package me.choicore.samples.pms.charge.domain

import me.choicore.samples.pms.charge.domain.ScheduleValidators.RecurringScheduleValidator
import java.time.DayOfWeek.FRIDAY
import java.time.DayOfWeek.MONDAY
import java.time.DayOfWeek.SATURDAY
import java.time.DayOfWeek.SUNDAY
import java.time.DayOfWeek.THURSDAY
import java.time.DayOfWeek.TUESDAY
import java.time.DayOfWeek.WEDNESDAY

data class StandardSchedule(
    val sun: List<RecurringSchedule>? = emptyList(),
    val mon: List<RecurringSchedule>? = emptyList(),
    val tue: List<RecurringSchedule>? = emptyList(),
    val wed: List<RecurringSchedule>? = emptyList(),
    val thu: List<RecurringSchedule>? = emptyList(),
    val fri: List<RecurringSchedule>? = emptyList(),
    val sat: List<RecurringSchedule>? = emptyList(),
) {
    init {
        RecurringScheduleValidator.validate(SUNDAY, sun ?: emptyList())
        RecurringScheduleValidator.validate(MONDAY, mon ?: emptyList())
        RecurringScheduleValidator.validate(TUESDAY, tue ?: emptyList())
        RecurringScheduleValidator.validate(WEDNESDAY, wed ?: emptyList())
        RecurringScheduleValidator.validate(THURSDAY, thu ?: emptyList())
        RecurringScheduleValidator.validate(FRIDAY, fri ?: emptyList())
        RecurringScheduleValidator.validate(SATURDAY, sat ?: emptyList())
    }
}
