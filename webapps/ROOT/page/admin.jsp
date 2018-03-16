<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" import="org.ma.session.SessionUtil" %>

<%@ taglib uri="/ma-tag-web" prefix="w"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>综合管理</title>
	
	<%@include file="/page/include/ez.jsp"%>
	<%
		boolean isLogin = SessionUtil.loginAdmin(request);
		System.out.println("是否登录成功:" + isLogin);
		if(!isLogin){
			
			response.sendRedirect( rootPath+"/page/login.jsp?loginStatus=fff"); 
		}
	%>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/app/admin/control.js"></script>
	
	<script type="text/javascript" src="<w:root/>/page/app/admin/userManager.js"></script>
	<script type="text/javascript" src="<w:root/>/page/app/admin/deviceMonitor.js"></script>
	<script type="text/javascript" src="<w:root/>/page/app/admin/loginStatus.js"></script>
	
	
	
</head> 
<body >
	<!-- background
	<div class="bg-div"><img src="<w:root/>/css/images/bg.jpg" width="100%"/></div>
	 -->
	<!-- topMenuDiv -->
	<nav  id="topMenuDiv" style="margin-bottom:5px;border-radius: 0px;" class="navbar navbar-default" role="navigation">
		<div   class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" style="font-weight:600;" href="#">综合管理</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
					<li viewMark="menu" menu="userManager" class="active"><a href="#">用户管理</a></li>
					<li viewMark="menu" menu="loginStatus" ><a href="#">登录状态</a></li>
					<li viewMark="menu" menu="deviceMonitor" ><a href="#">设备管理</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<div id="childDiv" style="width:100%;">
	 	<div viewMark="child" menu="userManager">
	 		<table id="table_userManager" style=" margin-bottom: 0px;" class="table table-hover table-striped">
				<caption style="font-weight:600;"> 
					<span style="padding-left:10px;margin-right:20px;">用户列表</span> 
					<button id="btn_user_res" type="button"  class="fa fa-search btn btn-primary">&nbsp;查询</button>
					<button id="btn_user_add" type="button" class="fa fa-plus btn btn-primary" >&nbsp;新增</button>
				</caption>
				<thead>
					<tr>
				 		<th>姓名</th>
						<th>公司</th>
						<th>员工编号</th>
						<th>性别</th>
						<th>指纹数</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody viewMark="tbody">
					<tr>
						<td>Tanmay</td>
						<td>Tanmay</td>
						<td>Tanmay</td>
						<td>Bangalore</td>
						<td>
							<button type="button" class="btn btn-info btn-xs  fa fa-hand-stop-o">&nbsp;指纹</button>
							<button type="button" class="btn btn-info btn-xs fa fa-edit">&nbsp;修改</button>
							<button type="button" class="btn btn-danger btn-xs fa fa-trash-o">&nbsp;删除</button>
						</td>
					</tr>
				</tbody>
			</table>
	 		<div>
	 			<div style="float:left;margin-left:3px;"  id="page_customer"></div>
	 			<div style="font-size:14px;float:left;height:66px;line-height: 66px;padding-left: 15px;"  id="page_customer_count">共234条</div>
	 		</div>
	 		
	 		
			
	 	</div> <!-- /. div[menu="userManager"] -->
	 	
	 	<div viewMark="child" style="display:none;" menu="loginStatus">
	 		<table id="table_loginStatus" style=" margin-bottom: 0px;" class="table table-hover table-striped">
				<caption style="font-weight:600;"> 
					<span style="padding-left:10px;margin-right:20px;">日志列表</span> 
					<button id="btn_loginStatus_res" type="button"  class="fa fa-search btn btn-primary">&nbsp;查询</button>
				</caption>
				<thead>
					<tr>
						<th>员工编号</th>
				 		<th>姓名</th>
						<th>类型</th>
						<th>时间</th>
						<th>cpu</th>
						<th>mac</th>
						<th>ip</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody viewMark="tbody">
					<tr>
						<td>Tanmay</td>
						<td>Tanmay</td>
						<td>Tanmay</td>
						<td>Bangalore</td>
						<td>Bangalore</td>
					</tr>
				</tbody>
			</table>
	 		<div>
	 			<div style="float:left;margin-left:3px;"  id="page_loginStatus"></div>
	 			<div style="font-size:14px;float:left;height:66px;line-height: 66px;padding-left: 15px;"  id="page_loginStatus_count">共234条</div>
	 		</div>
	 	</div>
	 	
	 	<div viewMark="child" style="display:none;" menu="deviceMonitor">
	 		<div style="margin-left:15px;">
 				<button onclick="deviceMonitor.open()" type="button"  class="fa fa-compress btn btn-success">&nbsp;打开连接</button>
				<button onclick="deviceMonitor.close()" style="margin-left:15px;" type="button" class="fa fa-power-off btn btn-danger" >&nbsp;关闭连接</button>
	 		</div>
	 		<div style="margin-top:20px;margin-left:15px;">
	 		    <div style="float:left">	连接状态: </div> 
	 		    <div id="panel_device_status" style="float: left;height:20px;width:50px;margin-left:10px;background-color:red;"></div>
	 		    
	 		</div>
	 	
	 	</div>
	 	
	</div>
	
</body>
</html>
