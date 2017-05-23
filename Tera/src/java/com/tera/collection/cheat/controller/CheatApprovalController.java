package com.tera.collection.cheat.controller;

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

import com.tera.collection.cheat.service.CollectionCheatService;
import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.judicial.model.CollectionJudicial;
import com.tera.collection.judicial.model.form.CollectionQBean;
import com.tera.collection.judicial.service.CollectionAssignService;
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
 * 欺诈审批控制器 <b>功能：</b>欺诈审批<br>
 * <b>作者：</b>zhukun<br>
 * <b>日期：</b>2015-06-08 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collection/cheat/approval")
public class CheatApprovalController {
	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CreditAssignReviewController.class);

	// 自动注入
	@Autowired
	private CollectionAssignService collectionAssignService;
	@Autowired
	private CollectionReviewService collectionReviewService;
	@Autowired
	private CollectionCheatService collectionCheatService;
	
	/**
	 * 跳转到司法审批的查询条件页面
	 * 
	 * @param request request
	 * @param map  map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String creditReviewQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		log.info(thisMethodName + ":start");
		log.info(thisMethodName + ":end");
		return "collection/cheat/collectionApprovalQuery";
	}
	/**
	 * 欺诈审批的查询列表
	 * 
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String creditReviewList(CollectionQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		queryMap.put("nonStates", new String[] { "0" }); // 状态
		queryMap.put("applyType", "2");// 节点为欺诈
		if (0 == user.getIsAdmin()) {
			qBean.setApprovalUid(loginId);
			queryMap.put("approvalUid", loginId);//当前处理人
		}
		queryMap.put("orderByType", "2");//排序类型 1 申请时间倒序排列、2 复核时间倒序排列、3审批时间倒序排列
		
		if(qBean.getState().equals("3")||qBean.getState().equals("")){//3待处理  欺诈审批处理中
			queryMap.put("state", "3");
			int rowsCount = collectionAssignService.queryCount(queryMap);
			pm.init(request, rowsCount, null, qBean);
			queryMap.put("rowS", pm.getRowS());
			queryMap.put("rowE", pm.getRowE());
			List<CollectionJudicial> creditAppList =collectionAssignService.queryList(queryMap);
			pm.setData(creditAppList);
		}
		if(qBean.getState().equals("4")){//4 已处理
			queryMap.remove("state");
			queryMap.put("processName", CollectionConstant.COLLECTION_PROCESS_NAME);//流程名称
			queryMap.put("operator", loginId);
			queryMap.put("logState", CollectionConstant.COLLECTION_STATE_CHEAT_EXAMINE);
			int rowsCount = collectionCheatService.queryDoneCount(queryMap);
			pm.init(request, rowsCount, null, qBean);
			queryMap.put("rowS", pm.getRowS());
			queryMap.put("rowE", pm.getRowE());
			List<CollectionJudicial> creditAppList = collectionCheatService.queryDoneList(queryMap);
			pm.setData(creditAppList);
		}
		map.put("pm", pm);
		map.put("state", qBean.getState());
		log.info(thisMethodName + ":end");
		return "collection/cheat/collectionApprovalList";
	}

	/**
	 * 显示欺诈审批的查询页面
	 * 
	 * @param request request
	 * @param map  map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String creditReviewUpdate(String id, HttpServletRequest request,HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");
		CollectionJudicial bean = new CollectionJudicial();
		if (StringUtils.isNotNullAndEmpty(id)) {
			bean.setId(id);
			bean = collectionReviewService.queryBuId(bean);
		}
		map.put("bean", bean);
		log.info(thisMethodName + ":end");
		return "collection/cheat/collectionApprovalUpdate";
	}
	/**
	 * 欺诈审批
	 * @param Judicial
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public void creditReviewSave(CollectionJudicial Judicial,HttpServletRequest request,HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			collectionCheatService.approvalSave(Judicial, loginId, sessionOrg);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "审批成功")));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "审批失败，请联系系统管理员")));
			writer.flush();
			writer.close();
			e.printStackTrace();
		}
		log.info(thisMethodName+":end");
	}
}
