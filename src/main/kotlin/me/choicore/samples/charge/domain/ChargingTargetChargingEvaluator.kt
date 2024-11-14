package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingStatus.CHARGED
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

        var currentChargedOn: LocalDate = target.nextChargedOn

        val units: MutableList<ChargingUnit> = mutableListOf()
        while (currentChargedOn <= chargedOn) {
            val chargingUnit: ChargingUnit = target.getChargingUnit(chargedOn = currentChargedOn)
            context.charge(unit = chargingUnit, chargedOn = currentChargedOn)
            target.charged(chargedOn = currentChargedOn)

            units.add(chargingUnit)

            if (target.status == CHARGED) {
                break
            }

            currentChargedOn = currentChargedOn.plusDays(1)
        }
        chargingTransactionRegistrar.register(target = target, units = units)

        chargeRequest.processed()
    }
}
