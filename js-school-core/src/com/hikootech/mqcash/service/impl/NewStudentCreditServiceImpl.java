package com.hikootech.mqcash.service.impl;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.CreditCfgKeyConstants;
import com.hikootech.mqcash.constants.CreditCodeConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.TelecomConstants;
import com.hikootech.mqcash.dao.CreditCollegeDAO;
import com.hikootech.mqcash.dao.NewStudentCreditDAO;
import com.hikootech.mqcash.dao.UserCreditDataDAO;
import com.hikootech.mqcash.dao.UserQhzxCreditDAO;
import com.hikootech.mqcash.po.BlackList;
import com.hikootech.mqcash.po.BrApplyLoan;
import com.hikootech.mqcash.po.DepartmentInfo;
import com.hikootech.mqcash.po.DistrictInfo;
import com.hikootech.mqcash.po.JsschoolEducationScore;
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
import com.hikootech.mqcash.qhzx.CommonUtil;
import com.hikootech.mqcash.service.CheckSchoolService;
import com.hikootech.mqcash.service.ConfigCreditKvService;
import com.hikootech.mqcash.service.CreditCollegeService;
import com.hikootech.mqcash.service.NewStudentCreditService;
import com.hikootech.mqcash.service.QhzxService;
import com.hikootech.mqcash.service.SysAlarmService;
import com.hikootech.mqcash.service.UserCreditDataService;
import com.hikootech.mqcash.service.UserJyzxService;
import com.hikootech.mqcash.util.BRUtils;
import com.hikootech.mqcash.util.CheckNameUtils;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.ValidateParamsTools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("newStudentCreditService")
public class NewStudentCreditServiceImpl implements NewStudentCreditService {
	
	private static Logger logger = LoggerFactory.getLogger(NewStudentCreditServiceImpl.class);
	
