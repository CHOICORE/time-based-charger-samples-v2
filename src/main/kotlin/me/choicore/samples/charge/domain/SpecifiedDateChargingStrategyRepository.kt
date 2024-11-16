package me.choicore.samples.charge.domain

import java.time.LocalDate

interface SpecifiedDateChargingStrategyRepository : ChargingStrategyRepository<SpecifiedDateChargingStrategy> {
    fun findBySpecifiedDate(specifiedDate: LocalDate): List<SpecifiedDateChargingStrategy>

    fun findAllByComplexId(complexId: Long): List<SpecifiedDateChargingStrategy>
}
