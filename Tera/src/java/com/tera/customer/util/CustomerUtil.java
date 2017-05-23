package com.tera.customer.util;

import com.tera.customer.model.Contact;
import com.tera.customer.model.Customer;
import com.tera.customer.model.CustomerExt;
import com.tera.customer.model.form.CustomerFBean;

/**
 * CustomerFBean转换成客户相关信息
 * @author Peng
 */
public class CustomerUtil {

	public static Customer getCustomer(CustomerFBean bean) {
		// 财富客户信息
		Customer customer = new Customer();
		customer.setId(bean.getId()); // ID
		customer.setCustomerNo(bean.getIdNo()); //客户编号 个人取证件号
		customer.setName(bean.getName()); // 姓名/机构全称
		customer.setShortName(bean.getShortName()); // 简称
		customer.setCustomerType(bean.getCustomerType()); // 客户类型
		customer.setEngName(bean.getEngName()); // 英文名称
		customer.setGender(bean.getGender()); // 性别
		customer.setBirthday(bean.getBirthday()); // 生日
		customer.setNationality(bean.getNationality()); // 国籍
		customer.setLanguage(bean.getLanguage()); // 语言
		customer.setMotherFiratName(bean.getMotherFiratName()); // 母亲姓氏
		customer.setMarriage(bean.getMarriage()); // 婚姻
		customer.setIdType(bean.getIdType()); // 证件类型
		customer.setIdNo(bean.getIdNo()); // 证件号码
		customer.setIdIssueDate(bean.getIdIssueDate()); // 签发日期
		customer.setIdExpiryDate(bean.getIdExpiryDate()); // 失效日期
		customer.setIdIssueGov(bean.getIdIssueGov()); // 签发机关
		customer.setEducation(bean.getEducation()); // 学历
		customer.setJob(bean.getJob()); // 职业
		customer.setIndustry1(bean.getIndustry1()); // 行业代码1
		customer.setIndustry2(bean.getIndustry2()); // 行业代码2
		customer.setIndustry3(bean.getIndustry3()); // 行业代码3
		customer.setCompanyName(bean.getCompanyName()); // 单位名称
		customer.setWorkYears(bean.getWorkYears()); // 工作年限
		customer.setCompanyScale(bean.getCompanyScale()); // 单位规模
		customer.setJobDuty(bean.getJobDuty()); // 职务
		customer.setPhone(bean.getPhone()); // 固定电话
		customer.setMobile(bean.getMobile()); // 移动电话
		customer.setEmail(bean.getEmail()); // EMAIL
		customer.setAddProvince(bean.getAddProvince()); // 通讯地址-省
		customer.setAddCity(bean.getAddCity()); // 通讯地址-市
		customer.setAddCounty(bean.getAddCounty()); // 通讯地址-区县
		customer.setAddress(bean.getAddress()); // 通讯地址
		customer.setPostcode(bean.getPostcode()); // 邮编
		customer.setFamily(bean.getFamily()); // 家庭情况
		customer.setFamilyIncome(bean.getFamilyIncome()); // 家庭收入
		customer.setFileReceive(bean.getFileReceive()); // 文件接收方式
		customer.setRequirements(bean.getRequirements()); // 资源需求
		customer.setBizzScope(bean.getBizzScope()); // 经营范围
		customer.setRegProvince(bean.getRegProvince()); // 注册地址-省
		customer.setRegCity(bean.getRegCity()); // 注册地址-市
		customer.setRegCounty(bean.getRegCounty()); // 注册地址-区县
		customer.setRegAddress(bean.getRegAddress()); // 注册地址
		customer.setCompanyType(bean.getCompanyType()); // 企业性质
		customer.setTrustAssets(bean.getTrustAssets()); // 信托资产
		customer.setTrustSettlor(bean.getTrustSettlor()); // 信托委托人
		customer.setTrustSettlorPhone(bean.getTrustSettlorPhone()); // 信托委托人电话
		customer.setTrustBenefit(bean.getTrustBenefit()); // 信托受益人
		customer.setTrustBenefitPhone(bean.getTrustBenefitPhone()); // 信托受益人电话
		customer.setCustomerManager(bean.getCustomerManager()); // 客户经理
		customer.setOrgId(bean.getOrgId()); // 所属机构
		customer.setCreateTime(bean.getCreateTime()); // 创建日期
		customer.setUpdateTime(bean.getUpdateTime()); // 修改日期
		customer.setAppTime(bean.getAppTime()); // 提交日期
		customer.setState(bean.getState()); // 状态
		return customer;
	}

