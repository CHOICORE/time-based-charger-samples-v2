package me.choicore.samples.charge.infrastructure.jpa.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.choicore.samples.charge.domain.ChargingMethod
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategy
import me.choicore.samples.charge.domain.Timeline
import java.time.LocalDate

@Entity
@Table(name = "speficied_date_charging_strategies")
@DiscriminatorValue("SPECIFIED_DATE")
class SpecifiedDateChargingStrategyEntity(
    complexId: Long,
    method: ChargingMethod,
    rate: Int,
    timeline: Timeline,
    val specifiedDate: LocalDate,
) : ChargingStrategyAttributeEntity(
        complexId = complexId,
        method = method,
        rate = rate,
        timeline = timeline,
    ) {
    override fun toChargingStrategy(): SpecifiedDateChargingStrategy {
        requireNotNull(this.specifiedDate) {
            "specifiedDate must not be null"
        }

        return SpecifiedDateChargingStrategy(
            strategyId = this.id,
            complexId = this.complexId,
            specifiedDate = this.specifiedDate,
            mode = super.toChargingMode(),
            timeline = super.timeline,
        )
    }
}
