package com.ictwsn.web.wechat.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Servlet implementation class SchServlet
 */
@WebServlet(description = "日程提醒相关操作控制", urlPatterns = { "/SchServlet" })
public class SchServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private static final String appKey = "54642be1084bdd1303610ee1";
//    private static final String masterSecret = "be880fb5915b7f581122bdaa";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
//		//驱动程序名
//		PrintWriter out = response.getWriter();
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String time = request.getParameter("user_date");
//
//		String text = request.getParameter("schtext");
//
//		String check = request.getParameter("schcheck");
//				String driverName = "com.mysql.jdbc.Driver";
//		  	    String DB_URL = "jdbc:mysql://localhost:3306/schreminder?useSSL=true";
//				//数据库用户名
//				String userName = "root";
//				//密码
//				String userPasswd = "5362191";
//				//数据库名
//				String dbName = "schreminder";
//				//表名
//				String tableName = "sch";
//				//合并字符串
//				//String url = "jdbc:mysql://localhost:3306/" + dbName  + "?useSSL=" + true+ "?user="
//				//		+ userName + "&password=" + userPasswd;
//				try {
//					Class.forName("com.mysql.jdbc.Driver").newInstance();
//				} catch (InstantiationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				Connection connection = null;
//				try {
//					connection = DriverManager.getConnection(DB_URL,userName,userPasswd);
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				Statement statement = null;
//				try {
//					statement = connection.createStatement();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				String sql = "INSERT INTO sch(time,text,dayrepet) VALUES('"+time+"','"+text+"','"+check+"')";
//				int result;
//				if(time==""||text==""||check==""){
//					out.println("日程提醒提交失败，请检查您的输入内容是否完整!");
//					response.setHeader("Refresh","3;url=http://115.28.90.164/Weixin/JSP/Schedule reminder.jsp");
//				}
//				else{
//					try {
//						result =  statement.executeUpdate(sql);
//						if(result!=0){
//							Jpush.testSendPush(appKey, masterSecret);
//							response.setContentType("text/html;charset=gbk");
//							 out.println("提交成功!");
//
//							 //response.setHeader("Refresh","1;url=http://115.28.90.164/Weixin/JSP/SchAdd.jsp");
//
//							 connection.close();
//							 statement.close();
//							 //request.getRequestDispatcher("http://115.28.90.164/Weixin/JSP/SchAdd.jsp?user_date="+time+"&schtext="+text+"&schcheck="+check+"").forward(request, response);
//							 response.setHeader("Refresh","3;url=http://115.28.90.164/Weixin/JSP/SchAdd.jsp?user_date="+time+"&schtext="+text+"&schcheck="+check+"");
//						}else {
//							out.println("日程提醒提交失败，请检查您的输入内容是否完整!");
//							response.setHeader("Refresh","1;url=http://115.28.90.164/Weixin/JSP/Schedule reminder.jsp");
//						}
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						out.println("日程提醒提交失败，请检查您的输入内容是否完整!");
//						response.setHeader("Refresh","3;url=http://115.28.90.164/Weixin/JSP/Schedule reminder.jsp");
//					}
//				}
//
	}

}
