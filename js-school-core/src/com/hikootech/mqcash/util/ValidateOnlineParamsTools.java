package com.hikootech.mqcash.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikootech.mqcash.constants.StatusConstants;

/**
 * @author yuwei
 * 2015年8月6日
 * 校验工具类
 */
public class ValidateOnlineParamsTools {
	
	private static Logger log = LoggerFactory.getLogger(ValidateOnlineParamsTools.class);


	public static Map<String, Object> validateInterfaceParams(String responseValue){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		String partnerId = "";			//合作伙伴id
		String responseValueParams = "";
		
		if (EntityUtils.isNotEmpty(responseValue)) {
			 
			log.info("电渠推送支付信息解密前：" + responseValue);
			
			partnerId = responseValue.split("\\$")[0];
			log.info("接收网厅支付接口参数：" + "partnerId:" + partnerId);

			 responseValueParams = UrlEncodeForPay.deCodingForPay(responseValue.split("\\$")[1], ConfigUtils.getProperty("online_hall_key"),"UTF-8");
			 log.info("电渠推送支付信息解密后：" + responseValueParams + ",合作伙伴id：" + partnerId);
			 
			 //验证参数成功
			try {
				if (UrlEncodeForPay.signDigst(responseValueParams)) {
					//得到各数据
					if (EntityUtils.isNotEmpty(responseValueParams)) {
						responseValueParams =responseValueParams  + "$" + partnerId;
						String[] responseValueArr = responseValueParams.split("\\$");
						retMap.put("code", responseValueArr);
					}
					
				}else{
					 log.info("电渠推送分期付款信息接收参数：验证参数失败");
						retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
						retMap.put("desc", "signDigst is error");
						
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("电渠推送分期付款信息接收参数：验证参数异常");
				retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
				retMap.put("desc", "RequestValue is parseException");

			}
		}else{
			 log.info("记录人员信息接收网厅支付接口参数：请求参数为空");
				retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
				retMap.put("desc", "RequestValue is null");
				
		}
		return retMap;
	}
	
		
	public static Map<String,Object> parseArray(String[] responseValueArr){
		
		String partnerNotifyUrl = "";	//后台通知的URL地址
		String partnerReturnUrl = "";	//结果返回的URL地址
		String userName =  "";		//用户姓名
		String partnerIdCard =  "";		//用户身份证
		String partneridCardAddress =  "";	//身份证地址
		String partnerTelNumber =  "";		//联系电话
		String partnerOrderId =  "";	//电信平台的订单号
		String partnerOrderAmount = "";	//订单价格
		String partnerOrderUrl =  "";	//用户在电信的订单URL地址
		String partnerOrderTime =  "";	//下单时间
		String receiver =  "";			//收货人
		String receiverCity =  "";		//收货城市
		String receiverArea =  "";		//收货区域
		String receivingAddress =  "";	//详细地址		
		String email =  "";				//用户下单时填写的电子邮箱
		String postCode =  "";			//用户下单时填写的邮编		
		String receiverMobile =  "";	//收货固定电话
		String receiverTelephone = "";	//收货手机号码
		String goodsUrl =  "";			//商品地址		
		String goodsNum =  "";			//商品ID			
		String goodsName =  "";			//商品名称
		String goodsDescp =  "";			//商品描述
		String goodsPrice =  "";		//商品价格		
		String goodsCount =  "";		//商品数量		
		String partnerTransactionId =  "";	//电信方发起支付请求流水号		
		String receiverProvince =  ""; //
		String partnerId =  ""; //
		
		
		partnerNotifyUrl = responseValueArr[0];
		partnerReturnUrl = responseValueArr[1];
		userName = responseValueArr[2];
		partnerIdCard = responseValueArr[3];
		partneridCardAddress = responseValueArr[4];
		partnerTelNumber = responseValueArr[5];
		partnerOrderId = responseValueArr[6];
		partnerOrderAmount = responseValueArr[7];
		partnerOrderUrl = responseValueArr[8];
		partnerOrderTime = responseValueArr[9];
		receiver = responseValueArr[10];
		receiverCity = responseValueArr[11];
		receiverArea = responseValueArr[12];
		receivingAddress = responseValueArr[13];
		email = responseValueArr[14];
		postCode = responseValueArr[15];
		receiverMobile = responseValueArr[16];
		receiverTelephone = responseValueArr[17];
		goodsUrl = responseValueArr[18];
		goodsNum = responseValueArr[19];
		goodsName = responseValueArr[20];
		goodsDescp = responseValueArr[21];
		goodsPrice = responseValueArr[22];
		goodsCount = responseValueArr[23];
		partnerTransactionId = responseValueArr[24];
		receiverProvince = responseValueArr[25];
		partnerId = responseValueArr[27];
		
		log.info("接收网厅支付接口参数：partnerId" + partnerId 
				+ ",partnerNotifyUrl:" + partnerNotifyUrl
				+ ",partnerReturnUrl:" + partnerReturnUrl
				+ ",userName:" + userName
				+ ",partnerIdCard:" + partnerIdCard 
				+ ",partneridCardAddress:" + partneridCardAddress
				 + ",partnerTelNumber:" + partnerTelNumber 
				 + ",partnerOrderId:" + partnerOrderId
				 + ",partnerOrderAmount:" + partnerOrderAmount
				 + ",partnerOrderUrl:" + partnerOrderUrl
				 + ",partnerOrderTime:" + partnerOrderTime
				 + ",receiver:" + receiver
				 + ",receiverCity:" + receiverCity
				 + ",receiverArea:" + receiverArea
				 + ",receivingAddress:" + receivingAddress
				 + ",email:" + email
				 + ",postCode:" + postCode
				 + ",receiverMobile:" + receiverMobile
				 + ",receiverTelephone:" + receiverTelephone
				 + ",goodsUrl:" + goodsUrl
				 + ",goodsNum:" + goodsNum
				 + ",goodsName:" + goodsName
				 + ",goodsDescp:" + goodsDescp
				 + ",goodsPrice:" + goodsPrice
				 + ",goodsCount:" + goodsCount
				  + ",partnerTransactionId:" + partnerTransactionId
				   + ",receiverProvince:" + receiverProvince
				 );
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//校验参数
		if(EntityUtils.isEmpty(userName)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "name is empty");
		}else if(!checkChineseName(userName)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "name is not Chinese");
		}else if(EntityUtils.isEmpty(partnerIdCard)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "idCard is empty");
		}else if(!validateIdCard(partnerIdCard)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "idCard is formatError");
		}else if(EntityUtils.isEmpty(partneridCardAddress)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "idCardAddress is empty");
		}else if(EntityUtils.isEmpty(partnerTelNumber)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "contactPhone is empty");
		}else if(!validateMobileNumber(partnerTelNumber)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "contactPhone is formatError");
		}else if(EntityUtils.isEmpty(partnerNotifyUrl)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "notify_url is empty");
		}else if(EntityUtils.isEmpty(partnerReturnUrl)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "return_url is empty");
		}else if(EntityUtils.isEmpty(partnerOrderId)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "orderSeq is empty");
		}else if(EntityUtils.isEmpty(partnerOrderAmount)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "orderPrice is empty");
		}else if(EntityUtils.isEmpty(partnerOrderUrl)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "orderUrl is empty");
		}else if(EntityUtils.isEmpty(partnerOrderTime)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "createTime is empty");
		}else if(EntityUtils.isEmpty(receiver)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "consignee is empty");
		}else if(EntityUtils.isEmpty(receiver)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "consignee is empty");
		}else if(EntityUtils.isEmpty(receiverCity)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "distributinoRegion is empty");
		}else if(EntityUtils.isEmpty(receiverArea)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "distributinoSubregion is empty");
		}else if(EntityUtils.isEmpty(receivingAddress)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "deliveryAddress is empty");
		}else if(EntityUtils.isEmpty(email)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "emailAddr is empty");
		}else if(EntityUtils.isEmpty(receiverMobile)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "cfsnedtel is empty");
		}else if(EntityUtils.isEmpty(goodsUrl)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "proUrl is empty");
		}else if(EntityUtils.isEmpty(goodsNum)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "proId is empty");
		}else if(EntityUtils.isEmpty(goodsName)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "proName is empty");
		}else if(EntityUtils.isEmpty(goodsPrice)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "proPrice is empty");
		}else if(EntityUtils.isEmpty(goodsCount)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "proCount is empty");
		}else if(EntityUtils.isEmpty(goodsCount)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "proCount is empty");
		}else if(EntityUtils.isEmpty(partnerTransactionId)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "TransactionID is empty");
		}else if(EntityUtils.isEmpty(receiverProvince)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "distributinoProvince is empty");
		}else if(EntityUtils.isEmpty(partnerId)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "partnerId is empty");
		}else{
			retMap.put("code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		
		return retMap;
	}
	public static Map<String,Object> getNeededParamsMap(String[] responseValueArr){
		//验证必要参数 partnerTransactionId partnerOrderId partnerOrderAmount partnerNotifyUrl partnerReturnUrl
		
		String partnerNotifyUrl = "";	//后台通知的URL地址
		String partnerReturnUrl = "";	//结果返回的URL地址
		String partnerOrderId =  "";	//电信平台的订单号
		String partnerOrderAmount = "";	//订单价格
		String partnerTransactionId =  "";	//电信方发起支付请求流水号		
		
		
		partnerNotifyUrl = responseValueArr[0];
		partnerReturnUrl = responseValueArr[1];
		partnerOrderId = responseValueArr[6];
		partnerOrderAmount = responseValueArr[7];
		partnerTransactionId = responseValueArr[24];
		
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//校验参数
		 if(EntityUtils.isEmpty(partnerNotifyUrl)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "notify_url is empty");
		}else if(EntityUtils.isEmpty(partnerReturnUrl)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "return_url is empty");
		}else if(EntityUtils.isEmpty(partnerOrderId)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "orderSeq is empty");
		}else if(EntityUtils.isEmpty(partnerOrderAmount)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "orderPrice is empty");
		}else if(EntityUtils.isEmpty(partnerTransactionId)){
			retMap.put("code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("desc", "TransactionID is empty");
		}else{
			retMap.put("code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		return retMap;
	}
	
	public static String[] getNeededParamsArray(String[] responseValueArr){
		//验证必要参数 partnerTransactionId partnerOrderId partnerOrderAmount partnerNotifyUrl partnerReturnUrl
		
		String partnerNotifyUrl = "";	//后台通知的URL地址
		String partnerReturnUrl = "";	//结果返回的URL地址
		String partnerOrderId =  "";	//电信平台的订单号
		String partnerOrderAmount = "";	//订单价格
		String partnerTransactionId =  "";	//电信方发起支付请求流水号		
		
		
		partnerNotifyUrl = responseValueArr[0];
		partnerReturnUrl = responseValueArr[1];
		partnerOrderId = responseValueArr[6];
		partnerOrderAmount = responseValueArr[7] == null||"".equals(responseValueArr[7])?"0":responseValueArr[7] ;
		partnerTransactionId = responseValueArr[24];
		
		String[] needParamsArray = new String[]{partnerNotifyUrl,partnerReturnUrl,partnerOrderId,partnerOrderAmount,partnerTransactionId};
		
		return needParamsArray;
	}
	
	/**
	 * 校验手机号是否合法
	 * @param mobileNumber
	 * @return
	 */
	public static boolean validateMobileNumber(String mobileNumber){
		if(EntityUtils.isEmpty(mobileNumber)){
			return false;
		}
		String pattern = "^\\d{11}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(mobileNumber);  
		return m.matches();
	}
	
	/**
	 * 身份证校验
	 * @param idCard
	 * @return
	 */
	public static boolean validateIdCard(String idCard){
		if(EntityUtils.isEmpty(idCard)){
			return false;
		}
		return EntityUtils.isEmpty(IDCard.IDCardValidate(idCard)) ? true : false;
	}
	
	/**  
	 * checkChineseName(校验姓名是否是汉字)  
	 * @param name
	 * @return   
	 * boolean 
	 * @create time： Oct 15, 2015 4:16:46 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static boolean checkChineseName(String name) {
        if (!name.matches("[\u4e00-\u9fa5]{2,10}")) {
            System.out.println("只能输入2到10个汉字");
            return false;
        }else return true;
    }

}
