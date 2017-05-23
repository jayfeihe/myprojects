package com.hikootech.mqcash.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.CreditCfgKeyConstants;
import com.hikootech.mqcash.constants.CreditCodeConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.UnipayScoreConstants;
import com.hikootech.mqcash.context.CommonKVContext;
import com.hikootech.mqcash.dao.CreditCollegeDAO;
import com.hikootech.mqcash.dao.UnipayDAO;
import com.hikootech.mqcash.dao.UserCreditDataDAO;
import com.hikootech.mqcash.po.UserCreditModelRecord;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.po.unipay.UnipayCity;
import com.hikootech.mqcash.po.unipay.UnipayPersonalConsumeCategory;
import com.hikootech.mqcash.po.unipay.UnipayPersonalConsumeCity;
import com.hikootech.mqcash.po.unipay.UnipayPersonalMonthConsume;
import com.hikootech.mqcash.po.unipay.UnipayPersonalReport;
import com.hikootech.mqcash.po.unipay.UnipayPersonalScore;
import com.hikootech.mqcash.po.unipay.UnipayPersonalTransCredit;
import com.hikootech.mqcash.service.ConfigCreditKvService;
import com.hikootech.mqcash.service.UnipayService;
import com.hikootech.mqcash.service.UserCreditDataService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.UniPay;

@Service("unipayService")
public class UnipayServiceImpl implements UnipayService {
	private static Logger log = LoggerFactory.getLogger(UnipayServiceImpl.class);

	@Autowired
	private UnipayDAO unipayDao;
	
	@Autowired
	private UserCreditDataDAO userCreditDataDAO;
	
	@Autowired
	private CreditCollegeDAO creditCollegeDAO;
	
	@Autowired
	private UserCreditDataService userCreditDataService;
	
	@Autowired
	private ConfigCreditKvService configCreditKvService;
	
