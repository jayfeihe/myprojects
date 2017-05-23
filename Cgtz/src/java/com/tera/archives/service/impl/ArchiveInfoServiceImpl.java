package com.tera.archives.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.archives.dao.ArchiveInfoDao;
import com.tera.archives.model.ArchiveInfo;
import com.tera.archives.service.IArchiveInfoService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 档案管理Service
 * 
 * @author QYANZE
 *
 */
@Service("archiveInfoService")
public class ArchiveInfoServiceImpl extends MybatisBaseService<ArchiveInfo> implements IArchiveInfoService {

	@Autowired
	private ArchiveInfoDao dao;
	
	/* (non-Javadoc)
	 * @see com.tera.archives.service.IArchiveInfoService#add(com.tera.archives.model.ArchiveInfo)
	 */
	@Override
	@Transactional
	public void add(ArchiveInfo... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(ArchiveInfo obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.archives.service.IArchiveInfoService#update(com.tera.archives.model.ArchiveInfo)
	 */
	@Override
	@Transactional
	public void update(ArchiveInfo obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.archives.service.IArchiveInfoService#updateOnlyChanged(com.tera.archives.model.ArchiveInfo)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(ArchiveInfo obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.archives.service.IArchiveInfoService#delete(java.lang.Object)
	 */
	@Override
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.archives.service.IArchiveInfoService#queryByKey(java.lang.Object)
	 */
	@Override
	public ArchiveInfo queryByKey(Object id) throws Exception {
		return dao.queryByKey(id);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.archives.service.IArchiveInfoService#queryByContractId(java.lang.Object)
	 */
	@Override
	public ArchiveInfo queryByContractId(Object contId) throws Exception {
		return dao.queryByContractId(contId);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.archives.service.IArchiveInfoService#queryList(java.util.Map)
	 */
	@Override
	public List<ArchiveInfo> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.archives.service.IArchiveInfoService#queryPageList(java.util.Map)
	 */
	@Override
	public PageList<ArchiveInfo> queryPageList(Map<String , Object> params) {
		return this.selectPageList(ArchiveInfoDao.class, "queryList", params);
	}
}
