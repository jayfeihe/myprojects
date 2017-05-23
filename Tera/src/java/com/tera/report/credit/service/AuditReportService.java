package com.tera.report.credit.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.credit.dao.AuditReportDao;
import com.tera.report.credit.model.AuditReportBean;
import com.tera.sys.service.MybatisBaseService;

/**信审统计报表Service
 * @author QYANZE
 *
 */
@Service("auditReportService")
public class AuditReportService extends MybatisBaseService<AuditReportBean>{

	@Autowired(required=false)
	private AuditReportDao dao;
	
	/**
	 * 列表查询
	 * @param map
	 * @return
	 */
	public List<AuditReportBean> queryList(Map<String, Object> map) {
		return dao.queryList(map);
	}
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public PageList<AuditReportBean> queryPageList(Map<String, Object> params) {
		return this.selectPageList(AuditReportDao.class, "queryList", params);
	}
	
	/**
	 * 根据decision表查询信审相关处理量
	 * @param map
	 * @return
	 */
	public Integer queryAmountWithDecision(Map<String, Object> map) {
		return dao.queryAmountWithDecision(map);
	}
	
	/**
	 * 获取信审echarts报表数据
	 * @param queryMap
	 * @return
	 */
	public AuditReportBean getAuditEchartData(Map<String, Object> queryMap) {
		int totalAmount = this.queryAmountWithDecision(queryMap); // 总量
		
		queryMap.put("decisions", new String[]{"01","02"}); // 通过
		int passAmount = this.queryAmountWithDecision(queryMap); //通过量
		
		queryMap.put("decisions", new String[]{"03","05"}); // 拒贷
		int denyAmount = this.queryAmountWithDecision(queryMap); // 拒贷量
		
		int otherAmount = totalAmount - passAmount - denyAmount; // 其他量
		
		AuditReportBean bean = new AuditReportBean(totalAmount, passAmount, denyAmount, otherAmount);
		
		return bean;
	}
}
