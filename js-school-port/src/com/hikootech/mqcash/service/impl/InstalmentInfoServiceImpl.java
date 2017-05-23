package com.hikootech.mqcash.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.hikootech.mqcash.dao.InstalmentInfoDAO;
import com.hikootech.mqcash.dto.EveryDayPaymentViewDTO;
import com.hikootech.mqcash.dto.InstalmentInfoDTO;
import com.hikootech.mqcash.dto.TopViewDTO;
import com.hikootech.mqcash.dto.UserPaymentOrderDTO;
import com.hikootech.mqcash.dto.UserPaymentOrderDetailDTO;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.po.UserRepaymentPlans;
import com.hikootech.mqcash.service.InstalmentInfoService;
import com.hikootech.mqcash.service.UserPayOrderBySelfService;
import com.hikootech.mqcash.service.UserPaymentOrderService;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.InstalmentUtils;

@Service("instalmentInfoService")
public class InstalmentInfoServiceImpl implements InstalmentInfoService {

	private static Logger log = LoggerFactory.getLogger(InstalmentInfoServiceImpl.class);
	@Autowired
	private InstalmentInfoDAO instalmentInfoDAO;
	@Autowired
	private UserPaymentOrderService userPaymentOrderService;
	@Autowired
	private UserPayOrderBySelfService userPayOrderBySelfService;

	@Override
	public InstalmentInfoDTO queryInstalmentAndProductInfoByUserId(Map<String, Object> queryMap) {

		try {
			return instalmentInfoDAO.queryInstalmentAndProductInfoByUserId(queryMap);
		} catch (Exception e) {
			log.error("根据限定条件查询所有符合条件的账单失败", e);
			throw new RuntimeException("根据限定条件查询所有符合条件的账单失败", e);
		}

	}

	@Override
	public List<InstalmentInfoDTO> getInstalmentlistByUserId(Map<String, Object> queryMap) {

		try {
			return instalmentInfoDAO.getInstalmentlistByUserId(queryMap);
		} catch (Exception e) {
			log.error("根据限定条件查询近期符合条件的账单( 获取顶部标线用 )失败", e);
			throw new RuntimeException("根据限定条件查询近期符合条件的账单( 获取顶部标线用 )失败", e);
		}
	}

