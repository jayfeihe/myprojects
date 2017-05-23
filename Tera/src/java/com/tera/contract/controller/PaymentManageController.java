/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.contract.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.contract.model.Contract;
import com.tera.contract.model.form.PaymentManageBean;
import com.tera.contract.model.form.PaymentManageMsgBean;
import com.tera.contract.service.ContractService;
import com.tera.contract.service.PaymentManageService;
import com.tera.credit.service.CreditSignService;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.model.Payment;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.payment.service.PaymentService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.ResultObj;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.UserService;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;

/**
 * 
 * 还款管理
 * <b>功能：</b>CreditAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/paymentManage")
public class PaymentManageController  extends BaseController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(PaymentManageController.class);


	@Autowired(required=false) //自动注入
	RoleService roleService;

	@Autowired(required=false)
    private ContractService contractService;
	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false) //自动注入
	private PaymentManageService paymentManageService;
	@Autowired(required=false) //自动注入
	PaymentService<Payment> paymentService;
	@Autowired(required=false) //自动注入
	CreditSignService creditSignService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	
	@RequestMapping("/query.do")
	public String manageQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "paymentManage/paymentManageQuery";
	}

	
	@RequestMapping("/list.do")
	public String manageList(PaymentManageBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		PageModel pm = new PageModel();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		queryMap.put("orgIdLike", sessionOrg.getOrgId());			
		int rowsCount =contractService.queryContractRepayPlanCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Contract> contractAppList = contractService.queryContractRepayPlanList(queryMap);
		/*for (Contract contract : contractAppList) {
			contract.setLoanIdNo(contract.getIdNo());
		}*/
		pm.setData(contractAppList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "paymentManage/paymentManageList";
	}

	
	@RequestMapping("/update.do")
	public String manageUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		Map<String, Object> beanMap = new HashMap<String, Object>();
		beanMap.put("loginId", loginId);
		beanMap.put("orgId", org.getOrgId());
		List<Role> roleList = roleService.getRoleByOrgLoginId(beanMap);
		boolean sign = true;
		for (Role role : roleList) {
			if(role.getName().equals("还款管理专员") ){
				sign = false;
				map.put("roleRepay", role.getName());
			}
			if(role.getName().equals("对公平账员") ){
				sign = false;
				map.put("rolePublic", role.getName());
			}
		}
		if(user.getIsAdmin() != 1 && sign){
			return "redirect:predictPayment.do?id=" + id + "&loanDate=";
		}
		//如果存在
		if (null != id && !"".equals(id)) {
			Contract contract  = this.contractService.queryByKey(id);
			map.put("contract", contract);
			Map<String, Object> dbMap=new HashMap<String, Object>();
			dbMap.put("contractNo", contract.getContractNo());
			List<LoanRepayPlan> repayPlanList= loanRepayPlanService.queryFyLoanStateList(dbMap);
			double ycxhkje = paymentManageService.getYcxhkje(contract.getContractNo());
			double maxjmje = paymentManageService.getMaxjmje(contract.getContractNo());
			
			//积木 与 鼎轩 两个渠道 都不减免服务费
			if(
//				contract.getLoanProduct().indexOf("JM")<=0 && contract.getLoanProduct().indexOf("DX")<=0 && contract.getLoanProduct().indexOf("MD")<=0
				"HD".equals(contract.getChannelType())
				){ 
				double fhfwf = paymentManageService.getFhfwf(repayPlanList, contract, new Date());
				ycxhkje = MathUtils.sub(ycxhkje, fhfwf);				
			}
			
			if(ycxhkje > 0)
				map.put("ycxhkje", ycxhkje);//一次性还款金额
			map.put("maxjmje", maxjmje);//最高减免金额
			int yhkqs = 0; //已还款期数
			int ljwycs = 0; //累计违约次数
			if(null != repayPlanList && repayPlanList.size() > 0){
				double yhkje = MathUtils.add(repayPlanList.get(0).getPrincipalReceivable(), repayPlanList.get(0).getInterestReceivable());
				map.put("yhkje", yhkje);//月还款金额
				for (LoanRepayPlan loanRepayPlan : repayPlanList) {
					if(1 == loanRepayPlan.getPeriodNum()){
						map.put("hkqsrq", loanRepayPlan.getRepaymentDateStr());
					}
					if("2".equals(loanRepayPlan.getState())){//状态为已完成
						yhkqs++;
					}	
					if(loanRepayPlan.getPenaltyReceivable() > 0){
						ljwycs++;
					}
					if(!"2".equals(loanRepayPlan.getState()) && loanRepayPlan.getPenaltyReceivable() > 0){
						loanRepayPlan.setYqts(DateUtils.getDayRange(loanRepayPlan.getRepaymentDate(), new Date()));
					}
					double dyyshj = MathUtils.add(loanRepayPlan.getDelayReceivable(), MathUtils.add(loanRepayPlan.getPenaltyReceivable(), MathUtils.add(loanRepayPlan.getInterestReceivable(), MathUtils.add(loanRepayPlan.getSreviceFeeReceivable(), loanRepayPlan.getPrincipalReceivable()))));
					loanRepayPlan.setDyyshj(MathUtils.roundUp(dyyshj, 2));
					double dyshhj = MathUtils.add(loanRepayPlan.getDelayReceived(), MathUtils.add(loanRepayPlan.getPenaltyReceived(), MathUtils.add(loanRepayPlan.getInterestReceived(), MathUtils.add(loanRepayPlan.getSreviceFeeReceived(), loanRepayPlan.getPrincipalReceived()))));
					loanRepayPlan.setDyshhj(MathUtils.roundUp(dyshhj, 2));
					if("1".equals(loanRepayPlan.getState())){
						loanRepayPlan.setHkzt("未还");
					}
					if("2".equals(loanRepayPlan.getState())){
						loanRepayPlan.setHkzt("已还清");
					}
					if("3".equals(loanRepayPlan.getState())){
						loanRepayPlan.setHkzt("未还清");
					}
					if("1".equals(loanRepayPlan.getState()) || "3".equals(loanRepayPlan.getState())){
						loanRepayPlan.setBqyhkhj(MathUtils.sub(MathUtils.sub(MathUtils.sub(dyyshj, dyshhj), loanRepayPlan.getPenaltyReduce()), loanRepayPlan.getDelayReduce()));
					}else{
						loanRepayPlan.setBqyhkhj(0.00);
					}
				}
				
				for (LoanRepayPlan loanRepayPlan : repayPlanList) {
					if(loanRepayPlan.getPenaltyReceivable() > 0){
						map.put("wyqsrq", DateUtils.toDateString(DateUtils.addDay(loanRepayPlan.getRepaymentDate(), 1)));
						break;
					}
				}
			}
			map.put("yhkqs", yhkqs);
			map.put("ljwycs", ljwycs);
			map.put("repayPlanList", repayPlanList);
		}
		log.info(thisMethodName+":end");
		return "paymentManage/paymentManageUpdate";
	}
	
	
	
	@RequestMapping("/save.do")
	public void manageSave(PaymentManageBean fbean,BindingResult bingding, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		Contract contract = this.contractService.queryByKey(fbean.getContractId());
		
		try {
			Map<String, Object> fmap = new HashMap<String, Object>();
			fmap.put("contractNo", contract.getContractNo());
			fmap.put("inOut", "1");
			List<Payment> paymentList = paymentService.queryList(fmap);
			for (Payment payment : paymentList) {
				if("2".equals(payment.getState()) || "4".equals(payment.getState())){
					writer.print(JsonUtil.object2json(new JsonMsg(false, "批处理正在执行，您不能做此操作。")));
					writer.flush();
					writer.close();
					return;
				}
			}
			if("abatement".equals(fbean.getButtonType())){
				//减免
				PaymentManageMsgBean msgBean=paymentManageService.abatementFine(contract.getContractNo(), fbean.getAbatementAmount(),
						loginId, org.getOrgId());
				writer.print(JsonUtil.object2json(msgBean));
			}else if("realTime".equals(fbean.getButtonType())){
				//实时还款
//				PaymentManageMsgBean msgBean=paymentManageService.realTimePayment(contract.getContractNo(), fbean.getRealTimeAmount(), loginId, org.getOrgId());
				PaymentManageMsgBean msgBean=paymentManageService.realTimePaymentFY(contract.getContractNo(), fbean.getRealTimeAmount(), loginId, org.getOrgId());
				writer.print(JsonUtil.object2json(msgBean));
			}else if("advancePayment".equals(fbean.getButtonType())){
				//一次性还清
//				ResultObj resultObj = paymentManageService.advancePayment(contract.getContractNo(), paymentList);
				ResultObj resultObj = paymentManageService.advancePaymentFY(contract.getContractNo(), paymentList, new Date(),loginId, org.getOrgId());
				writer.print(JsonUtil.object2json(new JsonMsg(resultObj.isSuccess(), resultObj.getResult() + "")));
			}else if("publicAdvancePayment".equals(fbean.getButtonType())){
				//对公一次性还清
				ResultObj resultObj = paymentManageService.publicAdvancePayment(contract.getContractNo(), paymentList, new Date(),fbean.getPublicAdvanceAmount(),loginId, org.getOrgId());
				writer.print(JsonUtil.object2json(new JsonMsg(resultObj.isSuccess(), resultObj.getResult() + "")));
			}else if("dghk".equals(fbean.getButtonType())){
				//对公还款
				PaymentManageMsgBean resultObj = paymentManageService.dghk(contract.getContractNo(), fbean.getDghkAmount(), loginId, org.getOrgId());
				writer.print(JsonUtil.object2json(new JsonMsg(resultObj.isSuccess(), resultObj.getMessage() + "")));
			}
			
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "操作失败，程序异常。")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 根据还款日期计算还款页面
	 * @param id
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/predictPayment.do")
	public String predictPayment(String id, String loanDate, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//如果存在
		if (null != id && !"".equals(id)) {
			if(null == loanDate || "".equals(loanDate)){
				loanDate = DateUtils.formatDate(new Date());
			}
			Contract contract  = this.contractService.queryByKey(id);
			map.put("contract", contract);
			Map<String, Object> dbMap=new HashMap<String, Object>();
			dbMap.put("contractNo", contract.getContractNo());
			List<LoanRepayPlan> repayPlanList= loanRepayPlanService.queryFyLoanStateList(dbMap);
			List<LoanRepayPlan> loanRepayPlanList1 = new ArrayList<LoanRepayPlan>();//违约还款集合
			
			int yhkqs = 0; //已还款期数
			int ljwycs = 0; //累计违约次数
			if(null != repayPlanList && repayPlanList.size() > 0){
				Product product = productService.queryByName(contract.getLoanProduct());
				for (LoanRepayPlan loanRepayPlan : repayPlanList) {
					int sjDay = DateUtils.compareDate(DateUtils.getDate(loanDate, DateUtils.DEFAULT_DATE_PATTERN), loanRepayPlan.getRepaymentDate());
					if(sjDay > 0){
						if(!"2".equals(loanRepayPlan.getState())){
							//TODO 信用贷计算罚息和还款; 风险金补利息和本金需要拆分
							int dayRange = DateUtils.getDayRange(loanRepayPlan.getRepaymentDate(), DateUtils.getDate(loanDate, DateUtils.DEFAULT_DATE_PATTERN));
							//罚息
							double db=creditSignService.getFxje(contract.getLoanAmount(), product, dayRange);
							//滞纳金
							double znAmount=creditSignService.getZnje(MathUtils.add(loanRepayPlan.getPrincipalReceivable(),loanRepayPlan.getInterestReceivable()), product);
							//滞纳金 不足100 按照100计算
							znAmount=znAmount>100?znAmount:100.0;
							loanRepayPlan.setPenaltyReceivable(MathUtils.round(db, 2));		//罚息
							loanRepayPlan.setDelayReceivable(MathUtils.round(znAmount, 2));	//滞纳金		
							loanRepayPlan.setYqts(dayRange);
						}
					}
					if("1".equals(loanRepayPlan.getState()) || "3".equals(loanRepayPlan.getState())){
						loanRepayPlanList1.add(loanRepayPlan);
					}
					if(1 == loanRepayPlan.getPeriodNum()){
						map.put("hkqsrq", loanRepayPlan.getRepaymentDateStr());
					}
					if("2".equals(loanRepayPlan.getState())){//状态为已完成
						yhkqs++;
					}	
					if(loanRepayPlan.getPenaltyReceivable() > 0){
						ljwycs++;
					}
					if(sjDay != 1){
						if(!"2".equals(loanRepayPlan.getState()) && loanRepayPlan.getPenaltyReceivable() > 0){
							loanRepayPlan.setYqts(DateUtils.getDayRange(loanRepayPlan.getRepaymentDate(), new Date()));
						}						
					}
					double dyyshj = MathUtils.add(loanRepayPlan.getDelayReceivable(), MathUtils.add(loanRepayPlan.getPenaltyReceivable(), MathUtils.add(loanRepayPlan.getInterestReceivable(), MathUtils.add(loanRepayPlan.getSreviceFeeReceivable(), loanRepayPlan.getPrincipalReceivable()))));
					loanRepayPlan.setDyyshj(MathUtils.roundUp(dyyshj, 2));
					double dyshhj = MathUtils.add(loanRepayPlan.getDelayReceived(), MathUtils.add(loanRepayPlan.getPenaltyReceived(), MathUtils.add(loanRepayPlan.getInterestReceived(), MathUtils.add(loanRepayPlan.getSreviceFeeReceived(), loanRepayPlan.getPrincipalReceived()))));
					loanRepayPlan.setDyshhj(MathUtils.roundUp(dyshhj, 2));
					if("1".equals(loanRepayPlan.getState())){
						loanRepayPlan.setHkzt("未还");
					}
					if("2".equals(loanRepayPlan.getState())){
						loanRepayPlan.setHkzt("已还清");
					}
					if("3".equals(loanRepayPlan.getState())){
						loanRepayPlan.setHkzt("未还清");
					}
					if("1".equals(loanRepayPlan.getState()) || "3".equals(loanRepayPlan.getState())){
						loanRepayPlan.setBqyhkhj(MathUtils.sub(MathUtils.sub(MathUtils.sub(dyyshj, dyshhj), loanRepayPlan.getPenaltyReduce()), loanRepayPlan.getDelayReduce()));
					}else{
						loanRepayPlan.setBqyhkhj(0.00);
					}
				}
				double yhkje = MathUtils.add(repayPlanList.get(0).getPrincipalReceivable(), repayPlanList.get(0).getInterestReceivable());
				map.put("yhkje", yhkje);//月还款金额
				
				for (LoanRepayPlan loanRepayPlan : repayPlanList) {
					if(loanRepayPlan.getPenaltyReceivable() > 0){
						map.put("wyqsrq", DateUtils.toDateString(DateUtils.addDay(loanRepayPlan.getRepaymentDate(), 1)));
						break;
					}
				}
			}
			
			
			
			double ycxhkje = paymentManageService.getYcxhkje(loanRepayPlanList1, DateUtils.getDate(loanDate, DateUtils.DEFAULT_DATE_PATTERN));
			
			double maxjmje = paymentManageService.getMaxjmje(loanRepayPlanList1);
			//积木 与 鼎轩 两个渠道 都不减免服务费
			if(
//				contract.getLoanProduct().indexOf("JM")<=0 && contract.getLoanProduct().indexOf("DX")<=0 && contract.getLoanProduct().indexOf("MD")<=0
				"HD".equals(contract.getChannelType())
			  ){ 
				double fhfwf = paymentManageService.getFhfwf(repayPlanList, contract, DateUtils.getDate(loanDate, DateUtils.DEFAULT_DATE_PATTERN));
				ycxhkje = MathUtils.sub(ycxhkje, fhfwf);				
			}
			if(ycxhkje > 0)
				map.put("ycxhkje", ycxhkje);//一次性还款金额
			map.put("maxjmje", maxjmje);//最高减免金额
			map.put("yhkqs", yhkqs);
			map.put("ljwycs", ljwycs);
			map.put("repayPlanList", repayPlanList);
			map.put("yjhkrq", loanDate);
		}
		log.info(thisMethodName+":end");
		return "paymentManage/predictPayment";
	}
	
}
