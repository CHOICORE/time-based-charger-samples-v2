package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingStrategy.ChargingStrategyIdentifier
import me.choicore.samples.charge.domain.ChargingStrategy.SpecifiedDateChargingStrategy
import java.time.LocalDate

data class SpecifiedDateChargingStrategy(
    override val identifier: SpecifiedDateChargingStrategyIdentifier,
    override val specifiedDate: LocalDate,
    override val mode: ChargingMode,
    override val timeline: Timeline,
) : SpecifiedDateChargingStrategy {
    override fun supports(date: LocalDate): Boolean = date == this.specifiedDate

    data class SpecifiedDateChargingStrategyIdentifier(
        private val _strategyId: Long? = null,
        override val complexId: Long,
    ) : ChargingStrategyIdentifier {
        override val strategyId: Long get() = this._strategyId ?: throw IllegalStateException("id is not set")

        companion object {
            fun unregistered(complexId: Long): SpecifiedDateChargingStrategyIdentifier =
                SpecifiedDateChargingStrategyIdentifier(complexId = complexId)

            fun registered(
                strategyId: Long,
                complexId: Long,
            ): SpecifiedDateChargingStrategyIdentifier =
                SpecifiedDateChargingStrategyIdentifier(_strategyId = strategyId, complexId = complexId)
        }
    }
}
