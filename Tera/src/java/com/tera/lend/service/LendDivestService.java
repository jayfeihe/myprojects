package com.tera.lend.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.lend.model.LendApp;
import com.tera.match.dao.Lend2matchDao;
import com.tera.match.dao.MatchResultDao;
import com.tera.match.model.Lend2match;
import com.tera.match.model.MatchResult;
import com.tera.payment.dao.PaymentDao;
import com.tera.payment.model.Payment;
import com.tera.product.dao.ProductDao;
import com.tera.product.model.Product;
import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>LendAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-18 11:08:58<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("lendDivestService")
public class LendDivestService<T> {

	private final static Logger log = Logger.getLogger(LendAppService.class);
	
	@Autowired(required=false) //自动注入
	private LendAppService lendAppService;
	
	@Autowired(required=false) //自动注入
	private PaymentDao<Payment> paymentDao;
	
	@Autowired(required=false) //自动注入
	private Lend2matchDao<Lend2match> lend2matchDao;
	
	@Autowired(required=false) //自动注入
	private MatchResultDao<MatchResult> matchResultDao;
	
	@Autowired(required=false) //自动注入
	private ProductDao<Product> productDao;
	
	@Transactional
	public void Divest(int lendId, String defaultRate, String divestDays) throws Exception {

//		撤资操作逻辑
//		1、修改Lend_app里st;ate=2 
//		2、清理payment，state=9的改成state=0
//		3、计算金额插入payment:根据借款申请号查询到所有payment信息，根据Id排序
		//1、修改Lend_app里state=2 
		LendApp lendApp=new LendApp();
		lendApp.setId(lendId);
		lendApp.setState("2");
		this.lendAppService.updateOnlyChanged(lendApp);
		//-------------------------------------------------------------------
		LendApp lendApp2=this.lendAppService.queryByKey(lendId);
		Payment payment=null;
		Map<String, Object> paymentMap=new HashMap<String, Object>();
		paymentMap.put("contractNo", lendApp2.getAppId());
		paymentMap.put("subjects", new String[]{"付利息","付本息"});
		List<Payment> paymentList=paymentDao.queryDivestList(paymentMap);
		Date lastDate=null;
		//查询payment,根据ID排序，获得第一个状态为9的数据，如果他的前一条数据的状态是5，就存入变量，停止循环
		for (int i = 0; i < paymentList.size(); i++) {
			
			if ("9".equals(paymentList.get(i).getState())) {
				if(i!=0){
					lastDate=paymentList.get(i-1).getRepaymentDate();
					payment=paymentList.get(i);
					break;
				}else{
					
					Map<String, Object> map2=new HashMap<String, Object>();
					map2.put("lendAppId", lendApp2.getAppId());
					List<Lend2match> lend2matchList=lend2matchDao.queryList(map2);
					if (lend2matchList.size()>0) {
						lastDate=lend2matchList.get(0).getValueDate();
						payment=paymentList.get(i);
						break;
					}
				}
			}
		}
		
		System.out.println(lastDate);
		long dqDate=System.currentTimeMillis();
		Date todayDate=new Date(dqDate);
		//求相差天数=上一次的还息日和今日的天数差+要提前的天数
		int days=DateUtils.getDayRange(lastDate, todayDate)+Integer.parseInt(divestDays);
		System.out.println(days);
		//获得利率
		double interestRate=productDao.queryByName(lendApp2.getProduct()).getInterestRate()/100;
		//获得最新金额
		double amount=lendApp2.getAmount()*(1+(double)days/360*interestRate-Double.parseDouble(defaultRate)/100);
		System.out.println(amount);
		//更新
		for (Payment payment2 : paymentList) {
			//2、清理payment，state=9的改成state=0
			if ("9".equals(payment2.getState())) {
				payment2.setState("0");
				paymentDao.updateOnlyChanged(payment2);
			}
		}
		//3、计算金额插入payment
		payment.setPlanAmount(lendApp2.getAmount());
		payment.setActualAmount(amount);
		payment.setState("9");
		payment.setSubject("赎回资金");
		//payment.setRepaymentDate(null);
		payment.setCreateTime(new java.sql.Timestamp(dqDate));
		StringBuffer sb=new StringBuffer();
		sb.append(lendApp2.getAmount())
			.append(",")
			.append(lendApp2.getAmount()*(double)days/360*interestRate)
			.append(",")
			.append(lendApp2.getAmount()*Double.parseDouble(defaultRate)/100);
		System.out.println(sb.toString());
		payment.setDetail(sb.toString());
		payment.setRepaymentDate(DateUtils.addDay(todayDate, Integer.parseInt(divestDays)));
		paymentDao.add(payment);
	}
	
}
