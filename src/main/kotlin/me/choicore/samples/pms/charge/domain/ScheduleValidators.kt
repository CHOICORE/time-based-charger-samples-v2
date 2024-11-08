package me.choicore.samples.pms.charge.domain

import java.time.DayOfWeek
import java.time.LocalDate

object ScheduleValidators {
    object RecurringScheduleValidator : AbstractScheduleValidator<DayOfWeek, RecurringSchedule>() {
        override fun validate(
            basis: DayOfWeek,
            schedules: List<RecurringSchedule>,
        ) {
            val invalid: List<DayOfWeek> =
                schedules
                    .asSequence()
                    .map { it.dayOfWeek }
                    .filterNot { it == basis }
                    .distinct()
                    .sorted()
                    .toList()
            super.validateMismatchSchedule(basis, invalid)
            super.validateOverlappingTimeSlots(schedules)
        }
    }

    object SpecifiedDateScheduleValidator : AbstractScheduleValidator<LocalDate, OneTimeSchedule>() {
        override fun validate(
            basis: LocalDate,
            schedules: List<OneTimeSchedule>,
        ) {
            val invalid: List<LocalDate> =
                schedules
                    .asSequence()
                    .map { it.specifiedDate }
                    .filterNot { it == basis }
                    .distinct()
                    .sorted()
                    .toList()

            super.validateMismatchSchedule(basis, invalid)
            super.validateOverlappingTimeSlots(schedules)
        }
    }
}
