package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingMode.Surcharge
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

        println(strategies.getChargingStrategies(LocalDate.now()))
    }
}
