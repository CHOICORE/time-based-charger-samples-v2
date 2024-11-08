package me.choicore.samples.pms.charge.domain

import java.time.DayOfWeek.*

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
        ScheduleValidators.RecurringScheduleValidator.validate(SUNDAY, sun!!)
        ScheduleValidators.RecurringScheduleValidator.validate(MONDAY, mon!!)
        ScheduleValidators.RecurringScheduleValidator.validate(TUESDAY, tue!!)
        ScheduleValidators.RecurringScheduleValidator.validate(WEDNESDAY, wed!!)
        ScheduleValidators.RecurringScheduleValidator.validate(THURSDAY, thu!!)
        ScheduleValidators.RecurringScheduleValidator.validate(FRIDAY, fri!!)
        ScheduleValidators.RecurringScheduleValidator.validate(SATURDAY, sat!!)
    }
}