	@Autowired
	private NewStudentCreditDAO newStudentCreditDAO;
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
	private CreditCollegeService creditCollegeService;
	
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.NEVER, timeout = 30)
	@Override
	public Map<String, String> validateCreditParameters(Map<String, String> busMap) {
		// TODO Auto-generated method stub
		logger.info("开始校验校园新生征信参数");
		Map<String, String> retMap = new HashMap<String, String>();
		//校验参数
		if(EntityUtils.isEmpty(busMap.get("merchantAccount"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "merchantAccount is empty");
		}else if(!ValidateParamsTools.validateMobileNumber(busMap.get("merchantAccount"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "userAccount is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("userAccount"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "userAccount is empty");
		}else if(!ValidateParamsTools.validateMobileNumber(busMap.get("userAccount"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "userAccount is formatError");
		}else if(!Arrays.asList(CommonConstants.CHANNELID.split(",")).contains(busMap.get("channelId")) ){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "channelId is unKnown");
		}else if(EntityUtils.isEmpty(busMap.get("custIp"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "custIp is empty");
		}else if(!ValidateParamsTools.isIpv4(busMap.get("custIp"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "custIp is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("userName"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "您需要填写正确的姓名，谢谢~");
		}else if(!ValidateParamsTools.checkChineseName(busMap.get("userName"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "您需要填写正确的姓名，谢谢~");
		}else if(userCreditDataService.isCreditByName(EntityUtils.replaceNull(busMap.get("userName")))){
			retMap.put(ResponseConstants.RETURN_CODE,StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "您需要填写正确的姓名，谢谢~");
		}else if(EntityUtils.isEmpty(busMap.get("idCard"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入正确身份证号信息");
		}else if(!ValidateParamsTools.validateIdCard(busMap.get("idCard").toLowerCase())){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入正确身份证号信息");
		}else if(EntityUtils.isEmpty(busMap.get("idCardAddress"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请按照身份证地址格式输入!");
		}else if(!ValidateParamsTools.validIdCardAddress(busMap.get("idCardAddress"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请按照身份证地址格式输入!");
		}else if(EntityUtils.isEmpty(busMap.get("telNumber"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入正确的手机号");
		}else if(!ValidateParamsTools.validateMobileNumber(busMap.get("telNumber"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入正确的手机号");
		}else if(EntityUtils.isEmpty(busMap.get("schoolName"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "schoolName is empty");
		}else if(EntityUtils.isEmpty(busMap.get("department"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "department is empty");
		}else if(EntityUtils.isEmpty(busMap.get("major"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入正确专业信息，谢谢！");
		}else if(!ValidateParamsTools.validMajor(busMap.get("major"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入正确专业信息，谢谢！");
		}else if(EntityUtils.isEmpty(busMap.get("entranctDate"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "entranctDate is empty");
		}else if(!ValidateParamsTools.validEntranctDate(busMap.get("entranctDate"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "entranctDate is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("dormitoryAddress"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入详细宿舍地址!");
		}else if(!ValidateParamsTools.validDormitoryAddress(busMap.get("dormitoryAddress"))){
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入详细宿舍地址!");
		}else{
			
			//查询该校区是否存在
			DistrictInfo schoolInfo = newStudentCreditDAO.queryExistSchoolInfo(EntityUtils.replaceNull(busMap.get("schoolName")));
			if (schoolInfo == null) {
				logger.info(busMap.get("schoolName") + ",不存在该校区。");
				retMap.put(ResponseConstants.RETURN_CODE,StatusConstants.PARTNER_CALL_FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "秒趣库中无该校区信息");
				return retMap;
			}
			
			logger.info(busMap.get("schoolName") + ",查询该校区对应的院系是否存在");
			//如果校区存在，查询该校区下面是否存在院系，如果没有院系，则可以继续后续流程，如果有院系，但是与接口中院系参数不匹配，则直接返回错误。
			List<DepartmentInfo> departmentList = newStudentCreditDAO.queryExistDepartmentInfo(schoolInfo.getCollegeId());
			if (departmentList != null && departmentList.size() > 0) {
				boolean flag = false;//匹配到院系则置为true
				logger.info(busMap.get("schoolName") + "存在院系：" + JSONArray.fromObject(departmentList).toString() );
				for (DepartmentInfo departmentInfo : departmentList) {
					if (busMap.get("department").equals(departmentInfo.getDepartmentName())) {
						logger.info("接口中传入院系参数：" + busMap.get("department") + "与 库中" + departmentInfo.getDepartmentName() + "匹配。" );
						flag = true;
						break;
					}
				}
				if (!flag) {
					retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_FAIL);
					retMap.put(ResponseConstants.RETURN_DESC, "秒趣校区字典中无该院系");
					return retMap;
				}
			}
			
			retMap.put(ResponseConstants.RETURN_CODE, StatusConstants.PARTNER_CALL_SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, "校园参数校验通过");
		}
		
		logger.info("校验发送验证码参数结束");
		return retMap;
	}

	@Override
	public Map<String, String> credit(Map<String, String> busMap) {
		// TODO Auto-generated method stub
		logger.info("新生征信：" + busMap);
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
		
		String busId = busMap.get("busId");				//业务表主键
		String userName = busMap.get("userName");			//用户姓名
		String idCard = busMap.get("idCard");				//用户身份证
		String idCardAddress = busMap.get("idCardAddress");	//身份证地址
		String telNumber = busMap.get("telNumber");			//联系电话
		String channelId = busMap.get("channelId");			//渠道id,用户所使用的平台（1：微信 2：APP 3：web）
		String custIp = busMap.get("custIp");				//用户IP地址
		String schoolName = busMap.get("schoolName");		//所在院校--院校全称
		String department = busMap.get("department");		//所在院系--院系全称
		String entranctDate = busMap.get("entranctDate");	//入学时间
		String partnerId = busMap.get("partnerId");	//合作伙伴ID
		
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
			logger.error("调用校园征信接口，初始化征信数据，插入征信记录表异常。busId:" + busId +  ",idCard:" + idCard + "，name：" + userName,e);
			return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "调用校园征信接口，初始化征信数据，插入征信记录表异常。");
		}
		
		String _CROpen = "1" ;//征信结果默认生效
		/*******************************************************前海一鉴通**************************************************************************/
		try {
			UserQhzxVerify isVerify = userCreditDataService.queryVerify(idCard, userName,telNumber,busId);
			if (StatusConstants.QHZX_IDNO_NAME_NO_MATCH_VAL.equals(isVerify.getIsRealIdentity())) { //需要调用一鉴通接口查询
				Map<String, Object> userQhzxVerifyMap = qhzxService.reqQhzxVerify(idCard, userName, telNumber, null, busId);
				
				//异常情况或参数为空情况
				if (EntityUtils.isEmpty(userQhzxVerifyMap.get(ResponseConstants.RESULT_CODE))|| EntityUtils.isEmpty(userQhzxVerifyMap.get(ResponseConstants.DATA)) 
						|| !ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals((String)userQhzxVerifyMap.get(ResponseConstants.RESULT_CODE))) {
					logger.info("前海一致性校验，处理前海时发生问题，问题描述：" + userQhzxVerifyMap.get(ResponseConstants.RESULT_DESC)+ ",返回码：" + userQhzxVerifyMap.get(ResponseConstants.RESULT_CODE) + ",DATA:" + userQhzxVerifyMap.get(ResponseConstants.DATA));
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_HXYJTEXP_ERROR);
//					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_HXYJTEXP_ERROR);
					creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_HXYJTEXP_ERROR,null);
					return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "前海一致性校验，处理前海时发生问题，问题描述：" + userQhzxVerifyMap.get(ResponseConstants.RESULT_DESC));
				}
					
				//前海正常的处理
				UserQhzxVerify userQhzxVerify = CommonUtil.parseJSON2Bean(userQhzxVerifyMap.get(ResponseConstants.DATA).toString(), UserQhzxVerify.class);
				
				
				if (EntityUtils.isEmpty(userQhzxVerify.getIsRealIdentity())) {
					String result = userQhzxVerify == null?null: userQhzxVerify.getIsRealIdentity();
					logger.info("前海一致性校验未通过,校验结果为["+ result +"],身份证号：" + idCard + "，姓名：" + userName);
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_SAME_UNPASS);
//					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_SAME_UNPASS);
					creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_SAME_UNPASS,userQhzxVerify.getId());
					return responseMap(TelecomConstants.CRE_QH_YJT_CODE, "前海一致性校验未通过,校验结果为["+ result +"]");
				}
				
				//判断是否为真是身份
				if(StatusConstants.QHZX_IDNO_NAME_NOT_MATCH_VAL.equals(userQhzxVerify.getIsRealIdentity())){
					logger.info("前海一致性校验明确返回未通过，征信失败。身份证号：" + idCard + "，姓名：" + userName);
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS);
//					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS);
					creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS,userQhzxVerify.getId());
					return responseMap(TelecomConstants.CRE_QH_YJT_CODE, "前海一致性校验明确返回未通过，征信失败。");
				}
				
				if(StatusConstants.QHZX_IDNO_NAME_MATCH_VAL.equals(userQhzxVerify.getIsRealIdentity())){
					logger.info("前海一致性校验通过,继续向下执行");
					creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_SAME_PASS,userQhzxVerify.getId());
				}else{
					logger.info("前海一致性校验未通过,校验结果异常,身份证号：" + idCard + "，姓名：" + userName);
					userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_HXYJT_ERROR);
//					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_HXYJT_ERROR);
					creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_HXYJT_ERROR,userQhzxVerify.getId());
					return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "前海一致性校验未通过,校验结果异常");
				}
			}else if(StatusConstants.QHZX_IDNO_NAME_NOT_MATCH_VAL.equals(isVerify.getIsRealIdentity())){
				logger.info("查询一鉴通本地库，一鉴通身份信息匹配失败，不通过。身份证号：" + idCard + "，姓名：" + userName);
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS);
//				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS);
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_SAME_RETURN_UNPASS,isVerify.getId());
				return responseMap(TelecomConstants.CRE_QH_YJT_CODE, "查询一鉴通本地库，一鉴通身份信息匹配失败，不通过。");
			}else{
				logger.info("查询一鉴通本地库，一鉴通身份信息匹配成功，通过。");
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_SAME_PASS,isVerify.getId());
			}
		} catch (Exception e) {
			logger.error("调用前海一鉴通--> 数据异常: busId:" + busId +  ",idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ,e);
			creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants. CREDIT_QH_RECORD_HXYJT_ERROR.toString(),null);
//			updateJsSchoolBusCreditResult(busId,CreditCodeConstants. CREDIT_QH_RECORD_HXYJT_ERROR);  
			String alarmContent = "调用前海一鉴通--> 数据异常: busId:" + busId +  ",idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ;
			return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "调用前海一鉴通--> 数据异常");
		}
		/*******************************************************前海一鉴通**************************************************************************/

		
		/*******************************************************年龄检查**************************************************************************/
		logger.info("调用年龄判断-->调用年龄判断开关开启。");
		try {
			
			String configAgeSection = (String) (meMap.get(CreditCfgKeyConstants.COLLEGE_AGE_LIMIT_SECTION));
			logger.info("校园征信，查询出配置可以调用征信系统的年龄段为:" + configAgeSection);
			//检查年龄合法性
			boolean isCreditAge = userCreditDataService.isCreditByAge(idCard,configAgeSection);
			if (isCreditAge) {
				logger.info("年龄结果开关生效,年龄未通过,身份证号：" + idCard + "，姓名：" + userName);
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_AGE_RECORD_UNPASS);
//				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_AGE_RECORD_UNPASS);
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_AGE, CreditCodeConstants.CREDIT_TYPE_AGE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_AGE_RECORD_UNPASS,null);
				return responseMap(TelecomConstants.CRE_FAIL, "年龄未通过");
			}else{
				logger.info("年龄通过。");		
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_AGE, CreditCodeConstants.CREDIT_TYPE_AGE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_AGE_RECORD_PASS,null);
			}
		} catch (Exception e) {
			logger.error("调用年龄判断异常：busId:" + busId +  ",身份证号：" + idCard + "，姓名：" + userName , e);
			creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_AGE, CreditCodeConstants.CREDIT_TYPE_AGE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_AGE_RECORD_RROR.toString(),null);
//			updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_AGE_RECORD_RROR);  
			return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "调用年龄判断异常");
		}
		/*******************************************************年龄检查**************************************************************************/
		
		
		/*******************************************************秒趣黑名单**************************************************************************/
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
//				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BL_RECORD_UNPASS);
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_HD_BLACK, blackType, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BL_RECORD_UNPASS.toString(),blackList.getListId());
				return responseMap(TelecomConstants.CRE_FAIL, "命中秒趣黑名单。");
			}else {
				logger.info("调用征信接口，黑名单开关生效，未命中黑名单。busId:" + busId +  ",idCard:" + idCard + "，name：" + userName);
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_HD_BLACK, CreditCodeConstants.CREDIT_TYPE_HD_NOBLACK, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_BL_RECORD_PASS,null);
			}
		} catch (Exception e) {
			logger.error("调用黑名单--> 返回数据异常:busId:" + busId +  ", idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ,e);
			creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_HD_BLACK, CreditCodeConstants.CREDIT_TYPE_HD_NOBLACK, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_MQ_RECORD_BLACK_ERROR.toString(),null);
//			updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_MQ_RECORD_BLACK_ERROR);  
			return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "调用黑名单--> 返回数据异常");
		}
		/*******************************************************秒趣黑名单**************************************************************************/
		
		
		/*******************************************************历史黑名单**************************************************************************/
		try{
			//校验该用户之前是否已经命中了前海或者好贷黑名单
			UserCreditRecord isBlackUser = creditCollegeService.isBlackUser(idCard,userName);
			if (isBlackUser != null) {
				logger.info(userName + "," + idCard  + ",在" + DateUtil.formatDate(isBlackUser.getCreateTime(), DateUtil.FORMAT_SS) + "申请秒趣分期");
				String blackUserResult = "" ;
				String creditModelType = "" ;
				switch (isBlackUser.getReason()) {
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
				default:
					break;
				}
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,blackUserResult);
//				updateJsSchoolBusCreditResult(busId,blackUserResult);
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BLACKLIST_REFUSE, creditModelType, StatusConstants.CREDIT_CHECKING_RESULT_FAILED,blackUserResult,isBlackUser.getCreditId());
				return responseMap(TelecomConstants.CRE_FAIL, "命中历史黑名单。");
			}else{
				logger.info(userName + "," + idCard + "未命中黑名单性质拒贷规则。");
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BLACKLIST_REFUSE, CreditCodeConstants.CREDIT_QH_BLACKLIST_UNCHECKED, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_RECORD_BLACK_PASS.toString(),null);
			}
		}catch(Exception e){
			logger.error(userName + "," + idCard + "，查询历史黑名单异常",e);
			creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BLACKLIST_REFUSE, CreditCodeConstants.CREDIT_QH_BLACKLIST_UNCHECKED, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BL_HISTORY_EXP_ERROR.toString(),null);
