package me.choicore.samples.charge.domain.strategy

import me.choicore.samples.charge.domain.core.ChargingMode
import me.choicore.samples.charge.domain.core.Timeline
import java.time.LocalDate

class SpecifiedDateChargingStrategies() : AbstractChargingStrategies<LocalDate, SpecifiedDateChargingStrategy>() {
    constructor(strategies: List<SpecifiedDateChargingStrategy>) : this() {
        super.register(strategies)
    }

    override fun getKeyForDate(date: LocalDate): LocalDate = date

    override fun getChargingStrategies(date: LocalDate): List<SpecifiedDateChargingStrategy> {
        val specifiedDateChargingStrategy: MutableList<SpecifiedDateChargingStrategy>? = super.strategies[date]
        if (specifiedDateChargingStrategy.isNullOrEmpty()) {
            return emptyList()
        } else {
            val remainingTimeline: Timeline =
                Timeline.Companion.remain(super.getOverallTimeSlots(specifiedDateChargingStrategy))
            specifiedDateChargingStrategy.add(
                SpecifiedDateChargingStrategy(
                    specifiedDate = date,
                    mode = ChargingMode.Standard,
                    timeline = remainingTimeline,
                ),
            )
        }

        return specifiedDateChargingStrategy
    }

    override fun getKey(strategy: SpecifiedDateChargingStrategy): LocalDate = strategy.specifiedDate

    companion object {
        val EMPTY = SpecifiedDateChargingStrategies()
    }
}
