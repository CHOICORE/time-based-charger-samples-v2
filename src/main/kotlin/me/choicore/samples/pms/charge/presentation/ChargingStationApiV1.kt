package me.choicore.samples.pms.charge.presentation

import me.choicore.samples.pms.charge.application.ChargingStationManager
import me.choicore.samples.pms.charge.presentation.request.ChargingStationRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/charging-stations")
class ChargingStationApiV1(
    private val chargingStationManager: ChargingStationManager,
) {
    @PostMapping
    fun create(
        @RequestBody request: ChargingStationRequest,
    ) {
        chargingStationManager.register(request.toChargingStationRegistration())
    }

    fun specificDateStrategies() {
        // 특정 날짜만 등록함
    }
}
