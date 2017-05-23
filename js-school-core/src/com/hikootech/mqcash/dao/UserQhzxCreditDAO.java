package com.hikootech.mqcash.dao;

import java.util.List;

import com.hikootech.mqcash.po.UserQhzxCredit;

public interface UserQhzxCreditDAO {

	public void addQhzxCredit(UserQhzxCredit msc);
	public Integer queryQhzxCreditByInfo(UserQhzxCredit msc);
	public int updateQhzxCreditByInfo(UserQhzxCredit msc);
	public List<UserQhzxCredit> queryQhzxCredit(UserQhzxCredit msc);
}
