package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingStatus.EXEMPTED
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
            if (target.status == EXEMPTED) {
                chargingTransactionRegistrar.exempt(target)
            }
            chargeRequest.processed()
        }
    }
}
