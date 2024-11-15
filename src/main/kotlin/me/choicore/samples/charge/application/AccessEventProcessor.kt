package me.choicore.samples.charge.application

import me.choicore.samples.charge.application.AccessEvent.Type.ARRIVAL
import me.choicore.samples.charge.application.AccessEvent.Type.DEPARTURE
import me.choicore.samples.charge.domain.ChargingStatus.REGISTERED
import me.choicore.samples.charge.domain.ChargingTarget
import me.choicore.samples.charge.domain.ChargingTarget.ChargingTargetIdentifier
import me.choicore.samples.charge.domain.ChargingTargetCriteria
import me.choicore.samples.charge.domain.ChargingTargetReader
import me.choicore.samples.charge.domain.ChargingTargetRegistrar
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AccessEventProcessor(
    private val chargingTargetReader: ChargingTargetReader,
    private val chargingTargetRegistrar: ChargingTargetRegistrar,
) {
    /**
     *
     * 입차 이벤트 처리 방식
     * 해당 단지, 세대, 동, 차량 정보로 미출차 건을 조회하고
     * 가장 최근의 입차 시간을 확인하여 중복 요청인지 확인
     * 동일한 차량의 입차 요청이 들어온 경우 1분 이내에 중복 요청으로 판단
     * 1분 이내에 중복 요청으로 판단되는 경우 로그를 남기고 처리하지 않음
     * 1분 초과 시에는 이전 기록을 중단하고 중단된 이유로 출차 누락으로 상태 업데이트
     * 새로운 입차 기록을 생성
     *
     * 출차 이벤트 처리 방식
     * access_id 에 해당하는 대상을 찾아서 출차 처리
     * 출차 시간 기록 및 출차 상태로 변경
     * 워커 스레드에게 충전 요금 계산 및 충전 처리 요청
     */
    @Transactional
    fun process(event: AccessEvent) {
        when (event.type) {
            ARRIVAL -> {
                val criteria: ChargingTargetCriteria = event.toChargingTargetCriteria()
                val found: List<ChargingTarget> =
                    chargingTargetReader.findByCriteriaAndDepartedAtIsNullForUpdate(criteria = criteria)

                val mostRecentArrivedAt = found.maxOfOrNull { it.arrivedAt }
                if (mostRecentArrivedAt != null && mostRecentArrivedAt.isAfter(event.accessedAt)) {
                    log.warn("Suspect duplicate request. Already arrived: {}", event)
                    return
                }

                val chargingTarget =
                    ChargingTarget(
                        identifier =
                            ChargingTargetIdentifier.unregistered(
                                accessId = event.accessId,
                                complexId = event.complexId,
                                building = event.building,
                                unit = event.unit,
                                licensePlate = event.licensePlate,
                            ),
                        arrivedAt = event.accessedAt,
                        departedAt = null,
                        status = REGISTERED,
                    )
                chargingTargetRegistrar.register(chargingTarget)
                log.info("Registered: {}", chargingTarget)
            }

            DEPARTURE -> {
                val candidate: ChargingTarget? = chargingTargetReader.findChargingTargetByAccessId(event.accessId)

                if (candidate == null) {
                    log.warn("No charging target found for access id: {}", event.accessId)
                    return
                }

                val lastChargedOn: LocalDate? = candidate.lastChargedOn
                if (lastChargedOn != null) {
                    val departedAt: LocalDateTime = event.accessedAt
                    if (departedAt.isBefore(lastChargedOn.atStartOfDay())) {
                        log.warn(
                            "Departed on {} is before last charged on {}. Aborting.",
                            departedAt,
                            lastChargedOn,
                        )
                    }

                    candidate.aborted()
                } else {
                    candidate.departedAt = event.accessedAt
                }
            }
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(AccessEventProcessor::class.java)
    }
}
