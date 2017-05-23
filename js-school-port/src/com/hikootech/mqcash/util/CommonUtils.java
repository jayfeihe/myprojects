package com.hikootech.mqcash.util;

/**
 * @author yuwei 2015年8月19日 一些公用工具方法
 */
public class CommonUtils {

	private static String[] randomChar = new String[] {  "2", "3",
			"4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
			"h",   "j", "k",  "m", "n", "u", "t", "s",  "x", "v",
			"p", "q", "r", "w", "y", "z" };
	
	private static String[] randomNumber = new String[] {  "2", "3",
		"4", "5", "6", "7", "8", "9"};
	
	public static String randomChar(int lenght) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < lenght; i++) {
			Double number = Math.random() * (randomChar.length - 1);
			str.append(randomChar[number.intValue()]);
		}

		return str.toString();
	}
	
	public static String randomNumber(int lenght) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < lenght; i++) {
			Double number = Math.random() * (randomNumber.length - 1);
			str.append(randomNumber[number.intValue()]);
		}

		return str.toString();
	}
	
	public static String trim(String str){
		return str.replaceAll("\\s*", "");
	}
	
	public static String join( Object[] o , String separator ){
        StringBuffer str_buff = new StringBuffer();
        int len=o.length;
        for(int i=0 ; i<len ; i++){
            str_buff.append( String.valueOf( o[i] ) );
            if(i<len-1)str_buff.append( separator );
        }
        return str_buff.toString(); 
    }
	
public static void main(String[] args) {
	randomChar(5);
}
}
