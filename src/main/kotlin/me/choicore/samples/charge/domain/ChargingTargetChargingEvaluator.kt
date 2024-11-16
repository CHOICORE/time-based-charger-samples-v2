package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingStatus.CHARGED
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@Order(2)
class ChargingTargetChargingEvaluator(
    private val chargingTransactionRegistrar: ChargingTransactionRegistrar,
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

            if (target.status == CHARGED) {
                log.info("Charging target: ${target.targetId} fully charged. Ending evaluation.")
                break
            }

            currentChargedOn = currentChargedOn.plusDays(1)
        }

        chargingTransactionRegistrar.register(target = target, units = units)
        log.info("Charging transaction registered for target: ${target.targetId} with ${units.size} units")

        chargeRequest.processed()
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ChargingTargetChargingEvaluator::class.java)
    }
}
