package com.hikootech.mqcash.vo;

import java.io.Serializable;

/**  
 *   
 * UserCreditDataVo  
 *   
 * @function:(用户信用数据VO)  
 * @create time:Oct 20, 2015 8:04:08 PM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class UserCreditDataVo implements Serializable{

	private static final long serialVersionUID = 736216300224525672L;
	
	/** 请求流水号 */
	private String requestSeq= "";
	/** 时间戳 */
	private String timeStamp= "";
	/** 申办人姓名 */
	private String userName = "";
	/** 申办人身份证号码 */
	private String idCard= "";
	/** 身份证地址 */
	private String idCardAddress= "";
	/** 联系电话 */
	private String connectPhone= "";
	/** 外部征信数据 */
	private String outCreditData= "";
	/** 数据校验码 */
	private String dataCode = "";
	
	
	/** 处理结果 */
	private String result;
	/** 流水号 */
	private String resultSeq;
	/** 评分卡数据 */
	private String score ;
	public String getRequestSeq() {
		return requestSeq;
	}
	public void setRequestSeq(String requestSeq) {
		this.requestSeq = requestSeq;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getIdCardAddress() {
		return idCardAddress;
	}
	public void setIdCardAddress(String idCardAddress) {
		this.idCardAddress = idCardAddress;
	}
	public String getConnectPhone() {
		return connectPhone;
	}
	public void setConnectPhone(String connectPhone) {
		this.connectPhone = connectPhone;
	}
	public String getOutCreditData() {
		return outCreditData;
	}
	public void setOutCreditData(String outCreditData) {
		this.outCreditData = outCreditData;
	}
	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultSeq() {
		return resultSeq;
	}
	public void setResultSeq(String resultSeq) {
		this.resultSeq = resultSeq;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	
	
	

}
