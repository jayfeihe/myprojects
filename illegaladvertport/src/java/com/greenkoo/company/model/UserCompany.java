package com.greenkoo.company.model;

import java.util.Date;

/**
 * 广告主-媒体用户
 * 
 * @author QYANZE
 *
 */
public class UserCompany {

	private String companyId    ; // 主键id            
	private String companyName  ; // 公司名称            
	private String companyUrl   ; // 公司域名 
	private int industryId      ; // 行业id
	private String linkName     ; // 联系人姓名
	private String linkPhone    ; // 联系人电话
	private String linkEmail    ; // 联系人邮箱
	private String companyAddr  ; // 公司地址
	private String companyPhone ; // 公司电话
	private String postCode     ; // 公司邮编
	private int type            ; // 类型（1：广告主 2：媒体）  
	private int status          ; // 状态（0:无效 1：有效）   
	private Date createTime     ; // 创建时间
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	public int getIndustryId() {
		return industryId;
	}
	public void setIndustryId(int industryId) {
		this.industryId = industryId;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getLinkEmail() {
		return linkEmail;
	}
	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}
	public String getCompanyAddr() {
		return companyAddr;
	}
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
     * 类型-广告主  
     */
	public static final int TYPE_ADVERTER = 1;
	/**
     * 类型-媒体  
     */
	public static final int TYPE_MEDIA = 2;
}
