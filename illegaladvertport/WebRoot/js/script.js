Date.prototype.Format = function (fmt) {  
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
// 日期范围控制
function getPickerDate(dateVal, day) {
	var date = new Date(dateVal);
	date.setDate(date.getDate() + day);
	var now = new Date();
	if(date > now) return now.Format("yyyy-MM-dd");
	
	return date.Format("yyyy-MM-dd");
}

$(function(){
	// 去掉超链接的虚线
	$("a,button").focus(function(){this.blur()});

	// 导航用户挪上去效果
	$(".ssp-dropdown").hover(function(){
		$("#down-menu").stop().slideDown();
	},function(){
		$("#down-menu").stop().slideUp();
	});
	
	// tab页切换效果控制
	$(".fuca").on("click",function(){
		$(this).children().children().addClass("changeColor");
		$(this).parent().siblings().children().children().children().removeClass("changeColor");
	})
	
	// 图片挪上去信息控制
	var t;
	$('.mask .waterfall').on('mouseenter','.image-box',function(){
		var $this = $(this).parent();
		var winWidth = $(window).width();
		var docHeight = $(document).height();
		var maskWidth = $this.outerWidth();
		
		var topOffset = $this.offset().top;
		var bottomOffset = docHeight - topOffset;
		var leftOffset = $this.offset().left;
		
		var $somediv = $this.find(".somediv");
		var someWidth = $somediv.outerWidth();
		
		var posTop=-maskWidth/2,posLeft=maskWidth/2;posRight='';
		
		if(leftOffset > winWidth/2) {
			posLeft = '';
			posRight = maskWidth/2;
		}
		
		t = setTimeout(function(){
			$somediv.css({'left':posLeft,'top':posTop,'right':posRight}).stop().fadeIn();
		}, 600);
	});
	
	$('.mask .waterfall').on('mouseleave',function(){
		$(this).find(".somediv").hide();
		clearTimeout(t);
	});
	
	// 点击整改
	$("#updatePwd").on('click',function(){
		$("#updatePwdModal").modal({
			show:true,
			backdrop:'static',
			keyboard:false
		});
	});
});

/*//设置图片浮层详细信息
function setVal($this) {
	$('#somediv').find("#adpic").attr('src',$this.nextAll("#adpic").val());
	console.log($('#somediv').find("#adpic").attr('src'))
	// 广告尺寸
	$('#somediv').find("#adpicSize").text($this.nextAll("#adpicSize").val());
	// 落地页
	$('#somediv').find("#landingUrl a").attr('href',$this.nextAll("#landingUrl").val()).text($this.nextAll("#landingUrl").val());
	// 广告名
	$('#somediv').find("#adName").text($this.nextAll("#adName").val());
	// 广告主
	$('#somediv').find("#advertiser a").attr('href',$this.nextAll("#advertiser").val()).text($this.nextAll("#advertiser").val());
	// 媒体
	$('#somediv').find("#media a").attr('href',$this.nextAll("#media").val()).text($this.nextAll("#media").val());
	// 投放平台
	var terminalType = $this.nextAll("#terminalType").val();
	var terminalVal = '';
	if(terminalType == '1') terminalVal = 'PC';
	if(terminalType == '2') terminalVal = 'APP';
	if(terminalType == '3') terminalVal = 'WAP';
	$('#somediv').find("#terminalType").text(terminalVal);
	
	// 采集时间
	$('#somediv').find("#collectTime").text($this.nextAll("#collectTime").val());
	// 确认结果
	var confirmLevel = $this.nextAll("#checkLevel").val();
	var confirmLevelVal = '';
	if(confirmLevel == '1') confirmLevelVal = '严重违法  <b style="color: red;">★★★</b>';
	if(confirmLevel == '2') confirmLevelVal = '一般违法  <b style="color: red;">★★</b>';
	if(confirmLevel == '3') confirmLevelVal = '轻微违法  <b style="color: red;">★</b>';
	$('#somediv').find("#confirmLevel").html(confirmLevelVal);
}*/

// 日期框点击确定
function bindDate(form) {
	
	var dateText = $("#"+form).find("#datePan").find("ul li.active a").text();
	$("#" + form).find("#dateChange").val(dateText.trim());
	if ('今日' == dateText) {
		var date = new Date().Format("yyyy-MM-dd");
		$("#"+form).find("#confirmTimeMin").val(date);
		$("#"+form).find("#confirmTimeMax").val(date);
		$("#" + form).find("#dateChange").val(date);
	} else {
		var confirmTimeMin = $("#"+form).find("#confirmTimeMin").val().trim();
		var confirmTimeMax = $("#"+form).find("#confirmTimeMax").val().trim();
		if(confirmTimeMin != '' && confirmTimeMax != '') {
			$("#"+form).find("#dateChange").val(confirmTimeMin + "至" +confirmTimeMax);
		}
	}
	
	$("#"+form).find("#datePan").hide();
	
	if(form == "dataCount") {
		loadSevereCount();
		loadCommonCount();
		loadLightCount();
		loadChartData();
	}
}

// 日期框点击取消
function cancelDate(form) {
	$("#"+form).find("#datePan").hide();
}

// 图片加载遮罩显示
function maskShow() {
	$("#bgBlock").show();
}

//图片加载遮罩隐藏
function maskHide() {
	$("#bgBlock").fadeOut(500);
}

// ajax session 失效全局处理
$.ajaxSetup({   
    contentType:"application/x-www-form-urlencoded;charset=utf-8",   
    cache:false ,   
    complete:function(XHR,TS){   
    	var resText=XHR.responseText; 
    	if(resText == 'session-invalidate') {
    		window.location = "/login";
    	}
    }    
});
