package com.hikootech.mqcash.dao;

import com.hikootech.mqcash.po.UserQhzxLoan;

public interface UserQhzxLoanDAO {

	
		/**saveQhzxMsc8004
		* 此方法描述的是：内部先调用查询数据库内是否包含该客户的信息，如不再则插入，若有则更新
		* @author: zhanghaida
		* @version: 2015年11月11日 下午3:00:06
		*/
		
	public void saveQhzxLoaneeList(UserQhzxLoan msc);
	
}
