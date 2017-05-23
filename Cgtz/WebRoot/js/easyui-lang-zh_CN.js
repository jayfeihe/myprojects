if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = '第';
	$.fn.pagination.defaults.afterPageText = '共{pages}页';
	$.fn.pagination.defaults.displayMsg = '显示{from}到{to},共{total}记录';
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = '正在处理，请稍待。。。';
}
if ($.fn.treegrid && $.fn.datagrid){
	$.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager){
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
}
if ($.fn.validatebox){
	$.fn.validatebox.defaults.missingMessage = '该输入项为必输项';
	$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
	$.fn.validatebox.defaults.rules.url.message = '请输入有效的URL地址';
	$.fn.validatebox.defaults.rules.length.message = '输入内容长度必须介于{0}和{1}之间';
	$.fn.validatebox.defaults.rules.remote.message = '请修正该字段';
}
if ($.fn.numberbox){
	$.fn.numberbox.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combobox){
	$.fn.combobox.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combotree){
	$.fn.combotree.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combogrid){
	$.fn.combogrid.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['日','一','二','三','四','五','六'];
	$.fn.calendar.defaults.months = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '今天';
	$.fn.datebox.defaults.closeText = '关闭';
	$.fn.datebox.defaults.okText = '确定';
	$.fn.datebox.defaults.missingMessage = '该输入项为必输项';
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	};
	$.fn.datebox.defaults.parser = function(s){
		if (!s) return new Date();
		var ss = s.split('-');
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	};
}
if ($.fn.datetimebox && $.fn.datebox){
	$.extend($.fn.datetimebox.defaults,{
		currentText: $.fn.datebox.defaults.currentText,
		closeText: $.fn.datebox.defaults.closeText,
		okText: $.fn.datebox.defaults.okText,
		missingMessage: $.fn.datebox.defaults.missingMessage
	});
}


$.extend($.fn.validatebox.defaults.rules, {
    CHS: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '请输入汉字'
    },
    ZIP: {
        validator: function (value, param) {
//            return /^[1-9]\d{5}$/.test(value);
    	      return /\d{6}$/.test(value);
//  		  return /^[0-9]\d{5}$/.test(value);
        },
        message: '邮政编码不存在'
    },
    QQ: {
        validator: function (value, param) {
            return /^[1-9]\d{4,10}$/.test(value);
        },
        message: 'QQ号码不正确'
    },
    mobile: {
        validator: function (value, param) {
            return /1[0-9]{10}$/.test(value);
        },
        message: '手机号码不正确'
    },
    loginName: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5\w]+$/.test(value);
        },
        message: '登录名称只允许汉字、英文字母、数字及下划线。'
    },
    safepass: {
        validator: function (value, param) {
            return safePassword(value);
        },
        message: '密码由字母和数字组成，至少6位'
    },
    equalTo: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '两次输入的字符不一至'
    },
    number: {
        validator: function (value, param) {
            return /^\d+$/.test(value);
        },
        message: '请输入数字'
    },
    idcard: {
        validator: function (value, param) {
            return idCard(value);
        },
        message:'请输入正确的身份证号码'
    },
    idNo:{
    	 validator: function (value, param) {
             return /^[A-Za-z0-9]+\-?[A-Za-z0-9]*$/.test(value);
         },
         message: '证件号码，格式不正确。'
    },
    NumLetters:{//值必须是 数字 或字母
   	 validator: function (value, param) {
            return /^[a-zA-Z0-9]*$/.test(value);
        },
        message: '只能为字母与数字。'
   }
     
});

/* 密码由字母和数字组成，至少6位 */
var safePassword = function (value) {
    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
}

var idCard = function (value) {
    if (value.length == 18 && 18 != value.length) return false;
    var number = value.toLowerCase();
    var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
    if (re == null || a.indexOf(re[1]) < 0) return false;
    if (re[2].length == 9) {
        number = number.substr(0, 6) + '19' + number.substr(6);
        d = ['19' + re[4], re[5], re[6]].join('-');
    } else d = [re[9], re[10], re[11]].join('-');
    if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
}

var isDateTime = function (format, reObj) {
    format = format || 'yyyy-MM-dd';
    var input = this, o = {}, d = new Date();
    var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
    var len = f1.length, len1 = f3.length;
    if (len != f2.length || len1 != f4.length) return false;
    for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
    for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
    o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
    o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
    o.dd = s(o.dd, o.d, d.getDate(), 31);
    o.hh = s(o.hh, o.h, d.getHours(), 24);
    o.mm = s(o.mm, o.m, d.getMinutes());
    o.ss = s(o.ss, o.s, d.getSeconds());
    o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
    if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
    if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
    d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
    var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
    return reVal && reObj ? d : reVal;
    function s(s1, s2, s3, s4, s5) {
        s4 = s4 || 60, s5 = s5 || 2;
        var reVal = s3;
        if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
        if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
        return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
    }
};

