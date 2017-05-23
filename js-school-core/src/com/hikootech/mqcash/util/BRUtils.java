package com.hikootech.mqcash.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bfd.facade.MerchantBean;
import com.bfd.facade.MerchantServer;
import com.bfd.facade.PortraitBean;
import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.po.BrApplyLoan;
import com.hikootech.mqcash.po.UserBrRequest;
import com.hikootech.mqcash.po.UserBrSpeciallistC;

import net.sf.json.JSONObject;

/**
 * @ClassName BRUtils
 * @Description 百融贷工具类
 * @author 余巍 yuweiqwe@126.com
 * @date 2016年5月25日 下午4:10:47
 * 
 */
public class BRUtils {

	private static Logger logger = LoggerFactory.getLogger(BRUtils.class);

	private static MerchantServer ms;
	private static String tokenId;

	public static MerchantServer getInstance() {
		if (ms == null) {
			synchronized (BRUtils.class) {
				if (ms == null) {
					ms = new MerchantServer();
				}
			}
		}
		return ms;
	}
	
	public static String getTokenId(){
		logger.debug("获取当前tokenid");
		if(EntityUtils.isEmpty(tokenId)){
			String tokenid = MemCachedUtils.get(CommonConstants.BR_USER_LOGIN_TOKEN_KEY);
			if(EntityUtils.isEmpty(tokenid)){
				synchronized (BRUtils.class) {
					if(EntityUtils.isEmpty(tokenId)){
						tokenid = login();
						tokenId = tokenid;
						
						logger.debug("返回tokenid：" + tokenId);
						return tokenId;
					}
				}
			}else{
				tokenId = tokenid;
			}
		}
		
		logger.debug("返回tokenid：" + tokenId);
		return tokenId;
	}

	public static String login() {
		logger.info("百融登陆");
		getInstance();
		// 登陆
		String login_result = "";
		try {
			login_result = ms.login(ConfigUtils.getProperty("brxd_username"), ConfigUtils.getProperty("brxd_password"));
		} catch (Exception e) {
			logger.error("百融贷调用登陆接口发生错误", e);
			throw new RuntimeException("百融贷调用登陆接口发生错误", e);
		}
		logger.info("百融贷调用登陆接口反馈报文：" + login_result);

		JSONObject json = JSONObject.fromObject(login_result);
		logger.info("调用百融返回json：" + json.toString());
		String tokenid = json.getString("tokenid");
		logger.info("百融贷调用登陆接口生成tokenid:" + tokenid);
		if (EntityUtils.isEmpty(tokenid)) {
			throw new RuntimeException("登陆百融贷接口返回的tokenid为空");
		}
		
		cachedTokenId(tokenid);
		tokenId = tokenid;
		
		return tokenid;
	}

	public static void cachedTokenId(String tokenid) {
		logger.info("缓存tokenid：" + tokenid);
		// 将新的tokenid 刷近缓存
		MemCachedUtils.set(CommonConstants.BR_USER_LOGIN_TOKEN_KEY, tokenid);
	}

