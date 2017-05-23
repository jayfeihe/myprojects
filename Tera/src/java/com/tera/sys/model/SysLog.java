package com.tera.sys.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.tera.util.NetUtils;

/**
 * 
 * <br>
 * <b>功能：</b>SyslogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-23 13:09:42<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class SysLog  implements Serializable{
	
		private int id;//ID	private String loginId;//登录名
	private String name;//用户名
	private String orgId;//用户名	private java.sql.Timestamp happendDate = new Timestamp(System.currentTimeMillis());//发生时间	private String ipAddress;//IP地址	private String event;//事件
	private String bizKey;//业务KEY
	private String remark;//操作描述
	
	
	public  SysLog(){
		
	}
	/**
	 * construction
	 * @param request HttpServletRequest
	 * @param user User
	 * @param event String
	 * @return 
	 */
	public  SysLog(HttpServletRequest request, User user, String event) {
		this.ipAddress = NetUtils.getIpAddr(request);
		this.loginId = user.getLoginId();
		this.name = user.getName();
		this.event = event;
	}
	/**
	 * construction
	 * @param ip ip
	 * @param user user
	 * @param event String
	 */
	public SysLog(String ip, User user, String event) {
		this.ipAddress = ip;
		this.loginId = user.getLoginId();
		this.name = user.getName();
		this.event = event;
	}	
	/**
	 * construction
	 * @param ip ip
	 * @param user user
	 * @param event String
	 * @param bizKey String
	 * @param remark String
	 */
	public SysLog(String ip, User user,String orgId, String event, String bizKey, String remark) {
		this.ipAddress = ip;
		this.loginId = user.getLoginId();
		this.name = user.getName();
		this.setOrgId(orgId);
		this.event = event;
		this.bizKey = bizKey;
		this.remark = remark;
	}
	
	/**
	 * construction
	 * @param ip ip
	 * @param user user
	 * @param event String
	 */
	public SysLog(String ip, User user,String orgId, String event) {
		this.ipAddress = ip;
		this.loginId = user.getLoginId();
		this.name = user.getName();
		this.setOrgId(orgId);
		this.event = event;
	}
	
		public int getId() {		return this.id;	}	public void setId(int id) {		this.id=id;	}	public String getLoginId() {		return this.loginId;	}	public void setLoginId(String loginId) {		this.loginId=loginId;	}	public String getName() {		return this.name;	}	public void setName(String name) {		this.name=name;	}	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		
		this.orgId = orgId==null?"":orgId;
	}
	public java.sql.Timestamp getHappendDate() {		return this.happendDate;	}	public void setHappendDate(java.sql.Timestamp happendDate) {		this.happendDate=happendDate;	}	public String getIpAddress() {		return this.ipAddress;	}	public void setIpAddress(String ipAddress) {		this.ipAddress=ipAddress;	}	public String getEvent() {		return this.event;	}	public void setEvent(String event) {		this.event=event;	}
	public String getBizKey() {
		return bizKey;
	}
	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

