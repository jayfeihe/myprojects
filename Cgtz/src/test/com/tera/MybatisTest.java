package com.tera;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.dao.PayOutDao;
import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.audit.account.model.form.PayOutQBean;
import com.tera.audit.account.service.IPayOutService;
import com.tera.audit.judge.model.form.JudgeFormBean;
import com.tera.audit.judge.service.IJudgeFirstServcie;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.push.service.IPushService;
import com.tera.feature.loanguar.model.LoanGuar;
import com.tera.feature.loanguar.service.ILoanGuarService;
import com.tera.file.model.Files;
import com.tera.file.service.FileService;
import com.tera.interfaces.service.InterfaceService;
import com.tera.sys.model.JsonMsg;
import com.tera.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoTestContext.xml"
				}
		)
public class MybatisTest extends AbstractTransactionalJUnit4SpringContextTests  {

	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private IPayOutService payOutService;
	@Autowired
	private PayOutDao dao;
	
	@Autowired
	private ILoanAppService loanAppService;
	@Autowired
	private ICollateralService collateralService;
	@Autowired
	private ILoanGuarService loanGuarService;
	@Autowired
	private FileService fileService;
	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private IPushService pushService;
	@Autowired
	private IJudgeFirstServcie judgeFirstService;
	
	@Test
	@Rollback(true)
	public void test01() throws Exception{
		LoanBase base = new LoanBase();
		base.setName("1111111");
		base.setIdNo("333333222222222");
		base.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		this.loanBaseService.add(base);
		Assert.assertNotSame(0, base.getId());
		System.out.println(base.getId());
	}
	
	@Test
	public void test02() {
		PageList<PayOutQBean> list = payOutService.queryPageList(new HashMap<String, Object>());
//		List<PayOutQBean> list = dao.queryList(null);
		Assert.assertTrue(list.size()==0);
	}
	
	@Test
	public void test03(){
		try {
//			LoanBase base = this.loanBaseService.queryByKey(15);
//			AppBean bean = new AppBean();
//			bean.setLoanBase(base);
//			LoanApp bean = this.loanAppService.queryByKey(14);
//			Collateral bean = this.collateralService.queryByKey(7);
			LoanGuar bean = this.loanGuarService.queryByKey(3);
			String jsonStr = JsonUtil.object2json(bean);
			FileUtils.writeStringToFile(new File("app2.json"), jsonStr, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test04(){
		try {
			Files files = this.fileService.queryByKey(31);
			String path = files.getFilePath();
			System.out.println(path.substring(0, path.lastIndexOf("\\")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Rollback(true)
	public void testPush() {
		try {
			AccountFormBean bean = new AccountFormBean();
			JsonMsg jsonMsg = null;
			// 直投车贷
//			bean.setLoanId("HZ_0001201602170751092");
//			bean.setRemark("直投车贷-推送线上测试");
//			jsonMsg = this.pushService.operateProcess(bean , "tszy");
//			System.out.println(jsonMsg.getMessage());
			
			// 直投房贷
			bean.setLoanId("HZ_0001201602191002221");
			bean.setRemark("直投房贷-推送线上测试");
			jsonMsg = this.pushService.operateProcess(bean , "tszy");
			System.out.println(jsonMsg.getMessage());
			
			// 直投红木
			bean.setLoanId("HZ_0001201602171025155");
			bean.setRemark("直投红木-推送线上测试");
			jsonMsg = this.pushService.operateProcess(bean , "tszy");
			System.out.println(jsonMsg.getMessage());
			
			// 直投海鲜
			bean.setLoanId("HZ_0001201602220759106");
			bean.setRemark("直投海鲜-推送线上测试");
			jsonMsg = this.pushService.operateProcess(bean , "tszy");
			System.out.println(jsonMsg.getMessage());
			
			// 债权车贷
			bean.setLoanId("HZ_0001201602230122494");
			bean.setRemark("债权车贷-推送线上测试");
			jsonMsg = this.pushService.operateProcess(bean , "tszy");
			System.out.println(jsonMsg.getMessage());
			
			// 债权房贷
			bean.setLoanId("HZ_0001201602280856237");
			bean.setRemark("债权房贷-推送线上测试");
			jsonMsg = this.pushService.operateProcess(bean , "tszy");
			System.out.println(jsonMsg.getMessage());
			
			// 债权车商
			bean.setLoanId("HZ_0001201602191004173");
			bean.setRemark("债权车商-推送线上测试");
			jsonMsg = this.pushService.operateProcess(bean , "tszy");
			System.out.println(jsonMsg.getMessage());
			
			// 债权海鲜
			bean.setLoanId("HZ_0001201602170916164");
			bean.setRemark("债权海鲜-推送线上测试");
			jsonMsg = this.pushService.operateProcess(bean , "tszy");
			System.out.println(jsonMsg.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Rollback(true)
	public void test05() throws Exception {
		JudgeFormBean bean = new JudgeFormBean();
		bean.setLoanId("HZ_0001201602191004173");
		bean.setDecision("1");
		this.judgeFirstService.operateProcess(bean , "psms1");
	}
}
