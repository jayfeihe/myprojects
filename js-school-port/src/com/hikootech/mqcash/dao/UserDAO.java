package com.hikootech.mqcash.dao;

import org.apache.ibatis.annotations.Param;

import com.hikootech.mqcash.po.UserInfo;

public interface UserDAO {
	
	/** 
	* queryUserInfoByIdCard<br/> 
	*  TODO(根据身份证号查询用户信息) 
	* @param IdCard
	* @return
	* @throws Exception 
	* @author zhaohefei
	* @2015年12月15日
	* @return UserInfo	返回类型 
	*/
	public UserInfo queryUserInfoByIdCard(String IdCard) throws Exception;
	
	/** 
	* queryUserInfoByBindMobile<br/> 
	*  TODO(根据绑定手机号，查询用户信息) 
	* @param bindMobile
	* @return
	* @throws Exception 
	* @author zhaohefei
	* @2015年12月15日
	* @return UserInfo	返回类型 
	*/
	public UserInfo queryUserInfoByBindMobile(String bindMobile) throws Exception;
	
	/** 
	* queryEffectiveUserInfoByBindMobile<br/> 
	*  TODO(根据绑定手机号查询有效用户信息) 
	* @param bindMobile
	* @return
	* @throws Exception 
	* @author zhaohefei
	* @2015年12月15日
	* @return UserInfo	返回类型 
	*/
	public UserInfo queryEffectiveUserInfoByBindMobile(String bindMobile) throws Exception;
	
	/** 
	* queryUserInfoByBindMobileAndPwd<br/> 
	*  TODO(根据绑定手机和登陆密码查询用户信息) 
	* @param bindMobile
	* @param pwd
	* @return
	* @throws Exception 
	* @author zhaohefei
	* @2015年12月15日
	* @return UserInfo	返回类型 
	*/
	public UserInfo queryUserInfoByBindMobileAndPwd(@Param("bindMobile")String bindMobile, @Param("pwd")String pwd) throws Exception;
	
	/** 
	* invalidUser<br/> 
	*  TODO(根据身份证号，将用户状态置为无效) 
	* @param idCard
	* @return
	* @throws Exception 
	* @author zhaohefei
	* @2015年12月15日
	* @return int	返回类型 
	*/
	public int invalidUser(String idCard) throws Exception ;

	
	
	/** 
	* updateUserPwd<br/> 
	*  TODO(通过原密码方式修改用户密码) 
	* @param IdCard
	* @param pwd
	* @param oldPwd
	* @param pwdLevel
	* @return
	* @throws Exception 
	* @author zhaohefei
	* @2015年12月15日
	* @return int	返回类型 
	*/
	public int updateUserPwdByPwd(@Param("IdCard")String IdCard, @Param("pwd")String pwd,@Param("oldPwd")String oldPwd,@Param("pwdLevel")int pwdLevel) throws Exception;
	
	/** 
	* updateUserPwd<br/> 
	*  TODO(通过手机方式直接修改密码) 
	* @param IdCard
	* @param pwd
	* @param pwdLevel
	* @throws Exception 
	* @author zhaohefei
	* @2015年12月15日
	* @return int	返回类型 
	*/
	public int updateUserPwdByMobile(@Param("IdCard")String IdCard, @Param("pwd")String pwd,@Param("pwdLevel")int pwdLevel) throws Exception;
	
	/** 
	* updateBindMobile<br/> 
	*  TODO(修改绑定手机号) 
	* @param userInfo
	* @throws Exception 
	* @author zhaohefei
	* @2015年12月15日
	* @return int	返回类型 
	*/
	public int updateBindMobile(UserInfo userInfo) throws Exception;
	
	/** 
	* updateUserBaseInfo<br/> 
	*  TODO(修改个人信息) 
	* @param userInfo
	* @throws Exception 
	* @author zhaohefei
	* @2015年12月15日
	* @return void	返回类型 
	*/
	public void updateUserBaseInfo(UserInfo userInfo)throws Exception;

	/**  
	 * updateUserInitPwd(修改用户初始密码)  
	 * @param idCard
	 * @param pwd
	 * @param oldPwd
	 * @param pwdLevel
	 * @return   
	 * int 
	 * @create time： 2016年4月23日 下午3:21:41 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public int updateUserInitPwd(@Param("idCard")String idCard, @Param("pwd")String pwd, @Param("oldPwd")String oldPwd, @Param("pwdLevel")int pwdLevel);
	
}
