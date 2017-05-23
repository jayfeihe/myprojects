package com.greenkoo.account.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenkoo.account.model.UserAccount;
import com.greenkoo.account.model.form.UserAccountVo;
import com.greenkoo.account.service.IUserAccountService;
import com.greenkoo.company.model.UserCompany;
import com.greenkoo.company.model.form.UserCompanyVo;
import com.greenkoo.company.service.IUserCompanyService;
import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.sys.model.Pager;
import com.greenkoo.sys.model.SysJsonMsg;
import com.greenkoo.utils.ConfigUtil;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.GenerateKeyUtil;
import com.greenkoo.utils.SecurityUtil;

/**
 * 用户账户Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/account")
public class UserAccountController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserAccountService userAccountService;
	@Autowired
	private IUserCompanyService userCompanyService;
	
	
	@RequestMapping(value = "/advertiser/query", method = RequestMethod.GET)
	public String advertiserQuery(Model model) {
		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("type", UserCompany.TYPE_ADVERTER);
		paramMap.put("status", CommonConstants.STATUS_ON);
		// 查询所有可用的广告主
		List<UserCompanyVo> advertisers = this.userCompanyService.queryList(paramMap );
		model.addAttribute("advertisers", advertisers);
		
		return "account/advertiser/accountAdvertiserQuery";
	}
	
	/**
	 * 广告主账号管理列表
	 * 
	 * @param companyName
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/advertiser/list", method = RequestMethod.GET)
	public String advertiserList(String companyName, String status,Model model) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 条件-广告主类型
		paramMap.put("type", UserCompany.TYPE_ADVERTER);
		// 条件-广告主名称
		paramMap.put("companyName", companyName);
		// 条件-状态
		paramMap.put("status", status);
		
		Pager pager = new Pager();
		int totalCount = this.userAccountService.queryCount(paramMap);
		pager.init(totalCount);
		paramMap.put("pageSize", pager.getPageSize());
		paramMap.put("pageOffset", pager.getPageOffset());
		List<UserAccountVo> datas = this.userAccountService.queryList(paramMap);
		pager.setDatas(datas);
		model.addAttribute("pager", pager);
		
		return "account/advertiser/accountAdvertiserList";
	}
	
	@RequestMapping(value = "/media/query", method = RequestMethod.GET)
	public String mediaQuery(Model model) {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("type", UserCompany.TYPE_MEDIA);
		paramMap.put("status", CommonConstants.STATUS_ON);
		// 查询所有可用的媒体
		List<UserCompanyVo> medias = this.userCompanyService.queryList(paramMap );
		model.addAttribute("medias", medias);
		return "account/media/accountMediaQuery";
	}
	
	/**
	 * 媒体账号管理列表
	 * 
	 * @param companyName
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/media/list", method = RequestMethod.GET)
	public String mediaList(String companyName, String status,Model model) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 条件-媒体类型
		paramMap.put("type", UserCompany.TYPE_MEDIA);
		// 条件-媒体名称
		paramMap.put("companyName", companyName);
		// 条件-状态
		paramMap.put("status", status);
		
		Pager pager = new Pager();
		int totalCount = this.userAccountService.queryCount(paramMap);
		pager.init(totalCount);
		paramMap.put("pageSize", pager.getPageSize());
		paramMap.put("pageOffset", pager.getPageOffset());
		List<UserAccountVo> datas = this.userAccountService.queryList(paramMap);
		pager.setDatas(datas);
		model.addAttribute("pager", pager);
		
		return "account/media/accountMediaList";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public SysJsonMsg add(UserAccountVo accountVo, HttpServletRequest request) {
		try {
			if (accountVo != null) {
				UserAccount userAccount = accountVo.getUserAccount();
				
				if (userAccount != null) {
					userAccount.setId(GenerateKeyUtil.getId(CommonConstants.PREFIX_USER_ACCOUNT, ConfigUtil.getProperty("db_id")));
					userAccount.setPwd(SecurityUtil.md5Str(userAccount.getPwd()));
					userAccount.setCreateTime(DateUtil.getCurDate());
					
					this.userAccountService.add(userAccount);
					
					// 保存操作记录日志
					request.setAttribute(CommonConstants.OPERATION_RECORD_FLAG, CommonConstants.SUCCESS_FLAG);
					request.setAttribute(CommonConstants.OPERATION_RECORD_DESC, "添加广告主或媒体账号管理成功");
					request.setAttribute(CommonConstants.OPERATION_RECORD_TYPE, 10);

					return new SysJsonMsg(true, "添加成功");
				} else {
					return new SysJsonMsg(false, "添加失败");
				}
			} else {
				return new SysJsonMsg(false, "添加失败");
			}
		} catch (Exception e) {
			logger.error("添加广告主或媒体账号发生错误:" + e.getMessage(), e);
			return new SysJsonMsg(false, "添加失败");
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public SysJsonMsg update(UserAccountVo accountVo, HttpServletRequest request) {
		try {
			
			if (accountVo != null) {
				UserAccount userAccount = accountVo.getUserAccount();
				if (userAccount != null) {
					userAccount.setPwd(SecurityUtil.md5Str(userAccount.getPwd()));
					int updateCount = this.userAccountService.update(userAccount);
					if (updateCount > 0) {
						// 保存操作日志
						request.setAttribute(CommonConstants.OPERATION_RECORD_FLAG, CommonConstants.SUCCESS_FLAG);
						request.setAttribute(CommonConstants.OPERATION_RECORD_DESC, "修改广告主或媒体账号管理成功");
						request.setAttribute(CommonConstants.OPERATION_RECORD_TYPE, 30);
						return new SysJsonMsg(true, "修改成功");
					} else {
						return new SysJsonMsg(false, "修改失败");
					}
					
				}  else {
					return new SysJsonMsg(false, "修改失败");
				}
			} else {
				return new SysJsonMsg(false, "修改失败");
			}
		} catch (Exception e) {
			logger.error("修改广告主或媒体账号发生错误，accountId:" + accountVo.getUserAccount().getId() + "," + e.getMessage(), e);
			return new SysJsonMsg(false, "修改失败");
		}
	}
	
	/**
	 * 根据主键查找信息，用于更新时显示信息
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	@ResponseBody
	public UserAccount queryById(String id) {
		UserAccount userAccount = this.userAccountService.queryById(id);
		return userAccount;
	}
	
	/**
	 * 根据账号查询信息，用于账号重复校验
	 * 
	 * @param companyUrl
	 * @return
	 */
	@RequestMapping(value = "/queryByAccount", method = RequestMethod.GET)
	@ResponseBody
	public SysJsonMsg queryByAccount(String account) {
		UserAccount userAccount = this.userAccountService.queryByAccount(account);
		if (userAccount != null) {
			return new SysJsonMsg(false, "账号已存在");
		}
		return new SysJsonMsg(true, "账号可用");
	}
}
