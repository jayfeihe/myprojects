package com.greenkoo.sys.model;

import java.io.Serializable;
import java.util.Date;

import com.greenkoo.sys.model.form.MenuVo;

public class SysMenu implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer menuId;       // 菜单id
	private String menuName;      // 菜单名称
	private String menuLevel;     // 菜单级别：0虚拟主菜单 1系统级别菜单 2功能模块菜单 3功能点菜单
	private String menuUrl;       // 菜单对应url	
	private String menuDesc;      // 菜单功能描述
	private Integer parentMenuId; // 上级id
	private Integer orderBy;      // 排序
	private Integer status;       // 状态：0：无效，1：有效
	private Date createTime;      // 创建时间
	private Date updateTime;      // 更新时间
	private String operator;      // 操作人
	
	public SysMenu() {
		super();
	}

	public SysMenu(MenuVo menuVo){
		this.menuName=menuVo.getMenuName();
		this.menuLevel=menuVo.getMenuLevel();
		this.menuUrl=menuVo.getMenuUrl();
		this.menuDesc=menuVo.getMenuDesc();
		this.parentMenuId=menuVo.getParentMenuId();
		this.orderBy=menuVo.getOrderBy();
		this.status=menuVo.getStatus();
	}
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	public Integer getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(Integer parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public Integer getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
