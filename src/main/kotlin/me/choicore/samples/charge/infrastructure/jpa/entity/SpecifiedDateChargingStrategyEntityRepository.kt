package me.choicore.samples.charge.infrastructure.jpa.entity

import org.springframework.data.jpa.repository.JpaRepository

interface SpecifiedDateChargingStrategyEntityRepository : JpaRepository<SpecifiedDateChargingStrategyEntity, Long> {
    fun findByComplexId(complexId: Long): List<SpecifiedDateChargingStrategyEntity>
}
