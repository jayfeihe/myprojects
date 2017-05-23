package com.greenkoo.dmp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greenkoo.dmp.model.AdxApp;
import com.greenkoo.dmp.model.AdxMobile;
import com.greenkoo.dmp.model.AdxPc;
import com.greenkoo.dmp.service.IAdxAppService;
import com.greenkoo.dmp.service.IAdxMobileService;
import com.greenkoo.dmp.service.IAdxPcService;
import com.greenkoo.dmp.sys.model.Pager;
import com.greenkoo.dmp.utils.FastJsonUtil;

@Controller
@RequestMapping("/dmp")
public class AdxController {

	@Autowired
	private IAdxPcService adxPcService;
	@Autowired
	private IAdxMobileService adxMobileService;
	@Autowired
	private IAdxAppService adxAppService;
	
	@GetMapping("/adxPc/list")
	public String pclist(Model model) {
		Pager<AdxPc> pager = adxPcService.pageList();
		
		Collection<AdxPc> datas = pager.getDatas();
		List<String> jsonDatas = new ArrayList<>();
		for (AdxPc adx : datas) {
			jsonDatas.add(FastJsonUtil.toJSONNoFeatures(adx));
		}
		
		model.addAttribute("pager", pager);
		model.addAttribute("jsonDatas", this.getJsonDatas(jsonDatas));
		return "dmp/adxPc";
	}
	
	@GetMapping("/adxMobile/list")
	public String mobilelist(Model model) {
		Pager<AdxMobile> pager = adxMobileService.pageList();
		
		Collection<AdxMobile> datas = pager.getDatas();
		List<String> jsonDatas = new ArrayList<>();
		for (AdxMobile adx : datas) {
			jsonDatas.add(FastJsonUtil.toJSONNoFeatures(adx));
		}
		
		model.addAttribute("pager", pager);
		model.addAttribute("jsonDatas", this.getJsonDatas(jsonDatas));
		return "dmp/adxMobile";
	}
	
	@GetMapping("/adxApp/list")
	public String applist(Model model) {
		Pager<AdxApp> pager = adxAppService.pageList();
		
		Collection<AdxApp> datas = pager.getDatas();
		List<String> jsonDatas = new ArrayList<>();
		for (AdxApp adx : datas) {
			jsonDatas.add(FastJsonUtil.toJSONNoFeatures(adx));
		}
		
		model.addAttribute("pager", pager);
		model.addAttribute("jsonDatas", this.getJsonDatas(jsonDatas));
		return "dmp/adxApp";
	}

	private String getJsonDatas(List<String> jsonDatas) {
		String json = FastJsonUtil.toJSONNoFeatures(jsonDatas);
		json = json.replace("device_id", "渠道cookie")
				.replace("os_version", "操作系统版本")
				.replace("browser_version", "浏览器版本")
				.replace("os", "操作系统")
				.replace("browser", "浏览器")
				.replace("brand", "手机品牌")
				.replace("model", "手机型号")
				.replace("isApp", "是否是App")
				.replace("device_type", "设备类型")
				.replace("qkCookie", "青稞cookie")
				.replace("citys", "常驻地区")
				.replace("operators", "运营商")
				.replace("appTypeTag", "应用标签")
				.replace("mediaTypeTag", "媒体偏好标签")
				.replace("firstLevel", "渠道一级类型")
				.replace("channelType", "渠道二级类型")
				.replace("value", "值")
				.replace("tagDate", "命中日期")
				.replace("interestTag", "兴趣标签");
		return json;
	}
}
