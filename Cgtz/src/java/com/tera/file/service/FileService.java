package com.tera.file.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import third.rewrite.fastdfs.StorePath;
import third.rewrite.fastdfs.service.IStorageClientService;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.file.config.Configurations;
import com.tera.file.config.FileConstant;
import com.tera.file.dao.FilesDao;
import com.tera.file.model.Files;
import com.tera.file.model.Range;
import com.tera.file.util.SreamIOUtil;
import com.tera.file.util.TokenUtil;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.service.MybatisBaseService;
import com.tera.util.DateUtils;

/**
 * 
 * T_FILE服务类
 * <b>功能：</b>FileDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 10:28:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("fileService")
public class FileService extends MybatisBaseService<Files> {

	private final static Logger log = Logger.getLogger(FileService.class);

	@Autowired(required=false)
    private FilesDao dao;
	
	@Autowired
	private IStorageClientService storageClientService;

	@Transactional
	public void add(Files... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(Files obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(Files obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(Files obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<Files> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public Files queryByKey(Object id) throws Exception {
		return (Files)dao.queryByKey(id);
	}
	
	public List<Files> queryCategoryCount(Map<String, Object> map) {
		return dao.queryCategoryCount(map);
	}
	
	public List<Files> queryCategoryCount(String loanId,String bizKey,String sec,String ext) {
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("loanId", loanId);
		queryMap.put("bizKey", bizKey);
		queryMap.put("sceneType", sec);
		queryMap.put("ext", ext);
		List<Files> list = dao.queryCategoryCount(queryMap);
		return list;
	}
	
	public PageList<Files> queryPageList(Map<String, Object> params) {
		return this.selectPageList(FilesDao.class, "queryList", params);
	}
	
	public void FormDataService(HttpServletRequest req, HttpServletResponse resp,String loanId) throws IOException {

		/** flash @ windows bug */
		req.setCharacterEncoding("utf8");
		
		final PrintWriter writer = resp.getWriter();
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (!isMultipart) {
			writer.println("ERROR: It's not Multipart form.");
			return;
		}
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";

		ServletFileUpload upload = new ServletFileUpload();
		InputStream in = null;
		String token = null;
		try {
			FileItemIterator iter = upload.getItemIterator(req);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				in = item.openStream();
				if (item.isFormField()) {
					String value = Streams.asString(in);
					if (FileConstant.TOKEN_FIELD.equals(name)) {
						token = value;
						/** TODO: validate your token. */
					}
					System.out.println(name + ":" + value);
				} else {
					String fileName = item.getName();
					start = SreamIOUtil.streaming(in, token, fileName,loanId);
				}
			}
		} catch (FileUploadException fne) {
			success = false;
			message = "Error: " + fne.getLocalizedMessage();
		} finally {
			try {
				if (success)
					json.put(FileConstant.START_FIELD, start);
				json.put(FileConstant.SUCCESS, success);
				json.put(FileConstant.MESSAGE, message);
			} catch (JSONException e) {}
			
			writer.write(json.toString());
			SreamIOUtil.close(in);
			SreamIOUtil.close(writer);
		}
	}
	
	
	public void tokenService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String name = req.getParameter(FileConstant.FILE_NAME_FIELD);
		String size = req.getParameter(FileConstant.FILE_SIZE_FIELD);
		String token = TokenUtil.generateToken(name, size);
		
		PrintWriter writer = resp.getWriter();
		
		JSONObject json = new JSONObject();
		try {
			json.put(FileConstant.TOKEN_FIELD, token);
			if (Configurations.isCrossed())
				json.put(FileConstant.SERVER_FIELD, Configurations.getCrossServer());
			json.put(FileConstant.SUCCESS, true);
			json.put(FileConstant.MESSAGE, "");
		} catch (JSONException e) {
		}
		/** TODO: save the token. */
		
		writer.write(json.toString());
	}
	/**
	 * 上传文件
	 * @param req
	 * @param resp
	 * @throws Exception 
	 */
	@Transactional
	public void streamServicePost(HttpServletRequest req, HttpServletResponse resp,String loanId,String secId,String biz,String catId) throws Exception {
		doOptions(req, resp);
		final String token = req.getParameter(FileConstant.TOKEN_FIELD);
		final String fileName = req.getParameter(FileConstant.FILE_NAME_FIELD);
		Range range = SreamIOUtil.parseRange(req);
		
		OutputStream out = null;
		InputStream content = null;
		final PrintWriter writer = resp.getWriter();
		
		
		//针对推送线上做特殊处理，只能保留一份文件
		if("filesce10".equals(secId)){
			Map<String, Object> queryMap = new HashMap<String,Object>();
			queryMap.put("loanId", loanId);
			
			queryMap.put("sceneType", secId);
			List<Files> exists = this.queryList(queryMap);
			for (Files files : exists) {
				storageClientService.deleteFile(files.getGroupName(), files.getFilePath());
				dao.delete(files.getId());
			}
			
		}
		
		
		/** TODO: validate your token. */
		
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		File f = SreamIOUtil.getTokenedFile(token);
		try {
			if (f.length() != range.getFrom()) {
				/** drop this uploaded data */
				throw new StreamException(StreamException.ERROR_FILE_RANGE_START);
			}
			
			out = new FileOutputStream(f, true);
			content = req.getInputStream();
//			int read = 0;
	//		final byte[] bytes = new byte[FileConstant.BUFFER_LENGTH];
//			while ((read = content.read(bytes)) != -1)
//				out.write(bytes, 0, read);
			//无断点续传，一次成功
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
	        byte[] buff = new byte[FileConstant.BUFFER_LENGTH];  
	        int rc = 0;  
	        while ((rc = content.read(buff, 0, FileConstant.BUFFER_LENGTH)) > 0) {  
	            swapStream.write(buff, 0, rc);  
	        }  
	        byte[] in2b = swapStream.toByteArray();  
	        //long straaString =in2b.length;

	        String strExt=fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
			//上传到文件服务器
			
    		StorePath storePath = storageClientService.uploadFile("",new ByteArrayInputStream(in2b), in2b.length, strExt);
			//start = f.length();
			//扩展名分类
			String strImg=".jpg_.png_.jpeg_.gif";
			String strDoc=".docx_.doc_.xlsx_.xls_.ppt_.pptx_.pdf";
			String extState="file";
		
			if (strImg.contains(strExt)) {
				extState="img";
			}
			if (strDoc.contains(strExt)) {
				extState="doc";
			}
			
//			File dst = SreamIOUtil.getFile(fileName,loanId);
			f.delete();
//			f.renameTo(dst);
			System.out.println("TK: `" + token + "`, NE: `" + fileName + "`");
			//文件上传成功
			String name = fileName.replaceAll("/", Matcher.quoteReplacement(File.separator));
			//String strPath=File.separator+DateUtils.getYear(DateUtils.getDateNow()) +File.separator +DateUtils.getMonth(DateUtils.getDateNow())+File.separator + loanId+File.separator+extState+File.separator+ name;
			String loginId = (String) req.getSession().getAttribute(SysConstants.LOGIN_ID);
		
			
			Files files=new Files();
			files.setBizKey(biz);
			files.setLoanId(loanId);
			files.setSceneType(secId);
			files.setFileName(name);
		    files.setGroupName(storePath.getGroup());//组
			files.setFilePath(storePath.getPath()); //文件地址
			files.setExt(extState);
			files.setCategory(catId);
			files.setOperator(loginId);
			files.setState("1");
			files.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dao.add(files);
			
			start = range.getTo();
			SreamIOUtil.close(swapStream);
			
		} catch (StreamException se) {
			success = StreamException.ERROR_FILE_RANGE_START == se.getCode();
			message = "Code: " + se.getCode();
		} catch (FileNotFoundException fne) {
			message = "Code: " + StreamException.ERROR_FILE_NOT_EXIST;
			success = false;
		} catch (IOException io) {
			message = "IO Error: " + io.getMessage();
			success = false;
		} finally {
			
			
			SreamIOUtil.close(out);
			SreamIOUtil.close(content);
			
			try {
				if (success)
					json.put(FileConstant.START_FIELD, start);
				json.put(FileConstant.SUCCESS, success);
				json.put(FileConstant.MESSAGE, message);
			} catch (JSONException e) {}
			
			writer.write(json.toString());
			SreamIOUtil.close(writer);
		}
	}
