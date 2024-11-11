package me.choicore.samples.charge.domain

import org.springframework.stereotype.Service

@Service
class ChargingStationSelectorProvider(
    private val chargingStationReader: ChargingStationReader,
) {
    fun getChargingStationSelector(complexId: Long): ChargingStationSelector =
        ChargingStationSelector(stations = chargingStationReader.getChargingStations(complexId = complexId))
}
