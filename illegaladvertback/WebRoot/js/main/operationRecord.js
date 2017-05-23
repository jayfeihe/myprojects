/**
 * 广告主管理
*/
var operationRecord = {
	// 列表查询
	submitForm : function(params) {
		var curPage = params['curPage'];
		var url = params['url'];
		var params = $("#queryForm").serialize();
		$.ajax({
			method : 'get',
			url : url +'?curPage=' + curPage,
			dataType : 'html',
			data : params,
			success : function(data) {
				$("#queryList").html(data);
			}
		})
	},
	
	queryForm : function(params) {
		var curPage = params['curPage'];
		var url = params['url'];
		$("#queryBtn").on('click',function(){
			operationRecord.submitForm({
				'url':url,
				'curPage':curPage
			});
		})
	}
}