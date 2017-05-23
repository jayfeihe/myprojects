package com.greenkoo.record.model;

import java.util.Date;

/**
 * 告警记录
 * 
 * @author QYANZE
 *
 */
public class DataRecord {
	
	private String id             ; // 主键id
	private long infoId           ; // 信息的唯一标识       
	private String adName         ; // 广告的中文名称                                      
	private String creativeName   ; // 创意的中文名称                                      
	private String adpicUrl       ; // 广告创意的图片地址                                    
	private int adpicWidth     	  ; // 图片宽度                                         
	private int adpicHeight       ; // 图片高度       
	private int thumbWidth     	  ; // 压缩图片宽度                                         
	private int thumbHeight       ; // 压缩图片高度
	private String landingUrl     ; // 广告落地页URL地址                                   
	private String advertiserUrl  ; // 广告主URL域名地址                                   
	private String advertiserName ; // 广告主中文名称                                      
	private String mediaUrl       ; // 媒体URL域名地址                                    
	private String mediaName      ; // 媒体中文名称                                       
	private int terminalType      ; // 投放的终端类型（1：PC 2：APP  3：WAP）                   
	private String adxUrl         ; // ADX的URL地址                                    
	private String adxName        ; // ADX的中文名称                                     
	private int type              ; // 数据类型（1: 坐席审核后的-疑似违法广告，2: 领导审核后的-确认违法广告）      
	private int level             ; // 违法程度（1：严重违法 2：一般违法 3：轻微违法）                   
	private Date collectTime      ; // 采集时间                                         
	private Date checkTime        ; // 坐席审核时间                                       
	private Date confirmTime      ; // 领导确认时间                                       
	private Date createTime       ; // 创建时间               
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getInfoId() {
		return infoId;
	}
	public void setInfoId(long infoId) {
		this.infoId = infoId;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getCreativeName() {
		return creativeName;
	}
	public void setCreativeName(String creativeName) {
		this.creativeName = creativeName;
	}
	public String getAdpicUrl() {
		return adpicUrl;
	}
	public void setAdpicUrl(String adpicUrl) {
		this.adpicUrl = adpicUrl;
	}
	public int getAdpicWidth() {
		return adpicWidth;
	}
	public void setAdpicWidth(int adpicWidth) {
		this.adpicWidth = adpicWidth;
	}
	public int getAdpicHeight() {
		return adpicHeight;
	}
	public void setAdpicHeight(int adpicHeight) {
		this.adpicHeight = adpicHeight;
	}
	public int getThumbWidth() {
		return thumbWidth;
	}
	public void setThumbWidth(int thumbWidth) {
		this.thumbWidth = thumbWidth;
	}
	public int getThumbHeight() {
		return thumbHeight;
	}
	public void setThumbHeight(int thumbHeight) {
		this.thumbHeight = thumbHeight;
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
	public int getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(int terminalType) {
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	 /**
     * 终端类型-pc
     */
	public static final int TERMINAL_TYPE_PC = 1;
	/**
     * 终端类型-app
     */
	public static final int TERMINAL_TYPE_APP = 2;
	/**
     * 终端类型-wap
     */
	public static final int TERMINAL_TYPE_WAP = 3;
	
	/**
     * 违法程度-严重
     */
	public static final int LEVEL_SEVERE = 1;
	/**
     * 违法程度-一般
     */
	public static final int LEVEL_COMMON = 2;
	/**
     * 违法程度-轻微
     */
	public static final int LEVEL_LIGHT = 3;
	
	/**
     * 数据类型-坐席审核后的-疑似违法广告
     */
	public static final int TYPE_CHECK = 1;
	/**
     * 数据类型-领导审核后的-确认违法广告
     */
	public static final int TYPE_CONFIRM = 2;
}                                 
