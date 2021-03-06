/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.file.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import third.rewrite.fastdfs.StorePath;
import third.rewrite.fastdfs.service.IStorageClientService;
import third.rewrite.fastdfs.service.impl.ByteArrayFdfsFileInputStreamHandler;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.file.config.Configurations;
import com.tera.file.config.FileConstant;
import com.tera.file.model.Files;
import com.tera.file.service.FileService;
import com.tera.file.util.SreamIOUtil;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * T_FILE控制器
 * <b>功能：</b>FileController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 10:28:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/files")
public class FileController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(FileController.class);
	
	/**
	 * FileService
	 */
	@Autowired(required=false) //自动注入
	private FileService fileService;
	
	@Autowired
	private IStorageClientService storageClientService;

	
	/**
	 * 跳转到T_FILE的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/load.do")
	public String fileQuery(HttpServletRequest request, ModelMap map,String loId,String sec,String bizKey,String opt) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		map.put("loanId", loId);
		map.put("secId", sec);
		map.put("biz", bizKey);
		map.put("opt", opt); // 0：操作,1：查看
		log.info(thisMethodName+":end");
		return "files/fileupload";
	}

	@RequestMapping("/tk.do")
	public void tokekSer(HttpServletRequest req,  HttpServletResponse resp,ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		fileService.tokenService(req, resp);
		log.info(thisMethodName+":end");
		
	}
	
	@RequestMapping(value="/upload.do" ,method =RequestMethod.GET)
	public void uploadSer(HttpServletRequest req,  HttpServletResponse resp,ModelMap map,String loanId,String secId,String biz,String catId) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		fileService.streamServiceGet(req, resp,loanId,secId,biz,catId);
		
		log.info(thisMethodName+":end");
		
	}
	@RequestMapping(value="/upload.do" ,method =RequestMethod.POST)
	public void uploadSer2(HttpServletRequest req,  HttpServletResponse resp,ModelMap map,String loanId,String secId,String biz,String catId) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		String loginId="";
		fileService.streamServicePost(req, resp, loanId, secId, biz, catId);
		
		log.info(thisMethodName+":end");
		
	}

	@RequestMapping("/fd.do")
	public void fdSer(HttpServletRequest req,  HttpServletResponse resp,ModelMap map,String loanId) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		fileService.FormDataService(req, resp,loanId);
		log.info(thisMethodName+":end");
		
	}
	
	@RequestMapping (value = "/update.do" ,method =RequestMethod.GET)
	public String fileUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Files bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.fileService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "file/fileUpdate";
	}

	/**
	 * 保存T_FILE数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void fileSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Files bean = (Files) RequestUtils.getRequestBean(Files.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.fileService.updateOnlyChanged(bean);
			} else { //如果不存在
				this.fileService.add(bean);
			}
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

	/**
	 * 删除文件
	 * @param response response
	 * @param ids id
	 * @throws Exception exception
	 */
	@RequestMapping("/deleteFile.do")
	public void deleteFile(String[] ids, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			if(ids != null) {
				for (String id : ids) {
					Files files = this.fileService.queryByKey(id);
					
					//String savePath = Configurations.USER_FILE_PATH + files.getFilePath();
					
					log.info("-----------  申请编号："+files.getLoanId()+"  ----------------");
					log.info("-----------  场景："+files.getSceneType()+"  ----------------");
					log.info("-----------  业务id："+files.getBizKey()+"  ----------------");
					//log.info("--------------  删除路径："+savePath+"  -------------------");
					storageClientService.deleteFile(files.getGroupName(), files.getFilePath());
//					// 删除磁盘文件
//					File delFile = new File(savePath);
//					if (delFile.exists()) {
//						if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
//							Runtime.getRuntime().exec("chmod 777 " + delFile);
//						}
//						boolean delFlag = delFile.delete();
//						if(delFlag) {
//							log.info("-------------  文件：["+files.getFileName()+ "]删除成功 ------------------");
//						}
//					}
				}
				
				// 删除数据库数据
				Map<String, Object> delMap = new HashMap<String,Object>();
				delMap.put("ids", ids);
				this.fileService.deleteByMap(delMap );
				writer.print(JsonUtil.object2json(new JsonMsg(true, "删除成功！")));
			} else {
				writer.print(JsonUtil.object2json(new JsonMsg(false, "数据异常，删除失败！")));
			}
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "数据异常，删除失败！")));
			throw e;
		} finally {
			writer.flush();
			writer.close();
		}
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 按照类型删除图片
	 * @param response response
	 * @param ids id
	 * @throws Exception exception
	 */
	@RequestMapping("/deleteImage.do")
	public void deleteImage(String loanId,String bizKey,String sec,String category,HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			if (StringUtils.isNullOrEmpty(loanId) 
					|| StringUtils.isNullOrEmpty(bizKey)
					|| StringUtils.isNullOrEmpty(sec) 
					|| StringUtils.isNullOrEmpty(category)) {
				
				writer.print(JsonUtil.object2json(new JsonMsg(false, "数据异常，删除失败！")));
				return;
			}
			
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("loanId", loanId);
			queryMap.put("bizKey", bizKey);
			queryMap.put("sceneType", sec);
			queryMap.put("category", category);
			queryMap.put("ext", Files.EXT_IMG);
			queryMap.put("state", "1");
			List<Files> delImages = this.fileService.queryList(queryMap);
			
			if(delImages != null && delImages.size() > 0) {
				for (Files img : delImages) {
					String savePath = Configurations.USER_FILE_PATH + img.getFilePath();
					
					log.info("-----------  申请编号："+img.getLoanId()+"  ----------------");
					log.info("-----------  场景："+img.getSceneType()+"  ----------------");
					log.info("-----------  业务id："+img.getBizKey()+"  ----------------");
					log.info("--------------  删除路径："+savePath+"  -------------------");
					
//					// 删除磁盘文件
//					File delFile = new File(savePath);
//					
//					if (delFile.exists()) {
//						if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
//							Runtime.getRuntime().exec("chmod 777 " + delFile);
//						}
//						boolean delFlag = delFile.delete();
//						if(delFlag) {
//							log.info("-------------  文件：["+img.getFileName()+ "]删除成功 ------------------");
//						}
//					}
					storageClientService.deleteFile(img.getGroupName(), img.getFilePath());
					
					// 删除数据库数据
					this.fileService.delete(img.getId());
				}
				writer.print(JsonUtil.object2json(new JsonMsg(true, "删除成功！")));
			} else {
				writer.print(JsonUtil.object2json(new JsonMsg(false, "数据异常，删除失败！")));
			}
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "数据异常，删除失败！")));
			throw e;
		} finally {
			writer.flush();
			writer.close();
		}
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到查看的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String fileRead(String loanId,String bizKey,String sec,String category,String opt,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		if (StringUtils.isNotNullAndEmpty(loanId) 
				&& StringUtils.isNotNullAndEmpty(bizKey)) {
			
			// 图片分类
			List<Files> imgCategories = this.fileService.queryCategoryCount(loanId,bizKey, sec,Files.EXT_IMG);
			map.put("imgCategories", imgCategories);
			
			Map<String, Object> queryMap = new HashMap<String,Object>();
			queryMap.put("loanId", loanId);
			queryMap.put("bizKey", bizKey);
			queryMap.put("sceneType", sec);
			queryMap.put("category", category);
			queryMap.put("state", "1");
			
			// 图片数量
			queryMap.put("exts", new String[]{Files.EXT_IMG});
			int imgCount = this.fileService.queryCount(queryMap);
			map.put("imgCount", imgCount);
			
			
			queryMap.put("exts", new String[]{Files.EXT_DOC,Files.EXT_FILE});
			// 文件数量
			int fileCount = this.fileService.queryCount(queryMap);
			map.put("fileCount", fileCount);
			
			// 文件列表
			PageModel pm = new PageModel();
			pm.init(request, null, queryMap);
			queryMap.put("curPage", pm.getCurPage());
			queryMap.put("pageSize", pm.getPageSize());
			
			PageList<Files> files = this.fileService.queryPageList(queryMap);
			pm.setData(files);
			pm.initRowsCount(files.getPaginator().getTotalCount());
			map.put("pm", pm);
			
			map.put("loanId", loanId);
			map.put("bizKey", bizKey);
			map.put("sec", sec);
			map.put("category", category);
			map.put("opt", opt);
			map.put("rootUrl", Configurations.USER_FILE_PATH);
		}
		log.info(thisMethodName+":end");
		return "files/fileReadNew";
	}

	/**
	 * 获取全部图片
	 * @param loanId
	 * @param bizKey
	 * @param sec
	 * @param category
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/getImgs.do") 
	public void getImgs(String loanId,String bizKey,String sec,String category, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		if (StringUtils.isNotNullAndEmpty(loanId) 
				&& StringUtils.isNotNullAndEmpty(bizKey)) {
			PrintWriter writer = response.getWriter();
			response.setContentType("application/json;charset=UTF-8");
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("loanId", loanId);
			queryMap.put("bizKey", bizKey);
			queryMap.put("sceneType", sec);
			queryMap.put("category", category);
			queryMap.put("ext", Files.EXT_IMG);
			queryMap.put("state", "1");
			List<Files> list = this.fileService.queryList(queryMap);
			writer.print(JsonUtil.object2json(list));
			writer.flush();
			writer.close();
		}
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 通过图片路径查看图片
	 * @param imgurl
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/imgRead.do")
	public void imgRead(String imgurl,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("filePath", imgurl);
		List<Files> files=fileService.queryList(queryMap);
		if(files!=null&&files.size()>0){
			Files f=files.get(0);
			byte[] bytes=storageClientService.downloadFile(f.getGroupName(),imgurl ,  new ByteArrayFdfsFileInputStreamHandler());
			OutputStream out=response.getOutputStream();
			
			out.write(bytes);
			
			out.flush();
			out.close();		
		}
		
			
		
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 通过图片路径查看图片
	 * @param imgurl
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getfile.do")
	public void getfile(String filePath,HttpServletRequest request,HttpServletResponse response) throws Exception {
		InputStream fis = null;
		OutputStream output = null;
		try {
			response.reset();

			response.setContentType("application/x-download");
			response.addHeader("content-type ", "application/x-msdownload");
			response.setContentType("application/octet-stream");
			response.setHeader("content-disposition",
					"attachment; filename=" + transCharacter(request, filePath));

			
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("filePath", filePath);
			List<Files> files=fileService.queryList(queryMap);
			if(files!=null&&files.size()>0){
				Files f=files.get(0);
				byte[] bytes=storageClientService.downloadFile(f.getGroupName(),filePath ,  new ByteArrayFdfsFileInputStreamHandler());
				 output=response.getOutputStream();
				
				output.write(bytes);
				
				output.flush();
				
			}
			
		} catch (Exception ex) {
			System.err.println("下载出错");
		} finally {
			if (fis != null)
				fis.close();
			if (output != null)
				output.close();
		}
	}
	
	/**
	 * 下载文档视频
	 * @param filePath
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/download.do")
	public void download(String filePath,String fileName,HttpServletRequest request, HttpServletResponse response) throws Exception {

		InputStream fis = null;
		OutputStream output = null;
		try {
			response.reset();

			response.setContentType("application/x-download");
			response.addHeader("content-type ", "application/x-msdownload");
			response.setContentType("application/octet-stream");
			response.setHeader("content-disposition",
					"attachment; filename=" + transCharacter(request, fileName));

			
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("filePath", filePath);
			List<Files> files=fileService.queryList(queryMap);
			if(files!=null&&files.size()>0){
				Files f=files.get(0);
				byte[] bytes=storageClientService.downloadFile(f.getGroupName(),filePath ,  new ByteArrayFdfsFileInputStreamHandler());
				 output=response.getOutputStream();
				
				output.write(bytes);
				
				output.flush();
				
			}
			
		} catch (Exception ex) {
			System.err.println("下载出错");
		} finally {
			if (fis != null)
				fis.close();
			if (output != null)
				output.close();
		}
	}

	private String transCharacter(HttpServletRequest request, String str) throws Exception {
		String agent = request.getHeader("USER-AGENT").toLowerCase();
		if (agent.indexOf("msie") > 0 || agent.indexOf("like gecko") > 0) {
			return URLEncoder.encode(str, "UTF-8");
		}
		return new String(str.getBytes("UTF-8"), "ISO8859-1");
	}
	
	/**
	 * 跳转到查看的页面(防止同一嵌套页面下元素会串的问题)
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read2.do")
	public String fileRead2(String loanId,String bizKey,String sec,String category,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		if (StringUtils.isNotNullAndEmpty(loanId) 
				&& StringUtils.isNotNullAndEmpty(bizKey)) {
			
			// 图片分类
			List<Files> imgCategories = this.fileService.queryCategoryCount(loanId,bizKey, sec,Files.EXT_IMG);
			map.put("imgCategories", imgCategories);
			
			Map<String, Object> queryMap = new HashMap<String,Object>();
			queryMap.put("loanId", loanId);
			queryMap.put("bizKey", bizKey);
			queryMap.put("sceneType", sec);
			queryMap.put("category", category);
			queryMap.put("state", "1");
			
			// 图片数量
			queryMap.put("exts", new String[]{Files.EXT_IMG});
			int imgCount = this.fileService.queryCount(queryMap);
			map.put("imgCount", imgCount);
			
			
			queryMap.put("exts", new String[]{Files.EXT_DOC,Files.EXT_FILE});
			// 文件数量
			int fileCount = this.fileService.queryCount(queryMap);
			map.put("fileCount", fileCount);
			
			// 文件列表
			PageModel pm = new PageModel();
			pm.init(request, null, queryMap);
			queryMap.put("curPage", pm.getCurPage());
			queryMap.put("pageSize", pm.getPageSize());
			
			PageList<Files> files = this.fileService.queryPageList(queryMap);
			pm.setData(files);
			pm.initRowsCount(files.getPaginator().getTotalCount());
			map.put("pm", pm);
			
			map.put("loanId", loanId);
			map.put("bizKey", bizKey);
			map.put("sec", sec);
			map.put("category", category);
		}
		log.info(thisMethodName+":end");
		
		// 申请影像查看页面
		if ("filesce1".equals(sec) || "filesce2".equals(sec)) {
			return "files/read/loanFileRead";
		}
		
		// 合同影像查看页面
		if ("filesce9".equals(sec)) {
			return "files/read/contractFileRead";
		}
		
		// 打款凭证影像查看页面
		if ("filesce12".equals(sec)) {
			return "files/read/certFileRead";
		}
		
		// 法律意见影像查看页面
		if ("filesce13".equals(sec)) {
			return "files/read/lawAdviceFileRead";
		}
		return "";
	}
	
	/**
	 * 直接post传递，不支持断点方式
	 * 
	 * APP上传接口
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param loanId
	 * @param secId
	 * @param biz
	 * @param catId
	 * @throws Exception
	 */
	@RequestMapping("/imgupload.do")
	public void imgupload(HttpServletRequest request,HttpServletResponse response, ModelMap map,String loanId,String secId,String biz,String catId) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		
		System.out.println("loanId-------->"+loanId);
		System.out.println("biz----------->"+biz);
		System.out.println("secId--------->"+secId);
		System.out.println("catId--------->"+catId);
		
		loanId = URLDecoder.decode(loanId, "UTF-8");
		secId = URLDecoder.decode(secId, "UTF-8");
		biz = URLDecoder.decode(biz, "UTF-8");
		catId = URLDecoder.decode(catId, "UTF-8");
		
		// ajaxSubmit 异步提交 必须设置返回类型  是 text
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
	
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(15943040);
//		InputStream inStream = null;
		try {
//			List fileItems = upload.parseRequest(request);
			 List<FileItem> list = upload.parseRequest(request);
		  for(FileItem item : list){
              //如果fileitem中封装的是普通输入项的数据
              if(item.isFormField()){
                  String name = item.getFieldName();
                  //解决普通输入项的数据的中文乱码问题
                  String value = item.getString("UTF-8");
                  //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                  System.out.println(name + "=" + value);
              }else{//如果fileitem中封装的是上传文件
                  //得到上传的文件名称，
                  String filename = item.getName();
                  System.out.println(filename);
                  if(filename==null || filename.trim().equals("")){
                      continue;
                  }
                  //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                  //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                  filename = filename.substring(filename.lastIndexOf("\\")+1);
              
        		//扩展名分类
        		String strImg=".jpg_.png_.jpeg_.gif";
        		String strDoc=".docx_.doc_.xlsx_.xls_.ppt_.pptx_.pdf";
        		String extState="file";
        		String strExt=filename.substring(filename.lastIndexOf(".")).toLowerCase();
        		if (strImg.contains(strExt)) {
        			extState="img";
        		}
        		if (strDoc.contains(strExt)) {
        			extState="doc";
        		}
        		String name = filename.replaceAll("/", Matcher.quoteReplacement(File.separator));
                String filePath=Configurations.getFileRepository() + File.separator+DateUtils.getYear(DateUtils.getDateNow()) +File.separator +DateUtils.getMonth(DateUtils.getDateNow())+File.separator + loanId+File.separator+extState+File.separator+ name;
//                File dst = SreamIOUtil.getFile(name,loanId);
//				dst.delete();
                //获取item中的上传文件的输入流
                InputStream in = item.getInputStream();
                
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
    	        byte[] buff = new byte[FileConstant.BUFFER_LENGTH];  
    	        int rc = 0;  
    	        while ((rc = in.read(buff, 0, FileConstant.BUFFER_LENGTH)) > 0) {  
    	            swapStream.write(buff, 0, rc);  
    	        }  
    	        byte[] in2b = swapStream.toByteArray();  
    	        
    
    			//上传到文件服务器
    			
        		StorePath storePath = storageClientService.uploadFile("",new ByteArrayInputStream(in2b), in2b.length, strExt);
        		SreamIOUtil.close(swapStream);
        		SreamIOUtil.close(in);
        		
                
                  //删除处理文件上传时生成的临时文件
                  item.delete();

  				//添加新的记录
  				Files files=new Files();
				files.setBizKey(biz);
				files.setLoanId(loanId);
				files.setSceneType(secId);
				files.setFileName(name);
			    files.setGroupName(storePath.getGroup());
				files.setFilePath(storePath.getPath());
				files.setExt(extState);
				files.setCategory(catId);
				files.setOperator(loginId);
				files.setState("1");
				files.setCreateTime(new Timestamp(System.currentTimeMillis()));
				fileService.add(files);
              }
		  } 
			writer.print(JsonUtil.object2json(new JsonMsg(true, "上传完成。")));
			
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
	
	/**
	 * App获取图片分类
	 * 
	 * @param loanId
	 * @param biz
	 * @param secId
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/getCategories.do")
	public void getCat(String loanId, String biz, String secId, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");

		loanId = URLDecoder.decode(loanId, "UTF-8");
		secId = URLDecoder.decode(secId, "UTF-8");
		biz = URLDecoder.decode(biz, "UTF-8");
		
		PrintWriter writer = response.getWriter();
		response.setContentType("application/json;charset=UTF-8");
		if (StringUtils.isNotNullAndEmpty(loanId) && StringUtils.isNotNullAndEmpty(biz)) {
			List<Files> imgCategories = this.fileService.queryCategoryCount(loanId, biz, secId, Files.EXT_IMG);
			if (imgCategories != null && imgCategories.size() > 0) {
				List<Files> files = new ArrayList<Files>();
				for (Files f : imgCategories) {
					Files tmpFile = new Files();
					tmpFile.setCategory(f.getCategory());
					tmpFile.setCategoryCount(f.getCategoryCount());
					files.add(tmpFile);
				}
				writer.print(GsonUtils.getInstance().toJson(files, true));
			} else {
				writer.print("[]");
			}
		} else {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "loanId或biz不能为空")));
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName + ":end");
	}

	/**
	 * App获取图片地址接口
	 * 
	 * @param loanId
	 * @param biz
	 * @param secId
	 * @param catId
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/getUrl.do") 
	public void getUrl(String loanId,String biz,String secId,String catId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		loanId = URLDecoder.decode(loanId, "UTF-8");
		secId = URLDecoder.decode(secId, "UTF-8");
		biz = URLDecoder.decode(biz, "UTF-8");
		catId = URLDecoder.decode(catId, "UTF-8");
		
		PrintWriter writer = response.getWriter();
		response.setContentType("application/json;charset=UTF-8");
		if (StringUtils.isNotNullAndEmpty(loanId) 
				&& StringUtils.isNotNullAndEmpty(biz)) {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("loanId", loanId);
			queryMap.put("bizKey", biz);
			queryMap.put("sceneType", secId);
			queryMap.put("category", catId);
			queryMap.put("ext", Files.EXT_IMG);
			queryMap.put("state", "1");
			List<Files> list = this.fileService.queryList(queryMap);
			if (list != null && list.size() > 0) {
				List<Files> files = new ArrayList<Files>();
				for (Files f : list) {
					Files tmpFile = new Files();
					tmpFile.setFileName(f.getFileName());
					tmpFile.setFilePath(f.getFilePath());
					files.add(tmpFile);
				}
				writer.print(GsonUtils.getInstance().toJson(files, true));
			} else {
				writer.print("[]");
			}
		} else {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "loanId或biz不能为空")));
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
}
