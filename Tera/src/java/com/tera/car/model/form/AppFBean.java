package com.tera.car.model.form;

import java.util.List;

import com.tera.car.model.CarApp;
import com.tera.car.model.CarContact;
import com.tera.car.model.CarExt;
import com.tera.car.model.CarInfo;


public class AppFBean {

	private CarApp carApp;						// 申请表信息
	private List<CarExt> carExts;					// 重要情况说明   扩展信息
	private List<CarContact> commonContacts;			// 常用联系人
	private List<CarContact> dealingsContacts;		// 经营往来联系人
	private CarInfo carInfo; //车辆信息
	
	private String buttonType;		//按钮事件类型   save 保存,submit 提交,waive 放弃
	
	public CarApp getCarApp() {
		return carApp;
	}
	public void setCarApp(CarApp carApp) {
		this.carApp = carApp;
	}
	public List<CarExt> getCarExts() {
		return carExts;
	}
	public void setCarExts(List<CarExt> carExts) {
		this.carExts = carExts;
	}
	public List<CarContact> getCommonContacts() {
		return commonContacts;
	}
	public void setCommonContacts(List<CarContact> commonContacts) {
		this.commonContacts = commonContacts;
	}
	public List<CarContact> getDealingsContacts() {
		return dealingsContacts;
	}
	public void setDealingsContacts(List<CarContact> dealingsContacts) {
		this.dealingsContacts = dealingsContacts;
	}
	public String getButtonType() {
		return buttonType;
	}
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	public CarInfo getCarInfo() {
		return carInfo;
	}
	public void setCarInfo(CarInfo carInfo) {
		this.carInfo = carInfo;
	}
}
