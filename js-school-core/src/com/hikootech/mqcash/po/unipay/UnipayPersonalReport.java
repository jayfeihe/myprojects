package com.hikootech.mqcash.po.unipay;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.hikootech.mqcash.util.UniPay;

/** 
* @ClassName UnipayPersonalReport 
* @Description TODO(这里用一句话描述这个类的作用) 
* @author 余巍 yuweiqwe@126.com 
* @date 2016年7月18日 下午4:46:54 
*  
*/
public class UnipayPersonalReport implements java.io.Serializable {
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	// field
	/** 查询id **/
	private String id;
	/** 卡号 **/
	private String cardNumber;
	/** 银联查询结果：1查询成功 0查无结果 **/
	private Integer result;
	/** 是否有房产 **/
	private Integer hasHouse;
	/** 是否有车 **/
	private Integer hasCar;
	/** 房产购买时间 **/
	private String housePurTime;
	/** 汽车购买时间 **/
	private String carPurTime;
	/** 房产预估价值 单位 万元 **/
	private BigDecimal houseValue;
	/** 汽车预估价值 单位万元 **/
	private BigDecimal carValue;
	/** 还贷能力 **/
	private String repaymentAbility;
	/** 有无出差消费 **/
	private Integer businessTrip;
	/** 有无婚庆消费 **/
	private Integer marriageConsume;
	/** 是否就业 **/
	private Integer employed;
	/** 有无母婴/教育投资 **/
	private Integer childInvest;
	/** 有无夜消费 **/
	private Integer nightConsume;
	/** 常驻城市 **/
	private String city;
	/** 工作时间消费区域 **/
	private String workRegion;
	/** 非工作时间消费区域 **/
	private String freeRegion;
	/**  **/
	private Date createTime;
	
	public UnipayPersonalReport(){}
	
	public UnipayPersonalReport(JSONObject IndexProperty,JSONObject IndexTransBehavior){
		//IndexProperty.get
		this.hasHouse=IndexProperty.getBooleanValue("HasHouse")?1:0;
		this.hasCar=IndexProperty.getBooleanValue("hasCar")?1:0;
		this.housePurTime=IndexProperty.getString("HousePurTime")==null?"":IndexProperty.getString("HousePurTime");
		this.carPurTime=IndexProperty.getString("CarPurTime")==null?"":IndexProperty.getString("CarPurTime");
		this.houseValue=IndexProperty.getBigDecimal("HouseValue");
		this.carValue=IndexProperty.getBigDecimal("CarValue");
		this.repaymentAbility=IndexProperty.getString("RepaymentAbility");
		
		this.businessTrip=IndexTransBehavior.getBooleanValue("BusinessTrip")?1:0;
		this.marriageConsume=IndexTransBehavior.getBooleanValue("MarriageConsume")?1:0;
		this.employed=IndexTransBehavior.getBooleanValue("Employed")?1:0;
		this.childInvest=IndexTransBehavior.getBooleanValue("ChildInvest")?1:0;
		this.nightConsume=IndexTransBehavior.getBooleanValue("NightConsume")?1:0;
		this.city=IndexTransBehavior.getString("City");
		this.workRegion=IndexTransBehavior.getString("WorkRegion");
		this.freeRegion=IndexTransBehavior.getString("FreeRegion");
		
		this.city=UniPay.replaceBlank(this.city);
		/*
		if(!StringUtils.isEmpty(this.workRegion)){
			String[] wr=this.workRegion.split(" ");
			wr[0]=UniPay.replaceBlank(wr[0]);
			this.workRegion="";
			for(int i=0;i<wr.length;i++){
				this.workRegion+=wr[i]+" ";
			}
			this.workRegion=this.workRegion.trim();
		}
		if(!StringUtils.isEmpty(this.freeRegion)){
			String[] wr=this.freeRegion.split(" ");
			wr[0]=UniPay.replaceBlank(wr[0]);
			this.freeRegion="";
			for(int i=0;i<wr.length;i++){
				this.freeRegion+=wr[i]+" ";
			}
			this.freeRegion=this.freeRegion.trim();
		}
		*/
	}

