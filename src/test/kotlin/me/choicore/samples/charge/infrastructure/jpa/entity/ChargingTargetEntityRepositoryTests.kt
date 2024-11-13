package me.choicore.samples.charge.infrastructure.jpa.entity

import me.choicore.samples.charge.domain.ChargingStatus.REGISTERED
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL
import java.time.LocalDate

@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = NONE)
@TestConstructor(autowireMode = ALL)
class ChargingTargetEntityRepositoryTests(
    private val chargingTargetEntityRepository: ChargingTargetEntityRepository,
) {
    @Test
    fun t1() {
        val buildings: List<String> = listOf("100", "200", "300")
        val plateLetters = "가나다라마바사아자차카타파하"
        val plateLastNumbers: IntRange = (1000..1010)
        var start: LocalDate = LocalDate.of(2024, 11, 1)
        val end: LocalDate = LocalDate.now()
        start = end.minusDays(1)

        while (start < end) {
            (1..10)
                .map {
                    val building = buildings.random()
                    val unit = "${building.substring(0, 1)}${(1..10).random().toString().padStart(2, '0')}"
                    val plate = "${building}${plateLetters.random()}${plateLastNumbers.random()}"
                    val arrivedAt = start.atStartOfDay().plusSeconds((1..86399).random().toLong())
                    var departedAt = arrivedAt.plusMinutes((1..120).random().toLong())
                    if (departedAt.toLocalDate() != arrivedAt.toLocalDate()) {
                        departedAt = null
                    }
                    ChargingTargetEntity(
                        complexId = 1L,
                        building = building,
                        unit = unit,
                        licensePlate = plate,
                        arrivedAt = arrivedAt,
                        departedAt = departedAt,
                        status = REGISTERED,
                        lastChargedOn = null,
                    )
                }.run { chargingTargetEntityRepository.saveAll(this) }
            start = start.plusDays(1)
        }
    }
}
