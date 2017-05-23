package com.tera.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * @author wallace
 * 
 */
public class PoiUtil {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(PoiUtil.class);
	
	private XWPFDocument document;

	/**
	 * @return
	 */
	public XWPFDocument getDocument() {
		return document;
	}

	/**
	 * 从ClassPath中获取Docx文件
	 * @param templatePath 路径
	 */
	public void setClassPathDocument(String templatePath) {
		try {
			this.document = new XWPFDocument(PoiUtil.class.getResourceAsStream("/" + templatePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从文件系统中获取Docx文件
	 * @param templatePath 路径
	 */
	public void setDocument(String templatePath) {
		try {
			this.document = new XWPFDocument(POIXMLDocument.openPackage(templatePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 替换word中的文字
	 * @param mapValue 替换的字符串
	 */
	public void replaceTextToText(Map<String, String> mapValue) {
		//得到段落信息
		List<XWPFParagraph> listParagraphs = document.getParagraphs();
		for (int i = 0; i < listParagraphs.size(); i++) {
			XWPFParagraph paragraph = listParagraphs.get(i);
			List<XWPFRun> listRun = listParagraphs.get(i).getRuns();
//			log.debug("Paragraph====" + listParagraphs.get(i).getText());
			for (int j = 0; j < listRun.size(); j++) {
				log.debug("XWPFRun:" + listRun.get(j).getText(0));
				if (mapValue.get(listRun.get(j).getText(0)) != null
						|| "null".equals(mapValue .get(listRun.get(j).getText(0)))) {
					listRun.get(j).setText(mapValue.get(listRun.get(j).getText(0)), 0);
				}
			}
		}
	}

	/**
	 * 插入表格信息，第一行为表头
	 * @param list 参数列表
	 * @param position table的序号
	 * @param columnNum 列数
	 */
	public void setTablesValue(List<List<String>> list, int position) {
		Iterator<XWPFTable> it = document.getTablesIterator();
		int k = 0; // 为0的时候表示Word第一个table 并且循环Set 行
		while (it.hasNext()) {
			XWPFTable table = (XWPFTable) it.next();
			// 根据List创建行
			if (k == position) {
				for (int i = 0; i < list.size(); i++) {
					XWPFTableRow row = table.createRow();
					List val = list.get(i);
					List<XWPFTableCell> cells = row.getTableCells();
					for (int j = 0; j < cells.size(); j++) {
						cells.get(j).setText((String) val.get(j));
					}
				}
			}
			k++;
		}
	}

	/**
	 * 获取Docx的输出字节流
	 * @return 输出字节流
	 */
	public byte[] getDocx() {
		try {
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			document.write(ostream);
			return ostream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}