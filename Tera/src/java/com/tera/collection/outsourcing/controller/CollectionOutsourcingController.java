package com.tera.collection.outsourcing.controller;

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
import com.tera.collection.outsourcing.model.CollectionOutsourcing;
import com.tera.collection.outsourcing.service.CollectionOutsourcingService;
import com.tera.collection.phone.controller.CollectionBaseController;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.StringUtils;
import com.tera.collection.constant.CollectionConstant;
/**
 * 外包催收控制器
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/collection/outsourcing")
public class CollectionOutsourcingController {
	/**
	 * 日志
	 */
	private final static Logger log = Logger
			.getLogger(CollectionBaseController.class);

	@Autowired
	private CollectionOutsourcingService collectionOutsouringService;

	/**
	 * 跳转到外包催收信息基本表的查询条件面
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
	public String collectionBaseQuery(HttpServletRequest request, ModelMap map)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		log.info(thisMethodName + ":start");
		log.info(thisMethodName + ":end");
		return "collection/outsourcing/collectionOutsourcingQuery";
	}
	/**
	 * 跳转到外包催收处理中基查询条件面
	 * 
	 * @param request
	 *            request
	 * @param map
	 *            map
	 * @throws Exception
	 *             exception
	 * @return string
	 */
	@RequestMapping("/queryPass.do")
	public String collectionPassQuery(HttpServletRequest request, ModelMap map)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		log.info(thisMethodName + ":start");
		log.info(thisMethodName + ":end");
		return "collection/outsourcing/collectionOutsourcingPassQuery";
	}

	/**
	 * 跳转到外包催收信息基本表查询结果页面
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
	public String collectionBaseList(CollectionQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		queryMap.put("orderByType", "1");
		queryMap.put("applyType", "3");//申请类型  3外包催收
		int rowsCount=collectionOutsouringService.queryCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CollectionOutsourcing> outsourcingList= collectionOutsouringService.queryList(queryMap);
		pm.setData(outsourcingList);
		map.put("pm", pm);
		map.put("state", qBean.getState());
		log.info(thisMethodName + ":end");
		return "collection/outsourcing/collectionOutsourcingList";
	}

	/**
	 * 显示外包催收的查询页面
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
		CollectionOutsourcing bean = new CollectionOutsourcing();
		if (StringUtils.isNotNullAndEmpty(id)) {
			bean.setId(id);
			bean = collectionOutsouringService.queryBuId(bean);
		}
		map.put("bean", bean);
		log.info(thisMethodName + ":end");
		return "collection/outsourcing/collectionOutsourcingUpdate";
	}
	
	/**
	 * 外包催收审核
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
		try {
			collectionOutsouringService.creditReviewSave(Judicial, loginId, sessionOrg);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "审核成功")));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "审核失败，请联系系统管理员")));
			writer.flush();
			writer.close();
			e.printStackTrace();
		}
		
		log.info(thisMethodName+":end");
	}
}
