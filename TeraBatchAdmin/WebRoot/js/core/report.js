/*
		是不是白户的操作
		sign         show 不是白户, hide 是白户
	*/
	function showOrHideObj(sign){
		if('show' == sign){
			showOrHideAllFn('personageBaseMessageDiv', false);
			showOrHideAllFn('messageAbstractDiv', false);
			showOrHideAllFn('loanDiv', false);
			showOrHideAllFn('creditCardDiv', false);
			showOrHideAllFn('semiCreditCardDiv', false);
			showOrHideAllFn('guaranteeMessageDiv', false);
			showOrHideAllFn('publicMessageDetailDiv', false);
// 			showOrHideAllFn('queryRecordDiv', false);
		}else if('hide' == sign){
			showOrHideAllFn('personageBaseMessageDiv', true);
			showOrHideAllFn('messageAbstractDiv', true);
			showOrHideAllFn('loanDiv', true);
			showOrHideAllFn('creditCardDiv', true);
			showOrHideAllFn('semiCreditCardDiv', true);
			showOrHideAllFn('guaranteeMessageDiv', true);
			showOrHideAllFn('publicMessageDetailDiv', true);
// 			showOrHideAllFn('queryRecordDiv', true);
		}
	}
	
	/*
		动态显示隐藏div面板控制方法
	*/
	function showOrHideAllFn(divId, sign){
		var obj=$("#" + divId).closest('.panel');
		obj.find('.easyui-validatebox').validatebox({novalidate:sign});
		obj.find('.easyui-combobox').combobox({novalidate:sign});
		obj.find('.easyui-numberbox').validatebox({novalidate:sign});
		obj.find('.easyui-datebox').datebox({novalidate:sign});
		//obj.find('#state').val("0");
		if(true == sign){
			obj.hide();		
		}else{
			obj.show();
		}
	}

	//添加对象
	function addObj(key){
		var html=$("#"+key+"_textarea").val();		//得到 特约驾驶员 HTML
		var keyIndex=key+"Index";
		var index=$("#"+keyIndex).val();					//得到索引值
		html=html.replace(eval('/贷款'+keyIndex+'/gm'), '贷款' + Number(Number(index) + 1));	//把索引占位符 替换
		html=html.replace(eval('/贷记卡'+keyIndex+'/gm'), '贷记卡' + Number(Number(index) + 1));	//把索引占位符 替换
		html=html.replace(eval('/准贷记卡'+keyIndex+'/gm'), '准贷记卡' + Number(Number(index) + 1));	//把索引占位符 替换
		html=html.replace(eval('/'+keyIndex+'/gm'),index);	//把索引占位符 替换
		var newDriver=$(html);					//转换成 JQuery 对象
		newDriver.appendTo("#"+key);				//添加到 对应地点
		$.parser.parse(newDriver);						//初始化 EasyUI
		var key1 = key.substring(0, key.length - 1);
		key1 = key1 + "_" + index;
		onSelects(key, key1);
		$("#"+keyIndex).val(++index);	//索引递增
	}
	/*
		添加对象带编号（目前是担保信息和查询记录明细加了编号）
		key   		表的Id
		num         去掉的tr数
	*/
	function addObjHaveNo(key){
		var html=$("#"+key+"_textarea").val();		//得到 特约驾驶员 HTML
		var keyIndex=key+"Index";
		var index=$("#"+keyIndex).val();					//得到索引值
		html=html.replace(eval('/'+keyIndex+'/gm'),index);	//把索引占位符 替换
		var newDriver=$(html);					//转换成 JQuery 对象
		newDriver.appendTo("#"+key);				//添加到 对应地点
		$.parser.parse(newDriver);						//初始化 EasyUI
		var key1 = key.substring(0, key.length - 1);
		key1 = key1 + "_" + index;
		onSelects(key, key1);
		//var queryReasonurl = "sys/datadictionary/listjason.do?keyName=reportquerycause";
		$("#rhQueryDetails").find("#rhQueryDetail_" + index).find("#queryReason").combobox("clear");
		$("#rhQueryDetails").find("#rhQueryDetail_" + index).find("#queryReason").combobox({
			//url: queryReasonurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			data:dataDictJson.reportquerycause,
			onChange: function(newValue, oldValue){
	   		}
		});
		$("#"+keyIndex).val(++index);	//索引递增
		addNo(key);
	}
	/*添加编号*/
	function addNo(key){
		var num = 1;
		$('#' + key + ' tr').each(function() {
			if($(this).find("#state").val() != null && $(this).find("#state").val() != '0'){
				$(this).find("#serialNumber").val(num);
				++num;
			}
		});
	}
	/*
		添加逾期、透支记录
		type                 类型 01、02、03
		parentId             最顶级表格Id
		parentInfo           父亲对象名称
		parentIndex          父亲表格索引值
		childInfo            要添加的逾期、透支表Id
		childIndex           要添加的逾期、透支索引
		ulId                 要加到那里去的ulId 
		addObjInfo('01', 'rhTransDetail01s', 'rhTransDetail01', 'rhTransDetail01sIndex', 'rhTransDefault01', 'rhTransDetail01sIndex_rhTransDefault01sIndex', 'rhTransDetail01sIndex_rhTransDefault01s')
		addObjInfo('02', 'rhTransDetail02s', 'rhTransDetail02', 'rhTransDetail02sIndex', 'rhTransDefault02', 'rhTransDetail02sIndex_rhTransDefault02sIndex', 'rhTransDetail02sIndex_rhTransDefault02s')
		addObjInfo('03', 'rhTransDetail03s', 'rhTransDetail03', 'rhTransDetail03sIndex', 'rhTransDefault03', 'rhTransDetail03sIndex_rhTransDefault03sIndex', 'rhTransDetail03sIndex_rhTransDefault03s')
	*/
	function addObjInfo(type, parentId, parentInfo, parentIndex, childInfo, childIndex, ulId){
		var html=$("#info_textarea").val();		//得到 特约驾驶员 HTML
		html=html.replace(eval('/-type-/gm'), type);
		html=html.replace(eval('/-parentId-/gm'), parentId);
		html=html.replace(eval('/-parentInfo-/gm'), parentInfo);
		html=html.replace(eval('/-parentIndex-/gm'), parentIndex);
		html=html.replace(eval('/-childInfo-/gm'), childInfo);
		var childIndex1 = $("#" + parentId).find("#" + parentInfo + "_" + parentIndex).find("#" + childIndex).val();
		html=html.replace(eval('/-childIndex-/gm'), childIndex1);
		var newDriver = $(html);					//转换成 JQuery 对象
		newDriver.appendTo("#" + ulId);
		$.parser.parse(newDriver);	
		$("#" + parentId).find("#" + parentInfo + "_" + parentIndex).find("#" + childIndex).val(++childIndex1);
	}
	/*
		贷款添加特殊交易类型
	*/
	function addSpecial(tableId, parentId, showSign){
		var tr11 = $("#" + tableId).find("#" + parentId).find("#tr11");
		if(showSign == 'show'){
			$("#" + tableId).find("#" + parentId).find("#img2").hide();
			$("#" + tableId).find("#" + parentId).find("#img3").show();
			tr11.show();
		}else{
			$("#" + tableId).find("#" + parentId).find("#img2").show();
			$("#" + tableId).find("#" + parentId).find("#img3").hide();
			tr11.hide();
		}
	}
	//删除元素
	function rmObj(objId){
		var obj = $("#"+objId);
		obj.find("#state").val("0");
		obj.hide();
	}
	//删除元素
	function rmObjInfo(tabelName, objId){
		var obj = $("#" + tabelName).find("#" + objId);
		obj.find("#state").val("0");
		obj.hide();
	}
	
	//切换 是否验证
	function toggleValidate(objId,isValidete){
		var state=!isValidete;
		var obj=$(objId);
		obj.find('.easyui-validatebox').validatebox({novalidate:state});
		obj.find('.easyui-combobox').combobox({novalidate:state});
		obj.find('.easyui-numberbox').validatebox({novalidate:state});
		obj.find('.easyui-datebox').datebox({novalidate:state});
	}
	
	function onSelects(key, key1){
		if(key == "rhTransDetail01s"){
			/*
			var loangranttypeurl = "sys/datadictionary/listjason.do?keyName=loangranttype";
            var loanbusinesstypeurl = "sys/datadictionary/listjason.do?keyName=loanbusinesstype";
            var loanrepaytypeurl = "sys/datadictionary/listjason.do?keyName=loanrepaytype";
            var loantransstateurl = "sys/datadictionary/listjason.do?keyName=loantransstate";
            var loanfivelevelurl = "sys/datadictionary/listjason.do?keyName=loanfivelevel";
            var loanspecialtransurl = "sys/datadictionary/listjason.do?keyName=loanspecialtrans";
            */
			$("#" + key).find("#" + key1).find("#loanType").combobox("clear");
			$("#" + key).find("#" + key1).find("#loanType").combobox({
				//url: loangranttypeurl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.loangranttype,
				onChange: function(newValue, oldValue){
		   		}
			});
			$("#" + key).find("#" + key1).find("#bizType").combobox("clear");
			$("#" + key).find("#" + key1).find("#bizType").combobox({
				//url: loanbusinesstypeurl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.loanbusinesstype,
				onChange: function(newValue, oldValue){
		   		}
			});
			$("#" + key).find("#" + key1).find("#payMethod").combobox("clear");
			$("#" + key).find("#" + key1).find("#payMethod").combobox({
				//url: loanrepaytypeurl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.loanrepaytype,
				onChange: function(newValue, oldValue){
		   		}
			});
			$("#" + key).find("#" + key1).find("#transState").combobox("clear");
			$("#" + key).find("#" + key1).find("#transState").combobox({
				//url: loantransstateurl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.loantransstate,
				onChange: function(newValue, oldValue){
					if(newValue == '03'){//结清
						$("#" + key).find("#" + key1).find("#tr3").hide();
						$("#" + key).find("#" + key1).find("#tr4").hide();
						$("#" + key).find("#" + key1).find("#tr5").hide();
						$("#" + key).find("#" + key1).find("#tr6").hide();
						$("#" + key).find("#" + key1).find("#tr7").hide();
						$("#" + key).find("#" + key1).find("#tr8").show();
						$("#" + key).find("#" + key1).find("#tr9").show();
						$("#" + key).find("#" + key1).find("#tr10").show();
						$("#" + key).find("#" + key1).find("#tr11").show();
						$("#" + key).find("#" + key1).find("#img2").hide();
						$("#" + key).find("#" + key1).find("#img3").show();
					}else{
						$("#" + key).find("#" + key1).find("#tr3").show();
						$("#" + key).find("#" + key1).find("#tr4").show();
						$("#" + key).find("#" + key1).find("#tr5").show();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
						$("#" + key).find("#" + key1).find("#tr8").show();
						$("#" + key).find("#" + key1).find("#tr9").show();
						$("#" + key).find("#" + key1).find("#tr10").hide();
						$("#" + key).find("#" + key1).find("#tr11").hide();
						$("#" + key).find("#" + key1).find("#img2").show();
						$("#" + key).find("#" + key1).find("#img3").hide();
					}
		   		}
			});
			$("#" + key).find("#" + key1).find("#loanClass").combobox("clear");
			$("#" + key).find("#" + key1).find("#loanClass").combobox({
				//url: loanfivelevelurl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.loanfivelevel,
				onChange: function(newValue, oldValue){
		   		}
			});
			$("#" + key).find("#" + key1).find("#specialTransClass").combobox("clear");
			$("#" + key).find("#" + key1).find("#specialTransClass").combobox({
				//url: loanspecialtransurl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.loanspecialtrans,
				onChange: function(newValue, oldValue){
		   		}
			});
		}else if(key == "rhTransDetail02s"){
			/*
			var creditgranttypeurl = "sys/datadictionary/listjason.do?keyName=creditgranttype";
			var credittransstateurl = "sys/datadictionary/listjason.do?keyName=credittransstate";
			*/
			$("#" + key).find("#" + key1).find("#loanType").combobox("clear");
			$("#" + key).find("#" + key1).find("#loanType").combobox({
				//url: creditgranttypeurl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.creditgranttype,
				onChange: function(newValue, oldValue){
		   		}
			});
			$("#" + key).find("#" + key1).find("#transState").combobox("clear");
			$("#" + key).find("#" + key1).find("#transState").combobox({
				//url: credittransstateurl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.credittransstate,
				onChange: function(newValue, oldValue){
					if(newValue == '01'){
						$("#" + key).find("#" + key1).find("#tr3").hide();
						$("#" + key).find("#" + key1).find("#tr4").hide();
						$("#" + key).find("#" + key1).find("#tr5").hide();
						$("#" + key).find("#" + key1).find("#tr6").hide();
						$("#" + key).find("#" + key1).find("#tr7").hide();
						$("#" + key).find("#" + key1).find("#tr8").show();
						$("#" + key).find("#" + key1).find("#tr9").show();
					}else if(newValue == '02'){
						$("#" + key).find("#" + key1).find("#tr3").show();
						$("#" + key).find("#" + key1).find("#tr4").show();
						$("#" + key).find("#" + key1).find("#tr5").show();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
						$("#" + key).find("#" + key1).find("#tr8").show();
						$("#" + key).find("#" + key1).find("#tr9").show();
					}else if(newValue == '03'){
						$("#" + key).find("#" + key1).find("#tr3").hide();
						$("#" + key).find("#" + key1).find("#tr4").hide();
						$("#" + key).find("#" + key1).find("#tr5").hide();
						$("#" + key).find("#" + key1).find("#tr6").hide();
						$("#" + key).find("#" + key1).find("#tr7").hide();
						$("#" + key).find("#" + key1).find("#tr8").hide();
						$("#" + key).find("#" + key1).find("#tr9").hide();
					}
		   		}
			});
		}else if(key == "rhTransDetail03s"){
			/*
			var allowgranttypeurl = "sys/datadictionary/listjason.do?keyName=allowgranttype";
			var allowbusinesstypeurl = "sys/datadictionary/listjason.do?keyName=allowbusinesstype";
			var allowtransstateurl = "sys/datadictionary/listjason.do?keyName=allowtransstate";
			*/
			$("#" + key).find("#" + key1).find("#loanType").combobox("clear");
			$("#" + key).find("#" + key1).find("#loanType").combobox({
				//url: allowgranttypeurl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.allowgranttype,
				onChange: function(newValue, oldValue){
		   		}
			});
			$("#" + key).find("#" + key1).find("#bizType").combobox("clear");
			$("#" + key).find("#" + key1).find("#bizType").combobox({
				//url: allowbusinesstypeurl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.allowbusinesstype,
				onChange: function(newValue, oldValue){
		   		}
			});
			$("#" + key).find("#" + key1).find("#transState").combobox("clear");
			$("#" + key).find("#" + key1).find("#transState").combobox({
				//url: allowtransstateurl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.allowtransstate,
				onChange: function(newValue, oldValue){
					if(newValue == '01'){
						$("#" + key).find("#" + key1).find("#tr3").hide();
						$("#" + key).find("#" + key1).find("#tr4").hide();
						$("#" + key).find("#" + key1).find("#tr5").hide();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
					}else if(newValue == '02'){
						$("#" + key).find("#" + key1).find("#tr3").show();
						$("#" + key).find("#" + key1).find("#tr4").show();
						$("#" + key).find("#" + key1).find("#tr5").show();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
					}else if(newValue == '03'){
						$("#" + key).find("#" + key1).find("#tr3").hide();
						$("#" + key).find("#" + key1).find("#tr4").hide();
						$("#" + key).find("#" + key1).find("#tr5").hide();
						$("#" + key).find("#" + key1).find("#tr6").hide();
						$("#" + key).find("#" + key1).find("#tr7").hide();
					}
		   		}
			});
		}
	}
	/*
		回显时控制显示隐藏
	*/
	function showOrHideFn(key, key1, transState){
		if(key == 'rhTransDetail01s'){
			if(transState == '03'){//结清
				$("#" + key).find("#" + key1).find("#tr3").hide();
				$("#" + key).find("#" + key1).find("#tr4").hide();
				$("#" + key).find("#" + key1).find("#tr5").hide();
				$("#" + key).find("#" + key1).find("#tr6").hide();
				$("#" + key).find("#" + key1).find("#tr7").hide();
				$("#" + key).find("#" + key1).find("#tr8").show();
				$("#" + key).find("#" + key1).find("#tr9").show();
				$("#" + key).find("#" + key1).find("#tr10").show();
				$("#" + key).find("#" + key1).find("#tr11").show();
				$("#" + key).find("#" + key1).find("#img2").hide();
				$("#" + key).find("#" + key1).find("#img3").show();
			}else {
				$("#" + key).find("#" + key1).find("#tr3").show();
				$("#" + key).find("#" + key1).find("#tr4").show();
				$("#" + key).find("#" + key1).find("#tr5").show();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
				$("#" + key).find("#" + key1).find("#tr8").show();
				$("#" + key).find("#" + key1).find("#tr9").show();
				$("#" + key).find("#" + key1).find("#tr10").hide();
				$("#" + key).find("#" + key1).find("#tr11").hide();
				$("#" + key).find("#" + key1).find("#img2").show();
				$("#" + key).find("#" + key1).find("#img3").hide();
			}
		}
		if(key == 'rhTransDetail02s'){
			if(transState == '01'){
				$("#" + key).find("#" + key1).find("#tr3").hide();
				$("#" + key).find("#" + key1).find("#tr4").hide();
				$("#" + key).find("#" + key1).find("#tr5").hide();
				$("#" + key).find("#" + key1).find("#tr6").hide();
				$("#" + key).find("#" + key1).find("#tr7").hide();
				$("#" + key).find("#" + key1).find("#tr8").show();
				$("#" + key).find("#" + key1).find("#tr9").show();
			}else if(transState == '02'){
				$("#" + key).find("#" + key1).find("#tr3").show();
				$("#" + key).find("#" + key1).find("#tr4").show();
				$("#" + key).find("#" + key1).find("#tr5").show();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
				$("#" + key).find("#" + key1).find("#tr8").show();
				$("#" + key).find("#" + key1).find("#tr9").show();
			}else if(transState == '03'){
				$("#" + key).find("#" + key1).find("#tr3").hide();
				$("#" + key).find("#" + key1).find("#tr4").hide();
				$("#" + key).find("#" + key1).find("#tr5").hide();
				$("#" + key).find("#" + key1).find("#tr6").hide();
				$("#" + key).find("#" + key1).find("#tr7").hide();
				$("#" + key).find("#" + key1).find("#tr8").hide();
				$("#" + key).find("#" + key1).find("#tr9").hide();
			}
		}
		if(key == 'rhTransDetail03s'){
			if(transState == '01'){
				$("#" + key).find("#" + key1).find("#tr3").hide();
				$("#" + key).find("#" + key1).find("#tr4").hide();
				$("#" + key).find("#" + key1).find("#tr5").hide();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
			}else if(transState == '02'){
				$("#" + key).find("#" + key1).find("#tr3").show();
				$("#" + key).find("#" + key1).find("#tr4").show();
				$("#" + key).find("#" + key1).find("#tr5").show();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
			}else if(transState == '03'){
				$("#" + key).find("#" + key1).find("#tr3").hide();
				$("#" + key).find("#" + key1).find("#tr4").hide();
				$("#" + key).find("#" + key1).find("#tr5").hide();
				$("#" + key).find("#" + key1).find("#tr6").hide();
				$("#" + key).find("#" + key1).find("#tr7").hide();
			}
		}
	}