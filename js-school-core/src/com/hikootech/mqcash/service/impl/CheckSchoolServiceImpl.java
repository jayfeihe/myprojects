package com.hikootech.mqcash.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.TelecomConstants;
import com.hikootech.mqcash.dao.CheckSchoolDAO;
import com.hikootech.mqcash.po.UserSchoolRoll;
import com.hikootech.mqcash.service.CheckSchoolService;
import com.hikootech.mqcash.util.BASE64Util;
import com.hikootech.mqcash.util.CommonUtils;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.FastJsonUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.JRAlgorithmUtils;
import com.hikootech.mqcash.util.SHA256Util;

/**
 * 
 * CheckSchoolService
 * 
 * @function:(学籍核查信息Service)
 * @create time:Oct 9, 2015 11:06:01 AM
 * @version 1.0.0
 * @author:张海达
 */
@Service("checkSchoolService")
public class CheckSchoolServiceImpl implements CheckSchoolService {

	private static Logger logger = LoggerFactory.getLogger(CheckSchoolServiceImpl.class);

	@Autowired
	private CheckSchoolDAO checkSchoolDAO;

	@Override
	public Map<String, Object> queryCheckSchool(Map<String, String> queryMap) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			String checkSchoolUrl = ConfigUtils.getProperty("check_school_url");
			String des3Key = ConfigUtils.getProperty("check_shcool_des3key");
			String merchantCode = ConfigUtils.getProperty("check_shcool_merchantCode");
			String charset = ConfigUtils.getProperty("check_school_charset");
			String version = ConfigUtils.getProperty("check_shcool_version");
			String accountId = ConfigUtils.getProperty("check_shcool_account_id");
			String accountType = ConfigUtils.getProperty("check_shcool_account_type");

			// 1.基础请求数据
			JSONObject json = new JSONObject();
			json.put("accountId", accountId);
			json.put("accountType", accountType);
			json.put("userName", queryMap.get("userName"));
			json.put("documentNo", queryMap.get("documentNo"));
			json.put("collegeLevel", queryMap.get("collegeLevel"));
			json.put("startTime", queryMap.get("startTime"));
			json.put("college", queryMap.get("college"));
			json.put("merchantcode", merchantCode);

			// 2.构造base64编码的请求数据
			String encodeRequestData = makeEncodeRequestJson(json.toJSONString(), des3Key);
			logger.info("encodeRequestData---" + encodeRequestData);
			// 3.生成签名数据
			String sign = getSignature(version, charset, encodeRequestData, des3Key);

			JSONObject param = new JSONObject();
			param.put("version", version);
			param.put("checkSign", sign);
			param.put("data", encodeRequestData);
			param.put("charset", charset);
			// 4.进行urlEncode
			String reqStr = JRAlgorithmUtils.urlEncode(param.toJSONString(), "UTF-8");
			logger.info("reqStr---" + reqStr);
			// 5.发送请求
			String feedback = streamPost(checkSchoolUrl, reqStr);
			logger.info("feedback---" + feedback);
			// 6.数据解码
			Map<String, String> map = FastJsonUtils.toBean(feedback, HashMap.class);
			
