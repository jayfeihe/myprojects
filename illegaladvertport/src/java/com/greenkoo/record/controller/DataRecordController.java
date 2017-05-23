package com.greenkoo.record.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greenkoo.company.model.UserCompany;
import com.greenkoo.record.model.DataRecord;
import com.greenkoo.record.service.IDataRecordService;
import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.utils.ConfigUtil;

/**
 * 告警记录Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/dataRecord")
public class DataRecordController {

	private final String photoRootPath = ConfigUtil.getProperty("photo_root_path");
	private final String default_adpic = ConfigUtil.getProperty("default_adpic");
	
	@Autowired
	private IDataRecordService dataRecordService;
	
	/**
	 * 数据概况统计
	 * 
	 * @return
	 */
	@RequestMapping("/dataCount")
	public String dataCount() {
		
		return "advert/dataCount";
	}
	
	/**
	 * 最新违法广告创意
	 * 
	 * @return
	 */
	@RequestMapping("/latestIllegalList")
	public String latestIllegal(HttpServletRequest request, Model model) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		UserCompany loginCompany = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		// 广告主
		if (UserCompany.TYPE_ADVERTER == loginCompany.getType()) {
			paramMap.put("advertiserUrl", loginCompany.getCompanyUrl());
		}
		// 媒体
		if (UserCompany.TYPE_MEDIA == loginCompany.getType()) {
			paramMap.put("mediaUrl", loginCompany.getCompanyUrl());
		}
		
//		Pager pager = new Pager();
//		int totalCount = this.dataRecordService.queryCount(paramMap);
//		pager.init(totalCount);
//		paramMap.put("pageSize", pager.getPageSize());
//		paramMap.put("pageOffset", pager.getPageOffset());
		
		// 不分页显示100条
		paramMap.put("pageSize", 100);
		paramMap.put("pageOffset", 0);
		List<DataRecord> datas = this.dataRecordService.queryList(paramMap);
//		pager.setDatas(datas);
//		model.addAttribute("pager", pager);
		model.addAttribute("latestDatas", datas);
		return "advert/latestIllegalList";
	}
	
	/**
	 * 最新违法广告创意
	 * 
	 * @return
	 */
	@RequestMapping("/severeIllegalList")
	public String severeIllegal(HttpServletRequest request, Model model) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		UserCompany loginCompany = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		// 广告主
		if (UserCompany.TYPE_ADVERTER == loginCompany.getType()) {
			paramMap.put("advertiserUrl", loginCompany.getCompanyUrl());
		}
		// 媒体
		if (UserCompany.TYPE_MEDIA == loginCompany.getType()) {
			paramMap.put("mediaUrl", loginCompany.getCompanyUrl());
		}
		
		paramMap.put("level", DataRecord.LEVEL_SEVERE);
		
//		Pager pager = new Pager();
//		int totalCount = this.dataRecordService.queryCount(paramMap);
//		pager.init(totalCount);
//		paramMap.put("pageSize", pager.getPageSize());
//		paramMap.put("pageOffset", pager.getPageOffset());
		
		// 不分页显示100条
		paramMap.put("pageSize", 100);
		paramMap.put("pageOffset", 0);
		List<DataRecord> datas = this.dataRecordService.queryList(paramMap);
//		pager.setDatas(datas);
//		model.addAttribute("pager", pager);
		model.addAttribute("severeDatas", datas);
		return "advert/severeIllegalList";
	}
	
	/**
	 * 读取图片
	 * 
	 * @param imgUrl
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/imgRead")
	public void imgRead(String imgType, String adpicUrl, HttpServletResponse response) throws Exception {
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("adpicUrl", adpicUrl);
		List<DataRecord> records = this.dataRecordService.queryList(queryMap);
		if(records!=null && records.size()>0){
			if ("2".equals(imgType)) {
				// 获取缩略图路径
				adpicUrl = adpicUrl.replace(".jpg", "_thumb.jpg");
			}
			OutputStream out=response.getOutputStream();
			File file = new File(photoRootPath + File.separator + adpicUrl);
			if (file.exists()) {
				out.write(FileUtils.readFileToByteArray(file));
			} else {
				out.write(FileUtils.readFileToByteArray(new File(photoRootPath + File.separator + default_adpic + ".jpg")));
			}
			out.flush();
			out.close();		
		}
	}
}
