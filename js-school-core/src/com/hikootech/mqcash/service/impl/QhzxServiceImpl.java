package com.hikootech.mqcash.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.po.UserQhzxBlackList;
import com.hikootech.mqcash.po.UserQhzxCredit;
import com.hikootech.mqcash.po.UserQhzxLoan;
import com.hikootech.mqcash.po.UserQhzxVerify;
import com.hikootech.mqcash.qhzx.BusiDataItemRspQhMSC8004;
import com.hikootech.mqcash.qhzx.BusiDataItemRspQhMSC8005;
import com.hikootech.mqcash.qhzx.BusiDataItemRspQhMSC8017;
import com.hikootech.mqcash.qhzx.BusiDataItemRspQhMSC8037;
import com.hikootech.mqcash.qhzx.DataSecurityUtil;
import com.hikootech.mqcash.qhzx.HeadersRspQhzx;
import com.hikootech.mqcash.qhzx.HttpRequestUtil;
import com.hikootech.mqcash.qhzx.QhCustInfo;
import com.hikootech.mqcash.qhzx.QhzxUtil;
import com.hikootech.mqcash.service.QhzxService;
import com.hikootech.mqcash.service.SysAlarmService;
import com.hikootech.mqcash.service.UserQhzxBlackListService;
import com.hikootech.mqcash.service.UserQhzxCreditService;
import com.hikootech.mqcash.service.UserQhzxLoanService;
import com.hikootech.mqcash.service.UserQhzxVerifyService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.UUIDTools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("qhzxService")
public class QhzxServiceImpl implements QhzxService {
	private static Logger logger = LoggerFactory.getLogger(QhzxServiceImpl.class);

	@Autowired
	private UserQhzxBlackListService userQhzxBlackListService;
	@Autowired
	private UserQhzxCreditService userQhzxMsc8005Service;
	@Autowired
	private UserQhzxVerifyService userQhzxVerifyService;
	@Autowired
	private UserQhzxLoanService userQhzxLoanService;
	@Autowired
	private SysAlarmService sysAlarmService;

