/************************************* 创建索引   ***************************************/
CREATE INDEX IDX_T_ACCT_APP_LOAN_ID ON T_ACCT_APP (LOAN_ID ASC);
CREATE INDEX IDX_T_ACCT_APP_CONTRACT_ID ON T_ACCT_APP (CONTRACT_ID ASC);
          
CREATE INDEX IDX_T_BILL_ACCT_LOAN_ID ON T_BILL_ACCT (LOAN_ID ASC);
CREATE INDEX IDX_T_BILL_ACCT_CONTRACT_ID ON T_BILL_ACCT (CONTRACT_ID ASC);
 
CREATE INDEX IDX_T_BILL_BASE_LOAN_ID ON T_BILL_BASE (LOAN_ID ASC); 
CREATE INDEX IDX_T_BILL_BASE_CONTRACT_ID ON T_BILL_BASE (CONTRACT_ID ASC);

CREATE INDEX IDX_T_BILL_ONLINE_LOAN_ID ON T_BILL_ONLINE (LOAN_ID ASC);  
CREATE INDEX IDX_T_BILL_ONLINE_CONTRACT_ID ON T_BILL_ONLINE (CONTRACT_ID ASC); 

CREATE INDEX IDX_T_BPM_DEF_PROCESS_NAME ON T_BPM_DEF (PROCESS_NAME ASC);

CREATE INDEX IDX_T_BPM_LOG_TASK_ID ON T_BPM_LOG (TASK_ID ASC);
CREATE INDEX IDX_T_BPM_LOG_BIZ_KEY ON T_BPM_LOG (BIZ_KEY ASC); 

CREATE INDEX IDX_T_BPM_TASK_DEF_ID ON T_BPM_TASK (DEF_ID ASC);  
CREATE INDEX IDX_T_BPM_TASK_BIZ_KEY ON T_BPM_TASK (BIZ_KEY ASC);

CREATE INDEX IDX_T_CLLT_DISTR_CONTRACT_ID ON T_CLLT_DISTR (CONTRACT_ID ASC); 

CREATE INDEX IDX_T_CLLT_LOG_LOAN_ID ON T_CLLT_LOG (LOAN_ID ASC); 
CREATE INDEX IDX_T_CLLT_LOG_CONTRACT_ID ON T_CLLT_LOG (CONTRACT_ID ASC);

CREATE INDEX IDX_T_COLLATERAL_LOAN_ID ON T_COLLATERAL (LOAN_ID ASC); 

CREATE INDEX IDX_T_COLLA_CHECK_COLLA_ID ON T_COLLATERAL_CHECK (COLLATERAL_ID ASC);

CREATE INDEX IDX_COLLA_PRI_ADT_COLLA_ID ON T_COLLATERAL_PRICE_AUDIT (COLLATERAL_ID ASC);

CREATE INDEX IDX_T_COLLA_PRI_CHA_COLLA_ID ON T_COLLATERAL_PRICE_CHANGE (COLLATERAL_ID ASC); 

CREATE INDEX IDX_T_CONTACT_LOAN_ID ON T_CONTACT (LOAN_ID ASC); 

CREATE INDEX IDX_T_CONTRACT_LOAN_ID ON T_CONTRACT (LOAN_ID ASC); 
CREATE INDEX IDX_T_CONTRACT_CONTRACT_ID ON T_CONTRACT (CONTRACT_ID ASC);  

CREATE INDEX IDX_T_CONT_ONLINE_LOAN_ID ON T_CONTRACT_ONLINE (LOAN_ID ASC); 
CREATE INDEX IDX_T_CONT_ONLINE_CONTRACT_ID ON T_CONTRACT_ONLINE (CONTRACT_ID ASC); 
CREATE INDEX IDX_T_CONT_ONLINE_LINE_CON_ID ON T_CONTRACT_ONLINE (ONLINE_CON_ID ASC); 

CREATE INDEX IDX_T_DATA_DICT_KEY_NAME ON T_DATA_DICTIONARY (KEY_NAME ASC);   
CREATE INDEX IDX_T_DATA_DICT_PARE_KEY ON T_DATA_DICTIONARY (PARENT_KEY_PROP ASC);

CREATE INDEX IDX_T_DEPT_DEPT_ID ON T_DEPT (DEPT_ID ASC); 

CREATE INDEX IDX_T_FILES_LOAN_ID ON T_FILES (LOAN_ID ASC);
CREATE INDEX IDX_T_FILES_BIZ_KEY ON T_FILES (BIZ_KEY ASC);
CREATE INDEX IDX_T_FILES_SCENE_TYPE ON T_FILES (SCENE_TYPE ASC);
 
CREATE INDEX IDX_T_JUDGE_ADV_LOAN_ID ON T_JUDGE_ADV (LOAN_ID ASC); 

CREATE INDEX IDX_T_LEND_USER_LOG_LUR_ID ON T_LEND_USER_LOG (LEND_USER_ID ASC); 

CREATE INDEX IDX_T_LOAN_APP_LOAN_ID ON T_LOAN_APP (LOAN_ID ASC); 

CREATE INDEX IDX_T_LOAN_BASE_LOAN_ID ON T_LOAN_BASE (LOAN_ID ASC);
 
CREATE INDEX IDX_T_LOAN_GUAR_LOAN_ID ON T_LOAN_GUAR (LOAN_ID ASC); 

