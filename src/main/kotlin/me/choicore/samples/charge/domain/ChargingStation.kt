package me.choicore.samples.charge.domain

import java.time.LocalDate

data class ChargingStation(
    val identifier: ChargingStationIdentifier,
    val name: String,
    val description: String?,
    val startsOn: LocalDate? = null,
    val endsOn: LocalDate? = null,
    val exemptionThreshold: Long,
    val dischargeAmount: Long,
    val dayOfWeekChargingStrategies: DayOfWeekChargingStrategies,
) {
    class ChargingStationIdentifier private constructor(
        private val _chargingStationId: Long? = null,
        val complexId: Long,
    ) {
        val chargingStationId: Long
            get() = this._chargingStationId ?: throw IllegalStateException("id is not set")

        companion object {
            fun unregistered(complexId: Long): ChargingStationIdentifier = ChargingStationIdentifier(complexId = complexId)

            fun registered(
                stationId: Long,
                complexId: Long,
            ): ChargingStationIdentifier = ChargingStationIdentifier(_chargingStationId = stationId, complexId = complexId)
        }
    }

    object Comparators {
        fun withDefaults(): Comparator<ChargingStation> = compareBy({ it.startsOn == null }, { it.endsOn == null }, { it.startsOn })
    }
}
