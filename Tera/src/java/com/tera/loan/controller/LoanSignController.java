/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.loan.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.lend.model.LendApp;
import com.tera.lend.service.LendAppService;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanAppCollateral;
import com.tera.loan.model.LoanAppContact;
import com.tera.loan.model.LoanApproval;
import com.tera.loan.model.form.LoanQBean;
import com.tera.loan.service.LoanAppContactService;
import com.tera.loan.service.LoanAppService;
import com.tera.loan.service.LoanApprovalService;
import com.tera.loan.service.LoanSignService;
import com.tera.match.model.MatchResult;
import com.tera.match.service.MatchResultService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.PoiUtil;
import com.tera.util.RMBUpper;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * <br>
 * <b>功能：</b>DepartVerifyController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 17:47:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loan/sign")
public class LoanSignController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger
			.getLogger(LoanSignController.class);

	/**
	 * LoanSignController
	 */
	@Autowired(required = false)
	// 自动注入,借款端申请服务
	private LoanAppService<LoanApp> loanAppService;
	@Autowired(required = false)
	// 自动注入,财富端申请服务
	private LendAppService lendAppService;
	@Autowired(required = false)
	// 自动注入，签约服务
	private LoanSignService loanSignService;
	@Autowired(required = false)
	// 自动注入，借款申请审批服务
	private LoanApprovalService loanApprovalService;
	@Autowired(required = false)
	// 自动注入，撮合结果服务
	private MatchResultService<MatchResult> matchResultService;
	@Autowired(required = false)
	// 自动注入，合同服务
	private ContractService contractService;
	@Autowired(required = false)
	private LoanAppContactService<LoanAppContact> loanAppContactService;

	/**
	 * 跳转到签约的查询条件页面
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
	public String loanSignQuery(HttpServletRequest request, ModelMap map)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "loan/sign/loanSignQuery";
	}

	@RequestMapping("/saveContract.do")
	public void createContract(String id, String signTime,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		String currentLoginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Contract contract = new Contract();
			LoanApp loanApp = loanAppService.queryByKey(id);
			System.out.println(id);
			System.out.println(request.getParameter("id"));
			
			// 合同号
			String contractno = "";
			// 如果合同为空，则添加。
			if (contractService.getContractByAppId(loanApp.getAppId(),"01","").size() == 0) {
				// 自动生成的合同号
				contractno = this.loanSignService.getContractNo(request.getParameter("id"));
				System.out.println("生成的合同号是：" + contractno);
				contract.setContractNo(contractno);
				contract.setLoanIdNo(loanApp.getIdNo());
				contract.setLoanAppId(loanApp.getAppId());
				contract.setLoanIdType(loanApp.getIdType());
				contract.setLoanName(loanApp.getName());
				contract.setLoanServiceRate(loanApp.getSreviceFeeRate());
				contract.setOperator(currentLoginId);
				MatchResult matchResult=new MatchResult();
				if (matchResultService.getResultByAppId(loanApp.getAppId()).size()>0) {
					matchResult = matchResultService.getResultByAppId(loanApp.getAppId()).get(0);
				
				
				contract.setLoanProduct(matchResult.getLoanProduct());
				// TODO：根据Product获取期限、利率、服务费率吗？合同里的产品是什么ID还是产品名字
				contract.setLoanPeriod(matchResult.getLoanPeriod());
				contract.setLoanInterestRate(matchResult.getLoanInterestRate());
				contract.setOrgId(matchResult.getOrgId());
				}
				LoanApproval loanApproval=loanApprovalService.queryByKey(loanApp.getAppId());
				contract.setLoanAmount(loanApproval.getApprovalAmount());
				// contract.setContractType(hetong);//TODO:暂时都是线上合同
				contract.setContractType("01");// TODO:暂时都是线上合同
				contract.setRepayMethod("01"); // TODO：目前都为01
				contract.setContractClass("01");
				contract.setState("0");
				// TODO:合同签约日期.
				System.out.println("签约时间+="+signTime);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
				java.util.Date date=sdf.parse(signTime);  
				contract.setSignDate(date);
				// 开始日期,、结束日期-不能填
//				contract.setStartDate(date);
//				Date endDate=DateUtils.addMonth(date,contract.getLoanPeriod());
//				endDate=DateUtils.addDay(endDate, -1);
//				contract.setEndDate(endDate);
				contract.setLendAppId("待定");// 不能为空
				// TODO:出借人姓名为空.
				// TODO:出借人证件类型为空.
				// TODO:出借人证件号码为空.
				// 创建日期. service添加了
				// 修改日期. service添加了
				// 合同生成日期.service添加
				this.loanSignService.createContract(contract);
				writer.print(JsonUtil.object2json(new JsonMsg(true, contractno)));

			} else {// 如果不为空，则查出来,如果修改了签约时间，则修改
				contract = contractService.getContractByAppId(loanApp.getAppId(),"01","").get(0);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
				java.util.Date date=sdf.parse(signTime);  
				contract.setSignDate(date);
//				// 开始日期,、结束日期
//				contract.setStartDate(date);
//				Date endDate=DateUtils.addMonth(date,contract.getLoanPeriod());
//				endDate=DateUtils.addDay(endDate, -1);
//				contract.setEndDate(endDate);
				contractno = contract.getContractNo();
				this.loanSignService.updateContract(contract);
				writer.print(JsonUtil.object2json(new JsonMsg(true, contractno)));
			}
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	@RequestMapping("/downloadContract.do")
	public void downloadContract(String id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanApp loanApp = loanAppService.queryByKey(id);
		Contract contract=contractService.getContractByAppId(loanApp.getAppId(), "01", "").get(0);
		// 下载合同
		response.setContentType("application/x-msdownload");
		response.addHeader("Content-Disposition", "attachment; filename=\""+ contract.getContractNo() + ".docx\"");
		// 获得出借List---->lendList
		List<List<String>> lendList = new ArrayList<List<String>>();
		List<LendApp> lends = lendAppService.getLendListByContractNo(contract.getContractNo());
		for (int i = 0, j = lends.size(); i < j; i++) {
			List<String> lend =new ArrayList<String>();
			LendApp lendApp = lends.get(i);
			lend.add(lendApp.getName());
			lend.add(lendApp.getIdNo());
			//必须转换成String类型，否则会报错
			lend.add(StringUtils.formatNumber(lendApp.getAmount(),2)+"");
			lend.add(lendApp.getAddress());
			lendList.add(lend);
		}
		// 获得借款List---->loanList
		List<List<String>> loanList = new ArrayList<List<String>>();
		List<LoanApp> loans = loanAppService.getLoanListByContractNo(contract.getContractNo());
		for (int i = 0, j = loans.size(); i < j; i++) {
			List<String> loan =new ArrayList<String>();
			LoanApp loanApp2 = loans.get(i);
			loan.add(loanApp2.getName());
			loan.add(loanApp2.getIdNo());
			loan.add(StringUtils.formatNumber(loanApp2.getAmount(),2)+"");
			
			//String loanAddress = loanApp2.getAddProvince()+ loanApp2.getAddCity() + loanApp2.getAddCounty()+ loanApp2.getAddress();
			loan.add(loanApp2.getAddress());
			loanList.add(loan);
		}
		PoiUtil poi = new PoiUtil();
		poi.setClassPathDocument("config/contract/贷款合同_v20140701.docx"); // 模板
		poi.setTablesValue(lendList, 0); // 表 1
		poi.setTablesValue(loanList, 1); // 表2
		// 设置字段替代值
		Map<String, String> mapValue = new HashMap<String, String>();
		// 字段
		mapValue.put("contractno",contract.getContractNo());
		mapValue.put("lendAmount", RMBUpper.toBigAmt(contract.getLoanAmount()));
		mapValue.put("CNlendAmount",StringUtils.formatNumber(contract.getLoanAmount(),2)+"");
		String use=loanSignService.getUse( loanApp.getUseage());
		mapValue.put("useage",use);
		
		mapValue.put("detailUseage",loanApp.getDetailUseage());
		
		Date signDate=contract.getSignDate();
		int month=contract.getLoanPeriod();
		Date dateEnd=DateUtils.addMonth(signDate, month);
		dateEnd=DateUtils.addDay(dateEnd, -1);
		
		
		mapValue.put("startY",DateUtils.getYear(signDate)+"");   //待定TODO：
		mapValue.put("startM",DateUtils.getMonth(signDate)+"");
		mapValue.put("startD",DateUtils.getDay(signDate)+"");
		mapValue.put("endY",DateUtils.getYear(dateEnd)+"");   //待定TODO：
		mapValue.put("endM",DateUtils.getMonth(dateEnd)+"");
		mapValue.put("end",DateUtils.getDay(dateEnd)+"");
		
		
		mapValue.put("intRate",Double.toString(loanApp.getInterestRate()));
		mapValue.put("lendAccName",loanApp.getLendAccName());
		mapValue.put("lendAccBank",loanApp.getLendAccBank());
		mapValue.put("lendAccount",loanApp.getLendAccount());
		mapValue.put("mobile", loanApp.getMobile());  
		poi.replaceTextToText(mapValue);
		ServletOutputStream servletOS = response.getOutputStream();
		servletOS.write(poi.getDocx());
		servletOS.flush();
		servletOS.close();	
		log.info(thisMethodName+":end");
	}
	

	/**
	 * 显示签约的查询列表
	 * 
	 * @param request
	 *            request
	 * @param map
	 *            map
	 * @throws Exception
	 *             exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String loanSignList(HttpServletRequest request, ModelMap map)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		// -----------获得当前登陆用户被分配的审核任务列表---------------
		// 1.获得当前登陆用户
		// 2.获得当前登陆用户的机构
		// -----------根据以上两个条件，查询关联信息，才是当前登陆用户的代办工作。
		// 查询T_LOAN_2MATCH表，查询state=2的信息，即撮合成功未签合同的申请。

		PageModel pm = new PageModel();
		LoanQBean bean = (LoanQBean) RequestUtils.getRequestBean(
				LoanQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);

		/**
		 * 1、取得当前登陆的用户ID 存入OPERATOR="当前用户"
		 */
		// 1.获取当前登陆用户Id
		String currentLoginId = (String) request.getSession().getAttribute(
				SysConstants.LOGIN_ID);
		queryMap.put("userLoginId", currentLoginId);

		/**
		 * 2、获得当前登陆用户的机构 存入
		 */
		if (bean.getOrgId() == null || bean.getOrgId().equals("")) {
			Org org = (Org) request.getSession().getAttribute(
					SysConstants.LOGIN_ORG);
			String orgId = org.getOrgId();
			queryMap.put("orgId", orgId);
		}

		int rowsCount = this.loanSignService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<LoanApp> loanSignList = this.loanSignService.queryList(queryMap);
		pm.setData(loanSignList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "loan/sign/loanSignList";
	}

	/**
	 * 保存借款申请审批表数据
	 * 
	 * @param request
	 *            request
	 * @param map
	 *            map
	 * @throws Exception
	 *             exception
	 */
	@RequestMapping("/save.do")
	public void loanSignSave(String subbpm, String auditResult,
			String denyToRole, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		System.out.println("-------------" + request.getParameter("id"));
	
		try {
			// TODO service操作 需要修改
			LoanApp bean = loanAppService
					.queryByKey(request.getParameter("id"));
			Org org = (Org) request.getSession().getAttribute(
					SysConstants.LOGIN_ORG);
			String loginid = (String) request.getSession().getAttribute(
					SysConstants.LOGIN_ID);
			
			// 如果存在
			if (bean.getAppId() != null) {
				// 提交 走流程
				if ("trueSubbpm".equals(subbpm)) {
					this.loanSignService.bpmNext(loginid, denyToRole,
							auditResult, bean, org);
					writer.print(JsonUtil.object2json(new JsonMsg(true,
							"审核已提交！")));
				} else {
					writer.print(JsonUtil
							.object2json(new JsonMsg(true, "保存成功！")));
				}
			} else {
				writer.print(JsonUtil.object2json(new JsonMsg(true,
						"保存失败，申请ID不存在")));
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}

		log.info(thisMethodName+":end");
	}
	/**
	 * 跳转到签约审批表的页面
	 * 
	 * @param id
	 *            id
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param map
	 *            map
	 * @throws Exception
	 *             exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String loanSignUpdate(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanApp bean =new LoanApp();
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.loanAppService.queryByKey(id);
		}
		// 根据appId查询审批结果
		LoanApproval loanApproval = loanApprovalService.queryByKey(bean.getAppId());
		map.put("bean", bean);
		map.put("id", id);
		map.put("loanApproval", loanApproval);
		// ---------------------------------------------------
		// 显示该申请号对应的撮合结果，显示所有借助人信息列表
		PageModel pm = new PageModel();
		LoanQBean qBean = (LoanQBean) RequestUtils.getRequestBean(
				LoanQBean.class, request);
		Map<String, Object> beanMap = ObjectUtils.describe(qBean);
		beanMap.put("loanAppId", bean.getAppId());
		pm.init(request, 0, null, qBean);
		beanMap.put("rowS", pm.getRowS());
		beanMap.put("rowE", pm.getRowE());
		List<MatchResult> results = this.matchResultService.queryListInSign(beanMap);
		pm.setData(results);
		map.put("pm", pm);
		//---------------------------------------------------
		//查询该申请下的所有房产抵押物--type=01
		PageModel pm2=new PageModel();
		pm2.init(request, 0, null, qBean);
		List<LoanAppCollateral> loanAppCollaterals=this.loanSignService.queryCollateralByAppId(bean.getAppId(), "01");
		pm2.setData(loanAppCollaterals);
		map.put("pm2", pm2);
		
		//----------------------------------------------------------------------
		//查询是否该申请的主合同
		List<Contract> contracts=contractService.getContractByAppId(bean.getAppId(), "01", "");
		
		if (contracts.size()>0 ){
			map.put("contractno", contracts.get(0).getContractNo());
		}
		
		log.info(thisMethodName+":end");
		return "loan/sign/loanSignUpdate";
	}
	/**
	 * 生成抵押物合同
	 * @param id
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/createCollateralContract.do")
	public void createCollateralContract(String id,String diyaId,HttpServletRequest request, HttpServletResponse response,ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//获得当前登录人ID
		String currentLoginId = (String) request.getSession().getAttribute(
				SysConstants.LOGIN_ID);
		System.out.println("diyaId============"+request.getParameter("diyaId"));
		//String diyaId=request.getParameter("diyaId");
		System.out.println("diyaId============"+diyaId);
		//获得借款申请实体
		LoanApp loanApp=loanAppService.queryByKey(id);
		String loanAppId=loanApp.getAppId();
		
		//获得抵押物序号
		LoanAppCollateral loanAppCollateral=loanSignService.queryCollateralByKey(diyaId);
		int flag=loanAppCollateral.getSeqFlag();
		
		//获得该借款申请的借款合同信息
		Contract contract=contractService.getContractByAppId(loanAppId, "01", "").get(0);
		String contractNo=contract.getContractNo();
		contractNo=contractNo+"-"+String.valueOf(flag);
		if (contract != null) {
			//-----重新装配需要变化的公共属性------------------------------
			contract.setContractClass("02");//抵押物借款合同各类型为02
			contract.setOperator(currentLoginId);//设置当前操作人
			contract.setState("0");//设置当前状态为0，无效合同
			contract.setExt1(Integer.valueOf(diyaId));
			contract.setContractNo(contractNo);
			//添加或修改合同信息
			loanSignService.saveContract(contract);
			// 下载合同
			response.setContentType("application/x-msdownload");
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ contractNo + ".docx\"");
			PoiUtil poi = new PoiUtil();
			poi.setClassPathDocument("config/contract/房屋抵押担保合同_v20140701.docx"); // 模板
			List<LoanAppContact> contacts= loanAppContactService.queryCollList(loanAppId, "", String.valueOf(flag));
			String name="";
			String idNo="";
			String name2="";
			String mobile="";
			for(LoanAppContact contact:contacts){
				if ("01".equals(contact.getContactType())) {
					name=name+","+contact.getName(); 
					idNo=idNo+","+contact.getIdNo();
					mobile=mobile+","+contact.getMobile();
				} else if ("02".equals(contact.getContactType())) {
					name2=name2+","+contact.getName2();
				}
			}
			
			name=name.substring(1);
			idNo=idNo.substring(1);
			name2=name2.substring(1);
			mobile=mobile.substring(1);
			
			String mainAddress=loanApp.getAddProvince()+loanApp.getAddCity()+loanApp.getAddCounty()+loanApp.getAddress();//主借款人地址
			String houseAddress=loanAppCollateral.getAddProvice()+loanAppCollateral.getAddCity()+loanAppCollateral.getAddCounty()+loanAppCollateral.getAddress();//房屋地址
			String houseArea=String.valueOf(loanAppCollateral.getHouseSize()); //房屋面积
			String houseNo=loanAppCollateral.getCertificate1();  //房产证编号
			String lendAmount=RMBUpper.toBigAmt(contract.getLoanAmount()); //借款金额
			//出借人列表 
			String lendName="";
			Contract contract2=contractService.getContractByAppId(loanAppId, "01", "").get(0);
			String contractNo2=contract2.getContractNo();
			List<List<String>> lendList = new ArrayList<List<String>>();
			List<LendApp> lends = lendAppService.getLendListByContractNo(contractNo2);
			for(LendApp lendApp:lends){
				lendName=lendName+","+lendApp.getName();
			}
			lendName=lendName.substring(1);
			// 设置字段替代值
			Map<String, String> mapValue = new HashMap<String, String>();
			// 字段
			mapValue.put("contractno",contractNo2);
			mapValue.put("contract",contractNo);
			mapValue.put("name",name);
			mapValue.put("idNo",idNo);
			mapValue.put("name2",name2);
			mapValue.put("mainAddress",mainAddress);
			mapValue.put("houseAddress",houseAddress);
			mapValue.put("mobile",mobile);
			mapValue.put("lendName",lendName);
			mapValue.put("houseNo",houseNo);
			mapValue.put("houseArea",houseArea);
			mapValue.put("lend",lendAmount);
			poi.replaceTextToText(mapValue);
			ServletOutputStream servletOS = response.getOutputStream();
			servletOS.write(poi.getDocx());
			servletOS.flush();
			servletOS.close();	
		}
		log.info(thisMethodName+":end");
	}
}

