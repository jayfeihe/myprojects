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

import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.springframework.core.NestedRuntimeException;

import com.tera.util.DateUtils;


/**
 * @author Tera
 */
public final class ExcelReportBuilder {

	/**
	 * Workbook
	 */
	private HSSFWorkbook wb;

	/**
	 * 条件样式
	 */
	private HSSFCellStyle conditionStyle;

	/**
	 * 表头样式
	 */
	private HSSFCellStyle headStyle;

	/**
	 * 数据样式
	 */
	private HSSFCellStyle dataStyle;

	/**
	 * 统计栏位样式
	 */
	private HSSFCellStyle statisticStyle;

	/**
	 * 统计栏位数据样式
	 */
	private HSSFCellStyle statisticStyleData;

	/**
	 * 注释样式
	 */
	private HSSFCellStyle commentStyle;

	/**
	 * 其它样式
	 */
	private HSSFCellStyle otherStyle;

	/**
	 * 构造函数
	 */
	private ExcelReportBuilder() {
		this.wb = new HSSFWorkbook();
		init();
	}

	/**
	 * @param wb wb
	 */
	private ExcelReportBuilder(HSSFWorkbook wb) {
		this.wb = wb;
		init();
	}

	/**
	 * @return ExcelReportBuilder
	 */
	public static ExcelReportBuilder getInstance() {
		return new ExcelReportBuilder();
	}

	/**
	 * @param wb wb
	 * @return ExcelReportBuilder
	 */
	public static ExcelReportBuilder getInstance(HSSFWorkbook wb) {
		return new ExcelReportBuilder(wb);
	}

