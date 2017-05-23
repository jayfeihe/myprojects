/**参数*/

INSERT INTO T_PARAMETER VALUES (T_PARAMETER_SEQ.NEXTVAL, 'onlineip', 'http://172.16.33.47:80', '线上系统对接ip和端口');
INSERT INTO T_PARAMETER VALUES (T_PARAMETER_SEQ.NEXTVAL, 'loanAmount', '1000000', '财务审批放款额度');

INSERT INTO t_bpm_def VALUES (T_BPM_DEF_SEQ.nextval, 'config/bpm/auditbpm.xml', 'auditbpm', '审批流程');
INSERT INTO t_org VALUES (T_ORG_SEQ.nextval, '86', '总公司', '总公司', 'ZB', '0', '0.00', '0.00', '0.0000', null, null, null);
INSERT INTO T_USER VALUES (T_USER_SEQ.nextval, 'admin', '系统管理员', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, NULL, '1', '1');

/**角色初始化**/
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '业务员'    , '业务员'    , '2', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '分公司经理', '分公司经理', '2', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '风控经理'  , '风控经理'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '风控总监'  , '风控总监'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '评审会秘书', '评审会秘书', '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '评审会评委', '评审会评委', '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '法务专员'  , '法务专员'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '内勤专员'  , '内勤专员'  , '2', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '出纳专员'  , '出纳专员'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '财务专员'  , '财务专员'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '财务经理'  , '财务经理'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '贷后主管'  , '贷后主管'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '催收专员'  , '催收专员'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '稽查专员'  , '稽查专员'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '推送专员'  , '推送专员'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (T_ROLE_SEQ.nextval, '档案专员'  , '档案专员'  , '1', '1', NULL, NULL);

/**节点初始化**/
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '1', '1', '录入申请');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '1', '2', '分公司审批');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '1', '3', '风控经理审批');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '1', '4', '风控总监审批');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '1', '5', '法务初审');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '1', '6', '法务内勤');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '1', '7', '法务复核');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '1', '8', '出纳核账');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '1', '9', '财务审批');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '2', '1', '录入申请');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '2', '2', '分公司审批');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '2', '3', '评审会初审');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '2', '4', '评审会复核');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '2', '5', '风控总监审批');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '2', '6', '法务初审');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '2', '7', '法务内勤');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '2', '8', '法务复核');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '2', '9', '出纳核账');
INSERT INTO t_node_order VALUES (T_NODE_ORDER_SEQ.nextval, '2', '10', '财务审批');

/**数据字典初始化**/
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'productType', '01', '车贷', '产品类型', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'productType', '02', '车商贷', '产品类型', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'productType', '03', '房贷', '产品类型', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'productType', '04', '红木贷', '产品类型', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'productType', '05', '海鲜贷', '产品类型', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'productType', '99', '其他', '产品类型', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'relation', '1', '父母', '联系人关系', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'relation', '2', '配偶', '联系人关系', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'relation', '3', '子女', '联系人关系', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'relation', '4', '亲属', '联系人关系', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'relation', '5', '朋友', '联系人关系', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'relation', '6', '同事', '联系人关系', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'relation', '7', '同学', '联系人关系', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'relation', '8', '本人', '联系人关系', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'relation', '99','其他', '联系人关系', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'repayMethod', '01', '利息等额', '还款方式', '');       
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'repayMethod', '02','利息先部分后等额', '还款方式', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'idType', '01', '身份证',  '证件类型', '');       
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'idType', '02', '军官证','证件类型', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'idType', '03', '驾驶证','证件类型', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'idType', '04', '营业执照',  '证件类型', '');       
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'idType', '05', '组织机构代码证','证件类型', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'idType', '06', '税务登记证',  '证件类型', '');   

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'marriage', '01', '未婚', '婚姻状况', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'marriage', '02', '已婚', '婚姻状况', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'marriage', '03', '离异', '婚姻状况', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'marriage', '04', '丧偶', '婚姻状况', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'marriage', '99', '其他', '婚姻状况', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'education', '10', '博士', '学历', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'education', '9', '硕士', '学历', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'education', '7', '本科', '学历', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'education', '6', '大专', '学历', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'education', '5', '中专', '学历', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'education', '3', '高中', '学历', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'education', '2', '初中', '学历', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'education', '1', '小学', '学历', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'live', '01', '自有房产', '居住情况', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'live', '02', '租赁', '居住情况', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'live', '03', '与亲属同住', '居住情况', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'live', '04', '公司宿舍', '居住情况', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'live', '99', '其他', '居住情况', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'nativeHouse', '01', '无房', '房产情况', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'nativeHouse', '02', '有房无贷款', '房产情况', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'nativeHouse', '03', '有房有贷款', '房产情况', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'companyType', '01', '国有企业',  '单位类型', '');       
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'companyType', '02', '机关事业','单位类型', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'companyType', '03', '外资','单位类型', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'companyType', '04', '合资',  '单位类型', '');       
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'companyType', '05', '民营','单位类型', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'companyType', '06', '个体',  '单位类型', ''); 
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'companyType', '99', '其他', '单位类型', '');

