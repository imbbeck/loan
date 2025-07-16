package org.example.loan

enum class LoanType(val description: String) {
    EMI("원리금균등상환"),
    BULLET_LOAN("만기일시상환");

    class InvalidLoanTypeException(message: String) : IllegalArgumentException(message)

    companion object {
        fun fromString(type: String): LoanType {
            return entries.find { it.name.equals(type, ignoreCase = true) }
                ?: throw InvalidLoanTypeException("지원하지 않는 대출 유형: $type")
        }
    }
}
