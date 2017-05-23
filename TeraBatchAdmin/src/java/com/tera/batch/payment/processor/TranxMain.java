/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：系统对接demo
 */
package com.tera.batch.payment.processor;

import java.util.Date;

import com.tera.payment.service.AllinPayTranxService;
import com.tera.util.DateUtils;

/**
 */
public class TranxMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		String URL8083="http://113.108.182.4:8083/aipg/ProcessServlet"; //
//		String URL11="http://172.16.1.11:8080/aipg/ProcessServlet"; //
//		
//		String URL="http://tlt.allinpay.com/aipg/ProcessServlet";
//		String NOTICEURL="http://116.52.144.99:11021"; //
//		String URLbill="http://172.16.1.11:8080/aipg/GetConFile.do?SETTDAY=@xxx&REQTIME=@yyy&MERID=@zzz&SIGN=@sss"; //
		
		String URLhttps="https://113.108.182.3/aipg/ProcessServlet"; //有效
		boolean isfront=false;//是否发送至前置机（由前置机进行签名）
		String reqsn="200604000000445-"+System.currentTimeMillis();//交易流水号
		AllinPayTranxService tranxService=new AllinPayTranxService();
		
	//	tranxService.batchDaiShou(URLhttps, isfront);
		
		//tranxService.downBills(URL11, isfront);
		//签约结果通知
//		tranxService.signNotice(NOTICEURL, isfront);
		//简单对账文件下载
//		tranxService.downSimpleBills(URLbill, true);
		//交易查询
		//tranxService.QueryTradeNew(URLhttps, reqsn, isfront);
		//单笔实时代付
	//	tranxService.singleDaiFushi(URL11, isfront);
		Date dt=DateUtils.getDateTimeNow();
		
		System.out.print(DateUtils.toString(dt, "yyyyMMddHHmmss"));
	}

}
