package me.choicore.samples.charge.domain

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class DelegatingChargingEvaluator(
    private val evaluators: List<ChargingEvaluator>,
) : ChargingEvaluator {
    override fun evaluate(chargeRequest: ChargeRequest) {
        for (evaluator: ChargingEvaluator in evaluators) {
            if (!chargeRequest.processed) {
                evaluator.evaluate(chargeRequest)
            }
        }
    }
}