	@Override
	public Map<String, Object> requestQhBlackList(String idNo, String name, String id, String totalInfoId) {
		logger.info("请求前海黑名单service开始，参数：idNo-->" + idNo + ",name-->" + name + ",id-->" + id + ",totalInfoId-->"
				+ totalInfoId);

		Map<String, Object> retMap = new HashMap<String, Object>();
		if (EntityUtils.isEmpty(id)) {
			id = UUIDTools.getFormatUUID();
		}

		// 校验参数
		if (EntityUtils.isEmpty(idNo)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "idNo is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}
		if (EntityUtils.isEmpty(name)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "name is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}
		if (EntityUtils.isEmpty(totalInfoId)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "totalInfoId is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}

		// 组装请求信息
		List<QhCustInfo> custInfoList = new ArrayList<QhCustInfo>();
		String batchNo = UUIDTools.getFormatUUID();
		String tranNo = GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_TRANS_NO_PREFIX,
				ConfigUtils.getProperty("db_id"));
		QhCustInfo custInfo = new QhCustInfo();
		custInfo.setIdNo(idNo);
		custInfo.setIdType("0");
		custInfo.setName(name);
		custInfo.setSeqNo(GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_SEQ_NO_PREFIX,
				ConfigUtils.getProperty("db_id")));
		custInfo.setEntityAuthCode(GenerateKey.getId(null, ConfigUtils.getProperty("db_id")));
		custInfo.setEntityAuthDate(DateUtil.getCurDateStr("yyyy-MM-dd HH:mm:ss"));
		custInfo.setReasonCode("01");

		custInfoList.add(custInfo);

		Map<String, UserQhzxBlackList> qhmscMap = new HashMap<String, UserQhzxBlackList>();
		QhCustInfo qhCustInfo = custInfoList.get(0);
		UserQhzxBlackList qhmscForMap = new UserQhzxBlackList();
		qhmscForMap.setId(id);
		qhmscForMap.setIdNo(qhCustInfo.getIdNo());
		qhmscForMap.setIdType(qhCustInfo.getIdType());
		qhmscForMap.setName(qhCustInfo.getName());
		qhmscForMap.setReasonCode(qhCustInfo.getReasonCode());
		qhmscForMap.setEntityAuthCode(qhCustInfo.getEntityAuthCode());
		try {
			qhmscForMap
					.setEntityAuthDate(DateUtil.transStrToDate(qhCustInfo.getEntityAuthDate(), "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e2) {
			qhmscForMap.setEntityAuthDate(new Date());
		}
		qhmscForMap.setCreateTime(DateUtil.getCurDate());
		qhmscForMap.setSeqNo(qhCustInfo.getSeqNo());
		qhmscForMap.setBatchNo(batchNo);
		qhmscForMap.setTransNo(tranNo);
		qhmscForMap.setTotalInfoId(totalInfoId);
		qhmscMap.put(qhCustInfo.getSeqNo(), qhmscForMap);

		String sfUrl8004 = ConfigUtils.getProperty("sendHttpsUrl8004");
		String message8004 = "";

		try {
			message8004 = QhzxUtil.getQhzxReq8004Msg(custInfoList, batchNo, tranNo);
		} catch (Exception e) {
			logger.error("组装前海黑名单请求报文发生错误", e);
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "组装前海黑名单请求报文发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_MAKE_UP_THIRD_MSG);
			return retMap;
		}

		// 发送信息
		JSONObject msgJSON = null;

		try {
			msgJSON = postHttpsRequest(sfUrl8004, message8004);
		} catch (Exception e) {
			logger.error("请求前海发生错误", e);

			// 插入告警表
			String alarmContent = "前海黑名单访问异常，姓名：" + qhCustInfo.getName() + ",身份证号：" + qhCustInfo.getIdNo();
			try {
				sysAlarmService.alarm(alarmContent);
			} catch (Exception e1) {
				logger.error("前海黑名单访问异常，姓名：" + qhCustInfo.getName() + ",身份证号：" + qhCustInfo.getIdNo(), e1);
			}

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "与前海黑名单交互发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_INTERACTION_THIRD);
			return retMap;
		}

		// 验证签名
		try {
			DataSecurityUtil.verifyData(msgJSON.getString("busiData"),
					msgJSON.getJSONObject("securityInfo").getString("signatureValue"));
			logger.info("验签ok！！！");
		} catch (Exception e) {
			logger.error("验签失败！！！");

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海返回报文验签失败");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_SIGN_WRONG);
			return retMap;
		}

		// 解析
		BusiDataItemRspQhMSC8004 rsp = null;
		try {

			// 报文头
			String rspHeaderStr = msgJSON.getString("header");
			HeadersRspQhzx rspHeader = (HeadersRspQhzx) JSONObject.toBean(JSONObject.fromObject(rspHeaderStr),
					HeadersRspQhzx.class);

			if (!"E000000".equals(rspHeader.getRtCode()) && !"E000996".equals(rspHeader.getRtCode())) {
				logger.error("前海黑名单返回的信息头错误码：" + rspHeader.getRtCode());
				throw new RuntimeException("前海黑名单信息头错误码不是E000000和E000996，而是：" + rspHeader.getRtCode());
			}

			// 报文业务信息
			logger.info("响应BusiData密文：" + msgJSON.getString("busiData"));

			String rspBusiData = DataSecurityUtil.decrypt(msgJSON.getString("busiData"),
					ConfigUtils.getProperty("busiDataKey"));
			logger.info("前海黑名单响应BusiData明文：" + rspBusiData);

			String recordsStr = JSONObject.fromObject(rspBusiData).get("records").toString();

			JSONArray records = JSONArray.fromObject(recordsStr);
			rsp = (BusiDataItemRspQhMSC8004) JSONObject.toBean((JSONObject) records.get(0),
					BusiDataItemRspQhMSC8004.class);

		} catch (Exception e) {
			logger.error("解析前海黑名单反馈报文发生错误", e);

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "解析前海黑名单报文发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_RESOLUTION_THIRD_MSG);
			return retMap;
		}

		// 入库 查询到返回对象转化的json串，没查到返回null
		UserQhzxBlackList qhmsc = qhmscMap.get(rsp.getSeqNo());
		if ("E000000".equals(rsp.getErCode())) {
			qhmsc.setSourceId(rsp.getSourceId());
			qhmsc.setState(rsp.getState());
			qhmsc.setDataStatus(rsp.getDataStatus());
			qhmsc.setUpdateTime(DateUtil.getCurDate());
			qhmsc.setErCode(rsp.getErCode());
			qhmsc.setGradeQuery(rsp.getGradeQuery());
			retMap.put(ResponseConstants.DATA, JSONObject.fromObject(qhmsc).toString());
		} else {
			qhmsc.setErCode(rsp.getErCode());
			qhmsc.setUpdateTime(DateUtil.getCurDate());
			qhmsc.setErMsg(rsp.getErMsg());
			retMap.put(ResponseConstants.DATA, null);
		}

		try {
			userQhzxBlackListService.saveQhzxBlackList(qhmsc);
		} catch (Exception e) {
			logger.error("前海黑名单插入数据库时发生错误", e);
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海黑名单插入数据库时发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_DATABASE);
			return retMap;
		}

		// E000000 为查到信息 E000996：没有查到信息
		if (!"E000000".equals(rsp.getErCode()) && !"E000996".equals(rsp.getErCode())) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海黑名单错误信息返回码非E000000、E000996，而是"+ rsp.getErCode());
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_ERCODE_FAILED);
			return retMap;
		}
		
		// 返回信息
		retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_SUCCESS);
		retMap.put(ResponseConstants.RESULT_DESC, "解析前海黑名单报文成功");

