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
	customertype:[{"keyProp":"01","keyValue":"个人"},{"keyProp":"02","keyValue":"企业"}],
	companyidtype : [{'keyProp':'01','keyValue':'组织机构代码证'},{'keyProp':'02','keyValue':'营业执照'},{'keyProp':'03','keyValue':'税务登记证'},{'keyProp':'99','keyValue':'其他'}],
	personidtype:[{'keyProp':'01','keyValue':'身份证'},{'keyProp':'02','keyValue':'护照'},{'keyProp':'03','keyValue':'军官证'},{'keyProp':'04','keyValue':'港澳台居民往来大陆通行证'},{'keyProp':'05','keyValue':'户口簿'},{'keyProp':'99','keyValue':'其他'}],
	marriage:[{"keyProp":"01","keyValue":"未婚"},{"keyProp":"02","keyValue":"已婚"},{"keyProp":"03","keyValue":"离异"},{"keyProp":"04","keyValue":"丧偶"},{"keyProp":"99","keyValue":"其他"}],	
	familyinfo:[{"keyProp":"01","keyValue":"无子女"},{"keyProp":"02","keyValue":"有子女"},{"keyProp":"03","keyValue":"抚赡三代"}],
	familyincome:[{"keyProp":"01","keyValue":"单薪"},{"keyProp":"02","keyValue":"双薪"}],
	receivetype:[{"keyProp":"01","keyValue":"信件"},{"keyProp":"02","keyValue":"电子邮件(感谢支持低碳生活)"},{"keyProp":"03","keyValue":"两者都选"}],
	companytype:[{"keyProp":"01","keyValue":"国企"},{"keyProp":"02","keyValue":"民企"},{"keyProp":"03","keyValue":"合资"},{"keyProp":"04","keyValue":"外资"},{"keyProp":"05","keyValue":"个体"},{"keyProp":"99","keyValue":"其他"}],
	collateraltype:[{"keyProp":"01","keyValue":"房"},{"keyProp":"02","keyValue":"车"},{"keyProp":"03","keyValue":"股权"}],
	holdingtype:[{"keyProp":"01","keyValue":"上市"},{"keyProp":"02","keyValue":"非上市"}],
	loanusage:[{"keyProp":"01","keyValue":"消费"},{"keyProp":"02","keyValue":"经营"}],
	businessattr:[{"keyProp":"01","keyValue":"有限责任公司"},{"keyProp":"02","keyValue":"股份有限公司"},{"keyProp":"03","keyValue":"个体工商户"},{"keyProp":"04","keyValue":"合伙企业"},{"keyProp":"99","keyValue":"其他"}],
	gender:[{"keyProp":"1","keyValue":"男"},{"keyProp":"0","keyValue":"女"},{"keyProp":"9","keyValue":"未知"}],
	lendapptype:[{"keyProp":"01","keyValue":"项目"},{"keyProp":"02","keyValue":"咨询通道"},{"keyProp":"03","keyValue":"服务"}],
	loanchannel:[{"keyProp":"X","keyValue":"项目"},{"keyProp":"Z","keyValue":"咨询通道"},{"keyProp":"F","keyValue":"服务"}],
	customerlever:[{"keyProp":"P","keyValue":"普通"},{"keyProp":"V","keyValue":"VIP"}],
	repaymethod:[{"keyProp":"01","keyValue":"每月还息最后一次还本"},{"keyProp":"02","keyValue":"等额本息"},{"keyProp":"03","keyValue":"等额本金"},{"keyProp":"11","keyValue":"每3月付息最后一次付本"}],
	matchtype:[{"keyProp":"0","keyValue":"自动撮合"},{"keyProp":"1","keyValue":"人工撮合"}],
//	lendtype:,
	producttype:[{"keyProp":"1","keyValue":"理财"},{"keyProp":"2","keyValue":"借款"},{"keyProp":"3","keyValue":"信用"}],
