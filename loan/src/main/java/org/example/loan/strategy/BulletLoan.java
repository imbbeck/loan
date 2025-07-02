package org.example.loan.strategy;

import org.example.loan.RepaymentScheduleItem;
import java.util.ArrayList;
import java.util.List;

// 만기일시상환 구현체
public class BulletLoan implements Repayment {

    /**
     * 만기일시상환
     * 매월 납입금 = P * r (1개월 ~ n-1개월)
     * 마지막 달 납입금 = P + (P * r)
     * P: 원금
     * r: 월 이자율
     * n: 상환 개월수
     * 매월 이자 = 원금 * 월 이자율
     * 매월 원금 = 0 (마지막 달 제외)
     * 마지막 달 원금 = 전체 원금
     * 남은 원금 = 마지막 달까지 원금 유지, 마지막 달에 0
     */
    @Override
    public List<RepaymentScheduleItem> getRepaymentSchedule(double principal, double annualRate, int months) {
        List<RepaymentScheduleItem> schedule = new ArrayList<>();

        double monthlyRate = annualRate / 12.0 / 100.0; // r: 월 이자율
        double interestPayment = principal * monthlyRate; // 매월 이자 = 매월 납입금

        // 1개월 ~ (n-1)개월: 이자만 납부
        for (int month = 1; month < months; month++) {
            schedule.add(RepaymentScheduleItem.builder()
                    .month(month)
                    .totalPayment(interestPayment)
                    .principalPayment(0.0)
                    .interestPayment(interestPayment)
                    .remainingPrincipal(principal)
                    .build()
            );
        }

        // 마지막 달: 원금 + 이자 일시상환
        schedule.add(RepaymentScheduleItem.builder()
                .month(months)
                .totalPayment(principal + interestPayment)
                .principalPayment(principal)
                .interestPayment(interestPayment)
                .remainingPrincipal(0.0)
                .build()
        );

        return schedule;
    }
}