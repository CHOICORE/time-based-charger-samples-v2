package me.choicore.samples.charge.domain.evaluator

import jakarta.transaction.Transactional
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
import java.time.LocalDateTime

@Service
@Order(3)
class ChargingTargetChargingEvaluator(
    private val chargingRecorder: ChargingRecorder,
) : ChargingEvaluator {
    @Transactional
    override fun evaluate(chargeRequest: ChargeRequest) {
        val (context: ChargingContext, target: ChargingTarget, chargedOn: LocalDate) = chargeRequest
        log.info("Evaluating charge for target: ${target.targetId} on $chargedOn")

        val units: List<ChargingUnit> = chargeTargetUntilFullyCharged(context, target, chargedOn)

        chargingRecorder.record(target = target, units = units)

        log.info("Charging transaction registered for target: ${target.targetId} with ${units.size} units")
        chargeRequest.processed()
    }

    private fun chargeTargetUntilFullyCharged(
        context: ChargingContext,
        target: ChargingTarget,
        chargedOn: LocalDate,
    ): List<ChargingUnit> {
        calibrateChargingIfNeeded(target = target, chargedOn = chargedOn)
        var current: LocalDate = target.nextChargedOn
        val units: MutableList<ChargingUnit> = mutableListOf()
        while (current <= chargedOn) {
            log.debug("Processing charge on {} for target: {}", current, target.targetId)

            val chargingUnit: ChargingUnit = target.getChargingUnit(chargedOn = current)
            context.charge(unit = chargingUnit, chargedOn = current)
            target.charged(chargedOn = current)
            units.add(element = chargingUnit)

            if (target.status == ChargingStatus.CHARGED || target.status == ChargingStatus.CALIBRATED) {
                log.info("Charging target: ${target.targetId} fully charged. Ending evaluation.")
                break
            }

            current = current.plusDays(1)
        }

        return units
    }

    private fun calibrateChargingIfNeeded(
        target: ChargingTarget,
        chargedOn: LocalDate,
    ) {
        if (target.lastChargedOn != null && target.status == ChargingStatus.CHARGING) {
            val departedAt: LocalDateTime? = target.departedAt
            val lastChargedOn: LocalDate = target.lastChargedOn!!
            if (departedAt != null) {
                if (departedAt <= lastChargedOn.atStartOfDay()) {
                    log.info("Calibrating charge for target: ${target.targetId} on $chargedOn")
                    chargingRecorder.calibrate(target = target.apply { this.calibrated() })
                }
            }
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ChargingTargetChargingEvaluator::class.java)
    }
}
