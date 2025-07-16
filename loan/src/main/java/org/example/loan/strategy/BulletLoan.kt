package org.example.loan.strategy

import org.example.loan.RepaymentScheduleItem

/**
 * 만기일시상환 구현체
 * 
 * 매월 납입금 = P * r (1개월 ~ n-1개월)
 * 마지막 달 납입금 = P + (P * r)
 * P: 원금, r: 월 이자율, n: 상환 개월수
 * 
 * 매월 이자 = 원금 * 월 이자율
 * 매월 원금 = 0 (마지막 달 제외)
 * 마지막 달 원금 = 전체 원금
 * 남은 원금 = 마지막 달까지 원금 유지, 마지막 달에 0
 */
class BulletLoan : Repayment {
    
    override fun getRepaymentSchedule(
        principal: Double, 
        annualRate: Double, 
        months: Int
    ): MutableList<RepaymentScheduleItem> {
        val schedule = mutableListOf<RepaymentScheduleItem>()
        val monthlyRate = annualRate / 12.0 / 100.0
        val interestPayment = principal * monthlyRate

        // 1개월 ~ (n-1)개월: 이자만 납부
        (1 until months).forEach { month ->
            schedule.add(
                RepaymentScheduleItem(
                    month = month,
                    totalPayment = interestPayment,
                    principalPayment = 0.0,
                    interestPayment = interestPayment,
                    remainingPrincipal = principal
                )
            )
        }

        // 마지막 달: 원금 + 이자 일시상환
        schedule.add(
            RepaymentScheduleItem(
                month = months,
                totalPayment = principal + interestPayment,
                principalPayment = principal,
                interestPayment = interestPayment,
                remainingPrincipal = 0.0
            )
        )

        return schedule
    }
}
