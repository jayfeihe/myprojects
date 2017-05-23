package com.hikootech.mqcash.qhzx;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.UUIDTools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 此类描述的是：前海征信工具类
 * 
 * @author: zhaohefei
 * @version: 2015年11月5日 下午3:54:16
 */

public class QhzxUtil {
	private static Logger logger = LoggerFactory.getLogger(QhzxUtil.class);

	/*
	 * orgCode: 10000000 机构代码 chnlId: qhcs-dcs 接入系统ID authCode: CRT001A2 授权码
	 * userName: V_PA025_QHCS_DCS 用户名 userPassword: weblogic1 用户密码 校验码
	 * SK803@!QLF-D25WEDA5E52DA 用于数据加密密钥（测试和生产环境不一致） 服务器地址:
	 * https://test-qhzx.pingan.com.cn:5443/do/dmz/query/blacklist/v1/MSC8004
	 * 具体交易完整请求URL详见接口文档说明
	 */

	/** 构造请求数据头 */
	public static String getHeader_Req_DMZ(String transNo) {
		HeadersReqQhzx header = new HeadersReqQhzx();
		header.setOrgCode(ConfigUtils.getProperty("orgCode"));
		header.setChnlId(ConfigUtils.getProperty("chnlId"));
		header.setAuthCode(ConfigUtils.getProperty("authCode"));
		header.setAuthDate("2015-12-02 14:12:14"); // 授权时间
		header.setTransDate(DateUtil.getCurDateStr("yyyy-MM-dd HH:mm:ss"));
		header.setTransNo(transNo);
		StringBuffer sb = new StringBuffer("\"header\":");
		sb.append(JSONObject.fromObject(header).toString());
		return sb.toString();
	}

	/**
	 * getReqSecurityInfo 此方法描述的是：构造请求数据签名
	 * 
	 * @param encBusiData
	 *            加密以后的busiData部分的json字符串
	 * @author: zhaohefei
	 * @version: 2015年11月5日 下午4:49:40
	 */

