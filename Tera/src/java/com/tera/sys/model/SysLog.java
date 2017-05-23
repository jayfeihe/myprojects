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
	
	
	private String name;//用户名
	private String orgId;//用户名
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
	
	
		return orgId;
	}
	public void setOrgId(String orgId) {
		
		this.orgId = orgId==null?"":orgId;
	}
	public java.sql.Timestamp getHappendDate() {
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
