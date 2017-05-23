package com.tera.interfaces.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.audit.branch.model.AuditFormBean;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.risk.model.CollateralPriceAudit;
import com.tera.audit.risk.service.ICollateralPriceAuditService;
import com.tera.audit.risk.service.IRiskFirstAuditService;
import com.tera.feature.warehouse.model.Warehouse;
import com.tera.feature.warehouse.service.IWarehouseService;
import com.tera.interfaces.model.AppAuditBean;
import com.tera.interfaces.model.AppPriceBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.UserExt;
import com.tera.sys.service.UserExtService;

/**
 * 风控初审相关接口
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter/riskFirst")
public class InterRiskFirstController extends BaseController {

	private static final Logger log = Logger.getLogger(InterRiskFirstController.class);
	
	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private UserExtService userExtService;
	@Autowired
	private IRiskFirstAuditService riskFirstAuditService;
	@Autowired
	private ICollateralService collateralService;
	@Autowired
	private IWarehouseService warehouseService;
	@Autowired
	private ICollateralPriceAuditService collateralPriceAuditService;
	
	
	@RequestMapping("/list.do")
	public void riskFirstList(String loginId,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		log.info("请求参数：loginId<--------------->"+loginId);
		
		PrintWriter writer = null;

		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			UserExt userExt = this.userExtService.queryByKey(loginId);
			Map<String, Object> queryMap = new HashMap<String,Object>();
			
			queryMap.put("bpmStates", new String[]{CommonConstant.PROCESS_C});
			queryMap.put("processer", loginId);
			queryMap.put("orgId", userExt.getOrgId());
			queryMap.put("isRenew", "0");
			queryMap.put("appProducts", new String[]{"01","03"}); // 车贷和房贷
			List<LoanBase> riskFirstList = this.loanBaseService.queryBusinessList(queryMap);
			
			String jsonStr = "";
			if (riskFirstList != null && riskFirstList.size() > 0) {
				for (LoanBase loanBase : riskFirstList) {
					loanBase.setAppState(loanBase.getAppState());
				}
			}
			
			jsonStr = GsonUtils.getInstance().toJson(riskFirstList, List.class);
			log.info("== 响应报文："+ jsonStr +" ==");
			
			writer.print(jsonStr);
		} catch (Exception e) {
			writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"接口服务调用失败")));
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		
		log.info(thisMethodName+":end");
	}
	
	@RequestMapping("/update.do")
	public void riskFirstUpdate(Integer id,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		if (id != null && id != 0) {
			PrintWriter writer = null;
			
			try {
				response.setContentType("application/json;charset=UTF-8");
				writer = response.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			LoanBase loanBase = null;
			try {
				// 申请信息
				loanBase = this.loanBaseService.queryByKey(id);
				String loanId = loanBase.getLoanId();
				
				AppAuditBean bean = new AppAuditBean();
				bean.setLoanId(loanId);
				bean.setIsTgth(loanBase.getIsTgth());
				bean.setNodeType("1");
				
				String jsonStr = GsonUtils.getInstance().toJson(bean);
				log.info("== 响应报文："+ jsonStr +" ==");
				writer.print(jsonStr);
				
			} catch (Exception e) {
				writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"接口服务调用失败")));
				e.printStackTrace();
				log.error(e.getMessage());
			} finally {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
			}
		}
		
		log.info(thisMethodName+":end");
	}
	
	@RequestMapping("/save.do")
	public void riskFirstSave(@RequestBody String reqBody, HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		try {
			reqBody = URLDecoder.decode(reqBody, "UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		
		log.info("== 请求报文:"+reqBody+" ==");
		
		PrintWriter writer = null;
		
		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			AppAuditBean bean = GsonUtils.getInstance().fromJson(reqBody, AppAuditBean.class);
			
			AuditFormBean formBean = new AuditFormBean();
			formBean.setDecision(bean.getDecision());
			formBean.setLoanId(bean.getLoanId());
			formBean.setNode(bean.getNode());
			formBean.setRemark(bean.getRemark());
			
			JsonMsg jsonMsg = this.riskFirstAuditService.operateProcess(formBean, bean.getLoginId());
			
			String jsonStr = GsonUtils.getInstance().toJson(jsonMsg);
			log.info("== 响应报文："+ jsonStr +" ==");
			
			writer.print(jsonStr);
		} catch (Exception e) {
			writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"接口服务调用失败")));
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 核价列表接口
	 * @param loginId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/priceList.do")
	public void priceList(String loanId,String type,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		log.info("请求参数：loanId<--------------->"+loanId + ",type<--------------->"+type);
		
		PrintWriter writer = null;

		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			Map<String, Object> queryMap = new HashMap<String,Object>();
			queryMap.put("loanId", loanId);
			queryMap.put("type", type);
			List<Collateral> priceList = this.collateralService.queryList(queryMap);
			
			String jsonStr = GsonUtils.getInstance().toJson(priceList, List.class);
			
			log.info("== 响应报文："+ jsonStr +" ==");
			
			writer.print(jsonStr);
		} catch (Exception e) {
			writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"接口服务调用失败")));
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		
		log.info(thisMethodName+":end");
	}
	
	@RequestMapping("/priceUpdate.do")
	public void priceUpdate(Integer id,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		if (id != null && id != 0) {
			PrintWriter writer = null;
			
			try {
				response.setContentType("application/json;charset=UTF-8");
				writer = response.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			Collateral collateral = null;
			List<CollateralPriceAudit> priceHistoryList = null;
			try {
				collateral  = this.collateralService.queryByKey(id);
				Warehouse warehouse = this.warehouseService.queryByKey(collateral.getWarehouseId());
				if (warehouse != null) {
					collateral.setWarehousePrvn(warehouse.getPrvn());
					collateral.setWarehouseCity(warehouse.getCity());
				}
				priceHistoryList = this.collateralPriceAuditService.queryByCollateralId(String.valueOf(id));
				
				AppPriceBean bean = new AppPriceBean();
				bean.setCollateral(collateral);
				bean.setPriceHistoryList(priceHistoryList);
				
				String jsonStr = GsonUtils.getInstance().toJson(bean);
				log.info("== 响应报文："+ jsonStr +" ==");
				writer.print(jsonStr);
				
			} catch (Exception e) {
				writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"接口服务调用失败")));
				e.printStackTrace();
				log.error(e.getMessage());
			} finally {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
			}
		}
		
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 核价保存
	 * @param reqBody
	 * @param request
	 * @param response
	 */
	@RequestMapping("/priceSave.do")
	public void priceSave(@RequestBody String reqBody, HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		try {
			reqBody = URLDecoder.decode(reqBody, "UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		
		log.info("== 请求报文:"+reqBody+" ==");
		
		PrintWriter writer = null;
		
		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			AppPriceBean bean = GsonUtils.getInstance().fromJson(reqBody, AppPriceBean.class);
			
			Collateral coll = new Collateral();
			coll.setId(bean.getCollateralId()); 
			coll.setAuditPriceState(bean.getAuditPriceState());
			coll.setAuditPriceRemark(bean.getAuditPriceRemark());
			coll.setLatestPrice(bean.getLatestPrice());
			this.collateralPriceAuditService.saveOrUpdate(coll ,bean.getLoginId());
			
			String jsonStr = GsonUtils.getInstance().toJson(new JsonMsg(true, "操作成功!"));
			log.info("== 响应报文："+ jsonStr +" ==");
			
			writer.print(jsonStr);
		} catch (Exception e) {
			writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"接口服务调用失败")));
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		
		log.info(thisMethodName+":end");
	}
}
