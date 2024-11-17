package me.choicore.samples.charge.domain.target

import java.time.LocalDate

interface ChargingTargetRepository {
    fun findByCriteriaAndDepartedAtIsNullForUpdate(criteria: ChargingTargetCriteria): List<ChargingTarget>

    fun findByCriteria(criteria: ChargingTargetCriteria): List<ChargingTarget>

    fun findChargingTargetByAccessId(accessId: Long): ChargingTarget?

    fun save(chargingTarget: ChargingTarget): ChargingTarget

    fun updateForStatus(chargingTarget: ChargingTarget): ChargingTarget

    fun getChargingTargetsByComplexIdAndChargedOn(
        complexId: Long,
        chargedOn: LocalDate,
        size: Int,
    ): List<ChargingTarget>
}
