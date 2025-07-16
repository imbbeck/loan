package org.example.loan

import org.example.loan.LoanType.InvalidLoanTypeException
import org.example.loan.factory.RepaymentFactory
import kotlin.system.exitProcess

// 메인 클래스
object LoanCalculatorApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size < 4) {
            eprintln("입력값 오류")
            exitProcess(1)
        }

        val (principal, annualRate, months, loanType) = try {
            val principal = args[0].toDouble()
            val annualRate = args[1].toDouble()
            val months = args[2].toInt()
            val loanType = LoanType.fromString(args[3])
            LoanParameters(principal, annualRate, months, loanType)
        } catch (ie: InvalidLoanTypeException) {
            eprintln("대출 유형 오류: ${ie.message}")
            exitProcess(1)
        } catch (nfe: NumberFormatException) {
            eprintln("숫자 형식 오류: ${nfe.message}")
            exitProcess(1)
        } catch (e: Exception) {
            eprintln("입력값 오류: ${e.message}")
            exitProcess(1)
        }

        println("원금: $principal, 이자율: $annualRate, 상환기간: $months, 상환방식: ${loanType.description}")

        val strategy = RepaymentFactory.createStrategy(loanType)
        val schedule = strategy.getRepaymentSchedule(principal, annualRate, months)

        // 함수형 스타일
        schedule.forEach { println(it) }
    }

    private fun eprintln(message: String) = System.err.println(message)
    
    private data class LoanParameters(
        val principal: Double,
        val annualRate: Double,
        val months: Int,
        val loanType: LoanType
    )
}
