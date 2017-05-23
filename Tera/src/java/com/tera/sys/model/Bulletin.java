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
import java.util.Date;

/**
 * @author wy
 *
 */
public class Bulletin implements Serializable {

	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private int id;

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 正文
	 */
	private String content;
	/**
	 * 发布时间
	 */
	private Date publishTime;
	/**
	 * 作者
	 */
	private String writer;
	/**
	 * 公告状态 writting publishing cancel
	 */
	private String bulState;

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id id
	 */
	public void setId(int id) {
		this.id = id;
	}

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
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return publishTime
	 */
	public Date getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime publishTime
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
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
	 * （非 Javadoc）
	 * @see java.lang.Object#toString()
	 * @return String
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("User{").append("\n");
		buffer.append("id: ").append(id).append("\n");
		buffer.append("title: ").append(title).append("\n");
		buffer.append("content: ").append(content).append("\n");
		buffer.append("publishTime: ").append(publishTime).append("\n");
		buffer.append("state: ").append(bulState).append("\n");
		buffer.append("writer: ").append(writer).append("\n");
		buffer.append("}").append("\n");
		return buffer.toString();
	}
}
