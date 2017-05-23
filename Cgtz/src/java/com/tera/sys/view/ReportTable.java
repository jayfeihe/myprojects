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

/**
 * @author Tera
 */
public interface ReportTable {

	/**
	 * @return 行数
	 */
	int getRowCount();
	/**
	 * @return 列数
	 */
	int getColumnCount();

	/**
	 * Cell宽度
	 * @param row 行数
	 * @param column 列数
	 * @return 宽度
	 */
	int getCellWidth(int row, int column);

	/**
	 * Cell高度
	 * @param row 行数
	 * @param column 列数
	 * @return 高度
	 */
	int getCellHeight(int row, int column);

	/**
	 * Cell对象
	 * @param row 行数
	 * @param column 列数
	 * @return Cell对象
	 */
	Object getObject(int row, int column);

	/**
	 * 数据
	 * @param row 行数
	 * @param column 列数
	 * @return 是否数据
	 */
	boolean isData(int row, int column);

	/**
	 * 表头
	 * @param row 行数
	 * @param column 列数
	 * @return 是否表头
	 */
	boolean isHead(int row, int column);

	/**
	 * 条件
	 * @param row 行数
	 * @param column 列数
	 * @return 是否条件
	 */
	boolean isCondition(int row, int column);

	/**
	 * 统计数据
	 * @param row 行数
	 * @param column 列数
	 * @return 是否统计数据
	 */
	boolean isStatistic(int row, int column);

	/**
	 * 注释
	 * @param row 行数
	 * @param column 列数
	 * @return 是否注释
	 */
	boolean isComment(int row, int column);

	/**
	 * @return 冻结行数
	 */
	Integer getFrozenRow();

	/**
	 * @return 冻结列数
	 */
	Integer getFrozenColumn();

	/**
	 * @return 获取标题
	 */
	String getTitle();

}
