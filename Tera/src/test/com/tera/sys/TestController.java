/*
 *  Copyright 2014, TERATECH Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERATECH Co.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERATECH Co., LTD
 *
 */
package com.tera.sys;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.util.IOUtils;

/**
 * 测试页面Controller
 * @author Administrator
 *
 */
@Controller
public class TestController {
	/**
	 *  xmlInput
	 */
	private String xmlInput;

	/**
	 * encoding
	 */
	private final String encoding = "UTF-8";


	/**
	 * 将上传文件转换为字符串
	 * @param request request
	 * @param map map
	 * @return	跳转页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/dto2xom.do")
	public String dto2xom(HttpServletRequest request, ModelMap map) {

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(4194304);
		InputStream inStream = null;

		try {
			List fileItems = upload.parseRequest(request);
			FileItem item = (FileItem) fileItems.get(0);
			inStream = item.getInputStream();
			xmlInput = IOUtils.read(inStream, encoding);
			inStream.close();
			// XML为空
			if ("".equals(xmlInput)) {
				throw new Exception("Dto2XomController---XML错误");
			}
//			DtoXmlParser parser = new DtoXmlParser(xmlInput);
//			String proposalStr = parser.getMapXom().toString().replaceAll("\n", "<br>");
//			map.put("result", proposalStr);
			return "mapresult";
		} catch (Exception e) {
			e.printStackTrace();
			map.put("exception", e);
			return "error";
		}
	}
	
	/**
	 * 请求处理
	 * @param request 请求
	 * @param map 页面数据Map
	 * @return 返回页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/dtotest.do")
	public String dtotest(HttpServletRequest request, ModelMap map) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(4194304);
		InputStream inStream = null;
		try {
			List fileItems = upload.parseRequest(request);
			FileItem item = (FileItem) fileItems.get(0);
			inStream = item.getInputStream();
			xmlInput = IOUtils.read(inStream, encoding);
			inStream.close();
			// XML为空
			if ("".equals(xmlInput)) {
				throw new Exception("DtoXmlController--XML错误");
			}
			//调用核保服务
//			DtoXmlParser parser = new DtoXmlParser(xmlInput);
//			MapXom mapxom = parser.getMapXom();
//			ExecutionService insurance = new ExecutionServiceImpl();			
//			ResultXom result = insurance.insure(mapxom);
//			map.put("result", result);
			return "xomresult";

		} catch (Exception e) {
			e.printStackTrace();
			map.put("exception", e);
			return "error";
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

}
