package com.hikootech.mqcash.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yuwei
 * 2015年8月6日
 * 校验工具类
 */
public class ValidateParamsTools {
	
	private static Logger log = LoggerFactory.getLogger(ValidateParamsTools.class);
	
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
		Matcher m = p.matcher(mobileNumber.trim());  
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
		return EntityUtils.isEmpty(IDCard.IDCardValidate(idCard.trim())) ? true : false;
	}
	
	/**  
	 * checkChineseName(校验姓名是否是汉字)  
	 * @param name
	 * @return   
	 * boolean 
	 * @create time： Oct 15, 2015 4:16:46 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static boolean checkChineseName(String name) {
        if (!name.trim().matches("[\u4e00-\u9fa5]{2,10}")) {
            log.info("只能输入2到10个汉字");
            return false;
        }else return true;
    }
	
	/**  
	 * isIpv4(验证ip地址是否有效)  
	 * @param ipAddress
	 * @return   
	 * boolean 
	 * @create time： Dec 1, 2015 4:11:12 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static boolean isIpv4(String ipAddress) {  
		  
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";  
  
        Pattern pattern = Pattern.compile(ip);  
        Matcher matcher = pattern.matcher(ipAddress);  
        return matcher.matches();  
  
    } 

	/**  
	 * validIdCardAddress(验证身份证地址)  
	 * @param idCardAddress
	 * @return   
	 * boolean 
	 * @create time： 2016年7月20日 下午5:47:13 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static  boolean validIdCardAddress(String idCardAddress){
		//验证通过后，进行身份证地址合法性校验
		//1.不少于8个字符
		//2.前两个字符必须为省级名称
		//出现以上情况，提示客户按照身份证地址格式输入
		if(idCardAddress.trim().length() < 8){
			return false;
		}
		return true ;
	}
	/**  
	 * validMajor(验证专业)  
	 * @param major
	 * @return   
	 * boolean 
	 * @create time： 2016年7月20日 下午5:57:54 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static  boolean validMajor(String major){
		//验证通过后，进行身份证地址合法性校验
		//3.B4所在专业：长度校验，2-20个有效字符。失败提示“请输入正确专业信息，谢谢！”
		if(major.trim().length() < 2 || major.trim().length() > 22){
			return false;
		}
		return true ;
	}
	
	/**  
	 * validEntranctDate(验证入学时间)  
	 * @param major
	 * @return   
	 * boolean 
	 * @create time： 2016年7月20日 下午5:58:10 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static  boolean validEntranctDate(String major){
		Pattern pattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
		Matcher isNum = pattern.matcher(major);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static  boolean validDormitoryAddress(String dormitoryAddress){
		//验证通过后，进行身份证地址合法性校验
		//3.B4所在专业：长度校验，2-20个有效字符。失败提示“请输入正确专业信息，谢谢！”
		if(dormitoryAddress.trim().length() < 2 || dormitoryAddress.trim().length() > 30){
			return false;
		}
		return true ;
	}
	
}
