/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.bpm.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.bpm.model.BpmDefine;

/**
 * @author Administrator
 */
@Service
public class BpmDefineService {
	
	/**
	 * defineDaoMapper
	 */
	@Autowired(required=false)
	BpmDefineService bpmDefineDao;
	
	/**
	 * 添加流程定义
	 * @param bpmDefine BpmDefine
	 */
	public void addBpmDefine(BpmDefine bpmDefine) {
		bpmDefineDao.addBpmDefine(bpmDefine);
	}

	/**
	 * 根据id获取流程定义信息
	 * @param id id
	 * @return BpmDefine
	 */
	public BpmDefine getBpmDefineById(int id) {
		return bpmDefineDao.getBpmDefineById(id);
	}

	/**
	 * 根据processName获取流程定义信息
	 * @param processName processName
	 * @return BpmDefine
	 */
	public BpmDefine getBpmDefineByName(String processName) {
		return bpmDefineDao.getBpmDefineByName(processName);
	}


	/**
	 * 添加流程定义
	 * @param bpmDefine BpmDefine
	 */
	public void updateBpmDefine(BpmDefine bpmDefine) {
		bpmDefineDao.updateBpmDefine(bpmDefine);
	}

	/**
	 * 删除流程定义
	 * @param map ID列表
	 */
	public void deleteBpmDefineByIds(Map<String, Object> map) {
		bpmDefineDao.deleteBpmDefineByIds(map);
	}

	/**
	 * 删除流程定义
	 * @param processName processName
	 */
	public void deleteBpmDefineByName(String processName) {
		bpmDefineDao.deleteBpmDefineByName(processName);
	}


}
