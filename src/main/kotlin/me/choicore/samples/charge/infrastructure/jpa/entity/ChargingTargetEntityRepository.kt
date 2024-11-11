package me.choicore.samples.charge.infrastructure.jpa.entity

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ChargingTargetEntityRepository : JpaRepository<ChargingTargetEntity, Long> {
    fun findByComplexIdAndLastChargedOnIsNullOrLastChargedOnLessThanEqual(
        complexId: Long,
        lastChargedOn: LocalDate,
    ): List<ChargingTargetEntity>
}
