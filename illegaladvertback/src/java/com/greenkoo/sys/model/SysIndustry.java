package com.greenkoo.sys.model;

/**
 * 行业实体
 * 
 * @author QYANZE
 *
 */
public class SysIndustry {

	private int industryId       ; // 行业id
	private String industryName ; // 行业名称
	
	public int getIndustryId() {
		return industryId;
	}
	public void setIndustryId(int industryId) {
		this.industryId = industryId;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
}
