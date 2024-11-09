package me.choicore.samples.pms.charge.infrastucture

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Transient
import me.choicore.samples.bak.ChargingMode
import me.choicore.samples.common.jpa.AutoIncrement
import me.choicore.samples.pms.charge.domain.Timeline
import java.time.DayOfWeek
import java.time.LocalDate

@Entity
class ChargerStrategyEntity(
    val complexId: Long,
    val stationId: Long? = null,
    val dayOfWeek: DayOfWeek,
    val specifiedDate: LocalDate? = null,
    @Enumerated(value = STRING)
    val mode: ChargingMode,
    val rate: Int,
    @Convert(converter = TimelineConverter::class)
    @Column(columnDefinition = "json")
    val timeline: Timeline,
) : AutoIncrement() {
    @Transient
    val repeatable: Boolean = this.specifiedDate == null
}
