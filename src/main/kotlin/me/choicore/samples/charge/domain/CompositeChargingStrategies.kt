package me.choicore.samples.charge.domain

import java.time.LocalDate

class CompositeChargingStrategies(
    private val registries: Map<ChargingStrategy.Period, AbstractChargingStrategies<*, out ChargingStrategy>>,
) : ChargingStrategies<ChargingStrategy> {
    constructor() : this(
        mapOf(
            ONCE_STRATEGY to SpecifiedDateChargingStrategies(),
            REPEATABLE_STRATEGY to DayOfWeekChargingStrategies(),
        ),
    )

    override fun register(strategy: ChargingStrategy) {
        when (strategy.period) {
            ONCE_STRATEGY -> {
                val specifiedDateChargingStrategy: SpecifiedDateChargingStrategy =
                    strategy as SpecifiedDateChargingStrategy
                (registries[ONCE_STRATEGY] as SpecifiedDateChargingStrategies).register(strategy = specifiedDateChargingStrategy)
            }

            REPEATABLE_STRATEGY -> {
                strategy as DayOfWeekChargingStrategy
                (registries[REPEATABLE_STRATEGY] as DayOfWeekChargingStrategies).register(strategy = strategy)
            }

            else -> throw IllegalArgumentException(
                "Unsupported strategy type: ${strategy.period}. Supported types: $ONCE_STRATEGY, $REPEATABLE_STRATEGY",
            )
        }
    }

    override fun getChargingStrategies(date: LocalDate): List<ChargingStrategy> {
        val onceStrategies: List<ChargingStrategy> = registries[ONCE_STRATEGY]!!.getChargingStrategies(date = date)
        return onceStrategies.ifEmpty {
            registries[REPEATABLE_STRATEGY]!!.getChargingStrategies(date = date)
        }
    }

    class CompositeChargingStrategiesBuilder {
        private val specifiedDateChargingStrategies: SpecifiedDateChargingStrategies =
            SpecifiedDateChargingStrategies()
        private val dayOfWeekChargingStrategies: DayOfWeekChargingStrategies =
            DayOfWeekChargingStrategies()

        fun once(chargingStrategy: SpecifiedDateChargingStrategy): CompositeChargingStrategiesBuilder {
            this.specifiedDateChargingStrategies.register(strategy = chargingStrategy)
            return this
        }

        fun repeatable(chargingStrategy: DayOfWeekChargingStrategy): CompositeChargingStrategiesBuilder {
            this.dayOfWeekChargingStrategies.register(strategy = chargingStrategy)
            return this
        }

        fun build(): CompositeChargingStrategies =
            CompositeChargingStrategies(
                mapOf(
                    ONCE_STRATEGY to this.specifiedDateChargingStrategies,
                    REPEATABLE_STRATEGY to this.dayOfWeekChargingStrategies,
                ),
            )
    }

    companion object {
        private val ONCE_STRATEGY = ChargingStrategy.Period.ONCE
        private val REPEATABLE_STRATEGY = ChargingStrategy.Period.REPEATABLE

        fun builder(): CompositeChargingStrategiesBuilder = CompositeChargingStrategiesBuilder()
    }
}
