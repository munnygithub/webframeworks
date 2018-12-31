package com.sunkara.inv.utils;

import org.springframework.stereotype.Component;

@Component
public class NumberUtility {
	
	public Integer convertStringToInteger(String inValue, Integer defaultValue) {
		try {
			return Integer.valueOf(inValue);
		} catch (Exception ex) {
			return defaultValue;
		}
	}
	public Double convertStringToDouble(String inValue, Double defaultValue) {
		try {
			return Double.valueOf(inValue);
		} catch (Exception ex) {
			return defaultValue;
		}
	}
	public Long convertStringToLong(String inValue, Long defaultValue) {
		try {
			return Long.valueOf(inValue);
		} catch (Exception ex) {
			return defaultValue;
		}
	}
	
}
