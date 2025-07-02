package org.example.loan.factory;

import org.example.loan.LoanType;
import org.example.loan.strategy.Repayment;
import org.example.loan.strategy.EquatedMonthlyInstallment;
import org.example.loan.strategy.BulletLoan;

public class RepaymentFactory {
    
    public static Repayment createStrategy(LoanType type) {
	    return switch (type) {
		    case EMI -> new EquatedMonthlyInstallment();
		    case BULLET_LOAN -> new BulletLoan();
	    };
    }
} 