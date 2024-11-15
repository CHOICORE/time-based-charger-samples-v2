package me.choicore.samples.charge.domain

import org.springframework.stereotype.Service

@Service
class ChargingTargetRegistrar(
    private val chargingTargetRepository: ChargingTargetRepository,
) {
    fun register(chargingTarget: ChargingTarget): ChargingTarget = chargingTargetRepository.save(chargingTarget)
}
