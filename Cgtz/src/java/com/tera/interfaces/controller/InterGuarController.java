package com.tera.interfaces.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
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

import com.tera.audit.loan.model.form.CollateralJsonMsg;
import com.tera.feature.loanguar.model.LoanGuar;
import com.tera.feature.loanguar.service.ILoanGuarService;
import com.tera.interfaces.model.AppGuarBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.util.MathUtils;

/**
 * 抵押物相关接口
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter/loanGuar")
public class InterGuarController extends BaseController {

	private static final Logger log = Logger.getLogger(InterGuarController.class);
	
	@Autowired
	private ILoanGuarService loanGuarService;
	
	
	@RequestMapping("/list.do")
	public void guarList(String loanId,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		log.info("请求参数：loanId<--------------->"+loanId);
		
		PrintWriter writer = null;
		
		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			if (null == loanId) {
				loanId = "";
			}
			Map<String, Object> queryMap = new HashMap<String,Object>();
			queryMap.put("loanId", loanId);
			List<LoanGuar> guars = this.loanGuarService.queryList(queryMap);
			
			String jsonStr = "";
			
			jsonStr = GsonUtils.getInstance().toJson(guars, List.class);
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
	public void guarUpdate(Integer id,HttpServletRequest request,HttpServletResponse response) {
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
			LoanGuar guar = null;
			try {
				guar = this.loanGuarService.queryByKey(id);
				// 注册资本单位换算
				guar.setOrgRegAmt(MathUtils.div(guar.getOrgRegAmt(), 10000.0));
				String jsonStr = GsonUtils.getInstance().toJson(guar);
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
	public void guarSave(@RequestBody String reqBody,HttpServletRequest request,HttpServletResponse response) {
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
			
			AppGuarBean bean = GsonUtils.getInstance().fromJson(reqBody, AppGuarBean.class);
			
			Timestamp nowTime=new Timestamp(System.currentTimeMillis());
			LoanGuar tmpBean = bean.getLoanGuar();
			
			// 注册资本单位换算
			tmpBean.setOrgRegAmt(MathUtils.mul(tmpBean.getOrgRegAmt(), 10000.0));
			//如果存在
			if (tmpBean.getId() != 0) {
				LoanGuar loanGuar = this.loanGuarService.queryByKey(tmpBean.getId());
				loanGuar.setEdu(tmpBean.getEdu());
				loanGuar.setHomeAddr(tmpBean.getHomeAddr());
				loanGuar.setHomeCity(tmpBean.getHomeCity());
				loanGuar.setHomeCtry(tmpBean.getHomeCtry());
				loanGuar.setHomePrvn(tmpBean.getHomePrvn());
				loanGuar.setIdNo(tmpBean.getIdNo());
				loanGuar.setIdType(tmpBean.getIdType());
				loanGuar.setIsOrig(tmpBean.getIsOrig());
				loanGuar.setLegalIdNo(tmpBean.getLegalIdNo());
				loanGuar.setLegalIdType(tmpBean.getLegalIdType());
				loanGuar.setLegalName(tmpBean.getLegalName());
				loanGuar.setLegalTel(tmpBean.getLegalTel());
				loanGuar.setMarriage(tmpBean.getMarriage());
				loanGuar.setName(tmpBean.getName());
				loanGuar.setNowAddr(tmpBean.getNowAddr());
				loanGuar.setNowCity(tmpBean.getNowCity());
				loanGuar.setNowCtry(tmpBean.getNowCtry());
				loanGuar.setNowPrvn(tmpBean.getNowPrvn());
				loanGuar.setOrgBus(tmpBean.getOrgBus());
				loanGuar.setOrgRegAmt(tmpBean.getOrgRegAmt());
				loanGuar.setOrgRegTime(tmpBean.getOrgRegTime());
				loanGuar.setSaleRemark(tmpBean.getSaleRemark());
				loanGuar.setSex(tmpBean.getSex());
				loanGuar.setShareIdNo(tmpBean.getShareIdNo());
				loanGuar.setShareIdType(tmpBean.getShareIdType());
				loanGuar.setShareName(tmpBean.getShareName());
				loanGuar.setShareTel(tmpBean.getShareTel());
				loanGuar.setTel(tmpBean.getTel());
				loanGuar.setTel2(tmpBean.getTel2());
				loanGuar.setType(tmpBean.getType());
				loanGuar.setUpdateTime(nowTime);
				loanGuar.setUpdateUid(bean.getLoginId());
				loanGuar.setValidType(tmpBean.getValidType());
				loanGuar.setUpdateTime(nowTime);
				loanGuar.setUpdateUid(bean.getLoginId());
				this.loanGuarService.update(loanGuar);
			} else { //如果不存在
				tmpBean.setCreateTime(nowTime);
				tmpBean.setCreateUid(bean.getLoginId());
				this.loanGuarService.add(tmpBean);
			}
			
			CollateralJsonMsg jsonMsg = new CollateralJsonMsg(tmpBean.getId(), tmpBean.getLoanId(), tmpBean.getType(), true, "操作成功!");
			
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
}
