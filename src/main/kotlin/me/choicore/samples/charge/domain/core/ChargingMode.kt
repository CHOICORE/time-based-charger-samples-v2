package me.choicore.samples.charge.domain.core

import java.math.BigDecimal

interface ChargingMode {
    val method: ChargingMethod
    val rate: Int

    fun charge(amount: Long): BigDecimal

    data class Surcharge(
        val percentage: Int,
    ) : ChargingMode {
        override val method: ChargingMethod = ChargingMethod.SURCHARGE
        override val rate: Int = this.percentage

        override fun charge(amount: Long): BigDecimal = amount.toBigDecimal().multiply(BigDecimal(rate + 100)).divide(BigDecimal(100))
    }

    data class Discharge(
        val percentage: Int,
    ) : ChargingMode {
        override val rate: Int = this.percentage

        override fun charge(amount: Long): BigDecimal = amount.toBigDecimal().multiply(BigDecimal(100 - rate)).divide(BigDecimal(100))

        override val method: ChargingMethod = ChargingMethod.DISCHARGE
    }

    data object Standard : ChargingMode {
        override val method: ChargingMethod = ChargingMethod.STANDARD
        override val rate: Int = 0

        override fun charge(amount: Long): BigDecimal = amount.toBigDecimal()
    }
}
