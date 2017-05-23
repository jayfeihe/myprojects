package com.hikootech.mqcash.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.po.UserCreditInfo;
import com.hikootech.mqcash.po.UserMobileCreditInfo;
import com.hikootech.mqcash.service.UserCreditDataService;
import com.hikootech.mqcash.util.GbossXmlUtil;


/**  
 *   
 * UserController  
 *   
 * @function:(秒趣客户信用信息获取接口)  
 * @create time:Oct 9, 2015 10:44:30 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
@Repository("/user")
@Controller
public class UserController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserCreditDataService userCreditDataService;
	
	
	/**  
	 * getGbossCreditData()  
	 * @return   
	 * Map<String,Object> 
	 * @create time： Oct 9, 2015 10:44:24 AM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	@RequestMapping("/GetGbossCreditData.do")
	@ResponseBody
	public Map<String, Object> getGbossCreditData(){
		logger.info("秒趣客户学历信用数据从国政通获取接口开始");
		
		String name = getRequest().getParameter("name");
		String idCard = getRequest().getParameter("idCard");
		String idCardAddress = getRequest().getParameter("idCardAddress");
		String contactPhone = getRequest().getParameter("contactPhone");
	//	String key = getRequest().getParameter("key");
		
		//String name = "";
	//	String idCard = "";
		logger.info("获取参数为：name|" + name + ",idCard|" + idCard + ",idCardAddress|" + idCardAddress + ",contactPhone|" + contactPhone);
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		
			UserCreditInfo userCreditInfo =	userCreditDataService.queryGbossResult(name, idCard);

			map.put("gbossUserCredit", userCreditInfo);
			//判断是否存在信用评估：第三方国政通的信用评估、电渠四元组信用评估
		
		logger.info("秒趣客户学历信用数据从国政通获取接口结束");
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("秒趣客户学历信用数据从国政通获取接口异常" + e.getMessage());
		}
		return map;
	}
	
	@RequestMapping("/GetGbossMobileCreditData.do")
	@ResponseBody
	public Map<String, Object> getGbossMobileCreditData(){
		logger.info("秒趣客户手机信用数据从国政通获取接口开始");
		
		String name = getRequest().getParameter("name");
		String idCard = getRequest().getParameter("idCard");
		String idCardAddress = getRequest().getParameter("idCardAddress");
		String contactPhone = getRequest().getParameter("contactPhone");
		String uniqueCode = "" ; //唯一标识
		logger.info("获取参数为：name|" + name + ",idCard|" + idCard + ",idCardAddress|" + idCardAddress + ",contactPhone|" + contactPhone);
		
		//contactPhone = "13581528901";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		 //判断手机号是否是移动手机 134、135、136、137、 138、139、158、159、157（TD专属号段）、150、151、187（3G）、188（3G）
			if (GbossXmlUtil.isMobileNumber(contactPhone)) {
				UserMobileCreditInfo userMobileCreditInfo =	userCreditDataService.querySingleGbossUserMobileCreditInfo(contactPhone,uniqueCode);

				map.put("gbossUserMobileCredit", userMobileCreditInfo);
			}
			
			//判断是否存在信用评估：第三方国政通的信用评估、电渠四元组信用评估
		
		logger.info("秒趣客户学历信用数据从国政通获取接口结束");
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("秒趣客户学历信用数据从国政通获取接口异常" + e.getMessage());
		}
		return map;
	}

}
