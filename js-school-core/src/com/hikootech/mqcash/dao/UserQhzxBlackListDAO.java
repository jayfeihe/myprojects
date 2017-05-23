package com.hikootech.mqcash.dao;

import com.hikootech.mqcash.po.UserQhzxBlackList;

public interface UserQhzxBlackListDAO {

	public void addQhzxBlackList(UserQhzxBlackList msc);
	public Integer queryQhzxBlackListCountByInfo(UserQhzxBlackList msc);
	public int updateQhzxBlackListByInfo(UserQhzxBlackList msc);
	
}
