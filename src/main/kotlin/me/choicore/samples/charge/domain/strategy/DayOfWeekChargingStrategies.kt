package me.choicore.samples.charge.domain.strategy

import me.choicore.samples.charge.domain.core.ChargingMode
import me.choicore.samples.charge.domain.core.Timeline
import java.time.DayOfWeek
import java.time.LocalDate

class DayOfWeekChargingStrategies() : AbstractChargingStrategies<DayOfWeek, DayOfWeekChargingStrategy>() {
    constructor(schedules: List<DayOfWeekChargingStrategy>) : this() {
        super.register(schedules)
    }

    val sunday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.SUNDAY)

    val monday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.MONDAY)

    val tuesday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.TUESDAY)

    val wednesday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.WEDNESDAY)

    val thursday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.THURSDAY)

    val friday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.FRIDAY)

    val saturday: List<DayOfWeekChargingStrategy> get() = getStrategiesForDay(DayOfWeek.SATURDAY)

    override fun getKeyForDate(date: LocalDate): DayOfWeek = date.dayOfWeek

    override fun getChargingStrategies(date: LocalDate): List<DayOfWeekChargingStrategy> = getStrategiesForDay(date.dayOfWeek)

    override fun getKey(strategy: DayOfWeekChargingStrategy): DayOfWeek = strategy.dayOfWeek

    private fun getStrategiesForDay(dayOfWeek: DayOfWeek): List<DayOfWeekChargingStrategy> {
        val dayOfWeekChargingStrategies: MutableList<DayOfWeekChargingStrategy>? = super.strategies[dayOfWeek]
        if (dayOfWeekChargingStrategies.isNullOrEmpty()) {
            return emptyList()
        } else {
            val remainingTimeline: Timeline =
                super.getRemainingTimeline(super.getOverallTimeSlots(dayOfWeekChargingStrategies))
            if (remainingTimeline.empty) {
                return dayOfWeekChargingStrategies
            }

            dayOfWeekChargingStrategies.add(
                DayOfWeekChargingStrategy(
                    dayOfWeek = dayOfWeek,
                    mode = ChargingMode.Standard,
                    timeline = remainingTimeline,
                ),
            )
        }

        return dayOfWeekChargingStrategies
    }
}
