package me.choicore.samples.charge.application

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@SpringBootTest
@TestConstructor(autowireMode = ALL)
class AccessEventProcessorTests(
    private val accessEventProcessor: AccessEventProcessor,
) {
    @Test
    fun t1() {
        val event =
            AccessEvent(
                accessId = 1L,
                complexId = 1L,
                building = "100",
                unit = "101",
                licensePlate = "123가1234",
                type = AccessEvent.Type.ARRIVAL,
                accessedAt = LocalDateTime.now(),
            )

        accessEventProcessor.process(event)
    }

    @Test
    fun t2() {
        val departedAt: LocalDate = LocalDate.of(2024, 11, 15).with(LocalTime.now())
        println(
            departedAt,
        )
    }
}
