package com.hikootech.mqcash.dao;


import com.hikootech.mqcash.dto.RecordPartnerOrderStatisticDTO;
public interface RecordPartnerOrderStatiticDAO{
	
	/**  
	 * queryRecordPartnerOrderStatistic(查询各渠道来源下单成功的分期单统计记录)  
	 * @param recordPartnerOrderStatistic
	 * @return
	 * @throws Exception   
	 * RecordPartnerOrderStatisticVo 
	 * @create time： Nov 4, 2015 3:50:11 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public RecordPartnerOrderStatisticDTO queryRecordPartnerOrderStatistic(RecordPartnerOrderStatisticDTO recordPartnerOrderStatistic) throws Exception;
	
	/**  
	 * insertRecordPartnerOrderStatistic(初始数据插入，各渠道来源下单成功的分期单统计记录)  
	 * @param recordPartnerOrderStatistic
	 * @return
	 * @throws Exception   
	 * int 
	 * @create time： Nov 4, 2015 3:50:13 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public int insertRecordPartnerOrderStatistic(RecordPartnerOrderStatisticDTO recordPartnerOrderStatistic) throws Exception;

	/**  
	 * queryqueryPartner2Id(配置表：合作伙伴（电渠、商户）)  
	 * @param partnerId
	 * @return   
	 * String 
	 * @create time： Nov 4, 2015 7:32:08 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public String queryPartner2Id(String partnerId);

}
