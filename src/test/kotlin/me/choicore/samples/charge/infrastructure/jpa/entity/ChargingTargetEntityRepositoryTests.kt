package me.choicore.samples.charge.infrastructure.jpa.entity

import me.choicore.samples.charge.domain.ChargingStatus.REGISTERED
import me.choicore.samples.charge.domain.ChargingTarget
import me.choicore.samples.charge.domain.ChargingTarget.ChargingTargetIdentifier
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@TestConstructor(autowireMode = ALL)
class ChargingTargetEntityRepositoryTests(
    private val chargingTargetEntityRepository: ChargingTargetEntityRepository,
) {
    @Test
    fun t1() {
        val chargingTarget =
            ChargingTarget(
                identifier =
                    ChargingTargetIdentifier.unregistered(
                        complexId = 1,
                        building = "100",
                        unit = "100",
                        licensePlate = "123가1234",
                    ),
                arrivedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                departedAt = null,
                status = REGISTERED,
                lastChargedOn = null,
            )

        chargingTargetEntityRepository.save(
            ChargingTargetEntity(chargingTarget),
        )
        val targets =
            chargingTargetEntityRepository.findByComplexIdAndLastChargedOnIsNullOrLastChargedOnLessThanEqual(
                1,
                LocalDate.now(),
            )

        println(targets)
    }
}
