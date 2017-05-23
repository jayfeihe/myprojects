package com.tera.collection.reduce.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.sys.model.User;
import com.tera.sys.service.UserService;

/**
 * 减免公共操作Service
 * @author QYANZE
 * 
 */
@Service("remissionHandlerService")
public class RemissionHandlerService {

	@Autowired(required=false)
	private UserService userService;
	
	/**
	 * 随机生成处理人
	 * 
	 * @author QYANZE
	 * 
	 * @param roleName
	 * @param orgId
	 * @return
	 */
	public String generateRandProcesser(String roleName,String orgId) {
		String processer = "";
		List<User> users = userService.getUserByOrgAndRoleAndDepart(orgId,new String[]{roleName},null);
		Collections.shuffle(users);
		if (users != null && users.size() > 0) {
			processer = users.get(0).getLoginId();
		}
		return processer;
	}
}
