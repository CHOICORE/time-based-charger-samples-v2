package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingMode.Surcharge
import me.choicore.samples.charge.domain.ChargingUnit.ChargingUnitIdentifier
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
                ).repeatable(
                    DayOfWeekChargingStrategy(
                        DayOfWeekChargingStrategy.DayOfWeekChargingStrategyIdentifier.registered(2, 2, 1),
                        LocalDate.now().dayOfWeek,
                        Surcharge(20),
                        Timeline(
                            listOf(TimeSlot(LocalTime.of(14, 0), LocalTime.of(16, 0))),
                        ),
                    ),
                ).build()

        val chargingUnit =
            ChargingUnit(
                identifier = ChargingUnitIdentifier.registered(1, 1),
                chargedOn = LocalDate.now(),
                startTime = LocalTime.of(4, 0),
                endTime = LocalTime.of(23, 0),
            )

        strategies.charge(chargingUnit)

        chargingUnit.details.forEach {
            println("${it.basis} - ${it.originalAmount}, mode: ${it.mode.method} ${it.applied} - ${it.chargedAmount}")
        }
    }
}
