package com.sits.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class test3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		List<Integer> nlist = new ArrayList<Integer>(Arrays.asList(1,2,5,4,6,2,4));
		List<Integer> flist= nlist.stream().distinct().collect(Collectors.toList());
		System.out.println("flist||"+flist.toString());
		
		//Remove duplicate elements from a list using Java 8 streams
		Set<Integer> mset = new HashSet<>();
		nlist.stream().filter(n1 -> !mset.add(n1)).collect(Collectors.toList());
		System.out.println("mset||"+mset);
	}

}
