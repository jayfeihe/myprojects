package com.greenkoo.dmp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

@Document(collection = "adx_mobile")
public class AdxMobile {

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
	private String brand;
	@JSONField(ordinal=8)
	private String model;
	@JSONField(ordinal=9)
	private String imei;
	@JSONField(ordinal=10)
	private String mac;
	@JSONField(ordinal=11)
	private String idfa;
	@JSONField(ordinal=12)
	private String qkCookie;
	@JSONField(ordinal=13)
	private JSONObject citys;
	@JSONField(ordinal=14)
	private String[] operators; // 0：未知 ，1：中国移动，2：中国联通，3：中国电信
	@JSONField(ordinal=15)
	private JSONObject mediaTypeTag;
	@JSONField(ordinal=16)
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
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getIdfa() {
		return idfa;
	}
	public void setIdfa(String idfa) {
		this.idfa = idfa;
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
	public String[] getOperators() {
		if (operators == null) return operators;
		for (int i = 0; i < operators.length; i++) {
			String o = operators[i];
			if("0".equals(o)) operators[i] = "未知";
			if("1".equals(o)) operators[i] = "中国移动";
			if("2".equals(o)) operators[i] = "中国联通";
			if("3".equals(o)) operators[i] = "中国电信";
		}
		return operators;
	}
	public void setOperators(String[] operators) {
		this.operators = operators;
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
