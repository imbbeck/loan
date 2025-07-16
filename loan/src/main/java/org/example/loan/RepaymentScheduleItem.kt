package org.example.loan

import kotlin.math.abs

/**
 * 월별 상환 정보를 담는 DTO
 */
data class RepaymentScheduleItem(
    val month: Int,
    val totalPayment: Double,
    val principalPayment: Double,
    val interestPayment: Double,
    val remainingPrincipal: Double
) {
    override fun toString(): String {
        return String.format(
            "%d개월차 - 납입액: %.0f(원금분: %.0f, 이자분: %.0f), 잔액: %.0f",
            month, totalPayment, principalPayment, interestPayment, abs(remainingPrincipal)
        )
    }
}
