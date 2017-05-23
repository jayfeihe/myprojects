package com.hikootech.mqcash.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.WxPayConstants;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.service.UserPaymentOrderService;
import com.hikootech.mqcash.service.WxNativePayService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;

/**
 * 微信扫一扫支付Service
 * 
 * @author QYANZE
 *
 */
@Service("wxNativePayService")
public class WxNativePayServiceImpl implements WxNativePayService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserPaymentOrderService userPaymentOrderService;
	
	private final String appid = ConfigUtils.getProperty("wx_appid");
	private final String mch_id = ConfigUtils.getProperty("wx_mch_id");
	private final String md5_key = ConfigUtils.getProperty("wx_md5_key");
	private final String charset = ConfigUtils.getProperty("wx_charset");
	private final String unified_order_url = ConfigUtils.getProperty("wx_unified_order_url");
	private final String wx_notify_url = ConfigUtils.getProperty("wx_notify_url");
	private final String wx_short_url = ConfigUtils.getProperty("wx_short_url");
	private final String query_order_url = ConfigUtils.getProperty("wx_query_order_url");

	@Override
	public String getQRCodeUrlModel1(String product_id) throws Exception {
		String long_url = "weixin://wxpay/bizpayurl?sign=SIGN&appid=APPID&mch_id=MCH_ID&product_id=PRODUCT_ID&time_stamp=TIME_STAMP&nonce_str=NONCE_STR";
		String time_stamp = System.currentTimeMillis() / 1000 + "";
		String nonce_str = this.getNonceStr(30);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("appid", appid);
		paramMap.put("mch_id", mch_id);
		paramMap.put("time_stamp", time_stamp);
		paramMap.put("nonce_str", nonce_str);
		paramMap.put("product_id", product_id);

		// 根据参数生成签名
		String sign = this.getSign(paramMap);
		long_url = long_url.replace("SIGN", sign)
							.replace("APPID", appid)
							.replace("MCH_ID", mch_id)
							.replace("PRODUCT_ID", product_id)
							.replace("TIME_STAMP", time_stamp)
							.replace("NONCE_STR", nonce_str);
		logger.info("获取二维码url：" + long_url);
		
		// 转换短链接
		String shortUrl = this.shortUrlReq(long_url);
		logger.info("转换短链接：" + shortUrl);
		return shortUrl;
	}

	@Override
	public String getQRCodeUrlModel2(Map<String, String> busMap) throws Exception {
		Map<String, String> resultMap = this.unifiedOrderReq(busMap);

		// 获取状态返回码
		String return_code = resultMap.get("return_code");
		// 状态码为失败
		if (WxPayConstants.RETURN_CODE_FAIL.equals(return_code)) {
			return "";
		}
		// 进行签名认证
		String respSign = resultMap.get("sign");
		resultMap.remove("sign");
		String _respSign = this.getSign(resultMap);

		// 签名一致
		if (respSign.equals(_respSign)) {
			String result_code = resultMap.get("result_code");
			if (WxPayConstants.RESULT_CODE_SUCCESS.equals(result_code)) {
				return resultMap.get("code_url");
			} else {
				return "";
			}
		} else {
			logger.info("签名不一致：【返回签名：" + respSign + ",根据返回信息生成签名：" + _respSign);
			return "";
		}
	}

	@Override
	public Map<String, String> queryOrderInfo(Map<String, String> paramMap) throws Exception {
		Map<String, String> resultMap = this.orderQueryReq(paramMap);
		// 获取状态返回码
		String return_code = resultMap.get("return_code");
		// 状态码为失败
		if (WxPayConstants.RETURN_CODE_FAIL.equals(return_code)) {
			resultMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			resultMap.put(ResponseConstants.RETURN_DESC, resultMap.get("err_code_des"));
			return resultMap;
		}

		// 进行签名认证
		String respSign = resultMap.get("sign");
		resultMap.remove("sign");
		String _respSign = this.getSign(resultMap);

		// 签名一致
		if (respSign.equals(_respSign)) {
			String result_code = resultMap.get("result_code");
			if (WxPayConstants.RESULT_CODE_FAIL.equals(result_code)) {
				resultMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				resultMap.put(ResponseConstants.RETURN_DESC, resultMap.get("err_code_des"));
				return resultMap;
			}

			if (WxPayConstants.RESULT_CODE_SUCCESS.equals(result_code)) {
				// 格式化订单信息返回
				resultMap.remove("return_code");
				resultMap.remove("return_msg");
				resultMap.remove("appid");
				resultMap.remove("mch_id");
				resultMap.remove("nonce_str");
				resultMap.remove("sign");
				resultMap.remove("result_code");
				resultMap.remove("err_code");
				resultMap.remove("err_code_des");
				
				resultMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
				return resultMap;
			}
		}

		return resultMap;
	}

	/**
	 * 请求微信统一下单接口
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> unifiedOrderReq(Map<String, String> reqMap) throws Exception {

		reqMap.put("appid", appid);
		reqMap.put("mch_id", mch_id);
		reqMap.put("nonce_str", this.getNonceStr(30));
		reqMap.put("trade_type", WxPayConstants.TRADE_TYPE_NATIVE);
		reqMap.put("spbill_create_ip", this.getLocalIp());
		reqMap.put("limit_pay", "no_credit");
		reqMap.put("notify_url", wx_notify_url);

		// 根据参数获取签名
		String reqSign = this.getSign(reqMap);
		// 加入签名参数
		reqMap.put("sign", reqSign);

		// xml格式化参数
		String reqXml = this.map2Xml(reqMap);
		logger.info("微信统一下单请求报文：" + reqXml);

		// 接口请求
		String respXml = this.reqWx(unified_order_url, reqXml, charset);
		logger.info("微信统一下单返回报文：" + respXml);

		Map<String, String> respMap = this.xml2Map(respXml);

		return respMap;
	}

	@Override
	public String notifyProcess(String receiveXml) throws Exception {
		Map<String, String> receiveMap = this.xml2Map(receiveXml);
		String return_code = receiveMap.get("return_code");
		Map<String, String> resultMap = new HashMap<String, String>();
		String resultXml = null;
		if (WxPayConstants.RETURN_CODE_SUCCESS.equals(return_code)) {
			String receiveSign = receiveMap.get("sign");
			receiveMap.remove("sign");
			String sign = this.getSign(receiveMap);
			if (sign.equals(receiveSign)) {
				// 成功业务数据操作
				this.busProcess(receiveMap, true);
				resultMap.put("return_code", WxPayConstants.RETURN_CODE_SUCCESS);
				resultMap.put("return_msg", "微信异步通知处理成功");
			} else {
				logger.info("微信支付异步通知签名不一致：【返回签名=" + receiveSign + ",根据返回参数生成签名=" + sign);
				// 失败业务数据操作
				this.busProcess(receiveMap, false);
				resultMap.put("return_code", WxPayConstants.RETURN_CODE_FAIL);
				resultMap.put("return_msg", "微信异步通知签名不一致");
			}
		} else {
			// 失败业务数据操作
			this.busProcess(receiveMap, false);
			resultMap.put("return_code", WxPayConstants.RETURN_CODE_FAIL);
			resultMap.put("return_msg", "微信异步通知return_code为FAIL");
		}
		
		resultXml = this.map2Xml(resultMap);
		return resultXml;
	}

	/**
	 * 根据微信异步支付结果成功进行业务处理
	 * 
	 * @param receiveMap
	 */
	private void busProcess(Map<String, String> receiveMap, boolean isSuccess) {
		
		String out_trade_no = receiveMap.get("out_trade_no");
		String time_end = receiveMap.get("time_end");
		String err_code_des = receiveMap.get("err_code_des");
		String bank_type = receiveMap.get("bank_type");
		
		// 业务操作
		Date bankTxTime = new Date();
		try {
			bankTxTime = DateUtil.transStrToDate(time_end, DateUtil.FORMAT_SS_NO_SYMBOL);
		} catch (ParseException e) {
			logger.error("微信异步通知错误，转换支付完成时间time_end字段失败，默认还款成功时间为当前时间，错误：" + e.getMessage());
		}
		UserPaymentOrder paymentOrder = this.userPaymentOrderService.queryPaymentOrderById(out_trade_no);
		if (EntityUtils.isEmpty(paymentOrder)) {
			logger.error("微信异步返回订单号未查询到相关订单! 返回流水号：" + out_trade_no + ",请求状态："
					+ paymentOrder.getRequestStatus() + ",支付状态：" + paymentOrder.getPaymentStatus());
			throw new RuntimeException("根据流水号" + out_trade_no + "未查询到相关订单");
		}

		// 待支付和处理中的订单进行支付成功更新操作
		if (StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY.intValue() == paymentOrder.getPaymentStatus()
				|| StatusConstants.PAYMENT_ORDER_STATUS_DEALING.intValue() == paymentOrder.getPaymentStatus()) {
			// 成功处理
			if (isSuccess) {
				String bankName = null;
				if (EntityUtils.isNotEmpty(bank_type)) {
					bankName = this.getBankName(bank_type);
					paymentOrder.setBankId(bank_type);
					paymentOrder.setBankName(bankName);
				}
				this.userPaymentOrderService.dealPaymentOrderOfPaSuccess(paymentOrder, bankTxTime, "");
			} else {
				// 失败处理
				this.userPaymentOrderService.dealPaymentOrderOfPayFailed(paymentOrder, false, bankTxTime, err_code_des);
			}
		}
	}

	/**
	 * 请求微信查询订单接口
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> orderQueryReq(Map<String, String> reqMap) throws Exception {
		reqMap.put("appid", appid);
		reqMap.put("mch_id", mch_id);
		reqMap.put("nonce_str", this.getNonceStr(30));

		// 根据参数获取签名
		String reqSign = this.getSign(reqMap);
		// 加入签名参数
		reqMap.put("sign", reqSign);

		// xml格式化参数
		String reqXml = this.map2Xml(reqMap);
		logger.info("微信查询订单请求报文：" + reqXml);

		// 接口请求
		String respXml = this.reqWx(query_order_url, reqXml, charset);
		logger.info("微信查询订单返回报文：" + respXml);

		Map<String, String> respMap = this.xml2Map(respXml);

		return respMap;
	}
	
	/**
	 * 短链接获取
	 * 
	 * @param long_url 
	 * @return
	 * @throws Exception
	 */
	private String shortUrlReq(String long_url) throws Exception {
		long_url = URLEncoder.encode(long_url, charset);
		
		Map<String, String> reqMap = new HashMap<String,String>();
		
		reqMap.put("appid", appid);
		reqMap.put("mch_id", mch_id);
		reqMap.put("long_url", long_url);
		reqMap.put("nonce_str", this.getNonceStr(30));

		// 根据参数获取签名
		String reqSign = this.getSign(reqMap);
		// 加入签名参数
		reqMap.put("sign", reqSign);

		// xml格式化参数
		String reqXml = this.map2Xml(reqMap);
		logger.info("微信短链接请求报文：" + reqXml);

		// 接口请求
		String respXml = this.reqWx(wx_short_url, reqXml, charset);
		logger.info("微信短链接返回报文：" + respXml);
		
		Map<String, String> respMap = this.xml2Map(respXml);

		String return_code = respMap.get("return_code");
		if (WxPayConstants.RETURN_CODE_SUCCESS.equals(return_code)) {
			return respMap.get("short_url");
		}
		return null;
	}

	/**
	 * 根据规则生成签名
	 * 
	 * @param paramMap
	 * @return
	 */
	private String getSign(Map<String, String> paramMap) {
		List<String> list = new ArrayList<String>();

		// 对参数按照key=value的格式拼接字符串
		Set<Entry<String, String>> reqEntrySet = paramMap.entrySet();
		for (Entry<String, String> entry : reqEntrySet) {
			if (EntityUtils.isNotEmpty(entry.getValue())) {
				String s = entry.getKey() + "=" + entry.getValue();
				list.add(s);
			}
		}

		// 进行ASCII字典序排序
		Collections.sort(list);
		// 格式化拼接
		String stringSign = StringUtils.collectionToDelimitedString(list, "&");
		// 拼接key
		stringSign = stringSign + "&key=" + md5_key;
		// 根据参数生成签名
		String sign = DigestUtils.md5DigestAsHex(stringSign.getBytes()).toUpperCase();

		return sign;
	}

	private String reqWx(String url, String reqXml, String charset) {
		CloseableHttpClient client = null;
		CloseableHttpResponse httpResponse = null;
		try {
			// 设置httpClient参数
			RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000)
					.setConnectionRequestTimeout(5000).build();

			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
			connManager.setValidateAfterInactivity(1000);
			connManager.setMaxTotal(200); // 设置整个连接池最大连接数
			connManager.setDefaultMaxPerRoute(20);// 每个路由最大连接数为20（单个路由可设置为最大conman.getMaxTotal()）

			client = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(config).build();
			HttpPost post = new HttpPost(url);
			StringEntity ent = new StringEntity(reqXml, ContentType.create("application/xml", charset));
			post.setEntity(ent);
			httpResponse = client.execute(post);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String respXml = org.apache.http.util.EntityUtils.toString(entity, charset);
				return respXml;
			}
		} catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * map转xml
	 * 
	 * @param map
	 * @return
	 * @throws IOException
	 */
	private String map2Xml(Map<String, String> map) throws IOException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("xml");
		Set<Entry<String, String>> entrySet = map.entrySet();
		for (Entry<String, String> entry : entrySet) {
			root.addElement(entry.getKey()).addText(entry.getValue());
		}
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw);
		xw.setEscapeText(false);
		xw.write(doc);
		String str = sw.toString();
		str = str.replace(str.substring(0, str.lastIndexOf("?>") + 2), "");
		str = StringUtils.trimAllWhitespace(str);
		return str;
	}

	/**
	 * xml转map
	 * 
	 * @param xml
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> xml2Map(String xml) throws IOException {
		Map<String, String> map = new HashMap<String, String>();

		// 解析XML
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			for (Iterator it = root.elementIterator(); it.hasNext();) {
				Element next = (Element) it.next();
				map.put(next.getName(), next.getTextTrim());
			}
			return map;
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取随机字符
	 * 
	 * @param limit
	 * @return
	 */
	private String getNonceStr(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(62);
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}

	/**
	 * 获取本机ip
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getLocalIp() {
		String ip = null;
		Enumeration allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
				for (InterfaceAddress add : InterfaceAddress) {
					InetAddress Ip = add.getAddress();
					if (Ip != null && Ip instanceof Inet4Address) {
						ip = Ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			logger.warn("获取本机Ip失败:异常信息:" + e.getMessage());
		}
		return ip;
	}
	
	/**
	 * 根据微信返回银行代码转换银行名称
	 * 
	 * @return
	 */
	private String getBankName(String bank_type) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("CFT","财付通");
		map.put("ICBC_DEBIT","工商银行(借记卡)");
		map.put("ICBC_CREDIT","工商银行(信用卡)");
		map.put("ABC_DEBIT","农业银行(借记卡)");
		map.put("ABC_CREDIT","农业银行(信用卡)");
		map.put("PSBC_DEBIT","邮政储蓄银行(借记卡)");
		map.put("PSBC_CREDIT","邮政储蓄银行(信用卡)");
		map.put("CCB_DEBIT","建设银行(借记卡)");
		map.put("CCB_CREDIT","建设银行(信用卡)");
		map.put("CMB_DEBIT","招商银行(借记卡)");
		map.put("CMB_CREDIT","招商银行(信用卡)");
		map.put("BOC_DEBIT","中国银行(借记卡)");
		map.put("BOC_CREDIT","中国银行(信用卡)");
		map.put("COMM_DEBIT","交通银行(借记卡)");
		map.put("SPDB_DEBIT","浦发银行(借记卡)");
		map.put("SPDB_CREDIT","浦发银行(信用卡)");
		map.put("GDB_DEBIT","广发银行(借记卡)");
		map.put("GDB_CREDIT","广发银行(信用卡)");
		map.put("CMBC_DEBIT","民生银行(借记卡)");
		map.put("CMBC_CREDIT","民生银行(信用卡)");
		map.put("PAB_DEBIT","平安银行(借记卡)");
		map.put("PAB_CREDIT","平安银行(信用卡)");
		map.put("CEB_DEBIT","光大银行(借记卡)");
		map.put("CEB_CREDIT","光大银行(信用卡)");
		map.put("CIB_DEBIT","兴业银行(借记卡)");
		map.put("CIB_CREDIT","兴业银行(信用卡)");
		map.put("CITIC_DEBIT","中信银行(借记卡)");
		map.put("CITIC_CREDIT","中信银行(信用卡)");
		map.put("BOSH_DEBIT","上海银行(借记卡)");
		map.put("BOSH_CREDIT","上海银行(信用卡)");
		map.put("CRB_DEBIT","华润银行(借记卡)");
		map.put("HZB_DEBIT","杭州银行(借记卡)");
		map.put("HZB_CREDIT","杭州银行(信用卡)");
		map.put("BSB_DEBIT","包商银行(借记卡)");
		map.put("BSB_CREDIT","包商银行(信用卡)");
		map.put("CQB_DEBIT","重庆银行(借记卡)");
		map.put("SDEB_DEBIT","顺德农商行(借记卡)");
		map.put("SZRCB_DEBIT","深圳农商银行(借记卡)");
		map.put("HRBB_DEBIT","哈尔滨银行(借记卡)");
		map.put("BOCD_DEBIT","成都银行(借记卡)");
		map.put("GDNYB_DEBIT","南粤银行(借记卡)");
		map.put("GDNYB_CREDIT","南粤银行(信用卡)");
		map.put("GZCB_DEBIT","广州银行(借记卡)");
		map.put("GZCB_CREDIT","广州银行(信用卡)");
		map.put("JSB_DEBIT","江苏银行(借记卡)");
		map.put("JSB_CREDIT","江苏银行(信用卡)");
		map.put("NBCB_DEBIT","宁波银行(借记卡)");
		map.put("NBCB_CREDIT","宁波银行(信用卡)");
		map.put("NJCB_DEBIT","南京银行(借记卡)");
		map.put("JZB_DEBIT","晋中银行(借记卡)");
		map.put("KRCB_DEBIT","昆山农商(借记卡)");
		map.put("LJB_DEBIT","龙江银行(借记卡)");
		map.put("LNNX_DEBIT","辽宁农信(借记卡)");
		map.put("LZB_DEBIT","兰州银行(借记卡)");
		map.put("WRCB_DEBIT","无锡农商(借记卡)");
		map.put("ZYB_DEBIT","中原银行(借记卡)");
		map.put("ZJRCUB_DEBIT","浙江农信(借记卡)");
		map.put("WZB_DEBIT","温州银行(借记卡)");
		map.put("XAB_DEBIT","西安银行(借记卡)");
		map.put("JXNXB_DEBIT","江西农信(借记卡)");
		map.put("NCB_DEBIT","宁波通商银行(借记卡)");
		map.put("NYCCB_DEBIT","南阳村镇银行(借记卡)");
		map.put("NMGNX_DEBIT","内蒙古农信(借记卡)");
		map.put("SXXH_DEBIT","陕西信合(借记卡)");
		map.put("SRCB_CREDIT","上海农商银行(信用卡)");
		map.put("SJB_DEBIT","盛京银行(借记卡)");
		map.put("SDRCU_DEBIT","山东农信(借记卡)");
		map.put("SRCB_DEBIT","上海农商银行(借记卡)");
		map.put("SCNX_DEBIT","四川农信(借记卡)");
		map.put("QLB_DEBIT","齐鲁银行(借记卡)");
		map.put("QDCCB_DEBIT","青岛银行(借记卡)");
		map.put("PZHCCB_DEBIT","攀枝花银行(借记卡)");
		map.put("ZJTLCB_DEBIT","浙江泰隆银行(借记卡)");
		map.put("TJBHB_DEBIT","天津滨海农商行(借记卡)");
		map.put("WEB_DEBIT","微众银行(借记卡)");
		map.put("YNRCCB_DEBIT","云南农信(借记卡)");
		map.put("WFB_DEBIT","潍坊银行(借记卡)");
		map.put("WHRC_DEBIT","武汉农商行(借记卡)");
		map.put("ORDOSB_DEBIT","鄂尔多斯银行(借记卡)");
		map.put("XJRCCB_DEBIT","新疆农信银行(借记卡)");
		map.put("ORDOSB_CREDIT","鄂尔多斯银行(信用卡)");
		map.put("CSRCB_DEBIT","常熟农商银行(借记卡)");
		map.put("JSNX_DEBIT","江苏农商行(借记卡)");
		map.put("GRCB_CREDIT","广州农商银行(信用卡)");
		map.put("GLB_DEBIT","桂林银行(借记卡)");
		map.put("GDRCU_DEBIT","广东农信银行(借记卡)");
		map.put("GDHX_DEBIT","广东华兴银行(借记卡)");
		map.put("FJNX_DEBIT","福建农信银行(借记卡)");
		map.put("DYCCB_DEBIT","德阳银行(借记卡)");
		map.put("DRCB_DEBIT","东莞农商行(借记卡)");
		map.put("CZCB_DEBIT","稠州银行(借记卡)");
		map.put("CZB_DEBIT","浙商银行(借记卡)");
		map.put("CZB_CREDIT","浙商银行(信用卡)");
		map.put("GRCB_DEBIT","广州农商银行(借记卡)");
		map.put("CSCB_DEBIT","长沙银行(借记卡)");
		map.put("CQRCB_DEBIT","重庆农商银行(借记卡)");
		map.put("CBHB_DEBIT","渤海银行(借记卡)");
		map.put("BOIMCB_DEBIT","内蒙古银行(借记卡)");
		map.put("BOD_DEBIT","东莞银行(借记卡)");
		map.put("BOD_CREDIT","东莞银行(信用卡)");
		map.put("BOB_DEBIT","北京银行(借记卡)");
		map.put("BNC_DEBIT","江西银行(借记卡)");
		map.put("BJRCB_DEBIT","北京农商行(借记卡)");
		map.put("AE_CREDIT","AE(信用卡)");
		map.put("GYCB_CREDIT","贵阳银行(信用卡)");
		map.put("JSHB_DEBIT","晋商银行(借记卡)");
		map.put("JRCB_DEBIT","江阴农商行(借记卡)");
		map.put("JNRCB_DEBIT","江南农商(借记卡)");
		map.put("JLNX_DEBIT","吉林农信(借记卡)");
		map.put("JLB_DEBIT","吉林银行(借记卡)");
		map.put("JJCCB_DEBIT","九江银行(借记卡)");
		map.put("HXB_DEBIT","华夏银行(借记卡)");
		map.put("HXB_CREDIT","华夏银行(信用卡)");
		map.put("HUNNX_DEBIT","湖南农信(借记卡)");
		map.put("HSB_DEBIT","徽商银行(借记卡)");
		map.put("HSBC_DEBIT","恒生银行(借记卡)");
		map.put("HRXJB_DEBIT","华融湘江银行(借记卡)");
		map.put("HNNX_DEBIT","河南农信(借记卡)");
		map.put("HKBEA_DEBIT","东亚银行(借记卡)");
		map.put("HEBNX_DEBIT","河北农信(借记卡)");
		map.put("HBNX_DEBIT","湖北农信(借记卡)");
		map.put("HBNX_CREDIT","湖北农信(信用卡)");
		map.put("GYCB_DEBIT","贵阳银行(借记卡)");
		map.put("GSNX_DEBIT","甘肃农信(借记卡)");
		map.put("JCB_CREDIT","JCB(信用卡)");
		map.put("MASTERCARD_CREDIT","MASTERCARD(信用卡)");
		map.put("VISA_CREDIT","VISA(信用卡)");

		return map.get(bank_type);
	}
}
