package com.tera.cooperation.dinxuan.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.oss.model.OSSObject;
import com.tera.contract.model.Contract;
import com.tera.cooperation.dinxuan.constants.DXConstants;
import com.tera.cooperation.jmbox.service.JmboxService;
import com.tera.img.model.Img;
import com.tera.img.service.ImgService;
import com.tera.util.IOUtils;

/**
 * 鼎轩 对接 相关服务
 * 
 */
@Service("dxJointService")
public class DxJointService {

	private final static Logger log = Logger.getLogger(DxJointService.class);
	
	@Autowired(required = false)// 自动注入
	private ImgService imgService;

	/**
	 * 根据合同下载 鼎轩所需要的 产品
	 * 
	 * @param contract
	 * @return
	 * @throws Exception
	 */
	public byte[] downloadImage(Contract contract) throws Exception {

		String[] incomeClassTypes = null;
		String loanProduct = contract.getLoanProduct();
		if(loanProduct.indexOf("DX") != -1){
		//鼎轩产品
			if(loanProduct.indexOf("工薪贷") != -1) {
				incomeClassTypes = DXConstants.IMAGE_CLASS_PAYROLL;
			}else if(loanProduct.indexOf("精英贷") != -1) {
				incomeClassTypes = DXConstants.IMAGE_CLASS_ELITE;
			}
		}else if(loanProduct.indexOf("RY") != -1){
		//中海软银产品
			if(loanProduct.indexOf("工薪贷") != -1) {
				incomeClassTypes = DXConstants.RY_IMAGE_CLASS_PAYROLL;
			}else if(loanProduct.indexOf("精英贷") != -1) {
				incomeClassTypes = DXConstants.RY_IMAGE_CLASS_ELITE;
			}
		}
		
		byte[] zipOutputStream = null;

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("appId", contract.getLoanAppId());
		// 查询图片
		List<Img> imgList = imgService.queryList(queryMap);
		if (imgList != null && imgList.size() > 0) {
			ByteArrayOutputStream returnOut = new ByteArrayOutputStream();
			ZipOutputStream zipOut = new ZipOutputStream(returnOut);
			zipOut.setEncoding("GBK");

			for (String classType : incomeClassTypes) {
				String[] types = classType.split("_");
				boolean isif = false;
				for (String type : types) { // 第一个类型不存在的时候 才去取 第二个类型
					for (Img img : imgList) {
						if (img.getFileName().startsWith(type)) {
							// 读取 阿里云 放入 zip输出流
							OSSObject ossObj=null;
							byte[] imgByte;
							try {
								ossObj = imgService.aliyunOSSGetOSSObject(img.getImgPath());
								imgByte= IOUtils.getBytes(ossObj.getObjectContent());
							} catch (Exception e) {
								log.error("影像下载 异常（ContractNo:"+contract.getContractNo()+"；imgUrl）"+img.getImgPath()+"：", e);
								continue;
							}
							
							ZipEntry imgEntry = new ZipEntry(img.getFileName());
							imgEntry.setSize(ossObj.getObjectMetadata().getContentLength());
							zipOut.putNextEntry(imgEntry);
							zipOut.write(imgByte);
							isif = true;
						}
					}
					if (isif) {
						break;
					}
				}
			}
			zipOut.closeEntry();
			zipOut.close();
			zipOutputStream=returnOut.toByteArray();
		}
		log.info("影像下载 完成（ContractNo:"+contract.getContractNo()+")");
		return zipOutputStream;
	}

}
