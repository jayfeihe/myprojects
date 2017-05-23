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

import java.util.Arrays;

/**
 * @author Tera
 */
public class ExcelReportTable implements ReportTable {

	/**
	 * 数据
	 */
	private Object[][] data = new Object[0][0];

	/**
	 * 统计数据，列长度与data相同
	 */
	private Object[][] statisticData = new Object[0][0];

	/**
	 * 标题
	 */
	private String title = "";

	/**
	 * 表头
	 */
	private String[] head = new String[0];

	/**
	 * 条件
	 */
	private String[] condition = new String[0];

	/**
	 * 注释
	 */
	private String[] comment = new String[0];

	/**
	 * heights
	 */
	private Integer[][] heights = new Integer[0][0];

	/**
	 * 冻结列
	 */
	private Integer frozenColumn;

	/**
	 * 冻结行
	 */
	private Integer frozenRow;

	/**
	 * 间隔行数
	 */
	private static final int INTERVAL = 1;

	/**
	 * 构造函数
	 * @param title title
	 * @param condition condition
	 * @param head head
	 * @param data data
	 * @param statisticData statisticData
	 * @param comment comment
	 */
	public ExcelReportTable(String title, String[] condition, String[] head,
			Object[][] data, Object[][] statisticData, String[] comment) {
		this.heights = new Integer[data.length][data[0].length];
		this.title = title;
		this.condition = condition;
		this.head = head;
		this.data = data;
		this.statisticData = statisticData;
		this.comment = comment;
	}

	/**
	 * 构造函数
	 * @param title title
	 * @param condition condition
	 * @param head head
	 * @param data data
	 * @param statisticData statisticData
	 */
	public ExcelReportTable(String title, String[] condition, String[] head,
			Object[][] data, Object[][] statisticData) {

		this.title = title;
		this.condition = combineCondition(condition);
		this.head = head;
		this.data = data;
		this.statisticData = statisticData;
		this.heights = new Integer[getRowCount()][getColumnCount()];
	}
	/**
	 * 构造函数
	 * @param title title
	 * @param condition condition
	 * @param head head
	 * @param data data
	 * @param statisticData statisticData
	 * @param frozenRow frozenRow
	 * @param frozenColumn frozenColumn
	 */
	public ExcelReportTable(String title, String[] condition, String[] head,
			Object[][] data, Object[][] statisticData, int frozenRow, int frozenColumn) {

		this.title = title;
		this.condition = combineCondition(condition);
		this.head = head;
		this.data = data;
		this.statisticData = statisticData;
		this.frozenRow = frozenRow;
		this.frozenColumn = frozenColumn;
		this.heights = new Integer[getRowCount()][getColumnCount()];
	}

	/**
	 * 构造函数
	 * @param title title
	 * @param condition condition
	 * @param head head
	 * @param data data
	 */
	public ExcelReportTable(String title, String[] condition, String[] head,
			Object[][] data) {

		this.title = title;
		this.condition = combineCondition(condition);
		this.head = head;
		this.data = data;
		this.heights = new Integer[getRowCount()][getColumnCount()];
	}

	/**
	 * 构造函数
	 * @param title title
	 * @param head head
	 * @param data data
	 */
	public ExcelReportTable(String title, String[] head, Object[][] data) {

		this.title = title;
		this.head = head;
		this.data = data;
		this.heights = new Integer[getRowCount()][getColumnCount()];
	}

	/**
	 * 构造函数
	 * @param title title
	 * @param data data
	 */
	public ExcelReportTable(String title, Object[][] data) {

		this.title = title;
		this.data = data;
		this.heights = new Integer[getRowCount()][getColumnCount()];
	}

	/**
	 * @return comment
	 */
	public String[] getComment() {
		return comment;
	}

	/**
	 * @param comment comment
	 */
	public void setComment(String[] comment) {
		this.comment = comment;
	}

	/**
	 * @return Object[][]
	 */
	public Object[][] getData() {
		return data;
	}