CREATE INDEX IDX_T_MENU_PARENT_ID ON T_MENU (PARENT_ID ASC);  

CREATE INDEX IDX_T_ORG_ORG_ID ON T_ORG (ORG_ID ASC); 

CREATE INDEX IDX_T_OVERDUE_ACC_LOAN_ID ON T_OVERDUE_ACCEPT (LOAN_ID ASC);  
CREATE INDEX IDX_T_OVERDUE_ACC_CONTRACT_ID ON T_OVERDUE_ACCEPT (CONTRACT_ID ASC);

CREATE INDEX IDX_T_OVERDUE_RPT_LOAN_ID ON T_OVERDUE_REPORT (LOAN_ID ASC); 
CREATE INDEX IDX_T_OVERDUE_RPT_CONTRACT_ID ON T_OVERDUE_REPORT (CONTRACT_ID ASC); 

CREATE INDEX IDX_T_PARAMETER_PARAM_NAME ON T_PARAMETER (PARAM_NAME ASC); 

CREATE INDEX IDX_T_RET_PLAN_LOAN_ID ON T_RET_PLAN (LOAN_ID ASC);  
CREATE INDEX IDX_T_RET_PLAN_CONTRACT_ID ON T_RET_PLAN (CONTRACT_ID ASC); 

CREATE INDEX IDX_T_SCL_LOAN_ID ON T_SALES_CHECK_LOG (LOAN_ID ASC); 
CREATE INDEX IDX_T_SCL_CONTRACT_ID ON T_SALES_CHECK_LOG (CONTRACT_ID ASC);

/************************************* 删除索引   ***************************************/
DROP INDEX IDX_T_ACCT_APP_LOAN_ID;          
DROP INDEX IDX_T_ACCT_APP_CONTRACT_ID;      
          
DROP INDEX IDX_T_BILL_ACCT_LOAN_ID;         
DROP INDEX IDX_T_BILL_ACCT_CONTRACT_ID;     
 
DROP INDEX IDX_T_BILL_BASE_LOAN_ID;         
DROP INDEX IDX_T_BILL_BASE_CONTRACT_ID;     

DROP INDEX IDX_T_BILL_ONLINE_LOAN_ID;       
DROP INDEX IDX_T_BILL_ONLINE_CONTRACT_ID;   

DROP INDEX IDX_T_BPM_DEF_PROCESS_NAME;      

DROP INDEX IDX_T_BPM_LOG_TASK_ID;           
DROP INDEX IDX_T_BPM_LOG_BIZ_KEY;           

DROP INDEX IDX_T_BPM_TASK_DEF_ID;           
DROP INDEX IDX_T_BPM_TASK_BIZ_KEY;          

DROP INDEX IDX_T_CLLT_DISTR_CONTRACT_ID;    

DROP INDEX IDX_T_CLLT_LOG_LOAN_ID;          
DROP INDEX IDX_T_CLLT_LOG_CONTRACT_ID;      

DROP INDEX IDX_T_COLLATERAL_LOAN_ID;        

DROP INDEX IDX_T_COLLA_CHECK_COLLA_ID;      

DROP INDEX IDX_COLLA_PRI_ADT_COLLA_ID;      

DROP INDEX IDX_T_COLLA_PRI_CHA_COLLA_ID;    

DROP INDEX IDX_T_CONTACT_LOAN_ID;           

DROP INDEX IDX_T_CONTRACT_LOAN_ID;          
DROP INDEX IDX_T_CONTRACT_CONTRACT_ID;      

DROP INDEX IDX_T_CONT_ONLINE_LOAN_ID;       
DROP INDEX IDX_T_CONT_ONLINE_CONTRACT_ID;   
DROP INDEX IDX_T_CONT_ONLINE_LINE_CON_ID;   

DROP INDEX IDX_T_DATA_DICT_KEY_NAME;        
DROP INDEX IDX_T_DATA_DICT_PARE_KEY;        

DROP INDEX IDX_T_DEPT_DEPT_ID;              

DROP INDEX IDX_T_FILES_LOAN_ID;             
DROP INDEX IDX_T_FILES_BIZ_KEY;             
DROP INDEX IDX_T_FILES_SCENE_TYPE;          
 
DROP INDEX IDX_T_JUDGE_ADV_LOAN_ID;         

DROP INDEX IDX_T_LEND_USER_LOG_LUR_ID;      

DROP INDEX IDX_T_LOAN_APP_LOAN_ID;          

DROP INDEX IDX_T_LOAN_BASE_LOAN_ID;         
 
DROP INDEX IDX_T_LOAN_GUAR_LOAN_ID;         

DROP INDEX IDX_T_MENU_PARENT_ID;            

DROP INDEX IDX_T_ORG_ORG_ID;                

DROP INDEX IDX_T_OVERDUE_ACC_LOAN_ID;       
DROP INDEX IDX_T_OVERDUE_ACC_CONTRACT_ID;   

DROP INDEX IDX_T_OVERDUE_RPT_LOAN_ID;       
DROP INDEX IDX_T_OVERDUE_RPT_CONTRACT_ID;   

DROP INDEX IDX_T_PARAMETER_PARAM_NAME;      

DROP INDEX IDX_T_RET_PLAN_LOAN_ID;          
DROP INDEX IDX_T_RET_PLAN_CONTRACT_ID;      

DROP INDEX IDX_T_SCL_LOAN_ID;               
DROP INDEX IDX_T_SCL_CONTRACT_ID;