package com.hikootech.mqcash.constants;

public class CreditCfgKeyConstants {

	/**开   */
	public final static String OPEN="1";
	/**关 */
	public final static String CLOSE="0";
	
	/**************************配置表key开始******************************************/

	public final static String CREDIT_HD_QUERYTIMES_MAX="CREDIT_HD_QUERYTIMES_MAX";
	/**根据身份证配置允许用户最大下单量*/
	public final static String  MAX_ORDER_COUNT="MAX_ORDER_COUNT";
	/**根据手机号配置截至今日最长天数内禁止下单*/
	public final static String  MAX_ORDER_TEL_DAY="MAX_ORDER_TEL_DAY";
	/**调用国政通征信前，校验年龄段 例：22~35 以“~”链接*/
	public final static String  BEFORE_GBOSS_AGE_SECTION="BEFORE_GBOSS_AGE_SECTION";
	/**前海征信--黑名单命中--配置判断项--0-拒贷 1-通过*/
	public final static String QH_BLACKLIST_CONFIG = "QH_BLACKLIST_CONFIG";
	
	/**前海征信--好信度--无数据配置判断项--0-拒贷 1-通过*/
	public final static String QH_NOGOODRELDATA_CONFIG = "QH_NOGOODRELDATA_CONFIG";
	
	/**前海征信--好信度--有数据配置判断项--0-拒贷 1-通过*/
	public final static String QH_GOODRELDATA_CONFIG = "QH_GOODRELDATA_CONFIG";
	
	/**前海征信--好信度--配置判断项--好信度得分*/
	public final static String QH_GOODREL_SCORE_CONFIG = "QH_GOODREL_SCORE_CONFIG";
	
	/**91征信--近6个月不在同机构申请命中开关*/
	public final static String JY_M6DIFFORG_OPEN_CONFIG = "JY_M6DIFFORG_OPEN_CONFIG";
	/**91征信--近6个月不在同机构申请数大于等于N拒贷*/
	public final static String JY_M6DIFFORG_DATA_CONFIG = "JY_M6DIFFORG_DATA_CONFIG";
	/**91征信--近12个月不在同机构申请命中开关*/
	public final static String JY_M12DIFFORG_OPEN_CONFIG = "JY_M12DIFFORG_OPEN_CONFIG";
	/**91征信--近12个月不在同机构申请命中开关*/
	public final static String JY_M12DIFFORG_DATA_CONFIG = "JY_M12DIFFORG_DATA_CONFIG";
	
	/**91征信----配置判断项--该客户有逾期数据判断标准*/
	public final static String JY_OVERDUE_FLAG_CONFIG = "JY_OVERDUE_FLAG_CONFIG";
	/**91征信----有数据配置判断项--借款类型为0未知--0-拒贷 1-通过*/
	public final static String JY_DATA_UNKNOWN_CONFIG = "JY_DATA_UNKNOWN_CONFIG";
	/**91征信----有数据配置判断项--借款类型为1或者3（1.个人信贷 3.企业信贷），欠款金额在3万以上--0-拒贷 1-通过*/
	public final static String JY_DATA_XD_UPPERQK_CONFIG = "JY_DATA_XD_UPPERQK_CONFIG";
	/**91征信----有数据配置判断项--借款类型为1或者3（1.个人信贷 3.企业信贷）欠款金额在3万以下，还款除1(正常)以外--0-拒贷 1-通过*/
	public final static String JY_DATA_XD_LOWERQK_CONFIG = "JY_DATA_XD_LOWERQK_CONFIG";
	/**91征信----有数据配置判断项--借款类型为2或者4(2.个人抵押 4.企业抵押)，如果还款状态，除1（正常）以外的任何情况--0-拒贷 1-通过*/
	public final static String JY_DATA_DY_CONFIG = "JY_DATA_DY_CONFIG";
	/**91征信----有数据配置判断项--借款状态为2，3，6时(2.批贷已放款3.批贷未放款6.待放款)，还款状态不为1（1.正常）--0-拒贷 1-通过*/
	public final static String JY_DATA_FK_CONFIG = "JY_DATA_FK_CONFIG";
	/**91征信----有数据配置判断项--借款状态为1，5（1.拒贷 5.审核中）--0-拒贷 1-通过*/
	public final static String JY_DATA_JD_SH_CONFIG = "JY_DATA_JD_SH_CONFIG";
	/**好贷征信--生效开关---0-关闭；1-开启*/
	public final static String QH_CR_EFFECT_ISOPEN = "QH_CR_EFFECT_ISOPEN";
	