	public static List<UserBrSpeciallistC> parseSpeciallyCListJSON(JSONObject portrait_json) {
		UserBrSpeciallistC speciallistCId = new UserBrSpeciallistC();
		UserBrSpeciallistC speciallistCCell = new UserBrSpeciallistC();
		List<UserBrSpeciallistC> list = new ArrayList<UserBrSpeciallistC>();
		
		speciallistCId.setCode(JsonObjectUtils.getString(portrait_json.get("code")));
		speciallistCId.setType(StatusConstants.SPECIAL_LIST_C_TYPE_ID);
		speciallistCId.setSwift_number(JsonObjectUtils.getString(portrait_json.get("swift_number")));
		speciallistCId.setBank_bad(JsonObjectUtils.getString(portrait_json.get("sl_id_bank_bad")));
		speciallistCId.setBank_fraud(JsonObjectUtils.getString(portrait_json.get("sl_id_bank_fraud")));
		speciallistCId.setBank_lost(JsonObjectUtils.getString(portrait_json.get("sl_id_bank_lost")));
		speciallistCId.setBank_overdue(JsonObjectUtils.getString(portrait_json.get("sl_id_bank_overdue")));
		speciallistCId.setBank_refuse(JsonObjectUtils.getString(portrait_json.get("sl_id_bank_refuse")));
		speciallistCId.setP2p_bad(JsonObjectUtils.getString(portrait_json.get("sl_id_p2p_bad")));
		speciallistCId.setP2p_fraud(JsonObjectUtils.getString(portrait_json.get("sl_id_p2p_fraud")));
		speciallistCId.setP2p_lost(JsonObjectUtils.getString(portrait_json.get("sl_id_p2p_lost")));
		speciallistCId.setP2p_overdue(JsonObjectUtils.getString(portrait_json.get("sl_id_p2p_overdue")));
		speciallistCId.setP2p_refuse(JsonObjectUtils.getString(portrait_json.get("sl_id_p2p_refuse")));
		speciallistCId.setCourt_bad(JsonObjectUtils.getString(portrait_json.get("sl_id_court_bad")));
		speciallistCId.setCourt_executed(JsonObjectUtils.getString(portrait_json.get("sl_id_court_executed")));
		speciallistCId.setCredit_bad(JsonObjectUtils.getString(portrait_json.get("sl_id_credit_bad")));
		speciallistCId.setPhone_overdue(JsonObjectUtils.getString(portrait_json.get("sl_id_phone_overdue")));
		speciallistCId.setInsurance_fraud(JsonObjectUtils.getString(portrait_json.get("sl_id_ins_fraud")));
		list.add(speciallistCId);
		
		speciallistCCell.setCode(JsonObjectUtils.getString(portrait_json.get("code")));
		speciallistCCell.setType(StatusConstants.SPECIAL_LIST_C_TYPE_CELL);
		speciallistCCell.setSwift_number(JsonObjectUtils.getString(portrait_json.get("swift_number")));
		speciallistCCell.setBank_bad(JsonObjectUtils.getString(portrait_json.get("sl_cell_bank_bad")));
		speciallistCCell.setBank_fraud(JsonObjectUtils.getString(portrait_json.get("sl_cell_bank_fraud")));
		speciallistCCell.setBank_lost(JsonObjectUtils.getString(portrait_json.get("sl_cell_bank_lost")));
		speciallistCCell.setBank_overdue(JsonObjectUtils.getString(portrait_json.get("sl_cell_bank_overdue")));
		speciallistCCell.setBank_refuse(JsonObjectUtils.getString(portrait_json.get("sl_cell_bank_refuse")));
		speciallistCCell.setP2p_bad(JsonObjectUtils.getString(portrait_json.get("sl_cell_p2p_bad")));
		speciallistCCell.setP2p_fraud(JsonObjectUtils.getString(portrait_json.get("sl_cell_p2p_overdue")));
		speciallistCCell.setP2p_lost(JsonObjectUtils.getString(portrait_json.get("sl_cell_p2p_lost")));
		speciallistCCell.setP2p_overdue(JsonObjectUtils.getString(portrait_json.get("sl_cell_p2p_overdue")));
		speciallistCCell.setP2p_refuse(JsonObjectUtils.getString(portrait_json.get("sl_cell_p2p_refuse")));
		speciallistCCell.setCourt_bad(JsonObjectUtils.getString(portrait_json.get("sl_cell_court_bad")));
		speciallistCCell.setCourt_executed(JsonObjectUtils.getString(portrait_json.get("sl_cell_court_executed")));
		speciallistCCell.setCredit_bad(JsonObjectUtils.getString(portrait_json.get("sl_cell_p2p_bad")));
		speciallistCCell.setPhone_overdue(JsonObjectUtils.getString(portrait_json.get("sl_cell_phone_overdue")));
		speciallistCCell.setInsurance_fraud(JsonObjectUtils.getString(portrait_json.get("sl_cell_ins_fraud")));
		list.add(speciallistCCell);
		
		return list;
	}
	public static PortraitBean parsePortraitJSON(JSONObject portrait_json) {
		PortraitBean portrait = new PortraitBean();
		portrait.setCode(portrait_json.getString("code"));
		portrait.setSwift_number(portrait_json.getString("swift_number"));
		portrait.setAuthentication(portrait_json.getJSONObject("Authentication"));
		portrait.setInternet(portrait_json.getJSONObject("Internet"));
		portrait.setLocation(portrait_json.getJSONObject("Location"));
		portrait.setStability(portrait_json.getJSONObject("Stability"));
		portrait.setConsumption(portrait_json.getJSONObject("Consumption"));
		portrait.setApplyloan(portrait_json.getJSONObject("ApplyLoan"));
		portrait.setOnline(portrait_json.getJSONObject("Online"));
		portrait.setSpeciallist(portrait_json.getJSONObject("SpecialList"));
		portrait.setRuleresult(portrait_json.getJSONObject("RuleResult"));
		portrait.setScore(portrait_json.getJSONObject("Score"));
		portrait.setTitle(portrait_json.getJSONObject("Title"));
		portrait.setAssets(portrait_json.getJSONObject("Assets"));
		portrait.setMedia(portrait_json.getJSONObject("Media"));
		portrait.setBrand(portrait_json.getJSONObject("Brand"));
		portrait.setFlag(portrait_json.getJSONObject("Flag"));
		return portrait;
	}
	
