package me.choicore.samples.charge.presentation.dto.request

import me.choicore.samples.charge.domain.core.ChargingMethod
import me.choicore.samples.charge.domain.core.Timeline
import me.choicore.samples.charge.domain.strategy.DayOfWeekChargingStrategies
import me.choicore.samples.charge.domain.strategy.DayOfWeekChargingStrategy
import me.choicore.samples.charge.presentation.dto.TimeSlotDto
import java.time.DayOfWeek

data class DayOfWeekChargingStrategiesRequestDto(
    val sun: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val mon: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val tue: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val wed: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val thu: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val fri: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val sat: List<DayOfWeekChargingStrategyDto> = emptyList(),
) {
    data class DayOfWeekChargingStrategyDto(
        val mode: ChargingMethod,
        val rate: Int,
        val timeline: List<TimeSlotDto>,
    ) {
        fun toDayOfWeekChargingStrategy(
            complexId: Long,
            dayOfWeek: DayOfWeek,
        ): DayOfWeekChargingStrategy =
            DayOfWeekChargingStrategy(
                complexId = complexId,
                dayOfWeek = dayOfWeek,
                mode = this.mode.toChargingMode(rate = this.rate),
                timeline = this.toTimeline(),
            )

        private fun toTimeline(): Timeline = Timeline(this.timeline.map { it.toTimeSlot() })
    }

    fun toDayOfWeekChargingStrategies(complexId: Long): DayOfWeekChargingStrategies {
        val settings: MutableList<DayOfWeekChargingStrategy> = mutableListOf()
        settings += sun.map { it.toDayOfWeekChargingStrategy(complexId, DayOfWeek.SUNDAY) }
        settings += mon.map { it.toDayOfWeekChargingStrategy(complexId, DayOfWeek.MONDAY) }
        settings += tue.map { it.toDayOfWeekChargingStrategy(complexId, DayOfWeek.TUESDAY) }
        settings += wed.map { it.toDayOfWeekChargingStrategy(complexId, DayOfWeek.WEDNESDAY) }
        settings += thu.map { it.toDayOfWeekChargingStrategy(complexId, DayOfWeek.THURSDAY) }
        settings += fri.map { it.toDayOfWeekChargingStrategy(complexId, DayOfWeek.FRIDAY) }
        settings += sat.map { it.toDayOfWeekChargingStrategy(complexId, DayOfWeek.SATURDAY) }
        return DayOfWeekChargingStrategies(settings)
    }
}
