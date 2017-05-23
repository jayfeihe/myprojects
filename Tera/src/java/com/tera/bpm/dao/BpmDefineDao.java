/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.bpm.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tera.bpm.model.BpmDefine;

/**
 * @author Administrator
 */
@Repository
public interface BpmDefineDao {

	/**
	 * 添加流程定义
	 * @param bpmDefine BpmDefine
	 */
	void addBpmDefine(BpmDefine bpmDefine);

	/**
	 * 根据id获取流程定义信息
	 * @param id id
	 * @return BpmDefine
	 */
	BpmDefine getBpmDefineById(int id);

	/**
	 * 根据processName获取流程定义信息
	 * @param processName processName
	 * @return BpmDefine
	 */
	BpmDefine getBpmDefineByName(String processName);


	/**
	 * 添加流程定义
	 * @param bpmDefine BpmDefine
	 */
	void updateBpmDefine(BpmDefine bpmDefine);

	/**
	 * 删除流程定义
	 * @param map ID列表
	 */
	void deleteBpmDefineByIds(Map<String, Object> map);

	/**
	 * 删除流程定义
	 * @param processName processName
	 */
	void deleteBpmDefineByName(String processName);

}
