package com.hikootech.mqcash.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.CreditCfgKeyConstants;
import com.hikootech.mqcash.constants.CreditCodeConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.TelecomConstants;
import com.hikootech.mqcash.dao.UserCreditDataDAO;
import com.hikootech.mqcash.dao.UserQhzxCreditDAO;
import com.hikootech.mqcash.po.BlackList;
import com.hikootech.mqcash.po.BrApplyLoan;
import com.hikootech.mqcash.po.PartnerUserOrder;
import com.hikootech.mqcash.po.UserBrRequest;
import com.hikootech.mqcash.po.UserBrSpeciallistC;
import com.hikootech.mqcash.po.UserCreditInfo;
import com.hikootech.mqcash.po.UserCreditModelRecord;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.po.UserCreditRequestRecord;
import com.hikootech.mqcash.po.UserJyzxRecordItem;
import com.hikootech.mqcash.po.UserMobileCreditDetailInfo;
import com.hikootech.mqcash.po.UserMobileCreditInfo;
import com.hikootech.mqcash.po.UserQhzxBlackList;
import com.hikootech.mqcash.po.UserQhzxCredit;
import com.hikootech.mqcash.po.UserQhzxLoan;
import com.hikootech.mqcash.po.UserQhzxVerify;
import com.hikootech.mqcash.po.WhiteList;
import com.hikootech.mqcash.qhzx.CommonUtil;
import com.hikootech.mqcash.service.BrzxService;
import com.hikootech.mqcash.service.ConfigCreditKvService;
import com.hikootech.mqcash.service.ConfigKvService;
import com.hikootech.mqcash.service.QhzxService;
import com.hikootech.mqcash.service.SysAlarmService;
import com.hikootech.mqcash.service.UserCreditDataService;
import com.hikootech.mqcash.service.UserQhzxVerifyService;
import com.hikootech.mqcash.util.BRUtils;
import com.hikootech.mqcash.util.CheckNameUtils;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DESUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GbossXmlUtil;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.HkEncryptUtils;
import com.hikootech.mqcash.util.HttpClientUtil;
import com.hikootech.mqcash.util.IDCard;
import com.hikootech.mqcash.util.UUIDTools;
import com.hikootech.mqcash.util.ValidateAPIPlatParamsTools;

import cn.id5.gboss.businesses.validator.service.app.QueryValidatorServices;
import cn.id5.gboss.businesses.validator.service.app.QueryValidatorServicesProxy;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("userCreditDataService")
public class UserCreditDataServiceImpl implements UserCreditDataService {
	
	private static Logger logger = LoggerFactory.getLogger(UserCreditDataServiceImpl.class);
	
	
	@Autowired
	private UserCreditDataDAO userCreditDataDAO;
	@Autowired
	private ConfigCreditKvService configCreditKvService;
	@Autowired
	private QhzxService qhzxService;
	@Autowired
	private SysAlarmService sysAlarmService;
	@Autowired
	private ConfigKvService configKvService;
	@Autowired
	private UserQhzxVerifyService userQhzxVerifyService;
	@Autowired
	private BrzxService brzxService;
	@Autowired
	private UserQhzxCreditDAO userQhzxCreditDao;
	
	/** 
	* @Title queryCreditResult 
	* @Description TODO(将国政通数据传给网厅，网厅通过企信查询个人征信，返回用户是否通过征信) 
	* @param 参数列表
	* @param creditRecord (征信记录表对象)
	* @param partnerUserOrder(用户在电渠合作伙伴的订单相关消息表)
	* @param crmMap
	* @return
	* @throws Exception 设定文件 
	* @return Map<String,String>	返回类型 
	* @throws 
	*/
	public Map<String, String>  returnCreditResult(UserCreditRecord creditRecord ,PartnerUserOrder partnerUserOrder,Map<String, String> crmMap) {
		logger.info("调用征信数据开始。");
		Map<String, String> returnMap = new HashMap<String,String>();
		
		//从memcached中取出数据
		Map<String, String> meMap = null;
		try {
			meMap = configCreditKvService.getAllCreditKv();
		} catch (Exception e3) {
			throw new RuntimeException("调用核心征信接口，获取征信配置数据异常");
		}
		
		if (meMap == null) {
			throw new RuntimeException("调用核心征信接口，获取征信配置数据为空");
		}
		
		
		//初始化征信结果记录表信息
		try {
			Integer isPass = StatusConstants.CREDIT_IS_NOT_PASS;// 1
			String reason = CreditCodeConstants.CREDIT_RECORD_INIT.toString(); // 征信不通过
			
			creditRecord.setInfoId(crmMap.get("infoId"));
			creditRecord.setIsPass(isPass);
			creditRecord.setReason(reason);
			creditRecord.setCreateTime(new Date());
			creditRecord.setUpdateTime(new Date());
			userCreditDataDAO.insertUserCreditRecord(creditRecord);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用征信接口，初始化征信数据，插入征信记录表异常。infoId:" + crmMap.get("infoId") +  ",idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName(),e);
			throw new RuntimeException("调用征信接口，初始化征信数据，插入征信记录表异常。idCard:" + partnerUserOrder.getIdCard());	
		}
		

