package com.ictwsn.web.wechat.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AudiodelServlet
 */
@WebServlet({ "/AudiodelServlet" })
public class AudiodelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AudiodelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		//驱动程序名
//				String driverName = "com.mysql.jdbc.Driver";
//		  	    String DB_URL = "jdbc:mysql://localhost:3306/audiopath?useSSL=true";
//				//数据库用户名
//				String userName = "root";
//				//密码
//				String userPasswd = "5362191";
//				//数据库名
//				String dbName = "audiopath";
//				//表名
//				String tableName = "audiopath";
//				//联结字符串
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
//				Statement st1 = null;
//				try {
//					st1 = connection.createStatement();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				Statement st2 = null;
//				try {
//					st2 = connection.createStatement();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				String audioId = request.getParameter("Remove");
//		  		if (audioId != null) {
//
//		  		//先删除相应语音文件
//		  			String sqldelfile = "SELECT * FROM audiopath WHERE id="+audioId+" limit 1 ";
//		  			ResultSet rs2 = null;
//					try {
//						rs2 = st2.executeQuery(sqldelfile);
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		  			try {
//						if(rs2.next()){
//						    String filepath = rs2.getString("path");
//							//String filepath = "/opt/tomcat9/webapps/Downloadfiles/data/";
//							String filepathmp3 = filepath + audioId+".mp3";
//							String filepathamr = filepath + audioId+".amr";
//							File file1 =  new File(filepathmp3);
//							if(file1.isFile()&&file1.exists()){
//								file1.delete();
//							}
//							File file2 =  new File(filepathamr);
//							if(file2.isFile()&&file2.exists()){
//								file2.delete();
//							}
//						}
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//
//		  			String sqldel = "DELETE FROM audiopath" + " WHERE id =" +audioId+ ""  ;
//		  			try {
//						st1.executeUpdate(sqldel);
//						Jpush.audioPush(Jpush.appKey,Jpush.masterSecret);
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//		  			try {
//						connection.close();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		  			try {
//						st1.close();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		  			try {
//						st2.close();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		  			try {
//						rs2.close();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//		  			response.setHeader("Refresh","1;url=http://115.28.90.164/Weixin/JSP/AudioDel.jsp");
//
//	}
	}
}
