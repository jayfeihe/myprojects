 package com.hikootech.mqcash.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dto.BankDTO;
import com.hikootech.mqcash.dto.ConfigBankDTO;
import com.hikootech.mqcash.dto.ContractInfoDTO;
import com.hikootech.mqcash.dto.UserBankCardDTO;
import com.hikootech.mqcash.po.UserBankCard;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.po.UserProtocol;
import com.hikootech.mqcash.po.UserRepaymentPlans;
import com.hikootech.mqcash.service.ConfigConstantsService;
import com.hikootech.mqcash.service.ConfigKvService;
import com.hikootech.mqcash.service.ContractInfoService;
import com.hikootech.mqcash.service.InstalmentInfoService;
import com.hikootech.mqcash.service.UserBankCardService;
import com.hikootech.mqcash.service.UserProtocolService;
import com.hikootech.mqcash.service.UserRepayPlanService;
import com.hikootech.mqcash.service.UserService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.EntityUtils;
/**
 * 分期账单管理
 * */
@RequestMapping("/contractManage")
@Controller
public class ContractManageController extends BaseController {
	
	private static Logger log = LoggerFactory.getLogger(ContractManageController.class);
	
	@Autowired
	private ContractInfoService contractInfoService;
	@Autowired
	private UserBankCardService userBankCardService;
	@Autowired
	private InstalmentInfoService instalmentInfoService;
	@Autowired
	private  ConfigConstantsService  configConstantsService;
	@Autowired
	private UserRepayPlanService userRepayPlanService;
	@Autowired
	private UserProtocolService userProtocolService;
	@Autowired
	private ConfigKvService configKvService;

	/**
	 *  合同管理画面
	 * */
	@RequestMapping("/userContract.jhtml")
	public String contractManage(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("进入合同管理画面");
		UserInfo userInfo=getUserInfo();
		map.put("userInfo", userInfo);
		map.put("logoMsg", CommonConstants.LOGO_MSG[2]);
		map.put("topView", instalmentInfoService.getCurTopView(userInfo.getUserId()));
		return "contractManage/user_contract";
	}
	
	/**
	 *  加载合同列表
	 * */
	@RequestMapping("/contractList.jhtml")
	public String contractList(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("加载账单合同列表画面");
		UserInfo userInfo=getUserInfo();
		map.put("userInfo", userInfo);
		String curLi=request.getParameter("curLi");
		String queryNum=request.getParameter("queryNum");
		Map<String,Object> queryMap=new HashMap<String, Object>();
		queryMap.put("userId", getUserInfo().getUserId());
		
		//判断全部 或是执行中
		if(curLi==null||"".equals(curLi)||"0".equals(curLi)){
			queryMap.put("protocolStatus", "");
		}else if("1".equals(curLi)){
			
			//执行中
	 		queryMap.put("protocolStatus", new String[]{StatusConstants.PROTOCAL_STATUS_DOING_EFFECTIVE.toString()});
		}else{
			
			//结束
	 		queryMap.put("protocolStatus", new String[]{StatusConstants.PROTOCAL_STATUS_CANCEL_ORDER.toString(),StatusConstants.PROTOCAL_STATUS_COMPLETED.toString(),StatusConstants.PROTOCAL_STATUS_RETURN_GOODS.toString()});
		}
		if(queryNum==null||"".equals(queryNum.trim()) ){
			queryMap.put("queryNum", "");
		}else{
			queryMap.put("queryNum", queryNum.trim());
		}
		List<ContractInfoDTO> list=null;
		try {
			list = contractInfoService.queryInstalmentOrderInfoList(queryMap);
		} catch (Exception e) {
			log.error("查询合同信息发生错误",e);
		}
		for (ContractInfoDTO contractInfoDTO : list) {
			if(EntityUtils.isEmpty(contractInfoDTO.getProductImgUrl())){
				contractInfoDTO.setProductImgUrl(configKvService.get("DEFAULT_PRODUCT_PIC"));
			}
		}
		map.put("list", list);
		map.put("queryNum", queryMap.get("queryNum"));
		String partnerOrderListUrl= ConfigUtils.getProperty("mqcash_partner_orderlist_url");
		map.put("partnerOrderListUrl", partnerOrderListUrl);
		return "contractManage/user_contract_list";
	}
	