		//白名单
		String _WLOpen = (String) (meMap.get(CreditCfgKeyConstants.WL_CR_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_WLOpen)) {
			logger.info("白名单-->调用模快开关开启。");
			try {
				boolean isWhiteList = this.queryWhiteList(partnerUserOrder.getIdCard(), partnerUserOrder.getName(),partnerUserOrder.getContactPhone(), partnerUserOrder.getPartnerId());
				//征信结果判断开关
				String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.WL_CR_WHITELIST_ISOPEN));
				if (CreditCfgKeyConstants.OPEN.equals(_CROpen)) { //开关生效,结果生效
					if (isWhiteList) {//是秒趣白名单
						logger.info("调用征信接口，白名单结果开关生效，匹配秒趣白名单,征信成功。idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());
						updateCR(creditRecord, StatusConstants.CREDIT_IS_PASS,CreditCodeConstants.CREDIT_WL_RECORD_PASS);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_HD_WHITE, CreditCodeConstants.CREDIT_TYPE_HD_WHITE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_WL_RECORD_PASS);
						returnMap.put("resultCode",TelecomConstants.CRE_SUCCESS);
						returnMap.put("desc", TelecomConstants.CRE_SUCCESS_DESC);
						return returnMap; 
					}else {
						logger.info("调用征信接口，白名单结果开关生效，未匹配秒趣白名单，结果失效。");
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_HD_WHITE, CreditCodeConstants.CREDIT_TYPE_HD_WHITE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_WL_RECORD_PASS);
					} 
				}else{
					logger.info("调用征信接口，白名单结果开关失效，未命中秒趣白名单。idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());
					this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_HD_WHITE, CreditCodeConstants.CREDIT_TYPE_HD_NOWHITE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_UNWL_RECORD_PASS);
				}
				
				
			} catch (Exception e) {
				logger.error("调用白名单--> 数据异常: infoId:" + crmMap.get("infoId") +  ",idCard："+ partnerUserOrder.getName() + ",contactPhone:" + partnerUserOrder.getContactPhone() +  ",userName:" + partnerUserOrder.getName() ,e);
			}
		}else {
			logger.info("白名单-->开关关闭。");
		}
		
		//检查姓名合法性
		try {
			boolean isCreditName = this.isCreditByName(partnerUserOrder.getName());
			if (isCreditName) {
				updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_NAME_RECORD_UNPASS);
				this.insertCreditModelRecord(creditRecord.getCreditId(), "10", CreditCodeConstants.CREDIT_NAME, CreditCodeConstants.CREDIT_UNEFFECT_NAME, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_NAME_RECORD_UNPASS);
				returnMap.put("resultCode",TelecomConstants.CRE_NAME_CODE);
				returnMap.put("desc", TelecomConstants.CRE_NAME_CODE_DESC);
				return returnMap;

			}
			this.insertCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_NAME, CreditCodeConstants.CREDIT_EFFECT_NAME, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_NAME_RECORD_PASS);
		} catch (Exception e) {
			logger.error("查询姓名判断异常：infoId:" + crmMap.get("infoId") +  ",身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName() , e);
		}
		//校验该用户之前是否已经命中了前海或者好贷黑名单
		UserCreditRecord isBlackUser = this.isBlackUser(partnerUserOrder.getIdCard(),partnerUserOrder.getName());
		if (isBlackUser != null) {
			logger.info(partnerUserOrder.getName() + "," + partnerUserOrder.getIdCard()  + ",在" + DateUtil.formatDate(isBlackUser.getCreateTime(), DateUtil.FORMAT_SS) + "申请秒趣分期");
			String blackUserResult = "" ;
			String creditModelType = "" ;
			switch (isBlackUser.getReason()) {
			case "205320":
				blackUserResult = CreditCodeConstants.CREDIT_HD_RECORD_BL_UNPASS;
				creditModelType = CreditCodeConstants.CREDIT_HD_BLACKLIST_REFUSE;
				logger.info("命中好贷黑名单");
				break;
			case "201020":
				blackUserResult = CreditCodeConstants.CREDIT_QH_RECORD_BL_UNPASS;
				creditModelType = CreditCodeConstants.CREDIT_QH_BLACKLIST_REFUSE;
				logger.info("命中前海黑名单");
				break;
			case "213020":
				blackUserResult = CreditCodeConstants.CREDIT_BR_RECORD_BL_UNPASS;
				creditModelType = CreditCodeConstants.CREDIT_BR_SL_BLACKLIST_REFUSE;
				logger.info("命中百融黑名单");
				
				break;
			case "204020":
				blackUserResult = CreditCodeConstants.CREDIT_JY_RECORD_BL_UNPASS;
				creditModelType = CreditCodeConstants.CREDIT_JY_BLACKLIST_REFUSE;
				logger.info("命中91黑名单");
				break;
			default:
				break;
			}
			updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,blackUserResult);
			this.insertCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BLACKLIST_REFUSE, creditModelType, StatusConstants.CREDIT_CHECKING_RESULT_FAILED,blackUserResult);
			returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
			returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
			return returnMap; 
		}else{
			logger.info(partnerUserOrder.getName() + "," + partnerUserOrder.getIdCard() + "未命中黑名单性质拒贷规则。");
			this.insertCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BLACKLIST_REFUSE, CreditCodeConstants.CREDIT_QH_BLACKLIST_UNCHECKED, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_RECORD_BLACK_PASS.toString());

		}
		
		//黑名单开关
		String _BLOpen = (String) (meMap.get(CreditCfgKeyConstants.BL_CR_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_BLOpen)) {//0-关闭；1-开启
			logger.info("黑名单-->调用模快开关开启。");
			try {
				
				BlackList blackList = this.queryBlackList(partnerUserOrder.getIdCard(), partnerUserOrder.getContactPhone());
				//征信结果判断开关
				String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.BL_CR_BLACKLIST_ISOPEN));
				if (CreditCfgKeyConstants.OPEN.equals(_CROpen)) {//开关生效,结果生效
					if (blackList != null) {
						logger.info("调用征信接口，黑名单开关生效，命中秒趣黑名单。infoId:" + crmMap.get("infoId") +  ",idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());
						String blackType = "";
						if (StatusConstants.BLACK_MATCH_IDCARD_TYPE.toString().equals(blackList.getMatchType())) {//黑名单身份证
							blackType = CreditCodeConstants.CREDIT_TYPE_HD_BLACK_IDCARD;
						}else  if (StatusConstants.BLACK_MATCH_TELPHONE_TYPE.toString().equals(blackList.getMatchType())) {//黑名单手机号
							blackType = CreditCodeConstants.CREDIT_TYPE_HD_BLACK_PHONE;
						}
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BL_RECORD_UNPASS);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_HD_BLACK, blackType, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BL_RECORD_UNPASS.toString());
						returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
						returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
						return returnMap; 
						
					}else {
						logger.info("调用征信接口，黑名单开关生效，未命中黑名单。infoId:" + crmMap.get("infoId") +  ",idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());
					}
					
				}else {
					logger.info("调用征信接口，黑名单开关失效，未匹配黑名单信息可以继续征信。idCard:" + partnerUserOrder.getIdCard() );
				}
				
				this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_HD_BLACK, CreditCodeConstants.CREDIT_TYPE_HD_NOBLACK, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_BL_RECORD_PASS);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("调用黑名单--> 返回数据异常:infoId:" + crmMap.get("infoId") +  ", idCard："+ partnerUserOrder.getName() + ",contactPhone:" + partnerUserOrder.getContactPhone() +  ",userName:" + partnerUserOrder.getName() ,e);
			}
		}else {
			logger.info("调用黑名单模快开关关闭。");
		}
				
		
		//请求年龄开关
		try {
			
			String _AgeOpen = (String) (meMap.get(CreditCfgKeyConstants.AGE_MODEL_ISOPEN));
			if (CreditCfgKeyConstants.OPEN.equals(_AgeOpen)) {
				logger.info("调用年龄判断-->调用年龄判断开关开启。");
				
				String configAgeSection = (String) (meMap.get(CreditCfgKeyConstants.BEFORE_GBOSS_AGE_SECTION));
				logger.info("查询出配置可以调用征信系统的年龄段为:" + configAgeSection);
				//检查年龄合法性
				boolean isCreditAge = this.isCreditByAge(partnerUserOrder.getIdCard(),configAgeSection);
				//征信结果判断开关
				String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.AGE_CR_BLACKLIST_ISOPEN));
				if (CreditCfgKeyConstants.OPEN.equals(_CROpen)) { //开关生效,结果生效
					logger.info("年龄结果开关生效");
					if (isCreditAge) {
						logger.info("年龄结果开关生效,年龄未通过,身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName());
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_AGE_RECORD_UNPASS);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_AGE, CreditCodeConstants.CREDIT_TYPE_AGE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_AGE_RECORD_UNPASS);
						returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
						returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
						return returnMap;
					}else{
						logger.info("年龄结果开关生效,年龄通过。");
					}
				}else {
					logger.info("年龄结果失效。年龄判断通过。");
				}
				//增加征信模快
				this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_AGE, CreditCodeConstants.CREDIT_TYPE_AGE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_AGE_RECORD_PASS);

			}else {
				logger.info("调用年龄判断-->开关关闭。");
			}
		} catch (Exception e) {
			logger.error("调用年龄判断异常：infoId:" + crmMap.get("infoId") +  ",身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName() , e);
			
		}
		
		//好信一鉴通
		try {
			String _verifyOpen = (String) (meMap.get(CreditCfgKeyConstants.VERIFY_MODEL_ISOPEN));
			if (CreditCfgKeyConstants.OPEN.equals(_verifyOpen)) {//调用一鉴通开关打开
				logger.info("调用前海一致开关打开。");
				
				Map<String, Object> userQhzxVerifyMap =qhzxService.reqQhzxVerify(partnerUserOrder.getIdCard(), partnerUserOrder.getName(), partnerUserOrder.getContactPhone(), null, crmMap.get("infoId"));
				
				
				//好信一鉴通结果生效开关 
				String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.VERIFY_CR_BLACKLIST_ISOPEN));
				if (CreditCfgKeyConstants.OPEN.equals(_CROpen)) { //开
					
					//异常情况或参数为空情况
					if (EntityUtils.isEmpty(userQhzxVerifyMap.get(ResponseConstants.RESULT_CODE))|| EntityUtils.isEmpty(userQhzxVerifyMap.get(ResponseConstants.DATA)) 
							|| !ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals((String)userQhzxVerifyMap.get(ResponseConstants.RESULT_CODE))) {
						logger.info("前海一致性校验，处理前海时发生问题，问题描述：" + userQhzxVerifyMap.get(ResponseConstants.RESULT_DESC)+ ",返回码：" + userQhzxVerifyMap.get(ResponseConstants.RESULT_CODE) + ",DATA:" + userQhzxVerifyMap.get(ResponseConstants.DATA));
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_HXYJTEXP_ERROR);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_HXYJTEXP_ERROR);
						String alarmContent = "前海一致性校验，处理前海时发生问题，问题描述：" + userQhzxVerifyMap.get(ResponseConstants.RESULT_DESC);
						return sysAlarmService.exceptionAlarm(alarmContent);
					}
					
					//前海正常的处理
					UserQhzxVerify userQhzxVerify = CommonUtil.parseJSON2Bean(userQhzxVerifyMap.get(ResponseConstants.DATA).toString(), UserQhzxVerify.class);
					
					
					if (EntityUtils.isEmpty(userQhzxVerify.getIsRealIdentity())) {
						String result = userQhzxVerify == null?null: userQhzxVerify.getIsRealIdentity();
						logger.info("前海一致性校验未通过,校验结果为["+ result +"],身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName());
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_SAME_UNPASS);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_SAME_UNPASS);
						returnMap.put("resultCode",TelecomConstants.CRE_QH_YJT_CODE);
						returnMap.put("desc", TelecomConstants.CRE_QH_YJT_CODE_DESC);
						return returnMap;
					}
					
					//判断是否为真是身份
					if(StatusConstants.QHZX_IDNO_NAME_NOT_MATCH_VAL.equals(userQhzxVerify.getIsRealIdentity())){
						logger.info("前海一致性校验明确返回未通过，征信失败。身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName());
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS);
						returnMap.put("resultCode",TelecomConstants.CRE_QH_YJT_CODE);
						returnMap.put("desc", TelecomConstants.CRE_QH_YJT_CODE_DESC);
						return returnMap;
					}
					
					if(StatusConstants.QHZX_IDNO_NAME_MATCH_VAL.equals(userQhzxVerify.getIsRealIdentity())){
						logger.info("前海一致性校验通过,继续向下执行");
						
					}else{
						logger.info("前海一致性校验未通过,校验结果异常,身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName());
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_HXYJT_ERROR);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_HXYJT_ERROR);
						returnMap.put("resultCode",TelecomConstants.API_SYSTEM_EXCEPTION);
						returnMap.put("desc", TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
						return returnMap;
					}
				}else{
					logger.info("前海一致性结果生效开关关闭。");
				}
				this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_SAME_PASS);
			}else{
				logger.info("调用一鉴通开关关闭。");
			}
			
		} catch (Exception e) {
			 logger.error("前海一致性校验发生错误，身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName(),e);
			 updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_HXYJT_ERROR);
			 String alarmContent = "前海一致性校验发生错误，身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName();
			 return sysAlarmService.exceptionAlarm(alarmContent);
		}
		
		
		//调用百融贷特殊名单接口
		String _BROpen = (String) (meMap.get(CreditCfgKeyConstants.BR_MODEL_SPECIAL_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_BROpen)) {
			logger.info("调用百融贷特殊名单核查征信-->调用开关开启。");
			try {
				UserBrRequest brRequestPo = new UserBrRequest();
				brRequestPo.setIdCard(partnerUserOrder.getIdCard());
				brRequestPo.setName(partnerUserOrder.getName());
				brRequestPo.setMobile(partnerUserOrder.getContactPhone());
				brRequestPo.setTotalInfoId(crmMap.get("infoId"));
				brRequestPo.setProductName(CommonConstants.BR_MEAL_SPECIALLIST_C);
				
			    Map<String, Object> paramMap = new HashMap<>();
		        paramMap.put("UserBrRequest", brRequestPo);
		        
		        Map<String, Object> brSpeciallyListMap = (Map<String, Object>) brzxService.requestBR(paramMap);
		        logger.info("brSpeciallyListMap" + JSONArray.fromObject(brSpeciallyListMap).toString());
		        
		    	//百融特殊名单结果生效开关
				String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.BR_CR_SPECIALLIST_ISOPEN));
				if (CreditCfgKeyConstants.OPEN.equals(_CROpen)) { //开
					logger.info("百融特殊名单结果生效开关开启");
					 if (brSpeciallyListMap.get(ResponseConstants.RETURN_CODE).equals(ResponseConstants.SUCCESS)) {
				        		List<UserBrSpeciallistC> specialIdCard = (List<UserBrSpeciallistC>)brSpeciallyListMap.get("portrait");
				        		if (BRUtils.speciallistCreditResult(specialIdCard.get(0))) {
									logger.info( partnerUserOrder.getName() + "命中百融贷特殊名单");
									updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_SL_UNPASS);
									this.insertCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_SPECIAL_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_SL_UNPASS);
									returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
									returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
									return returnMap;
								}else{
									logger.info( partnerUserOrder.getName() + "未命中百融贷特殊名单，征信通过");
								}
						}else{
							logger.info("调用百融特殊名单出现错误，错误码：" + brSpeciallyListMap.get(ResponseConstants.RETURN_CODE) + ",错误描述:" + brSpeciallyListMap.get(ResponseConstants.RETURN_DESC));
							String alarmContent = "调用百融特殊名单核查接口出错,返回错误代码：" + ",姓名：" + partnerUserOrder.getName() + ",身份证号：" + partnerUserOrder.getIdCard();
							sysAlarmService.alarm(alarmContent);
						}
				}else{
					logger.info("百融特殊名单结果生效开关关闭");
				}
		       
				
			} catch (Exception e) {
				 logger.error("前调用百融贷特殊名单核查征信发生错误，身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName(),e);
				 updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_SPECIALEXP_ERROR);
				 String alarmContent = "前调用百融贷特殊名单核查征信发生错误，身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName();
				 sysAlarmService.alarm(alarmContent);
			}
		}else{
			logger.info("调用百融贷特殊名单核查征信-->调用开关关闭。");
		}
		
		//请求前海征信黑名单开关
		String _QHOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_MODEL_BLACK_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_QHOpen)) {
			logger.info("调用前海黑名单征信-->调用开关开启。");
			try {
				Map<String, Object>  userQhzxMsc8004Map = qhzxService.requestQhBlackList(partnerUserOrder.getIdCard(), partnerUserOrder.getName(),null,crmMap.get("infoId"));
		
				//前海严重等级不为空，则命中黑名单
				//征信结果判断开关
				String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.QH_CR_BLACKLIST_ISOPEN));
				if (CreditCfgKeyConstants.OPEN.equals(_CROpen)) { //开关生效,结果生效	
					
					if (EntityUtils.isEmpty(userQhzxMsc8004Map.get(ResponseConstants.RESULT_CODE)) 
							|| !ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals((String)userQhzxMsc8004Map.get(ResponseConstants.RESULT_CODE))) {
						logger.info("处理前海黑名单接口时发生问题，问题描述：" + userQhzxMsc8004Map.get(ResponseConstants.RESULT_DESC) + ",返回码：" + userQhzxMsc8004Map.get(ResponseConstants.RESULT_CODE));
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_BLACKEXP_ERROR);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_BLACKEXP_ERROR);
						String alarmContent = "处理前海黑名单接口时发生问题，问题描述：" + userQhzxMsc8004Map.get(ResponseConstants.RESULT_DESC);
						return sysAlarmService.exceptionAlarm(alarmContent);
					}
					
					if (EntityUtils.isNotEmpty(userQhzxMsc8004Map.get(ResponseConstants.DATA))) {

						UserQhzxBlackList userQhzxMsc8004 = CommonUtil.parseJSON2Bean(userQhzxMsc8004Map.get(ResponseConstants.DATA).toString(), UserQhzxBlackList.class);

						logger.info("前海征信结果生效开关开启，结果生效");
						try {
							if (qhzxService.queryIsQhzxBlackList(userQhzxMsc8004)) {
								String result = (String) (meMap.get(CreditCfgKeyConstants.QH_BLACKLIST_CONFIG));
								if ("0".equals(result)) {
									logger.info("前海黑名单征信结果开关生效,,idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName() + ",配置为拒贷。");
									updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_BLACK_UNPASS);
									this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_BLACK_UNPASS);
									returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
									returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
									logger.info("前海征信结果开关打开，结果生效，命中黑名单。");
									return returnMap;
										
								}else {
									logger.info("前海黑名单征信结果开关生效,通过配置值：" + result + ",通过。" );
								}
								
							}else {
								logger.info("前海黑名单征信结果开关生效，未命中前海黑名单,idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());
							}
						} catch (Exception e) {
							throw new RuntimeException("前海黑名单，有数据，但数据异常",e);
						}
						
					}else{
						logger.info("前海黑名单征信结果开关生效，未查询到任何黑名单数据，未命中前海黑名单,idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());

					}

				}else {
					logger.info("前海黑名单征信结果开关失效，未命中前海黑名单，idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());
				}
				
				this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_BLACK_PASS);
				
			} catch (Exception e1) {
				logger.error("调用前海征信黑名单接口（8004）异常：infoId:" + crmMap.get("infoId") +  ",身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName() , e1);
				updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_BLACKE_RROR);
				//插入告警表
				String alarmContent = "调用前海征信黑名单接口（8004）异常,idCard："+  partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName() ;
				return sysAlarmService.exceptionAlarm(alarmContent);
			}
		
		}else{
			logger.info("调用前海征信黑名单-->开关关闭。");
		}
		
		//调用百融贷多次申请核查接口
		String _alOpen = (String) (meMap.get(CreditCfgKeyConstants.BR_MODEL_APPLYLOAN_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_alOpen)) {
			logger.info("调用百融贷多次申请核查核查征信-->调用开关开启。");
			try {
				UserBrRequest brRequestPo = new UserBrRequest();
				brRequestPo.setIdCard(partnerUserOrder.getIdCard());
				brRequestPo.setName(partnerUserOrder.getName());
				brRequestPo.setMobile(partnerUserOrder.getContactPhone());
				brRequestPo.setTotalInfoId(crmMap.get("infoId"));
				brRequestPo.setProductName(CommonConstants.BR_MEAL_APPLYLOAN);
				
		        Map<String, Object> paramMap = new HashMap<>();
		        paramMap.put("UserBrRequest", brRequestPo);
		        
		        Map<String, Object> brApplyLoanMap = (Map<String, Object>) brzxService.requestBR(paramMap);
		        logger.info("brApplyLoanMap" + JSONArray.fromObject(brApplyLoanMap).toString());
		       
		    	//百融多次申请核查结果生效开关
				String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.BR_CR_APPLYLOAN_ISOPEN));
				if (CreditCfgKeyConstants.OPEN.equals(_CROpen)) { //开
					logger.info("百融多次申请核查结果生效开关开启");
					if (brApplyLoanMap.get(ResponseConstants.RETURN_CODE).equals(ResponseConstants.SUCCESS)) {
			        		BrApplyLoan brApplyLoan = (BrApplyLoan)brApplyLoanMap.get("portrait");
			        		if ("00".equals(brApplyLoan.getCode())) {
								logger.info( partnerUserOrder.getName() + "命中百融多次申请核查");
								
								//近3个月在银行机构申请次数生效开关
								String m3Banknum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3BANKNUM_ISOPEN_CONFIG));
								String m3IdBankSelfnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3IDBANKSELFNUM_DATA_CONFIG));
								String m3IdBankAllnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3IDBANKALLNUM_DATA_CONFIG));
								String m3IdBankOrgnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3IDBANKORGNUM_DATA_CONFIG));
								
								if (CreditCfgKeyConstants.OPEN.equals(m3Banknum)) {
									logger.info("百融多次申请近3个月在银行机构申清次数生效开关开启");
									
									logger.info("身份证在银行-本机构申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM3IdBankSelfnum(),m3IdBankSelfnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									
									logger.info("身份证在银行-总申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM3IdBankAllnum(),m3IdBankAllnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									logger.info("身份证在银行-申清过的机构数。");
									if (applyLoanCreditCompare(brApplyLoan.getM3IdBankOrgnum(),m3IdBankOrgnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
								}
								//近3个月在非银行机构申请次数生效开关
								String m3NoBanknum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3NOBANKNUM_ISOPEN_CONFIG));
								String m3IdNoBankSelfnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3IDNOBANKSELFNUM_DATA_CONFIG));
								String m3IdNoBankAllnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3IDNOBANKALLNUM_DATA_CONFIG));
								String m3IdNoBankOrgnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3IDNOBANKORGNUM_DATA_CONFIG));
								
								if (CreditCfgKeyConstants.OPEN.equals(m3NoBanknum)) {
									logger.info("百融多次申请近3个月在非银机构申清次数生效开关开启");
									
									logger.info("身份证在非银行-本机构申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM3IdNotbankSelfnum(),m3IdNoBankSelfnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									
									logger.info("身份证在非银行-总申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM3IdNotbankAllnum(),m3IdNoBankAllnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									logger.info("身份证在非银行-申清过的机构数。");
									if (applyLoanCreditCompare(brApplyLoan.getM3IdNotbankOrgnum(),m3IdNoBankOrgnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
								}
								
								//近6个月在银行机构申请次数生效开关
								String m6banknum = (String) (meMap.get(CreditCfgKeyConstants.BR_M6BANKNUM_ISOPEN_CONFIG));
								String m6IdBankSelfnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M6IDBANKSELFNUM_DATA_CONFIG));
								String m6IdBankAllnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M6IDBANKALLNUM_DATA_CONFIG));
								String m6IdBankOrgnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M6IDBANKORGNUM_DATA_CONFIG));
								
								if (CreditCfgKeyConstants.OPEN.equals(m6banknum)) {
									logger.info("百融多次申请近6个月在银行机构申清次数生效开关开启");
									
									logger.info("身份证在银行-本机构申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM6IdBankSelfnum(),m6IdBankSelfnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									
									logger.info("身份证在银行-总申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM6IdBankAllnum(),m6IdBankAllnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									logger.info("身份证在银行-申清过的机构数。");
									if (applyLoanCreditCompare(brApplyLoan.getM6IdBankOrgnum(),m6IdBankOrgnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
								}
								
								//近6个月在非银行机构申请次数生效开关
								String m6NoBanknum = (String) (meMap.get(CreditCfgKeyConstants.BR_M6NOBANKNUM_ISOPEN_CONFIG));
								String m6IdNoBankSelfnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M6IDNOBANKSELFNUM_DATA_CONFIG));
								String m6IdNoBankAllnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M6IDNOBANKALLNUM_DATA_CONFIG));
								String m6IdNoBankOrgnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M6IDNOBANKORGNUM_DATA_CONFIG));
								
								if (CreditCfgKeyConstants.OPEN.equals(m6NoBanknum)) {
									logger.info("百融多次申请近6个月在非银行机构申清次数生效开关开启");
									
									logger.info("身份证在非银行-本机构申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM6IdNotbankSelfnum(),m6IdNoBankSelfnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									
									logger.info("身份证在非银行-总申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM6IdNotbankAllnum(),m6IdNoBankAllnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									logger.info("身份证在非银行-申清过的机构数。");
									if (applyLoanCreditCompare(brApplyLoan.getM6IdNotbankOrgnum(),m6IdNoBankOrgnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
								}
								
								//近12个月在银行机构申请次数生效开关
								String m12banknum = (String) (meMap.get(CreditCfgKeyConstants.BR_M12BANKNUM_ISOPEN_CONFIG));
								String m12IdBankSelfnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M12IDBANKSELFNUM_DATA_CONFIG));
								String m12IdBankAllnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M12IDBANKALLNUM_DATA_CONFIG));
								String m12IdBankOrgnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M12IDBANKORGNUM_DATA_CONFIG));
								
								if (CreditCfgKeyConstants.OPEN.equals(m12banknum)) {
									logger.info("百融多次申请近12个月在银行机构申清次数生效开关开启");
									
									logger.info("身份证在银行-本机构申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM12IdBankSelfnum(),m12IdBankSelfnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									
									logger.info("身份证在银行-总申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM12IdBankAllnum(),m12IdBankAllnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									logger.info("身份证在银行-申清过的机构数。");
									if (applyLoanCreditCompare(brApplyLoan.getM12IdBankOrgnum(),m12IdBankOrgnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
								}
								
								//近12个月在非银行机构申请次数生效开关
								String m12NoBanknum = (String) (meMap.get(CreditCfgKeyConstants.BR_M12NOBANKNUM_ISOPEN_CONFIG));
								String m12IdNoBankSelfnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M12IDNOBANKSELFNUM_DATA_CONFIG));
								String m12IdNoBankAllnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M12IDNOBANKALLNUM_DATA_CONFIG));
								String m12IdNoBankOrgnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M12IDNOBANKORGNUM_DATA_CONFIG));
								
								if (CreditCfgKeyConstants.OPEN.equals(m12NoBanknum)) {
									logger.info("百融多次申请近12个月在非银行机构申清次数生效开关开启");
									
									logger.info("身份证在非银行-本机构申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM12IdNotbankSelfnum(),m12IdNoBankSelfnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									
									logger.info("身份证在非银行-总申清次数");
									if (applyLoanCreditCompare(brApplyLoan.getM12IdNotbankAllnum(),m12IdNoBankAllnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
									logger.info("身份证在非银行-申清过的机构数。");
									if (applyLoanCreditCompare(brApplyLoan.getM12IdNotbankOrgnum(),m12IdNoBankOrgnum)) {
										return applyLoanCreditResult(creditRecord,_CROpen);
									}
								}
		
							}else{
								logger.info("百融多次申请核查无数据返回。");
							}
				  }else{
					  logger.error("调用百融多次申请核查接口，返回代码Code:" + partnerUserOrder.getName()  + ",身份证号:" + partnerUserOrder.getIdCard());
						String alarmContent = "调用百融多次申请核查接口出错,返回错误代码：" + partnerUserOrder.getName()  + ",身份证号:" + partnerUserOrder.getIdCard();
						sysAlarmService.alarm(alarmContent);		
				  }
				}else{
					logger.info("百融多次申请核查结果生效开关关闭");
				}
				
			} catch (Exception e) {
				 logger.error("调用百融贷多次申请核查接口征信发生错误，身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName(),e);
				 updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR);
				 String alarmContent = "调用百融贷多次申请核查接发生错误，身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName();
				 sysAlarmService.alarm(alarmContent);
			}
		}else{
			logger.info("调用百融多次申请核查征信接口-->调用开关关闭。");
		}
		
		
		//请求前海征信常贷客开关
		String _loanOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_MODEL_LOAN_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_loanOpen)) {
			logger.info("调用前海常贷客征信-->调用开关开启。");
			try {
				Map<String, Object>  userQhzxMsc8037Map = qhzxService.requestQhLoanList(partnerUserOrder.getIdCard(), partnerUserOrder.getName(),null,crmMap.get("infoId"));
		
				//征信结果判断开关
				String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.QH_CR_LOAN_ISOPEN));
				if (CreditCfgKeyConstants.OPEN.equals(_CROpen)) { //开关生效,结果生效	
					logger.info("前海征信前海常贷客接口，征信结果开关生效");
					if (EntityUtils.isEmpty(userQhzxMsc8037Map.get(ResponseConstants.RESULT_CODE)) 
							|| !ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals((String)userQhzxMsc8037Map.get(ResponseConstants.RESULT_CODE))) {
						logger.info("处理前海常贷客接口时发生问题，问题描述：" + userQhzxMsc8037Map.get(ResponseConstants.RESULT_DESC) + ",返回码：" + userQhzxMsc8037Map.get(ResponseConstants.RESULT_CODE));
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR);
						String alarmContent = "处理前海常贷客接口时发生问题，问题描述：" + userQhzxMsc8037Map.get(ResponseConstants.RESULT_DESC);
						return sysAlarmService.exceptionAlarm(alarmContent);
					}
					
					if (EntityUtils.isNotEmpty(userQhzxMsc8037Map.get(ResponseConstants.DATA))) {

						String bakIsOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_BAK_ISOPEN_CONFIG));
						String bakData = (String) (meMap.get(CreditCfgKeyConstants.QH_BAK_DATA_CONFIG));
						String mclIsOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_MCL_ISOPEN_CONFIG));
						String mclData = (String) (meMap.get(CreditCfgKeyConstants.QH_MCL_DATA_CONFIG));
						String p2pIsOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_P2P_ISOPEN_CONFIG));
						String p2pData = (String) (meMap.get(CreditCfgKeyConstants.QH_P2P_DATA_CONFIG));
						String asmIsOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_ASM_ISOPEN_CONFIG));
						String asmData = (String) (meMap.get(CreditCfgKeyConstants.QH_ASM_DATA_CONFIG));
						String truIsOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_TRU_ISOPEN_CONFIG));
						String truData = (String) (meMap.get(CreditCfgKeyConstants.QH_TRU_DATA_CONFIG));
						String leaIsOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_LEA_ISOPEN_CONFIG));
						String leaData = (String) (meMap.get(CreditCfgKeyConstants.QH_LEA_DATA_CONFIG));
						String crfIsOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_CRF_ISOPEN_CONFIG));
						String crfData = (String) (meMap.get(CreditCfgKeyConstants.QH_CRF_DATA_CONFIG));
						String invIsOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_INV_ISOPEN_CONFIG));
						String invData = (String) (meMap.get(CreditCfgKeyConstants.QH_INV_DATA_CONFIG));
						String cnsIsOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_CNS_ISOPEN_CONFIG));
						String cnsData = (String) (meMap.get(CreditCfgKeyConstants.QH_CNS_DATA_CONFIG));
						String insIsOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_INS_ISOPEN_CONFIG));
						String insData = (String) (meMap.get(CreditCfgKeyConstants.QH_INS_DATA_CONFIG));
						String thrIsOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_THR_ISOPEN_CONFIG));
						String thrData = (String) (meMap.get(CreditCfgKeyConstants.QH_THR_DATA_CONFIG));
						
						UserQhzxLoan userQhzxMsc8037 = CommonUtil.parseJSON2Bean(userQhzxMsc8037Map.get(ResponseConstants.DATA).toString(), UserQhzxLoan.class);
						
						boolean bakIsPass = loanCreditCompare(userQhzxMsc8037,bakData,"BAK");
						if (CreditCfgKeyConstants.OPEN.equals(bakIsOpen)) {
							logger.info("BAK命中生效开关开启，结果生效");
							if (bakIsPass) {
								logger.info("BAK命中");
								return loanCreditResult(userQhzxMsc8037,creditRecord,"BAK",_CROpen);
							}else{
								logger.info("BAK未命中");
							}
						}else{
							logger.info("BAK命中生效开关关闭，征信通过。");
						}
						
						boolean mclIsPass = loanCreditCompare(userQhzxMsc8037,mclData,"MCL");
						if (CreditCfgKeyConstants.OPEN.equals(mclIsOpen)) {
							logger.info("MCL命中生效开关开启，结果生效");
							if (mclIsPass) {
								return loanCreditResult(userQhzxMsc8037,creditRecord,"MCL",_CROpen);
							}else{
								logger.info("MCL未命中");
							}
						}else{
							logger.info("MCL命中生效开关关闭，征信通过。");
						}
						
						boolean p2pIsPass = loanCreditCompare(userQhzxMsc8037,p2pData,"P2P");
						if (CreditCfgKeyConstants.OPEN.equals(p2pIsOpen)) {
							logger.info("P2P命中生效开关开启，结果生效");
							if (p2pIsPass) {
								return loanCreditResult(userQhzxMsc8037,creditRecord,"P2P",_CROpen);
							}else{
								logger.info("P2P未命中");
							}
						}else{
							logger.info("P2P命中生效开关关闭，征信通过。");
						}
						
						boolean asmIsPass = loanCreditCompare(userQhzxMsc8037,asmData,"ASM");
						if (CreditCfgKeyConstants.OPEN.equals(asmIsOpen)) {
							logger.info("ASM命中生效开关开启，结果生效");
							if (asmIsPass) {
								return loanCreditResult(userQhzxMsc8037,creditRecord,"ASM",_CROpen);
							}else{
								logger.info("ASM未命中");
							}
						}else{
							logger.info("ASM命中生效开关关闭，征信通过。");
						}
						boolean truIsPass = loanCreditCompare(userQhzxMsc8037,truData,"TRU");
						if (CreditCfgKeyConstants.OPEN.equals(truIsOpen)) {
							logger.info("ASM命中生效开关开启，结果生效");
							if (truIsPass) {
								return loanCreditResult(userQhzxMsc8037,creditRecord,"TRU",_CROpen);
							}else{
								logger.info("TRU未命中");
							}
						}else{
							logger.info("TRU命中生效开关关闭，征信通过。");
						}
						
						boolean leaIsPass = loanCreditCompare(userQhzxMsc8037,leaData,"LEA");
						if (CreditCfgKeyConstants.OPEN.equals(leaIsOpen)) {
							logger.info("ASM命中生效开关开启，结果生效");
							if (leaIsPass) {
								return loanCreditResult(userQhzxMsc8037,creditRecord,"LEA",_CROpen);
							}else{
								logger.info("TRU未命中");
							}
						}else{
							logger.info("TRU命中生效开关关闭，征信通过。");
						}
						
						boolean crfIsPass = loanCreditCompare(userQhzxMsc8037,crfData,"CRF");
						if (CreditCfgKeyConstants.OPEN.equals(crfIsOpen)) {
							logger.info("CRF命中生效开关开启，结果生效");
							if (crfIsPass) {
								return loanCreditResult(userQhzxMsc8037,creditRecord,"CRF",_CROpen);
							}else{
								logger.info("CRF未命中");
							}
						}else{
							logger.info("CRF命中生效开关关闭，征信通过。");
						}
						
						boolean invIsPass = loanCreditCompare(userQhzxMsc8037,invData,"INV");
						if (CreditCfgKeyConstants.OPEN.equals(invIsOpen)) {
							logger.info("INV命中生效开关开启，结果生效");
							if (invIsPass) {
								return loanCreditResult(userQhzxMsc8037,creditRecord,"INV",_CROpen);
							}else{
								logger.info("INV未命中");
							}
						}else{
							logger.info("INV命中生效开关关闭，征信通过。");
						}
						
						boolean cnsIsPass = loanCreditCompare(userQhzxMsc8037,cnsData,"CNS");
						if (CreditCfgKeyConstants.OPEN.equals(cnsIsOpen)) {
							logger.info("CNS命中生效开关开启，结果生效");
							if (cnsIsPass) {
								return loanCreditResult(userQhzxMsc8037,creditRecord,"CNS",_CROpen);
							}else{
								logger.info("CNS未命中");
							}
						}else{
							logger.info("CNS命中生效开关关闭，征信通过。");
						}
						boolean insIsPass = loanCreditCompare(userQhzxMsc8037,insData,"INS");
						if (CreditCfgKeyConstants.OPEN.equals(insIsOpen)) {
							logger.info("INS命中生效开关开启，结果生效");
							if (insIsPass) {
								return loanCreditResult(userQhzxMsc8037,creditRecord,"INS",_CROpen);
							}else{
								logger.info("INS未命中");
							}
						}else{
							logger.info("INS命中生效开关关闭，征信通过。");
						}
						
						boolean thrIsPass = loanCreditCompare(userQhzxMsc8037,thrData,"THR");
						if (CreditCfgKeyConstants.OPEN.equals(thrIsOpen)) {
							logger.info("THR命中生效开关开启，结果生效");
							if (thrIsPass) {
								return loanCreditResult(userQhzxMsc8037,creditRecord,"THR",_CROpen);
							}else{
								logger.info("THR未命中");
							}
						}else{
							logger.info("THR命中生效开关关闭，征信通过。");
						}
						
					}else{
						logger.info("前海常贷客征信结果开关生效，未查询到任何常贷客数据,idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());
					}

				}else {
					logger.info("前海常贷客征信开关失效，idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());
				}
				
				this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_LOAN_PASS);
				
			} catch (Exception e1) {
				logger.error("调用前海常贷客接口（8037）异常：infoId:" + crmMap.get("infoId") +  ",身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName() , e1);
				updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR);
				//插入告警表
				String alarmContent = "调用前海常贷客接口（8037）异常,idCard："+  partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName() ;
				return sysAlarmService.exceptionAlarm(alarmContent);
			}
		
		}else{
			logger.info("调用前海常贷客-->开关关闭。");
		}
		
		
	    //请求前海征信好信度开关
		String _QHHXDOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_MODEL_GOODREL_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_QHHXDOpen)) {
			//调用前海征信好信度接口（8005），（判断返回对象为空，或者对象不为空且credooScore属性不为空，且credooScore得分低于550分，拒贷。）
			try {
				Map<String, Object> userQhzxMsc8005Map = qhzxService.requestQhCredit(partnerUserOrder.getIdCard(), partnerUserOrder.getName(), null, partnerUserOrder.getContactPhone(),null,crmMap.get("infoId"));
				
				//征信结果判断开关
				String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.QH_CR_GOODREL_ISOPEN));
				String noGoodrelData = (String) (meMap.get(CreditCfgKeyConstants.QH_NOGOODRELDATA_CONFIG));
				String goodrelData = (String) (meMap.get(CreditCfgKeyConstants.QH_GOODRELDATA_CONFIG));
				String score_result = (String) (meMap.get(CreditCfgKeyConstants.QH_GOODREL_SCORE_CONFIG));
				logger.info("调用前海征信好信度接口（8005）,前海征信好信度数据库中配置分数score_result：" + score_result);

				if (CreditCfgKeyConstants.OPEN.equals(_CROpen)) { //开关生效,结果生效
					
					if (EntityUtils.isEmpty(userQhzxMsc8005Map.get(ResponseConstants.RESULT_CODE))
							|| !ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals((String)userQhzxMsc8005Map.get(ResponseConstants.RESULT_CODE))) {
						logger.info("前海好信度接口开关生效，处理前海好信度接口发生问题，问题描述：" + userQhzxMsc8005Map.get(ResponseConstants.RESULT_DESC) + ",返回码：" + userQhzxMsc8005Map.get(ResponseConstants.RESULT_CODE));
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODRELEXPE_ERROR);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODRELEXPE_ERROR);
						String alarmContent = "处理前海好信度接口时发生问题，问题描述：" + userQhzxMsc8005Map.get(ResponseConstants.RESULT_DESC);
						return sysAlarmService.exceptionAlarm(alarmContent);
					}
					
					if (EntityUtils.isEmpty(userQhzxMsc8005Map.get(ResponseConstants.DATA))) {
						if ("0".equals(noGoodrelData)) {//当分数为空的时候，是否可以通过的开关，
							logger.info("前海好信度接口开关生效，未查询到数据,前海征信好信度分数为空,配置项" + noGoodrelData + "为无数据时,拒贷。");
							updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
							this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
							returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
							returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
							return returnMap;
						}else{
							logger.info("前海好信度接口开关生效,未查询到数据，前海征信好信度分数为空,配置项" + noGoodrelData + "为无数据时,通过。");
						}
					}else{
						UserQhzxCredit userQhzxMsc8005 = CommonUtil.parseJSON2Bean(userQhzxMsc8005Map.get(ResponseConstants.DATA).toString(), UserQhzxCredit.class);
						
						//当分数为空时
						if (EntityUtils.isEmpty(userQhzxMsc8005.getCredooScore())) {
							if ("0".equals(noGoodrelData)) {//当分数为空的时候，是否可以通过的开关，
								logger.info("前海好信度接口开关生效，,前海征信好信度分数为空,配置项" + noGoodrelData + "为无数据时,拒贷。");
								updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
								this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
								returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
								returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
								return returnMap;
							}else{
								logger.info("前海好信度接口开关生效,前海征信好信度分数为空,配置项" + noGoodrelData + "为无数据时,通过。");
							}
						}else{
							if ("0".equals(goodrelData)) {//当分数不为空的时候，是否可以通过的开关，0-拒贷 1-通过
										logger.info("前海好信度接口开关生效，分数不为空，配置项控制,拒贷.infoId:" + crmMap.get("infoId") +  ",idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName() + "，拒绝征信。");
										updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
										this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
										returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
										returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
										return returnMap;
							}else{
								logger.info("调前海好信度接口开关生效，分数不为空，配置项控制,通过,判断分数是否可以通过.infoId:" + crmMap.get("infoId") +  ",idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName() );
								 if (qhzxService.queryIsQhzxGoodRel(userQhzxMsc8005,score_result)) {
										String descp = userQhzxMsc8005 == null ? "获得好信度对象为空":"前海征信好信度得分不通过，得分为:" + userQhzxMsc8005.getCredooScore();
										logger.info("前海好信度接口开关生效，分数不为空，前海好信度得分不通过.infoId:" + crmMap.get("infoId") +  ",idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName() + "," + descp + "，拒绝征信。");
										updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
										this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
										returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
										returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
										return returnMap;
								 }else {
										logger.info("前海好信度接口开关生效，分数不为空，前海得分通过.infoId:" + crmMap.get("infoId") +  ",idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName() );
								 }
							}

						}
						
					}
				}else {
					logger.info("前海征信结果好信度结果开关关闭。");
				}
				this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_PASS);

			} catch (Exception e1) {
				logger.error("调用前海征信好信度接口（8005）异常：infoId:" + crmMap.get("infoId") +  ",身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName() , e1);
			}
		}else {
			logger.info("调用前海征信好信度-->开关关闭。");
		}
			
		//请求91征信开关
		String _91Open = (String) (meMap.get(CreditCfgKeyConstants.JY_MODEL_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_91Open)) {//可以请求91征信
			logger.info("调用91征信-->调用模快开关开启。");
			//请求91征信
			try {
				this.requestJYCredit(partnerUserOrder.getName(),partnerUserOrder.getIdCard(),partnerUserOrder.getContactPhone(),crmMap.get("infoId"),StatusConstants.JY_CALL_TYPE_CREDIT);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("调用91征信--> 调用91征信接口返回数据异常: infoId:" + crmMap.get("infoId") +  ",idCard："+ partnerUserOrder.getName() + ",contactPhone:" + partnerUserOrder.getContactPhone() +  ",userName:" + partnerUserOrder.getName() ,e);
			}
		}else {
			logger.info("调用91征信-->开关关闭。");
		}
		
		//请求好贷征信开关
		String _HDOpen = (String) (meMap.get(CreditCfgKeyConstants.HD_MODEL_ISOPEN));

		if (CreditCfgKeyConstants.OPEN.equals(_HDOpen)) {//可以请求好贷征信
			logger.info("调用好贷征信-->调用模快开关开启。");
			//请求好贷征信
			try {
				 this.requestHDCredit(partnerUserOrder.getName(),partnerUserOrder.getIdCard(),partnerUserOrder.getContactPhone(),crmMap.get("infoId"));
			} catch (Exception e) {
				logger.error("调用好贷征信--> 调用好贷征信接口返回数据异常:infoId:" + crmMap.get("infoId") +  ", idCard："+ partnerUserOrder.getName() + ",contactPhone:" + partnerUserOrder.getContactPhone() +  ",userName:" + partnerUserOrder.getName() ,e);
			}
		}else {
			logger.info("调用好贷征信-->开关关闭。");
		}
				
		//调用国政通
		UserCreditInfo userCreditInfo = null;
		
		//调用国政通开关
		String _GbossOpen = (String) (meMap.get(CreditCfgKeyConstants.GBOSS_MODEL_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_GbossOpen)) {//调用国政通开关开启
			logger.info("调用国政通-->调用国政通开关开启。");
			
			try {
				logger.info("用户征信，前海征信通过，好贷征信通过。年龄通过，调用国政通开始。idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());
				 userCreditInfo = this.queryGbossResult(partnerUserOrder.getName(),partnerUserOrder.getIdCard());
			} catch (Exception e1) {
				logger.error("调用国政通：infoId:" + crmMap.get("infoId") +  ",身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName() , e1);
			}
		}else {
			logger.info("调用国政通-->调用国政通开关关闭。");
		}
		
		
		//调用企信征信开关
		String _QXOpen = (String) (meMap.get(CreditCfgKeyConstants.QX_MODEL_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_QXOpen)) {//调用企信开关开启
			logger.info("调用企信-->调用企信开关开启。");
			//调用征信系统
			try {
				//组装请求企信的参数
				Map<String, String> digestMap = getQueryString(partnerUserOrder, userCreditInfo, crmMap);
				//调用企信
				Map<String, String> creditMap = requestQXCredit(digestMap);	
				
				//征信结果判断开关
				String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.QX_CR_BLACKLIST_ISOPEN));
				if (CreditCfgKeyConstants.OPEN.equals(_CROpen)) {
					logger.info("用·户征信调用企信征信系统开关生效。idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());

					if (StatusConstants.CREDIT_IS_PASS.toString().equals(creditMap.get("resultCode"))) {
						logger.info("征信判断接口开始-->征信通过");
						returnMap.put("resultCode",TelecomConstants.CRE_SUCCESS);
						returnMap.put("desc", TelecomConstants.CRE_SUCCESS_DESC);
						updateCR(creditRecord, StatusConstants.CREDIT_IS_PASS,creditMap.get("desc"));
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QX, creditMap.get("desc"), StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QX_RECORD_GOODREL_PASS);

					}else if(TelecomConstants.API_SYSTEM_EXCEPTION.toString().equals(creditMap.get("resultCode"))
							|| TelecomConstants.API_PARAMS_NULL.toString().equals(creditMap.get("resultCode"))
							|| TelecomConstants.API_PARAMS_WRONG.toString().equals(creditMap.get("resultCode"))
							|| TelecomConstants.API_PARAMS_SIGN_WRONG.toString().equals(creditMap.get("resultCode"))){
						logger.info("征信判断接口开始-->企信征信异常。resultCode:" + creditMap.get("resultCode"));
						returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
						returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QX_RECORD_GOODREL_UNPASS);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QX, CreditCodeConstants.CREDIT_TYPE_QX_EXP, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QX_RECORD_GOODREL_UNPASS);
					
					}else {
						logger.info("征信判断接口开始-->征信未通过");
						returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
						returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,creditMap.get("desc"));
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QX, creditMap.get("desc"), StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QX_RECORD_GOODREL_UNPASS);

					}
					return returnMap;
					
				} else {
					logger.info("用户征信调用企信征信系统开关失效，征信通过。idCard:" + partnerUserOrder.getIdCard() + "，name：" + partnerUserOrder.getName());
				}
				this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QX, creditMap.get("desc"), StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QX_RECORD_GOODREL_PASS);

			} catch (Exception e1) {
				updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QX_RECORD_RROR);
				logger.error("调用企信系统异常：TelecomConstants.API_SYSTEM_EXCEPTION ，身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName() , e1);
				//插入告警表
				String alarmContent = "调用企信系统异常：TelecomConstants.API_SYSTEM_EXCEPTION ，身份证号：" + partnerUserOrder.getIdCard() + "，姓名：" + partnerUserOrder.getName() ;
				return sysAlarmService.exceptionAlarm(alarmContent);
			}
		}else {
			logger.info("调用企信征信开关-->调用企信征信开关关闭。");
		}
		
		//如果能走到这一步，说明前面都是征信通过的
		updateCR(creditRecord, StatusConstants.CREDIT_IS_PASS,CreditCodeConstants.CREDIT_RECORD_CLOSED);
		returnMap.put("resultCode",TelecomConstants.CRE_SUCCESS);
		returnMap.put("desc", TelecomConstants.CRE_SUCCESS_DESC);
		return returnMap;
	}

	@Override
	public UserCreditRecord isBlackUser(String idCard,String name) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("idCard", idCard);
		map.put("name", name);
		//查询该和用户是否曾命中过前海或者好贷黑名单
		UserCreditRecord blackRecord = userCreditDataDAO.queryBlackUser(map);
		return blackRecord;
	}
	
	@Override
	public UserCreditInfo queryGbossResult(String userName, String identityCard) {
		logger.info("查询单个用户征信信息（国政通）,userName : " + userName + ",identityCard : " + identityCard);
		if(EntityUtils.isEmpty(userName)){
			logger.error("用户名为空");
		}else if(EntityUtils.isEmpty(identityCard)){
			logger.error("身份证号码为空");
		}
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("userName", userName);
		paramMap.put("identityCard", identityCard);
		
		
		//1.判断数据是否在数据库中已经存在：存在（国政通信用数据表不为空并且查询状态为成功并且查询结果为有数据），直接查询数据库返回；不存在，请求国政通，入库，返回
		List<UserCreditInfo> userCreditInfoList =  userCreditDataDAO.querySingleUserCreditInfo(paramMap);
		
		if(userCreditInfoList != null && userCreditInfoList.size() > 0 && userCreditInfoList.get(0) != null){
			Integer count = userCreditInfoList.get(0).getCount();
			count +=1;
			//更新查询国政通次数
			userCreditInfoList.get(0).setCount(count);
			userCreditInfoList.get(0).setUpdateTime(new Date());
			userCreditDataDAO.updateUserCreditRequestRecordCount(userCreditInfoList.get(0));
			logger.info("学历信用信息在数据库中已存在，返回数据库中学历信息：" );
			return userCreditInfoList.get(0);
		}
		
		logger.info("学历信用信息在数据库中不存在，请求国政通查询学历信息：");
		
		// 请求国政通接口
		String resultXML = "";
		UserCreditInfo userCreditInfo = null;
		try {
			String closeGboss = ConfigUtils.getProperty("is_open_gboss");//0:可以请求国政通；1：不可以请求国政通
			if (EntityUtils.isNotEmpty(closeGboss) && "0".equals(closeGboss)) {
				logger.info("请求国政通正式环境。" + userName + "," + identityCard);
				resultXML = this.querySingleUserCreditInfo(userName + "," + identityCard);
			}else{
				logger.info("请求模拟国政通环境。" + userName + "," + identityCard);
				// 模拟请求测试
				resultXML = this.testXueli();
			}

			logger.info("请求国政通返回数据信息：" + resultXML);
			
			userCreditInfo = (UserCreditInfo) GbossXmlUtil.xmlStrToBean(// 转换xml格式字符串为对象
					resultXML, UserCreditInfo.class);
		} catch (Exception e) {
			logger.error("请求国政通接口,解析数据异常");
			//插入告警表
			String alarmContent = "国政通访问异常，param：" + userName + "," + identityCard ;
			try {
				sysAlarmService.alarm(alarmContent);
			} catch (Exception e1) {
				logger.error("国政通访问异常异常，国政通访问异常，" + userName + "," + identityCard,e1);
			}
		}
		 //保存国政通数据
		try { 
			if (userCreditInfo != null) {
			 
				//插入t_user_credit_request_record（用户征信请求记录表）
				UserCreditRequestRecord userCreditRequestRecord = new UserCreditRequestRecord();
				userCreditRequestRecord.setRequestId(GenerateKey.getId(CommonConstants.CREDIT_REQUEST_RECORD_ID_PREFIX, ConfigUtils.getProperty("db_id"))); //主键
				userCreditRequestRecord.setName(userName); //用户姓名
				userCreditRequestRecord.setIdCard(identityCard); //身份证
				userCreditRequestRecord.setUniqueCode(""); //唯一标识(手机征信用)
				userCreditRequestRecord.setMobileNumber(""); //手机号(手机征信用)
				userCreditRequestRecord.setSource(StatusConstants.SERVICE_SOURCE_NAME_GZT.toString()); //请求源:10国政通
				userCreditRequestRecord.setRequestType(StatusConstants.SERVICE_REQUEST_TYPE_EDU); //学历
				userCreditRequestRecord.setRequestTime(new Date());
				userCreditRequestRecord.setRequestResult(userCreditInfo.getResultStatus() == 0 ? 0 : 1);
				userCreditRequestRecord.setDescp(userCreditInfo.getResultStatus() == 0 ?"处理成功" :userCreditInfo.getResultStatus().toString());
				userCreditDataDAO.insertUserCreditRequestRecord(userCreditRequestRecord);
 
			  logger.info("返回结果码："+ userCreditInfo.getQueryStatus() + ": 0--处理成功 -9999--参数数据不正确(部分参数为空) -9998--您的用户信息错误（用户名不存在） -9997--您无权查询数据 -9996--参数请求数据过长 -9995--该产品已暂停使用 -9994--参数数据加密错误 -990--系统异常 9999--未查到数据 -9919--参数数据不正确(参数格式不正确)"+     
					  "-9929--参数数据不正确(参数个数不正确) -9917--您无权查询数据(ip 无权限) -9927--您无权查询数据(没有订购该产品) -9937--您无权查询数据(产品状态是开始状态) -9947--您无权查询数据(产品状态是暂停状态)"+
					  "-9957--您无权查询数据(产品状态是终止状态) -9967--您无权查询数据(测试量已经用完) -9977--您无权查询数据(账户余额不足)");
			  
				if (userCreditInfo.getResultStatus() != null && userCreditInfo.getQueryStatus() == 0) { //接口返回成功
					//插入t_user_credit_info表（用户征信信息表）
					String creditId =GenerateKey.getId(CommonConstants.CREDIT_INFO_ID_PREFIX, ConfigUtils.getProperty("db_id"));
					userCreditInfo.setCreditId(creditId);
					userCreditInfo.setUserName(userName);
					userCreditInfo.setIdentityCard(identityCard);
					userCreditInfo.setSource(StatusConstants.SERVICE_SOURCE_NAME_GZT.toString()); //请求源:10国政通
					userCreditInfo.setCardSplitAddress(identityCard.substring(0, 6));     //身份证号分割出的省市区编码
					userCreditInfo.setCardSplitBirthday(IDCard.getBirthdayByIdCard(identityCard).replace("-", ""));
					userCreditInfo.setEnrolDate(userCreditInfo.getEnrolDate());
					userCreditInfo.setGraduateTime(userCreditInfo.getGraduateTime());
					 
					//获取性别
					String sex = "";
					boolean sexFlag = IDCard.getSexByIdCard(identityCard);
					if (sexFlag) {
						sex = StatusConstants.SEX_FEMALE.toString(); //女
					}else{
						sex = StatusConstants.SEX_MALE.toString(); //男
					}
					userCreditInfo.setCardSplitSex(sex);
					 
					String photoUrl =  GbossXmlUtil.savePhoto(ConfigUtils.getProperty("outer_gzt_photo_url"), userCreditInfo.getPhoto(),creditId + ".png",DateUtil.formatDate(new Date(),"yyyyMMdd") + "/" + identityCard );
					logger.info("保存照片地址为：" + photoUrl);
					userCreditInfo.setPhoto(photoUrl);
					userCreditInfo.setCreateTime(new Date());
					userCreditInfo.setUpdateTime(new Date());
					userCreditInfo.setCount(1);
					userCreditDataDAO.insertUserCreditInfo(userCreditInfo);
					 
				}
			
			}else{
				logger.info("请求国政通接口,返回数据为空");
			}
		} catch (Exception e) {
			logger.error("请求国政通接口,保存数据异常");
		}
		return userCreditInfo;
	}
	@Override
	public UserMobileCreditInfo querySingleGbossUserMobileCreditInfo(String mobileNumber,String uniqueCode) throws Exception{
			
		logger.info("查询单个用户征信信息（国政通）,mobileNumber : " + mobileNumber + ",uniqueCode : " + uniqueCode);
		if(EntityUtils.isEmpty(mobileNumber)){
			logger.error("用户名为空");
			throw new RuntimeException("用户名为空");
		}else if(EntityUtils.isEmpty(uniqueCode)){
			logger.info("唯一码为空");
			uniqueCode = UUIDTools.getFormatUUID();
		}
		
		//1.判断数据是否在数据库中已经存在：存在，直接查询数据库返回；不存在，请求国政通，入库，返回
		UserMobileCreditInfo userMobileCreditInfo = userCreditDataDAO.querySingleUserMobileCreditInfo(mobileNumber);
		
		if(EntityUtils.isNotEmpty(userMobileCreditInfo) 
				&& StatusConstants.CREDIT_QUERY_STATUS_SUCCESS.intValue() == userMobileCreditInfo.getQueryStatus()
				&& StatusConstants.CREDIT_QUERY_STATUS_SUCCESS_GET_DATA.intValue() == userMobileCreditInfo.getResultStatus()){
			logger.info("电信信用信息在数据库中已存在，返回数据库中学历信息：" );
			return userMobileCreditInfo;
		}
		
		String param = mobileNumber + "," + uniqueCode;
		
		logger.info("电信信用信息在数据库中不存在，请求国政通查询手机信用信息：" );
		
		//请求国政通接口
		String resultXML = this.querySingleUserMobileCreditInfo(param);
		//测试
		//String resultXML = this.testMobile();
		logger.info("请求国政通手机信息返回数据信息：" + resultXML);
		
		try {
			//转换xml格式字符串为对象
			userMobileCreditInfo = (UserMobileCreditInfo) GbossXmlUtil.xmlStrToBean(resultXML, UserMobileCreditInfo.class);
			
			 if (userMobileCreditInfo != null) {
				 
				 UserCreditRequestRecord userCreditRequestRecord = new UserCreditRequestRecord();
				 userCreditRequestRecord.setRequestId(GenerateKey.getId(CommonConstants.CREDIT_REQUEST_RECORD_ID_PREFIX, ConfigUtils.getProperty("db_id"))); //主键
				 userCreditRequestRecord.setName(""); //用户姓名
				 userCreditRequestRecord.setIdCard(""); //身份证
				 userCreditRequestRecord.setUniqueCode(uniqueCode); //唯一标识(手机征信用)
				 userCreditRequestRecord.setMobileNumber(mobileNumber); //手机号(手机征信用)
				 userCreditRequestRecord.setSource(StatusConstants.SERVICE_SOURCE_NAME_GZT.toString()); //请求源:10国政通
				 userCreditRequestRecord.setRequestType(StatusConstants.SERVICE_REQUEST_TYPE_PHONE); //学历
				 userCreditRequestRecord.setRequestTime(new Date());
				 userCreditRequestRecord.setRequestResult((userMobileCreditInfo.getQueryStatus() == null || userMobileCreditInfo.getQueryStatus() == 0) ? 0 : 1);
				 userCreditRequestRecord.setDescp((userMobileCreditInfo.getQueryStatus() == null|| userMobileCreditInfo.getQueryStatus() == 0) ?"处理成功" :userMobileCreditInfo.getQueryStatus().toString());
				 userCreditDataDAO.insertUserCreditRequestRecord(userCreditRequestRecord);
				 
				 
				  logger.info("返回结果码："+ userMobileCreditInfo.getQueryStatus() + ": 0--处理成功 -9999--参数数据不正确(部分参数为空) -9998--您的用户信息错误（用户名不存在） -9997--您无权查询数据 -9996--参数请求数据过长 -9995--该产品已暂停使用 -9994--参数数据加密错误 -990--系统异常 9999--未查到数据 -9919--参数数据不正确(参数格式不正确)"+     
						  "-9929--参数数据不正确(参数个数不正确) -9917--您无权查询数据(ip 无权限) -9927--您无权查询数据(没有订购该产品) -9937--您无权查询数据(产品状态是开始状态) -9947--您无权查询数据(产品状态是暂停状态)"+
						  "-9957--您无权查询数据(产品状态是终止状态) -9967--您无权查询数据(测试量已经用完) -9977--您无权查询数据(账户余额不足)");
				  
				 if (userMobileCreditInfo.getQueryStatus() != null && userMobileCreditInfo.getQueryStatus() == 0) { //接口返回成功
					 //插入t_user_mobile_credit_info表（手机征信信息表）
					 String mobileCreditId = GenerateKey.getId(CommonConstants.MOBILE_CREDIT_INFO_ID_PREFIX, ConfigUtils.getProperty("db_id"));
					 	userMobileCreditInfo.setMobileCreditId(mobileCreditId); //主键
					 	userMobileCreditInfo.setResultStatus(userMobileCreditInfo.getQueryStatus() == null || "".equals(userMobileCreditInfo.getQueryStatus())?null : userMobileCreditInfo.getQueryStatus());
					 	userMobileCreditInfo.setSource(StatusConstants.SERVICE_SOURCE_NAME_GZT.toString()); //请求源:10国政通
					 	userMobileCreditInfo.setEstimateIncomeMonth(getAmountCode(userMobileCreditInfo.getEstimateIncomeMonth()));
						userMobileCreditInfo.setEstimateIncomeAcc(getAmountCode(userMobileCreditInfo.getEstimateIncomeAcc()));
						userMobileCreditInfo.setEstimateStableIncomeMonth(getAmountCode(userMobileCreditInfo.getEstimateStableIncomeMonth()));
						userMobileCreditInfo.setEstimateStableIncomeAcc(getAmountCode(userMobileCreditInfo.getEstimateStableIncomeAcc()));
						userMobileCreditInfo.setEstimateOtherIncomeMonth(getAmountCode(userMobileCreditInfo.getEstimateOtherIncomeMonth()));
						userMobileCreditInfo.setEstimateOtherIncomeAcc(getAmountCode(userMobileCreditInfo.getEstimateOtherIncomeAcc()));
						userMobileCreditInfo.setEstimateOutgoMonth(getAmountCode(userMobileCreditInfo.getEstimateOutgoMonth()));
						userMobileCreditInfo.setEstimatePosOutgoMonth(getAmountCode(userMobileCreditInfo.getEstimatePosOutgoMonth()));
						userMobileCreditInfo.setEstimatePosOutgoAcc(getAmountCode(userMobileCreditInfo.getEstimatePosOutgoAcc()));
						userMobileCreditInfo.setEstimateOtherOutgoMonth(getAmountCode(userMobileCreditInfo.getEstimateOtherOutgoMonth()));
						userMobileCreditInfo.setEstimateOtherOutgoAcc(getAmountCode(userMobileCreditInfo.getEstimateOtherOutgoAcc()));
						userMobileCreditInfo.setHfBalance(getHfBalanceCode(userMobileCreditInfo.getHfBalance()));
						userMobileCreditInfo.setHfBalSign(userMobileCreditInfo.getHfBalSign() == null || "".equals(userMobileCreditInfo.getHfBalSign())?null : userMobileCreditInfo.getHfBalSign());
						userMobileCreditInfo.setHfUserStatus(userMobileCreditInfo.getHfUserStatus() == null || "".equals(userMobileCreditInfo.getHfUserStatus())? null : userMobileCreditInfo.getHfUserStatus());
						userMobileCreditInfo.setCreateTime(new Date());
						
						//上网时长去掉月份
						if (EntityUtils.isNotEmpty(userMobileCreditInfo.getOnlineTimes())) {
							Pattern pattern = Pattern.compile("\\d*");
							Matcher m = pattern.matcher(userMobileCreditInfo.getOnlineTimes());
					        while (m.find()) {
					        	
					        	 if (EntityUtils.isNotEmpty(m.group())) {
					        		 userMobileCreditInfo.setOnlineTimes(m.group());
					        	 }
					        }
					        
						}else{
							 userMobileCreditInfo.setOnlineTimes(null);
						}
						
					logger.info("手机品牌代码："
							+ userMobileCreditInfo.getHfSegCardType()
							+ ", 01:随 E 行 02:全球通预付费卡 10:神州行标准卡 11:神州行畅听卡(北京) 12:神州行家园卡(北京) 13:神州行 5 元卡(北京) 14:神州行 B(北京) 15:神州行金卡(浙江) 16:神州行智能卡(浙江) 17:非签约神州行本地卡(广西) 18:非签约神州行储值卡(广西) 20:动感地带 21:非签约动感地带(广西) 30:TD语音卡 31:TD数据卡 40:G3 信息机卡 41:G3 预付费卡 51:铁路公话(北京) 52:无线座机铁通卡(北京) 53:集团品牌（上海） 54:家庭宽带(上海) 55:先锋卡(上海) 56:家庭副号码(上海) 57:长白行(山西) 58:数据卡(山西) 59:省内套餐(山西) 510:数据卡(重庆) 511:长白行(吉林) 512:数据卡(吉林) 513:省内套餐(吉林) 99:其他");
						
						userCreditDataDAO.insertUserMobileCreditInfo(userMobileCreditInfo);
						 
						
						List<UserMobileCreditDetailInfo> list  = userMobileCreditInfo.getUserMobileCreditDetailInfo();
						
						if (list != null && list.size() > 0) {
							for (UserMobileCreditDetailInfo detailVo: list) {
								detailVo.setMobileCreditDetailId(GenerateKey.getId(CommonConstants.MOBILE_CREDIT_DETAIL_INFO_ID_PREFIX, ConfigUtils.getProperty("db_id"))); //主键
								detailVo.setMobileCreditId(mobileCreditId);
								detailVo.setCardType(detailVo.getCardType() == null || "".equals(detailVo.getCardType())?null : detailVo.getCardType());
								detailVo.setIfStable(detailVo.getIfStable() == null ?null : detailVo.getIfStable());
								detailVo.setIfPayOntime(detailVo.getIfPayOntime() == null ?null : detailVo.getIfPayOntime());
								detailVo.setStableIncome(getAmountCode(detailVo.getStableIncome()));
								detailVo.setOtherIncome(getAmountCode(detailVo.getOtherIncome()));
								detailVo.setPosOutgo(getAmountCode(detailVo.getPosOutgo()));
								detailVo.setOtherOutgo(getAmountCode(detailVo.getOtherOutgo()));
								detailVo.setRemainAmt(getAmountCode(detailVo.getRemainAmt()));
								detailVo.setCreateTime(new Date());
								 userCreditDataDAO.insertUserMobileCreditDetailInfo(detailVo);
							}
						}
						
				}
					
			 }else{
					logger.info("请求国政通手机征信接口,返回数据为空");
				}
			
		} catch (Exception e) {
			logger.error("请求国政通手机信息接口,解析数据异常");
			e.printStackTrace();
			throw new RuntimeException("请求国政通手机信息接口,解析数据异常");
		}
		
		return userMobileCreditInfo;
	}
	
	/**  
	 * querySingleUserCreditInfo(请求国政通征信信息接口)  
	 * @param param
	 * @return   
	 * String 
	 * @create time： Oct 9, 2015 5:25:38 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static String querySingleUserCreditInfo(String param){
		QueryValidatorServicesProxy proxy = new QueryValidatorServicesProxy(); 
		proxy.setEndpoint(ConfigUtils.getProperty("outer_gzt_service_ip")); 
		QueryValidatorServices service = proxy.getQueryValidatorServices();
		
		String gztUserName = ConfigUtils.getProperty("outer_gzt_user_name");
		String gztPassword = ConfigUtils.getProperty("outer_gzt_user_password");
		String gztDatasource = ConfigUtils.getProperty("outer_gzt_datasource_user_credit");//数据类型 
		String key = ConfigUtils.getProperty("outer_gzt_des_key"); //国政通DES加密密钥key
		
		System.setProperty("javax.net.ssl.trustStore", "CheckID.keystore"); 
		String resultXML = "";
		
		//输入参数
		try {
			logger.info("加密前：param" + param);
			
			gztUserName = DESUtils.encode(key, gztUserName);
			gztDatasource = DESUtils.encode(key, gztDatasource);
			param = DESUtils.encode(key, param);
			
			gztPassword = DESUtils.encode(key, gztPassword);
			logger.info("加密后：param" + param);
			
			resultXML = service.querySingle(gztUserName, gztPassword, gztDatasource, param);
			
			resultXML = DESUtils.decode(key, resultXML);
		} catch (Exception e) {
			logger.error("请求国政通征信信息接口异常",e);
			throw new RuntimeException("请求国政通征信信息接口异常");
		} 
	
		return resultXML;
	}
	
	@Override
	public void insertCreditModelRecord(String creditId,String _CROpen,String creditModel, String creditModelType, int creditResult,String resultDesc) {
		
		UserCreditModelRecord record = new UserCreditModelRecord();
		record.setCreditId(creditId);
		record.setModelRecordId(GenerateKey.getId(CommonConstants.USER_CREIDT_MODEL_RECORD_ID_PREFIX, ConfigUtils.getProperty("db_id")));
		record.setCreditModel(creditModel);
		record.setCreditModelType(creditModelType);
		record.setIsEffective(_CROpen); //（0-无效；1-有效）
		record.setCreditCheckingResult(creditResult);//征信模块判断结果：10 征信通过 20征信不通过
		record.setCreditCheckingResultDesc(resultDesc);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setOperator(CommonConstants.DEFAULT_OPERATOR);
		userCreditDataDAO.insertCreditModelRecord(record);
	}

	@Override
	public BlackList queryBlackList(String idCard,String telPhone) throws Exception{
		Map<String, String> map = new HashMap<String,String>();
		map.put("idCard", idCard);
		map.put("telPhone", telPhone);
		return userCreditDataDAO.queryBlackList(map);
	}
	
	@Override
	public boolean queryWhiteList(String idCard, String name,String contactPhone, String partnerId) throws Exception{
		Map<String, String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("idCard", idCard);
		map.put("contactPhone", contactPhone);
		map.put("partnerId", partnerId);
		
		//判断是否匹配到白名单
		int whiteCount = userCreditDataDAO.queryWhiteList(map);
		if (whiteCount > 0) {
			logger.info("调用征信接口，查询白名单信息，匹配白名单信息。idCard:" + idCard + "，name：" + name);
			return true;
		}else {
			logger.info("调用征信接口，查询白名单信息，未匹配到匹配白名单信息。idCard:" + idCard + "，name：" + name);
			return false;
		}
	}
	@Override
	public WhiteList queryWhiteList(Map<String,String> map) throws Exception{
		
		//判断是否匹配到白名单
		WhiteList witeList = userCreditDataDAO.querySchoolWhiteList(map);
		return witeList;
	}


	/**  
	 * querySingleUserMobileCreditInfo(请求国政通手机信息接口)  
	 * @param param
	 * @return   
	 * String 
	 * @create time： Oct 9, 2015 5:27:27 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static String querySingleUserMobileCreditInfo(String param){
		QueryValidatorServicesProxy proxy = new QueryValidatorServicesProxy(); 
		proxy.setEndpoint(ConfigUtils.getProperty("outer_gzt_service_ip")); 
		QueryValidatorServices service = proxy.getQueryValidatorServices();
		
		String gztUserName = ConfigUtils.getProperty("gzt_user_name");
		String gztPassword = ConfigUtils.getProperty("outer_gzt_user_name");
		String gztDatasource = ConfigUtils.getProperty("outer_gzt_datasource_user_mobile_credit");//数据类型 
		String key = ConfigUtils.getProperty("outer_gzt_des_key"); //国政通DES加密密钥key
		
		System.setProperty("javax.net.ssl.trustStore", "CheckID.keystore"); 
		String resultXML = "";
		
		//输入参数
		try {
			logger.info("加密前：param" + param);
			
			gztUserName = DESUtils.encode(key, gztUserName);
			gztPassword = DESUtils.encode(key, gztPassword);
			gztDatasource = DESUtils.encode(key, gztDatasource);
			param = DESUtils.encode(key, param);
			
			logger.info("加密后：param" + param);
			
			resultXML = service.querySingle(gztUserName, gztPassword, gztDatasource, param);
			
			resultXML = DESUtils.decode(key, resultXML);
		} catch (Exception e) {
			logger.error("请求国政通手机接口异常");
			e.printStackTrace();
			throw new RuntimeException("请求国政通手机接口异常");
		} 
	
		return resultXML;
	}
	
	public static String getAmountCode(String amountRange){
		
		if (amountRange != null && !"".equals(amountRange)) {
			
			if ("[0,500)".equals(amountRange)) {
				return "00";
			}else if ("[500,1000)".equals(amountRange)) {
				return "01";
			}else if ("[1000,1500)".equals(amountRange)) {
				return "02";
			}else if ("[1500,2000)".equals(amountRange)) {
				return "03";
			}else if ("[2000,2500)".equals(amountRange)) {
				return "04";
			}else if ("[2500,3000)".equals(amountRange)) {
				return "05";
			}else if ("[3000,3500)".equals(amountRange)) {
				return "06";
			}else if ("[3500,4000)".equals(amountRange)) {
				return "07";
			}else if ("[4000,4500)".equals(amountRange)) {
				return "08";
			}else if ("[4500,5000)".equals(amountRange)) {
				return "09";
			}else if ("[5000,5500)".equals(amountRange)) {
				return "010";
			}else if ("[5500,6000)".equals(amountRange)) {
				return "011";
			}else if ("[6000,6500)".equals(amountRange)) {
				return "012";
			}else if ("[6500,7000)".equals(amountRange)) {
				return "013";
			}else if ("[7000,7500)".equals(amountRange)) {
				return "014";
			}else if ("[7500,8000)".equals(amountRange)) {
				return "015";
			}else if ("[8000,8500)".equals(amountRange)) {
				return "016";
			}else  if ("[8500,9000)".equals(amountRange)) {
				return "017";
			}else  if ("[9000,9500)".equals(amountRange)) {
				return "018";
			}else  if ("[9500,10000)".equals(amountRange)) {
				return "019";
			}else  if ("[10000,11000)".equals(amountRange)) {
				return "10";
			}else  if ("[11000,12000)".equals(amountRange)) {
				return "11";
			}else  if ("[12000,13000) ".equals(amountRange)) {
				return "12";
			}else  if ("[13000,14000)".equals(amountRange)) {
				return "13";
			}else  if ("[14000,15000)".equals(amountRange)) {
				return "14";
			}else  if ("[15000,16000)".equals(amountRange)) {
				return "15";
			}else  if ("[16000,17000)".equals(amountRange)) {
				return "16";
			}else if ("[17000,18000)".equals(amountRange)) {
				return "17";
			}else if ("[18000,19000)".equals(amountRange)) {
				return "18";
			}else if ("[19000,20000)".equals(amountRange)) {
				return "19";
			}else if ("[20000,21000)".equals(amountRange)) {
				return "20";
			}else if ("[21000,22000)".equals(amountRange)) {
				return "21";
			}else if ("[22000,23000)".equals(amountRange)) {
				return "22";
			}else if ("[23000,24000)".equals(amountRange)) {
				return "23";
			}else if ("[24000,25000)".equals(amountRange)) {
				return "24";
			}else if ("[25000,26000)".equals(amountRange)) {
				return "25";
			}else if ("[26000,27000)".equals(amountRange)) {
				return "26";
			}else if ("[27000,28000)".equals(amountRange)) {
				return "27";
			}else if ("[28000,29000)".equals(amountRange)) {
				return "28";
			}else if ("[29000,30000)".equals(amountRange)) {
				return "29";
			}else if ("[30000,31000)".equals(amountRange)) {
				return "30";
			}else if ("[31000,32000)".equals(amountRange)) {
				return "31";
			}else if ("[32000,33000)".equals(amountRange)) {
				return "32";
			}else  if ("[33000,34000)".equals(amountRange)) {
				return "33";
			}else  if ("[34000,35000)".equals(amountRange)) {
				return "34";
			}else  if ("[35000,36000)".equals(amountRange)) {
				return "35";
			}else  if ("[36000,37000)".equals(amountRange)) {
				return "36";
			}else  if ("[37000,38000)".equals(amountRange)) {
				return "37";
			}else  if ("[38000,39000)".equals(amountRange)) {
				return "38";
			}else  if ("[39000,40000)".equals(amountRange)) {
				return "39";
			}else  if ("[40000,41000)".equals(amountRange)) {
				return "40";
			}else  if ("[41000,42000)".equals(amountRange)) {
				return "41";
			}else  if ("[42000,43000)".equals(amountRange)) {
				return "42";
			}else  if ("[43000,44000)".equals(amountRange)) {
				return "43";
			}else  if ("[44000,45000)".equals(amountRange)) {
				return "44";
			}else if ("[45000,46000)".equals(amountRange)) {
				return "45";
			}else   if ("[46000,47000)".equals(amountRange)) {
				return "46";
			}else   if ("[47000,48000)".equals(amountRange)) {
				return "47";
			}else   if ("[48000,49000)".equals(amountRange)) {
				return "48";
			}else   if ("[49000,50000)".equals(amountRange)) {
				return "49";
			}else   if ("[50000,50000 以上 )".equals(amountRange)) {
				return "50";
			}else    
				return "99";
		}
	
	return null;
}

public static String getHfBalanceCode(String hfBalance){
	
	if (hfBalance != null && !"".equals(hfBalance)) {
		
		if ("小于 0".equals(hfBalance)) {
			return "00";
		}else if ("[0,50)".equals(hfBalance)) {
			return "01";
		}else if ("[50,100)".equals(hfBalance)) {
			return "02";
		}else if ("[100,150)".equals(hfBalance)) {
			return "03";
		}else if ("[150,200)".equals(hfBalance)) {
			return "04";
		}else if ("[200,200 以上)".equals(hfBalance)) {
			return "05";
		}else{
			return "98"; //空值
		}
	}
	return null;
}

	/**
	 * getQueryString(征信判断请求的业务参数)
	 * 
	 * @param partnerUserOrder
	 * @param userCreditInfo
	 * @param userMobileCreditInfo
	 * @return
	 * @throws UnsupportedEncodingException
	 *             Map<String,String>
	 * @create time： Nov 11, 2015 5:59:24 PM
	 * @author：张海达
	 * @since 1.0.0
	 */
	public static Map<String, String> getQueryString(PartnerUserOrder partnerUserOrder, UserCreditInfo userCreditInfo,Map<String, String> crmMap) throws UnsupportedEncodingException {
			
		JSONObject userCreditJson = JSONObject.fromObject(userCreditInfo);

		Map<String, String> digestMap = new HashMap<String, String>();
		digestMap.put("seq", UUIDTools.getFormatUUID());// 请求流水
		digestMap.put("userName", partnerUserOrder.getName());
		digestMap.put("idCard", partnerUserOrder.getIdCard());
		digestMap.put("idCardAddress", partnerUserOrder.getIdCardAddress());
		digestMap.put("telNumber", partnerUserOrder.getContactPhone());
		digestMap.put("creditData", userCreditJson.toString());
		digestMap.put("custId", crmMap.get("custId"));
		digestMap.put("pdInstId", crmMap.get("pdInstId"));
		digestMap.put("custIp", crmMap.get("custIp"));

		logger.info("seq:" + digestMap.get("seq") + ",userName:"
				+ partnerUserOrder.getName() + ",idCard"
				+ partnerUserOrder.getIdCard() + ",idCardAddress"
				+ partnerUserOrder.getIdCardAddress() + ",telNumber"
				+ partnerUserOrder.getContactPhone() + ",creditData"
				+ userCreditJson.toString());
		return digestMap;
	}

	//测试
	public static String testXueli()throws Exception{  
	    //创建SAXReader对象  
	    SAXReader reader = new SAXReader();  
	    reader.setEncoding("utf8");
	    //读取文件 转换成Document  
	    Document document = reader.read(new File(ConfigUtils.getProperty("gboss_xueli_dir")));  
	    //Document document = reader.read(new File("D:/xueli.xml"));  
	    //document转换为String字符串  
	    String documentStr = document.asXML();  
	    //获取根节点  
	    Element root = document.getRootElement();  
	    //根节点转换为String字符串  
	    String rootStr = root.asXML();  
	    //System.out.println("root 字符串：" + rootStr);  
	    return rootStr;
	} 

	public static String testMobile()throws Exception{  
		//创建SAXReader对象  
		SAXReader reader = new SAXReader();  
		reader.setEncoding("utf8");
		//读取文件 转换成Document  
		Document document = reader.read(new File("D:/mobil.xml"));  
		//document转换为String字符串  
		String documentStr = document.asXML();  
		//获取根节点  
		Element root = document.getRootElement();  
		//根节点转换为String字符串  
		String rootStr = root.asXML();  
		//System.out.println("root 字符串：" + rootStr);  
		return rootStr;
	}

	@Override
	public Integer queryExistInstalment(Map<String, Object> instalmentMap) {
		return userCreditDataDAO.queryExistInstalment(instalmentMap);
	}
	
	@Override
	public void insertUserCreditRecord(UserCreditRecord creditRecord) {
		userCreditDataDAO.insertUserCreditRecord(creditRecord);
	}

	@Override
	public boolean isAllowInstalment(Map<String, Object> instalmentMap) {
	Integer queryExistInstalment = userCreditDataDAO.queryExistInstalment(instalmentMap);
		
		//查询配置用户最大下单量
		Integer maxInscount = Integer.parseInt(configKvService.get(CreditCfgKeyConstants.MAX_ORDER_COUNT));

		logger.info("查询每个用户配置的最大下单量：" + maxInscount +"，当前下单量:" + queryExistInstalment);
		//查询允许用户最大下单量
		if (queryExistInstalment >= maxInscount) {
			
			 logger.info("征信判断接口-->用户下单量超过限制，禁止下单。");
			 return false;
		}
		
		
		//查询根据手机号配置截至今日最长天数内禁止下单
		Integer maxOrderTelDay = Integer.parseInt(configKvService.get(CreditCfgKeyConstants.MAX_ORDER_TEL_DAY));
		
		//根据用户手机下单，未还清并且下单时间在90天内的不可以继续下单
		boolean flag = false;
		ArrayList<Map<String,String>> instalmentList = userCreditDataDAO.queryExistInstalmentByTel(instalmentMap);
		if (instalmentList != null && instalmentList.size() > 0) {
			for (Map<String, String> inst:instalmentList) {
				if (EntityUtils.isNotEmpty(inst) && Integer.parseInt(inst.get("day")) <= maxOrderTelDay) {
					//判断是否是同一个人，如果是同一个人则可以继续下单
					if (!instalmentMap.get("idCard").equals(inst.get("idCard"))) {
						logger.info("该手机号下单天数在：" + maxOrderTelDay +"天内，" +  "，当前天数:" + Integer.parseInt(inst.get("day")) + ",禁止下单。");
						flag = true;
						break;
					}else{
						logger.info("改手机号距上次下单时间在" + maxOrderTelDay +"天内，但是属于同一个人下单，可以继续下单。");
					}

				}
			}
			
			if (flag) {
				logger.info("根据用户手机查询下单，存在未还清并且下单时间在" + maxOrderTelDay +"天内的订单，禁止继续下单。手机号：" + instalmentMap.get("contactPhone"));
				return false;
			}
		}
		return true;
	}

	@Override
	public Map<String, String> requestQXCredit(Map<String, String> digestMap){
		logger.info("调用企信征信数据开始。");
	
		Map<String, String> returnMap = new HashMap<String,String>();
		String enc = ConfigUtils.getProperty("inner_mq_plat_enc");
		String desKey = ConfigUtils.getProperty("inner_mq_plat_3des_key");//双方约定的密钥
		String md5Key = ConfigUtils.getProperty("inner_mq_plat_md5_key");
		String configPartnerId = ConfigUtils.getProperty("inner_mq_plat_partner_id");
		String configVersion = ConfigUtils.getProperty("inner_mq_plat_data_version");
		String url = ConfigUtils.getProperty("inner_mq_plat_data_valiurl");

		
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			// 生成业务加密串
			String busParams = HkEncryptUtils.createEncryptBusParams(digestMap,desKey, enc);
			logger.info("生成业务加密串parseByte2HexStr : " + busParams);

			// 协议参数
			paramMap.put("timestamp", DateUtil.getCurDateStr("yyyyMMddHHmmss"));
			paramMap.put("partnerId", configPartnerId);
			paramMap.put("version", configVersion);
			paramMap.put("params", busParams);

			// 生成验签sign
			String sign = HkEncryptUtils.createMd5Sign(paramMap, md5Key, enc);
			paramMap.put("sign", sign);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("请求企信组装参数异常", e1);
			throw new RuntimeException("请求企信组装参数异常");
		}
		
		//将参数传给征信引擎
		String ret = null;
		try {
			HttpClientUtil http = new HttpClientUtil(Integer.parseInt(ConfigUtils.getProperty("httpclient_timeOut")));
			logger.info("url:" + url + ",configPartnerId:" + configPartnerId + ",md5Key:" + md5Key + ",desKey:" + desKey + ",enc:" + enc +  ",configVersion:" + configVersion);
			ret = http.doPost(url, paramMap);
			logger.info("ret:" + ret);
		} catch (Exception e) {
			logger.error("征信接口，调用企信征信网络异常", e);
			//插入告警表
			String alarmContent = "企信访问异常，姓名：" + digestMap.get("userName") +  ",身份证号：" +  digestMap.get("idCard") ;
			try {
				sysAlarmService.alarm(alarmContent);
			} catch (Exception e1) {
				logger.error("企信访问异常，idCard:" + digestMap.get("idCard"),e1);
			}
			
			throw new RuntimeException("征信接口，调用企信征信网络异常",e);	
		}
		
		//转换为Map
		Map<String,String> resultMap = HkEncryptUtils.stringToMap(ret);
		
		//验证协议参数完整性
		Map<String, Object>  protocolMap = ValidateAPIPlatParamsTools.validateProtocolParams(resultMap,configPartnerId,configVersion);
		
		 if (!StatusConstants.PARTNER_CALL_SUCCESS.equals(protocolMap.get("result_code"))) {
			logger.info("订单同步接口-->协议参数验证未通过：" + protocolMap.get("error_code").toString());
			returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
			returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
			return returnMap;
		}		
		
		String bus_params_ret =  resultMap.get("params");
		String partner_id_ret = resultMap.get("partnerId");
		String sign_ret = resultMap.get("sign") ;
		String timestamp_ret = resultMap.get("timestamp");
		String version_ret = resultMap.get("version");
		logger.info("查询征信数据，企信返回协议结果："  + ",params:" + bus_params_ret + ",partnerId" + partner_id_ret + 
									",sign" + sign_ret + ",timestamp" + timestamp_ret + ",version" + version_ret );
				
		//验签参数
		Map<String,String> signMap = new HashMap<String,String>();
		signMap.put("params", bus_params_ret);
		signMap.put("partnerId", partner_id_ret);
		signMap.put("timestamp", timestamp_ret);
		signMap.put("version", version_ret);
		
		String _sign = HkEncryptUtils.createMd5Sign(signMap, md5Key, enc);
		logger.info("_sign:" + _sign);
		
		if(!sign_ret.equals(_sign)){
			logger.info("验签不通过,获得验签参数：" + sign_ret + ",生成验签参数：" + _sign);
			returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
			returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
			return returnMap;
		}
		
		logger.info("验签通过");
		Map<String, String> busMap = HkEncryptUtils.createDecryptBusMap(bus_params_ret, desKey, enc);
				
		logger.info("征信评分返回评分结果：" + busMap.get("resultCode") + ",描述：" + busMap.get("desc"));
		
		logger.info("调用企信征信数据结束。");
		return busMap;
		
	}
	
	@Override
	public boolean  isCreditByName(String name) {
		
		//调用国政通前，判断姓氏是否合法
		int count = userCreditDataDAO.queryIllegalCount(name);
		if (count > 0) {
			logger.info( name + "在不合理姓名中，拒绝征信。");
			return true;
		}
		logger.info(name + "不属于不合理的姓名：" + ",判断姓氏是否合理开始。");
		
		boolean flag = true;
		logger.info("征信用户全名：" + name);
		for (int i = name.length()-1 ; i > 0; i--) {
			
			String surName = name.substring(0, i);
			logger.info("获取征信用户姓氏：" + surName);
			
			if (!Arrays.asList(CheckNameUtils.getChineseSurName()).contains(surName)) {
				logger.info("百家姓不包含" + surName);
			}else{
				logger.info("百家姓包含"  + surName);
				flag = false; //姓名通过
				break;
			}
		}
		
		return flag;
	}
	
	@Override
	public boolean  isCreditByAge(String idCard,String configAgeSection) {

		//调用国政通接口，查询国政通数据,首先判断年龄是如果小于配置 ，不能进行征信请求
		int age  = IDCard.caculAgeIdCard(idCard);
		logger.info("计算该客户年龄：" + age  + ",身份证号：" +idCard);

		if (Integer.parseInt(configAgeSection.split("~")[0]) > IDCard.caculAgeIdCard(idCard) || Integer.parseInt(configAgeSection.split("~")[1]) < IDCard.caculAgeIdCard(idCard)) {
			logger.info("计算该客户年龄" + age + "不在" + configAgeSection +"岁之间，征信不通过。身份证号：" + idCard );
			return true;
		}else {
			logger.info("计算该客户年龄" + age + "在" + configAgeSection + "岁之间，可以继续征信。");
			return false;
		}
		
	
	}
	
	
	@Override
	public  void  requestJYCredit(String userName,String idCard,String telNumber,String totalInfoId,String callType) {
		logger.info("征信接口，调用91开始。totalinfoId：" + totalInfoId + ",userName" + userName  + ",idCard" + idCard  + ",telNumber" + telNumber);
		
		//将参数传给征信引擎
		HttpClientUtil http = new HttpClientUtil(Integer.parseInt(ConfigUtils.getProperty("httpclient_timeOut")));
		logger.info("征信接口，调用91征信开始。配置超时时间：" + ConfigUtils.getProperty("httpclient_timeOut"));
		
		String jyzxUrl = ConfigUtils.getProperty("inner_jyzx_credit_url");
		
		logger.info("征信接口，调用91征信开始。配置超时时间：" + ConfigUtils.getProperty("httpclient_timeOut") + ",91征信url：" + jyzxUrl );

		String jyzxRet = null;
		
		Map<String, String> digestMap = new HashMap<String, String>();
		
		logger.info("征信接口，请求91征信开始。");
		try {
			digestMap.put("userName", new String(userName.toString().getBytes("UTF-8"),"iso8859-1"));
			digestMap.put("userIdCard", idCard);
			digestMap.put("totalInfoId", totalInfoId);
			digestMap.put("callType", callType);
			
			jyzxRet = http.doPost(jyzxUrl, digestMap);
			logger.info("jyzxRet:" + jyzxRet);
		} catch (Exception e) {
			logger.error("征信接口，请求91征信网络异常", e);
			throw new RuntimeException("征信接口，请求91征信网络异常", e);
		}
		logger.info("征信接口，请求91征信结束。");
	}
	
	@Override
	public  void  requestHDCredit(String userName,String idCard,String telNumber,String totalInfoId) {
		logger.info("征信接口，调用好贷征信开始。totalinfoId：" + totalInfoId + ",userName" + userName  + ",idCard" + idCard  + ",telNumber" + telNumber);
		
		//将参数传给征信引擎
		HttpClientUtil http = new HttpClientUtil(Integer.parseInt(ConfigUtils.getProperty("httpclient_timeOut")));
		logger.info("征信接口，调用好贷征信开始。配置超时时间：" + ConfigUtils.getProperty("httpclient_timeOut"));
		
		String hdzxUrl = ConfigUtils.getProperty("inner_hdzx_credit_url");
		
		logger.info("征信接口，调用好贷征信开始。配置超时时间：" + ConfigUtils.getProperty("httpclient_timeOut") +  ",好贷征信url：" + hdzxUrl);

		String hdzxRet = null;
		
		Map<String, String> digestMap = new HashMap<String, String>();
		logger.info("征信接口，请求好贷征信开始。");
		try {
			digestMap.put("userName", new String(userName.toString().getBytes("UTF-8"),"iso8859-1"));
			digestMap.put("userIdCard", idCard);
			digestMap.put("totalInfoId", totalInfoId);
			digestMap.put("mobileNumber", telNumber);
			
			hdzxRet = http.doPost(hdzxUrl, digestMap);
			logger.info("hdzxRet:" + hdzxRet);
		} catch (Exception e) {
			logger.error("征信接口，调用好贷征信网络异常", e);
			throw new RuntimeException("征信接口，请求好贷征信网络异常", e);
		}
		logger.info("征信接口，请求好贷征信结束。");
	}
	@Override
	public void updateCR(UserCreditRecord creditRecord ,Integer isPass,String reason){
		creditRecord.setIsPass(isPass);
		creditRecord.setReason(reason);
		creditRecord.setUpdateTime(new Date());
		userCreditDataDAO.updateUserCreditRecord(creditRecord);
	}

	@Override
	public String queryCreditIdByTotalId(String totalId) {
		return userCreditDataDAO.queryCreditIdByTotalId(totalId);
	}
	
	private boolean loanBakCreditResult(UserQhzxLoan userQhzxMsc8037,String bakData){
		//判断命中次数
		if (userQhzxMsc8037 != null && EntityUtils.isNotEmpty(userQhzxMsc8037.getIndustry())
				&& "BAK".equals(userQhzxMsc8037.getIndustry())
				&& EntityUtils.isNotEmpty(userQhzxMsc8037.getAmount())
				&& Integer.parseInt(userQhzxMsc8037.getAmount()) > Integer.parseInt(bakData)) {
			logger.info("命中BAK规则，征信未通过。");
			return true;
		}else{
			logger.info("不符合BAK命中规则，征信通过。");
			return false;
		}
	}
	
	@Override
	public boolean loanCreditCompare(UserQhzxLoan userQhzxMsc8037,String data,String type){
		logger.info("data:" + data + ",type:" + type);
		//判断命中次数
		if (userQhzxMsc8037 != null && EntityUtils.isNotEmpty(userQhzxMsc8037.getIndustry())
				&& type.equals(userQhzxMsc8037.getIndustry())
				&& EntityUtils.isNotEmpty(userQhzxMsc8037.getAmount())
				&& Integer.parseInt(userQhzxMsc8037.getAmount()) > Integer.parseInt(data)) {
			logger.info("userQhzxMsc8037.getIndustry():" + userQhzxMsc8037.getIndustry() + ",userQhzxMsc8037.getAmount():" + userQhzxMsc8037.getAmount());
			logger.info("命中" + type + "规则，征信未通过。");
			return true;
		}else{
			logger.info("不符合" + type + "命中规则，征信通过。");
			return false;
		}
	}
	
	//常贷客拒贷结果
	@Override
	public Map<String,String> loanCreditResult(UserQhzxLoan userQhzxMsc8037,UserCreditRecord creditRecord,String type,String _CROpen){
		Map<String,String> returnMap = new HashMap<String,String>();
		//判断命中次数
		updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
		this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
		returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
		returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
		return returnMap;
	}
	
	//判断是否符合百融多次申请核查拒贷条件
	@Override
	public boolean applyLoanCreditCompare(Integer brBankNum,String brBankNumConfig){
		logger.info("百融返回数据：" + brBankNum + ",数据库读取配置：" + brBankNumConfig);
		if (EntityUtils.isEmpty(brBankNum) || EntityUtils.isEmpty(brBankNumConfig)) {
			return false;
		}
		
		if (brBankNum > Integer.parseInt(brBankNumConfig)) {
			logger.info("根据配置信息，拒贷。" + brBankNum + ">"+ brBankNumConfig);
			return true;
		}
		logger.info("通过。");
		return false;
	}
	//百融多次申请核查征信结果
	@Override
	public Map<String,String> applyLoanCreditResult(UserCreditRecord creditRecord,String _CROpen){
		Map<String,String> returnMap = new HashMap<String,String>();
		//判断命中次数
		updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
		this.insertCreditModelRecord(creditRecord.getCreditId(),_CROpen, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_APPLOAN_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
		returnMap.put("resultCode",TelecomConstants.CRE_FAIL);
		returnMap.put("desc", TelecomConstants.CRE_FAIL_DESC);
		return returnMap;
	}
	
	
	@Override
	public UserQhzxVerify queryVerify(String idCard, String userName, String telNumber,String infoId) {
		String isRealIdentity = StatusConstants.QHZX_IDNO_NAME_NO_MATCH_VAL;
		UserQhzxVerify ver = new UserQhzxVerify();
		
		//先查询一鉴通数据是否存在
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("idNo", idCard);
		queryMap.put("name", userName);
		List<UserQhzxVerify> qhVerifyList = userQhzxVerifyService.queryCountByInfo(queryMap);
		if(qhVerifyList!=null && qhVerifyList.size()>0){
			logger.info("查询到" + queryMap.get("name") + "," + queryMap.get("idNo") + "在一鉴通表中存在");
			//有且只允许有一条
			UserQhzxVerify verify = qhVerifyList.get(0);
			ver.setId(verify.getId());
			//判断本地库中一鉴通结果是否一致
			isRealIdentity = verify.getIsRealIdentity();
			if (StatusConstants.QHZX_IDNO_NAME_MATCH_VAL.equals(isRealIdentity)) {
				logger.info(queryMap.get("name") + "和" + queryMap.get("idNo") + "在本地库中存储结果为身份一致。");
				//判断本地库中姓名与查询姓名是否一致
				if (queryMap.get("name").equals(verify.getName())) {
					logger.info("查询的姓名" + queryMap.get("name") + ",本地库中姓名：" + verify.getName() + ",结果一致。");
					isRealIdentity = StatusConstants.QHZX_IDNO_NAME_MATCH_VAL;
				}else{
					logger.info("查询的姓名" + queryMap.get("name") + ",本地库中姓名：" + verify.getName() + ",结果不一致。");
					isRealIdentity = StatusConstants.QHZX_IDNO_NAME_NOT_MATCH_VAL; //需要插入不一致的假人
				}
			}else if(EntityUtils.isEmpty(isRealIdentity) || StatusConstants.QHZX_IDNO_NAME_NO_MATCH_VAL.equals(isRealIdentity)){
				logger.info("查询一鉴通数据库中一致性结果：" + isRealIdentity + ",需要调用一鉴通接口查询。");
			}else{
				logger.info(queryMap.get("name") + "和" + queryMap.get("idNo") + "在本地库中存储结果为身份不一致。");
				//判断本地库中姓名与查询姓名是否一致
				if (queryMap.get("name").equals(verify.getName())) {
					logger.info("查询的姓名" + queryMap.get("name") + ",本地库中姓名：" + verify.getName() + ",姓名相同，明确结果不一致。");
					isRealIdentity = StatusConstants.QHZX_IDNO_NAME_NOT_MATCH_VAL;
				}else{
					logger.info("查询的姓名" + queryMap.get("name") + ",本地库中姓名：" + verify.getName() + ",姓名不同，需要调用一鉴通接口确定身份是否一致");
				}
			}
			
		}else{
			logger.info("未查询到一鉴通数据,需要调用一鉴通接口,查询一致性。");
		}
		ver.setIsRealIdentity(isRealIdentity);
		logger.info("查询一鉴通一致性开始。。。。");
		
		return ver;
	}

	@Override
	public List<UserJyzxRecordItem> queryJyCreditResult(String userName, String idCard) {
		 Map<String, Object> queryMap = new HashMap<String,Object>();
		 queryMap.put("name",userName);
		 queryMap.put("idNo", idCard);
		//查询本地库是否存在91借贷借款数据
		List<UserJyzxRecordItem> list = userCreditDataDAO.queryExist91LoanList(queryMap);
		
		List<UserJyzxRecordItem> result = new ArrayList<UserJyzxRecordItem>();
		String trxNo = null;
		if (list != null && list.size() > 0) {
			for(UserJyzxRecordItem item : list){
				if(EntityUtils.isEmpty(trxNo)){
					trxNo = item.getTrxNo();
				}
				
				if(EntityUtils.isEmpty(trxNo) || !trxNo.equals(item.getTrxNo())){
					continue;
				}
				
				int day = 0 ;
				try {
					day = DateUtil.daysBetween(item.getCreateTime(),new Date());
					
					if (day < 90) {
						logger.info("查询核心91借贷接口，计算获取天数未超过90天，结束征信点。" + day  + "天。");
						result.add(item);
					}
				} catch (Exception e) {
					logger.info("查询核心91借贷接口，计算获取天数异常。",e);
					
				}
				logger.info("查询核心91借贷接口，计算获取天数超过90天，结束征信点。" + day  + "天。调用91借贷接口");
			}
		}
		
		return result;
		
	}
	

	@Override
	public Map<String, Object> queryExistQhBlackListData(String name ,String idCard,String infoId) throws Exception {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		 Map<String, Object> queryMap = new HashMap<String,Object>();
		 queryMap.put("name",name);
		 queryMap.put("idNo", idCard);
		//查询本地库是否存在征信过的黑名单数据
		 List<UserQhzxBlackList> list =  userCreditDataDAO.queryExistQhBlackListData(queryMap) ;
		if (list != null && list.size() > 0) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_SUCCESS);
			retMap.put(ResponseConstants.RESULT_DESC, "库中已存在前海黑名单");
			retMap.put(ResponseConstants.DATA, JSONObject.fromObject(list.get(0)).toString());
			return  retMap;
		}
		Map<String, Object>  userQhzxMsc8004Map = qhzxService.requestQhBlackList(idCard, name,null,infoId);
		return  userQhzxMsc8004Map;
		
	}

	@Override
	public Map<String, Object> queryExistBrBlackListData(UserBrRequest brRequestPo) throws Exception {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		 List<UserBrSpeciallistC> brSpeciallistCList = userCreditDataDAO.queryExistBrBlackListData(brRequestPo);
		 if (brSpeciallistCList != null && brSpeciallistCList.size() > 0) {
			logger.info("库中已经存在百融黑名单，不再继续请求百融");
			retMap.put("portrait", brSpeciallistCList);
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RESULT_DESC, "库中已经存在百融黑名单，不再继续请求百融");
			return retMap;
		}
		 Map<String, Object> paramMap = new HashMap<>();
	     paramMap.put("UserBrRequest", brRequestPo);
		 Map<String, Object> brSpeciallistCMap = (Map<String, Object>) brzxService.requestBR(paramMap);
		 return brSpeciallistCMap ;
		 
	}

	@Override
	public Map<String, Object> queryExistQhLoanList(String userName, String idCard,String infoId) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", userName);
		map.put("idCard", idCard);
		List<UserQhzxLoan> userQhzxLoanList = userCreditDataDAO.queryExistQhLoanList(map);
		
		if (userQhzxLoanList != null && userQhzxLoanList.size() > 0) {
			UserQhzxLoan userQhzxLoan = userQhzxLoanList.get(0);
			if (userQhzxLoan != null) {
				//判断获取的时间是否超过90天
				int day = 0 ;
				try {
					day = DateUtil.daysBetween(userQhzxLoan.getCreateTime(),new Date());
				} catch (Exception e) {
					logger.error("查询核心前海常贷客接口，计算获取天数异常。",e);
				}

				if (day < 90) {
					logger.info("查询核心前海常贷客接口，计算获取天数未超过90天，结束征信点。" + day  + "天。");
					retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_SUCCESS);
					retMap.put(ResponseConstants.RESULT_DESC, "解析前海常贷客报文成功");
					retMap.put(ResponseConstants.DATA, JSONObject.fromObject(userQhzxLoanList.get(0)).toString());
					return retMap;
				}
				logger.info("查询核心前海常贷客接口，计算获取天数超过90天，结束征信点。" + day  + "天。调用常贷客接口");
			}
		}
		

		Map<String, Object>  userQhzxMsc8037Map = qhzxService.requestQhLoanList(idCard, userName,null,infoId);
		return  userQhzxMsc8037Map;
	}

	@Override
	public Map<String, Object> queryExistBrApplyList(UserBrRequest brRequestPo) throws Exception {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		 List<BrApplyLoan> brapplyLoanList = userCreditDataDAO.queryExistBrApplyList(brRequestPo);
		 
		 if (brapplyLoanList != null && brapplyLoanList.size() > 0) {
			 BrApplyLoan userBrApplyLoan = brapplyLoanList.get(0);
				try {
					if (userBrApplyLoan != null) {//&& brCreditResult(userBrApplyLoan)
						//判断获取的时间是否超过90天
						int day = 0 ;
						try {
							day = DateUtil.daysBetween(userBrApplyLoan.getCreateTime(),new Date());
						} catch (Exception e) {
							logger.error("查询核心百融多次核查接口，计算获取天数异常。",e);
						}
						if (day < 90) {
							logger.info("查询核心百融多次核查接口，计算获取天数未超过90天，结束征信点。" + day  + "天。");
							logger.info("库中已经存在该多次申请请求，不再继续请求百融");
							BrApplyLoan brapplyLoan=brapplyLoanList.get(0);
							retMap.put("portrait", brapplyLoan);
							retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.SUCCESS);
							retMap.put(ResponseConstants.RESULT_DESC, "库中已经存在该多次申请请求，不再继续请求百融");
							return retMap;
						}
						logger.info("查询核心百融多次核查接口，计算获取天数超过90天，结束征信点。" + day  + "天。调用常贷客接口");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		 
		 Map<String, Object> paramMap = new HashMap<>();
	     paramMap.put("UserBrRequest", brRequestPo);
		 Map<String, Object> brApplyLoanMap = (Map<String, Object>) brzxService.requestBR(paramMap);
		 return brApplyLoanMap ;
	}
	
	@Override
	public  Map<String, Object> queryQhCreditList(String userName , String idCard ,String telNumber ,String bankCardNumber ,String busId) throws Exception{
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		UserQhzxCredit userQhzx = new UserQhzxCredit();
		userQhzx.setName(userName);
		userQhzx.setIdNo(idCard);
		userQhzx.setMobileNo(telNumber);
		List<UserQhzxCredit> qhCreditList = userQhzxCreditDao.queryQhzxCredit(userQhzx);
		if (qhCreditList != null && qhCreditList.size() > 0) {
			logger.info("库中已经存在该多次申请请求，qhCreditList再继续请求百融");
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RESULT_DESC, "库中已经存在该多次申请请求，不再继续请求前海");
			retMap.put(ResponseConstants.DATA, JSONObject.fromObject(qhCreditList.get(0)).toString());
			return retMap;
		}

	     Map<String, Object> userQhzxMsc8005Map = qhzxService.requestQhCredit(idCard, userName, bankCardNumber, telNumber, null, busId);
		 return userQhzxMsc8005Map ;
	}
	
	
	public static boolean brCreditResult(Object e) throws Exception{  
		boolean flag = false;
		String ignore = new String("id,brReqId,code,swiftNumber,createTime");
		
        Class cls = e.getClass();  
        Field[] fields = cls.getDeclaredFields();  
        for(int i=0; i<fields.length; i++){  
            Field f = fields[i];  
            f.setAccessible(true);  
            logger.info("属性名:" + f.getName() + " 属性值:" + f.get(e)); 
            if (EntityUtils.isEmpty(f.get(e)) && !Arrays.asList(ignore.split(",")).contains(f.getName())) {
            	flag =  false;
			}else if(EntityUtils.isNotEmpty(f.get(e)) && !"0".equals(f.get(e).toString())  && !Arrays.asList(ignore.split(",")).contains(f.getName())){
				flag =  true;
				break;
			}
        }   
        
        return flag;
    }

	@Override
	public String queryJyRecordId(String trxNo) {
		return userCreditDataDAO.queryJyRecordId(trxNo);
	}

}
