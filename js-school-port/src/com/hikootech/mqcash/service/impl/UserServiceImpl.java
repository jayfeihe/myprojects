package com.hikootech.mqcash.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.UserDAO;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDAO userDAO;

	@Override
	public UserInfo queryUserInfoByIdCard(String idCard) {

		try {
			return userDAO.queryUserInfoByIdCard(idCard);
		} catch (Exception e) {
			log.error("根据身份证号查询用户信息失败", e);
			throw new RuntimeException("根据身份证号查询用户信息失败", e);
		}
	}

	@Override
	public UserInfo queryUserInfoByBindMobile(String bindMobile) {

		try {
			return userDAO.queryUserInfoByBindMobile(bindMobile);
		} catch (Exception e) {
			log.error("根据绑定手机，查询用户信息失败", e);
			throw new RuntimeException("根据绑定手机，查询用户信息失败", e);
		}
	}

	@Override
	public UserInfo queryEffectiveUserInfoByBindMobile(String bindMobile) {

		try {
			return userDAO.queryEffectiveUserInfoByBindMobile(bindMobile);
		} catch (Exception e) {
			log.error("根据绑定手机号查询有效用户信息失败", e);
			throw new RuntimeException("根据绑定手机号查询有效用户信息失败", e);
		}
	}

	@Override
	public UserInfo queryUserInfoByBindMobileAndPwd(String bindMobile, String pwd) {

		try {
			return userDAO.queryUserInfoByBindMobileAndPwd(bindMobile, pwd);
		} catch (Exception e) {
			log.error("根据绑定手机和登陆密码查询用户信息失败", e);
			throw new RuntimeException("根据绑定手机和登陆密码查询用户信息失败", e);
		}
	}

	@Override
	public void updateBindMobile(UserInfo userInfo) {

		try {
			if (userDAO.updateBindMobile(userInfo) != 1) {
				throw new RuntimeException("修改绑定手机时，返回结果不为1");
			}
		} catch (Exception e) {
			log.error("修改绑定手机号失败", e);
			throw new RuntimeException("修改绑定手机号失败", e);
		}
	}

	@Override
	public void updateUserPwd(String IdCard, String pwd, String oldPwd, int pwdLevel) {
		try {
			if (userDAO.updateUserPwdByPwd(IdCard, pwd, oldPwd, pwdLevel) != 1) {
				throw new RuntimeException("修改密码时，返回结果不为1");
			}
		} catch (Exception e) {
			log.error("通过原密码方式修改用户密码失败", e);
			throw new RuntimeException("通过原密码方式修改用户密码失败", e);
		}
	}
	
	@Override
	public void updateUserInitPwd(String IdCard, String pwd, String oldPwd, int pwdLevel) {
		try {
			if (userDAO.updateUserInitPwd(IdCard, pwd, oldPwd, pwdLevel) != 1) {
				throw new RuntimeException("修改密码时，返回结果不为1");
			}
		} catch (Exception e) {
			log.error("通过原密码方式修改用户密码失败", e);
			throw new RuntimeException("通过原密码方式修改用户密码失败", e);
		}
	}

	@Override
	public void updateUserPwd(String IdCard, String pwd, int pwdLevel) {
		try {
			if (userDAO.updateUserPwdByMobile(IdCard, pwd, pwdLevel) != 1) {
				throw new RuntimeException("修改密码时，返回结果不为1");
			}
		} catch (Exception e) {
			log.error("通过手机方式修改用户密码失败", e);
			throw new RuntimeException("通过手机方式修改用户密码失败", e);
		}
	}

	@Override
	public void updateUserBaseInfo(UserInfo userInfo) {
		try {
			userDAO.updateUserBaseInfo(userInfo);
		} catch (Exception e) {
			log.error("修改个人信息失败", e);
			throw new RuntimeException("修改个人信息失败", e);
		}
	}

	@Override
	public void invalidUser(String idCard) {

		log.info("置用户账号失效，idcard：" + idCard);
		try {
			int row = userDAO.invalidUser(idCard);
			if (row != 1) {
				throw new RuntimeException("同一个身份证对应有效用户账号不为1，系统异常！");
			}
		} catch (Exception e) {
			log.error("置用户账号失效失败", e);
			throw new RuntimeException("置用户账号失效失败", e);
		}

	}


}
