package org.example.loan.calculator;

import org.example.loan.RepaymentScheduleItem;
import org.example.loan.strategy.BulletLoan;
import org.example.loan.strategy.EquatedMonthlyInstallment;
import org.example.loan.strategy.Repayment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class LoanCalculatorTest {
    private final double PRINCIPAL = 50000000; // 대출 원금
    private final double ANNUAL_RATE = 10; // 연이율
    private final int MONTHS = 60; // 대출 기간 (개월)
    private final int Delta = 100; // 오차 범위


    @Test
    // 우리은행 금융계산기 사용. 이자가 일단위라 오차범위 적용
    void testEMI() {
        Repayment strategy = new EquatedMonthlyInstallment();

        List<RepaymentScheduleItem> schedule = strategy.getRepaymentSchedule(PRINCIPAL, ANNUAL_RATE, MONTHS);
        assertEquals(MONTHS, schedule.size());
        // 첫달 value셋 확인
	    assertEquals(1062352, schedule.get(0).getTotalPayment(), Delta);
        assertEquals(645686, schedule.get(0).getPrincipalPayment(),Delta);
        assertEquals(416666, schedule.get(0).getInterestPayment(),Delta);
        assertEquals(49354314, schedule.get(0).getRemainingPrincipal(),Delta);
        // 마지막달 value셋 확인
        assertEquals(1062352, schedule.get(MONTHS-1).getTotalPayment(),Delta);
        assertEquals(1053551, schedule.get(MONTHS-1).getPrincipalPayment(),Delta);
        assertEquals(8779, schedule.get(MONTHS-1).getInterestPayment(),Delta);
        assertEquals(0, schedule.get(MONTHS-1).getRemainingPrincipal(),Delta);
    }

    @Test
    // 국민은행 금융계산기 사용. 월평균금액 416667
    void testBulletLoan() {
        Repayment strategy = new BulletLoan();

        List<RepaymentScheduleItem> schedule = strategy.getRepaymentSchedule(PRINCIPAL, ANNUAL_RATE, MONTHS);
        assertEquals(MONTHS, schedule.size());
        // 첫달 value셋 확인
        assertEquals(416667, schedule.get(0).getTotalPayment(), Delta);
        assertEquals(0, schedule.get(0).getPrincipalPayment(),Delta);
        assertEquals(416667, schedule.get(0).getInterestPayment(),Delta);
        assertEquals(PRINCIPAL, schedule.get(0).getRemainingPrincipal(),Delta);
        // 마지막달 value셋 확인
        assertEquals(PRINCIPAL + 416667, schedule.get(MONTHS-1).getTotalPayment(),Delta);
        assertEquals(PRINCIPAL, schedule.get(MONTHS-1).getPrincipalPayment(),Delta);
        assertEquals(416667, schedule.get(MONTHS-1).getInterestPayment(),Delta);
        assertEquals(0, schedule.get(MONTHS-1).getRemainingPrincipal(),Delta);
    }
} 