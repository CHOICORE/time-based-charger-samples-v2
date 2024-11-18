package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.evaluator.ChargingEvaluator
import me.choicore.samples.charge.domain.strategy.SpecifiedDateChargingStrategies
import me.choicore.samples.charge.domain.strategy.SpecifiedDateChargingStrategy
import me.choicore.samples.charge.domain.strategy.SpecifiedDateChargingStrategyReader
import me.choicore.samples.charge.domain.strategy.station.ChargingStationSelector
import me.choicore.samples.charge.domain.strategy.station.ChargingStationSelectorProvider
import me.choicore.samples.charge.domain.target.ChargingTarget
import me.choicore.samples.charge.domain.target.ChargingTargetReader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ChargingMeter(
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
        val chunk = 1000
        var chargingTargets: List<ChargingTarget>
        log.info("Starting charging session for complex id: $complexId on $chargedOn")

        while (true) {
            chargingTargets =
                chargingTargetReader.getChargingTargets(
                    complexId = complexId,
                    chargedOn = chargedOn,
                    size = chunk,
                )

            if (chargingTargets.isEmpty()) {
                log.info("No more charging targets found. Ending session.")
                break
            }

            for (chargingTarget: ChargingTarget in chargingTargets) {
                log.info(
                    "Evaluating charge for target id: ${chargingTarget.targetId}, derived from access id: ${chargingTarget.accessId}",
                )
                chargingEvaluator.evaluate(
                    ChargeRequest(
                        context = chargingContext,
                        target = chargingTarget,
                        chargedOn = chargedOn,
                    ),
                )
            }
        }

        log.info("Charging session completed successfully for complex id: $complexId on $chargedOn")
    }

    private fun createChargingContext(complexId: Long): ChargingContext {
        log.info("Creating ChargingContext for complex id: $complexId")

        val chargingStationSelector: ChargingStationSelector =
            chargingStationSelectorProvider.getChargingStationSelector(complexId = complexId)
        log.debug("ChargingStationSelector retrieved for complex id: $complexId")

        val specifiedDateChargingStrategies: SpecifiedDateChargingStrategies =
            loadSpecifiedDateChargingStrategies(complexId)

        val chargingContext =
            ChargingContext(
                chargingStationSelector = chargingStationSelector,
                specifiedDateChargingStrategies = specifiedDateChargingStrategies,
            )

        log.info("ChargingContext created successfully for complex id: $complexId")

        return chargingContext
    }

    private fun loadSpecifiedDateChargingStrategies(complexId: Long): SpecifiedDateChargingStrategies {
        val specifies: List<SpecifiedDateChargingStrategy> =
            specifiedDateChargingStrategyReader.getSpecifiedDateChargingStrategiesByComplexId(complexId = complexId)
        log.debug("SpecifiedDateChargingStrategies retrieved for complex id: $complexId, count: ${specifies.size}")

        val specifiedDateChargingStrategies: SpecifiedDateChargingStrategies =
            if (specifies.isEmpty()) {
                SpecifiedDateChargingStrategies.EMPTY
            } else {
                SpecifiedDateChargingStrategies(specifies)
            }
        return specifiedDateChargingStrategies
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ChargingMeter::class.java)
    }
}
