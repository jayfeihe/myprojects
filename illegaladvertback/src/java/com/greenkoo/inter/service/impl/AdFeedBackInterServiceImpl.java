package com.greenkoo.inter.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.greenkoo.inter.model.AdFeedBackDto;
import com.greenkoo.inter.service.IAdFeedBackInterService;
import com.greenkoo.record.model.AdFeedBack;
import com.greenkoo.record.service.IAdFeedBackService;
import com.greenkoo.utils.ConfigUtil;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.FastJsonUtil;
import com.greenkoo.utils.HttpUtil;
import com.greenkoo.utils.SecurityUtil;

@Service("adFeedBackInterService")
public class AdFeedBackInterServiceImpl implements IAdFeedBackInterService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String inter_url = ConfigUtil.getProperty("inter_url");
	private final String md5_key = ConfigUtil.getProperty("md5_key");
	private final String charset = ConfigUtil.getProperty("charset");
	
	@Autowired
	private IAdFeedBackService adFeedBackService;
	
	@Override
	public void feedBack() {
		// 整改配置天数
		String feedBackDay = ConfigUtil.getProperty("feed_back_day");
		// 整改配置天数前的数据
		Date beforeFeedBackDay = DateUtil.addDate(DateUtil.getCurDate(), Calendar.DATE, Integer.valueOf(feedBackDay).intValue());
		List<AdFeedBack> feedbackDatas = adFeedBackService
				.queryFeedbackDataByTime(DateUtil.formatDate(beforeFeedBackDay, DateUtil.FORMAT_DATE));
		
		if (feedbackDatas != null && feedbackDatas.size() > 0) {
			for (AdFeedBack data : feedbackDatas) {
				String infoId = SecurityUtil.URLEncode(String.valueOf(data.getInfoId()), charset);
				String status = SecurityUtil.URLEncode(String.valueOf(data.getStatus()), charset);
				String remark = SecurityUtil.URLEncode(data.getRemark(), charset);
				// 创建接口传递实体
				AdFeedBackDto dto = new AdFeedBackDto(infoId, status, remark);
				// 获取签名
				String sign = this.getSign(dto);
				dto.setSign(sign);
				
				String jsonParam = FastJsonUtil.toJSONString(dto);
				
				logger.info("请求整改反馈接口参数：" + SecurityUtil.URLDecode(jsonParam, charset));
				
				List<NameValuePair> vps = new ArrayList<NameValuePair>();
				vps.add(new BasicNameValuePair("param", jsonParam ));
				
				try {
					String resp = HttpUtil.doPost(inter_url, vps, charset);
					logger.info("整改反馈接口响应：" + resp);
				} catch (Exception e) {
					logger.error("请求接口出错：" + e.getMessage(), e);
				}
			}
		} else {
			logger.debug("未查到" + feedBackDay + "天前的整改数据");
		}
	}
	
	/**
	 * 获取签名参数
	 * 
	 * @param object
	 * @return
	 */
	private String getSign(Object object) {
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
	
	public static void main(String[] args) {
		Date addDate = DateUtil.addDate(DateUtil.getCurDate(), Calendar.DATE, -Integer.valueOf("3"));
		
		System.out.println(DateUtil.formatDate(addDate, DateUtil.FORMAT_DATE));
	}
}
