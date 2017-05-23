<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<style type="text/css">
	.ul_table{
		padding:0;
		width:100%; /*设置表格宽 */
		margin:0px;
		padding:0px;
	   }
	.li_table_tr{ 
		float:left; 
		list-style:none;
		display:block;
		text-align:center;
		width:48%;
		border-top:1px solid #d3d3d3;
		border-bottom:1px solid #d3d3d3;
		border-right:1px solid #d3d3d3;
	}
	.li_table_td{ 
		float:left; 
		list-style:none;
		display:block;
		text-align:center;
		width:33%; 
		border-left:1px solid #d3d3d3;
	}
</style>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="tab_show_report" style="display: none;" title="信用报告" ></div>

  <div id="reportVerify" class="easyui-accordion" data-options="multiple:false" style="" data-options="fit:true">
        <div title="个人信用报告" style="overflow:auto;padding:10px;"
		data-options="collapsed:false,collapsible:false">
        	<table width="100%">
        		<tr>
        			<td>
        				<input name="rhReport.id" type="hidden" value="${rhReport.id }"/>
						<input name="rhReport.state" type="hidden"  value="${rhReport.state }"/>
						<input name="rhReport.appId" type="hidden" value="${bean.appId}"/>
						查询请求时间：<input name="rhReport.requestTime" value="${rhReport.requestTimeStr }" type="text" class="textbox easyui-datetimebox" data-options="validType:['length[0,19]']" style="width:120px;"/>
					</td>
					<td align="center">
        				<input type="radio" value="0" <c:if test="${rhReport.flag == '0'}">checked="checked"</c:if> name="rhReport.flag" onchange="showOrHideObj('hide')">
						<font color="red" style="font-weight: bolder;">白户</font>
						<input type="radio" <c:if test="${rhReport.flag == '1'}">checked="checked"</c:if> value="1" name="rhReport.flag" onchange="showOrHideObj('show')">
						<font color="green" style="font-weight: bolder;">非白户</font>
        			</td>
        		</tr>
        	</table>
			<table class="datatable">
				<tr>
					<th scope="col">被查询者姓名</th>
					<th scope="col">被查询者证件类型</th>
					<th scope="col">被查询者证件号码</th>
					<th scope="col">查询原因</th>
				</tr>
				<tr>
					<td><input name="rhReport.name" value="${bean.name }" type="text" class="textbox easyui-validatebox" readonly="readonly"  style="width:120px;"/></td>
					<td>
						<input id="idType" name="rhReport.idType" type="text" data-options="validType:['length[0,50]']" 
							class="easyui-combobox"  value="${bean.idType }" editable="false" readonly="readonly" style="width:120px;"/>
					</td>
					<td><input name="rhReport.idNo" value="${bean.idNo }" type="text" class="textbox easyui-validatebox" readonly="readonly" style="width:140px;"/></td>
					<td>
						<input id="queryReason" name="rhReport.queryReason" type="text" data-options="validType:['length[0,50]']" 
							class="easyui-combobox"  value="${rhReport.queryReason }" editable="false" style="width:120px;"/>
					</td>
				</tr>
			</table>
		</div>
		<div title="个人基本信息" style="overflow:auto;padding:10px;" id="personageBaseMessageDiv" >
			<center><font style="font-size: 15px;"><strong>身份信息</strong></font></center>
			<table class="datatable">
				<tr>
					<th scope="col">婚姻状况</th>
					<th scope="col">学历</th>
				</tr>
				<tr>
					<td>
						<input id="marriage" name="rhReport.marriage" type="text" data-options="validType:['length[0,10]']" 
						class="easyui-combobox" value="${rhReport.marriage}" editable="false" style="width:120px;"/>
					</td>
					<td>
						<input id="education" name="rhReport.education" type="text" data-options="validType:['length[0,50]']" 
						class="easyui-combobox" value="${rhReport.education}" editable="false" style="width:120px;"/>
					</td>
				</tr>
				<tr>

				</tr>
			</table>
			<center><font style="font-size: 15px;"><strong>配偶信息</strong></font></center>
			<table class="datatable">
				<tr>
					<th scope="col">姓名</th>
					<th scope="col">证件类型</th>
					<th scope="col">证件号码</th>
				</tr>
				<tr>
					<td>
						<input name="rhReport.mateName" value="${rhReport.mateName }" type="text" class="textbox easyui-validatebox" style="width:120px;"/>
					</td>
					<td>
						<input id="rhReport.mateIdType" name="rhReport.mateIdType" value="${rhReport.mateIdType }" 
						type="text" class="textbox easyui-validatebox" style="width:120px;"/>
					</td>
					<td>
						<input name="rhReport.mateIdNo" value="${rhReport.mateIdNo }" type="text" class="textbox easyui-validatebox" style="width:120px;" data-options="validType:['idcard']"/>
					</td>
				</tr>
			</table>
		</div>
		<div title="信息概要" style="overflow:auto;padding:10px;" id="messageAbstractDiv" >
			<center><font style="font-size: 15px;"><strong>(一)信用提示</strong></font></center>
			<table class="datatable">
				<tr>
					<th scope="col">个人住房贷款笔数</th>
					<th scope="col">个人商用房(含商住两用)贷款笔数</th>
					<th scope="col">其他贷款笔数</th>
					<th scope="col">首笔贷款发放月份</th>
					<th scope="col">贷记卡账户数</th>
				</tr>
				<tr>
					<td>
						<input name="rhSummary.id" type="hidden" value="${rhSummary.id }"/>
						<input name="rhSummary.state" type="hidden"  value="${rhSummary.state }"/>
						<input name="rhSummary.appId" type="hidden" value="${bean.appId}"/>
						<input name="rhSummary.personHouseLoanNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.personHouseLoanNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.personBizHouseLoanNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.personBizHouseLoanNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.otherLoanNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.otherLoanNum}" style="width:120px;"/>
					</td>
					<td><input name="rhSummary.firstLoanDate" value="${rhSummary.firstLoanDateStr }" type="text" class="textbox easyui-datebox" data-options="validType:['length[0,10]']" style="width:120px;"/></td>
					<td>
						<input name="rhSummary.creditNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.creditNum}" style="width:120px;"/>
					</td>
				</tr>
				<tr>
					<th scope="col">首张贷记卡发卡月份</th>
					<th scope="col">准贷记卡账户数</th>
					<th scope="col">首张准贷记卡发卡月份</th>
					<th scope="col">本人声明数目</th>
					<th scope="col">异议标注数目</th>
				</tr>
				<tr>
					<td><input name="rhSummary.firstCreditDate" value="${rhSummary.firstCreditDateStr }" type="text" class="textbox easyui-datebox" data-options="validType:['length[0,10]']" style="width:120px;"/></td>
					<td>
						<input name="rhSummary.semiCreditNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.semiCreditNum}" style="width:120px;"/>
					</td>
					<td><input name="rhSummary.semiCreditDate" value="${rhSummary.semiCreditDateStr }" type="text"class="textbox easyui-datebox" data-options="validType:['length[0,10]']" style="width:120px;"/></td>
					<td>
						<input name="rhSummary.declareNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.declareNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.objectionNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.objectionNum}" style="width:120px;"/>
					</td>
				</tr>
			</table>
			<center><font style="font-size: 15px;"><strong>(二)逾期及违约信息概要</strong></font></center>
			<center><font style="font-size: 13px;"><strong>逾期(透支)信息汇总</strong></font></center>
			<table class="datatable">
				<tr>
					<th scope="col" colspan="4">贷款逾期</th>
				</tr>
				<tr>
					<th scope="col">笔数</th>
					<th scope="col">月份数</th>
					<th scope="col">单月最高逾期总额</th>
					<th scope="col">最长逾期月数</th>
				</tr>
				<tr>
					<td>
						<input name="rhSummary.loanDefaultNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.loanDefaultNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.loanDefaultMonthNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.loanDefaultMonthNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.loanMaxDefaultAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.loanMaxDefaultAmount}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.loanMaxDefaultMonth" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.loanMaxDefaultMonth}" style="width:120px;"/>
					</td>
				</tr>
				<tr>
					<th scope="col" colspan="4">贷记卡逾期</th>
				</tr>
				<tr>
					<th scope="col">账户数</th>
					<th scope="col">月份数</th>
					<th scope="col">单月最高逾期总额</th>
					<th scope="col">最长逾期月数</th>
				</tr>
				<tr>
					<td>
						<input name="rhSummary.creditDefaultNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.creditDefaultNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.creditDefaultMonthNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.creditDefaultMonthNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.creditMaxDefaultAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.creditMaxDefaultAmount}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.creditMaxDefaultMonth" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.creditMaxDefaultMonth}" style="width:120px;"/>
					</td>
				</tr>
				<tr>
					<th scope="col" colspan="4">准贷记卡60天以上透支</th>
				</tr>
				<tr>
					<th scope="col">账户数</th>
					<th scope="col">月份数</th>
					<th scope="col">单月最高透支总额</th>
					<th scope="col">最长透支月数</th>
				</tr>
				<tr>
					<td>
						<input name="rhSummary.semiCreditDefaultNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.semiCreditDefaultNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.semiCreditDefaultMonthNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.semiCreditDefaultMonthNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.semiCreditMaxDefaultAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.semiCreditMaxDefaultAmount}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.semiCreditMaxDefaultMonth" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.semiCreditMaxDefaultMonth}" style="width:120px;"/>
					</td>
				</tr>
			</table>
			<center><font style="font-size: 15px;"><strong>(三)授信及负债信息概要</strong></font></center>
			<center><font style="font-size: 13px;"><strong>未结清贷款信息汇总</strong></font></center>
			<table class="datatable">
				<tr>
					<th scope="col">贷款法人机构数</th>
					<th scope="col">贷款机构数</th>
					<th scope="col">笔数</th>
					<th scope="col">合同总额</th>
					<th scope="col">余额</th>
					<th scope="col">最近6个月平均应还款</th>
				</tr>
				<tr>
					<td>
						<input name="rhSummary.loanLegalNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.loanLegalNum}" style="width:100px;"/>
					</td>
					<td>
						<input name="rhSummary.loanComNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.loanComNum}" style="width:100px;"/>
					</td>
					<td>
						<input name="rhSummary.loanNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.loanNum}" style="width:100px;"/>
					</td>
					<td>
						<input name="rhSummary.loanAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.loanAmount}" style="width:100px;"/>
					</td>
					<td>
						<input name="rhSummary.loanRestAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.loanRestAmount}" style="width:100px;"/>
					</td>
					<td>
						<input name="rhSummary.loanAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.loanAvg6mAmount}" style="width:100px;"/>
					</td>
				</tr>
			</table>
			<br/>
			<center><font style="font-size: 13px;"><strong>未销户贷记卡信息汇总</strong></font></center>
			<table class="datatable">
				<tr>
					<th scope="col">发卡法人机构数</th>
					<th scope="col">发卡机构数</th>
					<th scope="col">账户数</th>
					<th scope="col">授信总额</th>
				</tr>
				<tr>
					<td>
						<input name="rhSummary.creditLegalNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.creditLegalNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.creditComNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.creditComNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.creditAccountNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.creditAccountNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.creditTotalAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.creditTotalAmount}" style="width:120px;"/>
					</td>
				</tr>
				<tr>
					<th scope="col">单家行最高授信额</th>
					<th scope="col">单家行最低授信额</th>
					<th scope="col">已用额度</th>
					<th scope="col">最近6个月平均使用额度</th>
				</tr>
				<tr>
					<td>
						<input name="rhSummary.creditMaxAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.creditMaxAmount}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.creditMinAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.creditMinAmount}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.creditUseAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.creditUseAmount}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.creditAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.creditAvg6mAmount}" style="width:120px;"/>
					</td>
				</tr>
			</table>
			<br/>
			<center><font style="font-size: 13px;"><strong>未销户准贷记卡信息汇总</strong></font></center>
			<table class="datatable">
				<tr>
					<th scope="col">发卡法人机构数</th>
					<th scope="col">发卡机构数</th>
					<th scope="col">账户数</th>
					<th scope="col">授信总额</th>
				</tr>
				<tr>
					<td>
						<input name="rhSummary.semiCreditLegalNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.semiCreditLegalNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.semiCreditComNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.semiCreditComNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.semiCreditAccountNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.semiCreditAccountNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.semiCreditTotalAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.semiCreditTotalAmount}" style="width:120px;"/>
					</td>
				</tr>
				<tr>
					<th scope="col">单家行最高授信额</th>
					<th scope="col">单家行最低授信额</th>
					<th scope="col">透支余额</th>
					<th scope="col">最近6个月平均透支余额</th>
				</tr>
				<tr>
					<td>
						<input name="rhSummary.semiCreditMaxAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.semiCreditMaxAmount}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.semiCreditMinAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.semiCreditMinAmount}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.semiCreditUseAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.semiCreditUseAmount}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.semiCreditAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.semiCreditAvg6mAmount}" style="width:120px;"/>
					</td>
				</tr>
			</table>
			<br/>
			<center><font style="font-size: 13px;"><strong>对外担保信息汇总</strong></font></center>
			<table class="datatable">
				<tr>
					<th scope="col">担保笔数</th>
					<th scope="col">担保金额</th>
					<th scope="col">担保本金余额</th>
				</tr>
				<tr>
					<td>
						<input name="rhSummary.secureNum" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhSummary.secureNum}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.secureAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.secureAmount}" style="width:120px;"/>
					</td>
					<td>
						<input name="rhSummary.secureRestAmount" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${rhSummary.secureRestAmount}" style="width:120px;"/>
					</td>
				</tr>
			</table>
		</div>
		<div title="贷款" style="overflow:auto;padding:10px;" id="loanDiv"
		data-options="tools:[{iconCls:'icon-add', handler:function(){addObj('rhTransDetail01s');}}]">
			<input type="hidden"  id="rhTransDetail01sIndex" 
					value="${rhTransDetail01s==null?0:(fn:length(rhTransDetail01s)>0?fn:length(rhTransDetail01s):0)}"/>
			<table id="rhTransDetail01s">
				<c:forEach items="${rhTransDetail01s}" var="rhTransDetail01" varStatus="status">
					<tr id="rhTransDetail01_${status.index}">
						<td>
							<table class="datatable">
								<tr id="tr1">
									<td colspan="24">
										<strong>贷款${status.index + 1}</strong> &nbsp;
										<img id="img1" src="img/deleteItem2.png"  onclick="rmObjInfo('rhTransDetail01s', 'rhTransDetail01_${status.index}');" />
										<hr width="100%" size=2 color="#D3D3D3" noshade>
									</td>
								</tr>
								<tr id="tr2">
									<td colspan="24">
										<input name="type01RhTransDetailList[${status.index}].id" type="hidden" value="${rhTransDetail01.id}"/>
										<input id="state" name="type01RhTransDetailList[${status.index}].state" type="hidden"  value="${rhTransDetail01.state}"/>
										<input name="type01RhTransDetailList[${status.index}].appId" type="hidden" value="${bean.appId}"/>
										<input name="type01RhTransDetailList[${status.index}].type" type="hidden" value="01"/>
									
										&nbsp; &nbsp; &nbsp;<input id="startDate" name="type01RhTransDetailList[${status.index}].startDate" type="text" value="${rhTransDetail01.startDateStr }"class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
										发放的<input id="amount" name="type01RhTransDetailList[${status.index}].amount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail01.amount }" style="width:100px;"/>元(人民币)
										<input id="loanType" name="type01RhTransDetailList[${status.index}].loanType" type="text" data-options="required:true,validType:['length[0,50]']" 
										class="easyui-combobox"  value="${rhTransDetail01.loanType}" editable="false" style="width:100px;"/>,
										<input id="bizType" name="type01RhTransDetailList[${status.index}].bizType" type="text" data-options="required:true,validType:['length[0,50]']" 
										class="easyui-combobox"  value="${rhTransDetail01.bizType}" editable="false" style="width:100px;"/>
										<input id="period" name="type01RhTransDetailList[${status.index}].period" type="text" data-options="required:true,validType:['length[0,50]']" 
											class="textbox easyui-validatebox" value="${rhTransDetail01.period}" style="width:100px;"/>期,
										<input id="payMethod" name="type01RhTransDetailList[${status.index}].payMethod" type="text" data-options="required:true,validType:['length[0,50]']" 
										class="easyui-combobox"  value="${rhTransDetail01.payMethod}" editable="false" style="width:100px;"/>,
										<input id="endDate" name="type01RhTransDetailList[${status.index}].endDate" type="text" value="${rhTransDetail01.endDateStr }" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>到期。
										截至/于<input id="toDate" name="type01RhTransDetailList[${status.index}].toDate" type="text" value="${rhTransDetail01.toDateStr }" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
										<input id="transState" name="type01RhTransDetailList[${status.index}].transState" type="text" data-options="required:true,validType:['length[0,50]']" 
										class="easyui-combobox"  value="${rhTransDetail01.transState}" editable="false" style="width:100px;"/>,
									</td>
								</tr>
								<tr id="trTwoYearsCount">
									<td colspan="8">
										近两年逾期次数:<input id="twoYearsOverdue" name="type01RhTransDetailList[${status.index}].twoYearsOverdue" type="text" data-options="validType:['length[0,2]']" 
											class="textbox easyui-numberbox" value="${rhTransDetail01.twoYearsOverdue}" style="width:100px;"/>
									</td>
									<td colspan="8">
										近两年逾期最高程度:<input id="twoYearsDegree" name="type01RhTransDetailList[${status.index}].twoYearsDegree" type="text" data-options="validType:['length[0,2]']" 
											class="textbox easyui-numberbox" value="${rhTransDetail01.twoYearsDegree}" style="width:100px;"/>
									</td>
									<td colspan="8">
										近两年逾期最高金额:<input id="twoYearsLimit" name="type01RhTransDetailList[${status.index}].twoYearsLimit" type="text" data-options="min:0,precision:2,validType:['length[0,11]']" 
											class="textbox easyui-numberbox" value="${rhTransDetail01.twoYearsLimit}" style="width:100px;"/>
									</td>
								</tr>
								<tr id="tr3">
									<th scope="col" colspan="8">五级分类</th>
									<th scope="col" colspan="8">本金金额</th>
									<th scope="col" colspan="8">本月应还款</th>
								</tr>
								<tr id="tr4">
									<td colspan="8" id="tdLoanClass">
										<input id="loanClass" name="type01RhTransDetailList[${status.index}].loanClass" type="text" data-options="validType:['length[0,50]']" 
										class="easyui-combobox"  value="${rhTransDetail01.loanClass}" editable="false" style="width:100px;"/>
									</td>
									<td colspan="8">
										<input id="loanRestAmount" name="type01RhTransDetailList[${status.index}].loanRestAmount" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail01.loanRestAmount}" style="width:100px;"/>
									</td>
									<td colspan="8">
										<input id="loanPayAmount" name="type01RhTransDetailList[${status.index}].loanPayAmount" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail01.loanPayAmount}" style="width:100px;"/>
									</td>
								</tr>
								<tr id="tr5">
									<th scope="col" colspan="8">本月实还款</th>
									<th scope="col" colspan="8">当前逾期期数</th>
									<th scope="col" colspan="8">当前逾期金额</th>
								</tr>
								<tr id="tr6">
									<td colspan="8">
										<input id="loanPayReceived" name="type01RhTransDetailList[${status.index}].loanPayReceived" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail01.loanPayReceived}" style="width:100px;"/>
									</td>
									<td colspan="8">
										<input id="loanDefaultNum" name="type01RhTransDetailList[${status.index}].loanDefaultNum" type="text" editable="false"  data-options="min:0" 
											class="textbox easyui-numberbox" value="${rhTransDetail01.loanDefaultNum}" style="width:100px;"/>
									</td>
									<td colspan="8">
										<input id="loanDefaultAount" name="type01RhTransDetailList[${status.index}].loanDefaultAount" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail01.loanDefaultAount}" style="width:100px;"/>
									</td>
								</tr>
								<tr id="tr7">
									<td colspan="24">
										<table class="datatable">
											<tr>
												<td colspan="24">
													<strong>还款记录</strong> &nbsp;
												</td>
											</tr>
											<tr>
												<td>
													<input id="n1" name="type01RhTransDetailList[${status.index}].n1" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n1}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="1" />
												</td>
												<td>
													<input id="n2" name="type01RhTransDetailList[${status.index}].n2" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n2}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="2" />
												</td>
												<td>
													<input id="n3" name="type01RhTransDetailList[${status.index}].n3" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n3}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="3" />
												</td>
												<td>
													<input id="n4" name="type01RhTransDetailList[${status.index}].n4" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n4}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="4" />
												</td>
												<td>
													<input id="n5" name="type01RhTransDetailList[${status.index}].n5" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n5}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="5" />
												</td>
												<td>
													<input id="n6" name="type01RhTransDetailList[${status.index}].n6" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n6}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="6" />
												</td>
												<td>
													<input id="n7" name="type01RhTransDetailList[${status.index}].n7" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n7}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="7" />
												</td>
												<td>
													<input id="n8" name="type01RhTransDetailList[${status.index}].n8" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n8}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="8" />
												</td>
												<td>
													<input id="n9" name="type01RhTransDetailList[${status.index}].n9" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n9}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="9" />
												</td>
												<td>
													<input id="n10" name="type01RhTransDetailList[${status.index}].n10" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n10}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="10" />
												</td>
												<td>
													<input id="n11" name="type01RhTransDetailList[${status.index}].n11" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n11}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="11" />
												</td>
												<td>
													<input id="n12" name="type01RhTransDetailList[${status.index}].n12" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n12}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="12" />
												</td>
												<td>
													<input id="n13" name="type01RhTransDetailList[${status.index}].n13" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n13}" style="width: 20px;"
														 oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="13" />
												</td>
												<td>
													<input id="n14" name="type01RhTransDetailList[${status.index}].n14" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n14}" style="width: 20px;"
														 oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="14" />
												</td>
												<td>
													<input id="n15" name="type01RhTransDetailList[${status.index}].n15" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n15}" style="width: 20px;"
														 oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="15" />
												</td>
												<td>
													<input id="n16" name="type01RhTransDetailList[${status.index}].n16" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n16}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="16" />
												</td>
												<td>
													<input id="n17" name="type01RhTransDetailList[${status.index}].n17" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n17}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="17" />
												</td>
												<td>
													<input id="n18" name="type01RhTransDetailList[${status.index}].n18" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n18}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="18" />
												</td>
												<td>
													<input id="n19" name="type01RhTransDetailList[${status.index}].n19" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n19}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="19" />
												</td>
												<td>
													<input id="n20" name="type01RhTransDetailList[${status.index}].n20" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n20}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="20" />
												</td>
												<td>
													<input id="n21" name="type01RhTransDetailList[${status.index}].n21" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n21}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="21" />
												</td>
												<td>
													<input id="n22" name="type01RhTransDetailList[${status.index}].n22" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n22}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)"  tabindex="22"/>
												</td>
												<td>
													<input id="n23" name="type01RhTransDetailList[${status.index}].n23" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n23}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="23" />
												</td>
												<td>
													<input id="n24" name="type01RhTransDetailList[${status.index}].n24" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.n24}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_${status.index}',this)" tabindex="24" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr id="tr10">
									<td colspan="24">
										<strong>特殊交易类型</strong> &nbsp; &nbsp; <img id="img2" src="img/addItem.gif"  onclick="addSpecial('rhTransDetail01s', 'rhTransDetail01_${status.index}', 'show');" />
										<img id="img3" src="img/deleteItem2.png"  onclick="addSpecial('rhTransDetail01s', 'rhTransDetail01_${status.index}', 'hide');" />
									</td>
								</tr>
								<tr id="tr11">
									<td colspan="24">
										<table class="datatable">
											<tr>
												<th scope="col" colspan="3">特殊交易类型</th>
												<th scope="col" colspan="3">发生日期</th>
												<th scope="col" colspan="3">变更月数</th>
												<th scope="col" colspan="3">发生金额</th>
												<th scope="col" colspan="12">明细记录</th>
											</tr>
											<tr>
												<td scope="col" colspan="3">
													<input id="specialTransClass" name="type01RhTransDetailList[${status.index}].specialTransClass" type="text" data-options="validType:['length[0,50]']" 
														class="easyui-combobox"  value="${rhTransDetail01.specialTransClass}" editable="false" style="width:100px;"/>
												</td>
												<td scope="col" colspan="3">
													<input id="transDate" name="type01RhTransDetailList[${status.index}].transDate" type="text" value="${rhTransDetail01.transDate}" class="textbox easyui-datebox" data-options="validType:['length[0,10]']" style="width:90px;"/>
												</td>
												<td scope="col" colspan="3">
													<input id="changeMonth" name="type01RhTransDetailList[${status.index}].changeMonth" type="text" editable="false"  data-options="min:0" 
														class="textbox easyui-numberbox" value="${rhTransDetail01.changeMonth}" style="width:100px;"/>
												</td>
												<td scope="col" colspan="3">
													<input id="transAmount" name="type01RhTransDetailList[${status.index}].transAmount" type="text" editable="false"  data-options="min:0,precision:2" 
														class="textbox easyui-numberbox" value="${rhTransDetail01.transAmount}" style="width:100px;"/>
												</td>
												<td scope="col" colspan="12">
													<input id="transDetail" name="type01RhTransDetailList[${status.index}].transDetail" type="text" data-options="validType:['length[0,50]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail01.transDetail}" style="width:100px;"/>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
				
					<textarea id="rhTransDetail01s_textarea" disabled="disabled" style="display:none">
						<tr id="rhTransDetail01_rhTransDetail01sIndex">
							<td colspan="25">
								<table class="datatable">
									<tr id="tr1">
										<td colspan="24">
											<strong>贷款rhTransDetail01sIndex</strong> &nbsp;
											<img id="img1" src="img/deleteItem2.png"  onclick="rmObjInfo('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex');" />
											<hr width="100%" size=2 color="#D3D3D3" noshade>
										</td>
									</tr>
									<tr id="tr2">
										<td colspan="23">
											<input name="type01RhTransDetailList[rhTransDetail01sIndex].id" type="hidden" value="0"/>
											<input id="state" name="type01RhTransDetailList[rhTransDetail01sIndex].state" type="hidden"  value="1"/>
											<input name="type01RhTransDetailList[rhTransDetail01sIndex].appId" type="hidden" value="${bean.appId}"/>
											<input name="type01RhTransDetailList[rhTransDetail01sIndex].type" type="hidden" value="01"/>
										
											&nbsp; &nbsp; &nbsp;<input id="startDate" name="type01RhTransDetailList[rhTransDetail01sIndex].startDate" type="text" value="" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
											发放的<input id="amount" name="type01RhTransDetailList[rhTransDetail01sIndex].amount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>元(人民币)
											<input id="loanType" name="type01RhTransDetailList[rhTransDetail01sIndex].loanType" type="text" data-options="required:true,validType:['length[0,50]']" 
											class="easyui-combobox"  value="" editable="false" style="width:100px;"/>,
											<input id="bizType" name="type01RhTransDetailList[rhTransDetail01sIndex].bizType" type="text" data-options="required:true,validType:['length[0,50]']" 
											class="easyui-combobox"  value="" editable="false" style="width:100px;"/>
											<input id="period" name="type01RhTransDetailList[rhTransDetail01sIndex].period" type="text" data-options="required:true,validType:['length[0,50]']" 
												class="textbox easyui-validatebox" value="" style="width:100px;"/>期,
											<input id="payMethod" name="type01RhTransDetailList[rhTransDetail01sIndex].payMethod" type="text" data-options="required:true,validType:['length[0,50]']" 
											class="easyui-combobox"  value="" editable="false" style="width:100px;"/>,
											<input id="endDate" name="type01RhTransDetailList[rhTransDetail01sIndex].endDate" type="text" value="" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>到期。
											截至/于<input id="toDate" name="type01RhTransDetailList[rhTransDetail01sIndex].toDate" type="text" value="" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
											<input id="transState" name="type01RhTransDetailList[rhTransDetail01sIndex].transState" type="text" data-options="required:true,validType:['length[0,50]']" 
											class="easyui-combobox"  value="" editable="false" style="width:100px;"/>,
										</td>
									</tr>
									<tr id="trTwoYearsCount">
										<td colspan="8">
											近两年逾期次数：<input id="twoYearsOverdue" name="type01RhTransDetailList[rhTransDetail01sIndex].twoYearsOverdue" type="text" data-options="validType:['length[0,2]']" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
										<td colspan="8">
											近两年逾期最高程度:<input id="twoYearsDegree" name="type01RhTransDetailList[rhTransDetail01sIndex].twoYearsDegree" type="text" data-options="validType:['length[0,2]']" 
											class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
										<td colspan="8">
											近两年逾期最高金额:<input id="twoYearsLimit" name="type01RhTransDetailList[rhTransDetail01sIndex].twoYearsLimit" type="text" data-options="min:0,precision:2,validType:['length[0,11]']" 
											class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
									</tr>
									<tr id="tr3">
										<th scope="col" colspan="8">五级分类</th>
										<th scope="col" colspan="8">本金金额</th>
										<th scope="col" colspan="8">本月应还款</th>
									</tr>
									<tr id="tr4">
										<td colspan="8" id="tdLoanClass">
											<input id="loanClass" name="type01RhTransDetailList[rhTransDetail01sIndex].loanClass" type="text" data-options="validType:['length[0,50]']" 
											class="easyui-combobox"  value="" editable="false" style="width:100px;"/>
										</td>
										<td colspan="8">
											<input id="loanRestAmount" name="type01RhTransDetailList[rhTransDetail01sIndex].loanRestAmount" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
										<td colspan="8">
											<input id="loanPayAmount" name="type01RhTransDetailList[rhTransDetail01sIndex].loanPayAmount" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
									</tr>
									<tr id="tr5">
										<th scope="col" colspan="8">本月实还款</th>
										<th scope="col" colspan="8">当前逾期期数</th>
										<th scope="col" colspan="8">当前逾期金额</th>
									</tr>
									<tr id="tr6">
										<td colspan="8">
											<input id="loanPayReceived" name="type01RhTransDetailList[rhTransDetail01sIndex].loanPayReceived" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
										<td colspan="8">
											<input id="loanDefaultNum" name="type01RhTransDetailList[rhTransDetail01sIndex].loanDefaultNum" type="text" editable="false"  data-options="min:0" 
												class="textbox easyui-numberbox" value="0" style="width:100px;"/>
										</td>
										<td colspan="8">
											<input id="loanDefaultAount" name="type01RhTransDetailList[rhTransDetail01sIndex].loanDefaultAount" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="0" style="width:100px;"/>
										</td>
									</tr>
									<tr id="tr7">
										<td colspan="24">
											<table class="datatable">
												<tr>
													<td colspan="24">
														<strong>还款记录</strong> &nbsp;
													</td>
												</tr>
												<tr>
													<td>
														<input id="n1" name="type01RhTransDetailList[rhTransDetail01sIndex].n1" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="1" />
													</td>
													<td>
														<input id="n2" name="type01RhTransDetailList[rhTransDetail01sIndex].n2" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="2" />
													</td>
													<td>
														<input id="n3" name="type01RhTransDetailList[rhTransDetail01sIndex].n3" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="3" />
													</td>
													<td>
														<input id="n4" name="type01RhTransDetailList[rhTransDetail01sIndex].n4" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="4" />
													</td>
													<td>
														<input id="n5" name="type01RhTransDetailList[rhTransDetail01sIndex].n5" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="5" />
													</td>
													<td>
														<input id="n6" name="type01RhTransDetailList[rhTransDetail01sIndex].n6" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="6" />
													</td>
													<td>
														<input id="n7" name="type01RhTransDetailList[rhTransDetail01sIndex].n7" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="7" />
													</td>
													<td>
														<input id="n8" name="type01RhTransDetailList[rhTransDetail01sIndex].n8" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="8" />
													</td>
													<td>
														<input id="n9" name="type01RhTransDetailList[rhTransDetail01sIndex].n9" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="9" />
													</td>
													<td>
														<input id="n10" name="type01RhTransDetailList[rhTransDetail01sIndex].n10" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="10" />
													</td>
													<td>
														<input id="n11" name="type01RhTransDetailList[rhTransDetail01sIndex].n11" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="11" />
													</td>
													<td>
														<input id="n12" name="type01RhTransDetailList[rhTransDetail01sIndex].n12" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="12" />
													</td>
													<td>
														<input id="n13" name="type01RhTransDetailList[rhTransDetail01sIndex].n13" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="13" />
													</td>
													<td>
														<input id="n14" name="type01RhTransDetailList[rhTransDetail01sIndex].n14" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="14" />
													</td>
													<td>
														<input id="n15" name="type01RhTransDetailList[rhTransDetail01sIndex].n15" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="15" />
													</td>
													<td>
														<input id="n16" name="type01RhTransDetailList[rhTransDetail01sIndex].n16" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="16" />
													</td>
													<td>
														<input id="n17" name="type01RhTransDetailList[rhTransDetail01sIndex].n17" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="17" />
													</td>
													<td>
														<input id="n18" name="type01RhTransDetailList[rhTransDetail01sIndex].n18" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="18" />
													</td>
													<td>
														<input id="n19" name="type01RhTransDetailList[rhTransDetail01sIndex].n19" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="19" />
													</td>
													<td>
														<input id="n20" name="type01RhTransDetailList[rhTransDetail01sIndex].n20" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="20" />
													</td>
													<td>
														<input id="n21" name="type01RhTransDetailList[rhTransDetail01sIndex].n21" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="21" />
													</td>
													<td>
														<input id="n22" name="type01RhTransDetailList[rhTransDetail01sIndex].n22" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="22" />
													</td>
													<td>
														<input id="n23" name="type01RhTransDetailList[rhTransDetail01sIndex].n23" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="23" />
													</td>
													<td>
														<input id="n24" name="type01RhTransDetailList[rhTransDetail01sIndex].n24" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex',this)" tabindex="24" />
													</td>
												</tr>
											</table>
										</td>
								</tr>
								<tr id="tr10">
									<td colspan="24">
										<strong>特殊交易类型</strong> &nbsp; &nbsp; <img id="img2" src="img/addItem.gif"  onclick="addSpecial('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex', 'show');" />
										<img id="img3" src="img/deleteItem2.png"  onclick="addSpecial('rhTransDetail01s', 'rhTransDetail01_rhTransDetail01sIndex', 'hide');" />
									</td>
								</tr>
								<tr id="tr11">
									<td colspan="24">
										<table class="datatable">
											<tr>
												<th scope="col" colspan="3">特殊交易类型</th>
												<th scope="col" colspan="3">发生日期</th>
												<th scope="col" colspan="3">变更月数</th>
												<th scope="col" colspan="3">发生金额</th>
												<th scope="col" colspan="12">明细记录</th>
											</tr>
											<tr>
												<td scope="col" colspan="3">
													<input id="specialTransClass" name="type01RhTransDetailList[rhTransDetail01sIndex].specialTransClass" type="text" data-options="validType:['length[0,50]']" 
														class="easyui-combobox"  value="" editable="false" style="width:100px;"/>
												</td>
												<td scope="col" colspan="3">
													<input id="transDate" name="type01RhTransDetailList[rhTransDetail01sIndex].transDate" type="text" value="" class="textbox easyui-datebox" data-options="validType:['length[0,10]']" style="width:90px;"/>
												</td>
												<td scope="col" colspan="3">
													<input id="changeMonth" name="type01RhTransDetailList[rhTransDetail01sIndex].changeMonth" type="text" editable="false"  data-options="min:0" 
														class="textbox easyui-numberbox" value="" style="width:100px;"/>
												</td>
												<td scope="col" colspan="3">
													<input id="transAmount" name="type01RhTransDetailList[rhTransDetail01sIndex].transAmount" type="text" editable="false"  data-options="min:0,precision:2" 
														class="textbox easyui-numberbox" value="" style="width:100px;"/>
												</td>
												<td scope="col" colspan="12">
													<input id="transDetail" name="type01RhTransDetailList[rhTransDetail01sIndex].transDetail" type="text" data-options="validType:['length[0,50]']" 
														class="textbox easyui-validatebox" value="" style="width:100px;"/>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</textarea>
			</table>
		</div>
		<div title="贷记卡" style="overflow:auto;padding:10px;" id="creditCardDiv"
		data-options="tools:[{iconCls:'icon-add', handler:function(){addObj('rhTransDetail02s');}}]">
			<input type="hidden"  id="rhTransDetail02sIndex" 
					value="${rhTransDetail02s==null?0:(fn:length(rhTransDetail02s)>0?fn:length(rhTransDetail02s):0)}"/>
			<table id="rhTransDetail02s">
				<c:forEach items="${rhTransDetail02s}" var="rhTransDetail02" varStatus="status">
					<tr id="rhTransDetail02_${status.index}">
						<td>
							<table class="datatable">
								<tr id="tr1">
									<td colspan="24">
										<strong>贷记卡${status.index + 1}</strong> &nbsp;
										<img id="img1" src="img/deleteItem2.png"  onclick="rmObjInfo('rhTransDetail02s', 'rhTransDetail02_${status.index}');" />
										<hr width="100%" size=2 color="#D3D3D3" noshade>
									</td>
								</tr>
								<tr id="tr2">
									<td id="tdCreditCardHeader" colspan="24">
										<input name="type02RhTransDetailList[${status.index}].id" type="hidden" value="${rhTransDetail02.id }"/>
										<input id="state" name="type02RhTransDetailList[${status.index}].state" type="hidden"  value="${rhTransDetail02.state }"/>
										<input name="type02RhTransDetailList[${status.index}].appId" type="hidden" value="${bean.appId}"/>
										<input name="type02RhTransDetailList[${status.index}].type" type="hidden" value="02"/>
									
										&nbsp; &nbsp; &nbsp;<input id="startDate" name="type02RhTransDetailList[${status.index}].startDate" type="text" value="${rhTransDetail02.startDateStr }" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
										发放的<input id="loanType" name="type02RhTransDetailList[${status.index}].loanType" type="text" data-options="required:true,validType:['length[0,50]']" 
										class="easyui-combobox"  value="${rhTransDetail02.loanType}" editable="false" style="width:100px;"/>,
										<input id="amount" name="type02RhTransDetailList[${status.index}].amount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail02.amount}" style="width:100px;"/>元,信用/免担保。
										截至/于<input id="toDate" name="type02RhTransDetailList[${status.index}].toDate" type="text" value="${rhTransDetail02.toDateStr }" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
										<input id="transState" name="type02RhTransDetailList[${status.index}].transState" type="text" data-options="required:true,validType:['length[0,50]']" 
										class="easyui-combobox"  value="${rhTransDetail02.transState}" editable="false" style="width:100px;"/>,
									</td>
								</tr>
								<tr id="trTwoYearsCount">
									<td colspan="8">
										近两年逾期次数:<input id="twoYearsOverdue" name="type02RhTransDetailList[${status.index}].twoYearsOverdue" type="text" data-options="validType:['length[0,2]']" 
											class="textbox easyui-numberbox" value="${rhTransDetail02.twoYearsOverdue}" style="width:100px;"/>
									</td>
									<td colspan="8">
										近两年逾期最高程度:<input id="twoYearsDegree" name="type02RhTransDetailList[${status.index}].twoYearsDegree" type="text" data-options="validType:['length[0,2]']" 
											class="textbox easyui-numberbox" value="${rhTransDetail02.twoYearsDegree}" style="width:100px;"/>
									</td>
									<td colspan="8">
										近两年逾期最高金额:<input id="twoYearsLimit" name="type02RhTransDetailList[${status.index}].twoYearsLimit" type="text" data-options="min:0,precision:2,validType:['length[0,11]']" 
											class="textbox easyui-numberbox" value="${rhTransDetail02.twoYearsLimit}" style="width:100px;"/>
									</td>
								</tr>
								<tr id="tr3">
									<th scope="col" colspan="8">已用额度</th>
									<th scope="col" colspan="8">最近6个月平均使用额度</th>
									<th scope="col" colspan="8">本月应还款</th>
								</tr>
								<tr id="tr4">
									<td colspan="8">
										<input id="creditUseAmount" name="type02RhTransDetailList[${status.index}].creditUseAmount" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail02.creditUseAmount}" style="width:100px;"/>
									</td>
									<td colspan="8">
										<input id="creditAvg6mAmount" name="type02RhTransDetailList[${status.index}].creditAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail02.creditAvg6mAmount}" style="width:100px;"/>
									</td>
									<td colspan="8">
										<input id="creditPayAmount" name="type02RhTransDetailList[${status.index}].creditPayAmount" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail02.creditPayAmount}" style="width:100px;"/>
									</td>
								</tr>
								<tr id="tr5">
									<th scope="col" colspan="8">本月实还款</th>
									<th scope="col" colspan="8">当前逾期期数</th>
									<th scope="col" colspan="8">当前逾期金额</th>
								</tr>
								<tr id="tr6">
									<td colspan="8">
										<input id="creditPayReceived" name="type02RhTransDetailList[${status.index}].creditPayReceived" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail02.creditPayReceived}" style="width:100px;"/>
									</td>
									<td colspan="8">
										<input id="creditDefaultNum" name="type02RhTransDetailList[${status.index}].creditDefaultNum" type="text" editable="false"  data-options="min:0" 
											class="textbox easyui-numberbox" value="${rhTransDetail02.creditDefaultNum}" style="width:100px;"/>
									</td>
									<td colspan="8">
										<input id="creditDefaultAount" name="type02RhTransDetailList[${status.index}].creditDefaultAount" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail02.creditDefaultAount}" style="width:100px;"/>
									</td>
								</tr>
								<tr id="tr7">
									<td colspan="24">
										<table class="datatable">
											<tr>
												<td colspan="24">
													<strong>还款记录</strong> &nbsp;
												</td>
											</tr>
											<tr>
												<td>
													<input id="n1" name="type02RhTransDetailList[${status.index}].n1" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n1}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="1" />
												</td>
												<td>
													<input id="n2" name="type02RhTransDetailList[${status.index}].n2" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n2}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="2" />
												</td>
												<td>
													<input id="n3" name="type02RhTransDetailList[${status.index}].n3" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n3}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="3" />
												</td>
												<td>
													<input id="n4" name="type02RhTransDetailList[${status.index}].n4" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n4}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="4" />
												</td>
												<td>
													<input id="n5" name="type02RhTransDetailList[${status.index}].n5" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n5}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="5" />
												</td>
												<td>
													<input id="n6" name="type02RhTransDetailList[${status.index}].n6" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n6}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="6" />
												</td>
												<td>
													<input id="n7" name="type02RhTransDetailList[${status.index}].n7" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n7}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="7" />
												</td>
												<td>
													<input id="n8" name="type02RhTransDetailList[${status.index}].n8" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n8}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="8" />
												</td>
												<td>
													<input id="n9" name="type02RhTransDetailList[${status.index}].n9" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n9}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="9" />
												</td>
												<td>
													<input id="n10" name="type02RhTransDetailList[${status.index}].n10" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n10}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="10" />
												</td>
												<td>
													<input id="n11" name="type02RhTransDetailList[${status.index}].n11" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n11}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="11" />
												</td>
												<td>
													<input id="n12" name="type02RhTransDetailList[${status.index}].n12" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n12}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="12" />
												</td>
												<td>
													<input id="n13" name="type02RhTransDetailList[${status.index}].n13" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n13}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="13" >
												</td>
												<td>
													<input id="n14" name="type02RhTransDetailList[${status.index}].n14" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n14}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="14" />
												</td>
												<td>
													<input id="n15" name="type02RhTransDetailList[${status.index}].n15" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n15}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="15" />
												</td>
												<td>
													<input id="n16" name="type02RhTransDetailList[${status.index}].n16" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n16}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="16" />
												</td>
												<td>
													<input id="n17" name="type02RhTransDetailList[${status.index}].n17" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n17}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="17" />
												</td>
												<td>
													<input id="n18" name="type02RhTransDetailList[${status.index}].n18" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n18}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="18" />
												</td>
												<td>
													<input id="n19" name="type02RhTransDetailList[${status.index}].n19" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n19}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="19" />
												</td>
												<td>
													<input id="n20" name="type02RhTransDetailList[${status.index}].n20" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n20}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="20" />
												</td>
												<td>
													<input id="n21" name="type02RhTransDetailList[${status.index}].n21" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n21}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="21" />
												</td>
												<td>
													<input id="n22" name="type02RhTransDetailList[${status.index}].n22" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n22}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="22" />
												</td>
												<td>
													<input id="n23" name="type02RhTransDetailList[${status.index}].n23" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n23}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="23" />
												</td>
												<td>
													<input id="n24" name="type02RhTransDetailList[${status.index}].n24" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail02.n24}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_${status.index}',this)" tabindex="24" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
					<textarea id="rhTransDetail02s_textarea" disabled="disabled" style="display:none">
						<tr id="rhTransDetail02_rhTransDetail02sIndex">
							<td colspan="25">
								<table class="datatable">
									<tr id="tr1">
										<td colspan="24">
											<strong>贷记卡rhTransDetail02sIndex</strong> &nbsp;
											<img id="img1" src="img/deleteItem2.png"  onclick="rmObjInfo('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex');" />
											<hr width="100%" size=2 color="#D3D3D3" noshade>
										</td>
									</tr>
									<tr id="tr2">
										<td id="tdCreditCardHeader" colspan="24">
											<input name="type02RhTransDetailList[rhTransDetail02sIndex].id" type="hidden" value="0"/>
											<input id="state" name="type02RhTransDetailList[rhTransDetail02sIndex].state" type="hidden"  value="1"/>
											<input name="type02RhTransDetailList[rhTransDetail02sIndex].appId" type="hidden" value="${bean.appId}"/>
											<input name="type02RhTransDetailList[rhTransDetail02sIndex].type" type="hidden" value="02"/>
										
											&nbsp; &nbsp; &nbsp;<input id="startDate" name="type02RhTransDetailList[rhTransDetail02sIndex].startDate" type="text" value="" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
											发放的<input id="loanType" name="type02RhTransDetailList[rhTransDetail02sIndex].loanType" type="text" data-options="required:true,validType:['length[0,50]']" 
											class="easyui-combobox"  value="" editable="false" style="width:100px;"/>,
											授信额度<input id="amount" name="type02RhTransDetailList[rhTransDetail02sIndex].amount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>元,信用/免担保。
											截至/于<input id="toDate" name="type02RhTransDetailList[rhTransDetail02sIndex].toDate" type="text" value="" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
											<input id="transState" name="type02RhTransDetailList[rhTransDetail02sIndex].transState" type="text" data-options="required:true,validType:['length[0,50]']" 
											class="easyui-combobox"  value="" editable="false" style="width:100px;"/>,
										</td>
									</tr>
									<tr id="trTwoYearsCount">
										<td colspan="8">
											近两年逾期次数:<input id="twoYearsOverdue" name="type02RhTransDetailList[rhTransDetail02sIndex].twoYearsOverdue" type="text" data-options="validType:['length[0,2]']" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
										<td colspan="8">
											近两年逾期最高程度:<input id="twoYearsDegree" name="type02RhTransDetailList[rhTransDetail02sIndex].twoYearsDegree" type="text" data-options="validType:['length[0,2]']" 
											class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
										<td colspan="8">
											近两年逾期最高金额:<input id="twoYearsLimit" name="type02RhTransDetailList[rhTransDetail02sIndex].twoYearsLimit" type="text" data-options="min:0,precision:2,validType:['length[0,11]']" 
											class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
									</tr>
									<tr id="tr3">
										<th scope="col" colspan="8">已用额度</th>
										<th scope="col" colspan="8">最近6个月平均使用额度</th>
										<th scope="col" colspan="8">本月应还款</th>
									</tr>
									<tr id="tr4">
										<td colspan="8">
											<input id="creditUseAmount" name="type02RhTransDetailList[rhTransDetail02sIndex].creditUseAmount" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
										<td colspan="8">
											<input id="creditAvg6mAmount" name="type02RhTransDetailList[rhTransDetail02sIndex].creditAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
										<td colspan="8">
											<input id="creditPayAmount" name="type02RhTransDetailList[rhTransDetail02sIndex].creditPayAmount" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
									</tr>
									<tr id="tr5">
										<th scope="col" colspan="8">本月实还款</th>
										<th scope="col" colspan="8">当前逾期期数</th>
										<th scope="col" colspan="8">当前逾期金额</th>
									</tr>
									<tr id="tr6">
										<td colspan="8">
											<input id="creditPayReceived" name="type02RhTransDetailList[rhTransDetail02sIndex].creditPayReceived" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
										<td colspan="8">
											<input id="creditDefaultNum" name="type02RhTransDetailList[rhTransDetail02sIndex].creditDefaultNum" type="text" editable="false"  data-options="min:0" 
												class="textbox easyui-numberbox" value="0" style="width:100px;"/>
										</td>
										<td colspan="8">
											<input id="creditDefaultAount" name="type02RhTransDetailList[rhTransDetail02sIndex].creditDefaultAount" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="0" style="width:100px;"/>
										</td>
									</tr>
									<tr id="tr7">
										<td colspan="24">
											<table class="datatable">
												<tr>
													<td colspan="24">
														<strong>还款记录</strong> &nbsp;
													</td>
												</tr>
												<tr>
													<td>
														<input id="n1" name="type02RhTransDetailList[rhTransDetail02sIndex].n1" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="1" />
													</td>
													<td>
														<input id="n2" name="type02RhTransDetailList[rhTransDetail02sIndex].n2" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="2" />
													</td>
													<td>
														<input id="n3" name="type02RhTransDetailList[rhTransDetail02sIndex].n3" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="3" />
													</td>
													<td>
														<input id="n4" name="type02RhTransDetailList[rhTransDetail02sIndex].n4" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="4" />
													</td>
													<td>
														<input id="n5" name="type02RhTransDetailList[rhTransDetail02sIndex].n5" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="5" />
													</td>
													<td>
														<input id="n6" name="type02RhTransDetailList[rhTransDetail02sIndex].n6" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="6" />
													</td>
													<td>
														<input id="n7" name="type02RhTransDetailList[rhTransDetail02sIndex].n7" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="7" />
													</td>
													<td>
														<input id="n8" name="type02RhTransDetailList[rhTransDetail02sIndex].n8" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="8" />
													</td>
													<td>
														<input id="n9" name="type02RhTransDetailList[rhTransDetail02sIndex].n9" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="9" />
													</td>
													<td>
														<input id="n10" name="type02RhTransDetailList[rhTransDetail02sIndex].n10" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="10" />
													</td>
													<td>
														<input id="n11" name="type02RhTransDetailList[rhTransDetail02sIndex].n11" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="11" />
													</td>
													<td>
														<input id="n12" name="type02RhTransDetailList[rhTransDetail02sIndex].n12" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="12" />
													</td>
													<td>
														<input id="n13" name="type02RhTransDetailList[rhTransDetail02sIndex].n13" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="13" />
													</td>
													<td>
														<input id="n14" name="type02RhTransDetailList[rhTransDetail02sIndex].n14" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="14" />
													</td>
													<td>
														<input id="n15" name="type02RhTransDetailList[rhTransDetail02sIndex].n15" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="15" />
													</td>
													<td>
														<input id="n16" name="type02RhTransDetailList[rhTransDetail02sIndex].n16" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="16" />
													</td>
													<td>
														<input id="n17" name="type02RhTransDetailList[rhTransDetail02sIndex].n17" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="17" />
													</td>
													<td>
														<input id="n18" name="type02RhTransDetailList[rhTransDetail02sIndex].n18" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="18" />
													</td>
													<td>
														<input id="n19" name="type02RhTransDetailList[rhTransDetail02sIndex].n19" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="19" />
													</td>
													<td>
														<input id="n20" name="type02RhTransDetailList[rhTransDetail02sIndex].n20" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="20" />
													</td>
													<td>
														<input id="n21" name="type02RhTransDetailList[rhTransDetail02sIndex].n21" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="21" />
													</td>
													<td>
														<input id="n22" name="type02RhTransDetailList[rhTransDetail02sIndex].n22" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="22" />
													</td>
													<td>
														<input id="n23" name="type02RhTransDetailList[rhTransDetail02sIndex].n23" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="23" />
													</td>
													<td>
														<input id="n24" name="type02RhTransDetailList[rhTransDetail02sIndex].n24" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail02s', 'rhTransDetail02_rhTransDetail02sIndex',this)" tabindex="24" />
													</td>
												</tr>
											</table>
										</td>
								</tr>
							</table>
						</td>
					</tr>
				</textarea>
			</table>
		</div>
		<div title="准贷记卡" style="overflow:auto;padding:10px;" id="semiCreditCardDiv"
		data-options="tools:[{iconCls:'icon-add', handler:function(){addObj('rhTransDetail03s');}}]">
			<input type="hidden"  id="rhTransDetail03sIndex" 
					value="${rhTransDetail03s==null?0:(fn:length(rhTransDetail03s)>0?fn:length(rhTransDetail03s):0)}"/>
			<table id="rhTransDetail03s">
				<c:forEach items="${rhTransDetail03s}" var="rhTransDetail03" varStatus="status">
					<tr id="rhTransDetail03_${status.index}">
						<td>
							<table class="datatable">
								<tr id="tr1">
									<td colspan="24">
										<strong>准贷记卡${status.index + 1}</strong> &nbsp;
										<img id="img1" src="img/deleteItem2.png"  onclick="rmObjInfo('rhTransDetail03s', 'rhTransDetail03_${status.index}');" />
										<hr width="100%" size=2 color="#D3D3D3" noshade>
									</td>
								</tr>
								<tr id="tr2">
									<td id="tdQuasiCreditHeader" colspan="24">
										<input name="type03RhTransDetailList[${status.index}].id" type="hidden" value="${rhTransDetail03.id }"/>
										<input id="state" name="type03RhTransDetailList[${status.index}].state" type="hidden"  value="${rhTransDetail03.state }"/>
										<input name="type03RhTransDetailList[${status.index}].appId" type="hidden" value="${bean.appId}"/>
										<input name="type03RhTransDetailList[${status.index}].type" type="hidden" value="03"/>
									
										&nbsp; &nbsp; &nbsp;<input id="startDate" name="type03RhTransDetailList[${status.index}].startDate" type="text" value="${rhTransDetail03.startDateStr }" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
										发放的<input id="loanType" name="type03RhTransDetailList[${status.index}].loanType" type="text" data-options="required:true,validType:['length[0,50]']" 
										class="easyui-combobox"  value="${rhTransDetail03.loanType}" editable="false" style="width:100px;"/>,
										授信额度<input id="amount" name="type03RhTransDetailList[${status.index}].amount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail03.amount}" style="width:100px;"/>元。
