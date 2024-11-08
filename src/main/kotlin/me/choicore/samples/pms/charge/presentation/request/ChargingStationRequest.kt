package me.choicore.samples.pms.charge.presentation.request

import me.choicore.samples.pms.charge.domain.ChargingStationRegistration
import java.time.LocalDate

data class ChargingStationRequest(
    val complexId: Long,
    val name: String,
    val description: String?,
    val startsOn: LocalDate? = null,
    val endsOn: LocalDate? = null,
    val exemptionThreshold: Int,
    val dischargeAmount: Int,
    val standard: StandardScheduleRequest,
) {
    fun toChargingStationRegistration(): ChargingStationRegistration =
        ChargingStationRegistration(
            complexId = this.complexId,
            name = this.name,
            description = this.description,
            startsOn = this.startsOn,
            endsOn = this.endsOn,
            exemptionThreshold = this.exemptionThreshold,
            dischargeAmount = this.dischargeAmount,
            standard = this.standard.toRecurringSchedules(),
        )
}
