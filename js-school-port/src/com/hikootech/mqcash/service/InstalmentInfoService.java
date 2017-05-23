package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.dto.InstalmentInfoDTO;
import com.hikootech.mqcash.dto.TopViewDTO;
import com.hikootech.mqcash.dto.UserPaymentOrderDTO;
import com.hikootech.mqcash.dto.UserPaymentOrderDetailDTO;

public interface InstalmentInfoService {

	
	/**查询账单及产品相关信息（用于立即还款的相关信息查询）**/
	public InstalmentInfoDTO queryInstalmentAndProductInfoByUserId(Map<String, Object> queryMap);
	
	/**根据限定条件查询近期符合条件的账单(用于查询显示顶部标线)**/
	public List<InstalmentInfoDTO> getInstalmentlistByUserId(Map<String,Object> queryMap);
	
	
	/** 
	* getInsMsgByUserId<br/> 
	*  TODO(根据用户id 账单状态获取订单与账单的关联信息，显示账单标签用) 
	* @param queryMap
	* @return 
	* @author zhaohefei
	* @2015年12月14日
	* @return List<InstalmentInfoDTO>	返回类型 
	*/
	public List<InstalmentInfoDTO> getInsMsgByUserId(Map<String,Object> queryMap);
	/**
	 * 获取标线显示位置专用
	 */
	public TopViewDTO getCurTopView(String userId);

	/**  
	 * queryUserPaymentOrderList(查询已还款订单信息)  
	 * @param queryMap
	 * @return   
	 * @create time： Sep 18, 2015 11:42:47 AM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public List<UserPaymentOrderDTO> queryUserPaymentOrderList(Map<String, Object> queryMap);
	
	/**  
	 * queryUserPaymentOrderDetailList(查询已还款订单明细信息)  
	 * @param queryMap
	 * @return   
	 * @create time： Sep 18, 2015 3:10:37 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public List<UserPaymentOrderDetailDTO> queryUserPaymentOrderDetailList(Map<String, Object> queryMap);

	/**  
	 * queryUserPaymentOrderInfo(查询还款信息)  
	 * @param userPaymentOrderDTO
	 * @return   
	 * UserPaymentOrderDTO 
	 * @create time： Sep 18, 2015 4:48:01 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserPaymentOrderDTO queryUserPaymentOrderInfo(UserPaymentOrderDTO userPaymentOrderDTO);

	/**  
	 * queryUserPaymentOrderTotalVO(查询总数)  
	 * @param queryMap
	 * @return   
	 * int 
	 * @create time： Sep 18, 2015 6:39:21 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public int queryUserPaymentOrderTotalVO(Map<String, Object> queryMap);
	
	
	/**
	 * 根据getInstalmentlistByUserId查询得到的volist，将对每一个vo对象拼接成为完整的InstalmentInfoDTO 
	 * @param list 根据getInstalmentlistByUserId查询得到的volist
	 * @param  instalmentStatus 账单状态 （目前是用来区分 全部:Integer.Max_value 待还款:0）
	 * @return listToShow 拼接完毕且筛选状态完毕的vo集合
	 */
	public  List<InstalmentInfoDTO> makeInstalmentInfoList(List<InstalmentInfoDTO> list,Integer instalmentStatus);

	/**
	 * 根据getInstalmentlistByUserId查询得到的volist，将对每一个vo对象拼接成为完整的InstalmentInfoDTO --按照30天计算
	 * @param list 根据getInstalmentlistByUserId查询得到的volist
	 * @param  instalmentStatus 账单状态 （目前是用来区分 全部:Integer.Max_value 待还款:0）
	 * @return listToShow 拼接完毕且筛选状态完毕的vo集合
	 */
	public TopViewDTO makeTopViewInfoByDays(List<InstalmentInfoDTO> list);
	
	/**validataPaymentResult
	* 此方法描述的是：根据还款订单的主键查询还款订单状态是否成功
	* @author: zhaohefei
	* @version: 2015年11月3日 下午2:18:12
	*/
	public Map<String, Object> validataPaymentResult(String paymentOrderId,  String userId) ;
			
}
