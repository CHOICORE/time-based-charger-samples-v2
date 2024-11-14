package me.choicore.samples.charge.domain

/**
 * 충전 상태
 *
 * - REGISTERED: 등록
 * - PENDED: 대기, 입차 시간 + 면제 시간이 다음날 자정을 넘어가면 대기 상태로 변경
 * - ABORTED: 충전 중단
 * - CHARGING: 충전 중
 * - CHARGED: 충전 완료
 * - EXEMPTED: 면제
 */
enum class ChargingStatus {
    REGISTERED,
    PENDED,
    ABORTED,
    CHARGING,
    CHARGED,
    EXEMPTED,
}
