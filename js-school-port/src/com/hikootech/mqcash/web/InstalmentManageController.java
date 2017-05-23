package com.hikootech.mqcash.web;

import java.util.Collections;
import java.util.Date;
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
import com.hikootech.mqcash.dto.InstalmentInfoDTO;
import com.hikootech.mqcash.dto.UserPaymentOrderDTO;
import com.hikootech.mqcash.dto.UserPaymentOrderDetailDTO;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.po.UserInstalment;
import com.hikootech.mqcash.service.ConfigConstantsService;
import com.hikootech.mqcash.service.InstalmentInfoService;
import com.hikootech.mqcash.service.InstalmentManageService;
import com.hikootech.mqcash.service.UserInstalmentService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.InstalmentSortCompare;
import com.hikootech.mqcash.util.UserUtils;

/**
 * 分期账单管理
 */
@RequestMapping("/instalmentManage")
@Controller
public class InstalmentManageController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(InstalmentManageController.class);

	@Autowired
	private InstalmentInfoService instalmentInfoService;
	@Autowired
	private ConfigConstantsService configConstantsService;
	@Autowired
	private InstalmentManageService instalmentManageService;

	@Autowired
	private UserInstalmentService userInstalmentService;
	
	/**
	 * 分期账单管理画面
	 */
	@RequestMapping("/instalmentBill.jhtml")
	public String instalmentBill(HttpServletRequest request, ModelMap map) throws Exception {

		log.info("进入分期账单管理画面");
		UserInfo userInfo = getUserInfo();
		map.put("userInfo", userInfo);

		// 用于判断是哪个标签
		String currentLi = request.getParameter("currentLi");

		// 返回页面数据
		map.put("topView", instalmentInfoService.getCurTopView(userInfo.getUserId()));
		map.put("logoMsg", CommonConstants.LOGO_MSG[1]);
		map.put("currentLi", currentLi);
		return "instalmentManage/user_bill";
	}
	/**
	 * 分期账单管理画面
	 */
	@RequestMapping("/instalmentInitBill.jhtml")
	public String instalmentInitBill(HttpServletRequest request, ModelMap map) throws Exception {
		
		log.info("进入分期账单管理画面");
		UserInfo userInfo = getUserInfo();
		map.put("userInfo", userInfo);
		
		// 用于判断是哪个标签
		String currentLi = request.getParameter("currentLi");
		
		// 返回页面数据
		map.put("topView", instalmentInfoService.getCurTopView(userInfo.getUserId()));
		map.put("logoMsg", CommonConstants.LOGO_MSG[1]);
		map.put("currentLi", currentLi);
		return "instalmentManage/user_bill";
	}

	/**
	 * queryInstalmentInfo<br/>
	 * TODO(加载账单标签)
	 * 
	 * @return
	 * @author zhaohefei
	 * @2015年12月11日
	 * @return Map<String,Object> 返回类型
	 */
	@RequestMapping("/queryInstalmentInfo.jhtml")
	@ResponseBody
	public Map<String, Object> queryInstalmentInfo() {

		log.info("查询分期账单情况");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		UserInfo userInfo = getUserInfo();
		queryMap.put("userId", userInfo.getUserId());

		// 前台传值为空时，默认认为是全部标签，不为空时，根据传值转化后的值进行查询
		int instalmentStatus = 0;
		try {
			instalmentStatus = Integer.parseInt(getRequest().getParameter("instalmentStatus"));
		} catch (Exception e) {
			//“全部”标签
			instalmentStatus = Integer.MAX_VALUE;
		}
		
		//根据用户id 查询所有分期单
		List<InstalmentInfoDTO> list = instalmentInfoService.getInsMsgByUserId(queryMap);

		//返回分期单
		if (list == null || list.size() < 1) {
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "目前没有查询到账单！");
			return retMap;
		}

		//根据需求，找出所有符合条件的订单
		List<InstalmentInfoDTO> listToShow=null;
		try {
			listToShow = instalmentInfoService.makeInstalmentInfoList(list, instalmentStatus);
			//针对订单排序
			Collections.sort(listToShow, new InstalmentSortCompare());
		} catch (Exception e) {
			log.error("查询全部或者待还款分期账单时，发生错误",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "发生错误，请刷新重试！");
			 return retMap;
		}
		
		//将信息返回画面
		String partnerOrderListUrl = ConfigUtils.getProperty("mqcash_partner_orderlist_url");
		retMap.put("instalmentlist", listToShow);
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put("partnerOrderListUrl", partnerOrderListUrl);
		return retMap;
	}

	/**
	 * queryPlanInfo<br/>
	 * TODO(查询分期计划)
	 * 
	 * @return
	 * @author zhaohefei
	 * @2015年12月11日
	 * @return Map<String,Object> 返回类型
	 */
	@RequestMapping("/queryPlanInfo.jhtml")
	@ResponseBody
	public Map<String, Object> queryPlanInfo() {
		
		log.info("查询分期计划情况");
		Map<String, Object> retMap = new HashMap<String, Object>();

		String instalmentId = getRequest().getParameter("instalmentId");

		//调用查询计划列表和当期计划，返回画面
		try {
			retMap=instalmentManageService.queryPlansInfo(instalmentId,getUserInfo().getUserId(),null);
		} catch (Exception e) {
			log.error("加载分期账单下的分期计划时，发生错误",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "发生错误，请刷新重试！");
			 return retMap;
		}
		return retMap;
	}

	/** 弹出立即付款详单 */
	@RequestMapping("/payNow.jhtml")
	@ResponseBody
	public Map<String, Object> payNow() {
		log.info("弹出立即付款详单");
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		String instalmentId = getRequest().getParameter("instalmentId");
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userId", getUserInfo().getUserId());
		queryMap.put("instalmentId", instalmentId);
		
		//判断分期单状态，是否可以弹出立即付款详单画面
		try {
			UserInstalment userInstalment=userInstalmentService.queryInstalmentById(queryMap);
			
			//只有分期单状态是待还款和已逾期时，才允许弹窗
			if( userInstalment.getInstalmentStatus() != StatusConstants.INSTALMENT_STATUS_AWAIT_PAY.intValue()
					&&userInstalment.getInstalmentStatus() != StatusConstants.INSTALMENT_STATUS_OVERDUE.intValue()){
				log.error("点击弹出立即付款，该分期单没有立即付款详单权限，分期状态为："+userInstalment.getInstalmentStatus()+",分期单id："+instalmentId);
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "发生错误，请刷新重试！");
				return retMap;
			}
			
		} catch (Exception e) {
			log.error("点击弹出立即付款，,分期单id："+instalmentId+",查询分期单信息发生错误:",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "发生错误，请刷新重试！");
			return retMap;
		}
		
		//根据传值账单id，查询还款中 或者待还款的 还款计划
	 	InstalmentInfoDTO dtoInfo=null;
	 	Map<String,Object> planMap=null;
		try {
			dtoInfo = instalmentInfoService.queryInstalmentAndProductInfoByUserId(queryMap);
			
			//只需查出待还款，还款中和已逾期三个状态的即可
			  planMap=instalmentManageService.queryPlansInfo(instalmentId,getUserInfo().getUserId(),
						new int[] { StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY, StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY ,StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE});
			
		} catch (Exception e) {
			log.error("弹出立即付款详单时，发生错误",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "发生错误，请刷新重试！");
			return retMap;
		}
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "查询成功！");
		retMap.put("payList", planMap!=null?planMap.get("list"):null);
		retMap.put("dtoInfo", dtoInfo);
		return retMap;
	}

	/** 校验金额是否正确 **/
	@RequestMapping("/payNowSub.jhtml")
	@ResponseBody
	public Map<String, Object> payNowSub() {
		log.info("校验金额和还款顺序是否正确");
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		String redirectUrl = "instalmentManage/getBankList.jhtml" ;
		
		//获取前台传值
		String payPlanIds = getRequest().getParameter("payPlanIds");
		String instalmentId = getRequest().getParameter("instalmentId");
		String totalAmount = getRequest().getParameter("totalAmount");
		
		if(EntityUtils.isEmpty(payPlanIds)){
			log.error("传入的计划id集合为空");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "发生错误，请刷新重试！");
			return retMap;
		}
		
		//调用服务层校验是否可以进入收银台画面，成功后返回包含还款订单的map对象
		Map<String, Object> resultMap;
		try {
			resultMap = instalmentManageService.payNow(totalAmount, payPlanIds, instalmentId, getUserInfo());
		} catch (Exception e) {
			log.error("立即付款校验金额和还款顺序是否正确时，发生错误",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "发生错误，请刷新重试！");
			return retMap;
		}

		//修改失败，直接返回画面
		if(resultMap.get(ResponseConstants.RETURN_CODE).toString().equals( ResponseConstants.FAIL)||resultMap.get(ResponseConstants.RETURN_CODE).toString().equals( ResponseConstants.FAIL_PAYNOW)){
			return resultMap;
		}
				
		//通过校验，可以跳转收银台画面
		
		//生成还款相关信息key值（只作为key，无他用）
		String paymentInfoToken=GenerateKey.getId(CommonConstants.PAYMENT_ORDER_NO_PREFIX, ConfigUtils.getProperty("db_id"));
		 
		Map<String,Object> payInfoMap=new HashMap<String,Object>();
		payInfoMap.put("instalmentId", instalmentId);
		payInfoMap.put("payPlanIds", payPlanIds);
		payInfoMap.put("totalAmount", totalAmount);

		// 将还款相关信息存入缓存
		Map<String, Object> sessionPayMap = UserUtils.getPaymentOrderToPayFromCache(getRequest().getSession());
		if (EntityUtils.isEmpty(sessionPayMap) || sessionPayMap.size() == 0) {
			sessionPayMap = new HashMap<String, Object>();
		}
 		sessionPayMap.put(paymentInfoToken, payInfoMap);
		UserUtils.cachePaymentOrderToPay(getRequest().getSession(), sessionPayMap);
		UserUtils.cachePaymentOrderToPayEffectiveTime(getRequest().getSession(), new Date());

		//跳转选择银行界面
		retMap.put("paymentInfoToken", paymentInfoToken);
		retMap.put("redirectUrl", redirectUrl);
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "您的还款金额与后台计算结果一致，请选择银行！");
		return retMap;
	}


	/**** 跳转值收银台画面 ****/
	@RequestMapping("/getBankList.jhtml")
	public String getBankListToPay(HttpServletRequest request, ModelMap map) throws Exception {
		String paymentInfoToken = request.getParameter("paymentInfoToken");
		String orderNameInfo = request.getParameter("orderNameInfo");
		log.info("跳转值收银台画面，paymentInfoToken="+paymentInfoToken);
		
		UserInfo userInfo = getUserInfo();
		map.put("userInfo", userInfo);
		
		//随机生成还款订单号
		String paymentOrderNo=GenerateKey.getId(CommonConstants.PAYMENT_ORDER_NO_PREFIX, ConfigUtils.getProperty("db_id"));

		//查询主动付款用的银行列表
		List<BankDTO> bankList = null;
		try {
			bankList = configConstantsService.getBankList(new ConfigBankDTO(null, 1, null));
		} catch (Exception e) {
			log.error("查询主动付款用的银行列表发生错误:",e);
		}
		
		//从缓存中获取还款的相关信息
		Map<String, Object> sessionPayMap = UserUtils.getPaymentOrderToPayFromCache(getRequest().getSession());
		if( sessionPayMap==null||sessionPayMap.get(paymentInfoToken)==null){
			log.error("session缓存中没有key为"+paymentInfoToken+"的还款相关信息");
		}else{
			Map<String,Object> payInfoMap=(Map<String, Object>) sessionPayMap.get(paymentInfoToken);
			map.put("payAmount", payInfoMap.get("totalAmount"));
			map.put("instalmentId", payInfoMap.get("instalmentId"));
			//将还款单号存入session
			payInfoMap.put("paymentOrderNo", paymentOrderNo);
		}
		

		//返回画面信息
		map.put("bankList", bankList);
		map.put("orderNameInfo", orderNameInfo);
		map.put("paymentOrderNo", paymentOrderNo);
		map.put("paymentInfoToken", paymentInfoToken);
		map.put("logoMsg", CommonConstants.LOGO_MSG[5]);

		return "instalmentManage/cashier";
	}

	

	/**** 收银台画面关闭浮层后的ajax请求刷新请求订单号 ****/
	@RequestMapping("/refreshPaymentOrderNo.jhtml")
	@ResponseBody
	public Map<String,Object> refreshPaymentOrderNo(HttpServletRequest request) throws Exception {
		String paymentInfoToken = request.getParameter("paymentInfoToken");
		log.info("收银台画面刷新订单号refreshPaymentOrderNo，paymentInfoToken="+paymentInfoToken);
		Map<String,Object> retMap=new HashMap<String,Object>();
		
		//随机生成还款订单号
		String paymentOrderNo=GenerateKey.getId(CommonConstants.PAYMENT_ORDER_NO_PREFIX, ConfigUtils.getProperty("db_id"));

		//从缓存中获取还款的相关信息
		Map<String, Object> sessionPayMap = UserUtils.getPaymentOrderToPayFromCache(getRequest().getSession());
		if( sessionPayMap==null||sessionPayMap.get(paymentInfoToken)==null){
			log.error("session缓存中没有key为"+paymentInfoToken+"的还款相关信息");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "系统错误，请进入账单中心重新选择支付！");
			return retMap;
			
		}else{
			Map<String,Object> payInfoMap=(Map<String, Object>) sessionPayMap.get(paymentInfoToken);
			//将还款单号存入session
			payInfoMap.put("paymentOrderNo", paymentOrderNo);
			
			//返回画面信息
			retMap.put("paymentOrderNo", paymentOrderNo);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, "系统错误，请进入账单中心重新选择支付！");
			return retMap;
		}

	}
	
	/**** 确定选择银行，准备跳转 ****/
	@RequestMapping("/surePayBank.jhtml")
	@ResponseBody
	public Map<String, Object> surePayBank() throws Exception {
		log.info("跳转至第三方支付画面前的最终校验");
		Map<String, Object> retMap = new HashMap<String, Object>();

		String relationBankId = getRequest().getParameter("relationBankId");
		String paymentInfoToken = getRequest().getParameter("paymentInfoToken");

		// 从缓存中获取订单
		Map<String, Object> sessionPayMap = UserUtils.getPaymentOrderToPayFromCache(getRequest().getSession());
		
		if(sessionPayMap==null||sessionPayMap.get(paymentInfoToken)==null){
			log.error("缓存中找不到对应的还款相关信息，key：" + paymentInfoToken);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return retMap;
		}
		
		Map<String,Object> payInfoMap=(Map<String, Object>) sessionPayMap.get(paymentInfoToken);

		try {
			// 调用服务层，返回跳转中金支付画面的信息
			retMap = instalmentManageService.surePayBank(relationBankId, payInfoMap, getUserInfo());
		} catch (Exception e) {
			log.error("跳转至第三方支付画面前的最终校验，发生错误：" , e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return retMap;
		}
		return retMap;
	}

	/**** 验证还款是否成功 ****/
	@RequestMapping("/validataPaymentResult.jhtml")
	@ResponseBody
	public Map<String, Object> validataPaymentResult() throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();

		//调用服务层，根据还款订单id查询还款订单是否还清
		boolean ret=false;
		try {
			retMap = instalmentInfoService.validataPaymentResult(getRequest().getParameter("paymentOrderId"),getUserInfo().getUserId());
		} catch (Exception e) {
			log.error("跳转至第三方支付画面前的最终校验，发生错误：" , e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "系统发生错误！");
			return retMap;
		}
		
		if(!retMap.get(ResponseConstants.RETURN_CODE).equals(ResponseConstants.SUCCESS)){
			log.info("服务层返回调用结果code不为成功，code："+retMap.get(ResponseConstants.RETURN_CODE)+",desc:"+retMap.get(ResponseConstants.RETURN_DESC));
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);//查询成功 ，支付失败
			retMap.put("payFlag", ret);
			return retMap;
		}
		
		ret=true;
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put("payFlag", ret);
		return retMap;
	}

	/**
	 * queryPaymentOrderAjax(分期账单--已还款信息Ajax)
	 * 
	 * @return Map<String,Object>
	 * @create time： Sep 18, 2015 3:01:01 PM
	 * @author：张海达
	 * @since 1.0.0
	 */
	@RequestMapping("/queryPaymentOrderAjax.jhtml")
	@ResponseBody
	public Map<String, Object> queryPaymentOrderAjax(HttpServletRequest request) {
		log.info("查询已还款信息");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();

		UserInfo userInfo = getUserInfo();
		queryMap.put("userId", userInfo.getUserId());
		
		//查询还款支付订单状态为支付成功和重复付款
		queryMap.put("paymentStatus", new int[]{StatusConstants.PAYMENT_ORDER_STATUS_PAY_AGAIN,StatusConstants.PAYMENT_ORDER_STATUS_PAY_SUCCESS});
		// 查询总数
		int totalNum = instalmentInfoService.queryUserPaymentOrderTotalVO(queryMap);

		// 如需分页，必须调用该方法
		initPV(totalNum);

		queryMap.put("pageStart", pv.getPageStart() * pv.getPageSize() - pv.getPageSize()); // 每一页开始的行数
		queryMap.put("pageSize", pv.getPageSize());// 每个分页数据记录数

		//查询上述信息，查询已经成功还款的还款订单信息
		List<UserPaymentOrderDTO> userPaymentOrderlist;
		try {
			userPaymentOrderlist = instalmentInfoService.queryUserPaymentOrderList(queryMap);
			if (userPaymentOrderlist != null && userPaymentOrderlist.size() > 0) {
				for (UserPaymentOrderDTO vo : userPaymentOrderlist) {
					if(EntityUtils.isNotEmpty(vo.getPaymentStatus())){
						
						if ( vo.getPaymentStatus() == StatusConstants.PAYMENT_ORDER_STATUS_PAY_AGAIN.intValue()) {
							vo.setPaymentStatusC("重复付款");
						} else if ( vo.getPaymentStatus() == StatusConstants.PAYMENT_ORDER_STATUS_PAY_SUCCESS.intValue()) {
							vo.setPaymentStatusC("已支付");
						}
					}
					String paymentTimeStr = !"".equals(DateUtil.formatDate(vo.getPaymentTime(), "yyyy-MM-dd HH:mm:ss"))
							&& DateUtil.formatDate(vo.getPaymentTime(), "yyyy-MM-dd HH:mm:ss") != null
									? DateUtil.formatDate(vo.getPaymentTime(), "yyyy-MM-dd HH:mm:ss") : "";
					vo.setPaymentTimeStr(paymentTimeStr);
					
					if (EntityUtils.isNotEmpty(vo.getPaymentChannelId())) {
						if (CommonConstants.PAYMENT_CHANNEL_ID_CPCN.equals(vo.getPaymentChannelId().toUpperCase())) {
							vo.setPaymentChannelId("网银支付");
						}
					}
					if (CommonConstants.PAYMENT_CHANNEL_ID_WX.equals(vo.getPaymentChannelId())) {
						vo.setPaymentChannelId("微信支付");
					}
				}
			}
		} catch (Exception e) {
			log.error("查询已还款信息，发生错误：" , e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return retMap;
		}
		//返回画面的信息
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "查询成功！");
		retMap.put("list", userPaymentOrderlist);
		retMap.put("totalNum", totalNum);
		retMap.put("totalPage", pv.getTotalPage());

		return retMap;
	}

	/**
	 * queryPaymentOrderDetail(查询还款订单明细)
	 * 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 *             String
	 * @create time： Sep 24, 2015 12:55:51 PM
	 * @author：张海达
	 * @since 1.0.0
	 */
	@RequestMapping("/queryPaymentOrderDetail.jhtml")
	public String queryPaymentOrderDetail(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("进入分期账单已还款订单明细画面");
		UserInfo userInfo = getUserInfo();

		String userPaymentOrderId = request.getParameter("userPaymentOrderId");
		UserPaymentOrderDTO userPaymentOrderDTO = new UserPaymentOrderDTO();
		userPaymentOrderDTO.setUserPaymentOrderId(userPaymentOrderId);
		userPaymentOrderDTO.setUserId(userInfo.getUserId());
		
		//查询还款订单
		UserPaymentOrderDTO userPaymentOrderDTOInfo = instalmentInfoService.queryUserPaymentOrderInfo(userPaymentOrderDTO);
		userPaymentOrderDTOInfo.setUserPaymentOrderId(userPaymentOrderId);
		userPaymentOrderDTOInfo.setUserId(userInfo.getUserId());
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userId", userInfo.getUserId());
		queryMap.put("userPaymentOrderId", request.getParameter("userPaymentOrderId"));
		
		//查询还款订单的详细信息
		List<UserPaymentOrderDetailDTO> list = instalmentInfoService.queryUserPaymentOrderDetailList(queryMap);
		
		//返回画面信息
		map.put("userPaymentOrderDTO", userPaymentOrderDTOInfo);
		map.put("list", list);
		map.put("userInfo", userInfo);
		map.put("logoMsg", CommonConstants.LOGO_MSG[1]);
		String partnerOrderListUrl = ConfigUtils.getProperty("mqcash_partner_orderlist_url");
		map.put("partnerOrderListUrl", partnerOrderListUrl);
		return "instalmentManage/user_bill_detail";
	}

	/**
	 * 微信支付生成订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sureWxPay.jhtml")
	@ResponseBody
	public Map<String, Object> sureWxPay() throws Exception {
		log.info("跳转至微信扫码支付的最终校验");
		Map<String, Object> retMap = new HashMap<String, Object>();

		String paymentInfoToken = getRequest().getParameter("paymentInfoToken");

		// 从缓存中获取订单
		Map<String, Object> sessionPayMap = UserUtils.getPaymentOrderToPayFromCache(getRequest().getSession());
		
		if(sessionPayMap==null||sessionPayMap.get(paymentInfoToken)==null){
			log.error("缓存中找不到对应的还款相关信息，key：" + paymentInfoToken);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return retMap;
		}
		
		Map<String,Object> payInfoMap=(Map<String, Object>) sessionPayMap.get(paymentInfoToken);

		try {
			// 调用服务层，返回跳转微信二维码的信息
			retMap = instalmentManageService.sureWxPay(payInfoMap, getUserInfo());
			String redirectUrl = "wxPay/nativePay.jhtml" ;
			retMap.put("redirectUrl", redirectUrl);
		} catch (Exception e) {
			log.error("跳转至微信扫码支付的最终校验，发生错误：" , e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return retMap;
		}
		return retMap;
	}
}
