package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingStatus.EXEMPTED
import me.choicore.samples.charge.domain.ChargingStatus.PENDED
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@Order(1)
class ExemptChargingEvaluator(
    private val chargingTransactionRegistrar: ChargingTransactionRegistrar,
) : ChargingEvaluator {
    override fun evaluate(chargeRequest: ChargeRequest) {
        val (context: ChargingContext, target: ChargingTarget, chargedOn: LocalDate) = chargeRequest
        val chargingStation: ChargingStation = context.chargingStationSelector.select(chargedOn)
        if (!target.chargeable(chargedOn, chargingStation.exemptionThreshold)) {
            when (target.status) {
                EXEMPTED -> chargingTransactionRegistrar.exempt(target)
                PENDED -> chargingTransactionRegistrar.pend(target)
                else -> {}
            }
            chargeRequest.processed()
        }
    }
}
