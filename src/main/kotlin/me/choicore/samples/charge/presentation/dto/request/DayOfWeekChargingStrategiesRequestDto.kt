package me.choicore.samples.charge.presentation.dto.request

import me.choicore.samples.charge.domain.ChargingMethod
import me.choicore.samples.charge.domain.ChargingMethod.DISCHARGE
import me.choicore.samples.charge.domain.ChargingMethod.STANDARD
import me.choicore.samples.charge.domain.ChargingMethod.SURCHARGE
import me.choicore.samples.charge.domain.ChargingMode
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategies
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategy
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategy.DayOfWeekChargingStrategyIdentifier
import me.choicore.samples.charge.domain.Timeline
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
                identifier = DayOfWeekChargingStrategyIdentifier.unregistered(complexId),
                dayOfWeek = dayOfWeek,
                mode = this.toChargingMode(),
                timeline = this.toTimeline(),
            )

        private fun toChargingMode(): ChargingMode =
            when (this.mode) {
                SURCHARGE -> ChargingMode.Surcharge(this.rate)
                DISCHARGE -> ChargingMode.Discharge(this.rate)
                STANDARD -> ChargingMode.Standard
            }

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
