package com.hikootech.mqcash.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.po.GenArea;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.service.ConfigKvService;
import com.hikootech.mqcash.service.GenAreaService;
import com.hikootech.mqcash.service.InstalmentInfoService;
import com.hikootech.mqcash.service.UpdateUserInfoService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.UUIDTools;
import com.hikootech.mqcash.util.UserUtils;
import com.hikootech.mqcash.util.ValidateTools;
/**
 * 用于用户信息修改类(由于存在有可能前台修改旧手机号的风险，在后台校验原手机号时需要从缓存中获取)
 * */
@RequestMapping("/updateUserInfo")
@Controller
public class UpdateUserInfoController extends BaseController {
	
	private static Logger log = LoggerFactory.getLogger(UpdateUserInfoController.class);
 
	@Autowired
	private InstalmentInfoService instalmentInfoService;
	@Autowired
	private GenAreaService genAreaService;
 
	@Autowired
	private UpdateUserInfoService updateUserInfoService;
	@Autowired
	private ConfigKvService configKvService;
	
	/**
	 *  进入用户信息画面
	 * 	进入修改信息画面之前，首先要在filter过滤其中判断其权限且登陆状态是否有效，如无效不会进入该方法
	 * */
	@RequestMapping("/viewInfo.jhtml")
	public String viewInfo(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("查看用户信息画面");
		UserInfo userInfo=getUserInfo();
		
		String mobileNumber =userInfo!=null?userInfo.getBindMobile():"";
		
		//前台画面修改加密中间四位
		map.put("mobileNumber", mobileNumber.substring(0, 3)+"****"+mobileNumber.substring(7,11));
		map.put("mobileNumberShow", mobileNumber);
		map.put("identificationDate", DateUtil.formatDate(userInfo.getCreateTime(),"yyyy-MM-dd"));
		map.put("idCard", userInfo.getIdCard().substring(0, 6)+"****"+userInfo.getIdCard().substring(14,18));
		map.put("userInfo", userInfo);
		
		//获取画面上方日期标签
		map.put("topView", instalmentInfoService.getCurTopView(userInfo.getUserId()));
		map.put("logoMsg", CommonConstants.LOGO_MSG[3]);
		return "updateUserInfo/user_account";
	}
	
	/********************************************************更改基本信息start *******************************************************/
	/** 
	* modifyInfo<br/> 
	*  TODO(进入个人信息修改画面) 
	* @param request
	* @param map
	* @return
	* @throws Exception 
	* @author zhaohefei
	* @2015年12月11日
	* @return String	返回类型 
	*/
	@RequestMapping("/modifyInfo.jhtml")
	public String modifyInfo(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("修改用户信息画面");
		UserInfo userInfo=getUserInfo();
		
		//初始化地址信息中的省级列表
		List<GenArea> listPro=genAreaService.getAreaList(CommonConstants.AREA_PARENT_CODE);
		
		//初始化家庭地址信息中的市、区级列表
		List<GenArea> listCity=genAreaService.getAreaList(userInfo.getProvinceId());
		List<GenArea> listArea=genAreaService.getAreaList(userInfo.getCityId());
		
		//初始化公司地址信息中的市、区级列表
		List<GenArea> listCityCom=genAreaService.getAreaList(userInfo.getCompanyProvinceId());
		List<GenArea> listAreaCom=genAreaService.getAreaList(userInfo.getCompanyCityId());
		
		
		map.put("listPro", listPro);
		map.put("listCity", listCity);
		map.put("listArea", listArea);
		map.put("listCityCom", listCityCom);
		map.put("listAreaCom", listAreaCom);
		
		map.put("logoMsg", CommonConstants.LOGO_MSG[3]);
		map.put("userInfo", userInfo);
		return "updateUserInfo/user_modify_info";
	}
	
	/** 
	* getAreaInfo<br/> 
	*  TODO(根据前台传递的省级代码返回市级列表，根据市级代码返回地区列表) 
	* @param request
	* @return 
	* @author zhaohefei
	* @2015年12月11日
	* @return Map<String,Object>	返回类型 
	*/
	@RequestMapping("/getAreaInfo.jhtml")
	@ResponseBody
	public Map<String,Object>  getAreaInfo(HttpServletRequest request){
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		//从前台获取省级代码、市级代码
		String paramProCode=request.getParameter("paramProCode");
		String paramCityCode=request.getParameter("paramCityCode");
		List<GenArea> listCityInfo=null;
		List<GenArea> listAreaInfo=null;
		
		//省级联动
		if(paramProCode!=null&&!"".equals(paramProCode)){
			  listCityInfo=genAreaService.getAreaList(paramProCode);
			  listAreaInfo=genAreaService.getAreaList(listCityInfo.get(0).getAreaNo());
		}
		//市级联动
		else{
			listAreaInfo=genAreaService.getAreaList(paramCityCode);
		}
		
		retMap.put("listCityInfo", listCityInfo);
		retMap.put("listAreaInfo", listAreaInfo);
		return retMap;
	}
	
