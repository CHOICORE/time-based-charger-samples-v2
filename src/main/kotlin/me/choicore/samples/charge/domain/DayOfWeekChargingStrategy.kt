package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingStrategy.ChargingStrategyIdentifier
import me.choicore.samples.charge.domain.ChargingStrategy.DayOfWeekChargingStrategy
import java.time.DayOfWeek
import java.time.LocalDate

data class DayOfWeekChargingStrategy(
    override val identifier: DayOfWeekChargingStrategyIdentifier,
    override val dayOfWeek: DayOfWeek,
    override val mode: ChargingMode,
    override val timeline: Timeline,
) : DayOfWeekChargingStrategy {
    override fun supports(date: LocalDate): Boolean = date.dayOfWeek == this.dayOfWeek

    class DayOfWeekChargingStrategyIdentifier private constructor(
        private val _strategyId: Long? = null,
        private val _stationId: Long? = null,
        override val complexId: Long,
    ) : ChargingStrategyIdentifier {
        override val strategyId: Long get() = this._strategyId ?: 0
        val stationId: Long get() = this._stationId ?: 0

        companion object {
            fun unregistered(complexId: Long): DayOfWeekChargingStrategyIdentifier =
                DayOfWeekChargingStrategyIdentifier(complexId = complexId)

            fun unregistered(
                complexId: Long,
                stationId: Long,
            ): DayOfWeekChargingStrategyIdentifier = DayOfWeekChargingStrategyIdentifier(complexId = complexId, _stationId = stationId)

            fun registered(
                strategyId: Long,
                complexId: Long,
                stationId: Long,
            ): DayOfWeekChargingStrategyIdentifier =
                DayOfWeekChargingStrategyIdentifier(
                    _strategyId = strategyId,
                    _stationId = stationId,
                    complexId = complexId,
                )

            fun empty(): DayOfWeekChargingStrategyIdentifier = DayOfWeekChargingStrategyIdentifier(complexId = 0)
        }
    }
}
