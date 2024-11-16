package me.choicore.samples.charge.infrastructure.jpa.entity

import me.choicore.samples.charge.domain.ChargingUnit
import me.choicore.samples.charge.domain.TimeUtils
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL
import java.time.LocalDate

@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = NONE)
@TestConstructor(autowireMode = ALL)
class ChargingUnitEntityRepositoryTests(
    private val chargingUnitEntityRepository: ChargingUnitEntityRepository,
    private val testEntityManager: TestEntityManager,
) {
    @Test
    fun t1() {
        val chargingUnit =
            ChargingUnit(
                targetId = 1,
                chargedOn = LocalDate.now(),
                startTime = TimeUtils.MAX_TIME,
                endTime = TimeUtils.MAX_TIME,
            )

        chargingUnitEntityRepository.save(
            ChargingUnitEntity(
                chargingUnit = chargingUnit,
            ),
        )
        testEntityManager.flush()
        testEntityManager.clear()

        chargingUnitEntityRepository.findAll().forEach {
            println(it.toChargingUnit())
        }
    }

    @Test
    fun t2() {
        chargingUnitEntityRepository.findAll().forEach {
            println(it.toChargingUnit())
        }
    }
}
