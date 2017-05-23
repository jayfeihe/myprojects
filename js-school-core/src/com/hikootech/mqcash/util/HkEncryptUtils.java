package com.hikootech.mqcash.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yuwei
 * 2015年11月11日
 * 加密、解密、验签工具类
 */
public class HkEncryptUtils {
	
	private static Logger log = LoggerFactory.getLogger(HkEncryptUtils.class);
	
	
	/**根据字符串，先进行字符串16进制转2进制，对转换结果字节数组根据desKey进行3des解密，
	 * 解密结果字节数组转换为字符串，将字符串转换为Map对象
	 * @param busParams 加密后业务参数字符串
	 * @param desKey 3des密钥
	 * @param charset 字符集
	 * @return
	 */
	public static Map<String, String> createDecryptBusMap(String busParams, String desKey, String charset){
		if(busParams == null || "".equals(busParams.trim())){
			return null;
		}
		
		Map<String, String> busMap = null;
		try {
			byte[] encrypt = HkEncryptUtils.parseHexStr2Byte(busParams);
			byte[] decrypt = ThreeDES.decryptMode(desKey.getBytes(charset), encrypt);
			String desString = new String(decrypt, charset);
			log.debug("解密后明文字符串：" +  URLDecoder.decode(desString, charset));
			busMap = HkEncryptUtils.stringToMapWithEncodeValue(desString, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("解密字符串编码集有误", e);
		}
		
		return busMap;
	}
	
	
	/**根据Map中参数，生成以key=value形式以&符号、参数key以ASCII排序，拼接各key=value字符串，
	 * 然后把字符串根据md5key进行MD5加密
	 * @param paramMap 参数Map
	 * @param md5Key md5密钥
	 * @param charset 字符集
	 * @return
	 */
	public static String createMd5Sign(Map<String, String> paramMap, String md5Key, String charset){
		if(paramMap == null || paramMap.size() == 0){
			log.error("生成摘要（验签）的参数Map为空");
			return null;
		}
		
		String mapString = HkEncryptUtils.mapToString(paramMap);
		String sign = HkEncryptUtils.getKeyedDigest(mapString, md5Key, charset);
		
		return sign;
	}
	
	/**
	 * 多字符串MD5加密
	 * @param strSrc 加密前原串
	 * @param key md5密钥
	 * @param charset 字符集
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
		} catch (Exception e) {
			log.error("生成md5签名异常："+e);
		}
		return null;
	}
	
	/**根据Map中参数，生成以key=value形式以&符号、参数key以ASCII排序、参数值进行URLEncoder.encode，拼接各key=value字符串，
	 * 然后把字符串根据desKey以3des加密方式加密，最后将加密结果字节数组转换为16进制字符串
	 * 例如：Map中值为{key1=value1,key2=value2},生成结果key1=URLEncoder.encode(value1)&key2=URLEncoder.encode(value2)
	 * @param busMap 参数Map
	 * @param desKey 3des密钥
	 * @param charset 字符集
	 * @return
	 */
	public static String createEncryptBusParams(Map<String, String> busMap, String desKey, String charset){
		if(busMap == null || busMap.size() == 0){
			log.error("生成加密串的参数Map为空");
			return null;
		}
		
		String busParams = null;
		try {
			String busString = HkEncryptUtils.mapToStringWithEncodeValue(busMap, charset);
			byte[] desString = ThreeDES.encryptMode(desKey.getBytes(charset), busString.getBytes(charset));
			busParams = HkEncryptUtils.parseByte2HexStr(desString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("加密字符串编码集有误", e);
		}
		
		return busParams;
	}
	
	/**根据Map中参数，生成以key=value形式以&符号、参数key以ASCII排序、参数值进行URLEncoder.encode，拼接各key=value字符串
	 * 例如：Map中值为{key1=value1,key2=value2},生成结果key1=URLEncoder.encode(value1)&key2=URLEncoder.encode(value2)
	 * @param paramMap 参数Map
	 * @param enc URLEncoder.encode的字符集
	 * @return
	 */
	public static String mapToStringWithEncodeValue(Map<String, String> paramMap, String enc) {
		if (paramMap == null || paramMap.isEmpty()) {
			return null;
		}

		Object[] key = paramMap.keySet().toArray();
		Arrays.sort(key);

		StringBuffer res;
		res = new StringBuffer(128);
		for (Object aKey : key) {
			try {
				res.append(aKey).append("=").append(URLEncoder.encode(paramMap.get(aKey), enc)).append("&");
			} catch (UnsupportedEncodingException e) {
				log.error("生成业务参数字符串出错，字符集编码出错,aKey:" + aKey + ",paramMap.get(aKey)：" + paramMap.get(aKey) + ",key:" + key,e);
				return null;
			}
		}

		return res.substring(0, res.length() - 1);
	}
	
	/**
	 * map转化为对应的字符串
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
	 * @param digest
	 * @return
	 */
	public static Map<String, String> stringToMap(String paramString) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if (paramString == null) {
			return null;
		}
		String[] split = paramString.split("&");
		for (String aSplit : split) {
			String[] temp = aSplit.split("=");
			if (temp.length == 1) {
				resultMap.put(temp[0], "");
			}
			if (temp.length > 1) {
				resultMap.put(temp[0], temp[1]);
			}
		}
		return resultMap;
	}
	
	/**
	 * 字符转化为对应map
	 * @param digest
	 * @return
	 */
	public static Map<String, String> stringToMapWithEncodeValue(String paramString, String enc) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if (paramString == null) {
			return null;
		}
		String[] split = paramString.split("&");
		for (String aSplit : split) {
			String[] temp = aSplit.split("=");
			if (temp.length == 1) {
				resultMap.put(temp[0], "");
			}
			if (temp.length > 1) {
				try {
					resultMap.put(temp[0], URLDecoder.decode(temp[1], enc));
				} catch (UnsupportedEncodingException e) {
					log.error("生成业务参数Map字符串出错，字符集编码出错", e);
					return null;
				}
			}
		}
		return resultMap;
	}
	
	
	 /**将二进制转换16进制
	  * 二进制 & 0xFF --> 高位补0
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    
    /**16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    
    /**
	 * 根据字符串，先进行字符串16进制转2进制，对转换结果字节数组根据desKey进行3des解密，
	 * @param busString 
	 * @param desKey 3des密钥
	 * @param charset 字符集
	 * @return
	 */
	public static String decrypt(String busParams, String desKey, String charset){
		if(busParams == null || "".equals(busParams.trim())){
			return null;
		}
		
		String desString = null;
		try {
			byte[] encrypt = HkEncryptUtils.parseHexStr2Byte(busParams);
			byte[] decrypt = ThreeDES.decryptMode(desKey.getBytes(charset), encrypt);
			desString = new String(decrypt, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("解密字符串编码集有误", e);
		}
		
		return desString;
	}
	
	/**
	 * 字符串根据desKey以3des加密方式加密，最后将加密结果字节数组转换为16进制字符串
	 * @param busString key=value形式以&符号、参数key以ASCII排序、参数值进行URLEncoder.encode，拼接各key=value字符串
	 * @param desKey 3des密钥
	 * @param charset 字符集
	 * @return
	 */
	public static String encrypt(String busString, String desKey, String charset){
		if(busString == null || busString.trim() == ""){
			log.error("加密串不能为空");
			return null;
		}
		
		String busParams = null;
		try {
			byte[] desString = ThreeDES.encryptMode(desKey.getBytes(charset), busString.getBytes(charset));
			busParams = HkEncryptUtils.parseByte2HexStr(desString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("加密字符串编码集有误", e);
		}
		
		return busParams;
	}

}
