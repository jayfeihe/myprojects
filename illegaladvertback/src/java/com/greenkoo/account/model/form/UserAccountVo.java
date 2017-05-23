package com.greenkoo.account.model.form;

import com.greenkoo.account.model.UserAccount;
import com.greenkoo.company.model.UserCompany;

/**
 * 登录存储用户信息bean
 * 
 * @author QYANZE
 *
 */
public class UserAccountVo {

	private UserAccount userAccount;
	
	private UserCompany userCompany;

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public UserCompany getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(UserCompany userCompany) {
		this.userCompany = userCompany;
	}
}