//			updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BL_HISTORY_EXP_ERROR); 
			return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "查询历史黑名单异常。");
		}
		/*******************************************************历史黑名单**************************************************************************/
		
		/*******************************************************院校评分**************************************************************************/
		logger.info("调用学历评分-->调用开关开启。");
		try {
			JsschoolEducationScore collegeScore = creditCollegeService.queryCollegeScore(schoolName,department,entranctDate,busId);
			if (!"0".equals(collegeScore.getEducateResult())) {
				logger.info(userName + "," + idCard + "学历评分拒贷。");
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_EDUCATE_SCORE_UNPASS);
//				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_EDUCATE_SCORE_UNPASS);
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_EDUCATION_SCORE, CreditCodeConstants.CREDIT_EDUCATION_SCORE_MODEL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED,CreditCodeConstants.CREDIT_EDUCATE_SCORE_UNPASS,collegeScore.getId());
				return responseMap(TelecomConstants.CRE_FAIL, "学历评分拒贷");
			}else{
				logger.info(userName + "," + idCard + "学历评分通过。");
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_EDUCATION_SCORE, CreditCodeConstants.CREDIT_EDUCATION_SCORE_MODEL, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS,CreditCodeConstants.CREDIT_EDUCATE_SCORE_PASS,collegeScore.getId());
			}
		} catch (Exception e) {
			logger.info(userName + "," + idCard + "院校评分异常。");
			creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_EDUCATION_SCORE, CreditCodeConstants.CREDIT_EDUCATION_SCORE_MODEL, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_XL_RECORD_SCOREEXP_ERROR.toString(),null);
