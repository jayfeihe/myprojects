package com.greenkoo.utils;

import java.util.Date;
import java.util.Hashtable;

import com.greenkoo.constants.CommonConstants;



public class GenerateKeyUtil {
	
	public static long seed = 0;
	
	/** 默认id前缀 */
	private final static String DEFAULT = "DEFAULT";
	/** 默认id最追序列号长度 */
	private final static String DEFAULT_NUMBERS = "5";
	private static Hashtable<String, Object> idHashtable;
	
	static {
		idHashtable = new Hashtable<String, Object>();
		
		/** 告警记录id*/
		SeqCreateVo DRVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.PREFIX_DATA_RECORD);
		idHashtable.put(CommonConstants.PREFIX_DATA_RECORD, DRVo);
	}
	
	
	/**
	 * 生成序号
	 * @param prefix 前缀
	 * @param numbers 序号位数(数列号总长度)
	 * @return
	 */
	public synchronized static String getSeqId(String prefix, String numbers) {
		if (StringUtil.isEmpty(prefix)) {
			prefix = DEFAULT;// 开头字母为空时，默认使用
		}
		String id = "";
		SeqCreateVo seqVo = (SeqCreateVo) idHashtable.get(prefix);
		String curDate = DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL);
		if (curDate.equals(seqVo.getCurDate())) {
			long num = seqVo.getNum() + 1;
			if (String.valueOf(num).length() > Integer.valueOf(numbers)) {
				num = 1;
			}
			seqVo.setNum(num);
			id = String.format("%0" + numbers + "d", num);
		} else {
			seqVo.setNum(1);
			seqVo.setCurDate(curDate);
			id = String.format("%0" + numbers + "d", seqVo.getNum());
		}
		idHashtable.put(prefix, seqVo);
		return id;
	}
	
	public synchronized static String getSeqId(String prefix) {
		return GenerateKeyUtil.getSeqId(prefix, DEFAULT_NUMBERS);
	}
	
	
	/**
	 * 获取id
	 */
	public synchronized static String getId(String prefix, String dbNum) {
		String strCurDate = DateUtil.formatDate(new Date(), DateUtil.FORMAT_SS_NO_SYMBOL);
		// String dbNum = dbRouteInfo.getDbNumber();
		// 生成5位随机数
		String rd = GenerateKeyUtil.getSeqId(prefix);

		String id = prefix + strCurDate + dbNum + rd;

		return id;
	}
	
	public static String generateMdcKey() {
		String ramdomNum = get8Num();
		return "[" + ramdomNum + "]";
	}

	private synchronized static String get8Num() {
		if (seed > 99999999) {
			seed = 0;
		}
		seed = seed + 1;

		return String.format("%08d", seed);
	}
	
	public static void main(String[] args) {
		System.out.println(getId(CommonConstants.PREFIX_DATA_RECORD, "01"));
	}
	
}

class SeqCreateVo { 
	private long  num = 1;//订单序号
	private String curDate;//当前日期
	private String serviceType;//业务类型
	
	
	public SeqCreateVo(long num,String curDate,String serviceType)
	{
		this.num =num;
		this.curDate = curDate;
		this.serviceType = serviceType;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getCurDate() {
		return curDate;
	}
	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
}
