package me.choicore.samples.charge.infrastructure.jpa

import me.choicore.samples.charge.domain.strategy.SpecifiedDateChargingStrategy
import me.choicore.samples.charge.domain.strategy.SpecifiedDateChargingStrategyRepository
import me.choicore.samples.charge.infrastructure.jpa.entity.SpecifiedDateChargingStrategyEntityRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class SpecifiedDateChargingStrategyRepositoryImpl(
    private val specifiedDateChargingStrategyEntityRepository: SpecifiedDateChargingStrategyEntityRepository,
) : SpecifiedDateChargingStrategyRepository {
    override fun findBySpecifiedDate(specifiedDate: LocalDate): List<SpecifiedDateChargingStrategy> {
        TODO("Not yet implemented")
    }

    override fun findAllByComplexId(complexId: Long): List<SpecifiedDateChargingStrategy> =
        specifiedDateChargingStrategyEntityRepository
            .findByComplexId(complexId = complexId)
            .map { it.toChargingStrategy() }
}