//			updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_XL_RECORD_SCOREEXP_ERROR); 
			return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "院校评分异常。");
		}
		/*******************************************************院校评分**************************************************************************/
		
		/*******************************************************前海黑名单**************************************************************************/
		logger.info("调用前海黑名单征信-->调用开关开启。");
		try {
			logger.info("查询前海黑名单开始");
			Map<String, Object>  userQhzxMsc8004Map = userCreditDataService.queryExistQhBlackListData(userName,idCard,busId);
			if (EntityUtils.isEmpty(userQhzxMsc8004Map.get(ResponseConstants.RESULT_CODE)) 
					|| !ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals((String)userQhzxMsc8004Map.get(ResponseConstants.RESULT_CODE))) {
				logger.info("处理前海黑名单接口时发生问题，问题描述：" + userQhzxMsc8004Map.get(ResponseConstants.RESULT_DESC) + ",返回码：" + userQhzxMsc8004Map.get(ResponseConstants.RESULT_CODE));
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_BLACKEXP_ERROR);
//					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_BLACKEXP_ERROR);
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_VERIFY, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_BLACKEXP_ERROR,null);
				return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "处理前海黑名单接口时发生问题，问题描述：" + userQhzxMsc8004Map.get(ResponseConstants.RESULT_DESC));
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
//								updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_BLACK_UNPASS);
							creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_BLACK_UNPASS,userQhzxMsc8004.getId());
							logger.info("前海征信结果开关打开，结果生效，命中黑名单。");
							return responseMap(TelecomConstants.CRE_FAIL, "前海黑名单征信命中黑名单。");
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
			creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_BLACK_PASS,null);
			
		} catch (Exception e1) {
			logger.error("调用前海征信黑名单接口（8004）异常：busId:" + busId +  ",身份证号：" + idCard + "，姓名：" + userName , e1);
			userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_BLACKE_RROR);
