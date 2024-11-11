package me.choicore.samples.charge.domain

import org.springframework.stereotype.Service

@Service
class ChargingStationRegistrar(
    private val chargingStationRepository: ChargingStationRepository,
) {
    fun register(chargingStation: ChargingStation): Long = chargingStationRepository.save(chargingStation)
}
