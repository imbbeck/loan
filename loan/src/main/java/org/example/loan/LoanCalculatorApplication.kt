package org.example.loan;

import org.example.loan.factory.RepaymentFactory;
import org.example.loan.strategy.Repayment;
import java.util.List;

// 메인 클래스
public class LoanCalculatorApplication {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("입력값 오류");
            System.exit(1); // 프로그램 종료 (비정상 종료 코드)
        }

        double principal;
        double annualRate;
        int months;
        LoanType loanType;

        try {
            principal = Double.parseDouble(args[0]);
            annualRate = Double.parseDouble(args[1]);
            months = Integer.parseInt(args[2]);
            loanType = LoanType.fromString(args[3]);
        } catch (LoanType.InvalidLoanTypeException ie) {
            System.err.println("대출 유형 오류: " + ie.getMessage());
            System.exit(1);
            return;
        } catch (NumberFormatException nfe) {
            System.err.println("숫자 형식 오류: " + nfe.getMessage());
            System.exit(1);
            return;
        } catch (Exception e) {
            System.err.println("입력값 오류: " + e.getMessage());
            System.exit(1);
            return;
        }

        System.out.println("원금 : " + principal + ", 이자율 : " + annualRate + ", 상환기간 : " + months + ", 상환방식 : " + loanType.getDescription());

        Repayment strategy = RepaymentFactory.createStrategy(loanType);
        List<RepaymentScheduleItem> schedule = strategy.getRepaymentSchedule(principal, annualRate, months);

        for (RepaymentScheduleItem repaymentScheduleItem : schedule) {
            System.out.println(repaymentScheduleItem.toString());
        }
    }
}
