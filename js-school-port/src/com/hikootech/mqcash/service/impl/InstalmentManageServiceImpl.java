package com.hikootech.mqcash.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dto.BankDTO;
import com.hikootech.mqcash.dto.InstalmentInfoDTO;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.po.UserInstalment;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.po.UserPaymentRecordItem;
import com.hikootech.mqcash.po.UserRepaymentPlans;
import com.hikootech.mqcash.service.ConfigConstantsService;
import com.hikootech.mqcash.service.InstalmentManageService;
import com.hikootech.mqcash.service.UserInstalmentService;
import com.hikootech.mqcash.service.UserPayOrderBySelfService;
import com.hikootech.mqcash.service.UserPaymentOrderService;
import com.hikootech.mqcash.service.UserRepayPlanService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.InstalmentUtils;
import com.hikootech.mqcash.util.OrderIdRuleUtil;
import com.hikootech.mqcash.vo.UserRepaymentPlansVO;

import payment.api.system.PaymentEnvironment;

@Service("instalmentManageService")
public class InstalmentManageServiceImpl implements InstalmentManageService {

	private static Logger log=LoggerFactory.getLogger(InstalmentManageServiceImpl.class);

	@Autowired
	private UserRepayPlanService userRepayPlanService;
	@Autowired
	private ConfigConstantsService configConstantsService;
	@Autowired
	private UserPayOrderBySelfService userPayOrderBySelfService;
	@Autowired
	private UserPaymentOrderService userPaymentOrderService;
	@Autowired
	private UserInstalmentService userInstalmentService;
	