//				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_BLACKE_RROR);
			creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_BLACKEXP_ERROR,null);
			return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "调用前海征信黑名单接口（8004）异常。");
		}
		logger.info("查询前海黑名单结束");
		/*******************************************************前海黑名单**************************************************************************/
		
		
		/*******************************************************百融黑名单**************************************************************************/
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
//					updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_SL_UNPASS);
					creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_SPECIAL_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_SL_UNPASS,specialIdCard.get(0).getBrReqId());
					return responseMap(TelecomConstants.CRE_FAIL, "命中百融贷特殊名单。");
				}else{
					logger.info( userName + "未命中百融贷特殊名单，征信通过");
					creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_SPECIAL_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_BR_RECORD_SL_PASS,specialIdCard.get(0).getBrReqId());
				}
			}else{
				logger.info("调用百融特殊名单出现错误，错误码：" + brSpeciallyListMap.get(ResponseConstants.RETURN_CODE) + ",错误描述:" + brSpeciallyListMap.get(ResponseConstants.RETURN_DESC));
				String alarmContent = "调用百融特殊名单核查接口出错,返回错误代码：" + ",姓名：" + userName + ",身份证号：" + idCard;
				sysAlarmService.alarm(alarmContent);
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_SPECIAL_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_SPECIALEXP_ERROR,null);
			}
			
		} catch (Exception e) {
			 logger.error("前调用百融贷特殊名单核查征信发生错误，身份证号：" + idCard + "，姓名：" + userName,e);
			 userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_SPECIALEXP_ERROR);