		return retMap;
	}

	@Override
	public Map<String, Object> requestQhCredit(String idNo, String name, String cardNo, String mobileNum, String id,
			String busId) throws Exception {
		logger.info("请求前海好信度service开始，参数：idNo-->" + idNo + ",name-->" + name + ",mobileNum-->" + mobileNum + ",id-->"
				+ id + ",busId-->" + busId);

		Map<String, Object> retMap = new HashMap<String, Object>();

		if (EntityUtils.isEmpty(id)) {
			id = UUIDTools.getFormatUUID();
		}

		// 若不存在银行卡号直接填*
		if (EntityUtils.isEmpty(cardNo)) {
			cardNo = "**************";
		}

		// 校验参数
		if (EntityUtils.isEmpty(idNo)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "idNo is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}
		if (EntityUtils.isEmpty(name)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "name is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}
		if (EntityUtils.isEmpty(mobileNum)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "mobileNum is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}
		if (EntityUtils.isEmpty(busId)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "busId is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}

		// 组装请求报文
		List<QhCustInfo> custInfoList = new ArrayList<QhCustInfo>();
		QhCustInfo custInfo = new QhCustInfo();
		custInfo.setIdNo(idNo);
		custInfo.setIdType("0");
		custInfo.setName(name);
		custInfo.setSeqNo(GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_SEQ_NO_PREFIX,
				ConfigUtils.getProperty("db_id")));
		custInfo.setEntityAuthCode(GenerateKey.getId(null, ConfigUtils.getProperty("db_id")));
		custInfo.setEntityAuthDate(DateUtil.getCurDateStr("yyyy-MM-dd HH:mm:ss"));
		custInfo.setReasonCode("01");
		custInfo.setCardNo(cardNo);
		custInfo.setMobileNo(mobileNum);
		custInfoList.add(custInfo);

		String batchNo = UUIDTools.getFormatUUID();
		String tranNo = GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_TRANS_NO_PREFIX,
				ConfigUtils.getProperty("db_id"));
		Map<String, UserQhzxCredit> qhmscMap = new HashMap<String, UserQhzxCredit>();
		QhCustInfo qhCustInfo = custInfoList.get(0);

		UserQhzxCredit qhmscForMap = new UserQhzxCredit();
		qhmscForMap.setId(id);
		qhmscForMap.setIdNo(qhCustInfo.getIdNo());
		qhmscForMap.setIdType(qhCustInfo.getIdType());
		qhmscForMap.setName(qhCustInfo.getName());
		qhmscForMap.setMobileNo(qhCustInfo.getMobileNo());
		qhmscForMap.setEntityAuthCode(qhCustInfo.getEntityAuthCode());
		qhmscForMap.setEntityAuthDate(DateUtil.transStrToDate(qhCustInfo.getEntityAuthDate(), "yyyy-MM-dd HH:mm:ss"));
		qhmscForMap.setSeqNo(qhCustInfo.getSeqNo());
		qhmscForMap.setCardNo(qhCustInfo.getCardNo());
		qhmscForMap.setReasonCode(qhCustInfo.getReasonCode());
		qhmscForMap.setEmail(qhCustInfo.getEmail());
		qhmscForMap.setWeiboNo(qhCustInfo.getWeiboNo());
		qhmscForMap.setWeixinNo(qhCustInfo.getWeiXinNo());
		qhmscForMap.setQqNo(qhCustInfo.getQqNo());
		qhmscForMap.setTaobaoNo(qhCustInfo.getTaobaoNo());
		qhmscForMap.setJdNo(qhCustInfo.getJdNo());
		qhmscForMap.setAmazonNo(qhCustInfo.getAmazonNo());
		qhmscForMap.setYhdNo(qhCustInfo.getYhdNo());
		qhmscForMap.setCreateTime(DateUtil.getCurDate());
		qhmscForMap.setBatchNo(batchNo);
		qhmscForMap.setTransNo(tranNo);
		qhmscForMap.setTotalInfoId(busId);
		qhmscMap.put(qhCustInfo.getSeqNo(), qhmscForMap);

		String sfUrl8005 = ConfigUtils.getProperty("sendHttpsUrl8005");
		String message8005 = "";

		try {
			message8005 = QhzxUtil.getQhzxReq8005Msg(custInfoList, batchNo, tranNo);
		} catch (Exception e) {
			logger.error("组装前海好信度请求报文发生错误", e);

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "组装前海好信度请求报文发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_MAKE_UP_THIRD_MSG);
			return retMap;
		}

		// 发送报文
		JSONObject msgJSON = null;
		try {
			msgJSON = postHttpsRequest(sfUrl8005, message8005);
		} catch (Exception e) {
			logger.error("请求前海好信度发生错误", e);

			// 插入告警表
			String alarmContent = "前海好信度访问异常！姓名：" + qhCustInfo.getName() + ",身份证号：" + qhCustInfo.getIdNo();
			try {
				sysAlarmService.alarm(alarmContent);
			} catch (Exception e1) {
				logger.error("前海好信度访问异常！idCard:" + qhCustInfo.getIdNo(), e1);
			}

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "请求前海好信度发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_INTERACTION_THIRD);
			return retMap;
		}

		// 验证签名
		try {
			DataSecurityUtil.verifyData(msgJSON.getString("busiData"),
					msgJSON.getJSONObject("securityInfo").getString("signatureValue"));
			logger.info("验签ok！！！");
		} catch (Exception e) {
			logger.error("验签失败！！！");

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海返回报文验签失败");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_SIGN_WRONG);
			return retMap;
		}
		// 解析
		BusiDataItemRspQhMSC8005 rsp = null;
		try {
			// 报文头
			String rspHeaderStr = msgJSON.getString("header");
			HeadersRspQhzx rspHeader = (HeadersRspQhzx) JSONObject.toBean(JSONObject.fromObject(rspHeaderStr),
					HeadersRspQhzx.class);

			if (!"E000000".equals(rspHeader.getRtCode()) && !"E000996".equals(rspHeader.getRtCode())) {
				logger.error("前海好信度返回的信息头错误码：" + rspHeader.getRtCode());
				throw new RuntimeException("前海好信度信息头错误码不是E000000和E000996，而是：" + rspHeader.getRtCode());
			}

			// 报文业务信息
			logger.info("前海好信度响应BusiData密文：" + msgJSON.getString("busiData"));

			String rspBusiData = DataSecurityUtil.decrypt(msgJSON.getString("busiData"),
					ConfigUtils.getProperty("busiDataKey"));
			String recordsStr = JSONObject.fromObject(rspBusiData).get("records").toString();

			JSONArray records = JSONArray.fromObject(recordsStr);

			rsp = (BusiDataItemRspQhMSC8005) JSONObject.toBean((JSONObject) records.get(0),
					BusiDataItemRspQhMSC8005.class);

		} catch (Exception e) {
			logger.error("解析前海好信度反馈报文发生错误", e);

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "解析前海好信度报文发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_RESOLUTION_THIRD_MSG);
			return retMap;
		}

		// 入库
		UserQhzxCredit qhMsc = qhmscMap.get(rsp.getSeqNo());
		if ("E000000".equals(rsp.getErCode())) {
			qhMsc.setSourceId(rsp.getSourceId());
			qhMsc.setCredooScore(rsp.getCredooScore());
			qhMsc.setBseInfoScore(rsp.getBseInfoScore());
			qhMsc.setFinRequireScore(rsp.getFinRequireScore());
			qhMsc.setPayAbilityScore(rsp.getPayAbilityScore());
			qhMsc.setPerformScore(rsp.getPerformScore());
			qhMsc.setActionScore(rsp.getActionScore());
			qhMsc.setVirAssetScore(rsp.getVirAssetScore());
			qhMsc.setTrendScore(rsp.getTrendScore());
			qhMsc.setDataBuildTime(rsp.getDataBuildTime());
			qhMsc.setErCode(rsp.getErCode());
			qhMsc.setUpdateTime(DateUtil.getCurDate());
			retMap.put(ResponseConstants.DATA, JSONObject.fromObject(qhMsc).toString());
		} else {
			qhMsc.setErCode(rsp.getErCode());
			qhMsc.setUpdateTime(DateUtil.getCurDate());
			qhMsc.setErMsg(rsp.getErMsg());
		}

		try {
			userQhzxMsc8005Service.saveQhzxCreditList(qhMsc);
		} catch (Exception e) {
			logger.error("前海好信度插入数据库发生错误", e);
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海好信度插入数据库发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_DATABASE);
			return retMap;
		}

		// E000000 为查到信息 E000996：没有查到信息
		if (!"E000000".equals(rsp.getErCode()) && !"E000996".equals(rsp.getErCode())) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海好信度错误信息返回码非E000000、E000996，而是"+ rsp.getErCode());
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_ERCODE_FAILED);
			return retMap;
		}
		
		// 返回信息
		retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_SUCCESS);
		retMap.put(ResponseConstants.RESULT_DESC, "解析前海好信度报文成功");

		return retMap;
	}

	@Override
	public boolean queryIsQhzxBlackList(UserQhzxBlackList userQhzxMsc8004)  {
		logger.info("调用前海征信黑名单Service");

		if ("E000000".equals(userQhzxMsc8004.getErCode())) {
			logger.info("调用前海征信黑名单接口（8004）,命中黑名单，错误码：" + userQhzxMsc8004.getErCode());
			return true;
		}else if("E000996".equals(userQhzxMsc8004.getErCode())){
			logger.info("调用前海征信黑名单接口（8004）,未命中黑名单，错误码：" + userQhzxMsc8004.getErCode());
			return false;
		}else {
			logger.info("调用前海征信黑名单接口（8004）,错误码：" + userQhzxMsc8004.getErCode() + ",数据异常");
			throw new RuntimeException("调用前海征信黑名单接口,错误码：" + userQhzxMsc8004.getErCode() + ",数据异常");
		}
	}

	@Override
	public boolean queryIsQhzxGoodRel(UserQhzxCredit userQhzxMsc8005, String score_result) throws Exception {
		logger.info("调用前海征信好信度Service");

		boolean flag = false;

		logger.info("调用前海征信好信度接口（8005）,配置好信度分数：" + score_result);

		if (EntityUtils.isNotEmpty(userQhzxMsc8005.getCredooScore())) {
			if (new BigDecimal(userQhzxMsc8005.getCredooScore()).compareTo(new BigDecimal(score_result)) == -1) {
				logger.info("调用前海征信好信度接口（8005）,前海征信好信度得分不通过，得分为:" + userQhzxMsc8005.getCredooScore() + ",前海征信好信度得分低于"
						+ score_result + "分，拒绝征信。");
				flag = true;
			} else {
				logger.info("调用前海征信好信度接口（8005）,前海征信好信度得分通过，得分为:" + userQhzxMsc8005.getCredooScore() + ",前海征信好信度得分"
						+ score_result + "分,征信通过。");
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 发送HTTPs请求,注意这里我们信任了任何服务器证书，并接收返回报文
	 * 
	 * @throws Exception
	 */
	public static JSONObject postHttpsRequest(String sfUrl, String message) throws Exception {

		logger.debug("请求msg密文：" + message);
		logger.info("请求前海url：" + sfUrl);
		String res = HttpRequestUtil.sendJsonWithHttps(sfUrl, message);

		logger.debug("响应Message密文：" + res);
		JSONObject msgJSON = net.sf.json.JSONObject.fromObject(res);

		return msgJSON;
	}

	@Override
	public Map<String, Object> reqQhzxVerify(String idNo, String name, String mobileNum, String id,
			String totalInfoId) {

		logger.info(idNo + "==" + name + "==" + mobileNum + "==" + id + "==" + totalInfoId);
		Map<String, Object> retMap = new HashMap<String, Object>();
		// 校验参数
		if (EntityUtils.isEmpty(idNo)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "idNo is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}
		if (EntityUtils.isEmpty(name)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "name is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}
		if (EntityUtils.isEmpty(mobileNum)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "mobileNum is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}
		if (EntityUtils.isEmpty(totalInfoId)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "totalInfoId is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("idNo", idNo);
		queryMap.put("name", name);
//		queryMap.put("isRealIdentity", new String[]{StatusConstants.QHZX_IDNO_NAME_MATCH_VAL,StatusConstants.QHZX_IDNO_NAME_NOT_MATCH_VAL});

		List<UserQhzxVerify> qhVerifyList = userQhzxVerifyService.queryCountByInfo(queryMap);

		//有且只允许有一条
		if(qhVerifyList!=null&&qhVerifyList.size()>0){
			logger.info("库中已经存在该一致性请求，不再继续请求前海");
			UserQhzxVerify qhVerify=qhVerifyList.get(0);
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_SUCCESS);
			retMap.put(ResponseConstants.RESULT_DESC, "库中已经存在该一致性请求，不再请求前海");
			retMap.put(ResponseConstants.DATA, JSONObject.fromObject(qhVerify).toString());
			return retMap;
		}
		logger.info("库中不存在该一致性请求，继续请求前海");
		
		if (EntityUtils.isEmpty(id)) {
			id = UUIDTools.getFormatUUID();
		}
		// 组成信息
		List<QhCustInfo> custInfoList = new ArrayList<QhCustInfo>();
		QhCustInfo custInfo = new QhCustInfo();
		String tranNo = GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_TRANS_NO_PREFIX,
				ConfigUtils.getProperty("db_id"));
		custInfo.setIdNo(idNo);
		custInfo.setIdType("0");
		custInfo.setName(name);
		custInfo.setSeqNo(GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_SEQ_NO_PREFIX,
				ConfigUtils.getProperty("db_id")));
		custInfo.setEntityAuthCode(UUIDTools.getFormatUUID());
		custInfo.setEntityAuthDate(DateUtil.getCurDateStr("yyyy-MM-dd HH:mm:ss"));
		custInfo.setReasonCode("01");
		custInfo.setMobileNo(mobileNum);
		custInfoList.add(custInfo);
		String batchNo = UUIDTools.getFormatUUID();
		// System.out.println(getQhzxReq8004Msg(custInfoList));

		// 子产品编号
		String subProductInc = "0000000000000001";
		// 存库信息组成
		Map<String, UserQhzxVerify> qhmscMap = new HashMap<String, UserQhzxVerify>();
		QhCustInfo qhCustInfo = custInfoList.get(0);
		UserQhzxVerify qhmscForMap = new UserQhzxVerify();
		qhmscForMap.setId(id);
		qhmscForMap.setIdNo(qhCustInfo.getIdNo());
		qhmscForMap.setName(qhCustInfo.getName());
		qhmscForMap.setMobileNo(qhCustInfo.getMobileNo());
		qhmscForMap.setEntityAuthCode(qhCustInfo.getEntityAuthCode());
		try {
			qhmscForMap
					.setEntityAuthDate(DateUtil.transStrToDate(qhCustInfo.getEntityAuthDate(), "yyyy-MM-dd HH:mm:ss"));
		} catch (java.text.ParseException e1) {
			e1.printStackTrace();
		}
		qhmscForMap.setSeqNo(qhCustInfo.getSeqNo());
		qhmscForMap.setReasonCode(qhCustInfo.getReasonCode());

		qhmscForMap.setCreateTime(DateUtil.getCurDate());
		qhmscForMap.setUpdateTime(DateUtil.getCurDate());
		qhmscForMap.setBatchNo(batchNo);
		qhmscForMap.setTransNo(tranNo);
		qhmscForMap.setTotalInfoId(totalInfoId);
		qhmscForMap.setSubProductInc(subProductInc);
		qhmscMap.put(qhCustInfo.getSeqNo(), qhmscForMap);

		String sfUrl8017 = ConfigUtils.getProperty("sendHttpsUrl8017");
		String message8017 = "";

		try {
			message8017 = QhzxUtil.getQhzxReq8017Msg(custInfoList, batchNo, subProductInc, tranNo);
		} catch (Exception e) {
			logger.error("组装好信一鉴通请求报文发生错误", e);
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "组装好信一鉴通请求报文发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_INTERACTION_THIRD);
			return retMap;
		}

		// 发送信息
		JSONObject msgJSON = null;
		try {
			msgJSON = postHttpsRequest(sfUrl8017, message8017);
		} catch (Exception e) {
			logger.error("请求前海好信一鉴通发生错误", e);
			// 插入告警表
			String alarmContent = "前海好信一鉴通访问异常，请求前海异常姓名：" + qhCustInfo.getName() + ",身份证号：" + qhCustInfo.getIdNo();
			try {
				sysAlarmService.alarm(alarmContent);
			} catch (Exception e1) {
				logger.error("前海好信一鉴通访问异常，请求前海告警异常，姓名：" + qhCustInfo.getName() + ",身份证号：" + qhCustInfo.getIdNo(), e1);
			}
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "请求前海好信一鉴通发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_INTERACTION_THIRD);
			return retMap;
		}

		// 验证签名
		try {
			DataSecurityUtil.verifyData(msgJSON.getString("busiData"),
					msgJSON.getJSONObject("securityInfo").getString("signatureValue"));
			logger.info("验签ok！！！");
		} catch (Exception e) {
			logger.error("验签失败！！！", e);

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海返回报文验签失败");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_SIGN_WRONG);
			return retMap;
		}

		// 解析
		BusiDataItemRspQhMSC8017 rsp = null;
		String erCode = null;
		try {
			// 报文头
			String rspHeaderStr = msgJSON.getString("header");
			HeadersRspQhzx rspHeader = (HeadersRspQhzx) JSONObject.toBean(JSONObject.fromObject(rspHeaderStr),
					HeadersRspQhzx.class);

			if (!"E000000".equals(rspHeader.getRtCode()) && !"E000996".equals(rspHeader.getRtCode())) {
				logger.error("前海好信一鉴通返回的信息头错误码：" + rspHeader.getRtCode());
				throw new RuntimeException("前海好信一鉴通信息头错误码不是E000000和E000996，而是：" + rspHeader.getRtCode());
			}

			// 报文业务信息
			logger.info("前海好信一鉴通响应BusiData密文：" + msgJSON.getString("busiData"));
			
			String rspBusiData = DataSecurityUtil.decrypt(msgJSON.getString("busiData"),
					ConfigUtils.getProperty("busiDataKey"));
			logger.info("前海好信一鉴通响应BusiData明文：" + rspBusiData);
			String recordsStr = JSONObject.fromObject(rspBusiData).get("records").toString();
			logger.info("前海好信一鉴通响应records明文：" + recordsStr);
			JSONArray records = JSONArray.fromObject(recordsStr);
			JSONObject resultJsonObject = JSONObject.fromObject(records.get(0).toString());
			String errorInfoT = resultJsonObject.get("errorInfo").toString();
			errorInfoT = JSONObject.fromObject(errorInfoT).get("0000000000000001").toString();
			String errMsg = JSONObject.fromObject(errorInfoT).get("errMsg").toString();
			erCode = JSONObject.fromObject(errorInfoT).get("erCode").toString();
			logger.info("前海好信一鉴通erCode：" + erCode + ",errMsg" + errMsg);
			resultJsonObject.remove("errorInfo");
			rsp = (BusiDataItemRspQhMSC8017) JSONObject.toBean(resultJsonObject, BusiDataItemRspQhMSC8017.class);
			rsp.setErrorInfo(errMsg);

		} catch (Exception e) {
			logger.error("解析前海好信一鉴通反馈报文发生错误", e);

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "解析前海好信一鉴通报文发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_RESOLUTION_THIRD_MSG);
			return retMap;
		}

		//入库
		UserQhzxVerify userVerify = qhmscMap.get(rsp.getSeqNo());
		userVerify.setUpdateTime(DateUtil.getCurDate());
		userVerify.setSeqNo(rsp.getSeqNo());
		userVerify.setIsRealIdentity(rsp.getIsRealIdentity());
		userVerify.setIsOwnerMobile(rsp.getIsOwnerMobile());
		userVerify.setErrorInfo(rsp.getErrorInfo());
/*		if (EntityUtils.isNotEmpty(rsp.getErrorInfo())) {
			userVerify.setErrorInfo(
					rsp.getErrorInfo().length() > 100 ? rsp.getErrorInfo().substring(0, 100) : rsp.getErrorInfo());
		}*/

		try {
			logger.info("一鉴通数据入库。");
			userQhzxVerifyService.addQhzxVerify(userVerify);
		} catch (Exception e) {
			logger.error("前海好信一鉴通=》idNo:" + idNo + "，name:" + name + "的用户插入库中发生错误", e);
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海好信一鉴通插入数据库发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_DATABASE);
			return retMap;
		}
		
		// E000000 为查到信息 E000996：没有查到信息
		if (!"E000000".equals(erCode) && !"E000996".equals(erCode)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海一鉴通错误信息返回码非E000000、E000996，而是"+ erCode);
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_ERCODE_FAILED);
			return retMap;
		}
		
		// 返回信息
		retMap.put(ResponseConstants.DATA, JSONObject.fromObject(userVerify).toString());
		retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_SUCCESS);
		retMap.put(ResponseConstants.RESULT_DESC, "解析前海好信一鉴通报文成功");
		return retMap;

	}
	
	@Override
	public Map<String, Object> requestQhLoanList(String idNo, String name, String id, String totalInfoId) throws Exception{
		logger.info("请求前海常贷客service开始，参数：idNo-->" + idNo + ",name-->" + name + ",id-->" + id + ",totalInfoId-->"
				+ totalInfoId);

		Map<String, Object> retMap = new HashMap<String, Object>();
		if (EntityUtils.isEmpty(id)) {
			id = UUIDTools.getFormatUUID();
		}

		// 校验参数
		if (EntityUtils.isEmpty(idNo)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "idNo is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}
		if (EntityUtils.isEmpty(name)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "name is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}
		if (EntityUtils.isEmpty(totalInfoId)) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "totalInfoId is null");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_PARAM_NULL);
			return retMap;
		}

		// 组装请求前海对象信息
		QhCustInfo custInfo = new QhCustInfo();
		custInfo.setIdNo(idNo);
		custInfo.setIdType("0");
		custInfo.setName(name);
		custInfo.setSeqNo(GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_SEQ_NO_PREFIX,ConfigUtils.getProperty("db_id")));
		custInfo.setEntityAuthCode(GenerateKey.getId(null, ConfigUtils.getProperty("db_id")));
		custInfo.setEntityAuthDate(DateUtil.getCurDateStr("yyyy-MM-dd HH:mm:ss"));

		List<QhCustInfo> custInfoList = new ArrayList<QhCustInfo>();
		custInfoList.add(custInfo);
		

		String batchNo = UUIDTools.getFormatUUID();
		String tranNo = GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_TRANS_NO_PREFIX,ConfigUtils.getProperty("db_id"));
		
		
		QhCustInfo qhCustInfo = custInfoList.get(0);
		UserQhzxLoan qhmscForMap = new UserQhzxLoan();
		qhmscForMap.setId(id);
		qhmscForMap.setIdNo(qhCustInfo.getIdNo());
		qhmscForMap.setIdType(qhCustInfo.getIdType());
		qhmscForMap.setName(qhCustInfo.getName());
		qhmscForMap.setEntityAuthCode(qhCustInfo.getEntityAuthCode());
		try {
			qhmscForMap
					.setEntityAuthDate(DateUtil.transStrToDate(qhCustInfo.getEntityAuthDate(), "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e2) {
			qhmscForMap.setEntityAuthDate(new Date());
		}
		qhmscForMap.setCreateTime(DateUtil.getCurDate());
		qhmscForMap.setSeqNo(qhCustInfo.getSeqNo());
		qhmscForMap.setBatchNo(batchNo);
		qhmscForMap.setTransNo(tranNo);
		qhmscForMap.setTotalInfoId(totalInfoId);

		Map<String, UserQhzxLoan> qhmscMap = new HashMap<String, UserQhzxLoan>();
		qhmscMap.put(qhCustInfo.getSeqNo(), qhmscForMap);
		
		String message8037 = "";
		try {
			message8037 = QhzxUtil.getQhzxReq8037Msg(custInfoList, batchNo, tranNo);
		} catch (Exception e) {
			logger.error("组装前海常贷客请求报文发生错误", e);
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "组装前海常贷客请求报文发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_MAKE_UP_THIRD_MSG);
			return retMap;
		}

		// 发送信息
		JSONObject msgJSON = null;
		String sfUrl8037 = ConfigUtils.getProperty("sendHttpsUrl8037");

		try {
			msgJSON = postHttpsRequest(sfUrl8037, message8037);
		} catch (Exception e) {
			logger.error("请求前海常贷客发生错误", e);

			// 插入告警表
			String alarmContent = "前海常贷客访问异常，姓名：" + qhCustInfo.getName() + ",身份证号：" + qhCustInfo.getIdNo();
			try {
				sysAlarmService.alarm(alarmContent);
			} catch (Exception e1) {
				logger.error("前海常贷客访问异常，姓名：" + qhCustInfo.getName() + ",身份证号：" + qhCustInfo.getIdNo(), e1);
			}

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "与前海常贷客交互发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_INTERACTION_THIRD);
			return retMap;
		}

		// 验证签名
		try {
			DataSecurityUtil.verifyData(msgJSON.getString("busiData"),
					msgJSON.getJSONObject("securityInfo").getString("signatureValue"));
			logger.info("验签ok！！！");
		} catch (Exception e) {
			logger.error("验签失败！！！");

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海常贷客接口返回报文验签失败");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_SIGN_WRONG);
			return retMap;
		}

		// 解析
		BusiDataItemRspQhMSC8037 rsp = null;
		try {

			// 报文头
			String rspHeaderStr = msgJSON.getString("header");
			HeadersRspQhzx rspHeader = (HeadersRspQhzx) JSONObject.toBean(JSONObject.fromObject(rspHeaderStr),
					HeadersRspQhzx.class);

			if (!"E000000".equals(rspHeader.getRtCode()) && !"E000996".equals(rspHeader.getRtCode())) {
				logger.error("前海常贷客接口返回的信息头错误码：" + rspHeader.getRtCode());
				throw new RuntimeException("前海常贷客接口信息头错误码不是E000000和E000996，而是：" + rspHeader.getRtCode());
			}

			// 报文业务信息
			logger.info("响应BusiData密文：" + msgJSON.getString("busiData"));

			String rspBusiData = DataSecurityUtil.decrypt(msgJSON.getString("busiData"),
					ConfigUtils.getProperty("busiDataKey"));
			logger.info("前海常贷客接口响应BusiData明文：" + rspBusiData);

			String recordsStr = JSONObject.fromObject(rspBusiData).get("records").toString();

			JSONArray records = JSONArray.fromObject(recordsStr);
			rsp = (BusiDataItemRspQhMSC8037) JSONObject.toBean((JSONObject) records.get(0),
					BusiDataItemRspQhMSC8037.class);

		} catch (Exception e) {
			logger.error("解析前海常贷客反馈报文发生错误", e);

			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "解析前海常贷客报文发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_RESOLUTION_THIRD_MSG);
			return retMap;
		}

		// 入库 查询到返回对象转化的json串，没查到返回null
		UserQhzxLoan qhmsc = qhmscMap.get(rsp.getSeqNo());
		if ("E000000".equals(rsp.getErCode())) {
			qhmsc.setReasonCode(rsp.getReasonCode());
			qhmsc.setIndustry(rsp.getIndustry());
			qhmsc.setAmount(rsp.getAmount());
			qhmsc.setBusidate(rsp.getBusiDate());
			qhmsc.setErcode(rsp.getErCode());
			qhmsc.setErmsg(rsp.getErMsg());
			qhmsc.setUpdateTime(DateUtil.getCurDate());
			retMap.put(ResponseConstants.DATA, JSONObject.fromObject(qhmsc).toString());
		} else {
			qhmsc.setErcode(rsp.getErCode());
			qhmsc.setUpdateTime(DateUtil.getCurDate());
			qhmsc.setErmsg(rsp.getErMsg());
			retMap.put(ResponseConstants.DATA, null);
		}

		try {
			userQhzxLoanService.saveQhzxLoaneeList(qhmsc);
		} catch (Exception e) {
			logger.error("前海常贷客插入数据库时发生错误", e);
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海常贷客插入数据库时发生错误");
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_DATABASE);
			return retMap;
		}

		if (!"E000000".equals(rsp.getErCode()) && !"E000996".equals(rsp.getErCode())) {
			retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_FAIL);
			retMap.put(ResponseConstants.RESULT_DESC, "前海常贷客错误信息返回码非E000000、E000996，而是"+ rsp.getErCode());
			retMap.put(ResponseConstants.ERROR_CODE, ResponseConstants.MQ_EX_ERCODE_FAILED);
			return retMap;
		}
		
		// 返回信息
		retMap.put(ResponseConstants.RESULT_CODE, ResponseConstants.QHZX_RESULT_CODE_SUCCESS);
		retMap.put(ResponseConstants.RESULT_DESC, "解析前海常贷客报文成功");

		return retMap;
	}
	
	public static void main(String[] args) {
		String aa = "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";
		try {
			String rspBusiData = DataSecurityUtil.decrypt(aa,ConfigUtils.getProperty("busiDataKey"));
			System.out.println(rspBusiData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