	/**
	 * @param data Object[][]
	 */
	public void setData(Object[][] data) {
		this.data = data;
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#getTitle()
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return String[]
	 */
	public String[] getHead() {
		return head;
	}

	/**
	 * @param head String[]
	 */
	public void setHead(String[] head) {
		this.head = head;
	}

	/**
	 * @return String[]
	 */
	public String[] getCondition() {
		return condition;
	}

	/**
	 * @param condition String[]
	 */
	public void setCondition(String[] condition) {
		this.condition = condition;
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#getCellHeight(int, int)
	 * @param row row
	 * @param column column
	 * @return int
	 */
	public int getCellHeight(int row, int column) {
		if (getHeight(row, column) != null) {
			return getHeight(row, column);
		}
		int height = 1;
		int rowCount = getRowCount();

		if (isData(row, column)) {
			Object obj = getObject(row, column);

			if (rowCount > 0 && obj != null && !"".equals(obj)) {
				if (column == 0
						|| (column > 0 && getCellHeight(row, column - 1) != 1)) {
					if (obj.equals(getObject(row - 1, column))) {
						if (column == 0) {
							setHeight(row, column, 0);
							return 0;
						} else {
							if (getCellHeight(row, column - 1) <= getCellHeight(
									row - 1, column - 1)) {
								setHeight(row, column, 0);
								return 0;
							}
						}
					}
					for (int i = row + 1; i < rowCount; i++) {
						if (obj.equals(getObject(i, column))) {
							if (column == 0) {
								height++;
							} else {
								Object preObj1 = getObject(row, column - 1);
								Object preObj2 = getObject(i, column - 1);
								int preHeight = getCellHeight(row, column - 1);
								if (preObj1.equals(preObj2)) {
									if (getCellHeight(row, column - 1) >= getCellHeight(
											i, column - 1)  && (preHeight == 0
													|| (preHeight > 0 && preHeight > height))) {
										height++;
									} else {
										break;
									}
								} else {
									break;
								}
							}

						} else {
							break;
						}
					}
					setHeight(row, column, height);
				}
			}
		}
		setHeight(row, column, height);
		return height;
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#getCellWidth(int, int)
	 * @param row row
	 * @param column column
	 * @return int
	 */
	public int getCellWidth(int row, int column) {

		int width = 1;
		int columnCount = getColumnCount();
		int colIndex = 0;
		if (isStatistic(row, column)) {
			Object obj = getObject(row, column);
//			if (!"合计".equals(obj)) {
//				return 1;
//			} else if (column > 0) {
//				return 0;
//			}
//			for (colIndex = column+1; colIndex < columnCount; colIndex++) {
//				Object object = getObject(row, colIndex);
//				if ("合计".equals(object)) {
//					width++;
//				}
//			}

			if (obj instanceof String) {
				for (colIndex = column + 1; colIndex < columnCount; colIndex++) {
					Object object = getObject(row, colIndex);
					if (obj.equals(object)) {
						width++;
					} else {
						return width;
					}
				}
			} else {
				return 1;
			}
			return width;
		}
		return 1;
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#getColumnCount()
	 * @return 列数
	 */
	public int getColumnCount() {
		if (data.length == 0) {
			return head.length;
		} else {
			return Math.max(head.length, data[0].length);
		}
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#getObject(int, int)
	 * @param row 行数
	 * @param column 列数
	 * @return Cell对象
	 */
	public Object getObject(int row, int column) {
		if (row < 0 || row >= getRowCount() || column < 0
				|| column >= getColumnCount()) {
			return null;
		} else {
			if (row < condition.length) {
				if (column > 0) {
					return null;
				} else {
					return condition[row];
				}
			} else if (row == condition.length + INTERVAL) {
				if (head.length == 0) {
					return null;
				} else {
					return head[column];
				}
			} else if (row > condition.length + INTERVAL
					&& row < condition.length + INTERVAL + 1 + data.length) {
				return data[row - (condition.length + INTERVAL + 1)][column];
			} else if (row >= condition.length + INTERVAL + 1 + data.length
					&& row < condition.length + INTERVAL + 1 + data.length
							+ statisticData.length) {
				return statisticData[row
						- (condition.length + INTERVAL + 1 + data.length)][column];
			} else if (row > condition.length + INTERVAL + 1 + data.length
					+ statisticData.length) {
				if (column > 0) {
					return null;
				} else {
					return comment[row
							- (condition.length + INTERVAL + 1 + data.length + statisticData.length)];
				}
			} else {
				return null;
			}
		}
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#getRowCount()
	 * @return 行数
	 */
	public int getRowCount() {
		if (comment.length == 0) {
			return condition.length + INTERVAL + 1 + data.length
					+ statisticData.length;
		} else {
			return condition.length + INTERVAL + 1 + data.length
					+ statisticData.length + INTERVAL + comment.length;
		}
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#isData(int, int)
	 * @param row 行数
	 * @param column 列数
	 * @return 是否数据
	 */
	public boolean isData(int row, int column) {
		if (getObject(row, column) == null) {
			return false;
		} else {
			if (row > condition.length + INTERVAL
					&& row < condition.length + INTERVAL + 1 + data.length) {
				return true;
			} else {
				return false;
			}
		}
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#isCondition(int, int)
	 * @param row 行数
	 * @param column 列数
	 * @return 是否条件
	 */
	public boolean isCondition(int row, int column) {
		return Arrays.asList(condition).contains(getObject(row, column));
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#isHead(int, int)
	 * @param row 行数
	 * @param column 列数
	 * @return 是否表头
	 */
	public boolean isHead(int row, int column) {
		return Arrays.asList(head).contains(getObject(row, column));
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#isStatistic(int, int)
	 * @param row 行数
	 * @param column 列数
	 * @return boolean
	 */
	public boolean isStatistic(int row, int column) {
		if (getObject(row, column) == null) {
			return false;
		} else {
			if (row >= condition.length + INTERVAL + 1 + data.length
					&& row < condition.length + INTERVAL + 1 + data.length
							+ statisticData.length) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * @return Object[][]
	 */
	public Object[][] getStatisticData() {
		return statisticData;
	}

	/**
	 * @param statisticData Object[][]
	 */
	public void setStatisticData(Object[][] statisticData) {
		this.statisticData = statisticData;
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#isComment(int, int)
	 * @param row 行数
	 * @param column 列数
	 * @return boolean
	 */
	public boolean isComment(int row, int column) {
		return Arrays.asList(comment).contains(getObject(row, column));
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#getFrozenColumn()
	 * @return Integer
	 */
	public Integer getFrozenColumn() {
		return frozenColumn;
	}

	/**
	 * @param frozenColumn frozenColumn
	 */
	public void setFrozenColumn(Integer frozenColumn) {
		this.frozenColumn = frozenColumn;
	}

	/** (non-Javadoc)
	 * @see com.xyb.oms.sys.view.ReportTable#getFrozenRow()
	 * @return Integer
	 */
	public Integer getFrozenRow() {
		return frozenRow;
	}

	/**
	 * @param frozenRow frozenRow
	 */
	public void setFrozenRow(Integer frozenRow) {
		this.frozenRow = frozenRow;
	}

	/**
	 * combineCondition
	 * @param condition String[]
	 * @return String[]
	 */
	public String[] combineCondition(String[] condition) {
		StringBuffer sb = new StringBuffer();
		if (condition == null || condition.length == 0) {
			return new String[0];
		} else {
			for (int length = 0; length < condition.length; length++) {
				if (length != 0) {
					sb.append("\n");
				}
				sb.append(condition[length]);
			}
			return new String[]{sb.toString()};
		}
	}

	/**
	 * getHeight
	 * @param i i
	 * @param j j
	 * @return Integer
	 */
	private Integer getHeight(int i, int j) {
		return this.heights[i][j];
	}

	/**
	 * setHeight
	 * @param i i
	 * @param j j
	 * @param value value
	 */
	private void setHeight(int i, int j, int value) {
		this.heights[i][j] = value;
	}

}
