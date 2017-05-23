package com.greenkoo.dmp.sys.model;

import java.util.Collection;

/**
 * 分页对象
 * 
 * @author QYANZE
 *
 */
public class Pager<T> {
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
	private long totalPage;
	
	/**
	 * 总记录条数
	 */
	private long total;
	/**
	 * 数据
	 */
	private Collection<T> datas;

	/**
	 * 初始化
	 * 
	 * @param curPage
	 * @param totalCount
	 */
	public void init(long totalCount) {
		int _pageSize = SystemContext.getPageSize();
		int _pageOffset = SystemContext.getPageOffset();
		this.pageSize = _pageSize;

//		this.curPage = _curPage;
//		this.pageOffset = this.curPage * this.pageSize - this.pageSize;
		this.pageOffset = _pageOffset;
		this.total = totalCount;
		this.totalPage = (totalCount / this.pageSize + (totalCount % this.pageSize == 0 ? 0 : 1));
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

	public long getTotal() {
		return this.total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getTotalPage() {
		return totalPage;
	}
	
	public Collection<T> getDatas() {
		return this.datas;
	}

	public void setDatas(Collection<T> datas) {
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