	/**好贷征信--个人失信被执行命中--0-拒贷 1-通过*/
	public final static String HD_GRSXBZX_CONFIG = "HD_GRSXBZX_CONFIG";
	
	/**好贷征信--个人被执行公告命中--0-拒贷 1-通过*/
	public final static String HD_ZXGRBZX_CONFIG = "HD_ZXGRBZX_CONFIG";
	
	/**好贷征信--借款重复查询次数信息命中---0-拒贷 1-通过*/
	public final static String HD_DKCFSQ_CONFIG = "HD_DKCFSQ_CONFIG";
	
	/**好贷征信--黑名单命中--0-拒贷 1-通过*/
	public final static String HD_SYHMD_CONFIG = "HD_SYHMD_CONFIG";
	
	/**好贷征信--被信贷机构查询命中--0-拒贷 1-通过*/
	public final static String HD_BXDJGCXJL_CONFIG = "HD_BXDJGCXJL_CONFIG";
 	/**前海黑名单征信模块调用开关 ---0-关；1-开*/
 	public final static String  QH_MODEL_BLACK_ISOPEN="QH_MODEL_BLACK_ISOPEN";
 	/**前海常贷客征信模块调用开关 ---0-关；1-开*/
 	public final static String  QH_MODEL_LOAN_ISOPEN="QH_MODEL_LOAN_ISOPEN";
 	/**前海好信度征信模块调用开关 ---0-关；1-开*/
 	public final static String  QH_MODEL_GOODREL_ISOPEN="QH_MODEL_GOODREL_ISOPEN";
 	/**国政通征信模块调用开关--0-关；1-开*/
 	public final static String  GBOSS_MODEL_ISOPEN="GBOSS_MODEL_ISOPEN";
 	/**91通征信模块调用开关---0-关；1-开*/
 	public final static String  JY_MODEL_ISOPEN="JY_MODEL_ISOPEN";
 	/**好贷通征信模块调用开关--0-关；1-开*/
 	public final static String  HD_MODEL_ISOPEN="HD_MODEL_ISOPEN";
 	/**企信征信模块调用开关 ---0-关；1-开*/
 	public final static String  QX_MODEL_ISOPEN="QX_MODEL_ISOPEN";
 	/**年龄模块调用开关 ---0-关；1-开*/
 	public final static String  AGE_MODEL_ISOPEN="AGE_MODEL_ISOPEN";
 	/**好信一鉴通模块调用开关 ---0-关；1-开*/
 	public final static String  VERIFY_MODEL_ISOPEN="VERIFY_MODEL_ISOPEN";
 	/**好信一鉴通模块生效开关 ---0-关闭；1-开启*/
 	public final static String  VERIFY_CR_BLACKLIST_ISOPEN="VERIFY_CR_BLACKLIST_ISOPEN";
	/**企信生效开关 ---0-关闭；1-开启*/
 	public final static String  QX_CR_BLACKLIST_ISOPEN="QX_CR_BLACKLIST_ISOPEN";
	/**前海征信结果黑名单生效开关 ---0-关闭；1-开启*/
 	public final static String  QH_CR_BLACKLIST_ISOPEN="QH_CR_BLACKLIST_ISOPEN";
 	/**前海征信结果好信度生效开关 ---0-关闭；1-开启*/
 	public final static String  QH_CR_GOODREL_ISOPEN="QH_CR_GOODREL_ISOPEN";
 	/**前海常贷客生效开关 ---0-关闭；1-开启*/
 	public final static String  QH_CR_LOAN_ISOPEN="QH_CR_LOAN_ISOPEN";
 	/**91征信结果黑名单生效开关 ---0-关闭；1-开启*/
 	public final static String  JY_CR_BLACKLIST_ISOPEN="JY_CR_BLACKLIST_ISOPEN";
 	/**年龄生效开关 ---0-关闭；1-开启*/
 	public final static String  AGE_CR_BLACKLIST_ISOPEN="AGE_CR_BLACKLIST_ISOPEN";
 	/**好贷个人失信被执行查询生效开关 ---0-关闭；1-开启*/
 	public final static String  HD_CR_NOGRSXBZXDATA_ISOPEN="HD_CR_NOGRSXBZXDATA_ISOPEN";
 	/**好贷个人被执行公告查询生效开关 ---0-关闭；1-开启*/
 	public final static String  HD_CR_NOZXGRBZXDATA_ISOPEN="HD_CR_NOZXGRBZXDATA_ISOPEN";
 	/**好贷借款重复查询次数信息生效开关 ---0-关闭；1-开启*/
 	public final static String  HD_CR_NODKCFSQDATA_ISOPEN="HD_CR_NODKCFSQDATA_ISOPEN";
 	/**好贷黑名单查询生效开关 ---0-关闭；1-开启*/
 	public final static String  HD_CR_NOSYHMDDATA_ISOPEN="HD_CR_NOSYHMDDATA_ISOPEN";
 	/**好贷被信贷机构查询次数生效开关 ---0-关闭；1-开启*/
 	public final static String  HD_CR_NOBXDJGCXJLDATA_ISOPEN="HD_CR_NOBXDJGCXJLDATA_ISOPEN";
 	
