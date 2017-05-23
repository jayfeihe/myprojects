package com.tera.collection.judicial.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.collection.constant.Constants;
import com.tera.collection.judicial.model.CollectionJudicial;
import com.tera.collection.judicial.model.form.CollectionQBean;
import com.tera.collection.judicial.service.CollectionAssignService;
import com.tera.credit.controller.CreditAssignReviewController;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.User;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.NetUtils;
import com.tera.util.ObjectUtils;

/**
 * 
 * 司法分单控制器
 * <b>功能：</b>司法分单<br>
 * <b>作者：</b>zhukun<br>
 * <b>日期：</b>2015-06-08 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collection/judicial/assign")
public class CollectionAssignController {
	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CreditAssignReviewController.class);
	
	//自动注入

	@Autowired
	private CollectionAssignService collectionAssignService;

	
	/**
	 * 跳转到司法分单的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String assignJudicialQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "collection/judicial/collectionAssignQuery";
	} 
	
	
	/**
	 * 显示查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String assignReviewList(CollectionQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		//将前台请求数据转换成Map
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		//登陆用户名
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		queryMap.put("applyType", "1");//节点为司法
		if(qBean.getState().equals("10")){//司法审批退回
			queryMap.put("state", Constants.REVIEW);
			queryMap.put("approvalResult", "2");//审批结果 2 否决
		}
		if(qBean.getState().equals("2")){//司法复核中
			queryMap.put("state", Constants.REVIEW);
			queryMap.put("checkResult", "10");//复核结果  10 没有特殊意义，给出一个特殊标识，做sql查询条件 
		}
		String distributionState = qBean.getDistributionState(); //1已分配，0未分配
		if ("1".equalsIgnoreCase(distributionState)) {//已分配
			//processer 	流程待处理人
			//nonProcesser 	非流程待处理人
			queryMap.put("nonProcesser", "");
			if("".equals(queryMap.get("processer"))){
				queryMap.put("processer",null);
			}
		} else if ("0".equalsIgnoreCase(distributionState)) {
			queryMap.put("processer", "");
		}
		queryMap.put("orderByType", "1");//排序类型 1 申请时间倒序排列、2 复核时间倒序排列、3审批时间倒序排列
		//查询数据总行数
		int rowsCount = this.collectionAssignService.queryCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		
		//设置List每页的起始行和结束行
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		//根据查询条件，查询Base
		List<CollectionJudicial> collectionBaseList = this.collectionAssignService.queryList(queryMap);
		//将结果返回给页面
		pm.setData(collectionBaseList);
		map.put("loginId", loginId);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "collection/judicial/collectionAssignList";
	}

	/**
	 * 更新分配数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/update.do")
	public void assignReviewUpdate(String[] contractNos, String newprocesser, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String ipAddress = NetUtils.getIpAddr(request);//获取IP地址
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);//用户登陆的机构
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);//登陆用户
		try {
			
			collectionAssignService.assignSave(contractNos, user, org, ipAddress, newprocesser);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "分单成功！")));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "分单失败，请联系系统管理员")));
			writer.flush();
			writer.close();
			e.printStackTrace();
		}
		log.info(thisMethodName+":end");
	}
}
