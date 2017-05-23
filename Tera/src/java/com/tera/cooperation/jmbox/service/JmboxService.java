package com.tera.cooperation.jmbox.service;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.oss.model.OSSObject;
import com.tera.accounting.model.Accountting;
import com.tera.accounting.service.AccounttingService;
import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.cooperation.jmbox.constant.JMLendingConstant;
import com.tera.cooperation.jmbox.model.JmboxLog;
import com.tera.cooperation.jmbox.model.form.DefaultCustomersInfo;
import com.tera.cooperation.jmbox.model.form.DefaultInfoBean;
import com.tera.cooperation.jmbox.model.form.DefaultInfoResponseBean;
import com.tera.cooperation.jmbox.model.form.JMChannalDuplicateBean;
import com.tera.cooperation.jmbox.model.form.JMChannalDuplicateResponseBean;
import com.tera.cooperation.jmbox.model.form.JMCreditLendingFBean;
import com.tera.cooperation.jmbox.model.form.JMProjecQueryBean;
import com.tera.cooperation.jmbox.model.form.JMProjecResponseBean;
import com.tera.cooperation.jmbox.model.form.JMQueryObject;
import com.tera.credit.constant.Constants;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.model.CreditExt;
import com.tera.credit.model.WageFlow;
import com.tera.credit.model.form.RepelLoanFBean;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditDecisionService;
import com.tera.credit.service.CreditExtService;
import com.tera.credit.service.CreditSignService;
import com.tera.credit.service.WageFlowService;
import com.tera.img.model.Img;
import com.tera.img.service.ImgService;
import com.tera.payment.model.Payment;
import com.tera.payment.service.PaymentService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.ResultObj;
import com.tera.sys.service.DataDictionaryService;
import com.tera.util.DateUtils;
import com.tera.util.IOUtils;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.StringUtils;

/**
 * 积木盒子 交互服务类
 * @author XunXiake
 *
 */
@Service("jmboxService")
public class JmboxService {

	private final static Logger log = Logger.getLogger(JmboxService.class);

	@Autowired(required=false) //自动注入
	private CreditExtService creditExtService;
	@Autowired(required=false) //自动注入
	private DataDictionaryService<DataDictionary> dataDictionaryService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	@Autowired(required=false) //自动注入
	private CreditSignService creditSignService;
	@Autowired(required=false) //自动注入
	private JmboxLogService jmboxLogService;
	@Autowired(required=false) //自动注入
	private ImgService imgService;

	@Autowired(required=false) //自动注入
	private ContractService contractService;
	@Autowired(required=false) //自动注入
	private PaymentService<Payment> paymentService;
	
	@Autowired(required=false) //自动注入
	private CreditAppService creditAppService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;

	@Autowired(required=false) //自动注入
	private CreditDecisionService creditDecisionService;
	
	@Autowired(required=false) //自动注入
	private AccounttingService<Accountting> accounttingService;
	
	@Autowired(required=false) //自动注入
	private JmboxRepelLoanService jmboxRepelLoanService;
	
	@Autowired(required=false) //自动注入
	private WageFlowService wageFlowService;
	
