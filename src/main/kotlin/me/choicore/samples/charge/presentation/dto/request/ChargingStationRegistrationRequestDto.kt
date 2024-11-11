package me.choicore.samples.charge.presentation.dto.request

import me.choicore.samples.charge.domain.ChargingStation
import me.choicore.samples.charge.domain.ChargingStation.ChargingStationIdentifier
import java.time.LocalDate

data class ChargingStationRegistrationRequestDto(
    val complexId: Long,
    val name: String,
    val description: String?,
    val startsOn: LocalDate? = null,
    val endsOn: LocalDate? = null,
    val exemptionThreshold: Int,
    val dischargeAmount: Int,
    val settings: DayOfWeekChargingStrategiesRequestDto,
) {
    fun toChargingStation(): ChargingStation =
        ChargingStation(
            identifier = ChargingStationIdentifier.unregistered(complexId = this.complexId),
            name = this.name,
            description = this.description,
            startsOn = this.startsOn,
            endsOn = this.endsOn,
            exemptionThreshold = this.exemptionThreshold,
            dischargeAmount = this.dischargeAmount,
            dayOfWeekChargingStrategies = this.settings.toDayOfWeekChargingStrategies(complexId = complexId),
        )
}
