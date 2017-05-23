package com.tera.interfaces.model;

import com.tera.audit.loan.model.Collateral;

/**
 * App抵押接口bean
 * @author QYANZE
 *
 */
public class AppCollateralBean {

	private Collateral collateral;
	private String loginId;
	public Collateral getCollateral() {
		return collateral;
	}
	public void setCollateral(Collateral collateral) {
		this.collateral = collateral;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
