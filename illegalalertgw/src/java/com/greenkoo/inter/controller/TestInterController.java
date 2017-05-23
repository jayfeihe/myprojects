package com.greenkoo.inter.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.greenkoo.inter.constants.AdvertInterConstants;
import com.greenkoo.inter.model.AdvertInterRespDto;
import com.greenkoo.inter.model.AdvertInterDto;
import com.greenkoo.utils.Base64Util;
import com.greenkoo.utils.ConfigUtil;
import com.greenkoo.utils.FastJsonUtil;
import com.greenkoo.utils.HttpUtil;
import com.greenkoo.utils.SecurityUtil;

/**
 * 测试违法广告接口Controller
 * 
 * @author QYANZE
 *
 */
@Controller
public class TestInterController {

	private static String inter_url = ConfigUtil.getProperty("inter_url");
	private static String md5_key = ConfigUtil.getProperty("md5_key");
	private static String charset = ConfigUtil.getProperty("charset");
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "/testInter", method = RequestMethod.GET)
	public String testInter() {
		return "testInter";
	}

	
	/**
	 * 图片流测试
	 * 
	 * @param origDto
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/stream/testInter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> testInterByStream(AdvertInterDto origDto, @RequestParam MultipartFile file) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			//先进行URLEncode参数值
			AdvertInterDto encodeDto = encodeDto(origDto);
			
			byte[] bytes = file.getBytes();
			// 生成图片进行base64
			String adPic = Base64Util.encodeBase64ToString(bytes);
			encodeDto.setAdPic(SecurityUtil.URLEncode(adPic, charset));
			
			// 生成签名
			String sign = getSign(encodeDto);
			// 设置签名
			encodeDto.setSign(sign);
			
			String jsonParam = FastJsonUtil.toJSONString(encodeDto);
			
			logger.info("请求json参数 : " + jsonParam);
			
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("param", jsonParam ));
			
			String respStr = HttpUtil.doPost(inter_url, vps, "utf-8");
			
			logger.info("返回响应json数据 : " + respStr);
			
			AdvertInterRespDto respDto = FastJsonUtil.toBean(respStr, AdvertInterRespDto.class);
			String respSign = getSign(respDto);
			
			if (respSign.equals(respDto.getSign())) {
				if (AdvertInterConstants.SUCCESS_CODE.equals(respDto.getResult())) {
					returnMap.put("success", true);
					returnMap.put("message", "成功");
				} else {
					returnMap.put("success", false);
					returnMap.put("message", "接口返回失败");
				}
			} else {
				logger.error("响应返回签名认证不一致，生成签名：" + respSign + "，返回签名：" + respDto.getSign());
				returnMap.put("success", false);
				returnMap.put("message", "接口响应返回签名不一致");
			}
			
		} catch (Exception e) {
			logger.error("图片生成字节出错：" + e.getMessage(), e);
			returnMap.put("success", false);
			returnMap.put("message", "系统出错");
		}
		
		return returnMap;
	}
	
	/**
	 * 网络url测试
	 * 
	 * @param origDto
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/url/testInter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> testInterByUrl(AdvertInterDto origDto) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			//先进行URLEncode参数值
			AdvertInterDto encodeDto = encodeDto(origDto);
			
			encodeDto.setAdPic(SecurityUtil.URLEncode(origDto.getAdPic(), charset));
			
			// 生成签名
			String sign = getSign(encodeDto);
			// 设置签名
			encodeDto.setSign(sign);
			
			String jsonParam = FastJsonUtil.toJSONString(encodeDto);
			
			logger.info("请求json参数 : " + jsonParam);
			
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("param", jsonParam ));
			
			String respStr = HttpUtil.doPost(inter_url, vps, "utf-8");
			
			logger.info("返回响应json数据 : " + respStr);
			
			AdvertInterRespDto respDto = FastJsonUtil.toBean(respStr, AdvertInterRespDto.class);
			String respSign = getSign(respDto);
			
			if (respSign.equals(respDto.getSign())) {
				if (AdvertInterConstants.SUCCESS_CODE.equals(respDto.getResult())) {
					returnMap.put("success", true);
					returnMap.put("message", "成功");
				} else {
					returnMap.put("success", false);
					returnMap.put("message", "接口返回失败");
				}
			} else {
				logger.error("响应返回签名认证不一致，生成签名：" + respSign + "，返回签名：" + respDto.getSign());
				returnMap.put("success", false);
				returnMap.put("message", "接口响应返回签名不一致");
			}
			
		} catch (Exception e) {
			logger.error("图片生成字节出错：" + e.getMessage(), e);
			returnMap.put("success", false);
			returnMap.put("message", "系统出错");
		}
		
		return returnMap;
	}
	
	private AdvertInterDto encodeDto(AdvertInterDto dto) {
		AdvertInterDto adto = new AdvertInterDto();
		adto.setInfoId(SecurityUtil.URLEncode(dto.getInfoId(), "utf-8"));
		adto.setAdCreativeName(SecurityUtil.URLEncode(dto.getAdCreativeName(), "utf-8"));
		adto.setAdName(SecurityUtil.URLEncode(dto.getAdName(), "utf-8"));
		adto.setAdvertiserName(SecurityUtil.URLEncode(dto.getAdvertiserName(), "utf-8"));
		adto.setAdvertiserUrl(SecurityUtil.URLEncode(dto.getAdvertiserUrl(), "utf-8"));
		adto.setAdxName(SecurityUtil.URLEncode(dto.getAdxName(), "utf-8"));
		adto.setAdxUrl(SecurityUtil.URLEncode(dto.getAdxUrl(), "utf-8"));
		adto.setLandingUrl(SecurityUtil.URLEncode(dto.getLandingUrl(), "utf-8"));
		adto.setLevel(SecurityUtil.URLEncode(dto.getLevel(), "utf-8"));
		adto.setMediaName(SecurityUtil.URLEncode(dto.getMediaName(), "utf-8"));
		adto.setMediaUrl(SecurityUtil.URLEncode(dto.getMediaUrl(), "utf-8"));
		adto.setTerminalType(SecurityUtil.URLEncode(dto.getTerminalType(), "utf-8"));
		adto.setType(SecurityUtil.URLEncode(dto.getType(), "utf-8"));
		adto.setCollectTime(SecurityUtil.URLEncode(dto.getCollectTime(), "utf-8"));
		adto.setCheckTime(SecurityUtil.URLEncode(dto.getCheckTime(), "utf-8"));
		adto.setConfirmTime(SecurityUtil.URLEncode(dto.getConfirmTime(), "utf-8"));
		return adto;
	}
	
	private static String getSign(Object object) {
		List<String> asciiList = new ArrayList<String>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                boolean accessFlag = fields[i].isAccessible();
                fields[i].setAccessible(true);
                String varName = fields[i].getName();
                Object varValue = fields[i].get(object);
                if (!"sign".equals(varName)) {
                	if (!(Objects.equals(varValue, null) || Objects.equals(varValue, ""))) {
						asciiList.add(varName + "=" + varValue);
					}
				}
				fields[i].setAccessible(accessFlag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 进行ASCII字典序排序
        Collections.sort(asciiList);
        
        // 格式化拼接
        String signStr = StringUtils.collectionToDelimitedString(asciiList, "&");
        // 生成sign
        String sign = SecurityUtil.md5StrByKey(signStr, md5_key, charset);
        
        return sign;
    }
}
