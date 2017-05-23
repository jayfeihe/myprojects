package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.po.UserHdBxdjgcxjl;
import com.hikootech.mqcash.po.UserHdDkcfsq;
import com.hikootech.mqcash.po.UserHdGrbzxggcx;
import com.hikootech.mqcash.po.UserHdRequest;
import com.hikootech.mqcash.po.UserHdShanyinSyhmd;
import com.hikootech.mqcash.po.UserHdSxbzxrcx;
import com.hikootech.mqcash.vo.HaoDaiVo;

public interface HaoDaiService {

	/**  
	 * insertHaoDaiRecord(好贷网插入数据)  
	 * @param haoDaiVo   
	 * void 
	 * @return haoDaiVo
	 * @create time： Sep 15, 2015 4:58:19 PM 
	 * @author：李擎 
	 * @since  1.0.0
	 */
	
	public void insertHaoDaiRecord(HaoDaiVo haoDaiVo);

	public void addhdRequest(UserHdRequest hdRequest);

	public void addhdBxdjgcxjl(UserHdBxdjgcxjl hdBxdjgcxjl);

	public void addhdDkcfsq(UserHdDkcfsq hdDkcfsq);

	public void addhdSyhmd(UserHdShanyinSyhmd hdSyHmd);

	public void addhdSxbzxrcx(UserHdSxbzxrcx hdSxbzxrcx);
	
	public void addGrbzxggcx(UserHdGrbzxggcx hdGrbzxggcx);

	
	
	/** 
	* modifyThirdPartCreditStatus<br/> 
	*  TODO(根据好贷网返回征信结果状态，更改业务表数据状态) 
	* @param map void
	* @author zhaohefei
	* @2015年12月27日
	* @return void	返回类型 
	*/
	public void modifyThirdPartCreditStatus(String totalId, boolean creditStatus);
	
	/**
	 * grsxbzxQuery<br/>
	 * TODO(个人失信被执行查询:400)
	 * 
	 * @param result
	 * @param totalId
	 * @return boolean
	 * @author zhaohefei
	 * @2015年12月28日
	 * @return boolean 返回类型
	 */
	public boolean grsxbzxQuery(String result, String totalId,String hdrequestId);
	
	/**
	 * zxgrbzxQuery<br/>
	 * TODO(个人被执行公告查询:680)
	 * 
	 * @param result
	 * @param totalId
	 * @return boolean
	 * @author zhaohefei
	 * @2015年12月28日
	 * @return boolean 返回类型
	 */
	public boolean zxgrbzxQuery(String result, String totalId,String hdrequestId);
	
	/**
	 * dkcfsqQuery<br/>
	 * TODO(借款重复查询次数信息查询:650)
	 * 
	 * @param result
	 * @param totalId
	 * @return boolean
	 * @author zhaohefei
	 * @2015年12月28日
	 * @return boolean 返回类型
	 */
	public boolean dkcfsqQuery(String result, String totalId ,String hdrequestId);
	
	/**
	 * syhmdQuery<br/>
	 * TODO(黑名单查询：641)
	 * 
	 * @param result
	 * @param totalId
	 * @return boolean
	 * @author zhaohefei
	 * @2015年12月28日
	 * @return boolean 返回类型
	 */
	public boolean syhmdQuery(String result, String totalId ,String hdrequestId) ;
	
	/**
	 * bxdjgcxjlQuery<br/>
	 * TODO(被信贷机构查询次数:500)
	 * 
	 * @param result
	 * @param totalId
	 *            void
	 * @author zhaohefei
	 * @2015年12月28日
	 * @return boolean 返回类型
	 */
	public boolean bxdjgcxjlQuery(String result, String totalId ,String hdrequestId,int maxTimes) ;

	/** 
	* @Title requestHaoDai 
	* @Description TODO(请求好贷接口) 
	* @param 参数列表
	* @param map
	* @return 设定文件 
	* @return String	返回类型 
	* @throws 
	*/
	public String requestHaoDai(Map<String, String> map,Map<String, String> meMap);
	
	
	/** 
	* @Title queryIsHdCredit 
	* @Description TODO(这个service负责总体查询没一个好贷返回的结果) 
	* @param 参数列表
	* @param result
	* @param totalId
	* @param hdrequestId
	* @return 设定文件 
	* @return boolean	返回类型 
	* @throws 
	*/
	public boolean queryIsHdCredit(String result, String totalId,String hdrequestId,Map<String, String> meMap);

	/** 
	* @Title modifyCreditRecord 
	* @Description TODO(修改征信结果) 
	* @param 参数列表
	* @param totalId
	* @param creditStatus 设定文件 
	* @return void	返回类型 
	* @throws 
	*/
	public void modifyCreditRecord(String totalId, boolean creditStatus);
			
}
