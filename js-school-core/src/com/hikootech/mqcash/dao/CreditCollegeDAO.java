package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.DepartmentInfo;
import com.hikootech.mqcash.po.DistrictInfo;
import com.hikootech.mqcash.po.JsschoolEducationScore;
import com.hikootech.mqcash.po.UserBrSpeciallistC;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.po.UserQhzxBlackList;

/**  
 *   
 * CollegeCreditDataService  
 *   
 * @function:(查询用户信用信息Service)  
 * @create time:Oct 9, 2015 11:06:01 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
public interface CreditCollegeDAO {
	
	/**  
	 * queryBlackUser(查询是否命中历史黑名单)  
	 * @param map
	 * @return   
	 * UserCreditRecord 
	 * @create time： 2016年7月13日 下午4:47:25 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserCreditRecord queryBlackUser(Map<String,String> map);

	/**  
	 * queryJyCreditResult(查询91征信结果)  
	 * @param busId
	 * @return   
	 * String 
	 * @create time： 2016年7月13日 下午7:50:47 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public String queryJyCreditResult(String busId);

	/**  
	 * querySchoolDistrict(根据校区查询评分)  
	 * @param districtName
	 * @return   
	 * DistrictInfo 
	 * @create time： 2016年7月14日 下午5:12:54 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public DistrictInfo querySchoolDistrict(String districtName);

	/**  
	 * queryschoolDepartment(根据院系查询评分)  
	 * @param schoolName
	 * @return   
	 * JsschoolDepartmentInfo 
	 * @create time： 2016年7月14日 下午5:26:22 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public DepartmentInfo querySchoolDepartment(Map<String, Object> paramMap);

	/**  
	 * saveEducationScore(保存学历评分信息)  
	 * @param score   
	 * void 
	 * @create time： 2016年7月14日 下午7:31:25 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void saveEducationScore(JsschoolEducationScore score);

	/**  
	 * updateJsSchoolBusCreditResult(修改江苏校园业务表中征信结果)  
	 * @param map
	 * @return   
	 * String 
	 * @create time： 2016年7月14日 下午8:17:40 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void updateJsSchoolBusCreditResult(Map<String,String> map);

	/**是否是前海历史黑名单
	 * @param map
	 * @return
	 */
	public List<UserQhzxBlackList> queryIsQhBlackUser(Map<String, String> map);

	/**是否是否是百荣历史黑名单
	 * @param map
	 * @return
	 */
	public List<UserBrSpeciallistC> queryIsBrBlackUser(Map<String, String> map);
	
	
}
