-- t_user_login表添加字段
alter table t_user_login add login_description varchar(100) comment '记录登录描述信息' after login_result;
-- t_user_login表更改id字段长度
alter table t_user_login modify column id varchar(36);
-- t_user_login表更改login_result字段长度和注释
alter table t_user_login modify column login_result tinyint(2) comment '登录结果-1:成功，0：失败';

-- ----------------  更新字段及新表创建     ----------------  
-- 添加信息唯一标识id
alter table t_data_record add info_id bigint comment '信息的唯一标识' after id;
-- 添加压缩图片宽高字段
alter table t_data_record add column thumb_width int(11) after adpic_height;
alter table t_data_record add column thumb_height int(11) after thumb_width;
-- 添加索引
alter table t_data_record add index IDX_INFO_ID(info_id);


-- --------二期修改表结构-----------
-- 之前表中没有主键id，如果数据库中有数据需要进行数据备份以及添加主键
-- 添加信息整改反馈表
drop table if exists t_ad_feedback;
create table t_ad_feedback
(
	correction_id   varchar(36)   comment '主键id',
	info_id         bigint        comment '信息的唯一标识',
	role_type		tinyint(2)    comment '主体角色类型（1：广告主，2：媒体）',
	role_url        varchar(300)  comment '主体url',
	status          tinyint(2)    comment '反馈的状态（0-未知、1-已整改）',
	remark          varchar(200)  comment '备注信息',
	correction_time datetime      comment '整改时间',
	create_time     datetime      comment '创建时间',
	update_time     datetime      comment '修改时间', 
	
	primary key (correction_id), 	
    key IDX_INFO_ID (info_id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信息整改反馈表';

-- 添加信息整改反馈操作日志表
drop table if exists t_ad_feedback_log;
create table t_ad_feedback_log
(
	id              varchar(36)   comment '主键id',
	correction_id   varchar(36)   comment '整改反馈id',
	status          tinyint(2)    comment '反馈的状态（0-未知、1-已整改）',
	remark          varchar(200)  comment '备注信息',
	correction_time datetime      comment '整改时间',
	create_time     datetime      comment '创建时间',
	operator        varchar(32)   comment '操作人',
	
   key IDX_INFO_ID (correction_id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信息整改反馈日志记录表';
