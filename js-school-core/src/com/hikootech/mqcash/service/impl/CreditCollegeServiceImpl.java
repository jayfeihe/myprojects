package com.hikootech.mqcash.service.impl;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hikootech.mqcash.dao.CreditCollegeDAO;
import com.hikootech.mqcash.dao.StudentWhiteListDAO;
import com.hikootech.mqcash.dao.UserCreditDataDAO;
import com.hikootech.mqcash.dao.UserQhzxCreditDAO;
import com.hikootech.mqcash.po.BlackList;
import com.hikootech.mqcash.po.BrApplyLoan;
import com.hikootech.mqcash.po.DepartmentInfo;
import com.hikootech.mqcash.po.DistrictInfo;
import com.hikootech.mqcash.po.JsschoolEducationScore;
import com.hikootech.mqcash.po.StudentWhiteList;
import com.hikootech.mqcash.po.UserBrRequest;
import com.hikootech.mqcash.po.UserBrSpeciallistC;
import com.hikootech.mqcash.po.UserCreditModelRecord;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.po.UserJyzxRecord;
import com.hikootech.mqcash.po.UserJyzxRecordItem;
import com.hikootech.mqcash.po.UserQhzxBlackList;
import com.hikootech.mqcash.po.UserQhzxCredit;
import com.hikootech.mqcash.po.UserQhzxLoan;
import com.hikootech.mqcash.po.UserQhzxVerify;
import com.hikootech.mqcash.po.UserSchoolRoll;
import com.hikootech.mqcash.po.WhiteList;
import com.hikootech.mqcash.qhzx.CommonUtil;
import com.hikootech.mqcash.service.CheckSchoolService;
import com.hikootech.mqcash.service.ConfigCreditKvService;
import com.hikootech.mqcash.service.CreditCollegeService;
import com.hikootech.mqcash.service.QhzxService;
import com.hikootech.mqcash.service.SysAlarmService;
import com.hikootech.mqcash.service.UserCreditDataService;
import com.hikootech.mqcash.service.UserJyzxService;
import com.hikootech.mqcash.util.BRUtils;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.ValidateAPIPlatParamsTools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("creditCollegeService")
public class CreditCollegeServiceImpl implements CreditCollegeService {
	
