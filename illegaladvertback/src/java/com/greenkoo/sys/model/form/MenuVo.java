package com.greenkoo.sys.model.form;

import java.util.List;

import com.greenkoo.sys.model.SysMenu;

public class MenuVo {

	private String menuId;
	private String menuName;
	private String menuLevel;
	private String menuUrl;
	private String menuDesc;
	private Integer parentMenuId;
	private Integer orderBy;
	private Integer status;
	private Integer maxOrderBy;

	/** 该对象拥有的子级菜单 */
	private List<MenuVo> subMenuList;

	public MenuVo() {
	}

	public MenuVo(SysMenu menu) {
		this.menuId = String.valueOf(menu.getMenuId());
		this.menuName = menu.getMenuName();
		this.menuLevel = menu.getMenuLevel();
		this.menuUrl = menu.getMenuUrl();
		this.menuDesc = menu.getMenuDesc();
		this.parentMenuId = menu.getParentMenuId();
		this.orderBy = menu.getOrderBy();
		this.status = menu.getStatus();
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
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

	public Integer getMaxOrderBy() {
		return maxOrderBy;
	}

	public void setMaxOrderBy(Integer maxOrderBy) {
		this.maxOrderBy = maxOrderBy;
	}

	public List<MenuVo> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<MenuVo> subMenuList) {
		this.subMenuList = subMenuList;
	}
}
