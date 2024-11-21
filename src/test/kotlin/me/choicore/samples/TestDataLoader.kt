package me.choicore.samples

import me.choicore.samples.charge.domain.core.ChargingStatus
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingTargetEntity
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingTargetEntityRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.TestConstructor
import java.time.LocalDate
import java.time.LocalDateTime

@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TestDataLoader(
    private val chargingTargetEntityRepository: ChargingTargetEntityRepository,
) {
    @Test
    fun t1() {
        val buildings: List<String> = listOf("100", "200", "300")
        val plateLetters = "가나다라마바사아자차카타파하"
        val plateLastNumbers: IntRange = (1000..1010)
        var start: LocalDate = LocalDate.of(2024, 10, 1)
        val end: LocalDate = LocalDate.now()
        start = end.minusDays(2)

//        chargingTargetEntityRepository.deleteAll()
//        chargingTargetEntityRepository.save(
//            ChargingTargetEntity(
//                complexId = 1L,
//                building = "A",
//                unit = "A",
//                licensePlate = "123가1234",
//                arrivedAt =
//                    LocalDateTime.of(
//                        LocalDate.now().minusDays(1),
//                        LocalTime.MAX.truncatedTo(ChronoUnit.SECONDS),
//                    ),
//                departedAt = null,
//                status = REGISTERED,
//                lastChargedOn = null,
//            ),
//        )
//        val plusDays = start.plusDays(1)
        while (start < end) {
            (1..3)
                .map {
                    val building = buildings.random()
                    val unit = "${building.substring(0, 1)}${(1..10).random().toString().padStart(2, '0')}"
                    val plate = "${building}${plateLetters.random()}${plateLastNumbers.random()}"
                    val arrivedAt =
                        start
                            .atStartOfDay()
                            .plusSeconds((60000..86399).random().toLong())
                            .plusNanos((1..999999999).random().toLong())
                    var departedAt = arrivedAt.plusMinutes((1..120).random().toLong())
                    if (departedAt.toLocalDate() != arrivedAt.toLocalDate()) {
                        departedAt = null
                    }
                    ChargingTargetEntity(
                        complexId = (1..1).random().toLong(),
                        building = building,
                        unit = unit,
                        licensePlate = plate,
                        arrivedAt = arrivedAt,
                        departedAt = departedAt,
                        status = ChargingStatus.REGISTERED,
                        lastChargedOn = null,
                    )
                }.run { chargingTargetEntityRepository.saveAll(this) }
            start = start.plusDays(1)
        }
    }

    @Test
    fun t2() {
        val building = "101"
        val unit = "101"
        val plate = "123가1234"
        LocalDateTime.now()
        var departedAt = null

        ChargingTargetEntity(
            complexId = 1,
            building = building,
            unit = unit,
            licensePlate = plate,
            arrivedAt = LocalDate.now().atStartOfDay().minusMinutes(30),
            departedAt = departedAt,
            status = ChargingStatus.REGISTERED,
            lastChargedOn = null,
        ).also(chargingTargetEntityRepository::save)
    }
}
