package com.tera.collection.judicial.model;

import java.util.ArrayList;
import java.util.List;

import com.tera.rule.model.common.AssignUserInfoBean;

public class JudicialAssignRule {
	/**
	 * 司法待复核
	 */
	public static  final int VERIFY = 1;
	/**
	 * 司法复核处理中
	 */
	public static  final int APPROVAL = 2;
	/**
	 * 司法审批处理中
	 */
	public static  final int REVIEW = 3;
	
	/**
	 * 分配人信息
	 */
	private List<AssignUserInfoBean> assignInfo = new ArrayList<AssignUserInfoBean>();
	/**
	 * 结果信息
	 */
	private List<String> results = new ArrayList<String>();
	/**
	 * 初审和终审类型
	 */
	private Integer type;
}
