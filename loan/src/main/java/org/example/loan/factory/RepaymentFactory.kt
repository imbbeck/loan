package org.example.loan.factory

import org.example.loan.LoanType
import org.example.loan.strategy.BulletLoan
import org.example.loan.strategy.EquatedMonthlyInstallment

object RepaymentFactory {
    fun createStrategy(type: LoanType) = when (type) {
        LoanType.EMI -> EquatedMonthlyInstallment()
        LoanType.BULLET_LOAN -> BulletLoan()
    }
}
