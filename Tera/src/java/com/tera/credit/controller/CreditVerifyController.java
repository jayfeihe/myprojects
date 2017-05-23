/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.credit.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.tera.bpm.model.BpmLog;
import com.tera.bpm.service.BpmLogService;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.constant.BusinessConstants;
import com.tera.credit.constant.Constants;
import com.tera.credit.model.CreditAntifraud;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditCallLog;
import com.tera.credit.model.CreditContact;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.model.CreditExt;
import com.tera.credit.model.CreditHousing;
import com.tera.credit.model.CreditInterview;
import com.tera.credit.model.CreditRepeatDetail;
import com.tera.credit.model.CreditReport;
import com.tera.credit.model.CreditSummary;
import com.tera.credit.model.WageFlow;
import com.tera.credit.model.form.CreditQBean;
import com.tera.credit.model.form.VerifyFBean;
import com.tera.credit.service.CreditAntifraudService;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditCallLogService;
import com.tera.credit.service.CreditContactService;
import com.tera.credit.service.CreditDecisionService;
import com.tera.credit.service.CreditExtService;
import com.tera.credit.service.CreditHousingService;
import com.tera.credit.service.CreditInterviewService;
import com.tera.credit.service.CreditRepeatDetailService;
import com.tera.credit.service.CreditReportService;
import com.tera.credit.service.CreditSignService;
import com.tera.credit.service.CreditSummaryService;
import com.tera.credit.service.WageFlowService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.renhang.model.RhPublicDetail;
import com.tera.renhang.model.RhQuery;
import com.tera.renhang.model.RhQueryDetail;
import com.tera.renhang.model.RhReport;
import com.tera.renhang.model.RhSummary;
import com.tera.renhang.model.RhTransDefault;
import com.tera.renhang.model.RhTransDetail;
import com.tera.renhang.service.RhPublicDetailService;
import com.tera.renhang.service.RhQueryDetailService;
import com.tera.renhang.service.RhQueryService;
import com.tera.renhang.service.RhReportService;
import com.tera.renhang.service.RhSummaryService;
import com.tera.renhang.service.RhTransDefaultService;
import com.tera.renhang.service.RhTransDetailService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.sys.service.DataDictionaryService;
import com.tera.util.JsonUtil;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * 信用贷款审核控制器
 * <b>功能：</b>CreditAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/credit/verify")