			if(!"000".equals(map.get("resultCode")) && !"015".equals(map.get("resultCode"))){
				logger.info("学籍查询异常，返回异常原因：" + map.get("resultInfo"));
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "学籍查询异常");
				return retMap;
			}
			
			//验证签名
			String retSign = this.getSignature(version, charset, map.get("data"), des3Key);
			if(!retSign.equals(map.get("checkSign"))){
				logger.error("验证签名失败，返回结果签名：" + map.get("checkSign") + "，返回结果生成签名：" + retSign);
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "学籍查询异常");
				return retMap;
			}
			logger.info("验证签名成功");
			
			String responseData = decodeResponseJson(feedback);
			logger.info("responseData---" + responseData);
			
			Map<String, String> dataMap = FastJsonUtils.toBean(responseData, HashMap.class);
			String id = "" ;
			UserSchoolRoll userSchoolRoll = new UserSchoolRoll();
			if (EntityUtils.isNotEmpty(queryMap.get("id"))) {
				id = queryMap.get("id");
			}else{
				id = GenerateKey.getId(CommonConstants.CHECK_SCHOOL_ID_PREFIX, ConfigUtils.getProperty("db_id"));
			}
			userSchoolRoll.setId(id);
			userSchoolRoll.setRequestNo(queryMap.get("busId"));
			userSchoolRoll.setDocumentNo(queryMap.get("documentNo"));
			userSchoolRoll.setName(queryMap.get("userName"));
			userSchoolRoll.setCollege(queryMap.get("college"));
			userSchoolRoll.setStartTime(queryMap.get("startTime"));
			userSchoolRoll.setCollegeLevel(queryMap.get("collegeLevel")); // 学历层次
			userSchoolRoll.setDocumentNoCheckResult(this.exchangeCheckResult(dataMap.get("documentNoCheckResult")));
			userSchoolRoll.setNameCheckResult(this.exchangeCheckResult(dataMap.get("nameCheckResult")));
			userSchoolRoll.setCollegeLevelCheckResult(this.exchangeCheckResult(dataMap.get("collegeLevelCheckResult")));
			userSchoolRoll.setCollegeCheckResult(this.exchangeCheckResult(dataMap.get("collegeCheckResult")));
			userSchoolRoll.setStartTimeCheckResult(this.exchangeCheckResult(dataMap.get("startTimeCheckResult")));
			userSchoolRoll.setStatus(StatusConstants.CHECK_SHCOOL_QUERY_SUCCESS);
			userSchoolRoll.setCreateTime(new Date());
			userSchoolRoll.setDescp(map.get("resultCode"));

			this.insertCheckSchoolInfo(userSchoolRoll);
			
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, "学籍查询成功");
			retMap.put("UserSchoolRoll", userSchoolRoll);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("学籍查询异常", e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "学籍查询异常");
		}
		
		return retMap;
	}
	
	private String exchangeCheckResult(String source){
		if(EntityUtils.isEmpty(source)){
			return StatusConstants.CHECK_SHCOOL_INCORRECT;
		}else if("不一致".equals(source)){
			return StatusConstants.CHECK_SHCOOL_INCORRECT;
		}else if("一致".equals(source)){
			return StatusConstants.CHECK_SHCOOL_CORRECT;
		}
		return StatusConstants.CHECK_SHCOOL_INCORRECT;	
	}

	private String getSignature(String version, String charset, String data, String privateKey) throws Exception {
		String localSign = new String(SHA256Util.encrypt(version + charset + data + privateKey));
		return localSign;
	}

	private String makeEncodeRequestJson(String requestJson, String des3key) throws Exception {
		String reqData = BASE64Util.encode(requestJson.getBytes("UTF-8"));
		return reqData;
	}

	private String decodeResponseJson(String responseJson) throws Exception {
		Map<String, String> map = FastJsonUtils.toBean(responseJson, HashMap.class);
		String responseData = map.get("data");
		byte[] decodeBase64 = BASE64Util.decode(responseData);
		String strRS = new String(decodeBase64, "UTF-8");
		return strRS;
	}

	private String streamPost(String url, String param) {
		// HashMap类型的对象传输
		HttpClient client = null;
		PostMethod postMethod = null;
		try {
			client = new HttpClient();
			postMethod = new PostMethod(url);
			postMethod.setRequestBody(param);
			client.executeMethod(postMethod);
			String result = JRAlgorithmUtils.urlDecode(postMethod.getResponseBodyAsString(), "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}

	}

	@Override
	public void insertCheckSchoolInfo(UserSchoolRoll userSchoolRoll) {
		checkSchoolDAO.insertCheckSchoolInfo(userSchoolRoll);
	}

	@Override
	public List<UserSchoolRoll> queryUserSchoolRollInfo(Map<String, String> queryMap) {
		return checkSchoolDAO.queryUserSchoolRollInfo(queryMap);
	}

}
