/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.report.excel;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * @author S.Qiao
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
	@Override
	protected void buildExcelDocument(Map map, HSSFWorkbook wb,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/vnd.ms-excel");     
        response.setHeader("Content-Disposition", "attachment; filename=\"" + this.downloadName + "\"");
        String sheetname = (String) map.get(ExcelConstants.SHEET_NAME);
        ExcelMatrix excelMatrix = (ExcelMatrix) map.get(ExcelConstants.EXCEL_MATRIX);
        HSSFSheet sheet = wb.createSheet(sheetname);
        ExcelUtils.writeData(wb, sheet, excelMatrix);
        OutputStream ouputStream = response.getOutputStream();     
        wb.write(ouputStream);     
        ouputStream.flush();     
        ouputStream.close();  
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

}
