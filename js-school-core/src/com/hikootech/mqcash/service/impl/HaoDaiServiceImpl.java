package com.hikootech.mqcash.service.impl;

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
import com.hikootech.mqcash.constants.CreditCfgKeyConstants;
import com.hikootech.mqcash.constants.CreditCodeConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dao.HaoDaiDao;
import com.hikootech.mqcash.dao.UserCreditDataDAO;
import com.hikootech.mqcash.dao.UserDAO;
import com.hikootech.mqcash.haodai.RiskUtil;
import com.hikootech.mqcash.haodai.UnicodeUtils;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.po.UserHdBxdjgcxjl;
import com.hikootech.mqcash.po.UserHdDkcfsq;
import com.hikootech.mqcash.po.UserHdGrbzxggcx;
import com.hikootech.mqcash.po.UserHdRequest;
import com.hikootech.mqcash.po.UserHdShanyinSyhmd;
import com.hikootech.mqcash.po.UserHdSxbzxrcx;
import com.hikootech.mqcash.service.ConfigCreditKvService;
import com.hikootech.mqcash.service.HaoDaiService;
import com.hikootech.mqcash.service.SysAlarmService;
import com.hikootech.mqcash.service.UserCreditDataService;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.JsonObjectUtils;
import com.hikootech.mqcash.util.UUIDTools;
import com.hikootech.mqcash.vo.HaoDaiVo;
import com.hikootech.mqcash.vo.UserHdcfsqRecord;
import com.hikootech.mqcash.vo.UserHdhmdRecord;
import com.hikootech.mqcash.vo.UserHdsxbzxRecord;
import com.hikootech.mqcash.vo.UserHdzxgrbzxRecord;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service("haoDaiService")
public class HaoDaiServiceImpl implements HaoDaiService{

	private static Logger log = LoggerFactory.getLogger(HaoDaiServiceImpl.class);
	
	@Autowired
	private HaoDaiDao haoDaiDao;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserCreditDataService userCreditDataService;
	@Autowired
	private UserCreditDataDAO userCreditDataDAO;
	@Autowired
	private SysAlarmService sysAlarmService;
	
