package me.choicore.samples.charge.domain.core

import java.time.LocalTime
import java.time.temporal.ChronoUnit

object TimeUtils {
    fun duration(
        start: LocalTime,
        end: LocalTime,
        unit: ChronoUnit,
    ): Long = this.getElapsedTime(start = start, end = end, unit = unit)

    private fun getElapsedTime(
        start: LocalTime,
        end: LocalTime,
        unit: ChronoUnit,
    ): Long =
        when {
            this.isFullTime(start = start, end = end) -> this.getFullTimeDuration(unit = unit)
            else -> {
                val adjustedEnd: LocalTime = minOf(end, MAX_TIME)
                val paddingSeconds: Int = if (adjustedEnd == MAX_TIME) 1 else 0
                val calculated: Long = ChronoUnit.SECONDS.between(start, adjustedEnd) + paddingSeconds

                when (unit) {
                    ChronoUnit.DAYS -> calculated / 86400
                    ChronoUnit.HOURS -> calculated / 3600
                    ChronoUnit.MINUTES -> calculated / 60
                    ChronoUnit.SECONDS -> calculated
                    else -> throw IllegalArgumentException("Unsupported unit for full day calculation")
                }
            }
        }

    private fun getFullTimeDuration(unit: ChronoUnit): Long =
        when (unit) {
            ChronoUnit.DAYS -> 1
            ChronoUnit.HOURS -> 24
            ChronoUnit.MINUTES -> 1440
            ChronoUnit.SECONDS -> 86400
            else -> throw IllegalArgumentException("Unsupported unit for full day calculation")
        }

    private fun isFullTime(
        start: LocalTime,
        end: LocalTime,
    ): Boolean = start == LocalTime.MIN && end >= MAX_TIME

    val MAX_TIME: LocalTime = LocalTime.of(23, 59, 59)
}
