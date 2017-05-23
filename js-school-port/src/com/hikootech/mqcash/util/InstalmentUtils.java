package com.hikootech.mqcash.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikootech.mqcash.po.ConfigInstalment;
import com.hikootech.mqcash.po.UserRepaymentPlans;

/**
 * @author yuwei
 * 2015年8月17日
 * 分期相关工具
 */
public class InstalmentUtils {
	
	private static Logger log = LoggerFactory.getLogger(InstalmentUtils.class);
	
	/**
	 * 根据金额，计算每一期应还金额（除不尽算在第一期）
	 * @param instalmentMoney 分期总金额
	 * @param count 一共分多少期
	 * @param number 当前
	 * @return
	 */
	public static BigDecimal getRepaymentPrice(BigDecimal price, int count, int number){
		if(number > count){
			number = count;
		}
		
		price = price.setScale(2, RoundingMode.DOWN);
		BigDecimal divisor = new BigDecimal(count);
		BigDecimal average = price.divide(divisor, 2, RoundingMode.DOWN);
//		System.out.println(average);
		BigDecimal remainder = price.subtract(average.multiply(divisor));
//		System.out.println(remainder);
		if(number == 1){
			return average.add(remainder);
		}
		
		return average;
	}
	
	public static void main(String[] args) {
		System.out.println(getRepaymentPrice(new BigDecimal("2174.33"), 6, 1));
		System.out.println(getRepaymentPrice(new BigDecimal("2174.33"), 6, 2));
		System.out.println(getRepaymentPrice(new BigDecimal("2111"), 6, 1));
		System.out.println(getRepaymentPrice(new BigDecimal("2111"), 6, 2));
		System.out.println(getRepaymentPrice(new BigDecimal("63.33"), 6, 1));
		System.out.println(getRepaymentPrice(new BigDecimal("63.33"), 6, 2));
	}
	
	/**根据订单金额、分期费率、分期数计算分期总金额（电渠只保留2位小数，秒趣总金额四舍五入）
	 * @param orderPrice
	 * @param perServiceRate
	 * @param instalmentCount
	 * @return
	 */
	public static BigDecimal getRepaymentTotal(BigDecimal orderPrice, BigDecimal perServiceRate, int instalmentCount){
		orderPrice = orderPrice.setScale(2, RoundingMode.DOWN);
		perServiceRate = perServiceRate.setScale(4, RoundingMode.DOWN);
		return orderPrice.multiply(perServiceRate).setScale(2, RoundingMode.DOWN).multiply(new BigDecimal(instalmentCount)).add(orderPrice);
	}
	
	/**根据订单金额、分期费率、分期数计算分期总金额（电渠只保留2位小数，秒趣总金额四舍五入）
	 * @param orderPrice
	 * @param perServiceRate
	 * @param instalmentCount
	 * @return
	 */
	public static BigDecimal getRepaymentFeeTotal(BigDecimal orderPrice, BigDecimal perServiceRate, int instalmentCount){
		orderPrice = orderPrice.setScale(2, RoundingMode.DOWN);
		perServiceRate = perServiceRate.setScale(4, RoundingMode.DOWN);
		return orderPrice.multiply(perServiceRate).setScale(2, RoundingMode.DOWN).multiply(new BigDecimal(instalmentCount));
	}
	
	/**
	 * 生成防止表单多次提交的令牌
	 * @return
	 */
	public static String generateToken(){
		String token = UUIDTools.getFormatUUID();
		return token;
	}
	
	/**校验分期总金额是否正确
	 * @param config
	 * @param instalmentMoneys
	 * @return
	 */
	public static boolean isInstalmentAmountValid(ConfigInstalment config, BigDecimal instalmentAmount, BigDecimal partnerOrderAmount){
		instalmentAmount = instalmentAmount.setScale(2, RoundingMode.DOWN);
		partnerOrderAmount = partnerOrderAmount.setScale(2, RoundingMode.DOWN);
		
		BigDecimal real = getRepaymentTotal(partnerOrderAmount, config.getPerServiceRate(), config.getInstalmentCount());
		log.info("实际分期总金额：" + real.intValue() + ",前台上传总金额：" + instalmentAmount.intValue());
		
		return real.compareTo(instalmentAmount) == 0 ? true : false;
	}
	
	/**通过订单（本金）金额，逾期费率，应还时间(用于计算与当前时间的天数差值)，计算逾期手续费
	 * @param orderAmount 订单（本金）金额
	 * @param overdueRate 逾期费率
	 * @param repayPlanTime 应还时间
	 * @return
	 */
	public static BigDecimal calOverdue(BigDecimal orderAmount, BigDecimal overdueRate, Date repayPlanTime){
		int _days=DateUtil.daysBetween(repayPlanTime, new Date(), false);
		return calOverdue( orderAmount,  overdueRate,  _days);
	}
	
	/** 
	* @Title calOverdue 
	* @Description 通过订单（本金）金额，逾期费率，应还时间(用于计算与当前时间的天数差值)，计算逾期手续费
	* @param 参数列表
	* @param orderAmount 订单（本金）金额
	* @param overdueRate 逾期费率
	* @param repayPlanTime 应还时间
	* @param curTime 当前时间
	* @return 
	* @return BigDecimal	返回类型 
	*/
	public static BigDecimal calOverdue(BigDecimal orderAmount, BigDecimal overdueRate, Date repayPlanTime, Date curTime){
		int _days = DateUtil.daysBetween(repayPlanTime, curTime, false);
		return calOverdue( orderAmount,  overdueRate,  _days);
	}
	
