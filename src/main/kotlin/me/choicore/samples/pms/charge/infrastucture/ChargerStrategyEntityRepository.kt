package me.choicore.samples.pms.charge.infrastucture

import org.springframework.data.jpa.repository.JpaRepository

interface ChargerStrategyEntityRepository : JpaRepository<ChargingStationEntity, Long> {
    fun findByPolicyId(policyId: Long): List<ChargingStationEntity>
}
