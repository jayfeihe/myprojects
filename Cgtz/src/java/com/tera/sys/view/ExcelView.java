/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.view;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.tera.sys.constant.ReportConstants;


/**
 * @author Tera
 *
 */
public class ExcelView extends AbstractExcelView {

	/**
	 * 文件名
	 */
	private String downloadName;

	/**
	 * 构造函数
	 */
	public ExcelView() {
		super();
	}

	/**
	 * @param downloadName 文件名
	 */
	public ExcelView(String downloadName) {
		super();
		this.downloadName = downloadName;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.document.AbstractExcelView
	 * * #buildExcelDocument(java.util.Map, org.apache.poi.hssf.usermodel.HSSFWorkbook,
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void buildExcelDocument(Map map, HSSFWorkbook wb,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ReportTable report = (ReportTable) map.get(ReportConstants.REPORT);

		ExcelReportBuilder builder = ExcelReportBuilder.getInstance(wb);
		builder.build(report);
		// 处理中文文件名称
		this.downloadName = transCharacter(request,this.downloadName);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + this.downloadName + "\"");
	}

	/**
	 * @return 文件名
	 */
	public String getDownloadName() {
		return downloadName;
	}

	/**
	 * @param downloadName 文件名
	 */
	public void setDownloadName(String downloadName) {
		this.downloadName = downloadName;
	}

	private String transCharacter(HttpServletRequest request, String str) throws Exception {
		String agent = request.getHeader("USER-AGENT").toLowerCase();
		if (agent.indexOf("msie") > 0 || agent.indexOf("like gecko") > 0) {
			return URLEncoder.encode(str, "UTF-8");
		}
		return new String(str.getBytes("UTF-8"), "ISO8859-1");
	}
}
