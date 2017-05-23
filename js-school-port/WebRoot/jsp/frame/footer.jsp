<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>	
<!--/content-->
<div class="footer">
	<div class="footer-wrap">
		<div class="footer-left">
			<p class="footer-text f14"><a href="<%=basePath %>jsp/frame/about_us.html" target="_blank">关于秒趣分期</a>  |  <a href="<%=basePath %>jsp/frame/help_center.html" target="_blank">帮助中心</a></p>
			<p class="footer-text">公司地址：北京市海淀区中关村东升科技园C2楼&nbsp;&nbsp;|&nbsp;&nbsp;京ICP备15036698号-2</p>
			<p class="footer-text">Copyright &copy; 2015 秒趣  合稞同创（北京）科技有限公司 版权所有</p>
		</div>
		<div class="footer-right">
			<div class="footer-logo" style="margin-bottom:10px;"><img src="images/logo.png"/></div>
			<div class="footer-customer f16">
				<p>客服电话 </p>
				<p>4000918877</p>
				<p>工作日：10:00-17:00</p>
			</div>
		</div>
	</div>
</div>