//	loanmatchstate:,
//	lendmatchstate:,
	matchflag:[{"keyProp":"0","keyValue":"不可清除"},{"keyProp":"1","keyValue":"可清除"}],
	matchresultstate:[{"keyProp":"4","keyValue":"撮合中"}],
	orgrolelever:[{"keyProp":"0","keyValue":"营业部以下"},{"keyProp":"1","keyValue":"营业部级"},{"keyProp":"2","keyValue":"市级"},{"keyProp":"3","keyValue":"地区级"},{"keyProp":"4","keyValue":"总部级"}],
	contacttype:[{"keyProp":"01","keyValue":"配偶"},{"keyProp":"02","keyValue":"法定代表人"},{"keyProp":"03","keyValue":"授权代理人"},{"keyProp":"04","keyValue":"财务主管"},{"keyProp":"05","keyValue":"企业产权人"},{"keyProp":"06","keyValue":"个人产权人"},{"keyProp":"00","keyValue":"紧急联系人"},{"keyProp":"07","keyValue":"实际控制人"}],
	contractclass:[{"keyProp":"01","keyValue":"抵押借款合同"},{"keyProp":"02","keyValue":"抵押担保合同"}],
	contracttype:[{"keyProp":"01","keyValue":"线上合同"},{"keyProp":"02","keyValue":"线下合同"}],
//	contractstate:,
	inoutflag:[{"keyProp":"1","keyValue":"收"},{"keyProp":"2","keyValue":"付"}],
	paymentstate:[{"keyProp":"1","keyValue":"待支付"},{"keyProp":"2","keyValue":"已发盘"},{"keyProp":"3","keyValue":"发盘失败"},{"keyProp":"4","keyValue":"发盘成功"},{"keyProp":"5","keyValue":"支付成功"},{"keyProp":"6","keyValue":"支付失败"},{"keyProp":"9","keyValue":"未确认"},{"keyProp":"10","keyValue":"人工确认问题"}],
	hukou:[{"keyProp":"1","keyValue":"农村"},{"keyProp":"2","keyValue":"城镇"}],
