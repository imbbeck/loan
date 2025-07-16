package org.example.loan;

import java.lang.invoke.WrongMethodTypeException;

import lombok.Getter;

@Getter
public enum LoanType {
	EMI("원리금균등상환"),
	BULLET_LOAN("만기일시상환");

	private final String description;

	LoanType(String description) {
		this.description = description;
	}

	public static LoanType fromString(String type) throws InvalidLoanTypeException {
		return LoanType.valueOf(type.toUpperCase());
	}

	public static class InvalidLoanTypeException extends IllegalArgumentException {
		public InvalidLoanTypeException(String message) {
			super(message);
		}
	}

}
