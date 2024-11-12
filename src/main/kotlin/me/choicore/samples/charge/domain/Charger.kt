package me.choicore.samples.charge.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class Charger(
    private val chargingTargetRepository: ChargingTargetRepository,
    private val chargingUnitRepository: ChargingUnitRepository,
) {
    @Transactional
    fun charge(
        target: ChargingTarget,
        unit: ChargingUnit,
    ) {
        chargingUnitRepository.save(unit)
        chargingTargetRepository.update(target)
    }
}
