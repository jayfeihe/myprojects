package com.tera.losecredit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tera.losecredit.dao.LoseCreditDao;
import com.tera.losecredit.model.LoseCredit;
import com.tera.util.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoTestContext.xml"
				}
		)
public class LoseCreditTest  extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired(required=false)
    private LoseCreditDao dao;
	
	@Test
	@Rollback(false)
	public void test() throws Exception {
		Map<String , Object> params = new HashMap<String,Object>();
		
		long startQueryTime = System.currentTimeMillis();
		System.out.println("=================查询数据开始=================");
		List<LoseCredit> list = dao.queryList(params);
		System.out.println("=================查询数据结束=================");
		long endQueryTime = System.currentTimeMillis();
		System.out.println("=================查询数据耗时："+String.valueOf(endQueryTime-startQueryTime)+"ms=================");
		
		System.out.println("=================数据量："+list.size()+"条=================");
		
		System.out.println("=================匹配开始=================");
		long startlevenTime = System.currentTimeMillis();
		for (LoseCredit loseCredit : list) {
			String iname = loseCredit.getIname();
			double levenshtein = StringUtils.levenshtein(iname, "徐美卿");
			if (levenshtein > 0.90d) {
				System.out.println(loseCredit.getId()+"-"+loseCredit.getIname());
			}
		}
		long endlevenTime = System.currentTimeMillis();
		System.out.println("=================匹配结束=================");
		System.out.println("=================匹配耗时："+String.valueOf(endlevenTime-startlevenTime)+"ms=================");
		
	}
}
