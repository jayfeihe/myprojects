package com.tera.report.excel;

/**
 * @author Wallace Chu
 * 按照横轴X，向右为正方向；纵轴Y，向下为正方向；
 * 所有坐标遵循此约定
 */
public class ExcelRegion {
	
	private int x1;
	
	private int y1;
	
	private int x2;
	
	private int y2;
	
	private Object value;
	
	public ExcelRegion(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public ExcelRegion(int x1, int y1, int x2, int y2, Object value) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.value = value;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof ExcelRegion))
			return false;
		ExcelRegion region = (ExcelRegion)obj;
		if(region.x1 == this.x1 && region.y1 == this.y1 
				&& region.x2 == this.x2 && region.y2 == this.y2)
			return true;
		else
			return false;
	}

}