 	/**黑名单调用开关 ---0-关闭；1-开启*/
 	public final static String  BL_CR_ISOPEN="BL_CR_ISOPEN";
	/**黑名单生效开关 ---0-关闭；1-开启*/
 	public final static String BL_CR_BLACKLIST_ISOPEN="BL_CR_BLACKLIST_ISOPEN";
 	/**白名单调用效开关 ---0-关闭；1-开启*/
 	public final static String  WL_CR_ISOPEN="WL_CR_ISOPEN";
 	/**白名单生效开关 ---0-关闭；1-开启*/
 	public final static String  WL_CR_WHITELIST_ISOPEN="WL_CR_WHITELIST_ISOPEN";
 	
	/**BAK--银行机构命中生效开关 ---0-关闭；1-开启*/
	public final static String QH_BAK_ISOPEN_CONFIG = "QH_BAK_ISOPEN_CONFIG";
	/**命中次数大于N拒贷*/
	public final static String QH_BAK_DATA_CONFIG = "QH_BAK_DATA_CONFIG";
	/**MCL--小贷机构命中生效开关 ---0-关闭；1-开启*/
	public final static String QH_MCL_ISOPEN_CONFIG = "QH_MCL_ISOPEN_CONFIG";
	/**命中次数大于N拒贷*/
	public final static String QH_MCL_DATA_CONFIG = "QH_MCL_DATA_CONFIG";
	/**P2P--P2P命中生效开关 ---0-关闭；1-开启*/
	public final static String QH_P2P_ISOPEN_CONFIG = "QH_P2P_ISOPEN_CONFIG";
	/**命中次数大于N拒贷*/
	public final static String QH_P2P_DATA_CONFIG = "QH_P2P_DATA_CONFIG";
	/**ASM--资产管理机构命中生效开关 ---0-关闭；1-开启*/
	public final static String QH_ASM_ISOPEN_CONFIG = "QH_ASM_ISOPEN_CONFIG";
	/**命中次数大于N拒贷*/
	public final static String QH_ASM_DATA_CONFIG = "QH_ASM_DATA_CONFIG";
	/**TRU--信托机构命中生效开关 ---0-关闭；1-开启*/
	public final static String QH_TRU_ISOPEN_CONFIG = "QH_TRU_ISOPEN_CONFIG";
	/**命中次数大于N拒贷*/
	public final static String QH_TRU_DATA_CONFIG = "QH_TRU_DATA_CONFIG";
	/**LEA--租赁机构命中生效开关 ---0-关闭；1-开启*/
	public final static String QH_LEA_ISOPEN_CONFIG = "QH_LEA_ISOPEN_CONFIG";
	/**命中次数大于N拒贷*/
	public final static String QH_LEA_DATA_CONFIG = "QH_LEA_DATA_CONFIG";
	/**CRF--众筹机构命中生效开关 ---0-关闭；1-开启*/
	public final static String QH_CRF_ISOPEN_CONFIG = "QH_CRF_ISOPEN_CONFIG";
	/**命中次数大于N拒贷*/
	public final static String QH_CRF_DATA_CONFIG = "QH_CRF_DATA_CONFIG";
	/**INV--投资机构命中生效开关 ---0-关闭；1-开启*/
	public final static String QH_INV_ISOPEN_CONFIG = "QH_INV_ISOPEN_CONFIG";
	/**命中次数大于N拒贷*/
	public final static String QH_INV_DATA_CONFIG = "QH_INV_DATA_CONFIG";
	/**CNS--消费金融机构命中生效开关 ---0-关闭；1-开启*/
	public final static String QH_CNS_ISOPEN_CONFIG = "QH_CNS_ISOPEN_CONFIG";
	/**命中次数大于N拒贷*/
	public final static String QH_CNS_DATA_CONFIG = "QH_CNS_DATA_CONFIG";
	/**INS--保险机构命中生效开关 ---0-关闭；1-开启*/
	public final static String QH_INS_ISOPEN_CONFIG = "QH_INS_ISOPEN_CONFIG";
	/**命中次数大于N拒贷*/
	public final static String QH_INS_DATA_CONFIG = "QH_INS_DATA_CONFIG";
	/**THR--第三方机构命中生效开关 ---0-关闭；1-开启*/
	public final static String QH_THR_ISOPEN_CONFIG = "QH_THR_ISOPEN_CONFIG";
	/**命中次数大于N拒贷*/
	public final static String QH_THR_DATA_CONFIG = "QH_THR_DATA_CONFIG";
	
	
 	/**百融特殊名单模块调用开关 ---0-关；1-开*/
 	public final static String  BR_MODEL_SPECIAL_ISOPEN="BR_MODEL_SPECIAL_ISOPEN";
 	/**百融特殊名单结果生效开关 ---0-关闭；1-开启*/
 	public final static String  BR_CR_SPECIALLIST_ISOPEN="BR_CR_SPECIALLIST_ISOPEN";
 	
 	
 	
