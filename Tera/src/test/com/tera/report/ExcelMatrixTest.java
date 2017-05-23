package com.tera.report;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.tera.report.excel.ExcelMatrix;

/**
 * @author Wallace Chu
 *
 */
public class ExcelMatrixTest extends TestCase {
	
	ExcelMatrix excelMatrix;
	
	protected void setUp() throws Exception {
		super.setUp();
		excelMatrix = new ExcelMatrix();
		for (int i = 0; i < 5; i++) {
			List<Object> row = new ArrayList<Object>();
			for (int j = 0; j < 10; j++) {
				row.add(j, i + "*" + j);
			}
			excelMatrix.addRow(row);
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAddRow() {
		System.out.println(excelMatrix);
		List<Object> row = new ArrayList<Object>();
		for (int i = 0; i < 10; i++) {
			row.add(i, i + "");
		}
		excelMatrix.addRow(4, row);
		System.out.println(excelMatrix);
	}
	
	public void testAddColumn() {
		System.out.println(excelMatrix);
		List<Object> column = new ArrayList<Object>();
		for (int i = 0; i < 5; i++) {
			column.add(i, i + "");
		}
		excelMatrix.addColumn(0, column);
		System.out.println(excelMatrix);
	}
	
	public void testToString() {
		System.out.println(excelMatrix);
	}

}
