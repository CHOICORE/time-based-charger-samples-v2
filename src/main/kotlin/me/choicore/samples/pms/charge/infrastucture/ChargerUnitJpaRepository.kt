package me.choicore.samples.pms.charge.infrastucture

import org.springframework.data.jpa.repository.JpaRepository

interface ChargerUnitJpaRepository : JpaRepository<ChargerUnit, Long> {
    fun findByPolicyId(policyId: Long): List<ChargerUnit>
}
