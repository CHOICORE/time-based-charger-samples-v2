package me.choicore.samples.charge.domain.strategy.station

import org.springframework.stereotype.Service

@Service
class ChargingStationReader(
    private val chargingStationRepository: ChargingStationRepository,
) {
    fun getChargingStations(complexId: Long): List<ChargingStation> = chargingStationRepository.findAllByComplexId(complexId = complexId)
}
