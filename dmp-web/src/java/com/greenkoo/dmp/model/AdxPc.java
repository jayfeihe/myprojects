package com.greenkoo.dmp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

@Document(collection = "adx_pc")
public class AdxPc {

	@Id
	@JSONField(serialize=false)
	private String id;
	@JSONField(ordinal=1)
	private String device_id;
	@JSONField(ordinal=2)
	private String ua;
	@JSONField(ordinal=3)
	private String os;
	@JSONField(ordinal=4)
	private String os_version;
	@JSONField(ordinal=5)
	private String browser;
	@JSONField(ordinal=6)
	private String browser_version;
	@JSONField(ordinal=7)
	private String qkCookie;
	@JSONField(ordinal=8)
	private JSONObject citys;
	@JSONField(ordinal=9)
	private JSONObject mediaTypeTag;
	@JSONField(ordinal=10)
	private JSONObject interestTag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getOs_version() {
		return os_version;
	}
	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getBrowser_version() {
		return browser_version;
	}
	public void setBrowser_version(String browser_version) {
		this.browser_version = browser_version;
	}
	public String getQkCookie() {
		return qkCookie;
	}
	public void setQkCookie(String qkCookie) {
		this.qkCookie = qkCookie;
	}
	public JSONObject getCitys() {
		return citys;
	}
	public void setCitys(JSONObject citys) {
		this.citys = citys;
	}
	public JSONObject getMediaTypeTag() {
		return mediaTypeTag;
	}
	public void setMediaTypeTag(JSONObject mediaTypeTag) {
		this.mediaTypeTag = mediaTypeTag;
	}
	public JSONObject getInterestTag() {
		return interestTag;
	}
	public void setInterestTag(JSONObject interestTag) {
		this.interestTag = interestTag;
	}
}
