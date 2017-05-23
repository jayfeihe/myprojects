package com.hikootech.mqcash.service;

/** 
* @ClassName ProvidePaymentRecService 
* @Description TODO(记录资方打款Servicer) 
* @author HAI DA
* @date Dec 17, 2015 9:55:43 PM 
*  
*/
public interface ProvidePaymentRecService {

	/** 
	* @Title reqProvidePaymentRec 
	* @Description TODO(通知api已打款) 
	* @param 参数列表 设定文件 
	* @return void	返回类型 
	* @throws 
	*/
	public void reqProvidePaymentRec();
	/** 
	 * @Title reqProvidePaymentRec 
	 * @Description TODO(通知api已打款,打款无加密) 
	 * @param 参数列表 设定文件 
	 * @return void	返回类型 
	 * @throws 
	 */
	public void reqProvidePaymentRecT();
}
