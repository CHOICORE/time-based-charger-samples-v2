package me.choicore.samples.pms.charge.domain

abstract class AbstractScheduleValidator<K, S : ChargingStrategy> : ScheduleValidator<K, S> {
    protected fun validateMismatchSchedule(
        basis: K,
        invalid: List<K>,
    ) {
        require(invalid.isEmpty()) {
            "The specified schedule contains values that are not equal to the basis ($basis). Found: ${
                invalid.joinToString(", ")
            }"
        }
    }

    protected fun validateOverlappingTimeSlots(schedules: List<S>) {
        val allTimeSlots: List<TimeSlot> =
            schedules
                .flatMap { it.timeline.slots }
                .sortedBy { it.startTimeInclusive }

        for (i in 0 until allTimeSlots.size - 1) {
            val current: TimeSlot = allTimeSlots[i]
            val next: TimeSlot = allTimeSlots[i + 1]

            if (current.overlapsWith(next)) {
                throw IllegalStateException("The specified schedule contains overlapping timeslots: $current, $next")
            }
        }
    }
}
