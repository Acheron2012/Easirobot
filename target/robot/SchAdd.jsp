<%@page import="sun.rmi.server.Dispatcher"%>
<%@page import="java.lang.ProcessBuilder.Redirect"%>
<%@page import="com.sun.corba.se.spi.orbutil.fsm.Guard.Result"%>
<%@ page language="java" import="java.util.*"  import="java.sql.*,java.io.*,java.util.*" %>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>智能孝子	日程提醒添加结果</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <!-- 网站图标 -->
    <link rel="shortcut icon" href="../image/main.ico" type="image/x-icon" /> 
  </head>
  <body>
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<div class="container">
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="dropdown">
		<div class="btn-group">     
				<button onclick="window.location='http://115.28.90.164/Weixin/html/index.html'" type="button" class="btn btn-default btn-mg">
					<span class="glyphicon glyphicon-home"></span> 控制台
				</button>
		</div>
		</li>
		<li role="presentation" class="dropdown">
		<div class="btn-group">
				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
					 <span class="glyphicon glyphicon-list"></span>  语音提醒管理
				</button>       
				<ul class="dropdown-menu" role="menu">
					<li><a href="../JSP/Audioshow.jsp">查看</a></li>
					<li><a href="../html/audioadd.html">添加</a></li>
					<li><a href="../JSP/AudioDel.jsp">删除</a></li>
				</ul>							
		</div>
		</li>
		<li role="presentation" class="dropdown">
		<div class="btn-group">
			<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
					 <span class="glyphicon glyphicon-list"></span>  日程提醒管理
			</button>       
				<ul class="dropdown-menu" role="menu">
					<li><a href="../JSP/Schshow.jsp">查看</a></li>
					<li><a href="../JSP/Schedule reminder.jsp">添加</a></li>
					<li><a href="../JSP/SchDel.jsp">删除</a></li>
				</ul>
		</div>
		</li>
	</ul>
	
	<%request.setCharacterEncoding("UTF-8");%>
	<%String time = request.getParameter("user_date"); %>
	<%String text = request.getParameter("schtext"); %>
	<%String check = request.getParameter("schcheck"); %>
	<%String checkResult=null; %>
	<%
		 if(check.equals("1")) 
		 {
			 checkResult = "是";
		 } 
		 else if(check.equals("0"))
		 {
			 checkResult = "否";
		 }
	%>
	<h1>日程提醒提交成功</h1>
	<div class="table-responsive">
			<table class="table">
				<tr>
					<th>时间</th>
					<th>内容</th>
					<th>是否每日提醒</th>
				</tr>
		
				 <tr>
					<td>
						<%=time%>
					</td>
					<td>
						<%=text%>
					</td>
					<td>
						 <%=checkResult%> 
					</td>
				</tr>
		
				
			</table>
	</div>


	</div>
  </body>
</html>