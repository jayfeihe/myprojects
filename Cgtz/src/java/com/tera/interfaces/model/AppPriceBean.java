package com.tera.interfaces.model;

import java.util.List;

import com.tera.audit.loan.model.Collateral;
import com.tera.audit.risk.model.CollateralPriceAudit;

/**
 * App核价接口
 * @author QYANZE
 *
 */
public class AppPriceBean {

	private Collateral collateral; // 抵押信息
	
	private List<CollateralPriceAudit> priceHistoryList; // 核价历史
	
	private int collateralId; // 抵押id 
	
	private String auditPriceState; //最新核价结果    0:未处理,1:相符,2:不相符
	
	private String auditPriceRemark; //核价说明
	
	private double latestPrice; //核价金额
	
	private String loginId; // 登录人

	public Collateral getCollateral() {
		return collateral;
	}

	public void setCollateral(Collateral collateral) {
		this.collateral = collateral;
	}

	public List<CollateralPriceAudit> getPriceHistoryList() {
		return priceHistoryList;
	}

	public void setPriceHistoryList(List<CollateralPriceAudit> priceHistoryList) {
		this.priceHistoryList = priceHistoryList;
	}

	public int getCollateralId() {
		return collateralId;
	}

	public void setCollateralId(int collateralId) {
		this.collateralId = collateralId;
	}

	public String getAuditPriceState() {
		return auditPriceState;
	}

	public void setAuditPriceState(String auditPriceState) {
		this.auditPriceState = auditPriceState;
	}

	public String getAuditPriceRemark() {
		return auditPriceRemark;
	}

	public void setAuditPriceRemark(String auditPriceRemark) {
		this.auditPriceRemark = auditPriceRemark;
	}

	public double getLatestPrice() {
		return latestPrice;
	}

	public void setLatestPrice(double latestPrice) {
		this.latestPrice = latestPrice;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
