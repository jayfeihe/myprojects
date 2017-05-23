package com.hikootech.mqcash.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.po.unipay.UnipayPersonalReport;
import com.hikootech.mqcash.service.UnipayService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.GenerateKey;

@RequestMapping("/unipay")
@Controller
public class UnipayController extends BaseController {
	
	private static Logger log = LoggerFactory.getLogger(UnipayController.class);
	
	@Autowired
	private UnipayService unipayService;
	
	/**
	 * 银联评分征信
	 */
	@RequestMapping("/creditUnipay.do")
	@ResponseBody
	public void creditUnipay(){
		log.info("银联评分征信");
		Map<String, String> busMap = (Map<String, String>)getRequest().getAttribute("busMap");
		String cardNumber=busMap.get("cardNumber");
		String idCard=busMap.get("idCard"); 
		String name=busMap.get("name");
		String edScore=busMap.get("edScore"); 
		String busId=busMap.get("busId"); 
		String cityCode=busMap.get("cityCode");
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(cardNumber) || 
				StringUtils.isEmpty(idCard) || 
				StringUtils.isEmpty(name) || 
				StringUtils.isEmpty(edScore) || 
				StringUtils.isEmpty(busId) || 
				StringUtils.isEmpty(cityCode)){
			log.error("银联评分征信-参数为空！");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_SYSTEM_EXCEPTION);
			retMap.put(ResponseConstants.RETURN_DESC, "参数为空！");
			getRequest().setAttribute("retBusMap", retMap);
			return;
		}
		
		try {
			boolean rtn = unipayService.creditUnipay(cardNumber, idCard, name,
					Integer.parseInt(edScore), busId, cityCode);
			retMap.put(ResponseConstants.RETURN_CODE, rtn?ResponseConstants.SUCCESS:ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, rtn?"银联评分征信通过":"银联评分征信拒贷");
		} catch (Exception e) {
			log.error("银联评分征信出错:", e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_SYSTEM_EXCEPTION);
			retMap.put(ResponseConstants.RETURN_DESC, e.getMessage());
		}
		
		getRequest().setAttribute("retBusMap", retMap);
	}
	
	/**
	 * 银联评分征信测试
	 */
	@RequestMapping("/reqCreditUnipayTest.do")
	@ResponseBody
	public void reqCreditUnipayTest(HttpServletRequest request,HttpServletResponse response){
		log.info("银联评分征信测试");
		String cardNumber = request.getParameter("cardNumber");
		String idCard = request.getParameter("idCard");
		String name = request.getParameter("name");
		String edScore = request.getParameter("edScore");
		String busId = request.getParameter("busId");
		String cityCode = request.getParameter("cityCode");

		String reportId=GenerateKey.getId(CommonConstants.UNIPAY_REPORT, ConfigUtils.getProperty("db_id"));

		// 回复请求
		try {
			//response.getWriter().write("CSH201608051840150100014");
			response.getWriter().write(reportId);
			response.getWriter().flush();
			response.getWriter().close();
			log.info("请求银联征信数据。。==>name:" + name + ",idCard:" + idCard + ",cardNumber:" + cardNumber
					+ ",edScore:" + edScore + ",busId:" + busId + ",cityCode:" + cityCode);
		} catch (Exception e) {
			log.error("返回ok时发生错误", e);
		}
		
		UnipayPersonalReport report = null;
		try {
			report=unipayService.dealUnipayReport(cardNumber, idCard, name, busId, cityCode,reportId);
			unipayService.calScore(busId,report,cityCode,idCard,name);
		} catch (Exception e) {
			log.error("银联评分征信出错:", e);
		}
		
	}

}
