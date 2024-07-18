package com.sits.general;

public class Account {
	
	private String accountNumber;

	public String getAccountNumber() {
		return accountNumber;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + "]";
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
