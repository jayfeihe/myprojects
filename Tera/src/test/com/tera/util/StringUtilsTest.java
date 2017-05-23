package com.tera.util;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StringUtilsTest {
	
	@Test
	public void formatNumberTest() {
		System.out.println(StringUtils.formatNumber(123456789.055, 2));
		System.out.println(StringUtils.formatNumber(8.0, 2));

		DecimalFormat df = new DecimalFormat("#,###.00");
		String fmt = df.format(56.7891);
		System.out.println(fmt);
	}
	@Test
	public void md5Test() {
		System.out.println(StringUtils.md5("我是姓名50000012345678901234567812002b3938710d2b81374c0db3f1637891f47"));
	}
	
	@Test
	public void delimitedListToStringArrayTest() {
		String[] a= StringUtils.delimitedListToStringArray("1,2,3",",");
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
		
		List<String> b = Arrays.asList("1,2,3".split(","));
		for (int i = 0; i < b.size(); i++) {
			System.out.println(b.get(i));
		}
	}
	
	@Test
	public void levenshteinTest() {
		String str1 = "今天星期四";  
        String str2 = "今天是星期五";
        System.out.println(StringUtils.levenshtein(str1,str2));
	}

}
