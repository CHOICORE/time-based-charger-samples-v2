package me.choicore.samples.charge.infrastructure.jpa.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.choicore.samples.charge.domain.strategy.station.ChargingStation
import me.choicore.samples.common.jpa.AutoIncrement
import java.time.LocalDate

@Entity
@Table(name = "charging_stations")
class ChargingStationEntity private constructor(
    val complexId: Long,
    val name: String,
    val description: String?,
    val exemptionThreshold: Long,
    val dischargeAmount: Long,
    val startsOn: LocalDate? = null,
    val endsOn: LocalDate? = null,
    var active: Boolean = true,
) : AutoIncrement() {
    constructor(chargingStation: ChargingStation) : this(
        complexId = chargingStation.complexId,
        name = chargingStation.name,
        description = chargingStation.description,
        exemptionThreshold = chargingStation.exemptionThreshold,
        dischargeAmount = chargingStation.dischargeAmount,
        startsOn = chargingStation.startsOn,
        endsOn = chargingStation.endsOn,
    )
}
