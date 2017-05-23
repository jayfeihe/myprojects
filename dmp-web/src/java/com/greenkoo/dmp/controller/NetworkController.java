package com.greenkoo.dmp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greenkoo.dmp.model.NetworkApp;
import com.greenkoo.dmp.model.NetworkPc;
import com.greenkoo.dmp.service.INetworkAppService;
import com.greenkoo.dmp.service.INetworkPcService;
import com.greenkoo.dmp.sys.model.Pager;
import com.greenkoo.dmp.utils.FastJsonUtil;

@Controller
@RequestMapping("/dmp")
public class NetworkController {

	@Autowired
	private INetworkPcService networkPcService;
	@Autowired
	private INetworkAppService networkAppService;
	
	@GetMapping("/networkPc/list")
	public String pclist(Model model) {
		Pager<NetworkPc> pager = networkPcService.pageList();
		
		Collection<NetworkPc> datas = pager.getDatas();
		List<String> jsonDatas = new ArrayList<>();
		for (NetworkPc network : datas) {
			jsonDatas.add(FastJsonUtil.toJSONNoFeatures(network));
		}
		
		model.addAttribute("pager", pager);
		model.addAttribute("jsonDatas", this.getJsonDatas(jsonDatas));
		return "dmp/networkPc";
	}
	
	@GetMapping("/networkApp/list")
	public String applist(Model model) {
		Pager<NetworkApp> pager = networkAppService.pageList();
		
		Collection<NetworkApp> datas = pager.getDatas();
		List<String> jsonDatas = new ArrayList<>();
		for (NetworkApp network : datas) {
			jsonDatas.add(FastJsonUtil.toJSONNoFeatures(network));
		}
		
		model.addAttribute("pager", pager);
		model.addAttribute("jsonDatas", this.getJsonDatas(jsonDatas));
		return "dmp/networkApp";
	}

	private String getJsonDatas(List<String> jsonDatas) {
		String json = FastJsonUtil.toJSONNoFeatures(jsonDatas);
		json = json.replace("device_id", "设备id")
				.replace("os_version", "操作系统版本")
				.replace("browser_version", "浏览器版本")
				.replace("os", "操作系统")
				.replace("browser", "浏览器")
				.replace("brand", "手机品牌")
				.replace("model", "手机型号")
				.replace("network", "运营商")
				.replace("citys", "常驻地区")
				.replace("interestTag", "兴趣标签")
				.replace("value", "值")
				.replace("tagDate", "命中日期")
				.replace("accountTag", "账号标签")
				.replace("mobileAccount", "手机账号")
				.replace("qqAccount", "qq账号")
				.replace("emailAccount", "邮箱账号")
				.replace("taobaoAccount", "淘宝账号")
				.replace("baiduAccount", "百度账号")
				.replace("jdAccount", "京东账号")
				.replace("weiboAccount", "微博账号")
				.replace("times", "命中次数")
				.replace("sourceTag", "标签来源")
				.replace("keywords", "关键字")
				.replace("otherCookie", "其他cookie")
				.replace("apptags", "所有应用")
				.replace("appid", "应用标签")
				.replace("appname", "应用名")
				.replace("twolevel", "二级编号")
				.replace("threelevel", "三级编号")
				.replace("7days", "7天次数")
				.replace("15days", "15天次数")
				.replace("30days", "30天次数")
				.replace("60days", "60天次数")
				.replace("90days", "90天次数");
		return json;
	}
}
