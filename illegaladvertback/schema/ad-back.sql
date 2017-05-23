drop table if exists t_sys_user;
create table t_sys_user
(
	user_id        varchar(36)  comment '主键id',
	nick_name      varchar(32)  comment '昵称',
	account        varchar(32)  comment '账号',
	pwd            varchar(32)  comment '密码',
	status         tinyint(3)   comment '状态（0：无效 1：有效）',
	create_time    datetime     comment '创建时间',
	update_time    datetime     comment '修改时间',
	operator       varchar(32)  comment '操作人账号',
	
   primary key (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台系统用户表';

drop table if exists t_sys_menu;
create table t_sys_menu
(
	menu_id         int(11) auto_increment comment '菜单id',
	menu_name       varchar(32)            comment '菜单名称',
	menu_level      tinyint(3)             comment '菜单级别：0虚拟主菜单 1系统级别菜单 2功能模块菜单 3功能点菜单',
	menu_url        varchar(128)           comment '菜单对应url',
	menu_desc       varchar(128)           comment '菜单功能描述',
	parent_menu_id  int(11)                comment '上级id',
	order_by        tinyint(3)             comment '排序',
	status          tinyint(3)             comment '状态：0：不可用 1：可用',
	create_time     datetime               comment '创建时间',
	update_time     datetime               comment '修改时间',
	operator        varchar(32)            comment '操作人账号',

	primary key (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台功能菜单表';

drop table if exists t_sys_operation_record;
create table t_sys_operation_record
(
	id              varchar(36)      comment '主键id',
	account         varchar(32)      comment '用户账号',
	ip_address      varchar(15)      comment 'ip地址',
	menu_id         int(11)          comment '菜单id',
	nick_name       varchar(32)      comment '用户姓名',
	operation_desc  varchar(100)     comment '操作描述',
	operation_url   varchar(100)     comment '操作URL',
	request_params  varchar(1000)    comment '请求参数（以（参数名称1,参数值1）||（参数名称2,参数值2）方式存储）',
	operation_type  tinyint(2)       comment '（10:增 20:删 30:改  90:其他）',
	create_time     datetime         comment '创建时间',
	
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户操作日志表';

drop table if exists t_industry;
create table t_industry
(
	industry_id           int(11)     comment '行业id',
	industry_name         varchar(32) comment '行业名称',
	
   primary key (industry_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行业类型字典表';

-- 数据初始化
-- 用户数据（密码：123456）
insert into t_sys_user values (UUID(),'系统管理员','admin','e10adc3949ba59abbe56e057f20f883e',1,NOW(),NOW(),'admin');

-- 菜单数据
insert into 
t_sys_menu (`menu_name`, `menu_level`, `menu_url`, `menu_desc`, `parent_menu_id`, `order_by`, `status`, `create_time`, `update_time`, `operator`) 
values 
('系统管理', '2', '', '2级功能模块菜单-系统管理', '0', '1', '1', NOW(), NOW(), 'admin'),
('客户管理', '2', '', '2级功能模块菜单-客户管理', '0', '2', '1', NOW(), NOW(), 'admin'),
('客户账号管理', '2', '', '2级功能模块菜单-客户账户管理', '0', '3', '1', NOW(), NOW(), 'admin'),
-- 系统管理子菜单
('菜单管理', '3', '/sys/menu/list', '3级功能点菜单-菜单管理', '1', '1', '1', NOW(), NOW(), 'admin'),           
('个人信息管理', '3', '/sys/user/userCenter', '3级功能点菜单-个人信息管理', '1', '2', '1', NOW(), NOW(), 'admin'),
('操作日志', '3', '/sys/operationRecord/query', '3级功能点菜单-操作日志', '1', '2', '1', NOW(), NOW(), 'admin'),
-- 客户管理子菜单
('广告主管理', '3', '/company/advertiser/query', '3级功能点菜单-广告主管理', '2', '1', '1', NOW(), NOW(), 'admin'),
('媒体管理', '3', '/company/media/query', '3级功能点菜单-媒体管理', '2', '2', '1', NOW(), NOW(), 'admin'),
-- 客户账号管理子菜单
('广告主账号管理', '3', '/account/advertiser/query', '3级功能点菜单-广告主账号管理', '3', '1', '1', NOW(), NOW(), 'admin'),
('媒体账号管理', '3', '/account/media/query', '3级功能点菜单-媒体账号管理', '3', '2', '1', NOW(), NOW(), 'admin'); 

-- 行业数据初始化
insert into t_industry values 
(201,'页游'),
(202,'手游'),
(203,'电商'),
(204,'金融财经'),
(205,'教育培训'),
(206,'汽车'),
(207,'票务旅游'),
(208,'房地产'),
(209,'成人用品'),
(210,'生活娱乐'),
(211,'社交'),
(212,'医疗健康'),
(213,'工农林牧渔'),
(214,'网络与软件'),
(299,'其他');


