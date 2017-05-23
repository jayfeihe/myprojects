/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.model;

import java.io.Serializable;

/**
 * 操作
 * @author admin
 *
 */
public class Operation implements Serializable {

	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;
	/***/
	public static final String ENTRY_LIST = "ENTRY_LIST";
	/***/
	public static final String ENTRY_ADD = "ENTRY_ADD";
	/***/
	public static final String ENTRY_REMOVE = "ENTRY_REMOVE";
	/***/
	public static final String CONTENT_READ = "CONTENT_READ";
	/***/
	public static final String CONTENT_WRITE = "CONTENT_WRITE";
	/***/
	private int operationID;
	/***/
	private String operationName;
	/**
	 * @return the operationID
	 */
	public int getOperationID() {
		return operationID;
	}
	/**
	 * @param operationID the operationID to set
	 */
	public void setOperationID(int operationID) {
		this.operationID = operationID;
	}
	/**
	 * @return the operationName
	 */
	public String getOperationName() {
		return operationName;
	}
	/**
	 * @param operationName the operationName to set
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

}
