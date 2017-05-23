
//保留2位小数，第三位舍去
function cut2Position(number) {
	var _int = parseInt(number * 100);
	return Math.round(_int) / 100;
}

// 保留2位小数，第三位四舍五入
function reserve2Position(number) {
	return Math.round(number * 100) / 100;
}

/**
 * 对传入的数值，如果精度小于两位小数则将在小数点后补0 <br/> 例如：166.7 转化为166.70 1转化为1.00 1.转化为1.00 return
 * String
 */
function formatNum2Float(numberParam) {
	var number = numberParam + "";
	if (isNaN(parseFloat(number))) {
		return "0.00";
	}
	var ret = number;
	if (number.indexOf(".") != -1) {
		if ((number.length - 1) == number.indexOf(".")) {
			ret = number + "00";
		} else if ((number.length - 1 - 1) == number.indexOf(".")) {
			ret = number + "0";
		}
	} else {
		ret = number + ".00";
	}
	return ret;
}

// 校验银行卡
// Create Time: 07/28/2011
// Description: 银行卡号Luhm校验
// Luhm校验规则：16位银行卡号（19位通用）:
// 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
// 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
// 3.将加法和加上校验位能被 10 整除。
// 方法步骤很清晰，易理解，需要在页面引用Jquery.js
// bankno为银行卡号 banknoInfo为显示提示信息的DIV或其他控件
function luhmCheck(bankno) {
	var lastNum = bankno.substr(bankno.length - 1, 1);// 取出最后一位（与luhm进行比较）

	var first15Num = bankno.substr(0, bankno.length - 1);// 前15或18位
	var newArr = new Array();
	for (var i = first15Num.length - 1; i > -1; i--) { // 前15或18位倒序存进数组
		newArr.push(first15Num.substr(i, 1));
	}
	var arrJiShu = new Array(); // 奇数位*2的积 <9
	var arrJiShu2 = new Array(); // 奇数位*2的积 >9

	var arrOuShu = new Array(); // 偶数位数组
	for (var j = 0; j < newArr.length; j++) {
		if ((j + 1) % 2 == 1) {// 奇数位
			if (parseInt(newArr[j]) * 2 < 9)
				arrJiShu.push(parseInt(newArr[j]) * 2);
			else
				arrJiShu2.push(parseInt(newArr[j]) * 2);
		} else
			// 偶数位
			arrOuShu.push(newArr[j]);
	}

	var jishu_child1 = new Array();// 奇数位*2 >9 的分割之后的数组个位数
	var jishu_child2 = new Array();// 奇数位*2 >9 的分割之后的数组十位数
	for (var h = 0; h < arrJiShu2.length; h++) {
		jishu_child1.push(parseInt(arrJiShu2[h]) % 10);
		jishu_child2.push(parseInt(arrJiShu2[h]) / 10);
	}

	var sumJiShu = 0; // 奇数位*2 < 9 的数组之和
	var sumOuShu = 0; // 偶数位数组之和
	var sumJiShuChild1 = 0; // 奇数位*2 >9 的分割之后的数组个位数之和
	var sumJiShuChild2 = 0; // 奇数位*2 >9 的分割之后的数组十位数之和
	var sumTotal = 0;
	for (var m = 0; m < arrJiShu.length; m++) {
		sumJiShu = sumJiShu + parseInt(arrJiShu[m]);
	}

	for (var n = 0; n < arrOuShu.length; n++) {
		sumOuShu = sumOuShu + parseInt(arrOuShu[n]);
	}

	for (var p = 0; p < jishu_child1.length; p++) {
		sumJiShuChild1 = sumJiShuChild1 + parseInt(jishu_child1[p]);
		sumJiShuChild2 = sumJiShuChild2 + parseInt(jishu_child2[p]);
	}
	// 计算总和
	sumTotal = parseInt(sumJiShu) + parseInt(sumOuShu)
			+ parseInt(sumJiShuChild1) + parseInt(sumJiShuChild2);

	// 计算Luhm值
	var k = parseInt(sumTotal) % 10 == 0 ? 10 : parseInt(sumTotal) % 10;
	var luhm = 10 - k;

	if (lastNum == luhm) {
		// $("#banknoInfo").html("Luhm验证通过");
		return true;
	} else {
		// $("#banknoInfo").html("银行卡号必须符合Luhm校验");
		return false;
	}
}

/**
 * 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。<br/>
 * 调用：accDiv(arg1,arg2)<br/> 返回值：arg1除以arg2的精确结果<br/>
 */
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}
// 给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function(arg) {
	return accDiv(this, arg);
};
/**
 * 乘法函数，用来得到精确的乘法结果<br/>
 * 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。<br/>
 * 调用：accMul(arg1,arg2)<br/> 返回值：arg1乘以arg2的精确结果<br/>
 */
function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
			/ Math.pow(10, m);
}
// 给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function(arg) {
	return accMul(arg, this);
};
/**
 * 加法函数，用来得到精确的加法结果<br/>
 * 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。<br/>
 * 调用：accAdd(arg1,arg2)<br/> 返回值：arg1加上arg2的精确结果
 */
function accAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}
// 给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function(arg) {
	return accAdd(arg, this);
}
// 减法函数
function accSub(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2));
	// lastmodifybydeeka
	// 动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
	return ((arg2 * m - arg1 * m) / m).toFixed(n);
}
// /给number类增加一个sub方法，调用起来更加方便
Number.prototype.sub = function(arg) {
	return accSub(arg, this);
}

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
}

//+---------------------------------------------------  
//| 求两个时间的天数差 日期格式为 YYYY-MM-dd   
//+---------------------------------------------------  
function daysBetween(DateOne,DateTwo)  
{   
var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));  
var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);  
var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));  

var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));  
var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);  
var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  

var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);   
return Math.abs(cha);  
}  


//+---------------------------------------------------  
//| 日期计算  
//+---------------------------------------------------  
Date.prototype.DateAdd = function(strInterval, Number) {   
var dtTmp = this;  
switch (strInterval) {   
   case 's' :return new Date(Date.parse(dtTmp) + (1000 * Number));  
   case 'n' :return new Date(Date.parse(dtTmp) + (60000 * Number));  
   case 'h' :return new Date(Date.parse(dtTmp) + (3600000 * Number));  
   case 'd' :return new Date(Date.parse(dtTmp) + (86400000 * Number));  
   case 'w' :return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));  
   case 'q' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
   case 'm' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
   case 'y' :return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
}  
}  
//+---------------------------------------------------  
//| 字符串转成日期类型   
//| 格式 MM/dd/YYYY MM-dd-YYYY YYYY/MM/dd YYYY-MM-dd  
//+---------------------------------------------------  
function StringToDate(DateStr)  
{   

var converted = Date.parse(DateStr);  
var myDate = new Date(converted);  
if (isNaN(myDate))  
{   
   //var delimCahar = DateStr.indexOf('/')!=-1?'/':'-';  
   var arys= DateStr.split('-');  
   myDate = new Date(arys[0],--arys[1],arys[2]);  
}  
return myDate;  
} 

