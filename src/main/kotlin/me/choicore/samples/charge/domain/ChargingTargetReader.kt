package me.choicore.samples.charge.domain

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ChargingTargetReader(
    private val chargingTargetRepository: ChargingTargetRepository,
) {
    fun getChargingTargetsByComplexIdAndChargedOn(
        complexId: Long,
        chargedOn: LocalDate,
    ): List<ChargingTarget> =
        chargingTargetRepository.getChargingTargetsByComplexIdAndChargedOn(
            complexId = complexId,
            chargedOn = chargedOn,
        )
}
