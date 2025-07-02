package org.example.loan.strategy;

import org.example.loan.RepaymentScheduleItem;
import java.util.List;

public interface Repayment {
    List<RepaymentScheduleItem> getRepaymentSchedule(double principal, double annualRate, int months);
} 