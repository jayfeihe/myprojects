$(function() {
	loadSevereCount();
	loadCommonCount();
	loadLightCount();
	

	$('.bangzhu').hover(function(){
		$(this).parent().find('.answer').show();
	},function(){
		$(this).parent().find('.answer').hide();
	});
})

// 严重违法
function loadSevereCount() {
	var dateChange = $("#dateChange").val().trim();
	var confirmTimeMin = $("#confirmTimeMin").val().trim();
	var confirmTimeMax = $("#confirmTimeMax").val().trim();
	$.ajax({
		url : "/dataCount/severe",
		data : {
			'dateChange' : dateChange,
			'confirmTimeMin' : confirmTimeMin,
			'confirmTimeMax' : confirmTimeMax
		},
		success : function(data) {
			// 初始化广告
			initAdvert("#home", data.advert);
			// 初始化广告创意
			initCreative("#home", data.creative);
			if(data.companyType == 1) {
				// 初始化媒体
				initMedia("#home", data.media);
			}
			if(data.companyType == 2) {
				// 初始化广告zhu
				initAdvertiser("#home", data.advertiser);
			}
			
			// 初始化违法占比
			initCountProp("#home", data.countProp);
			
			// 初始化总体已整改广告创意
			initAllCorrect("#home", data.allCorrect);
			// 初始化我方已整改广告创意
			initSelfCorrect("#home", data.selfCorrect);
			
		},
		error : function() {
		}
	});
}

// 一般违法
function loadCommonCount() {
	var dateChange = $("#dateChange").val().trim();
	var confirmTimeMin = $("#confirmTimeMin").val().trim();
	var confirmTimeMax = $("#confirmTimeMax").val().trim();
	$.ajax({
		url : "/dataCount/common",
		data : {
			'dateChange' : dateChange,
			'confirmTimeMin' : confirmTimeMin,
			'confirmTimeMax' : confirmTimeMax
		},
		success : function(data) {
			// 初始化广告
			initAdvert("#profile", data.advert);
			// 初始化广告创意
			initCreative("#profile", data.creative);
			if(data.companyType == 1) {
				// 初始化媒体
				initMedia("#profile", data.media);
			}
			if(data.companyType == 2) {
				// 初始化广告zhu
				initAdvertiser("#profile", data.advertiser);
			}
			// 初始化违法占比
			initCountProp("#profile", data.countProp);
			
			// 初始化总体已整改广告创意
			initAllCorrect("#profile", data.allCorrect);
			// 初始化我方已整改广告创意
			initSelfCorrect("#profile", data.selfCorrect);
		},
		error : function() {
		}
	});
}

// 轻微违法
function loadLightCount() {
	var dateChange = $("#dateChange").val().trim();
	var confirmTimeMin = $("#confirmTimeMin").val().trim();
	var confirmTimeMax = $("#confirmTimeMax").val().trim();
	$.ajax({
		url : "/dataCount/light",
		data : {
			'dateChange' : dateChange,
			'confirmTimeMin' : confirmTimeMin,
			'confirmTimeMax' : confirmTimeMax
		},
		success : function(data) {
			// 初始化广告
			initAdvert("#messages", data.advert);
			// 初始化广告创意
			initCreative("#messages", data.creative);
			if(data.companyType == 1) {
				// 初始化媒体
				initMedia("#messages", data.media);
			}
			if(data.companyType == 2) {
				// 初始化广告zhu
				initAdvertiser("#messages", data.advertiser);
			}
			// 初始化违法占比
			initCountProp("#messages", data.countProp);
			
			// 初始化总体已整改广告创意
			initAllCorrect("#messages", data.allCorrect);
			// 初始化我方已整改广告创意
			initSelfCorrect("#messages", data.selfCorrect);
		},
		error : function() {
		}
	});
}

