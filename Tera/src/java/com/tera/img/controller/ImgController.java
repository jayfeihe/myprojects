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

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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

import com.tera.img.model.Img;
import com.tera.img.service.ImgService;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Parameter;
import com.tera.sys.service.DataDictionaryService;
import com.tera.sys.service.ParameterService;
import com.tera.util.IOUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * <br>
 * <b>功能：</b>ImgController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-13 12:58:14<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/img")
public class ImgController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(ImgController.class);
	
	/**
	 * ImgService
	 */
	@Autowired(required=false) //自动注入
	private ImgService imgService;
	@Autowired(required=false) //自动注入
	private ParameterService<Parameter> parameterService;
	@Autowired(required=false) //自动注入
	private DataDictionaryService<DataDictionary> dataDictionaryService;


	/**
	 * 跳转到影像表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String imgQuery(HttpServletRequest request, ModelMap map) throws Exception {
		return "img/imgQuery";
	}

	/**
	 * 显示影像表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String imgList(HttpServletRequest request, ModelMap map) throws Exception {
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Img.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.imgService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Img> imgList = this.imgService.queryList(queryMap);
		pm.setData(imgList);
		map.put("pm", pm);
		return "img/imgList";
	}

	/**
	 * 跳转到更新影像表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String imgUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		Img bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.imgService.queryByKey(id);
		}
		map.put("bean", bean);
		return "img/imgUpdate";
	}

	/**
	 * 保存影像表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void imgSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			Img bean = (Img) RequestUtils.getRequestBean(Img.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.imgService.updateOnlyChanged(bean);
			} else { //如果不存在
				this.imgService.add(bean);
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
	}

	/**
	 * 删除影像表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void imgDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.imgService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "关联数据，不能删除！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
	}
	
	/**
	 * 跳转到查看影像表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String imgRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		Img bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.imgService.queryByKey(id);
		}
		map.put("bean", bean);
		return "img/imgRead";
	}
	
	@RequestMapping("/imgSlide.do")
	public void imgSlide(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Object bean = RequestUtils.getRequestBean(Img.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		List<Img> imgList =this.imgService.queryList(queryMap);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(imgList));
		writer.flush();
		writer.close();
	}
	@RequestMapping("/imgSlidePath.do")
	public String imgSlidePath(String appId,HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception {
		map.put("appId", appId);
		if(appId!=null&&!appId.equals("")){
			Map<String, Object> queryMap =new HashMap<String, Object>();
			queryMap.put("appId", appId);
			List<Img> imgList =this.imgService.queryList(queryMap);
			if(imgList!=null && imgList.size()>0){
//				List<String> imgCategoryList=new ArrayList<String>();
				Map<String, Img> imgCategoryMap = new HashMap<String, Img>();
				Map<String, Object> dataMap =new HashMap<String, Object>();
				dataMap.put("keyName", "imgcategory");
//				dataMap.put("keyProp", img.getCategory());
				List<DataDictionary> dataDictionaryList = dataDictionaryService.queryList(dataMap);
				for (Img img : imgList) {
					//张悦2014-10-17修改
					img.setCategoryName(img.getCategory());
					imgCategoryMap.put(img.getCategory(), img);
					for (DataDictionary dataDictionary : dataDictionaryList) {
						if (dataDictionary.getKeyProp().equalsIgnoreCase(img.getCategory())) {
							img.setCategoryName(img.getCategory() + "(" +dataDictionary.getKeyValue()+ ")");
							imgCategoryMap.put(img.getCategory(), img);
						}
					}
					//以前的调用
//					String typ=img.getCategory();
//					if(!imgCategoryList.contains(typ)){
//						dataMap.put("keyProp", typ);
//						List<DataDictionary> dataDictionaryList = dataDictionaryService.queryList(dataMap);
//						if(dataDictionaryList.size()>0){
//							imgCategoryList.add(typ + "(" +dataDictionaryList.get(0).getKeyValue()+ ")");
//						}else{
//							imgCategoryList.add(typ);
//						}
//					}
				}
//				Collections.sort(imgCategoryList)
				List<String> imgCategoryKeyList = new ArrayList<String>();
				imgCategoryKeyList.addAll(imgCategoryMap.keySet()); //Map排序
				Collections.sort(imgCategoryKeyList);
				
				List<Img> resultList = new ArrayList<Img>();
				int imgTotalCount = 0;
				List<Img> categoryImags = this.imgService.getCategoryCount(appId);
				for (String key : imgCategoryKeyList) {
					Img img = imgCategoryMap.get(key);
					for (Img temImg : categoryImags) {
						if (temImg.getCategory().equals(key)) {
							img.setCategoryCount(temImg.getCategoryCount());
							imgTotalCount = imgTotalCount + temImg.getCategoryCount();
						} else {
							continue;
						}
					}
					resultList.add(img);
				}
				map.put("totalCount", imgTotalCount);
				map.put("imgCategoryList", resultList);
			}
		}
		
		return "img/imgSlide";
	}

	@RequestMapping("/imgReadYun.do")
	public void imgReadYun(String imgurl,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OutputStream out=response.getOutputStream();
		out.write(imgService.aliyunOSSGetBytes(imgurl));
		out.flush();
		out.close();
	}

	/**
	 * 本地测试用 读取 读取图片
	 * @param imgurl
	 * @param request
	 * @param response
	 * @throws Exception
	 */
//	@RequestMapping("/imgReadYun.do")
	public void imgReadYun_test(String imgurl,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OutputStream out=response.getOutputStream();
		String dirUrl=parameterService.queryByParamName("imgroot").getParamValue();
		FileInputStream fst=new FileInputStream(dirUrl+imgurl);
		out.write(IOUtils.getBytes(fst));
		out.flush();
		out.close();
	}

}