//	
	
	public void streamServiceGet(HttpServletRequest req, HttpServletResponse resp,String loanId,String secId,String biz,String catId) throws IOException {
		doOptions(req, resp);
		final String token = req.getParameter(FileConstant.TOKEN_FIELD);
		final String size = req.getParameter(FileConstant.FILE_SIZE_FIELD);
		final String fileName = req.getParameter(FileConstant.FILE_NAME_FIELD);
		final PrintWriter writer = resp.getWriter();
		
		/** TODO: validate your token. */
		
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		try {
			File f = SreamIOUtil.getTokenedFile(token);
			start = f.length();
			/** file size is 0 bytes. */
			if (token.endsWith("_0") && "0".equals(size) && 0 == start)
				f.renameTo(SreamIOUtil.getFile(fileName,loanId));
		} catch (FileNotFoundException fne) {
			message = "Error: " + fne.getMessage();
			success = false;
		} finally {
			try {
				if (success)
					json.put(FileConstant.START_FIELD, start);
				json.put(FileConstant.SUCCESS, success);
				json.put(FileConstant.MESSAGE, message);
			} catch (JSONException e) {}
			writer.write(json.toString());
			SreamIOUtil.close(writer);
		}
	}
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
	 {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		resp.setHeader("Access-Control-Allow-Origin", Configurations.getCrossOrigins());
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	 }
	
	public void deleteByMap(Map<String, Object> map){
		 dao.deleteByMap(map);
	}
}
