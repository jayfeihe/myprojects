package ${bizzPackage}.${modelPackage}.model;

import com.tera.util.DateUtils;

/**
 * 
 * ${tableComment}实体类
 * <b>功能：</b>${className}Dao<br>
 * <b>作者：</b>${author}<br>
 * <b>日期：</b>${createDate}<br>
 * <b>版权所有：<b>${copyRight}<br>
 */
public class ${className} {

	//属性部分
#foreach($item in $!{columnDatas})
	private $!item.propType $!item.propName; //$!item.columnComment
#if($!item.propNameStr)
	private $!item.propNameStrType $!item.propNameStr; //$!item.columnComment
#end 
#end 

	//getter部分
#foreach($item in $!{columnDatas})
	public $!item.propType get$!item.methodName () {
		return this.$!item.propName;
	}
#if($!item.propNameStr && $!item.propType=='java.util.Date')
	//getter部分,Date类型的修改获取String的方法
	public $!item.propNameStrType get$!item.methodNameStr () {
		return DateUtils.formatDate(this.$!item.propName);
	}
#end 
#if($!item.propNameStr && $!item.propType=='java.sql.Timestamp')
	//getter部分,Timestamp类型的修改获取String的方法
	public $!item.propNameStrType get$!item.methodNameStr () {
		return DateUtils.formatTime(this.$!item.propName);
	}
#end 
#end 

	//setter部分
#foreach($item in $!{columnDatas})
	public void set$!item.methodName ($!item.propType $!item.propName) {
		this.$!item.propName=$!item.propName;
	}
#if($!item.propNameStr)
	public void set$!item.methodNameStr ($!item.propNameStrType $!item.propNameStr) {
		this.$!item.propNameStr=$!item.propNameStr;
	}
#end 
#end 

}

