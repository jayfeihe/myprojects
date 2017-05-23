package com.tera.rule.model.credit.scorecard;


public class CreditScoreCard {
	public final static Integer TYPE = 1;

	/* 工作年限 */
	private Integer workYear;
	/*
	 * 公司性质 from
	 */
	private Integer corporate;
	/*
	 * 是否已婚 其他 已婚、未婚 1,2
	 */
	private Integer isMarried;
	/* 性别 女0 男1 */
	private Integer sex;
	/* 是否有孩子 无0 有1 */
	private Integer hasChild;
	/*
	 * 房屋状况 集体宿舍 其他
	 */
	private Integer houseStatus;
	/* 返回结果分值 */
//	private Integer result;

	
	/**
	 * 分数
	 */
	private double score;
	
	/**
	 * 分数
	 */
	private String level;
	
	private String appno; //申请单号
	
	private String operator; //操作员
	
	private String orgId; //所属机构
	
//	private List<String> results = new ArrayList<String>();
//
//	public void setResults(List<String> results) {
//		this.results = results;
//	}

	public Integer getWorkYear() {
		return workYear;
	}

	public void setWorkYear(Integer workYear) {
		this.workYear = workYear;
	}

	public Integer getCorporate() {
		return corporate;
	}

	public void setCorporate(Integer corporate) {
		this.corporate = corporate;
	}

	public Integer getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(Integer isMarried) {
		this.isMarried = isMarried;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getHasChild() {
		return hasChild;
	}

	public void setHasChild(Integer hasChild) {
		this.hasChild = hasChild;
	}

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

//	public Integer getResult() {
//		return result;
//	}

//	public void setResult(Integer result) {
//		this.result = result;
//		this.addResult(result + "");
//	}

//	public void addResult(String result) {
//		this.results.add(result);
//	}
//
//	public List<String> getResults() {
//		return results;
//	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getAppno() {
		return appno;
	}

	public void setAppno(String appno) {
		this.appno = appno;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	@Override
	public String toString() {
		return "CreditParam [workYear=" + workYear + ", corporate=" + corporate
				+ ", isMarried=" + isMarried + ", sex=" + sex + ", hasChild="
				+ hasChild + ", houseStatus=" + houseStatus + ", score="
				+ score + ", level=" + level + ", appno=" + appno
				+ ", operator=" + operator + ", orgId=" + orgId + "]";
	}

}
