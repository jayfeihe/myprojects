package com.tera.audit.common.service;

import java.sql.Timestamp;

public interface ICommonHandlerService {

	/**
	 * 根据角色和机构获取操作人
	 * @param roleName
	 * @param orgId
	 * @return
	 */
	String getNextUser(String roleName, String orgId);

	/**
	 * 根据角色和机构获取有数据权限的操作人
	 * @param roleName
	 * @param orgId
	 * @return
	 */
	String getNextUser(String roleName, String orgId, String dataOrgId);

	/**
	 * 获取上一节点处理人
	 * @param bizKey
	 * @param state
	 * @return
	 */
	String getPrevUser(String bizKey, String bpmState);

	/**
	 * 流程跳转
	 * @param bizKey
	 * @param nextState
	 * @param nextUser
	 * @param operator
	 * @param decision
	 * @param remark
	 */
	void workFlow(String bizKey, String nextState, String nextUser, String operator, String decision, String remark);

	String getLoanId(Timestamp nowTime);

	/**
	 * 根据用户登录id获取邮箱地址
	 * @param loginId
	 * @return
	 */
	String getEmailByLoginId(String loginId);

}