 	/**百融多次申请核查模块调用开关 ---0-关；1-开*/
 	public final static String  BR_MODEL_APPLYLOAN_ISOPEN="BR_MODEL_APPLYLOAN_ISOPEN";
 	/**百融多次申请核查结果生效开关 ---0-关闭；1-开启*/
 	public final static String  BR_CR_APPLYLOAN_ISOPEN="BR_CR_APPLYLOAN_ISOPEN";
 	
	/**近3个月在银行机构申清次数生效开关*/
	public final static String BR_M3BANKNUM_ISOPEN_CONFIG = "BR_M3BANKNUM_ISOPEN_CONFIG";
	/**身份证在银行-本机构申清次数大于N拒贷*/
	public static final String BR_M3IDBANKSELFNUM_DATA_CONFIG ="BR_M3IDBANKSELFNUM_DATA_CONFIG";   
	/**身份证在银行-总申清次数大于N拒贷*/
	public static final String BR_M3IDBANKALLNUM_DATA_CONFIG ="BR_M3IDBANKALLNUM_DATA_CONFIG";    
	/**身份证在银行-申清过的机构数大于N拒贷*/
	public static final String BR_M3IDBANKORGNUM_DATA_CONFIG ="BR_M3IDBANKORGNUM_DATA_CONFIG";    
	 
	/**近3个月在非银机构申清次数生效开关*/
	public static final String BR_M3NOBANKNUM_ISOPEN_CONFIG ="BR_M3NOBANKNUM_ISOPEN_CONFIG";     
	/**身份证在非银-本机构申清次数大于N拒贷*/
	public static final String BR_M3IDNOBANKSELFNUM_DATA_CONFIG="BR_M3IDNOBANKSELFNUM_DATA_CONFIG"; 
	/**身份证在非银-总申清次数大于N拒贷*/
	public static final String BR_M3IDNOBANKALLNUM_DATA_CONFIG	="BR_M3IDNOBANKALLNUM_DATA_CONFIG";
	/**身份证在非银-申清过的机构数大于N拒贷*/
	public static final String BR_M3IDNOBANKORGNUM_DATA_CONFIG="BR_M3IDNOBANKORGNUM_DATA_CONFIG";
	                                
