/*
* @author 廖艳丽（QQ:1079160717）
* @description 分期账单js
*/
$(function(){	
	$('#user-loan-tabcontainer').load(contextPath+'/contractManage/contractList.jhtml?curLi=0'+"&rnd="+Math.random(),function(responseText, textStatus, XMLHttpRequest){
		var result;
		try {
			result = eval("(" + responseText + ")");
			if(result.code==-1){
				window.location.reload(true);
			}
		} catch (e) {
		   //不能转换为json说明请求无误
		}
	});
	//tab
	$("#user-loan-tabs li").click(function(){
		var $this = $(this);
		var index = $this.index();
		 
		$this.addClass("current");
		$this.siblings("li").removeClass("current");
		//$('#user-loan-tabcontainer').html("");
		$('#user-loan-tabcontainer').load(contextPath+'/contractManage/contractList.jhtml?curLi='+index+"&rnd="+Math.random(),function(responseText, textStatus, XMLHttpRequest){
			
			var result;
			try {
				result = eval("(" + responseText + ")");
				if(result.code==-1){
					window.location.reload(true);
				}
			} catch (e) {
			   //不能转换为json说明请求无误
			}
		});
		//切换tab显示，不重新加载画面
		/*$("div .user-contract-item").each(function(){
			if(index==0){
				$(this).show();
			}else if(index==1){
				var status=$(this).find("input[name='instalmentStatus']").val();
				if(status==1){
					$(this).show();
				}else{
					$(this).hide();
				}
			}else if(index==2){
				var status=$(this).find("input[name='instalmentStatus']").val();
				if(status==2){
					$(this).show();
				}else{
					$(this).hide();
				}
			}
		});*/
		 
	});
	
	//省略号处理
	$(".user-pro-title-hide").each(function(){
		var $this = $(this);
		var $other = $this.prev(".user-pro-title").find("a");
		$this.text($other.text());
		if($this.width()>$this.prev(".user-pro-title").width()){
			$other.attr("title",$other.text());
		}else{
			$other.attr("title","");
		}
	});
	//查看合同
	$(".look_contract").click(function(){
		$("#contract_dialog").show();
	});
	//查看合同
	$("#close_contract_dialog,#close_contract_dialog_r").click(function(){
		$("#contract_dialog").hide();
	});
	
});

function querySub(){
	var index=$("#user-loan-tabs").find(".current").index();
	$('#user-loan-tabcontainer').load(contextPath+'/contractManage/contractList.jhtml?curLi='+index+'&queryNum='+$("#queryNum").val()+"&rnd="+Math.random(),function(responseText, textStatus, XMLHttpRequest){
		var result;
		try {
			result = eval("(" + responseText + ")");
			if(result.code==-1){
				window.location.reload(true);
			}
		} catch (e) {
		   //不能转换为json说明请求无误
		}
	});
}

//查看合同
function queryContactText(id){
	$("#contractContent").html("");
		$("#contractContent").load(contextPath+'/contractManage/requestJkht.jhtml?instalmentId='+id+"&rnd="+Math.random(),function(responseText, textStatus, XMLHttpRequest){
			var result;
			try {
				result = eval("(" + responseText + ")");
				if(result.code==-1){
					window.location.reload(true);
				}
			} catch (e) {
			   //不能转换为json说明请求无误
			}
			$("#contract_dialog").show();
		});
} 