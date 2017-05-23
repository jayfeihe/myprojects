package com.tera.report.model.interform;

import java.util.List;

/**
 * 线上返回报表信息（包括分页数据）
 * @author QYANZE
 *
 */
public class ResultInfo {

	private int totalPage; // 总页数
	private int count; // 总记录数
	private int nowPage; // 当前页数
	private List<RepayData> data; // 还款信息

	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public List<RepayData> getData() {
		return data;
	}
	public void setData(List<RepayData> data) {
		this.data = data;
	}
}
