package com.tera.match.model;

public class MatchInfo {
	private boolean flag; //标识成功或者失败
	private String remark; //备注，失败的原因
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