	@Override
	public UnipayPersonalReport dealUnipayReport(String cardNumber, String idCard, String name, String busId, String cityCode,String reportId) 
			 throws Exception {
		//请求银联接口数据
		int times=3;
		try {
			times=Integer.parseInt(ConfigUtils.getProperty("unipay.times"));
		} catch (Exception e1) {}
		if(times<1)
			times=1;
		
		String str="";
		for(int i=0;i<times;i++){
			str=UniPay.requestPersonalReport(cardNumber);
			if(str!=null && !str.equals("")){
				break;
			}
			log.info("银联请求数据第 "+i+" 次");
		}
		//String str="{\"IndexProperty\":{\"RepaymentAbility\":\"4000,6000\",\"HasHouse\":false,\"HasCar\":false,\"HousePurTime\":null,\"CarPurTime\":null,\"HouseValue\":0.0,\"CarValue\":0.0},\"IndexTransBehavior\":{\"BusinessTrip\":true,\"MarriageConsume\":false,\"Employed\":true,\"ChildInvest\":false,\"NightConsume\":true,\"City\":\"深圳市\",\"WorkRegion\":\"连云港市 海州区 新建西路\",\"FreeRegion\":\"深圳市 福田区 \"},\"IndexMonthConsumes\":[{\"Month\":\"2015-06\",\"Amount\":0.0,\"Count\":0,\"AmountRanking\":95.0,\"CountRanking\":95.0},{\"Month\":\"2015-07\",\"Amount\":0.01,\"Count\":1,\"AmountRanking\":100.0,\"CountRanking\":100.0},{\"Month\":\"2015-08\",\"Amount\":0.0,\"Count\":0,\"AmountRanking\":95.0,\"CountRanking\":95.0},{\"Month\":\"2015-09\",\"Amount\":49.2,\"Count\":1,\"AmountRanking\":100.0,\"CountRanking\":100.0},{\"Month\":\"2015-10\",\"Amount\":0.0,\"Count\":0,\"AmountRanking\":95.0,\"CountRanking\":95.0},{\"Month\":\"2015-11\",\"Amount\":0.0,\"Count\":0,\"AmountRanking\":95.0,\"CountRanking\":95.0},{\"Month\":\"2015-12\",\"Amount\":0.01,\"Count\":1,\"AmountRanking\":100.0,\"CountRanking\":100.0},{\"Month\":\"2016-01\",\"Amount\":0.0,\"Count\":0,\"AmountRanking\":95.0,\"CountRanking\":95.0},{\"Month\":\"2016-02\",\"Amount\":0.0,\"Count\":0,\"AmountRanking\":95.0,\"CountRanking\":95.0},{\"Month\":\"2016-03\",\"Amount\":0.0,\"Count\":0,\"AmountRanking\":95.0,\"CountRanking\":95.0},{\"Month\":\"2016-04\",\"Amount\":0.0,\"Count\":0,\"AmountRanking\":95.0,\"CountRanking\":95.0},{\"Month\":\"2016-05\",\"Amount\":0.0,\"Count\":0,\"AmountRanking\":95.0,\"CountRanking\":95.0},{\"Month\":\"近12个\",\"Amount\":49.22,\"Count\":3,\"AmountRanking\":100.0,\"CountRanking\":100.0}],\"IndexConsumeCities\":[{\"Name\":\"连云港市\",\"Amount\":49.2,\"Count\":1},{\"Name\":\"贵阳市\",\"Amount\":0.02,\"Count\":2}],\"IndexConsumeCategories\":[{\"Name\":\"IT行业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"餐饮业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"房地产业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"公用事业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"广告摄影行业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"婚庆行业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"交通运输业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"教育行业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"金融行业\",\"Amount\":0.01,\"Count\":3},{\"Name\":\"居民服务业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"零售行业\",\"Amount\":49.2,\"Count\":1},{\"Name\":\"住宿服务业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"旅游服务业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"批发行业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"银行柜台ATM\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"汽车行业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"商务服务业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"文化、体育和娱乐行业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"医疗服务业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"邮政快递服务业\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"政府机构及社会团体\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"其他行业\",\"Amount\":0.0,\"Count\":0}],\"IndexTransCredits\":[{\"Name\":\"取现\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"信用卡取现\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"公共事业缴费\",\"Amount\":0.0,\"Count\":4},{\"Name\":\"纳税\",\"Amount\":0.0,\"Count\":0},{\"Name\":\"夜消费\",\"Amount\":0.01,\"Count\":1},{\"Name\":\"网上消费\",\"Amount\":0.01,\"Count\":8},{\"Name\":\"信用卡还款\",\"Amount\":0.0,\"Count\":0}],\"ResponseCode\":100,\"Result\":1,\"ResultText\":\"查询成功\",\"ResponseText\":\"接口调用成功\"}";
		log.info("银联数据，cardNumber="+cardNumber+",str="+str);
		
		if(str==null || str.equals("")){
			throw new Exception("调用银联接口返回数据异常");
		}
		
		try {
			JSONObject json=JSON.parseObject(str);
			int code=json.getIntValue("ResponseCode");
			//判断接口是否成功
			if(code==100){
				UnipayPersonalReport report;
				if (EntityUtils.isEmpty(reportId)) {
					reportId=GenerateKey.getId(CommonConstants.UNIPAY_REPORT, ConfigUtils.getProperty("db_id"));
				}
				
				Date now=new Date();
				
				int result=json.getInteger("Result");
				//判断是否有数据
				if(result==1){
					JSONObject IndexProperty=json.getJSONObject("IndexProperty");
					JSONObject IndexTransBehavior=json.getJSONObject("IndexTransBehavior");
					report=new UnipayPersonalReport(IndexProperty,IndexTransBehavior);
					
					JSONArray IndexMonthConsumes=json.getJSONArray("IndexMonthConsumes");
					JSONArray IndexConsumeCities=json.getJSONArray("IndexConsumeCities");
					JSONArray IndexConsumeCategories=json.getJSONArray("IndexConsumeCategories");
					JSONArray IndexTransCredits=json.getJSONArray("IndexTransCredits");
					
					//解析、入库消费信用数据
					for(int i=0;i<IndexTransCredits.size();i++){
						JSONObject c=IndexTransCredits.getJSONObject(i);
						UnipayPersonalTransCredit credit=new UnipayPersonalTransCredit(c);
						credit.setId(GenerateKey.getId(CommonConstants.UNIPAY_REPORT_CREDIT, ConfigUtils.getProperty("db_id")));
						credit.setReportId(reportId);
						credit.setCreateTime(now);
						unipayDao.insertUnipayTransCredit(credit);
					}
					
					//解析、入库消费城市数据
					for(int i=0;i<IndexConsumeCities.size();i++){
						JSONObject c=IndexConsumeCities.getJSONObject(i);
						UnipayPersonalConsumeCity city=new UnipayPersonalConsumeCity(c);
						city.setId(GenerateKey.getId(CommonConstants.UNIPAY_REPORT_CITY, ConfigUtils.getProperty("db_id")));
						city.setReportId(reportId);
						city.setCreateTime(now);
						unipayDao.insertUnipayConsumeCity(city);
					}
					
					//解析、入库消费数据
					for(int i=0;i<IndexMonthConsumes.size();i++){
						JSONObject c=IndexMonthConsumes.getJSONObject(i);
						String month=c.getString("Month");
						if(month.indexOf("近")!=-1)
							continue;
						UnipayPersonalMonthConsume consume=new UnipayPersonalMonthConsume(c);
						consume.setId(GenerateKey.getId(CommonConstants.UNIPAY_REPORT_CONSUME, ConfigUtils.getProperty("db_id")));
						consume.setReportId(reportId);
						consume.setCreateTime(now);
						unipayDao.insertUnipayMonthConsume(consume);
					}
					
					//解析、入库消费类别数据
					for(int i=0;i<IndexConsumeCategories.size();i++){
						JSONObject c=IndexConsumeCategories.getJSONObject(i);
						UnipayPersonalConsumeCategory category=new UnipayPersonalConsumeCategory(c);
						category.setId(GenerateKey.getId(CommonConstants.UNIPAY_REPORT_CATEGORY, ConfigUtils.getProperty("db_id")));
						category.setReportId(reportId);
						category.setCreateTime(now);
						unipayDao.insertUnipayConsumeCategory(category);
					}
				}
				else{
					report=new UnipayPersonalReport();
				}
				
				//入库数据报告
				report.setId(reportId);
				report.setCardNumber(cardNumber);
				report.setResult(result);
				report.setCreateTime(now);
				unipayDao.insertUnipayReport(report);
				return report;
			}
			return null;
		} catch (Exception e) {
			log.error("处理银联接口返回数据异常："+e);
			throw new Exception("处理银联接口返回数据异常"+e.getMessage());
		}
	}

