package me.choicore.samples.bak

import me.choicore.samples.pms.charge.domain.OneTimeChargingStrategy
import me.choicore.samples.pms.charge.domain.RecurringChargingStrategy
import java.time.DayOfWeek
import java.time.LocalDate

object ScheduleValidators {
    object RecurringScheduleValidator : AbstractScheduleValidator<DayOfWeek, RecurringChargingStrategy>() {
        override fun validate(
            basis: DayOfWeek,
            schedules: List<RecurringChargingStrategy>,
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

    object SpecifiedDateScheduleValidator : AbstractScheduleValidator<LocalDate, OneTimeChargingStrategy>() {
        override fun validate(
            basis: LocalDate,
            schedules: List<OneTimeChargingStrategy>,
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
