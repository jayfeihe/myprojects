package com.tera.archives.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.archives.model.ArchiveInfo;

public interface IArchiveInfoService {

	void add(ArchiveInfo... objs) throws Exception;

	void update(ArchiveInfo obj) throws Exception;

	void updateOnlyChanged(ArchiveInfo obj) throws Exception;

	void delete(Object... ids) throws Exception;

	/**
	 * 根据id查找信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ArchiveInfo queryByKey(Object id) throws Exception;

	/**
	 * 根据合同编号查找信息
	 * 
	 * @param contId
	 * @return
	 * @throws Exception
	 */
	ArchiveInfo queryByContractId(Object contId) throws Exception;
	
	/**
	 * 列表查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<ArchiveInfo> queryList(Map<String, Object> map) throws Exception;

	/**
	 * 分页列表查询
	 * 
	 * @param params
	 * @return
	 */
	PageList<ArchiveInfo> queryPageList(Map<String, Object> params);
}