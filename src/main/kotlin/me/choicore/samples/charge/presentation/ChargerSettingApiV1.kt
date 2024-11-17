package me.choicore.samples.charge.presentation

import me.choicore.samples.charge.domain.ChargingMeter
import me.choicore.samples.charge.domain.strategy.station.ChargingStation
import me.choicore.samples.charge.domain.strategy.station.ChargingStationReader
import me.choicore.samples.charge.domain.strategy.station.ChargingStationRegistrar
import me.choicore.samples.charge.presentation.dto.request.ChargingStationRegistrationRequestDto
import me.choicore.samples.charge.presentation.dto.response.ChargingStationResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/v1/chargers")
class ChargerSettingApiV1(
    private val chargingStationRegistrar: ChargingStationRegistrar,
    private val chargingStationReader: ChargingStationReader,
    private val batchProcessingCharger: ChargingMeter,
) {
    @PostMapping("/settings")
    fun setChargerSettings(
        @RequestBody request: ChargingStationRegistrationRequestDto,
    ): ResponseEntity<*> {
        val registered: Long = chargingStationRegistrar.register(request.toChargingStation())
        return ResponseEntity.ok(registered)
    }

    @GetMapping("/settings")
    fun getChargerSettings(): ResponseEntity<*> {
        val chargingStations: List<ChargingStation> = chargingStationReader.getChargingStations(1L)
        return ResponseEntity.ok(chargingStations.map { ChargingStationResponse.from(it) })
    }

    @GetMapping("/charge")
    fun charge(
        @RequestParam complexId: Long,
        @RequestParam chargedOn: LocalDate,
    ): ResponseEntity<Any> {
        batchProcessingCharger.charge(complexId, chargedOn)
        return ResponseEntity.ok().build()
    }
}
