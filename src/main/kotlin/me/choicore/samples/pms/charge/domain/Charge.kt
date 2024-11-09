package me.choicore.samples.pms.charge.domain

import java.math.BigDecimal

data class Charge(
    val value: BigDecimal,
) {
    init {
        require(value > BigDecimal.ZERO) { "Charge amount must be greater than zero" }
    }

    fun add(charge: Charge): Charge = Charge(value.add(charge.value))
}
