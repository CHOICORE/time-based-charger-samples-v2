package me.choicore.samples.charge.application

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL
import java.time.LocalDateTime

@SpringBootTest
@TestConstructor(autowireMode = ALL)
class AccessEventProcessorTests(
    private val accessEventProcessor: AccessEventProcessor,
) {
    @Test
    fun t1() {
        val event = AccessEvent(
            accessId = 1L,
            complexId = 1L,
            building = "100",
            unit = "101",
            licensePlate = "123가1234",
            type = AccessEvent.Type.ARRIVAL,
            accessedAt = LocalDateTime.now()
        )

        accessEventProcessor.process(event)
    }

}