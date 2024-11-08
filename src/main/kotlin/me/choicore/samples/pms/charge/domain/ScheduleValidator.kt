package me.choicore.samples.pms.charge.domain

sealed interface ScheduleValidator<K, S : ChargingStrategy> {
    fun validate(
        basis: K,
        schedules: List<S>,
    )
}
