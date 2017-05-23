package com.tera.interfaces.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.reflect.TypeToken;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.feature.lenduser.model.LendUser;
import com.tera.feature.lenduser.service.ILendUserService;
import com.tera.file.model.Files;
import com.tera.file.service.FileService;
import com.tera.interfaces.constants.InterConstants;
import com.tera.interfaces.model.InterfaceLog;
import com.tera.interfaces.model.PushBankCard;
import com.tera.interfaces.model.PushData;
import com.tera.interfaces.model.PushDebt;
import com.tera.interfaces.model.PushDebtDesc;
import com.tera.interfaces.model.PushDebtPer;
import com.tera.interfaces.model.PushFileInfo;
import com.tera.interfaces.model.PushPledge;
import com.tera.interfaces.model.RetMsg;
import com.tera.interfaces.util.GsonUtils;
import com.tera.interfaces.util.HttpUtil;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Parameter;
import com.tera.sys.service.ParameterService;
import com.tera.util.PasswordUtil;

@Service("interfaceService")
public class InterfaceService {
	
	private static final Logger log = Logger.getLogger(InterfaceService.class);
	
	@Autowired(required = false)
	private ParameterService parameterService;
	@Autowired(required = false)
	private InterfaceLogService interfaceLogService;
	@Autowired(required = false)
	private ILoanBaseService loanBaseService;

	@Autowired(required = false)
	private IContractService contractService;

	@Autowired(required = false)
	private ICollateralService collateralService;

	@Autowired(required = false)
	private ILoanAppService loanAppService;

	@Autowired(required = false)
	private ILendUserService lendUserService;
	
	@Autowired(required = false)
	private FileService fileService;

	/**
	 * 推送线上
	 * @param loanId
	 * @return
	 * @throws Exception
	 */