	/** 
	 * 积木盒子 项目推送 请求服务 接口
	 * @param creditApp
	 * @param decision
	 * @param contract
	 * @param loginId
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	public ResultObj reqJmBox(CreditApp creditApp,CreditDecision decision,Contract contract,String loginId,String orgId) throws Exception{
		
		String spid =JMLendingConstant.SPID;
		String userKey = JMLendingConstant.USERKEY;
		String url = JMLendingConstant.LENDING_URL;
		
//		String[] classTypes=JMLendingConstant.CLASS_TYPES;
        String[] incomeClassTypes=null;
       if(decision.getProduct().indexOf("工薪贷")!=-1){
    	   incomeClassTypes=JMLendingConstant.CLASS_ORDER_PAYROLL;
       }else if(decision.getProduct().indexOf("精英贷")!=-1){
    	   incomeClassTypes=JMLendingConstant.CLASS_ORDER_ELITE;
       }else if(decision.getProduct().indexOf("业主贷")!=-1){
    	   incomeClassTypes=JMLendingConstant.CLASS_ORDER_OWNER;
       }
        
        
        JMCreditLendingFBean jmBean=this.getJMLendingBean(creditApp, decision, contract);
        //SPID 参与排序
        jmBean.setSPID(spid);
        List<String> jmBeanProList=jmBean.getPropertyList();
        //对象转 Map
        Map<String, Object> jmBeanMap = ObjectUtils.describe(jmBean);
        //参数拼接 字符串
        String TicketStr = ""; 
        
        //创建请求头信息
        MultipartEntity entity = new MultipartEntity();
        for (String proName : jmBeanProList) {
        	String zhi="";
        	Object obj=jmBeanMap.get(proName);
        	if (obj instanceof Integer) {
        	    int value = ((Integer) obj).intValue();
        	    zhi=value!=0?String.valueOf(value):null;
        	   } else if (obj instanceof String) {
        	    zhi = (String) obj;
        	   } else if (obj instanceof Double) {
        	    double d = ((Double) obj).doubleValue();
        	    zhi=d!=0?String.valueOf(d):null;
        	   }  else if (obj instanceof Date) {
        	    Date d = (Date) obj;
        	    zhi=DateUtils.toDateString(d);
        	   }  
        	if(zhi!=null&&!"".equals(zhi)){
        		log.info(proName+":"+zhi);
        		TicketStr+=zhi;
        		entity.addPart(proName, new StringBody(zhi, Charset.forName("UTF-8")));
        	}
		}
        //拼接 约定的 KEY
        TicketStr+=userKey;
        log.info("TicketStr:"+TicketStr);
        //计算MD5
        String Ticket = StringUtils.md5(TicketStr).toUpperCase();
        log.info("Ticket:"+Ticket);
        
        Map<String, Object> queryMap =new HashMap<String, Object>();
		queryMap.put("appId", creditApp.getAppId());
		//查询图片
		List<Img> imgList = imgService.queryList(queryMap);
		if(imgList!=null && imgList.size()>0){
			
			long max=20*1024*1024;
			long shiji=0;
			String fileNames="";
			
			//必传 没有了，统一改成选传 
/*			for (String classType : classTypes) {
				String[] types=classType.split("_");
				boolean isif=false;
				for (String type : types) {  //第一个类型不存在的时候 才去取 第二个类型
					for (Img img : imgList) {	
						if(img.getFileName().startsWith(type)){
							OSSObject ossObj=imgService.aliyunOSSGetOSSObject(img.getImgPath());
							shiji+=ossObj.getObjectMetadata().getContentLength();
							fileNames+=img.getFileName()+",";
							if(shiji<max){
								InputStream imgstm=ossObj.getObjectContent();
								//把图片读出来放到请求里面
								entity.addPart(img.getFileName(),new InputStreamBody(imgstm,img.getFileName()));
								isif=true;
							}else{
								log.info("积木盒子放款请求失败，必传附件大于 20M。当前大小："+shiji/1024/1024+"M。当前所有文件："+fileNames);
								return new ResultObj("积木盒子放款请求失败，必传附件大于 20M", null, false);
							}
						}
					}
					if(isif){
						break;
					}
				}
			}*/
			
