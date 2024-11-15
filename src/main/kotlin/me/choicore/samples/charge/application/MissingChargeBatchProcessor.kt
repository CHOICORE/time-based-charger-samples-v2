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
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
            log.info("Current charging session size: $totalSize (Previous size: $previousSize)")

            if (totalSize == previousSize) {
                throw IllegalStateException("Infinite loop detected! Charging session size is not increasing.")
            }

            previousSize = totalSize

            for (chargingTarget: ChargingTarget in chargingTargets) {
                log.debug(
                    "Evaluating charge for target id: ${chargingTarget.identifier.targetId}, derived from access id: ${chargingTarget.identifier.accessId}",
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
        private val log: Logger = LoggerFactory.getLogger(MissingChargeBatchProcessor::class.java)
    }
}
//        val chargingTargets: List<ChargingTarget> =
//            chargingTargetReader.getChargingTargetsByComplexIdAndChargedOn(
//                complexId = complexId,
//                chargedOn = chargedOn,
//            )
//
//        for (chargingTarget: ChargingTarget in chargingTargets) {
//            val chargeRequest = ChargeRequest(chargingContext, chargingTarget, chargedOn)
//            chargingEvaluator.evaluate(chargeRequest)
//        }
