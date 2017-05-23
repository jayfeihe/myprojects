package com.tera.audit.common.constant;

public class CommonConstant {
//	/**核心接口地址**/
//	public static final String BASE_URl = "http://218.241.201.154/boss";
	
	/**信审流程定义名称*/
	public static final String AUDIT_PROCESS_NAME = "auditbpm";
	public static final String SYS_USER = "sysauto";

	/**查询排序字段*/
	public static final String ORDERY_BY_SUBMITTIME = "submit_time";
	public static final String ORDERY_BY_CHEATTIME = "cheat_time";
	public static final String ORDERY_BY_HANGUPTIME = "hang_up_time";
	public static final String ORDERY_BY_REFUSETIME = "refuse_time";
	public static final String ORDERY_BY_REVIEWTIME = "review_time";
	
	
	
	/**==========角色定义==============**/
	public static final String ROLE_SALESMAN = "业务员";
	public static final String ROLE_BRAN_MGR = "分公司经理";
	public static final String ROLE_RISK_MGR = "风控经理";
	public static final String ROLE_RISK_DIR = "风控总监";
	public static final String ROLE_JUDGE_STAFF = "评审会秘书";
	public static final String ROLE_JUDGE_DIR = "评审会评委";
	public static final String ROLE_LAW_INSIDE = "内勤专员";
	public static final String ROLE_LAW_STAFF = "法务专员";
	public static final String ROLE_CASH_STAFF = "出纳专员";
	public static final String ROLE_ACCT_STAFF = "财务专员";
	public static final String ROLE_ACCT_MGR = "财务经理";
	public static final String ROLE_CLLT_DIR = "贷后主管";
	public static final String ROLE_CLLT_STAFF = "催收专员";
	public static final String ROLE_CHECK_STAFF = "稽查专员";
	public static final String ROLE_PUSH_STAFF = "推送专员";
	
	/**==========角色定义结束=============**/
	
	/**==========流程节点定义==============**/
	public static final String PROCESS_A = "录入申请";
	public static final String PROCESS_B = "分公司审批";
	public static final String PROCESS_C = "风控经理审批";
	public static final String PROCESS_D = "评审会初审";
	public static final String PROCESS_E = "评审会复核";
	public static final String PROCESS_F = "风控总监审批";
	public static final String PROCESS_G1 = "法务初审";
	public static final String PROCESS_G2 = "法务内勤";
	public static final String PROCESS_G3 = "法务复核";
	public static final String PROCESS_H = "出纳核账";
	public static final String PROCESS_I1 = "财务审批";
	public static final String PROCESS_I2 = "财务复核";
	public static final String PROCESS_J = "出纳放款";
	public static final String PROCESS_K = "放弃申请";
	public static final String PROCESS_M = "法律意见";
	public static final String PROCESS_N = "推送线上";
	public static final String PROCESS_END = "结束";
	
	/**==========流程节点定义结束==============**/
	
}