			income:for (String classType:incomeClassTypes) {
				String[] types=classType.split("_");
				boolean isif=false;
				for (String type : types) {  //第一个类型不存在的时候 才去取 第二个类型
					for (Img img : imgList) {	
						if(img.getFileName().startsWith(type)){
							OSSObject ossObj=imgService.aliyunOSSGetOSSObject(img.getImgPath());
							long dqMax=shiji+ossObj.getObjectMetadata().getContentLength();
							if(dqMax<=max){
								shiji=dqMax;
								InputStream imgstm=ossObj.getObjectContent();
								//把图片读出来放到请求里面
//								entity.addPart(img.getFileName(),new InputStreamBody(imgstm,img.getFileName()));
								entity.addPart(img.getFileName(),new ByteArrayBody(IOUtils.getBytes(imgstm),img.getFileName()));
								fileNames+=img.getFileName()+",";
								isif=true;
							}else{
								//非必传附件 超出的时候 就  不再传送 跳出循环
								log.info("积木盒子产品推送（ContractNo:"+contract.getContractNo()+"），传附件过大；当前大小："+shiji/1024/1024+"M。当前所有文件："+fileNames);
								break income;
							}
						}
					}
					if(isif){
						break;
					}
				}
			}
			log.info("积木盒子产品推送（ContractNo:"+contract.getContractNo()+"），最终推送大小："+shiji/1024/1024+"M。最终推送文件："+fileNames);
		}	
		//发送请求
		ClientConnectionManager connManager = new PoolingClientConnectionManager();
		DefaultHttpClient client = new DefaultHttpClient(connManager);
        try {
        	client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 600000); 
        	client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 600000);
        	HttpPost request = new HttpPost(url);
            request.setEntity(entity);
            request.addHeader("Ticket", Ticket);
            HttpResponse response = client.execute(request);
            HttpEntity httpentity = response.getEntity();
            if (httpentity != null) {
            	//打印响应内容长度
//            	System.out.println("Response content length: " + httpentity.getContentLength());
            	//打印响应内容
//            	System.out.println("Response content: " + EntityUtils.toString(httpentity));
            	String returnJson=EntityUtils.toString(httpentity);
            	
                log.info("Response content（ContractNo:"+contract.getContractNo()+"）: " +returnJson);
                
            	Map<String, Object> rtMap=JSONObject.fromObject(returnJson);
            	String rtStatus=(String)rtMap.get("Status");
            	String rtMessage=(String)rtMap.get("Message");
            	boolean success=false;
            	if("OK".equalsIgnoreCase(rtStatus)){
            		success=true;
            	}
            	JmboxLog jmLog=new JmboxLog();
            	jmLog.setAppId(creditApp.getAppId());
            	jmLog.setContractNo(contract.getContractNo());
            	jmLog.setType("1");
            	jmLog.setOperator(loginId);
            	jmLog.setOrgId(orgId);
            	jmLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
            	jmLog.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            	jmLog.setState(success?"1":"2");
            	jmLog.setJmProjectId(rtMap.get("ProjectID").toString());
            	jmLog.setJmChineseName((String)rtMap.get("ChineseName"));
            	jmLog.setJmIdentityNumber((String)rtMap.get("IdentityNumber"));
            	jmLog.setJmStatus((String)rtMap.get("Status"));
            	jmLog.setJmMessage((String)rtMap.get("Message"));
            	jmboxLogService.add(jmLog);
            	
            	//推送成功，更新合同内部推送ID
            	if(success){
            		contract.setChannelKeyId(jmLog.getJmProjectId());
            		contract.setChannelState("1");
            		contractService.updateOnlyChanged(contract);
            	}
            	
            	return new ResultObj("积木盒子调用消息:"+rtStatus+","+rtMessage, rtMap, success);
        	}else{
        		return new ResultObj("积木盒子放款请求异常。", null, false);
        	}
		} catch (Exception e) {
			log.error("积木盒子 产品放款请求 接口 调用异常（ContractNo:"+contract.getContractNo()+"）：", e);
			return new ResultObj("积木盒子 产品放款请求 接口 调用异常："+e.toString(), null, false);
		} finally {
           client.getConnectionManager().shutdown();
        }
	}
	
	/**
	 * 得到 积木盒子 发送请求的 数据对象
	 * @param creditApp
	 * @param decision
	 * @param contract
	 * @return
	 * @throws Exception 
	 */
	public JMCreditLendingFBean getJMLendingBean(CreditApp creditApp,CreditDecision decision,Contract contract) throws Exception{
		JMCreditLendingFBean jmBean=new JMCreditLendingFBean();		
		
		Map<String, Object> queryMap = new HashMap<String, Object>();//贷款用途;单位：45  
		queryMap.put("keyName", "creditusage1");
		queryMap.put("keyProp", creditApp.getUseage1());
		List<DataDictionary> useage1List = this.dataDictionaryService.queryList(queryMap);
		if(null != useage1List && useage1List.size() > 0){
			queryMap.put("keyName", "creditusage2");
			queryMap.put("keyProp", creditApp.getUseage2());
			queryMap.put("parentKeyProp", creditApp.getUseage1());
			List<DataDictionary> useage2List = this.dataDictionaryService.queryList(queryMap);
			jmBean.setDebtUsage(useage1List.get(0).getKeyValue());
			if(null != useage2List && useage2List.size() > 0){
				jmBean.setDebtUsage(useage1List.get(0).getKeyValue() + ":" + useage2List.get(0).getKeyValue());
			}
		}
		
		jmBean.setRepaymentSource("工作所得"); //还款来源;单位：50；取值：固定值 ：工作所得    
		jmBean.setChineseName(creditApp.getName()); //姓名;单位：20                                     
		
		String idNo = creditApp.getIdNo();
		//算出年龄，jmBean.setAge(); //年龄;取值：根据身份证计算年龄，根据进件时间
		int year1 = Integer.parseInt(idNo.substring(6, 10)); 
//        int month1 = Integer.parseInt(idNo.substring(10, 12)); 
//        int day1 = Integer.parseInt(idNo.substring(12, 14)); 
        Calendar c = Calendar.getInstance();
        c.setTime(creditApp.getInputTime());
        int age = c.get(Calendar.YEAR) - year1;
//		int age = DateUtils.getYearRange(DateUtils.getDateYMD(year1 + "-" + month1 + "-" + day1), creditApp.getInputTime());
		jmBean.setAge(age);
		//算出性别，jmBean.setSex(); //性别;取值：根据身份证分析性别；备注：男/女
		if (idNo.length() == 15){
			if(Integer.parseInt(idNo.substring(14,15)) % 2 == 1) { 
				jmBean.setSex("男");
			} else { 
				jmBean.setSex("女"); 
			}
		}else if(idNo.length() == 18){
			if(Integer.parseInt(idNo.substring(16,17)) % 2 == 1) { 
				jmBean.setSex("男"); 
			} else { 
				jmBean.setSex("女");
			}
		}else if(idNo.length() != 15 || idNo.length() != 18){
			jmBean.setSex("不详");
		}
		
		jmBean.setPhone(creditApp.getMobile()); //手机号码;单位：20                                       
		jmBean.setIdentityNumber(creditApp.getIdNo()); //身份证号;单位：18                              
		jmBean.setPostAddress(creditApp.getAddProvince()+creditApp.getAddCity()+creditApp.getAddCounty()+creditApp.getAddress()); //通讯地址;单位：100；取值：居住地址                
		jmBean.setAccountLocation(creditApp.getKosekiProvince()+creditApp.getKosekiCity()); //户籍地址;单位：100                            
		jmBean.setCurrentAddressLiveTime(String.valueOf(creditApp.getAddYear())); //现地址居住时间（年）;单位：20   
		
		String marriage = creditApp.getMarriage(); //婚姻状况;备注：婚姻状况
		if("01".equals(marriage)){
			jmBean.setMaritalStatus("未婚"); 
		}else if("02".equals(marriage)){
			jmBean.setMaritalStatus("已婚"); 
		}else if("03".equals(marriage)){
			jmBean.setMaritalStatus("离异"); 
		}else if("04".equals(marriage)){
			jmBean.setMaritalStatus("丧偶"); 
		}else if("99".equals(marriage)){
			jmBean.setMaritalStatus("其他"); 
		}
//		jmBean.setMaritalStatus(creditApp.getMarriage()); //婚姻状况;备注：婚姻状况                         
		jmBean.setSpouseName(creditApp.getSpouseName()); //配偶姓名;单位：20                                  
		jmBean.setSpouseID(creditApp.getSpouseIdNo()); //配偶身份证号;单位：18                                
		jmBean.setSpousePhone(creditApp.getSpouseMobile()); //配偶手机号码;单位：20                             
		jmBean.setSpouseWorkUnit(creditApp.getSpouseComName()); //配偶工作单位名称;单位：50                      
		jmBean.setCompanyName(creditApp.getComName()); //工作单位;单位：100；取值：本人公司信息     
		
		String companyType = creditApp.getComType(); //单位性质;
		if("01".equals(companyType)){
			jmBean.setCompanyNature("国企"); 
		}else if("02".equals(companyType)){
			jmBean.setCompanyNature("民企");
		}else if("03".equals(companyType)){
			jmBean.setCompanyNature("合资");
		}else if("04".equals(companyType)){
			jmBean.setCompanyNature("外资");
		}else if("05".equals(companyType)){
			jmBean.setCompanyNature("个体");
		}else if("99".equals(companyType)){
			jmBean.setCompanyNature("其他");
		}
//		jmBean.setCompanyNature(creditApp.getComType()); //单位性质;                                       
		jmBean.setCompanyTel(creditApp.getComPhone()); //工作单位座机;取值：单位电话                        
		jmBean.setCompanyAddress(creditApp.getComProvince()+creditApp.getComCity()+creditApp.getComCounty()+creditApp.getComAddress()); //单位地址;单位：100                             
		jmBean.setWorkStartDate(creditApp.getComAddDate()); //本工作开始日期;单位：年-月-日；取值：入职时间   
		jmBean.setPosition(creditApp.getComDuty()); //职务;单位：50；备注：职务                            
		
		//算出工作年限，进件时间，入职时间
		int workAge = DateUtils.getYearRange(creditApp.getComAddDate(), creditApp.getInputTime());
		jmBean.setWorkYears(workAge); //总工龄;单位：年；取值：根据入职时间计算             
		
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", creditApp.getAppId());
		fmap.put("key", "3");
		List<CreditExt> creditExtList = creditExtService.queryList(fmap);
		jmBean.setHasBadRecord("0".equals(creditExtList.get(0).getValue()) ? "无" : "有"); //是否有诉讼记录;取值：取 重要情况说明；备注：有/无
		
		jmBean.setMainBusinessDescrib(creditApp.getFirmMainBusiness()); //主营业务描述;单位：100；取值：业主贷 
		if(creditApp.getFirmManageDate()!=null){
			int years = DateUtils.getYearRange(creditApp.getFirmManageDate(), creditApp.getInputTime());
			int foundAge = years <= 0 ? 1 : years;
			jmBean.setFoundYears(foundAge); //成立年限;取值：进件时间 减去 本地经营时间 求年份  ，最少为1
		}
		
		jmBean.setShareHolderRate(creditApp.getFirmShare()); //融资人持股比例;取值：占股比例 
		jmBean.setAccountNo(contract.getBankAccount()); //单位：50 
		jmBean.setBankName(getBankName(contract.getBankName())); //签约的银行，11月24日改用bankName，单位：50  
		Map<String, Object> queryMap1 = new HashMap<String, Object>();
		queryMap1.put("keyName", "bankProv");
		queryMap1.put("keyProp", contract.getBankProvince());
		List<DataDictionary> provinceList = this.dataDictionaryService.queryList(queryMap1);	
		if(null != provinceList && provinceList.size() > 0){
			Map<String, Object> queryMap2 = new HashMap<String, Object>();
			queryMap2.put("keyName", "bankCity");
			queryMap2.put("keyProp", contract.getBankCity());
			queryMap2.put("parentKeyProp", contract.getBankProvince());
			jmBean.setProvence(getProvenceCityCounty(provinceList.get(0).getKeyValue()));//开户省
			List<DataDictionary> cityList = this.dataDictionaryService.queryList(queryMap2);
			if(null != cityList && cityList.size() > 0){
				jmBean.setArea(getProvenceCityCounty(cityList.get(0).getKeyValue()));//开户市
			}
		}
//		jmBean.setProvence(getProvenceCityCounty(contract.getBankProvince())); //单位：50                                
//		jmBean.setArea(getProvenceCityCounty(contract.getBankCity())); //单位：50                                    
		jmBean.setSubbranchBank(contract.getBankBranch()); //单位：50  
		jmBean.setApplyCity(getProvenceCityCounty(contract.getSignCity())); //贷款提交城市,单位：50                               
//		jmBean.setUserName(); //单位：50，备注：可以不填，由积木盒子生产
		jmBean.setLenderAmount((int)decision.getAmount()); //单位：元    放款金额
		
		Product product = productService.queryByName(decision.getProduct());
        double yhkje = creditSignService.getYhkje(contract.getLoanAmount(), product);
        yhkje = MathUtils.roundUp(yhkje, 2);
        jmBean.setRepaymentAmountByMonth((int)yhkje); //单位：元   月还款额
        
        jmBean.setType(decision.getProduct()); //产品类型;单位：50      决策                                  
        jmBean.setFinancingAmount((int)contract.getLoanAmount()); //意向融资金额（元）;单位：元              决策合同金额     
        jmBean.setBatch(contract.getLoanPeriod()); //贷款期限（月）;单位：月        合同产品期限
        //2015-03-18新增字段
        jmBean.setThirdPartyScore(creditApp.getRuleScore());//信用评分等级
        //2015-05-25新增字段
        jmBean.setHasCar("无");
        jmBean.setHasHouse("无");
        	//计算所有已存在的流水平均值，加起来。
        Map<String, Object> wageMap = new HashMap<String, Object>();
        wageMap.put("appId", creditApp.getAppId());
        List<WageFlow> wageFlowList = wageFlowService.queryList(wageMap);
        if(wageFlowList.size()>0){
        	double checkedAmount = 0.00 ;
        	for(int i=0;i<wageFlowList.size();i++){
        		checkedAmount = checkedAmount + wageFlowList.get(i).getAvgAmount();
        	}
        	jmBean.setCheckedAmount((int)checkedAmount);
        }
        
		return jmBean;
	}	
	
	/**
	 * 取积木盒子省、市、县(过滤掉省、市、县)
	 * @param provenceCityCounty
	 * @return
	 */
	private String getProvenceCityCounty(String provenceCityCounty){
		if(provenceCityCounty.length() <= 2)
			return provenceCityCounty;
		String sign = provenceCityCounty.substring(provenceCityCounty.length() - 1, provenceCityCounty.length());
		if("省".equals(sign) || "市".equals(sign) || "县".equals(sign)){
			provenceCityCounty = provenceCityCounty.substring(0, provenceCityCounty.length() - 1);
		}
		if("广西壮族自治区".equals(provenceCityCounty)){
			provenceCityCounty = "广西";
		}
		return provenceCityCounty;
	}
	
	/**
	 * 取积木盒子要求格式的银行名称(除了中国银行去掉中国两字)
	 * @param bankName
	 * @return
	 */
	private String getBankName(String bankName){
		if(null == bankName || "中国银行".equals(bankName))
			return bankName;
		if(bankName.length() >=2 && "中国".equals(bankName.substring(0, 2))){
			return bankName.substring(2, bankName.length());
		}else{
			return bankName;
		}
	}
	
	/**
	 * 积木盒子 项目推送后的 查询 服务   
	 * 	批处理调用 业务相关
	 * @param contract
	 * @return
	 * @throws Exception
	 */
	public JMProjecResponseBean reqQueryJmBox(Contract contract) throws Exception{
		//返回结果
		JMProjecResponseBean espBean=null;
		
		//项目 查询 URL
		String projecQueryurl = JMLendingConstant.PROJEC_QUERY_URL;
		String spid =JMLendingConstant.QUERY_ID;
		String userKey = JMLendingConstant.QUERY_KEY;
       
        JMProjecQueryBean pqb=new JMProjecQueryBean(spid, contract.getChannelKeyId());
        List<JMProjecResponseBean> jgList=this.requestJMQuery(pqb,userKey, projecQueryurl,JMProjecResponseBean.class);
        
        JmboxLog jmLog=new JmboxLog();
    	jmLog.setAppId(contract.getLoanAppId());
    	jmLog.setContractNo(contract.getContractNo());
    	jmLog.setType("2");
    	jmLog.setOperator("sysauto");
    	jmLog.setOrgId("86");
    	jmLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
    	jmLog.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    	jmLog.setJmProjectId(contract.getChannelKeyId());
    	jmLog.setJmChineseName(contract.getBankAccountName());
    	jmLog.setJmIdentityNumber(contract.getLoanIdNo());
    	jmLog.setState("0");
    	jmboxLogService.add(jmLog);
        
        if(jgList!=null){
        	espBean=jgList.get(0);
        	
        	jmLog.setJmStatus(espBean.getStatus());
        	jmLog.setJmMessage("积木项目结果查询 成功");
        	jmLog.setState("1");
        	
        	if("5".equals(espBean.getStatus())){
        		
        		contract.setChannelTime(new Timestamp(espBean.getBiddealline().getTime()));
        		contract.setState("2");
        		//更新 还款计划 与 支付信息状态
        		paymentService.paymentSuccessUpdate(contract.getContractNo(), espBean.getBiddealline());
        		
	    		//实际上里面只有一个BpmTask对象
	    		List<BpmTask> bpmTasks =  processService.getProcessInstanceByBizKey(contract.getLoanAppId());
	    		BpmTask task = bpmTasks.get(0);
	    		if (task!=null && "放款".equals(task.getState())) {
	    			task.setOperator(BpmConstants.SYSTEM_SYS);
		    		task = processService.goNext(task, Constants.PROCESS_END_APP, task.getProcesser());
	    		}
	    		//更新 APP 状态
	    		Map<String, Object> appMap = new HashMap<String, Object>();
	    		appMap.put("appId", contract.getLoanAppId());
	    		CreditApp creditApp = creditAppService.queryList(appMap).get(0);
	    		creditApp.setState("23");
	    		creditApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
	    		creditAppService.update(creditApp);
        		//记账
        		Map<String, Object> fmap = new HashMap<String, Object>();
        		fmap.put("appId", contract.getLoanAppId());
        		fmap.put("state", "1");
        		fmap.put("type", "0");
        		//最终决策
        		CreditDecision creditDecision = creditDecisionService.queryList(fmap).get(0);
        		Accountting account=new Accountting();
        		account.setInOut("2");
        		account.setContractNo(contract.getContractNo());
        		account.setSource("");
        		account.setPeriodNum(0);
        		account.setState("1");
        		account.setOperator(BpmConstants.SYSTEM_SYS);
        		account.setOrgId(contract.getOrgId());
        		long time=System.currentTimeMillis();
        		account.setCreateTime(new Timestamp(time));
        		account.setUpdateTime(new Timestamp(time));
    			account.setId(0);
    			account.setAccount("出借金额");
    			account.setSubject("业务往来-放款本金");
    			account.setPlanAmount(creditDecision.getAmount());
    			account.setActualAmount(creditDecision.getAmount());
    			accounttingService.add(account);
        	}else if("7".equals(espBean.getStatus())){
        		//拒贷处理
        		RepelLoanFBean repelLoanFBean = new RepelLoanFBean();
        		repelLoanFBean.setAppId(contract.getLoanAppId());
        		repelLoanFBean.setFeedbackDescribe("积木盒子项目终止，系统自动拒贷！");
        		jmboxRepelLoanService.jmRepelLoan(repelLoanFBean, BpmConstants.SYSTEM_SYS);
        	}
        	//更新合同
        	contract.setChannelState(espBean.getStatus());
        	contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        	contractService.update(contract);
        }else{

        	jmLog.setJmMessage("积木项目结果查询 失败");
        	jmLog.setState("2");
        	
        }
        jmboxLogService.updateOnlyChanged(jmLog);
        
        return espBean;
	}

	/**
	 * 向JM推送 违约客户信息
	 * 	批处理调用 业务相关
	 * @param contract
	 * @return
	 * @throws Exception
	 */
	public DefaultInfoResponseBean sendDefaultInfo(List<DefaultCustomersInfo> infoList) throws Exception{
		//返回结果
		DefaultInfoResponseBean repBean=null;
		//违约信息 推送
		String dueinfoUrl =JMLendingConstant.DUEINFO_URL;
		String spid =JMLendingConstant.DUEINFO_ID;
		String userKey = JMLendingConstant.DUEINFO_KEY;
		
		JmboxLog jmLog=new JmboxLog();
    	jmLog.setAppId("");
    	jmLog.setContractNo("");
    	jmLog.setType("3");
    	jmLog.setOperator("sysauto");
    	jmLog.setOrgId("86");
    	jmLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
    	jmLog.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    	jmLog.setState("0");
    	jmLog.setJmProjectId("");
    	jmLog.setJmChineseName("");
    	jmLog.setJmIdentityNumber("");
    	jmLog.setJmStatus("");
    	jmLog.setJmMessage("违约信息推送 等待推送结果");
    	jmboxLogService.add(jmLog);
		
		DefaultInfoBean info = new DefaultInfoBean(spid,infoList);
		JmboxService jm=new JmboxService();
		List<DefaultInfoResponseBean> jg=jm.requestJMQuery(info,userKey,dueinfoUrl,DefaultInfoResponseBean.class);
		
		if(jg!=null && !jg.isEmpty()){
			repBean=jg.get(0);
			DefaultInfoResponseBean jgb=jg.get(0);
	    	jmLog.setJmStatus(jgb.getState());
	    	jmLog.setJmMessage("违约信息推送结果："+jgb.getMessage());
	    	if("200".equals(jgb.getState())){
	    		jmLog.setState("1");
	    	}else{
	    		jmLog.setState("2");
	    	}
	    	jmboxLogService.update(jmLog);
		}
        return repBean;
	}
	
	
	
	/**
	 * JM盒子  统一规范的 信息交互接口。
	 * @param projecId
	 * @param spid
	 * @param userKey
	 * @param url
	 */
	private <T> List<T> requestJMQuery(JMQueryObject queryBean,String userKey,String url,Class<T> objClass){
		String params=queryBean.getParamsAndMd5Key(userKey);
//        log.info("积木盒子项目查询 >>>参数:"+params);;
        url=url+"?"+params;
        log.info("积木盒子项目查询 >>>GET URL:"+url);
        List<T> rgList=null;
        //发送请求
		ClientConnectionManager connManager = new PoolingClientConnectionManager();
		DefaultHttpClient client = new DefaultHttpClient(connManager);
        try {
        	HttpGet request = new HttpGet(url);
//        	HttpPost request = new HttpPost(url);
            HttpResponse response = client.execute(request);
            HttpEntity httpentity = response.getEntity();
            if (httpentity != null) {
            	String returnJson=EntityUtils.toString(httpentity);
                log.info("Response content: " +returnJson);
                if(returnJson!=null && !returnJson.equals("")){
                	 if(returnJson.startsWith("[")){
                		 JSONArray jsonArray = JSONArray.fromObject(returnJson.toLowerCase());
                         JSONUtils.getMorpherRegistry().
                         registerMorpher(new DateMorpher(
                         		new String[] {"yyyy-MM-dd", 
                         				"yyyy-MM-dd HH:mm:ss", 
                         				"yyyy-mm-ddThh:mi:ss.mmmZ"}));
                         if(jsonArray!=null&&jsonArray.size()>0){
                         	rgList=new ArrayList<T>();
                         	for (int i = 0; i < jsonArray.size(); i++) {
                             	JSONObject jsonObj=jsonArray.getJSONObject(i);
                             	rgList.add((T)JSONObject.toBean(jsonObj,objClass));
             				}
                         }
                	 }else if(returnJson.startsWith("{")){
                		 rgList=new ArrayList<T>();
                		 JSONObject jsonObj = JSONObject.fromObject(returnJson.toLowerCase());
                         JSONUtils.getMorpherRegistry().
                         registerMorpher(new DateMorpher(
                         		new String[] {"yyyy-MM-dd", 
                         				"yyyy-MM-dd HH:mm:ss", 
                         				"yyyy-mm-ddThh:mi:ss.mmmZ"}));
                         rgList.add((T)JSONObject.toBean(jsonObj,objClass));
                	 }
                }
            }
		} catch (Exception e) {
			log.error("积木盒子项目查询接口异常:", e);
		} finally {
           client.getConnectionManager().shutdown();
        }
		return rgList;
	}
	
	/**
	 * 积木盒子渠道查重接口(根据姓名和身份证号)
	 * 
	 * @param name
	 * @param idCard
	 * @return
	 * @throws Exception 
	 */
	public JMChannalDuplicateResponseBean JMChannalDuplicate(String name,
			String idCard, String appId) throws Exception {

		String cid = JMLendingConstant.QUERY_ID;
		String userKey = JMLendingConstant.QUERY_KEY;
		String url = JMLendingConstant.CHANNAL_DUPLICATE_URL;

		JMChannalDuplicateBean bean = new JMChannalDuplicateBean(cid, name,idCard);

		List<JMChannalDuplicateResponseBean> responseList = 
			requestJMQuery(bean, userKey, url, JMChannalDuplicateResponseBean.class);
		
		// 保存交互日志
		JmboxLog jmLog = new JmboxLog();
		jmLog.setAppId(appId);
		jmLog.setJmProjectId(cid);
		jmLog.setJmChineseName(name);
		jmLog.setJmIdentityNumber(idCard);
		jmLog.setOperator(BpmConstants.SYSTEM_SYS);
		jmLog.setOrgId(Constants.ORG_CODE);
		jmLog.setContractNo("");
		jmLog.setType("2");
		jmLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
		jmLog.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		
		if (responseList == null) {
			jmLog.setState("2");
			jmLog.setJmStatus("失败");
			jmLog.setJmMessage("积木渠道查重接口调用失败。");
			jmboxLogService.add(jmLog);
			return null;
		} else {
			jmLog.setState("1");
			jmLog.setJmStatus("成功");
			jmLog.setJmMessage("积木渠道查重接口调用成功。");
			jmboxLogService.add(jmLog);
			return responseList.get(0);
		}
	}

	public static void main(String[] args){
		/*
		String spid =JMLendingConstant.QUERY_ID;
		String userKey = JMLendingConstant.QUERY_KEY;
		//项目 查询 URL
        String projecQueryurl = JMLendingConstant.PROJEC_QUERY_URL;

//        String repayPlanQueryUrl = JMLendingConstant.REPAY_PLAN_QUERY_URL;
        
        JMProjecQueryBean pqb=new JMProjecQueryBean(spid, "72652");
        
		JmboxService jm=new JmboxService();
		List<JMProjecResponseBean> jg=jm.requestJMQuery(pqb,userKey,projecQueryurl,JMProjecResponseBean.class);
		System.out.println(0);
		/*List<JMRepayPlanResponseBean> plList=jm.requestJMQuery(pqb,userKey,repayPlanQueryUrl,JMRepayPlanResponseBean.class);
		System.out.println(1);
		
		
		//违约信息 推送
		String dueinfoUrl =JMLendingConstant.DUEINFO_URL;
		String spid =JMLendingConstant.DUEINFO_ID;
		String userKey = JMLendingConstant.DUEINFO_KEY;
		
		String json="[" +
				"{\"projectId\":\"11111\",\"customerName\":\"客户姓名\",\"idCard\":\"111111111111\"," +
				"\"moneyOverDue\":6029.79,\"interestOverDue\":668.21,\"moneyLeave\":69604.92,\"payDate\":" +
				"\"2015-03-06\",\"loanBackDate\":\"2015-2-16 18:45:45\",\"dueDays\":29," +
				"\"reason\":\"\",\"overdueNumber\":1,\"lastModifyDate\":\"2015-04-04 01:23:13\"}" +
				"]";
		
		DefaultInfoBean info = new DefaultInfoBean(spid,json);
		JmboxService jm=new JmboxService();
		List<DefaultInfoResponseBean> jg=jm.requestJMQuery(info,userKey,dueinfoUrl,DefaultInfoResponseBean.class);
		
		
		JmboxService jm=new JmboxService();
		jm.JMChannalDuplicate("童浩测试", "131022198903270015");
		*/
	}
	
}
