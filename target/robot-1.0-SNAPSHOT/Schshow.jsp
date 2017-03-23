<%@ page language="java" import="java.util.*"  import="java.sql.*,java.io.*,java.util.*" %>
<%@ page contentType="text/html;charset=utf-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>智能孝子	日程提醒查看</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <!-- 网站图标 -->
    <link rel="shortcut icon" href="../image/main.ico" type="image/x-icon" /> 
  </head>
  <body>
  
  <%request.setCharacterEncoding("UTF-8");%>
  
  	<%
  			String driverName = "com.mysql.jdbc.Driver";
    		String DB_URL = "jdbc:mysql://localhost:3306/schreminder?useSSL=true";
			//数据库用户名 
			String userName = "root";
			//密码 
			String userPasswd = "5362191";
			//数据库名 
			String dbName = "schreminder";
			//表名 
			String tableName = "sch";
		//联结字符串 
		//String url = "jdbc:mysql://localhost:3306/" + dbName  + "?useSSL=" + true+ "?user="
		//		+ userName + "&password=" + userPasswd;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection(DB_URL,userName,userPasswd);
		Statement statement = connection.createStatement();
		String sql = "SELECT * FROM " + tableName;
		ResultSet rs = statement.executeQuery(sql);
	%>
  
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
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
		<h1>当前全部日程提醒</h1>
		
		<div class="table-responsive">
			<table class="table">
				<tr>
					<th>ID</th>
					<th>时间</th>
					<th>内容</th>
					<th>是否每日提醒</th>
				</tr>
		<%
			while (rs.next()) {
		%>
			<tr>
				<td>
					<%=
						(rs.getInt("id"))
					%>
				</td>
				<td>
					<%=
						(rs.getString("time"))
					%>
				</td>
				<td>
					<%=
						(rs.getString("text"))
					%>
				</td>
				<td>
					<%
						if(rs.getInt("dayrepet")==1)
							out.print("是");
						else if(rs.getInt("dayrepet")==0)
							out.print("否");
					%>
				</td>
			</tr>
		<%
			}
		%>
			</table>
		</div>

    </div>

    <%
		rs.close();
		statement.close();
		connection.close();
	%>
  </body>
</html>

