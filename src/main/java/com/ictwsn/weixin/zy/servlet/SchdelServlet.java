/**
 * 
 */
package com.ictwsn.weixin.zy.servlet;

import com.ictwsn.weixin.jpush.Jpush;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author zhengye
 *
 */
public class SchdelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String appKey = "54642be1084bdd1303610ee1";
    private static final String masterSecret = "be880fb5915b7f581122bdaa";    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String textId = request.getParameter("Removeid");
	    String text = request.getParameter("Removetext");
		
		
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
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DB_URL,userName,userPasswd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Statement st1 = null;
		try {
			 st1 = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
  		if (textId != null) {
  			String sqldel = "DELETE FROM sch" + " WHERE id =" +textId+ ""  ;
  			String iddec = "alter table sch auto_increment=" +textId+ "";
  			try {
				st1.executeUpdate(sqldel);
				
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  			try {
					st1.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  			try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  			Jpush.testSendPush(appKey, masterSecret);
				response.setHeader("Refresh","1;url=http://115.28.90.164/Weixin/JSP/SchDel.jsp");   
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  			
			 
		}
	}

}
