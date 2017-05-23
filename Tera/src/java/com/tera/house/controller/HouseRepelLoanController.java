package com.tera.house.controller;

import java.io.PrintWriter;
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

import com.tera.house.model.HouseApp;
import com.tera.house.model.HouseDecision;
import com.tera.house.model.form.HouseQBean;
import com.tera.house.model.form.RepelLoanFBean;
import com.tera.house.service.HouseAppService;
import com.tera.house.service.HouseContactService;
import com.tera.house.service.HouseDecisionService;
import com.tera.house.service.HouseExtService;
import com.tera.house.service.HouseSignService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.cooperation.jmbox.model.form.JMProjecResponseBean;
import com.tera.cooperation.jmbox.service.JmboxRepelLoanService;
import com.tera.cooperation.jmbox.service.JmboxService;
import com.tera.img.service.ImgService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;

/**
 *	功能区 积木盒子 手工拒贷 控制器
 * @author zhang.yue
 *
 */
@Controller
@RequestMapping("/house/repelLoan")
public class HouseRepelLoanController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(HouseRepelLoanController.class);
	
	/**
	 * houseAppService
	 */
	@Autowired(required=false) //自动注入
	private HouseAppService houseAppService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false) //自动注入
	ImgService imgService;
	@Autowired(required=false) //自动注入
	HouseContactService houseContactService;
	@Autowired(required=false) //自动注入
	HouseExtService houseExtService;
	
	@Autowired(required=false) //自动注入
	HouseDecisionService houseDecisionService;
	@Autowired(required=false) //自动注入
	HouseSignService houseSignService;
	@Autowired(required=false) //自动注入
	ContractService contractService;
	@Autowired(required=false) //自动注入
	JmboxRepelLoanService jmboxRepelLoanService;
	@Autowired(required=false) //自动注入
	ProductService<Product> productService;
	@Autowired(required=false) //自动注入
	JmboxService jmboxService;
	
	
	
	
	/**
	 * 跳转到特殊审批的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String houseRepelLoanQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "house/repelLoan/houseRepelLoanQuery";
	}

	/**
	 * 显示特殊审批查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String houseRepelLoanList(HouseQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		int rowsCount = this.contractService.queryCreditRepelLoanCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Contract> contractList = this.contractService.queryCreditRepelLoanList(queryMap);
		pm.setData(contractList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "house/repelLoan/houseRepelLoanList";
	}

	/**
	 * 跳转到审批详情页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String houseRepelLoanRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		HouseApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.houseAppService.queryByKey(id);
			map.put("bean", bean);
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "1");
			List<HouseDecision> houseVerityList = houseDecisionService.queryList(fmap);
			if(null != houseVerityList && houseVerityList.size() > 0){
				HouseDecision houseDecision = houseVerityList.get(0);
				double yhkje = houseSignService.getYhkje(houseDecision.getAmount(), houseDecision.getProduct());
				map.put("yhkje1", yhkje);
				map.put("houseDecision1", houseVerityList.get(0));//审核信息
			}
			fmap.put("type", "2");
			List<HouseDecision> approvalList = houseDecisionService.queryList(fmap);
			if(null != approvalList && approvalList.size() > 0){
				HouseDecision houseDecision = approvalList.get(0);
				double yhkje = houseSignService.getYhkje(houseDecision.getAmount(), houseDecision.getProduct());
				map.put("yhkje2", yhkje);
				map.put("houseDecision2", approvalList.get(0));//审批决策信息
			}
			fmap.put("type", "3");
			List<HouseDecision> specialApprovalList = houseDecisionService.queryList(fmap);
			if(null != specialApprovalList && specialApprovalList.size() > 0){
				HouseDecision houseDecision = specialApprovalList.get(0);
				double yhkje = houseSignService.getYhkje(houseDecision.getAmount(), houseDecision.getProduct());
				map.put("yhkje3", yhkje);
				map.put("houseDecision3", specialApprovalList.get(0));//特殊审批信息
			}
		}
		log.info(thisMethodName+":end");
		return "house/repelLoan/houseRepelLoanRead";
	}
	
	/**
	 * 拒贷
	 * @param repelLoanFBean 			积木盒子传入参数
	 * @param request 	request
	 * @param response 	response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/repelLoan.do")
	public void repelLoan(RepelLoanFBean repelLoanFBean, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		try{
//			boolean b = jmboxRepelLoanService.jmRepelLoan(repelLoanFBean, loginId);
//			if(b){
//				writer.print(JsonUtil.object2json(new JsonMsg(true, "拒贷成功！")));				
//			}else{
//				writer.print(JsonUtil.object2json(new JsonMsg(false, "拒贷失败！")));								
//			}
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
	 * 新标签页中拒贷
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/repelLoanPopup.do")
	public String repelLoanPopup(String id,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		HouseApp bean = null;
		bean  = this.houseAppService.queryByKey(id);
		map.put("bean", bean);
		
		log.info(thisMethodName+":end");
		return "house/repelLoan/houseRepelLoanPopup";
	}
	/**
	 *  积木盒子 手动确认 放款成功 
	 * @param contractNo
	 * @param lendDate
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/jmRealTimeQuery.do")
	public void houseJMRealTimeQuery(String contractNo,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		if(user.getIsAdmin()==1){
			String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("contractNo", contractNo);
			Contract contract=contractService.queryList(fmap).get(0);
			
			fmap=new HashMap<String, Object>();
			fmap.put("appId", contract.getLoanAppId());
			fmap.put("state", "1");
			fmap.put("type", "0");
			//最终决策
			HouseDecision houseDecision = houseDecisionService.queryList(fmap).get(0);
			Map<String, Object> paMap=new HashMap<String, Object>();
			paMap.put("appId",contract.getLoanAppId());
			paMap.put("state", "1");
			paMap.put("type", "0");
			List<HouseDecision> houseDecisionList = houseDecisionService.queryList(paMap);
			HouseDecision decision=houseDecisionList.get(0);
			
			Product pro = productService.queryByName(decision.getProduct());
			
//			if(pro.getName().indexOf("JM")>0){//特殊流程  JM 合作
			if("JM".equals(pro.getBelongChannel())){//特殊流程  JM 合作
				JMProjecResponseBean jg=jmboxService.reqQueryJmBox(contract);
				writer.print(JsonUtil.object2json(jg));
			}else{
				writer.print("不是JM产品。");
			}
		}else{
			writer.print("没有访问权限。");
		}
	}
	
	
}
