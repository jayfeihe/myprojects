package com.tera.loan.constant;

public class LoanConstants {
	

	/**
	 * 抵押借款流程
	 */
	public static final String PROCESS_NAME_B = "抵押借款流程";

	/**
	 * 录入申请（杨长收添加）
	 */
	public static final String PROCESS_STATE_LUSQ = "录入申请";
	/**
	 * 风险专员初核（杨长收添加）
	 */
	public static final String PROCESS_STATE_FXZYCH = "风险专员初核";
	
	
	/**
	 * 风险专员初核不通过（杨长收添加）
	 */
	public static final String PROCESS_STATE_FXZYCH_BTG = "不通过";
	
	/**
	 * 风险专员初核复议（杨长收添加）
	 */
	public static final String PROCESS_STATE_FXZYCH_FY = "复议";
	
	/**
	 * 风险专员初核复议（杨长收添加）
	 */
	public static final String PROCESS_STATE_RGCHSP = "人工撮合审批";
	
	/**
	 * 风险专员初核复议（杨长收添加）
	 */
	public static final String PROCESS_STATE_FXZYCH_ROLE_YYBJL = "营业部经理";

	/**
	 * 执行失败（杨长收添加）
	 */
	public static final String PROCESS_STATE_FQ = "放弃";
	
	/**
	 * 执行失败（杨长收添加）
	 */
	public static final String PROCESS_STATE_FK = "放款";
	
	/**
	 * 签约（杨长收添加）
	 */
	public static final String PROCESS_STATE_QY = "签约";
	
	/**
	 * 签约（杨长收添加）
	 */
	public static final String PROCESS_STATE_RGCHSF = "人工撮合收费";
	
	/**
	 * 人工撮合（杨长收添加）
	 */
	public static final String PROCESS_STATE_RGCH = "人工撮合";
	
	
	/**
	 * 营业部经理审核
	 */
	public static final String PROCESS_STATE_YYBJLSH="营业部经理审核";
	/**
	 * 总部审核
	 */
	public static final String PROCESS_STATE_ZBSH="总部审核";
	
	/**
	 * 借款客户经理
	 */
	public static final String ROLE_JKKHJL = "借款客户经理";
	/**
	 * 风险专员
	 */
	public static final String ROLE_FXZY = "风险专员";
	/**
	 * 营业部经理
	 */
	public static final String ROLE_YYBJL = "营业部经理";
	/**
	 * 营业部负责人
	 */
	public static final String ROLE_YYBFZR = "营业部负责人";
	/**
	 * 总部高级审批人
	 */
	public static final String ROLE_ZBGJSPR = "总部高级审批人";
	/**
	 * 总部高级审批负责人
	 */
	public static final String ROLE_ZBGJFZR = "总部高级审批负责人";
	/**
	 * 放款专员
	 */
	public static final String ROLE_FKZY = "放款专员";
	/**
	 * 总部审批人
	 */
	public static final String ROLE_ZBSPR = "总部审批人";
	/**
	 * 总部负责人
	 */
	public static final String ROLE_ZBFZR = "总部审批负责人";
}
