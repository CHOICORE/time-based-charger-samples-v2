package me.choicore.samples.charge.domain

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
        var totalSize = 0
        var previousSize = 0

        log.info("Starting charging session for complex id: $complexId on $chargedOn")

        while (true) {
            chargingTargets =
                chargingTargetReader.getChargingTargetsByComplexIdAndChargedOn(
                    complexId = complexId,
                    chargedOn = chargedOn,
                    size = chunk,
                )

            if (chargingTargets.isEmpty()) {
                log.info("No more charging targets found. Ending session.")
                break
            }

            totalSize += chargingTargets.size
            log.debug("Current charging session size: $totalSize (Previous size: $previousSize)")

            if (totalSize == previousSize) {
                throw IllegalStateException("Infinite loop detected! Charging session size is not increasing.")
            }

            previousSize = totalSize

            for (chargingTarget: ChargingTarget in chargingTargets) {
                log.debug(
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

        val specifies: List<SpecifiedDateChargingStrategy> =
            specifiedDateChargingStrategyReader.getSpecifiedDateChargingStrategiesByComplexId(complexId = complexId)
        log.debug("SpecifiedDateChargingStrategies retrieved for complex id: $complexId, count: ${specifies.size}")

        val specifiedDateChargingStrategies = SpecifiedDateChargingStrategies(specifies)

        val chargingContext =
            ChargingContext(
                chargingStationSelector = chargingStationSelector,
                specifiedDateChargingStrategies = specifiedDateChargingStrategies,
            )

        log.info("ChargingContext created successfully for complex id: $complexId")

        return chargingContext
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ChargingMeter::class.java)
    }
}