<%-- 										<input id="bizType" name="type03RhTransDetailList[${status.index}].bizType" type="text" data-options="required:true,validType:['length[0,50]']"  --%>
<%-- 										class="easyui-combobox"  value="${rhTransDetail03.bizType}" editable="false" style="width:100px;"/>。 --%>
										截至/于<input id="toDate" name="type03RhTransDetailList[${status.index}].toDate" type="text" value="${rhTransDetail03.toDateStr }" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
										<input id="transState" name="type03RhTransDetailList[${status.index}].transState" type="text" data-options="required:true,validType:['length[0,50]']" 
										class="easyui-combobox"  value="${rhTransDetail03.transState}" editable="false" style="width:100px;"/>,
									</td>
								</tr>
								<tr id="trTwoYearsCount">
									<td colspan="8">
										近两年逾期次数:<input id="twoYearsOverdue" name="type03RhTransDetailList[${status.index}].twoYearsOverdue" type="text" data-options="validType:['length[0,2]']" 
											class="textbox easyui-numberbox" value="${rhTransDetail03.twoYearsOverdue}" style="width:100px;"/>
									</td>
									<td colspan="8">
										近两年逾期最高程度:<input id="twoYearsDegree" name="type03RhTransDetailList[${status.index}].twoYearsDegree" type="text" data-options="validType:['length[0,2]']" 
											class="textbox easyui-numberbox" value="${rhTransDetail03.twoYearsDegree}" style="width:100px;"/>
									</td>
									<td colspan="8">
										近两年逾期最高金额:<input id="twoYearsLimit" name="type03RhTransDetailList[${status.index}].twoYearsLimit" type="text" data-options="min:0,precision:2,validType:['length[0,11]']" 
											class="textbox easyui-numberbox" value="${rhTransDetail03.twoYearsLimit}" style="width:100px;"/>
									</td>
								</tr>
								<tr id="tr3">
									<th scope="col" colspan="5">透支金额</th>
									<th scope="col" colspan="5">最近6个月平均透支余额</th>
									<th scope="col" colspan="5">最大透支余额</th>
									<th scope="col" colspan="5">本月实还款</th>
									<th scope="col" colspan="4">透支180天以上未付余额</th>
								</tr>
								<tr id="tr4">
									<td colspan="5">
										<input id="semiCreditUseAmount" name="type03RhTransDetailList[${status.index}].semiCreditUseAmount" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail03.semiCreditUseAmount}" style="width:80px;"/>
									</td>
									<td colspan="5">
										<input id="semiCreditAvg6mAmount" name="type03RhTransDetailList[${status.index}].semiCreditAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail03.semiCreditAvg6mAmount}" style="width:80px;"/>
									</td>
									<td colspan="5">
										<input id="semiCreditMaxAmount" name="type03RhTransDetailList[${status.index}].semiCreditMaxAmount" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail03.semiCreditMaxAmount}" style="width:80px;"/>
									</td>
									<td colspan="5">
										<input id="semiCreditPayReceived" name="type03RhTransDetailList[${status.index}].semiCreditPayReceived" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail03.semiCreditPayReceived}" style="width:80px;"/>
									</td>
									<td colspan="4">
										<input id="semiCreditDefault6mAount" name="type03RhTransDetailList[${status.index}].semiCreditDefault6mAount" type="text" editable="false"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${rhTransDetail03.semiCreditDefault6mAount}" style="width:80px;"/>
									</td>
								</tr>
								<tr id="tr5">
									<td colspan="24">
										<table class="datatable">
											<tr>
												<td colspan="24">
													<strong>还款记录</strong> &nbsp;
												</td>
											</tr>
											<tr>
												<td>
													<input id="n1" name="type03RhTransDetailList[${status.index}].n1" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n1}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="1" />
												</td>
												<td>
													<input id="n2" name="type03RhTransDetailList[${status.index}].n2" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n2}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="2" />
												</td>
												<td>
													<input id="n3" name="type03RhTransDetailList[${status.index}].n3" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n3}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="3" />
												</td>
												<td>
													<input id="n4" name="type03RhTransDetailList[${status.index}].n4" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n4}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="4" />
												</td>
												<td>
													<input id="n5" name="type03RhTransDetailList[${status.index}].n5" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n5}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="5" />
												</td>
												<td>
													<input id="n6" name="type03RhTransDetailList[${status.index}].n6" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n6}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="6" />
												</td>
												<td>
													<input id="n7" name="type03RhTransDetailList[${status.index}].n7" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n7}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="7" />
												</td>
												<td>
													<input id="n8" name="type03RhTransDetailList[${status.index}].n8" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n8}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="8" />
												</td>
												<td>
													<input id="n9" name="type03RhTransDetailList[${status.index}].n9" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n9}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="9" />
												</td>
												<td>
													<input id="n10" name="type03RhTransDetailList[${status.index}].n10" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n10}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="10" />
												</td>
												<td>
													<input id="n11" name="type03RhTransDetailList[${status.index}].n11" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n11}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="11" />
												</td>
												<td>
													<input id="n12" name="type03RhTransDetailList[${status.index}].n12" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n12}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="12" />
												</td>
												<td>
													<input id="n13" name="type03RhTransDetailList[${status.index}].n13" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n13}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="13" />
												</td>
												<td>
													<input id="n14" name="type03RhTransDetailList[${status.index}].n14" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n14}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="14" />
												</td>
												<td>
													<input id="n15" name="type03RhTransDetailList[${status.index}].n15" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n15}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="15" />
												</td>
												<td>
													<input id="n16" name="type03RhTransDetailList[${status.index}].n16" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n16}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="16" />
												</td>
												<td>
													<input id="n17" name="type03RhTransDetailList[${status.index}].n17" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n17}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="17" />
												</td>
												<td>
													<input id="n18" name="type03RhTransDetailList[${status.index}].n18" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n18}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="18" />
												</td>
												<td>
													<input id="n19" name="type03RhTransDetailList[${status.index}].n19" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n19}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="19" />
												</td>
												<td>
													<input id="n20" name="type03RhTransDetailList[${status.index}].n20" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n20}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="20" />
												</td>
												<td>
													<input id="n21" name="type03RhTransDetailList[${status.index}].n21" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n21}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="21" />
												</td>
												<td>
													<input id="n22" name="type03RhTransDetailList[${status.index}].n22" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n22}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="22" />
												</td>
												<td>
													<input id="n23" name="type03RhTransDetailList[${status.index}].n23" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n23}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="23" />
												</td>
												<td>
													<input id="n24" name="type03RhTransDetailList[${status.index}].n24" type="text" data-options="validType:['length[0,1]']" 
														class="textbox easyui-validatebox" value="${rhTransDetail03.n24}" style="width: 20px;" 
														oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_${status.index}',this)" tabindex="24" />
												</td>
											</tr>
										</table>
									</td>
							</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
					<textarea id="rhTransDetail03s_textarea" disabled="disabled" style="display:none">
						<tr id="rhTransDetail03_rhTransDetail03sIndex">
							<td colspan="25">
								<table class="datatable">
									<tr id="tr1">
										<td colspan="25">
											<strong>准贷记卡rhTransDetail03sIndex</strong> &nbsp;
											<img id="img1" src="img/deleteItem2.png"  onclick="rmObjInfo('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex');" />
											<hr width="100%" size=2 color="#D3D3D3" noshade>
										</td>
									</tr>
									<tr id="tr2">
										<td id="tdQuasiCreditHeader" colspan="25">
											<input name="type03RhTransDetailList[rhTransDetail03sIndex].id" type="hidden" value="0"/>
											<input id="state" name="type03RhTransDetailList[rhTransDetail03sIndex].state" type="hidden"  value="1"/>
											<input name="type03RhTransDetailList[rhTransDetail03sIndex].appId" type="hidden" value="${bean.appId}"/>
											<input name="type03RhTransDetailList[rhTransDetail03sIndex].type" type="hidden" value="03"/>
											
											&nbsp; &nbsp; &nbsp;<input id="startDate" name="type03RhTransDetailList[rhTransDetail03sIndex].startDate" type="text" value="" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
											发放的<input id="loanType" name="type03RhTransDetailList[rhTransDetail03sIndex].loanType" type="text" data-options="required:true,validType:['length[0,50]']" 
											class="easyui-combobox"  value="" editable="false" style="width:100px;"/>,
											授信额度
											<input id="amount" name="type03RhTransDetailList[rhTransDetail03sIndex].amount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>元。
