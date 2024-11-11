package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.DayOfWeekChargingStrategy.DayOfWeekChargingStrategyIdentifier
import java.time.LocalDate

interface SpecifiedDateChargingStrategyRepository : ChargingStrategyRepository<SpecifiedDateChargingStrategy> {
    fun findBySpecifiedDate(
        identifier: DayOfWeekChargingStrategyIdentifier,
        specifiedDate: LocalDate,
    ): List<SpecifiedDateChargingStrategy>

    fun findAllByComplexId(complexId: Long): List<SpecifiedDateChargingStrategy>
}
