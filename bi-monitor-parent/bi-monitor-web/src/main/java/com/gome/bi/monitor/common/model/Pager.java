package com.gome.bi.monitor.common.model;

import java.util.Collection;

/**
 * 分页对象
 * 
 * @author QYANZE
 *
 */
public class Pager {
	/**
	 * 每页显示的条数
	 */
	private int pageSize;
	/**
	 * 从第几个开始
	 */
	private int pageOffset;
	/**
	 * 当前页数
	 */
	private int curPage;
	
	/**
	 * 共几页
	 */
	private int totalPage;
	
	/**
	 * 总记录条数
	 */
	private int total;
	/**
	 * 数据
	 */
	@SuppressWarnings("rawtypes")
	private Collection datas;

	/**
	 * 初始化
	 * 
	 * @param curPage
	 * @param totalCount
	 */
	public void init(int totalCount) {
		int _pageSize = SystemContext.getPageSize();
		int _curPage = SystemContext.getCurPage();
		this.pageSize = _pageSize;
		this.curPage = _curPage;
		
		int _pageOffset = _curPage * _pageSize - _pageSize;
		this.pageOffset = _pageOffset;
		this.total = totalCount;
		this.totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
	}
	
	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageOffset() {
		return this.pageOffset;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalPage() {
		return totalPage;
	}
	
	@SuppressWarnings("rawtypes")
	public Collection getDatas() {
		return this.datas;
	}

	@SuppressWarnings("rawtypes")
	public void setDatas(Collection datas) {
		this.datas = datas;
	}
	
	/*private int getTotalPage(long totalCount) {
		int count = (int) (totalCount / pageSize);
		if (totalCount % pageSize > 0)
			count++;
		if(count == 0){
			count++;
		}
		return count;
	}*/
}