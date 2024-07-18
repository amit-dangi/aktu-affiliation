package com.sits.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class test2 {
	//Separate odd and even numbers in a list of integers
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Number> numlist = new ArrayList<>(Arrays.asList(11,15.5,2,3,2,24,67,54));
		
	   Map<Boolean, List<Number>> partitioned = numlist.stream()
		            .filter(num -> num instanceof Integer)
		            .collect(Collectors.partitioningBy(num -> ((Integer) num) % 2 == 0));
		   
		   System.out.println("partitioned||"+partitioned.toString()); //This trika is not thread safe
		   
		List<Number> evenlist=partitioned.get(true);
		List<Number> oddlist=partitioned.get(false);
		System.out.println("evenlist||"+evenlist);
		System.out.println("oddlist||"+oddlist);
		
		//System.out.println("evenlist||"+evenlist);
		
		
		List<Number> simpleEvenlist=numlist.stream().filter(num -> num instanceof Integer && (Integer) num %2==0).collect(Collectors.toList());
		System.out.println("simpleEvenlist||"+simpleEvenlist);
		List<Number> simpleoddlist=numlist.stream().filter(num -> num instanceof Integer && (Integer) num %2!=0).collect(Collectors.toList());
		System.out.println("simpleoddlist||"+simpleoddlist);
		List<Number> doublelist=numlist.stream().filter(num -> num instanceof Double).collect(Collectors.toList());
		System.out.println("doublelist||"+doublelist);
		
		
		List<Integer> simpleNlist = new ArrayList<>(Arrays.asList(2,4,2,5,6,4,7,8));
		List<Integer> evenlist1=simpleNlist.stream().filter(nlist -> nlist%2==0).collect(Collectors.toList());
		System.out.println("evenlist1"+evenlist1);
		
		
		List<Integer> oddlist1=null;
		oddlist1=simpleNlist.stream().filter(nlist -> nlist%2!=0).collect(Collectors.toList());
		System.out.println("oddlist1"+oddlist1);
		
	}

}
