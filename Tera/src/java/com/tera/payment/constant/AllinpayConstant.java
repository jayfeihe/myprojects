/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.payment.constant;


public class AllinpayConstant {


	//测试环境配置
	///////////////////////在线网关支付////////////

	public final static String serverIp="http://ceshi.allinpay.com/gateway/index.do";	//第三方支付 IP
	public final static int inputCharset=1;								//字符集  	1代表utf-8
	public final static String pickupUrl="paycenter/payment/paydisplay.htm";			//取货地址
	public final static String receiveUrl="paycenter/payment/paydisplay.htm";			//商户系统通知地址
	public final static String version="v1.0";							//版本号
	public final static String merchantId="100020091218001";			//第三方支付  注册 商户号
	public final static int signType=1;									//签名类型
	public final static String key="1234567890";						// 第三方支付 用于 MD5 KEY
	public final static int payType=0;									// 支付方式 0代表未指定支付方式

	public final static String cerName="/TLCert.cer";					// 通联公钥
	
	////////////////////批量代付////////////
	
	public final static String daifuBussinessCode = "09900";  //代付业务代码,由通联提供 09900其他
	public final static String daishouBussinessCode = "19900";  //代收业务代码,由通联提供 其他
	public final static String merchantIdPay = "200604000000445";  //测试商户号
	public final static String password = "111111";                //密码
	//商户证书信息
	public final static String pfxPassword = "111111";             //证书密码
	public final static String pfxPath= "20060400000044502.p12";   //私钥证书名称
	public final static String tltcerPath= "allinpay-pds.cer";     //通联公钥证书名称
	public final static String userName = "20060400000044502";     //用户名

	public final static String URLhttps="https://113.108.182.3/aipg/ProcessServlet"; //支付请求地址

	/*
	//生产环境配置
	///////////////////////在线网关支付////////////
	
	public final static String serverIp="https://service.allinpay.com/gateway/index.do";	//第三方支付 IP
	public final static int inputCharset=1;								//字符集  	1代表utf-8
	public final static String pickupUrl="paycenter/payment/paydisplay.htm";			//取货地址
	public final static String receiveUrl="paycenter/payment/paydisplay.htm";			//商户系统通知地址
	public final static String version="v1.0";							//版本号
	public final static String merchantId="109070101308036";			//第三方支付  注册 商户号
	public final static int signType=1;									//签名类型
	public final static String key="1234567890";						// 第三方支付 用于 MD5 KEY
	public final static int payType=0;									// 支付方式 0代表未指定支付方式

	public final static String cerName="/TLCert.cer";					// 通联公钥
	
	
	////////////////////批量代付////////////
	
	public final static String daifuBussinessCode = "09900";  //代付业务代码,由通联提供 09900其他
	public final static String daishouBussinessCode = "19900";  //代收业务代码,由通联提供 其他
	public final static String merchantIdPay = "200100000011653";  //商户号
	public final static String password = "111111";                //密码
	//商户证书信息
	public final static String pfxPassword = "111111";             //证书密码
	public final static String pfxPath= "20010000001165304.p12";   //私钥证书名称
	public final static String tltcerPath= "allinpay-pds.cer";     //通联公钥证书名称
	public final static String userName = "20010000001165304";     //用户名
	
	public final static String URLhttps="https://tlt.allinpay.com/aipg/ProcessServlet"; //支付请求地址
	*/

}
