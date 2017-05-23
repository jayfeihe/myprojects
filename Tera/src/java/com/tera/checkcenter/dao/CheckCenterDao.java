package com.tera.checkcenter.dao;

import java.util.List;
import java.util.Map;

import com.tera.checkcenter.model.CheckCenter;
import com.tera.credit.model.CreditApp;

/**
 * 
 * 资金流转查看
 * <b>功能：</b>CreditAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 15:25:28<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CheckCenterDao {
	//进账总额
	public double queryTotalReceipts(Map<String, Object> map);
	//出借人资金锁定金额
	public double queryLendLockAmount(Map<String, Object> map);
	//撮合占用金额
	public double queryMatchOccupyAmount(Map<String, Object> map);
	//未撮合金额
	public double queryDisMatchAmount(Map<String, Object> map);

}
