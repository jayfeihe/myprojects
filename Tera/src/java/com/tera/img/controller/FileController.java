/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.img.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.ServiceException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSErrorCode;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;
import com.tera.img.service.ImgService;
import com.tera.loan.controller.LoanDepartVerifyController;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.Parameter;
import com.tera.sys.service.ParameterService;
import com.tera.util.DateUtils;
import com.tera.util.IOUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ZipUtils;

/**
 * @author Wallace chu
 *
 */
@Controller

public class FileController {
	
	@Autowired(required=false) //自动注入
	private ImgService imgService;
	@Autowired(required=false) //自动注入
	private ParameterService<Parameter> parameterService;
	
	private final static Logger log = Logger.getLogger(FileController.class);
	

	/**
	 * 本地上传图片
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@ResponseBody 
//	@RequestMapping("/file/upload/zipupload.do")
	public void zipupload(HttpServletRequest request,HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		String loginid=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String orgId=org.getOrgId();
		// ajaxSubmit 异步提交 必须设置返回类型  是 text
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String dirUrl=parameterService.queryByParamName("imgroot").getParamValue();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(15943040);
		InputStream inStream = null;
		try {
			List fileItems = upload.parseRequest(request);
			//得到申请ID
			FileItem itemorg = (FileItem) fileItems.get(0);
			String appId=itemorg.getString();
			String dirUrl1=dirUrl+"/"+appId+"/";
			String imgUrl;
			//得到 上传的ZIP
			FileItem itemfile = (FileItem) fileItems.get(1);
			inStream = itemfile.getInputStream();
			String filename=itemfile.getName();
			String type=filename.substring(filename.lastIndexOf("."));
			if(".zip".equals(type.toLowerCase())){
				String name=filename.substring(0,filename.lastIndexOf("."));
				String sjName=DateUtils.toTimeString(new Date()).replace(":","").replace(" ","_");
				imgUrl=dirUrl1+"img/";
				dirUrl1+=name;
				dirUrl1+="_"+sjName;
				dirUrl1+=type;
				dirUrl1=dirUrl1.replace("//","/");
				//把文件写入 本地
				IOUtils.write(initFile(dirUrl1), IOUtils.getBytes(inStream));
				//解压zip 
				Map<String, String> jieyaMap=ZipUtils.unZip(dirUrl1, imgUrl);
				// 把 列表数据入库
				imgService.imgPut(jieyaMap, appId,"",loginid,orgId);
				writer.print(JsonUtil.object2json(new JsonMsg(true, "附件上传完成。")));
			}else{
				writer.print(JsonUtil.object2json(new JsonMsg(false, "文件类型必须为ZIP")));
			}
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			e.printStackTrace();
			map.put("exception", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "上传失败，错误信息："+e.toString())));
			writer.flush();
			writer.close();
		}
		log.info(thisMethodName+":end");
	}
	
	File initFile(String fileUrl){
		File file=new File(fileUrl);
		File parent = file.getParentFile();
		if(parent!=null&&!parent.exists()){
			parent.mkdirs();
		}else if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	} 
	
	
 
//    @ResponseBody 
	@RequestMapping("/file/upload/zipupload.do")
	public void zipuploadYUN(HttpServletRequest request,HttpServletResponse response, ModelMap map) throws Exception {
    	String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		String loginid=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String orgId=org.getOrgId();
		// ajaxSubmit 异步提交 必须设置返回类型  是 text
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();

		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(15943040);
		InputStream inStream = null;
		// 创建 住文件夹   如果存在 不处理
//		ensureBucket(client, bucketName);
		try {
			
			List fileItems = upload.parseRequest(request);
			//得到申请ID
			FileItem itemorg = (FileItem) fileItems.get(0);
			String appId=itemorg.getString();
			//得到 上传的ZIP
			DiskFileItem itemfile = (DiskFileItem) fileItems.get(1);
			inStream = itemfile.getInputStream();
			String filename=itemfile.getName();
			String type=filename.substring(filename.lastIndexOf("."));
			if(".zip".equals(type.toLowerCase())){
				String zipName=filename.substring(0,filename.lastIndexOf("."));
				String sjName=DateUtils.toTimeString(new Date()).replace(":","").replace(" ","_");
				zipName=zipName+"_"+sjName+type;
				//zip 保存
				imgService.aliyunOSSPut(itemfile.getSize(), itemfile.getInputStream(), appId+"/"+zipName);
//				//解压zip 
				ZipFile zipFile = new ZipFile(itemfile.getStoreLocation().getPath(), "GBK"); 
				Map<String, List<Object>> yunMap=ZipUtils.unZip(zipFile);
				Map<String, String> jieyaMap=new HashMap<String, String>();
				//上传到云
				for (String key : yunMap.keySet()) {
					if(key.matches("[A-Z]{1}[0-9]{2}.*")){
						String pathUrl=appId+"/img/"+key;
						jieyaMap.put(key,pathUrl );
						List<Object> ls=yunMap.get(key);
						long lo=Long.valueOf(ls.get(0).toString());
						InputStream input=(InputStream) ls.get(1);
						//把解压后的 的 img 上传到云
						imgService.aliyunOSSPut(lo, input, pathUrl);
					}
				}
				zipFile.close();
				// 把 列表数据入库
				imgService.imgPut(jieyaMap, appId,"",loginid,orgId);
				writer.print(JsonUtil.object2json(new JsonMsg(true, "附件上传完成。")));
			}else{
				writer.print(JsonUtil.object2json(new JsonMsg(false, "文件类型必须为ZIP")));
			}
			writer.flush();
			writer.close();
			inStream.close();
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			map.put("exception", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "上传失败，错误信息："+e.toString())));
			writer.flush();
			writer.close();
			inStream.close();
			throw e;
		}
		log.info(thisMethodName+":end");
	}
	

}
