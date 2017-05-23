package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.po.UserQhzxBlackList;
import com.hikootech.mqcash.qhzx.BusiDataItemRspQhMSC8004;

public interface UserQhzxBlackListService {

	public void addQhzxBlackList(UserQhzxBlackList msc);
	public int queryQhzxBlackListCountByInfo(UserQhzxBlackList msc);
	public int updateQhzxBlackListByInfo(UserQhzxBlackList msc);
	
		/**saveQhzxMsc8004
		* 此方法描述的是：内部先调用查询数据库内是否包含该客户的信息，如不再则插入，若有则更新
		* @author: zhaohefei
		* @version: 2015年11月11日 下午3:00:06
		*/
		
	public void saveQhzxBlackList(UserQhzxBlackList msc);
	
	public void saveQhzxBlackList(Map<String, UserQhzxBlackList> qhMap) ;
}
