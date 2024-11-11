package me.choicore.samples.charge.infrastructure.jpa.entity

import org.springframework.data.jpa.repository.JpaRepository

interface DayOfWeekChargingStrategyEntityRepository : JpaRepository<DayOfWeekChargingStrategyEntity, Long> {
    fun findByStationIdIn(stationIds: List<Long>): List<DayOfWeekChargingStrategyEntity>
}
