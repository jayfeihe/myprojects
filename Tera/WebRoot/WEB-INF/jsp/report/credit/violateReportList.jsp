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
					<th scope="col">应还总额</th>
					<th scope="col">已还金额</th>
					<th scope="col">违约总额</th>
					<th scope="col">违约期数</th>
					<th scope="col">正常期数</th>
					<th scope="col">营业部</th>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>
							<fmt:formatNumber value="${data.totalAmount/10000}" type="currency"/>万元
						</td>
						<td>
							<fmt:formatNumber value="${data.repayAmount/10000}" type="currency"/>万元
						</td>
						<td>
							<fmt:formatNumber value="${data.violateAmount/10000}" type="currency"/>万元	
						</td>
						<td>${data.violatePeriod}</td>
						<td>${data.normalPeriod}</td>
						<td>${data.orgName}</td>
					</tr>
				</c:forEach>
			</table>
			
			<div id="pageStyle">
				${ pm.pageNavigation}
			</div>
		</form>
		<div id="echartMain" style="height:400px;padding:10px;border-top:1px solid #f1f1f1; display: none;"></div>
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
				//echartLoad('queryForm');
				$("#queryBtn").attr('onclick',"echartLoad('queryForm')");
			}
		}
		
		//导出
		function exportExcel(fromId) {
			$('#' + fromId).attr("action","<%=basePath%>report/credit/violateReport/exportExcel.do");
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
            	        subtext:'金额和占比统计',
            	        x:'center'
            	    },
	                tooltip : {
                   		trigger: 'item',
              	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	                },
	                legend: {
	                    x : 'center',
	                    y : 'bottom',
	                	data:['违约金额','已还金额','违约期数','正常期数']
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
	           	                     	funnelAlign: 'center',
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
	    	var orgId = $("#orgId").combotree("getValue");
			if(null == orgId || '' == orgId){
				$.messager.alert('消息', '请选择营业部！');
				return;
			}
	    	myChart.showLoading();
	    	var params = $('#' + formId).serialize();
	    	$.ajax({
        		url:"<%=basePath%>report/credit/violateReport/getEchartsData.do",
        		data:params,
        		async:false,
        		dataType : "json",
        		success:function(result){
        			if(result != null && '' != result) {
       					myChart.hideLoading();
        				if(result.totalAmount != 0) {
            				var text = $('#orgId').combobox('getText');
            				option.title.text = text;
            				if(option.series.length > 0) {
    	        				option.series.length = 0;
            				}
            				
            				option.series.push({
            						name:"金额占比",
    	                        	type:'pie',
    	                        	radius : ['30%', '50%'],
						            center : ['25%', 200],
    	                            itemStyle : {
    	                                normal : {
    	                                    label : {
    	                                        show : false
    	                                    },
    	                                    labelLine : {
    	                                        show : false
    	                                    }
    	                                },
    	                                emphasis : {
    	                                    label : {
    	                                        show : true,
    	                                        position : 'center',
    	                                        textStyle : {
    	                                            fontSize : '20',
    	                                            fontWeight : 'bold'
    	                                        }
    	                                    }
    	                                }
    	                            },
    	                        	data:[
    	                        	      {value:result.violateAmount,name:'违约金额'},
    	                        	      {value:result.repayAmount,name:'已还金额'},
    	                        	      {value:0,name:'违约期数'},
    	                        	      {value:0,name:'正常期数'},
    	                        	     ]});
            				option.series.push({
            					name:"百分占比",
	                        	type:'pie',
	                        	radius : ['30%', '50%'],
					            center : ['75%', 200],
	                            itemStyle : {
	                                normal : {
	                                    label : {
	                                        show : false
	                                    },
	                                    labelLine : {
	                                        show : false
	                                    }
	                                },
	                                emphasis : {
	                                    label : {
	                                        show : true,
	                                        position : 'center',
	                                        textStyle : {
	                                            fontSize : '20',
	                                            fontWeight : 'bold'
	                                        }
	                                    }
	                                }
	                            },
	                        	data:[
	                        	      {value:0,name:'违约金额'},
	                        	      {value:0,name:'已还金额'},
	                        	      {value:result.violatePeriod,name:'违约期数'},
	                        	      {value:result.normalPeriod,name:'正常期数'},
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