package com.tera.interfaces.model;

/**
 * 抵押物信息
 * @author Jesse
 *
 */
public class PushPledge {

	private String type;//质押抵押类型  1质押，２抵押，３其他，４担保
	private String category;//抵押物分类 2车，３房，４红木，５海鲜
	private String name;//抵押物名称  车　房　红木　海鲜
	private String quantity;//质押抵押数量  30　　　　　　　红木　海鲜
	private String valuations;//市场估值 1000　　车　房　红木　海鲜
	private String built_up;//建筑面积  135,单位平方米　房
	private String location;//抵押物所在位置 地点　　　　　　房
	private String register_date;//车辆登记时间  日期格式　2016-01-12　车
	private String mileage;// 车辆行驶公里数   21021,单位KM　　　　　车
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getValuations() {
		return valuations;
	}
	public void setValuations(String valuations) {
		this.valuations = valuations;
	}
	public String getBuilt_up() {
		return built_up;
	}
	public void setBuilt_up(String builtUp) {
		built_up = builtUp;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String registerDate) {
		register_date = registerDate;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	

}
