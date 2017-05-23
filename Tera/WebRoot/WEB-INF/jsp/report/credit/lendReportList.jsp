<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<!DOCTYPE html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
	<base href="<%=basePath%>" />
</head>

<body>
	<div class="content">
		<div id="control" class="control">
			<a href="javascript:void(0);" onclick="changeView('list');"><img src="img/square/list.png" class='dotimg' title="列表" /></a>&nbsp;
			<a href="javascript:void(0);" onclick="changeView('report');;"><img src="img/square/report.png" class='dotimg' title="报表" /></a>&nbsp;
			<a href="javascript:void(0);" onclick="exportExcel('queryForm');"><img src="img/square/export.png" class='dotimg' title="导出" /></a>&nbsp;
			<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
		</div>
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">申请编号</th>
					<th scope="col">产品</th>
					<th scope="col">放款金额</th>
					<th scope="col">城市</th>
					<th scope="col">放款完成日期</th>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.appId}</td>
						<td>${data.product}</td>
						<td>${data.lendAmount}</td>
						<td>${data.city}</td>
						<td>
							<fmt:formatDate value="${data.lendDate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<div id="pageStyle">
				${ pm.pageNavigation}
			</div>
		</form>
		<div id="echartMain" style="height:500px;padding:10px;border-top:1px solid #f1f1f1; display: none;"></div>
	</div>
	
	<script type="text/javascript">
		function changeView(type) {
			if('list' == type) {
				$("#echartMain").hide();
				submitForm('queryForm');
				$("#queryBtn").attr('onclick',"submitForm('queryForm')");
			}
			if("report" == type) {
				$("#echartMain").show();
				$("#queryList").hide();
				echartLoad('queryForm');
				$("#queryBtn").attr('onclick',"echartLoad('queryForm')");
			}
		}
		
		//导出
		function exportExcel(fromId) {
			$('#' + fromId).attr("action","<%=basePath%>report/credit/lendReport/exportExcel.do");
			$('#' + fromId).attr("method","post");
			$('#' + fromId).submit();
		}
	</script>
	
	<!-- echart相关 -->
    <script type="text/javascript">
	    var myChart;
	    var echarts;
	    var domMain = document.getElementById('echartMain');
    	require.config({
	        paths: {
	            echarts: '<%=basePath%>js/echarts'
	        }
	    });
	    
	    require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line'
	        ],
	        function (ec) {
	        	echarts = ec;
	        	myChart = ec.init(domMain);
	           
	            myChart.setOption({
	                tooltip : {
	                    trigger: 'axis'
	                },
	                legend: {
	                    data:[]
	                },
	                toolbox: {
	                    show : true,
	                    feature : {
	                        //mark : {show: true},
	                        //dataView : {show: true, readOnly: false},
	                        magicType : {show: true, type: ['line', 'bar']},
	                        restore : {show: true},
	                        saveAsImage : {show: true}
	                    }
	                },
	               // calculable : true,
	                xAxis : [
	                    {
	                        type : 'category',
	                        data : []
	                    }
	                ],
	                axisLabel:[
	                        {
	                          interval:0
	                        }
	                ],
	                yAxis : [
	                    {
	                        type : 'value',
	                        splitArea : {show : true},
	                        axisLabel : {
	                        	formatter: '{value} 万元'
	                        }
	                    }
	                ],
	                series : []
	            });
	            window.onresize = myChart.resize; // echarts自适应
		        myChart.hideLoading(); 
	        }
	    );
	    
	    function echartLoad(formId) {
	    	myChart.showLoading();
	    	var option = myChart.getOption();
	    	var params = $('#' + formId).serialize();
	    	$.ajax({
	    		url:"<%=basePath%>report/credit/lendReport/getEchartsData.do",
        		data:params,
        		async:false,
        		dataType : "json",
        		success:function(result){
        			if(result != null && '' != result) {
       					myChart.hideLoading();
        				option.legend.data = result.legend;
        				option.xAxis[0].data = result.category;
        				option.series = result.series; 
        				myChart = echarts.init(domMain);
        			    window.onresize = myChart.resize;
        				myChart.setOption(option, true);
        			}
                }  
        	});   	
		}
    </script>
</body>
</html>