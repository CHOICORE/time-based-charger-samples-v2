package me.choicore.samples.charge.infrastructure.jpa

import me.choicore.samples.charge.domain.ChargingTarget
import me.choicore.samples.charge.domain.ChargingTargetRepository
import me.choicore.samples.charge.infrastructure.jpa.entity.ChargingTargetEntityRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ChargingTargetRepositoryImpl(
    private val chargingTargetEntityRepository: ChargingTargetEntityRepository,
) : ChargingTargetRepository {
    override fun save(chargingTarget: ChargingTarget): ChargingTarget {
        TODO("Not yet implemented")
    }

    override fun findByComplexIdAndLastChargedOnLessThanEqualOrNull(
        complexId: Long,
        lastChargedOn: LocalDate,
    ): List<ChargingTarget> =
        chargingTargetEntityRepository
            .findByComplexIdAndLastChargedOnIsNullOrLastChargedOnLessThanEqual(
                complexId = complexId,
                lastChargedOn = lastChargedOn,
            ).map { it.toChargingTarget() }
}
