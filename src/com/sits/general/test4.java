package com.sits.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class test4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Find the frequency of each character in a string using Java 8 streams
		String mystr="jaishreeram";
		String[] strl = mystr.split("");
		List<String> strlist = new ArrayList<String>(Arrays.asList(strl));
		Map<String, Long> fl= strlist.stream().collect(Collectors.groupingBy(val-> val, Collectors.counting()));
		
		System.out.println("fl||"+fl);
	}

}