	public static BrApplyLoan parseBrApplyLoanJSON(JSONObject portrait_json){
		BrApplyLoan applyLoan = new BrApplyLoan();
		
		applyLoan.setCode(JsonObjectUtils.getString(portrait_json.get("code")));
		applyLoan.setSwiftNumber(JsonObjectUtils.getString(portrait_json.get("swift_number")));
		applyLoan.setM12IdBankAllnum(JsonObjectUtils.getInteger(portrait_json.get("al_m12_id_bank_allnum")));
		applyLoan.setM12IdBankOrgnum(JsonObjectUtils.getInteger(portrait_json.get("al_m12_id_bank_orgnum")));
		applyLoan.setM12IdBankSelfnum(JsonObjectUtils.getInteger(portrait_json.get("al_m12_id_bank_selfnum")));
		applyLoan.setM12IdNotbankAllnum(JsonObjectUtils.getInteger(portrait_json.get("al_m12_id_notbank_allnum")));
		applyLoan.setM12IdNotbankOrgnum(JsonObjectUtils.getInteger(portrait_json.get("al_m12_id_notbank_orgnum")));
		applyLoan.setM12IdNotbankSelfnum(JsonObjectUtils.getInteger(portrait_json.get("al_m12_id_notbank_selfnum")));

		applyLoan.setM6IdBankAllnum(JsonObjectUtils.getInteger(portrait_json.get("al_m6_id_bank_allnum")));
		applyLoan.setM6IdBankOrgnum(JsonObjectUtils.getInteger(portrait_json.get("al_m6_id_bank_orgnum")));
		applyLoan.setM6IdBankSelfnum(JsonObjectUtils.getInteger(portrait_json.get("al_m6_id_bank_selfnum")));
		applyLoan.setM6IdNotbankAllnum(JsonObjectUtils.getInteger(portrait_json.get("al_m6_id_notbank_allnum")));
		applyLoan.setM6IdNotbankOrgnum(JsonObjectUtils.getInteger(portrait_json.get("al_m6_id_notbank_orgnum")));
		applyLoan.setM6IdNotbankSelfnum(JsonObjectUtils.getInteger(portrait_json.get("al_m6_id_notbank_selfnum")));
		
		applyLoan.setM3IdBankAllnum(JsonObjectUtils.getInteger(portrait_json.get("al_m3_id_bank_allnum")));
		applyLoan.setM3IdBankOrgnum(JsonObjectUtils.getInteger(portrait_json.get("al_m3_id_bank_orgnum")));
		applyLoan.setM3IdBankSelfnum(JsonObjectUtils.getInteger(portrait_json.get("al_m3_id_bank_selfnum")));
		applyLoan.setM3IdNotbankAllnum(JsonObjectUtils.getInteger(portrait_json.get("al_m3_id_notbank_allnum")));
		applyLoan.setM3IdNotbankOrgnum(JsonObjectUtils.getInteger(portrait_json.get("al_m3_id_notbank_orgnum")));
		applyLoan.setM3IdNotbankSelfnum(JsonObjectUtils.getInteger(portrait_json.get("al_m3_id_notbank_selfnum")));
		
		return applyLoan;
	}
	
