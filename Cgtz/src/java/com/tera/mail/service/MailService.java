package com.tera.mail.service;

/**
 * 邮件服务Service
 * @author QYANZE
 *
 */
public class MailService implements Runnable {
	
	/**
	 * 流程节点
	 */
	private String processNode;
	
	/**
	 * 申请编号
	 */
	private String loanId;

	/**
	 * 主题
	 */
	private String subject;
	
	/**
	 * 邮件内容
	 */
	private String mailContent;
	
	/**
	 * 接收人邮件地址
	 */
	private String receiveEmail;
	
	public MailService(String processNode,String loanId, String receiveEamil) {
		super();
		this.processNode = processNode;
		this.loanId = loanId;
		this.receiveEmail = receiveEamil;
	}

	@Override
	public void run() {
		JavaMail mail = new JavaMail();
		this.subject = "线下系统审批任务通知";
		this.mailContent = "系统通知：【"+this.processNode+"】节点有新的审批任务,申请编号为( "+this.loanId+" )";
		mail.informMsg(this.subject, this.mailContent, this.receiveEmail);
	}

	public String getProcessNode() {
		return processNode;
	}

	public void setProcessNode(String processNode) {
		this.processNode = processNode;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getReceiveEmail() {
		return receiveEmail;
	}

	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}
}
