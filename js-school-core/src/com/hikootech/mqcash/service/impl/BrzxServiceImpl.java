package com.hikootech.mqcash.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bfd.facade.MerchantBean;
import com.bfd.facade.MerchantServer;
import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dao.BrCreditDAO;
import com.hikootech.mqcash.po.BrApplyLoan;
import com.hikootech.mqcash.po.SpecialList_c;
import com.hikootech.mqcash.po.UserBrRequest;
import com.hikootech.mqcash.po.UserBrSpeciallistC;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.service.BrzxService;
import com.hikootech.mqcash.service.SysAlarmService;
import com.hikootech.mqcash.util.BRUtils;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.MemCachedUtils;
import com.hikootech.mqcash.web.BrzxController;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Service("brzxService")
public class BrzxServiceImpl implements BrzxService {
	private static Logger logger = LoggerFactory.getLogger(BrzxController.class);
	
	@Autowired
	private BrCreditDAO brCreditDAO;
	@Autowired
	private SysAlarmService sysAlarmService;
	
	@Override
	public UserBrSpeciallistC transMsgToBr(UserBrRequest brRequestPo)  {
		
		String portrait_result = "";
		String reqId = GenerateKey.getId(CommonConstants.USER_BR_REQ_ID_PREFIX, ConfigUtils.getProperty("db_id"));
		brRequestPo.setId(reqId);
		try {
			portrait_result = this.reqBr(brRequestPo);
		} catch (Exception e2) {
			logger.error("请求百融接口异常",e2);
		}
		
		UserBrSpeciallistC  specialIdCard = null;
		String code = "";
		try {
			code = "0".equals(JSONObject.fromObject(portrait_result).getString("code").toString())?"00":JSONObject.fromObject(portrait_result).getString("code").toString();
			 if ("00".equals(code)) {
				 String  specialListC = JSONObject.fromObject(portrait_result).getString("SpecialList_c").toString();
				 if (EntityUtils.isNotEmpty(specialListC)) {
				 		SpecialList_c sp  = (SpecialList_c) JSONObject.toBean(JSONObject.fromObject(specialListC),SpecialList_c.class);
				 		String idCardData = JSONObject.fromObject(specialListC).getString("id").toString();
				 		logger.info("SpecialList_c，身份证对应的数据：" + idCardData);
				 		if (sp != null && EntityUtils.isNotEmpty(idCardData)) {
							 JSONObject idCardDataJSON = JSONObject.fromObject(idCardData);
							 
							 specialIdCard = (UserBrSpeciallistC) JSONObject.toBean(idCardDataJSON,UserBrSpeciallistC.class);
							 logger.info("解析百融特殊名单身份证核查，数据为：" + specialIdCard.toString());
							 specialIdCard.setType(StatusConstants.SPECIAL_LIST_C_IS_BLACKLIST);
						}
				 		
					 }
			 	 logger.info("解析SpecialList_c：" + specialListC );
			 	 
			 }
			 
		} catch (Exception e1) {
			logger.error("解析SpecialList_c数据异常,portrait_result:"+portrait_result ,e1);
			throw new RuntimeException("解析SpecialList_c数据异常,portrait_result:"+portrait_result,e1);
		}
		
		 String  swiftNumber = "";
	 	 try {
	 		 swiftNumber = JSONObject.fromObject(portrait_result).getString("swift_number").toString();
	 	 } catch (Exception e) {
	 		swiftNumber = "";
	 	 }
	 	logger.info("swiftNumber流水号：" +swiftNumber);
	 	 
	 	 String  flag =  "" ;
		 try {
			 flag = JSONObject.fromObject(portrait_result).getString("Flag").toString();
		} catch (Exception e) {
			flag = "";
		}
	 	 logger.info("flag:" + flag);
	 	 
		 if (specialIdCard == null) {
			 logger.info("specialIdCard is null,重新实例对象。");
			specialIdCard = new UserBrSpeciallistC();
			specialIdCard.setType(StatusConstants.SPECIAL_LIST_C_IS_NOT_BLACKLIST);
		 }

		 specialIdCard.setBrReqId(reqId);
		 specialIdCard.setCode(code);
		 specialIdCard.setFlag(flag);
		 specialIdCard.setSwift_number(swiftNumber);
		 specialIdCard.setCreateTime(new Date());
		 specialIdCard.setSpeciallistId(GenerateKey.getId(CommonConstants.USER_BR_REQ_SPECIALLIST_ID_PREFIX, ConfigUtils.getProperty("db_id")));
		 logger.info("插入百融数据表信息：" + specialIdCard.toString());
		 brCreditDAO.saveBrRequestSpecialListC(specialIdCard);
		 logger.info("插入百融SpecialList_c身份证对应数据成功");
	 
		 return specialIdCard;
	}
	@Override
	public String reqBr(UserBrRequest brRequestPo) throws Exception{
		MerchantBean merchantBean=new MerchantBean();
		merchantBean.setName(brRequestPo.getName()); 
        merchantBean.setId(brRequestPo.getIdCard());
        merchantBean.setCell(brRequestPo.getMobile());
        merchantBean.setHome_addr(brRequestPo.getHomeAddress());
        merchantBean.setBiz_addr(brRequestPo.getCompanyAddress());
        merchantBean.setMeal(brRequestPo.getProductName());
       
        MerchantServer ms=new MerchantServer();
        String tokenid = MemCachedUtils.get(CommonConstants.BR_USER_LOGIN_TOKEN_KEY);
		if (EntityUtils.isEmpty(tokenid)) {
			logger.info("百融特殊名单征信，从缓存中获取的tokenid为空，重新获取id");
			tokenid = this.LoginBr(ms);
			cachedTokenId(tokenid);
		}
		
		String portrait_result="";
		//调用登陆接口，返回tokenid
		merchantBean.setTokenid(tokenid);
		logger.info("发送至百融的查询内容："+JSONObject.fromObject(merchantBean).toString());
		
		try {
			portrait_result = ms.getUserPortrait(merchantBean);
			logger.info("百融返回信息：" + portrait_result);
		} catch (Exception e) {
			logger.error("调用百融贷接口发生错误",e);
			throw new RuntimeException("调用百融贷接口发生错误",e);
		}
		
		//保存请求百融信息
		brRequestPo.setCreateTime(new Date());
		brCreditDAO.saveBrRequestParams(brRequestPo);
				
		String  code = "0".equals(JSONObject.fromObject(portrait_result).getString("code").toString())?"00":JSONObject.fromObject(portrait_result).getString("code").toString();
		if ("100007".equals(code)) {//Tokenid过期,重新登录请求百融
			 tokenid = this.LoginBr(ms);
			 cachedTokenId(tokenid);
			 merchantBean.setTokenid(tokenid);
			 logger.info("重新登录后，发送至百融的查询内容："+JSONObject.fromObject(merchantBean).toString());
			 
			 try {
					portrait_result = ms.getUserPortrait(merchantBean);
					logger.info("百融返回信息：" + portrait_result);
				} catch (Exception e) {
					logger.error("调用百融贷接口发生错误",e);
				}
		}
		
		return portrait_result;
	}
	@Override
	public String testTransMsgToBr(UserBrRequest brRequestPo) throws Exception {
		
		MerchantBean merchantBean=new MerchantBean();
		merchantBean.setName(brRequestPo.getName()); 
        merchantBean.setId(brRequestPo.getIdCard());
        merchantBean.setCell(brRequestPo.getMobile());
        merchantBean.setHome_addr(brRequestPo.getHomeAddress());
        merchantBean.setBiz_addr(brRequestPo.getCompanyAddress());
        merchantBean.setMeal(brRequestPo.getProductName());
	
		//调用登陆接口，返回tokenid
		MerchantServer ms=new MerchantServer();
		String tokenid = this.LoginBr(ms);
		merchantBean.setTokenid(tokenid);
		
		logger.info("发送至百融的查询内容："+JSONObject.fromObject(merchantBean).toString());
		
		long begin=new Date().getTime();
		String portrait_result="";
		try {
			portrait_result = ms.getUserPortrait(merchantBean);
		} catch (Exception e) {
			logger.error("调用百融贷接口发生错误",e);
			throw new RuntimeException("调用百融贷接口发生错误",e);
		}
		 String retMsg=testJson(portrait_result,0,0,"","");
		 logger.info("转换后的百荣结果result:" + retMsg);
		 logger.info("------------------------------------------");
		 
		 long end=new Date().getTime();
		 logger.info("time:"+(end-begin)+" ms");
		 logger.info("------------------------------------------");
		return retMsg;
	}
	
