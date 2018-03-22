package com.modest.util;

import java.math.BigDecimal;

public class NullAs {
	
	public static Integer zero(Integer i){
		return i == null ? 0 : i;
	}
	
	public static Long zero(Long l){
		return l == null ? 0L : l;
	}
	
	public static Double zero(Double d){
		return d == null ? 0.0 : d;
	}
	
	public static BigDecimal zero(BigDecimal dec){
		return dec == null ? BigDecimal.ZERO : dec;
	}
	
	public static String empty(String s){
		return s == null ? "" : s;
	}
}
