package me.choicore.samples.charge.presentation.dto.response

import me.choicore.samples.charge.domain.strategy.station.ChargingStation
import java.time.LocalDate

data class ChargingStationResponse(
    val stationId: Long,
    val complexId: Long,
    val name: String,
    val description: String?,
    val startsOn: LocalDate? = null,
    val endsOn: LocalDate? = null,
    val exemptionThreshold: Long,
    val dischargeAmount: Long,
    val settings: DayOfWeekChargingStrategiesResponseDto,
) {
    companion object {
        fun from(chargingStation: ChargingStation): ChargingStationResponse =
            ChargingStationResponse(
                stationId = chargingStation.id,
                complexId = chargingStation.complexId,
                name = chargingStation.name,
                description = chargingStation.description,
                startsOn = chargingStation.startsOn,
                endsOn = chargingStation.endsOn,
                exemptionThreshold = chargingStation.exemptionThreshold,
                dischargeAmount = chargingStation.dischargeAmount,
                settings = DayOfWeekChargingStrategiesResponseDto.from(chargingStation.dayOfWeekChargingStrategies),
            )
    }
}
