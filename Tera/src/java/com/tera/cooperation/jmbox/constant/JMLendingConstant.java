package com.tera.cooperation.jmbox.constant;

/**
 * 积木盒子  静态配置类
 * @author XunXiake
 *
 */
public class JMLendingConstant {


	/*测试环境配置*/
	public final static String LENDING_URL="http://jm.in.76hui.com:8081/API/PushData/Send";
	public final static String SPID = "12002";
	public final static String USERKEY = "b3938710d2b81374c0db3f1637891f47";
	
	public final static String QUERY_ID = "hedao37";
	public final static String QUERY_KEY = "Xg9EknPLz12ngc45w32VMXsF2omh2PGs";
	public final static String PROJEC_QUERY_URL="https://co-test.jimubox.com/Interface76hui/QueryProjectStatus";		 //项目查询
	public final static String REPAY_PLAN_QUERY_URL="https://co-test.jimubox.com/Interface76hui/QueryRepaymentPlan";	 //还款计划查询
	public final static String CHANNAL_DUPLICATE_URL = "https://co-test.jimubox.com/Interface76hui/QueryProjectIsExist"; //	渠道查重URL
	
//	访问有IP限制 只能  114.215.176.153  112.124.126.51
	public final static String DUEINFO_URL="http://101.251.251.10:9090/overdueCustomers";
	public final static String DUEINFO_ID = "hdrt";
	public final static String DUEINFO_KEY = "673Bh4kmNlZNdh0B9aFXnI077Z4vJ5Sa";
	
	
	
	/*生产环境配置
	public final static String LENDING_URL="http://jm.in.76hui.com/API/PushData/Send";
	public final static String SPID = "12002";
	public final static String USERKEY = "f4d0f5b635fcee5ba6eb307f148120ee";
	
	public final static String QUERY_ID = "hedao37";
	public final static String QUERY_KEY = "Xg9EknPLz12ngc45w32VMXsF2omh2PGs";
	public final static String PROJEC_QUERY_URL="https://cooperation.jimubox.com/Interface76hui/QueryProjectStatus";
	public final static String REPAY_PLAN_QUERY_URL="https://cooperation.jimubox.com/Interface76hui/QueryRepaymentPlan";
	public final static String CHANNAL_DUPLICATE_URL = "https://cooperation.jimubox.com/Interface76hui/QueryProjectIsExist"; //	渠道查重URL

//	 访问有IP限制 只能  114.215.176.153  112.124.126.51 
	public final static String DUEINFO_URL="http://101.251.251.10:9090/overdueCustomers";
	public final static String DUEINFO_ID = "hdrt";
	public final static String DUEINFO_KEY = "673Bh4kmNlZNdh0B9aFXnI077Z4vJ5Sa";
	*/

	
	
	
	/**  积木盒子 附件 必选分类
	 *  应传 镜像资料
	 *  身份证明	B01类
	 *  征信证明	E01类
	 *  居住证明	F类
	 *  工作证明	D类
	 *  收入证明/银行流水	C类（如无取G19类）
	 *  经营证明	G01类（如无取G02类）
	 */
//	String[] CLASS_TYPE=new String[]{"B01","E01","F","D","C_G19","G01_G02","K"};
	public final static String[] CLASS_TYPES=new String[]{"B01","E01","F","D","G01_G02"};
	
	/** 积木盒子 附件 可选分类
	 	1	身份证明	B01
		2	征信证明	E01
		3	居住证明	F类
		4	工作证明	D类
		5	收入证明/银行流水	C类（如无取G19）
		6	经营证明	G01（如无取G02）
		7	申请表	A类
		8	经营地址证明	G20+G12或G21+G12
		9	实地征信	L类
		10	资产证明	K类
	 */
//	public final static String[] INCOME_CLASS_TYPES=new String[]{"C_G19"};
//	public final static String[] INCOME_CLASS_TYPES=new String[]{"B01","E01","F","D","C_G19","G01_G02","A","G12","L","K","G20_G21"};
	//工薪贷  附件优先级   第1条、第7条、第2条、第3条、第4条、第5条、第10条
	public final static String[] CLASS_ORDER_PAYROLL=new String[]{"B01","A","E01","F","D","C_G19","K"};
	//精英贷  附件优先级 第1条、第7条、第2条、第4条、第5条、第3条、第10条
	public final static String[] CLASS_ORDER_ELITE=new String[]{"B01","A","E01","D","C_G19","F","K"};
	//业主贷  附件优先级  第1条、第7条、第2条、第3条、第6条、第8条、第5条、第9条、第10条
	public final static String[] CLASS_ORDER_OWNER=new String[]{"B01","A","E01","F","G01_G02","G12","G20_G21","C_G19","L","K"};
	
	
	
}
