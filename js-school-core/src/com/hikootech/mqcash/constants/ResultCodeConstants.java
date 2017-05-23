package com.hikootech.mqcash.constants;

import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName MqArith 
* @Description TODO(各接口返回码对应翻译) 
* @author HAI DA
* @date 2016年2月17日 下午2:05:33 
*  
*/
public class ResultCodeConstants {
	
	public final static Map<String,String> BR_CREDIT_RESULT_TYPE=new HashMap<String,String>();
	
	static{

		BR_CREDIT_RESULT_TYPE.put("00", "操作成功");
		BR_CREDIT_RESULT_TYPE.put("100001", "程序错误");
		BR_CREDIT_RESULT_TYPE.put("100002", "匹配结果为空");
		BR_CREDIT_RESULT_TYPE.put("100003", "缺少必选key值");
		BR_CREDIT_RESULT_TYPE.put("100004", "商户不存在或用户名错误");
		BR_CREDIT_RESULT_TYPE.put("100005", "登陆密码不正确");
		BR_CREDIT_RESULT_TYPE.put("100006", "请求参数格式错误");
		BR_CREDIT_RESULT_TYPE.put("100007", "Tokenid过期");
		BR_CREDIT_RESULT_TYPE.put("100008", "客户端api调用码不能为空");
		BR_CREDIT_RESULT_TYPE.put("100009", "IP地址错误");
		BR_CREDIT_RESULT_TYPE.put("100010", "超出当天访问次数");
		BR_CREDIT_RESULT_TYPE.put("100011", "账户停用");
		BR_CREDIT_RESULT_TYPE.put("100012", "请求套餐为空");
		BR_CREDIT_RESULT_TYPE.put("1000015", "请求参数其他错误");
		BR_CREDIT_RESULT_TYPE.put("1000016", "捕获请求json异常，无法解析的错误");
	}

}
