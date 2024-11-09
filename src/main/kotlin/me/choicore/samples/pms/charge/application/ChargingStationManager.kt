package me.choicore.samples.pms.charge.application

import me.choicore.samples.pms.charge.domain.ChargingStationRegistrar
import org.springframework.stereotype.Service

@Service
class ChargingStationManager(
    private val chargingStationRegistrar: ChargingStationRegistrar,
) {
    fun register(registration: ChargingStationRegistration) {
        chargingStationRegistrar.register(station = registration.toChargingStation())
    }
}
