$(function() {
	// 广告表单日期操作
	$("#confirmAdvertForm").find("#datePan").find("ul li").on('click',
		function() {
			$(this).prevAll("li").removeClass("active");
			$(this).nextAll("li").removeClass("active");
			$(this).addClass("active");
	
			$("#confirmAdvertForm").find("#confirmTimeMin").val("");
			$("#confirmAdvertForm").find("#confirmTimeMax").val("");
		}
	)

	// 广告表单日期控件显示
	$("#confirmAdvertForm").find("#dateChange").on('click', function() {
		$("#confirmAdvertForm").find("#datePan").show();
	});

	$("#confirmAdvertForm").find("#dateArrow").on('click', function() {
		$("#confirmAdvertForm").find("#datePan").show();
	});

	$("#confirmAdvertForm").find("#confirmTimeMin").on("click", function() {
		$("#confirmAdvertForm").find("#confirmTimeMax").val("");
		$("#confirmAdvertForm").find("#datePan").find("ul li").removeClass("active");
		WdatePicker({
			skin : 'greenkoo',
			dateFmt : 'yyyy-MM-dd',
			maxDate : '%y-%M-%d',
			/*minDate : '#F{}'*/
		});
	});

	$("#confirmAdvertForm").find("#confirmTimeMax").on('click', function() {
		var min = $("#confirmAdvertForm").find("#confirmTimeMin").val();
		var max = getPickerDate($("#confirmAdvertForm").find("#confirmTimeMin").val(),29);
		WdatePicker({
			skin : 'greenkoo',
			dateFmt : 'yyyy-MM-dd',
			maxDate : max,
			minDate : min
		});
	});

	// 广告创意表单日期操作
	$("#confirmCreativeForm").find("#datePan").find("ul li").on('click',
			function() {
				$(this).prevAll("li").removeClass("active");
				$(this).nextAll("li").removeClass("active");
				$(this).addClass("active");

				$("#confirmCreativeForm").find("#confirmTimeMin").val("");
				$("#confirmCreativeForm").find("#confirmTimeMax").val("");
			})

	// 广告创意日期控件显示
	$("#confirmCreativeForm").find("#dateChange").on('click', function() {
		$("#confirmCreativeForm").find("#datePan").show();
	});

	$("#confirmCreativeForm").find("#dateArrow").on('click', function() {
		$("#confirmCreativeForm").find("#datePan").show();
	});

	$("#confirmCreativeForm").find("#confirmTimeMin").on("click", function() {
		$("#confirmCreativeForm").find("#confirmTimeMax").val("");
		$("#confirmCreativeForm").find("#datePan").find("ul li").removeClass("active");
		WdatePicker({
			skin : 'greenkoo',
			dateFmt : 'yyyy-MM-dd',
			maxDate : '%y-%M-%d',
			/*minDate : '#F{}'*/
		});
	});

	$("#confirmCreativeForm").find("#confirmTimeMax").on('click', function() {
		var min = $("#confirmCreativeForm").find("#confirmTimeMin").val();
		var max = getPickerDate($("#confirmCreativeForm").find("#confirmTimeMin").val(),29);
		WdatePicker({
			skin : 'greenkoo',
			dateFmt : 'yyyy-MM-dd',
			maxDate : max,
			minDate : min
		});
	});

	loadConfirmAdvertIllegal(1);
	loadConfirmCreativeIllegal(1);
	loadConfirmRelatedList();
});

function bindVal(form) {
	var confirmTimeMin = $("#"+form).find("#confirmTimeMin").val().trim();
	var confirmTimeMax = $("#"+form).find("#confirmTimeMax").val().trim();
	
	if(confirmTimeMin != '' && confirmTimeMax != '') {
		$("#"+form).find("#dateChange").val(confirmTimeMin + "至" +confirmTimeMax);
	} else {
		var dateText = $("#"+form).find("#datePan").find("ul li.active a").text();
		if ('今日' == dateText) {
			var date = new Date().Format("yyyy-MM-dd");
			$("#"+form).find("#confirmTimeMin").val(date);
			$("#"+form).find("#confirmTimeMax").val(date);
		}
		$("#" + form).find("#dateChange").val(dateText.trim());
	}
}

// 确认违法广告列表
function loadConfirmAdvertIllegal(nextCurPage) {
	maskShow();
	bindVal("confirmAdvertForm");
	$("#confirmAdvertForm").find("#datePan").hide();
	$("#confirmCreativeForm").find("#datePan").hide();
	var params = $("#confirmAdvertForm").serialize();
	var keyWord = $("#keyWord").val().trim();
	$.ajax({
		url : "/confirmIllegal/list/advert?keyWord=" + keyWord + "&curPage=" + nextCurPage, /*+ "&pageSize=50"*/
		data : encodeURI(params),
		async:false,
		success : function(data) {
			$("#confirmAdvertIllegal").html(data);
			var count = $("#confirmAdvertIllegal").find("#totalCount").val();
			if (count == 0) 
				$("#confirmAdvertIllegal").html("<div class='errorTip'>抱歉，未找到您要查询的数据</div>");
			
			if (count == '' || count == null) 
				$("#advertCount").text('0');
			else 
				$("#advertCount").text(count);
		},
		error : function() {
			$("#confirmAdvertIllegal").html("<div class='errorTip'>抱歉，系统出现错误！</div>");
			setTimeout(function(){
				maskHide();
			},1000);
		}
	});
}

