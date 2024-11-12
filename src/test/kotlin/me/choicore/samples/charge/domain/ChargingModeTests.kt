package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingMode.Discharge
import me.choicore.samples.charge.domain.ChargingMode.Surcharge
import org.junit.jupiter.api.Test

class ChargingModeTests {
    @Test
    fun t1() {
        val surcharge = Surcharge(percentage = 10)
        val charge = surcharge.charge(1000L)
        println(charge.toLong())
    }

    @Test
    fun t2() {
        val discharge = Discharge(percentage = 10)
        val charge = discharge.charge(1000L)
        println(charge.toLong())
    }
}
