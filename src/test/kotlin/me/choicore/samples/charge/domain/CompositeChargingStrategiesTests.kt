package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.core.ChargingMode.Surcharge
import me.choicore.samples.charge.domain.core.TimeSlot
import me.choicore.samples.charge.domain.core.Timeline
import me.choicore.samples.charge.domain.strategy.CompositeChargingStrategies
import me.choicore.samples.charge.domain.strategy.DayOfWeekChargingStrategy
import me.choicore.samples.charge.domain.strategy.SpecifiedDateChargingStrategy
import me.choicore.samples.charge.domain.target.ChargingUnit
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
                        strategyId = 1,
                        complexId = 1,
                        LocalDate.now(),
                        Surcharge(10),
                        Timeline(
                            listOf(TimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0))),
                        ),
                    ),
                ).repeatable(
                    DayOfWeekChargingStrategy(
                        strategyId = 2,
                        complexId = 2,
                        stationId = 1,
                        LocalDate.now().dayOfWeek,
                        Surcharge(20),
                        Timeline(
                            listOf(TimeSlot(LocalTime.of(14, 0), LocalTime.of(16, 0))),
                        ),
                    ),
                ).build()

        val chargingUnit =
            ChargingUnit(
                unitId = 1,
                targetId = 1,
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