	@Override
	public UnipayPersonalScore calScore(String busId,UnipayPersonalReport report,String cityCode, String idCard, String name)
		throws Exception {
		UnipayPersonalScore score=new UnipayPersonalScore();
		int upa1=0,upa2=0,upa3=0,upa4=0,upa5=0,upa6=0;
		int upb1=0,upb2=0,upb3=0,upb4=0,upb5=0,upb6=0;
		int upf1=0,upf2=0,upf3=0,upf4=0,upf5=0,upf6=0;
		
		if(report.getResult()==1){
			List<UnipayPersonalTransCredit> credits=unipayDao.listUnipayTransCredit(report.getId());
			List<UnipayPersonalConsumeCity> citys=unipayDao.listUnipayConsumeCity(report.getId());
			List<UnipayPersonalMonthConsume> consumes=unipayDao.listUnipayMonthConsume(report.getId());
			List<UnipayPersonalConsumeCategory> categorys=unipayDao.listUnipayConsumeCategory(report.getId());
			
			//UPA
			String idCity=idCard.substring(0,4)+"00";
			
			String city=CommonKVContext.unipayCityDic.get(report.getCity());
			if(city!=null && !city.equals("")){
				if(city.equals(cityCode))
					upa1=3;
				else if(city.equals(idCity))
					upa1=2;
				else
					upa1=1;
			}
			
			String workRegion=report.getWorkRegion();
			try {
				workRegion=workRegion.substring(0,workRegion.indexOf(" "));
			} catch (Exception e1) {}
			String workCity=CommonKVContext.unipayCityDic.get(workRegion);
			if(workCity!=null && !workCity.equals("")){
				if(workCity.equals(cityCode))
					upa2=3;
				else if(workCity.equals(idCity))
					upa2=2;
				else
					upa2=1;
			}
			
			String freeRegion=report.getFreeRegion();
			try {
				freeRegion=freeRegion.substring(0,freeRegion.indexOf(" "));
			} catch (Exception e1) {}
			String freeCity=CommonKVContext.unipayCityDic.get(freeRegion);
			if(freeCity!=null && !freeCity.equals("")){
				if(freeCity.equals(cityCode))
					upa3=3;
				else if(freeCity.equals(idCity))
					upa3=2;
				else
					upa3=1;
			}
			
			if(citys!=null && citys.size()>0){
				int maxCount=0;
				BigDecimal maxAmount=new BigDecimal(0);
				String amountCity="";
				String countCity="";
				for(UnipayPersonalConsumeCity cc:citys){
					if(cc.getAmount().compareTo(maxAmount)==1){
						amountCity=cc.getName();
						maxAmount=cc.getAmount();
					}
					if(cc.getCount()>maxCount){
						countCity=cc.getName();
						maxCount=cc.getCount();
					}
				}
				
				countCity=CommonKVContext.unipayCityDic.get(countCity);
				if(countCity!=null && !countCity.equals("")){
					if(countCity.equals(cityCode))
						upa4=3;
					else if(countCity.equals(idCity))
						upa4=2;
					else
						upa4=1;
				}
				
				amountCity=CommonKVContext.unipayCityDic.get(amountCity);
				if(amountCity!=null && !amountCity.equals("")){
					if(amountCity.equals(cityCode))
						upa5=3;
					else if(amountCity.equals(idCity))
						upa5=2;
					else
						upa5=1;
				}
				
				if(upa4==1 && upa5==1){
					for(UnipayPersonalConsumeCity cc:citys){
						if(cityCode.equals(CommonKVContext.unipayCityDic.get(cc.getName()))){
							upa6=3;
							break;
						}
					}
					if(upa6==0){
						for(UnipayPersonalConsumeCity cc:citys){
							if(idCity.equals(CommonKVContext.unipayCityDic.get(cc.getName()))){
								upa6=2;
								break;
							}
						}
					}
					if(upa6==0){
						upa6=1;
					}
				}
				
			}
			
			//UPB
			switch (report.getHasHouse()){
			case 0:
				upb1=1;
				break;
			case 1:
				upb1=2;
				break;
			}
			
			switch (report.getHasCar()){
			case 0:
				upb2=1;
				break;
			case 1:
				upb2=2;
				break;
			}
			
			int bility=0;
			String[] bilitys=report.getRepaymentAbility().split(",");
			if(bilitys.length==1){
				try {
					bility=Integer.parseInt(bilitys[0]);
				} catch (Exception e) {}
			}
			else if(bilitys.length==2){
				try {
					int b1=Integer.parseInt(bilitys[0]);
					int b2=Integer.parseInt(bilitys[1]);
					bility=(b1+b2)/2;
				} catch (Exception e) {}
			}
			if(bility>10000)
				upb3=5;
			else if(bility>=8000)
				upb3=4;
			else if(bility>=6000)
				upb3=3;
			else if(bility>=4000)
				upb3=2;
			else if(bility>=2000)
				upb3=1;
			
			switch (report.getBusinessTrip()){
			case 0:
				upb4=1;
				break;
			case 1:
				upb4=2;
				break;
			}
			
			switch (report.getMarriageConsume()){
			case 0:
				upb5=1;
				break;
			case 1:
				upb5=2;
				break;
			}
			
			switch (report.getChildInvest()){
			case 0:
				upb6=1;
				break;
			case 1:
				upb6=2;
				break;
			}
			//UPF
			if(consumes!=null && consumes.size()>0){
				BigDecimal total=new BigDecimal(0);
				int count=0;
				BigDecimal avg=new BigDecimal(0);
				
				for(UnipayPersonalMonthConsume consume:consumes){
					total=total.add(consume.getAmount());
					count+=consume.getCount();
				}
				avg=total.divide(new BigDecimal(consumes.size()), BigDecimal.ROUND_FLOOR);
				
				if(total.compareTo(new BigDecimal(10000))>=0)
					upf1=4;
				else if(total.compareTo(new BigDecimal(5000))>=0)
					upf1=3;
				else if(total.compareTo(new BigDecimal(2000))>=0)
					upf1=2;
				else if(total.compareTo(new BigDecimal(500))>=0)
					upf1=1;
				
				if(count>15)
					upf2=3;
				else if(count>=10)
					upf2=2;
				else if(count>=3)
					upf2=1;
				
				if(avg.compareTo(new BigDecimal(2000))>=0)
					upf3=4;
				else if(avg.compareTo(new BigDecimal(1500))>=0)
					upf3=3;
				else if(avg.compareTo(new BigDecimal(1000))>=0)
					upf3=2;
				else if(avg.compareTo(new BigDecimal(500))>=0)
					upf3=1;
				
				BigDecimal middle=new BigDecimal(0);
				int n=consumes.size();
				if(n%2==0){
					middle=consumes.get(n/2-1).getAmount().add(consumes.get(n/2+1-1).getAmount()).divide(new BigDecimal(2));
				}
				else{
					middle=consumes.get((n+1)/2-1).getAmount();
				}
				if(middle.compareTo(new BigDecimal(2000))>=0)
					upf4=4;
				else if(middle.compareTo(new BigDecimal(1500))>=0)
					upf4=3;
				else if(middle.compareTo(new BigDecimal(1000))>=0)
					upf4=2;
				else if(middle.compareTo(new BigDecimal(500))>=0)
					upf4=1;
			}
			
			if(categorys!=null && categorys.size()>0){
				String consumeCategory="";
				BigDecimal amount=new BigDecimal(0);
				for(UnipayPersonalConsumeCategory category:categorys){
					if(category.getAmount().compareTo(amount)==1){
						consumeCategory=category.getName();
						amount=category.getAmount();
					}
				}
				if(consumeCategory.equals("银行柜台ATM"))
					upf5=2;
				else if(consumeCategory.equals("零售") || consumeCategory.equals("批发"))
					upf5=1;
				else if(consumeCategory.equals("金融行业"))
					upf5=-1;
			}
			
			if(credits!=null && credits.size()>0){
				String creditCategory="";
				BigDecimal amount=new BigDecimal(0);
				for(UnipayPersonalTransCredit credit:credits){
					if(credit.getAmount().compareTo(amount)==1){
						creditCategory=credit.getName();
						amount=credit.getAmount();
					}
				}
				if(creditCategory.equals("取现"))
					upf6=2;
				else if(creditCategory.equals("网上消费"))
					upf6=1;
				else if(creditCategory.equals("公共事业缴费") || creditCategory.equals("纳税"))
					upf6=0;
				else if(creditCategory.equals("信用卡取现") || creditCategory.equals("信用卡还款") || creditCategory.equals("夜消费"))
					upf6=-1;
			}
		}
		score.setUpa1(upa1);
		score.setUpa2(upa2);
		score.setUpa3(upa3);
		score.setUpa4(upa4);
		score.setUpa5(upa5);
		score.setUpa6(upa6);
		
		score.setUpb1(upb1);
		score.setUpb2(upb2);
		score.setUpb3(upb3);
		score.setUpb4(upb4);
		score.setUpb5(upb5);
		score.setUpb6(upb6);
		
		score.setUpf1(upf1);
		score.setUpf2(upf2);
		score.setUpf3(upf3);
		score.setUpf4(upf4);
		score.setUpf5(upf5);
		score.setUpf6(upf6);
		
		score.setBusId(busId);
		score.setReportId(report.getId());
		score.setCreateTime(new Date());
		score.setIdCard(idCard);
		score.setName(name);
		score.setCityCode(cityCode);
		unipayDao.insertUnipayPersonalScore(score);
		
		return score;
	}
	
