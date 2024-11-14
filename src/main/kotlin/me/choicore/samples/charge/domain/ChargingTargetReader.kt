package me.choicore.samples.charge.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ChargingTargetReader(
    private val chargingTargetRepository: ChargingTargetRepository,
) {
    @Transactional
    fun findByCriteriaAndDepartedAtIsNullForUpdate(criteria: ChargingTargetCriteria): List<ChargingTarget> {
        return chargingTargetRepository.findByCriteriaAndDepartedAtIsNullForUpdate(criteria)
    }

    fun getChargingTargetsByComplexIdAndChargedOn(
        complexId: Long,
        chargedOn: LocalDate,
    ): List<ChargingTarget> =
        chargingTargetRepository.getChargingTargetsByComplexIdAndChargedOn(
            complexId = complexId,
            chargedOn = chargedOn,
        )
}
