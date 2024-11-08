package me.choicore.samples.pms.charge.domain

import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalTime

class DayOfWeekChargingStrategiesTests {
    @Test
    fun t1() {
        val schedule1 =
            RecurringSchedule(
                dayOfWeek = DayOfWeek.MONDAY,
                mode = ChargingMode.DISCHARGE,
                rate = 10,
                timeline = Timeline().apply { this.addSlot(LocalTime.MIN, LocalTime.MAX) },
            )
        val schedule2 =
            RecurringSchedule(
                dayOfWeek = DayOfWeek.TUESDAY,
                mode = ChargingMode.SURCHARGE,
                rate = 10,
                timeline = Timeline().apply { this.addSlot(LocalTime.MIN, LocalTime.MAX) },
            )

        val dayOfWeekChargingStrategies = DayOfWeekChargingStrategies(listOf(schedule1, schedule2))
    }
}
