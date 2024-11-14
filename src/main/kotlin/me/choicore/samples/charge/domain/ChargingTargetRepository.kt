package me.choicore.samples.charge.domain

import java.time.LocalDate

interface ChargingTargetRepository {
    fun findByCriteriaAndDepartedAtIsNullForUpdate(
        criteria: ChargingTargetCriteria,
    ): List<ChargingTarget>

    fun findByCriteria(criteria: ChargingTargetCriteria): List<ChargingTarget>

    fun save(chargingTarget: ChargingTarget): ChargingTarget

    fun updateForStatus(chargingTarget: ChargingTarget): ChargingTarget

    fun getChargingTargetsByComplexIdAndChargedOn(
        complexId: Long,
        chargedOn: LocalDate,
    ): List<ChargingTarget>
}
