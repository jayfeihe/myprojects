/*
* @author 廖艳丽（QQ:1079160717）
* @description 修改基本信息
*/
$(function(){

	//选择兴趣爱好
	$("#pay-stages-list li").click(function(){
		$(this).toggleClass("hover");
	});
    /*
     * 生成级联菜单
     */
    var i=1945;
    var date = new Date();
    year = date.getFullYear();//获取当前年份
    var dropList;
    for(i;i<year+1;i++){
        if(i == year){
            dropList = dropList + "<option value='"+i+"' selected>"+i+"</option>";
        }else{
            dropList = dropList + "<option value='"+i+"'>"+i+"</option>";
        }
    }
    $('#birdthday_year').html(dropList);//生成年份下拉菜单
    var monthly;
    for(month=1;month<13;month++){
        monthly = monthly + "<option value='"+month+"'>"+month+"月</option>";
    }
    $('#birdthday_month').html(monthly);//生成月份下拉菜单
    var dayly;
    for(day=1;day<=31;day++){
        dayly = dayly + "<option value='"+day+"'>"+day+"日</option>";
    }
    $('#birdthday_day').html(dayly);//生成天数下拉菜单
    /*
     * 处理每个月有多少天---联动
     */
	$('#birdthday_month').change(function(){
	var currentDay;
	var Flag = $('#birdthday_year').val();
	var currentMonth = $('#birdthday_month').val();
	switch(currentMonth){
		case "1" :
		case "3" :
		case "5" :
		case "7" :
		case "8" :
		case "10" :
		case "12" :total = 31;break;
		case "4" :
		case "6" :
		case "9" :
		case "11" :total = 30;break;
		case "2" :
			if((Flag%4 == 0 && Flag%100 != 0) || Flag%400 == 0){
				total = 29;
			}else{
				total = 28;
			}
		default:break;
	}
	for(day=1;day <= total;day++){
		currentDay = currentDay + "<option value='"+day+"'>"+day+"日</option>";
	}
	$('#birdthday_day').html(currentDay);//生成日期下拉菜单
	})

	//性别切换时，默认头像的修改
	$("input[name='sex']").change(function(){
		var _this = $(this);
		if(_this.val()==1){
			$("#user_pic").attr("src",contextPath+"/"+"images/user-man.png");
		}else if(_this.val()==0){
			$("#user_pic").attr("src",contextPath+"/"+"images/user-woman.png");
		}else{
			$("#user_pic").attr("src",contextPath+"/"+"images/user-default.png");
		}
	});
});