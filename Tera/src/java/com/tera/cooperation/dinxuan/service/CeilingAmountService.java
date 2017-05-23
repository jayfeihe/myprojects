package com.tera.cooperation.dinxuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.cooperation.dinxuan.constants.DXConstants;
import com.tera.cooperation.dinxuan.dao.CeilingAmountDao;
import com.tera.cooperation.dinxuan.model.CeilingAmountQBean;
import com.tera.sys.model.Parameter;
import com.tera.sys.service.ParameterService;
import com.tera.util.MathUtils;


/**
 * 鼎轩上限金额Service
 * 
 * @author QYANZE
 *
 */
@Service("ceilingAmountService")
public class CeilingAmountService {

	@Autowired(required=false)
	private CeilingAmountDao dao;
	@Autowired(required=false)
	private ParameterService<Parameter> parameterService;
	
	public CeilingAmountQBean countDxAmountOfWeek() {
		Parameter dxParam = parameterService
				.queryByParamName(DXConstants.DX_CEILING_AMOUNT_PARAM_NAME);
		double approvalPassAmountOfWeek = dao.countApprovalPassAmountOfWeek();
		double signPassAmountOfWeek = dao.countSignPassAmountOfWeek();
		double dxCeilingAmount = Double.parseDouble(dxParam.getParamValue());
		double leaveAmountOfWeek = MathUtils.sub(dxCeilingAmount, approvalPassAmountOfWeek);
		double leaveSignAmountOfWeek = MathUtils.sub(dxCeilingAmount, signPassAmountOfWeek);
		CeilingAmountQBean bean = new CeilingAmountQBean();
		bean.setParamId(dxParam.getId());
		// 以万元单位显示
		dxCeilingAmount = MathUtils.div(dxCeilingAmount, 10000);
		approvalPassAmountOfWeek = MathUtils.div(approvalPassAmountOfWeek, 10000);
		leaveAmountOfWeek = MathUtils.div(leaveAmountOfWeek, 10000);
		signPassAmountOfWeek = MathUtils.div(signPassAmountOfWeek, 10000);
		leaveSignAmountOfWeek = MathUtils.div(leaveSignAmountOfWeek, 10000);
		
		bean.setCeilingAmount(dxCeilingAmount);
		bean.setPassAmountOfWeek(approvalPassAmountOfWeek);
		bean.setLeaveAmountOfWeek(leaveAmountOfWeek);
		bean.setSignAmountOfWeek(signPassAmountOfWeek);
		bean.setLeaveSignAmountOfWeek(leaveSignAmountOfWeek);
		return bean;
	}
}
