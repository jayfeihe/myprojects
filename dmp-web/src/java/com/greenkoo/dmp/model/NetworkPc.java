package com.greenkoo.dmp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

@Document(collection = "network_pc")
public class NetworkPc {
	
	@Id
	@JSONField(serialize=false)
	private String id;
	@JSONField(ordinal=1)
	private String device_id;
	@JSONField(ordinal=2)
	private String adid;
	@JSONField(ordinal=3)
	private String ua;
	@JSONField(ordinal=4)
	private String os;
	@JSONField(ordinal=5)
	private String os_version;
	@JSONField(ordinal=6)
	private String browser;
	@JSONField(ordinal=7)
	private String browser_version;
	@JSONField(ordinal=8)
	private String brand;
	@JSONField(ordinal=9)
	private String model;
	@JSONField(ordinal=10)
	private String imei;
	@JSONField(ordinal=11)
	private String idfa;
	@JSONField(ordinal=12)
	private String mac;
	@JSONField(ordinal=13)
	private String network;
	@JSONField(ordinal=14)
	private JSONObject citys;
	@JSONField(ordinal=15)
	private JSONObject interestTag;
	@JSONField(ordinal=16)
	private JSONObject accountTag;
	@JSONField(ordinal=17)
	private JSONObject keywords;
	@JSONField(ordinal=18)
	private String adxCookie;
	@JSONField(ordinal=19)
	private String otherCookie;
	
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
	public String getAdid() {
		return adid;
	}
	public void setAdid(String adid) {
		this.adid = adid;
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
	public String getNetwork() {
		return "0".equals(network)? "电信" : "";
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getIdfa() {
		return idfa;
	}
	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public JSONObject getInterestTag() {
		return interestTag;
	}
	public void setInterestTag(JSONObject interestTag) {
		this.interestTag = interestTag;
	}
	public JSONObject getCitys() {
		return citys;
	}
	public void setCitys(JSONObject citys) {
		this.citys = citys;
	}
	public JSONObject getAccountTag() {
		return accountTag;
	}
	public void setAccountTag(JSONObject accountTag) {
		this.accountTag = accountTag;
	}
	public JSONObject getKeywords() {
		return keywords;
	}
	public void setKeywords(JSONObject keywords) {
		this.keywords = keywords;
	}
	public String getAdxCookie() {
		return adxCookie;
	}
	public void setAdxCookie(String adxCookie) {
		this.adxCookie = adxCookie;
	}
	public String getOtherCookie() {
		return otherCookie;
	}
	public void setOtherCookie(String otherCookie) {
		this.otherCookie = otherCookie;
	}
}