	/**通过订单（本金）金额，逾期费率，逾期天数，计算逾期手续费
	 * @param orderAmount 订单（本金）金额
	 * @param overdueRate 逾期费率
	 * @param day 逾期天数
	 * @return
	 */
	public static BigDecimal calOverdue(BigDecimal orderAmount, BigDecimal overdueRate, int day){
		if(day <= 0){
			return new BigDecimal(0);
		}
		
		orderAmount = orderAmount.setScale(2, RoundingMode.DOWN);
		overdueRate = overdueRate.setScale(4, RoundingMode.DOWN);
		BigDecimal overdueFee = orderAmount.multiply(overdueRate).multiply(new BigDecimal(day)).setScale(2, RoundingMode.DOWN);
		return overdueFee;
	}

	/**根据传入的还款计划list，查询出最终需还款的金额***/
	public static BigDecimal calcPayAmount(List<UserRepaymentPlans> plansList,boolean isAll){
		BigDecimal retVal=new BigDecimal("0.00");
		for (UserRepaymentPlans plan : plansList) {
			if(plan==null){
				continue;
			}
			retVal=retVal.add(calcPayAmount(plan,isAll));
		}
		return retVal;
	}
	/**根据传入的还款计划list，查询出最终需还款的本金金额***/
	public static BigDecimal calcPayPrincipalAmount(List<UserRepaymentPlans> plansList){
		BigDecimal retVal=new BigDecimal("0.00");
		for (UserRepaymentPlans plan : plansList) {
			if(plan==null){
				continue;
			}
			retVal=retVal.add(calcPayPrincipalAmount(plan));
		}
		return retVal;
	}
	/**根据传入的还款计划list，查询出最终需还款的服务费金额***/
	public static BigDecimal calcPayServiceAmount(List<UserRepaymentPlans> plansList){
		BigDecimal retVal=new BigDecimal("0.00");
		for (UserRepaymentPlans plan : plansList) {
			if(plan==null){
				continue;
			}
			retVal=retVal.add(calcPayServiceAmount(plan));
		}
		return retVal;
	}
	
	/**根据传入的一个还款计划，查询出最终需还款的金额<br>
	 * @param isAll <b>是否包括罚息和罚息减免    true :包括罚息和罚息减免       false:不包括罚息和罚息减免</b>
	 * ***/
	public static BigDecimal calcPayAmount(UserRepaymentPlans plan,boolean isAll){
	 BigDecimal retValTemp=new BigDecimal("0.00");
	 if(plan==null){
		 return retValTemp;
	 }
			  BigDecimal receivablePrincipal=(plan.getReceivablePrincipal()==null?new BigDecimal(0):plan.getReceivablePrincipal());
			  BigDecimal receivableService=plan.getReceivableService()==null?new BigDecimal(0):plan.getReceivableService();
			  BigDecimal receivableOverdue =new BigDecimal(0);
			  BigDecimal receivedPrincipal=plan.getReceivedPrincipal()==null?new BigDecimal(0):plan.getReceivedPrincipal();
			  BigDecimal receivedService=plan.getReceivedService()==null?new BigDecimal(0):plan.getReceivedService();
			  BigDecimal receivedOverdue= new BigDecimal(0) ;
			  BigDecimal reduceOverdue= new BigDecimal(0);
			  if(isAll){
				    receivableOverdue =plan.getReceivableOverdue()==null?new BigDecimal(0):plan.getReceivableOverdue();
				    receivedOverdue=plan.getReceivedOverdue()==null?new BigDecimal(0):plan.getReceivedOverdue();
 				    reduceOverdue=plan.getReduceOverdue()==null?new BigDecimal(0):plan.getReduceOverdue();
			  } 
			  
			retValTemp=retValTemp.add(receivablePrincipal).add(receivableService).add(receivableOverdue).subtract(receivedPrincipal).subtract(receivedService).subtract(receivedOverdue).subtract(reduceOverdue);
			if(retValTemp.compareTo(new BigDecimal(0))<=0){
				retValTemp=new BigDecimal("0.00");
			}
			return retValTemp;
	}
	
	/**根据传入的一个还款计划，查询出最终需还款的本金金额***/
	public static BigDecimal calcPayPrincipalAmount(UserRepaymentPlans plan){
	 BigDecimal retValTemp=new BigDecimal("0.00");
	 if(plan==null){
		 return retValTemp;
	 }
			  BigDecimal receivablePrincipal=(plan.getReceivablePrincipal()==null?new BigDecimal(0):plan.getReceivablePrincipal());
			  BigDecimal receivedPrincipal=plan.getReceivedPrincipal()==null?new BigDecimal(0):plan.getReceivedPrincipal();
			  retValTemp=retValTemp.add(receivablePrincipal).subtract(receivedPrincipal);
			if(retValTemp.compareTo(new BigDecimal(0))<=0){
				retValTemp=new BigDecimal("0.00");
			}
			return retValTemp;
	}
	
	/**根据传入的一个还款计划，查询出最终需还款的服务费***/
	public static BigDecimal calcPayServiceAmount(UserRepaymentPlans plan){
	 BigDecimal retValTemp=new BigDecimal("0.00");
	 if(plan==null){
		 return retValTemp;
	 }
	  BigDecimal receivableService=plan.getReceivableService()==null?new BigDecimal(0):plan.getReceivableService();
	  BigDecimal receivedService=plan.getReceivedService()==null?new BigDecimal(0):plan.getReceivedService();
	   retValTemp=retValTemp.add(receivableService).subtract(receivedService);
			if(retValTemp.compareTo(new BigDecimal(0))<=0){
				retValTemp=new BigDecimal("0.00");
			}
			return retValTemp;
	}
}
