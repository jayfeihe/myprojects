package com.hikootech.mqcash.po;

//好贷网 被信贷机构查询次数  500
public class UserHdxdRecord {

	private int type;// 类型 1：银行 2：小贷 3：P2P 4：消费金融 5：融资租赁 6：商业保理 7：担保公司 8：数据服务
	private int lastOneMonth;// 最近1个月
	private int lastThreemonth;// 最近3个月
	private int lastSixMonth;// 最近6个月
	private int lastYear;// 最近12个月

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLastOneMonth() {
		return lastOneMonth;
	}

	public void setLastOneMonth(int lastOneMonth) {
		this.lastOneMonth = lastOneMonth;
	}

	public int getLastThreemonth() {
		return lastThreemonth;
	}

	public void setLastThreemonth(int lastThreemonth) {
		this.lastThreemonth = lastThreemonth;
	}

	public int getLastSixMonth() {
		return lastSixMonth;
	}

	public void setLastSixMonth(int lastSixMonth) {
		this.lastSixMonth = lastSixMonth;
	}

	public int getLastYear() {
		return lastYear;
	}

	public void setLastYear(int lastYear) {
		this.lastYear = lastYear;
	}

}
