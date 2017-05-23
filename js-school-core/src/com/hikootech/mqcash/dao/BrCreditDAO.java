package com.hikootech.mqcash.dao;

import java.util.List;

import com.hikootech.mqcash.po.BrApplyLoan;
import com.hikootech.mqcash.po.UserBrRequest;
import com.hikootech.mqcash.po.UserBrSpeciallistC;
import com.hikootech.mqcash.po.UserInfo;

public interface BrCreditDAO {
	
	public List<UserInfo> queryUserInfoList();

	public void updateBrMsg(UserInfo userDetailVo);

	/**
	 * saveBrRequestParams(插入百融信贷请求信息表)
	 * 
	 * @param brRequestPo
	 *            void
	 * @create time： 2016年5月13日 下午2:38:46
	 * @author：张海达
	 * @since 1.0.0
	 */
	public void saveBrRequestParams(UserBrRequest brRequestPo);

	/**
	 * saveBrRequestSpecialListC(插入百融特殊名单查询数据)
	 * 
	 * @param specialList
	 *            void
	 * @create time： 2016年5月16日 下午2:20:13
	 * @author：张海达
	 * @since 1.0.0
	 */
	public void saveBrRequestSpecialListC(UserBrSpeciallistC specialList);

	/**
	 * @Title saveBrApplyLoan
	 * @Description 新增百融多次申请核查记录
	 * @param 参数列表
	 * @param applyLoan
	 * @return 返回类型 void
	 */
	public void saveBrApplyLoan(BrApplyLoan applyLoan);

}
