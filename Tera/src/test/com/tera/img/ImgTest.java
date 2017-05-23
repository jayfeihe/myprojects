/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.img;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aliyun.openservices.oss.model.OSSObject;
import com.tera.cooperation.jmbox.constant.JMLendingConstant;
import com.tera.credit.model.CreditApp;
import com.tera.credit.service.CreditAppService;
import com.tera.img.model.Img;
import com.tera.img.service.ImgService;
import com.tera.util.IOUtils;

/**
 * @author Wallace chu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoTestContext.xml"
				}
		)
public class ImgTest extends AbstractTransactionalJUnit4SpringContextTests {


	@Autowired(required=false) //自动注入
	private CreditAppService creditAppService;
	@Autowired(required=false) //自动注入
	private ImgService imgService;

	@Test
	public void getImgByAppId() throws Exception {
		String appId = "C860100020001201501190008";
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("appId", appId);
		List<Img> imgList = imgService.queryList(queryMap);
		if (imgList != null && imgList.size() > 0) {
			for (Img img : imgList) {
				OSSObject ossObj = imgService.aliyunOSSGetOSSObject(img.getImgPath());
				IOUtils.write(new File("xml/" + appId + "/" + img.getFileName()), ossObj.getObjectContent());
			}
		}
	}

	@Test
	public void getSizeTest() throws Exception {
		
		String[] classTypes=JMLendingConstant.CLASS_TYPES;
		Map<String, Object> queryMap =new HashMap<String, Object>();
		queryMap.put("bpmStates", new String[]{"签约","复核","结束"});
		
		List<CreditApp> creditAppList = this.creditAppService.queryBpmLoanAppList(queryMap);
		for (CreditApp creditApp : creditAppList) {
			String appId=creditApp.getAppId();
			long imgsize=0;
			queryMap.put("appId", appId);
			List<Img> imgList = imgService.queryList(queryMap);
			if(imgList!=null && imgList.size()>0){
				for (String classType : classTypes) {
					String[] types=classType.split("_");
					boolean isif=false;
					for (String type : types) {  //第一个类型不存在的时候 才去取 第二个类型
						for (Img img : imgList) {	
							if(img.getFileName().startsWith(type)){
								OSSObject ossObj=imgService.aliyunOSSGetOSSObject(img.getImgPath());
								imgsize+=ossObj.getObjectMetadata().getContentLength();
								isif=true;
							}
						}
						if(isif){
							break;
						}
					}
				}
				System.out.println("appId:"+appId+":"+imgsize/1024/1024);
			}
		}
		
		
	}

}
