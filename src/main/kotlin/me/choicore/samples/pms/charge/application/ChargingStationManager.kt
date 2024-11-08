package me.choicore.samples.pms.charge.application

import me.choicore.samples.pms.charge.domain.ChargingStationRegistration
import me.choicore.samples.pms.charge.domain.DayOfWeekChargingStrategies
import me.choicore.samples.pms.charge.domain.RecurringSchedule
import org.springframework.stereotype.Service

@Service
class ChargingStationManager {
    fun register(chargingStationRegistration: ChargingStationRegistration) {
        val recurringSchedules: List<RecurringSchedule> = chargingStationRegistration.standard
        val dayOfWeekChargingStrategies = DayOfWeekChargingStrategies()
        dayOfWeekChargingStrategies.register(recurringSchedules)
    }
}
