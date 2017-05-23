package com.sunseetech.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.OSSObject;
import com.sunseetech.dao.ImgDao;
import com.sunseetech.model.Img;
import com.sunseetech.utils.IOUtils;

@Service
public class ImgService {

	@Autowired
	private ImgDao imgDao;
	
	private final String access_id = "wEFt6P4YIisMjRiw";
	private final String access_key = "Jh2Nqbse2Q1yRLMuDojzpUrQp0Lz4P";
	private final String bucket = "onlinehdoss";
	private final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

	public List<Img> queryByAppIdAndCategory(String[] appIds, String category) {
		return imgDao.queryByAppIdAndCategory(appIds, category);
	}
	
	public InputStream aliyunOSSGetInputStream(String fileUrl) throws OSSException, ClientException, IOException{
		OSSClient ossClient = new OSSClient(endpoint,access_id,access_key);
		OSSObject ossObj=ossClient.getObject(new GetObjectRequest(bucket, fileUrl));
		return ossObj.getObjectContent();
	}
	
	public byte[] aliyunOSSGetBytes(String fileUrl) throws OSSException, ClientException, IOException{
		OSSClient ossClient = new OSSClient(endpoint,access_id,access_key);
		OSSObject ossObj=ossClient.getObject(new GetObjectRequest(bucket, fileUrl));
		return IOUtils.getBytes(ossObj.getObjectContent());
	}
}
