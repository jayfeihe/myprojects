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
 * Authorization
 * @author admin
 *
 */
public class Authorization implements Serializable {

	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;

	/***/
	private int id;
	/***/
	private String authName;
	/***/
	private String authPath;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the authName
	 */
	public String getAuthName() {
		return authName;
	}
	/**
	 * @param authName the authName to set
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	/**
	 * @return the authPath
	 */
	public String getAuthPath() {
		return authPath;
	}
	/**
	 * @param authPath the authPath to set
	 */
	public void setAuthPath(String authPath) {
		this.authPath = authPath;
	}

}