//全局的ajax访问，处理ajax清求时sesion超时 
$.ajaxSetup({ 
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(XMLHttpRequest, textStatus) {
		// 通过XMLHttpRequest取得响应头，sessionstatus
		var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");  
		if (sessionstatus == "timeout") { 
			// 如果超时就处理 ，指定要跳转的页面 
			window.location.replace("/index.do"); 
		}
	} 
});


var dataDictJson = {
	inteDays:[{"keyProp":"360","keyValue":"360"},{"keyProp":"365","keyValue":"365"}],
	
	gender:[{"keyProp":"M","keyValue":"男"},{"keyProp":"F","keyValue":"女"}],
	
	bankAccountType:[{"keyProp":"1","keyValue":"个人账号"},{"keyProp":"2","keyValue":"对公账号"}],
	
	/*借款类型*/
	loanType:[{"keyProp":"01","keyValue":"个人"},{"keyProp":"02","keyValue":"公司"}],
	
	/*申请人证件类型*/
	applyIdType:[{"keyProp":"01","keyValue":"身份证"},{"keyProp":"02","keyValue":"军官证"},{"keyProp":"03","keyValue":"驾驶证"}],
	
	/*公司申请证件类型*/
	companyIdType:[{"keyProp":"04","keyValue":"营业执照"},{"keyProp":"05","keyValue":"组织机构代码证"},{"keyProp":"06","keyValue":"税务登记证"}],
	
	/*抵押物类型*/
	collateralType:[{"keyProp":"01","keyValue":"车"},{"keyProp":"02","keyValue":"车商"},{"keyProp":"03","keyValue":"房"},
	       	     {"keyProp":"04","keyValue":"红木"},{"keyProp":"05","keyValue":"海鲜"},{"keyProp":"99","keyValue":"其他"}],
	decision:[{'keyProp':'1','keyValue':'通过'},{'keyProp':'0','keyValue':'不通过'}],
	
	auditPriceState:[{'keyProp':'0','keyValue':'未处理'},{'keyProp':'1','keyValue':'相符'},{'keyProp':'2','keyValue':'不相符'}],
	
	getLoanWay:[{'keyProp':'1','keyValue':'直投'},{'keyProp':'2','keyValue':'债权转让'},{'keyProp':'3','keyValue':'线下'}],
	
	subject:[{'keyProp':'1','keyValue':'收利息'},{'keyProp':'2','keyValue':'收本金'}],
};

$.messager.defaults={ok:"确定", cancel:"取消"};

/**
 * 遮罩层js
 * 
 * */

//开启遮罩
function openMask(){
	$("<div class=\"datagrid-mask\" id='mask_id'></div>").css({display:"block",width:"100%",height:$(window).height(),position:'fixed'}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\" id='mask_msg_id'></div>").html("请稍候。。。").appendTo("body").css({display:"block",position:'fixed',left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
//关闭遮罩
function closeMask(){
	$("#mask_id").remove();
	$("#mask_msg_id").remove();
}

/**
 * 标签页制只读
 * @param redinfo
 */
function tabsReadOnly(tabs){
	
	var redinfo = $(tabs);
	
	redinfo.find("input").attr('disabled',true);

	redinfo.find('.easyui-validatebox').attr('disabled', 'disabled');
	redinfo.find('.easyui-combobox').attr('disabled', 'disabled');
	redinfo.find('.easyui-numberbox').attr('disabled', 'disabled');
	redinfo.find('.easyui-datebox').attr('disabled', 'disabled');
}

function resetElement(ele) {
	var element = $(ele);
	element.find("input").each(function(){
		$(this).val("");
	});
	element.find("textarea").each(function(){
		$(this).val("");
	});
	element.find("input[type='hidden']").each(function(){
		$(this).val("");
	});
	element.find("input[type='radio']").each(function(){
		$(this).removeAttr("checked");
	});
	
	element.find(".easyui-numberbox").each(function(){
		$(this).numberbox("setValue",0);
	});
	element.find(".easyui-combobox").each(function(){
		if($(this).combobox("getValue") != null && $(this).combobox("getValue") != '' ){
			$(this).combobox("setValue","");
		}
	});
	element.find(".easyui-datebox").each(function(){
		$(this).datebox("setValue","");
	});
}

/**
 * 切换隐藏元素是否进行校验
 * @param objId
 * @param isValidete
 */
function toggleValidate(objId,isValidete){
	var state=!isValidete;
	var obj=$(objId);
	obj.find('.easyui-validatebox').validatebox({novalidate:state});
	obj.find('.easyui-combobox').combobox({novalidate:state});
	obj.find('.easyui-numberbox').numberbox({novalidate:state});
	obj.find('.easyui-datebox').datebox({novalidate:state});
}