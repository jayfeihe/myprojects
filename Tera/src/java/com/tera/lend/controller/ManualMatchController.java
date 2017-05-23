/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.lend.controller;
 
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.lend.model.LendApp;
import com.tera.lend.model.form.Lend2MatchQBean;
import com.tera.lend.model.form.LendQBean;
import com.tera.lend.model.form.Loan2MatchQBean;
import com.tera.lend.model.form.TrustProductQBean;
import com.tera.lend.service.LendAppService;
import com.tera.lend.service.ManualMatchService;
import com.tera.match.model.Lend2match;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchModel;
import com.tera.match.service.Lend2matchService;
import com.tera.match.service.Loan2matchService;
import com.tera.match.service.MatchResultService;

import com.tera.product.model.TrustProduct;
import com.tera.product.service.TrustProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;

import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;

/**
 * 
 * <br>
 * <b>功能：</b>LendAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-18 11:08:58<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/lend/manualmatch")
public class ManualMatchController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LendAppController.class);
	
	/**
	 * ManualMatchService
	 */
	@Autowired(required=false) //自动注入
	private ManualMatchService<LendApp> manualMatchService;
	
	@Autowired(required=false) //自动注入
	private LendAppService lendAppService;
	
	@Autowired(required=false) //自动注入
	private Loan2matchService loan2matchService;
	
	@Autowired(required=false) //自动注入
	private TrustProductService trustProductService;
	
	@Autowired(required=false) //自动注入
	private Lend2matchService lend2matchService;
	
	@Autowired(required=false) //自动注入
	private MatchResultService matchResultService;
	
	
	
	
	/**
	 * 跳转到财富端申请表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String lendAppQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "lend/manualmatch/lendAppQuery";
	}

	/**
	 * 显示财富端申请表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String lendAppList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		//Object bean = RequestUtils.getRequestBean(LendQBean.class, request);
		Object bean = RequestUtils.getRequestBean(Lend2MatchQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		queryMap.put("bpmStates", new String[]{"人工撮合"});
		queryMap.put("states", new String[]{"1"});
		
		if(queryMap.get("orgId")==null||queryMap.get("orgId").equals("")){
			String orgId=org.getOrgId();
			queryMap.put("orgId", org.getOrgId()); //session 机构
		}
//		queryMap.put("orgId", org.getOrgId());
		queryMap.put("userLoginId",loginId);
		int rowsCount = this.lend2matchService.queryBpmManualMatchCount(queryMap);
		//int rowsCount = this.manualMatchService.queryBpmManualMatchCount(queryMap);
		//int rowsCount = this.lendAppService.queryBpmLendAppCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Lend2MatchQBean> lend2matchAppList = this.lend2matchService.queryBpmManualMatchList(queryMap);
		//List<Lend2MatchQBean> lendAppList = this.manualMatchService.queryBpmManualMatchList(queryMap);
		//List<LendApp> lendAppList = this.lendAppService.queryBpmLendAppList(queryMap);
		pm.setData(lend2matchAppList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "lend/manualmatch/lendAppList";
	}
	
	//选择出所有t_loan_2match表中的所有数据（t_loan_2match表中的loan_app_id一定也存在于loan_app表中）
	@RequestMapping("/loan2matchList.do")
	public String loan2matchList(HttpServletRequest request, ModelMap map) throws Exception {
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Loan2MatchQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		int rowsCount = this.loan2matchService.queryLoan2MatchByConditionCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Loan2MatchQBean> loan2matchList = this.loan2matchService.queryLoan2MatchByConditionList(queryMap);
		pm.setData(loan2matchList);
		map.put("pm", pm);
		return "lend/manualmatch/loanPersonInfo";
	}
	
	//查询出信托产品中的所有产品
	@RequestMapping("/trustProductList.do")
	public String trustProductList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(TrustProductQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		
		//起点金额的最小值
		String minStartMoney = request.getParameter("loanAmountMin");
		
		//起点金额的最大值
		String maxStartMoney = request.getParameter("loanAmountMax");
		
		if(null != minStartMoney && !"".equals(minStartMoney)){
			queryMap.put("prostartMoneyMin", minStartMoney);
		}
		
		if(null != maxStartMoney && !"".equals(maxStartMoney)){
			queryMap.put("prostartMoneyMax", maxStartMoney);
		}
		
		int rowsCount = this.trustProductService.queryTrustProCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<TrustProductQBean> trustProductQBeanList = this.trustProductService.queryTrustProList(queryMap);
		pm.setData(trustProductQBeanList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "lend/manualmatch/trustProductInfo";
	}
	
	@RequestMapping("/selectedLoanPersonList.do")
	public String selectedLoanPersonList(String loan2matchIdsStr,HttpServletResponse response,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		//取得前台传过来的loan_2match中的id号字符串（是一个拼接字符串，用逗号相隔）
//		String myselectedLoanAppIdStr = request.getParameter("loan2matchIdsStr");
		String[] chk_loanIds=loan2matchIdsStr.split(",");
		if(null == chk_loanIds || chk_loanIds.length==0){
			//如果前台传过来的loan_2match中的id号字符串为空（也就是说删除最后一个的时候）
			chk_loanIds=new String[]{""};
		}
		
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Loan2MatchQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		queryMap.clear();
		queryMap.put("selectedLoan2MatchIds", chk_loanIds);
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		int rowsCount = this.loan2matchService.queryLoan2MatchByConditionCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Loan2MatchQBean> loan2matchList = this.loan2matchService.queryLoan2MatchByConditionList(queryMap);
		
		
		pm.setData(loan2matchList);
		map.put("pm", pm);
		if(loan2matchList.size() == 0){
			writer.print(JsonUtil.object2json(new JsonMsg(true, "0")));
			writer.flush();
			writer.close();
		}
		
		
		log.info(thisMethodName+":end");
		return "lend/manualmatch/SelectedloanPersonInfo";
	}
	
	@RequestMapping("/selectedTrustProductList.do")
	public String selectedTrustProductList(String chk_productIds,HttpServletRequest request, ModelMap map) throws Exception {
		
		//取得前台传过来的loan_2match中的id号字符串（是一个拼接字符串，用逗号相隔）
		String myselectedTtrstProIdStr = request.getParameter("trustProIdsStr");
		
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(TrustProductQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		queryMap.clear();
		queryMap.put("selectedTrustProIds", myselectedTtrstProIdStr);
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		int rowsCount = this.trustProductService.queryTrustProCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<TrustProductQBean> trustProductQBeanList = this.trustProductService.queryTrustProList(queryMap);
		
		pm.setData(trustProductQBeanList);
		map.put("pm", pm);
		return "lend/manualmatch/selectedTrustProductInfo";
	}
	

	/**
	 * 跳转到更新财富端申请表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String lendAppUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LendApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.lendAppService.queryByKey(id);
			
		}
		
		//根据bean中的appid取得Lend2match实体，将lendapp中的amount替换成lend2match的lendAmount，只是为了显示而已
		Lend2match lend2match = (Lend2match) lend2matchService.queryBasicByKey(bean.getAppId());
		bean.setAmount((lend2match.getLendAmount()));
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "lend/manualmatch/lendAppUpdate";
	}

	/**
	 * 人工撮合数据提交进行处理
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void lendAppSave(String[] selectedLoan2MatchId,String mylend2matchId,String[] tz_loanAmount,String[] tz_loanServiceRate,String[] tz_loanInterestRate,
			HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		MatchModel matchModel = new MatchModel();
		String loginId=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		
		
		//将传过来的调整后的金额转换成数组
//		String[] tz_loanAmounts =tz_loanAmount.split(",");
	
		//将传过来的调整后的服务费率转换成数组
//		String[] tz_loanServiceRates = tz_loanServiceRate.split(",");
		
		//将传过来的调整后的利息率转换成数组
//		String[] tz_loanInterestRates = tz_loanInterestRate.split(",");
		
		try {
			//取得出借实体
			LendApp lendApp = this.lendAppService.queryByKey(mylend2matchId);
			
//			Object lend2matchbean = RequestUtils.getRequestBean(Lend2match.class, request);
			Map<String, Object> queryLend2matchMap = new HashMap<String, Object>();
			//根据lendAppId从lend_2match表中取出这条记录
			queryLend2matchMap.put("lendAppId", lendApp.getAppId());
			List<Lend2match> lend2matchList =  this.lend2matchService.queryList(queryLend2matchMap);
			
			//下面这几步是判断上面取得lend_2match记录中的余额是否大于传过来的匹配给借款方的金额总和，如果借款方
			//的借款金额总和不大于出借金额则进行匹配，否则给出提示说出借方的出借金额小于借款方总和
			
			//出借方剩余的出借金额
			double lend_money = lend2matchList.get(0).getLendAmount();
			
			//取得调整后的借款方的借款总和
			double loan_money_sum = 0;
			for(int j=0;j<tz_loanAmount.length;j++){
				loan_money_sum += Double.valueOf(tz_loanAmount[j]);
			}
			
			//如果出借放的剩余出借金额小于借款方的借款总和则返回并给出提示说出借放剩余出借金额不足，否则进行匹配
			if(lend_money < loan_money_sum){
				writer.print(JsonUtil.object2json(new JsonMsg(true, "出借方的剩余出借金额不足，请重新调整借款方借款金额！")));
				writer.flush();
				writer.close();
				return;
			}
			
			//取得借款人实体的List
			Object bean = RequestUtils.getRequestBean(Loan2match.class, request);
			Map<String, Object> queryMap = ObjectUtils.describe(bean);
			queryMap.clear();
			queryMap.put("loan2matchIds", selectedLoan2MatchId);
			List<Loan2match> loan2matchList = this.loan2matchService.queryList(queryMap);
			
			//2014-7-3添加，判断调整后的借款金额是否大于原借款金额，如果大于则不执行匹配操作，并给出提示
			for( int j=0;j<loan2matchList.size();j++){
				if(loan2matchList.get(j).getLoanAmount() < Double.valueOf(tz_loanAmount[j].trim())){
					writer.print(JsonUtil.object2json(new JsonMsg(true, "借款申请号：" + loan2matchList.get(j).getLoanAppId()+"调整后的借款金额大于原借款金额，请重新调整！")));
					writer.flush();
					writer.close();
					return;
				}
			}
			
			//给被选中的借款人实体的金额，服务费率，利息率赋值为调整后的新值
			for(int i=0;i<loan2matchList.size();i++){
				loan2matchList.get(i).setLoanAmount(Double.valueOf(tz_loanAmount[i]));
				loan2matchList.get(i).setLoanServiceRate(Double.valueOf(tz_loanServiceRate[i]));
				loan2matchList.get(i).setLoanInterestRate(Double.valueOf(tz_loanInterestRate[i]));
			}
			
			//设置出借方信息
			matchModel.setLend2match(lend2matchList.get(0));
			//设置借款方列表信息
			matchModel.setListLoan(loan2matchList);
			
			manualMatchService.bpmNext(matchModel, loginId);
			//matchResultService.addArtificialMatch(matchModel, loginId);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "提交成功！")));

		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "提交失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 删除财富端申请表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void lendAppDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.lendAppService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(new JsonMsg(false, "关联数据，不能删除！"));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到查看财富端申请表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String lendAppRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LendApp bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.lendAppService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "lend/manualmatch/lendAppRead";
	}
	
	/**
	 * 显示借款人的详细信息
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/loanPersonSpecifyInfo.do")
	public String loanPersonSpecifyInfo(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("id", id);
		log.info(thisMethodName+":end");
		return "lend/manualmatch/loanPersonSpecifyInfo";
	}
}
