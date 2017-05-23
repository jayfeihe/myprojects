package com.tera.report.excel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.Region;

/**
 * @author Wallace Chu
 * 根据传入的ExcelItem，生成Excel
 */
public class ExcelUtils {
	
	public static void writeData(HSSFWorkbook wb, HSSFSheet sheet,
			ExcelMatrix excelMatrix) throws Exception {
		writeData(wb, sheet, excelMatrix, 0, 0);
	}
	
	/**
	 * 从第offsetX列，offsetY行开始，将Matrix写入Excel
	 * @param wb
	 * @param sheet
	 * @param excelMatrix
	 * @param offsetX 列号
	 * @param offsetY 行号
	 * @throws Exception
	 */
	public static void writeData(HSSFWorkbook wb, HSSFSheet sheet,
			ExcelMatrix excelMatrix, int offsetX, int offsetY) throws Exception {
		int height = excelMatrix.getHeight();
		List<List<Object>> matrix = excelMatrix.getMatrix();
		HSSFRow row;
		HSSFCell cell;
		//Header部分
		int headerHeight = 0;
		ExcelRegion header = excelMatrix.getHeader();
		if(header!=null) {
			headerHeight = header.getY2()-header.getY1()+1;
			Region headerRegion = new Region(header.getY1() + offsetY,
					(short) (header.getX1() + offsetX),
					header.getY2() + offsetY,
					(short) (header.getX2() + offsetX));
			row = sheet.createRow(header.getY1() + offsetY);
			cell = createCell(wb, row, (short)(header.getX1() + offsetX), header.getValue()+"");
			sheet.addMergedRegion(headerRegion);
			ExcelCellStyle.setRegionStyle(wb, sheet, headerRegion);
		}
		int i = 0;
		for (i = headerHeight; i < height+headerHeight; i++) {
			row = sheet.createRow(i + offsetY);
			List<Object> rowList = matrix.get(i-headerHeight);
			if(rowList == null) {
				createCell(wb, row, (short)(0 + offsetX), "");
			}
			else {
				for (int j = 0; j < rowList.size(); j++) {
					Object data = rowList.get(j);
					createCell(wb, row, (short)(j + offsetX), data);
				}
			}
			
		}
		//自动调整列宽度
		for (int j = 0; j < excelMatrix.getMaxWidth(); j++) {
			sheet.autoSizeColumn((short)(j + offsetX)); 
		}
		//添加合并区域
		for (ExcelRegion element : excelMatrix.getRegions()) {
			Region region = new Region(element.getY1() + offsetY + headerHeight,
					(short) (element.getX1() + offsetX), element.getY2()
							+ offsetY + headerHeight, (short) (element.getX2() + offsetX));
			sheet.addMergedRegion(region);
			//将合并区域的文字居中
			row = sheet.getRow(element.getY1() + offsetY + headerHeight);
			cell = row.getCell((short)(element.getX1() + offsetX));
			ExcelCellStyle.setRegionStyle(wb, sheet, region);
		}
		//Footer部分
		int footerHeight = 0;
		ExcelRegion footer = excelMatrix.getFooter();
		if(footer!=null) {
			footerHeight = footer.getY2() - footer.getY1()+1;
			Region footerRegion = new Region(footer.getY1() + offsetY + i,
					(short) (footer.getX1() + offsetX), footer.getY2()
							+ offsetY + i, (short) (footer.getX2() + offsetX));
			row = sheet.createRow(footer.getY1() + offsetY + i);
			cell = createCell(wb, row, (short)(footer.getX1() + offsetX), footer.getValue()+"");
			sheet.addMergedRegion(footerRegion);
			ExcelCellStyle.setRegionStyle(wb, sheet, footerRegion);
		}
		//加外边框
//		Region region = new Region( 0, (short) 0, height - 1, (short)(width - 1) );
//		HSSFRegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THICK, region, sheet, wb);
//		HSSFRegionUtil.setBorderTop(HSSFCellStyle.BORDER_THICK, region, sheet, wb);
//		HSSFRegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THICK, region, sheet, wb);
//		HSSFRegionUtil.setBorderRight(HSSFCellStyle.BORDER_THICK, region, sheet, wb);
	}

