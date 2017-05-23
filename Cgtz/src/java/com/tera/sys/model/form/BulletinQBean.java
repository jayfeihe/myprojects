/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.model.form;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wy
 *
 */
@SuppressWarnings("serial")
public class BulletinQBean implements Serializable {

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 开始时间
	 */
	private Date spublishTime;
	/**
	 * 结束时间
	 */
	private Date epublishTime;
	/**
	 * 作者
	 */
	private String writer;
	/**
	 * 公告状态 1编辑中  2已发布  3无效
	 */
	private String bulState;


	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return writer
	 */
	public String getWriter() {
		return writer;
	}

	/**
	 * @param writer writer
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}

	/**
	 * @return bulState
	 */
	public String getBulState() {
		return bulState;
	}

	/**
	 * @param bulState bulState
	 */
	public void setBulState(String bulState) {
		this.bulState = bulState;
	}

	/**
	 * @return date
	 */
	public Date getSpublishTime() {
		return spublishTime;
	}
	/**
	 * @param spublishTime date
	 */
	public void setSpublishTime(Date spublishTime) {
		this.spublishTime = spublishTime;
	}
	/**
	 * @return date
	 */
	public Date getEpublishTime() {
		return epublishTime;
	}
	/**
	 * @param epublishTime date
	 */
	public void setEpublishTime(Date epublishTime) {
		this.epublishTime = epublishTime;
	}

	/**
	 * （非 Javadoc）
	 * @see java.lang.Object#toString()
	 * @return String
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("User{").append("\n");
		buffer.append("title: ").append(title).append("\n");
		buffer.append("state: ").append(bulState).append("\n");
		buffer.append("writer: ").append(writer).append("\n");
		buffer.append("}").append("\n");
		return buffer.toString();
	}
}
