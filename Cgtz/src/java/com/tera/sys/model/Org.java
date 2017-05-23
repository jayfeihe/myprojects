package com.tera.sys.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>OrgDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-29 13:00:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Org implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;//ID	private String orgId;//机构代码	private String orgName;//机构名称
	private String orgFullName; // 机构全称
	private String code;//组织机构代码	private String level;//级别
	private double aduitAmt; // 审批额度
	private double loanAmt; //月债权
	private double intRate; //利息差比
	private String remarks; // 说明
	
	private String ext1;// 扩展1
	private String ext2; // 扩展2
	
	private String text; //数据权限名称(combotree用)	private List<Org> children; //数据权限的孩子集合
	private String nodeLevel; // 节点等级，修改页面传递做判断显示树形
	private String parentOrgId; // 所属机构
	
	private int orgNodeId; //修改的时候选择上级菜单要去掉其本身及以下的自孩子, 此为节点本身的ID
	
	private List<String> roleNames; // 岗位权限角色设置
	private String orgAndRoleIds; // 机构和角色id
		public int getId() {		return this.id;	}	public void setId(int id) {		this.id=id;	}	public String getOrgId() {		return this.orgId;	}	public void setOrgId(String orgId) {		this.orgId=orgId;	}	public String getOrgName() {		return this.orgName;	}	public void setOrgName(String orgName) {		this.orgName=orgName;	}	public String getLevel() {		return this.level;	}	public void setLevel(String level) {		this.level=level;	}
	//此字段返回数据权限名称(combotree控件要求返回的数据格式)
	public String getText() {
		return this.orgName;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Org> getChildren() {
		return children;
	}
	public void setChildren(List<Org> children) {
		this.children = children;
	}
	public int getOrgNodeId() {
		return orgNodeId;
	}
	public void setOrgNodeId(int orgNodeId) {
		this.orgNodeId = orgNodeId;
	}
	public List<String> getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}
	public String getOrgAndRoleIds() {
		return orgAndRoleIds;
	}
	public void setOrgAndRoleIds(String orgAndRoleIds) {
		this.orgAndRoleIds = orgAndRoleIds;
	}
	public String getOrgFullName() {
		return orgFullName;
	}
	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public double getAduitAmt() {
		return aduitAmt;
	}
	public void setAduitAmt(double aduitAmt) {
		this.aduitAmt = aduitAmt;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public double getIntRate() {
		return intRate;
	}
	public void setIntRate(double intRate) {
		this.intRate = intRate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getNodeLevel() {
		return nodeLevel;
	}
	public void setNodeLevel(String nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	public String getParentOrgId() {
		return parentOrgId;
	}
	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}
}

