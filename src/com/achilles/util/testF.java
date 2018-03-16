package com.achilles.util;

import java.math.BigInteger;

public class testF {
	public static void main(String[] args) {
		int i=2;
		BigInteger[] f = new BigInteger[2018];
		(f[0]).setBit(1);
		(f[1]).setBit(1);
		for(; i < 2018; i++ ) {
			if(i == 2000) {
				System.out.println(f[i-1]);
			}
			f[i].setBit((f[i-1]).intValue() + (f[i-2]).intValue());
		}
		System.out.println("菲波拉契数列第2018项值为:" + f[2017]);
		System.out.println("除以3余数:" + (f[2017]).intValue()%3);
	}
}