	// override toString Method
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("'id':'" + this.getId() + "',");
		sb.append("'cardNumber':'" + this.getCardNumber() + "',");
		sb.append("'result':'" + this.getResult() + "',");
		sb.append("'hasHouse':'" + this.getHasHouse() + "',");
		sb.append("'hasCar':'" + this.getHasCar() + "',");
		sb.append("'housePurTime':'" + this.getHousePurTime() + "',");
		sb.append("'carPurTime':'" + this.getCarPurTime() + "',");
		sb.append("'houseValue':'" + this.getHouseValue() + "',");
		sb.append("'carValue':'" + this.getCarValue() + "',");
		sb.append("'repaymentAbility':'" + this.getRepaymentAbility() + "',");
		sb.append("'businessTrip':'" + this.getBusinessTrip() + "',");
		sb.append("'marriageConsume':'" + this.getMarriageConsume() + "',");
		sb.append("'employed':'" + this.getEmployed() + "',");
		sb.append("'childInvest':'" + this.getChildInvest() + "',");
		sb.append("'nightConsume':'" + this.getNightConsume() + "',");
		sb.append("'city':'" + this.getCity() + "',");
		sb.append("'workRegion':'" + this.getWorkRegion() + "',");
		sb.append("'freeRegion':'" + this.getFreeRegion() + "',");
		sb.append("'createTime':'" + this.getCreateTime() + "'");
		sb.append("}");
		return sb.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getHasHouse() {
		return hasHouse;
	}

	public void setHasHouse(Integer hasHouse) {
		this.hasHouse = hasHouse;
	}

	public Integer getHasCar() {
		return hasCar;
	}

	public void setHasCar(Integer hasCar) {
		this.hasCar = hasCar;
	}

	public String getHousePurTime() {
		return housePurTime;
	}

	public void setHousePurTime(String housePurTime) {
		this.housePurTime = housePurTime;
	}

	public String getCarPurTime() {
		return carPurTime;
	}

	public void setCarPurTime(String carPurTime) {
		this.carPurTime = carPurTime;
	}

	public BigDecimal getHouseValue() {
		return houseValue;
	}

	public void setHouseValue(BigDecimal houseValue) {
		this.houseValue = houseValue;
	}

	public BigDecimal getCarValue() {
		return carValue;
	}

	public void setCarValue(BigDecimal carValue) {
		this.carValue = carValue;
	}

	public String getRepaymentAbility() {
		return repaymentAbility;
	}

	public void setRepaymentAbility(String repaymentAbility) {
		this.repaymentAbility = repaymentAbility;
	}

	public Integer getBusinessTrip() {
		return businessTrip;
	}

	public void setBusinessTrip(Integer businessTrip) {
		this.businessTrip = businessTrip;
	}

	public Integer getMarriageConsume() {
		return marriageConsume;
	}

	public void setMarriageConsume(Integer marriageConsume) {
		this.marriageConsume = marriageConsume;
	}

	public Integer getEmployed() {
		return employed;
	}

	public void setEmployed(Integer employed) {
		this.employed = employed;
	}

	public Integer getChildInvest() {
		return childInvest;
	}

	public void setChildInvest(Integer childInvest) {
		this.childInvest = childInvest;
	}

	public Integer getNightConsume() {
		return nightConsume;
	}

	public void setNightConsume(Integer nightConsume) {
		this.nightConsume = nightConsume;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getWorkRegion() {
		return workRegion;
	}

	public void setWorkRegion(String workRegion) {
		this.workRegion = workRegion;
	}

	public String getFreeRegion() {
		return freeRegion;
	}

	public void setFreeRegion(String freeRegion) {
		this.freeRegion = freeRegion;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}