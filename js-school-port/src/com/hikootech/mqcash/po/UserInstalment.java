package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月6日
 * 用户分期订单表
 */
public class UserInstalment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 分期订单流水号
	 */
	private String instalmentId;
	/**
	 * 分期配置信息id
	 */
	private String configInstalmentId;
	/**
	 * 分期总金额（本金+服务费）
	 */
	private BigDecimal instalmentAmount;
	/**
	 * 分期数（3期、6期、9期 冗余字段）
	 */
	private Integer instalmentCount;
	/**
	 * 分期每期服务费（冗余字段）
	 */
	private BigDecimal perServiceRate;
	/**
	 * 用户绑定银行卡记录id
	 */
	private String bankCardId;
	/**
	 * 用户申请分期时绑定的手机号
	 */
	private String bindMobile;
	/**
	 * 商户电渠订单金额（分期本金）
	 */
	private BigDecimal partnerOrderAmount;
	/**
	 * 用户id
	 */
	private String userId;
	/** 所在地省份id **/
	private String provinceId;
	/** 所在地城市id **/
	private String cityId;
	/** 所在地地区id **/
	private String areaId;
	/** 家庭住址 **/
	private String homeAddress;
	/** 公司名称 **/
	private String companyName;
	/** 公司地址 **/
	private String companyAddress;
	/**
	 * 用户身份证号
	 */
	private String idCard;
	/**
	 * 逾期分配信息id
	 */
	private String configOverdueId;
	/**
	 * 逾期费率
	 */
	private BigDecimal overdueRate;
	 
	/**
	 * 分期订单状态：0分期下单失败 1分期下单成功 2分期完成
	 */
	private Integer instalmentStatus;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	public UserInstalment() {
		// TODO Auto-generated constructor stub
	}

	public String getInstalmentId() {
		return instalmentId;
	}

	public void setInstalmentId(String instalmentId) {
		this.instalmentId = instalmentId;
	}

	public String getBindMobile() {
		return bindMobile;
	}

	public void setBindMobile(String bindMobile) {
		this.bindMobile = bindMobile;
	}

	public BigDecimal getInstalmentAmount() {
		return instalmentAmount;
	}

	public void setInstalmentAmount(BigDecimal instalmentAmount) {
		this.instalmentAmount = instalmentAmount;
	}

	public BigDecimal getPartnerOrderAmount() {
		return partnerOrderAmount;
	}

	public void setPartnerOrderAmount(BigDecimal partnerOrderAmount) {
		this.partnerOrderAmount = partnerOrderAmount;
	}

	public String getConfigOverdueId() {
		return configOverdueId;
	}

	public void setConfigOverdueId(String configOverdueId) {
		this.configOverdueId = configOverdueId;
	}

	public String getConfigInstalmentId() {
		return configInstalmentId;
	}

	public void setConfigInstalmentId(String configInstalmentId) {
		this.configInstalmentId = configInstalmentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getInstalmentStatus() {
		return instalmentStatus;
	}

	public void setInstalmentStatus(Integer instalmentStatus) {
		this.instalmentStatus = instalmentStatus;
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

	public Integer getInstalmentCount() {
		return instalmentCount;
	}

	public void setInstalmentCount(Integer instalmentCount) {
		this.instalmentCount = instalmentCount;
	}

	public BigDecimal getPerServiceRate() {
		return perServiceRate;
	}

	public void setPerServiceRate(BigDecimal perServiceRate) {
		this.perServiceRate = perServiceRate;
	}


	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public BigDecimal getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
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

}