<!-- 											<input id="bizType" name="type03RhTransDetailList[rhTransDetail03sIndex].bizType" type="text" data-options="required:true,validType:['length[0,50]']"  -->
<!-- 											class="easyui-combobox"  value="" editable="false" style="width:100px;"/>。 -->
											截至/于<input id="toDate" name="type03RhTransDetailList[rhTransDetail03sIndex].toDate" type="text" value="" class="textbox easyui-datebox" data-options="required:true,validType:['length[0,10]']" style="width:90px;"/>
											<input id="transState" name="type03RhTransDetailList[rhTransDetail03sIndex].transState" type="text" data-options="required:true,validType:['length[0,50]']" 
											class="easyui-combobox"  value="" editable="false" style="width:100px;"/>,
										</td>
									</tr>
									<tr id="trTwoYearsCount">
										<td colspan="8">
											近两年逾期次数:<input id="twoYearsOverdue" name="type03RhTransDetailList[rhTransDetail03sIndex].twoYearsOverdue" type="text" data-options="validType:['length[0,2]']" 
												class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
										<td colspan="8">
											近两年逾期最高程度:<input id="twoYearsDegree" name="type03RhTransDetailList[rhTransDetail03sIndex].twoYearsDegree" type="text" data-options="validType:['length[0,2]']" 
											class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
										<td colspan="8">
											近两年逾期最高金额:<input id="twoYearsLimit" name="type03RhTransDetailList[rhTransDetail03sIndex].twoYearsLimit" type="text" data-options="min:0,precision:2,validType:['length[0,11]']" 
											class="textbox easyui-numberbox" value="" style="width:100px;"/>
										</td>
									</tr>
									<tr id="tr3">
										<th scope="col" colspan="5">透支金额</th>
										<th scope="col" colspan="5">最近6个月平均透支余额</th>
										<th scope="col" colspan="5">最大透支余额</th>
										<th scope="col" colspan="5">本月实还款</th>
										<th scope="col" colspan="4">透支180天以上未付余额</th>
									</tr>
									<tr id="tr4">
										<td colspan="5">
											<input id="semiCreditUseAmount" name="type03RhTransDetailList[rhTransDetail03sIndex].semiCreditUseAmount" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:80px;"/>
										</td>
										<td colspan="5">
											<input id="semiCreditAvg6mAmount" name="type03RhTransDetailList[rhTransDetail03sIndex].semiCreditAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:80px;"/>
										</td>
										<td colspan="5">
											<input id="semiCreditMaxAmount" name="type03RhTransDetailList[rhTransDetail03sIndex].semiCreditMaxAmount" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:80px;"/>
										</td>
										<td colspan="5">
											<input id="semiCreditPayReceived" name="type03RhTransDetailList[rhTransDetail03sIndex].semiCreditPayReceived" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:80px;"/>
										</td>
										<td colspan="4">
											<input id="semiCreditDefault6mAount" name="type03RhTransDetailList[rhTransDetail03sIndex].semiCreditDefault6mAount" type="text" editable="false"  data-options="min:0,precision:2" 
												class="textbox easyui-numberbox" value="" style="width:80px;"/>
										</td>
									</tr>
									<tr id="tr5">
										<td colspan="25">
											<table class="datatable">
												<tr>
													<td colspan="24">
														<strong>还款记录</strong> &nbsp;
													</td>
												</tr>
												<tr>
													<td>
														<input id="n1" name="type03RhTransDetailList[rhTransDetail03sIndex].n1" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="1" />
													</td>
													<td>
														<input id="n2" name="type03RhTransDetailList[rhTransDetail03sIndex].n2" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="2" />
													</td>
													<td>
														<input id="n3" name="type03RhTransDetailList[rhTransDetail03sIndex].n3" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="3" />
													</td>
													<td>
														<input id="n4" name="type03RhTransDetailList[rhTransDetail03sIndex].n4" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="4" />
													</td>
													<td>
														<input id="n5" name="type03RhTransDetailList[rhTransDetail03sIndex].n5" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="5" />
													</td>
													<td>
														<input id="n6" name="type03RhTransDetailList[rhTransDetail03sIndex].n6" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="6" />
													</td>
													<td>
														<input id="n7" name="type03RhTransDetailList[rhTransDetail03sIndex].n7" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="7" />
													</td>
													<td>
														<input id="n8" name="type03RhTransDetailList[rhTransDetail03sIndex].n8" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="8" />
													</td>
													<td>
														<input id="n9" name="type03RhTransDetailList[rhTransDetail03sIndex].n9" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="9" />
													</td>
													<td>
														<input id="n10" name="type03RhTransDetailList[rhTransDetail03sIndex].n10" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="10" />
													</td>
													<td>
														<input id="n11" name="type03RhTransDetailList[rhTransDetail03sIndex].n11" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="11" />
													</td>
													<td>
														<input id="n12" name="type03RhTransDetailList[rhTransDetail03sIndex].n12" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="12" />
													</td>
													<td>
														<input id="n13" name="type03RhTransDetailList[rhTransDetail03sIndex].n13" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="13" />
													</td>
													<td>
														<input id="n14" name="type03RhTransDetailList[rhTransDetail03sIndex].n14" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="14" />
													</td>
													<td>
														<input id="n15" name="type03RhTransDetailList[rhTransDetail03sIndex].n15" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="15" />
													</td>
													<td>
														<input id="n16" name="type03RhTransDetailList[rhTransDetail03sIndex].n16" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="16" />
													</td>
													<td>
														<input id="n17" name="type03RhTransDetailList[rhTransDetail03sIndex].n17" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="17" />
													</td>
													<td>
														<input id="n18" name="type03RhTransDetailList[rhTransDetail03sIndex].n18" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="18" />
													</td>
													<td>
														<input id="n19" name="type03RhTransDetailList[rhTransDetail03sIndex].n19" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="19" />
													</td>
													<td>
														<input id="n20" name="type03RhTransDetailList[rhTransDetail03sIndex].n20" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="20" />
													</td>
													<td>
														<input id="n21" name="type03RhTransDetailList[rhTransDetail03sIndex].n21" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="21" />
													</td>
													<td>
														<input id="n22" name="type03RhTransDetailList[rhTransDetail03sIndex].n22" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="22" />
													</td>
													<td>
														<input id="n23" name="type03RhTransDetailList[rhTransDetail03sIndex].n23" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="23" />
													</td>
													<td>
														<input id="n24" name="type03RhTransDetailList[rhTransDetail03sIndex].n24" type="text" data-options="validType:['length[0,1]']" 
															class="textbox easyui-validatebox" value="N" style="width: 20px;" 
															oninput ="autoNext('rhTransDetail03s', 'rhTransDetail03_rhTransDetail03sIndex',this)" tabindex="24" />
													</td>
												</tr>
											</table>
										</td>
								</tr>
							</table>
						</td>
					</tr>
				</textarea>
			</table>
		</div>
		<div title="查询记录" style="overflow:auto;padding:10px;" id="queryRecordDiv">
			<center><font style="font-size: 15px;"><strong>查询记录汇总</strong></font></center>
			<table class="datatable">
				<tr>
					<th scope="col" colspan="2">最近1个月内的查询机构数</th>
					<th scope="col" colspan="2">最近1个月内的查询次数</th>
					<th scope="col" colspan="3">最近2年内的查询次数</th>
				</tr>
				<tr>
					<th scope="col">贷款审批</th>
					<th scope="col">信用卡审批</th>
					<th scope="col">贷款审批</th>
					<th scope="col">信用卡审批</th>
					<th scope="col">货后管理</th>
					<th scope="col">担保资格审查</th>
					<th scope="col">特约商户实名审查</th>
				</tr>
				<tr>
					<td>
						<input name="rhQuery.id" type="hidden" value="${rhQuery.id }"/>
						<input id="state" name="rhQuery.state" type="hidden"  value="${rhQuery.state }"/>
						<input name="rhQuery.appId" type="hidden" value="${bean.appId}"/>
						<input id="query1mLoanCom" name="rhQuery.query1mLoanCom" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhQuery.query1mLoanCom }" style="width:100px;"/>
					</td>
					<td>
						<input id="query1mCreditCom" name="rhQuery.query1mCreditCom" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhQuery.query1mCreditCom }" style="width:100px;"/>
					</td>
					<td>
						<input id="query1mLoan" name="rhQuery.query1mLoan" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhQuery.query1mLoan }" style="width:100px;"/>
					</td>
					<td>
						<input id="query1mCredit" name="rhQuery.query1mCredit" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhQuery.query1mCredit }" style="width:100px;"/>
					</td>
					<td>
						<input id="query2yLoanAfter" name="rhQuery.query2yLoanAfter" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhQuery.query2yLoanAfter }" style="width:100px;"/>
					</td>
					<td>
						<input id="query2ySecureVerify" name="rhQuery.query2ySecureVerify" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhQuery.query2ySecureVerify }" style="width:100px;"/>
					</td>
					<td>
						<input id="query2yMerchantVerify" name="rhQuery.query2yMerchantVerify" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="${rhQuery.query2yMerchantVerify }" style="width:100px;"/>
					</td>
				</tr>
			</table>
			<br/>
			<table class="datatable" id="rhQueryDetails">
				<tr>
					<td colspan="4">
						<div style="font-size: 14px;" >
							<strong>添加明细</strong>
							<img src="img/addItem.gif"  onclick="addObjHaveNo('rhQueryDetails');" />
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<center><font style="font-size: 15px;"><strong>信贷审批查询记录明细</strong></font></center>			
					</td>
				</tr>
				<tr>
					<th style="display:none">
					<input type="hidden"  id="rhQueryDetailsIndex" 
					value="${rhQueryDetails==null?0:(fn:length(rhQueryDetails)>0?fn:length(rhQueryDetails):0)}"/>
					</th>
					<th scope="col" width="30px">编号</th>
					<th scope="col" width="100px">查询日期</th>
