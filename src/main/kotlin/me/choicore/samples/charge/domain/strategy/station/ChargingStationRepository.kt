package me.choicore.samples.charge.domain.strategy.station

interface ChargingStationRepository {
    fun save(station: ChargingStation): Long

    fun findAllByComplexId(complexId: Long): List<ChargingStation>
}
