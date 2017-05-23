package com.tera.payment.fuyou.model;

import java.util.ArrayList;
import java.util.List;


/**
 * 富有支付 请求结果 XML 实体
 * @author XunXiake
 *
 */
public class ResultBean {

	private String ret;  //返回信息 编码
	private String memo;  //返回信息说明
	private String reqtype;
	private List<QueryResultBean> tranList=new ArrayList<QueryResultBean>();
	
	public ResultBean(){ }
	
	public ResultBean(String ret,String memo,String reqtype){
		this.ret=ret;
		this.memo=memo;
		this.reqtype=reqtype;
	}
	
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getReqtype() {
		return reqtype;
	}
	public void setReqtype(String reqtype) {
		this.reqtype = reqtype;
	}

	public List<QueryResultBean> getTranList() {
		return tranList;
	}

	public void tranListAdd(QueryResultBean queryResult) {
		tranList.add(queryResult);
	}
	
	public String getMsg(){
		return "代码："+ret+",说明："+memo;
	}
	
	
}

