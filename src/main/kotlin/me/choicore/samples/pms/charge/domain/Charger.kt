package me.choicore.samples.pms.charge.domain

interface Charger {
    fun charge(amount: Charge): Charge
}
