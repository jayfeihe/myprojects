package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月12日
 * 用户征信信息表：通过第三方征信获取的用户征信数据，以身份证做为主键
 */
public class UserCreditInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String creditId;
	private String source;
	private String identityCard;
	private String userName;
	private Integer queryStatus;
	private Integer resultStatus;
	private String cardSplitAddress;
	private String cardSplitBirthday;
	private String cardSplitSex;
	private String graduate;
	private String educationDegree;
	private String enrolDate;
	private String specialityName;
	private String graduateTime;
	private String studyResult;
	private String studyStyle;
	private String photo;
	private Date createTime;
	private Date updateTime;
	private Integer count;

	
	public UserCreditInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getCreditId() {
		return creditId;
	}

	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(Integer queryStatus) {
		this.queryStatus = queryStatus;
	}

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getCardSplitAddress() {
		return cardSplitAddress;
	}

	public void setCardSplitAddress(String cardSplitAddress) {
		this.cardSplitAddress = cardSplitAddress;
	}

	public String getCardSplitBirthday() {
		return cardSplitBirthday;
	}

	public void setCardSplitBirthday(String cardSplitBirthday) {
		this.cardSplitBirthday = cardSplitBirthday;
	}

	public String getCardSplitSex() {
		return cardSplitSex;
	}

	public void setCardSplitSex(String cardSplitSex) {
		this.cardSplitSex = cardSplitSex;
	}

	public String getGraduate() {
		return graduate;
	}

	public void setGraduate(String graduate) {
		this.graduate = graduate;
	}

	public String getEducationDegree() {
		return educationDegree;
	}

	public void setEducationDegree(String educationDegree) {
		this.educationDegree = educationDegree;
	}

	public String getEnrolDate() {
		return enrolDate;
	}

	public void setEnrolDate(String enrolDate) {
		this.enrolDate = enrolDate;
	}

	public String getSpecialityName() {
		return specialityName;
	}

	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}

	public String getGraduateTime() {
		return graduateTime;
	}

	public void setGraduateTime(String graduateTime) {
		this.graduateTime = graduateTime;
	}

	public String getStudyResult() {
		return studyResult;
	}

	public void setStudyResult(String studyResult) {
		this.studyResult = studyResult;
	}

	public String getStudyStyle() {
		return studyStyle;
	}

	public void setStudyStyle(String studyStyle) {
		this.studyStyle = studyStyle;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
