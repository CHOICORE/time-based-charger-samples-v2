package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingMode.Standard
import me.choicore.samples.charge.domain.ChargingMode.Surcharge
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategy.DayOfWeekChargingStrategyIdentifier
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategy.SpecifiedDateChargingStrategyIdentifier
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

class CompositeChargingStrategiesTests {
    @Test
    fun t1() {
        val strategies =
            CompositeChargingStrategies
                .builder()
                .once(
                    SpecifiedDateChargingStrategy(
                        SpecifiedDateChargingStrategyIdentifier.registered(1, 1),
                        LocalDate.now(),
                        Surcharge(10),
                        Timeline(
                            listOf(TimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0))),
                        ),
                    ),
                ).build()

        val chargingStrategies = strategies.getChargingStrategies(LocalDate.now()).toMutableList()
        val existingTimeSlots = chargingStrategies.flatMap { it.timeline.slots }.sortedBy { it.startTimeInclusive }
        val remainingTimeline = Timeline()
        var previous: LocalTime = LocalTime.MIDNIGHT
        existingTimeSlots.forEach { slot ->
            if (slot.startTimeInclusive > previous) {
                remainingTimeline.addSlot(previous, slot.startTimeInclusive)
            }
            previous = slot.endTimeInclusive
        }
        if (previous < LocalTime.MAX) {
            remainingTimeline.addSlot(previous, LocalTime.MAX)
        }
        if (remainingTimeline.slots.isNotEmpty()) {
            chargingStrategies.add(
                DayOfWeekChargingStrategy(
                    DayOfWeekChargingStrategyIdentifier.registered(1, 1, 1),
                    LocalDate.now().dayOfWeek,
                    Standard,
                    remainingTimeline,
                ),
            )
        }
        println(chargingStrategies)
    }
}
