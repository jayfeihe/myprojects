<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<base href="<%=basePath%>" />

<!-- Nav tabs -->
<ul class="nav nav-tabs isac" id="isac" role="tablist">
	<li role="presentation" class="active"><a href="#home" role="tab"
		data-toggle="tab" id="severeIllegalCount">严重违法</a></li>
	<li role="presentation"><a href="#profile" role="tab"
		data-toggle="tab" id="commonIllegalCount">一般违法</a></li>
	<li role="presentation"><a href="#messages" role="tab"
		data-toggle="tab" id="lightIllegalCount">轻微违法</a></li>
</ul>
<!-- Tab panes -->
<div class="tab-content illegal">
	<div role="tabpanel" class="tab-pane illegal active" id="home">

		<div class="dash-data illegal">
			<%-- <img src="<%=basePath%>images/tb5.png"> --%>
			<div class="base">
				<p>违法广告活动</p>
				<span class="number" id="advertNumber"></span> <span class="change"><span
					id="advertProportion"></span>&nbsp;&nbsp;&nbsp;<span
					id="advertIncrease"></span></span>
			</div>
			<img class="bangzhu" src="<%=basePath%>images/bangzhu.png">
			<div class="answer" style="display: none;">
				<div class="bangzhuInfo">
					广告活动：同一个广告主，在同一个投放落地页（即使标题名称不同）下的不同尺寸和不同媒体上投放的广告创意为同一个广告活动。</div>
				<div class="triangle-down"></div>
			</div>
		</div>

		<div class="dash-data illegal">
			<%-- <img src="<%=basePath%>images/tb6.png"> --%>
			<div class="base">
				<p>违法广告创意</p>
				<span class="number" id="creativeNumber"></span> <span
					class="change"><span id="creativeProportion"></span>&nbsp;&nbsp;&nbsp;<span
					id="creativeIncrease"></span></span>
			</div>
		</div>
		
		<div class="dash-data illegal" id="illegal">
			<!-- <img src="./images/tb6.png"> -->
			<div class="base">
				<p>总体已整改广告创意</p>
				<span class="number" id="allCorrectNumber"></span>
				<span class="change"><span id="allCorrectProportion"></span> 
				&nbsp;&nbsp;&nbsp;
				<span id="allCorrectIncrease"></span></span>
			</div>
		</div>
		
		<div class="dash-data illegal" id="illegal">
			<!-- <img src="./images/tb6.png"> -->
			<div class="base">
				<p>我方已整改广告创意</p>
				<span class="number" id="selfCorrectNumber"></span>
				<span class="change"><span id="selfCorrectProportion"></span> 
				&nbsp;&nbsp;&nbsp;
				<span id="selfCorrectIncrease"></span></span>
			</div>
		</div>
		
		<c:if test="${login_company.type eq 1 }">
			<div class="dash-data illegal">
				<%-- <img src="<%=basePath%>images/tb7.png"> --%>
				<div class="base">
					<p>涉及媒体</p>
					<span class="number" id="mediaNumber"></span> <span
						class="change"><span id="mediaProportion"></span>&nbsp;&nbsp;&nbsp;<span
						id="mediaIncrease"></span></span>
				</div>
			</div>
		</c:if>
		<c:if test="${login_company.type eq 2 }">
			<div class="dash-data illegal">
				<%-- <img src="<%=basePath%>images/tb7.png"> --%>
				<div class="base">
					<p>违法广告主</p>
					<span class="number" id="advertiserNumber"></span> <span
						class="change"><span id="advertiserProportion"></span>&nbsp;&nbsp;&nbsp;<span
						id="advertiserIncrease"></span></span>
				</div>
			</div>
		</c:if>

		<div class="dash-data illegal">
			<%-- <img src="<%=basePath%>images/tb8.png"> --%>
			<div class="base">
				<p>严重违法广告活动占比</p>
				<span class="number" id="countProp"></span> <span class="change">&nbsp;&nbsp;<span></span>
					<span></span></span>
			</div>
			<img class="bangzhu" src="<%=basePath%>images/bangzhu.png">
			<div class="answer" style="display: none;">
				<div class="bangzhuInfo bangzhuInfo-small">严重违法在总违法数量中的占比情况。</div>
				<div class="triangle-down"></div>
			</div>
		</div>
	</div>

	<div role="tabpanel" class="tab-pane illegal" id="profile">

		<div class="dash-data illegal">
			<%-- <img src="<%=basePath%>images/tb5.png"> --%>
			<div class="base">
				<p>违法广告活动</p>
				<span class="number" id="advertNumber"></span> <span class="change"><span
					id="advertProportion"></span>&nbsp;&nbsp;&nbsp;<span
					id="advertIncrease"></span></span>
			</div>
			<img class="bangzhu" src="<%=basePath%>images/bangzhu.png">
			<div class="answer" style="display: none;">
				<div class="bangzhuInfo">
					广告活动：同一个广告主，在同一个投放落地页（即使标题名称不同）下的不同尺寸和不同媒体上投放的广告创意为同一个广告活动。</div>
				<div class="triangle-down"></div>
			</div>
		</div>

		<div class="dash-data illegal">
			<%-- <img src="<%=basePath%>images/tb6.png"> --%>
			<div class="base">
				<p>违法广告创意</p>
				<span class="number" id="creativeNumber"></span> <span
					class="change"><span id="creativeProportion"></span>&nbsp;&nbsp;&nbsp;<span
					id="creativeIncrease"></span></span>
			</div>
		</div>

		<div class="dash-data illegal" id="illegal">
			<!-- <img src="./images/tb6.png"> -->
			<div class="base">
				<p>总体已整改广告创意</p>
				<span class="number" id="allCorrectNumber"></span>
				<span class="change"><span id="allCorrectProportion"></span> 
				&nbsp;&nbsp;&nbsp;
				<span id="allCorrectIncrease"></span></span>
			</div>
		</div>
		
		<div class="dash-data illegal" id="illegal">
			<!-- <img src="./images/tb6.png"> -->
			<div class="base">
				<p>我方已整改广告创意</p>
				<span class="number" id="selfCorrectNumber"></span>
				<span class="change"><span id="selfCorrectProportion"></span> 
				&nbsp;&nbsp;&nbsp;
				<span id="selfCorrectIncrease"></span></span>
			</div>
		</div>

		<c:if test="${login_company.type eq 1 }">
			<div class="dash-data illegal">
				<%-- <img src="<%=basePath%>images/tb7.png"> --%>
				<div class="base">
					<p>涉及媒体</p>
					<span class="number" id="mediaNumber"></span> <span
						class="change"><span id="mediaProportion"></span>&nbsp;&nbsp;&nbsp;<span
						id="mediaIncrease"></span></span>
				</div>
			</div>
		</c:if>
		<c:if test="${login_company.type eq 2 }">
			<div class="dash-data illegal">
				<%-- <img src="<%=basePath%>images/tb7.png"> --%>
				<div class="base">
					<p>违法广告主</p>
					<span class="number" id="advertiserNumber"></span> <span
						class="change"><span id="advertiserProportion"></span>&nbsp;&nbsp;&nbsp;<span
						id="advertiserIncrease"></span></span>
				</div>
			</div>
		</c:if>

		<div class="dash-data illegal">
			<%-- <img src="<%=basePath%>images/tb8.png"> --%>
			<div class="base">
				<p>一般违法广告活动占比</p>
				<span class="number" id="countProp"></span> <span class="change">&nbsp;&nbsp;<span></span>
					<span></span></span>
			</div>
			<img class="bangzhu" src="<%=basePath%>images/bangzhu.png">
			<div class="answer" style="display: none;">
				<div class="bangzhuInfo bangzhuInfo-small">一般违法在总违法数量中的占比情况。</div>
				<div class="triangle-down"></div>
			</div>
		</div>
	</div>

	<div role="tabpanel" class="tab-pane illegal" id="messages">

		<div class="dash-data illegal">
			<%-- <img src="<%=basePath%>images/tb5.png"> --%>
			<div class="base">
				<p>违法广告活动</p>
				<span class="number" id="advertNumber"></span> <span class="change"><span
					id="advertProportion"></span>&nbsp;&nbsp;&nbsp;<span
					id="advertIncrease"></span></span>
			</div>
			<img class="bangzhu" src="<%=basePath%>images/bangzhu.png">
			<div class="answer" style="display: none;">
				<div class="bangzhuInfo">
					广告活动：同一个广告主，在同一个投放落地页（即使标题名称不同）下的不同尺寸和不同媒体上投放的广告创意为同一个广告活动。</div>
				<div class="triangle-down"></div>
			</div>
		</div>

		<div class="dash-data illegal">
			<%-- <img src="<%=basePath%>images/tb6.png"> --%>
			<div class="base">
				<p>违法广告创意</p>
				<span class="number" id="creativeNumber"></span> <span
					class="change"><span id="creativeProportion"></span>&nbsp;&nbsp;&nbsp;<span
					id="creativeIncrease"></span></span>
			</div>
		</div>

		<div class="dash-data illegal" id="illegal">
			<!-- <img src="./images/tb6.png"> -->
			<div class="base">
				<p>总体已整改广告创意</p>
				<span class="number" id="allCorrectNumber"></span>
				<span class="change"><span id="allCorrectProportion"></span> 
				&nbsp;&nbsp;&nbsp;
				<span id="allCorrectIncrease"></span></span>
			</div>
		</div>
		
		<div class="dash-data illegal" id="illegal">
			<!-- <img src="./images/tb6.png"> -->
			<div class="base">
				<p>我方已整改广告创意</p>
				<span class="number" id="selfCorrectNumber"></span>
				<span class="change"><span id="selfCorrectProportion"></span> 
				&nbsp;&nbsp;&nbsp;
				<span id="selfCorrectIncrease"></span></span>
			</div>
		</div>

		<c:if test="${login_company.type eq 1 }">
			<div class="dash-data illegal">
				<%-- <img src="<%=basePath%>images/tb7.png"> --%>
				<div class="base">
					<p>涉及媒体</p>
					<span class="number" id="mediaNumber"></span> <span
						class="change"><span id="mediaProportion"></span>&nbsp;&nbsp;&nbsp;<span
						id="mediaIncrease"></span></span>
				</div>
			</div>
		</c:if>
		<c:if test="${login_company.type eq 2 }">
			<div class="dash-data illegal">
				<%-- <img src="<%=basePath%>images/tb7.png"> --%>
				<div class="base">
					<p>违法广告主</p>
					<span class="number" id="advertiserNumber"></span> <span
						class="change"><span id="advertiserProportion"></span>&nbsp;&nbsp;&nbsp;<span
						id="advertiserIncrease"></span></span>
				</div>
			</div>
		</c:if>

		<div class="dash-data illegal">
			<%-- <img src="<%=basePath%>images/tb8.png"> --%>
			<div class="base">
				<p>轻微违法广告活动占比</p>
				<span class="number" id="countProp"></span> <span class="change"><span>&nbsp;&nbsp;</span>
					<span> </span></span>
			</div>
			<img class="bangzhu" src="<%=basePath%>images/bangzhu.png">
			<div class="answer" style="display: none;">
				<div class="bangzhuInfo bangzhuInfo-small">轻微违法在总违法数量中的占比情况。</div>
				<div class="triangle-down"></div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="<%=basePath%>js/index/dataCount.js"></script>