	@Transactional
	public JsonMsg pushOnline(String loanId) throws Exception {
		// 判断日志中是否存在成功记录，如果有，无需再次提交
		Map<String, Object> maplog = new HashMap<String, Object>();
		maplog.put("bizKey", loanId);
		maplog.put("type", "1");
		maplog.put("state", "2");
		List<InterfaceLog> listLogs = interfaceLogService.queryList(maplog);
		if (listLogs != null && listLogs.size() > 0) {
			return new JsonMsg(false, "已经推送，无需再次提交！");
		}
		//推送信息bean
//		LoanPushInfo pushInfo = new LoanPushInfo();
		String StrDatajson;
		try {
			
			// 推送数据到线上
			// 获取申请信息
			LoanBase loanBase = loanBaseService.queryByLoanId(loanId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loanId", loanId);
			List<Contract> listContracts = contractService.queryList(map);
			if (listContracts == null || listContracts.size() == 0) {
				// 数据问题
				return new JsonMsg(false, "数据有问题，无合同信息！");
			}
			Contract contract = listContracts.get(0);
			map.put("mainFlag", "1");
			List<LoanApp> listApps = loanAppService.queryList(map);
			if (listApps == null || listApps.size() == 0) {
				return new JsonMsg(false, "数据有问题，无申请信息");
			}
	
			LoanApp loanApp = listApps.get(0);
			if ("1".equals(loanBase.getIsRenew())) {// 续贷的
				// 抵押物是所有申请的
				map.clear();
				map.put("loanId", loanId);
				map.put("origLoanId", loanBase.getOrigLoanId().trim());
				map.put("state", "1");
			} else {
				map.clear();
				map.put("loanId", loanId);
				map.put("state", "1");
			}
			List<Collateral> listCollaterals = collateralService
					.queryListByLoanId(map);
			if (listCollaterals == null || listCollaterals.size() == 0) {
				// 无抵押物，不能提交
				return new JsonMsg(false, "无抵押物信息，不能提交！");
	
			}
	
			/** ========债权信息=================== **/
			PushDebt debt = new PushDebt();
			String debtType = "";
			if ("1".equals(contract.getGetLoanWay())) {// 直投模式
				debtType = "1";
			} else if ("2".equals(contract.getGetLoanWay())) { // 债权模式
				debtType = "0";
			} else {
				return new JsonMsg(false, "该申请不是直投和债权转让类型，不能提交！");
			}
			debt.setDebt_type(debtType); // 债权类型
	
			String debtPro = "";
			if ("01".equals(loanBase.getProduct())) {// 车
				debtPro = "1";
			} else if ("02".equals(loanBase.getProduct())) {// 车商
				debtPro = "99";
			} else if ("03".equals(loanBase.getProduct())) {// 房
				debtPro = "2";
			} else if ("04".equals(loanBase.getProduct())) {// 红木
				debtPro = "4";
			} else if ("05".equals(loanBase.getProduct())) {// 海鲜
				debtPro = "3";
			} else {// 其他
				debtPro = "100";
			}
			debt.setProduct_type(debtPro);// 产品类型
			debt.setSerial_number(loanId);// 编号
			debt.setAmount(String.valueOf(loanBase.getLoanAmt()));// 总额
			debt.setContract_rate(String.valueOf(loanBase.getRate()));// 合同利率
			debt.setFee_rate(String.valueOf(loanBase.getMemFee()
					+ loanBase.getLawFee() + loanBase.getOtherFee()));// 手续费
			// TODO 付息方式
			// 付息方式对应
			// String strPayWay="";
			// if ("01".equals(loanBase.getRetLoanSol())) { //月付
			// strPayWay="1";
			// }else if ("02".equals(loanBase.getRetLoanSol())) {//季付
			// strPayWay="2";
			// }else if ("03".equals(loanBase.getRetLoanSol())) {//首付
			// strPayWay="3";
			// }else if ("04".equals(loanBase.getRetLoanSol())) {//末付
			// strPayWay="3";
			// }else {
			// return null;
			// }
			debt.setRepayment("0");// 均采用默认值0
			debt.setStart_time(contract.getStartDateStr());
			debt.setEnd_time(contract.getEndDateStr());
	
			/** ========借款人信息===== **/
			PushDebtPer debtBorrower = new PushDebtPer();
			if ("01".equals(loanBase.getLoanType())) {// 个人
				debtBorrower.setBorrow_type("1");
				debtBorrower.setIdentity_number(loanBase.getIdNo());
				debtBorrower.setName(loanBase.getName());
				if ("M".equals(loanApp.getSex())) {
					debtBorrower.setSex("1");
				} else {
					debtBorrower.setSex("2");
				}
	
				debtBorrower.setPhone(loanApp.getTel());
	
				debtBorrower.setAge(loanApp.getAge());
				debtBorrower.setProvince(loanApp.getNowPrvn());
				debtBorrower.setCity(loanApp.getNowCity());
				debtBorrower.setAddress(loanApp.getNowAddr());
				
				if ("02".equals(loanApp.getMarriage())) {// 已婚
					debtBorrower.setMarrage("1");
				} else {
					debtBorrower.setMarrage("2");
				}
	
				debtBorrower.setEducation(loanApp.getEdu());// 学历
			} else {// 机构
				debtBorrower.setBorrow_type("2");
				debtBorrower.setLicense_number(loanBase.getIdNo());
				debtBorrower.setName(loanBase.getName());
				debtBorrower.setPhone(loanApp.getLegalTel());
				debtBorrower.setAddress(loanApp.getHomePrvn()
						+ loanApp.getHomeCity() + loanApp.getHomeCtry()
						+ loanApp.getHomeAddr());
				debtBorrower.setLegal_person_name(loanApp.getLegalName());
				debtBorrower.setLegal_person_identity(loanApp.getLegalIdNo());
				debtBorrower.setPeriod(String.valueOf(loanApp.getOrgPeriod()));// 经营年限
				debtBorrower.setAnnual_sales(String.valueOf(loanApp
						.getOrgSalesAmt()));
				debtBorrower.setPremises(loanApp.getNowPrvn()
						+ loanApp.getNowCity() + loanApp.getNowCtry()
						+ loanApp.getNowAddr());
	
			}
	
			PushDebtPer debtCreditor = new PushDebtPer();
			/** ===========债权人信息，直投没有债权人====================== **/
			if ("2".equals(contract.getGetLoanWay())) {// 债权模式才有
				LendUser lendUser = lendUserService.queryByKey(contract
						.getLendUserId());
				debtCreditor.setBorrow_type("1");
				debtCreditor.setIdentity_number(lendUser.getIdNo());
				debtCreditor.setName(lendUser.getRealName());
				if ("M".equals(lendUser.getGender())) {
					debtCreditor.setSex("1");
				} else {
					debtCreditor.setSex("2");
				}
	
				debtCreditor.setPhone(lendUser.getMobile());
	
				debtCreditor.setProvince(lendUser.getNowPrvn());
				debtCreditor.setCity(lendUser.getNowCity());
				debtCreditor.setAddress(lendUser.getNowAddr());
	
			}
	
			// TODO 学历
	
			/** ===============抵押物信息================= **/
	
			Collateral collateral = listCollaterals.get(0);
			// TODO 测试一个
			// 多个抵押物，只获取第一个。一般一个单子，只会有一类抵押物。
			PushPledge pleage = new PushPledge();
			String plType = "";// 质押抵押类型区分
	
			// TODO 抵押物分类 不对应
			pleage.setCategory("2");
			String plName = "";
			String plQuantity = "";
			String plBuild = "";
			String plCardate = "";
			String plMiles = "";
			if ("01".equals(loanBase.getProduct())) {// 车
				plType = "2";
				plName = collateral.getCarType();
				plCardate = collateral.getBuyDateStr();
				plMiles = collateral.getMile();
			} else if ("02".equals(loanBase.getProduct())) {// 车商
				plType = "2";
				plName = "车商";
				plQuantity = collateral.getSize();
			} else if ("03".equals(loanBase.getProduct())) {// 房
				plType = "1";
				plName = "房产";
				plBuild = collateral.getArea();
			} else if ("04".equals(loanBase.getProduct())) {// 红木
				plType = "2";
				plName = collateral.getVar();
				plQuantity = collateral.getSize();
			} else if ("05".equals(loanBase.getProduct())) {// 海鲜
				plType = "2";
				plName = collateral.getVar();
				plQuantity = collateral.getSize();
			} else {// 其他
				plType = "3";
				plName = collateral.getVar();
				plQuantity = collateral.getSize();
			}
			pleage.setType(plType);
			pleage.setName(plName);
			pleage.setQuantity(plQuantity);
			pleage.setValuations(String.valueOf(collateral.getEvalPrice()));
			pleage.setBuilt_up(plBuild);
			pleage.setLocation(collateral.getAssetAddr());
			pleage.setRegister_date(plCardate);
			pleage.setMileage(plMiles);
	
			/** ====债权描述========== **/
			PushDebtDesc debtDesc = new PushDebtDesc();
			debtDesc.setDesc(loanApp.getSaleRemark());// 债权描述
			debtDesc.setUse(loanBase.getLoanUse());
			debtDesc.setPledge(collateral.getAssetRemark());
			// debtDesc.setSource("");//还款来源，暂空
			// debtDesc.setRisk("测试");
			// debtDesc.setAdvice("测试");
			// debtDesc.setGuarantee("测试");
	
			/** =====银行卡信息======== **/
	
			PushBankCard bankCard = new PushBankCard();
			bankCard.setCard_type("0");
			bankCard.setCard_number(loanBase.getAcctCode());
			bankCard.setName(loanBase.getAcctName());
			bankCard.setBank_id(loanBase.getAcctBank());
			bankCard.setBank_province(loanBase.getAcctPrvn());
			bankCard.setBank_city(loanBase.getAcctCity());
			bankCard.setSub_branch_name(loanBase.getAcctBranch());
	
			/** =====文件信息======== **/
			map.clear();
			map.put("loanId", loanId);
			map.put("sceneType", "filesce10");//推送场景
			List<Files> exists =fileService.queryList(map);
			String strURL="";
			if(exists!=null&&exists.size()>0){
				strURL=exists.get(0).getFilePath();
			}
			PushFileInfo fileInfo = new PushFileInfo();
			fileInfo.setFile_url(strURL);
	
			/** ===数据体信息== **/
			PushData data = new PushData();
			data.setDebt(debt);
			data.setDebtAttachment(fileInfo);
			data.setDebtBorrower(debtBorrower);
			data.setDebtCreditor(debtCreditor);
			data.setDebtDesc(debtDesc);
			data.setDebtPledge(pleage);
			data.setUserBankCard(bankCard);
			/** =====，最后组装===== **/
	
//			pushInfo.setData(data);
			
		    StrDatajson = GsonUtils.getInstance().toJson(data);
			
		
			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			return new JsonMsg(false,str);
		}
		
		//签名认证
		long ctime = System.currentTimeMillis() / 1000;
		
//		System.out.println("SECRET_ID:"+InterConstants.SECRET_ID);
//		System.out.println("cTime:"+ctime);
//		System.out.println("json:"+StrDatajson);
//		
//		System.out.println("生成字符："+InterConstants.SECRET_ID + ctime + StrDatajson);
		
		String strSign = PasswordUtil.md5(InterConstants.SECRET_ID + ctime
				+ StrDatajson);

//		System.out.println("签名："+strSign);
		
		// json串
//		String strJsonInfo = GsonUtils.getInstance().toJson(pushInfo);
		
		/** =================组装推送信息完毕========================== **/

		Parameter parameter = parameterService.queryByParamName("onlineip");
		String url = parameter.getParamValue() + "/business/api/pushDebt?appId="+InterConstants.APP_ID+"&sign="+strSign+"&cTime="+String.valueOf(ctime);
		// String url="http://172.16.33.47:80/open/input/pushdebt";
		try {
			DefaultHttpClient client = HttpUtil.getDefaultHttpClient();
			HttpPost post = new HttpPost(url);
//			StringEntity s = new StringEntity(strJsonInfo, "utf-8");
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("data", StrDatajson));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps,"UTF-8");
			formEntity.setContentEncoding("UTF-8");
			
//			s.setContentEncoding("UTF-8");
//			s.setContentType("application/json");// 发送json数据需要设置contentType
			
//			client.getParams().setParameter(
//					CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
//			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,5000);
			
			post.setEntity(formEntity);
			// System.out.print(post.getURI());
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(entity);// 返回json格式：
//				Gson gson = new Gson();
//				Map<String, String> mapReq = gson.fromJson(result,
//						new TypeToken<Map<String, String>>() {
//						}.getType());
				
				log.info("返回的json数据："+result);
				
				RetMsg retMsg = GsonUtils.getInstance().fromJson(result, new TypeToken<RetMsg>() {});
				
//				Map<String, String> mapReq = GsonUtils.getInstance().fromJson(result, new TypeToken<Map<String, String>>() {});
				
				String respCode = retMsg.getCode();
				String msg = retMsg.getMsg();
				if ("0000".equals(respCode)) {// 成功
					listLogs = null;
					maplog.remove("state");
					listLogs = interfaceLogService.queryList(maplog);
					if (listLogs != null && listLogs.size() > 0) {// 存在
						InterfaceLog log = listLogs.get(0);
						log.setCount(log.getCount() + 1);
						log.setRspCode(respCode);
						log.setRspMsg(msg);
						log.setState("2");
						log.setUpdateTime(new Timestamp(System
								.currentTimeMillis()));
						interfaceLogService.update(log);

					} else {// 新记录

						InterfaceLog interLog = new InterfaceLog();
						interLog.setBizKey(loanId);
						interLog.setPara(StrDatajson);
						interLog.setType("1");
						interLog.setCount(1);
						interLog.setRspCode(respCode);
						interLog.setRspMsg(msg);
						interLog.setState("2");// 成功
						interLog.setCreateTime(new Timestamp(System
								.currentTimeMillis()));
						interfaceLogService.add(interLog);
					}

					return new JsonMsg(true, "推送成功！");
				}

				// 返回错误信息，记录
				if (listLogs != null && listLogs.size() > 0) {// 存在
					InterfaceLog log = listLogs.get(0);
					log.setCount(log.getCount() + 1);
					log.setRspCode(respCode);
					log.setRspMsg(msg);
					log.setState("3");// 错误
					log.setUpdateTime(new Timestamp(System
									.currentTimeMillis()));
					interfaceLogService.update(log);

				} else {// 新记录

					InterfaceLog interLog = new InterfaceLog();
					interLog.setPara(StrDatajson);
					interLog.setBizKey(loanId);
					interLog.setType("1");
					interLog.setCount(1);
					interLog.setRspCode(respCode);
					interLog.setRspMsg(msg);
					interLog.setState("3");// 错误
					interLog.setCreateTime(new Timestamp(System
							.currentTimeMillis()));
					interfaceLogService.add(interLog);
				}
				return new JsonMsg(false, msg);

			} else {
				// 返回码错误
				listLogs = null;
				maplog.remove("state");
				listLogs = interfaceLogService.queryList(maplog);

				if (listLogs != null && listLogs.size() > 0) {// 存在
					InterfaceLog log = listLogs.get(0);
					log.setCount(log.getCount() + 1);
					log.setRspCode(String.valueOf(res.getStatusLine()
							.getStatusCode()));
					log.setRspMsg(res.getStatusLine().getReasonPhrase());
					log.setUpdateTime(new Timestamp(System
									.currentTimeMillis()));
					interfaceLogService.update(log);
					return new JsonMsg(false, log.getRspMsg());

				}
				// 记录错误
				InterfaceLog interLog = new InterfaceLog();
				interLog.setPara(StrDatajson);
				interLog.setBizKey(loanId);
				interLog.setType("1");
				interLog.setCount(1);
				interLog.setRspCode(String.valueOf(res.getStatusLine()
						.getStatusCode()));
				interLog.setRspMsg(res.getStatusLine().getReasonPhrase());
				interLog.setState("3");
				interLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
				interfaceLogService.add(interLog);
				return new JsonMsg(false, interLog.getRspMsg());
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			listLogs=null;
			maplog.remove("state");
			listLogs = interfaceLogService.queryList(maplog);
	
			if (listLogs != null && listLogs.size() > 0) {// 存在
				InterfaceLog log = listLogs.get(0);
				log.setCount(log.getCount() + 1);
				log.setRspCode("9999");
				log.setRspMsg(str);
				log.setUpdateTime(new Timestamp(System
								.currentTimeMillis()));
				interfaceLogService.update(log);
				return new JsonMsg(false, log.getRspMsg());

			}
			// 记录错误
			InterfaceLog interLog = new InterfaceLog();
			interLog.setPara(StrDatajson);
			interLog.setBizKey(loanId);
			interLog.setType("1");
			interLog.setCount(1);
			interLog.setRspCode("9999");
			interLog.setRspMsg(str);
			interLog.setState("3");
			interLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			interfaceLogService.add(interLog);
			return new JsonMsg(false, interLog.getRspMsg());
			
		}
		
	}

}
