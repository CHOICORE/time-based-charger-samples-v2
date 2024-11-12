package me.choicore.samples.charge.infrastructure.jpa.entity

import org.springframework.data.jpa.repository.JpaRepository

interface ChargingDetailEntityRepository : JpaRepository<ChargingDetailEntity, Long>
