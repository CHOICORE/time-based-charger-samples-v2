package me.choicore.samples.charge.application

import me.choicore.samples.charge.domain.ChargeRequest
import me.choicore.samples.charge.domain.ChargingContext
import me.choicore.samples.charge.domain.ChargingEvaluator
import me.choicore.samples.charge.domain.ChargingStationSelector
import me.choicore.samples.charge.domain.ChargingStationSelectorProvider
import me.choicore.samples.charge.domain.ChargingTarget
import me.choicore.samples.charge.domain.ChargingTargetReader
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategies
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategy
import me.choicore.samples.charge.domain.SpecifiedDateChargingStrategyReader
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MissingChargeBatchProcessor(
    private val chargingStationSelectorProvider: ChargingStationSelectorProvider,
    private val specifiedDateChargingStrategyReader: SpecifiedDateChargingStrategyReader,
    private val chargingTargetReader: ChargingTargetReader,
    private val chargingEvaluator: ChargingEvaluator,
) {
    fun charge(
        complexId: Long,
        chargedOn: LocalDate,
    ) {
        val chargingContext: ChargingContext = createChargingContext(complexId)
        val chargingTargets: List<ChargingTarget> =
            chargingTargetReader.getChargingTargetsByComplexIdAndChargedOn(
                complexId = complexId,
                chargedOn = chargedOn,
            )

        for (chargingTarget: ChargingTarget in chargingTargets) {
            val chargeRequest = ChargeRequest(chargingContext, chargingTarget, chargedOn)
            chargingEvaluator.evaluate(chargeRequest)
        }
    }

    private fun createChargingContext(complexId: Long): ChargingContext {
        val chargingStationSelector: ChargingStationSelector =
            chargingStationSelectorProvider.getChargingStationSelector(complexId = complexId)

        val specifies: List<SpecifiedDateChargingStrategy> =
            specifiedDateChargingStrategyReader.getSpecifiedDateChargingStrategiesByComplexId(complexId = complexId)

        val specifiedDateChargingStrategies = SpecifiedDateChargingStrategies(specifies)

        return ChargingContext(
            chargingStationSelector = chargingStationSelector,
            specifiedDateChargingStrategies = specifiedDateChargingStrategies,
        )
    }
}
