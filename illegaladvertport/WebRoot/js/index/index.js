$(function() {
	// 日期操作
	$("#datePan").find("ul li").on('click', function() {
		$(this).prevAll("li").removeClass("active");
		$(this).nextAll("li").removeClass("active");
		$(this).addClass("active");

		$("#confirmTimeMin").val("");
		$("#confirmTimeMax").val("");
	});

	var date = new Date().Format("yyyy-MM-dd");
	$("#dateChange").val(date);
	$("#confirmTimeMin").val(date);
	$("#confirmTimeMax").val(date);
	
	// 日期控件显示
	$("#dateChange").on('click', function() {
		$("#datePan").show();
	});

	$("#confirmTimeMin").on("click", function() {
		$("#confirmTimeMax").val("");
		$("#datePan").find("ul li").removeClass("active");
		WdatePicker({
			skin : 'greenkoo',
			dateFmt : 'yyyy-MM-dd',
			maxDate : '%y-%M-%d',
			/*minDate : '#F{$dp.$D(\'confirmTimeMax\', {d:-29})}'*/
		});
	});

	$("#confirmTimeMax").on('click', function() {
		var min = $("#confirmTimeMin").val();
		var max = getPickerDate($("#confirmTimeMin").val(),29);
		WdatePicker({
			skin : 'greenkoo',
			dateFmt : 'yyyy-MM-dd',
			maxDate : max,
			minDate : min
		});
	});

	$("#dateArrow").on('click', function() {
		$("#datePan").show();
	});

	
	loadDataCount();
	loadChartData();
	loadLatestIllegal(1);
	loadSevereIllegal(1);
});

// 数据概况
function loadDataCount() {
	$.ajax({
		url : "/dataRecord/dataCount",
		success : function(data) {
			$("#dataCountArea").html(data);
		},
		error : function() {
			$("#dataCountArea").html("<div class='errorTip'>抱歉，系统出现错误！</div>");
		}
	});
}

// 图表
function loadChart() {
	$.ajax({
		url : "/dataRecord/loadChart",
		success : function(data) {
			$("#chartArea").html(data);
		},
		error : function() {
			$("#chartArea").html("<div class='errorTip'>抱歉，系统出现错误！</div>");
		}
	});
}

// 最新违法广告创意
function loadLatestIllegal(nextCurPage) {
	maskShow();
	$.ajax({
		url : "/dataRecord/latestIllegalList",
		/*data : {
			'curPage' : nextCurPage
		},*/
		async:false,
		success : function(data) {
			$("#latestIllegal").html(data);
			var count = $("#latestIllegal").find("#totalCount").val();
			if (count == 0) 
				$("#latestIllegal").html("<div class='errorTip'>抱歉，未找到您要查询的数据</div>");
		},
		error : function() {
			$("#latestIllegal").html("<div class='errorTip'>抱歉，系统出现错误！</div>");
			$("#bgBlock").fadeOut(1000);
		}
	});
}

// 严重违法广告创意
function loadSevereIllegal(nextCurPage) {
	maskShow();
	$.ajax({
		url : "/dataRecord/severeIllegalList",
		/*data : {
			'curPage' : nextCurPage
		},*/
		async:false,
		success : function(data) {
			$("#severeIllegal").html(data);
			var count = $("#severeIllegal").find("#totalCount").val();
			if (count == 0) 
				$("#severeIllegal").html("<div class='errorTip'>抱歉，未找到您要查询的数据</div>");
		},
		error : function() {
			$("#severeIllegal").html("<div class='errorTip'>抱歉，系统出现错误！</div>");
			$("#bgBlock").fadeOut(1000);
		}
	});
}

$("#severeTab").one('click',function(){console.log(2)
	loadSevereIllegal();
})

/*function latestImgLoaded() {
	var defereds = [];
	var $imgs = $("#latestIllegal").find('.thumb');
	$imgs.each(function() {
		var dfd = $.Deferred();
		$(this).load(dfd.resolve);
		defereds.push(dfd);
	});
	$.when.apply(null, defereds).done(function() {
		$("#latest-gallery-wrapper").pinterest_grid({
			no_columns : 5,
			padding_x : 10,
			padding_y : 10,
			margin_bottom : 50,
			single_column_breakpoint : 700
		});
	});
}

function servereImgLoaded() {
	var defereds = [];
	var $imgs = $("#severeIllegal").find('.thumb');
	$imgs.each(function() {
		var dfd = $.Deferred();
		$(this).load(dfd.resolve);
		defereds.push(dfd);
	});
	$.when.apply(null, defereds).done(function() {
		$("#severe-gallery-wrapper").pinterest_grid({
			no_columns : 5,
			padding_x : 10,
			padding_y : 10,
			margin_bottom : 50,
			single_column_breakpoint : 700
		});
	});
}*/

/*var lCount = 0;	
function nextPageLatest(number) {
	var curPage = $("#latestPage").find("#curPage").val();
	curPage = parseInt(curPage);
	var totalpage = $("#latestPage").find("#totalPage").val();
	totalpage = parseInt(totalpage);
	
	var nextCurPage = curPage + number;
	if (nextCurPage == 0){
		nextCurPage = 1;
	}else if(nextCurPage > totalpage){
		nextCurPage = totalpage;
	}
	
	loadLatestIllegal(nextCurPage);
	
	lCount ++;
	if(lCount != 0) {
		var top = $("#latestIllegal").offset().top;
		var win_height = $(window).height();
		$(window).scrollTop(parseInt(top-150));
	}
}

function goPageLatest(pNum) {
	loadLatestIllegal(pNum);
	lCount ++;
	if(lCount != 0) {
		var top = $("#latestIllegal").offset().top;
		var win_height = $(window).height();
		$(window).scrollTop(parseInt(top-150));
	}
}

var sCount = 0
function nextPageSevere(number) {
	var curPage = $("#severePage").find("#curPage").val();
	curPage = parseInt(curPage);
	var totalpage = $("#severePage").find("#totalPage").val();
	totalpage = parseInt(totalpage);
	
	var nextCurPage = curPage + number;
	if (nextCurPage == 0){
		nextCurPage = 1;
	}else if(nextCurPage > totalpage){
		nextCurPage = totalpage;
	}
	
	loadSevereIllegal(nextCurPage);
	
	sCount ++;
	if(sCount != 0) {
		var top = $("#severeIllegal").offset().top;
		var win_height = $(window).height();
		$(window).scrollTop(parseInt(top-150));
	}
}

function goPageSevere(pNum) {
	loadSevereIllegal(pNum);
	sCount ++;
	if(sCount != 0) {
		var top = $("#severeIllegal").offset().top;
		var win_height = $(window).height();
		$(window).scrollTop(parseInt(top-150));
	}
}*/