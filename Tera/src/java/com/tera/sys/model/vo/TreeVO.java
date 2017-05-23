/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.model.vo;
/**
 * 异步树数据模型
 * @author admin
 *
 */
public class TreeVO {
	//"title\": \"Lazy Folder 4\", \"isFolder\": true, \"isLazy\": true, \"key\": \"folder4\" +
	/***/
	private String title;
	/***/
	private String key;
	/***/
	private boolean isFolder;
	/***/
	private boolean isLazy;
	/***/
	private boolean hideCheckbox;
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the isFolder
	 */
	public boolean getIsFolder() {
		return isFolder;
	}
	/**
	 * @param isFolder the isFolder to set
	 */
	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	/**
	 * @return the isLazy
	 */
	public boolean getIsLazy() {
		return isLazy;
	}
	/**
	 * @param isLazy the isLazy to set
	 */
	public void setIsLazy(boolean isLazy) {
		this.isLazy = isLazy;
	}
	/**
	 * @return the hideCheckbox
	 */
	public boolean getHideCheckbox() {
		return hideCheckbox;
	}
	/**
	 * @param hideCheckbox the hideCheckbox to set
	 */
	public void setHideCheckbox(boolean hideCheckbox) {
		this.hideCheckbox = hideCheckbox;
	}

}
