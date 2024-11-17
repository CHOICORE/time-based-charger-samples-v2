package me.choicore.samples.charge.domain.evaluator

import me.choicore.samples.charge.domain.ChargeRequest
import me.choicore.samples.charge.domain.ChargingContext
import me.choicore.samples.charge.domain.ChargingRecorder
import me.choicore.samples.charge.domain.core.ChargingStatus
import me.choicore.samples.charge.domain.target.ChargingTarget
import me.choicore.samples.charge.domain.target.ChargingUnit
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@Order(2)
class ChargingTargetChargingEvaluator(
    private val chargingRecorder: ChargingRecorder,
) : ChargingEvaluator {
    override fun evaluate(chargeRequest: ChargeRequest) {
        val (context: ChargingContext, target: ChargingTarget, chargedOn: LocalDate) = chargeRequest
        log.info("Evaluating charge for target: ${target.targetId} on $chargedOn")

        var currentChargedOn: LocalDate = target.nextChargedOn

        val units: MutableList<ChargingUnit> = mutableListOf()

        while (currentChargedOn <= chargedOn) {
            log.debug("Processing charge on {} for target: {}", currentChargedOn, target.targetId)

            val chargingUnit: ChargingUnit = target.getChargingUnit(chargedOn = currentChargedOn)

            context.charge(unit = chargingUnit, chargedOn = currentChargedOn)

            target.charged(chargedOn = currentChargedOn)

            units.add(chargingUnit)

            if (target.status == ChargingStatus.CHARGED) {
                log.info("Charging target: ${target.targetId} fully charged. Ending evaluation.")
                break
            }

            currentChargedOn = currentChargedOn.plusDays(1)
        }

        chargingRecorder.record(target = target, units = units)
        log.info("Charging transaction registered for target: ${target.targetId} with ${units.size} units")

        chargeRequest.processed()
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ChargingTargetChargingEvaluator::class.java)
    }
}