<%--					<th scope="col" width="100px">查询操作员</th>--%>
					<th scope="col" width="100px">查询原因</th>
				</tr>
				<c:forEach items="${rhQueryDetails}" var="rhQueryDetail" varStatus="status">
					<tr id="rhQueryDetail_${status.index}">
						<td>
							<input type="hidden" name="rhQueryDetailList[${status.index}].id" value="${rhQueryDetail.id}" />
							<input type="hidden" name="rhQueryDetailList[${status.index}].appId" value="${bean.appId}" />
							<input id="state" type="hidden" name="rhQueryDetailList[${status.index}].state" value="${rhQueryDetail.state}" />
							<input id="serialNumber" type="text" value="${status.index}" class="textbox easyui-validatebox" readonly="readonly" style="width:30px;"/>
						</td>
						<td><input id="queryDate" name="rhQueryDetailList[${status.index}].queryDate" type="text"
						value="${rhQueryDetail.queryDateStr }" class="textbox easyui-datebox" data-options="validType:['length[0,10]']" style="width:100px;"/></td>
<%--						<td><input id="queryOperator" name="rhQueryDetailList[${status.index}].queryOperator" type="text"--%>
<%--						value="${rhQueryDetail.queryOperator }" class="textbox easyui-validatebox" style="width:100px;"/></td>--%>
						<td>
							<input id="queryReason" name="rhQueryDetailList[${status.index}].queryReason" type="text" data-options="validType:['length[0,50]']" 
								class="easyui-combobox"  value="${rhQueryDetail.queryReason }" editable="false" style="width:100px;"/>
							<img src="img/deleteItem2.png"  onclick="rmObj('rhQueryDetail_${status.index}');" />
						</td>
					</tr>
				</c:forEach>
				<tr style="display:none">
					<td colspan="4">
						<textarea id="rhQueryDetails_textarea" disabled="disabled" style="display:none">
							<tr id="rhQueryDetail_rhQueryDetailsIndex">
								<td>
									<input type="hidden" name="rhQueryDetailList[rhQueryDetailsIndex].id" value="0" />
									<input type="hidden" name="rhQueryDetailList[rhQueryDetailsIndex].appId" value="${bean.appId}" />	
									<input id="state" type="hidden" name="rhQueryDetailList[rhQueryDetailsIndex].state" value="1" />
									<input id="serialNumber" type="text" value="rhQueryDetailsIndex" class="textbox easyui-validatebox" readonly="readonly" style="width:30px;"/>
								</td>
								<td>
									<input id="queryDate" name="rhQueryDetailList[rhQueryDetailsIndex].queryDate" type="text" value=""
									class="textbox easyui-datebox" data-options="validType:['length[0,10]']" style="width:100px;"/>
								</td>
