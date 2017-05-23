-- t_user_company表添加字段
alter table t_user_company add industry_id   int(11)      comment '行业id'   after company_url;
alter table t_user_company add link_name     varchar(32)  comment '行业类型id' after industry_id;
alter table t_user_company add link_phone    varchar(32)  comment '联系人姓名'  after link_name;
alter table t_user_company add link_email    varchar(32)  comment '联系人电话' after link_phone;
alter table t_user_company add company_addr  varchar(100) comment '联系人邮箱' after link_email;
alter table t_user_company add company_phone varchar(32)  comment '公司地址' after company_addr;
alter table t_user_company add post_code     varchar(32)  comment '公司电话' after company_phone;