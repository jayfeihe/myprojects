package com.hikootech.mqcash.constants;

/**
 * @author yuwei
 * 2015年8月20日
 * 征信一些常量
 */
public class CreditCodeConstants {
	
	/*****************************各征信模块征信结果表code***************************************/
	
	/** 年龄 */
	public final static String CREDIT_AGE = "101";
	public final static String CREDIT_TYPE_AGE = "101001";
	/** 91黑名单*/
	public final static String CREDIT_91_BLACKLIST = "102";
	public final static String CREDIT_TYPE_91_BLACKLIST = "102001";
	
	/**前海 */
	public final static String CREDIT_QH = "104";
	/**前海黑名单 */
	public final static String CREDIT_TYPE_QH_BLACKLIST = "104001";
	/**前海好信度*/
	public final static String CREDIT_TYPE_QH_GOODREL = "104002";
	/**前海好信一鉴通*/
	public final static String CREDIT_TYPE_QH_VERIFY = "104003";
	/**前海常贷客*/
	public final static String CREDIT_TYPE_QH_LOAN = "104004";
	
	/**企信*/
	public final static String CREDIT_QX = "105";
	/**企信异常*/
	public final static String CREDIT_TYPE_QX_EXP = "105001";
	
	/**好贷*/
	public final static String CREDIT_HD_GRSXBZX = "103";
	/**好贷通过*/
	public final static String CREDIT_TYPE_HD_DEFAULT = "103000";
	/**好贷个人失信被执行*/
	public final static String CREDIT_TYPE_HD_GRSXBZX = "103001";
	/**好贷个人被执行公告*/
	public final static String CREDIT_TYPE_HD_ZXGRBZX = "103002";
	/**好贷借款重复查询次数信息*/
	public final static String CREDIT_TYPE_HD_DKCFSQ = "103003";
	/**好贷黑名单查询*/
	public final static String CREDIT_TYPE_HD_SYHMD = "103004";
	/**被信贷机构查询次数*/
	public final static String CREDIT_TYPE_HD_BXDJGCXJL = "103005";
	/**好贷拒贷*/
	public final static String CREDIT_TYPE_HD_REFUSE = "103006";
	
	/**白名单*/
	public final static String CREDIT_HD_WHITE = "106";
	/**白名单*/
	public final static String CREDIT_TYPE_HD_WHITE = "106001";
	/**不是白名单*/
	public final static String CREDIT_TYPE_HD_NOWHITE = "106002";
	
	/**黑名单*/
	public final static String CREDIT_HD_BLACK = "107";
	/**黑名单手机号*/
	public final static String CREDIT_TYPE_HD_BLACK_PHONE = "107001";
	/**黑名单身份证号*/
	public final static String CREDIT_TYPE_HD_BLACK_IDCARD = "107002";
	/**不是黑名单*/
	public final static String CREDIT_TYPE_HD_NOBLACK = "107003";
	
	/**姓名 */
	public final static String CREDIT_NAME = "108";
	/**姓名合理 */
	public final static String CREDIT_EFFECT_NAME = "108001";
	/**姓名不合理 */
	public final static String CREDIT_UNEFFECT_NAME = "108002";
	
	/**黑名单性质拒贷 */
	public final static String CREDIT_BLACKLIST_REFUSE = "109";
	/**命中好贷黑名单*/
	public final static String CREDIT_HD_BLACKLIST_REFUSE = "109001";
	/**命中前海黑名单 */
	public final static String CREDIT_QH_BLACKLIST_REFUSE = "109002";
	/**黑名单性质拒贷未命中 */
	public final static String CREDIT_QH_BLACKLIST_UNCHECKED = "109003";
	/**命中百融特殊名单核查 */
	public final static String CREDIT_BR_SL_BLACKLIST_REFUSE = "109004";
	/**命中91特殊名单核查 */
	public final static String CREDIT_JY_BLACKLIST_REFUSE = "109005";
	
