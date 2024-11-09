package me.choicore.samples.pms.charge.infrastucture

import org.springframework.data.jpa.repository.JpaRepository

interface ChargingStationEntityRepository : JpaRepository<ChargingStationEntity, Long>
