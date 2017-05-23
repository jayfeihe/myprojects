package com.tera.feature.projectDetail.controller;

/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.projectDetail.model.ProjectDetail;
import com.tera.feature.projectDetail.service.IProjectInfoDetailService;
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleDataRelService;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * ProjectDetailController控制器
 * <b>功能：</b>ProjectDetailController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/projectInfoDetail")
public class ProjectInfoDetailController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(ProjectInfoDetailController.class);
	
	/**
	 * projectInfoService
	 */
	@Autowired(required=false) //自动注入
	private IProjectInfoDetailService projectInfoService;
	
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	
	@Autowired(required=false) //自动注入
	private RoleDataRelService roleDataRelService;
	
	
	/**
	 * 跳转到T_PROJECT_DETAIL的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String projectDetailQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果是分公司需要用于页面显示
		map.put("loginOrgName",loginOrg.getOrgName());
		log.info(thisMethodName+":end");
		return "projectDetail/projectDetailQuery";
	}

	/**
	 * 显示T_projectDetail的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String projectDetailList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(ProjectDetail.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//查询权限机构用于sql注入
		List<String> orgs=this.roleDataRelService.queryList(request);
		//orgs用于权限控制
		queryMap.put("orgs",orgs);
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果分配给分公司,则根据登录者所在分公司查询
		if(loginOrg!=null&&!loginOrg.getOrgId().equals("86")){
			queryMap.put("org",loginOrg.getOrgId());
		}
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		/*//分公司查自己的
		User loginUser=(User)request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if(loginUser.getIsAdmin()==0&&loginUser.getState().equals("1")){
			queryMap.put("org",loginOrg.getOrgId());
		}*/
		PageList<ProjectDetail> projectDetailList = this.projectInfoService.queryPageList(queryMap);
		pm.setData(projectDetailList);
		pm.initRowsCount(projectDetailList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "projectDetail/projectDetailList";
	}

	/**
	 * 跳转到更新T_projectDetail的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String projectDetailUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		ProjectDetail bean = null;
		//如果存在
		if (null !=id  && !"".equals(id)) {
			bean  = this.projectInfoService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if(loginOrg!=null&&!loginOrg.getOrgId().equals("86")){
			return "projectDetail/projectDetailOrgUpdate";
		}
		return "projectDetail/projectDetailUpdate";
	}
	

	/**
	 * 保存T_projectDetail数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void projectDetailSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			ProjectDetail bean = (ProjectDetail) RequestUtils.getRequestBean(ProjectDetail.class, request);
			String loginid=request.getSession().getAttribute("loginid").toString();
			//如果存在
			if (bean.getId() != 0) {
				//修改录入的项目明细数据
				ProjectDetail pd=this.projectInfoService.queryByKey(bean.getId());
				pd.setContractNo(bean.getContractNo());
				pd.setProjectId(bean.getProjectId());
				pd.setProjectName(bean.getProjectName());
				pd.setStartDate(bean.getStartDate());
				pd.setEndDate(bean.getEndDate());
				pd.setOnlineDate(bean.getOnlineDate());
				pd.setRealEndDate(bean.getRealEndDate());
				pd.setLoanAmt(bean.getLoanAmt());
				pd.setLendName(bean.getLendName());
				pd.setLendNo(bean.getLendNo());
				pd.setLoanName(bean.getLoanName());
				pd.setLoanNo(bean.getLoanNo());
				pd.setLoanRate(bean.getLoanRate());
				pd.setOnlineRate(bean.getOnlineRate());
				pd.setRetWay(bean.getRetWay());
				pd.setType(bean.getType());
				pd.setOrg(bean.getOrg());
				pd.setBranchRemark(bean.getBranchRemark());
				pd.setAcctRemark(bean.getAcctRemark());
				projectInfoService.update(pd);
			} else { //如果不存在
				//添加创建时间和创建人id
				this.projectInfoService.add(bean);
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
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

	/**
	 * 删除T_projectDetail数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void projectDetailDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.projectInfoService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "关联数据，不能删除！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到查看T_projectDetail的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String projectDetailRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		ProjectDetail bean = null;
		// 如果存在
		if (null !=id  && !"".equals(id)) {
			bean = this.projectInfoService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "projectDetail/projectDetailRead";
	}
	

	/**
	 * 导出录入的历史数据excel
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/export.do")
	public ModelAndView export(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		ProjectDetail bean = (ProjectDetail) RequestUtils.getRequestBean(ProjectDetail.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//查询权限机构用于sql注入
				List<String> orgs=this.roleDataRelService.queryList(request);
				//orgs用于权限控制
				queryMap.put("orgs",orgs);
				//如果分配给分公司,则根据登录者所在分公司查询
				Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
				if(loginOrg!=null&&!loginOrg.getOrgId().equals("86")){
					queryMap.put("org",loginOrg.getOrgId());
				}		
		List<ProjectDetail> list = this.projectInfoService.queryList(queryMap);
		
		String title = "项目明细表";
		String[] head = new String[] { "序号","债权线上编号", "债权线下编号", "项目名称", "合同开始时间", "合同结束时间", "上线时间", "合同实际结束时间", "合同天数",
				"借款金额/元", "原始债权人", "借款人", "借款利率", "结算利率", "还款方式", "贷款类别", "所属分公司","财务说明","分公司说明"};
		Object[][] obj = new Object[list.size()][head.length];
		for (int i = 0; i < list.size(); i++) {
			ProjectDetail tmpBean = list.get(i);
			Object[] values = new Object[head.length];
			values[0]=i+1;
			values[1] = null != tmpBean.getProjectId() ? tmpBean.getProjectId() : "";
			values[2] = null != tmpBean.getContractNo() ? tmpBean.getContractNo() : "";
			values[3] = null != tmpBean.getProjectName() ? tmpBean.getProjectName() : "";
			values[4] = null != tmpBean.getStartDate() ? DateUtils.formatDate(tmpBean.getStartDate(),"yyyy/MM/dd") : "";
			values[5] = null != tmpBean.getEndDate() ? DateUtils.formatDate(tmpBean.getEndDate(),"yyyy/MM/dd") : "";
			values[6] = null != tmpBean.getOnlineDate() ? DateUtils.formatDate(tmpBean.getOnlineDate(),"yyyy/MM/dd") : "";
			values[7] = null != tmpBean.getRealEndDate() ? DateUtils.formatDate(tmpBean.getRealEndDate(),"yyyy/MM/dd") : "";
			values[8] = tmpBean.getDays();
			values[9]= tmpBean.getLoanAmt();
			values[10] = null != tmpBean.getLendName() ? tmpBean.getLendName() : "";
			values[11] = null != tmpBean.getLoanName() ? tmpBean.getLoanName() : "";
			values[12] = tmpBean.getLoanRate()+"%";
			values[13] = tmpBean.getOnlineRate()+"%";
			values[14] = null != tmpBean.getRetWay() ? tmpBean.getRetWay() : "";
			values[15] = null != tmpBean.getType() ? tmpBean.getType() : "";
			values[16] = null != tmpBean.getOrgName() ? tmpBean.getOrgName() : "";
			values[17] = null != tmpBean.getAcctRemark() ? tmpBean.getAcctRemark() : "";
			values[18] = null != tmpBean.getBranchRemark() ? tmpBean.getBranchRemark() : "";
			obj[i] = values;
		}
		
		// 条件
		List<String> conditionList = new ArrayList<String>();
		String org = null;
		String type = null;
		
		StringBuffer range = new StringBuffer();
		if (bean.getMinStartDate()!=null) {
			range.append("统计区间：");
			range.append(DateUtils.getDate(DateUtils.formatDate((bean.getMinStartDate())), "yyyy/MM/dd"));
		}
		if (bean.getMaxStartDate()!=null) {
			range.append(" - "+DateUtils.getDate(DateUtils.formatDate((bean.getMaxStartDate())), "yyyy/MM/dd"));
		}
		
		conditionList.add(range.toString());
		
		if (StringUtils.isNotNullAndEmpty(bean.getOrg())) {
			org = "分公司："+this.orgService.queryByOrgId(bean.getOrg()).getOrgName();
			conditionList.add(org);
		}
		
		if (StringUtils.isNotNullAndEmpty(bean.getType())) {
			conditionList.add(type);
		}
		
		String[] condition = conditionList.toArray(new String[conditionList.size()]);
		
		// 统计数据
		double totalLoanAmt = 0.0;
		
		for (int i = 0; i < obj.length; i++) {
			double loanAmt = (Double) obj[i][9];
			totalLoanAmt = MathUtils.add(totalLoanAmt, loanAmt);
		}	
		Object[][] statisticData = new Object[1][head.length];
		for (int i = 0; i < statisticData[0].length; i++) {
			if (i == 0) {
				statisticData[0][i] = "合计";
			} else if (i == 9) {
				statisticData[0][i] = totalLoanAmt;
			} else {
				statisticData[0][i] = "";
			}
		}
		ExcelReportTable report = new ExcelReportTable(title, condition, head, obj, statisticData);
		
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("历史项目明细表.xls"), map);
	}
}
