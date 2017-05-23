/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.feature.loanguar.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;
import com.tera.feature.loanguar.model.LoanGuar;
import com.tera.feature.loanguar.service.ILoanGuarService;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * T_LOAN_GUAR控制器
 * <b>功能：</b>LoanGuarController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 09:19:33<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loanguar")
public class LoanGuarController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoanGuarController.class);
	
	/**
	 * LoanGuarService
	 */
	@Autowired(required=false) //自动注入
	private ILoanGuarService loanGuarService;
	
	/**
	 * 跳转到T_LOAN_GUAR的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String loanGuarQuery(String loanId,String origLoanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("loanId", loanId);
		map.put("origLoanId", origLoanId);
		map.put("opt", opt);
		log.info(thisMethodName+":end");
		return "loanguar/loanGuarQuery";
	}

	/**
	 * 显示T_LOAN_GUAR的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String loanGuarList(String loanId,String origLoanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanGuar.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		if (StringUtils.isNotNullAndEmpty(origLoanId)) {
			queryMap.put("origLoanId", origLoanId);
		}
		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<LoanGuar> loanGuarList = this.loanGuarService.queryPageList(queryMap);
		pm.setData(loanGuarList);
		pm.initRowsCount(loanGuarList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("loanId",loanId);
		map.put("opt", opt);
		log.info(thisMethodName+":end");
		return "loanguar/loanGuarList";
	}

	/**
	 * 跳转到更新T_LOAN_GUAR的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String loanGuarUpdate(String id,String loanId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanGuar bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.loanGuarService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("loanId",loanId);
		log.info(thisMethodName+":end");
		if(bean.getType().equals("1")){
			return "loanguar/loanGuarUpdatePer";
		}
		if(bean.getType().equals("2")){
			return "loanguar/loanGuarUpdateOrg";
		}
		return "";
	}
	@RequestMapping("/updatePer.do")
	public String loanGuarUpdatePer(String id,String loanId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanGuar bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.loanGuarService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("loanId",loanId);
		log.info(thisMethodName+":end");
		
		return "loanguar/loanGuarUpdatePer";
	}
	@RequestMapping("/updateOrg.do")
	public String loanGuarUpdateOrg(String id,String loanId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanGuar bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.loanGuarService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("loanId",loanId);
		log.info(thisMethodName+":end");
		return "loanguar/loanGuarUpdateOrg";
	}
	/**
	 * 保存T_LOAN_GUAR数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void loanGuarSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			LoanGuar bean = (LoanGuar) RequestUtils.getRequestBean(LoanGuar.class, request);
			Timestamp nowTime=new Timestamp(System.currentTimeMillis());
			String loginid=request.getSession().getAttribute("loginid").toString();
			// 注册资本单位换算
//			bean.setOrgRegAmt(MathUtils.mul(bean.getOrgRegAmt(), 10000.0));
			//如果存在
			if (bean.getId() != 0) {
				//修改担保信息的修改时间和修改人id
				LoanGuar lg = this.loanGuarService.queryByKey(bean.getId());
				lg.setEdu(bean.getEdu());
				lg.setHomeAddr(bean.getHomeAddr());
				lg.setHomeCity(bean.getHomeCity());
				lg.setHomeCtry(bean.getHomeCtry());
				lg.setHomePrvn(bean.getHomePrvn());
				lg.setIdNo(bean.getIdNo());
				lg.setIdType(bean.getIdType());
				lg.setIsOrig(bean.getIsOrig());
				lg.setLegalIdNo(bean.getLegalIdNo());
				lg.setLegalIdType(bean.getLegalIdType());
				lg.setLegalName(bean.getLegalName());
				lg.setLegalTel(bean.getLegalTel());
				lg.setMarriage(bean.getMarriage());
				lg.setName(bean.getName());
				lg.setNowAddr(bean.getNowAddr());
				lg.setNowCity(bean.getNowCity());
				lg.setNowCtry(bean.getNowCtry());
				lg.setNowPrvn(bean.getNowPrvn());
				lg.setOrgBus(bean.getOrgBus());
				lg.setOrgRegAmt(bean.getOrgRegAmt());
				lg.setOrgRegTime(bean.getOrgRegTime());
				lg.setSaleRemark(bean.getSaleRemark());
				lg.setSex(bean.getSex());
				lg.setShareIdNo(bean.getShareIdNo());
				lg.setShareIdType(bean.getShareIdType());
				lg.setShareName(bean.getShareName());
				lg.setShareTel(bean.getShareTel());
				lg.setTel(bean.getTel());
				lg.setTel2(bean.getTel2());
				lg.setType(bean.getType());
				lg.setUpdateTime(nowTime);
				lg.setUpdateUid(loginid);
				lg.setValidType(bean.getValidType());
				this.loanGuarService.update(lg);
			} else { //如果不存在
				bean.setCreateTime(nowTime);
				bean.setCreateUid(loginid);
				this.loanGuarService.add(bean);
			}
			writer.print(JsonUtil.object2json(new LoanBaseJsonMsg(bean.getId(), bean.getLoanId(), true, "成功")));
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
	 * 删除T_LOAN_GUAR数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void loanGuarDelete(String id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.loanGuarService.delete(id);
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
	 * 跳转到查看T_LOAN_GUAR的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String loanGuarReadPer(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanGuar bean = null;
		// 如果存在
		if (null !=id  && !"".equals(id)) {
			bean = this.loanGuarService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		if(bean.getType().equals("1")){
			return "loanguar/loanGuarReadPer";
		}
		return "loanguar/loanGuarReadOrg";	
	}
}
