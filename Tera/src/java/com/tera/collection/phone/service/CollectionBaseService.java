package com.tera.collection.phone.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.judicial.model.CollectionApplication;
import com.tera.collection.judicial.service.CollectionApplicationService;
import com.tera.collection.phone.dao.CollectionBaseDao;
import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.model.CollectionBaseInfo;
import com.tera.collection.phone.model.CollectionDistribution;
import com.tera.collection.phone.model.CollectionRecord;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.constant.Constants;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditContact;
import com.tera.credit.model.CreditContactHistory;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditContactService;
import com.tera.sys.model.Org;
import com.tera.sys.model.User;
import com.tera.sys.service.DataDictionaryService;
import com.tera.sys.service.UserService;
import com.tera.util.RequestUtils;

/**
 * 
 * 催收信息基本表服务类
 * <b>功能：</b>CollectionBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:36:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collectionBaseService")
public class CollectionBaseService {

	private final static Logger log = Logger.getLogger(CollectionBaseService.class);

	@Autowired(required=false)
    private CollectionBaseDao dao;
	@Autowired(required=false) //自动注入
	CollectionApplicationService collectionApplicationService;

	@Autowired(required=false) //自动注入
	private UserService userService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	private CollectionDistributionService distributionService;
	@Autowired(required=false) //自动注入
	private CreditContactService creditContactService;
	@Autowired(required=false) //自动注入
	private CollectionRecordService collectionRecordService;
	@Autowired(required=false) //自动注入
	private ContractService contractService;
	@Autowired(required=false) //自动注入
	private DataDictionaryService dataDictionaryService;
	@Autowired(required=false) //自动注入
	private CreditAppService creditAppService;
	@Transactional
	public void add(CollectionBase... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CollectionBase obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CollectionBase obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CollectionBase obj)  throws Exception {
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
	
	public List<CollectionBase> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CollectionBase queryByKey(Object id) throws Exception {
		return (CollectionBase)dao.queryByKey(id);
	}
	public int queryActionCount(Map<String, Object> map) throws Exception {
		return dao.queryActionCount(map);
	}
	
	public List<CollectionBaseInfo> queryActionList(Map<String, Object> map) throws Exception {
		return dao.queryActionList(map);
	}
	public int queryPartCount(Map<String, Object> map) throws Exception {
		return dao.queryPartCount(map);
	}
	public List<CollectionBaseInfo> queryPartList(Map<String, Object> map) throws Exception {
		return dao.queryPartList(map);
	}
	
	public CollectionBaseInfo queryInfo(Map<String, Object> map) throws Exception {
		return dao.queryInfo(map);
	}
	
	public List<CollectionBase> queryListOrderByAmount(Map<String, Object> map) throws Exception {
		return dao.queryListOrderByAmount(map);
	}
	
	/**
	 * 查询催收渠道不为0的记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CollectionBase> queryListOfWay(Map<String, Object> map) throws Exception {
		return dao.queryListOfWay(map);
	}
	@Transactional
	public void collectionBaseSave(User user,Org org,String submitType,HttpServletRequest request)throws Exception{
		String loginId=user.getLoginId();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		String callTime_str=request.getParameter("orderTime");
//		Date date = format.parse(callTime_str);
		CollectionBase bean = (CollectionBase) RequestUtils.getRequestBean(CollectionBase.class, request);
		
		List<CollectionRecord> recordListBean = RequestUtils.getRequestBeanList("", CollectionRecord.class, request);
		//如果存在
		if (bean.getId() != 0) {
			CollectionBase collectionBaseBean = (CollectionBase) this.queryByKey(bean.getId());
			Timestamp timestamp=new Timestamp(new Date().getTime());
			String dictionaryphoneSummary=request.getParameter("dictionaryphoneSummary");
			collectionBaseBean.setPhoneSummary(dictionaryphoneSummary);
			collectionBaseBean.setState(CollectionConstant.COLLECTION_PHONE);
			collectionBaseBean.setUpdateUid(loginId);
			collectionBaseBean.setUpdateTime(timestamp);
			collectionBaseBean.setSubmitTime(timestamp);
			collectionBaseBean.setFollowTime(timestamp);
			if(!"submit".equals(submitType)){
				collectionBaseBean.setOrderTime(bean.getOrderTime());
			}else{
				collectionBaseBean.setOrderTime(null);
			}
			collectionBaseBean.setAnswerState(bean.getAnswerState());
			collectionBaseBean.setCollectionUidCur(loginId);
			collectionBaseBean.setHandleState("Y"); //处理状态
			this.update(collectionBaseBean);
		} 
		
		//如果存在
//		if(recordListBean!=null&&recordListBean.size()>0){
			String contactname[]=request.getParameterValues("contactname");
			String contactrelation[]=request.getParameterValues("contactrelation");
			String contactmobile[]=request.getParameterValues("contactmobile");
			String creditContacttype[]=request.getParameterValues("creditContacttype");
			//System.out.println(""+contactname.length+""+contactmobile.length+""+creditContacttype.length);
			String appId=request.getParameter("appId");
			Map<String, Object> creditContactMap = new HashMap<String, Object>();
			creditContactMap.put("appId", appId);
			creditContactMap.put("type", CollectionConstant.CONTACT_TYPE_COLLECTION);//3 电催添加
			
			
				if(contactname!=null){
				for(int i=0;i<contactname.length;i++){
					creditContactMap.put("name", contactname[i]);
					if(null!=contactrelation[i]&&!"".equals(contactrelation[i])&&CollectionConstant.CONTACT_RELATION_SELF.equals(contactrelation[i])){
					}
					else if(null!=contactrelation[i]&&!"".equals(contactrelation[i])&&!CollectionConstant.CONTACT_TYPE_COLLECTION.equals(creditContacttype[i]))
					{
						
					}
					else{
					List<CreditContact> creditContactList=this.creditContactService.queryList(creditContactMap);
						if(creditContactList!=null&&creditContactList.size()>0){
							CreditContact creditContact=creditContactList.get(0);
							creditContact.setType(CollectionConstant.CONTACT_TYPE_COLLECTION);					//类型
							creditContact.setAppId(appId);
							creditContact.setName(contactname[i]);
							creditContact.setRelation(contactrelation[i]);
							creditContact.setMobile(contactmobile[i]);
							creditContact.setOperator(loginId);
							creditContact.setOrgId(org.getOrgId());
							creditContact.setUpdateTime(new Timestamp(new Date().getTime()));
//							creditContact.setCreateTime(new Timestamp(new Date().getTime()));
							this.creditContactService.updateOnlyChanged(creditContact);//相关联系人
						}else{
							CreditContact creditContact=new CreditContact();
							creditContact.setType(CollectionConstant.CONTACT_TYPE_COLLECTION);					//类型
							creditContact.setAppId(appId);
							creditContact.setName(contactname[i]);
							creditContact.setRelation(contactrelation[i]);
							creditContact.setMobile(contactmobile[i]);
							creditContact.setOperator(loginId);
							creditContact.setOrgId(org.getOrgId());
							creditContact.setCreateTime(new Timestamp(new Date().getTime()));
							this.creditContactService.add(creditContact);//相关联系人
						}
					}
				}
				
		}
	}
	@Transactional
	public void collectionBaseUpdateDate(Org sessionOrg,User user,String ids,String org_id,String collection_uid_cur)throws Exception{
		String loginId=String.valueOf(user.getLoginId());
		String idstr[]=ids.split(",");
		String contractNo="";
		CollectionDistribution distributionBeanList[]=new CollectionDistribution[idstr.length];
		for(int k=0;k<idstr.length;k++){
			CollectionBase bean = null;
			String id=idstr[k];
			Timestamp timestamp=new Timestamp(new Date().getTime());
			//如果存在
			if (null != id && !"".equals(id)) {
				bean  = queryByKey(id);
			}
			
			contractNo=bean.getContractNo();
//			bean.setOrgId(org_id);
			bean.setCollectionUidCur(collection_uid_cur);
			//bean.setState(CollectionConstant.COLLECTION_BEFORE_PHONE);//电催待催收
			bean.setCollectionWayCur(CollectionConstant.COLLECTION_WAY_CUR_PHONE);
			bean.setHandleState("N");
//			Timestamp updateTime=new Timestamp(new Date().getTime());
//			bean.setUpdateUid(loginId);
//			bean.setUpdateTime(timestamp);
			bean.setFollowTime(null);
			bean.setDistributionState("Y");
			//修改基本表状态
			this.update(bean); 
			
			
			//分配历史数据更改
//			IS_CUR
			Map<String, Object> distributionHistoryMap =  new HashMap<String, Object>();
			distributionHistoryMap.put("contractNo", contractNo);
			List<CollectionDistribution> distributionHistoryLis=this.distributionService.queryList(distributionHistoryMap);
			for(int i=0;i<distributionHistoryLis.size();i++){
				CollectionDistribution distributionHistory=distributionHistoryLis.get(i);
				distributionHistory.setState("1");
				distributionHistory.setIsCur("N");
//				distributionHistory.setUpdateUid(loginId);
//				distributionHistory.setUpdateTime(timestamp);
				this.distributionService.updateOnlyChanged(distributionHistory);
			}
			
			CollectionDistribution distributionBean=new CollectionDistribution();
//			String collectionUid=bean.getCollectionUidCur();
			distributionBean.setContractNo(contractNo);
			distributionBean.setCollectionWay(CollectionConstant.COLLECTION_WAY_CUR_PHONE);				//催收渠道
			distributionBean.setCollectionUid(collection_uid_cur);
			
			distributionBean.setIsCur("Y");						//是否当前任务
			distributionBean.setIsHelp("N");					//是否协催
			distributionBean.setIsDone("N");					//是否完结
			distributionBean.setState("1");						//是否有效状态
			distributionBean.setDistributionDate(new Date());
			distributionBean.setCreateUid(loginId);
			distributionBean.setCreateTime(timestamp);
//			distributionBean.setUpdateUid(loginId);
//			distributionBean.setUpdateTime(timestamp);
			//分配表信息
			distributionBeanList[k]=distributionBean;
			//更新bmpTask表
//			Map<String, Object> contractMap = new HashMap<String, Object>();
//			contractMap.put("contractNo", contractNo);
//			List<Contract> contractList= contractService.queryList(contractMap);
//			User userTOnext=userService.getUserById(Integer.parseInt(collection_uid_cur));
			
			List<BpmTask> bpmsList=null;
			bpmsList=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,contractNo);
			if(bpmsList.size()>0){
			if (bpmsList!=null&&bpmsList.size()>0&&collection_uid_cur!=null) {
//					processService.goNext(bpmsList.get(0),"电话催收处理中",userTOupdate.getLoginId());
				processService.reAssignTask(bpmsList.get(0), collection_uid_cur);
			}
			
			
		}
		
		
		
	}
		this.distributionService.add(distributionBeanList);	
	}
	@Transactional
	public void cheatAplyUpdate(CollectionApplication collectionApplication,User user,String loginId)throws Exception{
		Timestamp timestamp=new Timestamp(new Date().getTime());
		Map<String, Object> applyQueryMap = new HashMap<String, Object>();
		String contractNo=null;
		if(collectionApplication!=null){
			contractNo=collectionApplication.getContractNo();
			String applyText=collectionApplication.getApplyText();
			String collectionWay=collectionApplication.getCollectionWay();
			String applyType=collectionApplication.getApplyType();
			if(contractNo!=null&&!"".equals(contractNo)){
				applyQueryMap.put("contractNo", contractNo);
				List<CollectionApplication> collectionApplicationList= this.collectionApplicationService.queryList(applyQueryMap);
				CollectionApplication app=new CollectionApplication();
				if(collectionApplicationList.size()>0){
					app.setId(collectionApplicationList.get(0).getId());
					app.setContractNo(contractNo);
					app.setApplyType(applyType);
					app.setCollectionWay(collectionWay);	//催收渠道
					app.setState("1");			//状态    	1待复核2复核处理3审批处理中4审批生效5解除0无效
					app.setDistributionState("0");//欺诈/司法分单状态
					app.setApplyText(applyText);
					app.setApplyUid(loginId);
					app.setApplyTime(timestamp);
					/*app.setCreateUid(loginId);
					app.setCreateTime(timestamp);*/
					app.setUpdateUid(loginId);
					app.setUpdateTime(timestamp);
					this.collectionApplicationService.update(app);
				}else{
					app.setApplyType(applyType);
					app.setCollectionWay(collectionWay);
					app.setContractNo(contractNo);
					app.setState("1");
					app.setDistributionState("0");
					app.setApplyText(applyText);
					app.setApplyUid(loginId);
					app.setApplyTime(timestamp);
					app.setCreateUid(loginId);
					app.setCreateTime(timestamp);
//					app.setUpdateUid(loginId);
//					app.setUpdateTime(timestamp);
					this.collectionApplicationService.add(app);
				}
			}
			String nextState="";
			String HEAT_BEFORE_REVIEW="";
			String[] roleNames = null;
			if(CollectionConstant.COLLECTION_APPLY_TYPE_CHEAT.equals(collectionApplication.getApplyType())){
				nextState=CollectionConstant.COLLECTION_STATE_CHEAT_WAIT;//"欺诈待复核"
				HEAT_BEFORE_REVIEW=CollectionConstant.CHEAT_BEFORE_REVIEW;///**欺诈待复核（分单）*/
				roleNames = new String[]{CollectionConstant.COLLECTION_ROLE_CHEAT_QZZG};// 欺诈主管
				
			}else if(CollectionConstant.COLLECTION_APPLY_TYPE_JUDICIAL.equals(collectionApplication.getApplyType())){
				nextState=CollectionConstant.COLLECTION_STATE_JUDICIAL_WAIT; //"司法待复核";
				HEAT_BEFORE_REVIEW=CollectionConstant.JUDICIAL_BEFORE_REVIEW; //司法待复核（分单）
				roleNames = new String[]{CollectionConstant.COLLECTION_ROLE_JUDICIAL_SFZG};// 司法主管
			}
			//外包申请
			else if(CollectionConstant.COLLECTION_APPLY_TYPE_OUTCOURCING.equals(collectionApplication.getApplyType())){
				nextState=CollectionConstant.COLLECTION_STATE_OUTCOURCING_WAIT;//"外包待审核";
				HEAT_BEFORE_REVIEW=CollectionConstant.COLLECTION_OUTSOURCING;///**外包待审核*/
				roleNames = new String[]{CollectionConstant.COLLECTION_ROLE_OUTCOURCING_WBZG};// 外包主管
			}
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", contractNo);
			List<CollectionBase> collectionBaseList=queryList(queryMap);
			CollectionBase collectionBase=null;
			if(collectionBaseList.size()>0){
				collectionBase=collectionBaseList.get(0);
				collectionBase.setState(HEAT_BEFORE_REVIEW);//base CollectionConstant.CHEAT_BEFORE_REVIEW   欺诈/司法
				//collectionBase.setCollectionWayCur(collectionWay);	//电催渠道
				//collectionBase.setCollectionUidCur(loginId);
				collectionBase.setApplyState("1");			//申请状态:  欺诈，司法共用  初始0 无申请或者退回  1 申请中  2 申请通过
				//collectionBase.setCreateUid(loginId);
				//collectionBase.setCreateTime(timestamp);
				collectionBase.setUpdateUid(loginId);
				collectionBase.setUpdateTime(timestamp);
				this.updateOnlyChanged(collectionBase);
			}
