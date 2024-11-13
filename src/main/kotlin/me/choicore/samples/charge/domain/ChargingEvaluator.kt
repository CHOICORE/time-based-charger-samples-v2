package me.choicore.samples.charge.domain

sealed interface ChargingEvaluator {
    fun evaluate(chargeRequest: ChargeRequest)
}
