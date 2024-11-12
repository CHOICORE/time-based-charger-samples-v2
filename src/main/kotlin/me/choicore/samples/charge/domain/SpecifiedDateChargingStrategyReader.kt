package me.choicore.samples.charge.domain

import org.springframework.stereotype.Service

@Service
class SpecifiedDateChargingStrategyReader(
    private val specifiedDateChargingStrategyRepository: SpecifiedDateChargingStrategyRepository,
) {
    fun getSpecifiedDateChargingStrategiesByComplexId(complexId: Long): List<SpecifiedDateChargingStrategy> =
        specifiedDateChargingStrategyRepository.findAllByComplexId(complexId = complexId)
}