//	saleslevel:[{"keyProp":"1","keyValue":"助理"},{"keyProp":"2","keyValue":"专员"},{"keyProp":"3","keyValue":"主管"},{"keyProp":"4","keyValue":"经理"},{"keyProp":"5","keyValue":"高级经理"},{"keyProp":"6","keyValue":"副总监"},{"keyProp":"7","keyValue":"总监"}],
	saleslevel:[{"keyProp":"3","keyValue":"主管"},{"keyProp":"4","keyValue":"经理"}],
	birthFlag:[{"keyProp":"1","keyValue":"已育"},{"keyProp":"0","keyValue":"未育"}],
	education:[{"keyProp":"1","keyValue":"专科"},{"keyProp":"2","keyValue":"本科"},{"keyProp":"3","keyValue":"研究生"},{"keyProp":"4","keyValue":"博士"},{"keyProp":"5","keyValue":"中专/高中"},{"keyProp":"6","keyValue":"初中及以下"}],
	degreeCertifyNo:[{"keyProp":"1","keyValue":"学士"},{"keyProp":"2","keyValue":"硕士"},{"keyProp":"3","keyValue":"博士"},{"keyProp":"9","keyValue":"无"}],
	booleanFlag:[{"keyProp":"1","keyValue":"是"},{"keyProp":"0","keyValue":"否"}],
	customerSource:[{"keyProp":"1","keyValue":"网络"},{"keyProp":"2","keyValue":"陌生拜访"},{"keyProp":"3","keyValue":"宣传页"},{"keyProp":"4","keyValue":"朋友推荐"},{"keyProp":"5","keyValue":"客户推荐"},{"keyProp":"6","keyValue":"广告"},{"keyProp":"99","keyValue":"其他"}],
	houseNature:[{"keyProp":"1","keyValue":"自建房"},{"keyProp":"2","keyValue":"无按揭房"},{"keyProp":"3","keyValue":"按揭房"},{"keyProp":"4","keyValue":"集体宿舍"},{"keyProp":"5","keyValue":"亲友同住"},{"keyProp":"6","keyValue":"租住"}],
	relation:[{"keyProp":"1","keyValue":"父母"},{"keyProp":"2","keyValue":"配偶"},{"keyProp":"3","keyValue":"子女"},{"keyProp":"4","keyValue":"亲属"},{"keyProp":"5","keyValue":"朋友"},{"keyProp":"6","keyValue":"同事"},{"keyProp":"7","keyValue":"同学"},{"keyProp":"8","keyValue":"本人"},{"keyProp":"99","keyValue":"其他"}],
	decision:[{"keyProp":"","keyValue":"请选择"},{"keyProp":"00","keyValue":"不通过"},{"keyProp":"01","keyValue":"通过"},{"keyProp":"02","keyValue":"拟通过"},{'keyProp':'03','keyValue':'拟拒贷'},{'keyProp':'04','keyValue':'特殊审核'},{'keyProp':'05','keyValue':'拒贷'}],
	
	interviewSource:[{"keyProp":"01","keyValue":"网络查询"},{"keyProp":"02","keyValue":"114查号台"},{"keyProp":"03","keyValue":"客户告知"},{"keyProp":"04","keyValue":"联系人告知"},{"keyProp":"05","keyValue":"信用报告"}],
	loangranttype: [{"keyProp":"01","keyValue":"个人经营性贷款"},{"keyProp":"02","keyValue":"个人汽车贷款"},{"keyProp":"03","keyValue":"个人商用房（包括商住两用）贷款"},{"keyProp":"04","keyValue":"个人消费贷款"},{"keyProp":"05","keyValue":"个人助学贷款"},{"keyProp":"06","keyValue":"个人住房贷款"},{"keyProp":"07","keyValue":"个人住房公积金贷款"},{"keyProp":"08","keyValue":"农户贷款"},{"keyProp":"09","keyValue":"其他贷款"}],
	loanbusinesstype:[{"keyProp":"01","keyValue":"保证"},{"keyProp":"02","keyValue":"抵押"},{"keyProp":"03","keyValue":"农户联保"},{"keyProp":"04","keyValue":"信用\/免担保"},{"keyProp":"05","keyValue":"质押（含保证金）"},{"keyProp":"06","keyValue":"组合（不含保证）"},{"keyProp":"07","keyValue":"组合（含保证）"},{"keyProp":"08","keyValue":"其他担保"}],
	loanrepaytype:[{"keyProp":"01","keyValue":"一次性归还"},{"keyProp":"02","keyValue":"按月归还"},{"keyProp":"03","keyValue":"按半年归还"},{"keyProp":"04","keyValue":"按年归还"},{"keyProp":"05","keyValue":"不定期归还"},{"keyProp":"06","keyValue":"按其他方式归还"},{"keyProp":"07","keyValue":"按季归还"}],
	loantransstate:[{"keyProp":"01","keyValue":"正常"},{"keyProp":"02","keyValue":"逾期"},{"keyProp":"03","keyValue":"结清"},{"keyProp":"04","keyValue":"止付"},{"keyProp":"05","keyValue":"冻结"},{"keyProp":"06","keyValue":"转出"}],
	loanfivelevel:[{"keyProp":"01","keyValue":"正常"},{"keyProp":"02","keyValue":"关注"},{"keyProp":"03","keyValue":"次级"},{"keyProp":"04","keyValue":"可疑"},{"keyProp":"05","keyValue":"损失"}],
	loanspecialtrans:[{"keyProp":"01","keyValue":"提前还款(全部)"},{"keyProp":"02","keyValue":"其他"},{"keyProp":"03","keyValue":"展期(延期)"}],
	creditgranttype:[{"keyProp":"01","keyValue":"贷记卡(人民币账户)"},{"keyProp":"02","keyValue":"贷记卡(美元账户)"},{"keyProp":"03","keyValue":"贷记卡(欧元账户)"},{"keyProp":"04","keyValue":"贷记卡(澳大利亚元账户)"},{"keyProp":"05","keyValue":"贷记卡(英镑账户)"},{"keyProp":"06","keyValue":"贷记卡(港元账户)"},{"keyProp":"07","keyValue":"贷记卡(日元账户)"},{"keyProp":"08","keyValue":"贷记卡(加拿大元账户)"},{"keyProp":"09","keyValue":"贷记卡(瑞士法郎账户)"}],
	credittransstate:[{"keyProp":"01","keyValue":"正常"},{"keyProp":"02","keyValue":"逾期"},{"keyProp":"03","keyValue":"销户"},{"keyProp":"04","keyValue":"未激活"},{"keyProp":"05","keyValue":"止付"},{"keyProp":"06","keyValue":"冻结"},{"keyProp":"07","keyValue":"呆账"}],
	allowgranttype:[{"keyProp":"01","keyValue":"准贷记卡(人民币账户)"},{"keyProp":"02","keyValue":"准贷记卡(美元账户)"},{"keyProp":"03","keyValue":"准贷记卡(欧元账户)"},{"keyProp":"04","keyValue":"准贷记卡(澳大利亚元账户)"},{"keyProp":"05","keyValue":"准贷记卡(英镑账户)"},{"keyProp":"06","keyValue":"准贷记卡(港元账户)"},{"keyProp":"07","keyValue":"准贷记卡(日元账户)"},{"keyProp":"08","keyValue":"准贷记卡(加拿大元账户)"},{"keyProp":"09","keyValue":"准贷记卡(瑞士法郎账户)"}],
	allowbusinesstype:[{"keyProp":"01","keyValue":"保证"},{"keyProp":"02","keyValue":"其他担保"},{"keyProp":"03","keyValue":"信用\/免担保"},{"keyProp":"04","keyValue":"抵押担保"}],
	allowtransstate:[{"keyProp":"01","keyValue":"正常"},{"keyProp":"02","keyValue":"逾期"},{"keyProp":"03","keyValue":"销户"},{"keyProp":"04","keyValue":"未激活"},{"keyProp":"05","keyValue":"止付"},{"keyProp":"06","keyValue":"冻结"},{"keyProp":"07","keyValue":"呆账"}],
	reporteducation:[{"keyProp":"10","keyValue":"未知"},{"keyProp":"01","keyValue":"研究生"},{"keyProp":"02","keyValue":"大学本科（简称\"大学\"）"},{"keyProp":"03","keyValue":"大学专科和专科学校（简称\"大专\")"},{"keyProp":"04","keyValue":"技术学校"},{"keyProp":"05","keyValue":"高中"},{"keyProp":"06","keyValue":"中等专业学校或中等技术学校"},{"keyProp":"07","keyValue":"初中"},{"keyProp":"08","keyValue":"小学"},{"keyProp":"09","keyValue":"文盲或半文盲"},{"keyProp":"10","keyValue":"未知"}],
	reportdegree:[{"keyProp":"01","keyValue":"学士"},{"keyProp":"02","keyValue":"大学本科（简称\"大学\"）"},{"keyProp":"03","keyValue":"大学专科和专科学校（简称\"大专\")"},{"keyProp":"04","keyValue":"高中"},{"keyProp":"05","keyValue":"未知"},{"keyProp":"06","keyValue":"其他"}],
	reportmarriage:[{"keyProp":"01","keyValue":"已婚"},{"keyProp":"02","keyValue":"未婚"},{"keyProp":"03","keyValue":"离婚"},{"keyProp":"04","keyValue":"未说明的婚姻状况"}],
	reportquerycause:[{"keyProp":"01","keyValue":"贷款审批"},{"keyProp":"02","keyValue":"信用卡审批"},{"keyProp":"03","keyValue":"担保资格审查"},{"keyProp":"04","keyValue":"贷后管理"},{"keyProp":"05","keyValue":"保前审批"},{"keyProp":"06","keyValue":"特约商户实名审核"},{"keyProp":"07","keyValue":"本人查询"}],
	channelType:[{"keyProp":"DX","keyValue":"鼎轩"},{"keyProp":"MD","keyValue":"MD"}]
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