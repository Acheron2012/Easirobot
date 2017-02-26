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
    <title>智能孝子	添加日程提醒</title>

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
    
    <script type="text/javascript">
		function checkall(){
		var text = document.getElementById("schtext");
		if(text == ""){
		    alert("提醒内容不可为空");
		    return false;
		}
	</script>
    
	<div class="container">
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="dropdown">
		<div class="btn-group">     
				<button onclick="window.location='../html/index.html'" type="button" class="btn btn-default btn-mg">
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
		<h1>添加日程提醒</h1>
		
		<form role="form" action="../sch.do" method="post" onsubmit="return checkall()">
		  <div class="form-group">
			<label for="user_date">日期</label>
			<input type="datetime-local" name="user_date" id="user_date" class="form-control" >
		  </div>
		  <div class="form-group">
			<label for="schtext">提醒内容</label>
			<input type="text" class="form-control" name="schtext" id="schtext" placeholder="提醒内容">
		  </div>
		  <div class="checkbox">
			  <p><input type="radio" name="schcheck" value="1" /> 每天重复提醒
				<input type="radio" name="schcheck" value="0" checked="checked" /> 不重复提醒</p>
		  </div>
		  <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-send"></span> 提交</button>
		</form>


    </div>
  </body>
</html>