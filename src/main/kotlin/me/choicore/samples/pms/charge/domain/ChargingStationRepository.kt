package me.choicore.samples.pms.charge.domain

interface ChargingStationRepository {
    fun save(chargingStationRegistration: ChargingStationRegistration): ChargingStation
}
