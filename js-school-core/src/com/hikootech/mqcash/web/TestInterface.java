package com.hikootech.mqcash.web;



import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.HkEncryptUtils;
import com.hikootech.mqcash.util.HttpClientUtil;
import com.hikootech.mqcash.web.BaseController;
@RequestMapping("/test")
@Controller
public class TestInterface  extends BaseController{
	
	String partnerId = "10000";
	String version = "1.00";
	String md5Key = "123456789";
	String desKey = "123456123456123456123456";
	String enc = "utf-8";
	
	/**  
	 * testCredit(测试征信)     
	 * void 
	 * @since  1.0.0
	 */

	public void testCredit(){
		
		Map<String, String> requestMap = new HashMap<String,String>();
		
		requestMap.put("userName", "寥寥");
		requestMap.put("idCard", "110101198001010010");
		requestMap.put("idCardAddress", "江苏省九江市九江县");
		requestMap.put("telNumber", "18646274955");	//入网信息中的用户联系电话
		requestMap.put("custIp", "202.102.111.146");	//用户访问网掌厅ip
		requestMap.put("custId", "270010000396");		//身份证对应的客户id（Cust_id）
		requestMap.put("pdInstId", "272254148376");		//联系电话对应的产品实例id（Pd_id）
		requestMap.put("source", "0");		//合作伙伴渠道（0-网厅，1-掌厅）
		requestMap.put("productId", "10625896232");	//商品在合作伙伴的唯一标识（具体参数以网厅数据为准）
		requestMap.put("productName", "长虹（CHANGHONG）48S1 48英寸内置wifi10核 安卓智能液晶电视");	//商品名称（手机名称）
		requestMap.put("productDesc", "由 京东 发货并提供售后服务。23:00点前完成下单,预计明天(11月29日)送达");	//商品描述
		requestMap.put("productPrice", "500000");		//商品价格（总价格=手机价格+号码价格），价格单位为：分
		requestMap.put("productCount", "1");	//商品数量（手机数量，默认1）
		requestMap.put("planType", "0");	//默认为手机合约机（0-手机合约机）
		requestMap.put("planPrice", "500000");	//套餐价格，单位为：分
	
			// 生成业务加密串
			String busParams = HkEncryptUtils.createEncryptBusParams(requestMap,desKey, enc);

			// 协议参数
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("timestamp",	DateUtil.getCurDateStr("yyyyMMddHHmmss"));
			paramMap.put("partnerId", partnerId);
			paramMap.put("version", version);
			paramMap.put("params", busParams);

			// 生成验签sign
			String sign = HkEncryptUtils.createMd5Sign(paramMap, md5Key, enc);
			paramMap.put("sign", sign);
			System.out.println(sign);
			System.out.println(DateUtil.getCurDateStr("yyyyMMddHHmmss"));
			HttpClientUtil http = new HttpClientUtil();	
			String ret = "";
			//String ss = "12588AF99E19E1B69297A5BDCC15F0FD7AE253F2B40834541554422CE2206B72CD16009345D033D34C02DDADA44D6B9179D09637C2118F7D9228033EF88A29069F6DBF2AC5430BDF4C620B58681080AF3E751F94F061E3B40AD28FE26E7937AE0378C1A0B141960137A97C0057C6E67E630D261679E8687ED3B81D1564DF3F0DD8D633EE902EBD72A287246FB1302C37419052C5314FAB17D866097F08F87DC332F474CF752073F360111D3330AA6232F3A43E31D444389E80CB5D7096EBB8B851FAB16052494735295594AA2C4C5BB02045327FD67AA8226187603EBB313B2978FD9B6AF3B2D569D229A0DC96A2B75D18AD350DD32BEC1F7F900FFE16560F243C98030A6D6DF95E789F38AC48A748FE66A2A85DF17A03EAD3E939B5B624A2B6E124FBEB6DA228007EC3CB1CDC8522DBB9A77CCAB1C899DBAC125C95E741FEDDF732407A6A57DBA70C22C5BA825FA15F9AA9C3438E5FF9B377652B7E86AFA827006255DE90578084666648CCD05EF5491E6DECA1F9FFFA697CE96722EDCDCB1D5E2D0ED653AF2A46,partnerId:189,sign:null,timestamp:20151130050353,version:89c9b17c07ca114e128c115432c5b0d4" ;
			try {
				ret = http.doPost("http://101.200.186.186:18080/getCoreCreditData.do", paramMap);
				//ret = http.doPost("http://111.204.49.235:8082/core/getCoreCreditData.do", paramMap);
				//ret = http.doPost("http://111.204.49.235:8082/core/getCoreCreditData.do", ss);
				//http.doPost("http://127.0.0.1:8080/getCoreCreditData.do", paramMap);
				testResult(ret);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**  
	 * validateMobile(发送银行卡预留手机号验证码接口)     
	 * void 
	 * @since  1.0.0
	 */
	public void testValidateMobile(){
		
		Map<String, String> requestMap = new HashMap<String,String>();
		requestMap.put("userName", "寥寥");
		requestMap.put("idCard", "110101198001010010");
		requestMap.put("idCardAddress", "江苏省九江市九江县");
		requestMap.put("telNumber", "18646274955");	//入网信息中的用户联系电话
		requestMap.put("custIp", "202.102.111.146");	//用户访问网掌厅ip
		requestMap.put("source", "0");				//合作伙伴渠道（0-网厅，1-掌厅）
		requestMap.put("bankCardNumber", "6228480841461627710");		//银行卡号
		requestMap.put("reserveMobile", "18168721051");		//银行卡预留手机号
		
		// 生成业务加密串
		String busParams = HkEncryptUtils.createEncryptBusParams(requestMap,desKey, enc);
		
		// 协议参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("timestamp",	DateUtil.getCurDateStr("yyyyMMddHHmmss"));
		paramMap.put("partnerId", partnerId);
		paramMap.put("version", version);
		paramMap.put("params", busParams);
		
		// 生成验签sign
		String sign = HkEncryptUtils.createMd5Sign(paramMap, md5Key, enc);
		paramMap.put("sign", sign);
		HttpClientUtil http = new HttpClientUtil();	
		String ret = "" ;
		try {
			//ret = http.doPost("http://111.204.49.235:8082/mqCashOrder/validateBindMobileNumber.do", paramMap);
			//ret = http.doPost("http://127.0.0.1:8080/mqCashOrder/validateBindMobileNumber.do", paramMap);
			ret = http.doPost("http://pay.mqcash.com:18180/mqCashOrder/validateBindMobileNumber.do", paramMap);
			testResult(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**  
	 * validateMobile(绑定银行卡接口)     
	 * void 
	 * @since  1.0.0
	 */
	public void testBindCard(){
		
		Map<String, String> requestMap = new HashMap<String,String>();
		requestMap.put("userName", "范凡");
		requestMap.put("idCard", "110101198001010272");
		requestMap.put("idCardAddress", "江苏省九江市九江县");
		requestMap.put("telNumber", "18646274955");	//入网信息中的用户联系电话
		requestMap.put("custIp", "202.102.111.146");	//用户访问网掌厅ip
		requestMap.put("source", "0");				//合作伙伴渠道（0-网厅，1-掌厅）
		requestMap.put("bankCardNumber", "6228480402564890018");		//银行卡号
		requestMap.put("reserveMobile", "18168721051");		//银行卡预留手机号
		requestMap.put("identifyingCode", "123456");		//发送给预留手机号的短信验证码(测试环境，默认123456)
		
		// 生成业务加密串
		String busParams = HkEncryptUtils.createEncryptBusParams(requestMap,desKey, enc);
		
		// 协议参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("timestamp",	DateUtil.getCurDateStr("yyyyMMddHHmmss"));
		paramMap.put("partnerId", partnerId);
		paramMap.put("version", version);
		paramMap.put("params", busParams);
		
		// 生成验签sign
		String sign = HkEncryptUtils.createMd5Sign(paramMap, md5Key, enc);
		paramMap.put("sign", sign);
		System.out.println(sign);
		HttpClientUtil http = new HttpClientUtil();	
		String ret = "";
		try {
			//ret =http.doPost("http://pay.mqcash.com:18180/mqCashOrder/bindBankCard.do", paramMap);
			ret = http.doPost("http://111.204.49.235:8082/mqCashOrder/bindBankCard.do", paramMap);
			testResult(ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**  
	 * validateMobile(确认分期接口)     
	 * void 
	 * @since  1.0.0
	 */
	public void testInstalment(){
		
		Map<String, String> requestMap = new HashMap<String,String>();
		requestMap.put("userName", "范凡");
		requestMap.put("idCard", "320121198906090099");
		requestMap.put("idCardAddress", "江苏省九江市九江县");
		requestMap.put("telNumber", "18646274955");	//入网信息中的用户联系电话
		requestMap.put("source", "0");				//合作伙伴渠道（0-网厅，1-掌厅）
		requestMap.put("bankCardId", "CRD20151201132805010002");//确定绑定银行卡成功
		requestMap.put("bankCardNumber", "6228480841461627710");		//银行卡号
		requestMap.put("reserveMobile", "18168721051");		//银行卡预留手机号
		requestMap.put("intalmentNum", "6");		//用户页面选择的分期数：3、6、9、12
		requestMap.put("orderPrice", "100000");			//订单金额（总金额），单位为：分
		requestMap.put("homeProvince", "江苏省");		//家庭地址所在省(中文名称)
		requestMap.put("homeCity", "镇江市");		
		requestMap.put("homeArea", "江宁");		
		requestMap.put("homeAddress", "山西省临汾市洪洞县洪洞镇");		
		requestMap.put("companyName", "阿里巴巴");		
		requestMap.put("companyProvince", "江苏省");		
		requestMap.put("companyCity", "苏州");		
		requestMap.put("companyArea", "六合");		
		requestMap.put("companyAddress", "黄浦大街1122号");		
		
		// 生成业务加密串
		String busParams = HkEncryptUtils.createEncryptBusParams(requestMap,desKey, enc);
		
		// 协议参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("timestamp",	DateUtil.getCurDateStr("yyyyMMddHHmmss"));
		paramMap.put("partnerId", partnerId);
		paramMap.put("version", version);
		paramMap.put("params", busParams);
		
		// 生成验签sign
		String sign = HkEncryptUtils.createMd5Sign(paramMap, md5Key, enc);
		paramMap.put("sign", sign);
		
		HttpClientUtil http = new HttpClientUtil();		
		String ret = "";
		try {
			//ret = http.doPost("http://pay.mqcash.com:18180/mqCashOrder/instalmentConfirm.do", paramMap);
			ret = http.doPost("http://111.204.49.235:8082/mqCashOrder/instalmentConfirm.do", paramMap);
			System.out.println("确认分期");
			testResult(ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**  
	 * validateMobile(测试同步订单接口)     
	 * void 
	 * @since  1.0.0
	 */
	public void testSyncOrder(){
		
		Map<String, String> requestMap = new HashMap<String,String>();
		requestMap.put("instalmentId", "MOR20151201132805010001");
		requestMap.put("userName", "范凡");
		requestMap.put("idCard", "320121198906090099");
		requestMap.put("idCardAddress", "江苏省九江市九江县");
		requestMap.put("telNumber", "18646274955");	//入网信息中的用户联系电话
		requestMap.put("custId", "270010000396");		//身份证对应的客户id（Cust_id）
		requestMap.put("pdInstId", "272254148376");		//联系电话对应的产品实例id（Pd_id）
		requestMap.put("source", "0");		//合作伙伴渠道（0-网厅，1-掌厅）
		
		
		
		requestMap.put("partnerOrderId", "SOW20151105710352");	//商品在合作伙伴的唯一标识（具体参数以网厅数据为准）
		requestMap.put("partnerOrderPrice", "100000");	//电信订单金额，单位为：分
		requestMap.put("partnerOrderUrl", "http://202.102.111.146:9184/service/order?orderNumber=SOW20151103967943");	//用户在电信的订单URL地址（具体参数以网厅数据为准）
		requestMap.put("partnerOrderTime", "20151120163923");	//用户在电信的下单时间
		requestMap.put("receiver", "张晓强");
		requestMap.put("receiverProvince", "江苏省");
		requestMap.put("receiverCity", "盐城市");
		requestMap.put("receiverArea", "江宁");
		requestMap.put("receiverAddress", "文昌街1022号");
		requestMap.put("email", "zhangqiang@163.com");
		requestMap.put("postCode", "115522");
		requestMap.put("receiverMobile", "13522287829");
		requestMap.put("receiverTelPhone", "0451-98765432");
		requestMap.put("productUrl", "http://202.102.111.146:9184/service/order");//商品的URL地址（具体参数以网厅数据为准）
		requestMap.put("productId", "106254");	//商品在合作伙伴的唯一标识（具体参数以网厅数据为准）
		requestMap.put("productName", "小米NOTE2");	
		requestMap.put("productDesc", "小米NOTE2");	
		requestMap.put("productPrice", "99987");	
		requestMap.put("productCount", "1");	//商品数量
		requestMap.put("planType", "0");	//默认为手机合约机（0-手机合约机）
		requestMap.put("planPrice", "100000");	//套餐价格，单位为：分
		// 生成业务加密串
		String busParams = HkEncryptUtils.createEncryptBusParams(requestMap,desKey, enc);
		
		// 协议参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("timestamp",	DateUtil.getCurDateStr("yyyyMMddHHmmss"));
		paramMap.put("partnerId", partnerId);
		paramMap.put("version", version);
		paramMap.put("params", busParams);
		
		// 生成验签sign
		String sign = HkEncryptUtils.createMd5Sign(paramMap, md5Key, enc);
		paramMap.put("sign", sign);
		HttpClientUtil http = new HttpClientUtil();	
		String ret = "" ;
		try {
			//http.doPost("http://pay.mqcash.com:18180/mqCashOrder/synchronousOrder.do", paramMap);
			ret = http.doPost("http://111.204.49.235:8082/mqCashOrder/synchronousOrder.do", paramMap);
			testResult(ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**  
	 * validateMobile(测试退单接口)     
	 * void 
	 * @since  1.0.0
	 */
	public void testRefundOrder(){
		
		Map<String, String> requestMap = new HashMap<String,String>();
		requestMap.put("refundSeq", "20151116175643T10002");//退款流水号（具体参数以网厅数据为准）
		requestMap.put("partnerOrderId", "SOW20151105710352");
		requestMap.put("orderId", "20151130125330010001");
		requestMap.put("refundType", "1");
		requestMap.put("refundReason", "1");	
		requestMap.put("refundDesc", "钱换多了啊啊啊啊啊啊啊啊啊啊");		
		requestMap.put("refundTime", "20151130125823");		
		requestMap.put("amount", "100000");		
		requestMap.put("paymentOrderSeq", "PYM20151107144641T100012");		
		
		
		
		// 生成业务加密串
		String busParams = HkEncryptUtils.createEncryptBusParams(requestMap,desKey, enc);
		
		// 协议参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("timestamp",	DateUtil.getCurDateStr("yyyyMMddHHmmss"));
		paramMap.put("partnerId", partnerId);
		paramMap.put("version", version);
		paramMap.put("params", busParams);
		
		// 生成验签sign
		String sign = HkEncryptUtils.createMd5Sign(paramMap, md5Key, enc);
		paramMap.put("sign", sign);
		HttpClientUtil http = new HttpClientUtil();		
		try {
			http.doPost("http://111.204.49.235:8082/mqCashOrder/orderRefund.do", paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**  
	 * validateMobile(测试打款接口)     
	 * void 
	 * @since  1.0.0
	 */
	@RequestMapping("/providerNotifyTest.do")
	@ResponseBody
	public String providerNotifyTest(HttpServletRequest request){
		
		Map<String, Object> requestMap = new HashMap<String,Object>();
		//通过资方id查询资方名称
/*		requestMap.put("transId", request.getParameter("transId"));
		requestMap.put("otherOrderId", request.getParameter("otherOrderId"));
		requestMap.put("orderId", request.getParameter("orderId"));
		requestMap.put("orderCost", request.getParameter("orderCost"));
		requestMap.put("payTrandId", request.getParameter("payTrandId"));
		requestMap.put("provider",  request.getParameter("provider"));
		requestMap.put("account", request.getParameter("account"));*/
		requestMap.put("success", true);
		requestMap.put("errorCode", "success");
		requestMap.put("errorMsg", "");
		
		JSONObject result = JSONObject.fromObject(requestMap);
		System.out.println(result);
		
		return result.toString();
		
		
	}
	
	public String testResult(String  ret){
		//String ret = "params=BAD63D2F58807E3D54CE9441D7B5FCA01E2681509FEAEE62C13D85FC6814B74AB9E9FCF4EE97C2B94DB619C0DB05BC5FC0538BC739BA26397E062EA959F6515ACB5C67204E8B0F6530BE2C5EA6CDF90A&partnerId=189&sign=f3a9fb1c4cea95b3cb2097d26976aff1&timestamp=20151127184442&version=1.00";
		//转换为Map
		Map<String,String> resultMap = HkEncryptUtils.stringToMap(ret);
		
		
		String bus_params_ret =  resultMap.get("params");
		String partner_id_ret = resultMap.get("partnerId");
		String sign_ret = resultMap.get("sign") ;
		String timestamp_ret = resultMap.get("timestamp");
		String version_ret = resultMap.get("version");
		System.out.println("查询征信数据，企信返回协议结果："  + ",params:" + bus_params_ret + ",partnerId" + partner_id_ret + 
				",sign" + sign_ret + ",timestamp" + timestamp_ret + 
				",version" + version_ret );
		
		//验签参数
		Map<String,String> signMap = new HashMap<String,String>();
			signMap.put("params", bus_params_ret);
			signMap.put("partnerId", partner_id_ret);
			signMap.put("timestamp", timestamp_ret);
			signMap.put("version", version_ret);
		
		String _sign = HkEncryptUtils.createMd5Sign(signMap, md5Key, enc);
		
		System.out.println("_sign:" + _sign);
		
		if(sign_ret.equals(_sign)){
			System.out.println("验签通过");
			Map<String, String> busMap = HkEncryptUtils.createDecryptBusMap(bus_params_ret, desKey, enc);
				if (busMap != null) {
					if ("0".equals(busMap.get("resultCode"))) {
						System.out.println("返回结果：" + busMap.get("resultCode") + ",描述：" + busMap.get("desc"));
					}else{
						System.out.println("返回结果：" + busMap.get("resultCode") + ",描述：" + busMap.get("desc"));
					}
					
				}
			
			
		}else{
			System.out.println("验签不通过,获得验签参数：" + sign_ret + ",生成验签参数：" + _sign);
		}
		return "";
	}
	public static void main(String[] args) {
		TestInterface test1 = new TestInterface();
		test1.testUnipay();
		///1.测试征信
		//test1.testCredit();
		//2.发送银行卡预留手机号验证码接口
		//test1.testValidateMobile();
		////3.绑定银行卡接口测试
		//test1.testBindCard();
		//4.确认分期
		//test1.testInstalment();
		//5.订单同步
		//test1.testSyncOrder();
		//6.退单接口测试
		//test1.testRefundOrder();
		//7.合同测试
		//test1.testContract();
		//8.测试返回结果
		//test1.testResult();
	}
	
	public void testUnipay(){
		
		Map<String, String> requestMap = new HashMap<String,String>();
		requestMap.put("cardNumber", "123456789");
		requestMap.put("idCard", "110101198001010272");
		requestMap.put("name", "范凡");
		requestMap.put("edScore", "12");
		requestMap.put("busId", "BID20160722155838010022");
		requestMap.put("cityCode", "320200");
		// 生成业务加密串
		String busParams = HkEncryptUtils.createEncryptBusParams(requestMap,desKey, enc);
		
		// 协议参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("timestamp",	DateUtil.getCurDateStr("yyyyMMddHHmmss"));
		paramMap.put("partnerId", partnerId);
		paramMap.put("version", version);
		paramMap.put("params", busParams);
		
		// 生成验签sign
		String sign = HkEncryptUtils.createMd5Sign(paramMap, md5Key, enc);
		paramMap.put("sign", sign);
		System.out.println(sign);
		HttpClientUtil http = new HttpClientUtil();	
		String ret = "";
		try {
			ret = http.doPost("http://127.0.0.1:8080/unipay/creditUnipay.do", paramMap);
			System.out.println(ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
