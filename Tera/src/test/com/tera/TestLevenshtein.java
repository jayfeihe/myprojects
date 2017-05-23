package com.tera;

import org.junit.Test;

import com.tera.util.StringUtils;

public class TestLevenshtein {

	@Test
	public void test(){
		String s1 = "今天星期二";
		String s2 = "今天是星期一";
		double simla = StringUtils.levenshtein(s1 , s2);
		System.out.println(simla);
		
		double similarity = 0;
		similarity = 1-(double)2 / Math.max(s1.length(), s2.length());
		System.out.println(similarity);
	}
}
