package me.choicore.samples.charge.infrastructure.jpa.entity

import me.choicore.samples.charge.domain.ChargingStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ChargingTargetEntityRepository : JpaRepository<ChargingTargetEntity, Long> {
    fun findByComplexIdAndLastChargedOnIsNullOrLastChargedOnLessThanEqualAndStatusIn(
        complexId: Long,
        lastChargedOn: LocalDate,
        status: Set<ChargingStatus>,
    ): List<ChargingTargetEntity>
}
