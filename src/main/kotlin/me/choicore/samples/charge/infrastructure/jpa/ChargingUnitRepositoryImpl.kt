package me.choicore.samples.charge.infrastructure.jpa

import me.choicore.samples.charge.domain.ChargingUnit
import me.choicore.samples.charge.domain.ChargingUnit.ChargingDetail
import me.choicore.samples.charge.domain.ChargingUnitRepository
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
        val cu: ChargingUnitEntity = chargingUnitEntityRepository.save(ChargingUnitEntity(unit))
        val details: List<ChargingDetail> = unit.details
        val chargingUnit: ChargingUnit = cu.toChargingUnit()
        for (detail: ChargingDetail in details) {
            val cd: ChargingDetailEntity = chargingDetailEntityRepository.save(ChargingDetailEntity(cu.id, detail))
            chargingUnit.addDetail(detail.copy(detailId = cd.id, unitId = cu.id))
        }

        return chargingUnit
    }

    @Transactional
    override fun markAsInactiveByTargetIdAndChargedOnGreatThanEqual(
        targetId: Long,
        chargedOn: LocalDate,
    ) {
        chargingUnitEntityRepository.markAsInactiveByTargetIdAndChargedOnGreatThanEqual(targetId, chargedOn)
    }
}
