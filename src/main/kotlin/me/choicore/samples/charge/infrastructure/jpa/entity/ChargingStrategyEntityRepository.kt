package me.choicore.samples.charge.infrastructure.jpa.entity

import org.springframework.data.jpa.repository.JpaRepository

interface ChargingStrategyEntityRepository : JpaRepository<ChargingStrategyEntity, Long> {
    fun findByComplexId(complexId: Long): List<ChargingStrategyEntity>

    fun findByStationIdIn(stationIds: List<Long>): List<ChargingStrategyEntity>
}
