package me.choicore.samples.charge.infrastructure.jpa.entity

import org.springframework.data.jpa.repository.JpaRepository

interface ChargingStationEntityRepository : JpaRepository<ChargingStationEntity, Long> {
    fun findByComplexId(complexId: Long): List<ChargingStationEntity>
}
