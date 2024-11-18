package me.choicore.samples.charge.domain.target

import java.time.LocalDate

interface ChargingUnitRepository {
    fun save(unit: ChargingUnit): ChargingUnit

    fun saveAll(units: List<ChargingUnit>): List<ChargingUnit>

    fun markAsInactiveByTargetIdAndChargedOnGreatThanEqual(
        targetId: Long,
        chargedOn: LocalDate,
        reason: String,
    )
}
