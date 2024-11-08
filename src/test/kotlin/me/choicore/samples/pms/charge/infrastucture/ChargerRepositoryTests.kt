package me.choicore.samples.pms.charge.infrastucture

import me.choicore.samples.pms.charge.domain.ChargingMode.DISCHARGE
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
    private val chargerUnitJpaRepository: ChargerUnitJpaRepository,
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
        val chargerUnit =
            ChargerUnit(
                1,
                SUNDAY,
                specifiedDate = null,
                mode = DISCHARGE,
                rate = 100,
                timeline = timeline,
            )

        chargerUnitJpaRepository.save(chargerUnit)

        chargerUnitJpaRepository.findByPolicyId(1).also { println(it) }
    }
}