	/**
	 * 根据getInstalmentlistByUserId查询得到的volist，将对每一个vo对象拼接成为完整的InstalmentInfoDTO
	 * --按照自然月计算
	 * 
	 * @param list
	 *            根据getInstalmentlistByUserId查询得到的volist
	 * @param instalmentStatus
	 *            账单状态 （目前是用来区分 全部画面:Integer.Max_value 待还款画面:0）
	 * @return listToShow 拼接完毕且筛选状态完毕的vo集合
	 */
	@Override
	public List<InstalmentInfoDTO> makeInstalmentInfoList(List<InstalmentInfoDTO> list, Integer instalmentStatus) {
		List<InstalmentInfoDTO> listToShow = new ArrayList<InstalmentInfoDTO>();
		for (InstalmentInfoDTO instalmentInfoDTO : list) {
			if (instalmentInfoDTO == null) {
				continue;
			}
			// 在待还款画面 ，只显示分期单状态为初始化、待还款、已逾期的未结清账单
			if (instalmentStatus != Integer.MAX_VALUE) {
				if (instalmentInfoDTO.getInstalmentIdStatus() != StatusConstants.INSTALMENT_STATUS_INIT.intValue()
						&& instalmentInfoDTO.getInstalmentIdStatus() != StatusConstants.INSTALMENT_STATUS_AWAIT_PAY
								.intValue()
						&& instalmentInfoDTO.getInstalmentIdStatus() != StatusConstants.INSTALMENT_STATUS_OVERDUE
								.intValue()) {
					continue;
				}
			}

			// *******************************将目前超过一个月的计划处理掉
			// start****************************
			List<UserRepaymentPlans> listPlans = new ArrayList<UserRepaymentPlans>();
			// 对获取的全部账单计划进行处理(账单列表是顺序排列的)
			for (int j = 0; j < instalmentInfoDTO.getUserPlanList().size(); j++) {
				UserRepaymentPlans plan = instalmentInfoDTO.getUserPlanList().get(j);

				if (plan == null) {
					continue;
				}

				// 如果是第一期，则不做过滤
				if (plan.getInstalmentNumber() == 1) {
					listPlans.add(plan);
				}
				// 如果还款时间==1个月+当前时间，即当天为当期还款日（第一期不算），不显示下一期的还款，跳出
				else if (DateUtil.daysBetween(DateUtil.addDate(DateUtil.getCurDate(), Calendar.MONTH, 1),
						plan.getPlanRepaymentTime(), false) == 0) {

					break;
				}
				// 如果还款时间大于当前时间+1个月，则将其过滤掉
				else if (DateUtil.daysBetween(DateUtil.addDate(DateUtil.getCurDate(), Calendar.MONTH, 1),
						plan.getPlanRepaymentTime(), false) > 0) {

				} else {
					listPlans.add(plan);
				}
			}

			// 将处理完毕后的计划赋予账单vo对象，进行下一步
			instalmentInfoDTO.setUserPlanList(listPlans);
			// *******************************将目前无效的计划处理掉
			// end****************************
			// *******************************针对页面展现值进行处理
			// start****************************
			BigDecimal repaymentAmount = InstalmentUtils.calcPayAmount(instalmentInfoDTO.getUserPlanList(), false); // 只计算本金+手续费，下面会计算罚息

			// 当前分期单当前的应还本金金额
			BigDecimal repaymentPrincipal = InstalmentUtils.calcPayPrincipalAmount(instalmentInfoDTO.getUserPlanList());
			instalmentInfoDTO.setRepaymentPrincipal(String.valueOf(repaymentPrincipal));

			// 当前分期单当前的应还手续费金额
			BigDecimal repaymentService = InstalmentUtils.calcPayServiceAmount(instalmentInfoDTO.getUserPlanList());
			instalmentInfoDTO.setRepaymentService(String.valueOf(repaymentService));

			Integer instalmentNum = 0;
			Integer lastDay = 0; 
			Date repayDay = new Date();
			String repaymentPlanId = "";
			Integer planState = 0; // 分期单综合状态
			int allOverDueDay = 0; // 逾期总天数
			boolean overDueFlag = false;
			BigDecimal repaymentReduce=new BigDecimal(0);

			// 若分期单已经不需要还款了，则直接获取分期单状态
			if (instalmentInfoDTO.getInstalmentIdStatus() != StatusConstants.INSTALMENT_STATUS_INIT.intValue()
					&& instalmentInfoDTO.getInstalmentIdStatus() != StatusConstants.INSTALMENT_STATUS_AWAIT_PAY
							.intValue()
					&& instalmentInfoDTO.getInstalmentIdStatus() != StatusConstants.INSTALMENT_STATUS_OVERDUE
							.intValue()) {

				planState = instalmentInfoDTO.getInstalmentIdStatus().intValue();

				// 待退款的显示在最上面（序号1），其他的显示在最下面（序号4）
				if (instalmentInfoDTO.getInstalmentIdStatus() == StatusConstants.INSTALMENT_STATUS_AWAIT_REFUND
						.intValue()) {
					instalmentInfoDTO.setSort(CommonConstants.MQ_CASH_INSTALMENT_SORT_ONE);
				} else {

					// 已中止 已取消 已结清显示在最下面， 在后面的比较器中按照下单时间进行排序
					instalmentInfoDTO.setSort(CommonConstants.MQ_CASH_INSTALMENT_SORT_FOUR);
				}

			} else {
				// 需要还款分期单的则分为待还款和逾期状态
				// 循环判断从第一期到目前的这一期(循环的目的：需要计算其中有一期或者多期逾期的计划的总的逾期罚息)
				for (int i = 0; i < instalmentInfoDTO.getUserPlanList().size(); i++) {
					UserRepaymentPlans plan = instalmentInfoDTO.getUserPlanList().get(i);

					instalmentNum = plan.getInstalmentNumber();
					repayDay = plan.getPlanRepaymentTime();
					repaymentPlanId = plan.getRepaymentPlansId();
					planState = plan.getPlansStatus();

					// 该次循环计划的还款日-今日 >=0 未过期 ，<0则已经过期
					lastDay = DateUtil.daysBetween(new Date(), plan.getPlanRepaymentTime(), false);

					
					//综合状态设置为待还款 ，如果有逾期计划，则在循环完毕后判断更改为 已逾期状态
					planState = StatusConstants.INSTALMENT_STATUS_AWAIT_PAY.intValue();
					
					// 待还款（序号3）
					instalmentInfoDTO.setSort(CommonConstants.MQ_CASH_INSTALMENT_SORT_THREEE);

					// 判断当期计划已经不需要还款，还需在画面显示剩余lastDay天
					if (plan.getPlansStatus() != StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_INIT.intValue()
							&& plan.getPlansStatus() != StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY
									.intValue()
							&& plan.getPlansStatus() != StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY
									.intValue()
							&& plan.getPlansStatus() != StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE
									.intValue()) {
						 

					} else {
						
						// 当期计划还需要还款，只有两个情况，一个是待还款，另外一个就是已经逾期了,可根据lastDay判断
						//如果是逾期状态，则修改标志位，同时记录当前分期单总逾期天数                              ？？？？-》（还款中状态是否判断逾期：倩倩说目前不算逾期）
						if(plan.getPlansStatus() != StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY .intValue()){
							if (lastDay < 0) {
								overDueFlag = true;
								allOverDueDay += Math.abs(lastDay);
								repaymentReduce=repaymentReduce.add(plan.getReduceOverdue());
							}  
						}
					}
				}
			}
			
			instalmentInfoDTO.setInstalmentNum(instalmentNum);
			instalmentInfoDTO.setLastDay(lastDay);
			instalmentInfoDTO.setRepayDay(DateUtil.formatDate(repayDay, "yyyy-MM-dd"));
			instalmentInfoDTO.setLoanDate(DateUtil.formatDate(instalmentInfoDTO.getLoanDateForCompare(), "yyyy-MM-dd"));
			instalmentInfoDTO.setRepaymentPlanId(repaymentPlanId);
			
			// 如果通过循环判断出分期单有逾期计划时,修改综合状态为已逾期，显示序号为（2）
			if (overDueFlag) {
				
				instalmentInfoDTO.setPlanState(StatusConstants.INSTALMENT_STATUS_OVERDUE); // 更改账单状态
				instalmentInfoDTO.setSort(CommonConstants.MQ_CASH_INSTALMENT_SORT_TWO);
				
			} else {
				instalmentInfoDTO.setPlanState(planState);
			}
			
			//设置逾期总额
			instalmentInfoDTO.setAllOverDueAmount(InstalmentUtils.calOverdue(instalmentInfoDTO.getLoanPrincipalAmount(),
					instalmentInfoDTO.getOverdueRate(), allOverDueDay));
			
			//设置减免总额
			instalmentInfoDTO.setRepaymentReduce(repaymentReduce);
			
			//设置应还总额
			instalmentInfoDTO
					.setRepaymentAmount(String.valueOf(repaymentAmount.add(instalmentInfoDTO.getAllOverDueAmount()).subtract(repaymentReduce)));
			
			//加至显示列表
			listToShow.add(instalmentInfoDTO);

			// *******************************针对页面展现值进行处理
			// end****************************
		}
		return listToShow;
	}