	public static HSSFCell createCell(HSSFWorkbook wb, HSSFRow row, short column, Object data) {
		HSSFCell cell = row.createCell(column);
		if(data instanceof Double) {
			cell.setCellValue((Double)data);
			cell.setCellStyle(ExcelCellStyle.getDoubleCellStyle(wb));
		}
		else if(data instanceof Date || data instanceof Timestamp) {
			cell.setCellValue((Date)data);
			cell.setCellStyle(ExcelCellStyle.getDateCellStyle(wb));
		}			
		else if(data instanceof String) {
			cell.setCellValue(new HSSFRichTextString((String)data));
			cell.setCellStyle(ExcelCellStyle.getNormalCellStyle(wb));
		}			
		else if(data instanceof Boolean) {
			cell.setCellValue((Boolean)data);
			cell.setCellStyle(ExcelCellStyle.getNormalCellStyle(wb));
		}			
		else if(data instanceof Integer) {
			cell.setCellValue((Integer)data);
			cell.setCellStyle(ExcelCellStyle.getNormalCellStyle(wb));
		}
		else if(data==null)
			cell.setCellValue(new HSSFRichTextString(""));
		return cell;
	}
	
	private static class ExcelCellStyle {
		
		private static HSSFCellStyle NORMAL_STYLE;
		
		private static HSSFCellStyle DOUBLE_STYLE;
		
		private static HSSFCellStyle DATE_STYLE;
		
		private static HSSFCellStyle REGION_STYLE;
		
		private static HSSFWorkbook workbook;
		
		private static HSSFCellStyle getNormalCellStyle(HSSFWorkbook wb) {
			if(NORMAL_STYLE!=null && workbook == wb)
				return NORMAL_STYLE;
			else {
				workbook = wb;
				HSSFCellStyle style = initCellStyle();
				NORMAL_STYLE = style;
				return NORMAL_STYLE;
			}
		}
		
		private static HSSFCellStyle getDoubleCellStyle(HSSFWorkbook wb) {
			if(DOUBLE_STYLE!=null && workbook == wb)
				return DOUBLE_STYLE;
			else {
				workbook = wb;
				HSSFCellStyle style = initCellStyle();
				//设置数据格式
				HSSFDataFormat format = wb.createDataFormat();
				style.setDataFormat(format.getFormat("0.0000"));
				DOUBLE_STYLE = style;
				return DOUBLE_STYLE;
			}
		}
		
		private static HSSFCellStyle getDateCellStyle(HSSFWorkbook wb) {
			if(DATE_STYLE!=null&& workbook == wb)
				return DATE_STYLE;
			else {
				workbook = wb;
				HSSFCellStyle style = initCellStyle();
				//设置数据格式
				style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
				DATE_STYLE = style;
				return DATE_STYLE;
			}
		}
		
		private static HSSFCellStyle initCellStyle() {
			HSSFCellStyle style = workbook.createCellStyle();
			//加边框
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			return style;
		}
		
		private static void setRegionStyle(HSSFWorkbook wb, HSSFSheet sheet, Region region) {
			if(REGION_STYLE==null || workbook != wb) {
				workbook = wb;
				HSSFCellStyle style = initCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				style.setWrapText(true);//自动换行
				REGION_STYLE = style;
			}
			int toprowNum = region.getRowFrom();
	        for (int i = region.getRowFrom(); i <= region.getRowTo(); i ++) {
	            HSSFRow row = HSSFCellUtil.getRow(i,  sheet);
	            for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
	                HSSFCell cell = HSSFCellUtil.getCell(row, (short)j);
	                cell.setCellStyle(REGION_STYLE);
	            }
	        }
		}
		
	}
}


