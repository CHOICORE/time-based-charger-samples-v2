package me.choicore.samples.charge.infrastructure.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Transient
import me.choicore.samples.charge.domain.ChargingMethod
import me.choicore.samples.charge.domain.ChargingMode
import me.choicore.samples.charge.domain.DayOfWeekChargingStrategy
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategy
import me.choicore.samples.charge.domain.Timeline
import me.choicore.samples.charge.infrastructure.jpa.TimelineConverter
import me.choicore.samples.common.jpa.AutoIncrement
import java.time.DayOfWeek
import java.time.LocalDate

@Entity
class ChargingStrategyEntity(
    val complexId: Long,
    val stationId: Long? = null,
    @Enumerated(value = EnumType.STRING)
    val dayOfWeek: DayOfWeek? = null,
    val specifiedDate: LocalDate? = null,
    @Enumerated(value = EnumType.STRING)
    val method: ChargingMethod,
    val rate: Int,
    @Convert(converter = TimelineConverter::class)
    @Column(columnDefinition = "json")
    val timeline: Timeline,
) : AutoIncrement() {
    constructor(
        dayOfWeekChargingStrategy: DayOfWeekChargingStrategy,
    ) : this(
        complexId = dayOfWeekChargingStrategy.identifier.complexId,
        stationId = dayOfWeekChargingStrategy.identifier.stationId,
        dayOfWeek = dayOfWeekChargingStrategy.dayOfWeek,
        specifiedDate = null,
        method = dayOfWeekChargingStrategy.mode.method,
        rate = dayOfWeekChargingStrategy.mode.rate,
        timeline = dayOfWeekChargingStrategy.timeline,
    )

    constructor(
        specifiedDateChargingStrategy: SpecifiedDateChargingStrategy,
    ) : this(
        complexId = specifiedDateChargingStrategy.identifier.complexId,
        dayOfWeek = null,
        stationId = null,
        specifiedDate = specifiedDateChargingStrategy.specifiedDate,
        method = specifiedDateChargingStrategy.mode.method,
        rate = specifiedDateChargingStrategy.mode.rate,
        timeline = specifiedDateChargingStrategy.timeline,
    )

    @Transient
    val repeatable: Boolean = this.specifiedDate == null && this.dayOfWeek != null

    fun toDayOfWeekChargingStrategy(): DayOfWeekChargingStrategy =
        DayOfWeekChargingStrategy(
            identifier =
                DayOfWeekChargingStrategy.DayOfWeekChargingStrategyIdentifier.registered(
                    strategyId = id,
                    complexId = complexId,
                    stationId = stationId!!,
                ),
            dayOfWeek = dayOfWeek!!,
            mode = toChargingMode(),
            timeline = timeline,
        )

    fun toSpecifiedDateChargingStrategy(): SpecifiedDateChargingStrategy {
        requireNotNull(this.specifiedDate) {
            "specifiedDate must not be null"
        }

        return SpecifiedDateChargingStrategy(
            identifier =
                SpecifiedDateChargingStrategy.SpecifiedDateChargingStrategyIdentifier
                    .registered(
                        strategyId = this.id,
                        complexId = this.complexId,
                    ),
            specifiedDate = this.specifiedDate!!,
            mode = toChargingMode(),
            timeline = timeline,
        )
    }

    private fun toChargingMode(): ChargingMode =
        when (method) {
            ChargingMethod.SURCHARGE -> ChargingMode.Surcharge(percentage = this.rate)
            ChargingMethod.DISCHARGE -> ChargingMode.Discharge(percentage = this.rate)
            ChargingMethod.STANDARD -> ChargingMode.Standard
        }
}
