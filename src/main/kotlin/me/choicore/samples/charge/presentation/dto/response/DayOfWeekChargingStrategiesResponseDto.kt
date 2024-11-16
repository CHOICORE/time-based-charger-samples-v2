package me.choicore.samples.charge.presentation.dto.response

import me.choicore.samples.charge.domain.ChargingMethod
import me.choicore.samples.charge.domain.ChargingMethod.DISCHARGE
import me.choicore.samples.charge.domain.ChargingMethod.STANDARD
import me.choicore.samples.charge.domain.ChargingMethod.SURCHARGE
import me.choicore.samples.charge.domain.ChargingMode
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategies
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategy
import me.choicore.samples.charge.domain.Timeline
import me.choicore.samples.charge.presentation.dto.TimeSlotDto

data class DayOfWeekChargingStrategiesResponseDto(
    val sun: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val mon: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val tue: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val wed: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val thu: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val fri: List<DayOfWeekChargingStrategyDto> = emptyList(),
    val sat: List<DayOfWeekChargingStrategyDto> = emptyList(),
) {
    data class DayOfWeekChargingStrategyDto(
        val strategyId: Long?,
        val mode: ChargingMethod,
        val rate: Int,
        val timeline: List<TimeSlotDto>,
    ) {
        private fun toChargingMode(): ChargingMode =
            when (this.mode) {
                SURCHARGE -> ChargingMode.Surcharge(this.rate)
                DISCHARGE -> ChargingMode.Discharge(this.rate)
                STANDARD -> ChargingMode.Standard
            }

        private fun toTimeline(): Timeline = Timeline(this.timeline.map { it.toTimeSlot() })

        companion object {
            fun from(dayOfWeekChargingStrategy: DayOfWeekChargingStrategy): DayOfWeekChargingStrategyDto =
                DayOfWeekChargingStrategyDto(
                    strategyId =
                        if (dayOfWeekChargingStrategy.strategyId ==
                            0L
                        ) {
                            null
                        } else {
                            dayOfWeekChargingStrategy.strategyId
                        },
                    mode = dayOfWeekChargingStrategy.mode.method,
                    rate = dayOfWeekChargingStrategy.mode.rate,
                    timeline = dayOfWeekChargingStrategy.timeline.slots.map { TimeSlotDto.from(it) },
                )
        }
    }

    companion object {
        fun from(dayOfWeekChargingStrategies: DayOfWeekChargingStrategies): DayOfWeekChargingStrategiesResponseDto =
            DayOfWeekChargingStrategiesResponseDto(
                sun = dayOfWeekChargingStrategies.sunday.map { DayOfWeekChargingStrategyDto.from(it) },
                mon = dayOfWeekChargingStrategies.monday.map { DayOfWeekChargingStrategyDto.from(it) },
                tue = dayOfWeekChargingStrategies.tuesday.map { DayOfWeekChargingStrategyDto.from(it) },
                wed = dayOfWeekChargingStrategies.wednesday.map { DayOfWeekChargingStrategyDto.from(it) },
                thu = dayOfWeekChargingStrategies.thursday.map { DayOfWeekChargingStrategyDto.from(it) },
                fri = dayOfWeekChargingStrategies.friday.map { DayOfWeekChargingStrategyDto.from(it) },
                sat = dayOfWeekChargingStrategies.saturday.map { DayOfWeekChargingStrategyDto.from(it) },
            )
    }
}
