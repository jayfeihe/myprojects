package com.hikootech.mqcash.haodai;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikootech.mqcash.util.ConfigUtils;

/**
 * Created by shenhongxi on 15/5/16.
 */
public class RiskUtil {
	private static Logger logger = LoggerFactory.getLogger(RiskUtil.class);

	/*
	 * private static final String AES = "AES"; private static final String
	 * PADDING = "AES/CBC/NoPadding";//"算法/模式/补码方式" private static final String
	 * DEFAULT_ENCODING = "utf-8"; private static final String IV =
	 * "1234567812345678"; //风控身份认证 public static final String RISK_CONTROL_URL
	 * = "http://fengkong.haodai.com/api/data/index"; public static final String
	 * RISK_CONTROL_KEY = "i9RSOl45pcB7jXLp"; public static final String
	 * RISK_CONTROL_ID = "00000000-0";
	 */

	private static final String AES = ConfigUtils.getProperty("aes");
	private static final String PADDING = ConfigUtils.getProperty("padding");
	private static final String DEFAULT_ENCODING = ConfigUtils.getProperty("default_encoding");
	private static final String IV = ConfigUtils.getProperty("iv");
	private static final String RISK_CONTROL_URL = ConfigUtils.getProperty("risk_control_url");
	public static final String RISK_CONTROL_KEY = ConfigUtils.getProperty("risk_control_key");
	private static final String RISK_CONTROL_ID = ConfigUtils.getProperty("risk_control_id");
	// private static final String IMAGE =
	// "http://stat.xuehaodai.com/ds/fhqxgjrvslh6wdprxawi4eroprdapsrn.jpg";
	// private static final String IMAGE =
	// "http://stat.xuehaodai.com/ds/x2/magbvwoql9zgjg4xjynykh8huckdfb6m.jpeg";
	// private static final String IMAGE =
	// "http://stat.xuehaodai.com/ds/e7dah5zi4je6nnzpridigpczikkglclo.jpg";

	/**
	 * 100000:操作成功 100001:组织机构代码号错误 100002:解密错误 !!!注意替换 jre/lib/security下的两个jar
	 * 100003:请求参数错误，请求类型不存在 100004:请求参数错误，请求类型存在，但其它参数有错误 100005:系统错误
	 * 100006:账户积分不足 100007:IP受限
	 * 
	 * 返回对比结果 00,10：核查成功 01：库中无此号 02：不一致 03：身份证号不符合规则 04：姓名不符合规则
	 * 05：姓名身份证号照片不符合规则 06：照片不符合规则 -1: 用户名有误 -2: 摘要有误 -3: IP地址受限 -4: 提交数据有误
	 * -999: 系统异常 其他：系统异常 用户实名认证，传递一个姓名 + 身份证号 + 头像照
	 * 调用接口失败返回ResultCode.SYSTEM_ERROR.code 成功则返回 code,message(,score)
	 * score为头像识别所得分数
	 * 
	 * @param name
	 *            姓名
	 * @param cardId
	 *            身份证号
	 * @param photoUrl
	 *            头像照片 URL
	 * @return
	 */
	public static String certify(JSONObject json) {

		try {
			json.put("name", UnicodeUtils.native2ascii(json.get("name") + ""));
			String jsonStr = json.toString();
			jsonStr = jsonStr.replace("\\\\", "\\");
			String encryptData = encrypt(jsonStr, RISK_CONTROL_KEY);

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("companyid", RISK_CONTROL_ID));
			pairs.add(new BasicNameValuePair("data", encryptData));

			String response = null;
			// 创建默认的httpClient实例.
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 创建httppost
			HttpPost httppost = new HttpPost(RISK_CONTROL_URL);
/*			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000).build();//设置请求和传输超时时间
			httppost.setConfig(requestConfig)*/;
			
			UrlEncodedFormEntity uefEntity;
			try {
				uefEntity = new UrlEncodedFormEntity(pairs, "UTF-8");
				httppost.setEntity(uefEntity);
				logger.info("executing requestURI " + httppost.getURI());
				logger.info("executing requestEntity " + httppost.getEntity());
				CloseableHttpResponse httpResponse = httpclient.execute(httppost);
				
				try {
					HttpEntity entity = httpResponse.getEntity();
					if (entity != null) {
						logger.info("--------------------------------------");
						response = EntityUtils.toString(entity, "UTF-8");
						logger.info("Response content: " + response);
						logger.info("--------------------------------------");
					}
				} finally {
					httpResponse.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("请求好贷网接口异常，json.get(name)" + json.get("name") , e);
				throw new RuntimeException();
			} finally {
				// 关闭连接,释放资源
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("请求好贷网接口关闭连接异常，json.get(name)" + json.get("name") , e);
					throw new RuntimeException();
				}
			}

			if (StringUtils.isBlank(response)) {
				return "";
			}

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求好贷网参数转换异常，json.get(name)" + json.get("name") , e);
			return "";
		}
	}

	private static String encrypt(String code, String key) {
		code = padding(code);
		try {
			return new org.apache.commons.codec.binary.Base64()
					.encodeToString(encrypt(code.getBytes(DEFAULT_ENCODING), key.getBytes(DEFAULT_ENCODING)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] encrypt(byte[] code, byte[] key) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(key, AES);
		Cipher cipher = Cipher.getInstance(PADDING);// "算法/模式/补码方式"
		// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
		return cipher.doFinal(code);
	}

	public static String decrypt(String data, String key) {
		data = padding(data);
		try {
			return new String(decrypt(data.getBytes(DEFAULT_ENCODING), key.getBytes(DEFAULT_ENCODING)),
					DEFAULT_ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(key, AES);
		Cipher cipher = Cipher.getInstance(PADDING);
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes(DEFAULT_ENCODING));
		cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
		byte[] _src = new Base64().decode(src);
		return cipher.doFinal(_src);
	}

	private static String padding(String code) {
		for (int i = 0; i < code.length(); i++) {
			code += " ";
			if (code.length() % 16 == 0) {
				break;
			}
		}
		return code;
	}

	/*
	 * public static void main(String[] args) throws Exception { // String code
	 * = "1234567812345678"; // 必须为16位倍数，不是的可用空格补齐 // String key =
	 * "LDb4LjU2wTunFnaXswtwerQzqaybJn9u"; // 必须为16/32位 //
	 * System.out.println("加密前：" + code); // String _code = encrypt(code, key);
	 * // System.out.println("加密编码后：" + _code); // System.out.println("编码解密后：" +
	 * decrypt(_code, key)); JSONObject jo=new JSONObject(); jo.put("type",
	 * 700); jo.put("name", "张三"); jo.put("idcard", "110105198307077715");
	 * jo.put("mobile", "13581528902"); System.out.println(jo); //JSONObject jo
	 * = JSONObject.fromObject(
	 * "{'type':202,'name':'张三','idcard':'110105198307077715'}"); RiskUtil rt =
	 * new RiskUtil(); JSONObject jo2 = JSONObject.fromObject(rt.certify(jo));
	 * String str = decrypt(jo2.get("data")+"",RISK_CONTROL_KEY);
	 * System.out.println("加密编码后：" + str); String result =
	 * UnicodeUtils.ascii2native(str); System.out.println(result);
	 * 
	 * }
	 */
}
