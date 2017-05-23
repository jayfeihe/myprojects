package com.hikootech.mqcash.util;

import java.util.UUID;

public class UUIDTools {
	
	public static String getFormatUUID(){
		String result = UUID.randomUUID().toString();
		return result.replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(getFormatUUID());
	}

}
