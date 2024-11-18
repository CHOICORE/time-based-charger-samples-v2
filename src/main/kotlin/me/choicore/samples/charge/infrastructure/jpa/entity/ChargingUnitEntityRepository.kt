package me.choicore.samples.charge.infrastructure.jpa.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface ChargingUnitEntityRepository : JpaRepository<ChargingUnitEntity, Long> {
    @Modifying
    @Query(
        "UPDATE ChargingUnitEntity cu SET cu.active = false, cu.reason = :reason WHERE cu.targetId = :targetId and cu.chargedOn >= :chargedOn",
    )
    fun markAsInactiveByTargetIdAndChargedOnGreatThanEqual(
        targetId: Long,
        chargedOn: LocalDate,
        reason: String,
    )
}
