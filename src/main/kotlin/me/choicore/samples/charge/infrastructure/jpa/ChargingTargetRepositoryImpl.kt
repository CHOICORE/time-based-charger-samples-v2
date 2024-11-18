package me.choicore.samples.charge.infrastructure.jpa

import me.choicore.samples.charge.domain.core.ChargingStatus.CHARGING
import me.choicore.samples.charge.domain.core.ChargingStatus.PENDED
import me.choicore.samples.charge.domain.core.ChargingStatus.REGISTERED
import me.choicore.samples.charge.domain.target.ChargingTarget
import me.choicore.samples.charge.domain.target.ChargingTargetCriteria
import me.choicore.samples.charge.domain.target.ChargingTargetRepository
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingTargetEntity
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingTargetEntityRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Repository
@Transactional(readOnly = true)
class ChargingTargetRepositoryImpl(
    private val chargingTargetEntityRepository: ChargingTargetEntityRepository,
) : ChargingTargetRepository {
    override fun findByCriteriaAndDepartedAtIsNullForUpdate(criteria: ChargingTargetCriteria): List<ChargingTarget> =
        chargingTargetEntityRepository
            .findByCriteriaAndDepartedAtIsNullForUpdate(criteria)
            .map { it.toChargingTarget() }

    override fun findByCriteria(criteria: ChargingTargetCriteria): List<ChargingTarget> {
        TODO()
    }

    override fun findChargingTargetByAccessId(accessId: Long): ChargingTarget? =
        chargingTargetEntityRepository.findByAccessId(accessId)?.toChargingTarget()

    @Transactional
    override fun save(chargingTarget: ChargingTarget): ChargingTarget {
        val entity = ChargingTargetEntity(chargingTarget)
        return chargingTargetEntityRepository.save(entity).toChargingTarget()
    }

    @Transactional
    override fun updateForStatus(chargingTarget: ChargingTarget): ChargingTarget {
        chargingTargetEntityRepository.updateForStatus(
            targetId = chargingTarget.targetId,
            status = chargingTarget.status,
            lastChargedOn = chargingTarget.lastChargedOn,
        )

        return chargingTarget
    }

    override fun getChargingTargetsByComplexIdAndChargedOn(
        complexId: Long,
        chargedOn: LocalDate,
        size: Int,
    ): List<ChargingTarget> =
        chargingTargetEntityRepository
            .findByComplexIdAndArrivedAtIsBeforeAndLastChargedOnIsNullOrLastChargedOnLessThanAndStatusIn(
                complexId = complexId,
                arrivedAtThreshold = chargedOn.plusDays(1).atStartOfDay(),
                lastChargedOn = chargedOn,
                statuses = setOf(REGISTERED, CHARGING, PENDED),
                pageable = PageRequest.ofSize(size),
            ).map { it.toChargingTarget() }
}
