package me.choicore.samples.pms.charge.infrastucture

import jakarta.persistence.Entity
import me.choicore.samples.common.jpa.AutoIncrement
import me.choicore.samples.pms.charge.domain.ChargingStation
import java.time.LocalDate

@Entity
class ChargingStationEntity private constructor(
    val complexId: Long,
    val name: String,
    val description: String?,
    val startsOn: LocalDate? = null,
    val endsOn: LocalDate? = null,
    val exemptionThreshold: Int,
    val dischargeAmount: Int,
    var active: Boolean = true,
) : AutoIncrement() {
    constructor(chargingStation: ChargingStation) : this(
        complexId = chargingStation.complexId,
        name = chargingStation.name,
        description = chargingStation.description,
        startsOn = chargingStation.startsOn,
        endsOn = chargingStation.endsOn,
        exemptionThreshold = chargingStation.exemptionThreshold,
        dischargeAmount = chargingStation.dischargeAmount,
    )
}
