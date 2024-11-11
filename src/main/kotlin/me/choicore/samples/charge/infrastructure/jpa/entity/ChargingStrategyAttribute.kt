package me.choicore.samples.charge.infrastructure.jpa.entity

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.DiscriminatorType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table
import me.choicore.samples.charge.domain.ChargingMethod
import me.choicore.samples.charge.domain.ChargingMode
import me.choicore.samples.charge.domain.ChargingStrategy
import me.choicore.samples.charge.domain.Timeline
import me.choicore.samples.charge.infrastructure.jpa.TimelineConverter
import me.choicore.samples.common.jpa.AutoIncrement

@Entity
@Table(name = "charging_strategy_attributes") // 공통
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "strategy_type", discriminatorType = DiscriminatorType.STRING)
@AttributeOverride(name = "_id", column = Column(name = "strategy_id"))
abstract class ChargingStrategyAttribute(
    val complexId: Long,
    @Enumerated(value = STRING)
    val method: ChargingMethod,
    val rate: Int,
    @Convert(converter = TimelineConverter::class)
    @Column(columnDefinition = "json")
    val timeline: Timeline,
    var deleted: Boolean = false,
) : AutoIncrement() {
    abstract fun toChargingStrategy(): ChargingStrategy

    protected fun toChargingMode(): ChargingMode =
        when (method) {
            ChargingMethod.SURCHARGE -> ChargingMode.Surcharge(percentage = this.rate)
            ChargingMethod.DISCHARGE -> ChargingMode.Discharge(percentage = this.rate)
            ChargingMethod.STANDARD -> ChargingMode.Standard
        }
}
