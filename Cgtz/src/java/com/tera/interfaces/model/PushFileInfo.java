package com.tera.interfaces.model;

/*
 * 推送的文件地址信息
 */
public class PushFileInfo {
	private String file_url; //图片附件地址   HTTP下载地址

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String fileUrl) {
		file_url = fileUrl;
	}

}
