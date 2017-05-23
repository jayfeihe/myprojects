/**清理数据和初始化索引**/
DELETE FROM T_MENU;
DROP SEQUENCE T_MENU_SEQ ;
CREATE SEQUENCE T_MENU_SEQ INCREMENT BY 1 START WITH 1; 

/***插入数据***/
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '根菜单', '根菜单', NULL, '0', '0', '0', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '功能管理', '功能管理', NULL, '1', '0', '1', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '审批流程', '审批流程', NULL, '1', '0', '2', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '稽查', '稽查', NULL, '1', '0', '3', '1');    
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '贷后', '贷后管理', NULL, '1', '0', '4', '1');  
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '财务报表', '财务报表', NULL, '1', '0', '5', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '系统管理', '系统管理', NULL, '1', '0', '6', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '历史数据统计', '历史数据统计',NULL, '1', '0', '7', '1');
/********** 功能管理菜单，parentId为2 ***********/                                                                   
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '仓库维护', '仓库维护', 'warehouse/query.do', '2', '1', '1', '1');  
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '出借人维护', '出借人维护', 'lenduser/query.do', '2', '1', '2', '1'); 
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '档案管理', '档案管理', 'archive/query.do', '2', '1', '3', '1');  
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '档案查看', '档案查看', 'archive/view/query.do', '2', '1', '4', '1'); 
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '线上状态', '线上状态', 'onLineStates/query.do', '2', '1', '5', '1');
/********** 审批流程菜单，parentId为3 ***********/   
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '申请管理', '申请管理', 'loan/query.do', '3', '1', '1', '1');                                           
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '分公司审批', '分公司审批', 'branch/query.do', '3', '1', '2', '1');                                           
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '风控初审', '风控初审', 'risk/first/query.do', '3', '1', '3', '1');            
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '评审会初审', '评审会初审', 'judge/first/query.do', '3', '1', '4', '1'); 
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '评审会复核', '评审会复核', 'judge/review/query.do', '3', '1', '5', '1');        
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '评审会意见', '评审会意见', 'judge/advice/query.do', '3', '1', '6', '1');        
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '风控复核', '风控复核', 'risk/review/query.do', '3', '1', '7', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '法务初审', '法务初审', 'law/first/query.do', '3', '1', '8', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '法务内勤', '法务内勤', 'law/inside/query.do', '3', '1', '9', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '法务复核', '法务复核', 'law/review/query.do', '3', '1', '10', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '出纳贷前核帐', '出纳贷前核帐', 'account/preloan/query.do', '3', '1', '11', '1');   
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '财务审批', '财务审批', 'account/finance/query.do', '3', '1', '12', '1'); 
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '财务复核', '财务复核', 'account/financeReview/query.do', '3', '1', '13', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '线下放款', '线下放款', 'account/offline/query.do', '3', '1', '14', '1');   
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '法律意见', '法律意见', 'law/advice/query.do', '3', '1', '15', '1');  
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '推送线上', '推送线上', 'push/query.do', '3', '1', '16', '1');  
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '线上放款', '线上放款', 'account/online/query.do', '3', '1', '17', '1');       
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '出纳贷后核帐', '出纳贷后核帐', 'account/afterloan/query.do', '3', '1', '18', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '贷后垫付处理', '贷后垫付处理', 'account/payout/query.do', '3', '1', '19', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '保证金处理', '保证金处理', 'account/magin/query.do', '3', '1', '20', '1');   
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '续贷申请', '续贷申请', 'loan/renew/query.do', '3', '1', '21', '1');           
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '综合查询', '综合查询', 'integrated/query.do', '3', '1', '22', '1');           

/********** 稽查菜单，parentId为4 ***********/    
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '稽查任务', '稽查任务', 'search/task/query.do', '4', '1', '1', '1');              
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '稽查清单', '稽查清单', 'search/afterloan/query.do', '4', '1', '2', '1');                             
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '分公司逾期处理', '分公司逾期处理', 'overdue/query.do', '4', '1', '3', '1');   

