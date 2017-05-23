package com.tera.sys.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

public abstract class MybatisBaseService<T> {

	@Autowired
	private SqlSessionFactoryBean sqlSessionFactory;

	/**
	 * 
	 * @param method
	 * @param params
	 * 				key
	 * 					curPage   当前页数
	 * 					pageSize  每页条数
	 * @return
	 */
	protected PageList<T> selectPageList(Class daoClass ,String method, Map<String, Object> params){
		PageList<T> list=null;
        SqlSession session = null;
        
      //分页对象
		Integer curPage=(Integer)params.get("curPage");
		Integer pageSize=(Integer)params.get("pageSize");
		PageBounds pageBounds = new PageBounds(curPage == null ? 1: curPage,pageSize== null ? Integer.MAX_VALUE :pageSize);
        
        try{
        	//这个对象是单例的
        	SqlSessionFactory sessionFactory = 	sqlSessionFactory.getObject();
        	//获取mybatis 的  SqlSession
			session = SqlSessionUtils.getSqlSession(sessionFactory);
			//执行方法
			System.out.println(daoClass.getName()+'.'+method);
            list = (PageList<T>) session.selectList(daoClass.getName()+'.'+method, params, pageBounds);
            
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            session.close();
        }
        return list;
    }

	
}
