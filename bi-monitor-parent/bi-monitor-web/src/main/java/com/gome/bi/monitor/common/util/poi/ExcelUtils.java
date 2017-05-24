package com.gome.bi.monitor.common.util.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 该类实现了将一组对象转换为Excel表格，并且可以从Excel表格中读取到一组List对象中 该类利用了BeanUtils框架中的反射完成 使用该类的前提，在相应的实体对象上通过ExcelReources来完成相应的注解
 * 
 * @author QYZ
 *
 */
@SuppressWarnings({"rawtypes"})
public class ExcelUtils {
	/**
	 * 一次写入内存行数
	 */
	public static final int rowAccessWindowSize = 10000;

	/**
	 * 数据行标识
	 */
	public final String DATA_LINE = "datas";

	private static final ExcelUtils eu = new ExcelUtils();

	private ExcelUtils() {}

	public static ExcelUtils getInstance() {
		return eu;
	}

	/**
	 * 根据标题获取相应的方法名称
	 * 
	 * @param eh
	 * @return
	 */
	private String getMethodName(ExcelHeader eh) {
		String mn = eh.getMethodName().substring(3);
		mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
		return mn;
	}

	private SXSSFWorkbook handleObj2Excel(String sheetName, List objs, Class clz) {
		SXSSFWorkbook wb = null;
		try {
			wb = new SXSSFWorkbook(rowAccessWindowSize);
			Sheet sheet = wb.createSheet(sheetName);
			Row r = sheet.createRow(0);
			List<ExcelHeader> headers = getHeaderList(clz);
			Collections.sort(headers);
			// 写标题
			for (int i = 0; i < headers.size(); i++) {
				r.createCell(i).setCellValue(headers.get(i).getTitle());
			}
			// 写数据
			Object obj = null;
			for (int i = 0; i < objs.size(); i++) {
				r = sheet.createRow(i + 1);
				obj = objs.get(i);
				for (int j = 0; j < headers.size(); j++) {
					r.createCell(j).setCellValue(
							BeanUtils.getProperty(obj, getMethodName(headers.get(j))));
				}
			}
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return wb;
	}

	/**
	 * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于路径的导出
	 * 
	 * @param outPath 导出路径
	 * @param sheetName sheet名
	 * @param objs 对象列表
	 * @param clz 对象类型
	 */
	public void exportObj2Excel(String outPath, String sheetName, List objs, Class clz) {
		SXSSFWorkbook wb = handleObj2Excel(sheetName, objs, clz);

		FileOutputStream fos = null;
		try {
			File f = new File(outPath);
			if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
			if (!f.exists()) f.createNewFile();

			fos = new FileOutputStream(f);
			wb.write(fos);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (fos != null) fos.close();
				if (wb != null) wb.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于流
	 * 
	 * @param os 输出流
	 * @param sheetName sheet名
	 * @param objs 对象列表
	 * @param clz 对象类型
	 */
	public void exportObj2Excel(OutputStream os, String sheetName, List objs, Class clz) {
		SXSSFWorkbook wb = null;
		try {
			wb = handleObj2Excel(sheetName, objs, clz);
			wb.write(os);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (wb != null) wb.dispose();
		}
	}

	/**
	 * 根据模板导出excel
	 * 
	 * @param os 输出流
	 * @param template 模板文件路径
	 * @param objs 数据集
	 * @param clz 目标对象
	 */
	public void exportObj2ExcelByTemplate(OutputStream os, String template, List objs, Class clz) {
		SXSSFWorkbook wb = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new File(template));
			wb = new SXSSFWorkbook(workbook, rowAccessWindowSize, true);
			Sheet sheet = workbook.getSheetAt(0);
			int startRowNum = 0;
			int startColumnIndex = 0;
			boolean isFindData = false;
			for (Row row : sheet) {
				if (isFindData) {
					break;
				}
				for (Cell cell : row) {
					if (DATA_LINE.equals(cell.getStringCellValue())) {
						startRowNum = row.getRowNum();
						startColumnIndex = cell.getColumnIndex();
						isFindData = true;
						sheet.removeRow(row);
						break;
					} else {
						startRowNum++;
					}
				}
			}

			List<ExcelHeader> headers = getHeaderList(clz);
			Collections.sort(headers);
			for (int i = 0; i < objs.size(); i++) {
				Row row = sheet.createRow(startRowNum + i);
				for (int j = 0; j < headers.size(); j++) {
					Cell cell = row.createCell(startColumnIndex + j);
					cell.setCellValue(
							BeanUtils.getProperty(objs.get(i), getMethodName(headers.get(j))));
				}
			}

			wb.write(os);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		finally {
			if (wb != null) {
				wb.dispose();
			}
		}

	}

	/**
	 * 从文件路径读取相应的Excel文件到对象列表
	 * 
	 * @param path 文件路径下的path
	 * @param clz 对象类型
	 * @param readLine 开始行，注意是标题所在行
	 * @param tailLine 底部有多少行，在读入对象时，会减去这些行
	 * @return
	 */
	public List<Object> readExcel2Objs(String path, Class clz, int readLine, int tailLine) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(path);
			SXSSFWorkbook wb = new SXSSFWorkbook(workbook, rowAccessWindowSize, true);
			return handlerExcel2Objs(wb, clz, readLine, tailLine);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从文件路径读取相应的Excel文件到对象列表，标题行为0，没有尾行
	 * 
	 * @param path 路径
	 * @param clz 类型
	 * @return 对象列表
	 */
	public List<Object> readExcel2Objs(String path, Class clz) {
		return this.readExcel2Objs(path, clz, 0, 0);
	}

	private String getCellValue(Cell c) {
		String o = null;
		switch (c.getCellTypeEnum()) {
			case BLANK:
				o = "";
				break;
			case BOOLEAN:
				o = String.valueOf(c.getBooleanCellValue());
				break;
			case FORMULA:
				o = String.valueOf(c.getCellFormula());
				break;
			case NUMERIC:
				o = String.valueOf(c.getNumericCellValue());
				break;
			case STRING:
				o = c.getStringCellValue();
				break;
			default:
				o = null;
				break;
		}
		return o;
	}

	private List<Object> handlerExcel2Objs(SXSSFWorkbook wb, Class clz, int readLine,
			int tailLine) {
		Sheet sheet = wb.getSheetAt(0);
		List<Object> objs = null;
		try {
			Row row = sheet.getRow(readLine);
			objs = new ArrayList<Object>();
			Map<Integer, String> maps = getHeaderMap(row, clz);
			if (maps == null || maps.size() <= 0)
				throw new RuntimeException("要读取的Excel的格式不正确，检查是否设定了合适的行");
			for (int i = readLine + 1; i <= sheet.getLastRowNum() - tailLine; i++) {
				row = sheet.getRow(i);
				Object obj = clz.newInstance();
				for (Cell c : row) {
					int ci = c.getColumnIndex();
					String mn = maps.get(ci).substring(3);
					mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
					BeanUtils.copyProperty(obj, mn, this.getCellValue(c));
				}
				objs.add(obj);
			}
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		finally {
			if (wb != null) wb.dispose();
		}
		return objs;
	}

	private List<ExcelHeader> getHeaderList(Class clz) {
		List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			String fn = field.getName();
			if (field.isAnnotationPresent(ExcelResources.class)) {
				ExcelResources er = field.getAnnotation(ExcelResources.class);
				String methodName = "get" + fn.substring(0, 1).toUpperCase() + fn.substring(1);
				headers.add(new ExcelHeader(er.title(), er.order(), methodName));
			}
		}
		return headers;
	}

	private Map<Integer, String> getHeaderMap(Row titleRow, Class clz) {
		List<ExcelHeader> headers = getHeaderList(clz);
		Map<Integer, String> maps = new HashMap<Integer, String>();
		for (Cell c : titleRow) {
			String title = c.getStringCellValue();
			for (ExcelHeader eh : headers) {
				if (eh.getTitle().equals(title.trim())) {
					maps.put(c.getColumnIndex(), eh.getMethodName().replace("get", "set"));
					break;
				}
			}
		}
		return maps;
	}
}
