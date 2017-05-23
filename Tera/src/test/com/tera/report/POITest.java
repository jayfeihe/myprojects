package com.tera.report;

import java.io.FileOutputStream;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

/**
 * @author Wallace Chu
 * 
 */
public class POITest extends TestCase {

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testWrite() throws Exception {
		// 声明一个工作薄
		HSSFWorkbook wb = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = wb.createSheet("测试0");
		// 生成一个列
		HSSFRow row = sheet.createRow(0);
		// 生成一个样式
		HSSFCellStyle style = wb.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 16);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 填充单元格
		for (short i = 0; i < 5; i++) {
			// 声明一个单元格
			HSSFCell cell = row.createCell(i);
			// 设置单元格的字符值
			//cell.setCellValue("第" + i + "列");
			cell.setCellValue(new HSSFRichTextString("第" + i + "列"));
			// 设置单元格的样式
			cell.setCellStyle(style);
		}
		FileOutputStream fout = new FileOutputStream("doc/测试.xls");
		// 输出到文件
		wb.write(fout);
		fout.close();
		System.out.println("Complete");
	}

	public void test() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("new sheet");
		// Create a row and put some cells in it. Rows are 0 based.
		HSSFRow row = sheet.createRow((short) 1);
		// Create a cell and put a value in it.
		HSSFCell cell = row.createCell((short) 1);
		cell.setCellValue(new Date());
		// Style the cell with borders all around.
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.GREEN.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLUE.index);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM_DASHED);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		cell.setCellStyle(style);
		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("doc/workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	public void testMerge() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("new sheet");
		HSSFRow row = sheet.createRow((short) 1);
		HSSFCell cell = row.createCell((short) 1);
		cell.setCellValue("This is a test of merging");
		sheet.addMergedRegion(new Region(1,(short)1,1,(short)2));
//		 Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("doc/workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	public void testHeaders() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("new sheet");
		HSSFHeader header = sheet.getHeader();
		header.setCenter("Center Header");
		header.setLeft("Left Header");
		 header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
                 HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
		FileOutputStream fileOut = new FileOutputStream("doc/book.xls");
		wb.write(fileOut);
		fileOut.close();
	}

}
