package me.choicore.samples.charge.presentation.dto.request

import me.choicore.samples.charge.domain.ChargingStation
import java.time.LocalDate

data class ChargingStationRegistrationRequestDto(
    val complexId: Long,
    val name: String,
    val description: String?,
    val startsOn: LocalDate? = null,
    val endsOn: LocalDate? = null,
    val exemptionThreshold: Long,
    val dischargeAmount: Long,
    val settings: DayOfWeekChargingStrategiesRequestDto,
) {
    fun toChargingStation(): ChargingStation =
        ChargingStation(
            complexId = this.complexId,
            name = this.name,
            description = this.description,
            startsOn = this.startsOn,
            endsOn = this.endsOn,
            exemptionThreshold = this.exemptionThreshold,
            dischargeAmount = this.dischargeAmount,
            dayOfWeekChargingStrategies = this.settings.toDayOfWeekChargingStrategies(complexId = complexId),
        )
}
