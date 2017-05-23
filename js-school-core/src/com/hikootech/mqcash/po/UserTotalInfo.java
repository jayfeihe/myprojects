package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserTotalInfo implements Serializable{
	
	private static final long serialVersionUID = -6460204900527458084L;
	
	/** id**/
	private String infoId = "";
	/** 身份证号码**/
	private String idCard = "";
	/** 用户真实姓名 **/
	private String name = "";
	/** 身份证上写的住址 **/
	private String idCardAddress = "";
	/** 联系电话 **/
	private String contactPhone = "";
	/** 用户访问网掌厅ip **/
	private String custIp = "";
	/** 身份证对应的客户id（Cust_id） **/
	private String custId = "";
	/** 联系电话对应的产品实例id（Pd_id）**/
	private String pdInstId = "";
	/** 合作伙伴渠道（0-网厅，1-掌厅）**/
	private Integer source ;
	/** 商品在合作伙伴的唯一标识 **/
	private String productId = "";
	/** 商品名称（手机名称） **/
	private String productName = "";
	/** 商品描述 **/
	private String productDesc = "";
	/** 商品价格（总价格=手机价格+号码价格），价格单位为：分 **/
	private BigDecimal productPrice;
	/** 商品数量（手机数量，默认1） **/
	private Integer productCount ;
	/** 默认为手机合约机（0-手机合约机） **/
	private Integer planType ;
	/** 套餐价格，单位为：分 **/
	private BigDecimal planPrice ;
	/** 银行卡绑定唯一标识 **/
	private String bankCardId = "";
	/** 银行卡号 **/
	private String bankCardNumber = "";
	/**银行卡预留手机号 **/
	private String reserveMobile = "";
	/** 第三方绑定流水号 **/
	private String thirdParyBindingId = "";
	/** 银行列表对应关系表id银行代码 **/
	private String relationBankId;
	/** 绑定状态：0未绑定 1绑定 2解绑 3绑定中 4绑定失败 **/
	private Integer bindStatus;
	/** 绑定时间 **/
	private Date bindTime;
	/** 卡类型 **/
	private String cardType = "";
	/** 用户页面选择的分期数：3、6、9、12 **/
	private Integer instalmentNum ;
	/** 电信订单金额（总金额），单位为：分 **/
	private BigDecimal partner_order_price ;
	/** 家庭地址所在省(中文名称) **/
	private String homeProvince = "";
	/** 家庭地址所在市(中文名称) **/
	private String homeCity = "";
	/** 家庭地址所在区(中文名称) **/
	private String homeArea = "";
	/** 家庭地址详细地址 **/
	private String homeAddress = "";
	/** 工作单位名称 **/
	private String companyName = "";
	/** 单位地址所在省(中文名称) **/
	private String companyProvince = "";
	/** 单位地址所在市(中文名称) **/
	private String companyCity = "";
	/**单位地址所在区(中文名称) **/
	private String companyArea = "";
	/** 单位地址详细地址 **/
	private String companyAddress = "";
	/** 电信平台的订单号 **/
	private String partnerOrderId = "";
	/** 用户在电信的订单URL地址 **/
	private String partnerOrderUrl = "";
	/** 用户在电信的下单时间 **/
	private String partnerOrderTime = "";
	/** 用户下单时填写的收货人 **/
	private String receiver = "";
	/** 用户下单时的省份，默认为江苏省(中文名称) **/
	private String receiverProvince = "";
	/** 用户下单时选择的城市(中文名称) **/
	private String receiverCity = "";
	/** 用户下单时选择的区(中文名称) **/
	private String receiverArea = "";
	/** 用户下单时填写的详细地址**/
	private String receiverAddress = "";
	/** 用户下单时填写的电子邮箱**/
	private String email = "";
	/** 用户下单时填写的邮编 **/
	private String postCode = "";
	/** 用户下单时填写的手机号码 **/
	private String receiverMobile = "";
	/** 用户下单时填写的固定电话 **/
	private String receiverTelPhone = "";
	/** 商品的URL地址 **/
	private String productUrl = "";
	/** 创建时间**/
	private Date createTime ;
	/** 修改时间 **/
	private Date updateTime ;
	/** 操作人 **/
	private String operator = "";
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
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
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getPdInstId() {
		return pdInstId;
	}
	public void setPdInstId(String pdInstId) {
		this.pdInstId = pdInstId;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public BigDecimal getPlanPrice() {
		return planPrice;
	}
	public void setPlanPrice(BigDecimal planPrice) {
		this.planPrice = planPrice;
	}
	public String getBankCardId() {
		return bankCardId;
	}
	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}
	public String getBankCardNumber() {
		return bankCardNumber;
	}
	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}
	public String getReserveMobile() {
		return reserveMobile;
	}
	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}
	public String getThirdParyBindingId() {
		return thirdParyBindingId;
	}
	public void setThirdParyBindingId(String thirdParyBindingId) {
		this.thirdParyBindingId = thirdParyBindingId;
	}
	public String getRelationBankId() {
		return relationBankId;
	}
	public void setRelationBankId(String relationBankId) {
		this.relationBankId = relationBankId;
	}
	public Integer getBindStatus() {
		return bindStatus;
	}
	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	public Date getBindTime() {
		return bindTime;
	}
	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Integer getInstalmentNum() {
		return instalmentNum;
	}
	public void setInstalmentNum(Integer instalmentNum) {
		this.instalmentNum = instalmentNum;
	}
	public BigDecimal getPartner_order_price() {
		return partner_order_price;
	}
	public void setPartner_order_price(BigDecimal partner_order_price) {
		this.partner_order_price = partner_order_price;
	}
	public String getHomeProvince() {
		return homeProvince;
	}
	public void setHomeProvince(String homeProvince) {
		this.homeProvince = homeProvince;
	}
	public String getHomeCity() {
		return homeCity;
	}
	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}
	public String getHomeArea() {
		return homeArea;
	}
	public void setHomeArea(String homeArea) {
		this.homeArea = homeArea;
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
	public String getCompanyProvince() {
		return companyProvince;
	}
	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}
	public String getCompanyCity() {
		return companyCity;
	}
	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}
	public String getCompanyArea() {
		return companyArea;
	}
	public void setCompanyArea(String companyArea) {
		this.companyArea = companyArea;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getPartnerOrderId() {
		return partnerOrderId;
	}
	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}
	public String getPartnerOrderUrl() {
		return partnerOrderUrl;
	}
	public void setPartnerOrderUrl(String partnerOrderUrl) {
		this.partnerOrderUrl = partnerOrderUrl;
	}
	public String getPartnerOrderTime() {
		return partnerOrderTime;
	}
	public void setPartnerOrderTime(String partnerOrderTime) {
		this.partnerOrderTime = partnerOrderTime;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiverProvince() {
		return receiverProvince;
	}
	public void setReceiverProvince(String receiverProvince) {
		this.receiverProvince = receiverProvince;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverArea() {
		return receiverArea;
	}
	public void setReceiverArea(String receiverArea) {
		this.receiverArea = receiverArea;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public String getReceiverTelPhone() {
		return receiverTelPhone;
	}
	public void setReceiverTelPhone(String receiverTelPhone) {
		this.receiverTelPhone = receiverTelPhone;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getCustIp() {
		return custIp;
	}
	public void setCustIp(String custIp) {
		this.custIp = custIp;
	}

	
}
