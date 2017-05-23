package com.tera.feature.search.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.search.model.AfterLoanSearchBean;

public interface IAfterLoanSearchService {
	 //稽查清单
		public int queryCount(Map<String, Object> map) throws Exception;
		
		public List<AfterLoanSearchBean> queryList(Map<String, Object> map) throws Exception;
		
		public List<AfterLoanSearchBean> queryTaskList(Map<String, Object> map)throws Exception;
		
		public List<AfterLoanSearchBean> queryRecordList(Map<String, Object> map)throws Exception;
		
		public PageList<AfterLoanSearchBean> queryPageList(Map<String, Object> params) throws Exception;
		
		public PageList<AfterLoanSearchBean> queryTaskPageList(Map<String, Object> params) throws Exception;
		
		public PageList<AfterLoanSearchBean> queryRecordPageList(Map<String, Object> params) throws Exception;

		public List<AfterLoanSearchBean> queryDetailList(Map<String, Object> map)throws Exception;
		
		public PageList<AfterLoanSearchBean> queryDetailPageList(Map<String, Object> params) throws Exception;

		public int queryLateConCount(Map<String, Object> map) throws Exception;
}
