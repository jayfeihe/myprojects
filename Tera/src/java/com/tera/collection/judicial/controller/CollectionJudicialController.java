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

import com.tera.collection.judicial.model.CollectionJudicial;
import com.tera.collection.judicial.model.form.CollectionQBean;
import com.tera.collection.judicial.service.CollectionAssignService;
import com.tera.collection.judicial.service.CollectionJudicialService;
import com.tera.credit.controller.CreditAssignReviewController;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;

/**
 * 
 * 司法客户控制器 <b>功能：</b>司法客户<br>
 * <b>作者：</b>zhukun<br>
 * <b>日期：</b>2015-06-08 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collection/judicial")
public class CollectionJudicialController {
	/**
	 * 日志
	 */
	private final static Logger log = Logger
			.getLogger(CreditAssignReviewController.class);

	// 自动注入
	@Autowired
	private CollectionAssignService collectionAssignService;
	@Autowired
	private CollectionJudicialService collectionJudicialService;

	/**
	 * 跳转到司法客户的查询条件页面
	 * 
	 * @param request
	 *            request
	 * @param map
	 *            map
	 * @throws Exception
	 *             exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String creditReviewQuery(HttpServletRequest request, ModelMap map)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		log.info(thisMethodName + ":start");
		log.info(thisMethodName + ":end");
		return "collection/judicial/collectionJudicialQuery";
	}

	/**
	 * 司法客户的查询列表
	 * 
	 * @param request
	 *            request
	 * @param map
	 *            map
	 * @throws Exception
	 *             exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String creditReviewList(CollectionQBean qBean,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		log.info(thisMethodName + ":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		queryMap.put("applyType", "1");// 节点为司法
		qBean.setState("4");// 状态 4审批通过 司法生效
		queryMap.put("state", "4");// 节点为司法
		queryMap.put("orderByType", "3");//排序类型 1 申请时间倒序排列、2 复核时间倒序排列、3审批时间倒序排列
		int rowsCount = this.collectionAssignService.queryCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CollectionJudicial> creditAppList = this.collectionAssignService
				.queryList(queryMap);
		pm.setData(creditAppList);
		map.put("pm", pm);
		log.info(thisMethodName + ":end");
		return "collection/judicial/collectionJudicialList";
	}

	/**
	 * 司法客户 司法解除
	 * 
	 * @param request
	 *            request
	 * @param map
	 *            map
	 * @throws Exception
	 *             exception
	 * @return string
	 */
	@RequestMapping("/save.do")
	public void creditReviewUpdate(String id, HttpServletRequest request,HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		try{
			collectionJudicialService.judicialSave(id, loginId, sessionOrg);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "解除成功")));
			writer.flush();
			writer.close();
		}catch (Exception e) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "解除失败，请联系系统管理员")));
			writer.flush();
			writer.close();
			e.printStackTrace();
		}
		log.info(thisMethodName + ":end");
	}
}
