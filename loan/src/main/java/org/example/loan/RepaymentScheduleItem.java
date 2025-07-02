package org.example.loan;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 월별 상환 정보를 담는 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RepaymentScheduleItem {
    private int month;
    private double totalPayment;
    private double principalPayment;
    private double interestPayment;
    private double remainingPrincipal;

    @Builder
    public RepaymentScheduleItem(int month, double totalPayment, double principalPayment, double interestPayment, double remainingPrincipal) {
        this.month = month;
        this.totalPayment = totalPayment;
        this.principalPayment = principalPayment;
        this.interestPayment = interestPayment;
        this.remainingPrincipal = remainingPrincipal;
    }

	@Override
    public String toString() {
        return String.format("%d개월차 - 납입액: %.0f(원금분: %.0f, 이자분: %.0f), 잔액: %.0f",
                month, totalPayment, principalPayment, interestPayment, Math.abs(remainingPrincipal));
    }
} 