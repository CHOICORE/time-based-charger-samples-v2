package me.choicore.samples.charge.infrastructure.jpa

import me.choicore.samples.charge.domain.target.ChargingUnit
import me.choicore.samples.charge.domain.target.ChargingUnitRepository
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingDetailEntity
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingDetailEntityRepository
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingUnitEntity
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingUnitEntityRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Repository
@Transactional(readOnly = true)
class ChargingUnitRepositoryImpl(
    private val chargingUnitEntityRepository: ChargingUnitEntityRepository,
    private val chargingDetailEntityRepository: ChargingDetailEntityRepository,
) : ChargingUnitRepository {
    @Transactional
    override fun save(unit: ChargingUnit): ChargingUnit {
        val savedUnit: ChargingUnitEntity = chargingUnitEntityRepository.save(ChargingUnitEntity(unit))

        val detailEntities: List<ChargingDetailEntity> =
            unit.details.map { detail ->
                ChargingDetailEntity(savedUnit.id, detail)
            }

        val savedDetails: List<ChargingDetailEntity> = chargingDetailEntityRepository.saveAll(detailEntities)

        return savedUnit.toChargingUnit().apply {
            savedDetails.forEach { detail ->
                addDetail(detail.toChargingDetail())
            }
        }
    }

    @Transactional
    override fun saveAll(units: List<ChargingUnit>): List<ChargingUnit> = units.map { unit -> this.save(unit) }

    @Transactional
    override fun markAsInactiveByTargetIdAndChargedOnGreatThanEqual(
        targetId: Long,
        chargedOn: LocalDate,
        reason: String,
    ) {
        chargingUnitEntityRepository.markAsInactiveByTargetIdAndChargedOnGreatThanEqual(targetId, chargedOn, reason)
    }
}
