package me.choicore.samples.charge.domain

interface ChargingStationRepository {
    fun save(station: ChargingStation): Long

    fun findByComplexId(complexId: Long): List<ChargingStation>
}
