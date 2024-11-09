package me.choicore.samples.bak

import me.choicore.samples.pms.charge.domain.ChargingStrategy

sealed interface ScheduleValidator<K, S : ChargingStrategy> {
    fun validate(
        basis: K,
        schedules: List<S>,
    )
}