	@Override
	public Map<String, Object> queryPlansInfo(String instalmentId,String userId,int[] planStatusArray) {

		Map<String, Object> retMap=new HashMap<String,Object>();
		
		//根据进账单id查询账单信息
		Map<String, Object> queryInstalmentMap=new HashMap<String,Object>();
		queryInstalmentMap.put("instalmentId", instalmentId);
		queryInstalmentMap.put("userId", userId);
		UserInstalment userInstalment = userInstalmentService.queryInstalmentById(queryInstalmentMap);

		// 当前账单费率
		BigDecimal overDueRate = userInstalment.getOverdueRate();

		//根据前台传值查询该账单下所有的分期计划
		Map<String,Object> queryPlansMap=new HashMap<String,Object>();
		queryPlansMap.put("userId", userId);
		queryPlansMap.put("instalmentId", instalmentId);
		List<UserRepaymentPlans> list = userRepayPlanService.queryRepayPlansByInstalmentId(queryPlansMap);
		List<UserRepaymentPlansVO> retList=new ArrayList<UserRepaymentPlansVO>();
		UserRepaymentPlans planCur = null;

		// 已还清的计划数量
		int a = 0;
		boolean flag = true;
		BigDecimal lastAmount=new BigDecimal(0); //剩余还款金额
		for (UserRepaymentPlans userRepaymentPlans : list) {
			 
			//逾期天数默认全部为0
			int overDueDays=0;
			
			// 1.判断当期
			// 循环到某个计划还款日期与今天的差值为0或者大于0时，可判断为当期,即可跳出
			int days = DateUtil.daysBetween(DateUtil.getCurDate(), userRepaymentPlans.getPlanRepaymentTime(), false);
			if (flag) {
				if (days >= 0) {
					planCur = userRepaymentPlans;
					flag = false;
				}
			}
			 BigDecimal normalAmount=InstalmentUtils.calcPayAmount(userRepaymentPlans, false);//计算正常的总还款金额
			 BigDecimal overDueAmount=new BigDecimal(0);
			 //判断是否逾期或者已还清
			if (userRepaymentPlans.getPlansStatus() == StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_INIT.intValue()
					|| userRepaymentPlans.getPlansStatus() == StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY .intValue()
					|| userRepaymentPlans.getPlansStatus() == StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY .intValue()
					|| userRepaymentPlans.getPlansStatus() == StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE .intValue()) {
				
				
				    //？？？？-》（还款中状态是否判断逾期：倩倩说目前不算逾期）
					if(userRepaymentPlans.getPlansStatus() != StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY .intValue()){
						
						// 未还清且还款日比今天小时，证明已逾期
						if(days<0){
							userRepaymentPlans.setPlansStatus( StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE );
							overDueAmount=InstalmentUtils
									.calOverdue(userInstalment.getPartnerOrderAmount(), overDueRate, Math.abs(days));
							userRepaymentPlans.setReceivableOverdue(overDueAmount);
							overDueDays=Math.abs(days);
						}
					}
					
					//将计算的还款总金额赋予当前计划
					userRepaymentPlans.setReceivableAmount(normalAmount.add(overDueAmount).subtract(userRepaymentPlans.getReduceOverdue()));
					lastAmount=lastAmount.add(userRepaymentPlans.getReceivableAmount());
			}else{
				a++; //除了需要还款的，其他可以认为已还清，+1
				userRepaymentPlans.setReceivableAmount(normalAmount);
			}
			
			
			//将所有计划属性和逾期天数赋予vo对象,用于页面展示
			UserRepaymentPlansVO planVO=new UserRepaymentPlansVO(userRepaymentPlans, overDueDays);
			
			//如果状态参数数组不为空，且当前计划状态不在数组范围内，则剔除出去
			if(planStatusArray!=null){
				 boolean addFlag=false;
				for (int i = 0; i < planStatusArray.length; i++) {
					if(userRepaymentPlans.getPlansStatus() == planStatusArray[i]){
						addFlag=true;
						break;
					}
				}
				if(addFlag){
					retList.add(planVO);
				}
			}else{
				retList.add(planVO);
			}
		}
		
		//如果该分期单不需要再还款，则剩余期数应该为0
		if(userInstalment.getInstalmentStatus()==StatusConstants.INSTALMENT_STATUS_COMPLETED.intValue()
				||userInstalment.getInstalmentStatus()==StatusConstants.INSTALMENT_STATUS_CANCELED.intValue()
				||userInstalment.getInstalmentStatus()==StatusConstants.INSTALMENT_STATUS_SUSPENDED.intValue()
				||userInstalment.getInstalmentStatus()==StatusConstants.INSTALMENT_STATUS_AWAIT_REFUND.intValue()
				){
			if(list.size() - a!=0){
				log.error("该分期单不需要再还款，但剩余期数不为0，分期单号："+userInstalment.getInstalmentId());
			}
		}
		
		
		//画面底部信息
		InstalmentInfoDTO instalmentInfo = new InstalmentInfoDTO();
		instalmentInfo.setLastAmount(lastAmount.setScale(2).toString());
		instalmentInfo.setLatPeriodNum(String.valueOf(list.size() - a));
		
		//返回信息
		retMap.put("list", retList);
		retMap.put("bottomInfo", instalmentInfo);
		retMap.put("planCur", planCur);

		return retMap;
	}
	@Override
	public Map<String, Object> payNow(String totalAmount,String payPlanIds,String instalmentId,UserInfo userInfo) {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();

		//转换总金额
		try {
			Double.parseDouble(totalAmount);
		} catch (NumberFormatException e) {
			log.error("转换浮点值时发生错误，前台画面传递需要还款为：" + totalAmount + ",");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL_PAYNOW);
			retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return retMap;
		}

		//前台支付总金额
		BigDecimal totalAmountPay = new BigDecimal(totalAmount);

		//根据传递的用户id和分期单id查询当前分期单下的所有计划(顺序排列)
//		queryMap.put("payPlanIds", payPlanIds.split(","));
// 		queryMap.put("planStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY);
		queryMap.put("userId", userInfo.getUserId());
		queryMap.put("instalmentId", instalmentId);
		 
		
		List<UserRepaymentPlans> repayPlanList = userRepayPlanService.queryRepayPlansByInstalmentId(queryMap);
//		List<UserRepaymentPlans> listTemp2=new ArrayList<UserRepaymentPlans>();
		
