package me.choicore.samples.pms.charge.infrastucture

import me.choicore.samples.pms.charge.domain.ChargingStation
import me.choicore.samples.pms.charge.domain.ChargingStationRegistration
import me.choicore.samples.pms.charge.domain.ChargingStationRepository

class ChargingStationJpaRepository : ChargingStationRepository {
    override fun save(chargingStationRegistration: ChargingStationRegistration): ChargingStation {
        TODO()
    }
}
