package com.hikootech.mqcash.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuwei
 * 2015年11月20日
 * 分期规则工具
 */
public class InstalmentRuleUtils {
	
	public static Map<Integer, BigDecimal> feeRateMap = new HashMap<Integer, BigDecimal>();
	
	static{
		feeRateMap.put(3, new BigDecimal("0"));
		feeRateMap.put(6, new BigDecimal("0.006"));
		feeRateMap.put(9, new BigDecimal("0.006"));
		feeRateMap.put(12, new BigDecimal("0.006"));
	}
	
	/**根据订单金额计算最低还款金额，规则：12期，手续费0.6%，第二期
	 * @param price 订单金额
	 * @return 最低还款金额
	 */
	public static BigDecimal getLowestAmount(BigDecimal orderPrice){
		int instalmentCount = 12;
		BigDecimal perServiceRate = new BigDecimal(0.006).setScale(4, RoundingMode.DOWN);;
		int number = 2;
		
		orderPrice = orderPrice.setScale(2, RoundingMode.DOWN);
		BigDecimal totalPrice = calOrderTotalAmount(orderPrice, perServiceRate, 12);
		BigDecimal perStageAmount = calPerStageAmount(totalPrice, instalmentCount, number);
		
		return perStageAmount;
	}
	
	/**根据订单金额、分期数和订单时间（当前时间）得到服务费率、服务费、每期款还金额和首期还款日
	 * @param orderPrice 订单金额
	 * @param instalmentCount 分期数
	 * @param orderTime 订单时间（当前时间）
	 * @return 返回一个Map,key分别是<br/> 
	 * feeRate:服务费率  fee:服务费 perStageAmount:每期还款金额 firstStageTime:首期还款日
	 * @throws Exception
	 */
	public static Map<String, Object> calStageInfo(BigDecimal orderPrice, int instalmentCount, Date orderTime) throws Exception{
		orderPrice = orderPrice.setScale(2, RoundingMode.DOWN);
		
		if(feeRateMap.get(instalmentCount) == null){
			throw new Exception("不支持该分期数");
		}
		
		BigDecimal feeRate = feeRateMap.get(instalmentCount);//服务费率
		BigDecimal fee = calPerStageFee(orderPrice, feeRate);//服务费
		BigDecimal perStageAmount = calPerStageAmount(orderPrice, instalmentCount, 2);//每期还款金额
		Date firstStageTime = calFirstStageTime(orderTime);//首期还款日
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("feeRate", feeRate);
		retMap.put("fee", fee);
		retMap.put("perStageAmount", perStageAmount);
		retMap.put("firstStageTime", firstStageTime);
		
		return retMap;
	}
	
	/**根据订单时间计算首期还款日
	 * @param orderTime
	 * @return
	 */
	public static Date calFirstStageTime(Date orderTime){
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(orderTime);
		cal.add(Calendar.MONTH, 1);
		
		return cal.getTime();
	}
	
	/**根据订单金额、服务费率计算每期服务费
	 * @param orderPrice
	 * @param perServiceRate
	 * @return
	 */
	public static BigDecimal calPerStageFee(BigDecimal orderPrice, BigDecimal perServiceRate){
		orderPrice = orderPrice.setScale(2, RoundingMode.DOWN);
		perServiceRate = perServiceRate.setScale(4, RoundingMode.DOWN);
		
		return orderPrice.multiply(perServiceRate).setScale(2, RoundingMode.DOWN);
	}
	
	/**根据金额，计算每一期应还金额（除不尽算在第一期）
	 * @param price 金额
	 * @param count 总分期数
	 * @param number 第几分期
	 * @return 返回number分期应还金额
	 */
	public static BigDecimal calPerStageAmount(BigDecimal price, int count, int number){
		if(number > count){
			number = count;
		}
		
		price = price.setScale(2, RoundingMode.DOWN);
		BigDecimal divisor = new BigDecimal(count);
		BigDecimal average = price.divide(divisor, 2, RoundingMode.DOWN);
		BigDecimal remainder = price.subtract(average.multiply(divisor));
		if(number == 1){
			return average.add(remainder);
		}
		return average;
	}
	
	/**根据订单金额、分期费率、分期数计算分期总金额
	 * @param orderPrice 订单金额
	 * @param perServiceRate 分期费率
	 * @param instalmentCount 分期数
	 * @return
	 */
	public static BigDecimal calOrderTotalAmount(BigDecimal orderPrice, BigDecimal perServiceRate, int instalmentCount){
		orderPrice = orderPrice.setScale(2, RoundingMode.DOWN);
		perServiceRate = perServiceRate.setScale(4, RoundingMode.DOWN);
		return orderPrice.multiply(perServiceRate).setScale(2, RoundingMode.DOWN).multiply(new BigDecimal(instalmentCount)).add(orderPrice);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getLowestAmount(new BigDecimal(100)));
		System.out.println(calStageInfo(new BigDecimal(1200), 6, new Date()));
	}

}
