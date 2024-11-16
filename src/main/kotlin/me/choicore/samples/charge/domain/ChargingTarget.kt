package me.choicore.samples.charge.domain

import me.choicore.samples.charge.domain.ChargingStatus.PENDED
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ChargingTarget(
    val targetId: Long = 0,
    val accessId: Long = 0,
    val complexId: Long = 0,
    val building: String,
    val unit: String,
    val licensePlate: String,
    val arrivedAt: LocalDateTime,
    var departedAt: LocalDateTime?,
    var status: ChargingStatus,
    var lastChargedOn: LocalDate? = null,
) {
    /**
     * 입차 날짜
     */
    val arrivedOn: LocalDate = arrivedAt.toLocalDate()

    /**
     * 출차 날짜
     */
    val departedOn: LocalDate? = departedAt?.toLocalDate()

    /**
     * 다음 청구 날짜
     */
    val nextChargedOn: LocalDate
        get() =
            if (this.lastChargedOn == null) {
                this.arrivedOn
            } else {
                this.lastChargedOn!!.plusDays(1)
            }

    /**
     * 청구 상태 변경 (충전 중, 충전 완료)
     */
    fun charged(chargedOn: LocalDate) {
        this.lastChargedOn = chargedOn
        this.status = if (this.departedOn == chargedOn) ChargingStatus.CHARGED else ChargingStatus.CHARGING
    }

    fun aborted() {
        this.status = ChargingStatus.ABORTED
    }

    /**
     * 청구 상태 변경 (면제)
     */
    fun exempted(chargedOn: LocalDate) {
        this.lastChargedOn = chargedOn
        this.status = ChargingStatus.EXEMPTED
    }

    /**
     * 청구 상태 변경 (대기)
     */
    fun pended() {
        this.status = PENDED
    }

    /**
     * 출차 처리
     */
    fun departed(departedAt: LocalDateTime) {
        this.departedAt = departedAt
    }

    /**
     * 청구 가능 여부 확인
     * @param chargedOn 청구 날짜
     * @param exemptionThreshold 면제 기준 (분)
     */
    fun chargeable(
        chargedOn: LocalDate,
        exemptionThreshold: Long,
    ): Boolean {
        if (departedAt != null) {
            val minutesOfStay: Long = Duration.between(arrivedAt, departedAt).toMinutes()
            if (minutesOfStay <= exemptionThreshold) {
                this.exempted(this.departedOn!!)
                return false
            }
        } else {
            if (arrivedOn == chargedOn) {
                val exemptionCutoffTime: LocalDateTime = arrivedAt.plusMinutes(exemptionThreshold)
                if (exemptionCutoffTime >= this.nextDayMidnight) {
                    this.status = PENDED
                    return false
                }
            }
        }
        return true
    }

    fun getChargingUnit(chargedOn: LocalDate): ChargingUnit {
        val startTime: LocalTime =
            if (this.arrivedOn == chargedOn) {
                this.arrivedAt.toLocalTime()
            } else {
                LocalTime.MIN
            }

        val endTime: LocalTime =
            if (this.departedOn == chargedOn) {
                this.departedAt!!.toLocalTime()
            } else {
                TimeUtils.MAX_TIME
            }

        return ChargingUnit(
            targetId = this.targetId,
            chargedOn = chargedOn,
            startTime = startTime,
            endTime = endTime,
        )
    }

    private val nextDayMidnight: LocalDateTime
        get() =
            this.arrivedAt
                .toLocalDate()
                .plusDays(1)
                .atStartOfDay()
}
