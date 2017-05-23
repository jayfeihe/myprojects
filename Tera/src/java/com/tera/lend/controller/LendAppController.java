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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.service.ProcessService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.model.CreditApp;
import com.tera.credit.service.CreditAppService;
import com.tera.img.service.ImgService;
import com.tera.lend.model.LendApp;
import com.tera.lend.model.LendJsonMsg;
import com.tera.lend.model.form.LendQBean;
import com.tera.lend.service.LendAppService;
import com.tera.match.model.Lend2match;
import com.tera.match.model.MatchResult;
import com.tera.match.service.Lend2matchService;
import com.tera.match.service.MatchResultService;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Parameter;
import com.tera.sys.model.Role;
import com.tera.sys.service.DataDictionaryService;
import com.tera.sys.service.ParameterService;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.UserService;
import com.tera.util.DateUtils;
import com.tera.util.IOUtils;
import com.tera.util.JsonUtil;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.PdfUtil;
import com.tera.util.RequestUtils;


@Controller
@RequestMapping("/lendApp") 
public class LendAppController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LendAppController.class);
	
	/**
	 * LendAppService
	 */
	@Autowired(required=false) //自动注入
	private LendAppService lendAppService;

	@Autowired(required=false) //自动注入
	ProcessService processService;
	
	@Autowired(required=false) //自动注入
	ImgService imgService;
	
	@Autowired(required=false) //自动注入
	RoleService roleService;
	
	@Autowired(required=false) //自动注入
	MatchResultService<MatchResult> matchResultService;
	
	@Autowired(required=false) //自动注入
	private CreditAppService creditAppService;
	
	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;
	
	@Autowired(required=false) //自动注入
	private Lend2matchService<Lend2match> lend2matchService;
	
	@Autowired(required=false) //自动注入
	private DataDictionaryService<DataDictionary> dataDictionaryService;
	
	@Autowired(required=false) //自动注入
	ContractService contractService;
	
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	
	@Autowired(required=false) //自动注入
	private ParameterService<Parameter> parameterService;
	
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
		return "lend/app/lendQuery";
	}

	/**
	 * 显示财富端申请表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String lendAppList(String stateTask,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LendQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("loginId", loginId);
		roleMap.put("orgId", org.getOrgId());
		//查询登录用户在当前机构下的角色等级
		List<Role> roles = this.roleService.getRoleByOrgLoginId(roleMap);
		Role role = null;
		String roleLever = ""; //用户当前登录机构的最大角色等级
		if (roles.size() > 0) {
			role = roles.get(0);
			roleLever = role.getOrgRoleLever();
		}
		queryMap.put("states", new String[]{"1"});
		if(queryMap.get("orgId")==null||queryMap.get("orgId").equals("")){
			String orgId=org.getOrgId();
			queryMap.put("orgId", orgId);		//当前机构
		}
		queryMap.put("roleLever", roleLever); //角色等级
		queryMap.put("customerManager", loginId); //客户经理
		
		if("waitTask".equals(stateTask)){// 查询 当前需要 次登录人 出来的 申请
			queryMap.put("userLoginId", loginId);
			queryMap.put("bpmStates", new String[]{"录入申请"});//查询录入流程定义为录入申请的
		}else if("inTask".equals(stateTask)){// 查询 是当前登录人录入的申请
			// 进行中
			queryMap.put("nonBpmStates", new String[]{"录入申请","结束"});
		}else if("offTask".equals(stateTask)){// 查询 是当前登录人录入的申请 并且已经结束
			queryMap.put("bpmStates", new String[]{"结束"});
		}
		
		int rowsCount = this.lendAppService.queryBpmLendAppCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<LendApp> lendAppList = this.lendAppService.queryBpmLendAppList(queryMap);
		pm.setData(lendAppList);
		map.put("pm", pm);
		map.put("stateType", stateTask);
		log.info(thisMethodName+":end");
		return "lend/app/lendList";
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
	public String lendAppUpdate(String id,String customerType, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LendApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.lendAppService.queryByKey(id);
			customerType=bean.getCustomerType();
		}
		map.put("bean", bean);
		if("01".equals(customerType)){
			log.info(thisMethodName+":end");
			return "lend/app/lendUpdatePer";
		}else{
			log.info(thisMethodName+":end");
			return "lend/app/lendUpdateOrg";
		}
	}


	
	/**
	 * 保存财富端申请表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void lendAppSave(String isStart,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginid=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			//TODO service操作 需要修改
			LendApp bean = (LendApp) RequestUtils.getRequestBean(LendApp.class, request);
			try{
				Map<String, Object> returnMap = this.lendAppService.lendAppSave(bean, loginid, org, isStart);
				boolean rtBool = (Boolean) returnMap.get("rtBool");
				String rtMss = returnMap.get("rtMss") + "";
				String rtType = returnMap.get("rtType") + "";
				bean = (LendApp) returnMap.get("bean");
				String json=JsonUtil.object2json(new LendJsonMsg(rtBool, rtMss, rtType, bean));
				log.info(json);
				writer.print(json);
				writer.flush();
				writer.close();
			} catch (Exception e) {
				log.error(thisMethodName+":error",e);
				writer.print(JsonUtil.object2json(new JsonMsg(false, "失败，报错了."+e.toString())));
				writer.flush();
				writer.close();
				throw e;
			}
			
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
	public void lendAppDelete(int id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginid=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		try {
			this.lendAppService.deleteLendApp(id,loginid);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
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
	 * 跳转到查看财富端申请表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String lendAppRead(String id,String customerType, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LendApp bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.lendAppService.queryByKey(id);
		}
		map.put("bean", bean);
		if("01".equals(customerType)){
			log.info(thisMethodName+":end");
			return "lend/app/lendReadPer";
		}else{
			log.info(thisMethodName+":end");
			return "lend/app/lendReadOrg";
		}
	}

	/**
	 * 下载债权报告
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/download.do")
	public void lendAppDownLoad(String appId, HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/octet-stream");
//		response.addHeader("Content-Disposition", "attachment;filename=\""+contract.getContractNo()+".zip\"");
		response.addHeader("Content-Disposition", "attachment;filename=\""+new String((appId + DateUtils.toDateString(new Date())).getBytes("GBK"), "iso-8859-1")+".zip\"");
		OutputStream out = response.getOutputStream();
		
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		String url1=basePath+"lendApp/creditorReport.do?appId=" + appId;
		//把头信息存到 map
		Enumeration<String> names=request.getHeaderNames();
		Map<String,String> headerMap=new HashMap<String, String>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			headerMap.put(name,request.getHeader(name));
		}
		HttpClient client = new  DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url1);
		//设置请求的头信息
		for (String name : headerMap.keySet()) {
			getRequest.setHeader(name, headerMap.get(name));
		}
		
		HttpResponse getResponse = client.execute(getRequest); 
		InputStream htmlIn=getResponse.getEntity().getContent();
		
		String html1=IOUtils.read(htmlIn,"utf-8");
		ByteArrayOutputStream pdf1=new ByteArrayOutputStream();
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		PdfUtil.pringHtml2Pdf(pdf1, new ByteArrayInputStream(html1.getBytes("utf-8")) ,  companyName, "utf-8");
		ByteArrayOutputStream returnOut=new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(returnOut);
		zipOut.setEncoding("GBK");
		ZipEntry pdf1entry = new ZipEntry("债权报告.pdf");
		pdf1entry.setSize(pdf1.toByteArray().length);
		zipOut.putNextEntry(pdf1entry);
		zipOut.write(pdf1.toByteArray());
		
		zipOut.closeEntry();
		zipOut.close();
		
		out.write(returnOut.toByteArray());
		out.flush();
		out.close();	
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 债权报告生成页面
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/creditorReport.do")
	public String creditorReport(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("appId", appId);
		List<LendApp> lendAppList = lendAppService.queryList(queryMap);
		Map<String, Object> lendQueryMap = new HashMap<String, Object>();
		lendQueryMap.put("lendAppId", appId);
		List<Lend2match> lend2matchList = lend2matchService.queryList(lendQueryMap);
		Product pro = productService.queryByName(lendAppList.get(0).getProduct());
		int days = DateUtils.getDayRange(lend2matchList.get(0).getValueDate(), new Date());//出借天数
		double bxze = MathUtils.add(MathUtils.mul(MathUtils.mul(lendAppList.get(0).getAmount(), MathUtils.div(MathUtils.div(pro.getInterestRate(), 100), 360)), days), lendAppList.get(0).getAmount());
		map.put("bxze", MathUtils.roundUp(bxze, 2));
		map.put("lendApp", lendAppList.get(0));//出借信息
		map.put("lendDate", DateUtils.formatDate(lend2matchList.get(0).getValueDate()));
		map.put("lendEndDate", DateUtils.formatDate(new Date()));
		Map<String, Object> queryMap1 = new HashMap<String, Object>();
		Map<String, Object> queryMap2 = new HashMap<String, Object>();
		queryMap1.put("lendAppId", appId);
		queryMap1.put("state", "3");
		List<MatchResult> matchResultList = matchResultService.queryList(queryMap1);
		Map<String, Object> repayQueryMap = new HashMap<String, Object>();
		Map<String, Object> repayQueryMap1 = new HashMap<String, Object>();
		repayQueryMap1.put("states", new String[]{"1"});
		double jkjehj = 0.0;
		for (MatchResult matchResult : matchResultList) {
			queryMap2.put("appId", matchResult.getLoanAppId());
			List<CreditApp> creditAppList = creditAppService.queryList(queryMap2);
			matchResult.setName(creditAppList.get(0).getName());//借款人姓名
			matchResult.setIdNo(creditAppList.get(0).getIdNo().substring(0, creditAppList.get(0).getIdNo().length() - 6) + "******");//借款人身份证号
			matchResult.setComDuty(creditAppList.get(0).getComDuty());//借款人职业情况
			//字典名称
			Map<String, Object> dataQueryMap1 = new HashMap<String, Object>();
			dataQueryMap1.put("keyName", "creditusage1");
			dataQueryMap1.put("keyProp", creditAppList.get(0).getUseage1());
			List<DataDictionary> useageList1 = this.dataDictionaryService.queryList(dataQueryMap1);
			Map<String, Object> dataQueryMap2 = new HashMap<String, Object>();
			dataQueryMap2.put("keyName", "creditusage2");
			dataQueryMap2.put("keyProp", creditAppList.get(0).getUseage1());
			dataQueryMap2.put("parentKeyProp", creditAppList.get(0).getUseage1());
			List<DataDictionary> useageList2 = this.dataDictionaryService.queryList(dataQueryMap2);
			String useage = "";
			if(useageList1.size() > 0)
				useage += useageList1.get(0).getKeyValue();
			if(useageList2.size() > 0)
				useage += "  " + useageList2.get(0).getKeyValue();
			matchResult.setUseage(useage);//借款用途
			Map<String, Object> contractQueryMap = new HashMap<String, Object>();
			contractQueryMap.put("loanAppId", matchResult.getLoanAppId());
			List<Contract> contractList = contractService.queryList(contractQueryMap);
			repayQueryMap.put("contractNo", contractList.get(0).getContractNo());
			repayQueryMap.put("periodNum", "1");
			repayQueryMap1.put("contractNo", contractList.get(0).getContractNo());
			List<LoanRepayPlan> wyRepayPlanList = loanRepayPlanService.queryListExt(repayQueryMap);
			matchResult.setLoanStartDate(wyRepayPlanList.get(0).getRepaymentDateStr());//还款起始日期
			List<LoanRepayPlan> wyRepayPlanList1 = loanRepayPlanService.queryListExt(repayQueryMap1);
			matchResult.setSurplusLoanPeriod(wyRepayPlanList1.size());//剩余还款时间（月）
			jkjehj = MathUtils.add(jkjehj, matchResult.getLendAmount());
		}
		map.put("jkjehj", MathUtils.roundUp(jkjehj, 2));
		map.put("matchResultList", matchResultList);
		
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		log.info(thisMethodName+":end");
		return "lend/app/creditorReport";
	}
	
}
