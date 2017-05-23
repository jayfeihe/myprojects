package com.hikootech.mqcash.service;

import com.hikootech.mqcash.po.UserInfo;

public interface UserService {
	
	public UserInfo queryUserInfoByIdCard(String idCard) ;
	
	/**根据登陆账号查询用户
	 * @param bindMobile
	 * @return
	 * 
	 */
	public UserInfo queryUserInfoByBindMobile(String bindMobile) ;
	
	/**根据登陆账号查询有效用户 status=1
	 * @param bindMobile
	 * @return
	 * 
	 */
	public UserInfo queryEffectiveUserInfoByBindMobile(String bindMobile) ;
	
	/**根据登陆账号和密码查询有效用户 status=1
	 * @param bindMobile
	 * @param pwd
	 * @return
	 * 
	 */
	public UserInfo queryUserInfoByBindMobileAndPwd(String bindMobile, String pwd) ;
	
	/**置用户账号失效
	 * @param IdCard
	 * @return
	 * 
	 */
	public void invalidUser(String idCard) ;
	
	 
	/** 
	* updateBindMobile<br/> 
	*  TODO(修改用户绑定手机) 
	* @param userInfo
	*  
	* @author zhaohefei
	* @2015年12月13日
	* @return void	返回类型 
	*/
	public void updateBindMobile(UserInfo userInfo) ;
	
	
	/**updateUserPwd
	* 此方法描述的是：根据旧密码修改密码，实际上 根据个人身份证号码和旧密码修改
	* @param pwd 新密码
	* @param oldPwd 旧密码
	* @author: zhaohefei
	* @version: 2015年11月15日 下午1:59:35
	*/
	public void updateUserPwd(String IdCard, String pwd, String oldPwd, int pwdLevel) ;
	
	/**  
	 * updateUserInitPwd(修改用户初始密码)  
	 * @param IdCard
	 * @param pwd
	 * @param oldPwd
	 * @param pwdLevel   
	 * void 
	 * @create time： 2016年4月23日 下午3:20:48 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void updateUserInitPwd(String IdCard, String pwd, String oldPwd, int pwdLevel);
	
	/**updateUserPwd
	* 此方法描述的是：根据手机号修改密码，实际上是验证绑定手机号正确后，根据个人身份证号码修改
	* @author: zhaohefei
	* @version: 2015年11月15日 下午1:59:24
	*/
	public void updateUserPwd(String IdCard, String pwd, int pwdLevel) ;
	
	/**updateUserBaseInfo
	* 此方法描述的是：修改个人资料
	* @author: zhaohefei
	* @version: 2015年11月15日 下午2:26:51
	*/
	public void updateUserBaseInfo(UserInfo userInfo) ;


}
