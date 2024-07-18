package com.sits.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class test {

	public static void main(String[] args) {
		
		//Find the linked account numbers with their Business ECIDs
		Account acnt1= new Account();
		Account acnt2= new Account();
		/*BusinessEntity ac= new BusinessEntity();
		ac.setAccounts("Account-1234-1");*/
		
		acnt1.setAccountNumber("Account-1234-1");
		acnt2.setAccountNumber("Account-1234-2");
		
		List<Account> accounts1= new ArrayList<>(Arrays.asList(acnt1,acnt2));
		
		
		BusinessEntity busEnt1 = new BusinessEntity();
		busEnt1.setBusinessEcid("ECID-1234");
		busEnt1.setAccounts(accounts1);
		
		Account acnt3= new Account();
		
		acnt3.setAccountNumber("Account-5678-1");
		
		List<Account> accounts2= new ArrayList<>(Arrays.asList(acnt3));
		
		
		BusinessEntity busEnt2 = new BusinessEntity();
		busEnt2.setBusinessEcid("ECID-5678");
		busEnt2.setAccounts(accounts2);
		
		List<BusinessEntity> BE = new ArrayList<>();
		BE.add(busEnt1);
		BE.add(busEnt2);
		
		
		Map<String,List<String>> accountreport=generateAccountreport(BE);
		System.out.println("final finalrepot||"+accountreport);
		
		accountreport.forEach((busid,accountnos)->{
			
			 System.out.println("Business ECID: " + busid);
			 accountnos.forEach(acnt->{
				 
				 System.out.println("Account Number ||"+acnt);
			 });
			
		});
		
		
		
	}
	
	/**
	 * @param businessEntities
	 * @return
	 */
	public static Map<String, List<String>> generateAccountreport(List<BusinessEntity> businessEntities){
		
		Map<String, List<String>> accountreport= new HashMap<>();
		
		for(BusinessEntity busent: businessEntities){
			System.out.println("busent||"+busent);
			
			List<String> acntnos = new ArrayList<>(busent.getAccounts()).stream().map(
										be -> be.getAccountNumber()).collect(Collectors.toList());
			
			System.out.println("BusinessEcid||"+busent.getBusinessEcid()+"|AccountNumbers|"+acntnos);
			accountreport.put(busent.getBusinessEcid(), acntnos);
		}
		
		return accountreport;
	}

}
