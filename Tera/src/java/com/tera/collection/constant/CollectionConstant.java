package com.tera.collection.constant;

public class CollectionConstant {
	/**催收流程定义名称*/
	public static final String COLLECTION_PROCESS_NAME = "催收流程";
	
	///////////////////////////////////////////////////////////
	/** 
	 * 催收基本表中的状态标识
	 */
	
	/**电催待催收*/
	public static final String COLLECTION_BEFORE_PHONE = "1";
	/**电催处理中*/
	public static final String COLLECTION_PHONE = "2";
	/**落地催收待分配*/
	public static final String COLLECTION_BEFORE_VISIT = "3";
	/**落地催处理中*/
	public static final String COLLECTION_VISIT = "4";
	/**协催待分配*/
	public static final String COLLECTION_BEFORE_HELP = "5";
	/**协催处理中*/
	public static final String COLLECTION_HELP = "6";
	/**催收完成*/
	public static final String COLLECTION_FINISH = "7";
	/**欺诈待复核（分单）*/
	public static final String CHEAT_BEFORE_REVIEW= "8";
	/**欺诈复核处理中*/
	public static final String CHEAT_REVIEW = "9";
	/**欺诈审批处理中*/
	public static final String CHEAT_APPROVAL = "10";
	/**欺诈认定生效*/
	public static final String CHEAT_PASS = "11";
	/**司法待复核（分单）*/
	public final static String JUDICIAL_BEFORE_REVIEW= "12";
	/**司法复核处理中*/
	public final static String JUDICIAL_REVIEW = "13";
	/**司法审批处理中*/
	public final static String JUDICIAL_APPROVAL = "14";
	/**司法认定生效*/
	public final static String JUDICIAL_PASS = "15";
	/**外包待审核*/
	public final static String COLLECTION_OUTSOURCING = "16";
	/**外包催收处理中*/
	public final static String COLLECTION_OUTSOURCING_PASS = "17";
	
	////////////////////////////////////////////////////////////////////
	/**电催到落地催账龄限制参数名**/
	public final static String COLLECTION_AGE_LIMIT = "collection_age_limit";
	
	///////////////////////////////////////////////////////////
	/** 
	 * 催收流程各节点名称
	 */
	/**
	 * 开始*/
	public final static String COLLECTION_STATE_START="开始";
	/**
	 * 逾期待分配*/
	public final static String COLLECTION_STATE_PHONE_WAIT="逾期待分配";
	/**
	 * 电话催收处理中*/
	public final static String COLLECTION_STATE_PHONE_REVIEW="电话催收处理中";
	/**
	 * 落地催收待分配*/
	public final static String COLLECTION_STATE_VISIT_WAIT="落地催收待分配";
	/**
	 * 落地催收处理中*/
	public final static String COLLECTION_STATE_VISIT_REVIEW="落地催收处理中";
	
	/**
	 * 协催待分配*/
	public final static String COLLECTION_STATE_HELP_WAIT="协催待分配";
	/**
	 * 协催处理中*/
	public final static String COLLECTION_STATE_HELP_REVIEW="协催处理中";
	/**
	 * 欺诈待复核*/
	public final static String COLLECTION_STATE_CHEAT_WAIT="欺诈待复核";
	/**
	 * 欺诈复核处理中*/
	public final static String COLLECTION_STATE_CHEAT_REVIEW="欺诈复核处理中";
	/**
	 * 欺诈审批处理中*/
	public final static String COLLECTION_STATE_CHEAT_EXAMINE ="欺诈审批处理中";
	/**
	 * 欺诈认定生效*/
	///public final static String COLLECTION_CHEAT_PASS ="欺诈认定生效";
	/**
	 * 司法待复核*/
	public final static String COLLECTION_STATE_JUDICIAL_WAIT="司法待复核";
	/**
	 * 司法复核处理中*/
	public final static String COLLECTION_STATE_JUDICIAL_REVIEW="司法复核处理中";
	
	/**
	 * 司法审批处理中*/
	public final static String COLLECTION_STATE_JUDICIAL_EXAMINE="司法审批处理中";
	
