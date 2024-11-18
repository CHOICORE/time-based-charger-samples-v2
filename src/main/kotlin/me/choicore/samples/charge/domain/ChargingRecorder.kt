package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.core.ChargingStatus
import me.choicore.samples.charge.domain.target.ChargingTarget
import me.choicore.samples.charge.domain.target.ChargingTargetRepository
import me.choicore.samples.charge.domain.target.ChargingUnit
import me.choicore.samples.charge.domain.target.ChargingUnitRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChargingRecorder(
    private val chargingTargetRepository: ChargingTargetRepository,
    private val chargingUnitRepository: ChargingUnitRepository,
) {
    @Transactional
    fun record(
        target: ChargingTarget,
        units: List<ChargingUnit>,
    ) {
        chargingUnitRepository.saveAll(units)
        chargingTargetRepository.updateForStatus(target)
    }

    @Transactional
    fun calibrate(target: ChargingTarget) {
        require(target.status == ChargingStatus.CALIBRATED) {
            "Target status must be CALIBRATED"
        }

        chargingUnitRepository.markAsInactiveByTargetIdAndChargedOnGreatThanEqual(
            target.targetId,
            target.departedOn!!,
            target.status.name,
        )
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
            target.status.name,
        )
    }
}
