package me.choicore.samples.charge.domain.core

enum class ChargingMethod {
    SURCHARGE,
    DISCHARGE,
    STANDARD,
    ;

    fun toChargingMode(rate: Int): ChargingMode =
        when (this) {
            SURCHARGE -> ChargingMode.Surcharge(percentage = rate)
            DISCHARGE -> ChargingMode.Discharge(percentage = rate)
            STANDARD -> ChargingMode.Standard
        }
}
