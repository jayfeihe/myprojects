package com.tera.archives.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.archives.model.ArchiveInfo;
import com.tera.archives.service.IArchiveInfoService;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 档案管理查看Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/archive/view")
public class ArchiveInfoViewController extends BaseController {

	private final static Logger log = Logger.getLogger(ArchiveInfoViewController.class);

	@Autowired
	private IArchiveInfoService archiveInfoService;

	/**
	 * 跳转到查询条件页面
	 * 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query.do")
	public String archiveQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");
		log.info(thisMethodName + ":end");
		return "archive/view/archiveViewQuery";
	}

	/**
	 * 跳转到列表页面
	 * 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String archiveList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");

//		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
//		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);

		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(ArchiveInfo.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);

//		if (0 == loginUser.getIsAdmin()) {
//			queryMap.put("orgId", loginOrg.getOrgId());
//		}

		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<ArchiveInfo> archiveInfos = this.archiveInfoService.queryPageList(queryMap);
		pm.setData(archiveInfos);
		pm.initRowsCount(archiveInfos.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName + ":end");
		return "archive/view/archiveViewList";
	}

	/**
	 * 跳转到更新页面
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update.do")
	public String archiveUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");
		ArchiveInfo bean = null;
		// 如果存在
		if (StringUtils.isNotNullAndEmpty(id)) {
			bean = this.archiveInfoService.queryByKey(id);
		}
		map.put("bean", bean);

		log.info(thisMethodName + ":end");
		return "archive/view/archiveViewUpdate";
	}
	
	@RequestMapping("/save.do")
	public void archiveSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		try {
			ArchiveInfo bean = (ArchiveInfo) RequestUtils.getRequestBean(ArchiveInfo.class, request);
			
			if (0 == bean.getId()) {
				this.archiveInfoService.add(bean);
			} else {
				ArchiveInfo info = this.archiveInfoService.queryByKey(bean.getId());
				info.setContractId(bean.getContractId());
				info.setName(bean.getName());
				info.setOrgId(bean.getOrgId());
				info.setLoanAmt(bean.getLoanAmt());
				info.setType(bean.getType());
				info.setStartDate(bean.getStartDate());
				info.setEndDate(bean.getEndDate());
				info.setRemarks(bean.getRemarks());
				this.archiveInfoService.update(info);
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, String.valueOf(bean.getId()))));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
}
