package com.greenkoo.inter.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.greenkoo.constants.CommonConstants;
import com.greenkoo.exception.IllegalAdvertException;
import com.greenkoo.inter.constants.AdvertInterConstants;
import com.greenkoo.inter.model.AdvertInterRespDto;
import com.greenkoo.inter.model.AdvertInterDto;
import com.greenkoo.inter.service.IAdvertInterService;
import com.greenkoo.record.model.AdFeedBack;
import com.greenkoo.record.model.AdPic;
import com.greenkoo.record.model.DataRecord;
import com.greenkoo.record.service.IAdFeedBackService;
import com.greenkoo.record.service.IAdPicService;
import com.greenkoo.record.service.IDataRecordService;
import com.greenkoo.utils.ConfigUtil;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.FastJsonUtil;
import com.greenkoo.utils.GenerateKeyUtil;
import com.greenkoo.utils.SecurityUtil;
import com.greenkoo.utils.StringUtil;

@Service("advertInterService")
public class AdvertInterServiceImpl implements IAdvertInterService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String md5_key = ConfigUtil.getProperty("md5_key");
	private final String charset = ConfigUtil.getProperty("charset");
	
	private final String default_adpic = ConfigUtil.getProperty("default_adpic");
	private final String default_width = ConfigUtil.getProperty("default_width");
	private final String default_height = ConfigUtil.getProperty("default_height");
	
	@Autowired
	private IDataRecordService dataRecordService;
	@Autowired
	private IAdPicService adPicService;
	@Autowired
	private IAdFeedBackService adFeedBackService;
	
	@Transactional
	@Override
	public String busProcess(String param) {
		AdvertInterDto dto = FastJsonUtil.toBean(param, AdvertInterDto.class);
		
		try {
			this.validateParam(dto);
		} catch (Exception e) {
			logger.error("参数校验异常：，" + e.getMessage(), e);
			return this.getResultJson(AdvertInterConstants.ERROR_CODE_EMPTY_PARAM);
		}
		
		String sign =  this.getSign(dto);
		
		if (sign.equals(dto.getSign())) {
			// 进行数据解析并存储
			try {
				// 创意数据保存
				DataRecord record = this.createDataRecord(dto);
				record.setAdpicUrl(default_adpic + ".jpg") ;
				record.setAdpicWidth(Integer.parseInt(default_width));
				record.setAdpicHeight(Integer.parseInt(default_height));
				record.setThumbWidth(Integer.parseInt(default_width));
				record.setThumbHeight(Integer.parseInt(default_height));
				this.dataRecordService.add(record);
				
				// 初始化图片下载信息
				AdPic pic = new AdPic();
				pic.setDataId(record.getId());
				pic.setPicUrl(SecurityUtil.URLDecode(dto.getAdPic(), charset));
				pic.setStatus(AdPic.STATUS_INIT);
				pic.setDownloadTimes(0);
				pic.setCreateTime(DateUtil.getCurDate());
				this.adPicService.add(pic );
				
				// 广告主反馈信息
				AdFeedBack advertiser = new AdFeedBack();
				advertiser.setInfoId(record.getInfoId());
				advertiser.setRoleType(AdFeedBack.ROLE_TYPE_ADVERTISER);
				advertiser.setRoleUrl(record.getAdvertiserUrl());
				advertiser.setStatus(AdFeedBack.STATUS_UNKNOW);
				advertiser.setCreateTime(DateUtil.getCurDate());
				this.adFeedBackService.add(advertiser);
				
				// 媒体反馈信息
				AdFeedBack media = new AdFeedBack();
				media.setInfoId(record.getInfoId());
				media.setRoleType(AdFeedBack.ROLE_TYPE_MEDIA);
				media.setRoleUrl(record.getMediaUrl());
				media.setStatus(AdFeedBack.STATUS_UNKNOW);
				media.setCreateTime(DateUtil.getCurDate());
				this.adFeedBackService.add(media);
				
				return this.getResultJson(AdvertInterConstants.SUCCESS_CODE);
			} catch (ParseException e) {
				logger.error("解析日期异常，" + e.getMessage(), e);
				return this.getResultJson(AdvertInterConstants.ERROR_CODE_SYS);
			} catch (IOException e) {
				logger.error("读取图片错误，" + e.getMessage(), e);
				return this.getResultJson(AdvertInterConstants.ERROR_CODE_SYS);
			} catch (Exception e) {
				logger.error("保存数据失败，" + e.getMessage(), e);
				return this.getResultJson(AdvertInterConstants.ERROR_CODE_SYS);
			}
		} else {
			logger.error("验证签名不一致，传递签名：" + dto.getSign() + ",生成签名：" + sign);
			return this.getResultJson(AdvertInterConstants.ERROR_CODE_NE_SIGN);
		}
	}

	/**
	 * 参数校验
	 * 
	 * @param dto
	 */
	private void validateParam(AdvertInterDto dto) throws Exception {
		Field[] fields = dto.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
        	try {
				boolean accessFlag = fields[i].isAccessible();
				fields[i].setAccessible(true);
				String varName = fields[i].getName();
				if (!"adPic".equals(varName) 
						&& !"adxUrl".equals(varName) 
						&& !"adxName".equals(varName)
						&& !"adCreativeName".equals(varName)
						&& !"advertiserName".equals(varName)) {
					Object varValue = fields[i].get(dto);
					if (StringUtil.isEmpty(varValue) || StringUtil.isEmpty(varValue.toString())) {
						throw new IllegalAdvertException("參數【" + varName + "】为空");
					}
				}
				fields[i].setAccessible(accessFlag);
			} catch (Exception e) {
				throw e;
			}
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

	/**
	 * 封装存储创意数据对象
	 * 
	 * @param dto
	 * @return
	 * @throws ParseException ,IOException
	 */
	private DataRecord createDataRecord(AdvertInterDto dto) throws ParseException,IOException {
		DataRecord record = new DataRecord();
		// 设置接口传递数据
		record.setInfoId(Long.parseLong(SecurityUtil.URLDecode(dto.getInfoId(), charset)));
		record.setAdName(SecurityUtil.URLDecode(dto.getAdName(), charset));
		record.setAdvertiserName(SecurityUtil.URLDecode(dto.getAdvertiserName(), charset));
		record.setAdvertiserUrl(SecurityUtil.URLDecode(dto.getAdvertiserUrl(), charset));
		record.setAdxName(SecurityUtil.URLDecode(dto.getAdxName(), charset));
		record.setAdxUrl(SecurityUtil.URLDecode(dto.getAdxUrl(), charset));
		record.setCreativeName(SecurityUtil.URLDecode(dto.getAdCreativeName(), charset));
		record.setLandingUrl(SecurityUtil.URLDecode(dto.getLandingUrl(), charset));
		record.setLevel(Integer.parseInt(SecurityUtil.URLDecode(dto.getLevel(), charset)));
		record.setMediaName(SecurityUtil.URLDecode(dto.getMediaName(), charset));
		record.setMediaUrl(SecurityUtil.URLDecode(dto.getMediaUrl(), charset));
		record.setTerminalType(Integer.parseInt(SecurityUtil.URLDecode(dto.getTerminalType(), charset)));
		record.setType(Integer.parseInt(SecurityUtil.URLDecode(dto.getType(), charset)));
		try {
			if (StringUtil.isNotEmpty(dto.getCollectTime())) {
				String decodeCollectTime = SecurityUtil.URLDecode(dto.getCollectTime(), charset);
				record.setCollectTime(DateUtil.transStrToDate(decodeCollectTime, DateUtil.FORMAT_SS));
			}
			
			if (StringUtil.isNotEmpty(dto.getCheckTime())) {
				String decodeCheckTime = SecurityUtil.URLDecode(dto.getCheckTime(), charset);
				record.setCheckTime(DateUtil.transStrToDate(decodeCheckTime, DateUtil.FORMAT_SS));
			}
			if (StringUtil.isNotEmpty(dto.getConfirmTime())) {
				String decodeConfirmTime = SecurityUtil.URLDecode(dto.getConfirmTime(), charset);
				record.setConfirmTime(DateUtil.transStrToDate(decodeConfirmTime, DateUtil.FORMAT_SS));
			}
			// 设置自定义的字段数据
			record.setId(GenerateKeyUtil.getId(CommonConstants.PREFIX_DATA_RECORD, ConfigUtil.getProperty("db_id")));
			record.setCreateTime(new Date());
			
		} catch (ParseException e) {
			throw e;
		}
		
		return record;
	}

	/**
	 * 获取返回信息
	 * 
	 * @param resultCode
	 * @return
	 */
	private String getResultJson(String resultCode) {
		AdvertInterRespDto respDto = new AdvertInterRespDto(SecurityUtil.URLEncode(resultCode, charset));
		String respSign = this.getSign(respDto);
		respDto.setSign(respSign);
		String resultJson = FastJsonUtil.toJSONString(respDto);
		logger.info("返回响应json数据：" + resultJson);
		return resultJson;
	}
}
