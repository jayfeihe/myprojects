package com.hikootech.mqcash.service.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dto.WxUnifiedOrderDTO;
import com.hikootech.mqcash.service.WxNativePayService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.HkEncryptUtils;
import com.hikootech.mqcash.util.HttpClientUtil;

/**
 * 微信扫一扫支付接口实现
 * 
 * @author QYANZE
 *
 */
@Service("wxNativePayService")
public class WxNativePayServiceImpl implements WxNativePayService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final String inner_desKey = ConfigUtils.getProperty("inner_mq_plat_3des_key");
	private final String inner_charset = ConfigUtils.getProperty("inner_mq_plat_enc");
	private final String inner_partnerId = ConfigUtils.getProperty("inner_mq_plat_partner_id");;
	private final String inner_version = ConfigUtils.getProperty("inner_mq_plat_data_version");
	private final String inner_md5Key = ConfigUtils.getProperty("inner_mq_plat_md5_key");
	
	private final String get_QR_code_url_1 = ConfigUtils.getProperty("inner_get_QR_code_url_1");
	private final String get_QR_code_url_2 = ConfigUtils.getProperty("inner_get_QR_code_url_2");
	private final String inner_query_order_url = ConfigUtils.getProperty("inner_query_order_url");

	@Override
	public String getQRCodeUrlModel1(String product_id) throws Exception {
		if (EntityUtils.isEmpty(product_id)) {
			throw new RuntimeException("商品id不能为空");
		}

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("product_id", product_id);

		// 生成业务加密串
		String busParams = HkEncryptUtils.createEncryptBusParams(paramsMap, inner_desKey, inner_charset);

		// 协议参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("timestamp", DateUtil.getCurDateStr("yyyyMMddHHmmss"));
		paramMap.put("partnerId", inner_partnerId);
		paramMap.put("version", inner_version);
		paramMap.put("params", busParams);

		// 生成验签sign
		String sign = HkEncryptUtils.createMd5Sign(paramMap, inner_md5Key, inner_charset);
		paramMap.put("sign", sign);

		HttpClientUtil http = new HttpClientUtil();
		String result = "";
		try {
			result = http.doPost(get_QR_code_url_1, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 接收返回结果
		Map<String, String> resultMap = HkEncryptUtils.stringToMap(result);

		// 获取结果参数
		String result_sign = resultMap.get("sign"); // 签名
		String result_timestamp = resultMap.get("timestamp"); // 访问时间戳
		String result_partnerId = resultMap.get("partnerId"); // 合作伙伴id
		String result_version = resultMap.get("version"); // 版本号
		String result_params = resultMap.get("params"); // 返回业务参数

		// 验签参数
		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("params", result_params);
		signMap.put("partnerId", result_partnerId);
		signMap.put("timestamp", result_timestamp);
		signMap.put("version", result_version);

		// 根据返回数据生成签名
		String _sign = HkEncryptUtils.createMd5Sign(signMap, inner_md5Key, inner_charset);

		// 根据签名判断处理
		if (result_sign.equals(_sign)) {
			// 解密返回参数
			Map<String, String> busMap = HkEncryptUtils.createDecryptBusMap(result_params, inner_desKey, inner_charset);
			logger.info("核心微信接口获取二维码url：" + busMap.get("code_url"));
			return busMap.get("code_url");
		} else {
			logger.info("核心微信接口获取二维码url（模式一）签名不一致：【获得签名：" + result_sign + ",生成签名：" + _sign + "】");
			return null;
		}
	}

	@Override
	public String getQRCodeUrlModel2(WxUnifiedOrderDTO order) throws Exception {
		// 参数校验
		this.validateParam(order);

		Map<String, String> paramsMap = obj2Map(order);

		// 生成业务加密串
		String busParams = HkEncryptUtils.createEncryptBusParams(paramsMap, inner_desKey, inner_charset);

		// 协议参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("timestamp", DateUtil.getCurDateStr("yyyyMMddHHmmss"));
		paramMap.put("partnerId", inner_partnerId);
		paramMap.put("version", inner_version);
		paramMap.put("params", busParams);

		// 生成验签sign
		String sign = HkEncryptUtils.createMd5Sign(paramMap, inner_md5Key, inner_charset);
		paramMap.put("sign", sign);

		HttpClientUtil http = new HttpClientUtil();
		String result = "";
		try {
			result = http.doPost(get_QR_code_url_2, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 接收返回结果
		Map<String, String> resultMap = HkEncryptUtils.stringToMap(result);

		// 获取结果参数
		String result_sign = resultMap.get("sign"); // 签名
		String result_timestamp = resultMap.get("timestamp"); // 访问时间戳
		String result_partnerId = resultMap.get("partnerId"); // 合作伙伴id
		String result_version = resultMap.get("version"); // 版本号
		String result_params = resultMap.get("params"); // 返回业务参数

		// 验签参数
		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("params", result_params);
		signMap.put("partnerId", result_partnerId);
		signMap.put("timestamp", result_timestamp);
		signMap.put("version", result_version);

		// 根据返回数据生成签名
		String _sign = HkEncryptUtils.createMd5Sign(signMap, inner_md5Key, inner_charset);

		// 根据签名判断处理
		if (result_sign.equals(_sign)) {
			// 解密返回参数
			Map<String, String> busMap = HkEncryptUtils.createDecryptBusMap(result_params, inner_desKey, inner_charset);
			logger.info("核心微信接口获取二维码url：" + busMap.get("code_url"));
			return busMap.get("code_url");
		} else {
			logger.info("核心微信接口获取二维码url（模式二）签名不一致：【获得签名：" + result_sign + ",生成签名：" + _sign + "】");
			return null;
		}
	}

	@Override
	public Map<String, String> queryOrder(String out_trade_no) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("out_trade_no", out_trade_no);

		// 生成业务加密串
		String busParams = HkEncryptUtils.createEncryptBusParams(paramsMap, inner_desKey, inner_charset);

		// 协议参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("timestamp", DateUtil.getCurDateStr("yyyyMMddHHmmss"));
		paramMap.put("partnerId", inner_partnerId);
		paramMap.put("version", inner_version);
		paramMap.put("params", busParams);

		// 生成验签sign
		String sign = HkEncryptUtils.createMd5Sign(paramMap, inner_md5Key, inner_charset);
		paramMap.put("sign", sign);

		HttpClientUtil http = new HttpClientUtil();
		String result = "";
		try {
			result = http.doPost(inner_query_order_url, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 接收返回结果
		Map<String, String> resultMap = HkEncryptUtils.stringToMap(result);

		// 获取结果参数
		String result_sign = resultMap.get("sign"); // 签名
		String result_timestamp = resultMap.get("timestamp"); // 访问时间戳
		String result_partnerId = resultMap.get("partnerId"); // 合作伙伴id
		String result_version = resultMap.get("version"); // 版本号
		String result_params = resultMap.get("params"); // 返回业务参数

		// 验签参数
		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("params", result_params);
		signMap.put("partnerId", result_partnerId);
		signMap.put("timestamp", result_timestamp);
		signMap.put("version", result_version);

		// 根据返回数据生成签名
		String _sign = HkEncryptUtils.createMd5Sign(signMap, inner_md5Key, inner_charset);

		// 根据签名判断处理
		if (result_sign.equals(_sign)) {
			// 解密返回参数
			Map<String, String> busMap = HkEncryptUtils.createDecryptBusMap(result_params, inner_desKey, inner_charset);
			logger.info("核心微信接口查询订单解密参数：" + busMap);
			return busMap;
		} else {
			logger.info("核心微信接口查询订单签名不一致：【获得签名：" + result_sign + ",生成签名：" + _sign + "】");
			return null;
		}
	}

	/**
	 * 参数校验
	 * 
	 * @param order
	 */
	private void validateParam(WxUnifiedOrderDTO order) {
		String body = order.getBody();
		String out_trade_no = order.getOut_trade_no();
		String total_fee = order.getTotal_fee() + "";
		String product_id = order.getProduct_id();

		if (EntityUtils.isEmpty(body)) {
			throw new RuntimeException("商品描述不能为空");
		}
		if (EntityUtils.isEmpty(out_trade_no)) {
			throw new RuntimeException("商户订单号不能为空");
		}
		if (EntityUtils.isEmpty(total_fee)) {
			throw new RuntimeException("总金额不能为空");
		}
		if (EntityUtils.isEmpty(product_id)) {
			throw new RuntimeException("商品ID不能为空");
		}
	}

	/**
	 * 对象转Map
	 * 
	 * @param obj
	 * @return
	 */
	private Map<String, String> obj2Map(Object obj) {
		Map<String, String> map = new HashMap<String, String>();
		if (obj == null) {
			return map;
		}
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				String val = null;
				Object o = field.get(obj);
				if (o instanceof Integer) {
					o = (Integer) o;
					val = String.valueOf(o);
				}
				if (o instanceof Float) {
					o = (Float) o;
					val = String.valueOf(o);
				}
				if (o instanceof Long) {
					o = (Long) o;
					val = String.valueOf(o);
				}
				if (o instanceof String) {
					val = (String) o;
				}
				if (val != null && val != "") {
					map.put(field.getName(), val);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("对象转换map异常：" + e.getMessage(), e);
		}
		return map;
	}
}
