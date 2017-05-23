package com.tera.collection.reduce.constants;


/**
 * 减免常量类
 * @author QYANZE
 *
 */
public class RemissionConstants {

	/**减免流程定义名称*/
	public final static String REMISSION_PROCESS_NAME = "减免流程";
	
	/**减免流程状态--开始*/
	public final static String REMISSION_BPM_STATE_START="开始";
	/**减免流程状态--复核*/
	public final static String REMISSION_BPM_STATE_REVIEW = "减免复核";
	/**减免流程状态--审批*/
	public final static String REMISSION_BPM_STATE_APPROVAL = "减免审批";
	/**减免流程状态--高级审批*/
	public final static String REMISSION_BPM_STATE_H_APPROVAL = "减免高级审批";
	/**减免流程状态--结束*/
	public final static String REMISSION_BPM_STATE_END = "结束";
	
	/**减免角色名--减免复核人员*/
	public final static String REMISSION_ROLE_NAME_REVIEW = "减免复核专员";
	/**减免角色名--减免审批人员*/
	public final static String REMISSION_ROLE_NAME_APPROVAL = "减免审批专员";
	/**减免角色名--减免高级审批人员*/
	public final static String REMISSION_ROLE_NAME_H_APPROVAL = "减免高级审批专员";
	
	/**减免申请状态--无效*/
	public final static String REMISSION_STATE_N_PASS = "0";
	/**减免申请状态--复核*/
	public final static String REMISSION_STATE_REVIEW = "1";
	/**减免申请状态--审批*/
	public final static String REMISSION_STATE_APPROVAL = "2";
	/**减免申请状态--高级审批*/
	public final static String REMISSION_STATE_H_APPROVAL = "3";
	/**减免申请状态--生效*/
	public final static String REMISSION_STATE_PASS = "4";
	/**减免申请状态--复核否决*/
	public final static String REMISSION_STATE_REVIEW_N_PASS = "5";
	/**减免申请状态--审批否决*/
	public final static String REMISSION_STATE_APPROVAL_N_PASS = "6";
	
	/**减免操作类型--复核*/
	public final static String REMISSION_OPERATE_TYPE_REVIEW = "1";
	/**减免操作类型--审批*/
	public final static String REMISSION_OPERATE_TYPE_APPROVAL = "2";
	/**减免操作类型--高级审批*/
	public final static String REMISSION_OPERATE_TYPE_H_APPROVAL = "3";
	
	/**减免复核/审批结果--通过*/
	public final static String REMISSION_RESULT_PASS = "Y";
	/**减免复核/审批结果--否决*/
	public final static String REMISSION_RESULT_N_PASS = "N";
	
	/**减免审批菜单域--审批*/
	public final static String REMISSION_SCOPE_APPROVAL = "1";
	/**减免审批菜单域--高级审批*/
	public final static String REMISSION_SCOPE_H_APPROVAL = "2";
	
	/**查询处理状态--待处理*/
	public final static String REMISSION_PROCESS_WAIT = "0";
	/**查询处理状态--已处理*/
	public final static String REMISSION_PROCESS_OFF = "1";
	
	/**复核提交审批*/
	public final static String REMISSION_REVIEW_TO_APPROVAL = "1";
	/**复核提交高级审批*/
	public final static String REMISSION_REVIEW_TO_H_APPROVAL = "2";
	
	/**最终操作--减免审批*/
	public final static String REMISSION_REMARK_APPROVAL = "减免审批";
	/**最终操作--减免高级审批*/
	public final static String REMISSION_REMARK_H_APPROVAL = "减免高级审批";
}
