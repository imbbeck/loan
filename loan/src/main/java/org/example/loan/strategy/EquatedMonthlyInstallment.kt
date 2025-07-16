package org.example.loan.strategy

import org.example.loan.RepaymentScheduleItem
import kotlin.math.pow

/**
 * 원리금균등상환 구현체
 * 
 * 납입금 = [P * r * (r + 1)^n] / [(r + 1)^n - 1]
 * P: 원금, r: 월 이자율, n: 상환 개월수
 * 
 * 매월 이자 = 남은 원금 * 월 이자율
 * 매월 원금 = 납입금 - 이자
 * 남은 원금 = 이전 남은 원금 - 이번 원금 상환
 */
class EquatedMonthlyInstallment : Repayment {
    
    override fun getRepaymentSchedule(
        principal: Double, 
        annualRate: Double, 
        months: Int
    ): MutableList<RepaymentScheduleItem> {
        val schedule = mutableListOf<RepaymentScheduleItem>()
        val monthlyRate = annualRate / 12.0 / 100.0
        
        // 월 납입금 계산
        val totalPayment = calculateMonthlyPayment(principal, monthlyRate, months)
        
        var remainingPrincipal = principal
        
        (1..months).forEach { month ->
            val interestPayment = remainingPrincipal * monthlyRate
            val principalPayment = totalPayment - interestPayment
            remainingPrincipal -= principalPayment

            schedule.add(
                RepaymentScheduleItem(
                    month = month,
                    totalPayment = totalPayment,
                    principalPayment = principalPayment,
                    interestPayment = interestPayment,
                    remainingPrincipal = remainingPrincipal
                )
            )
        }
        
        return schedule
    }
    
    private fun calculateMonthlyPayment(principal: Double, monthlyRate: Double, months: Int): Double {
        val ratePlus1 = monthlyRate + 1
        val ratePlus1Power = ratePlus1.pow(months.toDouble())
        val numerator = principal * monthlyRate * ratePlus1Power
        val denominator = ratePlus1Power - 1.0
        return numerator / denominator
    }
}