function initAdvert(level, advertData) {
	var $level = $("#dataCount").find(level);
	$level.find("#advertNumber").text(advertData.count);
	var proportion = advertData.timeProp;
	if (isNaN(proportion)) {
		$level.find("#advertProportion").text(proportion);
	} else if (proportion > 0) {
		$level.find("#advertProportion").text("+" + proportion + "%");
	} else {
		$level.find("#advertProportion").text(proportion + "%");
	}
	var increase = advertData.increase;
	if (increase > 0) {
		$level.find("#advertIncrease").text("+" + increase);
	} else {
		$level.find("#advertIncrease").text(increase);
	}
}

function initCreative(level, creativeData) {
	var $level = $("#dataCount").find(level);
	$level.find("#creativeNumber").text(creativeData.count);
	var proportion = creativeData.timeProp;
	if (isNaN(proportion)) {
		$level.find("#creativeProportion").text(proportion);
	} else if (proportion > 0) {
		$level.find("#creativeProportion").text("+" + proportion + "%");
	} else {
		$level.find("#creativeProportion").text(proportion + "%");
	}
	var increase = creativeData.increase;
	if (increase > 0) {
		$level.find("#creativeIncrease").text("+" + increase);
	} else {
		$level.find("#creativeIncrease").text(increase);
	}
}

function initAdvertiser(level, advertiserData) {
	var $level = $("#dataCount").find(level);
	$level.find("#advertiserNumber").text(advertiserData.count);
	var proportion = advertiserData.timeProp;
	if (isNaN(proportion)) {
		$level.find("#advertiserProportion").text(proportion);
	} else if (proportion > 0) {
		$level.find("#advertiserProportion").text("+" + proportion + "%");
	} else {
		$level.find("#advertiserProportion").text(proportion + "%");
	}
	var increase = advertiserData.increase;
	if(increase > 0) {
		$level.find("#advertiserIncrease").text("+"+increase);
	} else {
		$level.find("#advertiserIncrease").text(increase);
	} 
}

function initMedia(level, mediaData) {
	var $level = $("#dataCount").find(level);
	$level.find("#mediaNumber").text(mediaData.count);
	var proportion = mediaData.timeProp;
	if (isNaN(proportion)) {
		$level.find("#mediaProportion").text(proportion);
	} else if (proportion > 0) {
		$level.find("#mediaProportion").text("+" + proportion + "%");
	} else {
		$level.find("#mediaProportion").text(proportion + "%");
	}
	var increase = mediaData.increase;
	if(increase > 0) {
		$level.find("#mediaIncrease").text("+"+increase);
	} else {
		$level.find("#mediaIncrease").text(increase);
	} 
}

function initCountProp(level, countPropData) {
	var $level = $("#dataCount").find(level);
	$level.find("#countProp").text(countPropData + "%");
}

function initAllCorrect(level, allCorrectData) {
	var $level = $("#dataCount").find(level);
	$level.find("#allCorrectNumber").text(allCorrectData.count);
	var proportion = allCorrectData.timeProp;
	if (isNaN(proportion)) {
		$level.find("#allCorrectProportion").text(proportion);
	} else if (proportion > 0) {
		$level.find("#allCorrectProportion").text("+" + proportion + "%");
	} else {
		$level.find("#allCorrectProportion").text(proportion + "%");
	}
	var increase = allCorrectData.increase;
	if (increase > 0) {
		$level.find("#allCorrectIncrease").text("+" + increase);
	} else {
		$level.find("#allCorrectIncrease").text(increase);
	}
}

function initSelfCorrect(level, selfCorrectData) {
	var $level = $("#dataCount").find(level);
	$level.find("#selfCorrectNumber").text(selfCorrectData.count);
	var proportion = selfCorrectData.timeProp;
	if (isNaN(proportion)) {
		$level.find("#selfCorrectProportion").text(proportion);
	} else if (proportion > 0) {
		$level.find("#selfCorrectProportion").text("+" + proportion + "%");
	} else {
		$level.find("#selfCorrectProportion").text(proportion + "%");
	}
	var increase = selfCorrectData.increase;
	if (increase > 0) {
		$level.find("#selfCorrectIncrease").text("+" + increase);
	} else {
		$level.find("#selfCorrectIncrease").text(increase);
	}
}

