INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'productType', '01', '车贷', '产品类型', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'productType', '02', '车商贷', '产品类型', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'productType', '03', '房贷', '产品类型', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'productType', '04', '红木贷', '产品类型', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'productType', '05', '海鲜贷', '产品类型', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'productType', '99', '其他', '产品类型', '');


INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'relation', '1', '父母', '联系人关系', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'relation', '2', '配偶', '联系人关系', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'relation', '3', '子女', '联系人关系', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'relation', '4', '亲属', '联系人关系', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'relation', '5', '朋友', '联系人关系', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'relation', '6', '同事', '联系人关系', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'relation', '7', '同学', '联系人关系', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'relation', '8', '本人', '联系人关系', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'relation', '99','其他', '联系人关系', '');

INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'repayMethod', '01', '利息等额', '还款方式', '');       
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'repayMethod', '02','利息先部分后等额', '还款方式', '');

INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'idType', '01', '身份证',  '证件类型', '');       
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'idType', '02', '军官证','证件类型', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'idType', '03', '驾驶证','证件类型', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'idType', '04', '营业执照',  '证件类型', '');       
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'idType', '05', '组织机构代码证','证件类型', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'idType', '06', '税务登记证',  '证件类型', '');   

INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'marriage', '01', '未婚', '婚姻状况', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'marriage', '02', '已婚', '婚姻状况', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'marriage', '03', '离异', '婚姻状况', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'marriage', '04', '丧偶', '婚姻状况', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'marriage', '99', '其他', '婚姻状况', '');

INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'education', '01', '博士', '学历', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'education', '02', '研究生', '学历', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'education', '03', '本科', '学历', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'education', '04', '专科', '学历', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'education', '05', '高中', '学历', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'education', '06', '初中及以下', '学历', '');

INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'live', '01', '自有房产', '居住情况', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'live', '02', '租赁', '居住情况', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'live', '03', '与亲属同住', '居住情况', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'live', '04', '公司宿舍', '居住情况', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'live', '99', '其他', '居住情况', '');

INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'nativeHouse', '01', '无房', '房产情况', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'nativeHouse', '02', '有房无贷款', '房产情况', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'nativeHouse', '03', '有房有贷款', '房产情况', '');

INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'companyType', '01', '国有企业',  '单位类型', '');       
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'companyType', '02', '机关事业','单位类型', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'companyType', '03', '外资','单位类型', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'companyType', '04', '合资',  '单位类型', '');       
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'companyType', '05', '民营','单位类型', '');
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'companyType', '06', '个体',  '单位类型', ''); 
INSERT INTO `t_data_dictionary` VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'companyType', '99', '其他', '单位类型', '');

/*******************************************************以下是图片类型字典****************************************************************/
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce1', 'A', '身份证'                      ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce1', 'B', '户口本'                      ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce1', 'C', '结婚/离婚证'                 ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce1', 'D', '个人信用报告'                ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce1', 'E', '个人对外投资情况'            ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce1', 'F', '个人民间借贷情况'            ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce1', 'G', '个人负债及对外担保情况'      ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce1', 'H', '所有物抵/质押情况'           ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce1', 'I', '个人行政处罚情况'            ,'个人申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce1', 'J', '行政机构对个人的奖惩情况'     ,'个人申请', '');


INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'A', '法定代表人身份证'            	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'B', '实际控制人身份证'            	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'C', '主要管理者身份证'            	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'D', '营业执照'                    	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'E', '组织机构代码证'              	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'F', '税务登记证'                  	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'G', '企业贷款卡记录'              	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'H', '实际控制人个人信用报告'      	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'I', '企业对外投资情况'            	,'公司申请', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'J', '重大关联交易情况'            	,'公司申请', '');   
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'K', '企业民间借贷情况'            	,'公司申请', '');  
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'L', '企业负债及对外担保情况'      	,'公司申请', '');  
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'M', '所有物抵/质押情况'           	,'公司申请', ''); 
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'N', '企业及实际控制人行政处罚情况'	,'公司申请', '');  
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce2', 'O', '行政机构对企业的奖惩情况'    	,'公司申请', ''); 

INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce3', 'A', '身份证','共同借款人', '');   

INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce4', 'A', '现场照片'            	                    ,'质抵押物', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce4', 'B', '市场评估报告扫描文件'            	        ,'质抵押物', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce4', 'C', '所有权证、土地使用权证、他项权证扫描文件'    ,'质抵押物', '');


INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce5', 'A', '身份证'       ,'个人担保人', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce5', 'B', '保证函'       ,'个人担保人', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce5', 'C', '个人信用报告' ,'个人担保人', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce5', 'D', '资产证明'     ,'个人担保人', '');

INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce6', 'A', '保证函'         ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce6', 'B', '股东会决议'     ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce6', 'C', '营业执照'       ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce6', 'D', '组织机构代码证' ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce6', 'E', '税务登记证'     ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce6', 'F', '法人身份证'     ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce6', 'G', '股东身份证'     ,'机构担保人', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce6', 'H', '资产证明'      	,'机构担保人', '');

INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce7', 'A', '诉讼情况证明'   ,'诉讼情况', ''); 

INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce8', 'A', '身份证'       ,'出借人', '');
INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce8', 'B', '户口本'       ,'出借人', '');

INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce9', 'A', '合同'   ,'法务处理', '');

INSERT INTO t_data_dictionary VALUES (nextval('T_DATA_DICTIONARY_SEQ'), 'filesce11', 'A', '资产处置' ,'资产处置', '');