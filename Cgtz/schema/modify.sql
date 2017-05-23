ALTER TABLE t_loan_base ADD DE_RATE decimal(18,2) DEFAULT '0.00' COMMENT '等额利率' AFTER RATE;

ALTER TABLE t_bpm_log ADD DECISION varchar(50) DEFAULT NULL COMMENT '决策' AFTER OUTTIME;
ALTER TABLE t_bpm_log ADD REMARK varchar(500) DEFAULT NULL COMMENT '决策说明' AFTER DECISION;

ALTER TABLE T_COLLATERAL ADD RESERVE_DES varchar(300) DEFAULT NULL COMMENT '保管物品说明';

ALTER TABLE t_judge_adv ADD CREATE_TIME datetime DEFAULT NULL COMMENT '创建时间';

ALTER TABLE t_loan_base MODIFY COLUMN MEET_CHECK_TIME datetime DEFAULT NULL COMMENT '提交评审会复核时间';
ALTER TABLE t_loan_base ADD LAW_TIME datetime DEFAULT NULL COMMENT '提交法务时间' AFTER MEET_CHECK_TIME;

ALTER TABLE t_collateral DROP WAREHOUSE_PRVN ;
ALTER TABLE t_collateral DROP WAREHOUSE_CITY ;
ALTER TABLE t_collateral_price_audit DROP IS_HANDLED ;

ALTER TABLE t_org ADD CODE varchar(50)  COMMENT '自定义编码'  AFTER  ORG_FULL_NAME;
ALTER TABLE t_lend_user ADD REAL_NAME varchar(50)  COMMENT '真实姓名' AFTER  NAME;
ALTER TABLE t_lend_user_log ADD PROOF varchar(100) COMMENT '凭证号' AFTER REMARK;
ALTER TABLE t_sales_check_log ADD LOAN_ID varchar(50) COMMENT '申请ID' AFTER ID;


alter table t_bill_base add CONTRACT_ID varchar(50) DEFAULT NULL COMMENT '合同id' after LOAN_ID;
alter table t_bill_online add CONTRACT_ID varchar(50) DEFAULT NULL COMMENT '合同id' after LOAN_ID;

ALTER TABLE t_contract ADD CON_INDEX int  COMMENT '合同序号(默认是0，被拆分后累计标识)'  AFTER  REAL_END_DATE;

alter table t_overdue_report add ORG_AUDIT_UID varchar2(20) DEFAULT NULL ;
alter table t_overdue_report add ORG_AUDIT_TIME DATE DEFAULT NULL ;
alter table t_overdue_report add ORG_AUDIT_RESULT varchar2(20) DEFAULT NULL ;
alter table t_overdue_report add ORG_AUDIT_REMARK varchar2(500) DEFAULT NULL ;

comment on column t_overdue_report.ORG_AUDIT_UID is '分公司审批人';
comment on column t_overdue_report.ORG_AUDIT_TIME is '分公司审批时间';
comment on column t_overdue_report.ORG_AUDIT_RESULT is '分公司审批结果';
comment on column t_overdue_report.ORG_AUDIT_REMARK is '分公司审批意见';


//费用明细字段添加
ALTER TABLE t_loan_base ADD MGR_FEE NUMBER(18,2) default 0;
comment on column T_LOAN_BASE.MGR_FEE is '管理服务费';
ALTER TABLE t_loan_base ADD CERT_FEE NUMBER(18,2) default 0 ;
comment on column T_LOAN_BASE.CERT_FEE is '他项权证费';
ALTER TABLE t_loan_base ADD EVAL_FEE NUMBER(18,2) default 0 ;
comment on column T_LOAN_BASE.EVAL_FEE is '评估费';
ALTER TABLE t_loan_base ADD AGENT_FEE NUMBER(18,2) default 0 ;
comment on column T_LOAN_BASE.AGENT_FEE is '中介费';


ALTER TABLE T_RET_PLAN ADD PLAN_MGR_FEE NUMBER(18,2) default 0;
comment on column T_RET_PLAN.PLAN_MGR_FEE is '当月应收管理服务费';

ALTER TABLE T_RET_PLAN ADD PLAN_CERT_FEE NUMBER(18,2) default 0;
comment on column T_RET_PLAN.PLAN_CERT_FEE is '当月应收他项权证费';

ALTER TABLE T_RET_PLAN ADD PLAN_EVAL_FEE NUMBER(18,2) default 0;
comment on column T_RET_PLAN.PLAN_EVAL_FEE is '当月应收评估费';

ALTER TABLE T_RET_PLAN ADD PLAN_AGENT_FEE NUMBER(18,2) default 0;
comment on column T_RET_PLAN.PLAN_AGENT_FEE is '当月应收中介费';


ALTER TABLE T_RET_PLAN ADD REAL_MGR_FEE NUMBER(18,2) default 0;
comment on column T_RET_PLAN.REAL_MGR_FEE is '当月实收管理服务费';

ALTER TABLE T_RET_PLAN ADD REAL_CERT_FEE NUMBER(18,2) default 0;
comment on column T_RET_PLAN.REAL_CERT_FEE is '当月实收他项权证费';

ALTER TABLE T_RET_PLAN ADD REAL_EVAL_FEE NUMBER(18,2) default 0;
comment on column T_RET_PLAN.REAL_EVAL_FEE is '当月实收评估费';

ALTER TABLE T_RET_PLAN ADD REAL_AGENT_FEE NUMBER(18,2) default 0;
comment on column T_RET_PLAN.REAL_AGENT_FEE is '当月实收中介费';


ALTER TABLE T_RET_PLAN ADD STOR_FEE NUMBER(18,2) default 0;
comment on column T_RET_PLAN.STOR_FEE is '收取的仓储费用';

ALTER TABLE T_RET_PLAN ADD LOAN_OTHER_FEE NUMBER(18,2) default 0;
comment on column T_RET_PLAN.LOAN_OTHER_FEE is '收贷后其他费用';



 