<%--								<td>--%>
<%--									<input id="queryOperator" name="rhQueryDetailList[rhQueryDetailsIndex].queryOperator" --%>
<%--									type="text" value="" class="textbox easyui-validatebox" style="width:100px;"/>--%>
<%--								</td>--%>
								<td>
									<input id="queryReason" name="rhQueryDetailList[rhQueryDetailsIndex].queryReason" type="text" data-options="validType:['length[0,50]']" 
										class="easyui-combobox"  value="" editable="false" style="width:100px;"/>
									<img id="img3" src="img/deleteItem2.png"  onclick="rmObj('rhQueryDetail_rhQueryDetailsIndex');" />
								</td>
							</tr>
						</textarea>
					</td>
				</tr>
			</table>
		</div>
		
		<textarea id="info_textarea" disabled="disabled" style="display:none">
			<li class="li_table_tr" id="-parentIndex-_-childInfo-_-childIndex-">
				<ul class="ul_table">
					<li class="li_table_td" style=''>
						<input name="type-type-RhTransDetailList[-parentIndex-].rhTransDefaultList[-childIndex-].id" type="hidden" value="0"/>
						<input id="state" name="type-type-RhTransDetailList[-parentIndex-].rhTransDefaultList[-childIndex-].state" type="hidden"  value="1"/>
						<input name="type-type-RhTransDetailList[-parentIndex-].rhTransDefaultList[-childIndex-].appId" type="hidden" value="${bean.appId}"/>
						<input name="type-type-RhTransDetailList[-parentIndex-].rhTransDefaultList[-childIndex-].type" type="hidden" value="-type-"/>
						
						<input id="defaultDate" name="type-type-RhTransDetailList[-parentIndex-].rhTransDefaultList[-childIndex-].defaultDate" type="text" value="" class="textbox easyui-datebox" data-options="validType:['length[0,10]']" style="width:90px;"/>
					</li>
					<li class="li_table_td" style=''>
						<input id="defaultDuringMonth" name="type-type-RhTransDetailList[-parentIndex-].rhTransDefaultList[-childIndex-].defaultDuringMonth" type="text" editable="false"  data-options="min:0" 
							class="textbox easyui-numberbox" value="" style="width:100px;"/>
					</li>
					<li class="li_table_td" style=''>
						<input id="defaultAmount" name="type-type-RhTransDetailList[-parentIndex-].rhTransDefaultList[-childIndex-].defaultAmount" type="text" editable="false"  data-options="min:0,precision:2" 
							class="textbox easyui-numberbox" value="" style="width:100px;"/>
						<img id="img2" src="img/deleteItem2.png"  onclick="rmObjInfo('-parentId-', '-parentIndex-_-childInfo-_-childIndex-');" />
					</li>
				</ul>
			</li>
		</textarea>
  </div>
 
