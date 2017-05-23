package com.hikootech.mqcash.web;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bfd.facade.MerchantBean;
import com.bfd.facade.MerchantServer;
import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.po.UserBrRequest;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.service.BrzxService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.HttpClientUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;




	/**
	* 此类描述的是：百融贷征信类
	* @author: zhaohefei
	* @version: 2016年2月18日 下午5:44:12
	*/
	
@RequestMapping("/brzx")
@Controller
public class BrzxController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(BrzxController.class);

	@Autowired
	private BrzxService brzxService;

	@RequestMapping("requestBrzx.do")
	@ResponseBody
	public Map<String, Object> requestBrzx(HttpServletRequest request, HttpServletResponse responseBack) {
		String idNo = request.getParameter("idNo");
		String name = request.getParameter("name");
		String mobileNo = request.getParameter("mobileNo");
		String homeAddr = request.getParameter("homeAddr");
		String comAddr = request.getParameter("comAddr");
		String totalInfoId = request.getParameter("totalInfoId");
		String meal = request.getParameter("meal");//此次请求的产品代号，如不配置将返回商户购买的所有产品。
		
		logger.info("百融贷征信接口收到信息：idNo-》"+idNo+",name"+name+",mobileNo"+mobileNo+",homeAddr"+homeAddr+",comAddr"+comAddr+",totalInfoId"+totalInfoId + ",子产品：" +meal);
		
		
		UserBrRequest brRequestPo = new UserBrRequest();
		brRequestPo.setIdCard(idNo);
		brRequestPo.setName(name);
		brRequestPo.setMobile(mobileNo);
		brRequestPo.setHomeAddress(homeAddr);
		brRequestPo.setCompanyAddress(comAddr);
		brRequestPo.setTotalInfoId(totalInfoId);
		brRequestPo.setProductName(meal);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		paramMap.put("UserBrRequest", brRequestPo);
		try {
			//调用查询接口，返回查询结果
			retMap = brzxService.requestBR(paramMap);
		} catch (Exception e) {
			logger.error("调用百融接口发生错误",e);
		}
		 
		return retMap;
	}
	
	@RequestMapping("testBr.do")
	@ResponseBody
	public Map<String, Object> testBr(HttpServletRequest request){
		String idCard = request.getParameter("idCard");
		String name = request.getParameter("name");
		String mobileNo = request.getParameter("mobileNo");
		String homeAddr = request.getParameter("homeAddr");
		String comAddr = request.getParameter("comAddr");
		String totalInfoId = request.getParameter("totalInfoId");
		String meal = request.getParameter("meal");//此次请求的产品代号，如不配置将返回商户购买的所有产品。
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		MerchantBean merchantBean = new MerchantBean();
		merchantBean.setId(idCard);
		merchantBean.setName(name); 
        merchantBean.setCell(mobileNo);
        merchantBean.setHome_addr(homeAddr);
        merchantBean.setBiz_addr(comAddr);
        merchantBean.setMeal(meal);
        
        UserBrRequest brRequest = new UserBrRequest();
        brRequest.setIdCard(idCard);
		brRequest.setName(name);
		brRequest.setMobile(mobileNo);
		brRequest.setHomeAddress(homeAddr);
		brRequest.setCompanyAddress(comAddr);
		brRequest.setTotalInfoId(totalInfoId);
		brRequest.setProductName(meal);
		
		
		paramMap.put("MerchantBean", merchantBean);
		paramMap.put("UserBrRequest", brRequest);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			retMap = brzxService.requestBR(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("百融请求调用出错", e);
		}
		
		
		return retMap;
	}

	@RequestMapping("reqBatchBrzx.do")
	public void reqBatchBrzx(HttpServletRequest request, HttpServletResponse responseBack) {
		 
		logger.info("百融贷征信接口批量查询");
		//List<UserInfo> brList=brzxService.queryUserInfoList();
		/*MerchantServer ms=new MerchantServer();
		String tokenid ="";
		try {
			//调用登陆接口，返回tokenid
			  tokenid = brzxService.LoginBr(ms);
		} catch (Exception e) {
			logger.error("调用百融接口发生错误",e);
			return;
		}
		 
		for (UserInfo userInfo : brList) {
			MerchantBean merchantBean=new MerchantBean();
			merchantBean.setName(userInfo.getName()); 
	        merchantBean.setId(userInfo.getIdCard());
	        merchantBean.setCell(userInfo.getBindMobile());
//	        merchantBean.setHome_addr(homeAddr);
//	        merchantBean.setBiz_addr(comAddr);
			
	        merchantBean.setTokenid(tokenid);
			
			//调用查询接口，返回查询结果
		    try {
		    	  String retMsg=brzxService.transMsgToBr(ms, merchantBean);
		    	  userInfo.setPwd(retMsg);
		    	  brzxService.updateBrMsg(userInfo);
			} catch (Exception e) {
				logger.error("调用百融接口发生错误",e);
			}
		}*/
		
		
/*		for (UserInfo userInfo : brList) {
			Map<String, Object> retMap = new HashMap<String, Object>();
			//将参数传给征信引擎
			HttpClientUtil http = new HttpClientUtil(Integer.parseInt("3000"));
			
			String qhzxUrl = "http://127.0.0.1:8280/qhzx/request8107.do";
			Map<String, String> digestMap = new HashMap<String, String>();
			String userName=userInfo.getName();
			String idCard=userInfo.getIdCard();
			String totalInfoId="test001";
			String mobileNo="13456789666";
			String qhzxRet="";
			try {
				digestMap.put("name", userName);
				digestMap.put("idNo", idCard);
				digestMap.put("mobileNum", mobileNo);
				digestMap.put("totalInfoId", totalInfoId);
				
				qhzxRet = http.doPost(qhzxUrl, digestMap);
				
				retMap.put("code", "0");
				retMap.put("qhzxRet", qhzxRet);
				retMap.put("desc", "征信接口，请求qh征信ok");
			} catch (Exception e) {
				retMap.put("code", "1");
				retMap.put("desc", "征信接口，请求qh征信异常");
			}
		}*/
		logger.error("调用百融批量结束。。");
	}

}
