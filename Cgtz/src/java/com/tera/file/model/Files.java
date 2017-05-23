package com.tera.file.model;

import com.google.gson.annotations.Expose;
import com.tera.util.DateUtils;

/**
 * 
 * 文件存储实体类
 * <b>功能：</b>FilesDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-08 21:21:37<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Files {

	//属性部分
	private int id; //ID
	private String bizKey; //业务ID
	private String sceneType; //所属场景
	@Expose
	private String category; //分类
	private String subCategory; //子分类
	@Expose
	private String fileName; //文件名
	private String groupName; //文件名
	private String saveName; //文件名
	@Expose
	private String filePath; //文件路径
	private String ext; //1图片2文件3视频
	private String loanId; //申请Id
	private String state; //状态
	private String operator; //操作员
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间
	@Expose
	private int categoryCount; // 分类个数
	
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getBizKey () {
		return this.bizKey;
	}
	public String getSceneType () {
		return this.sceneType;
	}
	public String getCategory () {
		return this.category;
	}
	public String getSubCategory () {
		return this.subCategory;
	}
	public String getFileName () {
		return this.fileName;
	}
	public String getFilePath () {
		return this.filePath;
	}
	public String getExt () {
		return this.ext;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public String getState () {
		return this.state;
	}
	public String getOperator () {
		return this.operator;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr () {
		return DateUtils.formatTime(this.updateTime);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setBizKey (String bizKey) {
		this.bizKey=bizKey;
	}
	public void setSceneType (String sceneType) {
		this.sceneType=sceneType;
	}
	public void setCategory (String category) {
		this.category=category;
	}
	public void setSubCategory (String subCategory) {
		this.subCategory=subCategory;
	}
	public void setFileName (String fileName) {
		this.fileName=fileName;
	}
	public void setFilePath (String filePath) {
		this.filePath=filePath;
	}
	public void setExt (String ext) {
		this.ext=ext;
	}
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}
	public int getCategoryCount() {
		return categoryCount;
	}
	public void setCategoryCount(int categoryCount) {
		this.categoryCount = categoryCount;
	}
	
	
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}



	/**图片类*/
	public final static String EXT_IMG = "img";
	/**文档类*/
	public final static String EXT_DOC = "doc";
	/**视频类*/
	public final static String EXT_FILE = "file";
}