	/**
	 * 根据getInstalmentlistByUserId查询得到的volist，将对每一个vo对象拼接成为完整的InstalmentInfoDTO
	 * --按照30天计算 待还款
	 * 
	 * @param list
	 *            根据getInstalmentlistByUserId查询得到的volist
	 * @return TopViewDTO 用于展现于画面的对象
	 */
	@Override
	public TopViewDTO makeTopViewInfoByDays(List<InstalmentInfoDTO> list) {

		BigDecimal allAmount = new BigDecimal(0); // 全部待付款
		BigDecimal amountOf30Day = new BigDecimal(0); // 30待付款
		TopViewDTO topView = new TopViewDTO();
		topView.setCurrentPayment("0.00");
		topView.setAllLastPayment("0.00");
		Map<String, EveryDayPaymentViewDTO> eveMap = new HashMap<String, EveryDayPaymentViewDTO>(); // 包含每日还款对象的map，key为MM-dd格式的日期
		for (InstalmentInfoDTO instalmentInfoDTO : list) {
			if (instalmentInfoDTO == null) {
				continue;
			}
			// 分期单状态必须是初始化，代还款，已逾期，否则跳出
			if (instalmentInfoDTO.getInstalmentIdStatus() != StatusConstants.INSTALMENT_STATUS_INIT.intValue()
					&& instalmentInfoDTO.getInstalmentIdStatus() != StatusConstants.INSTALMENT_STATUS_AWAIT_PAY
							.intValue()
					&& instalmentInfoDTO.getInstalmentIdStatus() != StatusConstants.INSTALMENT_STATUS_OVERDUE
							.intValue()) {
				continue;
			}

			// *******************************将目前超过30天的计划处理掉
			// start****************************
			// List<UserRepaymentPlans> listPlans=new
			// ArrayList<UserRepaymentPlans>();
			allAmount = allAmount.add(InstalmentUtils.calcPayAmount(instalmentInfoDTO.getUserPlanList(), false));// 计算每个待还款的总金额，不计算逾期，逾期计算在下面
			// 对获取的全部账单计划进行处理(账单列表是顺序排列的)
			for (int j = 0; j < instalmentInfoDTO.getUserPlanList().size(); j++) {
				UserRepaymentPlans plan = instalmentInfoDTO.getUserPlanList().get(j);
				if (plan == null) {
					continue;
				}

				// 若还款计划状态不是初始化、待还款、还款中、已逾期 ，则不纳入计算范围，跳过，继续下一个计划
				if (plan.getPlansStatus() == StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_INIT.intValue()
						|| plan.getPlansStatus() == StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY
								.intValue()
						|| plan.getPlansStatus() == StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY
								.intValue()
						|| plan.getPlansStatus() == StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE
								.intValue()) {

				} else {
					continue;
				}

				// 日期过滤
				int ret = DateUtil.daysBetween( new Date(),plan.getPlanRepaymentTime(), false);

				// 获取格式化的还款日期
				String dateKey = DateUtil.formatDate(plan.getPlanRepaymentTime(), "MM-dd");

				// ret>30 表示本期及以后的还款计划还未在30天之内 跳出
				if (ret > 30) {
					break;
				}

				// 30日内的待还款计划
				if (ret >= 0 && ret <= 30) {

					BigDecimal payTemp = InstalmentUtils.calcPayAmount(plan, false); // 待还款的计划，无需计算逾期罚息
					if (payTemp.compareTo(new BigDecimal(0)) <= 0) {
						continue;
					}
					amountOf30Day = amountOf30Day.add(payTemp);

					// 判断map中是否已含有以当期还款日为key的还款对象
					EveryDayPaymentViewDTO evo = eveMap.get(dateKey);
					if (evo == null || evo.getCountToPay() == 0) {
						evo = new EveryDayPaymentViewDTO();
						evo.setCountToPay(1);
						evo.setDayToPayment(payTemp);
						evo.setPayDay(DateUtil.formatDate(plan.getPlanRepaymentTime(), "dd"));
						evo.setPayMon(DateUtil.formatDate(plan.getPlanRepaymentTime(), "MM"));
						evo.setBetweenDays(ret);
					} else {
						evo.setCountToPay(evo.getCountToPay() + 1);
						evo.setDayToPayment(evo.getDayToPayment().add(payTemp));
					}
					eveMap.put(dateKey, evo);

				}

				// 若ret<0，则证明还款计划已经逾期， 如果计划已经逾期，将逾期金额加在今天
				else {

					BigDecimal payTemp = InstalmentUtils.calcPayAmount(plan, false); // 计算当前本金+手续费
					// 计算已经逾期金额
					BigDecimal currentOverDueAmount = InstalmentUtils.calOverdue(
							instalmentInfoDTO.getLoanPrincipalAmount(), instalmentInfoDTO.getOverdueRate(), -ret);

					// 加至当前计划
					payTemp=payTemp.add(currentOverDueAmount);

					// 同时将该逾期金额加至"全部待还款"和“近30天待付款”金额中
					allAmount=allAmount.add(currentOverDueAmount);//在上面已经计算了正常应还的总金额，此处加上逾期金额即可
					amountOf30Day = amountOf30Day.add(payTemp);   //将该计划的正常应还金额和逾期金额加至30天应还内
					
					if (payTemp.compareTo(new BigDecimal(0)) <= 0) {
						continue;
					}

					// 判断map中是否已含有以当日为key的还款对象,如没有则新建一个
					String key = DateUtil.getCurDateStr("MM-dd");
					EveryDayPaymentViewDTO evo = eveMap.get(key);
					if (evo == null || evo.getCountToPay() == 0) {
						evo = new EveryDayPaymentViewDTO();
						evo.setCountToPay(1);
						evo.setDayToPayment(payTemp);
						evo.setPayDay(DateUtil.getCurDateStr("dd"));
						evo.setPayMon(DateUtil.getCurDateStr("MM"));
						evo.setBetweenDays(0);
					} else {
						evo.setCountToPay(evo.getCountToPay() + 1);
						evo.setDayToPayment(evo.getDayToPayment().add(payTemp));
					}

					eveMap.put(key, evo);
				}
			}
		}

		// 给标线对象赋值
		topView.setCurrentPayment(amountOf30Day.setScale(2).toString());
		topView.setAllLastPayment(allAmount.setScale(2).toString());
		topView.setEveMap(eveMap);
		topView.setNowDate(DateUtil.getCurDateStr("yyyy/MM/dd"));
		return topView;
	}

