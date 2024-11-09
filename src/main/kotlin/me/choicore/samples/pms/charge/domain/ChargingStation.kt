package me.choicore.samples.pms.charge.domain

import java.time.LocalDate

data class ChargingStation(
    val id: Long? = null,
    val complexId: Long,
    val name: String,
    val description: String?,
    val startsOn: LocalDate? = null,
    val endsOn: LocalDate? = null,
    val exemptionThreshold: Int,
    val dischargeAmount: Int,
    val dayOfWeekChargingStrategies: RecurringChargingStrategies,
)
