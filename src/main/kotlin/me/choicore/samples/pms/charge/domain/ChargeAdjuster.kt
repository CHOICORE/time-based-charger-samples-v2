package me.choicore.samples.pms.charge.domain

interface ChargeAdjuster {
    val mode: ChargingMode

    data class Discharge(
        val percentage: Int,
    ) : ChargeAdjuster {
        override val mode: ChargingMode = ChargingMode.DISCHARGE
    }

    class Standard : ChargeAdjuster {
        override val mode: ChargingMode = ChargingMode.STANDARD
    }

    data class Surcharge(
        val percentage: Int,
    ) : ChargeAdjuster {
        override val mode: ChargingMode = ChargingMode.SURCHARGE
    }

    enum class ChargingMode {
        DISCHARGE,
        STANDARD,
        SURCHARGE,
    }
}
