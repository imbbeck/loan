package org.example.loan.strategy

import org.example.loan.RepaymentScheduleItem

interface Repayment {
    fun getRepaymentSchedule(principal: Double, annualRate: Double, months: Int): MutableList<RepaymentScheduleItem>
}
