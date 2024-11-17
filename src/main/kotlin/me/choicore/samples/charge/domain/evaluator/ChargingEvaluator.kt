package me.choicore.samples.charge.domain.evaluator

import me.choicore.samples.charge.domain.ChargeRequest

sealed interface ChargingEvaluator {
    fun evaluate(chargeRequest: ChargeRequest)
}
