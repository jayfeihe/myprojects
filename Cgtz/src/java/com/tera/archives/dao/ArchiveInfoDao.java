package com.tera.archives.dao;

import java.util.List;
import java.util.Map;

import com.tera.archives.model.ArchiveInfo;

public interface ArchiveInfoDao {

	public void add(ArchiveInfo info);
	
	public void update(ArchiveInfo info);
	
	public void updateOnlyChanged(ArchiveInfo info);
	
	public void delete(Object id);
	
	public ArchiveInfo queryByKey(Object id);
	
	public ArchiveInfo queryByContractId(Object contId);
	
	public List<ArchiveInfo> queryList(Map<String, Object> map);
}
