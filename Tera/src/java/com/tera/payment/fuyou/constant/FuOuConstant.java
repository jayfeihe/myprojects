/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.payment.fuyou.constant;


public class FuOuConstant {

	/*
	 * 测试环境配置
	 * http://wwwtest.fuiou.com:8992/fuMer/
	 * 0002900F0345190
	 * zha049
	 * 888888
	 */
	public final static String URL="http://wwwtest.fuiou.com:8992/fuMer/req.do";
	public final static String MERID="0002900F0345190";
	public final static String KEY="123456";
	
	/*生产环境
	public final static String URL="https://fht.fuiou.com/req.do";
	public final static String MERID="0001000F0278469";
	public final static String KEY="hgfkytkfylkjokytuoigkjouydjshada";
	*/

	//付款请求TYPE　　单笔最高500万   
	public final static String PAY_REQ_TYPE="payforreq";
	
	//查询 TYPE
	public final static String QUERY_TYPE="qrytransreq";	//查询 TYPE
	
	//查询返回结果  列表 
	public final static String QUERY_RESULT_TYPE="trans";
	
	//代收异步请求 TYPE
//	public final static String INCOME_REQ_TYPE="incomeforreq";
	//代收 同步请求 TYPE 单笔最高50万  手续费5元
	public final static String SINCOME_REQ_TYPE="sincomeforreq";
	
	
	
}
