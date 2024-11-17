package me.choicore.samples.charge.domain.strategy.station

import org.springframework.stereotype.Service

@Service
class ChargingStationRegistrar(
    private val chargingStationRepository: ChargingStationRepository,
) {
    fun register(chargingStation: ChargingStation): Long = chargingStationRepository.save(chargingStation)
}
