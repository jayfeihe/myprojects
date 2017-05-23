package com.greenkoo.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.greenkoo.sys.model.Pager;
import com.greenkoo.sys.model.SysOperationRecord;
import com.greenkoo.sys.service.ISysOperationRecordService;

/**
 * 操作日志Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/sys/operationRecord")
public class SysOperationRecordController {

	@Autowired
	private ISysOperationRecordService sysOperationRecordService;
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String recordQuery() {
		return "sys/operationRecord/recordQuery";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String recordList(String createTimeMin,String createTimeMax,Model model) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("createTimeMin", createTimeMin);
		paramMap.put("createTimeMax", createTimeMax);
		
		Pager pager = new Pager();
		int totalCount = this.sysOperationRecordService.queryCount(paramMap);
		pager.init(totalCount);
		paramMap.put("pageSize", pager.getPageSize());
		paramMap.put("pageOffset", pager.getPageOffset());
		List<SysOperationRecord> datas = this.sysOperationRecordService.queryList(paramMap);
		pager.setDatas(datas);
		model.addAttribute("pager", pager);
		
		return "sys/operationRecord/recordList";
	}
}
