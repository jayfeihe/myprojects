$(function() {
	// 广告主表单日期操作
	$("#adverterForm").find("#datePan").find("ul li").on('click', function() {
		$(this).prevAll("li").removeClass("active");
		$(this).nextAll("li").removeClass("active");
		$(this).addClass("active");

		$("#adverterForm").find("#confirmTimeMin").val("");
		$("#adverterForm").find("#confirmTimeMax").val("");
	})

	// 广告主表单日期控件显示
	$("#adverterForm").find("#dateChange").on('click', function() {
		$("#adverterForm").find("#datePan").show();
	});

	$("#adverterForm").find("#dateArrow").on('click', function() {
		$("#adverterForm").find("#datePan").show();
	});

	$("#adverterForm").find("#confirmTimeMin").on("click", function() {
		$("#adverterForm").find("#confirmTimeMax").val("");
		$("#adverterForm").find("#datePan").find("ul li").removeClass("active");
		WdatePicker({
			skin : 'greenkoo',
			dateFmt : 'yyyy-MM-dd',
			maxDate : '%y-%M-%d',
			/*minDate : '#F{$dp.$D(\'confirmTimeMax\', {d:-29})}'*/
		});
	});

	$("#adverterForm").find("#confirmTimeMax").on('click', function() {
		var min = $("#adverterForm").find("#confirmTimeMin").val();
		var max = getPickerDate($("#adverterForm").find("#confirmTimeMin").val(),29);
		WdatePicker({
			skin : 'greenkoo',
			dateFmt : 'yyyy-MM-dd',
			maxDate : max,
			minDate : min
		});
	});

	loadAdverterIllegal(1);
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

// 广告主所有的违法广告创意
function loadAdverterIllegal(nextCurPage) {
	maskShow();
	bindVal('adverterForm');
	$("#adverterForm").find("#datePan").hide();
	var params = $("#adverterForm").serialize();
	$.ajax({
		url : "/advert/detail/adverterIllegalList",
//				"?curPage=" + nextCurPage + "&pageSize=50",
		data : encodeURI(params),
		async:false,
		success : function(data) {
			$("#adverterIllegal").html(data);
			var count = $("#adverterIllegal").find("#totalCount").val();
			if (count == 0)
				$("#adverterIllegal").html("<div class='errorTip'>抱歉，未找到您要查询的数据</div>");
			if (count == '' || count == null)
				$("#creativeCount").html("0");
			else
				$("#creativeCount").html(count);
		},
		error : function() {
			$("#adverterIllegal").html("<div class='errorTip'>抱歉，系统出现错误！</div>");
			$("#bgBlock").fadeOut(1000);
		}
	});
}

function queryAdvertDetail(keyWord) {
	window.location = '/confirmIllegal/list?keyWord='+keyWord;
}

/*function nextPageAdverter(number) {
	var curPage = $("#adverterPage").find("#curPage").val();
	curPage = parseInt(curPage);
	var totalpage = $("#adverterPage").find("#totalPage").val();
	totalpage = parseInt(totalpage);
	
	var nextCurPage = curPage + number;
	if (nextCurPage == 0){
		nextCurPage = 1;
	}else if(nextCurPage > totalpage){
		nextCurPage = totalpage;
	}
	
	loadAdverterIllegal(nextCurPage);
}

function goPageAdverter(pNum) {
	loadAdverterIllegal(pNum);
}*/