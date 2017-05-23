package com.hikootech.mqcash.util;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * MD5工具类
 * 
 * @author zhenghl
 * 
 */
public class DigestUtil {
	
	private static Logger log = LoggerFactory.getLogger(DigestUtil.class);

	/**
	 * map转化为对应的字符串
	 * 
	 * @param map
	 * @return
	 */
	public static String mapToString(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Object[] key = map.keySet().toArray();
		Arrays.sort(key);

		StringBuffer res;
		res = new StringBuffer(128);
		for (Object aKey : key) {
			res.append(aKey).append("=").append(map.get(aKey)).append("&");
		}

		return res.substring(0, res.length() - 1);
	}

	/**
	 * 字符转化为对应map
	 * 
	 * @param digest
	 * @return
	 */
	public static Map<String, String> stringToMap(String digest) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if (digest == null) {
			return null;
		}
		String[] split = digest.split("&");
		for (String aSplit : split) {
			String[] temp = aSplit.split("=");
			if (temp.length == 1) {
				resultMap.put(temp[0], "");
			}
			if (temp.length > 1) {
				resultMap.put(temp[0], temp[1]);
				// log.info(temp[0]+"="+temp[1]);
			}
		}
		return resultMap;
	}

	/**
	 * 构造请求字符串
	 * 
	 * @param map
	 * @param appInitKey
	 * @return
	 */
	public static String createParam(Map<String, String> map, String appInitKey) {
		try {
			String rStr = mapToString(map);
			if (appInitKey == null) {
				return rStr + "&verify=" + getKeyedDigest(rStr, "");
			}

			log.info("verify=" + getKeyedDigest(rStr, appInitKey));
			return rStr + "&verify=" + getKeyedDigest(rStr, appInitKey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 多字符串MD5加密
	 * 
	 * @param strSrc
	 * @param key
	 * @return
	 */
	public static String getKeyedDigest(String strSrc, String key) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strSrc.getBytes("UTF-8"));

			String result = "";
			byte[] temp;
			temp = md5.digest(key.getBytes("UTF-8"));
			for (byte aTemp : temp) {
				result += Integer
						.toHexString((0x000000ff & aTemp) | 0xffffff00)
						.substring(6);
			}

			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 多字符串MD5加密
	 * 
	 * @param strSrc
	 * @param key
	 * @param charset
	 * @return
	 */
	public static String getKeyedDigest(String strSrc, String key, String charset) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strSrc.getBytes(charset));

			String result = "";
			byte[] temp;
			temp = md5.digest(key.getBytes(charset));
			for (byte aTemp : temp) {
				result += Integer
						.toHexString((0x000000ff & aTemp) | 0xffffff00)
						.substring(6);
			}

			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Description：单点登录使用
	 * 
	 * @param map
	 * @param appInitKey
	 * @return
	 */
	public static String getKeyedDigest(Map<String, String> map,
			String appInitKey) {
		String rStr = mapToString(map);
		if (appInitKey != null) {
			return getKeyedDigest(rStr, appInitKey);
		}
		return getKeyedDigest(rStr, "");
	}

	/**
	 * 组合请求参数(a=b&c=d的形式)
	 * 
	 * @param map
	 *            请求参数
	 * @return
	 */
	public static String createParam2(Map<String, String> map) {
		try {
			if (map == null || map.isEmpty()) {
				return null;
			}

			// 对参数名按照ASCII升序排序
			Object[] key = map.keySet().toArray();
			Arrays.sort(key);

			// 生成加密原串
			StringBuffer res = new StringBuffer(128);
			for (int i = 0; i < key.length; i++) {
				res.append(key[i] + "=" + map.get(key[i]) + "&");
			}

			String rStr = res.substring(0, res.length() - 1);

			return rStr;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 组合请求参数(a=b&c=d的形式)同时对每一个value值进行URLEncoder.encode(value, "GBK")
	 * 
	 * @param map
	 *            请求参数
	 * @return
	 */
	public static String createParam3(Map<String, String> map) {
		try {
			if (map == null || map.isEmpty()) {
				return null;
			}

			// 对参数名按照ASCII升序排序
			Object[] key = map.keySet().toArray();
			Arrays.sort(key);

			// 生成加密原串
			StringBuffer res = new StringBuffer(128);
			for (int i = 0; i < key.length; i++) {
				res.append(key[i] + "=" + URLEncoder.encode(map.get(key[i]), "GBK") + "&");
			}

			String rStr = res.substring(0, res.length() - 1);

			return rStr;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
}
