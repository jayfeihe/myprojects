package com.hikootech.mqcash.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hikootech.mqcash.po.UserJyzxRecord;
import com.hikootech.mqcash.service.UserJyzxService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.UUIDTools;

import net.sf.json.JSONObject;

@RequestMapping("/91credit")
@Controller
public class JYZXController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(JYZXController.class);
	private static final String companyCode = ConfigUtils.getProperty("jyzx_custNo");
	@Autowired
	private UserJyzxService userJyzxService;

	/***********************************
	 * 接受91征信数据 start
	 *********************************************************/
	@RequestMapping("/notify")
	public String getCreditData(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		log.info("91征信数据获取接口");
		
		try {
			byte[] rspData = userJyzxService.receiveCreditDataResult(request,companyCode);
			response.getOutputStream().write(rspData);
		} catch (Exception e) {
			log.error("接收处理91返回数据时，发生错误",e);
		}
		return null;
	}

	/***********************************
	 * 接受91征信数据 end
	 *********************************************************/

	/***********************************
	 * 异步请求91征信数据 start
	 *********************************************************/
	@RequestMapping("/requestJYCreditData.do")
	public void requestJYCreditDataAsync(HttpServletRequest request, HttpServletResponse responseBack) {
		Map<String, String> map = new HashMap<String, String>();
		
		String userName = request.getParameter("userName");
		String userIdCard = request.getParameter("userIdCard");
		String totalInfoId = request.getParameter("totalInfoId");
		String callType = request.getParameter("callType");
		String recordId=UUIDTools.getFormatUUID();
		
		map.put("userName", userName);
		map.put("userIdCard", userIdCard);
		map.put("totalInfoId", totalInfoId);
		map.put("recordId", recordId);
		map.put("callType", callType);
		
		// 回复请求
		try {
			responseBack.getWriter().write(recordId);
			responseBack.getWriter().flush();
			responseBack.getWriter().close(); 
			log.info("请求91征信数据。。==>userName:" + userName + ",userIdCard" + userIdCard + ",totalInfoId:" + totalInfoId + ",callType:" + callType);
		} catch (Exception e) {
			log.error("返回id时发生错误", e);
		}

		userJyzxService.requestJyzx(map);
	
	}

	/***********************************
	 * 请求91征信数据 end
	 *********************************************************/
	
	/***********************************
	 * 同步请求91征信数据 start
	 *********************************************************/
	@RequestMapping("/requestJYCreditDataSync.do")
	public JSONObject requestJYCreditDataSync(HttpServletRequest request, HttpServletResponse responseBack) {
		Map<String, String> map = new HashMap<String, String>();
		
		String userName = request.getParameter("userName");
		String userIdCard = request.getParameter("userIdCard");
		String totalInfoId = request.getParameter("totalInfoId");
		String recordId=UUIDTools.getFormatUUID();
		
		map.put("userName", userName);
		map.put("userIdCard", userIdCard);
		map.put("totalInfoId", totalInfoId);
		map.put("recordId",recordId);
		
		UserJyzxRecord record = userJyzxService.requestJyzx(map);
	
		return JSONObject.fromObject(record);
	}

	/***********************************
	 * 同步请求91征信数据 end
	 *********************************************************/
}