	/**近6个月在银行机构申清次数生效开关*/
	public static final String BR_M6BANKNUM_ISOPEN_CONFIG="BR_M6BANKNUM_ISOPEN_CONFIG";       
	/**身份证在银行-本机构申清次数大于N拒贷*/
	public static final String BR_M6IDBANKSELFNUM_DATA_CONFIG="BR_M6IDBANKSELFNUM_DATA_CONFIG";   
	/**身份证在银行-总申清次数大于N拒贷*/
	public static final String BR_M6IDBANKALLNUM_DATA_CONFIG="BR_M6IDBANKALLNUM_DATA_CONFIG";    
	/**身份证在银行-申清过的机构数大于N拒贷*/
	public static final String BR_M6IDBANKORGNUM_DATA_CONFIG="BR_M6IDBANKORGNUM_DATA_CONFIG";    
	                                
	/**近6个月在非银机构申清次数生效开关*/
	public static final String BR_M6NOBANKNUM_ISOPEN_CONFIG ="BR_M6NOBANKNUM_ISOPEN_CONFIG";     
	/**身份证在非银-本机构申清次数大于N拒贷*/
	public static final String BR_M6IDNOBANKSELFNUM_DATA_CONFIG="BR_M6IDNOBANKSELFNUM_DATA_CONFIG"; 
	/**身份证在非银-总申清次数大于N拒贷*/
	public static final String BR_M6IDNOBANKALLNUM_DATA_CONFIG	="BR_M6IDNOBANKALLNUM_DATA_CONFIG";
	/**身份证在非银-申清过的机构数大于N拒贷*/
	public static final String BR_M6IDNOBANKORGNUM_DATA_CONFIG="BR_M6IDNOBANKORGNUM_DATA_CONFIG";   
	                              
	/**近12个月在银行机构申清次数生效开关*/
	public static final String BR_M12BANKNUM_ISOPEN_CONFIG     ="BR_M12BANKNUM_ISOPEN_CONFIG";      
	/**身份证在银行-本机构申清次数大于N拒贷*/
	public static final String BR_M12IDBANKSELFNUM_DATA_CONFIG ="BR_M12IDBANKSELFNUM_DATA_CONFIG";  
	/**身份证在银行-总申清次数大于N拒贷*/
	public static final String BR_M12IDBANKALLNUM_DATA_CONFIG  ="BR_M12IDBANKALLNUM_DATA_CONFIG";   
	/**身份证在银行-申清过的机构数大于N拒*/
	public static final String BR_M12IDBANKORGNUM_DATA_CONFIG  ="BR_M12IDBANKORGNUM_DATA_CONFIG";   
	                               
	/**近12个月在非银机构申清次数生效开关*/
	public static final String BR_M12NOBANKNUM_ISOPEN_CONFIG ="BR_M12NOBANKNUM_ISOPEN_CONFIG";     
	/**身份证在非银-本机构申清次数大于N拒贷*/
	public static final String BR_M12IDNOBANKSELFNUM_DATA_CONFIG="BR_M12IDNOBANKSELFNUM_DATA_CONFIG"; 
	/**身份证在非银-总申清次数大于N拒贷*/
	public static final String BR_M12IDNOBANKALLNUM_DATA_CONFIG	="BR_M12IDNOBANKALLNUM_DATA_CONFIG";
	/**身份证在非银-申清过的机构数大于N拒贷*/
	public static final String BR_M12IDNOBANKORGNUM_DATA_CONFIG="BR_M12IDNOBANKORGNUM_DATA_CONFIG"; 
	
	/**学籍查询开关 ---0-关闭；1-开启*/
	public static final String STUDENT_STATUS="STUDENT_STATUS";
	/**学历评分开关 ---0-关闭；1-开启*/
	public static final String STUDENT_SCORE="STUDENT_SCORE";
	/**银联评分开关 ---0-关闭；1-开启*/
	public static final String UNIPAY_SORE="UNIPAY_SORE";
	/**************************配置表key结束******************************************/
	
	/**************************校园征信配置 开始******************************************/
	/**校园征信，校验年龄段 例：18~30 以“~”链接*/
	public final static String  COLLEGE_AGE_LIMIT_SECTION="COLLEGE_AGE_LIMIT_SECTION";
	/**校园征信，调用91征信，配置等待时间（秒）*/
	public final static String  COLLEGE_JY_WAIT_TIME="COLLEGE_JY_WAIT_TIME";
	
	/**************************校园征信配置 结束******************************************/
	
	
}