		//先进行对所有的选中ids的数量判断，获取到ids中的最小期数与最大期数
		int samllNum =0;
		int bigNum =0;
		BigDecimal totalAmountDb=new BigDecimal(0);
		for (int i=0;i<repayPlanList.size();i++) {
			UserRepaymentPlans userRepaymentPlans=repayPlanList.get(i);
			//只查找待还款的或者是已逾期的,但查到的计划id在前台传值中必须存在
			if((userRepaymentPlans.getPlansStatus()==StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY.intValue()
					||userRepaymentPlans.getPlansStatus()==StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE.intValue()
					)
					&&payPlanIds.indexOf(userRepaymentPlans.getRepaymentPlansId())!=-1
					){
				if(samllNum==0){
					samllNum=i+1;
				}
				bigNum=i+1;
				
				//计算当前选中的需要还款的总金额
				totalAmountDb=totalAmountDb.add(userRepaymentPlans.getReceivableAmount());
			}
		}
		
		// 选择期数 进行比对,若第一个和最后一个相差的值和查询所得的数量不一致,将报错信息返回
		if((bigNum-samllNum+1)!=payPlanIds.split(",").length&&bigNum>0){
			log.error(" 选择的repayplanids(" + payPlanIds + ")与数据库结果筛选后的数量不一致，数据库的bigNum-->>" 
						+ bigNum + ",samllNum-->>" + samllNum );
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL_PAYNOW);
			retMap.put(ResponseConstants.RETURN_DESC, "请从第一个未还的开始，由上往下依次选择，不能跳过!");
			return retMap;
		}
		 

		//前后台不一致，返回错误信息
		if(totalAmountDb.compareTo(totalAmountPay) != 0){
			log.error("页面的还款金额结果与后台计算结果不一致，数据库计算还款为：" + totalAmountDb + "," + "页面计算结果为：" + totalAmountPay);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL_PAYNOW);
			retMap.put(ResponseConstants.RETURN_DESC, "您的还款金额与后台计算结果不一致，请重新操作！");
			return retMap;
			
		}
		
		// 若金额一致且都为0或小于0，则无需还款
		if (totalAmountDb.compareTo(new BigDecimal(0)) <= 0) {
			log.error("页面的还款金额结果与后台计算结果一致，但是该值小于等于0无需还款，数据库计算还款为：" + totalAmountDb + "," + "页面计算结果为："
					+ totalAmountPay);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL_PAYNOW);
			retMap.put(ResponseConstants.RETURN_DESC, "您当前选中的还款金额为0，无需还款！");
			return retMap;
		} else {
			log.info("页面的还款金额结果与后台计算结果一致,准备跳转,数据库计算还款为：" + totalAmountDb + "," + "页面计算结果为：" + totalAmountPay);
			
			//通过校验，正常返回
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, "您的还款金额与后台计算结果一致，请选择银行！");
			return retMap;
	}

}
	
	@Override
	public Map<String, Object> surePayBank(String relationBankId,Map<String,Object> payInfoMap,UserInfo userInfo) {
		 
		Map<String, Object> retMap=new HashMap<String,Object>();
		
		// 查询relationBankId
		// 获取银行信息
		BankDTO bankDTO = configConstantsService.getBankById(relationBankId);
		if (EntityUtils.isEmpty(bankDTO) || EntityUtils.isEmpty(bankDTO.getPaymentStatus())) {
			log.error("请求第三方用用户银行卡网银支付失败，找不到对应的银行，relationBankId：" + relationBankId);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return retMap;
		}

		String[] payPlanIds=((String)payInfoMap.get("payPlanIds")).split(",");
		
		//生成还款订单
		UserPaymentOrder paymentOrder = this.createPaymentOrder(payInfoMap, userInfo);
		paymentOrder.setPaymentCount(payPlanIds.length);
		paymentOrder.setBankId(bankDTO.getThirdPartyBankId());
		paymentOrder.setBankName(bankDTO.getBankName());
		paymentOrder.setPaymentChannelId(CommonConstants.PAYMENT_CHANNEL_ID_CPCN);
		paymentOrder.setPaymentType(CommonConstants.PAYMENT_TYPE_ACTIVE);// 主动支付
		
		//生成子项表
		List<UserPaymentRecordItem> itemList = new ArrayList<UserPaymentRecordItem>();
		
		for (int i = 0; i < payPlanIds.length; i++) {
			
			Map<String,Object> queryMapItem=new HashMap<String,Object>();
			queryMapItem.put("planId", payPlanIds[i]);
			queryMapItem.put("userId", userInfo.getUserId());
			
			UserRepaymentPlans repayPlan = userRepayPlanService.queryRepayPlanByKey(queryMapItem);
			
			// 如果计划状态不是待还款和已逾期， 则不在允许提交
 			if (repayPlan.getPlansStatus() !=StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY.intValue()
					&&repayPlan.getPlansStatus() !=StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE.intValue()
 					) {
				log.error("该还款计划目前不允许还款,还款计划id为:" + repayPlan.getRepaymentPlansId() +",计划状态为："+repayPlan.getPlansStatus());
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "该支付单已过期，请进入账单中心重新选择支付！");
				return retMap;
			}
			
 			// 创建支付记录子项
 			UserPaymentRecordItem userPaymentRecordItem = this.createUserPaymentRecordItem(paymentOrder,repayPlan);
 			
			itemList.add(userPaymentRecordItem);
		}
		
		try {
			//调用直通车组装请求信息
			userPayOrderBySelfService.UserRequestPay(paymentOrder, retMap);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("向中金发起还款请求发生错误：" + e1.getMessage());
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return retMap;
		}	
		
		// 跳转支付页面前，先将该次请求记录数据库中
		try {
			userPaymentOrderService.savePaymentOrder(paymentOrder, itemList,userInfo.getUserId());
			retMap.put("redirectUrl", PaymentEnvironment.paymentURL);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, " 请进行支付！");
			retMap.put("paymentOrderId", paymentOrder.getUserPaymentOrderId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("将本次主动付款请求存入数据库中时发生错误");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return retMap;
		}
		
		return retMap;
	}
	
	@Override
	public Map<String, Object> sureWxPay(Map<String, Object> payInfoMap, UserInfo userInfo) {

		Map<String, Object> retMap = new HashMap<String, Object>();

		String[] payPlanIds = ((String) payInfoMap.get("payPlanIds")).split(",");

		// 生成还款订单
		UserPaymentOrder paymentOrder = this.createPaymentOrder(payInfoMap, userInfo);
		paymentOrder.setPaymentCount(payPlanIds.length);

		paymentOrder.setPaymentChannelId(CommonConstants.PAYMENT_CHANNEL_ID_WX);
		paymentOrder.setPaymentType(CommonConstants.PAYMENT_TYPE_WX_NATIVE);// 扫码支付

		// 生成子项表
		List<UserPaymentRecordItem> itemList = new ArrayList<UserPaymentRecordItem>();

		for (int i = 0; i < payPlanIds.length; i++) {

			Map<String, Object> queryMapItem = new HashMap<String, Object>();
			queryMapItem.put("planId", payPlanIds[i]);
			queryMapItem.put("userId", userInfo.getUserId());

			UserRepaymentPlans repayPlan = userRepayPlanService.queryRepayPlanByKey(queryMapItem);

			// 如果计划状态不是待还款和已逾期， 则不在允许提交
			if (repayPlan.getPlansStatus() != StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY.intValue()
					&& repayPlan.getPlansStatus() != StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE
							.intValue()) {
				log.error("该还款计划目前不允许还款,还款计划id为:" + repayPlan.getRepaymentPlansId() + ",计划状态为："
						+ repayPlan.getPlansStatus());
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "该支付单已过期，请进入账单中心重新选择支付！");
				return retMap;
			}

			// 创建支付记录子项
			UserPaymentRecordItem userPaymentRecordItem = this.createUserPaymentRecordItem(paymentOrder, repayPlan);

			itemList.add(userPaymentRecordItem);
		}

		// 跳转支付页面前，先将该次请求记录数据库中
		try {
			userPaymentOrderService.savePaymentOrder(paymentOrder, itemList, userInfo.getUserId());
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, " 请进行支付！");
			retMap.put("paymentOrderId", paymentOrder.getUserPaymentOrderId());
		} catch (Exception e) {
			log.error("将本次主动付款请求存入数据库中时发生错误",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return retMap;
		}
		
		return retMap;
	}
	
	/**
	 * 创建用户订单信息
	 * 
	 * @param payInfoMap
	 * @param userInfo
	 * @return
	 */
	private UserPaymentOrder createPaymentOrder(Map<String, Object> payInfoMap, UserInfo userInfo) {
		UserPaymentOrder paymentOrder = new UserPaymentOrder();
		String paymentChannelId = ConfigUtils.getProperty("payment.orderid.channelid");
		paymentOrder.setUserPaymentOrderId(OrderIdRuleUtil.getId(paymentChannelId ));// 主键
		paymentOrder.setUserId(userInfo.getUserId());
		paymentOrder.setPaymentOrderNo((String)payInfoMap.get("paymentOrderNo"));// 市场订单号
		paymentOrder.setPaymentAmount(new BigDecimal((String)payInfoMap.get("totalAmount")));// 支付金额
		paymentOrder.setPaymentStatus(StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY);
		paymentOrder.setRequestStatus(StatusConstants.REQUEST_STATUS_AWAIT);
		paymentOrder.setPaymentFee(new BigDecimal(0));
		paymentOrder.setCreateTime(new Date());
		
		return paymentOrder;
	}
	
	/**
	 * 创建订单子表项信息
	 * 
	 * @param paymentOrder
	 * @param repayPlan
	 * @return
	 */
	private UserPaymentRecordItem createUserPaymentRecordItem(UserPaymentOrder paymentOrder,
			UserRepaymentPlans repayPlan) {
		UserPaymentRecordItem userPaymentRecordItem = new UserPaymentRecordItem();
		
		userPaymentRecordItem.setUserPaymentOrderId(paymentOrder.getUserPaymentOrderId()); // 用户主动和被动支付订单表id
		userPaymentRecordItem.setUpRecordItemId(GenerateKey
				.getId(CommonConstants.PAYMENT_RECORD_ITEM_ID_PREFIX, ConfigUtils.getProperty("db_id")));// 还款记录项id//
																											// 还款记录项id
		userPaymentRecordItem.setRepaymentPlansId(repayPlan.getRepaymentPlansId()); // 还款计划id
		userPaymentRecordItem.setInstalmentId(repayPlan.getInstalmentId());
		userPaymentRecordItem.setPaymentStatus(StatusConstants.PAYMENT_ORDER_ITEM_PAYSTATUS_AWAIT_PAY); //支付状态
		userPaymentRecordItem.setNeedPaymentAmount(repayPlan.getReceivableAmount());// 记录项对应应还金额（可能此记录项对应的是部分还款）
		userPaymentRecordItem.setRealPaymentAmount(userPaymentRecordItem.getNeedPaymentAmount());// 实收金额（可能此记录项对应的是部分还款）通知成功后再修改该值
		userPaymentRecordItem.setRepaymentType(StatusConstants.PAYMENT_TYPE_PAYALL); // 还款类型：0全部还款、1部分还款.(通过实际支付金额与应支付金额判断)
		userPaymentRecordItem.setPaymentTime(null);
		userPaymentRecordItem.setCreateTime(new Date());
		userPaymentRecordItem.setUpdateTime(new Date());
		userPaymentRecordItem.setOperator(CommonConstants.DEFAULT_OPERATOR);
		
		return userPaymentRecordItem;
	}
}