	@Override
	public TopViewDTO getCurTopView(String userId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userId", userId);
		// 初始化还款值
		List<InstalmentInfoDTO> list = getInstalmentlistByUserId(queryMap);
		// 初始化还款值
		return makeTopViewInfoByDays(list);
	}

	@Override
	public List<UserPaymentOrderDTO> queryUserPaymentOrderList(Map<String, Object> queryMap) {
		try {
			return instalmentInfoDAO.queryUserPaymentOrderList(queryMap);
		} catch (Exception e) {
			log.error("查询已还款订单信息失败", e);
			throw new RuntimeException("查询已还款订单信息失败", e);
		}
	}

	@Override
	public List<UserPaymentOrderDetailDTO> queryUserPaymentOrderDetailList(Map<String, Object> queryMap) {
		try {
			return instalmentInfoDAO.queryUserPaymentOrderDetailList(queryMap);
		} catch (Exception e) {
			log.error("查询已还款订单详细信息失败", e);
			throw new RuntimeException("查询已还款订单详细信息失败", e);
		}
	}

	@Override
	public UserPaymentOrderDTO queryUserPaymentOrderInfo(UserPaymentOrderDTO userPaymentOrderDTO) {
		try {
			return instalmentInfoDAO.queryUserPaymentOrderInfo(userPaymentOrderDTO);
		} catch (Exception e) {
			log.error("查询单个还款信息失败", e);
			throw new RuntimeException("查询单个还款信息失败", e);
		}
	}

