package me.choicore.samples.charge.domain.strategy

import me.choicore.samples.charge.domain.core.TimeSlot
import me.choicore.samples.charge.domain.core.Timeline
import java.time.LocalDate
import java.time.temporal.TemporalAdjuster

abstract class AbstractChargingStrategies<K : TemporalAdjuster, S : ChargingStrategy> : ChargingStrategies<S> {
    protected val strategies: MutableMap<K, MutableList<S>> = mutableMapOf()
    val all: List<S> get() = this.strategies.values.flatten()

    override fun register(strategy: S) {
        val key: K = this.getKey(strategy = strategy)
        val existingStrategies: MutableList<S> = this.strategies.getOrPut(key = key) { mutableListOf() }

        check(existingStrategies.none { it.timeline.overlapsWith(strategy.timeline) }) {
            "The specified strategy's timeline overlaps with an existing strategy on $key"
        }

        existingStrategies.add(element = strategy)
    }

    protected fun getOverallTimeSlots(strategies: List<S>): List<TimeSlot> = strategies.flatMap { it.timeline.slots }

    protected fun getRemainingTimeline(exclusiveTimeSlots: List<TimeSlot>): Timeline =
        Timeline.remain(excludedTimeSlots = exclusiveTimeSlots)

    protected abstract fun getKeyForDate(date: LocalDate): K

    protected abstract fun getKey(strategy: S): K

    fun isEmpty(date: LocalDate): Boolean = this.strategies[getKeyForDate(date)].isNullOrEmpty()
}