	/**
	 * 构建HSSFSheet
	 * @param report report
	 */
	public void build(ReportTable report) {
		HSSFSheet sheet = wb.createSheet(report.getTitle());
//		sheet.setDisplayGridlines(false);
		sheet.setDefaultColumnWidth((short) 20);

		for (int i = 0; i < report.getRowCount(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				row = sheet.createRow(i);
			}

			for (int j = 0; j < report.getColumnCount(); j++) {

				int cellHeight = report.getCellHeight(i, j);
				int cellWidth = report.getCellWidth(i, j);
				if (cellHeight != 0 && cellWidth != 0) {
					HSSFCell cell = row.createCell((short) j);

					Object object = report.getObject(i, j);
					boolean isSetWidth = true;

					if (report.isData(i, j)) {
						cell.setCellStyle(getDataStyle());
						Object data = report.getObject(i, j);
						if (data instanceof Integer) {
							cell.setCellValue((Integer) data);
						} else if (data instanceof Long) {
							cell.setCellValue((Long) data);
						} else if (data instanceof Timestamp) {
							cell.setCellValue((Timestamp) data);
						} else if (data instanceof java.util.Date) {
							cell.setCellValue(DateUtils.formatTime((Date) data));
						} else if (data instanceof Double) {
							cell.setCellValue((Double) data);
						} else {
							cell.setCellValue(new HSSFRichTextString((String) data));
						}
					} else  if (report.isCondition(i, j)) {
						isSetWidth = false;
						cell.setCellStyle(getConditionStyle());
						cell.setCellValue(new HSSFRichTextString((String) report.getObject(i, j)));
					} else if (report.isHead(i, j)) {
						cell.setCellStyle(getHeadStyle());
						cell.setCellValue(new HSSFRichTextString((String) report.getObject(i, j)));
					} else if (report.isStatistic(i, j)) {
						if (report.getCellWidth(i, j) > 1) {
							cell.setCellStyle(getStatisticStyle());
						} else {
							cell.setCellStyle(getStatisticStyleData());
						}
						Object data = report.getObject(i, j);
						if (data instanceof String) {
							cell.setCellValue(new HSSFRichTextString((String) data));
						} else if (data instanceof Integer) {
							cell.setCellValue((Integer) data);
						} else if (data instanceof Long) {
							cell.setCellValue((Long) data);
						} else {
							cell.setCellValue((Double) data);
						}
					} else if (report.isComment(i, j)) {
						cell.setCellStyle(getCommentStyle());
						cell.setCellValue(new HSSFRichTextString((String) report.getObject(i, j)));
					} else {
						cell.setCellStyle(getOtherStyle());
					}
					if (isSetWidth) {
						setSheetWidth(sheet, (short) j, object);
					} else {
						for (int colIndex = j; colIndex < report.getColumnCount(); colIndex++) {
							HSSFRow tempRow = sheet.getRow(i);
							if (tempRow == null) {
								tempRow = sheet.createRow(i);
							}

							tempRow.setHeight((short) 100);
							HSSFCell tempCell = tempRow.getCell((short) colIndex);
							if (tempCell == null) {
								tempCell = tempRow.createCell((short) colIndex);
								tempCell.setCellStyle(conditionStyle);
							}
						}
						String conditions = (String) report.getObject(i, j);
						int conditonLength = conditions.split("\n").length;
						sheet.getRow(i).setHeight((short) (conditonLength * 250));
						sheet.addMergedRegion(new Region(i, (short) j, i, (short) (report.getColumnCount() - 1)));
					}

					if (cellHeight > 1) {
						for (int k = i + 1; k < i + cellHeight; k++) {
							HSSFRow tempRow = sheet.getRow(k);
							if (tempRow == null) {
								tempRow = sheet.createRow(k);
							}
							HSSFCell tempCell = tempRow.getCell((short) j);
							if (tempCell == null) {
								tempCell = tempRow.createCell((short) j);
								tempCell.setCellStyle(dataStyle);
							}
						}

						sheet.addMergedRegion(new Region(i, (short) j, i + cellHeight - 1, (short) j));
					}
					if (cellWidth > 1) {
						for (int k = j + 1; k < j + cellWidth; k++) {
							HSSFRow tempRow = sheet.getRow(i);
							if (tempRow == null) {
								tempRow = sheet.createRow(i);
							}
							HSSFCell tempCell = tempRow.getCell((short) k);
							if (tempCell == null) {
								tempCell = tempRow.createCell((short) k);
								tempCell.setCellStyle(statisticStyle);
							}
						}

						sheet.addMergedRegion(new Region(i, (short) j, i, (short) (j + cellWidth - 1)));
					}
				}
			}

		}

//		for(int colIndex=0;colIndex<report.getColumnCount();colIndex++){
//			sheet.autoSizeColumn((short)colIndex);
//			if(sheet.getColumnWidth((short)colIndex)>25){
//			   sheet.setColumnWidth((short)colIndex, (short)25);
//			}
//		}
		Integer frozenColumn = report.getFrozenColumn();
		Integer frozenRow = report.getFrozenRow();
		if (frozenColumn != null && frozenRow != null) {
			sheet.createFreezePane(frozenColumn, frozenRow);
		}
	}

	/**
	 * @param report report
	 * @param os os
	 */
	public void build(ReportTable report, OutputStream os) {
		build(report);
		try {
			wb.write(os);
			os.flush();
		} catch (Exception e) {
			throw new ExcelBuildException("Excel Building Failed!", e);
		}
	}

	/**
	 * @param sheet sheet
	 * @param colIndex colIndex
	 * @param value value
	 */
	private  void setSheetWidth(HSSFSheet sheet, short colIndex,
			Object value) {
		int per = 300;
		int excel = 50;
		short columnWidth = sheet.getColumnWidth(colIndex);
		int valueLength = 0;
		if (value != null) {
			String viewSize = value.toString();
			valueLength = getLength(viewSize);
		}
		if (valueLength > 15) {
			valueLength = 15;
		}
		short colW = (short) (valueLength * per + excel);
		if (colW > columnWidth) {
			sheet.setColumnWidth(colIndex, colW);
		}
	}

