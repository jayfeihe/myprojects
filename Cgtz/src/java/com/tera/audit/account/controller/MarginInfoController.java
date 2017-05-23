/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.audit.account.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
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
import com.tera.audit.account.model.MarginInfo;
import com.tera.audit.account.service.IMarginInfoService;
import com.tera.audit.retplan.service.IRetPlanService;
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
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
 * 保证金信息表控制器
 * <b>功能：</b>MarginInfoController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-24 18:23:04<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/account/magin")
public class MarginInfoController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(MarginInfoController.class);
	
	/**
	 * MarginInfoService
	 */
	@Autowired(required=false) //自动注入
	private IMarginInfoService marginInfoService;
	
	@Autowired(required=false) //自动注入
	private IRetPlanService retPlanService;
	
	
	/**
	 * 跳转到保证金信息表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String marginInfoQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "account/magin/marginInfoQuery";
	}

	/**
	 * 显示保证金信息表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String marginInfoList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(MarginInfo.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<MarginInfo> assetList = this.marginInfoService.queryPageList(queryMap);
		pm.setData(assetList);
		pm.initRowsCount(assetList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "account/magin/marginInfoList";
	}

	/**
	 * 跳转到更新保证金信息表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String marginInfoUpdate(String id,String num, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		MarginInfo bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.marginInfoService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("num", num);
		log.info(thisMethodName+":end");
		return "account/magin/marginInfoUpdate";
	}

	/**
	 * 保存保证金信息表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void marginInfoSave(String num,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Timestamp nowTime=new Timestamp(System.currentTimeMillis());
		try {
			//TODO service操作 需要修改
			MarginInfo bean = (MarginInfo) RequestUtils.getRequestBean(MarginInfo.class, request);
			//如果存在
			if (bean.getId() != 0) {
				//更改保证金处理表
				MarginInfo marginInfo=this.marginInfoService.queryByKey(bean.getId());
				marginInfo.setState(bean.getState());
				marginInfo.setRemarks(bean.getRemarks());
				marginInfo.setHandleTime(nowTime);
				marginInfo.setUpdateTime(nowTime);
				//更改还款计划中的信息
				//如果退还借款人保证金，更改还款计划中当月实收保证金
//				Map<String,Object> queryMap=new HashMap<String, Object>();
//				queryMap.put("num",num);
//				queryMap.put("contractId",marginInfo.getContractId());
//				RetPlan retPlan=null;
//				if(this.retPlanService.queryList(queryMap).size()>0){
//					retPlan=retPlanService.queryList(queryMap).get(0);
//					if(bean.getState().equals("2")||bean.getState()=="2"){//退还借款人
//						//扣减当月实收保证金
//						retPlan.setRealMargin(0-marginInfo.getAmt());
//					}
//					retPlanService.update(retPlan);
//				}
				this.marginInfoService.update(marginInfo);
				
				//记录付保证金
			} else { //如果不存在
				this.marginInfoService.add(bean);
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
	 * 删除保证金信息表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void marginInfoDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.marginInfoService.delete(id);
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
	 * 跳转到查看保证金信息表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String marginInfoRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		MarginInfo bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.marginInfoService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "account/magin/marginInfoRead";
	}
	
	/**
	 * 导出保证金excel
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/excel.do")
	public ModelAndView sysUserExcel(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		MarginInfo user = (MarginInfo) RequestUtils.getRequestBean(MarginInfo.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(user);
		List<MarginInfo> users=new ArrayList<MarginInfo>();
		users=this.marginInfoService.queryList(beanMap);
		String title="保证金列表";
		String[] head = new String[]{"分公司","合同号","借款人","开始时间","结束时间","合同金额/元","保证金/元","状态","处理时间","说明"};
		Object[][] obj = new Object[users.size()][head.length];
		for (int i = 0; i < users.size(); i++) {
			MarginInfo myUser = users.get(i);
			Object[] values = new Object[head.length];
			values[0] = null != myUser.getOrgName() ? myUser.getOrgName() : "";
			values[1] = null != myUser.getContractId() ? myUser.getContractId() : "";
			values[2] = null != myUser.getLoanName() ? myUser.getLoanName() : "";
			values[3] = null != myUser.getStartDate() ? DateUtils.formatDate(myUser.getStartDate(),"yyyy/MM/dd") : "";
			values[4] = null != myUser.getEndDate() ?  DateUtils.formatDate(myUser.getEndDate(),"yyyy/MM/dd") : "";
			values[5] = myUser.getConAmt();
			values[6] = myUser.getAmt();
			values[7] = null != myUser.getState() ? transfer(myUser.getState()) : "";
			values[8] = null != myUser.getHandleTimeStr() ? myUser.getHandleTimeStr() : "";
			values[9] = null != myUser.getRemarks() ? myUser.getRemarks(): "";
		
			obj[i] = values;
		}
		//统计条件：分公司
		List<String> conditionList=new ArrayList<String>();
		String orgName="分公司:";
		if(StringUtils.isNotNullAndEmpty(user.getOrgId())){
			if(users.size()>0){
				orgName=orgName+users.get(0).getOrgName();
			}		
			conditionList.add(orgName);		
		}
		String[] condition = conditionList.toArray(new String[conditionList.size()]);
		//金额统计
		double sumRet=0;
		for (int i = 0; i < obj.length; i++) {
			sumRet=MathUtils.add(sumRet,(Double)obj[i][6]);
		}
		Object[][] sumObj=new Object[1][head.length];
		for (int i = 0; i < sumObj[0].length; i++) {
			if(i==0){
				sumObj[0][i]="保证金合计/元";
			}else if(i==6){
				sumObj[0][i]=sumRet;
			}else{
				sumObj[0][i]="";
			}
		}	
		ExcelReportTable report = new ExcelReportTable(title,condition,head,obj,sumObj);
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView(title+".xls"), map);
	}
	public String  transfer(String state){
		if(state.equals("1")){
			return "未处理";
		}else if(state.equals("2")){
			return "退还借款人";
		}else if(state.equals("3")){
			return "垫付本金";
		}else{
			return "";
		}
	}

}
