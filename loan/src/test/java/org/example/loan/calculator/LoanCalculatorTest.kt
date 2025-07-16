package org.example.loan.calculator

import org.example.loan.strategy.BulletLoan
import org.example.loan.strategy.EquatedMonthlyInstallment
import org.example.loan.strategy.Repayment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LoanCalculatorTest {
    
    companion object {
        private const val PRINCIPAL = 50_000_000.0 // 대출 원금
        private const val ANNUAL_RATE = 10.0 // 연이율
        private const val MONTHS = 60 // 대출 기간 (개월)
        private const val DELTA = 100.0 // 오차 범위
    }

    @Test
    fun testEMI() {
        val strategy: Repayment = EquatedMonthlyInstallment()
        val schedule = strategy.getRepaymentSchedule(PRINCIPAL, ANNUAL_RATE, MONTHS)
        
        assertEquals(MONTHS, schedule.size)
        
        // 첫 달 검증
        with(schedule.first()) {
            assertEquals(1062352.0, totalPayment, DELTA)
            assertEquals(645686.0, principalPayment, DELTA)
            assertEquals(416666.0, interestPayment, DELTA)
            assertEquals(49354314.0, remainingPrincipal, DELTA)
        }
        
        // 마지막 달 검증
        with(schedule.last()) {
            assertEquals(1062352.0, totalPayment, DELTA)
            assertEquals(1053551.0, principalPayment, DELTA)
            assertEquals(8779.0, interestPayment, DELTA)
            assertEquals(0.0, remainingPrincipal, DELTA)
        }
    }

    @Test
    fun testBulletLoan() {
        val strategy: Repayment = BulletLoan()
        val schedule = strategy.getRepaymentSchedule(PRINCIPAL, ANNUAL_RATE, MONTHS)
        
        assertEquals(MONTHS, schedule.size)
        
        // 첫 달 검증
        with(schedule.first()) {
            assertEquals(416667.0, totalPayment, DELTA)
            assertEquals(0.0, principalPayment, DELTA)
            assertEquals(416667.0, interestPayment, DELTA)
            assertEquals(PRINCIPAL, remainingPrincipal, DELTA)
        }
        
        // 마지막 달 검증
        with(schedule.last()) {
            assertEquals(PRINCIPAL + 416667, totalPayment, DELTA)
            assertEquals(PRINCIPAL, principalPayment, DELTA)
            assertEquals(416667.0, interestPayment, DELTA)
            assertEquals(0.0, remainingPrincipal, DELTA)
        }
    }
}
