package com.greenkoo.record.model;

import java.util.Date;

/**
 * 广告图片下载记录
 * 
 * @author QYANZE
 *
 */
public class AdPic {

	private String dataId;     // 告警记录ID
	private String picUrl;     // 图片URL
	private int status;        // 状态（0：初始化 1：已下载 2：下载失败）
	private int downloadTimes; // 下载次数
	private Date createTime;   // 下载时间
	
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDownloadTimes() {
		return downloadTimes;
	}
	public void setDownloadTimes(int downloadTimes) {
		this.downloadTimes = downloadTimes;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**状态-初始化*/
	public static final int STATUS_INIT = 0;
	/**状态-已下载*/
	public static final int STATUS_SUCCESS = 1;
	/**状态-下载失败*/
	public static final int STATUS_FAIL = 2;
}
