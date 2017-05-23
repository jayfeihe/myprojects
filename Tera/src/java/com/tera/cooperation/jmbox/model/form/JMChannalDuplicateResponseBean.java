package com.tera.cooperation.jmbox.model.form;

/**
 * 渠道查重接口返回响应实体类
 * @author QYANZE
 * @date 2014-04-25
 */
public class JMChannalDuplicateResponseBean {

	private boolean creditloaning; // 是否有在贷项目
	private boolean creditloaned; // 是否有历史项目
	
	public boolean isCreditloaning() {
		return creditloaning;
	}
	public void setCreditloaning(boolean creditLoaning) {
		this.creditloaning = creditLoaning;
	}
	public boolean isCreditloaned() {
		return creditloaned;
	}
	public void setCreditloaned(boolean creditLoaned) {
		this.creditloaned = creditLoaned;
	}
}
