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
        units: List<ChargingUnit>,
    ) {
        chargingUnitRepository.saveAll(units)
        chargingTargetRepository.updateForStatus(target)
    }

    @Transactional
    fun pend(target: ChargingTarget) {
        require(target.status == ChargingStatus.PENDED) {
            "Target status must be Pending"
        }
        chargingTargetRepository.updateForStatus(target)
    }

    @Transactional
    fun exempt(target: ChargingTarget) {
        require(target.status == ChargingStatus.EXEMPTED) {
            "Target status must be Exempted"
        }
        chargingTargetRepository.updateForStatus(target)
        chargingUnitRepository.markAsInactiveByTargetIdAndChargedOnGreatThanEqual(
            target.targetId,
            target.arrivedOn,
        )
    }
}
