package me.choicore.samples.charge.domain

import java.time.LocalDate

interface ChargingUnitRepository {
    fun save(unit: ChargingUnit): ChargingUnit

    fun markAsInactiveByTargetIdAndChargedOnGreatThanEqual(
        targetId: Long,
        chargedOn: LocalDate,
    )
}