<script type="text/javascript">
// $.parser.parse($("#houseAdd"));
// $.parser.parse($("#wageFlowAdd"));
// $.parser.parse($("#rhQueryDetailAdd"));

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
		toggleValidate(obj,false);
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
					if(newValue == '01'){
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
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
					}else if(newValue == '03'){//结清
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").show();
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
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
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
					if(newValue == '01' || newValue == '02'){
						$("#" + key).find("#" + key1).find("#tdLoanClass").css("background-color","");
					}else if(newValue == '03' || newValue == '04' || newValue == '05'){
			 			$("#" + key).find("#" + key1).find("#tdLoanClass").css("background-color","#FF0000");
					}
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
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
						$("#" + key).find("#" + key1).find("#tr3").show();
						$("#" + key).find("#" + key1).find("#tr4").show();
						$("#" + key).find("#" + key1).find("#tr5").show();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
						$("#" + key).find("#" + key1).find("#tr8").show();
						$("#" + key).find("#" + key1).find("#tr9").show();
						$("#" + key).find("#" + key1).find("#tdCreditCardHeader").css("background-color","");
					}else if(newValue == '02'){
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
						$("#" + key).find("#" + key1).find("#tr3").show();
						$("#" + key).find("#" + key1).find("#tr4").show();
						$("#" + key).find("#" + key1).find("#tr5").show();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
						$("#" + key).find("#" + key1).find("#tr8").show();
						$("#" + key).find("#" + key1).find("#tr9").show();
						$("#" + key).find("#" + key1).find("#tdCreditCardHeader").css("background-color","");
					}else if(newValue == '03'){
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").show();
						$("#" + key).find("#" + key1).find("#tr3").hide();
						$("#" + key).find("#" + key1).find("#tr4").hide();
						$("#" + key).find("#" + key1).find("#tr5").hide();
						$("#" + key).find("#" + key1).find("#tr6").hide();
						$("#" + key).find("#" + key1).find("#tr7").hide();
						$("#" + key).find("#" + key1).find("#tr8").show();
						$("#" + key).find("#" + key1).find("#tr9").show();
						$("#" + key).find("#" + key1).find("#tdCreditCardHeader").css("background-color","");
					}else if(newValue == '04'){
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
						$("#" + key).find("#" + key1).find("#tr3").hide();
						$("#" + key).find("#" + key1).find("#tr4").hide();
						$("#" + key).find("#" + key1).find("#tr5").hide();
						$("#" + key).find("#" + key1).find("#tr6").hide();
						$("#" + key).find("#" + key1).find("#tr7").hide();
						$("#" + key).find("#" + key1).find("#tr8").show();
						$("#" + key).find("#" + key1).find("#tr9").show();
						$("#" + key).find("#" + key1).find("#tdCreditCardHeader").css("background-color","");
					}else{
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
						$("#" + key).find("#" + key1).find("#tr3").show();
						$("#" + key).find("#" + key1).find("#tr4").show();
						$("#" + key).find("#" + key1).find("#tr5").show();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
						$("#" + key).find("#" + key1).find("#tr8").show();
						$("#" + key).find("#" + key1).find("#tr9").show();
						$("#" + key).find("#" + key1).find("#tdCreditCardHeader").css("background-color","#FF0000");
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
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
						$("#" + key).find("#" + key1).find("#tr3").show();
						$("#" + key).find("#" + key1).find("#tr4").show();
						$("#" + key).find("#" + key1).find("#tr5").show();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
						$("#" + key).find("#" + key1).find("#tdQuasiCreditHeader").css("background-color","");
					}else if(newValue == '02'){
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
						$("#" + key).find("#" + key1).find("#tr3").show();
						$("#" + key).find("#" + key1).find("#tr4").show();
						$("#" + key).find("#" + key1).find("#tr5").show();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
						$("#" + key).find("#" + key1).find("#tdQuasiCreditHeader").css("background-color","");
					}else if(newValue == '03'){
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").show();
						$("#" + key).find("#" + key1).find("#tr3").hide();
						$("#" + key).find("#" + key1).find("#tr4").hide();
						$("#" + key).find("#" + key1).find("#tr5").hide();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
						$("#" + key).find("#" + key1).find("#tdQuasiCreditHeader").css("background-color","");
					}else if(newValue == '04'){
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
						$("#" + key).find("#" + key1).find("#tr3").hide();
						$("#" + key).find("#" + key1).find("#tr4").hide();
						$("#" + key).find("#" + key1).find("#tr5").hide();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
						$("#" + key).find("#" + key1).find("#tdQuasiCreditHeader").css("background-color","");
					}else{
						$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
						$("#" + key).find("#" + key1).find("#tr3").show();
						$("#" + key).find("#" + key1).find("#tr4").show();
						$("#" + key).find("#" + key1).find("#tr5").show();
						$("#" + key).find("#" + key1).find("#tr6").show();
						$("#" + key).find("#" + key1).find("#tr7").show();
						$("#" + key).find("#" + key1).find("#tdQuasiCreditHeader").css("background-color","#FF0000");
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
			if(transState == '01'){//正常
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
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
			}else if(transState == '03'){//结清
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").show();
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
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
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
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
				$("#" + key).find("#" + key1).find("#tr3").show();
				$("#" + key).find("#" + key1).find("#tr4").show();
				$("#" + key).find("#" + key1).find("#tr5").show();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
				$("#" + key).find("#" + key1).find("#tr8").show();
				$("#" + key).find("#" + key1).find("#tr9").show();
				$("#" + key).find("#" + key1).find("#tdCreditCardHeader").css("background-color","");
			}else if(transState == '02'){
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
				$("#" + key).find("#" + key1).find("#tr3").show();
				$("#" + key).find("#" + key1).find("#tr4").show();
				$("#" + key).find("#" + key1).find("#tr5").show();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
				$("#" + key).find("#" + key1).find("#tr8").show();
				$("#" + key).find("#" + key1).find("#tr9").show();
				$("#" + key).find("#" + key1).find("#tdCreditCardHeader").css("background-color","");
			}else if(transState == '03'){
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").show();
				$("#" + key).find("#" + key1).find("#tr3").hide();
				$("#" + key).find("#" + key1).find("#tr4").hide();
				$("#" + key).find("#" + key1).find("#tr5").hide();
				$("#" + key).find("#" + key1).find("#tr6").hide();
				$("#" + key).find("#" + key1).find("#tr7").hide();
				$("#" + key).find("#" + key1).find("#tr8").show();
				$("#" + key).find("#" + key1).find("#tr9").show();
				$("#" + key).find("#" + key1).find("#tdCreditCardHeader").css("background-color","");
			}else if(transState == '04'){
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
				$("#" + key).find("#" + key1).find("#tr3").hide();
				$("#" + key).find("#" + key1).find("#tr4").hide();
				$("#" + key).find("#" + key1).find("#tr5").hide();
				$("#" + key).find("#" + key1).find("#tr6").hide();
				$("#" + key).find("#" + key1).find("#tr7").hide();
				$("#" + key).find("#" + key1).find("#tr8").show();
				$("#" + key).find("#" + key1).find("#tr9").show();
				$("#" + key).find("#" + key1).find("#tdCreditCardHeader").css("background-color","");
			}else{
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
				$("#" + key).find("#" + key1).find("#tr3").show();
				$("#" + key).find("#" + key1).find("#tr4").show();
				$("#" + key).find("#" + key1).find("#tr5").show();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
				$("#" + key).find("#" + key1).find("#tr8").show();
				$("#" + key).find("#" + key1).find("#tr9").show();
				$("#" + key).find("#" + key1).find("#tdCreditCardHeader").css("background-color","#FF0000");
			}
		}
		if(key == 'rhTransDetail03s'){
			if(transState == '01'){
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
				$("#" + key).find("#" + key1).find("#tr3").show();
				$("#" + key).find("#" + key1).find("#tr4").show();
				$("#" + key).find("#" + key1).find("#tr5").show();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
				$("#" + key).find("#" + key1).find("#tdQuasiCreditHeader").css("background-color","");
			}else if(transState == '02'){
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
				$("#" + key).find("#" + key1).find("#tr3").show();
				$("#" + key).find("#" + key1).find("#tr4").show();
				$("#" + key).find("#" + key1).find("#tr5").show();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
				$("#" + key).find("#" + key1).find("#tdQuasiCreditHeader").css("background-color","");
			}else if(transState == '03'){
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").show();
				$("#" + key).find("#" + key1).find("#tr3").hide();
				$("#" + key).find("#" + key1).find("#tr4").hide();
				$("#" + key).find("#" + key1).find("#tr5").hide();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
				$("#" + key).find("#" + key1).find("#tdQuasiCreditHeader").css("background-color","");
			}else if(transState == '04'){
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
				$("#" + key).find("#" + key1).find("#tr3").hide();
				$("#" + key).find("#" + key1).find("#tr4").hide();
				$("#" + key).find("#" + key1).find("#tr5").hide();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
				$("#" + key).find("#" + key1).find("#tdQuasiCreditHeader").css("background-color","");
			}else{
				$("#" + key).find("#" + key1).find("#trTwoYearsCount").hide();
				$("#" + key).find("#" + key1).find("#tr3").show();
				$("#" + key).find("#" + key1).find("#tr4").show();
				$("#" + key).find("#" + key1).find("#tr5").show();
				$("#" + key).find("#" + key1).find("#tr6").show();
				$("#" + key).find("#" + key1).find("#tr7").show();
				$("#" + key).find("#" + key1).find("#tdQuasiCreditHeader").css("background-color","#FF0000");
			}
		}
	}
	
	//下边是控制贷款回显改变颜色
	//贷记卡，准贷记卡变色字段为transtate，和控制隐藏相同
	function showColor(key, key1, loanclass){
		if(key == 'rhTransDetail01s'){
			if(loanclass == '01' || loanclass == '02'){
				$("#" + key).find("#" + key1).find("#tdLoanClass").css("background-color","");
			}else if(loanclass == '03' || loanclass == '04' || loanclass == '05'){
	 			$("#" + key).find("#" + key1).find("#tdLoanClass").css("background-color","#FF0000");
			}
		}
	}
	
	function autoNext(key, key1, thisInput){
		var thisNum = thisInput.tabIndex;
		if (thisInput.value.length==1 && thisNum<24){
			var place = "n"+(thisNum+1);
// 			$("#" + key).find("#" + key1).find("#"+place).focus();
			$("#" + key).find("#" + key1).find("#"+place).select();
		}
	}
	