	/** 百融征信 */
	public final static String CREDIT_BR_LIST = "110";
	/**百融特殊名单核查模块 */
	public final static String CREDIT_TYPE_BR_SPECIAL_LIST_REFUSE = "110001";
	/**百融多次申请核查模块 */
	public final static String CREDIT_TYPE_BR_APPLOAN_LIST_REFUSE = "110002";
	
	
	/**学历评分 */
	public final static String CREDIT_EDUCATION_SCORE = "111";
	/**学历评分模块 */
	public final static String CREDIT_EDUCATION_SCORE_MODEL = "111001";
	
	/**学籍信息 */
	public final static String CREDIT_CHECK_SCHOOL = "112";
	/**学籍信息是否有效模块 */
	public final static String CREDIT_CHECK_SCHOOL_MODEL = "112001";
	
	/**银联评分 */
	public final static String CREDIT_TYPE_UNIPAY_MODEL_TYPE = "113";
	/**银联评分模块 */
	public final static String CREDIT_TYPE_UNIPAY_MODEL = "113001";

	/**新生白名单 */
	public final static String CREITID_MODEL_NEW_STUDENT = "114";
	/**新生白名单征信模块 */
	public final static String CREITID_MODEL_TYPE_NEW_STUDENT = "114001";
	
	
	
	/*****************************各征信模块征信结果表code***************************************/

	
	
	/*****************************征信结果记录表code***************************************/
	
	/**初始插入*/
	public final static String CREDIT_RECORD_INIT = "200000";
	
	/**前海征信未命中黑名单*/
	public final static String CREDIT_QH_RECORD_BLACK_PASS = "201010";
	/**前海征信命中黑名单*/
	public final static String CREDIT_QH_RECORD_BLACK_UNPASS = "201020";
	
	/**前海征信好信度通过*/
	public final static String CREDIT_QH_RECORD_GOODREL_PASS = "202010";
	/**前海征信好信度未通过*/
	public final static String CREDIT_QH_RECORD_GOODREL_UNPASS = "202020";
	
	/**年龄通过*/
	public final static String CREDIT_AGE_RECORD_PASS = "203010";
	/**年龄不通过*/
	public final static String CREDIT_AGE_RECORD_UNPASS = "203020";
	
	/**91征信通过*/
	public final static String CREDIT_JY_RECORD_PASS = "204010";
	/**91征信不通过*/
	public final static String CREDIT_JY_RECORD_UNPASS = "204020";
	
	/**好贷通过*/
	public final static String CREDIT_HD_RECORD_DEAFULT_PASS = "205010";
	/**好贷未通过*/
	public final static String CREDIT_HD_RECORD_DEAFULT_UNPASS = "205020";
	/**好贷征信个人失信被执行查询通过*/
	public final static String CREDIT_HD_RECORD_GRSXBZX_PASS = "205110";
	/**好贷征信个人失信被执行查询未通过*/
	public final static String CREDIT_HD_RECORD_GRSXBZX_UNPASS = "205120";
	/**好贷征信被信贷机构查询次数通过*/
	public final static String CREDIT_HD_RECORD_BXDJGCXJL_PASS = "205210";
	/**好贷征信被信贷机构查询次数未通过*/
	public final static String CREDIT_HD_RECORD_BXDJGCXJL_UNPASS = "205220";
	/**好贷征信黑名单查询通过*/
	public final static String CREDIT_HD_RECORD_SYHMD_PASS = "205310";
	/**好贷征信黑名单查询未通过*/
	public final static String CREDIT_HD_RECORD_SYHMD_UNPASS = "205320";
	/**好贷征信借款重复查询次数通过*/
	public final static String CREDIT_HD_RECORD_DKCFSQ_PASS = "205410";
	/**好贷征信借款重复查询次数未通过*/
	public final static String CREDIT_HD_RECORD_DKCFSQ_UNPASS = "205420";
	/**好贷征信个人被执行公告查询通过*/
	public final static String CREDIT_HD_RECORD_ZXGRBZX_PASS = "205510";
	/**好贷征信个人被执行公告查询未通过*/
	public final static String CREDIT_HD_RECORD_ZXGRBZX_UNPASS = "205520";

