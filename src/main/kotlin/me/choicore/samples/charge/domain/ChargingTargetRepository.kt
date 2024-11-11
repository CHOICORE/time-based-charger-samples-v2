package me.choicore.samples.charge.domain

import java.time.LocalDate

interface ChargingTargetRepository {
    fun save(chargingTarget: ChargingTarget): ChargingTarget

    fun findByComplexIdAndLastChargedOnLessThanEqualOrNull(
        complexId: Long,
        lastChargedOn: LocalDate,
    ): List<ChargingTarget>
}
