package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingMode.Discharge
import me.choicore.samples.charge.domain.ChargingMode.Standard
import me.choicore.samples.charge.domain.ChargingMode.Surcharge
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ChargingModeTests {
    @Test
    fun t1() {
        val surcharge = Surcharge(percentage = 10)
        val charge: BigDecimal = surcharge.charge(1000L)
        assertThat(charge).isEqualByComparingTo(BigDecimal.valueOf(1100))
    }

    @Test
    fun t2() {
        val discharge = Discharge(percentage = 10)
        val charge: BigDecimal = discharge.charge(1000L)
        assertThat(charge).isEqualByComparingTo(BigDecimal.valueOf(900))
    }

    @Test
    fun t3() {
        val standard = Standard
        val charge: BigDecimal = standard.charge(1000L)
        assertThat(charge).isEqualByComparingTo(BigDecimal.valueOf(1000))
    }
}
