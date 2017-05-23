//保留2位小数，第三位舍去
function cut2Position(number) {
	var _int = parseInt(number * 100);
	return Math.round(_int) / 100;
}

/**保留2位小数，第三位四舍五入*/
function reserve2Position(number) {
	ret= Math.round(number * 100) / 100;
	return formatNum2Float(ret);
}

/** 去所有空格 */
function Trim(str){ 
    return str.replace(/\s/g, ""); 
    
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

/**********************************************浮点型计算工具函数*******************************************************************/

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

/**********************************************日期工具函数*******************************************************************/

//---------------------------------------------------  
//判断闰年  
//---------------------------------------------------  
Date.prototype.isLeapYear = function()   
{   
 return (0==this.getYear()%4&&((this.getYear()%100!=0)||(this.getYear()%400==0)));   
}   

//---------------------------------------------------  
//日期格式化  
//格式 YYYY/yyyy/YY/yy 表示年份  
//MM/M 月份  
//W/w 星期  
//dd/DD/d/D 日期  
//hh/HH/h/H 时间  
//mm/m 分钟  
//ss/SS/s/S 秒  
//---------------------------------------------------  
function formateDate(date,formatStr)   
{   
	date
 var str = formatStr;   
 var Week = ['日','一','二','三','四','五','六'];  

 str=str.replace(/yyyy|YYYY/,date.getFullYear());   
 str=str.replace(/yy|YY/,(date.getYear() % 100)>9?(date.getYear() % 100).toString():'0' + (date.getYear() % 100));   

 var _mon=date.getMonth()+1; //js的月数是从0开始的
 str=str.replace(/MM/,_mon>9?_mon.toString():'0' + _mon);   
 str=str.replace(/M/g,_mon);   

 str=str.replace(/w|W/g,Week[date.getDay()]);   

 str=str.replace(/dd|DD/,date.getDate()>9?date.getDate().toString():'0' + date.getDate());   
 str=str.replace(/d|D/g,date.getDate());   

 str=str.replace(/hh|HH/,date.getHours()>9?date.getHours().toString():'0' + date.getHours());   
 str=str.replace(/h|H/g,date.getHours());   
 str=str.replace(/mm/,date.getMinutes()>9?date.getMinutes().toString():'0' + date.getMinutes());   
 str=str.replace(/m/g,date.getMinutes());   

 str=str.replace(/ss|SS/,date.getSeconds()>9?date.getSeconds().toString():'0' + date.getSeconds());   
 str=str.replace(/s|S/g,date.getSeconds());   

 return str;   
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
//| 比较日期差 dtEnd 格式为日期型或者有效日期格式字符串  
//+---------------------------------------------------  
Date.prototype.DateDiff = function(strInterval, dtEnd) {   
 var dtStart = this;  
 if (typeof dtEnd == 'string' )//如果是字符串转换为日期型  
 {   
     dtEnd = StringToDate(dtEnd);  
 }  
 switch (strInterval) {   
     case 's' :return parseInt((dtEnd - dtStart) / 1000);  
     case 'n' :return parseInt((dtEnd - dtStart) / 60000);  
     case 'h' :return parseInt((dtEnd - dtStart) / 3600000);  
     case 'd' :return parseInt((dtEnd - dtStart) / 86400000);  
     case 'w' :return parseInt((dtEnd - dtStart) / (86400000 * 7));  
     case 'm' :return (dtEnd.getMonth()+1)+((dtEnd.getFullYear()-dtStart.getFullYear())*12) - (dtStart.getMonth()+1);  
     case 'y' :return dtEnd.getFullYear() - dtStart.getFullYear();  
 }  
}  

//+---------------------------------------------------  
//| 日期输出字符串，重载了系统的toString方法  
//+---------------------------------------------------  
Date.prototype.toString = function(showWeek)  
{   
 var myDate= this;  
 var str = myDate.toLocaleDateString();  
 if (showWeek)  
 {   
     var Week = ['日','一','二','三','四','五','六'];  
     str += ' 星期' + Week[myDate.getDay()];  
 }  
 return str;  
}  

//+---------------------------------------------------  
//| 日期合法性验证  
//| 格式为：YYYY-MM-DD或YYYY/MM/DD  
//+---------------------------------------------------  
function IsValidDate(DateStr)   
{   
 var sDate=DateStr.replace(/(^\s+|\s+$)/g,''); //去两边空格;   
 if(sDate=='') return true;   
 //如果格式满足YYYY-(/)MM-(/)DD或YYYY-(/)M-(/)DD或YYYY-(/)M-(/)D或YYYY-(/)MM-(/)D就替换为''   
 //数据库中，合法日期可以是:YYYY-MM/DD(2003-3/21),数据库会自动转换为YYYY-MM-DD格式   
 var s = sDate.replace(/[\d]{ 4,4 }[\-/]{ 1 }[\d]{ 1,2 }[\-/]{ 1 }[\d]{ 1,2 }/g,'');   
 if (s=='') //说明格式满足YYYY-MM-DD或YYYY-M-DD或YYYY-M-D或YYYY-MM-D   
 {   
     var t=new Date(sDate.replace(/\-/g,'/'));   
     var ar = sDate.split(/[-/:]/);   
     if(ar[0] != t.getYear() || ar[1] != t.getMonth()+1 || ar[2] != t.getDate())   
     {   
         //alert('错误的日期格式！格式为：YYYY-MM-DD或YYYY/MM/DD。注意闰年。');   
         return false;   
     }   
 }   
 else   
 {   
     //alert('错误的日期格式！格式为：YYYY-MM-DD或YYYY/MM/DD。注意闰年。');   
     return false;   
 }   
 return true;   
}   

//+---------------------------------------------------  
//| 日期时间检查  
//| 格式为：YYYY-MM-DD HH:MM:SS  
//+---------------------------------------------------  
function CheckDateTime(str)  
{   
 var reg = /^(\d+)-(\d{ 1,2 })-(\d{ 1,2 }) (\d{ 1,2 }):(\d{ 1,2 }):(\d{ 1,2 })$/;   
 var r = str.match(reg);   
 if(r==null)return false;   
 r[2]=r[2]-1;   
 var d= new Date(r[1],r[2],r[3],r[4],r[5],r[6]);   
 if(d.getFullYear()!=r[1])return false;   
 if(d.getMonth()!=r[2])return false;   
 if(d.getDate()!=r[3])return false;   
 if(d.getHours()!=r[4])return false;   
 if(d.getMinutes()!=r[5])return false;   
 if(d.getSeconds()!=r[6])return false;   
 return true;   
}   

//+---------------------------------------------------  
//| 把日期分割成数组  
//+---------------------------------------------------  
Date.prototype.toArray = function()  
{   
 var myDate = this;  
 var myArray = Array();  
 myArray[0] = myDate.getFullYear();  
 myArray[1] = myDate.getMonth();  
 myArray[2] = myDate.getDate();  
 myArray[3] = myDate.getHours();  
 myArray[4] = myDate.getMinutes();  
 myArray[5] = myDate.getSeconds();  
 return myArray;  
}  

//+---------------------------------------------------  
//| 取得日期数据信息  
//| 参数 interval 表示数据类型  
//| y 年 m月 d日 w星期 ww周 h时 n分 s秒  
//+---------------------------------------------------  
Date.prototype.DatePart = function(interval)  
{   
 var myDate = this;  
 var partStr='';  
 var Week = ['日','一','二','三','四','五','六'];  
 switch (interval)  
 {   
     case 'y' :partStr = myDate.getFullYear();break;  
     case 'm' :partStr = myDate.getMonth()+1;break;  
     case 'd' :partStr = myDate.getDate();break;  
     case 'w' :partStr = Week[myDate.getDay()];break;  
     case 'ww' :partStr = myDate.WeekNumOfYear();break;  
     case 'h' :partStr = myDate.getHours();break;  
     case 'n' :partStr = myDate.getMinutes();break;  
     case 's' :partStr = myDate.getSeconds();break;  
 }  
 return partStr;  
}  

//+---------------------------------------------------  
//| 取得当前日期所在月的最大天数  
//+---------------------------------------------------  
Date.prototype.MaxDayOfDate = function()  
{   
 var myDate = this;  
 var curMon=parseInt(myDate.getMonth())+1;       //获取当前月份(0-11,0代表1月)
 var curYear=myDate.getFullYear();
 var temp = new Date(curYear,curMon,0);
 return temp.getDate();  
}  

//+---------------------------------------------------  
//| 取得当前日期所在周是一年中的第几周  
//+---------------------------------------------------  
Date.prototype.WeekNumOfYear = function()  
{   
 var myDate = this;  
 var ary = myDate.toArray();  
 var year = ary[0];  
 var month = ary[1]+1;  
 var day = ary[2];  
 document.write('< script language=VBScript\> \n');  
 document.write("myDate = Datue(''+month+'-'+day+'-'+year+'') \n");  
 document.write("result = DatePart('ww', myDate) \n");  
 document.write(' \n');  
 return result;  
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

/**
 * top等待付款信息
 * payMon   还款月
 * payDay   还款日期
 * payAmount 本期还款金额
 * _payCount 几笔待还
 * _betweenDays 与坐标原点相差几天
 * 
*/
 function waitMsg(_payMon,_payDay,payAmount,_nowDate,_payCount,_betweenDays){
//	 var payMon=10;
//	 var payDay=24;
//	 var payAmount=formatNum2Float(464.23);
		 var myDate="";
		 if(_nowDate!=null&&_nowDate!=""&&_nowDate!="undefined"){
			 myDate = new Date(Date.parse(_nowDate));
		 }else{
			 myDate=new Date();
		 }
		 var curMon=parseInt(myDate.getMonth())+1;       //获取当前月份(getMonth()返回0-11,0代表1月)
		 var nextMon=(curMon==12?1:curMon+1) ;
		 var curDay= parseInt(myDate.getDate());
		 $(".user-month").find(".fl").html(curMon+""+"月");
		 $(".user-month").find(".fr").html(nextMon+""+"月");
		 var maxDay= myDate.MaxDayOfDate();
		 var pos=[4,23,57,91,125,159,193,218,244,278,312,345,378,413,439,465,499,533,566,600,634,659,685,718,753,786,819,853,878,906,940];
		 
		//若该div不存在，则是第一次进入，应初始化当天日期 
		if( $("div .user-hasloan").length==0){
			//增加当天日期显示 
			 var str='<div class="user-hasloan" style="left:'+pos[0] +'px;">'
				+'<div class="user-hasloan-in">'
				+'<div class="user-hasloan-pop">'
				+'</div>'
				+'<div class="user-day">'+curDay+'</div>'
				+'</div>'
				+'</div>';
			 	$("div .user-day-line").append(str);
			 	//增加30天后日期显示 
				 var str='<div class="user-hasloan" style="left:'+pos[pos.length-1] +'px;">'
					+'<div class="user-hasloan-in">'
					+'<div class="user-hasloan-pop">'
					+'</div>'
					+'<div class="user-day">'+(curDay+30-maxDay)+'</div>'
					+'</div>'
					+'</div>';
				 	$("div .user-day-line").append(str);
		}
		 var payMon=0;
		 var payDay=0;
	 
		 try {
			payMon = parseInt(_payMon);
			payDay = parseInt(_payDay);
			if(isNaN(payMon)||isNaN(payDay)){
				//转换失败,跳出
				return;
			}
		} catch (e) {
			//转换失败,跳出
			return;
		}
		 
		 var hideDay=curDay+_betweenDays; //当前实际值
		 var showDay=hideDay>maxDay?(hideDay-maxDay):hideDay;//当前实际日期
	 
		 var str='<div class="user-hasloan" style="left:'+pos[_betweenDays] +'px;">'
				+'<div class="user-hasloan-in">'
				+'<div class="d-h-circleLight"> '
				+'<div class="d-h-c-dot1"></div> '
				+'<div class="d-h-c-dot2"></div> '
				+'<div class="d-h-c-dot3"></div> '
				+'</div>'
				+'<div class="user-hasloan-pop">'
				+'<em class="user-icon user-hasloan-popico"></em>'
				+'<div class="user-hasloan-popcon">'
				+'	<p>'+payMon+'月'+payDay+'日</p>'
				+'	<p>还款总额：'+formatNum2Float(payAmount)+'</p>'
				+'	<p>共有'+_payCount+'笔还款</p>'
				+'</div>'
				+'</div>'
				+'<div class="user-day">'+showDay+'</div>'
				+'</div>'
				+'</div>';
		 $("div .user-day-line").append(str);
 	}
 	/**获取数组中的最大值*/
 	function getMaxFromArray(tmp){
 		var max = tmp[0];
 		for(var i=1;i<tmp.length;i++){ 
 		  if(max<tmp[i]){
 			  max=tmp[i];
 			}
 		  }
 		return max;
 	}
 	/**获取数组中的最小值*/
	function getMinFromArray(tmp){
 		var min = tmp[0];
 		for(var i=1;i<tmp.length;i++){ 
 		  if(min>tmp[i]){
 			 min=tmp[i];
 			}
 		}
 		return min;
 	}
	
	function goLocationHistory(param){
		var url="";
		switch (param) {
		case "contractManage":
			url=contextPath+"/contractManage/userContract.jhtml"+"?rnd="+Math.random();
			break;
		case "instalmentManage":
			url=contextPath+"/instalmentManage/instalmentBill.jhtml"+"?rnd="+Math.random();
			break;
		case "account":
			url=contextPath+"/updateUserInfo/viewInfo.jhtml"+"?rnd="+Math.random();
			break;
		default:
			 url=contextPath;
			break;
		}
		window.location.href=url;
	}
	
	/***分页方法*/
	function createPage(obj,options){
		var args = $.extend({
			pageCount : 10,
			current : 1,
			backFn : function(){}
		},options);
		ms.init(obj,args);
		args.backFn(1);
	}
	
	var ms={
			init:function(obj,args){
				return (function(){
					ms.fillHtml(obj,args);
					ms.bindEvent(obj,args);
				})();
			},
			//填充html
			fillHtml:function(obj,args){
				return (function(){
					obj.empty();
					//上一页
					if(args.current > 1){
						obj.append('<a href="javascript:;" class="prevPage">上一页</a>');
					}else{
						obj.remove('.prevPage');
						obj.append('<span class="disabled">上一页</span>');
					}
					//中间页码
					if(args.current != 1 && args.current >= 4 && args.pageCount != 4){
						obj.append('<a href="javascript:;" class="tcdNumber">'+1+'</a>');
					}
					if(args.current-2 > 2 && args.current <= args.pageCount && args.pageCount > 5){
						obj.append('<span>...</span>');
					}
					var start = args.current -2,end = args.current+2;
					if((start > 1 && args.current < 4)||args.current == 1){
						end++;
					}
					if(args.current > args.pageCount-4 && args.current >= args.pageCount){
						start--;
					}
					for (;start <= end; start++) {
						if(start <= args.pageCount && start >= 1){
							if(start != args.current){
								obj.append('<a href="javascript:;" class="tcdNumber">'+ start +'</a>');
							}else{
								obj.append('<span class="current">'+ start +'</span>');
							}
						}
					}
					if(args.current + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 5){
						obj.append('<span class="grey-color">...</span>');
					}
					if(args.current != args.pageCount && args.current < args.pageCount -2  && args.pageCount != 4){
						obj.append('<a href="javascript:;" class="tcdNumber">'+args.pageCount+'</a>');
					}
					//下一页
					if(args.current < args.pageCount){
						obj.append('<a href="javascript:;" class="nextPage">下一页</a>');
					}else{
						obj.remove('.nextPage');
						obj.append('<span class="disabled">下一页</span>');
					}
					//总记录条数
					obj.append('<span class="ml20">共<span class="total-num"></span>条记录</span>');
					//总页数
					obj.append('<span class="ml10">共<span class="total-page"></span>页</span>');
				})();
			},
			//绑定事件
			bindEvent:function(obj,args){
				return (function(){
					obj.on("click","a.tcdNumber",function(){
						var current = parseInt($(this).text());
						ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
						if(typeof(args.backFn)=="function"){
							args.backFn(current);
						}
					});
					//上一页
					obj.on("click","a.prevPage",function(){
						var current = parseInt(obj.children("span.current").text());
						ms.fillHtml(obj,{"current":current-1,"pageCount":args.pageCount});
						if(typeof(args.backFn)=="function"){
							args.backFn(current-1);
						}
					});
					//下一页
					obj.on("click","a.nextPage",function(){
						var current = parseInt(obj.children("span.current").text());
						ms.fillHtml(obj,{"current":current+1,"pageCount":args.pageCount});
						if(typeof(args.backFn)=="function"){
							args.backFn(current+1);
						}
					});
				})();
			}
		}
 