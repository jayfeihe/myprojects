package com.tera.report.model.interform;

/**
 * 参数实体类
 * @author QYANZE
 *
 */
public class RepayParam {

	private String project_id;       // 项目编号
	private String rate_start_time;  // 还款开始时间
	private String rate_end_time;    // 还款结束时间
	private String debt_type;        // 类型-0债权，１直投
	private int page_size;           // 每页显示数量-默认20,最大60
	private String export;           // 是否导出-1导出0不导出
	private int page;                // 页数
	
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getRate_start_time() {
		return rate_start_time;
	}
	public void setRate_start_time(String rate_start_time) {
		this.rate_start_time = rate_start_time;
	}
	public String getRate_end_time() {
		return rate_end_time;
	}
	public void setRate_end_time(String rate_end_time) {
		this.rate_end_time = rate_end_time;
	}
	public String getDebt_type() {
		return debt_type;
	}
	public void setDebt_type(String debt_type) {
		this.debt_type = debt_type;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	public String getExport() {
		return export;
	}
	public void setExport(String export) {
		this.export = export;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
}
