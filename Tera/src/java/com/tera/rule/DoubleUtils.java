package com.tera.rule;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @author  
 */
public final class DoubleUtils {

	/**
	 * 默认除法运算精度
	 */
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 构造函数
	 */
	private DoubleUtils() {
	}

	/**
	 * 提供（相对）精确的加法运算,精确到小数点后15位
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();

	}

	/**
	 * 提供（相对）精确的减法运算,精确到小数点后15位
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();

	}

	/**
	 * 提供（相对）精确的乘法运算,精确到小数点后15位
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();

	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 小数点以后10位，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
			"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException(

			"The scale must be a positive integer or zero");

		}

		BigDecimal b = new BigDecimal(Double.toString(v));

		BigDecimal one = new BigDecimal("1");

		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}
	/**
	 * 提供精确的小数位 根据   有数就入
	 * @param v 需要处理的数字
	 * @param scale 小数点后保留几位
	 * @return
	 */
	public static double roundUp(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
			"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_UP).doubleValue();

	}
	/**
	 * 提供（相对）精确的取最大值运算,精确到小数点后15位。
	 * @param v1 比较值
	 * @param v2 比较值
	 * @return 两个参数中的最大值
	 */
	public static double max(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		// b1.setScale(19);
		// b2.setScale(19);
		return b1.max(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的取最小值运算,精确到小数点后15位。
	 * @param v1 比较值
	 * @param v2 比较值
	 * @return 两个参数中的最小值
	 */
	public static double min(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.min(b2).doubleValue();
	}

	/**
	 * pow
	 * @param v1 底数
	 * @param v2 指数
	 * @return double
	 */
	public static double pow(double v1, double v2) {
		return Math.pow(v1, v2);
	}

	/**
	 * @param args args
	 */
	public static void main(String[] args) {
		// System.out.println(MathUtils.add(9.999999099999999999,0.00000000000000000009));
		// System.out.println(0.05+0.01);
		// System.out.println(MathUtils.sub(1.0,0.42));
		// System.out.println(1.0-0.42);
		// System.out.println(MathUtils.mul(4.015,100));
		// System.out.println(4.015*100);
		// System.out.println(MathUtils.div(123.3,100,15));
		// System.out.println(123.3/100);
		// System.out.println(MathUtils.div(1,3,15));
		// System.out.println(MathUtils.div(1,3));
		// System.out.println(MathUtils.max(9.999999999999,9.99999999999999));
		// System.out.println(new
		// java.text.DecimalFormat("0.000000000000000").format(9.999999999999999));
		// System.out.println(Math.max(9.999999999999,9.999999999));
		// System.out.println(MathUtils.min(0.0005,0.0001));
		// System.out.println(MathUtils.round(9.999999999999992,15));
		// System.out.println(MathUtils.pow(0.005,0.001));
		/*
		 * 下面的例子证明采用bigdecimal精度尽管提高了，但运算的性能却降低。
		 * 1138249174968 1138249174978
		 * the compute result is :4.95 The compute time is :10MilSec
		 * 1138249174978 1138249174978
		 * the compute result is :4.95 The compute time is :0MilSec
		 */
//		double[] list = new double[100];
//		for (int i = 0; i < 100; i++) {
//			list[i] = 0.001 * i;
//		}
//		double sum = 0;
//		long startTime = System.currentTimeMillis();
//		System.out.println(startTime);
//		for (int j = 0; j < 100; j++) {
//			sum = MathUtils.add(sum, list[j]);
//
//		}
//		long endTime = System.currentTimeMillis();
//		System.out.println(endTime);
//		System.out.println("the compute result is :" + sum
//				+ " The compute time is :" + (endTime - startTime) + "MilSec");
//		long startTimePoint = System.currentTimeMillis();
//		System.out.println(startTimePoint);
//		sum = 0;
//		for (int k = 0; k < 100; k++) {
//			sum += list[k];
//
//		}
//		long endTimePoint = System.currentTimeMillis();
//		System.out.println(endTimePoint);
//		System.out.println("the compute result is :" + MathUtils.round(sum, 3)
//				+ " The compute time is :" + (endTimePoint - startTimePoint)
//				+ "MilSec");

//		System.out.println(MathUtils.round(9.9999, 0));
//		System.out.println((int) 9.9999);
		
//		double gk_bj=30000.0,sff=34.92,lilu=0.9,qx=36.0;
//		
//		double hte=MathUtils.div(gk_bj,MathUtils.sub(1,MathUtils.div(sff, 100.0)));
//		System.out.println("合同金额："+hte);
//		double MAmount=MathUtils.div(
//				MathUtils.mul(MathUtils.mul(hte, MathUtils.div(lilu, 100.0)),
//						Math.pow(MathUtils.add(1.0,MathUtils.div(lilu, 100.0)),qx)), 
//				MathUtils.sub(Math.pow(MathUtils.add(1.0,MathUtils.div(lilu, 100.0)), qx),1.0)
//				);
//
//		System.out.println("月还款额："+MAmount);
//		MAmount=MathUtils.round(MAmount, 2, 0);
//		System.out.println("月还款额："+MAmount);
//		System.out.println("还款总额："+MathUtils.mul(MAmount,qx));
//		System.out.println("趸交服务费总额："+MathUtils.sub(hte,gk_bj));
//		
//		double sybj=hte;
//		for (int i = 1; i <= qx; i++) {
//			double m_li=0.0,m_bj=0.0;
//			m_li=MathUtils.mul(sybj,MathUtils.div(lilu, 100.0));
//			System.out.print("@@第"+i+"期>>>还利息："+m_li);
//			m_bj=MathUtils.sub(MAmount,m_li);
//			System.out.print(">>> 还本金："+m_bj);
//			sybj=MathUtils.sub(sybj,m_bj);
//			System.out.println(">>> 剩余本金："+sybj);
//		}
//		
		

		System.out.println(DoubleUtils.roundUp(1.2201, 2));
		System.out.println(DoubleUtils.roundUp(1.2210, 2));
		System.out.println(DoubleUtils.roundUp(1.2220, 2));
		System.out.println(DoubleUtils.roundUp(1.6900, 2));
		
	}

}