	@Override
	public String LoginBr(MerchantServer ms) throws Exception {
		
		//登陆
		String login_result = "";
		try {
			 
			login_result = ms.login(ConfigUtils.getProperty("brxd_username"),ConfigUtils.getProperty("brxd_password"));
		} catch (Exception e) {
			logger.error("百融贷调用登陆接口发生错误",e);
			throw new RuntimeException("百融贷调用登陆接口发生错误",e);
		}
		 logger.info("百融贷调用登陆接口反馈报文：" + login_result);
		
		JSONObject json=  JSONObject.fromObject(login_result);
		
		String tokenid=json.getString("tokenid");
		logger.info("百融贷调用登陆接口生成tokenid:" + tokenid);
		if(EntityUtils.isEmpty(tokenid)){
			throw new RuntimeException("登陆百融贷接口返回的tokenid为空");
		}
		 
		return tokenid;
	}

	
		@Override
		public List<UserInfo> queryUserInfoList() {
			return brCreditDAO.queryUserInfoList();
		}
		@Override
		public void updateBrMsg(UserInfo userDetailVo) {
			brCreditDAO.updateBrMsg(userDetailVo);
			
		}
		
		public static void cachedTokenId(String tokenid){
			//将新的tokenid 刷近缓存
			MemCachedUtils.set(CommonConstants.BR_USER_LOGIN_TOKEN_KEY, tokenid);
		}
		