//			 updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_SPECIALEXP_ERROR);
			 creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), CreditCfgKeyConstants.OPEN, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_SPECIAL_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_SPECIALEXP_ERROR,null);
			 return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "前调用百融贷特殊名单核查征信发生错误。");
		}
		/*******************************************************百融黑名单**************************************************************************/
		
		/*******************************************************前海常贷客**************************************************************************/
		logger.info("调用前海常贷客征信-->调用开关开启。");
		try {
			//查询本地库中是否存在常贷客信息
			Map<String, Object>  userQhzxMsc8037Map = userCreditDataService.queryExistQhLoanList(userName,idCard,busId);
			
			logger.info("前海征信前海常贷客接口，征信结果开关生效");
			if (EntityUtils.isEmpty(userQhzxMsc8037Map.get(ResponseConstants.RESULT_CODE)) 
					|| !ResponseConstants.QHZX_RESULT_CODE_SUCCESS.equals((String)userQhzxMsc8037Map.get(ResponseConstants.RESULT_CODE))) {
				logger.info("处理前海常贷客接口时发生问题，问题描述：" + userQhzxMsc8037Map.get(ResponseConstants.RESULT_DESC) + ",返回码：" + userQhzxMsc8037Map.get(ResponseConstants.RESULT_CODE));
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR);
//				updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR);
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR,null);
				return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "处理前海常贷客接口时发生问题，问题描述：" + userQhzxMsc8037Map.get(ResponseConstants.RESULT_DESC));
			}
			
			if (EntityUtils.isNotEmpty(userQhzxMsc8037Map.get(ResponseConstants.DATA)) 
					&& "E000000".equals((CommonUtil.parseJSON2Bean(userQhzxMsc8037Map.get(ResponseConstants.DATA).toString(), UserQhzxLoan.class)).getErcode())) {

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
//						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
						return creditCollegeService.loanCreditResult(userQhzxMsc8037,creditRecord,"BAK",_CROpen);
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
//						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
						return creditCollegeService.loanCreditResult(userQhzxMsc8037,creditRecord,"MCL",_CROpen);
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
//						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
						return creditCollegeService.loanCreditResult(userQhzxMsc8037,creditRecord,"P2P",_CROpen);
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
//						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
						return creditCollegeService.loanCreditResult(userQhzxMsc8037,creditRecord,"ASM",_CROpen);
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
//						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
						return creditCollegeService.loanCreditResult(userQhzxMsc8037,creditRecord,"TRU",_CROpen);
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
//						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
						return creditCollegeService.loanCreditResult(userQhzxMsc8037,creditRecord,"LEA",_CROpen);
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
//						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
						return creditCollegeService.loanCreditResult(userQhzxMsc8037,creditRecord,"CRF",_CROpen);
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
//						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
						return creditCollegeService.loanCreditResult(userQhzxMsc8037,creditRecord,"INV",_CROpen);
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
//						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
						return creditCollegeService.loanCreditResult(userQhzxMsc8037,creditRecord,"CNS",_CROpen);
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
//						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
						return creditCollegeService.loanCreditResult(userQhzxMsc8037,creditRecord,"INS",_CROpen);
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
//						updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOAN_UNPASS);
						return creditCollegeService.loanCreditResult(userQhzxMsc8037,creditRecord,"THR",_CROpen);
					}else{
						logger.info("THR未命中");
					}
				}else{
					logger.info("THR命中生效开关关闭，征信通过。");
				}
				
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_LOAN_PASS,userQhzxMsc8037.getId());
			}else{
				logger.info("前海常贷客征信结果开关生效，未查询到任何常贷客数据,idCard:" + idCard + "，name：" + userName);
				creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_QH_RECORD_LOAN_PASS,null);
			}
		} catch (Exception e1) {
			logger.error("调用前海常贷客接口（8037）异常：busId:" + busId+  ",身份证号：" + idCard + "，姓名：" + userName , e1);
			creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_QH, CreditCodeConstants.CREDIT_TYPE_QH_LOAN, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR,null);
