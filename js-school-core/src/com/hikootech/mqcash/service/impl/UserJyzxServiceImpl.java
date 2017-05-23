package com.hikootech.mqcash.service.impl;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hikootech.mqcash.constants.CreditCfgKeyConstants;
import com.hikootech.mqcash.constants.CreditCodeConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.TelecomConstants;
import com.hikootech.mqcash.dao.UserCreditDataDAO;
import com.hikootech.mqcash.dao.UserDAO;
import com.hikootech.mqcash.dao.UserJyzxRecordDAO;
import com.hikootech.mqcash.dao.UserJyzxRecordItemsDAO;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.po.UserJyzxRecord;
import com.hikootech.mqcash.po.UserJyzxRecordItem;
import com.hikootech.mqcash.service.ConfigCreditKvService;
import com.hikootech.mqcash.service.CreditCollegeService;
import com.hikootech.mqcash.service.SysAlarmService;
import com.hikootech.mqcash.service.UserCreditDataService;
import com.hikootech.mqcash.service.UserJyzxService;
import com.hikootech.mqcash.util.CommonUtils;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.UUIDTools;
import com.xiaocui.common.JsonSerializer;
import com.xiaocui.entity.LoanInfo;
import com.xiaocui.entity.Pkg1001;
import com.xiaocui.entity.Pkg2001;
import com.xiaocui.entity.Pkg3002;
import com.xiaocui.entity.Pkg4001;
import com.xiaocui.entity.PkgHeader;

import net.sf.json.JSONObject;

@Service("userJyzxService")
public class UserJyzxServiceImpl implements UserJyzxService {

	private static Logger log = LoggerFactory.getLogger(UserJyzxServiceImpl.class);
	@Autowired
	private UserJyzxRecordDAO userJyzxRecordDAO;
	@Autowired
	private UserJyzxRecordItemsDAO userJyzxRecordItemsDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ConfigCreditKvService configCreditKvService;
	@Autowired
	private UserCreditDataService userCreditDataService;
	@Autowired
	private CreditCollegeService creditCollegeService;
	@Autowired
	private UserCreditDataDAO userCreditDataDAO;
	@Autowired
	private SysAlarmService sysAlarmService;
	
