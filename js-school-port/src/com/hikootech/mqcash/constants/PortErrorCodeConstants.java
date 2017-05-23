package com.hikootech.mqcash.constants;

/** 
* @ClassName ErrorCodeConstants 
* @Description 定义所有错误常量
* @author 余巍 yuweiqwe@126.com 
* @date 2015年12月14日 下午7:11:14 
* 第1-2位表示系统类型，01-秒趣分期系统；02-秒趣门户系统；03-秒趣核心系统；04-秒趣后台系统；05-秒趣征信系统；
* 第3-6位表示功能模块：如,0001征信判断接口
* 第7-10位表示序号，递增，最大9999：如,0001征信查询国政通超时 0002征信调用征信系统超时 0003征信数据库异常
* 比如：秒趣门户密码数据库异常 02 + 0001 + 0001 = 0200010001
*/
public class PortErrorCodeConstants {

}
