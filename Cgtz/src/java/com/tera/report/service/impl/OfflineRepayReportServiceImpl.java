package com.tera.report.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.tera.interfaces.constants.InterConstants;
import com.tera.interfaces.util.GsonUtils;
import com.tera.interfaces.util.HttpUtil;
import com.tera.report.model.interform.RepayParam;
import com.tera.report.model.interform.ResultInfo;
import com.tera.report.model.interform.ResultJson;
import com.tera.report.service.IOfflineRepayReportService;
import com.tera.sys.model.Parameter;
import com.tera.sys.service.ParameterService;
import com.tera.util.PasswordUtil;
import com.tera.util.StringUtils;

/**
 * 线下还本计划报表Service
 * @author QYANZE
 *
 */
@Service("offlineRepayReportService")
public class OfflineRepayReportServiceImpl implements IOfflineRepayReportService {

	private static final Logger log = Logger.getLogger(OfflineRepayReportServiceImpl.class);
	
	@Autowired(required = false)
	private ParameterService parameterService;
	
	/** (non-Javadoc)
	 * @see com.tera.report.service.IOfflineRepayReportService#getInfos(com.tera.report.model.interform.RepayParam)
	 */
	@Override
	public ResultInfo getInfos(RepayParam param) {
		String paramJson = GsonUtils.getInstance().toJson(param);
		//签名认证
		long ctime = System.currentTimeMillis() / 1000;
		String sign = PasswordUtil.md5(InterConstants.SECRET_ID + ctime + paramJson);
		
		Parameter parameter = this.parameterService.queryByParamName("onlineip");
		String rootUrl = parameter.getParamValue() + "/business/api/ProjectRateRepay?appId="+InterConstants.APP_ID+"&sign="+sign+"&ctime="+String.valueOf(ctime);
		
		String project_id = param.getProject_id();
		String rate_start_time = param.getRate_start_time();
		String rate_end_time = param.getRate_end_time();
		String debt_type = param.getDebt_type();
		int page_size = param.getPage_size();
		int page = param.getPage();
		String export = param.getExport();
		
		StringBuffer urlBuffer = new StringBuffer(rootUrl);
		if (StringUtils.isNotNullAndEmpty(project_id)) {
			urlBuffer.append("&project_id="+project_id);
		}
		if (StringUtils.isNotNullAndEmpty(rate_start_time)) {
			urlBuffer.append("&rate_start_time="+rate_start_time);
		}
		if (StringUtils.isNotNullAndEmpty(rate_end_time)) {
			urlBuffer.append("&rate_end_time="+rate_end_time);
		}
		if (StringUtils.isNotNullAndEmpty(debt_type)) {
			urlBuffer.append("&debt_type="+debt_type);
		}
		if (StringUtils.isNotNullAndEmpty(String.valueOf(page_size))) {
			urlBuffer.append("&page_size="+page_size);
		}
		if (StringUtils.isNotNullAndEmpty(String.valueOf(page))) {
			urlBuffer.append("&page="+page);
		}
		if (StringUtils.isNotNullAndEmpty(export)) {
			urlBuffer.append("&export="+export);
		}
		String getUrl = urlBuffer.toString();
		
		try {
			DefaultHttpClient client = HttpUtil.getDefaultHttpClient();
			
			HttpGet get = new HttpGet(getUrl);
			
			HttpResponse res = client.execute(get);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(entity);
				
				log.info("线下还本计划接口返回的json数据："+result);
				
				
				ResultJson resultJson = GsonUtils.getInstance().fromJson(result, new TypeToken<ResultJson>() {});
				if (resultJson != null) {
					return resultJson.getData();
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
