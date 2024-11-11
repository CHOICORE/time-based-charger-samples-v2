package me.choicore.samples.charge.domain

interface ChargingStationRepository {
    fun save(station: ChargingStation): Long

    fun findAllByComplexId(complexId: Long): List<ChargingStation>
}
