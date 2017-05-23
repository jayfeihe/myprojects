package com.hikootech.mqcash.util;

import java.io.Serializable;

public class Page implements Serializable{

	private static final long serialVersionUID = 1072503008613217572L;

	public static final int PAGESIZE = 10;
	
	/**
	 * 页面大小
	 */
	private int pageSize;

	/**
	 * 起始记录
	 */
	private int pageStart;
	private long totalRow;
	
	/**
	 * 是否需要分页
	 */
	private boolean needPage = true;
	
	public boolean getNeedPage() {
		return needPage;
	}

	public void setNeedPage(boolean needPage) {
		this.needPage = needPage;
	}

	public Page(int pageSize, int pageStart, long totalRow) {
		this(pageSize, pageStart);
		this.setTotalRow(totalRow);
	}

	public Page(int pageSize, int pageStart) {
		init(pageSize, pageStart);
	}

	public Page() {
		init(PAGESIZE, 0);
	}

	public Page(int pageNo) {
		if (pageNo < 1) {
			pageNo = 1;
		}
		int pageStart = (pageNo - 1) * PAGESIZE + getFirstPageNum();
		init(pageSize, pageStart);
	}

	/**
	 * @return
	 * @uml.property name="pageSize"
	 */
	
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return
	 * @uml.property name="pageStart"
	 */
	
	public int getPageStart() {
		return pageStart;
	}

	
	public int getPrevPageNum() {
		return isFirstPage() ? getFirstPageNum() : pageStart - pageSize;
	}

	
	public int getNextPageNum() {
		return isLastPage() ? pageStart : pageSize + pageStart;
	}

	
	public int getLastPageNum() {
		int totalPageImpl = getTotalPage(this.totalRow);
		return totalPageImpl == 1 ? getFirstPageNum() : (totalPageImpl - 1) * pageSize + getFirstPageNum();
	}

	
	public int getFirstPageNum() {
		return 0;
	}

	private int getTotalPage(long totalCount) {
		int count = (int) (totalCount / pageSize);
		if (totalCount % pageSize > 0)
			count++;
		if(count == 0){
			count++;
		}
		return count;
	}

	
	public int getTotalPage() {
		return getTotalPage(this.totalRow);
	}

	
	public int getCurrentPage() {
		return this.pageStart / this.pageSize + 1;
	}

	
	public boolean isLastPage() {
		return totalRow - pageStart < pageSize;
	}

	
	public boolean isFirstPage() {
		return pageStart - pageSize <= 0;
	}

	private void init(int pageSize, int pageStart) {
		setPageSize(pageSize);
		setPageStart(pageStart);
	}

	public Page(String pageSize, String pageStart) {
		int size = 0;
		int start = 0;
		try {
			size = Integer.parseInt(pageSize);
		} catch (NumberFormatException e) {
		}
		try {
			start = Integer.parseInt(pageStart);
		} catch (NumberFormatException e) {
		}
		init(size, start);
	}

	public long getTotalRow() {
		return totalRow;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize <= 0 ? PAGESIZE : pageSize;
	}
	
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart <= 0 ? 0 : pageStart;
	}

	public void setTotalRow(long totalRow) {
		this.totalRow = totalRow < 0 ? 0 : totalRow;
	}
	
	static public Page getPageByPageNo(int pageSize,int pageNo){
		if(pageSize<=0){
			pageSize=Page.PAGESIZE;
		}
		if(pageNo<=0){
			pageNo=1;
		}
		int pageStart=pageSize*(pageNo-1);
		Page page=new Page(pageSize,pageStart);
		return page;
	}
	
}
