<%@ page contentType="text/html;charset=UTF-8" language="java"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>table</title>
	
	<%@include file="/page/include/ez.jsp"%>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ctj/plugins/Page.js"></script>
	<script type="text/javascript" src="table.js"></script>
</head> 
<body >
	<div id="testPage">
	</div>

	<div id="childDiv" style="width:100%;padding-left:10px;padding-right:10px;">
	
	 	<table class="table table-hover table-striped">
			<caption style="font-weight:600;"> 
				<span viewMark="title" style="padding-left:10px;margin-right:20px;">XX列表</span> 
				<button id="btnRes" type="button"  class="fa fa-search btn btn-primary">&nbsp;查询</button>
				<button id="btnP1" type="button"  class="fa fa-search btn btn-primary">&nbsp;奥巍</button>
				<button id="btn" type="button" class="fa fa-plus btn btn-primary" >&nbsp;新增</button>
			</caption>
			<thead>
				<tr>
			 		<th>名称</th>
					<th>城市</th>
					<th>邮编</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="">
				<tr>
					<td>Tanmay</td>
					<td>Bangalore</td>
					<td></td>
					<td>
						<button type="button" class="btn btn-info btn-xs  fa fa-hand-stop-o">&nbsp;指纹</button>
						<button type="button" class="btn btn-info btn-xs fa fa-edit">&nbsp;修改</button>
						<button type="button" class="btn btn-danger btn-xs fa fa-trash-o">&nbsp;删除</button>
					</td>
				</tr>
				<tr>
					<td>Sachin</td>
					<td>Mumbai</td>
					<td>400003</td>
					<td></td>
				</tr>
				<tr>
					<td>Uma</td>
					<td>Pune</td>
					<td>411027</td>
					<td></td>
				</tr>
				<tr class="active">
					<td>产品1</td>
					<td>23/11/2013</td>
					<td>待发货</td>
					<td></td>
				</tr>
				<tr class="success">
					<td>产品2</td>
					<td>10/11/2013</td>
					<td>发货中</td>
					<td></td>
				</tr>
				<tr  class="warning">
					<td>产品3</td>
					<td>20/10/2013</td>
					<td>待确认</td>
					<td></td>
				</tr>
				<tr  class="danger">
					<td>产品4</td>
					<td>20/10/2013</td>
					<td>已退货</td>
					<td></td>
				</tr>
				<tr>
					<td>Tanmay</td>
					<td>Bangalore</td>
					<td></td>
					<td>
						<button type="button" class="btn btn-info btn-xs">指纹</button>
						<button type="button" class="btn btn-info btn-xs">修改</button>
						<button type="button" class="btn btn-danger btn-xs">删除</button>
					</td>
				</tr>
				<tr>
					<td>Sachin</td>
					<td>Mumbai</td>
					<td>400003</td>
					<td></td>
				</tr>
				<tr>
					<td>Uma</td>
					<td>Pune</td>
					<td>411027</td>
					<td></td>
				</tr>
				<tr class="active">
					<td>产品1</td>
					<td>23/11/2013</td>
					<td>待发货</td>
					<td></td>
				</tr>
				<tr class="success">
					<td>产品2</td>
					<td>10/11/2013</td>
					<td>发货中</td>
					<td></td>
				</tr>
				<tr  class="warning">
					<td>产品3</td>
					<td>20/10/2013</td>
					<td>待确认</td>
					<td></td>
				</tr>
				<tr  class="danger">
					<td>产品4</td>
					<td>20/10/2013</td>
					<td>已退货</td>
					<td></td>
				</tr>
			</tbody>
		</table>
		<ul class="pagination pagination-lg" style="margin-top:0px;">
			<li><a href="#">&laquo;</a></li>
			<li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#">&raquo;</a></li>
		</ul>
	</div>

	
	<ul class="pagination pagination-lg">
		<li class="disabled"><a href="javaScript:void(0)">&laquo;</a></li>
		<li><a href="#">1</a></li>
		<li><a href="#">2</a></li>
		<li class="active"><a href="#" >3</a></li>
		<li class="disabled"><a href="#">4</a></li>
		<li><a href="#">5</a></li>
		<li><a href="#">&raquo;</a></li>
	</ul><br>
	<ul class="pagination" style="margin-top:0px;">
			<li><a href="#">&laquo;</a></li>
			<li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#">&raquo;</a></li>
		</ul><br>>
	<ul class="pagination pagination-sm">
		<li><a href="#">&laquo;</a></li>
		<li><a href="#">1</a></li>
		<li><a href="#">2</a></li>
		<li><a href="#">3</a></li>
		<li><a href="#">4</a></li>
		<li><a href="#">5</a></li>
		<li><a href="#">&raquo;</a></li>
	</ul>
	
	
	<div style="margin-top:40px;">
	
		<!-- 标准的按钮 -->
		<button type="button" class="btn btn-default">默认按钮</button>
		<!-- 提供额外的视觉效果，标识一组按钮中的原始动作 -->
		<button type="button" class="btn btn-primary">原始按钮</button>
		<!-- 表示一个成功的或积极的动作 -->
		<button type="button" class="btn btn-success">成功按钮</button>
		<!-- 信息警告消息的上下文按钮 -->
		<button type="button" class="btn btn-info">信息按钮</button>
		<!-- 表示应谨慎采取的动作 -->
		<button type="button" class="btn btn-warning">警告按钮</button>
		<!-- 表示一个危险的或潜在的负面动作 -->
		<button type="button" class="btn btn-danger">危险按钮</button>
		<!-- 并不强调是一个按钮，看起来像一个链接，但同时保持按钮的行为 -->
		<button type="button" class="btn btn-link">链接按钮</button>
		
	</div>
	
	
<a class="btn btn-default" href="#" role="button">链接</a>
<button class="btn btn-default" type="submit">按钮</button>
<input class="btn btn-info" type="button" value="输入">
<input class="btn btn-default" type="submit" value="提交">
	
	<div>
		
<p>
   <button type="button" class="btn btn-primary btn-lg">大的原始按钮</button>
   <button type="button" class="btn btn-default btn-lg">大的按钮</button>
</p>
<p>
   <button type="button" class="btn btn-primary"> 默认大小的原始按钮</button>
   <button type="button" class="btn btn-default"> 默认大小的按钮</button>
</p>
<p>
   <button type="button" class="btn btn-primary btn-sm">小的原始按钮</button>
   <button type="button" class="btn btn-default btn-sm">小的按钮</button>
</p>
<p>
   <button type="button" class="btn btn-primary btn-xs">指纹</button>
   <button type="button" class="btn btn-default btn-xs">特别小的按钮</button>
</p>
<p>
   <button type="button" class="btn btn-primary btn-lg btn-block">块级的原始按钮</button>
   <button type="button" class="btn btn-default btn-lg btn-block">块级的按钮</button>
</p>
		
	
	</div>
	
	
</body>
</html>
