package com.hikootech.mqcash.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yuwei
 * 2015年8月6日
 * 校验工具类
 */
public class ValidateTools {
	
	/**
	 * 校验手机号是否合法
	 * @param mobileNumber
	 * @return
	 */
	public static boolean validateMobileNumber(String mobileNumber){
		if(EntityUtils.isEmpty(mobileNumber)){
			return false;
		}
		String pattern = "^1\\d{10}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(mobileNumber);  
		return m.matches();
	}
	
	/**
	 * 身份证校验
	 * @param idCard
	 * @return
	 */
	public static boolean validateIdCard(String idCard){
		if(EntityUtils.isEmpty(idCard)){
			return false;
		}
		return EntityUtils.isEmpty(IDCard.IDCardValidate(idCard)) ? true : false;
	}
	
	/**
	 * 银行卡校验
	 * @param cardNumber
	 * @return
	 */
	public static boolean validateBankCardNumber(String cardNumber){
		return checkBankCard(cardNumber);
	}
    
	/**
	 * 校验银行卡卡号
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId
				.substring(0, cardId.length() - 1));
		return cardId.charAt(cardId.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeCardId
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null
				|| nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			throw new IllegalArgumentException("Bank card code must be number!");
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}
	
	
		/**validateUserPwd
		* 此方法描述的是：正则判断密码是否合法
		* @author: zhaohefei
		* @version: 2015年10月12日 下午3:37:12
		*/
		
	public static boolean validateUserPwd(String pwd){
//		System.out.println("===>"+pwd);
		int _len=pwd.length();
		if(_len>=6&&_len<=16){
			String pattern1="\\d";//是否全数字
			String pattern2 ="[a-zA-Z]";//是否全字母
			String pattern3="[\\pP\\p{Punct}]";//是否全字符 包括 !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
			Pattern p1 = Pattern.compile(pattern1);
			Pattern p2 = Pattern.compile(pattern2);
			Pattern p3 = Pattern.compile(pattern3);
			boolean has09=p1.matcher(pwd).find();  
			boolean hasAZ=p2.matcher(pwd).find();  
			boolean hasSign=p3.matcher(pwd).find(); 
//			System.out.println("===>"+has09);
//			System.out.println("===>"+hasAZ);
//			System.out.println("===>"+hasSign);
			
			if(!(hasAZ&&has09||hasAZ&&hasSign||has09&&hasSign)){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	public static void main(String[] args) {
//		System.out.println(validateMobileNumber("13716855866"));
//		System.out.println(validateMobileNumber("137168558661"));
//		System.out.println(validateMobileNumber("1371685586a"));
//		System.out.println(checkBankCard("6225880150586668"));
//		System.out.println(checkBankCard("6225880150586646"));
		validateUserPwd("`!@#$%^&()12sds");
//		validateUserPwd("_+-=;',");
//		validateUserPwd(".<>?:\"\\|[]{}");
	}
	
}
