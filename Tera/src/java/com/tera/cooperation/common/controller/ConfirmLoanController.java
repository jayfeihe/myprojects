package com.tera.cooperation.common.controller;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tera.cooperation.common.model.ConfirmLoanQBean;
import com.tera.cooperation.common.service.ConfirmLoanService;
import com.tera.credit.model.CreditApp;
import com.tera.credit.service.CreditAppService;
import com.tera.img.service.ImgService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Parameter;
import com.tera.sys.service.ParameterService;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.ZipUtils;

/**
 * 渠道放款管理Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/cooperation/common/loanManage")
public class ConfirmLoanController {

	private final static Logger log = Logger.getLogger(ConfirmLoanController.class);
	
	@Autowired(required=false)
	private ConfirmLoanService confirmLoanService;
	@Autowired(required=false)
	private CreditAppService creditAppService;
	@Autowired(required=false)
	private ParameterService<Parameter> parameterService;
	@Autowired(required=false)
	private ImgService imgService;

	
	@RequestMapping("/query.do")
	public String confirmLoanQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "cooperation/common/loanManage/confirmLoanQuery";
	}
	
	@RequestMapping("/list.do")
	public String confirmLoanList(HttpServletRequest request, ModelMap map)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		PageModel pm = new PageModel();
		ConfirmLoanQBean bean = (ConfirmLoanQBean) RequestUtils
				.getRequestBean(ConfirmLoanQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		int rowsCount = this.confirmLoanService
				.queryConfirmLoanCount(queryMap);
		
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		
		List<ConfirmLoanQBean> confirmLoanList = this.confirmLoanService
				.queryConfirmLoanList(queryMap);
		
		pm.setData(confirmLoanList);
		map.put("pm", pm);
		
		log.info(thisMethodName+":end");
		return "cooperation/common/loanManage/confirmLoanList";
	}
	
	@RequestMapping("/update.do")
	public String confirmLoanUpdate(String flag, String appNo, String channelType,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap map) throws Exception {
		
		String thisMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		log.info(thisMethodName + ":start");
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("appId", appNo);
		CreditApp creditApp = creditAppService.queryList(queryMap).get(0);
		
		map.put("bean", creditApp);
		map.put("flag", flag);	
		map.put("channelType", channelType);	
		
		log.info(thisMethodName + ":end");
		return "cooperation/common/loanManage/confirmLoanUpdate";
	}
	
	@RequestMapping("/save.do")
	public void confirmLoanSave(String flag,String channelType,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		ConfirmLoanQBean confirmLoanQBean = (ConfirmLoanQBean) RequestUtils
				.getRequestBeanList("confirmLoanQBean",
						ConfirmLoanQBean.class, request).get(0);
		confirmLoanQBean.setChannelType(channelType);
		try {
			confirmLoanService.loanManageOparete(confirmLoanQBean,flag,loginId);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
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
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/fileUpload.do")
	public void zipupload(HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");

		String loginid = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String orgId = org.getOrgId();

		// ajaxSubmit 异步提交 必须设置返回类型 是 text
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(15943040);
		InputStream inStream = null;

		try {
			List fileItems = upload.parseRequest(request);
			// 得到申请ID
			FileItem itemorg = (FileItem) fileItems.get(0);
			String appId = itemorg.getString();
			// 得到 上传的ZIP
			DiskFileItem itemfile = (DiskFileItem) fileItems.get(1);
			inStream = itemfile.getInputStream();
			String filename = itemfile.getName();
			String type = filename.substring(filename.lastIndexOf("."));

			if (".zip".equals(type.toLowerCase())) {
				String zipName = filename.substring(0,
						filename.lastIndexOf("."));
				String timeName = DateUtils.toTimeString(new Date()).replace(":", "").replace(" ", "_");
				zipName = zipName + "_" + timeName + type;
				
				ZipFile zipFile = new ZipFile(itemfile.getStoreLocation().getPath(), "GBK");
				// 解压ZIP
				Map<String, List<Object>> yunMap = ZipUtils.unZip(zipFile);
				
				Map<String, String> unZipMap = new HashMap<String, String>();
				Set<String> keySet = yunMap.keySet();
				for (String key : keySet) {
					if (key.toUpperCase().startsWith("M11")) {
						String pathUrl = appId + "/img/" + key;
						unZipMap.put(key, pathUrl);
						List<Object> ls = yunMap.get(key);
						long lo = Long.valueOf(ls.get(0).toString());
						InputStream input = (InputStream) ls.get(1);
						// 把解压后的 的 IMG 上传到云
						imgService.aliyunOSSPut(lo, input, pathUrl);
					} else {
						writer.print(JsonUtil.object2json(new JsonMsg(false,"请上传M11类文件。")));
						return;
					}
				}
				// ZIP 保存
				imgService.aliyunOSSPut(itemfile.getSize(),itemfile.getInputStream(), appId + "/" + zipName);
				
				zipFile.close();
				// 把 列表数据入库
				imgService.imgPut(unZipMap, appId, "", loginid, orgId);
				writer.print(JsonUtil.object2json(new JsonMsg(true, "附件上传完成。")));
			} else {
				writer.print(JsonUtil.object2json(new JsonMsg(false,
						"文件类型必须为ZIP")));
			}
		} catch (Exception e) {
			log.error(thisMethodName + ":error", e);
			e.printStackTrace();
			map.put("exception", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "上传失败，错误信息："
					+ e.toString())));
		} finally {
			if (inStream != null) {
				inStream.close();
			}
			if (writer != null) {
				writer.flush();
				writer.close();
			}
			log.info(thisMethodName + ":end");
		}
	}
}
