package com.tera.audit.loan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.loan.dao.BankBranchInfoDao;
import com.tera.audit.loan.model.BankBranchInfo;
import com.tera.audit.loan.service.IBankBranchInfoService;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.service.DataDictionaryService;

/**
 * 
 * 服务类
 * <b>功能：</b>BankBranchInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-29 06:49:49<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("bankBranchInfoService")
public class BankBranchInfoImpl implements IBankBranchInfoService {

	private final static Logger log = Logger.getLogger(BankBranchInfoImpl.class);

	@Autowired(required=false)
    private BankBranchInfoDao dao;
	
	@Autowired(required=false)
    private DataDictionaryService<DataDictionary> dataDictionaryService;
	@Transactional
	public void add(BankBranchInfo... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(BankBranchInfo obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(BankBranchInfo obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(BankBranchInfo obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<BankBranchInfo> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public BankBranchInfo queryByKey(Object id) throws Exception {
		return (BankBranchInfo)dao.queryByKey(id);
	}
	/**
	 * 判断是否有此分行，如果没有新添加
	 * @param city 城市名
	 * @param bankName 银行名称
	 * @param bankBranch分行名称
	 * @throws Exception
	 */
	public void verifyBranch(String city,String bankName,String bankBranch)  throws Exception {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("city", city);
		map.put("bankName",bankName );
		map.put("bankBranch", bankBranch);
		List<BankBranchInfo> listBranchs =dao.queryList(map);
		if (listBranchs==null||listBranchs.size()==0) {
			BankBranchInfo info=new BankBranchInfo();
			info.setBankName(bankName);
			info.setBranch(bankBranch);
			info.setCity(city);
			//新添加一条记录
			dao.add(info);
		}
	}
	
	/**
	 * 判断是否有此分行，没有添加
	 * @param bankName
	 * @throws Exception
	 */
	public void verifyBank(String bankName,HttpServletRequest request)  throws Exception {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("keyName", "bank");
		map.put("keyValue",bankName );
		List<DataDictionary> lists=dataDictionaryService.queryList(map);
		if (lists==null||lists.size()==0) {
			DataDictionary data=new DataDictionary();
			data.setKeyName("bank");
			data.setKeyProp(bankName);
			data.setKeyValue(bankName);
			data.setDescription("银行信息");
			dataDictionaryService.add(data);
			
			//更新共享数据
			Map<String, List<DataDictionary>> dictMap = (Map<String, List<DataDictionary>>) request
			.getSession().getServletContext().getAttribute("DATADICTS");
			map.remove("keyValue");
			lists=dataDictionaryService.queryList(map);
			dictMap.put("bank", lists);
			request.getSession().getServletContext().setAttribute("DATADICTS", dictMap);
			
			
		}
	}
	
}
