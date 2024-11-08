package me.choicore.samples.pms.charge.infrastucture

import org.springframework.data.jpa.repository.JpaRepository

interface ChargerStrategyJpaRepository : JpaRepository<ChargerStrategyEntity, Long> {
    fun findByPolicyId(policyId: Long): List<ChargerStrategyEntity>
}
