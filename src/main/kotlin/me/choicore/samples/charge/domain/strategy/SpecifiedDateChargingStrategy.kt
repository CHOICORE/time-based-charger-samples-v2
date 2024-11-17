package me.choicore.samples.charge.domain.strategy

import me.choicore.samples.charge.domain.core.ChargingMode
import me.choicore.samples.charge.domain.core.Timeline
import me.choicore.samples.charge.domain.strategy.ChargingStrategy.SpecifiedDateChargingStrategy
import java.time.LocalDate

data class SpecifiedDateChargingStrategy(
    override val strategyId: Long = 0,
    override val complexId: Long = 0,
    override val specifiedDate: LocalDate,
    override val mode: ChargingMode,
    override val timeline: Timeline,
) : SpecifiedDateChargingStrategy {
    override fun supports(date: LocalDate): Boolean = date == this.specifiedDate
}