/*******************************************************以下是图片类型字典****************************************************************/
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce1', 'A', '身份证'                      ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce1', 'B', '户口本'                      ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce1', 'C', '结婚/离婚证'                 ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce1', 'D', '个人信用报告'                ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce1', 'E', '个人对外投资情况'            ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce1', 'F', '个人民间借贷情况'            ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce1', 'G', '个人负债及对外担保情况'      ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce1', 'H', '所有物抵/质押情况'           ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce1', 'I', '个人行政处罚情况'            ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce1', 'J', '行政机构对个人的奖惩情况'     ,'个人申请', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'A', '法定代表人身份证'            	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'B', '实际控制人身份证'            	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'C', '主要管理者身份证'            	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'D', '营业执照'                    	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'E', '组织机构代码证'              	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'F', '税务登记证'                  	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'G', '企业贷款卡记录'              	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'H', '实际控制人个人信用报告'      	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'I', '企业对外投资情况'            	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'J', '重大关联交易情况'            	,'公司申请', '');   
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'K', '企业民间借贷情况'            	,'公司申请', '');  
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'L', '企业负债及对外担保情况'      	,'公司申请', '');  
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'M', '所有物抵/质押情况'           	,'公司申请', ''); 
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'N', '企业及实际控制人行政处罚情况'	,'公司申请', '');  
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce2', 'O', '行政机构对企业的奖惩情况'    	,'公司申请', ''); 

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce3', 'A', '身份证','共同借款人', '');   

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce4', 'A', '现场照片'            ,'质抵押物', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce4', 'B', '市场评估报告扫描文件' ,'质抵押物', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce4', 'C', '所有权证'    			,'质抵押物', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce4', 'D', '土地使用权证'    		,'质抵押物', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce4', 'E', '其他项权证扫描文件'   ,'质抵押物', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce4', 'F', '行驶证'   			,'质抵押物', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce5', 'A', '身份证'       ,'个人担保人', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce5', 'B', '保证函'       ,'个人担保人', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce5', 'C', '个人信用报告' ,'个人担保人', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce5', 'D', '资产证明'     ,'个人担保人', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce6', 'A', '保证函'         ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce6', 'B', '股东会决议'     ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce6', 'C', '营业执照'       ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce6', 'D', '组织机构代码证' ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce6', 'E', '税务登记证'     ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce6', 'F', '法人身份证'     ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce6', 'G', '股东身份证'     ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce6', 'H', '资产证明'      	,'机构担保人', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce7', 'A', '诉讼情况证明'   ,'诉讼情况', ''); 

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce8', 'A', '身份证'       ,'出借人', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce8', 'B', '户口本'       ,'出借人', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce9', 'A', '合同'   ,'法务内勤', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce9', 'B', '质/抵押设立证明'   ,'法务内勤', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce10', 'A', '推送线上'   ,'推送线上', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce11', 'A', '资产处置' ,'资产处置', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce12', 'A', '打款凭证' ,'打款凭证', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce13', 'A', '法律意见书' ,'法律意见书', '');

INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce14', 'A', '合同' ,'档案管理', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce14', 'B', '收条' ,'档案管理', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce14', 'C', '保证函' ,'档案管理', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce14', 'D', '法律意见书' ,'档案管理', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce14', 'E', '债权协议' ,'档案管理', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce14', 'F', '身份信息' ,'档案管理', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce14', 'G', '抵/质押物证明文件' ,'档案管理', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce14', 'H', '抵/质押物照片' ,'档案管理', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce14', 'I', '业务审批单' ,'档案管理', '');
INSERT INTO t_data_dictionary VALUES (T_DATA_DICTIONARY_SEQ.nextval, 'filesce14', 'J', '其他' ,'档案管理', '');