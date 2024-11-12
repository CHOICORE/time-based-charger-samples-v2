package me.choicore.samples.charge.domain

import java.time.LocalDate

interface ChargingTargetRepository {
    fun save(chargingTarget: ChargingTarget): ChargingTarget

    fun update(chargingTarget: ChargingTarget): ChargingTarget

    fun getChargingTargetsByComplexIdAndChargedOn(
        complexId: Long,
        chargedOn: LocalDate,
    ): List<ChargingTarget>
}
