$(function() {
	// 媒体表单日期操作
	$("#mediaForm").find("#datePan").find("ul li").on('click', function() {
		$(this).prevAll("li").removeClass("active");
		$(this).nextAll("li").removeClass("active");
		$(this).addClass("active");

		$("#mediaForm").find("#confirmTimeMin").val("");
		$("#mediaForm").find("#confirmTimeMax").val("");
	})

	// 媒体表单日期控件显示
	$("#mediaForm").find("#dateChange").on('click', function() {
		$("#mediaForm").find("#datePan").show();
	});

	$("#mediaForm").find("#dateArrow").on('click', function() {
		$("#mediaForm").find("#datePan").show();
	});

	$("#mediaForm").find("#confirmTimeMin").on("click", function() {
		$("#mediaForm").find("#confirmTimeMax").val("");
		$("#mediaForm").find("#datePan").find("ul li").removeClass("active");
		WdatePicker({
			skin : 'greenkoo',
			dateFmt : 'yyyy-MM-dd',
			maxDate : '%y-%M-%d',
			minDate : '#F{$dp.$D(\'confirmTimeMax\', {d:-29})}'
		});
	});

	$("#mediaForm").find("#confirmTimeMax").on('click', function() {
		var min = $("#mediaForm").find("#confirmTimeMin").val();
		var max = getPickerDate($("#mediaForm").find("#confirmTimeMin").val(),29);
		WdatePicker({
			skin : 'greenkoo',
			dateFmt : 'yyyy-MM-dd',
			maxDate : max,
			minDate : min
		});
	});

	loadMediaIllegal(1);
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

// 媒体所有的违法广告创意
function loadMediaIllegal(nextCurPage) {
	maskShow();
	bindVal('mediaForm');
	$("#mediaForm").find("#datePan").hide();
	var params = $("#mediaForm").serialize();
	$.ajax({
		url : "/advert/detail/mediaIllegalList?curPage=" + nextCurPage + "&pageSize=50",
		data : encodeURI(params),
		async:false,
		success : function(data) {
			$("#mediaIllegal").html(data);
			var count = $("#mediaIllegal").find("#totalCount").val();
			if (count == 0)
				$("#mediaIllegal").html("<div class='errorTip'>抱歉，未找到您要查询的数据</div>");
			if (count == '' || count == null)
				$("#creativeCount").html("0");
			else
				$("#creativeCount").html(count);
		},
		error : function() {
			$("#mediaIllegal").html("<div class='errorTip'>抱歉，系统出现错误！</div>");
			$("#bgBlock").fadeOut(1000);
		}
	});
}

function queryAdvertDetail(keyWord) {
	window.location = '/confirmIllegal/list?keyWord='+keyWord;
}

/*function nextPageMedia(number) {
	var curPage = $("#mediaPage").find("#curPage").val();
	curPage = parseInt(curPage);
	var totalpage = $("#mediaPage").find("#totalPage").val();
	totalpage = parseInt(totalpage);
	
	var nextCurPage = curPage + number;
	if (nextCurPage == 0){
		nextCurPage = 1;
	}else if(nextCurPage > totalpage){
		nextCurPage = totalpage;
	}
	
	loadMediaIllegal(nextCurPage);
}

function goPageMedia(pNum) {
	loadMediaIllegal(pNum);
}*/