package me.choicore.samples.charge.domain

interface ChargingMode {
    val method: ChargingMethod
    val rate: Int

    fun charge(charge: Charge): Charge

    data class Surcharge(
        val percentage: Int,
    ) : ChargingMode {
        override val method: ChargingMethod = ChargingMethod.SURCHARGE
        override val rate: Int = this.percentage

        override fun charge(charge: Charge): Charge {
            TODO()
        }
    }

    data class Discharge(
        val percentage: Int,
    ) : ChargingMode {
        override val rate: Int = this.percentage
        override val method: ChargingMethod = ChargingMethod.DISCHARGE

        override fun charge(charge: Charge): Charge {
            TODO("Not yet implemented")
        }
    }

    data object Standard : ChargingMode {
        override val method: ChargingMethod = ChargingMethod.STANDARD
        override val rate: Int = 0

        override fun charge(charge: Charge): Charge {
            TODO("Not yet implemented")
        }
    }
}
