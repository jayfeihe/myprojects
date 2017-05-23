package com.hikootech.mqcash.web;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.po.UserQhzxBlackList;
import com.hikootech.mqcash.po.UserQhzxCredit;
import com.hikootech.mqcash.service.QhzxService;
import com.hikootech.mqcash.service.UserQhzxBlackListService;
import com.hikootech.mqcash.service.UserQhzxCreditService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.UUIDTools;

import net.sf.json.JSONObject;

@RequestMapping("/qhzx")
@Controller
public class QhzxController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(QhzxController.class);

	@Autowired
	private UserQhzxBlackListService userQhzxMsc8004Service;
	@Autowired
	private UserQhzxCreditService userQhzxMsc8005Service;
	@Autowired
	private QhzxService qhzxService;

	@RequestMapping("requestQhzx.do")
	public void requestQhzx(HttpServletRequest request, HttpServletResponse responseBack) {
		String idNo = request.getParameter("idNo");
		String name = request.getParameter("name");
		String cardNo = getRequest().getParameter("cardNo");
		String totalInfoId = request.getParameter("totalInfoId");
		if (EntityUtils.isEmpty(cardNo)) {
			cardNo = "**************";
		}

		String id = UUIDTools.getFormatUUID();
		// 回复请求
		try {
			responseBack.getWriter().write(id);
			responseBack.getWriter().flush();
			responseBack.getWriter().close();
			logger.info("请求前海征信数据。。==>name:" + name + ",idNo:" + idNo + ",totalInfoId:" + totalInfoId);
		} catch (Exception e) {
			logger.error("返回ok时发生错误", e);
		}
		String mobileNum = request.getParameter("mobileNum");
		try {
			 qhzxService.requestQhBlackList(idNo, name, id, totalInfoId);
			 qhzxService.requestQhCredit(idNo, name, cardNo, mobileNum, id, totalInfoId);
			 qhzxService.requestQhLoanList(idNo, name, id, totalInfoId);
			 qhzxService.reqQhzxVerify(idNo, name, mobileNum, id, totalInfoId);
		} catch (Exception e) {
			logger.error("id==>" + id + ",请求前海征信发生错误", e);
		}
	}
	
	/** 
	* @Title requestQhzxBlackList 
	* @Description TODO(请求前海黑名单征信) 
	* @param 参数列表
	* @param idNo
	* @param name
	* @param id
	* @param totalInfoId
	* @return
	* @throws Exception 设定文件 
	* @return BusiDataItemRspQhMSC8004	返回类型 
	* @throws 
	*/
	@RequestMapping("requestQhzxBlackList.do")
	@ResponseBody
	public JSONObject requestQhzxBlackList(String idNo, String name, String id, String totalInfoId) throws Exception {
		
		logger.info("请求前海征信黑名单，请求参数：idNo-->" + idNo + ",name-->" + name + ",id-->" + id + ",totalInfoId-->" + totalInfoId);
		Map<String, Object> rsp=  qhzxService.requestQhBlackList(idNo,name,id,totalInfoId);

		return  JSONObject.fromObject(rsp);

	}
	
	/** 
	* @Title requestQhzxGoodRel 
	* @Description TODO(请求前海好信度接口) 
	* @param 参数列表
	* @param idNo
	* @param name
	* @param id
	* @param totalInfoId
	* @return
	* @throws Exception 设定文件 
	* @return JSONObject	返回类型 
	* @throws 
	*/
	@RequestMapping("requestQhzxGoodRel.do")
	@ResponseBody
	public JSONObject requestQhzxGoodRel(String idNo, String name, String cardNo, String mobileNum, String id,
			String totalInfoId) throws Exception {
		
		logger.info("请求前海好信度接口，请求参数：idNo-->" + idNo + ",name-->" + name + ",id-->" + id +
					 ",cardNo-->" + cardNo + ",mobileNum-->" + mobileNum+ ",totalInfoId-->" + totalInfoId);
		Map<String, Object>  qhcredit = qhzxService.requestQhCredit(idNo, name, cardNo, mobileNum,id,totalInfoId);
		
		return  JSONObject.fromObject(qhcredit);
		
	}
	
	
	@RequestMapping("requestQhzxLoan.do")
	@ResponseBody
	public JSONObject requestQhzxLoan(String idNo, String name, String id, String totalInfoId) throws Exception {
		
		logger.info("请求前海好信度接口，请求参数：idNo-->" + idNo + ",name-->" + name + ",id-->" + id + ",cardNo-->" + idNo + ",totalInfoId-->" + totalInfoId);
				
		Map<String, Object>  qhcredit = qhzxService.requestQhLoanList(idNo, name,id,totalInfoId);
		
		return  JSONObject.fromObject(qhcredit);
		
	}
	@RequestMapping("reqQhzxVerify.do")
	@ResponseBody
	public JSONObject reqQhzxVerify(String idNo, String name, String contactPhone ,String id, String totalInfoId) throws Exception {
		
		logger.info("请求前海一鉴通接口，请求参数：idNo-->" + idNo + ",name-->" + name + ",id-->" + id + ",cardNo-->" + idNo + ",totalInfoId-->" + totalInfoId);
		
		Map<String, Object>  qhzxVerify = qhzxService.reqQhzxVerify(idNo, name,contactPhone,id,totalInfoId);
		
		return  JSONObject.fromObject(qhzxVerify);
		
	}
	
	
	public static void main(String[] args) {
		System.out.println(GenerateKey.getId(null, ConfigUtils.getProperty("db_id")));

	}
}
