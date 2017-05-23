/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.credit.constant;

/**
 * @author wallace
 */
public class Constants {

	/**
	 * 信用借款流程
	 */
	public static final String PROCESS_NAME_C = "信用借款流程";

	/**
	 * 录入申请
	 */
	public static final String PROCESS_STATE_APP = "录入申请";
	/**
	 * 结束
	 */
	public static final String PROCESS_END_APP = "结束";
	/**
	 * 审核
	 */
	public static final String PROCESS_STATE_VERIFY = "审核";
	/**
	 * 审批
	 */
	public static final String PROCESS_STATE_APPROVAL = "审批";
	/**
	 * 特殊审批
	 */
	public static final String PROCESS_STATE_SPECIAL = "特殊审批";
	/**
	 * 推送
	 */
	public static final String PROCESS_STATE_PUSH = "推送管理";
	/**
	 * 签约
	 */
	public static final String PROCESS_STATE_SIGN = "签约";
	/**
	 * 复核
	 */
	public static final String PROCESS_STATE_REVIEW = "复核";
	/**
	 * 放款
	 */
	public static final String PROCESS_STATE_LENDING = "放款";
	/**
	 * 反欺诈
	 */
	public static final String PROCESS_STATE_ANTIFRAUD = "反欺诈";
	/**
	 * 放弃
	 */
	public static final String PROCESS_STATE_GIVEUP = "放弃";
	/**
	 * 拒件
	 */
	public static final String PROCESS_STATE_REJECT = "拒件";

	/**
	 * 信用风险专员
	 */
	public static final String ROLE_FXZY = "信用风险专员";
	/**
	 * 信用审核专员
	 */
	public static final String ROLE_SHZY = "信用审核专员";
	/**
	 * 信用审核组长
	 */
	public static final String ROLE_SHZZ = "信用审核组长";
	/**
	 * 信用审批专员
	 */
	public static final String ROLE_SPZY = "信用审批专员";
	/**
	 * 信用审批组长
	 */
	public static final String ROLE_SPZZ = "信用审批组长";
	/**
	 * 信用高级审批员
	 */
	public static final String ROLE_GJSP = "信用高级审批员";
	/**
	 * 信用复核组长
	 */
	public static final String ROLE_FHZZ = "信用复核组长";
	/**
	 * 信用复核专员
	 */
	public static final String ROLE_FHZY = "信用复核专员";
	/**
	 * 信用放款专员
	 */
	public static final String ROLE_FKZY = "信用放款专员";
	/**
	 * 信用欺诈专员
	 */
	public static final String ROLE_QZZY = "信用欺诈专员";
	
	/**
	 * 总部编码
	 */
	public static final String ORG_CODE = "86";
	
	/**
	 * 积木查重一级编码
	 */
	public static final String JM_DUPLICATE_B_CODE = "D0413";
	
	/**
	 * 积木查重拒贷码
	 */
	public static final String JM_DUPLICATE_J_CODE = "已在积木融资";
	
	/**
	 * 夫妻分别借款拒贷码
	 */
	public static final String COUPLE_SEPARATE_LOAN_J_CODE = "D0409";

}
