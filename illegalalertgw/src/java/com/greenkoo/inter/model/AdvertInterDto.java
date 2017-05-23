package com.greenkoo.inter.model;

public class AdvertInterDto {

	private String infoId         ; // 信息的唯一标识       
	private String adName         ; // 广告的中文名称                                      
	private String adCreativeName ; // 创意的中文名称                                      
	private String adPic          ; // 广告创意的图片                      
	private String landingUrl     ; // 广告落地页URL地址                                   
	private String advertiserUrl  ; // 广告主URL域名地址                                   
	private String advertiserName ; // 广告主中文名称                                      
	private String mediaUrl       ; // 媒体URL域名地址                                    
	private String mediaName      ; // 媒体中文名称                                       
	private String terminalType   ; // 投放的终端类型（1：PC 2：APP  3：WAP）                   
	private String adxUrl         ; // ADX的URL地址                                    
	private String adxName        ; // ADX的中文名称                                     
	private String type           ; // 数据类型（1: 坐席审核后的-疑似违法广告，2: 领导审核后的-确认违法广告）      
	private String level          ; // 违法程度（1：严重违法 2：一般违法 3：轻微违法）                   
	private String collectTime    ; // 采集时间                                         
	private String checkTime      ; // 坐席审核时间                                       
	private String confirmTime    ; // 领导确认时间    
	private String sign           ; // 签名
	
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getAdCreativeName() {
		return adCreativeName;
	}
	public void setAdCreativeName(String adCreativeName) {
		this.adCreativeName = adCreativeName;
	}
	public String getAdPic() {
		return adPic;
	}
	public void setAdPic(String adPic) {
		this.adPic = adPic;
	}
	public String getLandingUrl() {
		return landingUrl;
	}
	public void setLandingUrl(String landingUrl) {
		this.landingUrl = landingUrl;
	}
	public String getAdvertiserUrl() {
		return advertiserUrl;
	}
	public void setAdvertiserUrl(String advertiserUrl) {
		this.advertiserUrl = advertiserUrl;
	}
	public String getAdvertiserName() {
		return advertiserName;
	}
	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getAdxUrl() {
		return adxUrl;
	}
	public void setAdxUrl(String adxUrl) {
		this.adxUrl = adxUrl;
	}
	public String getAdxName() {
		return adxName;
	}
	public void setAdxName(String adxName) {
		this.adxName = adxName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
