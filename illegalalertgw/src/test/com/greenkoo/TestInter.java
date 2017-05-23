package com.greenkoo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.StringUtils;

import com.greenkoo.inter.model.AdvertInterDto;
import com.greenkoo.utils.FastJsonUtil;
import com.greenkoo.utils.HttpUtil;
import com.greenkoo.utils.SecurityUtil;

public class TestInter {

	public static void main(String[] args) throws Exception {
		
		AdvertInterDto ads = new AdvertInterDto();
		ads.setAdCreativeName(SecurityUtil.URLEncode("test", "utf-8"));
    	ads.setAdName(SecurityUtil.URLEncode("testadName","utf-8"));
    	ads.setAdvertiserName(SecurityUtil.URLEncode("联盟名称","utf-8"));
    	ads.setAdvertiserUrl(SecurityUtil.URLEncode("http://www.baidu.com","utf-8"));
    	ads.setAdxName(SecurityUtil.URLEncode("名称Name","utf-8"));
    	ads.setAdxUrl(SecurityUtil.URLEncode("http://baidu.com","utf-8"));
    	ads.setCheckTime(SecurityUtil.URLEncode("2016-10-09", "utf-8"));
    	ads.setCollectTime(SecurityUtil.URLEncode("2016-10-09", "utf-8"));
    	ads.setConfirmTime(SecurityUtil.URLEncode("2016-10-09", "utf-8"));
    	ads.setLandingUrl(SecurityUtil.URLEncode("http://baidu.com","utf-8"));
    	ads.setLevel(SecurityUtil.URLEncode("1","utf-8"));
    	ads.setMediaName(SecurityUtil.URLEncode("淘宝","utf-8"));
    	ads.setMediaUrl(SecurityUtil.URLEncode("http://taobao.com","utf-8"));
    	ads.setTerminalType(SecurityUtil.URLEncode("1","utf-8"));
    	ads.setType(SecurityUtil.URLEncode("1","utf-8"));
    	ads.setAdPic("");
    	ads.setSign(getSign(ads));
    	
    	String jsonParam = FastJsonUtil.toJSONString(ads);
		System.out.println("sign:"+ads.getSign());
    	System.out.println("请求json参数 : " + jsonParam);
		
		List<NameValuePair> vps = new ArrayList<NameValuePair>();
		vps.add(new BasicNameValuePair("param", jsonParam ));
		
		String respStr = HttpUtil.doPost("http://qiaoyanze.vicp.net/illegalAdvert", vps, "utf-8");
		
		System.out.println("返回响应json数据 : " + respStr);
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
        System.out.println("signStr:"+signStr);
        // 生成sign
        String sign = SecurityUtil.md5StrByKey(signStr, "123456", "UTF-8");
        
        return sign;
    }
}
