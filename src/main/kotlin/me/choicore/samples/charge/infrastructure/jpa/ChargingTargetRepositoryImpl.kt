package me.choicore.samples.charge.infrastructure.jpa

import me.choicore.samples.charge.domain.ChargingStatus.CHARGING
import me.choicore.samples.charge.domain.ChargingStatus.REGISTERED
import me.choicore.samples.charge.domain.ChargingTarget
import me.choicore.samples.charge.domain.ChargingTargetRepository
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingTargetEntity
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingTargetEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Repository
@Transactional(readOnly = true)
class ChargingTargetRepositoryImpl(
    private val chargingTargetEntityRepository: ChargingTargetEntityRepository,
) : ChargingTargetRepository {
    @Transactional
    override fun save(chargingTarget: ChargingTarget): ChargingTarget {
        val entity = ChargingTargetEntity(chargingTarget)
        return chargingTargetEntityRepository.save(entity).toChargingTarget()
    }

    @Transactional
    override fun update(chargingTarget: ChargingTarget): ChargingTarget {
        val entity: ChargingTargetEntity =
            chargingTargetEntityRepository.findByIdOrNull(chargingTarget.identifier.targetId)
                ?: throw NoSuchElementException("ChargingTarget not found")

        entity.update(chargingTarget)
        return chargingTarget
    }

    override fun getChargingTargetsByComplexIdAndChargedOn(
        complexId: Long,
        chargedOn: LocalDate,
    ): List<ChargingTarget> =
        chargingTargetEntityRepository
            .findByComplexIdAndLastChargedOnIsNullOrLastChargedOnLessThanEqualAndStatusIn(
                complexId = complexId,
                lastChargedOn = chargedOn,
                status = setOf(REGISTERED, CHARGING),
            ).map { it.toChargingTarget() }
}
