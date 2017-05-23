/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.car.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.car.constant.Constants;
import com.tera.car.model.CarApp;
import com.tera.car.model.CarContact;
import com.tera.car.model.CarDecision;
import com.tera.car.model.form.CarQBean;
import com.tera.car.model.form.SignFBean;
import com.tera.car.service.CarAppService;
import com.tera.car.service.CarContactService;
import com.tera.car.service.CarDecisionService;
import com.tera.car.service.CarExtService;
import com.tera.car.service.CarSignService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.cooperation.dinxuan.constants.ContractDingXuanConstants;
import com.tera.img.service.ImgService;
import com.tera.match.model.MatchResult;
import com.tera.match.service.MatchResultService;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.product.model.Product;
import com.tera.product.model.ProductFeeRate;
import com.tera.product.service.ProductFeeRateService;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Parameter;
import com.tera.sys.model.ResultObj;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.DataDictionaryService;
import com.tera.sys.service.ParameterService;
import com.tera.sys.service.RoleService;
import com.tera.util.IOUtils;
import com.tera.util.JsonUtil;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.PdfUtil;
import com.tera.util.RMBUpper;

/**
 * 
 * 信用贷款签约控制器
 * <b>功能：</b>carAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/car/sign")
public class CarSignController  extends BaseController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CarSignController.class);
	
	@Autowired(required=false) //自动注入
	private CarAppService carAppService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false) //自动注入
	ImgService imgService;
	@Autowired(required=false) //自动注入
	CarContactService carContactService;
	@Autowired(required=false) //自动注入
	CarExtService carExtService;
	@Autowired(required=false) //自动注入
	CarSignService carSignService;
	@Autowired(required=false) //自动注入
	ContractService contractService;
	@Autowired(required=false) //自动注入
	CarDecisionService carDecisionService;
	@Autowired(required=false) //自动注入
	MatchResultService<MatchResult> matchResultService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false) //自动注入
	private ProductFeeRateService productFeeRateService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	private DataDictionaryService<DataDictionary> dataDictionaryService;
	@Autowired(required=false) //自动注入
	private ParameterService<Parameter> parameterService;
	/**
	 * 签约首页
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query.do")
	public String carSignQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "car/sign/signQuery";
	}

	/**
	 * 签约 list 页面
	 * @param qBean
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String carSignList(CarQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		
		if(queryMap.get("orgId")==null||"".equals(queryMap.get("orgId"))){
			queryMap.put("orgId", sessionOrg.getOrgId()); //session 机构
		}
		
		queryMap.put("nonStates", new String[]{"0"}); //状态
		queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_SIGN});//查询流程签约
		
		
		queryMap.put("processer", loginId); // 审核人
		queryMap.put("customerManager", loginId);
		
		if("".equals(queryMap.get("processer"))||user.getIsAdmin()==1){
			queryMap.remove("processer");
			queryMap.remove("customerManager");
		}
		Map<String, Object> beanMap=new HashMap<String, Object>();
		beanMap.put("loginId",loginId);
		beanMap.put("orgId",map.get("orgId"));
		List<Role> loginRoles=roleService.getRoleByOrgLoginId(beanMap);
		for (Role role : loginRoles) {
			if("1".equals(role.getFlag())){
				queryMap.remove("processer");
				queryMap.remove("customerManager");
				break;
			}
		}
		
		if("waitTask".equals(qBean.getStateTask())){
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_SIGN});//查询录入流程定义为签约
		}else if("inTask".equals(qBean.getStateTask())){
			queryMap.remove("processer");
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_REVIEW,Constants.PROCESS_STATE_LENDING});
		}else if("offTask".equals(qBean.getStateTask())){
			queryMap.remove("processer");
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_END_APP});
		}	
		int rowsCount = this.carAppService.queryBpmAppAndContractCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CarApp> carAppList = this.carAppService.queryBpmAppAndContractList(queryMap);
		pm.setData(carAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "car/sign/signList";
	}

	/**
	 * 签约操作页面
	 * @param id
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update.do")
	public String carSignUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		log.info(thisMethodName+":start");
		CarApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.carAppService.queryByKey(id);
			map.put("bean", bean);
			List<Contract> contractList = contractService.getContractByAppId(bean.getAppId(), null, null);
			Contract contract = null;
			if(contractList.size() > 0)
				contract = contractList.get(0);
			else{
				contract = new Contract();
				contract.setId(0);
				contract.setState("0");
			}
			map.put("contract", contract);
			
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "0");
			List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
			if(carDecisionList.size() > 0){
				Product pro=productService.queryByName(carDecisionList.get(0).getProduct());
				map.put("decision", carDecisionList.get(0));
				double htjkje = 0.0;
				double MAmount = 0.0;
				if(contractList.size() > 0){
					htjkje = contractList.get(0).getLoanAmount();
					Map<String, Object> fmap1 = new HashMap<String, Object>();
					fmap1.put("contractNo", contractList.get(0).getContractNo());
					List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap1);
					if(loanRepayPlanList.size() > 0)
						MAmount = MathUtils.add(loanRepayPlanList.get(0).getInterestReceivable(), loanRepayPlanList.get(0).getPrincipalReceivable());
					else
						MAmount = carSignService.getYhkje(htjkje, pro);
				}else{
					htjkje = carSignService.getHtJkje(carDecisionList.get(0), pro);
					MAmount = carSignService.getYhkje(htjkje, pro);
				}
				MAmount = MathUtils.roundUp(MAmount, 2);
				htjkje = MathUtils.round(htjkje, 2);
				map.put("MAmount", MAmount);//月还款及金额
				map.put("actualAmount", htjkje);//合同借款金额	
			}
			List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_REVIEW);
			if(bpmLogList.size() > 0){
				map.put("returnReasons", bpmLogList.get(0).getLogContent2());//复核退回原因
			}
			//营业部经理只有查看,领取单子待处理人loginId=toBeProcessed,可操作
			map.put("loginId", loginId);
			List<BpmTask> bpmTaskList = processService.getProcessInstanceByBizKey(bean.getAppId());
			String toBeProcessed= bpmTaskList.get(0).getProcesser();
			map.put("toBeProcessed", toBeProcessed);
		}
		log.info(thisMethodName+":end");
		return "car/sign/signUpdate";
	}
	
	/**
	 * 撮合 list 页面
	 * @param qBean
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bringTogetherList.do")
	public String bringTogetherList(String carAppId, String state, HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		List<MatchResult> matchResultList = matchResultService.queryListByLoanAppId(carAppId);
		pm.init(request, matchResultList.size(), null, carAppId);
		pm.setData(matchResultList);
		map.put("pm1", pm);
		List<Contract> contractList = contractService.getContractByAppId(carAppId, null, null);
		Contract contract = null;
		if(contractList.size() > 0)
			contract = contractList.get(0);
		else{
			contract = new Contract();
			contract.setId(0);
			contract.setState("0");
		}
		map.put("contract", contract);
		map.put("appId", carAppId);
		map.put("state", state);
		log.info(thisMethodName+":end");
		return "car/sign/bringTogetherList";
	}

	/**
	 * 签约 操作类
	 * @param signFBean
	 * @param bingding
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public void carSignSave(SignFBean signFBean, BindingResult bingding, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if(bingding.hasErrors()) {
			List<ObjectError> errors = bingding.getAllErrors();
			for (ObjectError error : errors) {
				log.info(error.getCodes() + ", "+ error.getObjectName() + ", " + error.getDefaultMessage());
			}
			System.out.println("有参数未符合要求");
		}
		try {
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", signFBean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "0");
			List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
			CarDecision decision=carDecisionList.get(0);
			Product pro = productService.queryByName(decision.getProduct());
//			if(pro.getName().indexOf("JM")>0){//特殊流程  JM 合作
			if("JM".equals(pro.getBelongChannel())){//特殊流程  JM 合作
				ResultObj resultObj = carSignService.saveContractJM(signFBean, loginId, org.getOrgId());			
				writer.print(JsonUtil.object2json(new JsonMsg(resultObj.isSuccess(), resultObj.getResult() + "")));
//			}else if(pro.getName().indexOf("DX")>0){// 鼎轩合作
			}else if("DX".equals(pro.getBelongChannel())){// 鼎轩合作
				//这里复用 JM的 方法，业务 逻辑和 JM 一样
				ResultObj resultObj = carSignService.saveContractJM(signFBean, loginId, org.getOrgId());			
				writer.print(JsonUtil.object2json(new JsonMsg(resultObj.isSuccess(), resultObj.getResult() + "")));
//			}else if(pro.getName().indexOf("MD")>0){// 鼎轩合作
			}else if("MD".equals(pro.getBelongChannel())){// MD合作
				//这里复用 JM的 方法，业务 逻辑和 JM 一样
				ResultObj resultObj = carSignService.saveContractJM(signFBean, loginId, org.getOrgId());			
				writer.print(JsonUtil.object2json(new JsonMsg(resultObj.isSuccess(), resultObj.getResult() + "")));
			}else if("RY".equals(pro.getBelongChannel())){// 中海软银
				//这里复用 JM的 方法，业务 逻辑和 JM 一样
				ResultObj resultObj = carSignService.saveContractJM(signFBean, loginId, org.getOrgId());			
				writer.print(JsonUtil.object2json(new JsonMsg(resultObj.isSuccess(), resultObj.getResult() + "")));
			}else{//正常流程
				ResultObj resultObj = carSignService.saveContract(signFBean, loginId, org.getOrgId());			
				writer.print(JsonUtil.object2json(new JsonMsg(resultObj.isSuccess(), resultObj.getResult() + "")));
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
	 * 下载合同
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/download.do")
	public void carSignDownLoad(String appId,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		//如果存在
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("loanAppId", appId);
//		fmap.put("contractClass", "01");
//		fmap.put("contractType", "01");
		fmap.put("state", "1");
		Contract contract=contractService.queryList(fmap).get(0);
		response.setContentType("application/octet-stream");
//		response.addHeader("Content-Disposition", "attachment;filename=\""+contract.getContractNo()+".zip\"");
		response.addHeader("Content-Disposition", "attachment;filename=\""+new String(contract.getContractNo().getBytes("GBK"), "iso-8859-1")+".zip\"");
		OutputStream out = response.getOutputStream();
		
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		
		String url1=null,url2=null,url3=null,url4=null,url5=null;
		//增加判断,积木和内部，鼎轩,软银
//		if(contract.getLoanProduct().indexOf("DX")>=0){
		if("DX".equals(contract.getChannelType())){
			//鼎轩
			url1=basePath+"car/sign/hktxDXV.do?appId=" + appId;
			url2=basePath+"car/sign/jkxyDXV.do?appId=" + appId;
			url3=basePath+"car/sign/rzfwDXV.do?appId=" + appId;
			url4=basePath+"car/sign/wthkDXV.do?appId=" + appId;
			url5=basePath+"car/sign/wttxDXV.do?appId=" + appId;
		}else if("RY".equals(contract.getChannelType())){
			//软银
			url1=basePath+"car/sign/hktxRYV.do?appId=" + appId;
			 url2=basePath+"car/sign/jkxyRYV.do?appId=" + appId;
			url3=basePath+"car/sign/rzfwRYV.do?appId=" + appId;
			url4=basePath+"car/sign/wthkRYV.do?appId=" + appId;
		}else{
			//积木和内部产品 合同路径
			url1=basePath+"car/sign/hktxV.do?appId=" + appId;
			url2=basePath+"car/sign/jkxyV.do?appId=" + appId;
			url3=basePath+"car/sign/rzfwV.do?appId=" + appId;
			url4=basePath+"car/sign/wthkV.do?appId=" + appId;
		}
		
		String html1,html2,html3,html4,html5;
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
		
		html1=IOUtils.read(htmlIn,"utf-8");
		//第二次请求
		getRequest = new HttpGet(url2);
		//设置请求的头信息
		for (String name : headerMap.keySet()) {
			getRequest.setHeader(name, headerMap.get(name));
		}
		getResponse = client.execute(getRequest); 
		htmlIn=getResponse.getEntity().getContent();
		html2=IOUtils.read(htmlIn,"utf-8");
		//第三次请求
		getRequest = new HttpGet(url3);
		//设置请求的头信息
		for (String name : headerMap.keySet()) {
			getRequest.setHeader(name, headerMap.get(name));
		}
		getResponse = client.execute(getRequest); 
		htmlIn=getResponse.getEntity().getContent();
		html3=IOUtils.read(htmlIn,"utf-8");
		//第四次请求
		getRequest = new HttpGet(url4);
		//设置请求的头信息
		for (String name : headerMap.keySet()) {
			getRequest.setHeader(name, headerMap.get(name));
		}
		getResponse = client.execute(getRequest); 
		htmlIn=getResponse.getEntity().getContent();
		html4=IOUtils.read(htmlIn,"utf-8");
		
		//获取合同编号,编入获取印章的url
		String urlOfAddContent=basePath+"seal/getSealImage.do?contractNo="+contract.getContractNo();
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		
		ByteArrayOutputStream pdf1=new ByteArrayOutputStream();
		PdfUtil.pringHtml2Pdf(pdf1, new ByteArrayInputStream(html1.getBytes("utf-8")) ,  companyName, "utf-8");
		
		ByteArrayOutputStream pdf2=new ByteArrayOutputStream();
		PdfUtil.pringHtml2Pdf(pdf2, new ByteArrayInputStream(html2.getBytes("utf-8")) ,  companyName, "utf-8");
		
		ByteArrayOutputStream pdf3=new ByteArrayOutputStream();
		PdfUtil.pringHtml2Pdf(pdf3, new ByteArrayInputStream(html3.getBytes("utf-8")) ,  companyName, "utf-8");
		
		ByteArrayOutputStream pdf4=new ByteArrayOutputStream();
		PdfUtil.pringHtml2Pdf(pdf4, new ByteArrayInputStream(html4.getBytes("utf-8")) ,  companyName, "utf-8");
		
		//软银机构的采用动态刻章的方法
		if("RY".equals(contract.getChannelType())){
			//设置需要扣章页的位置
			Map mapPos1=new HashMap();
			mapPos1.put(1, new int[]{385,300});
			PdfUtil.addContent(pdf1.toByteArray(),pdf1,urlOfAddContent,mapPos1);//覆盖电子印章
			Map mapPos3=new HashMap();
			mapPos3.put(1, new int[]{350,130});
			mapPos3.put(2, new int[]{350,130});
			mapPos3.put(3, new int[]{350,130});
			mapPos3.put(4, new int[]{350,130});
			mapPos3.put(5, new int[]{350,130});
			mapPos3.put(6, new int[]{350,130});
			mapPos3.put(7, new int[]{50,270});
			PdfUtil.addContent(pdf3.toByteArray(),pdf3,urlOfAddContent,mapPos3);//覆盖电子印章
			 
		}
		ByteArrayOutputStream returnOut=new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(returnOut);
		zipOut.setEncoding("GBK");
		//添加第一个pdf
		ZipEntry pdf1entry = new ZipEntry("还款事项提醒函.pdf");
		pdf1entry.setSize(pdf1.toByteArray().length);
		zipOut.putNextEntry(pdf1entry);
		zipOut.write(pdf1.toByteArray());
		//添加第2个pdf
		ZipEntry pdf2entry = new ZipEntry("借款协议.pdf");
		pdf1entry.setSize(pdf2.toByteArray().length);
		zipOut.putNextEntry(pdf2entry);
		zipOut.write(pdf2.toByteArray());
		//添加第3个pdf
		ZipEntry pdf3entry = new ZipEntry("融资服务协议.pdf");
		pdf1entry.setSize(pdf3.toByteArray().length);
		zipOut.putNextEntry(pdf3entry);
		zipOut.write(pdf3.toByteArray());
		//添加第4个pdf
		ZipEntry pdf4entry = new ZipEntry("委托划扣授权书.pdf");
		pdf1entry.setSize(pdf4.toByteArray().length);
		zipOut.putNextEntry(pdf4entry);
		zipOut.write(pdf4.toByteArray());

//		if(contract.getLoanProduct().indexOf("DX")>=0){
		if("DX".equals(contract.getChannelType())){
			//第五次请求
			getRequest = new HttpGet(url5);
			//设置请求的头信息
			for (String name : headerMap.keySet()) {
				getRequest.setHeader(name, headerMap.get(name));
			}
			getResponse = client.execute(getRequest); 
			htmlIn=getResponse.getEntity().getContent();
			html5=IOUtils.read(htmlIn,"utf-8");
			ByteArrayOutputStream pdf5=new ByteArrayOutputStream();
			PdfUtil.pringHtml2Pdf(pdf5, new ByteArrayInputStream(html5.getBytes("utf-8")) ,  companyName, "utf-8");
			 
			
			//添加第5个pdf
			ZipEntry pdf5entry = new ZipEntry("委托提现授权书.pdf");
			pdf1entry.setSize(pdf5.toByteArray().length);
			zipOut.putNextEntry(pdf5entry);
			zipOut.write(pdf5.toByteArray());
		}
		
		zipOut.closeEntry();
		zipOut.close();

		
		out.write(returnOut.toByteArray());
		out.flush();
		out.close();	
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 还款事项提醒函-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hktxV.do")
	public String hktxV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		CarApp bean  = carAppList.get(0);
		map.put("bean", bean);
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", bean.getAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		double zkbjse = 0.0;
		double MAmount = 0.0;
		Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
		if(carDecisionList.size() > 0){
			map.put("decision", carDecisionList.get(0));
			map.put("product", pro);
//			zkbjse = carSignService.getHtJkje(carDecisionList.get(0), pro);
			
			zkbjse = contractList.get(0).getLoanAmount();
			map.put("zkbjse", zkbjse);//借款本金数额
			//createAllAmount(map, zkbjse, "zkbjse");//缺拆开的借款本金数额
			Map<String, Object> fmap1 = new HashMap<String, Object>();
			fmap1.put("contractNo", contractList.get(0).getContractNo());
			List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap1);
			
//			MAmount = carSignService.getYhkje(zkbjse, pro);
			MAmount = MathUtils.add(loanRepayPlanList.get(0).getInterestReceivable(), loanRepayPlanList.get(0).getPrincipalReceivable());
			
			double znje = carSignService.getZnje(MAmount, pro);//滞纳金额
			double fxje1 = carSignService.getFxje(zkbjse, pro, 1);//一天的罚息金额
			double fxje15 = carSignService.getFxje(zkbjse, pro, 15);//十五天的罚息金额
			double yqwyj1 = 0.0;
			double yqwyj15 = 0.0;
			if(znje <= 100){
				yqwyj1 = MathUtils.add(100.0, fxje1);
				yqwyj15 = MathUtils.add(100.0, fxje15);
			}else{
				yqwyj1 = MathUtils.add(znje, fxje1);
				yqwyj15 = MathUtils.add(znje, fxje15);
			}
			yqwyj1 = MathUtils.round(yqwyj1, 2);
			yqwyj15 = MathUtils.round(yqwyj15, 2);
			map.put("yqwyj1", yqwyj1);
			map.put("yqwyj15", yqwyj15);
			MAmount = MathUtils.roundUp(MAmount, 2);
			map.put("MAmountUpperCase", RMBUpper.toBigAmt(MAmount));//月偿还本息数额大写	
			map.put("MAmountLowerCase", MAmount);//月偿还本息数额小写
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		int day = date;
		if(month == 2 && date == 29)
			day = 28;
		if(date == 31)
			day = 30;
		map.put("day", day);//还款日
		Map<String, Object> fmap1 = new HashMap<String, Object>();
		Map<String, Object> fmap2 = new HashMap<String, Object>();
		fmap1.put("contractNo", contractList.get(0).getContractNo());
		List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap1);
		for (LoanRepayPlan loanRepayPlan : loanRepayPlanList) {
			double dyhke = MathUtils.add(loanRepayPlan.getInterestReceivable(), loanRepayPlan.getPrincipalReceivable());
			fmap2.put("name", carDecisionList.get(0).getProduct());
			fmap2.put("periodNum", loanRepayPlan.getPeriodNum());
			//一次性结清金额=剩余本金-返还服务费+当月还款额
			//剩余本金=上期剩余本金（第一期为合同借款金额）-月还本金
			//返还服务费=趸交服务费总额*服务费减免率
			//趸交服务费总额=合同借款金额-放款金额
			List<ProductFeeRate> productFeeRateList = productFeeRateService.queryList(fmap2);	
			double ycxhkje = MathUtils.add(MathUtils.sub(loanRepayPlan.getRestPrincipal(),MathUtils.mul(MathUtils.sub(zkbjse, carDecisionList.get(0).getAmount()), MathUtils.div(productFeeRateList.get(0).getSreviceFeeReduceRate(), 100.0))), dyhke);
			ycxhkje = MathUtils.round(ycxhkje, 2);
			loanRepayPlan.setRepaySum(ycxhkje);//一次性还款金额
			//dyhke = MathUtils.roundUp(dyhke, 2);
			loanRepayPlan.setMonthAmount(MAmount);//应还款额
		}
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		map.put("loanRepayPlanList", loanRepayPlanList);
		log.info(thisMethodName+":end");
//		if(pro.getName().indexOf("JM")>0){//特殊流程  JM 合作
		if("JM".equals(pro.getBelongChannel())){//特殊流程  JM 合作
			return "car/sign/contract/hktxJMV1.1";			
		}else{
			return "car/sign/contract/hktxV1.1";						
		}
	}
	
	/**
	 * 借款协议-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/jkxyV.do")
	public String jkxyV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		List<MatchResult> matchResultList = matchResultService.queryListByLoanAppId(appId);
		map.put("matchResultList", matchResultList);
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		CarApp bean  = carAppList.get(0);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("keyName", "carusage1");
		queryMap.put("keyProp", bean.getUseage1());
		List<DataDictionary> useage1List = this.dataDictionaryService.queryList(queryMap);
		if(null != useage1List && useage1List.size() > 0){
			queryMap.put("keyName", "carusage2");
			queryMap.put("keyProp", bean.getUseage2());
			queryMap.put("parentKeyProp", bean.getUseage1());
			List<DataDictionary> useage2List = this.dataDictionaryService.queryList(queryMap);
			bean.setUseage1(useage1List.get(0).getKeyValue());//借款用途1
			if(null != useage2List && useage2List.size() > 0){
				bean.setUseage2(useage2List.get(0).getKeyValue());//借款用途2				
			}
		}
		map.put("bean", bean);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", bean.getAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
		if(carDecisionList.size() > 0){
			map.put("decision", carDecisionList.get(0));
			map.put("product", pro);
//			double zkbjse = carSignService.getHtJkje(carDecisionList.get(0), pro);
			
			double zkbjse = contractList.get(0).getLoanAmount();
			
			double djfwfje = carSignService.getDjfwfje(zkbjse, carDecisionList.get(0).getAmount());//趸交服务费总额
			Map<String, Object> fmap1 = new HashMap<String, Object>();
			fmap1.put("contractNo", contractList.get(0).getContractNo());
			List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap1);
			double MAmount = MathUtils.add(loanRepayPlanList.get(0).getInterestReceivable(), loanRepayPlanList.get(0).getPrincipalReceivable());
//			double MAmount = carSignService.getYhkje(zkbjse, pro);
			MAmount = MathUtils.roundUp(MAmount, 2);
			map.put("MAmount", RMBUpper.toBigAmt(MAmount));//月偿还本息数额
			createAllAmount(map, MAmount, "MAmount");//缺拆开的月偿还本息数额
			int day = date;
			if(month == 2 && date == 29)
				day = 28;
			if(date == 31)
				day = 30;
			map.put("day", day);//还款日
			//还款起止日期
			Date startDate = carSignService.endDate(new Date(), 1);//还款开始日期
			c.setTime(startDate);
			int startYear = c.get(Calendar.YEAR);
			int startMonth = c.get(Calendar.MONTH);
			int startDay = c.get(Calendar.DATE);
			map.put("startYear", startYear);
			map.put("startMonth", startMonth + 1);
			map.put("startDay", startDay);
			Date endDate = carSignService.endDate(new Date(), pro.getPeriod());//还款结束日期
			c.setTime(endDate);
			int endYear = c.get(Calendar.YEAR);
			int endMonth = c.get(Calendar.MONTH) + 1;
			int endDay = c.get(Calendar.DATE);
			map.put("endYear", endYear);
			map.put("endMonth", endMonth);
			map.put("endDay", endDay);
			djfwfje = MathUtils.round(djfwfje, 2);
			map.put("djfwfjeUpperCase", RMBUpper.toBigAmt(djfwfje));//趸交服务费总额大写
			map.put("djfwfjeLowerCase", djfwfje);//趸交服务费总额小写
			zkbjse = MathUtils.round(zkbjse, 2);
			map.put("zkbjse", RMBUpper.toBigAmt(zkbjse));//借款本金数额
			createAllAmount(map, zkbjse, "zkbjse");//缺拆开的借款本金数额
		}
		
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		log.info(thisMethodName+":end");
//		if(pro.getName().indexOf("JM")>0){//特殊流程  JM 合作
		if("JM".equals(pro.getBelongChannel())){//特殊流程  JM 合作
			return "car/sign/contract/jkxyJMV1.1";			
		}else{
			return "car/sign/contract/jkxyV1.1";			
		}
	}
	
	/**
	 * 生成拆分金额的方法
	 * @param map
	 * @param amount
	 * @param amountType
	 */
	public void createAllAmount(ModelMap map, double amount, String amountType){
		amount = MathUtils.round(amount, 2);
		String zkbjseStr = amount + "";
		String[] str = zkbjseStr.split("\\.");
		if(str.length > 1){
			if(str[1].length() <= 1)
				zkbjseStr = zkbjseStr + "0";
		}
		zkbjseStr = zkbjseStr.replace(".", "");
		zkbjseStr = zkbjseStr.replace(",", "");
		char[] zkbjseChars = zkbjseStr.toCharArray();
		int j = 1;
		for (int i = zkbjseChars.length - 1; i >= 0; i--) {
			map.put(amountType + j, zkbjseChars[i]);
			j++;
		}
	}
	
	/**
	 * 融资服务协议-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rzfwV.do")
	public String rzfwV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		CarApp bean  = carAppList.get(0);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("keyName", "carusage1");
		queryMap.put("keyProp", bean.getUseage1());
		List<DataDictionary> useage1List = this.dataDictionaryService.queryList(queryMap);
		if(null != useage1List && useage1List.size() > 0){
			queryMap.put("keyName", "carusage2");
			queryMap.put("keyProp", bean.getUseage2());
			queryMap.put("parentKeyProp", bean.getUseage1());
			List<DataDictionary> useage2List = this.dataDictionaryService.queryList(queryMap);
			bean.setUseage1(useage1List.get(0).getKeyValue());//借款用途1
			if(null != useage2List && useage2List.size() > 0){
				bean.setUseage2(useage2List.get(0).getKeyValue());//借款用途2				
			}
		}
		map.put("bean", bean);
		map.put("loanAmount", RMBUpper.toBigAmt(contractList.get(0).getLoanAmount()));//合同借款金额大写
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", bean.getAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		if(carDecisionList.size() > 0)
			map.put("decision", carDecisionList.get(0));
		Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
		Map<String, Object> fmap1=new HashMap<String, Object>();
		fmap1.put("name", carDecisionList.get(0).getProduct());
//		if(pro.getName().indexOf("JM")>0)//特殊流程  JM 合作
		if("JM".equals(pro.getBelongChannel())){//特殊流程  JM 合作
			fmap1.put("periodNum", 3);			
		}else{
			fmap1.put("periodNum", 4);
		}
		List<ProductFeeRate> productFeeRateList = productFeeRateService.queryList(fmap1);
//		double zkbjse = carSignService.getHtJkje(carDecisionList.get(0), pro);
		double zkbjse = contractList.get(0).getLoanAmount();
		double djfwfje = carSignService.getDjfwfje(zkbjse, carDecisionList.get(0).getAmount());//趸交服务费总额
		map.put("djfwfjeUpperCase", RMBUpper.toBigAmt(MathUtils.round(djfwfje, 2)));//趸交服务费总额大写
		map.put("djfwfjeLowerCase", MathUtils.round(djfwfje, 2));//趸交服务费总额小写
		double jiaToYije = MathUtils.sub(djfwfje, MathUtils.mul(zkbjse, 0.04));//甲方应向乙支付服务费
		map.put("serviceSumAmountUpperCase", RMBUpper.toBigAmt(MathUtils.roundUp(jiaToYije, 2)));//趸交服务费总额大写=合同借款金额-放款金额
		map.put("serviceSumAmountLowerCase", MathUtils.roundUp(jiaToYije, 2));//趸交服务费总额小写=合同借款金额-放款金额
		double jiaToBingje = MathUtils.mul(MathUtils.mul(zkbjse, 0.04), 0.2);
		double jiaToDingje = MathUtils.mul(MathUtils.mul(zkbjse, 0.04), 0.8);
		map.put("jiaToBingjeUpperCase", RMBUpper.toBigAmt(MathUtils.round(jiaToBingje, 2)));//甲方应向丙方支付服务费大写
		map.put("jiaToBingjeLowerCase", MathUtils.round(jiaToBingje, 2));//甲方应向丙方支付服务费小写
		map.put("jiaToDingjeUpperCase", RMBUpper.toBigAmt(MathUtils.round(jiaToDingje, 2)));//甲方应向丁支付服务费大写
		map.put("jiaToDingjeLowerCase", MathUtils.round(jiaToDingje, 2));//甲方应向丁支付服务费小写
		double fhfwf = carSignService.getFhfwfje(djfwfje, productFeeRateList.get(0));
//		if(pro.getName().indexOf("JM")>0)//特殊流程  JM 合作
		if("JM".equals(pro.getBelongChannel())){//特殊流程  JM 合作
			fmap1.put("periodNum", 4);			
		}else{
			fmap1.put("periodNum", 5);	
		}
		productFeeRateList = productFeeRateService.queryList(fmap1);
//		double zkbjse1 = carSignService.getHtJkje(carDecisionList.get(0), pro);
		double zkbjse1 = contractList.get(0).getLoanAmount();
		double djfwfje1 = carSignService.getDjfwfje(zkbjse1, carDecisionList.get(0).getAmount());//趸交服务费总额
		double fhfwf1 = carSignService.getFhfwfje(djfwfje1, productFeeRateList.get(0));
		double fwfdj1 = MathUtils.sub(fhfwf, fhfwf1);
		double fwfdj = MathUtils.round(fwfdj1, 2);
		map.put("fwfdjUpperCase", RMBUpper.toBigAmt(fwfdj));//服务费递减金额大写
		map.put("fwfdjLowerCase", fwfdj);//服务费递减金额小写
		fhfwf = MathUtils.round(fhfwf, 2);
		map.put("fhfwfUpperCase", RMBUpper.toBigAmt(fhfwf));//返回服务费大写
		map.put("fhfwfLowerCase", fhfwf);//返回服务费小写
		log.info(thisMethodName+":end");
//		if(pro.getName().indexOf("JM")>0){//特殊流程  JM 合作	
		
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		if("JM".equals(pro.getBelongChannel())){//特殊流程  JM 合作
			return "car/sign/contract/rzfwJMV1.1";
		}else{
			return "car/sign/contract/rzfwV1.1";
		}
	}
	
	/**
	 * 委托划扣授权书-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wthkV.do")
	public String wthkV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", appId);
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
		CarApp bean  = carAppList.get(0);
		map.put("bean", bean);
		log.info(thisMethodName+":end");
//		if(pro.getName().indexOf("JM")>0){//特殊流程  JM 合作	
		
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		if("JM".equals(pro.getBelongChannel())){//特殊流程  JM 合作
			return "car/sign/contract/wthkJMV1.1";			
		}else{
			return "car/sign/contract/wthkV1.1";
		}
	}

	/**
	 * DX还款事项提醒函-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hktxDXV.do")
	public String hktxDXV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//查出借款人的合同表内信息
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		//查省市数据字典，代码替换成汉字
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		//查出借款人的申请表内信息
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		CarApp bean = carAppList.get(0);
		map.put("bean", bean);
		//查出借款人的决策表内信息
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", bean.getAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		//金额部分
		double zkbjse = 0.0;//合同金额
		double MAmount = 0.0;
		Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
		if(carDecisionList.size() > 0){
			map.put("decision", carDecisionList.get(0));
			map.put("product", pro);
//			zkbjse = carSignService.getHtJkje(carDecisionList.get(0), pro);
			
			zkbjse = contractList.get(0).getLoanAmount();
			map.put("zkbjse", zkbjse);//借款本金数额（后改为合同金额）
			//createAllAmount(map, zkbjse, "zkbjse");//缺拆开的借款本金数额
			Map<String, Object> fmap1 = new HashMap<String, Object>();
			fmap1.put("contractNo", contractList.get(0).getContractNo());
			List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap1);
			
//			MAmount = carSignService.getYhkje(zkbjse, pro);
			MAmount = MathUtils.add(loanRepayPlanList.get(0).getInterestReceivable(), loanRepayPlanList.get(0).getPrincipalReceivable());
			
			double znje = carSignService.getZnje(MAmount, pro);//滞纳金额
			double fxje1 = carSignService.getFxje(zkbjse, pro, 1);//一天的罚息金额
			double fxje15 = carSignService.getFxje(zkbjse, pro, 15);//十五天的罚息金额
			double yqwyj1 = 0.0;//逾期违约金
			double yqwyj15 = 0.0;
			if(znje <= 100){
				yqwyj1 = MathUtils.add(100.0, fxje1);
				yqwyj15 = MathUtils.add(100.0, fxje15);
			}else{
				yqwyj1 = MathUtils.add(znje, fxje1);
				yqwyj15 = MathUtils.add(znje, fxje15);
			}
			yqwyj1 = MathUtils.round(yqwyj1, 2);
			yqwyj15 = MathUtils.round(yqwyj15, 2);
			map.put("yqwyj1", yqwyj1);
			map.put("yqwyj15", yqwyj15);
			MAmount = MathUtils.roundUp(MAmount, 2);
			map.put("MAmountUpperCase", RMBUpper.toBigAmt(MAmount));//月偿还本息数额大写	
			map.put("MAmountLowerCase", MAmount);//月偿还本息数额小写
		}
		//日期部分，还款日
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		int day = date;
		if(month == 2 && date == 29)
			day = 28;
		if(date == 31)
			day = 30;
		map.put("day", day);//还款日
		Map<String, Object> fmap1 = new HashMap<String, Object>();
		Map<String, Object> fmap2 = new HashMap<String, Object>();
		fmap1.put("contractNo", contractList.get(0).getContractNo());
		List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap1);
		for (LoanRepayPlan loanRepayPlan : loanRepayPlanList) {
			double dyhke = MathUtils.add(loanRepayPlan.getInterestReceivable(), loanRepayPlan.getPrincipalReceivable());
			fmap2.put("name", carDecisionList.get(0).getProduct());
			fmap2.put("periodNum", loanRepayPlan.getPeriodNum());
			//一次性结清金额=剩余本金-返还服务费+当月还款额
			//剩余本金=上期剩余本金（第一期为合同借款金额）-月还本金
			//返还服务费=趸交服务费总额*服务费减免率
			//趸交服务费总额=合同借款金额-放款金额
/* 鼎轩不退还服务费 */
			List<ProductFeeRate> productFeeRateList = productFeeRateService.queryList(fmap2);	
			double ycxhkje = MathUtils.add(MathUtils.sub(loanRepayPlan.getRestPrincipal(),MathUtils.mul(MathUtils.sub(zkbjse, carDecisionList.get(0).getAmount()), MathUtils.div(productFeeRateList.get(0).getSreviceFeeReduceRate(), 100.0))), dyhke);
			ycxhkje = MathUtils.round(ycxhkje, 2);
			loanRepayPlan.setRepaySum(ycxhkje);//一次性还款金额
			//dyhke = MathUtils.roundUp(dyhke, 2);
			loanRepayPlan.setMonthAmount(MAmount);//应还款额
		}
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		map.put("loanRepayPlanList", loanRepayPlanList);
		log.info(thisMethodName+":end");
		return "car/sign/contract/hktxDXV1.1";						
	}
	
	/**
	 * DX借款协议-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/jkxyDXV.do")
	public String jkxyDXV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//合同表信息获得
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		//省市替换
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		//撮合结果
		List<MatchResult> matchResultList = matchResultService.queryListByLoanAppId(appId);
		map.put("matchResultList", matchResultList);
		//申请表信息
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		CarApp bean  = carAppList.get(0);
		//借款用途替换
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("keyName", "carusage1");
		queryMap.put("keyProp", bean.getUseage1());
		List<DataDictionary> useage1List = this.dataDictionaryService.queryList(queryMap);
		if(null != useage1List && useage1List.size() > 0){
			queryMap.put("keyName", "carusage2");
			queryMap.put("keyProp", bean.getUseage2());
			queryMap.put("parentKeyProp", bean.getUseage1());
			List<DataDictionary> useage2List = this.dataDictionaryService.queryList(queryMap);
			bean.setUseage1(useage1List.get(0).getKeyValue());//借款用途1
			if(null != useage2List && useage2List.size() > 0){
				bean.setUseage2(useage2List.get(0).getKeyValue());//借款用途2				
			}
		}
		map.put("bean", bean);
		//日期
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", bean.getAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
		if(carDecisionList.size() > 0){
			map.put("decision", carDecisionList.get(0));
			map.put("product", pro);
//			double zkbjse = carSignService.getHtJkje(carDecisionList.get(0), pro);
			
			double zkbjse = contractList.get(0).getLoanAmount();
			
			double djfwfje = carSignService.getDjfwfje(zkbjse, carDecisionList.get(0).getAmount());//趸交服务费总额
			Map<String, Object> fmap1 = new HashMap<String, Object>();
			fmap1.put("contractNo", contractList.get(0).getContractNo());
			List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap1);
			double MAmount = MathUtils.add(loanRepayPlanList.get(0).getInterestReceivable(), loanRepayPlanList.get(0).getPrincipalReceivable());
//			double MAmount = carSignService.getYhkje(zkbjse, pro);
			MAmount = MathUtils.roundUp(MAmount, 2);
			map.put("MAmount", RMBUpper.toBigAmt(MAmount));//月偿还本息数额
			createAllAmount(map, MAmount, "MAmount");//缺拆开的月偿还本息数额
			int day = date;
			if(month == 2 && date == 29)
				day = 28;
			if(date == 31)
				day = 30;
			map.put("day", day);//还款日
			//还款起止日期
			Date startDate = carSignService.endDate(new Date(), 1);//还款开始日期
			c.setTime(startDate);
			int startYear = c.get(Calendar.YEAR);
			int startMonth = c.get(Calendar.MONTH);
			int startDay = c.get(Calendar.DATE);
			map.put("startYear", startYear);
			map.put("startMonth", startMonth + 1);
			map.put("startDay", startDay);
			Date endDate = carSignService.endDate(new Date(), pro.getPeriod());//还款结束日期
			c.setTime(endDate);
			int endYear = c.get(Calendar.YEAR);
			int endMonth = c.get(Calendar.MONTH) + 1;
			int endDay = c.get(Calendar.DATE);
			map.put("endYear", endYear);
			map.put("endMonth", endMonth);
			map.put("endDay", endDay);
			djfwfje = MathUtils.round(djfwfje, 2);
			map.put("djfwfjeUpperCase", RMBUpper.toBigAmt(djfwfje));//趸交服务费总额大写
			map.put("djfwfjeLowerCase", djfwfje);//趸交服务费总额小写
			zkbjse = MathUtils.round(zkbjse, 2);
			map.put("zkbjse", RMBUpper.toBigAmt(zkbjse));//借款本金数额
			createAllAmount(map, zkbjse, "zkbjse");//缺拆开的借款本金数额
		}
		
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		log.info(thisMethodName+":end");
		return "car/sign/contract/jkxyDXV1.1";			
	}
	
	/**
	 * DX融资服务协议-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rzfwDXV.do")
	public String rzfwDXV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//合同表
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		//省市汉字替换
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		//日期
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		//app
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		CarApp bean  = carAppList.get(0);
		//算出借款人性别和出生年月
		String idNo = bean.getIdNo();
		String appBirthYear=null,appBirthMonth=null,appBirthDay=null;
		if (idNo.length() == 15){
			if(Integer.parseInt(idNo.substring(14,15)) % 2 == 1) { 
				bean.setSex("男");
			} else { 
				bean.setSex("女"); 
			}
			appBirthYear = "19"+idNo.substring(6,8);
			appBirthMonth = idNo.substring(8,10);
			appBirthDay = idNo.substring(10,12);
		}else if(idNo.length() == 18){
			if(Integer.parseInt(idNo.substring(16,17)) % 2 == 1) { 
				bean.setSex("男"); 
			} else { 
				bean.setSex("女");
			}
			appBirthYear = idNo.substring(6,10);
			appBirthMonth = idNo.substring(10,12);
			appBirthDay = idNo.substring(12,14);
		}else if(idNo.length() != 15 || idNo.length() != 18){
			bean.setSex("不详");
			appBirthYear = "不详";
			appBirthMonth = "不详";
			appBirthDay = "不详";
		}
		map.put("appBirthYear", appBirthYear);
		map.put("appBirthMonth", appBirthMonth);
		map.put("appBirthDay", appBirthDay);
		//算出配偶性别和出生年月
		String spouseIdNo = bean.getSpouseIdNo();
		String spouseSex = null;
		String spouseBirthYear=null,spouseBirthMonth=null,spouseBirthDay=null;
		if (spouseIdNo.length() == 15){
			if(Integer.parseInt(spouseIdNo.substring(14,15)) % 2 == 1) { 
				spouseSex = "男";
			} else { 
				spouseSex = "女"; 
			}
			spouseBirthYear = "19"+spouseIdNo.substring(6,8);
			spouseBirthMonth = spouseIdNo.substring(8,10);
			spouseBirthDay = spouseIdNo.substring(10,12);
		}else if(spouseIdNo.length() == 18){
			if(Integer.parseInt(spouseIdNo.substring(16,17)) % 2 == 1) { 
				spouseSex = "男"; 
			} else { 
				spouseSex = "女";
			}
			spouseBirthYear = spouseIdNo.substring(6,10);
			spouseBirthMonth = spouseIdNo.substring(10,12);
			spouseBirthDay = spouseIdNo.substring(12,14);
		}else if(spouseIdNo.length() != 15 || spouseIdNo.length() != 18){
			spouseSex = "不详";
			spouseBirthYear = "不详";
			spouseBirthMonth = "不详";
			spouseBirthDay = "不详";
		}
		map.put("spouseSex", spouseSex);
		map.put("spouseBirthYear", spouseBirthYear);
		map.put("spouseBirthMonth", spouseBirthMonth);
		map.put("spouseBirthDay", spouseBirthDay);
		//借款用途替换
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("keyName", "carusage1");
		queryMap.put("keyProp", bean.getUseage1());
		List<DataDictionary> useage1List = this.dataDictionaryService.queryList(queryMap);
		if(null != useage1List && useage1List.size() > 0){
			queryMap.put("keyName", "carusage2");
			queryMap.put("keyProp", bean.getUseage2());
			queryMap.put("parentKeyProp", bean.getUseage1());
			List<DataDictionary> useage2List = this.dataDictionaryService.queryList(queryMap);
			bean.setUseage1(useage1List.get(0).getKeyValue());//借款用途1
			if(null != useage2List && useage2List.size() > 0){
				bean.setUseage2(useage2List.get(0).getKeyValue());//借款用途2				
			}
		}
		map.put("bean", bean);
		map.put("loanAmount", RMBUpper.toBigAmt(contractList.get(0).getLoanAmount()));//合同借款金额大写
		//常用联系人（如果配偶不存在需展示）
		Map<String, Object> contactMap = new HashMap<String, Object>();
		contactMap.put("appId", appId);
		List<CarContact> contactList = carContactService.queryListOrderByRelation(contactMap);
		CarContact carContact = contactList.get(0);
		if("8".equals(carContact.getRelation()) || "99".equals(carContact.getRelation())){
			carContact.setRelation("9");
		}
		map.put("carContact", carContact);
//		for(carContact carContact:contactList){
//			String relation = carContact.getRelation();
//			while("1".equals(relation) || "3".equals(relation)){
//				//因为常用联系人中必有1父母或3子女的情况，而2配偶单独处理了。所以其他关系用不到没有处理。
//				map.put("carContact", carContact);
//				break;
//			}
//		}
		//决策信息
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", bean.getAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		if(carDecisionList.size() > 0)
			map.put("decision", carDecisionList.get(0));
		//产品
		Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
		Map<String, Object> fmap1=new HashMap<String, Object>();
		fmap1.put("name", carDecisionList.get(0).getProduct());
//		if(pro.getName().indexOf("JM")>0)//特殊流程  JM 合作，积木产品退还三个月服务费，内部产品退还4个月服务费
//			fmap1.put("periodNum", 3);			
//		else
//			fmap1.put("periodNum", 4);	
		fmap1.put("periodNum", 3);
		List<ProductFeeRate> productFeeRateList = productFeeRateService.queryList(fmap1);
//		double zkbjse = carSignService.getHtJkje(carDecisionList.get(0), pro);
		//趸交服务费
		double zkbjse = contractList.get(0).getLoanAmount();
		double djfwfje = carSignService.getDjfwfje(zkbjse, carDecisionList.get(0).getAmount());//趸交服务费总额
		map.put("djfwfjeUpperCase", RMBUpper.toBigAmt(MathUtils.round(djfwfje, 2)));//趸交服务费总额大写
		map.put("djfwfjeLowerCase", MathUtils.round(djfwfje, 2));//趸交服务费总额小写
		//根据不同产品去不同融资服务费，计算不同的清算费用
		int forPayPeriod = carDecisionList.get(0).getPeriod();//产品期限
		double financingServiceFee = 0.00 ;//融资服务费率
		if(forPayPeriod == 12){
			financingServiceFee = ContractDingXuanConstants.Financing_Service_Fee_12;
		}else if(forPayPeriod == 18){
			financingServiceFee = ContractDingXuanConstants.Financing_Service_Fee_18;
		}else if(forPayPeriod == 24){
			financingServiceFee = ContractDingXuanConstants.Financing_Service_Fee_24;
		}else if(forPayPeriod == 36){
			financingServiceFee = ContractDingXuanConstants.Financing_Service_Fee_36;
		}
		//甲方应向乙方支付服务费
		double jiaToYije = MathUtils.sub(djfwfje, MathUtils.mul(zkbjse, financingServiceFee));//甲方应向乙支付服务费
		map.put("serviceSumAmountUpperCase", RMBUpper.toBigAmt(MathUtils.roundUp(jiaToYije, 2)));//趸交服务费总额大写=合同借款金额-放款金额
		map.put("serviceSumAmountLowerCase", MathUtils.roundUp(jiaToYije, 2));//趸交服务费总额小写=合同借款金额-放款金额
		//甲方应向丙方支付服务费
		double jiaToBingje = MathUtils.mul(zkbjse, financingServiceFee);
		map.put("jiaToBingjeUpperCase", RMBUpper.toBigAmt(MathUtils.round(jiaToBingje, 2)));//甲方应向丙方支付服务费大写
		map.put("jiaToBingjeLowerCase", MathUtils.round(jiaToBingje, 2));//甲方应向丙方支付服务费小写
		
		//返还服务费
		double fhfwf = carSignService.getFhfwfje(djfwfje, productFeeRateList.get(0));
//		if(pro.getName().indexOf("JM")>0)//特殊流程  JM 合作
//			fmap1.put("periodNum", 4);			
//		else
//			fmap1.put("periodNum", 5);	
		fmap1.put("periodNum", 4);
		productFeeRateList = productFeeRateService.queryList(fmap1);
//		double zkbjse1 = carSignService.getHtJkje(carDecisionList.get(0), pro);
		double zkbjse1 = contractList.get(0).getLoanAmount();
		double djfwfje1 = carSignService.getDjfwfje(zkbjse1, carDecisionList.get(0).getAmount());//趸交服务费总额
		double fhfwf1 = carSignService.getFhfwfje(djfwfje1, productFeeRateList.get(0));
		double fwfdj1 = MathUtils.sub(fhfwf, fhfwf1);
		double fwfdj = MathUtils.round(fwfdj1, 2);
		map.put("fwfdjUpperCase", RMBUpper.toBigAmt(fwfdj));//服务费递减金额大写
		map.put("fwfdjLowerCase", fwfdj);//服务费递减金额小写
		fhfwf = MathUtils.round(fhfwf, 2);
		map.put("fhfwfUpperCase", RMBUpper.toBigAmt(fhfwf));//返回服务费大写
		map.put("fhfwfLowerCase", fhfwf);//返回服务费小写
		
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		log.info(thisMethodName+":end");
		return "car/sign/contract/rzfwDXV1.1";
	}
	
	/**
	 * DX委托划扣授权书-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wthkDXV.do")
	public String wthkDXV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//合同表
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		//省市汉字替换
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		//日期
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		//app
		//appList
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		//决策
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", appId);
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
		CarApp bean  = carAppList.get(0);
		map.put("bean", bean);
		
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		log.info(thisMethodName+":end");
		return "car/sign/contract/wthkDXV1.1";
	}
	
	/**
	 * 委托划扣授权书-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wttxDXV.do")
	public String wttxDXV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		CarApp bean  = carAppList.get(0);
		map.put("bean", bean);
		
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		log.info(thisMethodName+":end");
		return "car/sign/contract/wttxDXV1.1";
	}
	
	
	//**********************************软银画面 start********************************************
	/**
	 * 还款事项提醒函-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hktxRYV.do")
	public String hktxRYV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		CarApp bean  = carAppList.get(0);
		map.put("bean", bean);
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", bean.getAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		double zkbjse = 0.0;
		double MAmount = 0.0;
		Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
		if(carDecisionList.size() > 0){
			map.put("decision", carDecisionList.get(0));
			map.put("product", pro);
//			zkbjse = carSignService.getHtJkje(carDecisionList.get(0), pro);
			
			zkbjse = contractList.get(0).getLoanAmount();
			map.put("zkbjse", zkbjse);//借款本金数额
			//createAllAmount(map, zkbjse, "zkbjse");//缺拆开的借款本金数额
			Map<String, Object> fmap1 = new HashMap<String, Object>();
			fmap1.put("contractNo", contractList.get(0).getContractNo());
			List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap1);
			
//			MAmount = carSignService.getYhkje(zkbjse, pro);
			MAmount = MathUtils.add(loanRepayPlanList.get(0).getInterestReceivable(), loanRepayPlanList.get(0).getPrincipalReceivable());
			
			double znje = carSignService.getZnje(MAmount, pro);//滞纳金额
			double fxje1 = carSignService.getFxje(zkbjse, pro, 1);//一天的罚息金额
			double fxje15 = carSignService.getFxje(zkbjse, pro, 15);//十五天的罚息金额
			double yqwyj1 = 0.0;
			double yqwyj15 = 0.0;
			if(znje <= 100){
				yqwyj1 = MathUtils.add(100.0, fxje1);
				yqwyj15 = MathUtils.add(100.0, fxje15);
			}else{
				yqwyj1 = MathUtils.add(znje, fxje1);
				yqwyj15 = MathUtils.add(znje, fxje15);
			}
			yqwyj1 = MathUtils.round(yqwyj1, 2);
			yqwyj15 = MathUtils.round(yqwyj15, 2);
			map.put("yqwyj1", yqwyj1);
			map.put("yqwyj15", yqwyj15);
			MAmount = MathUtils.roundUp(MAmount, 2);
			map.put("MAmountUpperCase", RMBUpper.toBigAmt(MAmount));//月偿还本息数额大写	
			map.put("MAmountLowerCase", MAmount);//月偿还本息数额小写
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		int day = date;
		if(month == 2 && date == 29)
			day = 28;
		if(date == 31)
			day = 30;
		map.put("day", day);//还款日
		Map<String, Object> fmap1 = new HashMap<String, Object>();
		Map<String, Object> fmap2 = new HashMap<String, Object>();
		fmap1.put("contractNo", contractList.get(0).getContractNo());
		List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap1);
		for (LoanRepayPlan loanRepayPlan : loanRepayPlanList) {
			double dyhke = MathUtils.add(loanRepayPlan.getInterestReceivable(), loanRepayPlan.getPrincipalReceivable());
			fmap2.put("name", carDecisionList.get(0).getProduct());
			fmap2.put("periodNum", loanRepayPlan.getPeriodNum());
			//一次性结清金额=剩余本金-返还服务费+当月还款额
			//剩余本金=上期剩余本金（第一期为合同借款金额）-月还本金
			//返还服务费=趸交服务费总额*服务费减免率
			//趸交服务费总额=合同借款金额-放款金额
			List<ProductFeeRate> productFeeRateList = productFeeRateService.queryList(fmap2);	
			double ycxhkje = MathUtils.add(MathUtils.sub(loanRepayPlan.getRestPrincipal(),MathUtils.mul(MathUtils.sub(zkbjse, carDecisionList.get(0).getAmount()), MathUtils.div(productFeeRateList.get(0).getSreviceFeeReduceRate(), 100.0))), dyhke);
			ycxhkje = MathUtils.round(ycxhkje, 2);
			loanRepayPlan.setRepaySum(ycxhkje);//一次性还款金额
			//dyhke = MathUtils.roundUp(dyhke, 2);
			loanRepayPlan.setMonthAmount(MAmount);//应还款额
		}
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		map.put("loanRepayPlanList", loanRepayPlanList);
		log.info(thisMethodName+":end");
			return "car/sign/contract/hktxRYV1.1";						
	}
	
	/**
	 * 借款协议-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/jkxyRYV.do")
	public String jkxyRYV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		List<MatchResult> matchResultList = matchResultService.queryListByLoanAppId(appId);
		map.put("matchResultList", matchResultList);
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		CarApp bean  = carAppList.get(0);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("keyName", "carusage1");
		queryMap.put("keyProp", bean.getUseage1());
		List<DataDictionary> useage1List = this.dataDictionaryService.queryList(queryMap);
		if(null != useage1List && useage1List.size() > 0){
			queryMap.put("keyName", "carusage2");
			queryMap.put("keyProp", bean.getUseage2());
			queryMap.put("parentKeyProp", bean.getUseage1());
			List<DataDictionary> useage2List = this.dataDictionaryService.queryList(queryMap);
			bean.setUseage1(useage1List.get(0).getKeyValue());//借款用途1
			if(null != useage2List && useage2List.size() > 0){
				bean.setUseage2(useage2List.get(0).getKeyValue());//借款用途2				
			}
		}
		map.put("bean", bean);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", bean.getAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
		if(carDecisionList.size() > 0){
			map.put("decision", carDecisionList.get(0));
			map.put("product", pro);
//			double zkbjse = carSignService.getHtJkje(carDecisionList.get(0), pro);
			
			double zkbjse = contractList.get(0).getLoanAmount();
			
			double djfwfje = carSignService.getDjfwfje(zkbjse, carDecisionList.get(0).getAmount());//趸交服务费总额
			Map<String, Object> fmap1 = new HashMap<String, Object>();
			fmap1.put("contractNo", contractList.get(0).getContractNo());
			List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(fmap1);
			double MAmount = MathUtils.add(loanRepayPlanList.get(0).getInterestReceivable(), loanRepayPlanList.get(0).getPrincipalReceivable());
//			double MAmount = carSignService.getYhkje(zkbjse, pro);
			MAmount = MathUtils.roundUp(MAmount, 2);
			map.put("MAmount", RMBUpper.toBigAmt(MAmount));//月偿还本息数额
			createAllAmount(map, MAmount, "MAmount");//缺拆开的月偿还本息数额
			int day = date;
			if(month == 2 && date == 29)
				day = 28;
			if(date == 31)
				day = 30;
			map.put("day", day);//还款日
			//还款起止日期
			Date startDate = carSignService.endDate(new Date(), 1);//还款开始日期
			c.setTime(startDate);
			int startYear = c.get(Calendar.YEAR);
			int startMonth = c.get(Calendar.MONTH);
			int startDay = c.get(Calendar.DATE);
			map.put("startYear", startYear);
			map.put("startMonth", startMonth + 1);
			map.put("startDay", startDay);
			Date endDate = carSignService.endDate(new Date(), pro.getPeriod());//还款结束日期
			c.setTime(endDate);
			int endYear = c.get(Calendar.YEAR);
			int endMonth = c.get(Calendar.MONTH) + 1;
			int endDay = c.get(Calendar.DATE);
			map.put("endYear", endYear);
			map.put("endMonth", endMonth);
			map.put("endDay", endDay);
			djfwfje = MathUtils.round(djfwfje, 2);
			map.put("djfwfjeUpperCase", RMBUpper.toBigAmt(djfwfje));//趸交服务费总额大写
			map.put("djfwfjeLowerCase", djfwfje);//趸交服务费总额小写
			zkbjse = MathUtils.round(zkbjse, 2);
			map.put("zkbjse", RMBUpper.toBigAmt(zkbjse));//借款本金数额
			createAllAmount(map, zkbjse, "zkbjse");//缺拆开的借款本金数额
		}
		
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		log.info(thisMethodName+":end");
			return "car/sign/contract/jkxyRYV1.1";			
	}
	
	
	/**
	 * 融资服务协议-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rzfwRYV.do")
	public String rzfwRYV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		CarApp bean  = carAppList.get(0);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("keyName", "carusage1");
		queryMap.put("keyProp", bean.getUseage1());
		List<DataDictionary> useage1List = this.dataDictionaryService.queryList(queryMap);
		if(null != useage1List && useage1List.size() > 0){
			queryMap.put("keyName", "carusage2");
			queryMap.put("keyProp", bean.getUseage2());
			queryMap.put("parentKeyProp", bean.getUseage1());
			List<DataDictionary> useage2List = this.dataDictionaryService.queryList(queryMap);
			bean.setUseage1(useage1List.get(0).getKeyValue());//借款用途1
			if(null != useage2List && useage2List.size() > 0){
				bean.setUseage2(useage2List.get(0).getKeyValue());//借款用途2				
			}
		}
		map.put("bean", bean);
		map.put("loanAmount", RMBUpper.toBigAmt(contractList.get(0).getLoanAmount()));//合同借款金额大写
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", bean.getAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		if(carDecisionList.size() > 0)
			map.put("decision", carDecisionList.get(0));
		double zkbjse = contractList.get(0).getLoanAmount();//合同金额
		double djfwfje = carSignService.getDjfwfje(zkbjse, carDecisionList.get(0).getAmount());//趸交服务费总额=合同借款金额-放款金额
		double jiaToYije = MathUtils.sub(djfwfje, MathUtils.mul(zkbjse, 0.05));//甲方应向乙支付服务费
		map.put("serviceSumAmountUpperCase", RMBUpper.toBigAmt(MathUtils.roundUp(jiaToYije, 2)));//趸交服务费总额大写=合同借款金额-放款金额
		map.put("serviceSumAmountLowerCase", MathUtils.roundUp(jiaToYije, 2));//趸交服务费总额小写=合同借款金额-放款金额
		double jiaToBingje =  MathUtils.mul(zkbjse, 0.05) ;
		map.put("jiaToBingjeUpperCase", RMBUpper.toBigAmt(MathUtils.round(jiaToBingje, 2)));//甲方应向丙方支付服务费大写
		map.put("jiaToBingjeLowerCase", MathUtils.round(jiaToBingje, 2));//甲方应向丙方支付服务费小写
	
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		log.info(thisMethodName+":end");
			return "car/sign/contract/rzfwRYV1.1";
	}
	
	/**
	 * 委托划扣授权书-页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wthkRYV.do")
	public String wthkRYV(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Contract> contractList = contractService.getContractByAppId(appId, null, null);
		Contract contract = contractList.get(0);
		if(null != contract){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("keyName", "bankProv");
			queryMap.put("keyProp", contract.getBankProvince());
			List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap);	
			if(null != provinceList && provinceList.size() > 0){
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("keyName", "bankCity");
				queryMap1.put("keyProp", contract.getBankCity());
				queryMap1.put("parentKeyProp", contract.getBankProvince());
				contract.setBankProvince(provinceList.get(0).getKeyValue());
				List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap1);
				if(null != cityList && cityList.size() > 0){
					contract.setBankCity(cityList.get(0).getKeyValue());
				}
			}
		}
		map.put("contract", contract);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		map.put("year", year);
		map.put("month", month);
		map.put("date", date);
		Map<String, Object> fmap3 = new HashMap<String, Object>();
		fmap3.put("appId", appId);
		List<CarApp> carAppList = carAppService.queryList(fmap3);
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", appId);
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
		CarApp bean  = carAppList.get(0);
		map.put("bean", bean);
		
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		log.info(thisMethodName+":end");
			return "car/sign/contract/wthkRYV1.1";
	}
	//**********************************软银画面  end********************************************
}