	/**不是秒趣黑名单*/
	public final static String CREDIT_BL_RECORD_PASS = "206010";
	/**是秒趣黑名单*/
	public final static String CREDIT_BL_RECORD_UNPASS = "206020";
	/**是秒趣白名单*/
	public final static String CREDIT_WL_RECORD_PASS = "207010";
	/**不是秒趣白名单*/
	public final static String CREDIT_UNWL_RECORD_PASS = "207020";
	
	/**"前海一致性校验通过"*/
	public final static String CREDIT_QH_RECORD_SAME_PASS = "208010";
	/**"前海一致性校验明确返回未通过"*/
	public final static String CREDIT_QH_RECORD_SAME_RETURN_UNPASS = "208020";
	/**"前海一致性校验未通过"*/
	public final static String CREDIT_QH_RECORD_SAME_UNPASS = "208030";
	
	/**姓名通过*/
	public final static String CREDIT_NAME_RECORD_PASS = "209010";
	/**姓名不通过*/
	public final static String CREDIT_NAME_RECORD_UNPASS = "209020";
	
	
	/**企信征信通过*/
	public final static String CREDIT_QX_RECORD_GOODREL_PASS = "210010";
	/**企信征信未通过*/
	public final static String CREDIT_QX_RECORD_GOODREL_UNPASS = "210020";
	
	/**未命中黑名单性质拒贷规则*/
	public final static String CREDIT_RECORD_BLACK_PASS = "211010";
	/**命中黑名单性质拒贷--命中好贷黑名单*/
	public final static String CREDIT_HD_RECORD_BL_UNPASS = "211020";
	/**命中黑名单性质拒贷--命中前海黑名单*/
	public final static String CREDIT_QH_RECORD_BL_UNPASS = "211030";
	/**命中黑名单性质拒贷--百融贷不通过*/
	public final static String CREDIT_BR_RECORD_BL_UNPASS = "211040";
	/**命中黑名单性质拒贷--91征信不通过*/
	public final static String CREDIT_JY_RECORD_BL_UNPASS = "211050";
	
	
	/**前海常贷客征信通过*/
	public final static String CREDIT_QH_RECORD_LOAN_PASS = "212010";
	/**前海常贷客征信未通过*/
	public final static String CREDIT_QH_RECORD_LOAN_UNPASS = "212020";
	
	/**百融贷特殊名单通过*/
	public final static String CREDIT_BR_RECORD_SL_PASS = "213010";
	/**百融贷特殊名单不通过*/
	public final static String CREDIT_BR_RECORD_SL_UNPASS = "213020";
	
	/**百融贷多次申请核查通过*/
	public final static String CREDIT_BR_RECORD_AL_PASS = "214010";
	/**百融贷多次申请不通过*/
	public final static String CREDIT_BR_RECORD_AL_UNPASS = "214020";
	
	/**所有征信通过，最终判断通过*/
	public final static String CREDIT_RECORD_CLOSED = "000000";
	
	
	/**学历评分通过*/
	public final static String CREDIT_EDUCATE_SCORE_PASS = "215010";
	/**学历评分不通过*/
	public final static String CREDIT_EDUCATE_SCORE_UNPASS = "215020";
	
	/**学籍核查通过*/
	public final static String CREDIT_CHECK_SCHOOL_PASS = "216010";
	/**学籍核查不通过*/
	public final static String CREDIT_CHECK_SCHOOL_UNPASS = "216020";
	
