package me.choicore.samples.charge.infrastructure.jpa.entity

import jakarta.persistence.LockModeType.PESSIMISTIC_WRITE
import me.choicore.samples.charge.domain.ChargingStatus
import me.choicore.samples.charge.domain.ChargingTargetCriteria
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface ChargingTargetEntityRepository : JpaRepository<ChargingTargetEntity, Long> {
    @Query(
        """
        SELECT c
        FROM ChargingTargetEntity c
        WHERE c.complexId = :complexId
          AND (c.lastChargedOn IS NULL OR c.lastChargedOn < :lastChargedOn)
          AND c.status IN :statuses
        """,
    )
    fun findByComplexIdAndLastChargedOnIsNullOrLastChargedOnLessThanAndStatusIn(
        complexId: Long,
        lastChargedOn: LocalDate,
        statuses: Set<ChargingStatus>,
        pageable: Pageable,
    ): List<ChargingTargetEntity>

    fun findByAccessId(accessId: Long): ChargingTargetEntity?

    @Lock(PESSIMISTIC_WRITE)
    @Query(
        """
        SELECT c
        FROM ChargingTargetEntity c
        WHERE c.complexId = :#{#criteria.complexId}
          AND c.building = :#{#criteria.building}
          AND c.unit = :#{#criteria.unit}
          AND c.licensePlate = :#{#criteria.licensePlate}
          AND c.departedAt IS NULL
          AND c.status IN :#{#criteria.statuses}
        """,
    )
    fun findByCriteriaAndDepartedAtIsNullForUpdate(criteria: ChargingTargetCriteria): List<ChargingTargetEntity>

    @Modifying
    @Query(
        """
        UPDATE ChargingTargetEntity c
        set c.status = :status,
            c.lastChargedOn = :lastChargedOn
        where c._id = :id
        """,
    )
    fun charged(
        @Param("id") targetId: Long,
        @Param("status") status: ChargingStatus,
        @Param("lastChargedOn") lastChargedOn: LocalDate?,
    ): Int
}