	public static Contact getContact(CustomerFBean bean) {
		// 财富客户联系人信息
		Contact contact = new Contact();
		contact.setId(bean.getContactId());
		contact.setType("00");
		contact.setCustomerId(bean.getId()); //客户ID
		contact.setName(bean.getContactName()); // 姓名
		contact.setEngName(bean.getContactEngName()); // 英文名
		contact.setGender(bean.getContactGender()); // 性别
		contact.setBirthday(bean.getContactBirthday()); // 生日
		contact.setIdType(bean.getContactIdType()); // 证件类型
		contact.setIdNo(bean.getContactIdNo()); // 证件号码
		contact.setIdIssueDate(bean.getContactIdIssueDate()); // 签发日期
		contact.setIdExpiryDate(bean.getContactIdExpiryDate()); // 有效期
		contact.setIdIssueGov(bean.getContactIdIssueGov()); // 签发机关
		contact.setMobile(bean.getContactMobile()); // 移动电话
		contact.setPhone(bean.getContactPhone()); // 固定电话
		contact.setEmail(bean.getContactEmail()); // email
		contact.setRelation(bean.getContactRelation()); // 关系
		contact.setAddProvince(bean.getContactAddProvice()); // 省
		contact.setAddCity(bean.getContactAddCity()); // 市
		contact.setAddCounty(bean.getContactAddCounty()); // 区县
		contact.setAddress(bean.getContactAddress()); // 地址
		contact.setPostcode(bean.getContactPostcode()); // 邮编
		return contact;
	}
	
	public static Contact getFarenContact(CustomerFBean bean) {
		//法人
		Contact contact = new Contact();
		contact.setId(bean.getFarenId());
		contact.setType("02");
		contact.setName(bean.getFarenName());//法人姓名
		contact.setIdType(bean.getFarenIdType());//法人证件类型
		contact.setIdNo(bean.getFarenIdNo());//法人证件号码
		contact.setIdIssueDate(bean.getFarenIdIssueDate());//法人签发日期
		contact.setIdIssueGov(bean.getFarenIssueGov());//法人签发机关
		return contact;
	}

	public static Contact getCfoContact(CustomerFBean bean) {
		//财务负责人
		Contact contact = new Contact();
		contact.setId(bean.getCfoId());
		contact.setType("04");
		contact.setName(bean.getCfoName());//财务姓名
		contact.setIdType(bean.getCfoIdType());//财务证件类型
		contact.setIdNo(bean.getCfoIdNo());//财务证件号码
		contact.setIdIssueDate(bean.getCfoIdIssueDate());//财务签发日期
		contact.setIdIssueGov(bean.getCfoIssueGov());//财务签发机关
		return contact;
	}
	
	public static Contact getKongzhiContact(CustomerFBean bean) {
		//实际控制人
		Contact contact = new Contact();
		contact.setId(bean.getKongzhiId());
		contact.setType("07");
		contact.setName(bean.getKongzhiName());//控制人姓名
		contact.setIdType(bean.getKongzhiIdType());//控制人证件类型
		contact.setIdNo(bean.getKongzhiIdNo());//控制人证件号码
		contact.setIdIssueDate(bean.getKongzhiIdIssueDate());//控制人签发日期
		contact.setIdIssueGov(bean.getKongzhiIssueGov());//控制人签发机关
		return contact;
	}

	public static CustomerExt getCustomerExt(CustomerFBean bean) {
		// 财富客户扩展表
		CustomerExt customerExt = new CustomerExt();
		customerExt.setCustomerId(bean.getId()); //客户ID
		customerExt.setInterests(array2String(bean.getInterests())); // 兴趣爱好
		customerExt.setActivities(array2String(bean.getActivities())); // 参加活动
		customerExt.setRealEstate(array2String(bean.getRealEstate())); // 置业情况
		customerExt.setInvestmentLevel(array2String(bean.getInvestmentLevel())); // 投资了解程度
		customerExt.setInvestmentSource(array2String(bean.getInvestmentSource())); // 投资资金来源
		customerExt.setInvestmentExp(array2String(bean.getInvestmentExp())); // 投资经验
		customerExt.setInvestmentProduct(array2String(bean.getInvestmentProduct())); // 投资产品
		customerExt.setInvestmentIncome(array2String(bean.getInvestmentIncome())); // 投资收益
		customerExt.setFollowType(array2String(bean.getFollowType())); // 关注方式
		customerExt.setFollowPoint(array2String(bean.getFollowPoint())); // 关注特点
		customerExt.setPreferenceGoal(array2String(bean.getPreferenceGoal())); // 偏好-目标
		customerExt.setPreferencePeriod(array2String(bean.getPreferencePeriod())); // 偏好-期限
		customerExt.setPreferenceDecision(bean.getPreferenceDecision()); // 偏好-投资决策
		customerExt.setPreferenceRisk(bean.getPreferenceRisk()); // 偏好-风险偏好
		customerExt.setPreferenceProduct(array2String(bean.getPreferenceProduct())); // 偏好-投资产品
		customerExt.setPreferenceAmount(array2String(bean.getPreferenceAmount())); // 偏好-追加金额
		return customerExt;
	}

	private static  String array2String(String[] args) {
		String result = "";
		if (args == null) {
			return result;
		}
		if (args.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (String string : args) {
				sb.append(string).append(",");
			}
			result = sb.toString().substring(0, sb.toString().length() - 1); //去掉最后的,
			return result;
		} else {
			return result;
		}
	}
}
