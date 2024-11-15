package me.choicore.samples.charge.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ChargingTargetReader(
    private val chargingTargetRepository: ChargingTargetRepository,
) {
    @Transactional
    fun findByCriteriaAndDepartedAtIsNullForUpdate(criteria: ChargingTargetCriteria): List<ChargingTarget> =
        chargingTargetRepository.findByCriteriaAndDepartedAtIsNullForUpdate(criteria)

    fun getChargingTargetsByComplexIdAndChargedOn(
        complexId: Long,
        chargedOn: LocalDate,
        size: Int,
    ): List<ChargingTarget> =
        chargingTargetRepository.getChargingTargetsByComplexIdAndChargedOn(
            complexId = complexId,
            chargedOn = chargedOn,
            size = size,
        )

    fun findChargingTargetByAccessId(accessId: Long): ChargingTarget? = chargingTargetRepository.findChargingTargetByAccessId(accessId)
}
