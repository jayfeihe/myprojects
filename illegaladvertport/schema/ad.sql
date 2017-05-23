drop table if exists t_data_record;
create table t_data_record
(
   	id                    varchar(32)         comment '主键id',
	ad_name               varchar(100)        comment '广告的中文名称',
	creative_name         varchar(100)        comment '创意的中文名称',
	adpic_url             varchar(300)        comment '广告创意的图片地址',
	adpic_width           int(11)             comment '图片宽度',
	adpic_height          int(11)             comment '图片高度',
	landing_url           varchar(300)        comment '广告落地页URL地址',
	advertiser_url        varchar(300)        comment '广告主URL域名地址',
	advertiser_name       varchar(100)        comment '广告主中文名称',
	media_url             varchar(300)        comment '媒体URL域名地址',
	media_name            varchar(100)        comment '媒体中文名称',
	terminal_type         tinyint(2)          comment '投放的终端类型（1：PC 2：APP  3：WAP）',
	adx_url               varchar(300)        comment 'ADX的URL地址',
	adx_name              varchar(100)        comment 'ADX的中文名称',
	type                  tinyint(2)          comment '数据类型（1: 坐席审核后的-疑似违法广告，2: 领导审核后的-确认违法广告）',
	level                 tinyint(2)          comment '违法程度（1：严重违法 2：一般违法 3：轻微违法）',
	collect_time          datetime            comment '采集时间',
	check_time            datetime            comment '坐席审核时间',
	confirm_time          datetime            comment '领导确认时间',
	create_time           datetime            comment '创建时间',
	
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='告警记录表';

drop table if exists t_user_company;
create table t_user_company
(
	company_id          varchar(32)          comment '主键id',
	company_name        varchar(100)         comment '公司名称',
	company_url         varchar(100)         comment '公司域名',
	industry_id         int(11)              comment '行业id',
	link_name           varchar(32)          comment '行业类型ID',
	link_phone          varchar(32)          comment '联系人姓名',
	link_email          varchar(32)          comment '联系人电话',
	company_addr        varchar(100)         comment '联系人邮箱',
	company_phone       varchar(32)          comment '公司地址',
	post_code           varchar(32)          comment '公司电话',
	type                tinyint(2)           comment '公司邮编类型（1：广告主 2：媒体）',
	status              tinyint(2)           comment '状态（0：无效1:有效 ）',
	create_time         datetime             comment '创建时间',
	
   primary key (company_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告主、媒体用户表';

drop table if exists t_user_account;
create table t_user_account
(
	id                  varchar(32)         comment '主键id',
	account             varchar(50)         comment '账号',
	pwd                 varchar(50)         comment '密码',
	compay_id           varchar(32)         comment '所属公司',
	user_name           varchar(50)         comment '联系人',
	email               varchar(50)         comment '邮箱地址',
	fax                 varchar(50)         comment '传真',
	mobile              varchar(50)         comment '手机',
	telephone           varchar(50)         comment '固定电话',
	qq                  varchar(50)         comment 'QQ',
	status              tinyint(2)          comment '状态（0：无效1:有效 ）',
	create_time         datetime            comment '创建时间',
	
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账号表';

drop table if exists t_user_login;
create table t_user_login
(
	id                varchar(36)          comment '主键id',
	account           varchar(50)          comment '登录账号',
	ip_address        varchar(50)          comment '用户IP',
	login_result      varchar(32)          comment '登录结果-1:成功，0：失败',
	login_description varchar(100)         comment '记录登录描述信息',
	login_time        datetime             comment '登录时间',
	create_time       datetime             comment '创建时间',
	
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录日志表';