	public static String getReqSecurityInfo(String encBusiData) {

		String securityInfo = "";
		try {
			String sigValue = DataSecurityUtil.signData(encBusiData);
			String pwd = DataSecurityUtil.digest(ConfigUtils.getProperty("userPassword").getBytes());
			SecurityInfoReqQhzx securityInfoReq = new SecurityInfoReqQhzx();
			securityInfoReq.setSignatureValue(sigValue);
			securityInfoReq.setUserName(ConfigUtils.getProperty("userName"));
			securityInfoReq.setUserPassword(pwd);
			securityInfo = "\"securityInfo\":" + JSONObject.fromObject(securityInfoReq).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return securityInfo;
	}

	/**
	 * getReqBusiData8004 此方法描述的是：构造请求8004主体数据
	 * 
	 * @author: zhaohefei
	 * @version: 2015年11月5日 下午5:08:11
	 */
	public static String getReqBusiData8004(List<QhCustInfo> custList, String batchNo) {
		if (custList == null || custList.size() == 0) {
			logger.error("MSC8004传入查询客户信息为空，不作提交");
			throw new RuntimeException("MSC8004传入查询客户信息为空");
		}

		BusiDataQhzx bu = new BusiDataQhzx();
		List<BusiDataItemReqQhMSC8004> list = new ArrayList<BusiDataItemReqQhMSC8004>();
		bu.setBatchNo(batchNo);
		for (QhCustInfo custInfo : custList) {
			if (custInfo != null) {
				// 必填项不得为空
				if (EntityUtils.isEmpty(custInfo.getIdNo())) {
					throw new RuntimeException("身份证为空");
				}
				if (EntityUtils.isEmpty(custInfo.getIdType())) {
					throw new RuntimeException("身份证类型为空");
				}
				if (EntityUtils.isEmpty(custInfo.getName())) {
					throw new RuntimeException("姓名为空");
				}
				if (EntityUtils.isEmpty(custInfo.getReasonCode())) {
					throw new RuntimeException("查询原因为空");
				}
				if (EntityUtils.isEmpty(custInfo.getEntityAuthCode())) {
					throw new RuntimeException("授权码为空");
				}
				if (EntityUtils.isEmpty(custInfo.getEntityAuthDate())) {
					throw new RuntimeException("授权时间为空");
				}
				if (EntityUtils.isEmpty(custInfo.getSeqNo())) {
					throw new RuntimeException("子批次号为空");
				}

				BusiDataItemReqQhMSC8004 bui = new BusiDataItemReqQhMSC8004();
				bui.setIdNo(custInfo.getIdNo().toUpperCase());
				bui.setIdType(custInfo.getIdType());
				bui.setName(custInfo.getName());
				bui.setReasonCode(custInfo.getReasonCode());
				bui.setEntityAuthCode(custInfo.getEntityAuthCode());
				bui.setEntityAuthDate(custInfo.getEntityAuthDate());
				bui.setSeqNo(custInfo.getSeqNo());
				list.add(bui);

			}
		}
		if (list == null || list.size() == 0) {
			logger.error("8004请求msg主体list为空。。");
		}
		bu.setRecords(list);
		logger.info("8004请求msg明文主体==>" + JSONObject.fromObject(bu).toString());
		String encBusiData = null;
		try {
			encBusiData = DataSecurityUtil.encrypt(JSONObject.fromObject(bu).toString().getBytes("utf-8"),
					ConfigUtils.getProperty("busiDataKey"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return encBusiData;
	}

	/**
	 * getReqBusiData8005 此方法描述的是：构造请求8005主体数据
	 * 
	 * @author: zhaohefei
	 * @version: 2015年11月5日 下午5:08:11
	 */

	public static String getReqBusiData8005(List<QhCustInfo> custList, String batchNo) {
		if (custList == null || custList.size() == 0) {
			logger.error("MSC8005传入查询客户信息为空");
			throw new RuntimeException("MSC8005传入查询客户信息为空");
		}

		BusiDataQhzx bu = new BusiDataQhzx();
		List<BusiDataItemReqQhMSC8005> list = new ArrayList<BusiDataItemReqQhMSC8005>();
		bu.setBatchNo(batchNo);
		for (QhCustInfo custInfo : custList) {
			if (custInfo != null) {
				// 必填项不得为空
				if (EntityUtils.isEmpty(custInfo.getIdNo())) {
					throw new RuntimeException("身份证为空");
				}
				if (EntityUtils.isEmpty(custInfo.getIdType())) {
					throw new RuntimeException("身份证 类型为空");
				}
				if (EntityUtils.isEmpty(custInfo.getName())) {
					throw new RuntimeException("姓名为空");
				}
				if (EntityUtils.isEmpty(custInfo.getMobileNo())) {
					throw new RuntimeException("手机号为空");
				}
				if (EntityUtils.isEmpty(custInfo.getCardNo())) {
					throw new RuntimeException("银行卡为空");
				}
				if (EntityUtils.isEmpty(custInfo.getReasonCode())) {
					throw new RuntimeException("查询原因为空");
				}
				if (EntityUtils.isEmpty(custInfo.getEntityAuthCode())) {
					throw new RuntimeException("授权码为空");
				}
				if (EntityUtils.isEmpty(custInfo.getEntityAuthDate())) {
					throw new RuntimeException("授权日期为空");
				}
				if (EntityUtils.isEmpty(custInfo.getSeqNo())) {
					throw new RuntimeException("子批次序列号为空");
				}

				BusiDataItemReqQhMSC8005 bui = new BusiDataItemReqQhMSC8005();
				bui.setIdNo(custInfo.getIdNo().toUpperCase());
				bui.setIdType(custInfo.getIdType());
				bui.setName(custInfo.getName());
				bui.setMobileNo(custInfo.getMobileNo());
				bui.setCardNo(custInfo.getCardNo());
				bui.setReasonNo(custInfo.getReasonCode());
				if (EntityUtils.isNotEmpty(custInfo.getEmail())) {
					bui.setEmail(custInfo.getEmail());
				}
				if (EntityUtils.isNotEmpty(custInfo.getWeiboNo())) {
					bui.setWeiboNo(custInfo.getWeiboNo());
				}
				if (EntityUtils.isNotEmpty(custInfo.getQqNo())) {
					bui.setQqNo(custInfo.getQqNo());
				}
				if (EntityUtils.isNotEmpty(custInfo.getTaobaoNo())) {
					bui.setTaobaoNo(custInfo.getTaobaoNo());
				}
				if (EntityUtils.isNotEmpty(custInfo.getJdNo())) {
					bui.setJdNo(custInfo.getJdNo());
				}
				if (EntityUtils.isNotEmpty(custInfo.getAmazonNo())) {
					bui.setAmazonNo(custInfo.getAmazonNo());
				}
				if (EntityUtils.isNotEmpty(custInfo.getYhdNo())) {
					bui.setYhdNo(custInfo.getYhdNo());
				}
				bui.setEntityAuthCode(custInfo.getEntityAuthCode());
				bui.setEntityAuthDate(custInfo.getEntityAuthDate());
				bui.setSeqNo(custInfo.getSeqNo());
				list.add(bui);
			}
		}

		bu.setRecords(list);
		if (list == null || list.size() == 0) {
			logger.error("8005请求msg主体list为空。。");
		}
		logger.info("8005请求msg明文主体==>" + JSONObject.fromObject(bu).toString());
		String encBusiData = null;
		try {
			encBusiData = DataSecurityUtil.encrypt(JSONObject.fromObject(bu).toString().getBytes("utf-8"),
					ConfigUtils.getProperty("busiDataKey"));
		} catch (Exception e) {
			logger.error("3des加密发生错误：", e);
		}

		return encBusiData;
	}

	/**
	 * getReqBusiData8017 此方法描述的是：构造请求8017主体数据
	 * 
	 * @author: zhaohefei
	 * @version: 2015年11月5日 下午5:08:11
	 */

	public static String getReqBusiData8017(List<QhCustInfo> custList, String batchNo,String subProductInc) {
		if (custList == null || custList.size() == 0) {
			logger.error("MSC8017传入查询客户信息为空");
			throw new RuntimeException("MSC8017传入查询客户信息为空");
		}

		BusiDataQhzx bu = new BusiDataQhzx();
		List<BusiDataItemReqQhMSC8017> list = new ArrayList<BusiDataItemReqQhMSC8017>();
		bu.setBatchNo(batchNo);
		bu.setSubProductInc(subProductInc);
		for (QhCustInfo custInfo : custList) {
			if (custInfo != null) {
				// 必填项不得为空
				if (EntityUtils.isEmpty(custInfo.getIdNo())) {
					throw new RuntimeException("身份证为空");
				}
				if (EntityUtils.isEmpty(custInfo.getIdType())) {
					throw new RuntimeException("身份证 类型为空");
				}
				if (EntityUtils.isEmpty(custInfo.getName())) {
					throw new RuntimeException("姓名为空");
				}
				if (EntityUtils.isEmpty(custInfo.getMobileNo())) {
					throw new RuntimeException("手机号为空");
				}
				 
				if (EntityUtils.isEmpty(custInfo.getReasonCode())) {
					throw new RuntimeException("查询原因为空");
				}
				if (EntityUtils.isEmpty(custInfo.getEntityAuthCode())) {
					throw new RuntimeException("授权码为空");
				}
				if (EntityUtils.isEmpty(custInfo.getEntityAuthDate())) {
					throw new RuntimeException("授权日期为空");
				}
				if (EntityUtils.isEmpty(custInfo.getSeqNo())) {
					throw new RuntimeException("子批次序列号为空");
				}

				BusiDataItemReqQhMSC8017 bui = new BusiDataItemReqQhMSC8017();
				bui.setIdNo(custInfo.getIdNo().toUpperCase());
				bui.setIdType(custInfo.getIdType());
				bui.setName(custInfo.getName());
				bui.setMobileNo(custInfo.getMobileNo());
				bui.setReasonCode(custInfo.getReasonCode());
				bui.setEntityAuthCode(custInfo.getEntityAuthCode());
				bui.setEntityAuthDate(custInfo.getEntityAuthDate());
				bui.setSeqNo(custInfo.getSeqNo());
				list.add(bui);
			}
		}

		bu.setRecords(list);
		if (list == null || list.size() == 0) {
			logger.error("8017请求msg主体list为空。。");
		}
		logger.info("8017请求msg明文主体==>" + JSONObject.fromObject(bu).toString());
		String encBusiData = null;
		try {
			encBusiData = DataSecurityUtil.encrypt(JSONObject.fromObject(bu).toString().getBytes("utf-8"),
					ConfigUtils.getProperty("busiDataKey"));
		} catch (Exception e) {
			logger.error("3des加密发生错误：", e);
		}

		return encBusiData;
	}

	/**
	 * getReqBusiData8037 此方法描述的是：构造请求8037主体数据
	 * 
	 * @author: zhanghaida
	 * @version: 2015年11月5日 下午5:08:11
	 */
	public static String getReqBusiData8037(List<QhCustInfo> custList, String batchNo) {
		if (custList == null || custList.size() == 0) {
			logger.error("MSC8037传入查询客户信息为空，不作提交");
			throw new RuntimeException("MSC8037传入查询客户信息为空");
		}

		BusiDataQhzx bu = new BusiDataQhzx();
		List<BusiDataItemReqQhMSC8037> list = new ArrayList<BusiDataItemReqQhMSC8037>();
		bu.setBatchNo(batchNo);
		for (QhCustInfo custInfo : custList) {
			if (custInfo != null) {
				// 必填项不得为空
				if (EntityUtils.isEmpty(custInfo.getIdNo())) {
					throw new RuntimeException("身份证为空");
				}
				if (EntityUtils.isEmpty(custInfo.getIdType())) {
					throw new RuntimeException("身份证类型为空");
				}
				if (EntityUtils.isEmpty(custInfo.getName())) {
					throw new RuntimeException("姓名为空");
				}
				if (EntityUtils.isEmpty(custInfo.getEntityAuthCode())) {
					throw new RuntimeException("授权码为空");
				}
				if (EntityUtils.isEmpty(custInfo.getEntityAuthDate())) {
					throw new RuntimeException("授权时间为空");
				}
				if (EntityUtils.isEmpty(custInfo.getSeqNo())) {
					throw new RuntimeException("子批次号为空");
				}

				BusiDataItemReqQhMSC8037 bui = new BusiDataItemReqQhMSC8037();
				bui.setIdNo(custInfo.getIdNo().toUpperCase());
				bui.setIdType(custInfo.getIdType());
				bui.setName(custInfo.getName());
				bui.setEntityAuthCode(custInfo.getEntityAuthCode());
				bui.setEntityAuthDate(custInfo.getEntityAuthDate());
				bui.setSeqNo(custInfo.getSeqNo());
				list.add(bui);

			}
		}
		if (list == null || list.size() == 0) {
			logger.error("8037请求msg主体list为空。。");
		}
		bu.setRecords(list);
		logger.info("8037请求msg明文主体==>" + JSONObject.fromObject(bu).toString());
		String encBusiData = null;
		try {
			encBusiData = DataSecurityUtil.encrypt(JSONObject.fromObject(bu).toString().getBytes("utf-8"),
					ConfigUtils.getProperty("busiDataKey"));
		} catch (Exception e) {
			logger.error("8037请求msg主体3DES数据加密异常",e);
		}

		return encBusiData;
	}
	
	/**
	 * getQhzxReq8004Msg 此方法描述的是：构造MSC8004所需用的报文
	 * 
	 * @author: zhaohefei
	 * @version: 2015年11月9日 下午4:47:08
	 */

	public static String getQhzxReq8004Msg(List<QhCustInfo> custList, String batchNo, String transNo) {
		String header = getHeader_Req_DMZ(transNo);
		String encBusiData = getReqBusiData8004(custList, batchNo);
		String busiData = "\"busiData\":\"" + encBusiData + "\"";
		String securityInfo = getReqSecurityInfo(encBusiData);
		String message = "{" + header + "," + busiData + "," + securityInfo + "}";
		return message;
	}

	/**
	 * getQhzxReq8005Msg 此方法描述的是：构造MSC8005所需用的报文
	 * 
	 * @author: zhaohefei
	 * @version: 2015年11月9日 下午4:47:08
	 */

	public static String getQhzxReq8005Msg(List<QhCustInfo> custList, String batchNo, String transNo) {
		String header = getHeader_Req_DMZ(transNo);
		String encBusiData = getReqBusiData8005(custList, batchNo);
		if (EntityUtils.isEmpty(encBusiData)) {
			return "";
		}
		String busiData = "\"busiData\":\"" + encBusiData + "\"";
		String securityInfo = getReqSecurityInfo(encBusiData);
		String message = "{" + header + "," + busiData + "," + securityInfo + "}";
		return message;
	}

	 

	/** 
	* getQhzxReq8017Msg<br/> 
	*  构造MSC8017所需用的报文 
	* @param custList 
	* @param batchNo 批次号
	* @param subProductNo 子产品编号
	* @param transNo
	* @return String
	* @author zhaohefei
	* @2016年2月23日
	* @return String	返回类型 
	*/
	public static String getQhzxReq8017Msg(List<QhCustInfo> custList, String batchNo,String subProductInc ,String transNo) {
		String header = getHeader_Req_DMZ(transNo);
		String encBusiData = getReqBusiData8017(custList, batchNo,subProductInc);
		if (EntityUtils.isEmpty(encBusiData)) {
			return "";
		}
		String busiData = "\"busiData\":\"" + encBusiData + "\"";
		String securityInfo = getReqSecurityInfo(encBusiData);
		String message = "{" + header + "," + busiData + "," + securityInfo + "}";
		return message;
	}

	/**
	 * getQhzxReq8037Msg 此方法描述的是：构造MSC8037所需用的报文
	 * 
	 * @author: zhanghaida
	 * @version: 2015年11月9日 下午4:47:08
	 */

	public static String getQhzxReq8037Msg(List<QhCustInfo> custList, String batchNo, String transNo) {
		String header = getHeader_Req_DMZ(transNo);
		String encBusiData = getReqBusiData8037(custList, batchNo);
		String busiData = "\"busiData\":\"" + encBusiData + "\"";
		String securityInfo = getReqSecurityInfo(encBusiData);
		String message = "{" + header + "," + busiData + "," + securityInfo + "}";
		return message;
	}
	
	/**
	 * 发送HTTPs请求,注意这里我们信任了任何服务器证书，并接收返回报文
	 * 
	 * @throws Exception
	 */
	public static JSONObject postHttpsRequest(String sfUrl, String message) throws Exception {

		logger.debug("请求msg密文：" + message);

		String res = HttpRequestUtil.sendJsonWithHttps(sfUrl, message);

		logger.debug("响应Message密文：" + res);
		JSONObject msgJSON = net.sf.json.JSONObject.fromObject(res);
		logger.info("响应BusiData密文：" + msgJSON.getString("busiData"));
		String rspBusiData = DataSecurityUtil.decrypt(msgJSON.getString("busiData"),
				ConfigUtils.getProperty("busiDataKey"));
		logger.info("响应BusiData明文：" + rspBusiData);
		try {
			// 一定要验证签名的合法性！！！！！！！！
			DataSecurityUtil.verifyData(msgJSON.getString("busiData"),
					msgJSON.getJSONObject("securityInfo").getString("signatureValue"));
			logger.info("验签ok！！！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("验签失败！！！");
		}
		// 验证签名通过后，返回返回信息
		return msgJSON;
	}

	public static void main(String[] args) {
		// String busiData = "\"busiData\":\"" + encBusiData + "\"";
		try {
			test8005();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void test8005() throws Exception {
		List<QhCustInfo> custInfoList = new ArrayList<QhCustInfo>();
		// QhCustInfo custInfo=new QhCustInfo();
		// custInfo.setIdNo("210603198701315011");
		// custInfo.setIdType("0");
		// custInfo.setName("刘智博");
		// custInfo.setSeqNo(GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_SEQ_NO_PREFIX,
		// ConfigUtils.getProperty("db_id")));
		// custInfo.setEntityAuthCode(UUIDTools.getFormatUUID());
		// custInfo.setEntityAuthDate(DateUtil.getCurDateStr("yyyy-MM-dd
		// HH:mm:ss"));
		// custInfo.setReasonCode("01");
		// custInfo.setMobileNo("18600440006");
		// custInfo.setCardNo("****************");
		// custInfoList.add(custInfo);
		// custInfo.setCardNo(userStr[5]);
		// for (int i = 0; i < listUser.size(); i++) {
		// String[] userStr=listUser.get(i);
		// QhCustInfo custInfo=new QhCustInfo();
		// custInfo.setIdNo(userStr[1]);
		// custInfo.setIdType("0");
		// custInfo.setName(userStr[0]);
		// custInfo.setSeqNo(GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_SEQ_NO_PREFIX,
		// ConfigUtils.getProperty("db_id")));
		// custInfo.setEntityAuthCode(UUIDTools.getFormatUUID());
		// custInfo.setEntityAuthDate(DateUtil.getCurDateStr("yyyy-MM-dd
		// HH:mm:ss"));
		// custInfo.setReasonCode("01");
		// //custInfo.setCardNo(userStr[5]);
		// custInfo.setMobileNo(userStr[2]);
		// custInfoList.add(custInfo);
		// }
		//

		QhCustInfo custInfo2 = new QhCustInfo();
		custInfo2.setIdNo("210603198701315011");
		custInfo2.setIdType("0");
		custInfo2.setName("刘智博");
		custInfo2.setSeqNo(GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_SEQ_NO_PREFIX,
				ConfigUtils.getProperty("db_id")));
		custInfo2.setEntityAuthCode(UUIDTools.getFormatUUID());
		custInfo2.setEntityAuthDate(DateUtil.getCurDateStr("yyyy-MM-dd HH:mm:ss"));
		custInfo2.setReasonCode("01");
		custInfo2.setCardNo("6225758200418555");
		custInfo2.setMobileNo("18600440006");
		custInfoList.add(custInfo2);
		// System.out.println(getQhzxReq8004Msg(custInfoList));
		String sfUrl8005 = ConfigUtils.getProperty("sendHttpsUrl8005");
		String message8005 = QhzxUtil.getQhzxReq8005Msg(custInfoList, UUIDTools.getFormatUUID(),
				UUIDTools.getFormatUUID());
		// JSONObject msgJSON=postHttpsRequest(sfUrl8004,message8004);
		JSONObject msgJSON = postHttpsRequest(sfUrl8005, message8005);
		String rspHeaderStr = msgJSON.getString("header");
		HeadersRspQhzx rspHeader = (HeadersRspQhzx) JSONObject.toBean(JSONObject.fromObject(rspHeaderStr),
				HeadersRspQhzx.class);
		System.out.println("header.rtMsg==>" + rspHeader.getRtMsg());

		String rspBusiData = DataSecurityUtil.decrypt(msgJSON.getString("busiData"),
				ConfigUtils.getProperty("busiDataKey"));
		System.out.println("响应BusiData明文：" + rspBusiData);

		String recordsStr = JSONObject.fromObject(rspBusiData).get("records").toString();
		System.out.println(recordsStr);

	}

	public static void test8004() throws Exception {
		List<QhCustInfo> custInfoList = new ArrayList<QhCustInfo>();
		QhCustInfo custInfo = new QhCustInfo();
		custInfo.setIdNo("432425197209130029");
		custInfo.setIdType("0");
		custInfo.setName("江帆");
		custInfo.setSeqNo(GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_SEQ_NO_PREFIX,
				ConfigUtils.getProperty("db_id")));
		custInfo.setEntityAuthCode(UUIDTools.getFormatUUID());
		custInfo.setEntityAuthDate(DateUtil.getCurDateStr("yyyy-MM-dd HH:mm:ss"));
		custInfo.setReasonCode("01");
		custInfo.setCardNo("****************");
		custInfo.setMobileNo("13549205118");
		custInfoList.add(custInfo);
		// System.out.println(getQhzxReq8004Msg(custInfoList));

		String sfUrl8004 = ConfigUtils.getProperty("sendHttpsUrl8004");
		String message8004 = QhzxUtil.getQhzxReq8004Msg(custInfoList, UUIDTools.getFormatUUID(),
				UUIDTools.getFormatUUID());
		// JSONObject msgJSON=postHttpsRequest(sfUrl8004,message8004);
		JSONObject msgJSON = postHttpsRequest(sfUrl8004, message8004);
		String rspHeaderStr = msgJSON.getString("header");
		HeadersRspQhzx rspHeader = (HeadersRspQhzx) JSONObject.toBean(JSONObject.fromObject(rspHeaderStr),
				HeadersRspQhzx.class);
		System.out.println("header.rtMsg==>" + rspHeader.getRtMsg());

		String rspBusiData = DataSecurityUtil.decrypt(msgJSON.getString("busiData"),
				ConfigUtils.getProperty("busiDataKey"));
		System.out.println("响应BusiData明文：" + rspBusiData);

		String recordsStr = JSONObject.fromObject(rspBusiData).get("records").toString();
		System.out.println(recordsStr);

		JSONArray records = JSONArray.fromObject(recordsStr);
		System.out.println(records.size());

		BusiDataItemRspQhMSC8004 rsp = (BusiDataItemRspQhMSC8004) JSONObject.toBean((JSONObject) records.get(0),
				BusiDataItemRspQhMSC8004.class);
		System.out.println(rsp.getIdNo());

	}

}
