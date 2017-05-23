drop table if exists t_ad_pic;
create table t_ad_pic
(
	data_id         varchar(32)   comment '告警记录ID',
	pic_url         varchar(300)  comment '图片URL',
	status          tinyint(2)    comment '状态（0：初始化 1：已下载 2：下载失败）',
	download_times  int(11)       comment '下载次数',
	create_time     datetime      comment '下载时间',
	
   key IDX_DATA_ID (data_id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告图片表';