$(document).ready(function(){
	$.parser.parse($("#reportVerify"));
	
	var mateIdType = document.getElementById("rhReport.mateIdType").value;
	if(mateIdType==""||mateIdType==null){
		document.getElementById("rhReport.mateIdType").value="身份证";
	}
	
});

/*
 * 页面加载后调用
 */
$.parser.onComplete = function(){
	
	
	//回显时就做好贷款、贷记卡、准贷记卡中列的显示隐藏
	<c:forEach items="${rhTransDetail01s}" var="rhTransDetail01" varStatus="status">
		onSelects('rhTransDetail01s', 'rhTransDetail01_${status.index}');
		showOrHideFn('rhTransDetail01s', 'rhTransDetail01_${status.index}', '${rhTransDetail01.transState}');
		showColor('rhTransDetail01s', 'rhTransDetail01_${status.index}', '${rhTransDetail01.loanClass}');
	</c:forEach>
	<c:forEach items="${rhTransDetail02s}" var="rhTransDetail02" varStatus="status">
		onSelects('rhTransDetail02s', 'rhTransDetail02_${status.index}');
		showOrHideFn('rhTransDetail02s', 'rhTransDetail02_${status.index}', '${rhTransDetail02.transState}');
	</c:forEach>
	<c:forEach items="${rhTransDetail03s}" var="rhTransDetail03" varStatus="status">
		onSelects('rhTransDetail03s', 'rhTransDetail03_${status.index}');
		showOrHideFn('rhTransDetail03s', 'rhTransDetail03_${status.index}', '${rhTransDetail03.transState}');
	</c:forEach>
	
	//回显时就做好担保信息和查询记录明细加编号
<%--	addNo('rhTransDetail04s');--%>
<%--	addNo('rhQueryDetails');--%>
	
	//填充select数据 证件类型
	//var idTypeurl = "sys/datadictionary/listjason.do?keyName=personidtype";
	$("#idType").combobox("clear");
	$("#idType").combobox({
		//url: idTypeurl,
		valueField: 'keyProp',
		textField: 'keyValue',
		data:dataDictJson.personidtype,
		onChange: function(newValue, oldValue){
   		}
	});
	
	//性别
	$("#gender").combobox("clear");
	$('#gender').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.gender	
	});
	$('#gender').combobox('select', '${rhReport.gender}');
	
	//填充select数据 婚姻状况
	//var marriageurl = "sys/datadictionary/listjason.do?keyName=reportmarriage";
	$("#marriage").combobox("clear");
	$("#marriage").combobox({
		//url: marriageurl,
		valueField: 'keyProp',
		textField: 'keyValue',
		data:dataDictJson.marriage,
		onChange: function(newValue, oldValue){
   		}
	});
	
	//查询原因
	//var queryReasonurl = "sys/datadictionary/listjason.do?keyName=reportquerycause";
	$("#queryReason").combobox("clear");
	$("#queryReason").combobox({
		//url: queryReasonurl,
		valueField: 'keyProp',
		textField: 'keyValue',
		data:dataDictJson.reportquerycause,
		onChange: function(newValue, oldValue){
   		}
	});
	
	//查询原因
	$("#rhQueryDetails").find("#queryReason").combobox("clear");
	$("#rhQueryDetails").find("#queryReason").combobox({
		//url: queryReasonurl,
		valueField: 'keyProp',
		textField: 'keyValue',
		data:dataDictJson.reportquerycause,
		onChange: function(newValue, oldValue){
   		}
	});
	
	//填充select数据 学历
	//var educationurl = "sys/datadictionary/listjason.do?keyName=reporteducation";
	$("#education").combobox("clear");
	$("#education").combobox({
		//url: educationurl,
		valueField: 'keyProp',
		textField: 'keyValue',
		data:dataDictJson.reporteducation,
		onChange: function(newValue, oldValue){
   		}
	});
	
	//填充select数据 学位
	//var degreeurl = "sys/datadictionary/listjason.do?keyName=reportdegree";
	$("#degree").combobox("clear");
	$("#degree").combobox({
		//url: degreeurl,
		valueField: 'keyProp',
		textField: 'keyValue',
		data:dataDictJson.reportdegree,
		onChange: function(newValue, oldValue){
   		}
	});
	
	<c:if test="${rhReport.flag == '0'}">
		showOrHideObj('hide');
	</c:if>
	/* <c:if test="${rhReport.flag == '1'}">
		showOrHideObj('show');
	</c:if> */
	$.parser.onComplete=function(){};
};
 </script>