//			User userTOnext=userService.getUserById(Integer.parseInt(collection_uid_cur));
			List<BpmTask> bpmsList=null;
			List<User> roleList=this.userService.getUserByOrgAndRoleAndDepart(Constants.ORG_CODE,roleNames,null);
			String strProcessor="";
			if (roleList!=null&&roleList.size()!=0) {
				strProcessor=roleList.get(new Random().nextInt(roleList.size())).getLoginId();
			}
				bpmsList=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,contractNo);
				if(bpmsList.size()>0){
				if (bpmsList!=null&&bpmsList.size()>0&&loginId!=null) {
//					processService.goNext(bpmsList.get(0),"电话催收处理中",userTOupdate.getLoginId());
					BpmTask curTask=bpmsList.get(0);
					System.out.println("========================loginId================"+user.getLoginId());
					curTask.setOperator(user.getLoginId());
					processService.goNext(curTask, nextState,strProcessor);
				}
			}
			
		}
		
	}
	/**
	 * 跳转到查看催收信息基本表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	public ModelMap  collectionBaseRead(User user,String id,ModelMap map)throws Exception{
		Map<String, Object> userMap = new HashMap<String, Object>();
		String loginId=user.getLoginId();
		userMap.put("roleNames",new String[]{CollectionConstant.COLLECTION_ROLE_PHONE_DCZY});// "电话催收专员");
		userMap.put("loginId", loginId);
		userMap.put("states", new String[]{"1","2"});
		List<User> userList= this.userService.queryUserByOrgAndRoleAndDepart(userMap);
		CollectionBaseInfo bean = null;
		Map<String, Object> infoMap=null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			infoMap=  new HashMap<String, Object>();
			infoMap.put("id", id);
			bean = this.queryInfo(infoMap);
		}
		//add by wangyongliang 20150612
		String contractNo=bean.getContractNo(); //合同编号
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("contractNo", contractNo);
//		queryMap.put("type", "3");
		Map<String, Object> queryFirstMap = new HashMap<String, Object>();
		queryFirstMap.put("contractNo", contractNo);
		List<Contract> contactFirstList=this.contractService.queryList(queryFirstMap);//联系人本人
		CreditContactHistory creditContactFirst=new CreditContactHistory();
		List<CreditContactHistory> creditContactList=new ArrayList<CreditContactHistory>();
		if(contactFirstList.size()>0){
			Contract contactFirst=(Contract) contactFirstList.get(0); 
			creditContactFirst.setName(contactFirst.getLoanName());
			creditContactFirst.setRelation(CollectionConstant.CONTACT_RELATION_SELF);//本人
			creditContactFirst.setMobile(contactFirst.getBankMobile());
			
			Map<String, Object> collectionRecordMap = new HashMap<String, Object>();
			collectionRecordMap.put("contractNo", contractNo);
			collectionRecordMap.put("answerName", contactFirst.getLoanName());
			List<CollectionRecord> collectionRecordList = this.collectionRecordService.queryList(collectionRecordMap);//联系人本人历史记录最新数据
			CollectionRecord collectionRecord=new CollectionRecord();
			if(collectionRecordList.size()>0){
				collectionRecord=collectionRecordList.get(collectionRecordList.size()-1);
				creditContactFirst.setPhoneSummary(collectionRecord.getPhoneSummary());
				creditContactFirst.setRemark(collectionRecord.getRemark());
			}
			
			
			creditContactList.add(creditContactFirst);
		}
		List<CreditContactHistory> creditContactOtherList = this.creditContactService.getRelationList(queryMap);//相关联系人
			creditContactList.addAll(creditContactOtherList);
		
		Map<String, Object> dictionaryMap = new HashMap<String, Object>();
		dictionaryMap.put("keyName", "collectionsummary");//催收摘要
		List dictionarylist = this.dataDictionaryService.queryList(dictionaryMap);//催收摘要
		//end by wangyongliang 20150612
		
		Map<String, Object> historyMap =  new HashMap<String, Object>();
		historyMap.put("contractNo", contractNo);
		List<CollectionRecord> collectionHistoryList = this.collectionRecordService.queryList(historyMap);//催收历史信息
		
//		Map<String, Object> contractMap =  new HashMap<String, Object>();
//		contractMap.put("contractNo", contractNo);
//		List<CollectionRecord> contractList = this.collectionRecordService.queryList(historyMap);//
//		if(bean.getProduct()!=null&&!"".equals(bean.getProduct())&&bean.getProduct().contains("业主贷")){
//			map.put("product",bean.getProduct());
//			
		CreditApp creditApp  = this.creditAppService.queryByContractNo(contractNo);
		map.put("creditApp",creditApp);
//		}
		map.put("bean", bean);
		map.put("creditContactList", creditContactList);
		map.put("dictionarylist", dictionarylist);
		map.put("collectionHistoryList", collectionHistoryList);
		if(userList.size()>0){
			map.put("ishandler", "Y");
		}else{
			map.put("ishandler", "N");
		}
		return map;
	}
	
}
