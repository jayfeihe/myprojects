package com.tera.report.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Wallace Chu
 * 抽象的Excel二维数组
 * 按照横轴X，向右为正方向；纵轴Y，向下为正方向；
 * 所有坐标遵循此约定
 */
public class ExcelMatrix {
	
	/**
	 * Excel表头
	 */
	private ExcelRegion header;
	
	/**
	 * Excel结尾
	 */
	private ExcelRegion footer;
	
	/**
	 * 列中文名称
	 */
	private List<String> titles = new ArrayList<String>();
	
	/**
	 * Bean的属性名称列表
	 */
	private List<String> fields = new ArrayList<String>();
	
	/**
	 * Bean列表
	 */
	private List<?> objs = new ArrayList<Object>();
	
	/**
	 * 二维数据矩阵，数据区域
	 */
	private List<List<Object>> matrix = new ArrayList<List<Object>>();
	
	/**
	 * Excel中的合并区域
	 */
	private List<ExcelRegion> regions = new ArrayList<ExcelRegion>();
	
	public ExcelMatrix() {
	}
	
	/**
	 * List<Object> 转成二维数组
	 * @param objs 对象列表
	 * @param titles 属性中文名
	 * @param fields 属性
	 * @param seq 是否添加序号，1开头
	 * @return 二维数组
	 */
	public List<List<Object>> bean2Matrix(List<?> objs, List<String> titles, List<String> fields, boolean seq) {
		try {
			this.objs = objs;
			this.titles = titles;
			this.fields = fields;
			matrix = new ArrayList<List<Object>>();
			List<Object> column = new ArrayList<Object>();
			for (int i = 0; i < objs.size(); i++) {
				column.add(i+1);
				List<Object> row = new ArrayList<Object>();
//				Field[] objFields = objs.get(i).getClass().getDeclaredFields();
//				for (String field : fields) {
//					for (int j = 0; j < objFields.length; j++) {
//						Field objField = objFields[j];
//						objField.setAccessible(true);
//						if(field.equalsIgnoreCase(objField.getName())) {
//							row.add(objField.get(objs.get(i)));
//						}
//					}
//				}
				//通过get方法访问属性
				for (String field : fields) {
					String methodName = "get"+ field.substring(0,1).toUpperCase() + field.substring(1);
					Method getMethod = objs.get(i).getClass().getMethod(methodName, null);
					row.add(getMethod.invoke(objs.get(i), null));
				}
				matrix.add(i, row);
			}
			//添加序号
			if(seq) {
				this.addColumn(0, column);
			}
			//添加表头
			if(titles != null && !titles.isEmpty()) {
				List<Object> row = new ArrayList<Object>();
				row.addAll(titles);
				if(seq) {
					row.add(0,"");
				}
				matrix.add(0, row);
			}
			return matrix;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过注解方式进行排序导出操作
	 * @param objs
	 * @param clz
	 * @param seq
	 * @return
	 */
	public List<List<Object>> bean2Matrix(List<?> objs,Class clz, boolean seq) {
		List<ExcelHeader> headerList = getHeaderList(clz);
		// 排序
		Collections.sort(headerList);
		
		List<String> titles = new ArrayList<String>(headerList.size());
		List<String> fields = new ArrayList<String>(headerList.size());
		for (ExcelHeader header : headerList) {
			titles.add(header.getTitle());
			fields.add(header.getFieldName());
		}
		try {
			this.objs = objs;
			this.titles = titles;
			this.fields = fields;
			matrix = new ArrayList<List<Object>>();
			List<Object> column = new ArrayList<Object>();
			for (int i = 0; i < objs.size(); i++) {
				column.add(i+1);
				List<Object> row = new ArrayList<Object>();
//				Field[] objFields = objs.get(i).getClass().getDeclaredFields();
//				for (String field : fields) {
//					for (int j = 0; j < objFields.length; j++) {
//						Field objField = objFields[j];
//						objField.setAccessible(true);
//						if(field.equalsIgnoreCase(objField.getName())) {
//							row.add(objField.get(objs.get(i)));
//						}
//					}
//				}
				//通过get方法访问属性
				for (String field : fields) {
					String methodName = "get"+ field.substring(0,1).toUpperCase() + field.substring(1);
					Method getMethod = objs.get(i).getClass().getMethod(methodName, null);
					row.add(getMethod.invoke(objs.get(i), null));
				}
				matrix.add(i, row);
			}
			//添加序号
			if(seq) {
				this.addColumn(0, column);
			}
			//添加表头
			if(titles != null && !titles.isEmpty()) {
				List<Object> row = new ArrayList<Object>();
				row.addAll(titles);
				if(seq) {
					row.add(0,"");
				}
				matrix.add(0, row);
			}
			return matrix;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<ExcelHeader> getHeaderList(Class clz) {
		List<ExcelHeader> headerList = new ArrayList<ExcelHeader>();
		Field[] declaredFields = clz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(ExcelResource.class)) {
				ExcelResource excelResource = field.getAnnotation(ExcelResource.class);
				ExcelHeader header = new ExcelHeader(excelResource.title(),excelResource.order(), field.getName());;
				headerList.add(header);
			}
		}
		return headerList;
	}
	
	public List<List<Object>> getMatrix() {
		return matrix;
	}
	
	/**
	 * 设置第x列，y行的值
	 * @param x1
	 * @param y1
	 * @param obj
	 */
	public void setValue(int x1, int y1, Object obj) {
		if(obj == null || x1<0 || y1<0 || x1>=getWidth(y1) || y1>=getHeight())
			return;
		matrix.get(y1).set(x1, obj);
	}
	
	public List<Object> getNewRow() {
		List<Object> row = new ArrayList<Object>();
		matrix.add(row);
		return row;
	}
	
	//插入到第rowNo行，原来的rowNo行依次后移
	public void addRow(int rowNo, List<Object> row) {
		if(rowNo<0 || rowNo>getHeight())
			return;
		matrix.add(rowNo, row);
	}
	
	//追加到最后一行后面
	public void addRow(List<Object> row) {
		matrix.add(row);
	}
	
	/**
	 * 添加列数据
	 * @param columnNo
	 * @param column
	 */
	public void addColumn(int columnNo, List<Object> column) {
		if(columnNo<0 || columnNo>getMinWidth() || column.size() != getHeight())
			return;
		for (int i = 0; i < getHeight(); i++) {
			List<Object> row = (List<Object>) matrix.get(i);
			if(row != null)
				row.add(columnNo, column.get(i));
		}
	}
	
	public void addRegion(int x1, int y1, int x2, int y2) {
		regions.add(new ExcelRegion(x1, y1, x2, y2));
	}
	
	public void removeRegion(int x1, int y1, int x2, int y2) {
		ExcelRegion region = new ExcelRegion(x1, y1, x2, y2);
		regions.remove(region);
	}
	
	public ExcelRegion findRegion(int x1, int y1, int x2, int y2) {
		ExcelRegion region = new ExcelRegion(x1, y1, x2, y2);
		for (ExcelRegion element : regions) {
			if(element.equals(region))
				return element;
		}
		return null;
	}
	
	public List<ExcelRegion> getRegions() {
		return regions;
	}

	public int getHeight() {
		return matrix.size();
	}
	
	public int getWidth(int y) {
		int width = 0;
		if(getHeight()>0)
			width = matrix.get(y).size();
		return width;
	}
	
	public int getMinWidth() {
		int min = 0;
		for (List<Object> row : matrix) {
			if( row!= null && row.size() < min)
				min = row.size();
		}
		return min;
	}
	
	public int getMaxWidth() {
		int max = 0;
		for (List<Object> row : matrix) {
			if( row!= null && row.size() > max)
				max = row.size();
		}
		return max;
	}
	
	public ExcelRegion getHeader() {
		return header;
	}

	public void setHeader(ExcelRegion header) {
		this.header = header;
	}

	public ExcelRegion getFooter() {
		return footer;
	}

	public void setFooter(ExcelRegion footer) {
		this.footer = footer;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<?> getObjs() {
		return objs;
	}

	public void setObjs(List<?> objs) {
		this.objs = objs;
	}

	public void setMatrix(List<List<Object>> matrix) {
		this.matrix = matrix;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		int height = getHeight();
		int minWidth = getMinWidth();
		int maxWidth = getMaxWidth();
		buffer.append("Height:").append(height).append("\n");
		buffer.append("MinWidth:").append(minWidth).append("\n");
		buffer.append("MaxWidth:").append(maxWidth).append("\n");
		buffer.append("	");
		for (int j = 0; j < maxWidth; j++) {
			buffer.append("	").append(j);
		}
		buffer.append("\n");
		for (int i = 0; i < height; i++) {
			buffer.append("	").append(i);
			List<Object> row = matrix.get(i);
			if(row == null) {
				buffer.append("null").append("\n");
			}
			else {
				for (int j = 0; j < row.size(); j++) {
					buffer.append("	").append(row.get(j));
				}
				buffer.append("\n");
			}
		}
		return buffer.toString();
	}

}