		public static String testJson(String json,int array_param,int obj_param,String str_null,String ret){
			try {
				JSONObject jsonObject = JSONObject.fromObject(json);
				Iterator it = jsonObject.keys();
				String str_1 = "";
				String str_objec_rep="";
				if(array_param==0){//如果是第一个，那么为空
					str_1+="";
				}
				else{
					str_1+="      ";
				}
				if(obj_param==0){
					str_1+="";
				}
				else{       //如果不是第一个，那么都增加"     "输出
					str_1+="      ";
				}
				str_null+=str_1;
				while (it.hasNext()) {
					Object str=it.next();
					if(jsonObject.get(str).toString().contains("[")){//如果是array
						JSONArray jsonArray = jsonObject.getJSONArray(str.toString());
						ret+=str+":"+"\r\n";
						//System.out.println(str+":");
						for (int i = 0; i < jsonArray.size(); i++) {
							String str_array=jsonArray.getString(i);
							ret=testJson(str_array,1,0,str_null,ret);
						}
					}
					else{//如果不是array  object
						String json_obj=jsonObject.get(str).toString();
						if(json_obj.contains("{")){
							ret+=str_null+str+":"+"\r\n";
							//System.out.println(str_null+str+":");
							ret=testJson(json_obj,0,1,str_null,ret);
						}
						else{
							ret+=str_null+str+":"+jsonObject.get(str)+"\r\n";
							//System.out.println(str_null+str+":"+jsonObject.get(str));
						}
					}
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}
			return ret;

		}
	
		
	@Override
	public Map<String, Object> requestBR(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		logger.info("请求百融");
		
		if(EntityUtils.isEmpty(paramMap.get("UserBrRequest"))){
			logger.error("请求参数为空");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.UNVALID);
			retMap.put(ResponseConstants.RETURN_DESC, "请求参数为空！");
			return retMap;
		}
		
		UserBrRequest brRequest = (UserBrRequest) paramMap.get("UserBrRequest");
		if(EntityUtils.isEmpty(brRequest.getProductName())){
			logger.error("请求产品代号为空");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.UNVALID);
			retMap.put(ResponseConstants.RETURN_DESC, "请求产品代号为空！");
			return retMap;
		}
		