	@RequestMapping("/modifyInfoSub.jhtml")
	@ResponseBody
	public Map<String, Object> modifyInfoSub(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("提交修改用户信息");
		String redirectUrl ="updateUserInfo/viewInfo.jhtml";
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		//从缓存中获取缓存信息
		UserInfo userInfoSession=getUserInfo();
		
		//复制缓存信息
		UserInfo userInfo= (UserInfo) userInfoSession.clone();
		
		//获取前台传值
		userInfo.setCompanyName(request.getParameter("comName"));
		userInfo.setInteresting(request.getParameter("habby").replace(" ", ""));
		userInfo.setProvinceId(request.getParameter("proId"));
		userInfo.setCityId(request.getParameter("cityId"));
		userInfo.setAreaId(request.getParameter("areaId"));
		userInfo.setHomeAddress(request.getParameter("homeAddr"));
		userInfo.setCompanyProvinceId(request.getParameter("comProId"));
		userInfo.setCompanyCityId(request.getParameter("comCityId"));
		userInfo.setCompanyAreaId(request.getParameter("comAreaId"));
		userInfo.setCompanyAddress(request.getParameter("comAddr"));
		userInfo.setHeadImgUrl("");
		userInfo.setUpdateTime(DateUtil.getCurDate());
		
		//根据前台传值  修改个人信息
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			resultMap = updateUserInfoService.modifyInfoSub(userInfoSession, userInfo);
		} catch (Exception e) {
			log.error("提交修改用户信息发生错误，",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "修改用户信息失败！");
			return retMap;
		}
		
