package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.ProvidePaymentRecord;
import com.hikootech.mqcash.po.UserInstalment;

/** 
* @ClassName ProvidePaymentRecDAO 
* @Description TODO(记录资方打款DAO) 
* @author HAI DA
* @date Dec 17, 2015 9:55:43 PM 
*  
*/
public interface ProvidePaymentRecDAO {

	/** 
	* @Title reqProvidePaymentRec 
	* @Description TODO(请求api进行打款处理) 
	* @param 参数列表 设定文件 
	* @return List<ProvidePaymentRecord>	返回类型 
	* @throws 
	*/
	public List<ProvidePaymentRecord> queryProvidePaymentRec(ProvidePaymentRecord ppr);

	/** 
	* @Title queryProviderNameById 
	* @Description TODO(通过资方id查询资方名称) 
	* @param 参数列表
	* @param providerId
	* @return 设定文件 
	* @return String	返回类型 
	* @throws 
	*/
	public String queryProviderNameById(String providerId);

	/** 
	* @Title updateProvidePaymentRecord 
	* @Description TODO(通知api后将返回对应的结果插入到表中) 
	* @param 参数列表
	* @param paymentResult 设定文件 
	* @return int	返回类型 
	* @throws 
	*/
	public int updateProvidePaymentRecord(ProvidePaymentRecord paymentResult);

	/** 
	* @Title updateProvideStatus 
	* @Description TODO(修改分期订单的资方状态) 
	* @param 参数列表 
	* @param paramMap 
	* @return 返回类型 void	
	*/
	public void updateProvideStatus(Map<String, Object> paramMap);
}
