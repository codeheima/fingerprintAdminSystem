<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>登录</title>
<%
	request.getSession().setAttribute("loginType", "user");

	String loginStatusStr = request.getParameter("loginStatus");
	String loginStatus = "1";
	if("fff".equals(loginStatusStr)){
		loginStatus = "2";
	}
%>	
	
	 <%@include file="/page/include/ez.jsp"%>
	 
	 <script language="javaScript">
		 function loginAdmin(){
			 $('#adminForm').submit();	 
		 }
		 $(function(){
			 var loginStatus = '<%=loginStatus%>';
			 if(loginStatus == 2){
				 $('#errorDiv').html('密码错误');
				 
			 }
		 });
		 
	 </script>
	 
</head> 
<body>
	<div style="width:80%;"><h2 style="text-align:center;" >登录</h2> </div>
	<form id="adminForm" class="form-horizontal" method="post" style="width:70%" role="form"  action="${pageContext.request.contextPath}/page/admin.jsp">
		<div class="form-group">
			<label class="col-sm-2 control-label">用户名</label>
			<div class="col-sm-10">
				<p class="form-control-static">admin</p>
			</div>
		</div>
		<div class="form-group">
			<label for="inputPassword" class="col-sm-2 control-label">密码</label>
			<div class="col-sm-10"> 
				<input type="password" class="form-control" id="inputPassword"  name="inputPassword"
					placeholder="请输入密码">
			</div>
		</div>
		<div class="form-group" >
			<label for="" class="col-sm-2 control-label">&nbsp;</label>
			<button style="margin-left:10px;" onclick="loginAdmin()" type="button" class="btn btn-primary btn-lg ">登录</button>
			
		   <%-- 	<a type="button" href="${pageContext.request.contextPath}/page/user.jsp" class="btn btn-default btn-lg active">游客登录</a>
		 --%>
		</div>
	</form>
	<div id="errorDiv" style="color: red;font-size:14px;padding-left: 160px;"></div>
	
</body>
</html>
