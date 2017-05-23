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

import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.constant.Constants;
import com.tera.collection.judicial.model.CollectionJudicial;
import com.tera.collection.judicial.model.form.CollectionQBean;
import com.tera.collection.judicial.service.CollectionAssignService;
import com.tera.collection.judicial.service.CollectionJudicialService;
import com.tera.collection.judicial.service.CollectionReviewService;
import com.tera.credit.controller.CreditAssignReviewController;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.StringUtils;

/**
 * 
 * 司法复核控制器 <b>功能：</b><br>
 * <b>作者：</b>zhukun<br>
 * <b>日期：</b>2015-06-08 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collection/judicial/review")
public class CollectionReviewController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger
			.getLogger(CreditAssignReviewController.class);

	// 自动注入
	@Autowired
	private CollectionAssignService collectionAssignService;
	@Autowired
	private CollectionReviewService collectionReviewService;
	/**
	 * 跳转到司法复核的查询条件页面
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
		return "collection/judicial/collectionReviewQuery";
	}

	/**
	 * 显示司法复核的查询列表
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
		String loginId = (String) request.getSession().getAttribute(
				SysConstants.LOGIN_ID);
		User user = (User) request.getSession().getAttribute(
				SysConstants.LOGIN_USER);
		queryMap.put("applyType", "1");// 节点为司法
		
		if (0 == user.getIsAdmin()) {//登录人不为系统管理员，设置当前处理人
			qBean.setCheckUid(loginId);
			queryMap.put("checkUid", loginId);//当前处理人
		}
		
		//排序类型 1 申请时间倒序排列、2 复核时间倒序排列、3审批时间倒序排列、4修改时间倒序排列
		queryMap.put("orderByType", "4");
		
		if(qBean.getState().equals("0")||qBean.getState().equals("")){//0 待处理 司法复核中
			queryMap.put("states", new String[] {
					Constants.REVIEW
			});
			queryMap.remove("state");
			int rowsCount = this.collectionAssignService.queryCount(queryMap);
			pm.init(request, rowsCount, null, qBean);
			queryMap.put("rowS", pm.getRowS());
			queryMap.put("rowE", pm.getRowE());
			List<CollectionJudicial> creditAppList = this.collectionAssignService
					.queryList(queryMap);
			pm.setData(creditAppList);
		}else if(qBean.getState().equals("1")){//1 已处理 
			queryMap.remove("state");
			queryMap.put("processName", CollectionConstant.COLLECTION_PROCESS_NAME);//流程名称
			queryMap.put("operator", loginId);
			queryMap.put("logState", CollectionConstant.COLLECTION_STATE_JUDICIAL_REVIEW);
			int rowsCount = collectionReviewService.queryDoneCount(queryMap);
			pm.init(request, rowsCount, null, qBean);
			queryMap.put("rowS", pm.getRowS());
			queryMap.put("rowE", pm.getRowE());
			List<CollectionJudicial> creditAppList = this.collectionReviewService
					.queryDoneList(queryMap);
			pm.setData(creditAppList);
		}
		map.put("pm", pm);
		map.put("state", qBean.getState());
		log.info(thisMethodName + ":end");
		return "collection/judicial/collectionReviewList";
	}

	/**
	 * 显示司法复核的查询页面
	 * 
	 * @param request
	 *            request
	 * @param map
	 *            map
	 * @throws Exception
	 *             exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String creditReviewUpdate(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		log.info(thisMethodName + ":start");
		CollectionJudicial bean = new CollectionJudicial();
		if (StringUtils.isNotNullAndEmpty(id)) {
			bean.setId(id);
			bean = collectionReviewService.queryBuId(bean);
		}
		map.put("bean", bean);
		log.info(thisMethodName + ":end");
		return "collection/judicial/collectionReviewUpdate";
	}

	/**
	 * 
	 * @param Judicial
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public void creditReviewSave(CollectionJudicial Judicial,
			HttpServletRequest request,HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		log.info(thisMethodName + ":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(
				SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(
				SysConstants.LOGIN_ORG);
//		User user = (User) request.getSession().getAttribute(
//				SysConstants.LOGIN_USER);
		try {
			collectionReviewService.creditReviewSave(Judicial, loginId, sessionOrg);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "复核成功")));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "复核失败，请联系系统管理员")));
			writer.flush();
			writer.close();
			e.printStackTrace();
		}
		
		log.info(thisMethodName+":end");
	}

}
