package me.choicore.samples.pms.charge.presentation.request

import me.choicore.samples.bak.ChargingMode
import me.choicore.samples.pms.charge.domain.Timeline
import java.time.LocalDate

data class SpecifiedDateChargingStrategiesRequest(
    val events: List<OneTimeScheduleDto> = emptyList(),
) {
    data class OneTimeScheduleDto(
        val specifiedDate: LocalDate?,
        val mode: ChargingMode,
        val rate: Int,
        val timeline: Timeline,
    )
}
