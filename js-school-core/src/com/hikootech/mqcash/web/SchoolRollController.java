package com.hikootech.mqcash.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.service.CheckSchoolService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.GenerateKey;

@RequestMapping("/schoolRoll")
@Controller
public class SchoolRollController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SchoolRollController.class);
	@Autowired
	private CheckSchoolService checkSchoolService;
	
	/**
	 * 后台请求学籍核查接口
	 * @param reponse
	 * @return
	 */
	@RequestMapping("/reqSchoolRollTest.do")
	@ResponseBody
	public void reqSchoolRoll(HttpServletRequest request,HttpServletResponse response){
		logger.info("后台江苏校园请求学籍核查信息接口开始--");
		//对业务参数进行解密并解析到Map对象中
		String busId = request.getParameter("busId");				  //业务表主键
		String userName = request.getParameter("name");		  //用户姓名
		String idCard = request.getParameter("idCard");		  //用户身份证
		String telNumber = request.getParameter("mobile");		  //联系电话
		String collegeLevel = request.getParameter("collegeLevel"); //入学年份
		String startTime = request.getParameter("startTime");       //入学年份
		String college = request.getParameter("college");

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userName", userName);
		paramsMap.put("documentNo", idCard);
		paramsMap.put("telNumber", telNumber);
		paramsMap.put("collegeLevel", collegeLevel);
		paramsMap.put("startTime", startTime);
		paramsMap.put("college", college);
		
		String id = GenerateKey.getId(CommonConstants.CHECK_SCHOOL_ID_PREFIX, ConfigUtils.getProperty("db_id"));
		paramsMap.put("id", id);

		// 回复请求
		try {
			//response.getWriter().write("CSH201608051840150100014");
			response.getWriter().write(id);
			response.getWriter().flush();
			response.getWriter().close();
			logger.info("请求学籍征信数据。。==>userName:" + userName + ",idCard:" + idCard + ",telNumber:" + telNumber);
		} catch (Exception e) {
			logger.error("返回ok时发生错误", e);
		}
		
				
		/**************处理业务逻辑开始********/
		try {
			logger.info("学籍核查信息接口开始。");
			checkSchoolService.queryCheckSchool(paramsMap);
			logger.info("学籍核查信息接口结束");
			} catch (Exception e) {
				logger.error("江苏校园请求前海信度接口-->学籍核查信息接口异常：" + "|idCard:" + idCard +  "|telNumber：" + telNumber + e.getMessage(), e);
			}
	    /**************处理业务逻辑结束********/
	}
}
