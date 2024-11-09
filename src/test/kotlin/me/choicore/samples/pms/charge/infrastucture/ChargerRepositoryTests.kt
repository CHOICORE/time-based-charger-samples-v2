package me.choicore.samples.pms.charge.infrastucture

import me.choicore.samples.bak.ChargingMode.DISCHARGE
import me.choicore.samples.pms.charge.domain.TimeSlot
import me.choicore.samples.pms.charge.domain.Timeline
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL
import java.time.DayOfWeek.SUNDAY
import java.time.LocalTime

@DataJpaTest
@TestConstructor(autowireMode = ALL)
class ChargerRepositoryTests(
    private val chargerStrategyJpaRepository: ChargerStrategyEntityRepository,
) {
    @Test
    fun t1() {
        val timeline =
            Timeline(
                listOf(
                    TimeSlot(LocalTime.MIN, LocalTime.of(6, 0)),
                    TimeSlot(LocalTime.of(6, 0), LocalTime.of(12, 0)),
                ),
            )
        val chargerStrategyEntity =
            ChargingStationEntity(
                1,
                SUNDAY,
                specifiedDate = null,
                mode = DISCHARGE,
                rate = 100,
                timeline = timeline,
            )

        chargerStrategyJpaRepository.save(chargerStrategyEntity)

        chargerStrategyJpaRepository.findByPolicyId(1).also { println(it) }
    }
}