public class CreditVerifyController  extends BaseController{

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CreditVerifyController.class);
	
	/**
	 * CreditAppService
	 */
	@Autowired(required=false) //自动注入
	private CreditAppService creditAppService;
	@Autowired(required=false) //自动注入
	private CreditContactService creditContactService;
	@Autowired(required=false) //自动注入
	private CreditExtService creditExtService;
	@Autowired(required=false) //自动注入
	private CreditReportService creditReportService;
	@Autowired(required=false) //自动注入
	private CreditAntifraudService creditAntifraudService;
	@Autowired(required=false) //自动注入
	private CreditSummaryService creditSummaryService;
	@Autowired(required=false) //自动注入
	private CreditHousingService creditHousingService;
	@Autowired(required=false) //自动注入
	private CreditInterviewService creditInterviewService;
	@Autowired(required=false) //自动注入
	private CreditCallLogService creditCallLogService;
	@Autowired(required=false) //自动注入
	private CreditDecisionService creditDecisionService;
	@Autowired(required=false) //自动注入
	private CreditRepeatDetailService creditRepeatDetailService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	private CreditSignService creditSignService;
	@Autowired(required=false) //自动注入
	private ContractService contractService;
	@Autowired(required=false) //自动注入
	private WageFlowService wageFlowService;
	@Autowired(required=false) //自动注入
	DataDictionaryService<DataDictionary> dataDictionaryService;
	@Autowired(required=false) //自动注入
	private RhReportService rhReportService;
	@Autowired(required=false) //自动注入
	private RhSummaryService rhSummaryService;
	@Autowired(required=false) //自动注入
	private RhPublicDetailService rhPublicDetailService;
	@Autowired(required=false) //自动注入
	private RhQueryService rhQueryService;
	@Autowired(required=false) //自动注入
	private RhQueryDetailService rhQueryDetailService;
	@Autowired(required=false) //自动注入
	private RhTransDetailService rhTransDetailService;
	@Autowired(required=false) //自动注入
	private RhTransDefaultService rhTransDefaultService;
	@Autowired(required=false)
	private BpmLogService bpmLogService;

	/**
	 * 跳转到信用贷款申请表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String creditVerifyQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "credit/verify/creditVerifyQuery";
	}
	
	/**
	 * 跳转到查看信用贷款审核页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String creditVerifyRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CreditApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.creditAppService.queryByKey(id);
			map.put("bean", bean);
			Map<String, Object>  fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "1");
			List<CreditDecision> creditDecisionList = creditDecisionService.queryList(fmap);
			String imageSummarys = "";	//补充影像资料分类
			if(creditDecisionList != null && creditDecisionList.size()>0 && id!=null){
				CreditDecision creditDecision = creditDecisionList.get(0);
				imageSummarys = creditDecision.getImageSummarys();
				double yhkje = creditSignService.getYhkje(creditDecision.getAmount(), creditDecision.getProduct());
				map.put("yhkje", yhkje);
				map.put("creditVerify", creditDecision);//审核信息
			}
			List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_APPROVAL);
			if(bpmLogList.size() > 0){
				map.put("returnReasons", bpmLogList.get(0).getLogContent2());
			}
			//所有影像分类
			Map<String, Object>  imgMap=new HashMap<String, Object>();
			imgMap.put("keyName", "imgcategory");
			List<DataDictionary>  imgList= dataDictionaryService.queryList(imgMap);
				//去掉大类
				for(int i = 0;i<imgList.size();i++){
					if(imgList.get(i).getKeyProp().length()<2){
						imgList.remove(i);
						i=i-1;
					}
				}
			Map<String, Object> imgQMap =new HashMap<String, Object>();
			imgQMap.put("appId", bean.getAppId());
//			List<Img> imgSelectList =this.imgService.queryList(imgQMap);
			String key_props = "";	//选中的影像分类Key值
			String key_values = "";	//选中的影像分类Value值
			String[] imgs = null;	//补充影像资料集合
			if(null != imageSummarys && !"null".equals(imageSummarys) && !"".equals(imageSummarys.trim())){
				imgs = imageSummarys.split(",");
			}
			for (DataDictionary dataDictionary : imgList) {
				if(null == imgs || imgs.length <= 0){
//					for (Img img : imgSelectList) {
//						if(dataDictionary.getKeyProp().equals(img.getCategory() + img.getSubCategory())){
//							if(!key_props.contains(dataDictionary.getKeyProp())){
//								key_props = key_props + dataDictionary.getKeyProp() + ",";
//								key_values = key_values + dataDictionary.getKeyValue() + ",";								
//							}
//						}
//					}					
				}else{
					for (String imageSummary : imgs) {
						if(imageSummary.equals(dataDictionary.getKeyProp())){
							if(!key_props.contains(dataDictionary.getKeyProp())){
								key_props = key_props + dataDictionary.getKeyProp() + ",";
								key_values = key_values + dataDictionary.getKeyValue() + ",";								
							}
						}
					}
				}
			}
			map.put("imgList", imgList);	//全部的影像分类
			if(key_props.length() > 0){
				key_props = key_props.substring(0, key_props.length() - 1);
				key_values = key_values.substring(0, key_values.length() - 1);
			}
			map.put("key_props", key_props);//选中的影像分类Key值
			map.put("key_values", key_values);//选中的影像分类Value值
		}
		log.info(thisMethodName+":end");
		return "credit/verify/creditVerifyRead";
	}

	/**
	 * 显示信用贷款申请表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String creditVerifyList(CreditQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		
		if(queryMap.get("orgId")==null || "".equals(queryMap.get("orgId"))){
			queryMap.put("orgId", sessionOrg.getOrgId()); //session 机构
		}
		if(StringUtils.isNullOrEmpty((String) queryMap.get("processer"))){
			queryMap.remove("processer");
		}
		if(user.getIsAdmin()!=1){
			queryMap.put("processer", loginId); // 审核人
		}
		if("waitTask".equals(qBean.getStateTask())){
			queryMap.put("nonStates", new String[]{"0"}); //状态
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_VERIFY});//查询录入流程定义为审核的
		}else if("inTask".equals(qBean.getStateTask())){
			// 进行中
			if(user.getIsAdmin()!=1){
				queryMap.put("bpmLogOperator", loginId); //流程日志 实际处理人
			}
			queryMap.put("bpmLogStates", Constants.PROCESS_STATE_VERIFY);//日志状态
			queryMap.put("nonBpmStates", new String[]{Constants.PROCESS_END_APP,Constants.PROCESS_STATE_VERIFY});//查询录入流程定义为审核的
		}else if("offTask".equals(qBean.getStateTask())){
			if(user.getIsAdmin()!=1){
				queryMap.put("bpmLogOperator", loginId); //流程日志 实际处理人
			}
			queryMap.put("bpmLogStates", Constants.PROCESS_STATE_VERIFY);
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_END_APP});//查询录入流程定义为审核的
		}
		int rowsCount = this.creditAppService.queryBpmLoanAppCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CreditApp> creditAppList = this.creditAppService.queryBpmLoanAppList(queryMap);
		pm.setData(creditAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "credit/verify/creditVerifyList";
	}

	/**
	 * 跳转到更新信用贷款申请表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String creditVerifyUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CreditApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.creditAppService.queryByKey(id);
			map.put("bean", bean);
			Map<String, Object>  fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "1");
			List<CreditDecision> creditDecisionList = creditDecisionService.queryList(fmap);
			String imageSummarys = "";	//补充影像资料分类
			if(creditDecisionList != null && creditDecisionList.size()>0 && id!=null){
				CreditDecision creditDecision = creditDecisionList.get(0);
				imageSummarys = creditDecision.getImageSummarys();
				double yhkje = creditSignService.getYhkje(creditDecision.getAmount(), creditDecision.getProduct());
				map.put("yhkje", yhkje);
				map.put("creditVerify", creditDecision);//审核信息
			}
			List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_APPROVAL);
			if(bpmLogList.size() > 0){
				map.put("returnReasons", bpmLogList.get(0).getLogContent2());
			}
			//所有影像分类
			Map<String, Object>  imgMap=new HashMap<String, Object>();
			imgMap.put("keyName", "imgcategory");
			List<DataDictionary>  imgList= dataDictionaryService.queryList(imgMap);
				//去掉大类
				for(int i = 0;i<imgList.size();i++){
					if(imgList.get(i).getKeyProp().length()<2){
						imgList.remove(i);
						i=i-1;
					}
				}
			Map<String, Object> imgQMap =new HashMap<String, Object>();
			imgQMap.put("appId", bean.getAppId());
//			List<Img> imgSelectList =this.imgService.queryList(imgQMap);
			String key_props = "";	//选中的影像分类Key值
			String key_values = "";	//选中的影像分类Value值
			String[] imgs = null;	//补充影像资料集合
			if(null != imageSummarys && !"null".equals(imageSummarys) && !"".equals(imageSummarys.trim())){
				imgs = imageSummarys.split(",");
			}
			for (DataDictionary dataDictionary : imgList) {
				if(null == imgs || imgs.length <= 0){
//					for (Img img : imgSelectList) {
//						if(dataDictionary.getKeyProp().equals(img.getCategory() + img.getSubCategory())){
//							if(!key_props.contains(dataDictionary.getKeyProp())){
//								key_props = key_props + dataDictionary.getKeyProp() + ",";
//								key_values = key_values + dataDictionary.getKeyValue() + ",";								
//							}
//						}
//					}					
				}else{
					for (String imageSummary : imgs) {
						if(imageSummary.equals(dataDictionary.getKeyProp())){
							if(!key_props.contains(dataDictionary.getKeyProp())){
								key_props = key_props + dataDictionary.getKeyProp() + ",";
								key_values = key_values + dataDictionary.getKeyValue() + ",";								
							}
						}
					}
				}
			}
			map.put("imgList", imgList);	//全部的影像分类
			if(key_props.length() > 0){
				key_props = key_props.substring(0, key_props.length() - 1);
				key_values = key_values.substring(0, key_values.length() - 1);
			}
			map.put("key_props", key_props);//选中的影像分类Key值
			map.put("key_values", key_values);//选中的影像分类Value值
			
			// 获取欺诈审核信息
			CreditAntifraud antifraud = this.creditAntifraudService.queryLatestByAppId(bean.getAppId(),BusinessConstants.CREDIT_FRAUD_STATE_OK);
			map.put("creditFraud", antifraud);
		}
		log.info(thisMethodName+":end");
		return "credit/verify/creditVerifyUpdate";
	}

	/**
	 * 保存信用贷款申请表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void creditVerifySave(VerifyFBean verifyFBean,BindingResult bingding,
			HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			CreditDecision creditDecision = verifyFBean.getDecision();
			if(creditDecision!=null){
				//借款金额
				creditDecision.setAmount(creditDecision.getAmount()*10000);
				//产品
				Map<String, Object> fmap=new HashMap<String, Object>();
				fmap.put("name", creditDecision.getProduct());
				List<Product> productList = productService.queryList(fmap);
				creditDecision.setPeriod(productList.get(0).getPeriod());//借款期限
//				creditDecision.setSreviceFeeRate(productList.get(0).getSreviceFeeRate());//服务费
//				creditDecision.setInterestRate(productList.get(0).getInterestRate());//利率
			}
			//审核，面审，本人手机栏目下金额
			List<CreditInterview> changeAmountList = verifyFBean.getType01InterviewList();
			if(null != changeAmountList){
				for (CreditInterview creditInterview : changeAmountList) {
//					creditInterview.setMonthlyPayments(creditInterview.getMonthlyPayments()*10000);
					creditInterview.setAmount(creditInterview.getAmount()*10000);
				}				
			}
			
			creditDecisionService.verifyUpdate(verifyFBean, loginId, org.getOrgId());
			writer.print(JsonUtil.object2json(new JsonMsg(true, "操作成功")));
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
	 * 综合信息
	 * @param appId
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/complexInfo.do")
	public String  complexInfo(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		CreditApp bean = null;
		if (null != id && !"".equals(id)) {
			//取得个人信息
			bean  = this.creditAppService.queryByKey(id);
			map.put("bean", bean);
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			//取得反欺诈信息
			List<CreditAntifraud> antifraudList = creditAntifraudService.queryList(fmap);
			map.put("antifraudList", antifraudList);
			//type==1，取得人行信息
			fmap.put("type", "1");
			List<CreditReport> ReportList = creditReportService.queryList(fmap);
			if(ReportList.isEmpty()==false && ReportList.size()>0){
				map.put("creditReport", creditReportService.queryList(fmap).get(0));
			}
			List<Contract> contractList = contractService.queryHistoryList(bean.getAppId());
			if(contractList.size() > 0)
				map.put("contractList", contractList);
			else
				map.put("contractList", null);
		}

		log.info(thisMethodName+":end");
		return "credit/verify/creditComplexInfo";
	}
	/**
	 * 信息核查
	 * @param appId
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/infoCheck.do")
	public String  infoCheck(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		CreditApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.creditAppService.queryByKey(id);
			map.put("bean", bean);
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "2");
			List<CreditExt> type2Exts=creditExtService.queryList(fmap);//114查号台
			if(type2Exts==null||type2Exts.size()==0){
				CreditExt type1=new CreditExt();
				type1.setName(bean.getComName());//单位名称
//				type1.setValue("1");
				type1.setKey("1");
				type2Exts.add(type1);
				
				CreditExt type2=new CreditExt();
				type2.setName(bean.getComPhone());//单位电话反查
//				type2.setValue("1");
				type2.setKey("2");
				type2Exts.add(type2);
				
				
				CreditExt type3=new CreditExt();
				type3.setName(bean.getComProvince()+bean.getComCity()+bean.getComCounty()+bean.getComAddress());//单位地址反查
//				type3.setValue("1");
				type3.setKey("3");
				type2Exts.add(type3);
				
			}else{
				List<CreditExt> type2Exts1 = new ArrayList<CreditExt>();
				boolean sign1 = true;
				boolean sign2 = true;
				boolean sign3 = true;
				for (CreditExt creditExt : type2Exts) {
					if("1".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getComName())){
							sign1 = false;
							break;
						}
					}
				}
				for (CreditExt creditExt : type2Exts) {
					if("2".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getComPhone())){
							sign2 = false;
							break;
						}						
					}
				}
				for (CreditExt creditExt : type2Exts) {
					if("3".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getComProvince()+bean.getComCity()+bean.getComCounty()+bean.getComAddress())){
							sign3 = false;
							break;
						}						
					}
				}
				if(sign1){
					CreditExt type1=new CreditExt();
					type1.setName(bean.getComName());//单位名称
//					type1.setValue("1");
					type1.setKey("1");
					type2Exts1.add(type1);
				}
				if(sign2){
					CreditExt type2=new CreditExt();
					type2.setName(bean.getComPhone());//单位电话反查
//					type2.setValue("1");
					type2.setKey("2");
					type2Exts1.add(type2);
				}
				if(sign3){
					CreditExt type3=new CreditExt();
					type3.setName(bean.getComProvince()+bean.getComCity()+bean.getComCounty()+bean.getComAddress());//单位地址反查
//					type3.setValue("1");
					type3.setKey("3");
					type2Exts1.add(type3);
				}
				if(null != type2Exts1 && type2Exts1.size() > 0){
					type2Exts.addAll(type2Exts1);
				}
			}
			
			map.put("type2Exts", type2Exts);	
			fmap.put("type", "3");
			List<CreditExt> type3Exts=creditExtService.queryList(fmap);//人法网
			if(type3Exts==null||type3Exts.size()==0){
				CreditExt type1=new CreditExt();
				type1.setName(bean.getIdNo());//申请人身份证
//				type1.setValue("1");
				type1.setKey("4");
				type3Exts.add(type1);
				
				// 未婚不添加配偶身份证
				if ("02".equals(bean.getMarriage())) {
					CreditExt type2 = new CreditExt();
					type2.setName(bean.getSpouseIdNo());
					// type2.setValue("1");
					type2.setKey("5");
					type3Exts.add(type2);
				}
				
				CreditExt type3=new CreditExt();
				type3.setName(bean.getComName());//单位名称
//				type3.setValue("1");
				type3.setKey("1");
				type3Exts.add(type3);
			
			}else{
				List<CreditExt> type3Exts1 = new ArrayList<CreditExt>();
				boolean sign1 = true;
				boolean sign2 = true;
				boolean sign3 = true;
				for (CreditExt creditExt : type3Exts) {
					if("4".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getIdNo())){
							sign1 = false;
							break;
						}						
					}
				}
				for (CreditExt creditExt : type3Exts) {
					if("5".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getSpouseIdNo())){
							sign2 = false;
							break;
						}						
					}
				}
				for (CreditExt creditExt : type3Exts) {
					if("1".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getComName())){
							sign3 = false;
							break;
						}						
					}
				}
				if(sign1){
					CreditExt type1=new CreditExt();
					type1.setName(bean.getIdNo());//申请人身份证
//					type1.setValue("1");
					type1.setKey("4");
					type3Exts1.add(type1);
				}
				if(sign2){
					//未婚不添加配偶身份证
					if ("02".equals(bean.getMarriage())) {
						CreditExt type2 = new CreditExt();
						type2.setName(bean.getSpouseIdNo());
						type2.setKey("5");
						type3Exts1.add(type2);
					}
				}
				if(sign3){
					CreditExt type3=new CreditExt();
					type3.setName(bean.getComName());//单位名称
//					type3.setValue("1");
					type3.setKey("1");
					type3Exts1.add(type3);
				}
				if(null != type3Exts1 && type3Exts1.size() > 0){
					type3Exts.addAll(type3Exts1);
				}
			}
			
			/*
			 * 对于旧数据剔除未婚添加的配偶身份证信息
			 */
			if ("01".equals(bean.getMarriage())) {
				Map<String, Object> tmpMap = new HashMap<String, Object>();
				tmpMap.put("appId", bean.getAppId());
				tmpMap.put("type", "3");
				tmpMap.put("key", "5");
				List<CreditExt> tmpType3Exts = creditExtService.queryList(tmpMap);
				for (int i = 0; i < type3Exts.size(); i++) {
					if (tmpType3Exts != null && tmpType3Exts.size() > 0) {
						if (tmpType3Exts.get(0).getId() == type3Exts.get(i).getId()) {
							type3Exts.remove(i);
						}
					}
				}
			}
			
			map.put("type3Exts", type3Exts);	
			fmap.put("type", "4");
			List<CreditExt> type4Exts=creditExtService.queryList(fmap);//工商网
			if(type4Exts==null||type4Exts.size()==0){
				CreditExt type1=new CreditExt();
				type1.setName(bean.getComName());//单位名称
//				type1.setValue("1");
				type1.setKey("1");
				type4Exts.add(type1);
			}else{
				List<CreditExt> type4Exts1 = new ArrayList<CreditExt>();
				boolean sign = true;
				for (CreditExt creditExt : type4Exts) {
					if(creditExt.getName().equals(bean.getComName())){
						sign = false;
						break;
					}
				}
				if(sign){
					CreditExt type1=new CreditExt();
					type1.setName(bean.getComName());//单位名称
//					type1.setValue("1");
					type1.setKey("1");
					type4Exts1.add(type1);
				}
				if(null != type4Exts1 && type4Exts1.size() > 0){
					type4Exts.addAll(type4Exts1);
				}
			}
			map.put("type4Exts", type4Exts);
			
			fmap.put("type", "5");
			List<CreditExt> type5Exts1 = new ArrayList<CreditExt>();
			List<CreditExt> type5Exts=creditExtService.queryList(fmap);//网查
			if(type5Exts==null||type5Exts.size()==0){
				
				CreditExt type_brsfz=new CreditExt();
				type_brsfz.setName(bean.getIdNo());//个人身份证
				type_brsfz.setKey("4");
				type5Exts.add(type_brsfz);
				
				if("02".equals(bean.getMarriage())){
					CreditExt type_posfz=new CreditExt();
					type_posfz.setName(bean.getSpouseIdNo());//配偶身份证
					type_posfz.setKey("5");
					type5Exts.add(type_posfz);
				}
				
				CreditExt type1=new CreditExt();
				type1.setName(bean.getAddProvince()+bean.getAddCity()+bean.getAddCounty()+bean.getAddress());//居住地址
//				type1.setValue("1");
				type1.setKey("6");
				type5Exts.add(type1);
				
				CreditExt type2=new CreditExt();
				type2.setName(bean.getComName());//单位名称
//				type2.setValue("1");
				type2.setKey("1");
				type5Exts.add(type2);
				
				CreditExt type3=new CreditExt();
				type3.setName(bean.getComProvince()+bean.getComCity()+bean.getComCounty()+bean.getComAddress());//单位地址
//				type3.setValue("1");
				type3.setKey("3");
				type5Exts.add(type3);
				
				CreditExt type4=new CreditExt();
				type4.setName(bean.getMobile());//本人手机号
				type4.setName1(bean.getName());
//				type4.setValue("1");
				type4.setKey("7");
				type5Exts.add(type4);

				if("02".equals(bean.getMarriage())){
					CreditExt type_posj=new CreditExt();
					type_posj.setName(bean.getSpouseMobile());//配偶手机
					type_posj.setName1(bean.getSpouseName());
					type_posj.setKey("10");
					type5Exts.add(type_posj);
				}
				
				CreditExt type5=new CreditExt();
				type5.setName(bean.getPhone());//家庭固话
//				type5.setValue("1");
				type5.setKey("9");
				type5Exts.add(type5);
				
				CreditExt type6=new CreditExt();
				type6.setName(bean.getComPhone());//单位电话
//				type6.setValue("1");
				type6.setKey("2");
				type5Exts.add(type6);
				
				Map<String, Object> qmap=new HashMap<String, Object>();
				qmap.put("appId", bean.getAppId());
				qmap.put("state", "1");
				qmap.put("key", "8");
				if(!bean.getProduct().contains("业主贷")){
					qmap.put("type", "1");
				}
				List<CreditContact> contList = creditContactService.queryList(qmap);
				for (CreditContact creditContact : contList) {
					CreditExt type7=new CreditExt();
					type7.setName(creditContact.getName()+" :"+("1".equals(creditContact.getType())?creditContact.getMobile():creditContact.getPhone()));
					type7.setName1(creditContact.getName());
					type7.setPhone("1".equals(creditContact.getType())?creditContact.getMobile():creditContact.getPhone());
//					type7.setValue("1");
					type7.setKey("8");
					type5Exts.add(type7);
				}
			}else{
				List<CreditExt> type5Exts2 = new ArrayList<CreditExt>();
				boolean sign1 = true; // 居住地址
				boolean sign2 = true; // 单位名称
				boolean sign3 = true; // 单位地址
				boolean sign4 = true; // 本人手机
				boolean sign5 = true; // 家庭固话
				boolean sign6 = true; // 单位电话
				boolean sign7 = true; // 申请人身份证
				boolean sign8 = true; // 配偶身份证
				boolean sign9 = true; // 配偶手机
				for (CreditExt creditExt : type5Exts) {
					if("6".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getAddProvince()+bean.getAddCity()+bean.getAddCounty()+bean.getAddress())){
							sign1 = false;
							break;
						}
					}
				}
				for (CreditExt creditExt : type5Exts) {
					if("1".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getComName())){
							sign2 = false;
							break;
						}
					}
				}
				for (CreditExt creditExt : type5Exts) {
					if("3".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getComProvince()+bean.getComCity()+bean.getComCounty()+bean.getComAddress())){
							sign3 = false;
							break;
						}
					}
				}
				for (CreditExt creditExt : type5Exts) {
					if("7".equals(creditExt.getKey())){
						creditExt.setName1(bean.getName());
						if(creditExt.getName().equals(bean.getMobile())){
							sign4 = false;
							break;
						}
					}
				}
				for (CreditExt creditExt : type5Exts) {
					if("9".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getPhone())){
							sign5 = false;
							break;
						}
					}
				}
				for (CreditExt creditExt : type5Exts) {
					if("2".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getComPhone())){
							sign6 = false;
							break;
						}
					}
				}
				for (CreditExt creditExt : type5Exts) {
					if("8".equals(creditExt.getKey())){
						String[] s = creditExt.getName().split(":");
						creditExt.setName1(s[0]);
						creditExt.setPhone(s[1]);
						if(creditExt.getName().equals(bean.getComPhone())){
							sign6 = false;
							break;
						}
					}
				}
				for (CreditExt creditExt : type5Exts) {
					if("4".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getIdNo())){
							sign7 = false;
							break;
						}
					}
				}
				for (CreditExt creditExt : type5Exts) {
					if("5".equals(creditExt.getKey())){
						if(creditExt.getName().equals(bean.getSpouseIdNo())){
							sign8 = false;
							break;
						}
					}
				}
				for (CreditExt creditExt : type5Exts) {
					if("10".equals(creditExt.getKey())){ // 配偶手机
						creditExt.setName1(bean.getSpouseName());
						if(creditExt.getName().equals(bean.getSpouseMobile())){
							sign9 = false;
							break;
						}
					}
				}
				if(sign1){
					CreditExt type1=new CreditExt();
					type1.setName(bean.getAddProvince()+bean.getAddCity()+bean.getAddCounty()+bean.getAddress());//居住地址
//					type1.setValue("1");
					type1.setKey("6");
					type5Exts2.add(type1);
				}
				if(sign2){
					CreditExt type2=new CreditExt();
					type2.setName(bean.getComName());//单位名称
//					type2.setValue("1");
					type2.setKey("1");
					type5Exts2.add(type2);
				}
				if(sign3){
					CreditExt type3=new CreditExt();
					type3.setName(bean.getComProvince()+bean.getComCity()+bean.getComCounty()+bean.getComAddress());//单位地址
//					type3.setValue("1");
					type3.setKey("3");
					type5Exts2.add(type3);
				}
				if(sign4){
					CreditExt type4=new CreditExt();
					type4.setName(bean.getMobile());//本人手机号
					type4.setName1(bean.getName());
//					type4.setValue("1");
					type4.setKey("7");
					type5Exts2.add(type4);
				}
				if(sign5){
					CreditExt type5=new CreditExt();
					type5.setName(bean.getPhone());//家庭固话
//					type5.setValue("1");
					type5.setKey("9");
					type5Exts2.add(type5);
				}
				if(sign6){
					CreditExt type6=new CreditExt();
					type6.setName(bean.getComPhone());//单位电话
//					type6.setValue("1");
					type6.setKey("2");
					type5Exts2.add(type6);
				}
				if(sign7){
					CreditExt type7=new CreditExt();
					type7.setName(bean.getIdNo());//本人身份证
					type7.setKey("4");
					type5Exts2.add(type7);
				}
				if(sign8){
					if ("02".equals(bean.getMarriage())) {
						CreditExt type8 = new CreditExt();
						type8.setName(bean.getSpouseIdNo());//配偶身份证
						type8.setKey("5");
						type5Exts2.add(type8);
					}
				}
				if (sign9) {
					if ("02".equals(bean.getMarriage())) {
						CreditExt type9 = new CreditExt();
						type9.setName(bean.getSpouseMobile());//配偶手机
						type9.setName1(bean.getSpouseName());
						type9.setKey("10");
						type5Exts2.add(type9);
					}
				}
				//如果回退申请新加了联系人信息,则网查需要添加该联系人信息
				Map<String, Object> qmap=new HashMap<String, Object>();
				qmap.put("appId", bean.getAppId());
				qmap.put("state", "1");
				qmap.put("key", "8");
				if(!bean.getProduct().contains("业主贷")){
					qmap.put("type", "1");
				}
				List<CreditContact> contList = creditContactService.queryList(qmap);
				for (CreditContact creditContact : contList) {
					boolean sign = true;
					for (CreditExt creditExt1 : type5Exts) {
						if("1".equals(creditContact.getType())){
							if(creditExt1.getName().equals(creditContact.getName()+" :" + creditContact.getMobile())){
								sign = false;
								break;
							}
						}else{
							if(creditExt1.getName().equals(creditContact.getName()+" :" + creditContact.getPhone())){
								sign = false;
								break;
							}
						}
					}
					//添加新的联系人
					if(sign){
						CreditExt type7=new CreditExt();
						type7.setName(creditContact.getName()+" :"+("1".equals(creditContact.getType())?creditContact.getMobile():creditContact.getPhone()));
						type7.setName1(creditContact.getName());
						type7.setPhone("1".equals(creditContact.getType())?creditContact.getMobile():creditContact.getPhone());
//						type7.setValue("1");
						type7.setKey("8");
						type5Exts1.add(type7);							
					}
				}
				if(null != type5Exts2 && type5Exts2.size() > 0){
					type5Exts.addAll(type5Exts2);
				}
				if(null != type5Exts1 && type5Exts1.size() > 0){
					type5Exts.addAll(type5Exts1);
				}
			}
			/*
			 * 对于旧数据剔除未婚添加的配偶身份证信息
			 */
			if ("01".equals(bean.getMarriage())) {
				Map<String, Object> tmpMap = new HashMap<String, Object>();
				tmpMap.put("appId", bean.getAppId());
				tmpMap.put("type", "5");
				tmpMap.put("key", "5");
				List<CreditExt> tmpType5Exts = creditExtService.queryList(tmpMap);
				for (int i = 0; i < type5Exts.size(); i++) {
					if (tmpType5Exts != null && tmpType5Exts.size() > 0) {
						if (tmpType5Exts.get(0).getId() == type5Exts.get(i).getId()) {
							type5Exts.remove(i);
						}
					}
				}
			}
			map.put("type5Exts", type5Exts);
		}
		
		log.info(thisMethodName+":end");
		return "credit/verify/creditInfoCheck";
	}
	
	/**
	 * 显示信用贷款申请查重信息详细表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/repeatDetailList.do")
	public String creditRepeatDetailList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(CreditRepeatDetail.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.creditRepeatDetailService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("id", null);
		//查出已经group by的信息
		List<CreditRepeatDetail> creditRepeatDetailListGroupBy = this.creditRepeatDetailService.queryListGroupBy(queryMap);
		//查询重复次数
		for (int i=0;i<creditRepeatDetailListGroupBy.size();i++) {
			CreditRepeatDetail repeatDetail = creditRepeatDetailListGroupBy.get(i);
			queryMap.put("value", repeatDetail.getValue());
			List<CreditRepeatDetail> repeatDetailList = creditRepeatDetailService.queryList(queryMap);
			repeatDetail.setNumber(repeatDetailList.size());
		}
		pm.setData(creditRepeatDetailListGroupBy);
		/*
		List<CreditRepeatDetail> creditRepeatDetailList = this.creditRepeatDetailService.queryList(queryMap);
		//信息查重，重复次数
		CreditRepeatDetail creditRepeatDetail = null;
		for (int i=0;i<creditRepeatDetailList.size();i++) {
			CreditRepeatDetail repeatI = creditRepeatDetailList.get(i);
			repeatI.setNumber(1);
			for(int j=i+1;j<creditRepeatDetailList.size();j++){
				CreditRepeatDetail repeatJ = creditRepeatDetailList.get(j);
				if(repeatI.getValue().equals(repeatJ.getValue())){
					repeatI.setNumber(repeatI.getNumber()+1);
					creditRepeatDetailList.remove(j);
					j=j-1;
				}
			}
		}
		pm.setData(creditRepeatDetailList);
		*/
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "credit/verify/creditRepeatDetailList";
	}
	
	/**
	 * 新标签页中显示查重详细信息
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/repeatDetailPopup.do")
	public String creditRepeatDetailPopup(String value,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(CreditRepeatDetail.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.creditRepeatDetailService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("id", null);
		queryMap.put("value", value);
		List<CreditRepeatDetail> creditRepeatDetailList = this.creditRepeatDetailService.queryList(queryMap);
		
		//循环-->得到detail表的repeatAppId，去app表查id-->传id。
		Map<String, Object> fmap=new HashMap<String, Object>();
		for(int i=0;i<creditRepeatDetailList.size();i++){
			fmap.put("appId", creditRepeatDetailList.get(i).getRepeatAppId());
			List<CreditApp> creditAppList = creditAppService.queryList(fmap);
			creditRepeatDetailList.get(i).setId(creditAppList.get(0).getId());
			
			// 获取申请单最新状态，用于页面跳转判断
			CreditRepeatDetail creditRepeatDetail = creditRepeatDetailList.get(i);
			String currentBpmState = creditRepeatDetailService.
					getCurrentBpmState(creditRepeatDetail.getRepeatAppId());
			creditRepeatDetail.setCurrentBpmState(currentBpmState);
		}

		// 根据新id（查重申请id）
		Collections.sort(creditRepeatDetailList, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				CreditRepeatDetail c1 = (CreditRepeatDetail) o1;
				CreditRepeatDetail c2 = (CreditRepeatDetail) o2;
				if (c1.getId() < c2.getId())
					return 1;
				if (c1.getId() > c2.getId())
					return -1;
				return 0;
			}
		});
		
		pm.setData(creditRepeatDetailList);
		map.put("pm", pm);
		
		log.info(thisMethodName+":end");
		return "credit/verify/creditRepeatDetailPopup";
	}
	
	/**
	 * 影像摘要
	 * @param appId
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/imageSummary.do")
	public String  imageSummary(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		if (null != id && !"".equals(id)) {
			CreditApp bean = this.creditAppService.queryByKey(id);
			Map<String, Object> fmap = new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			map.put("bean", bean);
			//根据appId取得t_credit_summary的信息
			List<CreditSummary> SummaryList = creditSummaryService.queryList(fmap);
				CreditSummary summary = null;
				if (SummaryList == null || SummaryList.isEmpty()) {
					summary = new CreditSummary();
					//材料完整有效性
					//这里去掉	影像摘要tab页中的	默认选中“无效”
					summary.setValidteIdnoFlag(null);//有效性，身份证
					summary.setValidteIdnoRemark(summary.getValidteIdnoRemark());
					summary.setValidteHouseFlag(null);//有效性，房产证
					summary.setValidteHouseRemark(summary.getValidteHouseRemark());
					summary.setValidteDivorceFlag(null);//有效性，离婚证
					summary.setValidteDivorceRemark(summary.getValidteDivorceRemark());
					summary.setValidteWageFlag(null);//有效性，工资卡
					summary.setValidteWageRemark(summary.getValidteWageRemark());
					summary.setValidteDebitFlag(null);//有效性，借记卡
					summary.setValidteDebitRemark(summary.getValidteDebitRemark());
					
//					summary.setValidteIdnoFlag(summary.getValidteIdnoFlag());//有效性，身份证
//					summary.setValidteIdnoRemark(summary.getValidteIdnoRemark());
//					summary.setValidteHouseFlag(summary.getValidteHouseFlag());//有效性，房产证
//					summary.setValidteHouseRemark(summary.getValidteHouseRemark());
//					summary.setValidteDivorceFlag(summary.getValidteDivorceFlag());//有效性，离婚证
//					summary.setValidteDivorceRemark(summary.getValidteDivorceRemark());
//					summary.setValidteWageFlag(summary.getValidteWageFlag());//有效性，工资卡
//					summary.setValidteWageRemark(summary.getValidteWageRemark());
//					summary.setValidteDebitFlag(summary.getValidteDebitFlag());//有效性，借记卡
//					summary.setValidteDebitRemark(summary.getValidteDebitRemark());
					//人行信息顶头部分
					summary.setCreditCard(summary.getCreditCard());//是否有信用卡
					summary.setIsLoan(summary.getIsLoan());//是否有贷款
					summary.setQueryCount(summary.getQueryCount());//近12个月贷后管理查询次数
					//社保信息
					summary.setSocialComName(summary.getSocialComName());//社保单位名称
					summary.setSocialCode(summary.getSocialCode());//社保卡号
					summary.setSocialAmount(summary.getSocialAmount());//社保月基纳金额
					summary.setSocialDate(summary.getSocialDate());//社保最近基纳时间
					summary.setSocialMonths(summary.getSocialMonths());//社保已基纳时间
					//公积金信息
					summary.setPublicComName(summary.getPublicComName());//公积金单位名称
					summary.setPublicAmount(summary.getPublicAmount());//公积金月基纳金额
					summary.setPublicDate(summary.getPublicDate());//公积金最近基纳时间
					summary.setPublicMonths(summary.getPublicMonths());//公积金已基纳时间
					//备注
					summary.setRemarks(summary.getRemarks());
					summary.setState("1");
					//得到页面上信息放进SummaryList
					SummaryList.add(summary);
				} else {
					summary = SummaryList.get(0);
				}
				map.put("summary", summary);
			//工资流水
			Map<String, Object> wageFlowFmap = new HashMap<String, Object>();	
			wageFlowFmap.put("appId", bean.getAppId());
			wageFlowFmap.put("state", "1");
			List<WageFlow> wageFlowList = wageFlowService.queryList(wageFlowFmap);
			map.put("wageFlows", wageFlowList);
			
			//根据appId取得房产的信息
			List<CreditHousing> houseInfos = creditHousingService.queryList(fmap);
//					CreditHousing housing = null;
				if (houseInfos == null || houseInfos.isEmpty()) {
					//添加房产信息
					for(int num=0;num<houseInfos.size();num++){
//							houseInfos.get(num).setNum(num + 1);
						houseInfos.get(num).setAddress(houseInfos.get(num).getAddress());
						houseInfos.get(num).setArea(houseInfos.get(num).getArea());
						houseInfos.get(num).setIsMortgage(houseInfos.get(num).getIsMortgage());
						houseInfos.get(num).setCreateTimeStr(houseInfos.get(num).getCreateTimeStr());
						houseInfos.get(num).setState("1");
						houseInfos.add(houseInfos.get(num));
					}
				}
//					else {
					//房号
//						for(int num=0;num<houseInfos.size();num++){
//							houseInfos.get(num).setNum(num + 1);
//							housing = new CreditHousing();
//							housing = houseInfos.get(num);
//						}
//					}
				map.put("houseInfos", houseInfos);

			//根据appId取得t_credit_report的信息
			fmap.put("type", "1");//信用卡
			List<CreditReport> CreditReportList = creditReportService.queryList(fmap);
				CreditReport creditReport = null;
				if (CreditReportList == null || CreditReportList.isEmpty()) {
					creditReport = new CreditReport();
					//添加信用卡信息
					creditReport.setCreditCardException(creditReport.getCreditCardException());
					creditReport.setQuerySixCount(creditReport.getQuerySixCount());
					creditReport.setMaxDefault(creditReport.getMaxDefault());
					creditReport.setAmount(creditReport.getAmount());
					creditReport.setDefaultAmount(creditReport.getDefaultAmount());
					creditReport.setDefaultCount(creditReport.getDefaultCount());
					creditReport.setDefaultMaxCount(creditReport.getDefaultMaxCount());
					creditReport.setDefaultNinetyCount(creditReport.getDefaultNinetyCount());
					creditReport.setState("1");
					CreditReportList.add(creditReport);
				} else {
					creditReport = CreditReportList.get(0);
				}
				map.put("creditReport", creditReport);
			fmap.put("type", "2");//贷款
			List<CreditReport> LoanReportList = creditReportService.queryList(fmap);
				CreditReport loanReport = null;
				if (LoanReportList == null || LoanReportList.isEmpty()) {
					loanReport = new CreditReport();
					//添加贷款信息
					loanReport.setMaxDefault(loanReport.getMaxDefault());
					loanReport.setAmount(loanReport.getAmount());
					loanReport.setDefaultAmount(loanReport.getDefaultAmount());
					loanReport.setDefaultCount(loanReport.getDefaultCount());
					loanReport.setDefaultMaxCount(loanReport.getDefaultMaxCount());
					loanReport.setDefaultNinetyCount(loanReport.getDefaultNinetyCount());
					loanReport.setExpireLoan(loanReport.getExpireLoan());
					loanReport.setExpireHousing(loanReport.getExpireHousing());
					loanReport.setState("1");
					LoanReportList.add(loanReport);
				} else {
					loanReport = LoanReportList.get(0);
				}
				map.put("loanReport", loanReport);
			
		}
		log.info(thisMethodName+":end");
		return "credit/verify/creditImageSummary";
	}	
	
	/**
	 * 信用报告
	 * @param appId
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/creditReport.do")
	public String  creditReport(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		if (null != id && !"".equals(id)) {
			CreditApp bean = this.creditAppService.queryByKey(id);
			Map<String, Object> fmap = new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			map.put("bean", bean);
			RhReport rhReport = null;								//一、个人基本信息
			RhSummary rhSummary = null;								//二、信息概要
			List<RhTransDetail> rhTransDetail01s = null;			//贷款
			List<RhTransDetail> rhTransDetail02s = null;			//贷记卡
			List<RhTransDetail> rhTransDetail03s = null;			//准贷记卡
			List<RhTransDetail> rhTransDetail04s = null;			//担保信息
			RhPublicDetail rhPublicDetail = null;					//七、公共信息明细
			RhQuery rhQuery = null;									//八、查询记录汇总
			List<RhQueryDetail> rhQueryDetails;						//八、查询记录明细
			List<RhReport> rhReportList = rhReportService.queryList(fmap);
			if (null == rhReportList || rhReportList.isEmpty()) {
				rhReport = new RhReport();
				rhReport.setState("1");
			}else{
				rhReport = rhReportList.get(0);
			}
			map.put("rhReport", rhReport);							//一、个人基本信息
			List<RhSummary> rhSummaryList = rhSummaryService.queryList(fmap);
			if (null == rhSummaryList || rhSummaryList.isEmpty()) {
				rhSummary = new RhSummary();
				rhSummary.setState("1");
			}else{
				rhSummary = rhSummaryList.get(0);
			}
			map.put("rhSummary", rhSummary);						//二、信息概要
			List<RhPublicDetail> rhPublicDetailList = rhPublicDetailService.queryList(fmap);
			if (null == rhPublicDetailList || rhPublicDetailList.isEmpty()) {
				rhPublicDetail = new RhPublicDetail();
				rhPublicDetail.setState("1");
			}else{
				rhPublicDetail = rhPublicDetailList.get(0);
			}
			map.put("rhPublicDetail", rhPublicDetail);				//七、公共信息明细
			List<RhQuery> rhQueryList = rhQueryService.queryList(fmap);
			if (null == rhQueryList || rhQueryList.isEmpty()) {
				rhQuery = new RhQuery();
				rhQuery.setState("1");
			}else{
				rhQuery = rhQueryList.get(0);
			}
			map.put("rhQuery", rhQuery);							//八、查询记录汇总
			rhQueryDetails = rhQueryDetailService.queryList(fmap);
			map.put("rhQueryDetails", rhQueryDetails);				//八、查询记录明细
			fmap.put("type", "01");
			rhTransDetail01s = rhTransDetailService.queryList(fmap);//贷款
			Map<String, Object> rhTransDefaultMap = new HashMap<String, Object>();
			rhTransDefaultMap.put("appId", bean.getAppId());
			rhTransDefaultMap.put("state", "1");
			rhTransDefaultMap.put("type", "01");
			for (RhTransDetail rhTransDetail : rhTransDetail01s) {
				rhTransDefaultMap.put("transId", rhTransDetail.getId());
				List<RhTransDefault> rhTransDefaultList = rhTransDefaultService.queryList(rhTransDefaultMap);
				rhTransDetail.setRhTransDefaultList(rhTransDefaultList);
			}
			map.put("rhTransDetail01s", rhTransDetail01s);
			fmap.put("type", "02");
			rhTransDetail02s = rhTransDetailService.queryList(fmap);//贷记卡
			rhTransDefaultMap.put("type", "02");
			for (RhTransDetail rhTransDetail : rhTransDetail02s) {
				rhTransDefaultMap.put("transId", rhTransDetail.getId());
				List<RhTransDefault> rhTransDefaultList = rhTransDefaultService.queryList(rhTransDefaultMap);
				rhTransDetail.setRhTransDefaultList(rhTransDefaultList);
			}
			map.put("rhTransDetail02s", rhTransDetail02s);
			fmap.put("type", "03");
			rhTransDetail03s = rhTransDetailService.queryList(fmap);//准贷记卡
			rhTransDefaultMap.put("type", "03");
			for (RhTransDetail rhTransDetail : rhTransDetail03s) {
				rhTransDefaultMap.put("transId", rhTransDetail.getId());
				List<RhTransDefault> rhTransDefaultList = rhTransDefaultService.queryList(rhTransDefaultMap);
				rhTransDetail.setRhTransDefaultList(rhTransDefaultList);
			}
			map.put("rhTransDetail03s", rhTransDetail03s);
			fmap.put("type", "04");
			rhTransDetail04s = rhTransDetailService.queryList(fmap);//担保信息
			map.put("rhTransDetail04s", rhTransDetail04s);
		}
		log.info(thisMethodName+":end");
		return "credit/verify/creditReport";
	}	
	
	/**
	 * 面审调查摘要
	 * @param appId
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/interviewSurvey.do")
	public String  interviewSurvey(String id, HttpServletRequest request, HttpServletResponse response, 
			ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
//		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
//		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//借款申请
		CreditApp bean = this.creditAppService.queryByKey(id);
		map.put("bean", bean);
		Map<String, Object> interviewMap=new HashMap<String, Object>();
		interviewMap.put("appId", bean.getAppId());
		interviewMap.put("state", "1");
		
		//电话拨打记录
		List<CreditCallLog> callLogList = creditCallLogService.queryList(interviewMap);
		List<CreditInterview> interviewList1 = new ArrayList<CreditInterview>();
		List<CreditInterview> interviewList=null;
		//本人手机
		interviewMap.put("type","01");
		interviewList= creditInterviewService.queryList(interviewMap);
		if(interviewList==null||interviewList.isEmpty()){
			interviewList=new ArrayList<CreditInterview>();
			CreditInterview iv=new CreditInterview();
			iv.setName(bean.getName());
			iv.setPhone(bean.getMobile());
			iv.setType("01");
			iv.setPeriod(bean.getPeriod());
			iv.setMonthlyPayments(bean.getMonthlyPayments());
			iv.setAmount(bean.getAmount());
			iv.setUseage1(bean.getUseage1());
			iv.setUseage2(bean.getUseage2());
			iv.setTel(bean.getPhone());
			iv.setTelFlag(1);
			iv.setAddress(bean.getAddProvince()+bean.getAddCity()+bean.getAddCounty()+bean.getAddress());
			iv.setAddressFlag(1);
			iv.setComAddress(bean.getComProvince()+bean.getComCity()+bean.getComCounty()+bean.getComAddress());
			iv.setComAddressFlag(1);
			iv.setIndustry1("");
			iv.setIndustry2("");
			iv.setIndustry3("");
			iv.setIsAdd(0);
			iv.setState("1");
			iv.setSurveyState("01");
			iv.setSurveyFlag("01");
			interviewList.add(iv);
		}else{
			List<CreditInterview> interviewList_1 = new ArrayList<CreditInterview>();
			for (CreditInterview interview : interviewList) {
				for (CreditCallLog callLog : callLogList) {
					if(interview.getId()==callLog.getInterviewingId()){
						interview.getCallLogList().add(callLog);
					}
				}
			}
			boolean sign = true;
			for (CreditInterview interview : interviewList) {
				if(interview.getName().equals(bean.getName()) && interview.getPhone().equals(bean.getMobile())){
					sign = false;
					break;
				}
			}
			if(sign){
				CreditInterview iv=new CreditInterview();
				iv.setName(bean.getName());
				iv.setPhone(bean.getMobile());
				iv.setType("01");
				iv.setPeriod(bean.getPeriod());
				iv.setMonthlyPayments(bean.getMonthlyPayments());
				iv.setAmount(bean.getAmount());
				iv.setUseage1(bean.getUseage1());
				iv.setUseage2(bean.getUseage2());
				iv.setTel(bean.getPhone());
				iv.setTelFlag(1);
				iv.setAddress(bean.getAddProvince()+bean.getAddCity()+bean.getAddCounty()+bean.getAddress());
				iv.setAddressFlag(1);
				iv.setComAddress(bean.getComProvince()+bean.getComCity()+bean.getComCounty()+bean.getComAddress());
				iv.setComAddressFlag(1);
				iv.setIndustry1("");
				iv.setIndustry2("");
				iv.setIndustry3("");
				iv.setIsAdd(0);
				iv.setState("1");
				iv.setSurveyState("01");
				iv.setSurveyFlag("01");
				interviewList_1.add(iv);
			}
			interviewList.addAll(interviewList_1);
		}
		map.put("type01InterviewList", interviewList);		//把本人手机号，初始化
		interviewList=null;

		//家庭固话
		interviewMap.put("type","02");
		interviewList= creditInterviewService.queryList(interviewMap);
		if(interviewList==null||interviewList.isEmpty()){
			interviewList=new ArrayList<CreditInterview>();
			CreditInterview iv=new CreditInterview();
			iv.setName(bean.getName());
			iv.setPhone(bean.getPhone());
			iv.setAddress(bean.getAddProvince()+bean.getAddCity()+bean.getAddCounty()+bean.getAddress());
			iv.setAddressFlag(1);
			iv.setType("02");
			iv.setIsAdd(0);
			iv.setState("1");
			iv.setSurveyState("01");
			iv.setSurveyFlag("01");
			interviewList.add(iv);
		}else{
			List<CreditInterview> interviewList_1 = new ArrayList<CreditInterview>();
			for (CreditInterview interview : interviewList) {
				for (CreditCallLog callLog : callLogList) {
					if(interview.getId()==callLog.getInterviewingId()){
						interview.getCallLogList().add(callLog);
					}
				}
			}
			boolean sign = true;
			for (CreditInterview interview : interviewList) {
				if(interview.getName().equals(bean.getName()) && interview.getPhone().equals(bean.getPhone())){
					sign = false;
					break;
				}
			}
			if(sign){
				CreditInterview iv=new CreditInterview();
				iv.setName(bean.getName());
				iv.setPhone(bean.getPhone());
				iv.setAddress(bean.getAddProvince()+bean.getAddCity()+bean.getAddCounty()+bean.getAddress());
				iv.setAddressFlag(1);
				iv.setType("02");
				iv.setIsAdd(0);
				iv.setState("1");
				iv.setSurveyState("01");
				iv.setSurveyFlag("01");
				interviewList_1.add(iv);
			}
			interviewList.addAll(interviewList_1);
		}
		map.put("type02InterviewList", interviewList);		//把本人手机号，初始化
		interviewList=null;
		
		//单位电话
		interviewMap.put("type","03");
		interviewList= creditInterviewService.queryList(interviewMap);
		if(interviewList==null||interviewList.isEmpty()){
			interviewList=new ArrayList<CreditInterview>();
			CreditInterview iv=new CreditInterview();
			iv.setName(bean.getComName());
			iv.setPhone(bean.getComPhone());
			iv.setType("03");
			iv.setIsAdd(0);
			iv.setState("1");
			iv.setSurveyState("01");
			iv.setSurveyFlag("01");
			iv.setComName(bean.getComName());
			iv.setComNameFlag(1);
			iv.setComAddress(bean.getComProvince()+bean.getComCity()+bean.getComCounty()+bean.getComAddress());
			iv.setComAddressFlag(1);
			iv.setIsJob(1);
			iv.setPeriod(bean.getPeriod());
			iv.setMonthlyPayments(bean.getMonthlyPayments());
			iv.setAmount(bean.getAmount());
			iv.setUseage1(bean.getUseage1());
			iv.setUseage2(bean.getUseage2());
			interviewList.add(iv);
		}else{
			List<CreditInterview> interviewList_1 = new ArrayList<CreditInterview>();
			for (CreditInterview interview : interviewList) {
				for (CreditCallLog callLog : callLogList) {
					if(interview.getId()==callLog.getInterviewingId()){
						interview.getCallLogList().add(callLog);
					}
				}
			}
			boolean sign = true;
			for (CreditInterview interview : interviewList) {
				if(interview.getName().equals(bean.getComName()) && interview.getPhone().equals(bean.getComPhone())){
					sign = false;
					break;
				}
			}
			if(sign){
				CreditInterview iv=new CreditInterview();
				iv.setName(bean.getComName());
				iv.setPhone(bean.getComPhone());
				iv.setType("03");
				iv.setIsAdd(0);
				iv.setState("1");
				iv.setSurveyState("01");
				iv.setSurveyFlag("01");
				iv.setComName(bean.getComName());
				iv.setComNameFlag(1);
				iv.setComAddress(bean.getComProvince()+bean.getComCity()+bean.getComCounty()+bean.getComAddress());
				iv.setComAddressFlag(1);
				iv.setIsJob(1);
				iv.setPeriod(bean.getPeriod());
				iv.setMonthlyPayments(bean.getMonthlyPayments());
				iv.setAmount(bean.getAmount());
				iv.setUseage1(bean.getUseage1());
				iv.setUseage2(bean.getUseage2());
				interviewList_1.add(iv);
			}
			interviewList.addAll(interviewList_1);
		}
		map.put("type03InterviewList", interviewList);		//把单位电话，放到页面
		interviewList=null;
		
		//常用联系
		interviewMap.put("type","04");
		interviewList= creditInterviewService.queryList(interviewMap);
		if(interviewList==null||interviewList.isEmpty()){
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			if(!bean.getProduct().contains("业主贷")){
				fmap.put("type", "1");
			}
			List<CreditContact>  contList=creditContactService.queryList(fmap);
			interviewList=new ArrayList<CreditInterview>();
			for (CreditContact creditContact : contList) {
				CreditInterview iv=new CreditInterview();
				iv.setName(creditContact.getName());
				iv.setPhone("1".equals(creditContact.getType())?creditContact.getMobile():creditContact.getPhone());
				iv.setRelation(creditContact.getRelation());
				iv.setType("04");
				iv.setAddress(bean.getAddProvince()+bean.getAddCity()+bean.getAddCounty()+bean.getAddress());
				iv.setAddressFlag(1);
				iv.setIsAdd(0);
				iv.setState("1");
				iv.setSurveyState("01");
				iv.setSurveyFlag("01");
				interviewList.add(iv);
			}
		}else{
			for (CreditInterview interview : interviewList) {
				for (CreditCallLog callLog : callLogList) {
					if(interview.getId()==callLog.getInterviewingId()){
						interview.getCallLogList().add(callLog);
					}
				}
			}
			//如果回退申请新加了联系人信息,则面审调查需要添加该联系人信息
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			if(!bean.getProduct().contains("业主贷")){
				fmap.put("type", "1");
			}
			List<CreditContact>  contList=creditContactService.queryList(fmap);
			for (CreditContact creditContact : contList) {
				boolean sign = true;
				for (CreditInterview interview : interviewList) {
					if("1".equals(creditContact.getType())){
						if(interview.getName().equals(creditContact.getName()) && creditContact.getMobile().equals(interview.getPhone())){
							sign = false;
							break;
						}
					}else{
						if(interview.getName().equals(creditContact.getName()) && creditContact.getPhone().equals(interview.getPhone())){
							sign = false;
							break;
						}
					}
				}
				//添加新的联系人
				if(sign){
					CreditInterview iv=new CreditInterview();
					iv.setName(creditContact.getName());
					iv.setPhone("1".equals(creditContact.getType())?creditContact.getMobile():creditContact.getPhone());
					iv.setRelation(creditContact.getRelation());
					iv.setType("04");
					iv.setAddress(bean.getAddProvince()+bean.getAddCity()+bean.getAddCounty()+bean.getAddress());
					iv.setAddressFlag(1);
					iv.setIsAdd(0);
					iv.setState("1");
					iv.setSurveyState("01");
					iv.setSurveyFlag("01");
					interviewList1.add(iv);					
				}
			}
		}
		interviewList1.addAll(interviewList);
		map.put("type04InterviewList", interviewList1);		//把常用联系 穿个 JSP
		interviewList=null; 
		//配偶手机
		interviewMap.put("type","05");
		interviewList = creditInterviewService.queryList(interviewMap);
		if(interviewList==null||interviewList.isEmpty()){
			interviewList=new ArrayList<CreditInterview>();
			if("02".equals(bean.getMarriage())){
				CreditInterview iv=new CreditInterview();
				iv.setName(bean.getSpouseName());
				iv.setPhone(bean.getSpouseMobile());
				iv.setRelation("04");
				iv.setType("05");
				iv.setAddress(bean.getSpouseComProvince() + bean.getSpouseComCity() + bean.getSpouseComCounty());
				iv.setAddressFlag(1);
				iv.setIsAdd(0);
				iv.setState("1");
				iv.setSurveyState("01");
				iv.setSurveyFlag("01");
				interviewList.add(iv);				
			}
		}else{
			List<CreditInterview> interviewList_1 = new ArrayList<CreditInterview>();
			for (CreditInterview interview : interviewList) {
				for (CreditCallLog callLog : callLogList) {
					if(interview.getId()==callLog.getInterviewingId()){
						interview.getCallLogList().add(callLog);
					}
				}
			}
			boolean sign = true;
			for (CreditInterview interview : interviewList) {
				if(interview.getName().equals(bean.getSpouseName()) && interview.getPhone().equals(bean.getSpouseMobile())){
					sign = false;
					break;
				}
			}
			if(sign){
				if("02".equals(bean.getMarriage())){
					CreditInterview iv=new CreditInterview();
					iv.setName(bean.getSpouseName());
					iv.setPhone(bean.getSpouseMobile());
					iv.setRelation("04");
					iv.setType("05");
					iv.setAddress(bean.getSpouseComProvince() + bean.getSpouseComCity() + bean.getSpouseComCounty());
					iv.setAddressFlag(1);
					iv.setIsAdd(0);
					iv.setState("1");
					iv.setSurveyState("01");
					iv.setSurveyFlag("01");
					interviewList_1.add(iv);					
				}
			}
			interviewList.addAll(interviewList_1);
		}
		map.put("type05InterviewList", interviewList);		//把本人手机号，初始化
		log.info(thisMethodName+":end");
		return "credit/verify/creditInterviewSurvey";
	}
	
	/**
	 * 获取月还款额根据放款金额和产品
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 
	 */
	@RequestMapping("/getYhkje.do")
	public void getYhkje(double fkje, String productName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		//计算月还款额有两种方式：1.自己计算的月还款额，2.调赵鹏写的根据放款金额和产品计算月还款额的接口
		//自己计算的月还款额
		fkje = MathUtils.mul(fkje, 10000);
		double yhkje = creditSignService.getYhkje(fkje, productName);
		//调赵鹏写的根据放款金额和产品计算月还款额的接口
		
		writer.print(JsonUtil.object2json(new JsonMsg(true, yhkje + "")));
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 获取放款金额根据月还款额和产品
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 
	 */
	@RequestMapping("/getFkje.do")
	public void getFkje(double yhkje, String productName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		//计算放款金额有两种方式：1.自己计算的放款额，2.调赵鹏写的根据月还款额和产品计算放款金额的接口
		//自己计算的放款额
		double fkje = creditSignService.getFkje(yhkje, productName);
		//调赵鹏写的根据月还款额和产品计算放款金额的接口
		writer.print(JsonUtil.object2json(new JsonMsg(true, fkje + "")));
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 获取 月还款额和合同金额（根据产品类型和借款金额）、借款金额和合同金额（根据产品类型和月还款额）、借款金额和月还款额（根据产品类型和合同金额）
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 
	 */
	@RequestMapping("/getJe.do")
	public void getJe(double jkje, double yhkje, double htje, String productName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		if(jkje <= 0.00 && yhkje <= 0.00 && htje <= 0.00){
			writer.print(JsonUtil.object2json(new JsonMsg(true, 0.00 + "," + 0.00)));     //如果都为0则都返回0
		}
		Product product = productService.queryByName(productName);
		//1.根据 产品类型和借款金额 计算 月还款额和合同金额
		if(jkje > 0.00){
			jkje = MathUtils.mul(jkje, 10000);
			double yhkje1 = creditSignService.getYhkje(jkje, productName); //月还款额
			CreditDecision creditDecision = new CreditDecision();
			creditDecision.setAmount(jkje);
			double htje1 = creditSignService.getHtJkje(creditDecision, product); //合同金额
			writer.print(JsonUtil.object2json(new JsonMsg(true, yhkje1 + "," + htje1)));  //月还款额,合同金额
		}
		
		//2.根据 产品类型和月还款额 计算 借款金额和合同金额
		if(yhkje > 0.00){
			double jkje1 = creditSignService.getFkje(yhkje, productName); //借款金额
			CreditDecision creditDecision = new CreditDecision();
			creditDecision.setAmount(jkje1);
			double htje1 = creditSignService.getHtJkje(creditDecision, product); //合同金额
			writer.print(JsonUtil.object2json(new JsonMsg(true, jkje1 + "," + htje1)));   //借款金额,合同金额
		}
		
		//3.根据 产品类型和合同金额 计算 借款金额和月还款额
		if(htje > 0.00){
			double yhkje1 = creditSignService.getYhkje(htje, product); //月还款额
			double jkje1 = creditSignService.getFkje(yhkje1, productName); //借款金额
			writer.print(JsonUtil.object2json(new JsonMsg(true, jkje1 + "," + yhkje1)));  //借款金额,月还款额
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 流程报告
	 * @param id
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/processReport.do")
	public String  creditProcessReport(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//个人信息
		CreditApp bean = null;
		if (null != id && !"".equals(id)) {
			bean  = this.creditAppService.queryByKey(id);
			map.put("bean", bean);
		}
		//流程信息，去掉结束节点
		List<BpmLog> bpmLogList = bpmLogService.getBpmLogsByBizKey(bean.getAppId());
		for(int i=0;i<bpmLogList.size();i++){
			String state = bpmLogList.get(i).getState();
			if("结束".equals(state)){
				bpmLogList.remove(i);
				i=i-1;
			}
		}
		map.put("bpmLogList", bpmLogList);
		//退回码|拒贷码|前端拒贷码，均已识别解析显示
		List<String> code1list = new ArrayList<String>();
		List<String> code2list = new ArrayList<String>();
		for(int i=0;i<bpmLogList.size();i++){
			BpmLog bpmLog = bpmLogList.get(i);
			String[] codes = bpmLog.getCodes();
			code1list.add(codes[0]);
			code2list.add(codes[1]);
		}
		map.put("code1list", code1list);
		map.put("code2list", code2list);
		log.info(thisMethodName+":end");
		return "credit/verify/creditProcessReport";
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 跳转到 补录信用报告页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/updateExt.do")
	public String creditVerifyUpdateExt(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CreditApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.creditAppService.queryByKey(id);
			map.put("bean", bean);
		}
		log.info(thisMethodName+":end");
		return "credit/verify/creditVerifyUpdate_ext";
	}
	
	
	
	
}
