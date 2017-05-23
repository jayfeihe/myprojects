/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.sys.dao.UserDao;
import com.tera.sys.model.User;

/**
 * @author Wallace chu
 *
 */
@Service
public class LogInOutService {

	/**
	 * UserDao
	 */
	@Autowired
	private UserDao userDaoMapper;

	/** (non-Javadoc)
	 * @see com.tera.sys.service.LogInOutService#isCorrectUser(java.lang.String, java.lang.String)
	 * @param loginid loginid
	 * @param password password
	 * @return return
	 */
	public User checkUser(String loginid, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginId", loginid);
		map.put("password", password);
		return userDaoMapper.checkUser(map);
	}

}
