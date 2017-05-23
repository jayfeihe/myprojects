/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.house.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.model.BpmLog;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.house.constant.Constants;
import com.tera.house.model.HouseApp;
import com.tera.house.model.HouseDecision;
import com.tera.house.model.form.HouseQBean;
import com.tera.house.model.form.ReviewFBean;
import com.tera.house.service.HouseAppService;
import com.tera.house.service.HouseDecisionService;
import com.tera.house.service.HouseReviewService;
import com.tera.house.service.HouseSignService;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.UserService;
import com.tera.util.JsonUtil;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;

/**
 * 
 * 信用贷款复核控制器
 * <b>功能：</b>houseReviewController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-24 17:32:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/house/review")
public class HouseReviewController {

	
	
	
	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(HouseReviewController.class);


	@Autowired(required=false) //自动注入
	HouseReviewService houseReviewService;
	@Autowired(required=false) //申请表服务类
	HouseAppService houseAppService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false)
    UserService userService;
	@Autowired(required=false)
    ContractService contractService;
	@Autowired(required=false) //决策表服务
	HouseDecisionService houseDecisionService;
	@Autowired(required=false)
	ProductService productService;
	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	HouseSignService houseSignService;
	
	/**
	 * 跳转到信用复核的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String houseReviewQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "house/review/houseReviewQuery";
	}

	/**
	 * 显示信用复核的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String houseReviewList(HouseQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		queryMap.put("orgId", sessionOrg.getOrgId()); //session 机构
		queryMap.put("nonStates", new String[]{"0"}); //状态
//		queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_REVIEW});//查询流程签约
		if("waitTask".equals(qBean.getStateTask())){

			queryMap.put("processer", loginId); // 审核人
			if("".equals(queryMap.get("processer"))||user.getIsAdmin()==1){
				queryMap.remove("processer");
			}
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_REVIEW});//查询录入流程定义为签约
			
		}else if("inTask".equals(qBean.getStateTask())){
			// 进行中
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_LENDING});
		}else if("offTask".equals(qBean.getStateTask())){
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_END_APP});
		}
		
		int rowsCount = this.houseAppService.queryBpmAppAndContractCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<HouseApp> houseAppList = this.houseAppService.queryBpmAppAndContractList(queryMap);
		pm.setData(houseAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "house/review/houseReviewList";
	}

	/**
	 * 跳转到更新信用复核的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String houseReviewUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		HouseApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			//借款信息
			bean  = this.houseAppService.queryByKey(id);
			map.put("bean", bean);
			//得到合同
			List<Contract> contractList = contractService.getContractByAppId(bean.getAppId(), null, null);
			Contract contract = null;
			if(contractList.size() > 0){
				contract = contractList.get(0);
			}
			else{
				contract = new Contract();
				contract.setId(0);
				contract.setState("0");
			}
			map.put("contract", contract);
			//得到最终决策
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "0");
			List<HouseDecision> houseDecisionList = houseDecisionService.queryList(fmap);
			if(houseDecisionList.size() > 0){
				//产品费率
				Product product=productService.queryByName(houseDecisionList.get(0).getProduct());
				map.put("product", product);
				//借款金额
				double jkje = 0.0;
				jkje = contract.getLoanAmount();
				//MathUtils.div(houseDecisionList.get(0).getAmount(),MathUtils.sub(1,MathUtils.div(product.getSreviceFeeRate(), 100.0)));
				map.put("decision", houseDecisionList.get(0));
				map.put("jkje", jkje);
				//月还款额
				Map<String, Object> fmap1 = new HashMap<String, Object>();
				fmap1.put("contractNo", contractList.get(0).getContractNo());
				List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap1);
				double MAmount = MathUtils.add(loanRepayPlanList.get(0).getInterestReceivable(), loanRepayPlanList.get(0).getPrincipalReceivable());
				
//				double MAmount = houseSignService.getYhkje(jkje, product);
//				double MAmount=MathUtils.div(
//						MathUtils.mul(MathUtils.mul(jkje, MathUtils.div(product.getInterestRate(), 100.0)),Math.pow(MathUtils.add(1.0,MathUtils.div(product.getInterestRate(), 100.0)), product.getPeriod())), 
//						MathUtils.sub(Math.pow(MathUtils.add(1.0,MathUtils.div(product.getInterestRate(), 100.0)), product.getPeriod()),1.0)
//						);
				map.put("MAmount", MAmount);
			}
			//首次放款时间
			Map<String, Object> fmap2 = new HashMap<String, Object>();
			fmap2.put("contractNo", contractList.get(0).getContractNo());
			fmap2.put("periodNum", 1);
			List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap2);
			map.put("scfksj", loanRepayPlanList.get(0).getRepaymentDateStr());
			//放款退回复核原因
			List<BpmLog> lendingLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_LENDING);
			if(lendingLogList.size() > 0){
				map.put("returnReasons", lendingLogList.get(0).getLogContent2());//放款退回复核原因
			}
			//复核退回签约原因
			List<BpmLog> reviewLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_REVIEW);
				//不能直接取lendingLogList.get(1).getLogContent2()，因为签约重新提交到复核才是这个，和放款退回复核不一样。
			if(reviewLogList.size() > 0){
				f:for(int i=0;i<reviewLogList.size();i++){
					String link = reviewLogList.get(i).getLogContent1();
					String reason = reviewLogList.get(i).getLogContent2();
					//取第一条是复核退回的，因为复核通过LogContent2也可能有值。
					//虽然是复核退回，但退回原因不是必填，可能为空，不加“&& !"".equals(reason) && null!=reason”
					if("复核退回".equals(link)){
						map.put("reviewReasons", reason);//复核退回签约原因
						break f;
					}
				}
			}
		}
		log.info(thisMethodName+":end");
		return "house/review/houseReviewUpdate";
	}

	/**
	 * 信用 复核 保存
	 * @param reviewFBean
	 * @param bingding
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public void houseReviewSave(ReviewFBean reviewFBean, BindingResult bingding,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		String nextState,processer = null,logContent1,state;
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			if("submit".equals(reviewFBean.getButtonType())){
				nextState="放款";
				logContent1="复核通过";
				state="19";		//放款
				List<User> users = userService.getUserByOrgAndRoleAndDepart("86",new String[]{"信用放款专员"},null);
				if (users.size() > 0) {
					User user = users.get(new Random().nextInt(users.size()));
					processer=user.getLoginId();
				}else{
					writer.print(JsonUtil.object2json(new JsonMsg(true, "操作失败！没找到信用放款专员。")));
					writer.flush();
					writer.close();
					log.info(thisMethodName+":end");
					return;
				}
			}else{
				//往后退回要从日志表里去读
				List<BpmLog> bpmLogList = processService.getProcessHistoryLog(reviewFBean.getAppId(), "签约");
				processer = bpmLogList.get(0).getOperator();
				nextState="签约";
				logContent1="复核退回";
				state="15";		//签约（复核退回）
				String userState = userService.getUser(processer).getState();
				if("0".equals(userState)){//不在职 随机分配原信用风险专员所在部门的信用风险专员
					Map<String, Object> fmap = new HashMap<String, Object>();
					fmap.put("appId", reviewFBean.getAppId());
					List<HouseApp> appList  = this.houseAppService.queryList(fmap);
					HouseApp app = appList.get(0);
					List<User> users = userService.getUserByOrgAndRoleAndDepart(app.getOrgId(), new String[]{Constants.ROLE_FXZY},null);
					if (users.size() > 0) {
						User user = users.get(new Random().nextInt(users.size()));
						processer = user.getLoginId();
						app.setCustomerManager(processer);
						houseAppService.updateOnlyChanged(app);
					}
				}
				//在职 提交到原风险专员签约
			}
			houseReviewService.reviewBpm(state,reviewFBean.getId(), nextState,loginId, processer,logContent1, reviewFBean.getBackMsg(), null);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "操作成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "操作失败，程序出现异常。")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 跳转到 信用复核的只读页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String houseReviewRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		HouseApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			//借款信息
			bean  = this.houseAppService.queryByKey(id);
			map.put("bean", bean);
			//得到合同
			List<Contract> contractList = contractService.getContractByAppId(bean.getAppId(), null, null);
			Contract contract = null;
			if(contractList.size() > 0){
				contract = contractList.get(0);
			}
			else{
				contract = new Contract();
				contract.setId(0);
				contract.setState("0");
			}
			map.put("contract", contract);
			//得到最终决策
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "0");
			List<HouseDecision> houseDecisionList = houseDecisionService.queryList(fmap);
			if(houseDecisionList.size() > 0){
				//产品费率
				Product product=productService.queryByName(houseDecisionList.get(0).getProduct());
				map.put("product", product);
				//借款金额
				double jkje = 0.0;
				jkje = contract.getLoanAmount();
				//MathUtils.div(houseDecisionList.get(0).getAmount(),MathUtils.sub(1,MathUtils.div(product.getSreviceFeeRate(), 100.0)));
				map.put("decision", houseDecisionList.get(0));
				map.put("jkje", jkje);
				//月还款额
				double MAmount = houseSignService.getYhkje(jkje, product);
				MAmount = MathUtils.roundUp(MAmount, 2);
				map.put("MAmount", MAmount);
			}
			//首次放款时间
			Map<String, Object> fmap2 = new HashMap<String, Object>();
			fmap2.put("contractNo", contractList.get(0).getContractNo());
			fmap2.put("periodNum", 1);
			List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap2);
			map.put("scfksj", loanRepayPlanList.get(0).getRepaymentDateStr());
			List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_LENDING);
			if(bpmLogList.size() > 0){
				map.put("returnReasons", bpmLogList.get(0).getLogContent2());//放款退回原因
			}
		}
		log.info(thisMethodName+":end");
		return "house/review/houseReviewRead";
	}
}