/********** 贷后菜单，parentId为5***********/ 
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '资产管理', '资产管理', 'asset/manage/query.do', '5', '1', '1', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '业务员催收', '业务员催收', 'overdue/query.do', '5', '1', '2', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '贷后催收', '贷后催收', 'cllt/query.do', '5', '1', '3', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '催收分单', '催收分单', 'cllt/queryAll.do', '5', '1', '4', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '贷后综合查询', '贷后综合查询', 'afterLoanQ/query.do', '5', '1', '5', '1');

/********** 财务报表菜单，parentId为6***********/ 
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '分公司每月成交量统计表', '分公司每月成交量统计表', 'report/reportDeal/query.do', '6', '1', '1', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '分公司成交量统计表', '分公司成交量统计表', 'report/branchDeal/query.do', '6', '1', '2', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '分公司存量统计表', '分公司存量统计表', 'report/branchStock/query.do', '6', '1', '3', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '分公司逾期统计表', '分公司逾期统计表', 'report/reportOverdue/query.do', '6', '1', '4', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '抵押物台账', '抵押物台账', 'report/collateralAccount/query.do', '6', '1', '5', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '借款人基本情况表', '借款人基本情况表', 'report/loanManBase/query.do', '6', '1', '6', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '网络下放资金明细表', '网络下放资金明细表', 'report/netFunds/query.do', '6', '1', '7', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '项目明细表', '项目明细表', 'report/projectDetail/query.do', '6', '1', '8', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '逾期明细表', '逾期明细表', 'report/overdueDetail/query.do', '6', '1', '9', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '流水表', '流水表', 'report/billAcctFlow/query.do', '6', '1', '10', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '线下线上垫付表', '线下线上垫付表', 'report/prepay/query.do', '6', '1', '11', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '线下还本计划表', '线下还本计划表', 'report/offlinerepay/query.do', '6', '1', '12', '1');
INSERT INTO "T_MENU"  VALUES (T_MENU_SEQ.NEXTVAL, '法律服务费统计报表', '法律服务费统计报表', 'report/lawFee/query.do', '6', '1', '13', '1');

/********** 系统管理菜单，parentId为7 ***********/
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '用户管理', '用户管理', 'sys/user/query.do', '7', '1', '1', '1');          
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '角色管理', '角色管理', 'sys/role/query.do', '7', '1', '2', '1');          
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '菜单管理', '菜单管理', 'sys/menu/query.do', '7', '1', '3', '1');          
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '组织机构', '组织机构', 'sys/org/query.do', '7', '1', '4', '1');           
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '机构配置', '机构配置', 'sys/orgconfig/query.do', '7', '1', '5', '1'); 
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '部门管理', '部门管理', 'sys/dept/query.do', '7', '1', '6', '1');    
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '数据字典', '数据字典', 'sys/datadictionary/query.do', '7', '1', '7', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '参数配置', '参数配置', 'sys/parameter/query.do', '7', '1', '8', '1');     
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '日志查询', '日志查询', 'sys/log/query.do', '7', '1', '9', '1');
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '接口日志', '接口日志', 'interfaceLog/query.do', '7', '1', '10', '1'); 
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '公告管理', '公告管理', 'sys/bulletin/query.do', '7', '1', '11', '1');

/********** 历史数据统计菜单，parentId为8 ***********/
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '历史项目明细', '历史项目明细', 'projectInfoDetail/query.do', '8', '1', '1', '1');          
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '分公司每月成交量统计', '分公司每月成交量统计', 'stastics/monDeal/query.do', '8', '1', '2', '1');          
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '分公司成交量统计', '分公司成交量统计', 'stastics/deals/query.do', '8', '1', '3', '1');          
INSERT INTO "T_MENU" VALUES (T_MENU_SEQ.NEXTVAL, '分公司存量统计', '分公司存量统计', 'stastics/saveDeals/query.do', '8', '1', '4', '1');           

 
