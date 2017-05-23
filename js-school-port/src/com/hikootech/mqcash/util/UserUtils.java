package com.hikootech.mqcash.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikootech.mqcash.dto.UserBankCardDTO;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.po.UserPaymentRecordItem;

/**
 * @author yuwei
 * 2015年8月6日
 * 用户工具类：存入session、从session取出等方法
 */
public class UserUtils {
	
	private static Logger log = LoggerFactory.getLogger(UserUtils.class);
	
	/**
	 * 用户信息在session中缓存key
	 */
	public final static String USER_SESSION_KEY = "MQ_INSTALMENT_USER_KEY";
	/**
	 * 用户使用手机登录获取手机验证码时，使用的手机号在session中缓存key
	 */
	public final static String USER_MOBILE_LOGIN_MOBILE_NUMBER_SESSION_KEY = "MQ_USER_MOBILE_LOGIN_MOBILE_NUMBER_KEY";
	/**
	 * 用户登录验证码(validateCode)在session中缓存key
	 */
	public final static String USER_MOBILE_LOGIN_VALIDATION_CODE_SESSION_KEY = "MQ_USER_MOBILE_LOGIN_VALIDATION_CODE_KEY";
	/**
	 * 用户登录验证码(validateCode)在session中有效时间缓存key
	 */
	public final static String USER_MOBILE_LOGIN_VALIDATION_CODE_EFFECTIVE_TIME_SESSION_KEY = "MQ_USER_MOBILE_LOGIN_VALIDATION_CODE_EFFECTIVE_TIME_KEY";
	/**
	 * 用户使用手机登录动态密码(smcode)在session中缓存key
	 */
	public final static String USER_MOBILE_LOGIN_PWD_SESSION_KEY = "MQ_USER_MOBILE_LOGIN_PWD_KEY";
	/**
	 * 用户使用手机登录动态密码(smcode)在session中有效时间缓存key
	 */
	public final static String USER_MOBILE_LOGIN_PWD_EFFECTIVE_TIME_SESSION_KEY = "MQ_USER_MOBILE_LOGIN_PWD_EFFECTIVE_TIME_KEY";
	
	/**
	 * 用户修改绑定手机使用的动态密码(smcodeUpdate)在session中缓存key(修改手机第一步)
	 */
	public final static String USER_MOBILE_FOR_UPDATE_ONE_SESSION_KEY = "USER_MOBILE_FOR_UPDATE_ONE_SESSION_KEY";
	/**
	 * 用户修改绑定手机使用的动态密码(smcodeUpdate)在session中有效时间缓存key(修改手机第一步)
	 */
	public final static String USER_MOBILE_FOR_UPDATE_ONE_EFFECTIVE_TIME_SESSION_KEY = "USER_MOBILE_FOR_UPDATE_ONE_EFFECTIVE_TIME_SESSION_KEY";
	
	/**
	 * 用户修改绑定手机使用的动态密码(smcodeUpdate)在session中缓存key(修改手机第2步)
	 */
	public final static String USER_MOBILE_FOR_UPDATE_TWO_SESSION_KEY = "USER_MOBILE_FOR_UPDATE_TWO_SESSION_KEY";
	/**
	 * 用户修改绑定手机使用的动态密码(smcodeUpdate)在session中有效时间缓存key(修改手机第2步)
	 */
	public final static String USER_MOBILE_FOR_UPDATE_TWO_EFFECTIVE_TIME_SESSION_KEY = "USER_MOBILE_FOR_UPDATE_TWO_EFFECTIVE_TIME_SESSION_KEY";
	
	/**
	 * 用户修改PWD使用的动态密码(smcodeUpdate)在session中缓存key(修改PWD)
	 */
	public final static String USER_MOBILE_FOR_UPDATE_PSW_SESSION_KEY = "USER_MOBILE_FOR_UPDATE_PSW_SESSION_KEY";
	/**
	 * 用户修改PWD使用的动态密码(smcodeUpdate)在session中有效时间缓存key(修改PWD)
	 */
	public final static String USER_MOBILE_FOR_UPDATE_PSW_EFFECTIVE_TIME_SESSION_KEY = "USER_MOBILE_FOR_UPDATE_PSW_EFFECTIVE_TIME_SESSION_KEY";
	
	
	/**
	 * 用户使用手机登录验证码(validateCode)在session中缓存key
	 */
	public final static String USER_MOBILE_UPDATE_VALIDATION_CODE_SESSION_KEY = "MQ_USER_MOBILE_UPDATE_VALIDATION_CODE_KEY";
	/**
	 * 用户使用手机登录验证码(validateCode)在session中有效时间缓存key
	 */
	public final static String USER_MOBILE_UPDATE_VALIDATION_CODE_EFFECTIVE_TIME_SESSION_KEY = "MQ_USER_MOBILE_UPDATE_VALIDATION_CODE_EFFECTIVE_TIME_KEY";
	
