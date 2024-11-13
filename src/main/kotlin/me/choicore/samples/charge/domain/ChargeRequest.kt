package me.choicore.samples.charge.domain

import java.time.LocalDate

data class ChargeRequest(
    val context: ChargingContext,
    val target: ChargingTarget,
    val chargedOn: LocalDate,
) {
    private var _processed: Boolean = false
    val processed: Boolean get() = this._processed

    fun processed() {
        this._processed = true
    }
}
