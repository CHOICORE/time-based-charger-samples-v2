package me.choicore.samples.pms.charge.domain

import java.time.LocalDate

data class ChargingStation(
    val id: Long? = null,
    val complexId: Long,
    val name: String,
    val startsOn: LocalDate? = null,
    val endsOn: LocalDate? = null,
    val exemptionThreshold: Int,
    val dischargeAmount: Int,
    val sun: List<RecurringSchedule>? = emptyList(),
    val mon: List<RecurringSchedule>? = emptyList(),
    val tue: List<RecurringSchedule>? = emptyList(),
    val wed: List<RecurringSchedule>? = emptyList(),
    val thu: List<RecurringSchedule>? = emptyList(),
    val fri: List<RecurringSchedule>? = emptyList(),
    val sat: List<RecurringSchedule>? = emptyList(),
    val events: List<OneTimeSchedule>? = emptyList(),
)
