package org.example.loan.strategy;

import org.example.loan.RepaymentScheduleItem;
import java.util.ArrayList;
import java.util.List;

// 원리금균등상환 구현체
public class EquatedMonthlyInstallment implements Repayment {

    /**
     * 납입금 = [P * r * (r + 1)^n] / [(r + 1)^n - 1]
     * P: 원금
     * r: 월 이자율
     * n: 상환 개월수
     * 매월 이자 = 남은 원금 * 월 이자율
     * 매월 원금 = 납입금 - 이자
     * 남은 원금 = 이전 남은 원금 - 이번 원금 상환
     */
    @Override
    public List<RepaymentScheduleItem> getRepaymentSchedule(double principal, double annualRate, int months) {
        List<RepaymentScheduleItem> schedule = new ArrayList<>();
        
        double monthlyRate = annualRate / 12.0 / 100.0; // r: 월 이자율
        double totalPayment; // 매월 납입금

        double ratePlus1 = monthlyRate + 1;         // (r + 1)
        double ratePlus1Power = Math.pow(ratePlus1, months); // (r + 1)^n
        double numerator = principal * monthlyRate * ratePlus1Power; // P * r * (r + 1)^n
        double denominator = ratePlus1Power - 1.0;                // (r + 1)^n - 1
        totalPayment = numerator / denominator;             // [P * r * (r + 1)^n] / [(r + 1)^n - 1]

        double remainingPrincipal = principal; // 남은 원금
        for (int month = 1; month <= months; month++) {
            double interestPayment = remainingPrincipal * monthlyRate; // 이자 = 남은 원금 * 월 이자율
            double principalPayment = totalPayment - interestPayment;  // 원금 = 납입금 - 이자
            remainingPrincipal -= principalPayment;                    // 남은 원금 갱신

            schedule.add(RepaymentScheduleItem.builder()
                    .month(month)
                    .totalPayment(totalPayment)
                    .principalPayment(principalPayment)
                    .interestPayment(interestPayment)
                    .remainingPrincipal(remainingPrincipal)
                    .build()
            );
        }
        return schedule;
    }
} 