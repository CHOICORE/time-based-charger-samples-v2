package me.choicore.samples.pms.charge.application

import me.choicore.samples.pms.charge.domain.ChargingStation
import me.choicore.samples.pms.charge.domain.RecurringChargingStrategies
import java.time.LocalDate

data class ChargingStationRegistration(
    val complexId: Long,
    val name: String,
    val description: String?,
    val startsOn: LocalDate? = null,
    val endsOn: LocalDate? = null,
    val exemptionThreshold: Int,
    val dischargeAmount: Int,
    val settings: RecurringChargingStrategies,
) {
    fun toChargingStation(): ChargingStation =
        ChargingStation(
            complexId = complexId,
            name = name,
            description = description,
            startsOn = startsOn,
            endsOn = endsOn,
            exemptionThreshold = exemptionThreshold,
            dischargeAmount = dischargeAmount,
            dayOfWeekChargingStrategies = settings,
        )
}
