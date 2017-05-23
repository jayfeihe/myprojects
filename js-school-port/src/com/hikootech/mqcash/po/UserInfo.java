package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月5日
 * 用户信息
 */

public class UserInfo implements Serializable,Cloneable {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		UserInfo u=null;
		try {  
            u = (UserInfo) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return u;  
	}

	/**
	 * 用户逻辑逐渐
	 */
	private String userId;
	private String idCard;
	/** 用户真实姓名 **/
	private String name;
	/** 身份证上写的住址 **/
	private String idCardAddress;
	/** 联系电话 **/
	private String contactPhone;
	/** 用户密码，MD5加密，密文形式存储 **/
	private String pwd;
	
	/** 用户密码等级，0低 1中 2高 默认1 **/
	private Integer pwdLevel;
	/** 性别：0女 1男 2保密**/
	private Integer sex;
	/** 生日 yyyy-MM-dd**/
	private String birthday;
	/** 所在地省份id **/
	private String provinceId;
	/** 所在地城市id **/
	private String cityId;
	/** 所在地地区id **/
	private String areaId;
	/** 绑定手机号，用户登录名 **/
	private String bindMobile;
	/** 家庭住址 **/
	private String homeAddress;
	/** 公司名称 **/
	private String companyName;
	/** 公司所在地省份id **/
	private String companyProvinceId;
	/** 公司所在地城市id **/
	private String companyCityId;
	/** 公司所在地地区id **/
	private String companyAreaId;
	/** 公司地址 **/
	private String companyAddress;
	/** 兴趣爱好(以逗号分隔) **/
	private String interesting;
	/** 头像图片地址 **/
	private String headImgUrl;
	/**用户发送短信类型**/
	private Integer userSmType;
	/**用户是否将初始密码修改 0-否;1-是**/
	private Integer pwdModSts;
	/**
	 * 用户状态：0无效（逻辑删除）  1可用
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	
	public UserInfo() {
	}

	public UserInfo(String idCard, String name, String idCardAddress,
			String contactPhone) {
		super();
		this.idCard = idCard;
		this.name = name;
		this.idCardAddress = idCardAddress;
		this.contactPhone = contactPhone;
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardAddress() {
		return idCardAddress;
	}

	public void setIdCardAddress(String idCardAddress) {
		this.idCardAddress = idCardAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getBindMobile() {
		return bindMobile;
	}

	public void setBindMobile(String bindMobile) {
		this.bindMobile = bindMobile;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getInteresting() {
		return interesting;
	}

	public void setInteresting(String interesting) {
		this.interesting = interesting;
	}

	public Integer getPwdLevel() {
		return pwdLevel;
	}

	public void setPwdLevel(Integer pwdLevel) {
		this.pwdLevel = pwdLevel;
	}

	public String getCompanyProvinceId() {
		return companyProvinceId;
	}

	public void setCompanyProvinceId(String companyProvinceId) {
		this.companyProvinceId = companyProvinceId;
	}

	public String getCompanyCityId() {
		return companyCityId;
	}

	public void setCompanyCityId(String companyCityId) {
		this.companyCityId = companyCityId;
	}

	public String getCompanyAreaId() {
		return companyAreaId;
	}

	public void setCompanyAreaId(String companyAreaId) {
		this.companyAreaId = companyAreaId;
	}

	public Integer getUserSmType() {
		return userSmType;
	}

	public void setUserSmType(Integer userSmType) {
		this.userSmType = userSmType;
	}

	public Integer getPwdModSts() {
		return pwdModSts;
	}

	public void setPwdModSts(Integer pwdModSts) {
		this.pwdModSts = pwdModSts;
	}

	
}