	@Override
	public String requestHaoDai(Map<String, String> map,Map<String, String> meMap) {
		JSONObject jo = new JSONObject();
		
		String queryType = map.get("queryType");
		String userName = map.get("userName");
		String userIdCard = map.get("userIdCard");
		String mobileNumber = map.get("mobileNumber");
		String hdrequestId = map.get("hdrequestId");
		String totalId = map.get("totalId");
		
		jo.put("type", queryType);
		jo.put("name", userName);
		jo.put("idcard", userIdCard);//身份证号转换为大写
		jo.put("mobile",  mobileNumber);

		//   第一步     =============》发送数据，接受数据
		log.info("准备发送请求的有效数据为：" + jo.toString());
		RiskUtil rt = new RiskUtil();
		JSONObject jo2=null;
		try {
			jo2 = JSONObject.fromObject(rt.certify(jo));
		} catch (Exception e) {
			log.error("请求好贷网征信时发生错误",e);

			// 插入告警表
			String alarmContent = "(HAODAI)请求好贷，访问异常，姓名：" + userName + ",身份证号：" + userIdCard;
			sysAlarmService.alarm(alarmContent);

			return null;
		}
		
		String result = "" ;
		
		if (jo2 == null || EntityUtils.isEmpty(jo2.get("code")) ||  !"100000".equals(jo2.get("code").toString())) {
			String code = jo2 == null?"返回信息为空":EntityUtils.isNotEmpty(jo2.get("code"))?"返回码非100000，code：" +  jo2.get("code").toString():"返回码code为空。";
			log.error("好贷" +  code);
			return result;
		}
		log.info("接收返回的加密数据为：" + jo2.toString());
		
		
		if (EntityUtils.isEmpty(jo2.get("data") + "")) {
			log.info("jo2.get(data)为空" );
			return result;
		}
		
		String str = RiskUtil.decrypt(jo2.get("data") + "", RiskUtil.RISK_CONTROL_KEY);
		result = UnicodeUtils.ascii2native(str);
		log.info("返回数据解密编码后：" + str);

		try {
			UserHdRequest hdRequest=new UserHdRequest();
			
			hdRequest.setId(map.get("hdrequestId"));
			hdRequest.setTotalInfoId(map.get("totalId"));
			hdRequest.setType(map.get("queryType"));
			hdRequest.setIdCard(map.get("userIdCard"));
			hdRequest.setName(map.get("userName"));
			hdRequest.setMobile(map.get("mobileNumber"));
			hdRequest.setCreateTime(new Date());
			haoDaiDao.addhdRequest(hdRequest);
		} catch (Exception e) {
			 log.error("插入hdRequest对象入库发生错误",e);
		}
		
		
		//第二步     =============》接收数据存储
		/*******************************个人失信被执行查询:400 开始****************************************/
		try {
			String sxRecordsStr = JsonObjectUtils.getString(JSONObject.fromObject(result).get("sxbzxrcx"));
			if (EntityUtils.isNotEmpty(sxRecordsStr)) {
				String resultStr = JSONObject.fromObject(sxRecordsStr).get("sxggList").toString();
				log.info("(HAODAI)400报文-sxggList==>" + resultStr + ",sxbzxrcx==>" + sxRecordsStr);

				JSONArray records = JSONArray.fromObject(resultStr);
				
				List<UserHdSxbzxrcx> userHdSxbzxrcxList = new ArrayList<UserHdSxbzxrcx>();

				for (int i = 0; i < records.size(); i++) {
					UserHdsxbzxRecord rsp = (UserHdsxbzxRecord) JSONObject.toBean((JSONObject) records.get(i),UserHdsxbzxRecord.class);
					log.info("（HAODAI）个人失信被执行，命中,totalId-->" + totalId);
					try {
						UserHdSxbzxrcx hdSxcx=new UserHdSxbzxrcx(rsp);
						hdSxcx.setId(UUIDTools.getFormatUUID());
						hdSxcx.setRequestId(hdrequestId);
						hdSxcx.setCreateTime(new Date());
						userHdSxbzxrcxList.add(hdSxcx);
						
						this.addhdSxbzxrcx(hdSxcx);
					} catch (Exception e) {
						 log.error("插入UserHdSxbzxrcx对象入库发生错误",e);
					}
				}
			}else{
				log.info("(HAODAI)400报文-sxbzxrcx==>空" );
			}
		} catch (Exception e) {
			log.error("个人失信被执行查询:400发生错误",e);
			// 插入告警表
			String alarmContent = "(HAODAI)请求好贷，个人失信被执行查询:400发生错误，姓名：" + userName + ",身份证号：" + userIdCard;
			sysAlarmService.alarm(alarmContent);
		}
		
		/*******************************个人失信被执行查询:400 结束****************************************/
		
		/*******************************个人被执行公告查询:680 开始****************************************/
		try {
			String grRecordsStr = JsonObjectUtils.getString(JSONObject.fromObject(result).get("grbzxggcx"));
			if (EntityUtils.isNotEmpty(grRecordsStr)) {
				String grResultStr = JSONObject.fromObject(grRecordsStr).get("zxggList").toString();
				log.info("个人被执行公告查询:680报文-zxggList==>" + grResultStr);
				String grCodeStr = JSONObject.fromObject(grRecordsStr).get("code").toString();
				log.info("个人被执行公告查询:680报文-code==>" + grCodeStr);
				String grMsgStr = JSONObject.fromObject(grRecordsStr).get("msg").toString();
				log.info("个人被执行公告查询:680报文-grMsgStr==>" + grMsgStr);
				if ("s".equals(grCodeStr)) {
					
					JSONArray grRecords = JSONArray.fromObject(grResultStr);
					List<UserHdGrbzxggcx> userHdGrbzxggcxList = new ArrayList<UserHdGrbzxggcx>();
					
					for (int i = 0; i < grRecords.size(); i++) {
						UserHdzxgrbzxRecord grRsp = (UserHdzxgrbzxRecord) JSONObject.toBean((JSONObject) grRecords.get(i),UserHdzxgrbzxRecord.class);
								
						log.info("（HAODAI）个人被执行公告，命中,totalId-->" + totalId);

						try {
							UserHdGrbzxggcx record=new UserHdGrbzxggcx(grRsp);
							record.setId(UUIDTools.getFormatUUID());
							record.setRequestId(hdrequestId);
							record.setCreateTime(new Date());
							userHdGrbzxggcxList.add(record);
							
							this.addGrbzxggcx(record);
						} catch (Exception e) {
							 log.error("插入UserHdGrbzxggcx对象入库发生错误",e);
						}
					}
				}else{
					log.info("个人被执行公告查询:680报文-code==>" + grCodeStr + ",msg==>" + grMsgStr);
				}
			}else{
				log.info("个人被执行公告查询:680报文-grbzxggcx==>空" );
			}
		} catch (Exception e) {
			log.error("个人被执行公告查询:680 发生错误",e);
			 
			// 插入告警表
			String alarmContent = "(HAODAI)请求好贷，个人被执行公告查询:680 发生错误，姓名：" + userName + ",身份证号：" + userIdCard;
			sysAlarmService.alarm(alarmContent);
		}
	
		
		/*******************************个人被执行公告查询:680 结束****************************************/
		
		/*******************************借款重复查询次数信息查询:650 开始****************************************/
		try {
			String cfRecordsStr = JsonObjectUtils.getString(JSONObject.fromObject(result).get("hd_dkcfsq"));
			log.info("借款重复查询次数信息查询:650-hd_dkcfsq==>" + cfRecordsStr);
			if (EntityUtils.isNotEmpty(cfRecordsStr)) {
				String msg = JSONObject.fromObject(cfRecordsStr).get("Message").toString();
				String code = JSONObject.fromObject(cfRecordsStr).get("Code").toString();

				if ("1".equals(code)) {
					log.info("借款重复查询次数信息返回结果成功===========>Code:" + code + ",Message:" +  msg);
					JSONArray cfRecords = JSONArray.fromObject(msg);
					List<UserHdDkcfsq> userHdDkcfsqList = new ArrayList<UserHdDkcfsq>();

					for (int i = 0; i < cfRecords.size(); i++) {
						UserHdcfsqRecord cfRsp = (UserHdcfsqRecord) JSONObject.toBean((JSONObject) cfRecords.get(i),UserHdcfsqRecord.class);

						log.info("（HAODAI）借款重复查询次，命中,totalId-->" + totalId);
						try {
							UserHdDkcfsq dkcfsq=new UserHdDkcfsq(cfRsp);
							dkcfsq.setId(UUIDTools.getFormatUUID());
							dkcfsq.setRequestId(hdrequestId);
							dkcfsq.setCreateTime(new Date());
							userHdDkcfsqList.add(dkcfsq);
							
							this.addhdDkcfsq(dkcfsq);
						} catch (Exception e) {
							log.error("插入UserHdDkcfsq对象入库发生错误",e);
						}
					}
				}else{
					log.info("借款重复查询次数信息查询:650,Code:" + code + ",Message:" +  msg);
				}
			}else{
				log.info("借款重复查询次数信息查询:650-hd_dkcfsq==>空");
			}
		} catch (Exception e) {
			 log.error("借款重复查询次数信息查询:650 发生错误",e);
			// 插入告警表
			String alarmContent = "(HAODAI)请求好贷，借款重复查询次数信息查询:650 发生错误，姓名：" + userName + ",身份证号：" + userIdCard;
			sysAlarmService.alarm(alarmContent);
		}
		
		
		/*******************************借款重复查询次数信息查询:650结束****************************************/
	
		/*******************************黑名单查询：641 开始****************************************/
		try {
			String hmdRecordsStr = JsonObjectUtils.getString(JSONObject.fromObject(result).get("shanyin_syhmd"));
			log.info("黑名单查询：641报文-hmdRecordsStr===>" + hmdRecordsStr);
			if (EntityUtils.isNotEmpty(hmdRecordsStr)) {
				String hmdMessage = JSONObject.fromObject(hmdRecordsStr).get("message").toString();
				log.info("黑名单查询：641报文-message===>" + hmdMessage);
				
				String result_641 = JSONObject.fromObject(hmdRecordsStr).get("result").toString();
				if ("0".equals(result_641)) { //返回代码 0：请求成功 1：身份验证失败2：权限不足 3：余额不足 4：输入参数错误
					String hmdmatches = JSONObject.fromObject(hmdRecordsStr).get("matches").toString();
					log.info("matches:" + hmdmatches);

					JSONArray hmdRecords = JSONArray.fromObject(hmdmatches);
					List<UserHdShanyinSyhmd> userHdShanyinSyhmdList = new ArrayList<UserHdShanyinSyhmd>();

					log.info("hmdRecords:" + hmdRecords);
					for (int i = 0; i < hmdRecords.size(); i++) {
						UserHdhmdRecord hmdRsp = (UserHdhmdRecord) JSONObject.toBean((JSONObject) hmdRecords.get(i),UserHdhmdRecord.class);
								
						log.info("（HAODAI）黑名单查询，命中,totalId-->" + totalId);
						try {
							UserHdShanyinSyhmd hdHmd=new UserHdShanyinSyhmd(hmdRsp);
							hdHmd.setId(UUIDTools.getFormatUUID());
							hdHmd.setRequestId(hdrequestId);
							hdHmd.setCreateTime(new Date());
							userHdShanyinSyhmdList.add(hdHmd);
							
							this.addhdSyhmd(hdHmd);
						} catch (Exception e) {
							log.error("插入UserHdShanyinSyhmd对象入库发生错误",e);
						}
					}
				}else{
					log.error("借款重复查询次数信息查询:641,result:" + result_641 + ",message:" +  hmdMessage);
				}
			}else{
				log.info("黑名单查询：641报文-hmdRecordsStr===>空" );
			}

		} catch (Exception e) {
			 log.error("黑名单查询：641 发生错误",e);
			// 插入告警表
			String alarmContent = "(HAODAI)请求好贷，黑名单查询：641 发生错误，姓名：" + userName + ",身份证号：" + userIdCard;
			sysAlarmService.alarm(alarmContent);
		}
		
		/*******************************黑名单查询：641结束****************************************/
		
		/*******************************被信贷机构查询次数:500 开始****************************************/
		try {
			String bxRecordsStr = JsonObjectUtils.getString(JSONObject.fromObject(result).get("bxdjgcxjl"));
			log.info("被信贷机构查询次数:500报文-bxRecordsStr=======>" + bxRecordsStr);
			if (EntityUtils.isNotEmpty(bxRecordsStr)) {
				UserHdBxdjgcxjl hdBxd=new UserHdBxdjgcxjl();
				hdBxd.setId(UUIDTools.getFormatUUID());
				hdBxd.setRequestId(hdrequestId);
				hdBxd.setCreateTime(new Date());
				boolean addFlag=false; //是否入库标志
				
				List<UserHdBxdjgcxjl> userHdBxdjgcxjlList = new ArrayList<UserHdBxdjgcxjl>();

				// 由于返回报文中key值为1到8 ，故此处循环取值
				for (int i = 1; i <= 8; i++) {
					String iString = String.valueOf(i);
					if (JSONObject.fromObject(bxRecordsStr).get(iString) != null) {

						String string1 = JSONObject.fromObject(bxRecordsStr).get(iString).toString();
						
						addFlag=true;
						log.info("机构代码=》"+i+":" + string1 );
						int lastYear = (int) JSONObject.fromObject(string1).get("12");
						int lastSixMonth = (int) JSONObject.fromObject(string1).get("6");
						int lastThreemonth = (int) JSONObject.fromObject(string1).get("3");
						int lastOneMonth = (int) JSONObject.fromObject(string1).get("1");

						//读取配置表中的最大次数
						int maxTimes = 3;
						try {
							maxTimes = Integer.parseInt(meMap.get(CreditCfgKeyConstants.CREDIT_HD_QUERYTIMES_MAX));

						} catch (Exception e) {
							log.error("从kv表中读取key值为：" + CreditCfgKeyConstants.CREDIT_HD_QUERYTIMES_MAX + "的数据，转换为int格式发生错误", e.getMessage());
						}

						// 如果查询次数大于设定值，则证明命中
						if (lastYear > maxTimes) {
							log.info("（HAODAI）被查询次数，命中,totalId-->" + totalId);
						}

						switch (i) {
						//银行
						case 1:{
							hdBxd.setBank(lastOneMonth+","+lastThreemonth+","+lastSixMonth+","+lastYear);
							break;
						}
						
						//	小贷
						case 2:{
							hdBxd.setSmallLoans(lastOneMonth+","+lastThreemonth+","+lastSixMonth+","+lastYear);			
							break;
						}
						
						//p2p
						case 3:{
							hdBxd.setP2p(lastOneMonth+","+lastThreemonth+","+lastSixMonth+","+lastYear);
							break;
						}
						
						//消费金融
						case 4:{
							hdBxd.setConsumerFinance(lastOneMonth+","+lastThreemonth+","+lastSixMonth+","+lastYear);
							break;
						}
						
						//融资租凭
						case 5:{
							hdBxd.setCapitalLeases(lastOneMonth+","+lastThreemonth+","+lastSixMonth+","+lastYear);
							break;
						}
						
						//商业保理
						case 6:{
							hdBxd.setCommercialFactoring(lastOneMonth+","+lastThreemonth+","+lastSixMonth+","+lastYear);
							break;
						}
						
						//担保公司
						case 7:{
							hdBxd.setGuaranteeCompany(lastOneMonth+","+lastThreemonth+","+lastSixMonth+","+lastYear);
							break;
						}
						
						//数据服务
						case 8:{
							hdBxd.setDataServices(lastOneMonth+","+lastThreemonth+","+lastSixMonth+","+lastYear);
							break;
		 
						}
						default:
							break;
						}
						
						
					} else {

						log.info("机构代码=》"+i + "：数据为空");
					}
					
					userHdBxdjgcxjlList.add(hdBxd);
				}
				
				if(addFlag){
					try {
						this.addhdBxdjgcxjl(hdBxd);
					} catch (Exception e) {

						log.error("插入UserHdBxdjgcxjl对象入库时发生错误",e);
					}
				}
			}else{
				log.info("被信贷机构查询次数:500报文-bxRecordsStr=======>空" );
			}
		} catch (Exception e) {
			 log.error("被信贷机构查询次数:500  发生错误",e);
			// 插入告警表
			String alarmContent = "(HAODAI)请求好贷，被信贷机构查询次数:500  发生错误，姓名：" + userName + ",身份证号：" + userIdCard;
			sysAlarmService.alarm(alarmContent);
		}
		
		
		/*******************************被信贷机构查询次数:500 结束****************************************/
		
		return result;
	}
	
