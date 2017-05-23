package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.po.unipay.UnipayPersonalReport;
import com.hikootech.mqcash.po.unipay.UnipayPersonalScore;

public interface UnipayService {
	/**
	 * 处理银联个人数据报告：调用银联接口获取数据、计算得分、入库
	 * @param cardNumber
	 * @return 数据报告
	 */
	public UnipayPersonalReport dealUnipayReport(String cardNumber,String idCard,String name, String busId, String cityCode,String reportId) throws Exception;
	
	
	/**
	 * 查询银联数据得分，返回征信结果
	 */
	public boolean creditUnipay(String cardNumber,String idCard,String name,Integer edScore, String busId, String cityCode) throws Exception;
	
	/**
	 * 银联城市字典
	 * @return
	 */
	public Map<String, String> listCityCode();
	/**
	 * 计算银联评分
	 * @return
	 */
	public UnipayPersonalScore calScore(String busId,UnipayPersonalReport report,String cityCode, String idCard, String name)
			throws Exception ;
			
}