	@Override
	public int queryUserPaymentOrderTotalVO(Map<String, Object> queryMap) {
		try {
			return instalmentInfoDAO.queryUserPaymentOrderTotalVO(queryMap);
		} catch (Exception e) {
			log.error("查询已经还款的订单总数失败", e);
			throw new RuntimeException("查询已经还款的订单总数失败", e);
		}
	}

	@Override
	public Map<String,Object> validataPaymentResult(String paymentOrderId, String userId) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		// 先确认还款订单成功完成
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userId", userId);
		queryMap.put("paymentOrderId", paymentOrderId);
		UserPaymentOrder paymentOrder = userPaymentOrderService.queryPaymentOrderById(queryMap);

		if (paymentOrder == null) {
			log.error("没有查到到该笔还款支付订单，主键key："+paymentOrderId+",userid："+userId);
			 retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			 retMap.put(ResponseConstants.RETURN_DESC, "系统错误！");
			 return retMap;
		}

		// 若请求状态为待请求 则有可能中金接口还未返回通知，由核心系统调用中金1320查询
		if (paymentOrder.getRequestStatus() == StatusConstants.REQUEST_STATUS_AWAIT.intValue() ) {
			
			//调用核心系统进行主动支付查询 
			try {
				return userPayOrderBySelfService.queryPayOrderSelfResult(paymentOrderId);
			} catch (Exception e) {
				log.error("调用核心系统查询还款订单状态发生错误，主键key："+paymentOrderId+",userid："+userId,e);
				 retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				 retMap.put(ResponseConstants.RETURN_DESC, "系统错误！");
				 return retMap;
			}
		} else {
			// 请求失败时，可以直接判断未成功
			if (paymentOrder.getRequestStatus() == StatusConstants.REQUEST_STATUS_FAILED.intValue()) {
				log.info("数据库查询得知该还款支付订单请求失败，主键key："+paymentOrderId+",userid："+userId);
				 retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				 retMap.put(ResponseConstants.RETURN_DESC, "支付失败！");
				 return retMap;
			} else {
				
				//请求成功时，（主动付款的结果只会有成功、待支付，不会有处理中，和失败）
				if (paymentOrder.getPaymentStatus() == StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY.intValue()) {
					log.info("数据库查询得知该还款支付订单支付状态为待支付，主键key："+paymentOrderId+",userid："+userId);
					 retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
					 retMap.put(ResponseConstants.RETURN_DESC, "支付失败！");
					 return retMap;
				} else {
					log.info("数据库查询得知该还款支付订单支付状态为支付成功，主键key："+paymentOrderId+",userid："+userId);
					 retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
					 retMap.put(ResponseConstants.RETURN_DESC, "支付成功！");
					 return retMap;
				}
			}
		}
	}

	@Override
	public List<InstalmentInfoDTO> getInsMsgByUserId(Map<String, Object> queryMap) {
		try {
			return instalmentInfoDAO.getInsMsgByUserId(queryMap);
		} catch (Exception e) {
			log.error("根据账单状态、用户id获取账单与订单表的关联信息失败", e);
			throw new RuntimeException("根据账单状态、用户id获取账单与订单表的关联信息失败", e);
		}
	}

}