	/**
	 * 外包待审核
	 */
	public static String COLLECTION_STATE_OUTCOURCING_WAIT="外包待审核";
	/**
	 * 外包催收处理中
	 */
	public static String COLLECTION_STATE_OUTCOURCING_REVIEW="外包催收处理中";
	
	/**结束*/
	public final static String COLLECTION_STATE_END="结束";
	
///////////////////////////////////////////////////////////
	/** 
	 * 角色
	 */
	/**电话催收主管*/
	public final static String COLLECTION_ROLE_PHONE_DCZG="电话催收主管";
	
	/**电话催收专员*/
	public final static String COLLECTION_ROLE_PHONE_DCZY="电话催收专员";
	
	/**落地催收主管*/
	public final static String COLLECTION_ROLE_VISIT_LDZG="落地催收主管";
	
	/**落地催收专员*/
	public final static String COLLECTION_ROLE_VISIT_LDZY="落地催收专员";
	
	/**欺诈主管*/
	public final static String COLLECTION_ROLE_CHEAT_QZZG="欺诈主管";
	
	/**欺诈复核专员*/
	public final static String COLLECTION_ROLE_CHEAT_QZFHZY="欺诈复核专员";
	
	/**欺诈审批专员*/
	public final static String COLLECTION_ROLE_CHEAT_QZSPZY="欺诈审批专员";
	
	/**司法主管*/
	public final static String COLLECTION_ROLE_JUDICIAL_SFZG="司法主管";
	
	/**司法复核专员*/
	public final static String COLLECTION_ROLE_JUDICIAL_SFFHZY="司法复核专员";
	
	/**司法审批专员*/
	public final static String COLLECTION_ROLE_JUDICIAL_SFSPZY="司法审批专员";
	
	/**外包主管*/
	public final static String COLLECTION_ROLE_OUTCOURCING_WBZG="外包主管";
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////
	/**催收渠道标识**/
	/**电催*/
	public final static String COLLECTION_WAY_CUR_PHONE = "1";
	/**落地催*/
	public final static String COLLECTION_WAY_CUR_VISIT = "2";
	
	
	////////////////////////////////////////////////////////////////////
	/**申请类型**/
	
	/**司法申请*/
	public final static String COLLECTION_APPLY_TYPE_JUDICIAL = "1";
	/**欺诈申请*/
	public final static String COLLECTION_APPLY_TYPE_CHEAT = "2";
	/**外包申请*/
	public final static String COLLECTION_APPLY_TYPE_OUTCOURCING = "3";
	
	
	
	////////////////////////////////////////////////////////////////////

	/**联系人类型--常用联系人*/
	public static final String CONTACT_TYPE_COMMON = "1";
	/**联系人类型--经营往来联系人*/
	public static final String CONTACT_TYPE_DEAL = "2";
	/**联系人类型--催收添加*/
	public static final String CONTACT_TYPE_COLLECTION = "3";
	/**联系人类型--配偶*/
	public static final String CONTACT_TYPE_SPOUSE = "4";
	/**联系人类型--本人*/
	public static final String CONTACT_TYPE_SELF = "5";
	
	
	/**联系人关系--父母*/
	public static final String CONTACT_RELATION_PARENTS = "1";
	/**联系人关系--配偶*/
	public static final String CONTACT_RELATION_SPOUSE = "2";
	/**联系人关系--子女*/
	public static final String CONTACT_RELATION_CHILDREN = "3";
	/**联系人关系--亲属*/
	public static final String CONTACT_RELATION_RELATIVES = "4";
	/**联系人关系--朋友*/
	public static final String CONTACT_RELATION_FRIEND = "5";
	/**联系人关系--同事*/
	public static final String CONTACT_RELATION_WORKMATE = "6";
	/**联系人关系--同学*/
	public static final String CONTACT_RELATION_CLASSMATE = "7";
	/**联系人关系--本人*/
	public static final String CONTACT_RELATION_SELF = "8";
	/**联系人关系--其他*/
	public static final String CONTACT_RELATION_OTHER = "99";
	
	
}
