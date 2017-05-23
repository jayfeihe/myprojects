package com.sunseetech.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSException;
import com.sunseetech.model.Img;
import com.sunseetech.service.ImgService;

@Controller
@RequestMapping("/img")
public class ImgContoller {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final String img_path = "/home/imgs/";

	@Autowired
	private ImgService imgService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String download() {
		return "oss";
	}
	
	@RequestMapping(value = "/extract", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String extract(String[] appIds, String category) {
		try {
			List<Img> imgs = this.imgService.queryByAppIdAndCategory(appIds, category);

			if (imgs != null && imgs.size() > 0) {
				List<File> files = new ArrayList<File>();
				for (Img img : imgs) {
					byte[] bytes = this.imgService.aliyunOSSGetBytes(img.getImgPath());
//					byte[] bytes = FileUtils.readFileToByteArray(new File("F:\\imgs\\"+img.getImgPath()));
					File file = new File(img_path + img.getImgPath());
					FileUtils.writeByteArrayToFile(file, bytes);
					files.add(file);
				}
				//ZipUtil.compressFiles(files, new File(img_path + category + ".zip"));
				return "{\"message\":\"提取成功\"}";
			} else {
				return "{\"message\":\"没有查到相关数据\"}";
			}
		} catch (OSSException e) {
			logger.error("阿里云OSS异常" + e.getMessage(), e);
			return "{\"message\":\"提取失败，阿里云OSS异常\"}";
		} catch (ClientException e) {
			logger.error("阿里云OSS异常" + e.getMessage(), e);
			return "{\"message\":\"提取失败，连接阿里云OSS失败\"}";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{\"message\":\"提取失败\"}";
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String download(String category, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (category == null || category == "") {
			return "{\"message\":\"类别不能为空\"}";
		}
		
		String path = img_path + category + ".zip";

		File file = new File(path);
		if (!file.exists()) {
			return "{\"message\":\"文件"+category+".zip不存在\"}";
		}
		
		InputStream fis = null;
		OutputStream output = null;
		try {
			response.reset();

			response.setContentType("application/x-download");
			response.addHeader("content-type ", "application/x-msdownload");
			response.setContentType("application/octet-stream");
			response.setHeader("content-disposition",
					"attachment; filename=" + transCharacter(request, category+".zip"));

			fis = new BufferedInputStream(new FileInputStream(path));

			output = response.getOutputStream();
			byte[] buffer = new byte[10240];
			int n = 0;
			while ((n = fis.read(buffer)) != -1) {
				output.write(buffer, 0, n);
			}
			output.flush();
		} catch (Exception ex) {
			logger.info("下载出错",ex);
		} finally {
			if (fis != null)
				fis.close();
			if (output != null)
				output.close();
		}
		return "";
	}

	private String transCharacter(HttpServletRequest request, String str) throws Exception {
		if (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0)
			return URLEncoder.encode(str, "UTF-8");
		if (request.getHeader("USER-AGENT").toLowerCase().indexOf("firefox") > 0) {
			return new String(str.getBytes("UTF-8"), "ISO8859-1");
		}
		return new String(str.getBytes("UTF-8"), "ISO8859-1");
	}
}
