package com.tera.report.model;

import com.tera.report.excel.ExcelResource;

public class User {

	@ExcelResource(title="姓名",order=1)
	private String username;
	
	@ExcelResource(title="性别",order=3)
	private String gender;
	
	@ExcelResource(title="年龄",order=2)
	private int age;
	
	public User() {
		super();
	}
	public User(String username, String gender, int age) {
		super();
		this.username = username;
		this.gender = gender;
		this.age = age;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
