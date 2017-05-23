package com.greenkoo.dmp;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.greenkoo.dmp.model.NetworkPc;
import com.greenkoo.dmp.service.INetworkPcService;
import com.greenkoo.dmp.sys.model.Pager;
import com.greenkoo.dmp.sys.model.SystemContext;
import com.greenkoo.dmp.utils.FastJsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class TestNetworkService {

	@Autowired
	private INetworkPcService networkService;
	
	@Test
	public void testFindAll() {
		List<NetworkPc> list = networkService.findAll();
		System.out.println("数据有"+list.size()+"条");
		for (NetworkPc dmp : list) {
			System.out.println("数据："+FastJsonUtil.toJSONString(dmp));
		}
	}
	
	@Test
	public void testPageList() {
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(10);
		
		Pager<NetworkPc> pager = networkService.pageList();
		System.out.println("当前第"+pager.getCurPage()+"页,每页"+pager.getPageSize()+"条，共"+pager.getTotalPage()+"页,总记录："+pager.getTotal()+"条");
		Collection<NetworkPc> networks = pager.getDatas();
		for (NetworkPc network : networks) {
			System.out.println(FastJsonUtil.toJSONString(network));
		}
		SystemContext.removePageOffset();
		SystemContext.removePageSize();
		
		SystemContext.setPageOffset(1);
		SystemContext.setPageSize(10);
		
		Pager<NetworkPc> pager2 = networkService.pageList();
		System.out.println("当前第"+pager2.getCurPage()+"页,每页"+pager2.getPageSize()+"条，共"+pager2.getTotalPage()+"页,总记录："+pager2.getTotal()+"条");
		Collection<NetworkPc> networks2 = pager.getDatas();
		for (NetworkPc network : networks2) {
			System.out.println(FastJsonUtil.toJSONString(network));
		}
	}
}