	/**
	 * 获取字符串长度
	 * @param inStr inStr
	 * @return 字符串长度
	 */
	private  int getLength(String inStr) {
		char[] myBuffer = inStr.toCharArray();
        int length = 0;
        for (int i = 0; i < inStr.length(); i++) {
            int s = (int) myBuffer[i];
            if (s > 0 && s < 256) {
            	length = length + 1;
            } else {
            	length = length + 2;
            }
        }
		return length;
	}

	/**
	 * 初始化
	 */
	private void init() {
		this.conditionStyle = wb.createCellStyle();
		HSSFFont conditionFont = wb.createFont();
		conditionFont.setFontHeightInPoints((short) 10);
		conditionFont.setFontName("宋体");
		conditionFont.setColor(HSSFColor.BLUE.index);
		conditionFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		conditionStyle.setFont(conditionFont);
		conditionStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		conditionStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		conditionStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
		conditionStyle.setWrapText(true);

		this.headStyle = wb.createCellStyle();
		HSSFFont headFont = wb.createFont();
		headFont.setFontHeightInPoints((short) 10);
		headFont.setFontName("黑体");
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headStyle.setFont(headFont);
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headStyle.setWrapText(false);
		setCellBorderAll(headStyle, HSSFCellStyle.BORDER_THIN);

		this.dataStyle = wb.createCellStyle();
		HSSFFont dataFont = wb.createFont();
		dataFont.setFontHeightInPoints((short) 10);
		dataFont.setFontName("宋体");
		dataFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		dataStyle.setFont(dataFont);
		dataStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		dataStyle.setWrapText(true);
		setCellBorderAll(dataStyle, HSSFCellStyle.BORDER_THIN);

		this.statisticStyle = wb.createCellStyle();
		HSSFFont statisticFont = wb.createFont();
		statisticFont.setFontHeightInPoints((short) 10);
		statisticFont.setFontName("黑体");
		statisticFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		statisticStyle.setFont(statisticFont);
		statisticStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		statisticStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		statisticStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		statisticStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		statisticStyle.setWrapText(false);
		setCellBorderAll(statisticStyle, HSSFCellStyle.BORDER_THIN);

		this.statisticStyleData = wb.createCellStyle();
		HSSFFont statisticDataFont = wb.createFont();
		statisticDataFont.setFontHeightInPoints((short) 10);
		statisticDataFont.setFontName("黑体");
		statisticDataFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		statisticStyleData.setFont(statisticDataFont);
		statisticStyleData.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		statisticStyleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		statisticStyleData.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		statisticStyleData.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		statisticStyleData.setWrapText(false);
		setCellBorderAll(statisticStyleData, HSSFCellStyle.BORDER_THIN);

		this.commentStyle = wb.createCellStyle();
		HSSFFont commentFont = wb.createFont();
		commentFont.setFontHeightInPoints((short) 10);
		commentFont.setFontName("宋体");
		commentFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		commentFont.setColor(HSSFColor.RED.index);
		commentFont.setItalic(true);
		commentStyle.setFont(commentFont);
		commentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		commentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		commentStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
		commentStyle.setWrapText(false);

		this.otherStyle = wb.createCellStyle();
		HSSFFont otherFont = wb.createFont();
		otherFont.setFontHeightInPoints((short) 10);
		otherStyle.setFont(otherFont);
		otherStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		otherStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		otherStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
		otherStyle.setWrapText(false);
	}

	/**
	 * @return wb
	 */
	public HSSFWorkbook getWb() {
		return wb;
	}

