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
					<th scope="col">总量</th>
					<th scope="col">通过量</th>
					<th scope="col">拒贷量</th>
					<th scope="col">其他量</th>
					<th scope="col">决策人</th>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.totalAmount}</td>
						<td>${data.passAmount}</td>
						<td>${data.denyAmount}</td>
						<td>${data.totalAmount - data.passAmount - data.denyAmount}</td>
						<td>${data.auditUser}</td>
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
			$('#' + fromId).attr("action","<%=basePath%>report/credit/auditReport/exportExcel.do");
			$('#' + fromId).attr("method","post");
			$('#' + fromId).submit();
		}
	</script>
    
    <!-- echart相关 -->
    <script type="text/javascript">
    var myChart;
    var echarts;
    var domMain = document.getElementById('echartMain');
    var option;
	    require.config({
	        paths: {
	            echarts: '<%=basePath%>js/echarts'
	        }
	    });
	    
	    require(
	        [
	            'echarts',
	            'echarts/chart/pie',
	            'echarts/chart/funnel'
	        ],
	        function (ec) {
	        	echarts = ec;
	        	myChart = ec.init(domMain);
	           
	            option = {
            	 	title : {
            	        text: '',
            	        subtext : '全部',
            	        x:'center'
            	    },
	                tooltip : {
                   		trigger: 'item',
              	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	                },
	                legend: {
	                    orient : 'vertical',
	                    x : 'left',
	                	data:['通过量','拒贷量','其他']
	                },
	                toolbox: {
	                    show : true,
	                    feature : {
	                        //mark : {show: true},
	                        //dataView : {show: true, readOnly: false},
	                        magicType : {
	                        	show: true, 
	                        	type: ['pie', 'funnel'],
		                        option: {
	           	                    funnel: {
	           	                        x: '25%',
	           	                        width: '50%',
	           	                        funnelAlign: 'left',
	           	                        max:5000
	           	                    }
	           	                } 
	                        },
	                        restore : {show: true},
	                        saveAsImage : {show: true}
	                    }
	                },
	               series: []
	            };
	            window.onresize = myChart.resize; // echarts自适应
		        myChart.hideLoading(); 
		        myChart.setOption(option, true);
	        }
	    );
	    
	    function echartLoad(formId) {
	    	myChart.showLoading();
	    	var params = $('#' + formId).serialize();
	    	$.ajax({
        		url:"<%=basePath%>report/credit/auditReport/getEchartsData.do",
        		data:params,
        		async:false,
        		dataType : "json",
        		success:function(result){
        			if(result != null && '' != result) {
       					myChart.hideLoading();
        				if(result.totalAmount != 0) {
            				var text = $('#auditType').combobox('getText');
            				option.title.text = text;
            				if(option.series.length > 0) {
    	        				option.series.length = 0;
            				}
            				option.series.push({name:text,
    	                        	type:'pie',
    	                        	radius : '55%',
    	                            center: ['50%', '60%'],
    	                        	data:[
    	                        	      {value:result.passAmount,name:'通过量'},
    	                        	      {value:result.denyAmount,name:'拒贷量'},
    	                        	      {value:result.otherAmount,name:'其他'}
    	                        	     ]});	
	        				myChart = echarts.init(domMain);
	        			    window.onresize = myChart.resize;
	        				myChart.setOption(option, true);
        				} else {
        					myChart = echarts.init(domMain);
            			    window.onresize = myChart.resize;
            			    myChart.showLoading({
            			        text : '暂无数据',
            			        effect : 'bubble',
            			        textStyle : {
            			            fontSize : 20
            			        }
            			    });
        				}
        			} else {
        				myChart = echarts.init(domMain);
        			    window.onresize = myChart.resize;
        			    myChart.showLoading({
        			        text : '暂无数据',
        			        effect : 'bubble',
        			        textStyle : {
        			            fontSize : 20
        			        }
        			    });
        			}	 
                }  
        	});   	
		}
    </script>
</body>
</html>