	public static Object parseJSON(String meal, JSONObject portrait_json) {
		Object portrait = null;
		switch (meal) {
		case CommonConstants.BR_MEAL_SPECIALLIST_C:
			portrait = parseSpeciallyCListJSON(portrait_json);
			break;
		case CommonConstants.BR_MEAL_APPLYLOAN:
			portrait = parseBrApplyLoanJSON(portrait_json);
			break;
		default:
			portrait = parsePortraitJSON(portrait_json);
			break;
		}
		
		return portrait;
	}
	
	public static Map<String, Object> reqBR(UserBrRequest brRequest) throws Exception{
		MerchantBean merchantBean = new MerchantBean();
		merchantBean.setId(brRequest.getIdCard());
		merchantBean.setName(brRequest.getName()); 
        merchantBean.setCell(brRequest.getMobile());
        merchantBean.setHome_addr(brRequest.getHomeAddress());
        merchantBean.setBiz_addr(brRequest.getCompanyAddress());
        merchantBean.setMeal(brRequest.getProductName());
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		MerchantServer ms = BRUtils.getInstance();
		merchantBean.setTokenid(BRUtils.getTokenId());
		
		logger.info("发送至百融的查询内容："+JSONObject.fromObject(merchantBean).toString());
		String portrait_result = null;
		try {
			portrait_result = ms.getUserPortrait(merchantBean);
			logger.info("百融返回信息：" + portrait_result);
		} catch (Exception e) {
			logger.error("调用百融贷接口发生错误",e);
		}
		
		String code = null;
		try {
			code = JSONObject.fromObject(portrait_result).getString("code");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("转换百融json数据出错！" + portrait_result, e);
		}
		
		if("100007".equals(code)){
			logger.info("Tokenid过期,重新登录!过期Tokenid：" + BRUtils.getTokenId());
			try {
				BRUtils.login();
				merchantBean.setTokenid(BRUtils.getTokenId());
				portrait_result = ms.getUserPortrait(merchantBean);
				logger.info("百融返回信息：" + portrait_result);
			} catch (Exception e) {
				logger.error("调用百融贷接口发生错误",e);
			}
			
			try {
				code = JSONObject.fromObject(portrait_result).getString("code");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("转换百融json数据出错！" + portrait_result, e);
			}
			
			
		}
		
		retMap.put("code", code);
		retMap.put("portrait_result", portrait_result);
		
		return retMap;
	}
	
	public static boolean speciallistCreditResult(UserBrSpeciallistC e) throws Exception{  
		boolean flag = false;
		String ignore = new String("type,speciallistId,id,brReqId,code,swift_number,flag,createTime");
		
        Class cls = e.getClass();  
        Field[] fields = cls.getDeclaredFields();  
        for(int i=0; i<fields.length; i++){  
            Field f = fields[i];  
            f.setAccessible(true);  
            logger.info("属性名:" + f.getName() + " 属性值:" + f.get(e)); 
            if (EntityUtils.isEmpty(f.get(e)) && !Arrays.asList(ignore.split(",")).contains(f.getName())) {
            	flag =  false;
			}else if(null != f.get(e) && !"".equals(f.get(e)) && !Arrays.asList(ignore.split(",")).contains(f.getName())){
				flag =  true;
				break;
			}
        }   
        
        return flag;
    } 

}