	@Override
	public UserJyzxRecord requestJyzx(Map<String, String> map) {

		UserJyzxRecord record = new UserJyzxRecord();

		Pkg1001 pkg1001 = new Pkg1001();
		pkg1001.setIdCard(map.get("userIdCard"));
		pkg1001.setRealName(map.get("userName"));
		
		record.setId(map.get("recordId"));
		record.setIdCard(pkg1001.getIdCard());
		record.setName(pkg1001.getRealName());
		record.setTotalInfoId(map.get("totalInfoId"));
		String msgBody = JsonSerializer.serializer(pkg1001);

		PkgHeader reqPkg = new PkgHeader();
		reqPkg.setVersion(ConfigUtils.getProperty("jyzx_version")); // 默认01
		reqPkg.setCustNo(ConfigUtils.getProperty("jyzx_custNo")); // 客户编号
		reqPkg.setEncode(ConfigUtils.getProperty("jyzx_encode")); // 01.UTF8 // 02.GBK
		reqPkg.setTrxCode("1001"); // 报文编号 默认四位 例:0001
		reqPkg.setEncryptType(ConfigUtils.getProperty("jyzx_encryptType")); // 加密类型 // 01.不加密// 02.RSA
		reqPkg.setMsgType(ConfigUtils.getProperty("jyzx_msgType")); // 01.JSON// 02.XML // 03.Protobuf
																	
		reqPkg.setMsgBody(msgBody); // 报文主体
		String pkgStr = reqPkg.toPkgStr("UTF-8");

		PkgHeader pkgHeader = new PkgHeader();
		pkgHeader.parseFromString(pkgStr);

		log.info("经过加密后的请求数据:" + pkgStr);
		log.info("经过解密后的请求数据" + JsonSerializer.serializer(pkgHeader));
		log.info("经过加密后的发送的请求数据" + CommonUtils.byteArray2HexStr(reqPkg.toPkgBytes("UTF-8")));
		String strUrl = ConfigUtils.getProperty("jyzx_url");
		try {
			URL url = new URL(strUrl);
			log.info("url.getProtocol():" +url.getProtocol() + ",url.getHost():" + url.getHost() +",url.getPath():"+ url.getPath() +", url.getQuery():" +  url.getQuery());
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			log.info("请求数据准备发送至:" + uri);

			ByteArrayEntity reqEntity = new ByteArrayEntity(reqPkg.toPkgBytes("UTF-8"));

			// 请求91
			CloseableHttpResponse response;
			String result = "";
			try {
				CloseableHttpClient httpclient = createSSLClientDefault();
				HttpPost post = new HttpPost(uri);
				post.setEntity(reqEntity);
				response = httpclient.execute(post);
			} catch (Exception e) {
				log.error("与91通讯时发生错误", e);
				
				// 插入告警表
				String alarmContent = "(91)访问异常，姓名：" + map.get("userName") + ",身份证号：" + map.get("userIdCard");
				try {
					sysAlarmService.alarm(alarmContent);
				} catch (Exception e1) {
					log.error("(91)访问异常，姓名：" + map.get("userName") + ",身份证号：" + map.get("userIdCard"), e1);
				}

				
				throw new RuntimeException("与91通讯时发生错误:" + e.getMessage());
			}

			// 解析数据
			Pkg2001 pkgHeaderRsp2;
			try {
				HttpEntity rspEntity = response.getEntity();

				if (rspEntity != null) {
					result = org.apache.http.util.EntityUtils.toString(rspEntity);
					log.info("返回的加密数据：" + result);
				}
				response.close();
				PkgHeader pkgHeaderRsp = new PkgHeader();
				pkgHeaderRsp.parseFromString(result);
				// 输出返回信息
				
				log.info("返回的解密数据：" + JsonSerializer.serializer(pkgHeaderRsp));

				log.info("对应的实体类json为：" + pkgHeaderRsp.getMsgBody());

				pkgHeaderRsp2 = (Pkg2001) JSONObject.toBean(JSONObject.fromObject(pkgHeaderRsp.getMsgBody()),
						Pkg2001.class);

				log.info("反序列化后的交易流水号:" + pkgHeaderRsp2.getTrxNo());
			} catch (Exception e) {
				log.error("解析91返回流水号数据时发生错误", e);
				throw new RuntimeException("解析91返回流水号数据时发生错误:" + e.getMessage());
			}

			// 入库
			try {
				record.setTrxNo(pkgHeaderRsp2.getTrxNo());
				record.setCreateTime(DateUtil.getCurDate());
				record.setResultStatus(StatusConstants.RESPONSE_STATUS_JYZX_WAITTING);
				record.setRequestStatus(StatusConstants.REQUEST_STATUS_JYZX_SUCCESS);
				record.setCallType(map.get("callType"));
				userJyzxRecordDAO.addUserJyzxRecord(record);
			} catch (Exception e) {
				log.error("91返回流水号数据存储入库时发生错误", e);
				throw new RuntimeException("91返回流水号数据存储入库时发生错误" + e.getMessage());
			}
		} catch (Exception e) {
			log.error("请求91的流程发生错误，", e);
			record.setCreateTime(DateUtil.getCurDate());
			record.setResultStatus(StatusConstants.RESPONSE_STATUS_JYZX_WAITTING);
			record.setRequestStatus(StatusConstants.REQUEST_STATUS_JYZX_FAILED);
			record.setCallType(map.get("callType"));
			userJyzxRecordDAO.addUserJyzxRecord(record);
		}
		
		return record;
	}
	
	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	@Override
	public void addJyzxRecordItem(UserJyzxRecordItem recordItem) {

		userJyzxRecordItemsDAO.addUserJyzxRecordItem(recordItem);
	}

	@Override
	public String queryTotalInfoId(String trxNo) {
		return userJyzxRecordDAO.queryTotalInfoId(trxNo);
	}

	@Override
	public List<UserJyzxRecordItem> addJyzxRecordItems(Pkg3002 pkg3002) {

		List<UserJyzxRecordItem> recordItems = new ArrayList<UserJyzxRecordItem>();
		for (LoanInfo loanInfo : pkg3002.getLoanInfos()) {

			UserJyzxRecordItem recordItem = new UserJyzxRecordItem();
			recordItem.setArrearsAmount(loanInfo.getArrearsAmount() / 100000); // 根据协议，该值需除以100000即可
			recordItem.setBorrowAmount(loanInfo.getBorrowAmount());
			recordItem.setBorrowState(loanInfo.getBorrowState());
			recordItem.setBorrowType(loanInfo.getBorrowType());
			recordItem.setCompanyCode(loanInfo.getCompanyCode());
			recordItem.setContractDate(loanInfo.getContractDate());
			recordItem.setId(UUIDTools.getFormatUUID());
			recordItem.setLoanPeriod(loanInfo.getLoanPeriod());
			recordItem.setRepayState(loanInfo.getRepayState());
			recordItem.setTrxNo(pkg3002.getTrxNo());
			recordItem.setCreateTime(DateUtil.getCurDate());
			recordItems.add(recordItem);
		}

		// 增加返回信息入子项表
		if (recordItems == null || recordItems.size() == 0) {
		} else {
			for (UserJyzxRecordItem userJyzxRecordItem : recordItems) {
				this.addJyzxRecordItem(userJyzxRecordItem);
			}
		}

		return recordItems;
	}