	@Override
	public void insertHaoDaiRecord(HaoDaiVo haoDaiVo) {
		
		haoDaiDao.insertHaoDaiRecord(haoDaiVo);
	}
	@Override
	public boolean queryIsHdCredit(String result, String totalId,String hdrequestId,Map<String, String> meMap){
		boolean creditStatus = true;// 默认通过
		
		String creditId = userCreditDataService.queryCreditIdByTotalId(totalId);
		
		String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.QH_CR_EFFECT_ISOPEN));
		
		if ("1".equals(_CROpen)) { //好贷开关生效
			if (EntityUtils.isEmpty(result)) {
				log.warn("(HAODAI)好贷开关生效，返回无数据，通过");
				userCreditDataService.insertCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_HD_GRSXBZX, CreditCodeConstants.CREDIT_TYPE_HD_DEFAULT, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_HD_RECORD_DEAFULT_PASS);

			}else {
				log.warn("(HAODAI)好贷开关生效，返回有数据");
				
					boolean status400 = true;
					String _CROpen_400 = (String) (meMap.get(CreditCfgKeyConstants.HD_CR_NOGRSXBZXDATA_ISOPEN));
					try {
						status400 = this.grsxbzxQuery(result, totalId,hdrequestId);
						if ("1".equals(_CROpen_400)) { //开关生效,结果生效
							log.info("获取个人失信被执行查询信息 (请求类型码400):结果开关生效");
							if (!status400) {
								String isPass = (String) (meMap.get(CreditCfgKeyConstants.HD_GRSXBZX_CONFIG));
								if ("0".equals(isPass)) {//拒贷
									log.info("获取个人失信被执行查询命中,配置为拒贷。");
									userCreditDataService.insertCreditModelRecord(creditId, _CROpen_400, CreditCodeConstants.CREDIT_HD_GRSXBZX, CreditCodeConstants.CREDIT_TYPE_HD_GRSXBZX, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_HD_RECORD_GRSXBZX_UNPASS);
									status400 = false;
								}else {
									log.info("获取个人失信被执行查询命中,配置为通过。");
								}
									
							}else{
								log.info("获取个人失信被执行查询未命中");
							}
						}else {
							log.info("获取个人失信被执行查询信息 (请求类型码400):结果开关失效");
						}
						userCreditDataService.insertCreditModelRecord(creditId, _CROpen_400, CreditCodeConstants.CREDIT_HD_GRSXBZX, CreditCodeConstants.CREDIT_TYPE_HD_GRSXBZX, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_HD_RECORD_DEAFULT_PASS);
						
					} catch (Exception e) {
						log.error("获取个人失信被执行查询信息 (请求类型码400)发生错误", e);
					}
					log.info("获取个人失信被执行查询信息 (请求类型码400):   END  ,status400:"+status400);

						
					log.info("被信贷机构查询次数信息开始(请求类型码500):start");
					//String _500NoData = (String) (meMap.get(CommonConstants.HD_NOBXDJGCXJLDATA_CONFIG));
					//String isPass = (String) (meMap.get(CommonConstants.HD_BXDJGCXJL_CONFIG));
					int maxTimes =  Integer.parseInt(meMap.get(CreditCfgKeyConstants.CREDIT_HD_QUERYTIMES_MAX));

					boolean status500 = true;
					try {
						String _CROpen_500 = (String) meMap.get(CreditCfgKeyConstants.HD_CR_NOBXDJGCXJLDATA_ISOPEN);
						status500 = this.bxdjgcxjlQuery(result, totalId,hdrequestId,maxTimes);
						if ("1".equals(_CROpen_500)) { //开关生效,结果生效
							if (!status500) {
								log.info("被信贷机构查询次数信息开始(请求类型码500)命中:结果开关生效");
								userCreditDataService.insertCreditModelRecord(creditId, _CROpen_500, CreditCodeConstants.CREDIT_HD_GRSXBZX, CreditCodeConstants.CREDIT_TYPE_HD_BXDJGCXJL, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_HD_RECORD_BXDJGCXJL_UNPASS);
								status500 = false;
							}
						}else {
							log.info("被信贷机构查询次数信息结束(请求类型码500):结果开关失效");
						}
						userCreditDataService.insertCreditModelRecord(creditId, _CROpen_500, CreditCodeConstants.CREDIT_HD_GRSXBZX, CreditCodeConstants.CREDIT_TYPE_HD_BXDJGCXJL, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_HD_RECORD_BXDJGCXJL_PASS);
					} catch (Exception e) {
						log.error("被信贷机构查询次数信息结束(请求类型码500)发生错误", e);
					}
					log.info("被信贷机构查询次数信息结束(请求类型码500):end,status500:"+status500);

				
					log.info("黑名单查询信息开始(请求类型码641):start");
					//String _641NoData = (String) (meMap.get(CommonConstants.HD_NOSYHMDDATA_CONFIG));
					boolean status641 = true;
					try {
						String _CROpen_641 = (String) (meMap.get(CreditCfgKeyConstants.HD_CR_NOSYHMDDATA_ISOPEN));
						status641 = this.syhmdQuery(result, totalId,hdrequestId);
						if ("1".equals(_CROpen_641)) { //开关生效,结果生效
							log.info("黑名单查询信息开始(请求类型码641)命中:结果开关生效");
							if (!status641) {
								String isPass = (String) (meMap.get(CreditCfgKeyConstants.HD_SYHMD_CONFIG));
								if ("0".equals(isPass)) {
									log.info("黑名单查询信息开始(请求类型码641),命中黑名单，配置为拒贷");
									userCreditDataService.insertCreditModelRecord(creditId, _CROpen_641, CreditCodeConstants.CREDIT_HD_GRSXBZX, CreditCodeConstants.CREDIT_TYPE_HD_SYHMD, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_HD_RECORD_SYHMD_UNPASS);
									status641 = false;
								}else{
									log.info("黑名单查询信息开始(请求类型码641),命中黑名单，配置为通过");
								}
							}else {
								log.info("黑名单查询信息开始(请求类型码641),未命中黑名单，通过");

							}
						}else {
							log.info("黑名单查询信息开始(请求类型码641):结果开关失效");

						}
						userCreditDataService.insertCreditModelRecord(creditId, _CROpen_641, CreditCodeConstants.CREDIT_HD_GRSXBZX, CreditCodeConstants.CREDIT_TYPE_HD_SYHMD, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_HD_RECORD_SYHMD_PASS);
					} catch (Exception e) {
						log.error("黑名单查询信息结束(请求类型码641)发生错误", e);
					}
					log.info("黑名单查询信息结束(请求类型码641):end,status641:"+status641);
					
						
					log.info("借款重复查询次数信息开始(请求类型码650): start");
				//	String _650NoData = (String) (meMap.get(CommonConstants.HD_NODKCFSQDATA_CONFIG));
					boolean status650 = true;
					try {
						String _CROpen_650 = (String) (meMap.get(CreditCfgKeyConstants.HD_CR_NODKCFSQDATA_ISOPEN));
						status650 = this.dkcfsqQuery(result, totalId,hdrequestId);
						if ("1".equals(_CROpen_650)) { //开关生效,结果生效
							log.info("借款重复查询次数信息开始(请求类型码650):结果开关生效");
							if (!status650) {
								String isPass = (String) (meMap.get(CreditCfgKeyConstants.HD_DKCFSQ_CONFIG));
								if ("0".equals(isPass)) {
									log.info("借款重复查询次数信息开始(请求类型码650),命中，配置为拒贷");
									userCreditDataService.insertCreditModelRecord(creditId, _CROpen_650, CreditCodeConstants.CREDIT_HD_GRSXBZX, CreditCodeConstants.CREDIT_TYPE_HD_DKCFSQ, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_HD_RECORD_DKCFSQ_UNPASS);
									status650 = false;
								}else{
									log.info("借款重复查询次数信息开始(请求类型码650),命中，配置为通过");
								}
							}
						}else {
							log.info("借款重复查询次数信息开始(请求类型码650):结果开关失效");
						}
						userCreditDataService.insertCreditModelRecord(creditId, _CROpen_650, CreditCodeConstants.CREDIT_HD_GRSXBZX, CreditCodeConstants.CREDIT_TYPE_HD_DKCFSQ, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_HD_RECORD_DKCFSQ_PASS);
					} catch (Exception e) {
						log.error("借款重复查询次数信息结束(请求类型码650)发生错误", e);
					}
					log.info("借款重复查询次数信息结束(请求类型码650):  end,status650:"+status650);
					
					
					log.info("获取个人被执行公告查询信息开始(请求类型码680):start");
					//String _680NoData = (String) (meMap.get(CommonConstants.HD_NOZXGRBZXDATA_CONFIG));
					boolean status680 = true;
					try {
						String _CROpen_680 = (String) (meMap.get(CreditCfgKeyConstants.HD_CR_NOZXGRBZXDATA_ISOPEN));
						status680 = this.zxgrbzxQuery(result, totalId,hdrequestId);
						if ("1".equals(_CROpen_680)) { //开关生效,结果生效
							log.info("获取个人被执行公告查询信息开始(请求类型码680):结果开关生效");
							if (!status680) {
								String isPass = (String) (meMap.get(CreditCfgKeyConstants.HD_ZXGRBZX_CONFIG));
								if ("0".equals(isPass)) {
									log.info("获取个人被执行公告查询信息开始(请求类型码680),命中，配置为拒贷");
									userCreditDataService.insertCreditModelRecord(creditId, _CROpen_680, CreditCodeConstants.CREDIT_HD_GRSXBZX, CreditCodeConstants.CREDIT_TYPE_HD_ZXGRBZX, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_HD_RECORD_ZXGRBZX_UNPASS);
									status680 = false;
								}else{
									log.info("获取个人被执行公告查询信息开始(请求类型码680),命中，配置为通过");
								}
							}
						}else {
							log.info("获取个人被执行公告查询信息开始(请求类型码680):结果开关失效");
						}
						userCreditDataService.insertCreditModelRecord(creditId, _CROpen_680, CreditCodeConstants.CREDIT_HD_GRSXBZX, CreditCodeConstants.CREDIT_TYPE_HD_ZXGRBZX, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_HD_RECORD_ZXGRBZX_PASS);
					} catch (Exception e) {
						log.error("获取个人被执行公告查询信息结束(请求类型码680)发生错误", e);
					}
					log.info("获取个人被执行公告查询信息结束(请求类型码680): end ,status680:"+status680);
						
					if(status400&&status500&&status641&&status650&&status680){
						creditStatus=true;
					}else{
						creditStatus=false;
					}
					 log.info("好贷网的最终征信结果："+(creditStatus?"通过":"失败"));
				
			}
		}else {
			log.info("好贷开关失效。征信通过。");
		}
		
		
		return creditStatus;
	}
	/**
	 * grsxbzxQuery<br/>
	 * TODO(个人失信被执行查询:400)
	 * 
	 * @param result
	 * @param totalId
	 * @return boolean
	 * @author zhaohefei
	 * @2015年12月28日
	 * @return boolean 返回类型
	 */
	@Override
	public boolean grsxbzxQuery(String result, String totalId,String hdrequestId) {
		boolean creditStatus = true;
		String sxRecordsStr = JsonObjectUtils.getString(JSONObject.fromObject(result).get("sxbzxrcx"));
		if (EntityUtils.isNotEmpty(sxRecordsStr)) {
			String resultStr = JSONObject.fromObject(sxRecordsStr).get("sxggList").toString();
			log.info("(HAODAI)400报文-sxggList==>" + resultStr);
			if (EntityUtils.isEmpty(resultStr)) {

				log.info("个人失信被执行查询信息为空");
				return creditStatus;
			}
			
			JSONArray records = JSONArray.fromObject(resultStr);
			for (int i = 0; i < records.size(); i++) {
				UserHdsxbzxRecord rsp = (UserHdsxbzxRecord) JSONObject.toBean((JSONObject) records.get(i),UserHdsxbzxRecord.class);
				
				creditStatus = false;// 如果个人失信被执行查询中信息转换成功，则证明命中
				log.info("（HAODAI）个人失信被执行，命中,totalId-->" + totalId);
				break;
			}
		}else{
			log.info("(HAODAI)400报文-sxbzxrcx==>空" );
		}
		return creditStatus;
	}

	/**
	 * zxgrbzxQuery<br/>
	 * TODO(个人被执行公告查询:680)
	 * 
	 * @param result
	 * @param totalId
	 * @return boolean
	 * @author zhaohefei
	 * @2015年12月28日
	 * @return boolean 返回类型
	 */
	@Override
	public boolean zxgrbzxQuery(String result, String totalId,String hdrequestId) {
		boolean creditStatus = true;
		String grRecordsStr = JsonObjectUtils.getString(JSONObject.fromObject(result).get("grbzxggcx"));
		if (EntityUtils.isNotEmpty(grRecordsStr)) {
			String grResultStr = JSONObject.fromObject(grRecordsStr).get("zxggList").toString();
			log.info("个人被执行公告查询:680报文-zxggList==>" + grResultStr);
			if (EntityUtils.isEmpty(grResultStr)) {
				log.info("个人被执行公告查询信息为空");
				return true;
			}

			String grCodeStr = JSONObject.fromObject(grRecordsStr).get("code").toString();
			log.info("个人被执行公告查询:680报文-code==>" + grCodeStr);
			String grMsgStr = JSONObject.fromObject(grRecordsStr).get("msg").toString();
			log.info("个人被执行公告查询:680报文-grMsgStr==>" + grMsgStr);
			if ("s".equals(grCodeStr)) {
				JSONArray grRecords = JSONArray.fromObject(grResultStr);
				for (int i = 0; i < grRecords.size(); i++) {
					UserHdzxgrbzxRecord grRsp = (UserHdzxgrbzxRecord) JSONObject.toBean((JSONObject) grRecords.get(i),
							UserHdzxgrbzxRecord.class);
					creditStatus = false;// 如果个人被执行公告查询中信息转换成功，则证明命中
					log.info("（HAODAI）个人被执行公告，命中,totalId-->" + totalId);
					break;
				}
			}else{
				log.info("个人被执行公告查询:680报文-code==>" + grCodeStr + ",msg==>" + grMsgStr);
			}
			

		}else{
			log.info("个人被执行公告查询:680报文-zxggList==>空" );
		}
		
		return creditStatus;
	}

	/**
	 * dkcfsqQuery<br/>
	 * TODO(借款重复查询次数信息查询:650)
	 * 
	 * @param result
	 * @param totalId
	 * @return boolean
	 * @author zhaohefei
	 * @2015年12月28日
	 * @return boolean 返回类型
	 */
	@Override
	public boolean dkcfsqQuery(String result, String totalId ,String hdrequestId) {
		boolean creditStatus = true;
		String cfRecordsStr = JsonObjectUtils.getString(JSONObject.fromObject(result).get("hd_dkcfsq"));
		log.info("借款重复查询次数信息查询:650-hd_dkcfsq==>" + cfRecordsStr);
		if (EntityUtils.isNotEmpty(cfRecordsStr)) {
			String code = JSONObject.fromObject(cfRecordsStr).get("Code").toString();
			String msg = JSONObject.fromObject(cfRecordsStr).get("Message").toString();
			if (!code.equals("1")) {
				log.info("借款重复查询次数信息查询未成功===========>code：" + code + ",msg:" + msg);
				return creditStatus;
			}

			log.info("借款重复查询次数信息返回结果成功===========>msg:" + msg);
			JSONArray cfRecords = JSONArray.fromObject(msg);
			for (int i = 0; i < cfRecords.size(); i++) {
				UserHdcfsqRecord cfRsp = (UserHdcfsqRecord) JSONObject.toBean((JSONObject) cfRecords.get(i),
						UserHdcfsqRecord.class);

				creditStatus = false;// 如果借款重复查询次数中信息转换成功，则证明命中
				log.info("（HAODAI）借款重复查询次，命中,totalId-->" + totalId);
				break;
			}
		}else{
			log.info("借款重复查询次数信息查询:650-hd_dkcfsq==>空" );
		}
		
		return creditStatus;
	}

	/**
	 * syhmdQuery<br/>
	 * TODO(黑名单查询：641)
	 * 
	 * @param result
	 * @param totalId
	 * @return boolean
	 * @author zhaohefei
	 * @2015年12月28日
	 * @return boolean 返回类型
	 */
	@Override
	public boolean syhmdQuery(String result, String totalId ,String hdrequestId) {
		boolean creditStatus = true;
		String hmdRecordsStr = JsonObjectUtils.getString(JSONObject.fromObject(result).get("shanyin_syhmd"));
		
		log.info("黑名单查询：641报文-hmdRecordsStr===>" + hmdRecordsStr);
		if (EntityUtils.isNotEmpty(hmdRecordsStr)) {
			String hmdResultStr = JSONObject.fromObject(hmdRecordsStr).get("result").toString();
			String hmdResult = JSONObject.fromObject(hmdRecordsStr).get("message").toString();
			
			log.info("黑名单查询：641报文-message===>" + hmdResult);
			if (!hmdResultStr.equals("0")) {
				log.info("黑名单查询未成功，返回码为："+hmdResultStr);
				return creditStatus;
			}

			String hmdmatches = JSONObject.fromObject(hmdRecordsStr).get("matches").toString();
			log.info("matches:" + hmdmatches);

			JSONArray hmdRecords = JSONArray.fromObject(hmdmatches);
			log.info("hmdRecords:" + hmdRecords);
			for (int i = 0; i < hmdRecords.size(); i++) {
				UserHdhmdRecord hmdRsp = (UserHdhmdRecord) JSONObject.toBean((JSONObject) hmdRecords.get(i),
						UserHdhmdRecord.class);

				creditStatus = false;// 如果黑名单中信息转换成功，则证明命中
				log.info("（HAODAI）黑名单查询，命中,totalId-->" + totalId);
				break;
			}
		}else{
			log.info("黑名单查询：641报文-hmdRecordsStr===>空" );
		}
		
		return creditStatus;
	}

	/**
	 * bxdjgcxjlQuery<br/>
	 * TODO(被信贷机构查询次数:500)
	 * 
	 * @param result
	 * @param totalId
	 *            void
	 * @author zhaohefei
	 * @2015年12月28日
	 * @return boolean 返回类型
	 */
	@Override
	public boolean bxdjgcxjlQuery(String result, String totalId ,String hdrequestId,int maxTimes) {
		boolean creditStatus = true;
		String bxRecordsStr = JsonObjectUtils.getString(JSONObject.fromObject(result).get("bxdjgcxjl"));
		log.info("被信贷机构查询次数:500报文-bxRecordsStr=======>" + bxRecordsStr);
		if (EntityUtils.isNotEmpty(bxRecordsStr)) {
			UserHdBxdjgcxjl hdBxd=new UserHdBxdjgcxjl();
			hdBxd.setId(UUIDTools.getFormatUUID());
			hdBxd.setRequestId(hdrequestId);
			hdBxd.setCreateTime(new Date());
			
			// 由于返回报文中key值为1到8 ，故此处循环取值
			for (int i = 1; i <= 8; i++) {
				String iString = String.valueOf(i);
				
				if (JSONObject.fromObject(bxRecordsStr).get(iString) != null) {

					String string1 = JSONObject.fromObject(bxRecordsStr).get(iString).toString();
					
					log.info("机构代码=》"+i+":" + string1 );
					int lastYear = (int) JSONObject.fromObject(string1).get("12");
					// 如果查询次数大于设定值，则证明命中
					if (lastYear > maxTimes) {
						creditStatus = false;
						log.info("（HAODAI）被查询次数，命中,totalId-->" + totalId);
						break;
					}
				} else {
					log.info("机构代码=》"+i + "：数据为空");
				}
			}
		}else{
			log.info("被信贷机构查询次数:500报文-bxRecordsStr=======>空" );
		}
		
		return creditStatus;
	}
	

	
	
	@Override
	public void addhdRequest(UserHdRequest hdRequest) {
		 
		haoDaiDao.addhdRequest(hdRequest);
	}

	@Override
	public void addhdBxdjgcxjl(UserHdBxdjgcxjl hdBxdjgcxjl) {
		
		haoDaiDao.addhdBxdjgcxjl(hdBxdjgcxjl);
	}

	@Override
	public void addhdDkcfsq(UserHdDkcfsq hdDkcfsq) {
		
		haoDaiDao.addhdDkcfsq(hdDkcfsq);
	}

	@Override
	public void addhdSyhmd(UserHdShanyinSyhmd hdSyHmd) {
		
		haoDaiDao.addhdSyhmd(hdSyHmd);
	}

	@Override
	public void addhdSxbzxrcx(UserHdSxbzxrcx hdSxbzxrcx) {
		
		haoDaiDao.addhdSxbzxrcx(hdSxbzxrcx);
	}

	@Override
	public void addGrbzxggcx(UserHdGrbzxggcx hdGrbzxggcx) {
		
		haoDaiDao.addGrbzxggcx(hdGrbzxggcx);
	}

	@Override
	public void modifyThirdPartCreditStatus(String totalId, boolean creditStatus) {
		 
		int status=0;
		try {
			status = userDAO.queryUserThirdPartCreditStatus(totalId);
		} catch (Exception e) {
			log.error("数据库中没有查到totalId=》"+totalId+"对应的业务表数据，故不进行修改");
			return;
		}
		int realStatus=StatusConstants.THIRD_PARTY_CREDIT_STATUS_PASS;
		if(!creditStatus){
			realStatus=StatusConstants.THIRD_PARTY_CREDIT_STATUS_NOT_PASS;
		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("infoId", totalId);
		map.put("thirdPartCreditStatus", realStatus);
		map.put("updateTime", new Date());
		
		//如果数据库状态为不成功，则不再修改
		if(status==StatusConstants.THIRD_PARTY_CREDIT_STATUS_NOT_PASS.intValue()){
			return;
		}
		
		//如果数据库状态是初始化状态，修改
		if(status==StatusConstants.THIRD_PARTY_CREDIT_STATUS_INIT.intValue()){
			userDAO.modifyUserThirdPartCreditStatus(map);
			return ;
		}
		
		//如果数据库状态是成功状态，当前状态也是成功，则不修改，否则修改
		if(status==StatusConstants.THIRD_PARTY_CREDIT_STATUS_PASS.intValue()){
			if(realStatus!=status){
				userDAO.modifyUserThirdPartCreditStatus(map);
			}
			return ;
		}
		
	}

	@Override
	public void modifyCreditRecord(String totalId, boolean creditStatus) {
		
		if (!creditStatus) {
			UserCreditRecord creditRecord = new UserCreditRecord();
			creditRecord.setInfoId(totalId);
			creditRecord.setIsPass(StatusConstants.CREDIT_IS_NOT_PASS);
			creditRecord.setReason(CreditCodeConstants.CREDIT_HD_RECORD_DEAFULT_UNPASS);
			creditRecord.setUpdateTime(new Date());
			userCreditDataDAO.updateUserCreditRecordByInfoId(creditRecord);
		}
		
	}

}