		//修改失败，直接返回画面
		if(resultMap.get(ResponseConstants.RETURN_CODE).toString().equals( ResponseConstants.FAIL)){
			return resultMap;
		}
		//若修改成功，将有效信息返回画面，并且缓存新的用户信息
		UserUtils.cacheUserInfo(request.getSession(), userInfo);
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "修改用户信息成功！");
		retMap.put("redirectUrl", redirectUrl);
		return retMap;
	}
	
	/********************************************************更改基本信息end *******************************************************/
	
	 /*****************************************************更改密码start*****************************************************************/
	
	/**
	 *  进入更改密码画面
	 * 	进入修改信息画面之前，首先要在filter过滤其中判断其权限且登陆状态是否有效，如无效不会进入该方法
	 * */
	@RequestMapping("/updatePwd.jhtml")
	public String updatePwd(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("进入更改密码画面");
		UserInfo userInfo=getUserInfo();
		
		map.put("userInfo", userInfo);
		map.put("logoMsg", CommonConstants.LOGO_MSG[3]);
		String mobileNumber =userInfo!=null?userInfo.getBindMobile():"";
		
		//前台画面修改加密中间四位
		map.put("mobileNumberShow", mobileNumber.substring(0, 3)+"****"+mobileNumber.substring(7,11));
		map.put("mobileNumberHide", mobileNumber);
		return "updateUserInfo/user_modify_psw";
	}
	
	/**
	 *  进入更改密码成功画面
	 * 	进入修改信息画面之前，首先要在filter过滤其中判断其权限且登陆状态是否有效，如无效不会进入该方法
	 * */
	@RequestMapping("/updatePwdSuccess.jhtml")
	public String updatePwdSuccess(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("进入更改密码成功画面");
		UserInfo userInfo=getUserInfo();
		map.put("userInfo", userInfo);
		map.put("logoMsg", CommonConstants.LOGO_MSG[3]);
		String mobileNumber =userInfo!=null?userInfo.getBindMobile():"";
		
		//前台画面修改加密中间四位
		map.put("mobileNumberShow", mobileNumber.substring(0, 3)+"****"+mobileNumber.substring(7,11));
		map.put("mobileNumberHide", mobileNumber);
		return "updateUserInfo/user_modify_psw_suc";
	}
	
	@RequestMapping("/updateInitPwdSuccess.jhtml")
	public String updateInitPwdSuccess(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("进入更改密码成功画面");
		UserInfo userInfo=getUserInfo();
		map.put("userInfo", userInfo);
		map.put("logoMsg", CommonConstants.LOGO_MSG[3]);
		String mobileNumber =userInfo!=null?userInfo.getBindMobile():"";
		
		//前台画面修改加密中间四位
		map.put("mobileNumberShow", mobileNumber.substring(0, 3)+"****"+mobileNumber.substring(7,11));
		map.put("mobileNumberHide", mobileNumber);
		return "updateUserInfo/user_modify_psw_init_suc";
	}
	/**
	 *   手机方式修改密码
	 * 	进入修改信息画面之前，首先要在filter过滤其中判断其权限且登陆状态是否有效，如无效不会进入该方法
	 * */
	@RequestMapping("/updatePwdByMobile.jhtml")
	@ResponseBody
	public Map<String, Object>  updatePwdByMobile(HttpServletRequest request){
		log.info("通过 手机方式修改密码");
		String redirectUrl ="updateUserInfo/updatePwdSuccess.jhtml";
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		//获取前台传值
		String validationCode = getRequest().getParameter("validationCode");
		String smCode = getRequest().getParameter("smCode");
		String newPwd=getRequest().getParameter("newPwd");
		String newPwd2=getRequest().getParameter("newPwd2");
		
		//进行简单校验
		if(smCode==null||validationCode==null){
			log.error("通过 手机方式修改密码，获取验证码信息有误");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码错误！");
			return retMap;
		}
		
		if(newPwd==null||"".equals(newPwd)){
			log.error("通过 手机方式修改密码，新密码不能为空");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "新密码不能为空！");
			return retMap;
		}
		
		if(!newPwd.equals(newPwd2)){
			log.error("通过 手机方式修改密码，两次新密码输入的不一致");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "两次新密码输入不一致");
			return retMap;
		}
		
		//校验手机短信与图片验证码是否正确
		try {
			this.validateValid(getUserInfo().getBindMobile(), smCode, validationCode,"psw");
		} catch (Exception e) {
			log.error("通过 手机方式修改密码，",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, e.getMessage());
			return retMap;
		} 
		
		//进行业务校验，成功后修改入库
		Map<String, Object> resultMap;
		try {
			resultMap = updateUserInfoService.updatePwdByMobile( newPwd, getUserInfo());
		} catch (Exception e) {
			log.error("通过手机修改密码发生错误，",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "修改用户密码失败！");
			return retMap;
		}
		
		//修改失败，直接返回画面
		if(resultMap.get(ResponseConstants.RETURN_CODE).toString().equals( ResponseConstants.FAIL)){
			return resultMap;
		}
		
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "修改密码成功！");
		retMap.put("redirectUrl", redirectUrl);
		return retMap;
	}
	
	
	/**
	 *   通过原密码修改新密码
	 * 	进入修改信息画面之前，首先要在filter过滤其中判断其权限且登陆状态是否有效，如无效不会进入该方法
	 * */
	@RequestMapping("/updatePwdByPwd.jhtml")
	@ResponseBody
	public Map<String, Object>  updatePwdByPwd() throws Exception {
		log.info("通过 密码验证方式修改密码");
		String redirectUrl ="updateUserInfo/updatePwdSuccess.jhtml";
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		//获取前台传值
		String mobileNumber = getRequest().getParameter("mobileNumber");
		String validationCode = getRequest().getParameter("validationCode");
		String newPwd=getRequest().getParameter("newPwd");
		String newPwd2=getRequest().getParameter("newPwd2");
		String oldPwd=getRequest().getParameter("oldPwd");
		
		UserInfo userInfo =getUserInfo();
		
		//进行简单校验
		if(validationCode==null){
			log.error("通过 密码验证方式修改密码，获取验证码信息有误");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码错误！");
			return retMap;
		}
		
		if(newPwd==null||"".equals(newPwd)){
			log.error("通过密码验证方式修改密码，新密码不能为空");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入新密码！");
			return retMap;
		}
		if(!newPwd.equals(newPwd2)){
			log.error("通过 手机方式修改密码，两次新密码输入的不一致");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "两次密码需保持一致！");
			return retMap;
		}
		

		//校验图片验证码
		try {
			validateValid(mobileNumber, null, validationCode,"psw");
		} catch (Exception e) {
			log.error("通过密码验证方式修改密码，",e);
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, e.getMessage());
			return retMap;
		} 
		
		//进行业务逻辑判断，成功后修改入库
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			resultMap = updateUserInfoService.updatePwdByPwd(oldPwd, newPwd2, userInfo);
		} catch (Exception e) {
			log.error("通过原密码修改密码发生错误，",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "修改用户密码失败！");
			return retMap;
		}
		
		//修改失败，直接返回画面
		if(resultMap.get(ResponseConstants.RETURN_CODE).toString().equals( ResponseConstants.FAIL)){
			return resultMap;
		}
				
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "修改密码成功！");
		retMap.put("redirectUrl", redirectUrl);
		return retMap;
	}
	

	 /*****************************************************更改密码 end*****************************************************************/
	
	 /*****************************************************更改原始密码start*****************************************************************/
	
		/**
		 *  用户首次进入门户必须修改原始密码
		 *  进入更改初始密码画面
		 * 	进入修改信息画面之前，首先要在filter过滤其中判断其权限且登陆状态是否有效，如无效不会进入该方法
		 * */
		@RequestMapping("/updateInitPwd.jhtml")
		public String updateInitPwd(HttpServletRequest request, ModelMap map) throws Exception {
			log.info("进入更改初始密码画面");
			UserInfo userInfo=getUserInfo();
			
			map.put("userInfo", userInfo);
			map.put("logoMsg", CommonConstants.LOGO_MSG[3]);
			
			//前台画面修改加密中间四位
			return "updateUserInfo/user_modify_psw_init";
		}
		
		
		/**
		 *   通过原始密码修改新密码
		 * 	进入修改信息画面之前，首先要在filter过滤其中判断其权限且登陆状态是否有效，如无效不会进入该方法
		 * */
		@RequestMapping("/updateInitPwdByPwd.jhtml")
		@ResponseBody
		public Map<String, Object>  updateInitPwdByPwd() throws Exception {
			log.info("通过 密码验证方式修改密码");
			String redirectUrl ="updateUserInfo/updateInitPwdSuccess.jhtml";
			Map<String, Object> retMap = new HashMap<String, Object>();
			
			//获取前台传值
			String validationCode = getRequest().getParameter("validationCode");
			String newPwd=getRequest().getParameter("newPwd");
			String newPwd2=getRequest().getParameter("newPwd2");
			String oldPwd=getRequest().getParameter("oldPwd");
			
			UserInfo userInfo =getUserInfo();
			
			//进行简单校验
			if(validationCode==null){
				log.error("通过 密码验证方式修改密码，获取验证码信息有误");
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "动态验证码错误！");
				return retMap;
			}
			
			if(newPwd==null||"".equals(newPwd)){
				log.error("通过密码验证方式修改密码，新密码不能为空");
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "请输入新密码！");
				return retMap;
			}
			if(!newPwd.equals(newPwd2)){
				log.error("通过 密码验证方式修改密码，两次新密码输入的不一致");
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "两次密码需保持一致！");
				return retMap;
			}
			

			//校验图片验证码
			try {
				validateValid(null, null, validationCode,"psw");
			} catch (Exception e) {
				log.error("通过密码验证方式修改密码，",e);
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, e.getMessage());
				return retMap;
			} 
			
			//进行业务逻辑判断，成功后修改入库
			Map<String, Object> resultMap=new HashMap<String, Object>();
			try {
				resultMap = updateUserInfoService.updateInitPwdByPwd(oldPwd, newPwd2, userInfo);
			} catch (Exception e) {
				log.error("修改初始密码发生错误，",e);
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "修改用户密码失败！");
				return retMap;
			}
			
			//修改失败，直接返回画面
			if(resultMap.get(ResponseConstants.RETURN_CODE).toString().equals( ResponseConstants.FAIL)){
				return resultMap;
			}
			userInfo.setPwdModSts(1);
			UserUtils.cacheUserInfo(getRequest().getSession(), userInfo);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, "初始密码修改成功！");
			retMap.put("redirectUrl", redirectUrl);
			return retMap;
		}
		
	/*****************************************************更改原始密码end*****************************************************************/

	/*****************************************************更改绑定手机start*****************************************************************/
	
	/**
	 * 该方法为修改绑定手机画面获取必要信息及生成验证码
	 * 	进入修改信息画面之前，首先要在filter过滤其中判断其权限且登陆状态是否有效，如无效不会进入该方法
	 * */
	@RequestMapping("/updateMobileStepOne.jhtml")
	public String updateMobileStepOne(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("进入修改信息画面 step1验证身份");
		UserInfo userInfo=getUserInfo();
		
		map.put("userInfo", userInfo);
		map.put("logoMsg", CommonConstants.LOGO_MSG[3]);
		String mobileNumber = userInfo.getBindMobile();
		
		//前台画面修改加密中间四位
		map.put("mobileNumberShow", mobileNumber.substring(0, 3)+"****"+mobileNumber.substring(7,11));
		map.put("mobileNumberHide", mobileNumber);
			
		return "/updateUserInfo/user_mobile_first";
	}
	

	/**
	 * 更改绑定手机 step1 验证身份
	 * 
	 */
	@RequestMapping("/updateMobileStepOneSub.jhtml")
	@ResponseBody
	public Map<String, Object> updateMobileStepOneSub(){
		log.info("更改绑定手机 step1 验证解绑手机");
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		String mobileNumber = getUserInfo().getBindMobile();
		
		//获取前台传值
		String validationCode = getRequest().getParameter("validationCode");
		String smCode = getRequest().getParameter("smCode");
		
		String redirectUrl ="updateUserInfo/updateMobileStepTwo.jhtml";
		
		//简单校验
		if(smCode==null||validationCode==null){
			log.error("更换绑定手机step1，获取验证码信息有误");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码错误！");
			return retMap;
		} 
		//手机短信与图片验证码校验
		try {
			validateValid(mobileNumber, smCode, validationCode,"step1");
		} catch (Exception e) {
			log.error("更换绑定手机step1，",e);
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, e.getMessage());
			return retMap;
		} 
		 
		//校验无误后进行跳转下一步
		String tokenId=UUIDTools.getFormatUUID();
		
		//缓存修改手机号唯一key
		UserUtils.cacheMobileSmsCodeUpdateOne(getRequest().getSession(), tokenId);
		
		retMap.put("tokenId", tokenId);
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "验证要解绑的手机成功。");
		retMap.put("redirectUrl", redirectUrl);
		return retMap;
	}
	
	/**
	 * 更改绑定手机 step2 验证新绑定手机
	 * 
	 * 此方法需注意判断step1是否成功完成，需防止直接网址跳转 
	 **
	 */
	@RequestMapping("/updateMobileStepTwo.jhtml")
	public String updateMobileStepTwo(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("进入修改信息画面 step2验证新手机");
			//获取session中当前用户的手机号
		String tokenId=request.getParameter("tokenId");
		UserInfo userInfo=getUserInfo();
		map.put("userInfo", userInfo);
		map.put("logoMsg", CommonConstants.LOGO_MSG[3]);
		String mobileNumber = userInfo.getBindMobile();
		map.put("mobileNumberShow", mobileNumber.substring(0, 3)+"****"+mobileNumber.substring(7,11));
		map.put("mobileNumberHide", mobileNumber);
		String mobileUpdateSmsCodeOld = UserUtils.getMobileSmsCodeForUpdateFromCacheOne(getRequest().getSession());
		if(EntityUtils.isEmpty(tokenId)||!mobileUpdateSmsCodeOld.equals(tokenId)){
			log.error("进入修改信息画面 step2,step1完成标识未被正确解析!");
			return "/updateUserInfo/user_mobile_first";
		}
		map.put("tokenId", tokenId);
		return "/updateUserInfo/user_mobile_second";
	}

	
	/**
	 * 更改绑定手机 step3 绑定成功
	 * 
	 * 此方法需注意判断step2是否成功完成，需防止直接网址跳转 
	 **
	 */
	@RequestMapping("/updateMobileStepThree.jhtml")
	public String updateMobileStepThree(HttpServletRequest request, ModelMap map) throws Exception {
		log.info("进入修改信息画面 step3 重新绑定成功");
		
		//获取session中当前用户的手机号
		UserInfo userInfo=getUserInfo();
		String mobileNumber = userInfo.getBindMobile();
		
		map.put("userInfo", userInfo);
		map.put("logoMsg", CommonConstants.LOGO_MSG[3]);
		
		map.put("mobileNumberShow", mobileNumber.substring(0, 3)+"****"+mobileNumber.substring(7,11));
		map.put("mobileNumberHide", mobileNumber);
 
		return "/updateUserInfo/user_mobile_third";
	}

	
	/**
	 * 更改绑定手机 step2 验证新绑定手机提交
	 */
	@RequestMapping("/updateMobileStepTwoSub.jhtml")
	@ResponseBody
	public Map<String, Object> updateMobileStepTwoSub(){
		
		log.info("更改绑定手机 step2验证新绑定手机");
			
		String redirectUrl ="updateUserInfo/updateMobileStepThree.jhtml";
			
		Map<String, Object> retMap = new HashMap<String, Object>();
			
		String mobileUpdateSmsCodeOld = UserUtils.getMobileSmsCodeForUpdateFromCacheOne(getRequest().getSession());
		
		//再次验证第一步是否完成,若验证通过清除缓存第一步完成的信息
		String tokenId=getRequest().getParameter("tokenId");
		if(EntityUtils.isEmpty(tokenId)||!mobileUpdateSmsCodeOld.equals(tokenId)){
			log.error("进入修改信息画面 step2,step1完成标识未被正确解析!");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请先完成第一步验证身份！");
			return retMap;
		}
		UserUtils.cacheMobileSmsCodeUpdateOne(getRequest().getSession(), "");
		
		
		UserInfo userInfo=getUserInfo();
		String mobileNumberNew = getRequest().getParameter("mobileNumberNew").trim();
		String smCode = getRequest().getParameter("smCode");

		if(smCode==null){
			log.error("更换绑定手机step2 ，获取手机验证码信息有误");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码错误！");
			return retMap;
		} 
			
		try {
			validateValid(mobileNumberNew, smCode, null,"step2");
		} catch (Exception e) {
			log.error("更换绑定手机step2，",e);
			retMap.put("mobileNumber", mobileNumberNew);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, e.getMessage());
			return retMap;
		} 
		
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			resultMap = updateUserInfoService.updateUserBingMobile(smCode, mobileNumberNew, userInfo);
		} catch (Exception e) {
			log.error("更改绑定手机 step2发生错误，",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "修改用户密码失败！");
			return retMap;
		}
		
		//修改失败，直接返回画面
		if(resultMap.get(ResponseConstants.RETURN_CODE).toString().equals( ResponseConstants.FAIL)){
			return resultMap;
		}
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "新绑定手机成功。");
		retMap.put("redirectUrl", redirectUrl);
		return retMap;
	}

	 /*****************************************************更改绑定手机end*****************************************************************/
	
	/********发送验证码前，校验验证码是否正确！********/
	@RequestMapping("validateCode.jhtml")
	@ResponseBody
	public Map<String, Object> validateCode(){
		log.debug("发送验证码前，校验验证码是否正确！");
		Map<String, Object> retMap=new HashMap<String, Object>();
	
		try {
			validateValid(null, null, getRequest().getParameter("validationCode"), null);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, "图形验证码校验成功。");
		} catch (Exception e) {
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, e.getMessage());
		}
		
		return retMap;
	}
	
	/**
	 * 校验修改信息时的手机验证码与图片验证码
	 * @param mobileNumber 手机号 用于验证手机验证码，不验证手机验证码时，可置为空
	 * @param smCode  手机验证码：    如果不需要验证手机验证码则将其值为空，需要验证时 请务必保证该值不为空
	 *  @param validationCode 图片验证码：如果不需要验证图片验证码则将其值为空，需要验证时 请务必保证该值不为空
	 * 
	 * */
	public void validateValid(String mobileNumber,String smCode,String validationCode ,String type ){
		//从缓存中获取验证码、手机登录密码以及它们的过期时间
		String upValiCodeCache= UserUtils.getMobileUpdateValidationCodeFromCache(getRequest().getSession());
		Date upValiCodeStartTimeCache = UserUtils.getMobileUpdateValidationCodeEffectiveTimeFromCache(getRequest().getSession());
		String smCodeCache="";
		Date smCodeStartTimeCache=null;
		
		if("step1".equals(type)){
			smCodeCache = UserUtils.getMobileSmsCodeForUpdateFromCacheOne(getRequest().getSession());
			smCodeStartTimeCache = UserUtils.getMobileSmsCodeForUpdateEffectiveTimeFromCacheOne(getRequest().getSession());
		}else if("step2".equals(type)){
			smCodeCache = UserUtils.getMobileSmsCodeForUpdateFromCacheTwo(getRequest().getSession());
			smCodeStartTimeCache = UserUtils.getMobileSmsCodeForUpdateEffectiveTimeFromCacheTwo(getRequest().getSession());
		}else{
			smCodeCache = UserUtils.getMobileSmsCodeForUpdateFromPsw(getRequest().getSession());
			smCodeStartTimeCache = UserUtils.getMobileSmsCodeForUpdateEffectiveTimeFromCachePsw(getRequest().getSession());
		}
		 //将获取值进行逻辑判断
		updateUserInfoService.validateValid(smCodeCache, smCodeStartTimeCache, upValiCodeCache, upValiCodeStartTimeCache, smCode, mobileNumber, validationCode);
	}
	
	
	@RequestMapping("/generateValidationCodeForUpdate.jhtml")
	@ResponseBody
	public void generateValidationCodeForUpdate(HttpServletResponse response){
		// 设置不缓存图片
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// 指定生成的相应图片
		response.setContentType("image/jpeg");
		
		String type = getRequest().getParameter("type");
		if(EntityUtils.isEmpty(type)){
			type = "loginBySM";
		}
		List<Object> listTemp=constructValidationCode();
		 BufferedImage image = (BufferedImage) listTemp.get(1);
		String validationCode = (String) listTemp.get(0);
		log.debug("validationCode" + validationCode);
		//保存验证码到session中
		HttpSession session = getRequest().getSession();
		 if("updateMobile".equals(type)){
			UserUtils.cacheMobileUpdateValidationCode(session, validationCode);
			UserUtils.cacheMobileUpdateValidationCodeEffectiveTime(session, new Date());
		}else if("updatePwd".equals(type)){
			//修改密码复用修改绑定手机方法
			UserUtils.cacheMobileUpdateValidationCode(session, validationCode);
			UserUtils.cacheMobileUpdateValidationCodeEffectiveTime(session, new Date());
		}else{
			UserUtils.cacheMobileLoginValidationCode(session, validationCode);
			UserUtils.cacheMobileLoginValidationCodeEffectiveTime(session, new Date());
		}
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送手机验证码，并将手机号与验证码存入缓存中(修改绑定手机step1)
	 * 
	 * */
	@RequestMapping("/generateSmsCodeUpdateStep1.jhtml")
	@ResponseBody
	public Map<String, Object> generateSmsCodeUpdateMob(HttpServletResponse response){
		log.info("发送修改手机1用的动态短信密码");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String mobileNumber = getRequest().getParameter("mobileNumber").trim();
		
		//从缓存中获取短信验证码、它们的过期时间
		String smCodeCache = "";
		Date smCodeStartTimeCache = null;
		String moblileAndCodeCache=UserUtils.getMobileSmsCodeForUpdateFromCacheOne(getRequest().getSession());
		smCodeCache = EntityUtils.isEmpty(moblileAndCodeCache)||!moblileAndCodeCache.contains(",,,") ?"":moblileAndCodeCache.split(",,,")[1];
		smCodeStartTimeCache = UserUtils.getMobileSmsCodeForUpdateEffectiveTimeFromCacheOne(getRequest().getSession());
		
		//从缓存中获取手机号，校验手机号是否为发送短信的手机号
		String mobileNumCache = (EntityUtils.isEmpty(moblileAndCodeCache)||!moblileAndCodeCache.contains(",,,")?"":moblileAndCodeCache.split(",,,")[0]);
		
		//获取参数信息
		String templateId = ConfigUtils.getProperty("update_bindMobile1_template_id");
		String customerServicePhoneNumber =configKvService.get(CommonConstants.CUSTOMER_SERVICE_TEL);
		int smCodeEffectiveTime = 1;
		try {
			smCodeEffectiveTime = Integer.parseInt(ConfigUtils.getProperty("mqcash_mobile_login_pwd_effective_time"))/60;
		} catch (NumberFormatException e) {
			
			log.error("获取手机短信验证码有效时间出错:", e);
		}
		int interval = 1;
		try {
			interval = Integer.parseInt(ConfigUtils.getProperty("mqcash_sm_send_interval_time"))/60;
		} catch (NumberFormatException e1) {
			
			log.error("获取配置中的短信发送间隔失败",e1);
		}
		
		//手机号规则校验：1开头、11位数字
		if(!ValidateTools.validateMobileNumber(mobileNumber)){
			log.error("手机号不正确。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "手机号码错误！");
			return retMap;
		}
	 
		
		//调用云通讯发送信息
		Map<String, Object> resultMap=new HashMap<String,Object>();
		try {
			resultMap = updateUserInfoService.sendSmsCodeForUpdate(smCodeCache, mobileNumCache, smCodeStartTimeCache,smCodeEffectiveTime, interval,mobileNumber, templateId, customerServicePhoneNumber);
		} catch (Exception e) {
			log.error("发送修改手机1用的动态短信密码，发生错误，",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码发送失败！");
			return retMap;
		}
		
		//修改失败，直接返回画面
		if(resultMap.get(ResponseConstants.RETURN_CODE).toString().equals( ResponseConstants.FAIL)){
			return resultMap;
		}
	 
		smCodeCache=(String) resultMap.get("smCode");
		//缓存手机号短信验证码、验证码发送时间
		UserUtils.cacheMobileSmsCodeUpdateOne(getRequest().getSession(), mobileNumber+",,,"+smCodeCache);
		UserUtils.cacheMobileSmsCodeForUpdateEffectiveTimeOne(getRequest().getSession(), new Date());
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "动态验证码发送成功。");
		
		return retMap;
	}
	
	/**
	 * 发送手机验证码，并将手机号与验证码存入缓存中(修改绑定手机step2)
	 * 
	 * */
	@RequestMapping("/generateSmsCodeUpdateStep2.jhtml")
	@ResponseBody
	public Map<String, Object> generateSmsCodeUpdateMobTwo(HttpServletResponse response){
		log.info("generateSmsCodeUpdateStep2。。。。");
		log.info("发送修改手机2用的动态短信密码");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String mobileNumber = getRequest().getParameter("mobileNumber");
		
		//手机号规则校验：1开头、11位数字
		if(!ValidateTools.validateMobileNumber(mobileNumber)){
			log.error("手机号不正确。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "手机号码错误!");
			return retMap;
		}
		//从缓存中获取短信验证码、它们的过期时间
		String smCodeCache = "";
		Date smCodeStartTimeCache = null;
		String moblileAndCodeCache=UserUtils.getMobileSmsCodeForUpdateFromCacheTwo(getRequest().getSession());
		smCodeCache =(moblileAndCodeCache==null||"".equals(moblileAndCodeCache)?"":moblileAndCodeCache.split(",,,")[1]);
		smCodeStartTimeCache = UserUtils.getMobileSmsCodeForUpdateEffectiveTimeFromCacheTwo(getRequest().getSession());
		
		//从缓存中获取手机号，校验手机号是否为发送短信的手机号
		String _mobileNumber = (moblileAndCodeCache==null?"":moblileAndCodeCache.split(",,,")[0]);
		
		//获取参数配置
		String templateId = ConfigUtils.getProperty("update_bindMobile2_template_id");
		String customerServicePhoneNumber =configKvService.get(CommonConstants.CUSTOMER_SERVICE_TEL);
		int smCodeEffectiveTime = 1;
		try {
			smCodeEffectiveTime = Integer.parseInt(ConfigUtils.getProperty("mqcash_mobile_login_pwd_effective_time"))/60;
		} catch (NumberFormatException e) {
			
			log.error("获取手机短信验证码有效时间出错:", e);
		}
		
		int interval = 1;
		try {
			interval = Integer.parseInt(ConfigUtils.getProperty("mqcash_sm_send_interval_time"))/60;
		} catch (NumberFormatException e1) {
			
			log.error("获取配置中的短信发送间隔失败",e1);
		}
		
		
		
		//调用云通讯发送信息
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			resultMap = updateUserInfoService.sendSmsCodeForUpdate(smCodeCache, _mobileNumber, smCodeStartTimeCache,smCodeEffectiveTime, interval,mobileNumber, templateId, customerServicePhoneNumber);
		} catch (Exception e) {
			log.error("发送修改手机2用的动态短信密码，发生错误，",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码发送失败！");
			return retMap;
		}
		
		//修改失败，直接返回画面
		if(resultMap.get(ResponseConstants.RETURN_CODE).toString().equals( ResponseConstants.FAIL)){
			return resultMap;
		}
	 
		//缓存手机号短信验证码、验证码发送时间
		smCodeCache=(String) resultMap.get("smCode");
		UserUtils.cacheMobileSmsCodeUpdateTwo(getRequest().getSession(), mobileNumber+",,,"+smCodeCache);
		UserUtils.cacheMobileSmsCodeForUpdateEffectiveTimeTwo(getRequest().getSession(), new Date());
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "动态验证码发送成功。");
		
		return retMap;
	}
	
	/**
	 * 发送手机验证码，并将手机号与验证码存入缓存中(修改密码)
	 * 
	 * */
	@RequestMapping("/generateSmsCodeUpdatePsw.jhtml")
	@ResponseBody
	public Map<String, Object> generateSmsCodeUpdatePsw(HttpServletResponse response){
		log.info("generateSmsCodeUpdatePsw。。。。");
		log.info("发送修改密码用的动态短信密码");
		Map<String, Object> retMap = new HashMap<String, Object>();
		String mobileNumber = getRequest().getParameter("mobileNumber");
		
		//手机号规则校验：1开头、11位数字
		if(!ValidateTools.validateMobileNumber(mobileNumber)){
			log.error("手机号不正确。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "手机号码错误！");
			return retMap;
		}
		
		//从缓存中获取短信验证码、它们的过期时间
		String smCodeCache = "";
		Date smCodeStartTimeCache = null;
		String moblileAndCode=UserUtils.getMobileSmsCodeForUpdateFromPsw(getRequest().getSession());
		smCodeCache =(moblileAndCode==null||"".equals(moblileAndCode)?"":moblileAndCode.split(",,,")[1]);
		smCodeStartTimeCache = UserUtils.getMobileSmsCodeForUpdateEffectiveTimeFromCachePsw(getRequest().getSession());
		
		//从缓存中获取手机号，校验手机号是否为发送短信的手机号
		String _mobileNumber = (moblileAndCode==null?"":moblileAndCode.split(",,,")[0]);
		
		//获取参数配置
		String templateId = ConfigUtils.getProperty("update_pwd_template_id");
		String customerServicePhoneNumber =configKvService.get(CommonConstants.CUSTOMER_SERVICE_TEL);
		int smCodeEffectiveTime = 1;
		try {
			smCodeEffectiveTime = Integer.parseInt(ConfigUtils.getProperty("mqcash_mobile_login_pwd_effective_time"))/60;
		} catch (NumberFormatException e) {
			
			log.error("获取手机短信验证码有效时间出错:", e);
		}
		
		int interval = 1;
		try {
			interval = Integer.parseInt(ConfigUtils.getProperty("mqcash_sm_send_interval_time"))/60;
		} catch (NumberFormatException e1) {
			
			e1.printStackTrace();
		}
		
		
		
		//调用云通讯发送信息
		Map<String, Object> resultMap=new HashMap<String,Object>();
		try {
			resultMap = updateUserInfoService.sendSmsCodeForUpdate(smCodeCache, _mobileNumber, smCodeStartTimeCache,smCodeEffectiveTime, interval,mobileNumber, templateId, customerServicePhoneNumber);
		} catch (Exception e) {
			log.error("发送修改密码用的动态短信密码，发生错误，",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码发送失败！");
			return retMap;
		}
		
		//修改失败，直接返回画面
		if(resultMap.get(ResponseConstants.RETURN_CODE).toString().equals( ResponseConstants.FAIL)){
			return resultMap;
		}
	 
		//缓存手机号短信验证码、验证码发送时间
		smCodeCache=(String) resultMap.get("smCode");
		UserUtils.cacheMobileSmsCodeUpdatePsw(getRequest().getSession(), mobileNumber+",,,"+smCodeCache);
		UserUtils.cacheMobileSmsCodeForUpdateEffectiveTimePsw(getRequest().getSession(), new Date());
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "动态验证码发送成功。");
		
		return retMap;
	}

	 public static void main(String[] args) {
		
		String mobileNumber="13439188524";
		System.out.println(mobileNumber.substring(0, 3)+"****"+mobileNumber.substring(7,11));
	}
}