		if(EntityUtils.isEmpty(brRequest.getName())){
			logger.error("请求姓名为空");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.UNVALID);
			retMap.put(ResponseConstants.RETURN_DESC, "请求姓名为空！");
			return retMap;
		}
		
		if(EntityUtils.isEmpty(brRequest.getIdCard())){
			logger.error("请求身份证号为空");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.UNVALID);
			retMap.put(ResponseConstants.RETURN_DESC, "请求身份证号为空！");
			return retMap;
		}
		
		if(EntityUtils.isEmpty(brRequest.getMobile())){
			logger.error("请求手机号为空");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.UNVALID);
			retMap.put(ResponseConstants.RETURN_DESC, "请求手机号为空！");
			return retMap;
		}
		
		Map<String, Object> resultMap = null;
		try {
			resultMap = BRUtils.reqBR(brRequest);
		} catch (Exception e) {
			logger.error("请求百融出错!", e);
			sysAlarmService.alarm("请求百融出错!");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请求百融出错!");
		}
		
		
		//保存请求百融信息
		String reqId = GenerateKey.getId(CommonConstants.USER_BR_REQ_ID_PREFIX, ConfigUtils.getProperty("db_id"));
		brRequest.setId(reqId);
		brRequest.setCreateTime(new Date());
		brCreditDAO.saveBrRequestParams(brRequest);
		
		String code = null;
		try {
			code = (String) resultMap.get("code");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("百融结果码转换出错！code : " + code, e);
		}
		if(EntityUtils.isEmpty(resultMap) 
				|| EntityUtils.isEmpty(code)
				|| !("00".equals(code) || "100002".equals(code))){
			logger.error("请求百融失败，错误码：" + code);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请求百融出错!");
			return retMap;
		}
		
//		if("100002".equals(code)){
//			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
//			retMap.put(ResponseConstants.RETURN_DESC, "百融请求成功");
//			return retMap;
//		}
		
		Object portrait = null;
		String portrait_result = (String) resultMap.get("portrait_result");
		logger.info("portrait_result:" + portrait_result + ",brRequest.getProductName():" + brRequest.getProductName());
		try {
			portrait = BRUtils.parseJSON(brRequest.getProductName(), JSONObject.fromObject(portrait_result));
		} catch (Exception e) {
			logger.error("转换百融返回json数据出错!",e);
			sysAlarmService.alarm("转换百融返回json数据出错!");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "转换百融返回json数据出错!");
			return retMap;
		}
		
		switch (brRequest.getProductName()) {
			case CommonConstants.BR_MEAL_SPECIALLIST_C:
				List<UserBrSpeciallistC> list = (List<UserBrSpeciallistC>) portrait;
				for(UserBrSpeciallistC specialList :list){
					specialList.setBrReqId(reqId);
					specialList.setCreateTime(new Date());
					specialList.setSpeciallistId(GenerateKey.getId(CommonConstants.USER_BR_REQ_SPECIALLIST_ID_PREFIX, ConfigUtils.getProperty("db_id")));
					this.brCreditDAO.saveBrRequestSpecialListC(specialList);
				}
				logger.info("转换特殊名单对象成功");
				break;
			case CommonConstants.BR_MEAL_APPLYLOAN:
				BrApplyLoan applyLoan = (BrApplyLoan) portrait;
				
				String applyLoanId = GenerateKey.getId(CommonConstants.USER_BR_REQ_APPLYLOAD_ID_PREFIX, ConfigUtils.getProperty("db_id"));
				applyLoan.setId(applyLoanId);
				applyLoan.setBrReqId(reqId);
				applyLoan.setCreateTime(new Date());
				this.brCreditDAO.saveBrApplyLoan(applyLoan);
				
				logger.info("转换多次申请核查对象成功");
				break;
			default:
				break;
		}
		
		retMap.put("portrait", portrait);
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "百融请求成功");
		return retMap;
	}
	
	
}
