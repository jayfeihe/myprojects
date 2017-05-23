package com.greenkoo.record.model.form;

public class RelatedBean {

	private String relatedName; //名称
	
	private String adCount; // 违法广告数
	
	private String creativeCount; // 违法创意数

	public String getRelatedName() {
		return relatedName;
	}

	public void setRelatedName(String relatedName) {
		this.relatedName = relatedName;
	}

	public String getAdCount() {
		return adCount;
	}

	public void setAdCount(String adCount) {
		this.adCount = adCount;
	}

	public String getCreativeCount() {
		return creativeCount;
	}

	public void setCreativeCount(String creativeCount) {
		this.creativeCount = creativeCount;
	}
}
