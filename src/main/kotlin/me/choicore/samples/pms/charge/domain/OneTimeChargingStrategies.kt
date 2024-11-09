package me.choicore.samples.pms.charge.domain

import java.time.LocalDate

class OneTimeChargingStrategies() : AbstractChargingStrategies<LocalDate, OneTimeChargingStrategy>() {
    constructor(strategies: List<OneTimeChargingStrategy>) : this() {
        super.register(strategies)
    }

    override fun getChargingStrategies(date: LocalDate): List<OneTimeChargingStrategy> = super.strategies[date] ?: emptyList()

    override fun getKey(strategy: ChargingStrategy): LocalDate = (strategy as OneTimeChargingStrategy).specifiedDate

    companion object {
        val EMPTY = OneTimeChargingStrategies()
    }
}
