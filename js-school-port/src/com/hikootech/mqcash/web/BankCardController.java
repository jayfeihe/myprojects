package com.hikootech.mqcash.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.dto.UserBankCardDTO;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.service.BankCardService;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.IpUtils;
import com.hikootech.mqcash.util.UserUtils;

@RequestMapping("/bankCard")
@Controller
public class BankCardController extends BaseController {
	
	private static Logger log = LoggerFactory.getLogger(BankCardController.class);
	
	@Autowired
	private BankCardService bankCardService;
	
	@RequestMapping("/bindBankCard.jhtml")
	@ResponseBody
	public Map<String, Object> bindBankCard(UserBankCardDTO bankCardDTO){
		log.info("秒趣 门户绑定银行卡号");
		
		UserInfo userInfo = getUserInfo();
		UserBankCardDTO cacheBankCardVO = UserUtils.getUserBankCardFromCache(getRequest().getSession());

		Map<String,Object> resultMap=new HashMap<String,Object>();
		try {
			bankCardDTO.setCustIp(IpUtils.getRemoteHost(getRequest()));
			resultMap=bankCardService.bindBankCard(bankCardDTO, cacheBankCardVO, userInfo);
			
			if(EntityUtils.isEmpty(resultMap.get("code"))){
				throw new RuntimeException("服务层调用结果未描述成功code，code："+resultMap.get("code"));
			}
		} catch (Exception e) {
			log.error("请求中金绑卡时发生异常",e);
			resultMap.put("code", ResponseConstants.FAIL);
			resultMap.put("desc", "系统错误！");
			return resultMap;
		}
		return resultMap;
	}
	
	@RequestMapping("/validateBindMobileNumber")
	@ResponseBody
	public Map<String, Object> validateBindMobileNumber(UserBankCardDTO bankCardVO){
		log.info("秒趣门户用户新卡申请验证绑定手机号");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		//获取用户在session中信息,关于session信息校验，放在filter中，拦截不是从电渠跳过来的非法请求
		UserInfo userInfo = getUserInfo();
		

		Map<String,Object> resultMap=new HashMap<String,Object>();
		log.info("校验开始。。。");
		try {
			bankCardVO.setCustIp(IpUtils.getRemoteHost(getRequest()));
			resultMap=bankCardService.validateBindMobileNumber(bankCardVO, userInfo);
		} catch (Exception e) {
			 log.error("请求中金发送短信发生异常",e);
			 retMap.put("code", ResponseConstants.FAIL);
			 retMap.put("desc", "发送验证码失败！");
			 return resultMap;
		}
		log.info("校验结束。。。");
		//修改失败，直接返回画面
		if(!resultMap.get(ResponseConstants.RETURN_CODE).toString().equals( ResponseConstants.SUCCESS)){
			 log.error("请求中金发送短信失败"+(resultMap.get(ResponseConstants.RETURN_DESC)==null?"":resultMap.get(ResponseConstants.RETURN_CODE).toString()));
			return resultMap;
		}
		
		//缓存以便提交分期时关联绑定银行卡--缓存请求帮顶流水号
		UserUtils.cacheUserBankCard(getRequest().getSession(), bankCardVO);
		
		retMap.put("code", ResponseConstants.SUCCESS);
		retMap.put("desc", "申请验证绑定手机号成功，短信已发送成功，请查收。");
		
		return retMap;
	}
	
	

}