	@Override
	public boolean creditUnipay(String cardNumber, String idCard, String name,
			Integer edScore, String busId, String cityCode) throws Exception {
		
		UserCreditRecord creditRecord=new UserCreditRecord();
		Map<String,String> map = new HashMap<String,String>();
		map.put("busId", busId);
		
		creditRecord.setInfoId(busId);
		
		//从memcached中取出数据
		Map<String, String> meMap = null;
		try {
			meMap = configCreditKvService.getAllCreditKv();
		} catch (Exception e3) {
			userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_EXCEPTION);
			insertCreditModelRecord(false,CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_EXCEPTION, busId,null);
			
			map.put("creditReason", CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_EXCEPTION);
			creditCollegeDAO.updateJsSchoolBusCreditResult(map);
			throw new RuntimeException("调用核心征信接口，获取征信配置数据异常");
		}
		
		String _USCOREOpen = (String) (meMap.get(CreditCfgKeyConstants.UNIPAY_SORE));
		if (!CreditCfgKeyConstants.OPEN.equals(_USCOREOpen)) {
			log.info("调用银联评分-->调用开关关闭。");
			return true;
		}
		log.info("调用银联评分-->调用开关开启。");
		
		//查询最近一次数据报告，90天失效
		UnipayPersonalReport report=unipayDao.queryUnipayReport(cardNumber);
		if(report!=null){
			int interval=DateUtil.daysBetween(report.getCreateTime(), new Date(), true);
			if(interval>UnipayScoreConstants.INTERVAL_DAY)
				report=null;
		}
		
