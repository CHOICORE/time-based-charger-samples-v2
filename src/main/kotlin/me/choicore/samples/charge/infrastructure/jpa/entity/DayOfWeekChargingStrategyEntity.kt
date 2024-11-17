package me.choicore.samples.charge.infrastructure.jpa.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import me.choicore.samples.charge.domain.core.ChargingMethod
import me.choicore.samples.charge.domain.core.Timeline
import me.choicore.samples.charge.domain.strategy.DayOfWeekChargingStrategy
import java.time.DayOfWeek

@Entity
@Table(name = "day_of_week_charging_strategies")
@DiscriminatorValue("DAY_OF_WEEK")
class DayOfWeekChargingStrategyEntity(
    complexId: Long,
    method: ChargingMethod,
    rate: Int,
    timeline: Timeline,
    val stationId: Long,
    @Enumerated(value = STRING)
    val dayOfWeek: DayOfWeek,
) : ChargingStrategyAttributeEntity(
        complexId = complexId,
        method = method,
        rate = rate,
        timeline = timeline,
    ) {
    constructor(dayOfWeekChargingStrategy: DayOfWeekChargingStrategy) : this(
        complexId = dayOfWeekChargingStrategy.complexId,
        method = dayOfWeekChargingStrategy.mode.method,
        rate = dayOfWeekChargingStrategy.mode.rate,
        timeline = dayOfWeekChargingStrategy.timeline,
        stationId = dayOfWeekChargingStrategy.stationId,
        dayOfWeek = dayOfWeekChargingStrategy.dayOfWeek,
    )

    override fun toChargingStrategy(): DayOfWeekChargingStrategy =
        DayOfWeekChargingStrategy(
            strategyId = this.id,
            complexId = this.complexId,
            stationId = this.stationId,
            dayOfWeek = this.dayOfWeek,
            mode = super.toChargingMode(),
            timeline = this.timeline,
        )
}
