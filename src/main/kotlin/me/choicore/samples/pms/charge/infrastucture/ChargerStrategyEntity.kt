package me.choicore.samples.pms.charge.infrastucture

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Transient
import me.choicore.samples.common.jpa.AutoIncrement
import me.choicore.samples.pms.charge.domain.ChargingMode
import me.choicore.samples.pms.charge.domain.Timeline
import java.time.DayOfWeek
import java.time.LocalDate

@Entity
class ChargerStrategyEntity(
    val policyId: Long,
    val dayOfWeek: DayOfWeek,
    val specifiedDate: LocalDate? = null,
    @Enumerated(value = STRING)
    val mode: ChargingMode,
    val rate: Int,
    @Column(columnDefinition = "json")
    val timeline: Timeline,
) : AutoIncrement() {
    @Transient
    val repeatable: Boolean = this.specifiedDate == null
}
