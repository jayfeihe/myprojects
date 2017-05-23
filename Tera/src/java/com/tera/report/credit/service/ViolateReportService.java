package com.tera.report.credit.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.credit.dao.ViolateReportDao;
import com.tera.report.credit.model.ViolateReportBean;
import com.tera.sys.service.MybatisBaseService;
import com.tera.util.MathUtils;

/**
 * 违约统计报表Service
 * 
 * @author QYANZE
 *
 */
@Service("violateReportService")
public class ViolateReportService extends MybatisBaseService<ViolateReportBean> {

	@Autowired(required = false)
	private ViolateReportDao dao;

	/**
	 * 查询各个营业部的违约金额
	 * @param map(orgId)
	 * @return
	 */
	public ViolateReportBean queryAmount(Map<String,Object> map) {
		return dao.queryAmount(map);
	}
	
	/**
	 * 列表查询
	 * 
	 * @param map
	 * @return
	 */
	public List<ViolateReportBean> queryList(Map<String, Object> map) {
		return dao.queryList(map);
	}

	/**
	 * 分页查询
	 * 
	 * @param params
	 * @return
	 */
	public PageList<ViolateReportBean> queryPageList(Map<String, Object> params) {
		return this.selectPageList(ViolateReportDao.class, "queryList", params);
	}

	/**
	 * 获取违约echarts报表数据
	 * 
	 * @param queryMap
	 * @return
	 */
	public ViolateReportBean getViolateEchartData(Map<String, Object> queryMap) {
		ViolateReportBean bean = this.queryAmount(queryMap);
		if (bean != null) {
			// 转为万元单位
			if (bean.getRepayAmount() != 0d)
				bean.setRepayAmount(MathUtils.div(bean.getRepayAmount(), 10000d, 2));
			if (bean.getTotalAmount() != 0d)
				bean.setTotalAmount(MathUtils.div(bean.getTotalAmount(), 10000d, 2));
			if (bean.getViolateAmount() != 0d)
				bean.setViolateAmount(MathUtils.div(bean.getViolateAmount(), 10000d, 2));
		}
		return bean;
	}
}