	/**
	 * 用户绑定银行的绑定记录id
	 */
	public final static String USER_BIND_BANK_CARD_SESSION_KEY = "MQ_USER_BIND_BANK_CARD_KEY";
	
	/**
	 * 用户主动还款金额对象记录id
	 */
	public final static String USER_PAY_PAYMENTORDER_SESSION_KEY = "USER_PAY_PAYMENTORDER_SESSION_KEY";
	
	/**
	 * 用户主动还款信息子项记录id
	 */
	public final static String USER_PAY_PAYMENTORDER_ITEM_SESSION_KEY = "USER_PAY_PAYMENTORDER_ITEM_SESSION_KEY";
	
	
	/**
	 * 用户主动还款金额时，对还款金额对象进行缓存标记
	 */
	public final static String USER_PAY_PAYMENTORDER_TOKEN_SESSION_KEY = "USER_PAY_PAYMENTORDER_TOKEN_SESSION_KEY";
	
	/**
	 * 用户主动还款金额对象记录在session中的有效时间
	 */
	public final static String USER_PAY_PAYMENTORDER_EFFECTIVE_TIME_SESSION_KEY = "USER_PAY_PAYMENTORDER_EFFECTIVE_TIME_SESSION_KEY";
	
	
	/**缓存用户信息
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheUserInfo(HttpSession session, UserInfo userInfo){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_SESSION_KEY, userInfo);
		return true;
	}
	
	/**从缓存中获取用户信息
	 * @param session
	 * @return
	 */
	public static UserInfo getUserInfoFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (UserInfo) tmp; 
	}
	
	/**从缓存中清除当前用户信息
	 * @param session
	 * @return
	 */
	public static void clearUserInfoFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		session.removeAttribute(USER_SESSION_KEY);
	}
	
	/**缓存用户登录验证码(validateCode)
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileLoginValidationCode(HttpSession session, String validationCode){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_LOGIN_VALIDATION_CODE_SESSION_KEY, validationCode);
		return true;
	}
	
	/**从缓存中获取用户登录验证码(validateCode)
	 * @param session
	 * @return
	 */
	public static String getMobileLoginValidationCodeFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_LOGIN_VALIDATION_CODE_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (String) tmp; 
	}
	
	/**缓存用户登录验证码(validateCode)在session中有效时间
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileLoginValidationCodeEffectiveTime(HttpSession session, Date date){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_LOGIN_VALIDATION_CODE_EFFECTIVE_TIME_SESSION_KEY, date);
		return true;
	}
	
	/**从缓存中获取用户使用手机登录验证码(validateCode)在session中有效时间
	 * @param session
	 * @return
	 */
	public static Date getMobileLoginValidationCodeEffectiveTimeFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_LOGIN_VALIDATION_CODE_EFFECTIVE_TIME_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (Date) tmp; 
	}
	
	/**缓存用户使用手机登录动态密码(smcode)
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileLoginPwd(HttpSession session, String pwd){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_LOGIN_PWD_SESSION_KEY, pwd);
		return true;
	}
	
	/**从缓存中获取用户使用手机登录动态密码(smcode)
	 * @param session
	 * @return
	 */
	public static String getMobileLoginPwdFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_LOGIN_PWD_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (String) tmp; 
	}
	
	/**缓存用户使用手机登录动态密码(smcode)在session中有效时间
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileLoginPwdEffectiveTime(HttpSession session, Date date){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_LOGIN_PWD_EFFECTIVE_TIME_SESSION_KEY, date);
		return true;
	}
	
	/**从缓存中获取用户使用手机登录动态密码(smcode)在session中有效时间
	 * @param session
	 * @return
	 */
	public static Date getMobileLoginPwdEffectiveTimeFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_LOGIN_PWD_EFFECTIVE_TIME_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (Date) tmp; 
	}
	
	 

	//**********************************修改时使用的动态密码  start*********************************************************
	
	//*********************step1*********************
	/**缓存用户修改绑定手机时的动态密码step1(smcode)
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileSmsCodeUpdateOne(HttpSession session, String pwd){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_FOR_UPDATE_ONE_SESSION_KEY, pwd);
		return true;
	}

	/**从缓存中获取用户修改绑定手机时的动态密码step1(smcode)
	 * @param session
	 * @return
	 */
	public static String getMobileSmsCodeForUpdateFromCacheOne(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_FOR_UPDATE_ONE_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (String) tmp; 
	}

	/**缓存用户修改绑定手机时的动态密码(smcode)在session中有效时间step1
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileSmsCodeForUpdateEffectiveTimeOne(HttpSession session, Date date){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_FOR_UPDATE_ONE_EFFECTIVE_TIME_SESSION_KEY, date);
		return true;
	}
	
	/**从缓存中获取用户修改绑定手机时的动态密码(smcode)在session中有效时间step1
	 * @param session
	 * @return
	 */
	public static Date getMobileSmsCodeForUpdateEffectiveTimeFromCacheOne(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_FOR_UPDATE_ONE_EFFECTIVE_TIME_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (Date) tmp; 
	}
	
	
	//*********************step2*********************
	/**缓存用户修改绑定手机时的动态密码step2(smcode)
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileSmsCodeUpdateTwo(HttpSession session, String pwd){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_FOR_UPDATE_TWO_SESSION_KEY, pwd);
		return true;
	}
	
	/**从缓存中获取用户修改绑定手机时的动态密码step2(smcode)
	 * @param session
	 * @return
	 */
	public static String getMobileSmsCodeForUpdateFromCacheTwo(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_FOR_UPDATE_TWO_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (String) tmp; 
	}
	
	
	/**缓存用户修改绑定手机时的动态密码(smcode)在session中有效时间step2
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileSmsCodeForUpdateEffectiveTimeTwo(HttpSession session, Date date){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_FOR_UPDATE_TWO_EFFECTIVE_TIME_SESSION_KEY, date);
		return true;
	}
	
	/**从缓存中获取用户修改绑定手机时的动态密码(smcode)在session中有效时间step2
	 * @param session
	 * @return
	 */
	public static Date getMobileSmsCodeForUpdateEffectiveTimeFromCacheTwo(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_FOR_UPDATE_TWO_EFFECTIVE_TIME_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (Date) tmp; 
	}
	
	
	//*********************psw*********************
	/**缓存用户修改pwd时的动态密码(smcode)
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileSmsCodeUpdatePsw(HttpSession session, String pwd){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_FOR_UPDATE_PSW_SESSION_KEY, pwd);
		return true;
	}
	
	/**从缓存中获取用户修改pwd的动态密码(smcode)
	 * @param session
	 * @return
	 */
	public static String getMobileSmsCodeForUpdateFromPsw(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_FOR_UPDATE_PSW_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (String) tmp; 
	}
	
	/**缓存用户修改绑定手机时的动态密码(smcode)在session中有效时间
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileSmsCodeForUpdateEffectiveTimePsw(HttpSession session, Date date){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_FOR_UPDATE_PSW_EFFECTIVE_TIME_SESSION_KEY, date);
		return true;
	}
	
	/**从缓存中获取用户修改绑定手机时的动态密码(smcode)在session中有效时间
	 * @param session
	 * @return
	 */
	public static Date getMobileSmsCodeForUpdateEffectiveTimeFromCachePsw(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_FOR_UPDATE_PSW_EFFECTIVE_TIME_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (Date) tmp; 
	}
	
	
	//**********************************修改时使用的动态密码  end*********************************************************
	//**********************************修改时使用的验证码*********************************************************
	
	/**缓存用户修改绑定手机时的验证码(validateCode)
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileUpdateValidationCode(HttpSession session, String validationCode){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_UPDATE_VALIDATION_CODE_SESSION_KEY, validationCode);
		return true;
	}
	
	/**从缓存中获取用户修改绑定手机时的验证码(validateCode)
	 * @param session
	 * @return
	 */
	public static String getMobileUpdateValidationCodeFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_UPDATE_VALIDATION_CODE_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (String) tmp; 
	}
	
	/**缓存用户修改绑定手机时的验证码(validateCode)在session中有效时间
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileUpdateValidationCodeEffectiveTime(HttpSession session, Date date){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_UPDATE_VALIDATION_CODE_EFFECTIVE_TIME_SESSION_KEY, date);
		return true;
	}
	
	/**从缓存中获取用户修改绑定手机时的验证码(validateCode)在session中有效时间
	 * @param session
	 * @return
	 */
	public static Date getMobileUpdateValidationCodeEffectiveTimeFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_UPDATE_VALIDATION_CODE_EFFECTIVE_TIME_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (Date) tmp; 
	}
	
	/**缓存用户使用手机登录获取手机验证码时，使用的手机号
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cacheMobileLoginMobileNumber(HttpSession session, String mobileNumber){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_MOBILE_LOGIN_MOBILE_NUMBER_SESSION_KEY, mobileNumber);
		return true;
	}
	
	/**从缓存中获取用户使用手机登录获取手机验证码时，使用的手机号
	 * @param session
	 * @return
	 */
	public static String getMobileLoginMobileNumberFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_MOBILE_LOGIN_MOBILE_NUMBER_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (String) tmp; 
	}
	
	
	/**缓存用户绑定银行的绑定记录id
	 * @param session
	 * @param smCode
	 * @return
	 */
	public static boolean cacheUserBankCard(HttpSession session, UserBankCardDTO bankCard){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_BIND_BANK_CARD_SESSION_KEY, bankCard);
		return true;
	}
	
	/**从缓存中获取用户绑定银行的绑定记录id
	 * @param session
	 * @return
	 */
	public static UserBankCardDTO getUserBankCardFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_BIND_BANK_CARD_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (UserBankCardDTO) tmp; 
	}
	
	//********************************************缓存还款金额start***************************************************
	
	/**缓存用户需要还款的金额对象
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cachePaymentOrderToPay(HttpSession session, Map<String, Object> sessionPayMap){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_PAY_PAYMENTORDER_SESSION_KEY, sessionPayMap);
		return true;
	}
	
	/**从缓存中获取用户需要还款的金额对象
	 * @param session
	 * @return
	 */
	public static Map<String, Object>  getPaymentOrderToPayFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_PAY_PAYMENTORDER_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (Map<String, Object>)tmp; 
	}
	
	/**缓存用户需要还款的金额子项对象
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cachePaymentOrderItemToPay(HttpSession session, List<UserPaymentRecordItem> list){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_PAY_PAYMENTORDER_ITEM_SESSION_KEY, list);
		return true;
	}
	
	/**从缓存中获取用户需要还款的金额子项对象
	 * @param session
	 * @return
	 */
	public static List getPaymentOrderItemToPayFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_PAY_PAYMENTORDER_ITEM_SESSION_KEY);
		if(tmp == null){
			return new ArrayList();
		}
		return (List)tmp; 
	}
	
	
	
	
	
	
	/**缓存用户需要还款的金额对象token标记
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cachePaymentOrderToken(HttpSession session, String _obj){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_PAY_PAYMENTORDER_TOKEN_SESSION_KEY, _obj);
		return true;
	}
	
	/**从缓存中获取用户需要还款的金额对象token标记
	 * @param session
	 * @return
	 */
	public static String getPaymentOrderTokenFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_PAY_PAYMENTORDER_TOKEN_SESSION_KEY);
		if(tmp == null){
			return "";
		}
		
		return String.valueOf(tmp); 
	}
	
	/**缓存用户用户需要还款的金额在session中有效时间
	 * @param session
	 * @param userInfo
	 * @return
	 */
	public static boolean cachePaymentOrderToPayEffectiveTime(HttpSession session, Date date){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		session.setAttribute(USER_PAY_PAYMENTORDER_EFFECTIVE_TIME_SESSION_KEY, date);
		return true;
	}
	
	/**从缓存中获取用户需要还款的金额在session中有效时间
	 * @param session
	 * @return
	 */
	public static Date getPaymentOrderToPayEffectiveTimeFromCache(HttpSession session){
		if(session == null){
			throw new RuntimeException("session is null");
		}
		
		Object tmp = session.getAttribute(USER_PAY_PAYMENTORDER_EFFECTIVE_TIME_SESSION_KEY);
		if(tmp == null){
			return null;
		}
		
		return (Date) tmp; 
	}
	//********************************************缓存还款金额end***************************************************
}
