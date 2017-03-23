<%@page   language= "java" import="java.util.*"  import="java.sql.*,java.io.*,java.util.*" %> 
<%@page   contentType= "text/xml; charset=UTF-8"%>  

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
	
	<xmp>
	<%
			while (rs.next()) {
				
	%>
		
		<sch_table> 
  		<sch_info> 
  		  <id> <%=rs.getInt("id")%> </id> 
  		  <time> <%=rs.getString("time")%> </time> 
  		  <text> <%=rs.getString("text")%> </text>
  		</sch_info> 
		</sch_table>
		
  	<%
  	
			}
  	%>
 		</xmp> 
		
	