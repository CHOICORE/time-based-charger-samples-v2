package me.choicore.samples.charge.infrastructure.jpa

import me.choicore.samples.charge.domain.DayOfWeekChargingStrategy
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategyRepository
import me.choicore.samples.charge.infrastructure.jpa.entity.DayOfWeekChargingStrategyEntityRepository
import org.springframework.stereotype.Repository
import java.time.DayOfWeek

@Repository
class DayOfWeekChargingStrategyJpaRepository(
    private val dayOfWeekChargingStrategyEntityRepository: DayOfWeekChargingStrategyEntityRepository,
) : DayOfWeekChargingStrategyRepository {
    override fun findByDayOfWeek(dayOfWeek: DayOfWeek): List<DayOfWeekChargingStrategy> {
        TODO()
    }
}