	/**
	 * @param wb wb
	 */
	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}

	/**
	 * @return conditionStyle
	 */
	public HSSFCellStyle getConditionStyle() {
		return conditionStyle;
	}

	/**
	 * @param conditionStyle conditionStyle
	 */
	public void setConditionStyle(HSSFCellStyle conditionStyle) {
		this.conditionStyle = conditionStyle;
	}

	/**
	 * @return headStyle
	 */
	public HSSFCellStyle getHeadStyle() {
		return headStyle;
	}

	/**
	 * @param headStyle headStyle
	 */
	public void setHeadStyle(HSSFCellStyle headStyle) {
		this.headStyle = headStyle;
	}

	/**
	 * @return dataStyle
	 */
	public HSSFCellStyle getDataStyle() {
		return dataStyle;
	}

	/**
	 * @param dataStyle dataStyle
	 */
	public void setDataStyle(HSSFCellStyle dataStyle) {
		this.dataStyle = dataStyle;
	}

	/**
	 * @return commentStyle
	 */
	public HSSFCellStyle getCommentStyle() {
		return commentStyle;
	}

	/**
	 * @param commentStyle commentStyle
	 */
	public void setCommentStyle(HSSFCellStyle commentStyle) {
		this.commentStyle = commentStyle;
	}

	/**
	 * @return otherStyle
	 */
	public HSSFCellStyle getOtherStyle() {
		return otherStyle;
	}

	/**
	 * @param otherStyle otherStyle
	 */
	public void setOtherStyle(HSSFCellStyle otherStyle) {
		this.otherStyle = otherStyle;
	}

	/**
	 * @param conditionFont conditionFont
	 */
	public void setConditionFont(HSSFFont conditionFont) {
		conditionStyle.setFont(conditionFont);
	}

	/**
	 * @param headFont headFont
	 */
	public void setHeadFont(HSSFFont headFont) {
		headStyle.setFont(headFont);
	}

	/**
	 * @param dataFont dataFont
	 */
	public void setDataFont(HSSFFont dataFont) {
		dataStyle.setFont(dataFont);
	}

	/**
	 * @param commentFont commentFont
	 */
	public void setCommentFont(HSSFFont commentFont) {
		commentStyle.setFont(commentFont);
	}

	/**
	 * @param statisticFont statisticFont
	 */
	public void setStatisticFont(HSSFFont statisticFont) {
		statisticStyle.setFont(statisticFont);
	}

	/**
	 * @param otherFont otherFont
	 */
	public void setOtherFont(HSSFFont otherFont) {
		otherStyle.setFont(otherFont);
	}

	/**
	 * @return statisticStyle
	 */
	public HSSFCellStyle getStatisticStyle() {
		return statisticStyle;
	}

	/**
	 * @param statisticStyle statisticStyle
	 */
	public void setStatisticStyle(HSSFCellStyle statisticStyle) {
		this.statisticStyle = statisticStyle;
	}

	/**
	 * @param style style
	 * @param border border
	 */
	public void setCellBorderAll(HSSFCellStyle style, short border) {
		style.setBorderBottom(border);
		style.setBorderLeft(border);
		style.setBorderRight(border);
		style.setBorderTop(border);
	}

	/**
	 * @return 统计栏位数据样式
	 */
	public HSSFCellStyle getStatisticStyleData() {
		return statisticStyleData;
	}

//	public static void main(String[] args) {
//		String title = "Test";
//		String[] head = {"A", "B", "C", "D"};
//		Object[][] data = {{"xxx", 1, 2, 3},
//						   {"xxx", 4, 5, 6},
//						   {"yyy", 4, 7, 8},
//						   {"yyy", 4, 8, 8}};
//
//		ExcelReportTable report = new ExcelReportTable(title, head, data);
//		for(int i=0; i<report.getRowCount(); i++) {
//			StringBuffer sb = new StringBuffer();
//			for(int j=0; j<report.getColumnCount(); j++){
//				sb.append((report.getCellHeight(i, j))).append(",");
//			}
//		}
//
//		try {
//			FileOutputStream os = new FileOutputStream("d:/test.xls");
//			ExcelReportBuilder builder = ExcelReportBuilder.getInstance();
//			builder.build(report, os);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}

/**
 * @author Tera
 *
 */
@SuppressWarnings("serial")
class ExcelBuildException extends NestedRuntimeException {
	/**
	 * @param s string
	 * @param ex exception
	 */
	public ExcelBuildException(String s,  Throwable ex) {
		super(s, ex);
	}
}
