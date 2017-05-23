package com.tera.house.model.form;

import java.util.List;

import com.tera.house.model.HouseApp;
import com.tera.house.model.HouseContact;
import com.tera.house.model.HouseExt;
import com.tera.house.model.HouseInfo;


public class AppFBean {

	private HouseApp houseApp;						// 申请表信息
	private List<HouseExt> houseExts;					// 重要情况说明   扩展信息
	private List<HouseContact> commonContacts;			// 常用联系人
	private List<HouseContact> dealingsContacts;		// 经营往来联系人
	private HouseInfo houseInfo; //车辆信息
	
	private String buttonType;		//按钮事件类型   save 保存,submit 提交,waive 放弃

	public HouseApp getHouseApp() {
		return houseApp;
	}

	public void setHouseApp(HouseApp houseApp) {
		this.houseApp = houseApp;
	}

	public List<HouseExt> getHouseExts() {
		return houseExts;
	}

	public void setHouseExts(List<HouseExt> houseExts) {
		this.houseExts = houseExts;
	}

	public List<HouseContact> getCommonContacts() {
		return commonContacts;
	}

	public void setCommonContacts(List<HouseContact> commonContacts) {
		this.commonContacts = commonContacts;
	}

	public List<HouseContact> getDealingsContacts() {
		return dealingsContacts;
	}

	public void setDealingsContacts(List<HouseContact> dealingsContacts) {
		this.dealingsContacts = dealingsContacts;
	}

	public HouseInfo getHouseInfo() {
		return houseInfo;
	}

	public void setHouseInfo(HouseInfo houseInfo) {
		this.houseInfo = houseInfo;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
}
