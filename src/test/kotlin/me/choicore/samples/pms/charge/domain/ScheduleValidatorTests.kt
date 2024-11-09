package me.choicore.samples.pms.charge.domain

import me.choicore.samples.bak.ChargingMode
import me.choicore.samples.bak.ScheduleValidators
import org.junit.jupiter.api.Test
import java.time.DayOfWeek.MONDAY
import java.time.DayOfWeek.TUESDAY
import java.time.DayOfWeek.WEDNESDAY
import java.time.LocalTime

class ScheduleValidatorTests {
    @Test
    fun t1() {
        val schedule1 =
            RecurringChargingStrategy(
                dayOfWeek = WEDNESDAY,
                mode = ChargingMode.DISCHARGE,
                rate = 10,
                timeline = Timeline().apply { this.addSlot(LocalTime.MIN, LocalTime.NOON) },
            )
        val schedule2 =
            RecurringChargingStrategy(
                dayOfWeek = MONDAY,
                mode = ChargingMode.SURCHARGE,
                rate = 10,
                timeline = Timeline().apply { this.addSlot(LocalTime.NOON, LocalTime.MAX) },
            )

        ScheduleValidators.RecurringScheduleValidator.validate(
            basis = TUESDAY,
            schedules = listOf(schedule1, schedule2),
        )
    }
}
