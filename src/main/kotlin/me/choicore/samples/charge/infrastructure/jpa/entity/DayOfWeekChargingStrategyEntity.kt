package me.choicore.samples.charge.infrastructure.jpa.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import me.choicore.samples.charge.domain.ChargingMethod
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategy
import me.choicore.samples.charge.domain.Timeline
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
) : ChargingStrategyAttribute(
        complexId = complexId,
        method = method,
        rate = rate,
        timeline = timeline,
    ) {
    constructor(dayOfWeekChargingStrategy: DayOfWeekChargingStrategy) : this(
        complexId = dayOfWeekChargingStrategy.identifier.complexId,
        method = dayOfWeekChargingStrategy.mode.method,
        rate = dayOfWeekChargingStrategy.mode.rate,
        timeline = dayOfWeekChargingStrategy.timeline,
        stationId = dayOfWeekChargingStrategy.identifier.stationId,
        dayOfWeek = dayOfWeekChargingStrategy.dayOfWeek,
    )

    override fun toChargingStrategy(): DayOfWeekChargingStrategy =
        DayOfWeekChargingStrategy(
            identifier =
                DayOfWeekChargingStrategy.DayOfWeekChargingStrategyIdentifier.registered(
                    strategyId = id,
                    complexId = complexId,
                    stationId = stationId,
                ),
            dayOfWeek = dayOfWeek,
            mode = super.toChargingMode(),
            timeline = timeline,
        )
}
