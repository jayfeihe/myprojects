package com.greenkoo.company.controller;

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

import com.greenkoo.company.model.UserCompany;
import com.greenkoo.company.model.form.UserCompanyVo;
import com.greenkoo.company.service.IUserCompanyService;
import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.sys.model.Pager;
import com.greenkoo.sys.model.SysIndustry;
import com.greenkoo.sys.model.SysJsonMsg;
import com.greenkoo.sys.service.ISysIndustryService;
import com.greenkoo.utils.ConfigUtil;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.GenerateKeyUtil;

/**
 * 广告主、媒体管理
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/company")
public class UserCompanyController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserCompanyService userCompanyService;
	@Autowired
	private ISysIndustryService sysIndustryService;
	
	@RequestMapping(value = "/advertiser/query", method = RequestMethod.GET)
	public String advertiserQuery(Model model) {
		// 获取行业信息
		List<SysIndustry> industries = this.sysIndustryService.queryList();
		model.addAttribute("industries", industries);
		return "company/advertiser/companyAdvertiserQuery";
	}
	
	/**
	 * 广告主管理列表
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
		int totalCount = this.userCompanyService.queryCount(paramMap);
		pager.init(totalCount);
		paramMap.put("pageSize", pager.getPageSize());
		paramMap.put("pageOffset", pager.getPageOffset());
		List<UserCompanyVo> datas = this.userCompanyService.queryList(paramMap);
		pager.setDatas(datas);
		model.addAttribute("pager", pager);
		
		return "company/advertiser/companyAdvertiserList";
	}
	
	@RequestMapping(value = "/media/query", method = RequestMethod.GET)
	public String mediaQuery(Model model) {
		// 获取行业信息
		List<SysIndustry> industries = this.sysIndustryService.queryList();
		model.addAttribute("industries", industries);
		return "company/media/companyMediaQuery";
	}
	
	/**
	 * 媒体管理列表
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
		int totalCount = this.userCompanyService.queryCount(paramMap);
		pager.init(totalCount);
		paramMap.put("pageSize", pager.getPageSize());
		paramMap.put("pageOffset", pager.getPageOffset());
		List<UserCompanyVo> datas = this.userCompanyService.queryList(paramMap);
		pager.setDatas(datas);
		model.addAttribute("pager", pager);
		
		return "company/media/companyMediaList";
	}
	
	/**
	 * 添加
	 * 
	 * @param company
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public SysJsonMsg add(UserCompany company, HttpServletRequest request) {
		try {
			if (company != null) {
				
				company.setCompanyId(GenerateKeyUtil.getId(CommonConstants.PREFIX_USER_COMPANY, ConfigUtil.getProperty("db_id")));
				company.setCreateTime(DateUtil.getCurDate());
				company.setStatus(CommonConstants.STATUS_ON);
				
				this.userCompanyService.add(company);
				request.setAttribute(CommonConstants.OPERATION_RECORD_FLAG, CommonConstants.SUCCESS_FLAG);
				request.setAttribute(CommonConstants.OPERATION_RECORD_DESC, "添加广告主或媒体管理成功");
				request.setAttribute(CommonConstants.OPERATION_RECORD_TYPE, 10);
				
				return new SysJsonMsg(true, "添加成功");
			} else {
				return new SysJsonMsg(false, "添加失败");
			}
		} catch (Exception e) {
			logger.error("添加广告主或媒体发生错误:" + e.getMessage(), e);
			return new SysJsonMsg(false, "添加失败");
		}
	}
	
	/**
	 * 更新
	 * 
	 * @param company
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public SysJsonMsg update(UserCompany company, HttpServletRequest request) {
		try {
			int updateCount = this.userCompanyService.update(company);
			if (updateCount > 0) {
				// 保存操作日志
				request.setAttribute(CommonConstants.OPERATION_RECORD_FLAG, CommonConstants.SUCCESS_FLAG);
				request.setAttribute(CommonConstants.OPERATION_RECORD_DESC, "修改广告主或媒体管理成功");
				request.setAttribute(CommonConstants.OPERATION_RECORD_TYPE, 30);
				return new SysJsonMsg(true, "修改成功");
			} else {
				return new SysJsonMsg(false, "修改失败");
			}
		} catch (Exception e) {
			logger.error("修改广告主或媒体管理发生错误，companyId:" + company.getCompanyId() + "," + e.getMessage(), e);
			return new SysJsonMsg(false, "修改失败");
		}
	}
	
	/**
	 * 状态更新
	 * 
	 * @param company
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public SysJsonMsg updateStatus(UserCompany company, HttpServletRequest request) {
		try {
			if (company != null) {
				if (CommonConstants.STATUS_OFF == company.getStatus()) company.setStatus(CommonConstants.STATUS_ON);
				else if (CommonConstants.STATUS_ON == company.getStatus()) company.setStatus(CommonConstants.STATUS_OFF);
				this.userCompanyService.updateStatus(company);
				return new SysJsonMsg(true, "修改状态成功");
			} else {
				return new SysJsonMsg(false, "修改状态失败");
			}
		} catch (Exception e) {
			logger.error("修改状态发生错误:" + e.getMessage(), e);
			return new SysJsonMsg(false, "修改状态失败");
		}
	}
	
	/**
	 * 根据主键查找信息，用于更新时显示信息
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/queryByCompanyId", method = RequestMethod.GET)
	@ResponseBody
	public UserCompany queryByCompanyId(String companyId) {
		UserCompany userCompany = this.userCompanyService.queryByCompanyId(companyId);
		return userCompany;
	}
	
	/**
	 * 根据url查询信息，用于url重复校验
	 * 
	 * @param companyUrl
	 * @return
	 */
	@RequestMapping(value = "/queryByCompanyUrl", method = RequestMethod.GET)
	@ResponseBody
	public SysJsonMsg queryByCompanyUrl(String companyUrl,String type) {
		UserCompany userCompany = this.userCompanyService.queryByCompanyUrlAndType(companyUrl,type);
		if (userCompany != null) {
			return new SysJsonMsg(false, "URL地址已存在");
		}
		return new SysJsonMsg(true, "URL地址可用");
	}
}
