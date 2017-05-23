$(function(){
	var num = 0;
	var index = 0;
	var eTypeArr = $("input[name='eType']");
	
	eTypeArr.each(function(idx,obj){
		if(obj.checked) {
			num++;
			if(idx >= index) index = idx;
		}
	});
	
	eTypeArr.each(function(idx,obj){
		$(this).on('click',function(){
			if(obj.checked) {
				if(num > 0 && num <= 2) {
					if(num == 2){
						$(eTypeArr[index]).removeAttr('checked');
					}
					loadChartData();
				}
			}
			index = idx;
			
			num = 0;
			eTypeArr.each(function(idx,ob){
				if(ob.checked) {
					num++;
				}
			});
		})
	});
	
})

// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));
var colors = [ '#5793f3', '#b5e61d' ];

// 加载图表
function loadChartData() {
	myChart.showLoading();
	var dateChange = $("#dateChange").val().trim();
	var confirmTimeMin = $("#confirmTimeMin").val().trim();
	var confirmTimeMax = $("#confirmTimeMax").val().trim();
	var eArr = [];
	$("input[name='eType']:checked").each(function(idx,obj){
		eArr.push(obj.value);
	});
	
	var eType = eArr.join(",");
	
	$.ajax({
		method: 'post',
		url:'/echarts/dataCount',
		data:{'dateChange':dateChange,'confirmTimeMin':confirmTimeMin,'confirmTimeMax':confirmTimeMax,'eType':eType},
		success:function(result) {
			if(result != null) {
//				console.log("legend:"+result.legend.data);
//				console.log("x:"+result.xAxis[0].data);
//				console.log("y0-name:"+result.yAxis[0].name);
//				console.log("y1-name:"+result.yAxis[1].name);
//				console.log("s0-name:"+result.series[0].name);
//				console.log("s0-data:"+result.series[0].data);
//				console.log("s1-name:"+result.series[1].name);
//				console.log("s1-data:"+result.series[1].data);
				var option = {
					color : colors,
					tooltip : {
						trigger : 'axis',
						axisPointer:{
							lineStyle: {
								color: '#CCCCCC'
							}
						},
//						formatter : '{b}</br>{a0}：{c0}%</br>{a1}：{c1}'
					},
					grid : {
						width:'900px',
						left:'center'
					},
					/*toolbox : {
						feature : {
							dataView : {
								show : true,
								readOnly : false,
								textareaBorderColor:'#CCCCCC'
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},*/
					legend : {
						itemGap : 25,
						data : result.legend.data
					},
					xAxis : [ {
						type : 'category',
						axisTick : {
							alignWithLabel : true
						},
						data : result.xAxis[0].data,
						show : true, 
						boundaryGap : true,
                        axisLabel:{
                        	rotate:45, 
                            interval:0 /*,  
                            formatter:function(val){  
                              if(val == null) return "";
                              return val.split("-").join("\n");  
                            }  */
                        },
                        axisLine : {
							lineStyle : {
								color : '#CCCCCC'
							}
						},
						splitArea: {
							interval: 1,
						}
					} ],
					yAxis : [ {
						type : 'value',
						name : result.yAxis[0].name,
						min : 0,
						max : result.yAxis[0].max,
						minInterval: 1,
						position : 'left',
						axisTick : {
							show:false
						},
						axisLine : {
							/*lineStyle : {
								color : colors[0]
							}*/
							show:false
						},
						axisLabel : {
							margin : 10,
							formatter : result.yAxis[0].formatter
						},
						nameLocation : 'end',
						/*nameRotate : -90,
						nameGap : 50*/
					}, {
						type : 'value',
						name : result.yAxis[1].name,
						min : 0,
						max : result.yAxis[1].max,
						minInterval: 1,
						position : 'right',
						/*offset : 80,*/
						axisLine : {
							/*lineStyle : {
								color : colors[1]
							}*/
							show:false
						},
						axisTick : {
							show:false
						},
						axisLabel : {
							margin : 10,
							formatter : result.yAxis[1].formatter
						},
						nameLocation : 'end',
						/*nameRotate : -90,
						nameGap : 50*/
					}],
					series : [ 
				        {
				        	name : result.series[0].name,
				        	type : 'line',
				        	yAxisIndex : 0,
				        	data : result.series[0].data
				        },
				        {
				        	name : result.series[1].name,
				        	type : 'line',
				        	yAxisIndex : 1,
				        	data : result.series[1].data
				        } 
					]
				};
			}
			
			myChart.setOption(option,true);
			myChart.hideLoading();
		}
	});
}

window.onresize=myChart.resize