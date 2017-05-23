package com.tera.img.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.img.dao.ImgDao;
import com.tera.img.model.Img;
import com.tera.loan.model.LoanApp;
import com.tera.loan.service.LoanAppService;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.Parameter;
import com.tera.sys.service.DataDictionaryService;
import com.tera.sys.service.ParameterService;
import com.tera.util.IOUtils;

/**
 * 
 * <br>
 * <b>功能：</b>ImgDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-13 12:58:14<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("imgService")
public class ImgService {

	private final static Logger log = Logger.getLogger(ImgService.class);

	@Autowired(required=false)
    private ImgDao dao;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
//	@Autowired(required=false) //自动注入
//	private LoanAppService<LoanApp> loanAppService;
	@Autowired(required=false) //自动注入
	private ParameterService<Parameter> parameterService;
	@Autowired(required=false) //自动注入
	private DataDictionaryService<DataDictionary> dataDictionaryService;
	
	@Transactional
	public void add(Img... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(Img t : ts ){
			dao.add(t);
		}
	}
	
	@Transactional
	public void update(Img t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(Img t)  throws Exception {
		dao.updateOnlyChanged(t);
	}
	
	@Transactional
	public void delete(String... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(String id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<Img> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public Img queryByKey(String id) throws Exception {
		return dao.queryByKey(id);
	}
	
	private List<Img> queryCategoryCount(Map<String, Object> map) {
		return dao.queryCategoryCount(map);
	}
	
	/**
	 * 获取各分类影像数量
	 * @param map
	 * @return
	 */
	public List<Img> getCategoryCount(String appId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("appId", appId);
		return this.queryCategoryCount(map);
	}
	
	public String imgPut(Map<String, String> imgMap,String appId,String contract,String loginid,String orgId){

		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(appId);
		String processState="";
		if(taskList!=null&& taskList.size()>0){
			processState=taskList.get(0).getState();
		}
		
		for (String key : imgMap.keySet()) {
			
			Img img=null;
			// 判断 文件名是否 复核规则
			Map map=new HashMap();
			map.put("fileName", key);
			map.put("appId", appId);
			List<Img> list=dao.queryList(map);
			if(list!=null&&list.size()>0){
				img=list.get(0);
				img.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				img.setOperator(loginid);
				img.setOrgId(orgId);
				img.setContract(contract);
				img.setProcessState(processState);
				dao.update(img);
			}else{
				img=new Img();
				String category=key.substring(0,1);
				String subCategory=key.substring(1,3);
				String imgurl=imgMap.get(key);
				img.setAppId(appId);
				img.setCategory(category);
				img.setSubCategory(subCategory);
				img.setFileName(key);
				img.setImgPath(imgurl);
				img.setContract(contract);
				img.setProcessState(processState);
				img.setOperator(loginid);
				img.setOrgId(orgId);
				long sj=System.currentTimeMillis();
				img.setCreateTime(new Timestamp(sj));
				img.setUpdateTime(new Timestamp(sj));
				dao.add(img);
			}
			
		}
		return "";
	}
	/**
	 *   返回值  为  null 的时候 说明没附件   为 "" 的时候说明 满足条件  为  a01|b01 为缺失 的不许分类
	 * @param appId
	 * @param must
	 * @return
	 * @throws Exception 
	 */
	public String imgVerify(String appId,String[] must ) throws Exception{
		String jg="";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", appId);
//		map.put("orderby", appId);
		List<Img> list=dao.queryList(map);
		Map<String, Object> dataMap =new HashMap<String, Object>();
		dataMap.put("keyName", "imgcategory");
		if(list!=null && list.size()>0){
			for (String mu : must) {
				
				String[] bxs = mu.split("_");
				String lsJG="";
				b:for (int y = 0; y < bxs.length; y++) {
					String bx=bxs[y];
					for (int i=0;i<list.size();i++) {
						Img img=list.get(i);
						if(((img.getCategory()+img.getSubCategory()).toUpperCase().equals(bx.toUpperCase()))
								||img.getCategory().toUpperCase().equals(bx.toUpperCase())){
							lsJG="";
							break b;
						}else if(i==list.size()-1){
							dataMap.put("keyProp", bx);
							List<DataDictionary> dataDictionaryList = dataDictionaryService.queryList(dataMap);
							if(dataDictionaryList.size() > 0){
								lsJG += bx + "(" +dataDictionaryList.get(0).getKeyValue()+")|";							
							}else{
								lsJG += bx + "|";
							}
						}
					}
					if(y==bxs.length-1){
						jg+=lsJG;
						lsJG="";
					}
				}
				
			}
		}else{
			jg=null;
		}
		return jg;
	}
	/**
	 *  文件存储
	 * @param size
	 * @param input
	 * @param fileUrl
	 * @return
	 * @throws OSSException
	 * @throws ClientException
	 * @throws IOException
	 */
	public PutObjectResult aliyunOSSPut(long size,InputStream input,String fileUrl) throws OSSException, ClientException, IOException{
		OSSClient client = new OSSClient(this.getEndpoint(), this.getAccessId(),this.getAccessKey());
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(size);
		// 上传Object.
		PutObjectResult result = client.putObject(this.getBucket(), fileUrl, input, meta);
		return result;
	}
	
	
	public byte[] aliyunOSSGetBytes(String fileUrl) throws OSSException, ClientException, IOException{
		OSSClient ossClient = new OSSClient(this.getEndpoint(),this.getAccessId(),this.getAccessKey());
		OSSObject ossObj=ossClient.getObject(new GetObjectRequest(this.getBucket(), fileUrl));
		return IOUtils.getBytes(ossObj.getObjectContent());
	}
	
	public InputStream aliyunOSSGetInputStream(String fileUrl) throws OSSException, ClientException, IOException{
		OSSClient ossClient = new OSSClient(this.getEndpoint(),this.getAccessId(),this.getAccessKey());
		OSSObject ossObj=ossClient.getObject(new GetObjectRequest(this.getBucket(), fileUrl));
		return ossObj.getObjectContent();
	}
	
	public OSSObject aliyunOSSGetOSSObject(String fileUrl) throws OSSException, ClientException, IOException{
		OSSClient ossClient = new OSSClient(this.getEndpoint(),this.getAccessId(),this.getAccessKey());
		OSSObject ossObj=ossClient.getObject(new GetObjectRequest(this.getBucket(), fileUrl));
		return ossObj;
	}
	
	private String bucket;
	private String accessId;
	private String accessKey;	
	private String endpoint;

	public String getBucket() {
		if(bucket==null)
		bucket=parameterService.queryByParamName("oss_bucket").getParamValue();
		return bucket;
	}
	public String getAccessId() {
		if(accessId==null)
		accessId=parameterService.queryByParamName("oss_access_id").getParamValue();
		return accessId;
	}
	public String getAccessKey() {
		if(accessKey==null)
		accessKey=parameterService.queryByParamName("oss_access_key").getParamValue();
		return accessKey;
	}
	
	public String getEndpoint() {
		if(endpoint==null) {
			Parameter param = parameterService.queryByParamName("oss_endpoint");
			if(param !=null) {
				endpoint=param.getParamValue();
			}			
			if(endpoint==null || "".equals(endpoint)) {
				endpoint = "http://oss.aliyuncs.com";
			}
		} 
		return endpoint;
	}
	
	
}