	private static Logger logger = LoggerFactory.getLogger(CreditCollegeServiceImpl.class);
	@Autowired
	private UserCreditDataService userCreditDataService;
	@Autowired
	private CreditCollegeDAO creditCollegeDAO;
	@Autowired
	private QhzxService qhzxService;
	@Autowired
	private SysAlarmService sysAlarmService;
	@Autowired
	private UserJyzxService userJyzxService;
	@Autowired
	private ConfigCreditKvService configCreditKvService;
	@Autowired
	private UserCreditDataDAO userCreditDataDAO;
	@Autowired
	private CheckSchoolService checkSchoolService;
	@Autowired
	private UserQhzxCreditDAO userQhzxCreditDao;
	@Autowired
	private StudentWhiteListDAO studentWhiteListDAO;
	
	
	@Override
	public Map<String, String> requestCredit(Map<String, String> reqMap) {
		
		logger.info("进入校园征信CreditCollegeServiceImpl");
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
		
		String busId = reqMap.get("busId");				//业务表主键
		String userName = reqMap.get("userName");			//用户姓名
		String idCard = reqMap.get("idCard");				//用户身份证
		String idCardAddress = reqMap.get("idCardAddress");	//身份证地址
		String telNumber = reqMap.get("telNumber");			//联系电话
		String channelId = reqMap.get("channelId");			//渠道id,用户所使用的平台（1：微信 2：APP 3：web）
		String custIp = reqMap.get("custIp");				//用户IP地址
		String schoolName = reqMap.get("schoolName");		//所在院校--院校全称
		String department = reqMap.get("department");		//所在院系--院系全称
		String entranctDate = reqMap.get("entranctDate");	//入学时间
		String partnerId = reqMap.get("partnerId");	//合作伙伴ID
		
		Map<String, String> returnMap = new HashMap<String,String>();

		//插入征信结果记录表
		UserCreditRecord creditRecord = new UserCreditRecord();
		String creditId = GenerateKey.getId(CommonConstants.CREDIT_RECORD_ID_PREFIX, ConfigUtils.getProperty("db_id"));
		//初始化征信结果记录表信息
		try {
			Integer isPass = StatusConstants.CREDIT_IS_NOT_PASS;// 1
			String reason = CreditCodeConstants.CREDIT_RECORD_INIT.toString(); // 征信不通过
			
			creditRecord.setCreditId(creditId);
			creditRecord.setIdCard(idCard);
			creditRecord.setName(userName);
			creditRecord.setIdCardAddress(idCardAddress);
			creditRecord.setCustIp(custIp);
			creditRecord.setContactPhone(telNumber);
			creditRecord.setSource(Integer.parseInt(channelId));
			creditRecord.setOperator(CommonConstants.DEFAULT_OPERATOR);
			
			creditRecord.setInfoId(busId);
			creditRecord.setIsPass(isPass);
			creditRecord.setReason(reason);
			creditRecord.setCreateTime(new Date());
			creditRecord.setUpdateTime(new Date());
			userCreditDataService.insertUserCreditRecord(creditRecord);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用校园征信接口，初始化征信数据，插入征信记录表异常。infoId:" + reqMap.get("infoId") +  ",idCard:" + idCard + "，name：" + userName,e);
			String alarmContent = "调用校园征信接口，初始化征信数据，插入征信记录表异常。idCard:" + idCard ;
			return sysAlarmService.exceptionAlarm(alarmContent);
		}
		
		String _CROpen = "1" ;//征信结果默认生效
		
		/*******************************************************秒趣白名单**************************************************************************/
		String _WLOpen = (String) (meMap.get(CreditCfgKeyConstants.WL_CR_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_WLOpen)) {
			logger.info("白名单-->调用模快开关开启。");	
			try {
				Map<String, String> map = new HashMap<String,String>();
				map.put("name", userName);
				map.put("idCard", idCard);
				map.put("contactPhone", telNumber);
				map.put("partnerId", partnerId);
				WhiteList isWhiteList = userCreditDataService.queryWhiteList(map);
				if (isWhiteList != null) {
					logger.info("调用征信接口，白名单结果开关生效，匹配秒趣白名单,征信成功。idCard:" + idCard + "，name：" + userName);
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_PASS,CreditCodeConstants.CREDIT_WL_RECORD_PASS);
					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_WL_RECORD_PASS);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), "1", CreditCodeConstants.CREDIT_HD_WHITE, CreditCodeConstants.CREDIT_TYPE_HD_WHITE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_WL_RECORD_PASS,isWhiteList.getListId());
					returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_SUCCESS);
					returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_SUCCESS_DESC);
					return returnMap; 
				}else {
					logger.info("调用征信接口，不是秒趣白名单。");
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_HD_WHITE, CreditCodeConstants.CREDIT_TYPE_HD_NOWHITE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_UNWL_RECORD_PASS,null);
				}
				
			} catch (Exception e) {
				logger.error("调用白名单--> 数据异常: busId:" + busId +  ",idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ,e);
				this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_HD_WHITE, CreditCodeConstants.CREDIT_TYPE_HD_NOWHITE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_MQ_RECORD_WHITE_ERROR,null);
				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_MQ_RECORD_WHITE_ERROR); 
				String alarmContent = "调用白名单--> 数据异常: busId:" + busId +  ",idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ;
				return sysAlarmService.exceptionAlarm(alarmContent);
			}
		}else {
			logger.info("白名单-->开关关闭。");
		}
		/*******************************************************秒趣白名单**************************************************************************/
		
		/*******************************************************新生白名单**************************************************************************/
		logger.info("新生白名单征信判断开始");
		Map<String, Object> stdMap = null;
		try {
			stdMap = this.creditStudentWhiteList(reqMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("新生白名单征信判断异常");
			this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREITID_MODEL_NEW_STUDENT, CreditCodeConstants.CREITID_MODEL_TYPE_NEW_STUDENT, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREITID_RESULT_DESC_EXCEPTION_NEW_STUDENT,null);
			updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREITID_RESULT_DESC_EXCEPTION_NEW_STUDENT); 
			String alarmContent = "调用白名单--> 数据异常: busId:" + busId +  ",idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ;
			return sysAlarmService.exceptionAlarm(alarmContent);
		}
		
		if(EntityUtils.isNotEmpty(stdMap) && TelecomConstants.CRE_SUCCESS.equals(stdMap.get(ResponseConstants.RETURN_CODE))){
			logger.info("新生白名单征信判断通过，征信结束。");
			StudentWhiteList studentWhiteList = (StudentWhiteList) stdMap.get(ResponseConstants.DATA);
			
			userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_PASS,CreditCodeConstants.CREITID_RESULT_DESC_PASS_NEW_STUDENT);
			updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREITID_RESULT_DESC_PASS_NEW_STUDENT);
			this.insertShoolCreditModelRecord(creditRecord.getCreditId(), "1", CreditCodeConstants.CREITID_MODEL_NEW_STUDENT, 
					CreditCodeConstants.CREITID_MODEL_TYPE_NEW_STUDENT, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, 
					CreditCodeConstants.CREITID_RESULT_DESC_PASS_NEW_STUDENT, studentWhiteList.getId());
			returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_SUCCESS);
			returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_SUCCESS_DESC);
			return returnMap;
		}else{
			logger.info("新生白名单征信判断未通过，征信继续。");
			this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREITID_MODEL_NEW_STUDENT, CreditCodeConstants.CREITID_MODEL_TYPE_NEW_STUDENT, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREITID_RESULT_DESC_UNPASS_NEW_STUDENT,null);
		}
		
		logger.info("新生白名单征信判断结束");
		/*******************************************************新生白名单**************************************************************************/

		
		
		/*******************************************************前海一鉴通**************************************************************************/
		String _verifyOpen = (String) (meMap.get(CreditCfgKeyConstants.VERIFY_MODEL_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_verifyOpen)) {//调用一鉴通开关打开
			logger.info("调用前海一致开关打开。");	
			try {
				UserQhzxVerify isVerify = userCreditDataService.queryVerify(idCard, userName,telNumber,busId);
				if (StatusConstants.QHZX_IDNO_NAME_NO_MATCH_VAL.equals(isVerify.getIsRealIdentity())) { //需要调用一鉴通接口查询
					Map<String, Object> userQhzxVerifyMap = qhzxService.reqQhzxVerify(idCard, userName, telNumber, null, busId);
					
					//异常情况或参数为空情况
					if (EntityUtils.isEmpty(userQhzxVerifyMap.get(ResponseConstants.RESULT_CODE))|| EntityUtils.isEmpty(userQhzxVerifyMap.get(ResponseConstants.DATA)) 
							|| !ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals((String)userQhzxVerifyMap.get(ResponseConstants.RESULT_CODE))) {
						logger.info("前海一致性校验，处理前海时发生问题，问题描述：" + userQhzxVerifyMap.get(ResponseConstants.RESULT_DESC)+ ",返回码：" + userQhzxVerifyMap.get(ResponseConstants.RESULT_CODE) + ",DATA:" + userQhzxVerifyMap.get(ResponseConstants.DATA));
						userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_HXYJTEXP_ERROR);
						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_HXYJTEXP_ERROR);
						this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_HXYJTEXP_ERROR,null);
						String alarmContent = "前海一致性校验，处理前海时发生问题，问题描述：" + userQhzxVerifyMap.get(ResponseConstants.RESULT_DESC);
						return sysAlarmService.exceptionAlarm(alarmContent);
					}
						
					//前海正常的处理
					UserQhzxVerify userQhzxVerify = CommonUtil.parseJSON2Bean(userQhzxVerifyMap.get(ResponseConstants.DATA).toString(), UserQhzxVerify.class);
					
					
					if (EntityUtils.isEmpty(userQhzxVerify.getIsRealIdentity())) {
						String result = userQhzxVerify == null?null: userQhzxVerify.getIsRealIdentity();
						logger.info("前海一致性校验未通过,校验结果为["+ result +"],身份证号：" + idCard + "，姓名：" + userName);
						userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_SAME_UNPASS);
						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_SAME_UNPASS);
						this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_SAME_UNPASS,userQhzxVerify.getId());
						returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_QH_YJT_CODE);
						returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_QH_YJT_CODE_DESC);
						return returnMap;
					}
					
					//判断是否为真是身份
					if(StatusConstants.QHZX_IDNO_NAME_NOT_MATCH_VAL.equals(userQhzxVerify.getIsRealIdentity())){
						logger.info("前海一致性校验明确返回未通过，征信失败。身份证号：" + idCard + "，姓名：" + userName);
						userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS);
						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS);
						this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS,userQhzxVerify.getId());
						returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_QH_YJT_CODE);
						returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_QH_YJT_CODE_DESC);
						return returnMap;
					}
					
					if(StatusConstants.QHZX_IDNO_NAME_MATCH_VAL.equals(userQhzxVerify.getIsRealIdentity())){
						logger.info("前海一致性校验通过,继续向下执行");
						this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_SAME_PASS,userQhzxVerify.getId());
					}else{
						logger.info("前海一致性校验未通过,校验结果异常,身份证号：" + idCard + "，姓名：" + userName);
						userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_HXYJT_ERROR);
						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_HXYJT_ERROR);
						this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_HXYJT_ERROR,userQhzxVerify.getId());
						returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.API_SYSTEM_EXCEPTION);
						returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
						return returnMap;
					}
				}else if(StatusConstants.QHZX_IDNO_NAME_NOT_MATCH_VAL.equals(isVerify.getIsRealIdentity())){
					logger.info("查询一鉴通本地库，一鉴通身份信息匹配失败，不通过。身份证号：" + idCard + "，姓名：" + userName);
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS);
					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS,isVerify.getId());
					returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_QH_YJT_CODE);
					returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_QH_YJT_CODE_DESC);
					return returnMap;
					
				}else{
					logger.info("查询一鉴通本地库，一鉴通身份信息匹配成功，通过。");
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_SAME_PASS,isVerify.getId());
				}
			} catch (Exception e) {
				logger.error("调用前海一鉴通--> 数据异常: busId:" + busId +  ",idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ,e);
				this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants. CREDIT_QH_RECORD_HXYJT_ERROR.toString(),null);
				updateJsSchoolBusCreditResult(busId,CreditCodeConstants. CREDIT_QH_RECORD_HXYJT_ERROR);  
				String alarmContent = "调用前海一鉴通--> 数据异常: busId:" + busId +  ",idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ;
				return sysAlarmService.exceptionAlarm(alarmContent);
			}
		}else{
			logger.info("调用一鉴通开关关闭。");
		}
		/*******************************************************前海一鉴通**************************************************************************/

		
		/*******************************************************年龄检查**************************************************************************/
		//请求年龄开关
		String _AgeOpen = (String) (meMap.get(CreditCfgKeyConstants.AGE_MODEL_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_AgeOpen)) {
			logger.info("调用年龄判断-->调用年龄判断开关开启。");
			try {
				
				String configAgeSection = (String) (meMap.get(CreditCfgKeyConstants.COLLEGE_AGE_LIMIT_SECTION));
				logger.info("校园征信，查询出配置可以调用征信系统的年龄段为:" + configAgeSection);
				//检查年龄合法性
				boolean isCreditAge = userCreditDataService.isCreditByAge(idCard,configAgeSection);
				if (isCreditAge) {
					logger.info("年龄结果开关生效,年龄未通过,身份证号：" + idCard + "，姓名：" + userName);
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_AGE_RECORD_UNPASS);
					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_AGE_RECORD_UNPASS);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_AGE, CreditCodeConstants.CREDIT_TYPE_AGE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_AGE_RECORD_UNPASS,null);
					returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
					returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
					return returnMap;
				}else{
					logger.info("年龄通过。");		
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_AGE, CreditCodeConstants.CREDIT_TYPE_AGE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_AGE_RECORD_PASS,null);
		
				}
			} catch (Exception e) {
				logger.error("调用年龄判断异常：busId:" + busId +  ",身份证号：" + idCard + "，姓名：" + userName , e);
				this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_AGE, CreditCodeConstants.CREDIT_TYPE_AGE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_AGE_RECORD_RROR.toString(),null);
				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_AGE_RECORD_RROR);  
				String alarmContent = "调用年龄判断异常：busId:" + busId +  ",身份证号：" + idCard + "，姓名：" + userName ;
				 return sysAlarmService.exceptionAlarm(alarmContent);
			}
		}else {
			logger.info("调用年龄判断-->开关关闭。");
		}
		/*******************************************************年龄检查**************************************************************************/
		
		
		/*******************************************************秒趣黑名单**************************************************************************/
		String _BLOpen = (String) (meMap.get(CreditCfgKeyConstants.BL_CR_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_BLOpen)) {//0-关闭；1-开启
			logger.info("黑名单-->调用模快开关开启。");
			try {
				BlackList blackList = userCreditDataService.queryBlackList(idCard, telNumber);
				//征信结果判断开关
				if (blackList != null) {
					logger.info("调用征信接口，黑名单开关生效，命中秒趣黑名单。busId:" + busId +  ",idCard:" + idCard + "，name：" + userName);
					String blackType = "";
					if (StatusConstants.BLACK_MATCH_IDCARD_TYPE.toString().equals(blackList.getMatchType())) {//黑名单身份证
						blackType = CreditCodeConstants.CREDIT_TYPE_HD_BLACK_IDCARD;
					}else  if (StatusConstants.BLACK_MATCH_TELPHONE_TYPE.toString().equals(blackList.getMatchType())) {//黑名单手机号
						blackType = CreditCodeConstants.CREDIT_TYPE_HD_BLACK_PHONE;
					}
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BL_RECORD_UNPASS);
					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BL_RECORD_UNPASS);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_HD_BLACK, blackType, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BL_RECORD_UNPASS.toString(),blackList.getListId());
					returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
					returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
					return returnMap; 
					
				}else {
					logger.info("调用征信接口，黑名单开关生效，未命中黑名单。busId:" + busId +  ",idCard:" + idCard + "，name：" + userName);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_HD_BLACK, CreditCodeConstants.CREDIT_TYPE_HD_NOBLACK, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_BL_RECORD_PASS,null);
				}
			} catch (Exception e) {
				logger.error("调用黑名单--> 返回数据异常:busId:" + busId +  ", idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ,e);
				this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_HD_BLACK, CreditCodeConstants.CREDIT_TYPE_HD_NOBLACK, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_MQ_RECORD_BLACK_ERROR.toString(),null);
				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_MQ_RECORD_BLACK_ERROR);  
				String alarmContent = "调用黑名单--> 返回数据异常:busId:" + busId +  ", idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ;
				 return sysAlarmService.exceptionAlarm(alarmContent);
			}
		}else {
			logger.info("调用黑名单模快开关关闭。");
		}
		/*******************************************************秒趣黑名单**************************************************************************/
		
		
		/*******************************************************历史黑名单**************************************************************************/
		logger.info("查询历史黑名单开始");
		try{
			//校验该用户之前是否已经命中了前海或者百融黑名单
			//UserCreditRecord isBlackUser = this.isBlackUser(idCard,userName);
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("idCard", idCard);
			map.put("name",userName);
			List<UserQhzxBlackList> qhBlackList = creditCollegeDAO.queryIsQhBlackUser(map);
			
			String id = "";
			String blackUserResult = "" ;
			String creditModelType = "" ;
			if (qhBlackList != null && qhBlackList.size() > 0 ) {
				for(UserQhzxBlackList qhBlack : qhBlackList){
					if ("E000000".equals(qhBlack.getErCode())) {
						id = qhBlack.getId();
						logger.info("命中前海历史黑名单，前海黑名单id为：" + id );
						blackUserResult = CreditCodeConstants.CREDIT_QH_RECORD_BL_UNPASS;
						creditModelType = CreditCodeConstants.CREDIT_QH_BLACKLIST_REFUSE;
						break;
					}
				}
			}
			
			if(EntityUtils.isEmpty(id)){
				logger.info("未命中前海历史黑名单，查询是否命中百融历史黑名单。");
				List<UserBrSpeciallistC> brSpeciallistList = creditCollegeDAO.queryIsBrBlackUser(map);
				if (brSpeciallistList != null && brSpeciallistList.size() > 0 ) {
					for(UserBrSpeciallistC brBlack : brSpeciallistList){
						if (BRUtils.speciallistCreditResult(brBlack)) {
							id = brBlack.getId();
							logger.info("命中百融历史黑名单，百融黑名单id为：" + id );
							blackUserResult = CreditCodeConstants.CREDIT_BR_RECORD_BL_UNPASS;
							creditModelType = CreditCodeConstants.CREDIT_BR_SL_BLACKLIST_REFUSE;
							break;
						}
					}
				}
			}
			
			if (EntityUtils.isNotEmpty(id)) {

				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,blackUserResult);
				updateJsSchoolBusCreditResult(busId,blackUserResult);
				this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BLACKLIST_REFUSE, creditModelType, StatusConstants.CREDIT_CHECKING_RESULT_FAILED,blackUserResult,id);
				returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
				returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
				return returnMap; 
			}else{
				logger.info(userName + "," + idCard + "未命中黑名单性质拒贷规则。");
				this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BLACKLIST_REFUSE, CreditCodeConstants.CREDIT_QH_BLACKLIST_UNCHECKED, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_RECORD_BLACK_PASS.toString(),null);
	
			}
		}catch(Exception e){
			logger.error(userName + "," + idCard + "，查询历史黑名单异常",e);
			this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BLACKLIST_REFUSE, CreditCodeConstants.CREDIT_QH_BLACKLIST_UNCHECKED, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BL_HISTORY_EXP_ERROR.toString(),null);
			updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BL_HISTORY_EXP_ERROR); 
			 String alarmContent = userName + "," + idCard + "院校评分异常。";
			 return sysAlarmService.exceptionAlarm(alarmContent);
		}
		logger.info("查询历史黑名单结束");

		/*******************************************************历史黑名单**************************************************************************/

		/*******************************************************院校评分**************************************************************************/
		String _SSCOREOpen = (String) (meMap.get(CreditCfgKeyConstants.STUDENT_SCORE));
		if (CreditCfgKeyConstants.OPEN.equals(_SSCOREOpen)) {
			logger.info("调用学历评分-->调用开关开启。");
			try {
				JsschoolEducationScore collegeScore = this.queryCollegeScore(schoolName,department,entranctDate,busId);
				if (!"0".equals(collegeScore.getEducateResult())) {
					logger.info(userName + "," + idCard + "学历评分拒贷。");
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_EDUCATE_SCORE_UNPASS);
					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_EDUCATE_SCORE_UNPASS);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_EDUCATION_SCORE, CreditCodeConstants.CREDIT_EDUCATION_SCORE_MODEL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED,CreditCodeConstants.CREDIT_EDUCATE_SCORE_UNPASS,collegeScore.getId());
					returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
					returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
					return returnMap; 
				}else{
					logger.info(userName + "," + idCard + "学历评分通过。");
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_EDUCATION_SCORE, CreditCodeConstants.CREDIT_EDUCATION_SCORE_MODEL, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS,CreditCodeConstants.CREDIT_EDUCATE_SCORE_PASS,collegeScore.getId());
				}
				
			} catch (Exception e) {
				logger.info(userName + "," + idCard + "院校评分异常。");
				this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_EDUCATION_SCORE, CreditCodeConstants.CREDIT_EDUCATION_SCORE_MODEL, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_XL_RECORD_SCOREEXP_ERROR.toString(),null);
				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_XL_RECORD_SCOREEXP_ERROR); 
				String alarmContent = userName + "," + idCard + "院校评分异常。";
				 return sysAlarmService.exceptionAlarm(alarmContent);
			}
		}else{
			logger.info("调用学历评分-->调用开关关闭。");
		}
		/*******************************************************院校评分**************************************************************************/
		
		
		/*******************************************************请求91征信**************************************************************************/
		String _91Open = (String) (meMap.get(CreditCfgKeyConstants.JY_MODEL_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_91Open)) {//可以请求91征信
			logger.info("调用91征信-->调用模快开关开启。");
			try {
				UserJyzxRecord jyzxRecord = userJyzxService.queryLastestEffectJyzxRecord(idCard, userName);
				
				if(EntityUtils.isNotEmpty(jyzxRecord)){
//					List<UserJyzxRecordItem> itemList = userCreditDataService.queryJyCreditResult(userName,idCard);
					List<UserJyzxRecordItem> itemList = userJyzxService.queryUserJyzxRecordItemsByTrxNo(jyzxRecord.getTrxNo());
					//根据91规则判断结果
					Map<String, Object> retMap = userJyzxService.judgeStudentCreditInfos(busId, jyzxRecord, itemList);
					if(EntityUtils.isEmpty(retMap) 
							|| EntityUtils.isEmpty(retMap.get(ResponseConstants.RETURN_CODE))
							|| TelecomConstants.CRE_FAIL.equals(retMap.get(ResponseConstants.RETURN_CODE))){
						logger.info("91本地有数据，并且征信不通过。");
						returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
						returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
						return returnMap;
					}
				}else{
					//调用91征信接口
					String collegeJyWaitTime = (String) (meMap.get(CreditCfgKeyConstants.COLLEGE_JY_WAIT_TIME));
					logger.info("调用91征信，配置等待时间：" + collegeJyWaitTime + "秒。");
					Date startDate = new Date();
					logger.info("调用91征信，开始时间" + DateUtil.formatDate(startDate, DateUtil.FORMAT_SS));
					userCreditDataService.requestJYCredit(userName,idCard,telNumber,busId,StatusConstants.JY_CALL_TYPE_STUDENT_CREDIT);
					Date endDate = new Date();
					logger.info("调用91征信，开始时间" + DateUtil.formatDate(endDate, DateUtil.FORMAT_SS));
					long interval = (endDate.getTime() - startDate.getTime())/1000;
					logger.info("调用91征信耗时：" + String.valueOf(interval) + "秒。");
					
					if (EntityUtils.isEmpty(collegeJyWaitTime)) {
						logger.error("调用91征信,配置等待91时间为空--》COLLEGE_JY_WAIT_TIME");
					}else{
						String creditResult  = "";
						if (interval > Long.parseLong(collegeJyWaitTime)) {
							logger.info("调用91征信,耗时大于" + collegeJyWaitTime + "秒");
							creditResult = this.queryJyCreditResult(busId);
						}else{
							int leftTime = (int) (Long.parseLong(collegeJyWaitTime) - interval);
							logger.info("调用91征信，剩余时间：" + leftTime + "秒。");
							try {
								Thread.sleep(3000);
								logger.info("调用91征信，休息3秒");
							} catch (InterruptedException e) {
								logger.error("调用91征信,等待3秒异常。",e);
							}
							
							creditResult = this.queryJyCreditResult(busId);
							logger.info("查询91征信结果：" + creditResult);
							
						}
						
						//判断征信结果
						if (StatusConstants.CREDIT_CHECKING_RESULT_FAILED.toString().equals(creditResult)) {
							returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
							returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
							logger.info("调用91征信后，查询91征信结果：未通过。");
							return returnMap;
						}
					}
				}
				
			} catch (Exception e) {
				logger.error("调用91征信--> 调用91征信接口返回数据异常: busId:" + busId +  ",idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ,e);
				this.insertShoolCreditModelRecord(creditId, "1", CreditCodeConstants.CREDIT_91_BLACKLIST, CreditCodeConstants.CREDIT_TYPE_91_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_JY_RECORD_ERROR,null);
				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_JY_RECORD_ERROR);
				String alarmContent = "调用91征信接口返回数据异常: busId:" + busId +  ",idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName;
				 return sysAlarmService.exceptionAlarm(alarmContent);
			}
		}else {
			logger.info("调用91征信-->开关关闭。");
		}
		/*******************************************************91征信**************************************************************************/
		
		
		/*******************************************************前海黑名单**************************************************************************/
		String _QHOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_MODEL_BLACK_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_QHOpen)) {
			logger.info("调用前海黑名单征信-->调用开关开启。");
			try {
				logger.info("查询前海黑名单开始");
				Map<String, Object>  userQhzxMsc8004Map = userCreditDataService.queryExistQhBlackListData(userName,idCard,busId);
				if (EntityUtils.isEmpty(userQhzxMsc8004Map.get(ResponseConstants.RESULT_CODE)) 
						|| !ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals((String)userQhzxMsc8004Map.get(ResponseConstants.RESULT_CODE))) {
					logger.info("处理前海黑名单接口时发生问题，问题描述：" + userQhzxMsc8004Map.get(ResponseConstants.RESULT_DESC) + ",返回码：" + userQhzxMsc8004Map.get(ResponseConstants.RESULT_CODE));
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_BLACKEXP_ERROR);
					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_BLACKEXP_ERROR);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_BLACKEXP_ERROR,null);
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
								logger.info("前海黑名单征信结果开关生效,,idCard:" + idCard + "，name：" + userName + ",配置为拒贷。");
								userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_BLACK_UNPASS);
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_BLACK_UNPASS);
								this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_BLACK_UNPASS,userQhzxMsc8004.getId());
								returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
								returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
								logger.info("前海征信结果开关打开，结果生效，命中黑名单。");
								return returnMap;
									
							}else {
								logger.info("前海黑名单征信结果开关生效,通过配置值：" + result + ",通过。" );
							}
							
						}else {
							logger.info("前海黑名单征信结果开关生效，未命中前海黑名单,idCard:" + idCard + "，name：" + userName);
						}
					} catch (Exception e) {
						throw new RuntimeException("前海黑名单，有数据，但数据异常",e);
					}
					
				}else{
					logger.info("前海黑名单征信结果开关生效，未查询到任何黑名单数据，未命中前海黑名单,idCard:" + idCard + "，name：" + userName);
	
				}
				this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_BLACK_PASS,null);
				
			} catch (Exception e1) {
				logger.error("调用前海征信黑名单接口（8004）异常：busId:" + busId +  ",身份证号：" + idCard + "，姓名：" + userName , e1);
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_BLACKE_RROR);
				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_BLACKE_RROR);
				this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_BLACKEXP_ERROR,null);
				//插入告警表
				String alarmContent = "调用前海征信黑名单接口（8004）异常,idCard："+  idCard + "，姓名：" + userName ;
				return sysAlarmService.exceptionAlarm(alarmContent);
			}
			logger.info("查询前海黑名单结束");
		}else{
			logger.info("调用前海征信黑名单-->开关关闭。");
		}
		/*******************************************************前海黑名单**************************************************************************/
		
		
		/*******************************************************百融黑名单**************************************************************************/
		String _BROpen = (String) (meMap.get(CreditCfgKeyConstants.BR_MODEL_SPECIAL_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_BROpen)) {
			logger.info("调用百融贷特殊名单核查征信-->调用开关开启。");
			try {
				UserBrRequest brRequestPo = new UserBrRequest();
				brRequestPo.setIdCard(idCard);
				brRequestPo.setName(userName);
				brRequestPo.setMobile(telNumber);
				brRequestPo.setTotalInfoId(busId);
				brRequestPo.setProductName(CommonConstants.BR_MEAL_SPECIALLIST_C);
				
				//查询百融特殊名单信息是否存在
				 Map<String, Object> brSpeciallyListMap = userCreditDataService.queryExistBrBlackListData(brRequestPo);
				 logger.info("brSpeciallyListMap" + JSONArray.fromObject(brSpeciallyListMap).toString());
			        
		    	//百融特殊名单结果生效开关
				 if (brSpeciallyListMap.get(ResponseConstants.RETURN_CODE).equals(ResponseConstants.SUCCESS)) {
	        		List<UserBrSpeciallistC> specialIdCard = (List<UserBrSpeciallistC>)brSpeciallyListMap.get("portrait");
	        		if (specialIdCard != null && specialIdCard.size() > 0 && BRUtils.speciallistCreditResult(specialIdCard.get(0))) {
						logger.info( userName + "命中百融贷特殊名单");
						userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_SL_UNPASS);
						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_SL_UNPASS);
						this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_SPECIAL_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_SL_UNPASS,specialIdCard.get(0).getBrReqId());
						returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
						returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
						return returnMap;
					}else{
						logger.info( userName + "未命中百融贷特殊名单，征信通过");
						this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_SPECIAL_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_BR_RECORD_SL_PASS,specialIdCard.get(0).getBrReqId());
					}
				}else{
					logger.info("调用百融特殊名单出现错误，错误码：" + brSpeciallyListMap.get(ResponseConstants.RETURN_CODE) + ",错误描述:" + brSpeciallyListMap.get(ResponseConstants.RETURN_DESC));
					String alarmContent = "调用百融特殊名单核查接口出错,返回错误代码：" + ",姓名：" + userName + ",身份证号：" + idCard;
					sysAlarmService.alarm(alarmContent);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_SPECIAL_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_SPECIALEXP_ERROR,null);

				}
				
			} catch (Exception e) {
				 logger.error("前调用百融贷特殊名单核查征信发生错误，身份证号：" + idCard + "，姓名：" + userName,e);
				 userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_SPECIALEXP_ERROR);
				 updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_SPECIALEXP_ERROR);
				 this.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_SPECIAL_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_SPECIALEXP_ERROR,null);
				 String alarmContent = "前调用百融贷特殊名单核查征信发生错误，身份证号：" + idCard + "，姓名：" + userName;
				 sysAlarmService.alarm(alarmContent);
			}
		}else{
			logger.info("调用百融贷特殊名单核查征信-->调用开关关闭。");
		}
		/*******************************************************百融黑名单**************************************************************************/
		
		
		/*******************************************************前海常贷客**************************************************************************/
		String _loanOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_MODEL_LOAN_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_loanOpen)) {
			logger.info("调用前海常贷客征信-->调用开关开启。");
			try {
				
				//查询本地库中是否存在常贷客信息
				Map<String, Object>  userQhzxMsc8037Map = userCreditDataService.queryExistQhLoanList(userName,idCard,busId);
				
				logger.info("前海征信前海常贷客接口，征信结果开关生效");
				if (EntityUtils.isEmpty(userQhzxMsc8037Map.get(ResponseConstants.RESULT_CODE)) 
						|| !ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals((String)userQhzxMsc8037Map.get(ResponseConstants.RESULT_CODE))) {
					logger.info("处理前海常贷客接口时发生问题，问题描述：" + userQhzxMsc8037Map.get(ResponseConstants.RESULT_DESC) + ",返回码：" + userQhzxMsc8037Map.get(ResponseConstants.RESULT_CODE));
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR);
					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR,null);
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
					
					boolean bakIsPass = userCreditDataService.loanCreditCompare(userQhzxMsc8037,bakData,"BAK");
					if (CreditCfgKeyConstants.OPEN.equals(bakIsOpen)) {
						logger.info("BAK命中生效开关开启，结果生效");
						if (bakIsPass) {
							logger.info("BAK命中");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
							return this.loanCreditResult(userQhzxMsc8037,creditRecord,"BAK",_CROpen);
						}else{
							logger.info("BAK未命中");
						}
					}else{
						logger.info("BAK命中生效开关关闭，征信通过。");
					}
					
					boolean mclIsPass = userCreditDataService.loanCreditCompare(userQhzxMsc8037,mclData,"MCL");
					if (CreditCfgKeyConstants.OPEN.equals(mclIsOpen)) {
						logger.info("MCL命中生效开关开启，结果生效");
						if (mclIsPass) {
							logger.info("MCL命中");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
							return this.loanCreditResult(userQhzxMsc8037,creditRecord,"MCL",_CROpen);
						}else{
							logger.info("MCL未命中");
						}
					}else{
						logger.info("MCL命中生效开关关闭，征信通过。");
					}
					
					boolean p2pIsPass = userCreditDataService.loanCreditCompare(userQhzxMsc8037,p2pData,"P2P");
					if (CreditCfgKeyConstants.OPEN.equals(p2pIsOpen)) {
						logger.info("P2P命中生效开关开启，结果生效");
						if (p2pIsPass) {
							logger.info("P2P命中");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
							return this.loanCreditResult(userQhzxMsc8037,creditRecord,"P2P",_CROpen);
						}else{
							logger.info("P2P未命中");
						}
					}else{
						logger.info("P2P命中生效开关关闭，征信通过。");
					}
					
					boolean asmIsPass = userCreditDataService.loanCreditCompare(userQhzxMsc8037,asmData,"ASM");
					if (CreditCfgKeyConstants.OPEN.equals(asmIsOpen)) {
						logger.info("ASM命中生效开关开启，结果生效");
						if (asmIsPass) {
							logger.info("ASM命中");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
							return this.loanCreditResult(userQhzxMsc8037,creditRecord,"ASM",_CROpen);
						}else{
							logger.info("ASM未命中");
						}
					}else{
						logger.info("ASM命中生效开关关闭，征信通过。");
					}
					boolean truIsPass = userCreditDataService.loanCreditCompare(userQhzxMsc8037,truData,"TRU");
					if (CreditCfgKeyConstants.OPEN.equals(truIsOpen)) {
						logger.info("TRU命中生效开关开启，结果生效");
						if (truIsPass) {
							logger.info("ASM命中");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
							return this.loanCreditResult(userQhzxMsc8037,creditRecord,"TRU",_CROpen);
						}else{
							logger.info("TRU未命中");
						}
					}else{
						logger.info("TRU命中生效开关关闭，征信通过。");
					}
					
					boolean leaIsPass = userCreditDataService.loanCreditCompare(userQhzxMsc8037,leaData,"LEA");
					if (CreditCfgKeyConstants.OPEN.equals(leaIsOpen)) {
						logger.info("LEA命中生效开关开启，结果生效");
						if (leaIsPass) {
							logger.info("LEA命中");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
							return this.loanCreditResult(userQhzxMsc8037,creditRecord,"LEA",_CROpen);
						}else{
							logger.info("LEA未命中");
						}
					}else{
						logger.info("LEA命中生效开关关闭，征信通过。");
					}
					
					boolean crfIsPass = userCreditDataService.loanCreditCompare(userQhzxMsc8037,crfData,"CRF");
					if (CreditCfgKeyConstants.OPEN.equals(crfIsOpen)) {
						logger.info("CRF命中生效开关开启，结果生效");
						if (crfIsPass) {
							logger.info("CRF命中");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
							return this.loanCreditResult(userQhzxMsc8037,creditRecord,"CRF",_CROpen);
						}else{
							logger.info("CRF未命中");
						}
					}else{
						logger.info("CRF命中生效开关关闭，征信通过。");
					}
					
					boolean invIsPass = userCreditDataService.loanCreditCompare(userQhzxMsc8037,invData,"INV");
					if (CreditCfgKeyConstants.OPEN.equals(invIsOpen)) {
						logger.info("INV命中生效开关开启，结果生效");
						if (invIsPass) {
							logger.info("INV命中");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
							return this.loanCreditResult(userQhzxMsc8037,creditRecord,"INV",_CROpen);
						}else{
							logger.info("INV未命中");
						}
					}else{
						logger.info("INV命中生效开关关闭，征信通过。");
					}
					
					boolean cnsIsPass = userCreditDataService.loanCreditCompare(userQhzxMsc8037,cnsData,"CNS");
					if (CreditCfgKeyConstants.OPEN.equals(cnsIsOpen)) {
						logger.info("CNS命中生效开关开启，结果生效");
						if (cnsIsPass) {
							logger.info("CNS命中");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
							return this.loanCreditResult(userQhzxMsc8037,creditRecord,"CNS",_CROpen);
						}else{
							logger.info("CNS未命中");
						}
					}else{
						logger.info("CNS命中生效开关关闭，征信通过。");
					}
					boolean insIsPass = userCreditDataService.loanCreditCompare(userQhzxMsc8037,insData,"INS");
					if (CreditCfgKeyConstants.OPEN.equals(insIsOpen)) {
						logger.info("INS命中生效开关开启，结果生效");
						if (insIsPass) {
							logger.info("INS命中");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
							return this.loanCreditResult(userQhzxMsc8037,creditRecord,"INS",_CROpen);
						}else{
							logger.info("INS未命中");
						}
					}else{
						logger.info("INS命中生效开关关闭，征信通过。");
					}
					
					boolean thrIsPass = userCreditDataService.loanCreditCompare(userQhzxMsc8037,thrData,"THR");
					if (CreditCfgKeyConstants.OPEN.equals(thrIsOpen)) {
						logger.info("THR命中生效开关开启，结果生效");
						if (thrIsPass) {
							logger.info("THR命中");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
							return this.loanCreditResult(userQhzxMsc8037,creditRecord,"THR",_CROpen);
						}else{
							logger.info("THR未命中");
						}
					}else{
						logger.info("THR命中生效开关关闭，征信通过。");
					}
					
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_LOAN_PASS,userQhzxMsc8037.getId());
				}else{
					logger.info("前海常贷客征信结果开关生效，未查询到任何常贷客数据,idCard:" + idCard + "，name：" + userName);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_LOAN_PASS,null);
				}
	
			
				
			} catch (Exception e1) {
				logger.error("调用前海常贷客接口（8037）异常：busId:" + busId+  ",身份证号：" + idCard + "，姓名：" + userName , e1);
				this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR,null);
				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR);
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR);
				//插入告警表
				String alarmContent = "调用前海常贷客接口（8037）异常,idCard："+  idCard + "，姓名：" + userName ;
				return sysAlarmService.exceptionAlarm(alarmContent);
			}
		}else{
			logger.info("调用前海常贷客-->开关关闭。");
		}
		/*******************************************************前海常贷客**************************************************************************/
		
		
		
		/*******************************************************百融多次申请核查**************************************************************************/
		String _alOpen = (String) (meMap.get(CreditCfgKeyConstants.BR_MODEL_APPLYLOAN_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_alOpen)) {
			logger.info("调用百融贷多次申请核查核查征信-->调用开关开启。");
			try {
				UserBrRequest brRequestPo = new UserBrRequest();
				brRequestPo.setIdCard(idCard);
				brRequestPo.setName(userName);
				brRequestPo.setMobile(telNumber);
				brRequestPo.setTotalInfoId(busId);
				brRequestPo.setProductName(CommonConstants.BR_MEAL_APPLYLOAN);
				
				//查询百融特殊名单信息是否存在
				Map<String, Object> brApplyLoanMap = userCreditDataService.queryExistBrApplyList(brRequestPo);
		        logger.info("brApplyLoanMap" + JSONArray.fromObject(brApplyLoanMap).toString());
				if (brApplyLoanMap.get(ResponseConstants.RETURN_CODE).equals(ResponseConstants.SUCCESS)) {
		    		BrApplyLoan brApplyLoan = (BrApplyLoan)brApplyLoanMap.get("portrait");
		    		if (brApplyLoan!=null && "00".equals(brApplyLoan.getCode())) {
						
						//近3个月在银行机构申请次数生效开关
						String m3Banknum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3BANKNUM_ISOPEN_CONFIG));
						String m3IdBankSelfnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3IDBANKSELFNUM_DATA_CONFIG));
						String m3IdBankAllnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3IDBANKALLNUM_DATA_CONFIG));
						String m3IdBankOrgnum = (String) (meMap.get(CreditCfgKeyConstants.BR_M3IDBANKORGNUM_DATA_CONFIG));
						
						if (CreditCfgKeyConstants.OPEN.equals(m3Banknum)) {
							logger.info("百融多次申请近3个月在银行机构申清次数生效开关开启");
							
							logger.info("身份证在银行-本机构申清次数");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM3IdBankSelfnum(),m3IdBankSelfnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							
							logger.info("身份证在银行-总申清次数");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM3IdBankAllnum(),m3IdBankAllnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							logger.info("身份证在银行-申清过的机构数。");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM3IdBankOrgnum(),m3IdBankOrgnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
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
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM3IdNotbankSelfnum(),m3IdNoBankSelfnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							
							logger.info("身份证在非银行-总申清次数");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM3IdNotbankAllnum(),m3IdNoBankAllnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							logger.info("身份证在非银行-申清过的机构数。");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM3IdNotbankOrgnum(),m3IdNoBankOrgnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
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
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM6IdBankSelfnum(),m6IdBankSelfnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							
							logger.info("身份证在银行-总申清次数");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM6IdBankAllnum(),m6IdBankAllnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							logger.info("身份证在银行-申清过的机构数。");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM6IdBankOrgnum(),m6IdBankOrgnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
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
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM6IdNotbankSelfnum(),m6IdNoBankSelfnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							
							logger.info("身份证在非银行-总申清次数");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM6IdNotbankAllnum(),m6IdNoBankAllnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							logger.info("身份证在非银行-申清过的机构数。");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM6IdNotbankOrgnum(),m6IdNoBankOrgnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
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
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM12IdBankSelfnum(),m12IdBankSelfnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							
							logger.info("身份证在银行-总申清次数");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM12IdBankAllnum(),m12IdBankAllnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							logger.info("身份证在银行-申清过的机构数。");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM12IdBankOrgnum(),m12IdBankOrgnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
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
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM12IdNotbankSelfnum(),m12IdNoBankSelfnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							
							logger.info("身份证在非银行-总申清次数");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM12IdNotbankAllnum(),m12IdNoBankAllnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
							logger.info("身份证在非银行-申清过的机构数。");
							if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM12IdNotbankOrgnum(),m12IdNoBankOrgnum)) {
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
								return this.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
							}
						}
		
						this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_APPLOAN_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_BR_RECORD_AL_PASS,brApplyLoan.getBrReqId());
					}else{
						logger.info("百融多次申请核查无数据返回。");
						this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_APPLOAN_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_BR_RECORD_AL_PASS,null);
					}
	
			  }else{
				   this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_APPLOAN_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR,null);
				   updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR);
				   updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR);
				   logger.error("调用百融多次申请核查接口，姓名:" + userName  + ",身份证号:" + idCard);
				   String alarmContent = "调用百融多次申请核查接口出错,返回错误代码：" + userName  + ",身份证号:" + idCard;
				   return sysAlarmService.exceptionAlarm(alarmContent);
			  }
				
			} catch (Exception e) {
				 this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_APPLOAN_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR,null);
				 logger.error("调用百融贷多次申请核查接口征信发生错误，身份证号：" +idCard + "，姓名：" + userName,e);
				 updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR);
				 updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR);
				 String alarmContent = "调用百融贷多次申请核查接发生错误，身份证号：" + idCard + "，姓名：" + userName;
				return sysAlarmService.exceptionAlarm(alarmContent);
			}
		}else{
			logger.info("调用百融多次申请核查征信接口-->调用开关关闭。");
		}
		/*******************************************************百融多次申请核查**************************************************************************/
		
		
		
		//如果能走到这一步，说明前面都是征信通过的
		userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_PASS,CreditCodeConstants.CREDIT_RECORD_CLOSED);
		updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_RECORD_CLOSED);
		returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_SUCCESS);
		returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_SUCCESS_DESC);
		return returnMap;
	}
	@Override
	public JsschoolEducationScore queryCollegeScore(String districtName, String department, String entranctDate,String busId) {
		logger.info("校区：" + districtName + "	院系：" + department);
		boolean isPass = true;//默认通过
		//首先根据校区查询评分，如果有评分再根据评分进行年级评分；如果院校无评分则查询院系评分，再根据评分进行年级评分
		//①查询校区评分
		String defaultScore =null;
		String educateDetail = "";//评量细节
		String educateReason = "评分通过" ;//评分描述

		DistrictInfo schoolDistrictInfo = creditCollegeDAO.querySchoolDistrict(districtName);
		if (schoolDistrictInfo != null && EntityUtils.isNotEmpty(schoolDistrictInfo.getDistrictScore())) {
			educateDetail = schoolDistrictInfo.getDistrictScore();
			logger.info("学历评分，获取校区，评量细节：" + schoolDistrictInfo.getDistrictScore());
		}else{
			logger.info("学历评分，获取院系，评量细节为空");
		}
		
		if (EntityUtils.isNotEmpty(department) && EntityUtils.isNotEmpty(schoolDistrictInfo)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("departmentName", department);
			paramMap.put("collegeId", schoolDistrictInfo.getCollegeId());
			
			DepartmentInfo schoolDepartmentInfo = creditCollegeDAO.querySchoolDepartment(paramMap);
			if (schoolDepartmentInfo != null && EntityUtils.isNotEmpty(schoolDepartmentInfo.getDepartmentScore())) {
				educateDetail = schoolDepartmentInfo.getDepartmentScore();
				logger.info("学历评分，获取院系，评量细节：" + schoolDepartmentInfo.getDepartmentScore());
			}else{
				logger.info("学历评分，获取院系，评量细节为空");
			}
		}

		
		switch (educateDetail) {
		case "7":
			defaultScore = "7";
			break;
		case "6":
			defaultScore = "6";
			break;
		case "5":
			defaultScore = "5";
			break;
		case "4":
			defaultScore = "4";
			break;
		case "3":
			defaultScore = "3";
			break;
		case "2":
			defaultScore = "2";
			break;
		case "1":
			defaultScore = "1";
			break;
		case "0":
			defaultScore = "0";
			break;
		default:
			logger.info("学历评分，获取院系和校区评分细节为：" + educateDetail + "，获取默认评分0");
			defaultScore = "0";
			break;
		}
		logger.info("学历评分，评量细节："+ educateDetail  + " 获取学历Ed1评分：" + defaultScore);
		
		String ed2Score = null ;
		int  studyDay = 0 ;
		String educateResult = StatusConstants.EDUCATE_RESULT_PASS ;
		if ("0".equals(defaultScore)) {
			educateResult = StatusConstants.EDUCATE_RESULT_UNPASS ;
			educateReason = "学历评分拒贷" ;
			isPass = false;
			logger.info("学历评分，获取学历评分拒贷。");
		}else{

			try {
				studyDay = DateUtil.daysBetween(DateUtil.transStrToDate(entranctDate, DateUtil.FORMAT_DATE),new Date());
				logger.info("入学天数：" + studyDay);
			} catch (ParseException e) {
				logger.error("学历评分，判断入学天数异常。");
			}
			
			
			if (Integer.parseInt(defaultScore) > 2  &&  730 > studyDay && studyDay >=365) {
				ed2Score = "4" ;
				logger.info("学历评分，计算学历评分规则：判断是否是大二（4及以上年制） 判断规则：Ed1>2 且 730天>"+ studyDay +"(today-入学日期)>=365天。获取Ed2评分：" + ed2Score  + "分" );
			}else if((Integer.parseInt(defaultScore) > 2  &&  (studyDay <= 1095 &&  studyDay >=730)) || (Integer.parseInt(defaultScore) <3  && (studyDay <=730 && studyDay > 365))){
				ed2Score = "3" ;
				logger.info("学历评分，计算学历评分规则：大三（4及以上年制）/大二（3年制） 判断规则：①Ed1>2 且1095天>="+ studyDay +"(today-入学日期)>=730天 或者②Ed1<3 且 730天>="+ studyDay +"(today-入学日期)>365天。获取Ed2评分：" + ed2Score  + "分" );
			}else if(studyDay <= 365){
				ed2Score = "2" ;
				logger.info("学历评分，计算学历评分规则："+ studyDay +"(today-入学日期)<365天。获取Ed2评分：" + ed2Score  + "分" );
			}else if((Integer.parseInt(defaultScore) > 2  && studyDay > 1095) || (Integer.parseInt(defaultScore) < 3 && studyDay > 730)){
				ed2Score = "1" ;
				logger.info("学历评分，计算学历评分规则：大四/大三（三年制） 判断规则：①Ed1>2 且1095天<"+ studyDay +"(today-入学日期) 或者②Ed1<3 且730天<"+ studyDay +"(today-入学日期)。获取Ed2评分：" + ed2Score  + "分" );
			}else{
				ed2Score = "0" ;
				logger.info("学历评分，未命中任何规则，获取默认Ed2评分：" + ed2Score  + "分" );
			}
			logger.info("学历评分，获取年级评分：" + ed2Score);
			
//			if ("0".equals(ed2Score) ||"1".equals(ed2Score)) {
			if ("0".equals(ed2Score)) {
				logger.info("学历评分，获取年级评分拒贷。");
				educateResult = StatusConstants.EDUCATE_RESULT_UNPASS ;
				educateReason = "年级评分拒贷。" ;
				isPass = false;
			}
		}
		
		JsschoolEducationScore  score = new JsschoolEducationScore();
		score.setId(GenerateKey.getId(CommonConstants.EDUCATION_SCORE_ID_PREFIX, ConfigUtils.getProperty("db_id")));
		score.setBusId(busId);
		score.setEducateScore(defaultScore);
		score.setGradeScore(ed2Score);
		score.setEducateResult(educateResult);
		score.setEducateReason(educateReason);
		score.setCreateTime(new Date());
		score.setUpdateTime(new Date());
		creditCollegeDAO.saveEducationScore(score);
		return score;
	}

	@Override
	public String queryJyCreditResult(String busId) {
		logger.info("查询91征信模块表，busId:" + busId);
		return creditCollegeDAO.queryJyCreditResult(busId);
	}
	@Override
	public void updateJsSchoolBusCreditResult(String busId,String creditReason) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("busId", busId);
		map.put("creditReason", creditReason);
		creditCollegeDAO.updateJsSchoolBusCreditResult(map);
	}

	@Override
	public UserCreditRecord isBlackUser(String idCard,String name) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("idCard", idCard);
		map.put("name", name);
		//查询该和用户是否曾命中过前海或者百融黑名单
		UserCreditRecord blackRecord = creditCollegeDAO.queryBlackUser(map);
		return blackRecord;
	}
	
	@Override
	public String getBlackUserData(String idCard,String name) throws Exception {
		String resultId = null;
		Map<String,String> map = new HashMap<String,String>();
		map.put("idCard", idCard);
		map.put("name", name);
		//查询该和用户是否曾命中过前海或者百融黑名单
		//UserCreditRecord blackRecord = creditCollegeDAO.queryBlackUser(map);
		List<UserQhzxBlackList> qhBlackList = creditCollegeDAO.queryIsQhBlackUser(map);
		List<UserBrSpeciallistC> brSpeciallistList = creditCollegeDAO.queryIsBrBlackUser(map);
		
		if (qhBlackList != null && qhBlackList.size() > 0 ) {
			for(UserQhzxBlackList qhBlack : qhBlackList){
				if ("E000000".equals(qhBlack.getErCode())) {
					resultId = qhBlack.getId();
					logger.info("命中前海历史黑名单");
					break;
				}
			}
		}else if (brSpeciallistList != null && brSpeciallistList.size() > 0 ) {
			for(UserBrSpeciallistC brBlack : brSpeciallistList){
				if (BRUtils.speciallistCreditResult(brBlack)) {
					resultId = brBlack.getId();
					logger.info("命中百融历史黑名单");
					break;
				}
			}
		}
		
		return resultId;
	}
	
	@Override
	public Map<String, String> requestQhCredit(Map<String, String> queryMap) {
		
		Map<String, String> returnMap = new HashMap<String, String>();
		String busId = queryMap.get("busId");
		String userName = queryMap.get("userName");			//用户姓名
		String idCard = queryMap.get("idCard");				//用户身份证
		String idCardAddress = queryMap.get("idCardAddress");	//身份证地址
		String telNumber = queryMap.get("telNumber");			//联系电话
		String bankCardNumber = queryMap.get("bankCardNumber");			   
		
		Map<String, String> meMap = null;
		try {
			meMap = configCreditKvService.getAllCreditKv();
		} catch (Exception e3) {
			throw new RuntimeException("调用核心征信接口，获取征信配置数据异常");
		}
		
		if (meMap == null) {
			throw new RuntimeException("调用核心征信接口，获取征信配置数据为空");
		}
		
		UserCreditRecord creditRecord = new UserCreditRecord();
		creditRecord.setInfoId(busId);
		String _CROpen =  "1";
		try {
			Map<String, Object> userQhzxMsc8005Map =   userCreditDataService.queryQhCreditList(userName, idCard, telNumber, bankCardNumber, busId);
			
			//征信结果判断开关
/*			String noGoodrelData = (String) (meMap.get(CreditCfgKeyConstants.QH_NOGOODRELDATA_CONFIG));
			String goodrelData = (String) (meMap.get(CreditCfgKeyConstants.QH_GOODRELDATA_CONFIG));*/
			String score_result = (String) (meMap.get(CreditCfgKeyConstants.QH_GOODREL_SCORE_CONFIG));
			
			logger.info("调用前海征信好信度接口（8005）,前海征信好信度数据库中配置分数score_result：" + score_result);

			if (CreditCfgKeyConstants.OPEN.equals(_CROpen)) { //开关生效,结果生效
				
				if (EntityUtils.isEmpty(userQhzxMsc8005Map.get(ResponseConstants.RESULT_CODE))
						|| !ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals((String)userQhzxMsc8005Map.get(ResponseConstants.RESULT_CODE))) {
					logger.info("前海好信度接口开关生效，处理前海好信度接口发生问题，问题描述：" + userQhzxMsc8005Map.get(ResponseConstants.RESULT_DESC) + ",返回码：" + userQhzxMsc8005Map.get(ResponseConstants.RESULT_CODE));
					updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODRELEXPE_ERROR);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODRELEXPE_ERROR,null);
					String alarmContent = "处理前海好信度接口时发生问题，问题描述：" + userQhzxMsc8005Map.get(ResponseConstants.RESULT_DESC);
					return sysAlarmService.exceptionAlarm(alarmContent);
				}
				
				if (EntityUtils.isEmpty(userQhzxMsc8005Map.get(ResponseConstants.DATA))) {
/*					if ("0".equals(noGoodrelData)) {//当分数为空的时候，是否可以通过的开关，
						logger.info("前海好信度接口开关生效，未查询到数据,前海征信好信度分数为空,配置项" + noGoodrelData + "为无数据时,拒贷。");
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
						this.insertCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
						returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
						returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
						return returnMap;
					}else{
						logger.info("前海好信度接口开关生效,未查询到数据，前海征信好信度分数为空,配置项" + noGoodrelData + "为无数据时,通过。");
					}*/
					
					updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS,null);
					returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
					returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
					return returnMap;
					
				}else{
					UserQhzxCredit userQhzxMsc8005 = CommonUtil.parseJSON2Bean(userQhzxMsc8005Map.get(ResponseConstants.DATA).toString(), UserQhzxCredit.class);
					
					//当分数为空时
					if (EntityUtils.isEmpty(userQhzxMsc8005.getCredooScore())) {
/*						if ("0".equals(noGoodrelData)) {//当分数为空的时候，是否可以通过的开关，
							logger.info("前海好信度接口开关生效，,前海征信好信度分数为空,配置项" + noGoodrelData + "为无数据时,拒贷。");
							updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
							this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
							returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
							returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
							return returnMap;
						}else{
							logger.info("前海好信度接口开关生效,前海征信好信度分数为空,配置项" + noGoodrelData + "为无数据时,通过。");
						}*/
						
						updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
						this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS,userQhzxMsc8005.getId());
						returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
						returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
						return returnMap;
					}else{
						/*if ("0".equals(goodrelData)) {//当分数不为空的时候，是否可以通过的开关，0-拒贷 1-通过
									logger.info("前海好信度接口开关生效，分数不为空，配置项控制,拒贷.,idCard:" + idCard + "，name：" + userName + "，拒绝征信。");
									updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
									this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
									returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
									returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
									return returnMap;
						}else{
							logger.info("调前海好信度接口开关生效，分数不为空，配置项控制,通过,判断分数是否可以通过.idCard:" + idCard + "，name：" + userName );
							 if (qhzxService.queryIsQhzxGoodRel(userQhzxMsc8005,score_result)) {
									String descp = userQhzxMsc8005 == null ? "获得好信度对象为空":"前海征信好信度得分不通过，得分为:" + userQhzxMsc8005.getCredooScore();
									logger.info("前海好信度接口开关生效，分数不为空，前海好信度得分不通过.idCard:" + idCard + "，name：" + userName + "," + descp + "，拒绝征信。");
									updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
									this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
									returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
									returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
									return returnMap;
							 }else {
									logger.info("前海好信度接口开关生效，分数不为空，前海得分通过.idCard:" + idCard + "，name：" + userName );
							 }
						}*/
						
						 if (qhzxService.queryIsQhzxGoodRel(userQhzxMsc8005,score_result)) {
								String descp = userQhzxMsc8005 == null ? "获得好信度对象为空":"前海征信好信度得分不通过，得分为:" + userQhzxMsc8005.getCredooScore();
								logger.info("前海好信度接口开关生效，分数不为空，前海好信度得分不通过.idCard:" + idCard + "，name：" + userName + "," + descp + "，拒绝征信。");
								updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS);
								this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_UNPASS,userQhzxMsc8005.getId());
								returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
								returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
								return returnMap;
						 }else {
								logger.info("前海好信度接口开关生效，分数不为空，前海得分通过.idCard:" + idCard + "，name：" + userName );
						 }
					}
					this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_PASS,userQhzxMsc8005.getId());
				}
			}else {
				logger.info("前海征信结果好信度结果开关关闭。");
				this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_GOODREL_PASS,null);
			}

		} catch (Exception e1) {
			logger.error("调用前海征信好信度接口（8005）异常：busId:" + busId +  ",身份证号：" + idCard + "，姓名：" + userName, e1);
			this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_GOODREL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_GOODRELEXPE_ERROR,null);
			updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_GOODRELEXPE_ERROR);
			updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_GOODRELEXPE_ERROR);
			String alarmContent = "调用前海征信好信度接口发生错误，身份证号：" + idCard + "，姓名：" + userName;
			return sysAlarmService.exceptionAlarm(alarmContent);
		}

		returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_SUCCESS);
		returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_SUCCESS_DESC);
		return returnMap;
	}
	@Override
	public void updateCR(UserCreditRecord creditRecord, Integer isPass,
			String reason) {
		creditRecord.setIsPass(isPass);
		creditRecord.setReason(reason);
		creditRecord.setUpdateTime(new Date());
		userCreditDataDAO.updateUserCreditRecord(creditRecord);
		
	}
	@Override
	public void insertShoolCreditModelRecord(String creditId,String _CROpen,String creditModel, String creditModelType, int creditResult,String resultDesc,String dataId) {
		
		UserCreditModelRecord record = new UserCreditModelRecord();
		record.setCreditId(creditId);
		record.setModelRecordId(GenerateKey.getId(CommonConstants.USER_CREIDT_MODEL_RECORD_ID_PREFIX, ConfigUtils.getProperty("db_id")));
		record.setCreditModel(creditModel);
		record.setCreditModelType(creditModelType);
		record.setIsEffective(_CROpen); //（0-无效；1-有效）
		record.setCreditCheckingResult(creditResult);//征信模块判断结果：10 征信通过 20征信不通过
		record.setCreditCheckingResultDesc(resultDesc);
		record.setDataId(dataId);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setOperator(CommonConstants.DEFAULT_OPERATOR);
		userCreditDataDAO.insertCreditModelRecord(record);
	}
	
	@Override
	public Map<String, String> requestCheckSchool(Map<String, String> queryMap) throws Exception {
		
		Map<String, String> resultMap = new HashMap<String, String>();
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
		String _SSOpen = (String) (meMap.get(CreditCfgKeyConstants.STUDENT_STATUS));
		if (CreditCfgKeyConstants.OPEN.equals(_SSOpen)) {
			logger.info("调用学籍查询-->调用开关开启。");
			String _CROpen =  "1";
			String busId = queryMap.get("busId");
			String creditId = userCreditDataDAO.queryCreditIdByTotalId(busId);
			
			UserCreditRecord creditRecord = new UserCreditRecord();
			creditRecord.setCreditId(creditId);
			try {
				
				List<UserSchoolRoll> userSchoolRollList =  checkSchoolService.queryUserSchoolRollInfo(queryMap);
				
				if(userSchoolRollList.isEmpty() || !checkedSchoolCreateTime(userSchoolRollList.get(0))){
					logger.info("请求学籍核查接口 , 库中不存在该用户的学籍信息，并且信息在一年内有效" );
						//调用学籍核查接口 -------------开始//
						logger.info("请求学籍核查信息接口");
						Map<String, Object> respMap = checkSchoolService.queryCheckSchool(queryMap);
						UserSchoolRoll userSchoolRoll = (UserSchoolRoll) respMap.get("UserSchoolRoll");
						
						if(!ResponseConstants.SUCCESS.equals(respMap.get(ResponseConstants.RETURN_CODE))){
							logger.info("请求学籍核查接口异常,接口返回信息: resultCode :" + respMap.get(ResponseConstants.RETURN_CODE) +" desc :" + respMap.get(ResponseConstants.RETURN_DESC));
							
							userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
							this.insertShoolCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_CHECK_SCHOOL, CreditCodeConstants.CREDIT_CHECK_SCHOOL_MODEL, 
									StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS,userSchoolRoll.getId());		
							resultMap.put(ResponseConstants.RETURN_CODE, TelecomConstants.API_SYSTEM_EXCEPTION);
							resultMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
							return resultMap;
						}
						
						
					     if(!isPassSchoolDate(userSchoolRoll)){
					    	logger.info("请求学籍核查接口,未通过: 返回结果  nameCheckResult:" +userSchoolRoll.getNameCheckResult() +"|documentNoCheckResult :" 
					    			+userSchoolRoll.getDocumentNoCheckResult() + "| startTimeCheckResult:" + userSchoolRoll.getStartTime() + "| collegeLevelCheckResult :" 
					    			+ userSchoolRoll.getCollegeLevelCheckResult() + "| collegeCheckResult:" +userSchoolRoll.getCollegeCheckResult());
					    	
					    	logger.info("学籍核查未通过，修改征信结果");
					    	userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
					    	logger.info("学籍核查未通过，修改业务表征信结果");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
							logger.info("学籍核查未通过，修改业务表征信模块结果");
							this.insertShoolCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_CHECK_SCHOOL, CreditCodeConstants.CREDIT_CHECK_SCHOOL_MODEL, 
									StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS,userSchoolRoll.getId());
							
					    	resultMap.put(ResponseConstants.RETURN_CODE, TelecomConstants.CHECK_SCHOOL_DEFAULT);
							resultMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CHECK_SCHOOL_DEFAULT_DESC);
							return resultMap;
					     }else{
					    	logger.info("学籍核查通过，修改征信结果");
					    	userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_PASS,CreditCodeConstants.CREDIT_CHECK_SCHOOL_PASS);
					    	logger.info("学籍核查通过，修改业务表征信结果");
							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_CHECK_SCHOOL_PASS);
							logger.info("学籍核查通过，修改业务表征信模块结果");
							this.insertShoolCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_CHECK_SCHOOL, CreditCodeConstants.CREDIT_CHECK_SCHOOL_MODEL, 
									StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_CHECK_SCHOOL_PASS,userSchoolRoll.getId());
							
							resultMap.put(ResponseConstants.RETURN_CODE, TelecomConstants.CHECK_SCHOOL_SUCCESS);
							resultMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CHECK_SCHOOL_SUCCESS_DESC);
							return resultMap;
					     }
					   //调用学籍核查接口 -------------结束//
				}else{
					logger.info("库中已经存在该用户的学籍信息，并且信息在一年内有效");
					//判断该身份证信息，在本地库是否有全通过记录
					//---------------------------------
					//循环记录，找出是否有全通过的记录
					for(UserSchoolRoll roll : userSchoolRollList){
						if(isPassSchoolDate(roll)){ //有全通过记录，则进行信息校验(判断真假人)
							if(isPassIdCardDate(roll,queryMap)){  //信息校验结果: 相同 ，返回学籍核查通过
								logger.info("学籍核查通过，修改征信结果");
						    	userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_PASS,CreditCodeConstants.CREDIT_CHECK_SCHOOL_PASS);
						    	logger.info("学籍核查通过，修改业务表征信结果");
								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_CHECK_SCHOOL_PASS);
								logger.info("学籍核查通过，修改业务表征信模块结果");
								this.insertShoolCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_CHECK_SCHOOL, CreditCodeConstants.CREDIT_CHECK_SCHOOL_MODEL, 
										StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_CHECK_SCHOOL_PASS,roll.getId());
								
								resultMap.put(ResponseConstants.RETURN_CODE, TelecomConstants.CHECK_SCHOOL_SUCCESS);
								resultMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CHECK_SCHOOL_SUCCESS_DESC);
								return resultMap;
							}
						}
					}
					
					//通过以上循环结果是：没有全通过的记录
					for(UserSchoolRoll roll : userSchoolRollList){			
						if(!isPassSchoolDate(roll)){ //无全通过记录，则进行信息校验(判断真假人)
							if(!isPassIdCardDate(roll,queryMap)){  //校验信息不一致，请求学籍核查接口
								//调用学籍核查接口 -------------开始//
								Map<String, Object> respMap = checkSchoolService.queryCheckSchool(queryMap);
								
								logger.info("本地数据库中无全通过的学籍核查记录，且校验信息不一致，请求学籍核查信息接口");
								if(!ResponseConstants.SUCCESS.equals(respMap.get(ResponseConstants.RETURN_CODE))){
									logger.info("请求学籍核查接口异常,接口返回信息: resultCode :" + respMap.get(ResponseConstants.RETURN_CODE) +" desc :" + respMap.get(ResponseConstants.RETURN_DESC));
									
									userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
									updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
									this.insertShoolCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_CHECK_SCHOOL, CreditCodeConstants.CREDIT_CHECK_SCHOOL_MODEL, 
											StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS,roll.getId());		
									resultMap.put(ResponseConstants.RETURN_CODE, TelecomConstants.API_SYSTEM_EXCEPTION);
									resultMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
									return resultMap;
								}
								UserSchoolRoll userSchoolRoll = (UserSchoolRoll) respMap.get("UserSchoolRoll");
								
							     if(!isPassSchoolDate(userSchoolRoll)){
							    	logger.info("请求学籍核查接口,未通过: 返回结果  nameCheckResult:" +userSchoolRoll.getNameCheckResult() +"|documentNoCheckResult :" 
							    			+userSchoolRoll.getDocumentNoCheckResult() + "| startTimeCheckResult:" + userSchoolRoll.getStartTime() + "| collegeLevelCheckResult :" 
							    			+ userSchoolRoll.getCollegeLevelCheckResult() + "| collegeCheckResult:" +userSchoolRoll.getCollegeCheckResult());
							    	
							    	logger.info("学籍核查未通过，修改征信结果");
							    	userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
							    	logger.info("学籍核查未通过，修改业务表征信结果");
									updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
									logger.info("学籍核查未通过，修改业务表征信模块结果");
									this.insertShoolCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_CHECK_SCHOOL, CreditCodeConstants.CREDIT_CHECK_SCHOOL_MODEL, 
											StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS,roll.getId());
									
							    	resultMap.put(ResponseConstants.RETURN_CODE, TelecomConstants.CHECK_SCHOOL_DEFAULT);
									resultMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CHECK_SCHOOL_DEFAULT_DESC);
									return resultMap;
							     }else{
							    	logger.info("学籍核查通过，修改征信结果");
							    	userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_PASS,CreditCodeConstants.CREDIT_CHECK_SCHOOL_PASS);
							    	logger.info("学籍核查通过，修改业务表征信结果");
									updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_CHECK_SCHOOL_PASS);
									logger.info("学籍核查通过，修改业务表征信模块结果");
									this.insertShoolCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_CHECK_SCHOOL, CreditCodeConstants.CREDIT_CHECK_SCHOOL_MODEL, 
											StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_CHECK_SCHOOL_PASS,roll.getId());
									
									resultMap.put(ResponseConstants.RETURN_CODE, TelecomConstants.CHECK_SCHOOL_SUCCESS);
									resultMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CHECK_SCHOOL_SUCCESS_DESC);
									return resultMap;
							     }
							   //调用学籍核查接口 -------------结束//
							}
							
						}	
					}
					
					//没有符合规则的结果返回，则证明学籍核查未通过
					logger.info("学籍核查: 身份证号:" + queryMap.get("documentNo") + "在本地库无全部通过记录，校验信息一致 ,或者 在本地库有全部通过记录，校验信息不通过，拒贷！" );
					logger.info("学籍核查未通过，修改征信结果");
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
					logger.info("学籍核查未通过，修改业务表征信结果");
					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
					logger.info("学籍核查未通过，修改业务表征信模块结果");
					this.insertShoolCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_CHECK_SCHOOL, CreditCodeConstants.CREDIT_CHECK_SCHOOL_MODEL, 
							StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS,userSchoolRollList.get(0).getId());
					
					resultMap.put(ResponseConstants.RETURN_CODE, TelecomConstants.CHECK_SCHOOL_DEFAULT);
					resultMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CHECK_SCHOOL_DEFAULT_DESC);
					return resultMap;
	
				}
			} catch (Exception e) {
				logger.info("错误信息 :" + e);
				
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS);
				this.insertShoolCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_CHECK_SCHOOL, CreditCodeConstants.CREDIT_CHECK_SCHOOL_MODEL, 
						StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_CHECK_SCHOOL_UNPASS,null);
				
				resultMap.put(ResponseConstants.RETURN_CODE, TelecomConstants.API_SYSTEM_EXCEPTION);
				resultMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
			}
		}else{
			logger.info("调用学籍查询-->调用开关关闭。");
			resultMap.put("resultCode", TelecomConstants.CHECK_SCHOOL_SUCCESS);
			resultMap.put("desc", TelecomConstants.CHECK_SCHOOL_SUCCESS_DESC);
		}
		return resultMap;
	}
	
	@Override
	public Map<String, String> catchQhCreditData(Map<String, String> reqMap) {
		Map<String, String> retMap = new HashMap<String, String>();
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
		String _QHHXDOpen = (String) (meMap.get(CreditCfgKeyConstants.QH_MODEL_GOODREL_ISOPEN));
		if (CreditCfgKeyConstants.OPEN.equals(_QHHXDOpen)) {
			logger.info("调用前海征信好信度-->调用开关开启。");
			String busId = reqMap.get("busId");
			String userName = reqMap.get("userName");			//用户姓名
			String idCard = reqMap.get("idCard");				//用户身份证
			String telNumber = reqMap.get("telNumber");			//联系电话
			String bankCardNumber = reqMap.get("bankCardNumber");			   
			
			UserQhzxCredit userQhzx = new UserQhzxCredit();
			userQhzx.setName(userName);
			userQhzx.setIdNo(idCard);
			List<UserQhzxCredit> qhCreditList = userQhzxCreditDao.queryQhzxCredit(userQhzx);
			if (qhCreditList != null && qhCreditList.size() > 0) {
				UserQhzxCredit userQhzxCredit = qhCreditList.get(0);
				if (userQhzxCredit != null) {
					//判断获取的时间是否超过90天
					int day = 0 ;
					try {
						day = DateUtil.daysBetween(userQhzxCredit.getCreateTime(),new Date());
					} catch (ParseException e) {
						logger.info("查询核心好信度接口，计算获取天数异常。",e);
					}
					
					if (day < 90) {
						logger.info("查询核心好信度接口，计算获取天数未超过90天，结束征信点。" + day  + "天。");
						retMap.put(ResponseConstants.DATA, JSONObject.fromObject(qhCreditList.get(0)).toString());
						retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.SUCCESS);
						retMap.put(ResponseConstants.RESULT_DESC, "库中已经存在该多次申请请求，不再继续请求前海");
						return retMap;
					}
					logger.info("查询核心好信度接口，计算获取天数超过90天，结束征信点。" + day  + "天。调用好信度接口");
				}
			}
			
			Map<String, Object> responseMap = null;
			try {
				responseMap = qhzxService.requestQhCredit(idCard, userName, bankCardNumber, telNumber, null, busId);
			} catch (Exception e) {
				logger.error("获取前海好信度数据出错，busId：" + busId, e);
			}
			if(EntityUtils.isNotEmpty(responseMap) 
					&& ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals(responseMap.get(ResponseConstants.RESULT_CODE).toString())
					&& EntityUtils.isNotEmpty(responseMap.get(ResponseConstants.DATA))){
				retMap.put(ResponseConstants.DATA, responseMap.get(ResponseConstants.DATA).toString());
			}
		}else {
			logger.info("调用前海征信好信度-->开关关闭。");
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RESULT_DESC, "前海征信好信度开关关闭");
		}
		return retMap;
	}
	
	private boolean isEffectiveData(UserQhzxCredit qhzxCredit){
		Date start = qhzxCredit.getCreateTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		Date end = DateUtil.addDate(start, Calendar.DATE, 30);
		
		if(EntityUtils.isEmpty(start) || end.getTime() < new Date().getTime()){
			return false;
		}
		
		return true;
	}
	
	//校验数据是否在有效期内(有效期为1年)
	private boolean checkedSchoolCreateTime(UserSchoolRoll userSchoolRoll){
		
		Date start = userSchoolRoll.getCreateTime();
		Date end = DateUtil.addDate(start, Calendar.YEAR, 1);
		
		if(EntityUtils.isEmpty(start) || end.getTime() < new Date().getTime()){
			return false;
		}
		return true;
	}
	
	//校验学籍核查结果，五项是否全部通过，若一项不通过则返回false
	private boolean isPassSchoolDate(UserSchoolRoll userSchoolRoll){
		if(! StatusConstants.CHECK_SHCOOL_CORRECT.equals(userSchoolRoll.getNameCheckResult()) ) {
			return false;
		}else if(! StatusConstants.CHECK_SHCOOL_CORRECT.equals(userSchoolRoll.getDocumentNoCheckResult())){
			return false;
		}else if(! StatusConstants.CHECK_SHCOOL_CORRECT.equals(userSchoolRoll.getStartTimeCheckResult())){
			return false;
		}else if(! StatusConstants.CHECK_SHCOOL_CORRECT.equals(userSchoolRoll.getCollegeLevelCheckResult())){
			return false;
		}else if(! StatusConstants.CHECK_SHCOOL_CORRECT.equals(userSchoolRoll.getCollegeCheckResult())){
			return false;
		}else{
			return true;
	    }
	}
	
	//校验参数是否与数据库一致，若一项不一致则返回false
	private boolean isPassIdCardDate( UserSchoolRoll roll, Map<String, String> paramMap){
		if(! paramMap.get("documentNo").equals(roll.getDocumentNo())){
			return false;
		}else if(! paramMap.get("userName").equals(roll.getName())){
			return false;
		}else if(! paramMap.get("collegeLevel").equals(roll.getCollegeLevel())){
			return false;
		}else if(!paramMap.get("college").equals(roll.getCollege())){
			return false;
		}else if(! paramMap.get("startTime").equals(roll.getStartTime())){
			return false;
		}else{
			return true;
		}
	}

	//常贷客拒贷结果
	@Override
	public Map<String,String> loanCreditResult(UserQhzxLoan userQhzxMsc8037,UserCreditRecord creditRecord,String type,String _CROpen){
		Map<String,String> returnMap = new HashMap<String,String>();
		//判断命中次数
		updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
		this.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS,userQhzxMsc8037.getId());
		returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
		returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
		return returnMap;
	}
	//百融多次申请核查征信结果
	@Override
	public Map<String,String> applyLoanCreditResult(BrApplyLoan brApplyLoan,UserCreditRecord creditRecord,String _CROpen){
		Map<String,String> returnMap = new HashMap<String,String>();
		//判断命中次数
		updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
		this.insertShoolCreditModelRecord(creditRecord.getCreditId(),_CROpen, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_APPLOAN_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS,brApplyLoan.getBrReqId());
		returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
		returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
		return returnMap;
	}
	@Override
	public Map<String, Object> validateSchoolCreditParams(Map<String, String> busMap) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		//校验参数
		if(EntityUtils.isEmpty(busMap.get("busId"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "busId is empty");
		}else if(!Arrays.asList(CommonConstants.CHANNELID.split(",")).contains(busMap.get("channelId")) ){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "channelId is unKnown");
		}else if(EntityUtils.isEmpty(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is empty");
		}else if(!ValidateAPIPlatParamsTools.isIpv4(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is empty");
		}else if(!ValidateAPIPlatParamsTools.checkChineseName(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is not Chinese");
		}else if(EntityUtils.isEmpty(busMap.get("idCard"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is empty");
		}else if(!ValidateAPIPlatParamsTools.validateIdCard(busMap.get("idCard").toLowerCase())){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("idCardAddress"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCardAddress is empty");
		}else if(!ValidateAPIPlatParamsTools.validIdCardAddress(busMap.get("idCardAddress"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCardAddress is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is empty");
		}else if(!ValidateAPIPlatParamsTools.validateMobileNumber(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("schoolName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "schoolName is empty");
		}else if(EntityUtils.isEmpty(busMap.get("department"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "department is empty");
		}else{
			retMap.put("result_code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		return retMap;
	}
	@Override
	public Map<String, Object> creditStudentWhiteList(Map<String, String> busMap) {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
		retMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
		
		List<StudentWhiteList> list = studentWhiteListDAO.queryStudentWhiteList(busMap);
		if(EntityUtils.isNotEmpty(list)){
			StudentWhiteList studentWhiteList = list.get(0);
			if(DateUtil.daysBetween(studentWhiteList.getCreateTime(), new Date(), false) <= 7){
				logger.info("新生白名单通过，有白名单数据，且有效期在7天之内");
				retMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_SUCCESS);
				retMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_SUCCESS_DESC);
				retMap.put(ResponseConstants.DATA, studentWhiteList);
			}else{
				logger.info("新生白名单通过，有白名单数据，但有效期不在7天之内");
			}
		}else{
			logger.info("新生白名单未通过，无白名单数据");
		}
		
		return retMap;
	}
	
}
