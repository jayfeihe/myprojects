package com.greenkoo.dmp.dao.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.greenkoo.dmp.sys.model.Pager;

@Repository
public class BaseDaoImpl<T> implements IBaseDao<T> {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<T> findAll(Class<T> clz) {
		return mongoTemplate.findAll(clz);
	}

	@Override
	public Pager<T> pageList(Class<T> clz) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		query.addCriteria(criteria);
		// 查询总条数
		long totalCount = mongoTemplate.count(query, clz);
		Pager<T> pager = new Pager<>();
		pager.init(totalCount);
		
		query.skip(pager.getPageOffset());
		query.limit(pager.getPageSize());
		
		List<T> find = mongoTemplate.find(query, clz);
		
		pager.setDatas(find);
		
		return pager;
	}
}
