package com.tera.credit.constant;

/**
 * 业务常量
 * @author QYANZE
 *
 */
public class BusinessConstants {

	/**状态--有效*/
	public static final String BUSINESS_STATUS_Y_VALID = "1";
	/**状态--无效*/
	public static final String BUSINESS_STATUS_N_VALID = "0";
	
	/**借款人婚姻--未婚*/
	public static final String CREDIT_APP_MARRIAGE_N = "01";
	/**借款人婚姻--已婚*/
	public static final String CREDIT_APP_MARRIAGE_Y = "02";
	
	/**联系人类型--常用联系人*/
	public static final String CONTACT_TYPE_COMMON = "1";
	/**联系人类型--经营往来联系人*/
	public static final String CONTACT_TYPE_DEAL = "2";
	
	/**联系人关系--父母*/
	public static final String CONTACT_RELATION_PARENTS = "1";
	/**联系人关系--配偶*/
	public static final String CONTACT_RELATION_SPOUSE = "2";
	/**联系人关系--子女*/
	public static final String CONTACT_RELATION_CHILDREN = "3";
	/**联系人关系--亲属*/
	public static final String CONTACT_RELATION_RELATIVES = "4";
	/**联系人关系--朋友*/
	public static final String CONTACT_RELATION_FRIEND = "5";
	/**联系人关系--同事*/
	public static final String CONTACT_RELATION_WORKMATE = "6";
	/**联系人关系--同学*/
	public static final String CONTACT_RELATION_CLASSMATE = "7";
	/**联系人关系--本人*/
	public static final String CONTACT_RELATION_SELF = "8";
	/**联系人关系--其他*/
	public static final String CONTACT_RELATION_OTHER = "99";
	
	/**查重类型--单位名称*/
	public static final String CREDIT_REPEAT_TYPE_COM_NAME = "1";
	/**查重类型--单位地址*/          
	public static final String CREDIT_REPEAT_TYPE_COM_ADD = "2";
	/**查重类型--居住地址*/          
	public static final String CREDIT_REPEAT_TYPE_HOME_ADD = "3";
	/**查重类型--联系方式*/          
	public static final String CREDIT_REPEAT_TYPE_CONTACT = "4";
	/**查重类型--身份证*/            
	public static final String CREDIT_REPEAT_TYPE_ID_NO = "5";
	
	
	/**面审调查类型--本人手机*/
	public static final String INTERVIEW_TYPE_MOBILE = "01"; 
	/**面审调查类型--家庭固话*/
	public static final String INTERVIEW_TYPE_HOME_PHONE = "02"; 
	/**面审调查类型--单位电话*/
	public static final String INTERVIEW_TYPE_COM_PHONE = "03"; 
	/**面审调查类型--常用联系人*/
	public static final String INTERVIEW_TYPE_CONTACT_MAN = "04"; 
	/**面审调查类型--配偶手机*/
	public static final String INTERVIEW_TYPE_SPOUSE_MOBILE = "05";
	
	/**面审调查来源--网络查询*/
	public static final String INTERVIEW_SOURCE_INTERNET = "01";
	/**面审调查来源--114查号台*/
	public static final String INTERVIEW_SOURCE_114 = "02";
	/**面审调查来源--客户告知*/
	public static final String INTERVIEW_SOURCE_CUSTOMER = "03";
	/**面审调查来源--联系人告知*/
	public static final String INTERVIEW_SOURCE_CONTACT_MAN = "04";
	/**面审调查来源--信用报告*/
	public static final String INTERVIEW_SOURCE_CREDIT_REPORT = "05";
	
	/**面审调查状态--未调查*/
	public static final String INTERVIEW_SURVEY_STATE_NOT = "01";
	/**面审调查状态--调查中*/
	public static final String INTERVIEW_SURVEY_STATE_ING = "02";
	/**面审调查状态--调查成功*/
	public static final String INTERVIEW_SURVEY_STATE_SUCCESS = "03";
	/**面审调查状态--调查失败*/
	public static final String INTERVIEW_SURVEY_STATE_FAIL = "04";
	
	/**面审调查标志--正常*/
	public static final String INTERVIEW_SURVEY_FLAG_NORMAL = "01";
	/**面审调查标志--异常*/
	public static final String INTERVIEW_SURVEY_FLAG_EXCEPTION = "02";
	
	/**查重类型 -- 单位名称、单位地址、居住地址*/
	public static final String REPEAT_TYPE_NAME_OR_ADDRESS = "1";
	/**联系方式查重类型 -- 借款人对借款人*/
	public static final String REPEAT_TYPE_CONTACT_LOAN_TO_LOAN = "2";
	/**联系方式查重类型 -- 借款人对借款人配偶*/
	public static final String REPEAT_TYPE_CONTACT_LOAN_TO_SPOUSE = "3";
	/**联系方式查重类型 -- 借款人对联系人*/
	public static final String REPEAT_TYPE_CONTACT_LOAN_TO_LINKMAN = "4";
	/**联系方式查重类型 -- 借款人对面审添加*/
	public static final String REPEAT_TYPE_CONTACT_LOAN_TO_INTERVIEW = "5";
	/**联系方式查重类型 -- 联系人对借款人*/
	public static final String REPEAT_TYPE_CONTACT_LINKMAN_TO_LOAN = "6";
	/**联系方式查重类型 -- 联系人对联系人*/
	public static final String REPEAT_TYPE_CONTACT_LINKMAN_TO_LINKMAN = "7";
	/**联系方式查重类型 -- 联系人对面审添加*/
	public static final String REPEAT_TYPE_CONTACT_LINKMAN_TO_INTERVIEW = "8";
	/**查重类型 -- 身份证*/
	public static final String REPEAT_TYPE_CONTACT_IDCARD = "9";
	
	/**查重身份证比较类型--与借款人身份证比较*/
	public static final String REPEAT_IDNO_TYPE_BORROWER = "0";
	/**查重身份证比较类型--与借款人配偶身份证比较*/
	public static final String REPEAT_IDNO_TYPE_SPOUSE = "1";
	
	/**信审欺诈状态--未解决*/
	public static final String CREDIT_FRAUD_STATE_NOT_OK = "1";
	/**信审欺诈状态--已解决*/
	public static final String CREDIT_FRAUD_STATE_OK = "2";
}
