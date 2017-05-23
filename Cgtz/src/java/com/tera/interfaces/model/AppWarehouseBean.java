package com.tera.interfaces.model;

/**
 * app仓库实体
 * @author QYANZE
 *
 */
public class AppWarehouseBean {

	private int id;
	private String name;
	
	public AppWarehouseBean() {
		super();
	}
	public AppWarehouseBean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