//			updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR);
			userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_QH_RECORD_LOANEXP_ERROR);
			//插入告警表
			String alarmContent = "调用前海常贷客接口（8037）异常,idCard："+  idCard + "，姓名：" + userName ;
			return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "调用前海常贷客接口（8037）异常");
		}
		/*******************************************************前海常贷客**************************************************************************/
		
		/*******************************************************百融多次申请核查**************************************************************************/
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
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						
						logger.info("身份证在银行-总申清次数");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM3IdBankAllnum(),m3IdBankAllnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						logger.info("身份证在银行-申清过的机构数。");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM3IdBankOrgnum(),m3IdBankOrgnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
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
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						
						logger.info("身份证在非银行-总申清次数");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM3IdNotbankAllnum(),m3IdNoBankAllnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						logger.info("身份证在非银行-申清过的机构数。");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM3IdNotbankOrgnum(),m3IdNoBankOrgnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
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
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						
						logger.info("身份证在银行-总申清次数");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM6IdBankAllnum(),m6IdBankAllnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						logger.info("身份证在银行-申清过的机构数。");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM6IdBankOrgnum(),m6IdBankOrgnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
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
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						
						logger.info("身份证在非银行-总申清次数");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM6IdNotbankAllnum(),m6IdNoBankAllnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						logger.info("身份证在非银行-申清过的机构数。");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM6IdNotbankOrgnum(),m6IdNoBankOrgnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
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
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						
						logger.info("身份证在银行-总申清次数");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM12IdBankAllnum(),m12IdBankAllnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						logger.info("身份证在银行-申清过的机构数。");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM12IdBankOrgnum(),m12IdBankOrgnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
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
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						
						logger.info("身份证在非银行-总申清次数");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM12IdNotbankAllnum(),m12IdNoBankAllnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
						logger.info("身份证在非银行-申清过的机构数。");
						if (userCreditDataService.applyLoanCreditCompare(brApplyLoan.getM12IdNotbankOrgnum(),m12IdNoBankOrgnum)) {
//							updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_AL_UNPASS);
							return creditCollegeService.applyLoanCreditResult(brApplyLoan,creditRecord,_CROpen);
						}
					}
	
					creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_APPLOAN_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_BR_RECORD_AL_PASS,brApplyLoan.getBrReqId());
				}else{
					logger.info("百融多次申请核查无数据返回。");
					creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_APPLOAN_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_BR_RECORD_AL_PASS,null);
				}

		  }else{
			   creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_APPLOAN_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR,null);
			   userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR);
//			   updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR);
			   logger.error("调用百融多次申请核查接口，姓名:" + userName  + ",身份证号:" + idCard);
			   return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "调用百融多次申请核查接口出错");
		  }
			
		} catch (Exception e) {
			 creditCollegeService.insertShoolCreditModelRecord(creditRecord.getCreditId(), _CROpen, CreditCodeConstants.CREDIT_BR_LIST, CreditCodeConstants.CREDIT_TYPE_BR_APPLOAN_LIST_REFUSE, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR,null);
			 logger.error("调用百融贷多次申请核查接口征信发生错误，身份证号：" +idCard + "，姓名：" + userName,e);
			 userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR);
