package me.choicore.samples.pms.charge.infrastucture

import me.choicore.samples.pms.charge.domain.ChargingStation
import me.choicore.samples.pms.charge.domain.ChargingStationRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ChargingStationJpaRepository(
    private val chargingStationEntityRepository: ChargingStationEntityRepository,
    private val chargerStrategyEntityRepository: ChargerStrategyEntityRepository,
) : ChargingStationRepository {
    @Transactional
    override fun save(chargingStation: ChargingStation): ChargingStation {
        val entity = ChargingStationEntity(chargingStation = chargingStation)
        val saved: ChargingStationEntity = chargingStationEntityRepository.save(entity)
        return chargingStation.copy(id = saved.id)
    }
}