	/**
	 * 进入银行卡列表画面
	 * */
	@RequestMapping("/modifyBankCard.jhtml")
	public String modifyBankCard(HttpServletRequest request, ModelMap map){
		
		log.info("查询银行卡情况");
		String currentInstalmentId=request.getParameter("instalmentId");
		String currentbankCardId=request.getParameter("bankCardId");
		UserInfo userInfo=getUserInfo();
		map.put("userInfo", userInfo);
		
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("userId", userInfo.getUserId());
		queryMap.put("bindStatus", StatusConstants.USER_BANK_CARD_BIND);
		
		//获取用户已经绑定的银行卡
		List<UserBankCardDTO> listUserBankInfo=userBankCardService.queryBankInfoByUser(queryMap);
		
		//获取银行卡配置信息
		List<BankDTO> bankList = null;
		try {
			bankList = configConstantsService.getBankList(new ConfigBankDTO(null,  null,1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//返回画面信息
		map.put("currentInstalmentId", currentInstalmentId);
		map.put("currentbankCardId", currentbankCardId);
		map.put("idCard", userInfo.getIdCard().substring(0, 6)+"****"+userInfo.getIdCard().substring(14,18));
		map.put("userInfo",userInfo);
		map.put("bankList", bankList);
		map.put("listUserBankInfo", listUserBankInfo);
		map.put("logoMsg", CommonConstants.LOGO_MSG[2]);
		return "contractManage/user_bank_card";
	}
	
	/**
	 * 更改绑定银行卡
	 * */
	@RequestMapping("/modifyBankCardSub.jhtml")
	@ResponseBody
	public Map<String,Object> modifyBankCardSub(HttpServletRequest request){
		
		log.info("更改账单绑定的银行卡");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		//获取前台传值
		String redirectUrl="contractManage/userContract.jhtml";
		String bankId=request.getParameter("bankCardId");
		
		if(EntityUtils.isEmpty(bankId)){
			log.error("更改账单绑定的银行卡发生错误，新银行卡bankId为空");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请选择银行卡");
			return retMap;
		}
		
		//根据账单信息，用户id查询银行卡
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("bankCardId", bankId);
		queryMap.put("userId", getUserInfo().getUserId());
		
		UserBankCard bankInfo=null;
		try {
			bankInfo = userBankCardService.queryBankCardByKey(queryMap);
		} catch (Exception e) {
			log.error("查询银行卡时发生错误",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "查询发生错误，请稍候重试!");
			return retMap;
		}
		if(EntityUtils.isEmpty(bankInfo)){
			log.error("更改账单绑定的银行卡发生错误，没有查到当前用户的银行卡，bankCardId 为"+bankId);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请选择正确的银行卡!");
			return retMap;
		}
		
		//查到银行卡判断其状态只有是绑定状态的才能继续绑定
		if(StatusConstants.USER_BANK_CARD_BIND.intValue()!=bankInfo.getBindStatus()){
			log.error("更改账单绑定的银行卡发生错误，该银行卡状态不是已绑定，不能继续绑定订单");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请选择有效的银行卡!");
			return retMap;
		}
		
		queryMap.put("instalmentId", request.getParameter("instalmentId"));
		boolean ret=false;
		try {
			contractInfoService.modifyBankCardOfInstalment(queryMap);
			ret=true;
		} catch (Exception e) {
			log.error("更改银行卡发生错误：",e);
		}
		
		if(ret){
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, "更改账单绑定的银行卡成功");
			retMap.put("redirectUrl", redirectUrl);
			return retMap;
		}else{
			log.error("更改账单绑定的银行卡发生错误，结果为"+ret);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "更改账单绑定的银行卡失败");
			return retMap;
		}
	}
	
	/**
	 * 获取合同信息
	 * */
	@RequestMapping("/requestJkht.jhtml")
	public String requestJkht(HttpServletRequest request, ModelMap map) throws Exception {
		String instalmentId=request.getParameter("instalmentId");
		UserInfo userInfo=getUserInfo();
		map.put("userInfo", userInfo);
		
		Map<String,Object> queryMap=new HashMap<String, Object>();
		queryMap.put("instalmentId", instalmentId);
		queryMap.put("userId", userInfo.getUserId());
		List<UserProtocol> listProtacol=userProtocolService.queryUserProtocolByInstalmentId(queryMap);
		
		if(listProtacol==null||listProtacol.size()!=1){
			log.error("根据分期单号和用户id查询得到的合同不为1");
			return "jsp/error";
		}
		
		UserProtocol protocol=listProtacol.get(0);
		map.put("userProtocol", protocol);
		
		//查询还款计划
		Map<String,Object> queryMapPlan=new HashMap<String, Object>();
		queryMapPlan.put("instalmentId", instalmentId);
		queryMapPlan.put("userId", userInfo.getUserId());
		
		List<UserRepaymentPlans> planList=userRepayPlanService.queryRepayPlansByInstalmentId(queryMap);
		
		if(planList==null||planList.size()==0){
			return "jsp/error";
		}
		
		map.put("planList", planList);
		
		return "contractManage/jkht";
	}

}
