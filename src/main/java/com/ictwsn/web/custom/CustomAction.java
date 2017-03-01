package com.ictwsn.web.custom;


import com.ictwsn.utils.jpush.Jpush;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


////对应AudiodelServlet

@Controller
@RequestMapping("/web/cust")
public class CustomAction {

    public static Logger logger = LoggerFactory.getLogger(CustomAction.class);

    private static final long serialVersionUID = 1L;
    private static final String appKey = "54642be1084bdd1303610ee1";
    private static final String masterSecret = "be880fb5915b7f581122bdaa";

    @RequestMapping("/sch.do")
    public String SchServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
    {
        //驱动程序名
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String time = request.getParameter("user_date");

        String text = request.getParameter("schtext");

        String check = request.getParameter("schcheck");
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
        //合并字符串
        //String url = "jdbc:mysql://localhost:3306/" + dbName  + "?useSSL=" + true+ "?user="
        //		+ userName + "&password=" + userPasswd;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String sql = "INSERT INTO sch(time,text,dayrepet) VALUES('"+time+"','"+text+"','"+check+"')";
        int result;
        if(time==""||text==""||check==""){
            out.println("日程提醒提交失败，请检查您的输入内容是否完整!");
            response.setHeader("Refresh","3;url=http://115.28.90.164/Weixin/JSP/Schedule reminder.jsp");
        }
        else{
            try {
                result =  statement.executeUpdate(sql);
                if(result!=0){
                    Jpush.testSendPush(appKey, masterSecret);
                    response.setContentType("text/html;charset=gbk");
                    out.println("提交成功!");

                    //response.setHeader("Refresh","1;url=http://115.28.90.164/Weixin/JSP/SchAdd.jsp");

                    connection.close();
                    statement.close();
                    //request.getRequestDispatcher("http://115.28.90.164/Weixin/JSP/SchAdd.jsp?user_date="+time+"&schtext="+text+"&schcheck="+check+"").forward(request, response);
                    response.setHeader("Refresh","3;url=http://115.28.90.164/Weixin/JSP/SchAdd.jsp?user_date="+time+"&schtext="+text+"&schcheck="+check+"");
                }else {
                    out.println("日程提醒提交失败，请检查您的输入内容是否完整!");
                    response.setHeader("Refresh","1;url=http://115.28.90.164/Weixin/JSP/Schedule reminder.jsp");
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                out.println("日程提醒提交失败，请检查您的输入内容是否完整!");
                response.setHeader("Refresh","3;url=http://115.28.90.164/Weixin/JSP/Schedule reminder.jsp");
            }
        }
        return null;
    }

    @RequestMapping("/schdel.do")
    public String SchdelServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
    {
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
        return null;
    }



}
