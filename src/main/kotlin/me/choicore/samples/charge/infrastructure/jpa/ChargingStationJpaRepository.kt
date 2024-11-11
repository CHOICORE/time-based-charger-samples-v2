package me.choicore.samples.charge.infrastructure.jpa

import me.choicore.samples.charge.domain.ChargingStation
import me.choicore.samples.charge.domain.ChargingStation.ChargingStationIdentifier
import me.choicore.samples.charge.domain.ChargingStationRepository
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategies
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategy
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategy.DayOfWeekChargingStrategyIdentifier
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingStationEntity
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingStationEntityRepository
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingStrategyEntity
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingStrategyEntityRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ChargingStationJpaRepository(
    private val chargingStationEntityRepository: ChargingStationEntityRepository,
    private val chargingStrategyEntityRepository: ChargingStrategyEntityRepository,
) : ChargingStationRepository {
    @Transactional
    override fun save(station: ChargingStation): Long {
        val entity = ChargingStationEntity(station)
        val saved: ChargingStationEntity = chargingStationEntityRepository.save(entity)
        val chargingStationId: Long = saved.id
        val complexId: Long = saved.complexId

        station.dayOfWeekChargingStrategies.getAll().forEach { strategy ->
            val chargingStrategy: DayOfWeekChargingStrategy =
                strategy.copy(
                    identifier =
                        DayOfWeekChargingStrategyIdentifier.unregistered(
                            complexId = complexId,
                            stationId = chargingStationId,
                        ),
                )
            val strategyEntity = ChargingStrategyEntity(chargingStrategy)
            chargingStrategyEntityRepository.save(strategyEntity)
        }
        return chargingStationId
    }

    override fun findByComplexId(complexId: Long): List<ChargingStation> {
        val entities: List<ChargingStationEntity> = chargingStationEntityRepository.findByComplexId(complexId)
        val stationIds: List<Long> = entities.map { it.id }
        val found: List<ChargingStrategyEntity> = chargingStrategyEntityRepository.findByStationIdIn(stationIds)
        val grouped: Map<Long, List<ChargingStrategyEntity>> = found.groupBy { it.stationId!! }
        return entities
            .map {
                val stationId = it.id
                val identity =
                    ChargingStationIdentifier.of(
                        stationId = stationId,
                        complexId = it.complexId,
                    )
                val strategies: List<DayOfWeekChargingStrategy> =
                    (grouped[stationId] ?: emptyList()).map { strategy -> strategy.toDayOfWeekChargingStrategy() }
                ChargingStation(
                    identifier = identity,
                    name = it.name,
                    description = it.description,
                    startsOn = it.startsOn,
                    endsOn = it.endsOn,
                    exemptionThreshold = it.exemptionThreshold,
                    dischargeAmount = it.dischargeAmount,
                    dayOfWeekChargingStrategies = DayOfWeekChargingStrategies(strategies),
                )
            }.toList()
    }
}
