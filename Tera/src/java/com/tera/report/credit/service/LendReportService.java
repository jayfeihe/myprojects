package com.tera.report.credit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.report.credit.dao.LendReportDao;
import com.tera.report.credit.model.LendReportBean;
import com.tera.report.echarts.EchartsResult;
import com.tera.report.echarts.Series;
import com.tera.sys.model.Org;
import com.tera.sys.service.MybatisBaseService;
import com.tera.sys.service.OrgService;
import com.tera.util.MathUtils;
import com.tera.util.StringUtils;

/**放款统计Service
 * @author QYANZE
 *
 */
@Service("lendReportService")
public class LendReportService extends MybatisBaseService<LendReportBean>{

	@Autowired(required=false)
	private LendReportDao dao;
	@Autowired(required=false)
	private ProductService<Product> productService;
	@Autowired(required=false)
	private OrgService orgService;
	
	/**
	 * 列表查询
	 * @param map
	 * @return
	 */
	public List<LendReportBean> queryList(Map<String, Object> map) {
		return dao.queryList(map);
	}
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public PageList<LendReportBean> queryPageList(Map<String, Object> params) {
		return this.selectPageList(LendReportDao.class, "queryList", params);
	}

	/**
	 * 获取echarts报表数据
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EchartsResult getLendEchartData(Map<String,Object> queryMap) throws Exception {
		// 定义echarts相关
		List<String> legend = new ArrayList<String>();
		List<String> category = new ArrayList<String>();
		List<Series> series = new ArrayList<Series>();
		
		Map<String,Object> tmpMap = new HashMap<String,Object>();
		tmpMap.put("level", "2");
		List<Org> orgs = orgService.queryList(tmpMap);
		tmpMap.put("type", "3");
		tmpMap.put("state", "1");
		List<Product> products = productService.queryList(tmpMap);
		// 截取产品大类型
		List<String> productNames = new ArrayList<String>();
		for (Product product : products) {
			String pname = product.getName();
			productNames.add(pname.substring(0, 3));
		}
		HashSet<String> pNameSet = new HashSet<String>(productNames);
		
		for (Iterator<String> iterator = pNameSet.iterator(); iterator.hasNext();) {
			String productName = (String) iterator.next();
			List<Object> seriesData = new ArrayList<Object>();
			legend.add(productName);
			for (Org o : orgs) {
				queryMap.put("orgId", o.getOrgId());
				queryMap.put("product", productName);
				seriesData.add(queryLendAmountByOrgAndProduct(queryMap));
			}
			series.add(new Series(productName, Series.TYPE_BAR, seriesData));
		}
		
		// 横轴为营业部
		for (Org org : orgs) {
			category.add(org.getOrgName());
		}
		
		EchartsResult echartData = new EchartsResult(legend, category, series);
		return echartData;
	}

	/**
	 * 根据机构和产品获取放款金额
	 * @param map
	 * @return
	 */
	private double queryLendAmountByOrgAndProduct(Map<String, Object> map) {
		String amountStr = dao.queryLendAmountByOrgAndProduct(map);
		// 转为万为单位
		double amount = 0.00d;
		if (StringUtils.isNotNullAndEmpty(amountStr)) {
			amount = Double.parseDouble(amountStr);
		}
		amount = MathUtils.div(amount, 10000d, 2);
		return amount;
	}
}
