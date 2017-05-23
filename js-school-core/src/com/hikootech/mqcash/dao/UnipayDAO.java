package com.hikootech.mqcash.dao;

import java.util.List;

import com.hikootech.mqcash.po.unipay.UnipayCity;
import com.hikootech.mqcash.po.unipay.UnipayPersonalConsumeCategory;
import com.hikootech.mqcash.po.unipay.UnipayPersonalConsumeCity;
import com.hikootech.mqcash.po.unipay.UnipayPersonalMonthConsume;
import com.hikootech.mqcash.po.unipay.UnipayPersonalReport;
import com.hikootech.mqcash.po.unipay.UnipayPersonalScore;
import com.hikootech.mqcash.po.unipay.UnipayPersonalTransCredit;

public interface UnipayDAO {
	/**
	 * 查询银行卡号最近一次数据报告
	 * @param cardNumber
	 * @return
	 */
	public UnipayPersonalReport queryUnipayReport(String cardNumber);

	/**
	 * 查询此次报告的评分
	 * @param reportId
	 * @return
	 */
	public UnipayPersonalScore getUnipayPersonalScore(String busId);
	
	public void insertUnipayReport(UnipayPersonalReport report);
	public void insertUnipayPersonalScore(UnipayPersonalScore score);
	public void insertUnipayConsumeCategory(UnipayPersonalConsumeCategory category);
	public void insertUnipayConsumeCity(UnipayPersonalConsumeCity city);
	public void insertUnipayMonthConsume(UnipayPersonalMonthConsume consume);
	public void insertUnipayTransCredit(UnipayPersonalTransCredit trans);
	
	public List<UnipayPersonalConsumeCategory> listUnipayConsumeCategory(String reportId);
	public List<UnipayPersonalConsumeCity> listUnipayConsumeCity(String reportId);
	public List<UnipayPersonalMonthConsume> listUnipayMonthConsume(String reportId);
	public List<UnipayPersonalTransCredit> listUnipayTransCredit(String reportId);
	
	
	public String getCityCode(String cityName);
	public List<UnipayCity> listCityCode();
}