//			 updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_BR_RECORD_APPLOANEXP_ERROR);
			 return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "调用百融贷多次申请核查接发生错误");
		}
		/*******************************************************百融多次申请核查**************************************************************************/
		
		/*******************************************************请求91征信**************************************************************************/
		logger.info("调用91征信-->调用模快开关开启。");
		try {
			UserJyzxRecord jyzxRecord = userJyzxService.queryLastestEffectJyzxRecord(idCardAddress, userName);
			
			if(EntityUtils.isNotEmpty(jyzxRecord)){
//				List<UserJyzxRecordItem> itemList = userCreditDataService.queryJyCreditResult(userName,idCard);
				List<UserJyzxRecordItem> itemList = userJyzxService.queryUserJyzxRecordItemsByTrxNo(jyzxRecord.getTrxNo());
				//根据91规则判断结果
				Map<String, Object> retMap = userJyzxService.judgeStudentCreditInfos(busId, jyzxRecord, itemList);
				if(EntityUtils.isEmpty(retMap) 
						|| EntityUtils.isEmpty(retMap.get(ResponseConstants.RETURN_CODE))
						|| TelecomConstants.CRE_FAIL.equals(retMap.get(ResponseConstants.RETURN_CODE))){
					logger.info("91本地有数据，并且征信不通过。");
					return responseMap(TelecomConstants.CRE_FAIL, "91本地有数据，并且征信不通过");
				}
			}else{
				//调用91征信接口
//				String collegeJyWaitTime = (String) (meMap.get(CreditCfgKeyConstants.COLLEGE_JY_WAIT_TIME));
				String collegeJyWaitTime = "10";
				logger.info("调用91征信，配置等待时间：" + collegeJyWaitTime + "秒。");
				Date startDate = new Date();
				logger.info("调用91征信，开始时间" + DateUtil.formatDate(startDate, DateUtil.FORMAT_SS));
				userCreditDataService.requestJYCredit(userName,idCard,telNumber,busId,StatusConstants.JY_CALL_TYPE_STUDENT_CREDIT);
				Date endDate = new Date();
				logger.info("调用91征信，开始时间" + DateUtil.formatDate(endDate, DateUtil.FORMAT_SS));
				long interval = (endDate.getTime() - startDate.getTime());
				logger.info("调用91征信耗时：" + String.valueOf(interval) + "秒。");
				
				if (EntityUtils.isEmpty(collegeJyWaitTime)) {
					logger.error("调用91征信,配置等待91时间为空--》COLLEGE_JY_WAIT_TIME");
				}else{
					String creditResult  = "";
					if (interval > Long.parseLong(collegeJyWaitTime) * 1000) {
						logger.info("调用91征信,耗时大于" + collegeJyWaitTime + "秒");
						creditResult = creditCollegeService.queryJyCreditResult(busId);
					}else{
						int leftTime = (int) (Long.parseLong(collegeJyWaitTime) * 1000 - interval);
						logger.info("调用91征信，剩余时间：" + leftTime + "毫秒。");
						try {
							Thread.sleep(leftTime);
							logger.info("调用91征信，休息10秒");
						} catch (InterruptedException e) {
							logger.error("调用91征信,等待10秒异常。",e);
						}
						
						creditResult = creditCollegeService.queryJyCreditResult(busId);
						logger.info("查询91征信结果：" + creditResult);
						
					}
					
					//判断征信结果
					if (StatusConstants.CREDIT_CHECKING_RESULT_FAILED.toString().equals(creditResult)) {
						logger.info("调用91征信后，查询91征信结果：未通过。");
						return responseMap(TelecomConstants.CRE_FAIL, "调用91征信后，查询91征信结果：未通过。");
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("调用91征信--> 调用91征信接口返回数据异常: busId:" + busId +  ",idCard："+ idCard + ",contactPhone:" + telNumber +  ",userName:" + userName ,e);
			creditCollegeService.insertShoolCreditModelRecord(creditId, "1", CreditCodeConstants.CREDIT_91_BLACKLIST, CreditCodeConstants.CREDIT_TYPE_91_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_JY_RECORD_ERROR,null);
//			updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_JY_RECORD_ERROR);
			return responseMap(TelecomConstants.API_SYSTEM_EXCEPTION, "调用91征信接口返回数据异常");
		}
		/*******************************************************91征信**************************************************************************/
		
		/*******************************************************前海征信好信度**************************************************************************/
		logger.info("调用前海征信好信度-->调用开关开启。");
		String bankCardNumber = "**************";
		
		UserQhzxCredit userQhzx = new UserQhzxCredit();
		userQhzx.setName(userName);
		userQhzx.setIdNo(idCard);
		List<UserQhzxCredit> qhCreditList = userQhzxCreditDao.queryQhzxCredit(userQhzx);
		UserQhzxCredit userQhzxCredit = null;
		if (qhCreditList != null && qhCreditList.size() > 0) {
			userQhzxCredit = qhCreditList.get(0);
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
				}else{
					logger.info("查询核心好信度接口，计算获取天数超过90天，结束征信点。" + day  + "天。调用好信度接口");
					userQhzxCredit = null;
				}
			}
		}
		
		if(EntityUtils.isEmpty(userQhzxCredit)){
			try {
				qhzxService.requestQhCredit(idCard, userName, bankCardNumber, telNumber, null, busId);
			} catch (Exception e) {
				logger.error("获取前海好信度数据出错，busId：" + busId, e);
			}
		}
		/*******************************************************前海征信好信度**************************************************************************/
		
		userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_PASS,CreditCodeConstants.CREDIT_RECORD_CLOSED);
//		updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_RECORD_CLOSED);
		return responseMap(TelecomConstants.CRE_SUCCESS, "该用户征信通过");
	}

	@Override
	public boolean isCreditByName(String name) {
		// TODO Auto-generated method stub
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
	
	private Map<String, String> responseMap(String resultCode, String desc){
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put(ResponseConstants.RETURN_CODE, resultCode);
		resMap.put(ResponseConstants.RETURN_DESC, desc);
		
		return resMap;
	}
	
}
