package com.sits.general;

import java.util.List;

public class BusinessEntity {

	@Override
	public String toString() {
		return "BusinessEntity [businessEcid=" + businessEcid + ", accounts=" + accounts + ", getBusinessEcid()="
				+ getBusinessEcid() + ", getAccounts()=" + getAccounts() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	private String businessEcid;
    private List<Account> accounts;
    
	public String getBusinessEcid() {
		return businessEcid;
	}
	public void setBusinessEcid(String businessEcid) {
		this.businessEcid = businessEcid;
	}
	
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
    
}
