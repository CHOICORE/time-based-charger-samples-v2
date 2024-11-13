package me.choicore.samples.charge.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChargingTransactionRegistrar(
    private val chargingTargetRepository: ChargingTargetRepository,
    private val chargingUnitRepository: ChargingUnitRepository,
) {
    @Transactional
    fun register(
        target: ChargingTarget,
        unit: ChargingUnit,
    ) {
        chargingUnitRepository.save(unit)
        chargingTargetRepository.update(target)
    }

    @Transactional
    fun exempt(target: ChargingTarget) {
        chargingTargetRepository.update(target)
        chargingUnitRepository.markAsInactiveByTargetIdAndChargedOnGreatThanEqual(
            target.identifier.targetId,
            target.arrivedOn,
        )
    }
}
