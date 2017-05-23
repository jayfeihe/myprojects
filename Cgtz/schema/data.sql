delete from t_role;
UPDATE t_sequence SET current_value=0 WHERE NAME = 'T_ROLE_SEQ';

INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '业务员'    , '业务员'    , '2', '1', NULL, NULL);
INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '分公司经理', '分公司经理', '2', '1', NULL, NULL);
INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '风控经理'  , '风控经理'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '风控总监'  , '风控总监'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '评审会秘书', '评审会秘书', '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '评审会评委', '评审会评委', '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '法务专员'  , '法务专员'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '出纳专员'  , '出纳专员'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '财务专员'  , '财务专员'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '贷后主管'  , '贷后主管'  , '1', '1', NULL, NULL);
INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '催收专员'  , '催收专员'  , '2', '1', NULL, NULL);
INSERT INTO t_role VALUES (nextval('T_ROLE_SEQ'), '稽查专员'  , '稽查专员'  , '1', '1', NULL, NULL);
