package com.greenkoo.sys.model;

import java.util.Date;

/**
 * 后台用户操作日志
 * 
 * @author QYANZE
 *
 */
public class SysOperationRecord {

	private String id            ; // 主键id
	private String account       ; // 用户账号
	private String ipAddress     ; // ip地址
	private Integer menuId       ; // 菜单id
	private String nickName      ; // 用户姓名
	private String operationDesc ; // 操作描述
	private String operationUrl  ; // 操作URL
	private String requestParams ; // 请求参数（以（参数名称1参数值1）||（参数名称2参数值2）方式存储）
	private int operationType    ; // （10:增20:删30:改90:其他）
	private Date createTime      ; // 创建时间
	
	
	public SysOperationRecord(String account, String nickName, String ipAddress, Integer menuId, String operationDesc,
			String operationUrl, String requestParams, int operationType, Date createTime) {
		super();
		this.account = account;
		this.ipAddress = ipAddress;
		this.menuId = menuId;
		this.nickName = nickName;
		this.operationDesc = operationDesc;
		this.operationUrl = operationUrl;
		this.requestParams = requestParams;
		this.operationType = operationType;
		this.createTime = createTime;
	}
	public SysOperationRecord() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getOperationDesc() {
		return operationDesc;
	}
	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}
	public String getOperationUrl() {
		return operationUrl;
	}
	public void setOperationUrl(String operationUrl) {
		this.operationUrl = operationUrl;
	}
	public String getRequestParams() {
		return requestParams;
	}
	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}
	public int getOperationType() {
		return operationType;
	}
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**操作类型-增*/
	public static final int OPERATION_TYPE_ADD = 10;
	/**操作类型-删*/
	public static final int OPERATION_TYPE_DELETE = 20;
	/**操作类型-改*/
	public static final int OPERATION_TYPE_UPDATE = 30;
	/**操作类型-其他*/
	public static final int OPERATION_TYPE_OTHER = 90;
}
