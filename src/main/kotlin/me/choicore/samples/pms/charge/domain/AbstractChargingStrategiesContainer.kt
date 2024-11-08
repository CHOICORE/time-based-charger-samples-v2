package me.choicore.samples.pms.charge.domain

abstract class AbstractChargingStrategiesContainer<K, S : ChargingStrategy> : ChargingStrategiesContainer<K, S> {
    private val _strategies: MutableMap<K, MutableList<S>> = mutableMapOf()
    override val strategies: Map<K, List<S>> = _strategies.toMap()

    override fun register(strategy: S) {
        val key: K = this.getKey(strategy = strategy)
        val existingStrategies: MutableList<S> = this._strategies.getOrPut(key = key) { mutableListOf() }

        check(existingStrategies.none { it.timeline.overlapsWith(strategy.timeline) }) {
            "The specified strategy's timeline overlaps with an existing strategy on $key"
        }

        existingStrategies.add(element = strategy)
    }
}
