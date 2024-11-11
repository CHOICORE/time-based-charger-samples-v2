package me.choicore.samples.charge.domain

import org.springframework.stereotype.Service

@Service
class ChargingStationReader(
    private val chargingStationRepository: ChargingStationRepository,
) {
    fun getChargingStation(complexId: Long): List<ChargingStation> = chargingStationRepository.findByComplexId(complexId = complexId)
}