	@Override
	public boolean queryJyCreditResult(Pkg3002 pkg3002) {
		boolean creditResult=true; //查询客户的91征信结果
		
		//从memcached中取出数据
		Map<String, String> meMap = null;
		try {
			meMap = configCreditKvService.getAllCreditKv();
		} catch (Exception e3) {
			throw new RuntimeException("调用91征信接口，获取征信配置数据异常");
		}
		
		if (meMap == null) {
			throw new RuntimeException("调用91征信接口，获取征信配置数据为空");
		}
				
		
		//91--近6个月不在同机构申请命中开关
		String _91M6OpenConfig = (String) (meMap.get(CreditCfgKeyConstants.JY_M6DIFFORG_OPEN_CONFIG));
		//91--近6个月不在同机构申请数大于等于N拒贷
		String _91M6DataConfig = (String) (meMap.get(CreditCfgKeyConstants.JY_M6DIFFORG_DATA_CONFIG));
		//91--近12个月不在同机构申请命中开关
		String _91M12OpenConfig = (String) (meMap.get(CreditCfgKeyConstants.JY_M12DIFFORG_OPEN_CONFIG));
		//91--近12个月不在同机构申请数大于等于N拒贷
		String _91M12DataConfig = (String) (meMap.get(CreditCfgKeyConstants.JY_M12DIFFORG_DATA_CONFIG));

		
		//有数据借款类型未知
		String dataUnknownConfig = (String) (meMap.get(CreditCfgKeyConstants.JY_DATA_UNKNOWN_CONFIG));
		//借款类型为1或者3（1.个人信贷 3.企业信贷），欠款金额在3万以上拒贷；如果3万以下，还款除1以外均拒贷
		String dataXDUpperConfig = (String) (meMap.get(CreditCfgKeyConstants.JY_DATA_XD_UPPERQK_CONFIG));
		//借款类型为1或者3（1.个人信贷 3.企业信贷）欠款金额在3万以下，还款除1(正常)以外
		String dataXDLowerConfig = (String) (meMap.get(CreditCfgKeyConstants.JY_DATA_XD_LOWERQK_CONFIG));
		//借款类型为2或者4(2.个人抵押 4.企业抵押)，如果还款状态，除1（正常）以外的任何情况
		String dataDYConfig = (String) (meMap.get(CreditCfgKeyConstants.JY_DATA_DY_CONFIG));
		//借款状态为2，3，6时(2.批贷已放款3.批贷未放款6.待放款)，还款状态不为1（1.正常）
		String dataFKConfig = (String) (meMap.get(CreditCfgKeyConstants.JY_DATA_FK_CONFIG));
		//借款状态为1，5（1.拒贷 5.审核中）
		String dataJdshConfig = (String) (meMap.get(CreditCfgKeyConstants.JY_DATA_JD_SH_CONFIG));
		

		
		for (LoanInfo loanInfo : pkg3002.getLoanInfos()) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("trxNo", pkg3002.getTrxNo());
			
			if (CreditCfgKeyConstants.OPEN.equals(_91M6OpenConfig)) {
				log.info("91--近6个月不在同机构申请命中开关开启");
				map.put("month", "6");
				String m6DataConfig = userJyzxRecordDAO.query91DiffOrgConfig(map);
				if (compareDiffOrg(_91M6DataConfig,m6DataConfig)) {
					creditResult = false; // 更改征信结果标记
					break;
				}
			}else{
				log.info("91--近6个月不在同机构申请命中开关关闭");
			}
			
			if (CreditCfgKeyConstants.OPEN.equals(_91M12OpenConfig)) {
				log.info("91--近12个月不在同机构申请命中开关开启");
				map.put("month", "12");
				String m12DataConfig = userJyzxRecordDAO.query91DiffOrgConfig(map);
				if (compareDiffOrg(_91M12DataConfig,m12DataConfig)) {
					creditResult = false; // 更改征信结果标记
					break;
				}
			}else{
				log.info("91--近12个月不在同机构申请命中开关关闭");
			}
			
			if (EntityUtils.isNotEmpty(loanInfo.getBorrowType()) ) {
			
				//如果借款类型为0未知，拒贷
				if(loanInfo.getBorrowType() == 0){
					if ("1".equals(dataUnknownConfig)) {
						log.info("91征信接口返回有数据,借款类型为0未知,有数据配置值，" + dataUnknownConfig + "，征信通过" );
						log.info("查询到该客户有逾期数据,交易流水号：" + pkg3002.getTrxNo());

						creditResult = true; // 更改征信结果标记
					} else {
						log.info("91征信接口返回有数据,有数据配置值：" + dataUnknownConfig + ",拒贷。");
						creditResult = false; // 更改征信结果标记
						break;
					}
				}

				if ((loanInfo.getBorrowType() == 1 || loanInfo.getBorrowType() == 3)) {

					if(loanInfo.getArrearsAmount() > 30000){
						log.info("91征信接口返回有数据,借款类型为1或者3（1.个人信贷 3.企业信贷），欠款金额在3万以上,有数据配置值，" + dataXDUpperConfig );
						if ("1".equals(dataXDUpperConfig)) {
							log.info("通过");
							creditResult=true; //更改征信结果标记
						} else {
							log.info("拒贷");
							creditResult = false; // 更改征信结果标记
							break;
						}
						
					}else{
						
						log.info("91征信接口返回有数据,借款类型为1或者3（1.个人信贷 3.企业信贷）欠款金额在3万以下，还款除1(正常)以外,有数据配置值，" + dataXDLowerConfig );
						if(loanInfo.getRepayState() != 1){
							if ("1".equals(dataXDLowerConfig)) {
								log.info("通过");
								creditResult=true; //更改征信结果标记
							} else {
								creditResult = false; // 更改征信结果标记
								log.info("拒贷");
								break;
							}
						}else{
							log.info("loanInfo.getRepayState():" + loanInfo.getRepayState());
						}
					}
				}

				if ((loanInfo.getBorrowType() == 2 || loanInfo.getBorrowType() == 4)) {
					log.info("91征信接口返回有数据,借款类型为2或者4(2.个人抵押 4.企业抵押)，如果还款状态，除1（正常）以外的任何情况,有数据配置值，" + dataDYConfig);

					 if(loanInfo.getRepayState() != 1){
						if ("1".equals(dataDYConfig)) {
							log.info("通过");
							creditResult=true; //更改征信结果标记
						}else{
							log.info("拒贷");
							creditResult=false; //更改征信结果标记
							break;
						}
					}else{
						log.info("loanInfo.getRepayState():" + loanInfo.getRepayState());
					}

				}
				
				if(loanInfo.getBorrowState()  == 2 || loanInfo.getBorrowState() == 3 || loanInfo.getBorrowState() == 6) {
					log.info("91征信接口返回有数据,借款类型为2.批贷已放款||3.批贷未放款||6.待放款&&非正常还款,有数据配置值，" + dataDYConfig );

					if( loanInfo.getRepayState() != 1){
						if ("1".equals(dataFKConfig)) {
							log.info("通过");
							creditResult=true; //更改征信结果标记
						}else{
							log.info("拒贷");
							creditResult=false; //更改征信结果标记
							break;
						}
					}else{
						log.info("loanInfo.getRepayState():" + loanInfo.getRepayState());
					}
						
				}else if(loanInfo.getBorrowState()   == 1 || loanInfo.getBorrowState()   == 5 ){
					log.info("91征信接口返回有数据,借款类型为1.拒贷|| 5.审核中,有数据配置值，" + dataDYConfig );

					if ("1".equals(dataJdshConfig)) {
						log.info("通过");
						creditResult=true; //更改征信结果标记
					}else{
						log.info("拒贷");
						creditResult=false; //更改征信结果标记
						break;
					}

				}

			}

		}
		return creditResult;
	}

	@Override
	public void modifyThirdPartCreditStatus(String totalId, boolean creditStatus) {

		int status = 0;
		try {
			status = userDAO.queryUserThirdPartCreditStatus(totalId);
		} catch (Exception e) {
			log.error("数据库中没有查到totalId=》" + totalId + "对应的业务表数据，故不进行修改");
			return;
		}
		int realStatus = StatusConstants.THIRD_PARTY_CREDIT_STATUS_PASS;

		if (!creditStatus) {
			realStatus = StatusConstants.THIRD_PARTY_CREDIT_STATUS_NOT_PASS;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("infoId", totalId);
		map.put("thirdPartCreditStatus", realStatus);
		map.put("updateTime", new Date());
		// 如果数据库状态为不成功，则不再修改
		if (status == StatusConstants.THIRD_PARTY_CREDIT_STATUS_NOT_PASS.intValue()) {
			return;
		}

		// 如果数据库状态是初始化状态，修改
		if (status == StatusConstants.THIRD_PARTY_CREDIT_STATUS_INIT.intValue()) {
			userDAO.modifyUserThirdPartCreditStatus(map);
			return;
		}

		// 如果数据库状态是成功状态，当前状态也是成功，则不修改，否则修改
		if (status == StatusConstants.THIRD_PARTY_CREDIT_STATUS_PASS.intValue()) {
			if (realStatus != status) {
				userDAO.modifyUserThirdPartCreditStatus(map);
			}
			return;
		}

	}

	@Override
	public int modifyJyRecordResultStatus(UserJyzxRecord record) {

		return userJyzxRecordDAO.modifyJyRecordResultStatus(record);
	}

	@Override
	public byte[] receiveCreditDataResult(HttpServletRequest request, String companyCode) {

		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		int num = 0;
		byte[] recData = null; // 接受数据

		PkgHeader recPkg = new PkgHeader(); // 接受报文
		PkgHeader rspPkg = new PkgHeader(); // 回复91的报文
		rspPkg.setCustNo(companyCode); // 配置服务器编号

		// 读取数据
		try {
			while ((num = request.getInputStream().read(buf, 0, 4096)) > 0) {
				swapStream.write(buf, 0, num);
			}
			recData = swapStream.toByteArray();
			swapStream.close();
			// log.info("直接接收到的数据数组为：" + CommonUtils.byteArray2HexStr(recData));
		} catch (Exception e) {
			log.error("读取91返回征信数据时发生错误", e);
			rspPkg.setRetCode("9999");
			rspPkg.setRetMsg("系统异常消息:读取数据发生错误");
			rspPkg.setMsgBody("");
			return rspPkg.toPkgBytes("UTF-8");
		}

		// 解析数据
		try {
			recPkg.parseFormBytes(recData, recPkg.getEncode()); // 解析请求报文，将报文转化为对象
			log.info("直接接收到91的数据str为：" + JsonSerializer.serializer(recPkg));
			
			rspPkg.setEncode(recPkg.getEncode()); // 设置编码
			rspPkg.setEncryptType(recPkg.getEncryptType()); // 设置加密类型
			rspPkg.setMsgType(recPkg.getMsgType()); // 设置消息类型
			rspPkg.setVersion(recPkg.getVersion());// 设置版本

			rspPkg = dealWithRecMsg(recPkg, rspPkg); // 处理接收到的信息

			log.info("直接回复91的数据str为：" + JsonSerializer.serializer(rspPkg));
			// log.info("准备回复的数据数组为：" + CommonUtils.byteArray2HexStr(rspData));
		} catch (Exception e) {
			log.error("解析91返回征信数据时发生错误", e);
			rspPkg.setRetCode("9999");
			rspPkg.setRetMsg("系统异常消息：数据处理失败");
			rspPkg.setMsgBody("");
			return rspPkg.toPkgBytes("UTF-8");
		}

		return rspPkg.toPkgBytes("UTF-8");
	}

	public PkgHeader dealWithRecMsg(PkgHeader reqPkg, PkgHeader rspPkg) throws Exception {

		String txnCode = reqPkg.getTrxCode();
		int param = 0;
		try {
			param = Integer.parseInt(txnCode);
		} catch (Exception e) {
			log.error("转换txnCode失败", e);
			throw  new RuntimeException("转换txnCode失败:"+ e.getMessage());
		}
		switch (param) {
		// 客户端查询处理
		case 3001: {
			// 设置回复代码
			log.info("3001--->91方面对我方的数据请求");
			rspPkg.setTrxCode("4001");
			Pkg4001 pkg4001 = new Pkg4001();
			pkg4001.setLoanInfos(null);
			String msgBody = JsonSerializer.serializer(pkg4001);
			rspPkg.setMsgBody(msgBody); // 报文主体
			log.debug("针对3001的报文回复null");
			break;
		}
		case 3002: {
			log.info("3002--->91异步通知，报文主体为：" + reqPkg.getMsgBody());
			UserJyzxRecord record = new UserJyzxRecord();
			Pkg3002 pkg3002;
			
			//解析
			try {
				pkg3002 = (Pkg3002) JsonSerializer.deserializer(reqPkg.getMsgBody(), new TypeReference<Pkg3002>() {
				});
			} catch (Exception e) {
				 log.error("解析91返回征信数据时发生错误",e);
				 throw new RuntimeException("解析91返回征信数据时发生错误:"+e.getMessage());
			}
			
			
			if (pkg3002 != null && pkg3002.getLoanInfos() != null) {
				log.info("此人的借款信息数量为：" + pkg3002.getLoanInfos().size());
				if (pkg3002.getLoanInfos().size() > 0) {
					record.setResultStatus(StatusConstants.RESPONSE_STATUS_JYZX_DATA);
				} else {
					record.setResultStatus(StatusConstants.RESPONSE_STATUS_JYZX_NODATA);
				}
			}
			// 设置回复代码
			rspPkg.setTrxCode("4002");
			rspPkg.setRetCode("0000");
			log.debug("针对3002的报文回复0000");

			
			//业务处理
//			String totalId = userJyzxRecordDAO.queryTotalInfoId(pkg3002.getTrxNo());
			UserJyzxRecord jyzxRecord = null;
			int times = 3;
			try {
				times = Integer.parseInt(ConfigUtils.getProperty("jy_notify_wait_times"));
			} catch (Exception e) {
				log.error("找不到91异步通知等待次数配置:jy_notify_wait_times");
			}
			for (int i = 0; i < times; i++) {
				log.info("3002--->91异步通知，第" + i + "次查询");
				jyzxRecord = userJyzxRecordDAO.queryUserJyzxRecordByTrxNo(pkg3002.getTrxNo());
				if(EntityUtils.isNotEmpty(jyzxRecord)){
					log.info("3002--->91异步通知，交易流水号：" + pkg3002.getTrxNo() + ",在数据库中查到对应的totalId");
					break;
				}else{
					log.warn("3002--->91异步通知，交易流水号：" + pkg3002.getTrxNo() + ",在数据库中没有查到对应的totalId");
					Thread.sleep(3000l);
				}
			}
			if (EntityUtils.isEmpty(jyzxRecord)) {
				log.error("3002--->91异步通知，交易流水号：" + pkg3002.getTrxNo() + ",在数据库中没有查到对应的totalId");
				rspPkg.setRetCode("9999");
				break;
			}

			record.setTrxNo(pkg3002.getTrxNo());
			//存储请求结果状态
			try {
				userJyzxRecordDAO.modifyJyRecordResultStatus(record);
			} catch (Exception e) {
				log.error("更改91结果状态时发生错误，", e);
				 throw new RuntimeException("更改91结果状态时发生错误，"+e.getMessage());
			}
			
			//存储子项
			List<UserJyzxRecordItem> itemList = null ;
			try {
				itemList = this.addJyzxRecordItems(pkg3002);
			} catch (Exception e) {
				log.error("增加91子项信息发生错误", e);
				throw new RuntimeException("增加91子项信息发生错误"+e.getMessage());
			}
			
			//临时解决方案
			if(EntityUtils.isEmpty(itemList)){
				UserJyzxRecordItem item = new UserJyzxRecordItem();
				item.setTrxNo(pkg3002.getTrxNo());
				
				itemList.add(item);
			}
			
			//根据callType判断是否需要更改最终征信的第三方结果
//			String callType = userJyzxRecordDAO.queryCallType(pkg3002.getTrxNo());
			String callType = jyzxRecord.getCallType();
			if (StatusConstants.JY_CALL_TYPE_CREDIT.equals(callType)) {
				log.info("调用类型为：" + callType + "，更改征信结果。pkg3002.getTrxNo()：" + pkg3002.getTrxNo());
				try {
					this.judgeCreditInfos(jyzxRecord.getTotalInfoId(),pkg3002);
				} catch (Exception e) {
					log.error("根据91反馈结果，更改最终征信的第三方结果时发生错误，", e);
					throw new RuntimeException("根据91反馈结果，更改最终征信的第三方结果时发生错误，"+e.getMessage());
				}
			}else if(StatusConstants.JY_CALL_TYPE_STUDENT_CREDIT.equals(callType)){
				log.info("调用类型为：" + callType + "，该类型为学生征信，调用学生征信91规则判断。pkg3002.getTrxNo()：" + pkg3002.getTrxNo());
				try {
					this.judgeStudentCreditInfos(jyzxRecord.getTotalInfoId(), jyzxRecord, itemList);
				} catch (Exception e) {
					log.error("根据91反馈结果，更改最终征信的第三方结果时发生错误，", e);
					throw new RuntimeException("根据91反馈结果，更改最终征信的第三方结果时发生错误，"+e.getMessage());
				}
			}else{
				log.info("调用类型为：" + callType + "，不更改征信结果。pkg3002.getTrxNo()：" + pkg3002.getTrxNo());
			}

			
			break;
		}

		default: {
			throw new Exception("未知的报文类型");
		}
		}

		return rspPkg;
	}

	@Override
	public Map<String, Object> judgeStudentCreditInfos(String busId, UserJyzxRecord jyzxRecord, List<UserJyzxRecordItem> pkg3002) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		log.info("totalId:" + busId + ",91征信结果生效。");
		// 查询征信记录表主键，为了修改征信模块记录表征信结果
		boolean result = this.modifySchoolCreditRecord(busId,jyzxRecord, pkg3002);
		if (!result) {
			log.info("totalId:" + busId + ",91征信未通过。");
			creditCollegeService.updateJsSchoolBusCreditResult(busId,CreditCodeConstants.CREDIT_JY_RECORD_UNPASS);
			
			retMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_FAIL_DESC);
		} else {
			log.info("totalId:" + busId + ",91征信通过。");
			
			retMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.CRE_SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.CRE_SUCCESS_DESC);
		}
		
		return retMap;
	}

	@Override
	public boolean jyStudentCreditRule(List<UserJyzxRecordItem> pkg3002) {
		boolean creditResult=true; //查询客户的91征信结果
		boolean borrowStateFlag = false; //多笔借贷数据中，有一笔borrowState=2，其他借款数据的borrowState=[0,2,3,4,5,6]，直接拒贷。 这种情况，只要有2，修改为true；
		int borrowStateSum = 0 ;
		for (UserJyzxRecordItem loanInfo : pkg3002) {
			if (loanInfo.getBorrowState() == 1) {
				//所有借贷数据中，若有borrowState=1（即拒贷数据），直接拒贷
				//借款状态 0.未知1.拒贷2.批贷已放款3.批贷未放款4.借款人放弃申请5.审核中6.待放款
				log.info("借款状态拒贷,91征信不通过。");
				creditResult=false;
				break;
			}
			
			if(loanInfo.getRepayState() >= 2 && loanInfo.getRepayState() <=8){ //还款状态 0.未知1.正常2.M1 3.M2 4.M3 5.M4 6.M5 7.M6 8.M6+
				//还款状态 0.未知1.正常2.M1 3.M2 4.M3 5.M4 6.M5 7.M6 8.M6+
				//所有借贷数据中，若有repayState in （2,3,4,5,6,7,8），直接拒贷
				log.info("还款状态为, "  + loanInfo.getRepayState()  +  " 91征信不通过。");
				creditResult=false;
				break ;
			}
			
			if(loanInfo.getBorrowState() == 2 ){ 
				borrowStateFlag = true;
			}
			if (borrowStateFlag && (loanInfo.getBorrowState() == 0 || (loanInfo.getBorrowState() >=2 && loanInfo.getBorrowState() <=6))) {
				log.info("有一笔borrowState=2（即已放款），其他借款数据的borrowState="+ loanInfo.getBorrowState() +  " ,91征信不通过。");
				creditResult=false;
				break ;
			}
			
			if (pkg3002.size() >= 3 && (loanInfo.getBorrowState() == 0 || (loanInfo.getBorrowState() >=3 && loanInfo.getBorrowState() <=6))) {
				log.info("有"+pkg3002.size() +"条借贷数据大于等于3条，且借款状态borrowState="+ loanInfo.getBorrowState());
				//其他借款数据的borrowState=[0,2,3,4,5,6]，直接拒贷。
				borrowStateSum ++;
			}
			
			if (borrowStateSum == 3) {
				log.info("有"+pkg3002.size() +"条借贷数据大于等于3条，且借款状态borrowState=[0,3,4,5,6],91征信不通过。");
				creditResult=false;
				break ;
			}
			
		}
		return creditResult;
	}

	public void judgeCreditInfos(String totalId, Pkg3002 pkg3002) {
		boolean creditResult=true; //查询客户的91征信结果
		
		//从memcached中取出数据
		Map<String, String> meMap = null;
		try {
			meMap = configCreditKvService.getAllCreditKv();
		} catch (Exception e3) {
			throw new RuntimeException("调用好贷征信接口，获取征信配置数据异常");
		}

		
		if (meMap == null) {
			throw new RuntimeException("调用好贷征信接口，获取征信配置数据为空");
		}
		try {
			
			//征信结果判断开关
			String _CROpen = (String) (meMap.get(CreditCfgKeyConstants.JY_CR_BLACKLIST_ISOPEN));
			String creditId = userCreditDataService.queryCreditIdByTotalId(totalId);
			if ("1".equals(_CROpen)) {
				//征信结果处理
				creditResult=this.queryJyCreditResult(pkg3002);
				
				//修改业务表中征信结果
				//this.modifyThirdPartCreditStatus(totalId, creditResult);
				log.info("totalId:" + totalId + ",91征信结果生效。");
				// 查询征信记录表主键，为了修改征信模块记录表征信结果
				boolean result = this.modifyCreditRecord(totalId, creditResult);
				if (!result) {
					log.info("totalId:" + totalId + ",91征信未通过。");
					userCreditDataService.insertCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_91_BLACKLIST, CreditCodeConstants.CREDIT_TYPE_91_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_JY_RECORD_UNPASS);
					return ;
				} else {
					log.info("totalId:" + totalId + ",91征信通过。");
				}

			} else {
				log.info("totalId:" + totalId + ",91征信结果失效。");

			}
			userCreditDataService.insertCreditModelRecord(creditId, _CROpen, CreditCodeConstants.CREDIT_91_BLACKLIST, CreditCodeConstants.CREDIT_TYPE_91_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_JY_RECORD_PASS);
		} catch (Exception e) {
			log.error("根据91结果更改业务表数据状态时发生错误，", e);
			throw e;
		}
	}

	@Override
	public boolean modifyCreditRecord(String totalId, boolean creditResult) {

		if (creditResult) {
			log.info("totalId:" + totalId + ",91征信通过。");
			return true;
		} else {
			log.info("totalId:" + totalId + ",91征信未通过。");

			UserCreditRecord creditRecord = new UserCreditRecord();
			creditRecord.setInfoId(totalId);
			creditRecord.setIsPass(StatusConstants.CREDIT_IS_NOT_PASS);
			creditRecord.setReason(CreditCodeConstants.CREDIT_JY_RECORD_UNPASS);
			creditRecord.setUpdateTime(new Date());
			userCreditDataDAO.updateUserCreditRecordByInfoId(creditRecord);
			return false;
		}

	}
	@Override
	public boolean modifySchoolCreditRecord(String busId, UserJyzxRecord jyzxRecord, List<UserJyzxRecordItem> pkg3002) {
		
		boolean creditResult=true; //查询客户的91征信结果
		//征信结果处理
		creditResult=this.jyStudentCreditRule(pkg3002);
		//通过业务id查询征信记录表id
		String creditId = userCreditDataService.queryCreditIdByTotalId(busId);
		String jyRecordId = userCreditDataService.queryJyRecordId(jyzxRecord.getTrxNo());
		
		if (creditResult) {
			log.info("totalId:" + busId + ",91征信通过。");
			creditCollegeService.insertShoolCreditModelRecord(creditId, "1", CreditCodeConstants.CREDIT_91_BLACKLIST, CreditCodeConstants.CREDIT_TYPE_91_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_SUCCESS, CreditCodeConstants.CREDIT_JY_RECORD_PASS,jyRecordId);
			return true;
		} else {
			log.info("totalId:" + busId + ",91征信未通过。");
			
			UserCreditRecord creditRecord = new UserCreditRecord();
			creditRecord.setInfoId(busId);
			creditRecord.setIsPass(StatusConstants.CREDIT_IS_NOT_PASS);
			creditRecord.setReason(CreditCodeConstants.CREDIT_JY_RECORD_UNPASS);
			creditRecord.setUpdateTime(new Date());
			userCreditDataDAO.updateUserCreditRecordByInfoId(creditRecord);
			creditCollegeService.insertShoolCreditModelRecord(creditId, "1", CreditCodeConstants.CREDIT_91_BLACKLIST, CreditCodeConstants.CREDIT_TYPE_91_BLACKLIST, StatusConstants.CREDIT_CHECKING_RESULT_FAILED, CreditCodeConstants.CREDIT_JY_RECORD_UNPASS,jyRecordId);

			return false;
		}
		
	}
	
	private boolean compareDiffOrg(String queryConfig, String config) {

		if (EntityUtils.isEmpty(config)) {
			log.info("91返回无配置信息。");
			return false;
		}
		
		if (Integer.parseInt(config) >= Integer.parseInt(queryConfig)) {
			log.info("91返回配置信息config:" + config + ",配置表配置数据为：" + queryConfig + ",根据配置，" + config + " >=" + queryConfig + " 拒绝征信。");
			return true;
		}
		log.info("91返回配置信息config:" + config + ",配置表配置数据为：" + queryConfig + ",根据配置，" + config + " <" + queryConfig + " 可以征信。");
		return false;

	}

	@Override
	public UserJyzxRecord queryLastestEffectJyzxRecord(String idCard, String name) {
		// TODO Auto-generated method stub
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("idCard", idCard);
		paramMap.put("name", name);
		
		UserJyzxRecord jyzxRecord = userJyzxRecordDAO.queryLastestEffectiveJyzxRecord(paramMap);
		
		if (EntityUtils.isNotEmpty(jyzxRecord)) {
			int day = 0 ;
			try {
				day = DateUtil.daysBetween(jyzxRecord.getCreateTime(),new Date());
				
				if (day < 90) {
					log.info("查询核心91借贷接口，计算获取天数未超过90天，结束征信点。" + day  + "天。");
					return jyzxRecord;
				}
			} catch (Exception e) {
				log.info("查询核心91借贷接口，计算获取天数异常。",e);
				
			}
			log.info("查询核心91借贷接口，计算获取天数超过90天，结束征信点。" + day  + "天。调用91借贷接口");
		}
		
		return null;
	}

	@Override
	public List<UserJyzxRecordItem> queryUserJyzxRecordItemsByTrxNo(String trxNo) {
		// TODO Auto-generated method stub
		return userJyzxRecordItemsDAO.queryUserJyzxRecordItemsByTrxNo(trxNo);
	}

}
