package me.choicore.samples.pms.charge.domain

import me.choicore.samples.bak.ChargingMode
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalTime

class RecurringChargingStrategiesTests {
    @Test
    fun t1() {
        val schedule1 =
            RecurringChargingStrategy(
                dayOfWeek = DayOfWeek.MONDAY,
                mode = ChargingMode.DISCHARGE,
                rate = 10,
                timeline = Timeline().apply { this.addSlot(LocalTime.MIN, LocalTime.MAX) },
            )
        val schedule2 =
            RecurringChargingStrategy(
                dayOfWeek = DayOfWeek.TUESDAY,
                mode = ChargingMode.SURCHARGE,
                rate = 10,
                timeline = Timeline().apply { this.addSlot(LocalTime.MIN, LocalTime.MAX) },
            )

        val recurringChargingStrategies = RecurringChargingStrategies(listOf(schedule1, schedule2))
    }
}