		//重新查询数据
		if(report==null){
			try {
				report=this.dealUnipayReport(cardNumber, idCard, name, busId, cityCode,null);
			} catch (Exception e) {
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_EXCEPTION);
				insertCreditModelRecord(false,CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_EXCEPTION, busId,null);
				
				map.put("creditReason", CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_EXCEPTION);
				creditCollegeDAO.updateJsSchoolBusCreditResult(map);
				throw new Exception("处理银联数据异常"+e.getMessage());
			}
		}
		
		//银联评分
		UnipayPersonalScore score=calScore(busId,report,cityCode,idCard,name);
		//根据规则征信
		int upa=score.getUpa1()+score.getUpa2();
		if(upa!=6){
			upa+=score.getUpa3()+score.getUpa4()+score.getUpa5()+score.getUpa6();
			if(Arrays.contains(UnipayScoreConstants.ED_RULE_A_RANGE, edScore) && upa<UnipayScoreConstants.ED_RULE_A_UPA){
				
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_UPA_1);
				insertCreditModelRecord(false,CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_UPA_1, busId,report.getId());
				
				map.put("creditReason", CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_UPA_1);
				creditCollegeDAO.updateJsSchoolBusCreditResult(map);
				
				return false;
			}
			if(Arrays.contains(UnipayScoreConstants.ED_RULE_B_RANGE, edScore) && upa<UnipayScoreConstants.ED_RULE_B_UPA){
				userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_UPA_2);
				insertCreditModelRecord(false,CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_UPA_2, busId, report.getId());
				
				map.put("creditReason", CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_UPA_2);
				creditCollegeDAO.updateJsSchoolBusCreditResult(map);
				return false;
			}
		}
		
		int upf=score.getUpf1()+score.getUpf2()+score.getUpf3()+score.getUpf4()+score.getUpf5()+score.getUpf6();
		if(Arrays.contains(UnipayScoreConstants.UPF_RULE_RANGE, upf)){
			userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_NOT_PASS,CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_UPF);
			insertCreditModelRecord(false,CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_UPF, busId, report.getId());
			
			map.put("creditReason", CreditCodeConstants.CREDIT_CHECK_UNIPAY_UNPASS_UPF);
			creditCollegeDAO.updateJsSchoolBusCreditResult(map);
			return false;
		}
		userCreditDataService.updateCR(creditRecord, StatusConstants.CREDIT_IS_PASS,CreditCodeConstants.CREDIT_RECORD_CLOSED);
		insertCreditModelRecord(true,CreditCodeConstants.CREDIT_CHECK_UNIPAY_PASS, busId, report.getId());
		map.put("creditReason", CreditCodeConstants.CREDIT_RECORD_CLOSED);
		creditCollegeDAO.updateJsSchoolBusCreditResult(map);
		return true;
	}
	
	private void insertCreditModelRecord(boolean result,String resultDesc,String busId,String dataId){
		UserCreditModelRecord record = new UserCreditModelRecord();
		Date now=new Date();
		int creditResult=result?StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS:StatusConstants.CREDIT_CHECKING_RESULT_FAILED;
		
		record.setCreditId(userCreditDataDAO.queryCreditIdByTotalId(busId));
		record.setModelRecordId(GenerateKey.getId(CommonConstants.USER_CREIDT_MODEL_RECORD_ID_PREFIX, ConfigUtils.getProperty("db_id")));
		record.setCreditModelType(CreditCodeConstants.CREDIT_TYPE_UNIPAY_MODEL);
		record.setCreditModel(CreditCodeConstants.CREDIT_TYPE_UNIPAY_MODEL_TYPE);
		record.setIsEffective("1"); //（0-无效；1-有效）
		record.setCreditCheckingResult(creditResult);//征信模块判断结果：10 征信通过 20征信不通过
		record.setCreditCheckingResultDesc(resultDesc);
		record.setCreateTime(now);
		record.setUpdateTime(now);
		record.setOperator(CommonConstants.DEFAULT_OPERATOR);
		record.setDataId(dataId);
		userCreditDataDAO.insertCreditModelRecord(record);
	}


	@Override
	public Map<String, String> listCityCode() {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String,String>();
		List<UnipayCity> list=unipayDao.listCityCode();
		for(UnipayCity city:list){
			map.put(city.getCityName(), city.getCityCode());
		}
		return map;
	}
	
}