// 确认违法广告创意列表
function loadConfirmCreativeIllegal(nextCurPage) {
	maskShow();
	bindVal("confirmCreativeForm");
	$("#confirmCreativeForm").find("#datePan").hide();
	$("#confirmCreativeForm").find("#datePan").hide();
	var params = $("#confirmCreativeForm").serialize();
	var keyWord = $("#keyWord").val().trim();
	$.ajax({
		url : "/confirmIllegal/list/creative?keyWord=" + keyWord + "&curPage=" + nextCurPage, /*+ "&pageSize=50"*/
		data : encodeURI(params),
		async:false,
		success : function(data) {
			$("#confirmCreativeIllegal").html(data);
			var count = $("#confirmCreativeIllegal").find("#totalCount").val();
			if (count == 0)
				$("#confirmCreativeIllegal").html("<div class='errorTip'>抱歉，未找到您要查询的数据</div>");
			
			if (count == '' || count == null)
				$("#creativeCount").text('0');
			else
				$("#creativeCount").text(count);
		},
		error : function() {
			$("#confirmCreativeIllegal").html("<div class='errorTip'>抱歉，系统出现错误！</div>");
			setTimeout(function(){
				maskHide();
			},1000);
		}
	});
}

function loadConfirmRelatedList() {
	var keyWord = $("#keyWord").val().trim();
	$.ajax({
		url : "/confirmIllegal/list/related",
		data : {
			'keyWord' : keyWord
		},
		success : function(data) {
			$("#relatedArea").html(data);
			var count = $("#relatedArea").find("#totalCount").val();
			if (count == 0)
				$("#relatedArea").html("<div class='errorTip'>抱歉，未找到您要查询的数据！</div>");
			
			if (count == '' || count == null)
				$("#relatedCount").text('0');
			else
				$("#relatedCount").text(count);
			
		},
		error : function() {
			$("#relatedArea").html("<div class='errorTip'>抱歉，系统出现错误！</div>");
		}
	});
}

// GO查询
function queryAdvert() {
	$("#confirmAdvertForm").find("#datePan").hide();
	$("#confirmCreativeForm").find("#datePan").hide();
	//if ($("#home").is(":visible"))
		loadConfirmAdvertIllegal(1);
	//if ($("#profile").is(":visible"))
		loadConfirmCreativeIllegal(1);
	//if ($("#messages").is(":visible"))
		loadConfirmRelatedList();
}

// 切换标签进行查询
// 广告
$("#advert").on('click', function() {
//	$("#keyWord").val("");
	/*if ($("#home").is(":hidden")) {
		loadConfirmAdvertIllegal(1);
	}*/
})

// 创意
$("#creative").on('click', function() {
//	$("#keyWord").val("");
	/*if ($("#profile").is(":hidden")) {
		loadConfirmCreativeIllegal(1);
	}*/
})
$("#creative").one('click', function() {
	loadConfirmCreativeIllegal(1);
})
// 创意
$("#related").on('click', function() {
//	$("#keyWord").val("");
	/*if ($("#messages").is(":hidden")) {
		loadConfirmRelatedList(1);
	}*/
})

function nextPageAdvert(number) {
	var curPage = $("#advertPage").find("#curPage").val();
	curPage = parseInt(curPage);
	var totalpage = $("#advertPage").find("#totalPage").val();
	totalpage = parseInt(totalpage);

	var nextCurPage = curPage + number;
	if (nextCurPage == 0) {
		nextCurPage = 1;
	} else if (nextCurPage > totalpage) {
		nextCurPage = totalpage;
	}

	loadConfirmAdvertIllegal(nextCurPage);
}

function goPageAdvert(pNum) {
	loadConfirmAdvertIllegal(pNum);
}

function nextPageCreative(number) {
	var curPage = $("#creativePage").find("#curPage").val();
	curPage = parseInt(curPage);
	var totalpage = $("#creativePage").find("#totalPage").val();
	totalpage = parseInt(totalpage);
	
	var nextCurPage = curPage + number;
	if (nextCurPage == 0){
		nextCurPage = 1;
	}else if(nextCurPage > totalpage){
		nextCurPage = totalpage;
	}
	$('body,html').animate({scrollTop:0},100);
	loadConfirmCreativeIllegal(nextCurPage);
}

function goPageCreative(pNum) {
	$('body,html').animate({scrollTop:0},100);
	loadConfirmCreativeIllegal(pNum);
}