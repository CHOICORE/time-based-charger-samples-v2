package me.choicore.samples.charge.domain.evaluator

import me.choicore.samples.charge.domain.ChargeRequest
import me.choicore.samples.charge.domain.ChargingContext
import me.choicore.samples.charge.domain.ChargingRecorder
import me.choicore.samples.charge.domain.core.ChargingStatus.EXEMPTED
import me.choicore.samples.charge.domain.core.ChargingStatus.PENDED
import me.choicore.samples.charge.domain.strategy.station.ChargingStation
import me.choicore.samples.charge.domain.target.ChargingTarget
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@Order(1)
class ExemptChargingEvaluator(
    private val chargingRecorder: ChargingRecorder,
) : ChargingEvaluator {
    override fun evaluate(chargeRequest: ChargeRequest) {
        val (context: ChargingContext, target: ChargingTarget, chargedOn: LocalDate) = chargeRequest
        val chargingStation: ChargingStation = context.chargingStationSelector.select(chargedOn)
        if (!target.chargeable(chargedOn, chargingStation.exemptionThreshold)) {
            when (target.status) {
                EXEMPTED -> chargingRecorder.exempt(target)
                PENDED -> chargingRecorder.pend(target)
                else -> {}
            }
            chargeRequest.processed()
        }
    }
}
