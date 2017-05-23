package com.tera.audit.judge.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.judge.model.JudgeAdv;
import com.tera.audit.judge.model.form.JudgeFormBean;
import com.tera.audit.judge.service.IJudgeAdviceService;
import com.tera.audit.judge.service.IJudgeReviewService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/** 评审会复核Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/judge/review")
public class JudgeReviewController {

	private final static Logger log = Logger.getLogger(JudgeReviewController.class);
	
	@Autowired(required=false) //自动注入
	private ILoanBaseService loanBaseService;
	@Autowired
	private IJudgeReviewService judgeReviewService;
	@Autowired
	private IJudgeAdviceService judgeAdviceService;
	
	
	/**
	 * 跳转到评审会初审的查询条件页面
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query.do")
	public String judgeReviewQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "judge/review/judgeReviewQuery";
	}
	
	/**
	 * 跳转到评审会初审的列表页面
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String judgeReviewList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanBase.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		queryMap.put("bpmStates", new String[]{CommonConstant.PROCESS_E});
		if (0 == loginUser.getIsAdmin()) {
			queryMap.put("processer", loginUser.getLoginId());
			queryMap.put("orgId", loginOrg.getOrgId());
		}
		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<LoanBase> loanBaseList = this.loanBaseService.queryPageList(queryMap);
		pm.setData(loanBaseList);
		pm.initRowsCount(loanBaseList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "judge/review/judgeReviewList";
	}
	
	@RequestMapping("/update.do")
	public String judgeReviewUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = null;
		//如果存在
		if (StringUtils.isNotNullAndEmpty(id)) {
			loanBase  = this.loanBaseService.queryByKey(id);
			
			// 影像资料场景类型
			// 个人
			if ("01".equals(loanBase.getLoanType())) {
				map.put("sec","filesce1");
			}
			// 公司
			if ("02".equals(loanBase.getLoanType())) {
				map.put("sec","filesce2");
			}
			
			// 续贷
			if ("1".equals(loanBase.getIsRenew())) {
				LoanBase origLoanBase = this.loanBaseService.queryByLoanId(loanBase.getOrigLoanId());
				map.put("origLoanBase", origLoanBase);
				map.put("isTgth", origLoanBase.getIsTgth());
			} else {
				map.put("isTgth", loanBase.getIsTgth());
			}
		}
		map.put("loanBase", loanBase);
		
		// 评审会意见
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("loanId", loanBase.getLoanId());
		queryMap.put("num", this.judgeAdviceService.getNextNum(loanBase.getLoanId())-1);
		List<JudgeAdv> judgeAdvs = this.judgeAdviceService.queryList(queryMap );
		map.put("judgeAdvs", judgeAdvs);
		
		log.info(thisMethodName+":end");
		return "judge/review/judgeReviewUpdate";
	}
	
	@RequestMapping("/save.do")
	public void judgeReviewSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		
		try {
			JudgeFormBean bean = (JudgeFormBean) RequestUtils.getRequestBean(JudgeFormBean.class, request);
			JsonMsg jsonMsg = this.judgeReviewService.operateProcess(bean,loginId);
			writer.print(JsonUtil.object2json(jsonMsg));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
}
