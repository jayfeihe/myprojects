package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.po.UserQhzxCredit;

public interface UserQhzxCreditService {

	public void addQhzxCredit(UserQhzxCredit msc);
	public int queryQhzxCreditByInfo(UserQhzxCredit msc);
	public int updateQhzxCreditByInfo(UserQhzxCredit msc);
	
		/**saveQhzxMsc8005
		* 此方法描述的是：内部先调用查询数据库内是否包含该客户的信息，如不再则插入，若有则更新
		* @author: zhaohefei
		* @version: 2015年11月11日 下午3:00:06
		*/
		
	public void saveQhzxCreditList(UserQhzxCredit msc);
	
	public void saveQhzxCreditList(Map<String, UserQhzxCredit> qhMap);
}
