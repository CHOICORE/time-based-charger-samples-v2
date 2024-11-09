package me.choicore.samples.pms.charge.domain

import org.springframework.stereotype.Service

@Service
class ChargingStationRegistrar(
    private val chargingStationRepository: ChargingStationRepository,
) {
    fun register(station: ChargingStation): ChargingStation = chargingStationRepository.save(station)
}