	/**银联评分通过*/
	public final static String CREDIT_CHECK_UNIPAY_PASS = "217010";
	/**银联评分不通过*/
	public final static String CREDIT_CHECK_UNIPAY_UNPASS_UPA_1 = "217020";
	public final static String CREDIT_CHECK_UNIPAY_UNPASS_UPA_2 = "217030";
	public final static String CREDIT_CHECK_UNIPAY_UNPASS_UPF = "217040";
	public final static String CREDIT_CHECK_UNIPAY_UNPASS_EXCEPTION = "217050";
	
	/**新生白名单征信通过 */
	public final static String CREITID_RESULT_DESC_PASS_NEW_STUDENT = "114001010";
	/**新生白名单征信未通过 */
	public final static String CREITID_RESULT_DESC_UNPASS_NEW_STUDENT = "114001020";
	/**新生白名单征信异常 */
	public final static String CREITID_RESULT_DESC_EXCEPTION_NEW_STUDENT = "114001030";
	
	
	/*****************************征信结果记录表code***************************************/
	
	/**征信初始化错误 */
	public final static String CREDIT_INIT = "300101";
	/**查询黑名单异常 */
	public final static String CREDIT_MQ_RECORD_BLACK_ERROR = "300102";
	/**查询白名单异常 */
	public final static String CREDIT_MQ_RECORD_WHITE_ERROR = "300103";
	/**查询91异常 */
	public final static String CREDIT_JY_RECORD_ERROR = "300104";
	/**查询好贷异常 */
	public final static String CREDIT_HD_RECORD_ERROR = "300105";
	/**前海黑名单处理发生异常或错误 */
	public final static String CREDIT_QH_RECORD_BLACKEXP_ERROR = "300106";
	/**前海好信度处理发生异常或错误 */
	public final static String CREDIT_QH_RECORD_GOODRELEXPE_ERROR = "300107";
	/**查询前海黑名单异常 */
	public final static String CREDIT_QH_RECORD_BLACKE_RROR = "300108";
	/**查询前海好信度异常 */
	public final static String CREDIT_QH_RECORD_GOODREL_RROR = "300109";
	/**查询年龄异常 */
	public final static String CREDIT_AGE_RECORD_RROR = "300110";
	/**查询姓名异常 */
	public final static String CREDIT_NAME_RECORD_RROR = "300111";
	/**查询国政通结果异常 */
	public final static String CREDIT_GBOSS_RECORD_RROR = "300112";
	/**查询企信征信通结果异常 */
	public final static String CREDIT_QX_RECORD_RROR = "300113";
	/**查询前海好信度，反馈信息为空 */
	public final static String CREDIT_QH_RECORD_HXDEMPTY_ERROR = "300114";
	/**查询前海好信度，交易代码异常 */
	public final static String CREDIT_QH_RECORD_HSDCODE_ERROR = "300115";
	/**查询前海好好信一鉴通异常 */
	public final static String CREDIT_QH_RECORD_HXYJT_ERROR = "300116";
	/**前海好好信一鉴通处理发生异常或错误异常 */
	public final static String CREDIT_QH_RECORD_HXYJTEXP_ERROR = "300117";
	/**前海常贷客接口处理发生异常或错误 */
	public final static String CREDIT_QH_RECORD_LOANEXP_ERROR = "300118";
	/**百融贷特殊名单核查接口处理发生异常或错误 */
	public final static String CREDIT_BR_RECORD_SPECIALEXP_ERROR = "300119";
	/**百融贷多次申请核查接口处理发生异常或错误 */
	public final static String CREDIT_BR_RECORD_APPLOANEXP_ERROR = "300120";
	/**学历评分处理发生异常或错误 */
	public final static String CREDIT_XL_RECORD_SCOREEXP_ERROR = "300121";
	/**黑名单性质拒贷发生异常或错误 */
	public final static String CREDIT_BL_HISTORY_EXP_ERROR = "300122";
	
	/*****************************征信结果抛出异常code***************************************/

	
	
}
