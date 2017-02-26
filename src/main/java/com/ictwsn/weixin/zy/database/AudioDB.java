package com.ictwsn.weixin.zy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class AudioDB {
	// JDBC驱动名及数据库 URL
				static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
				static final String DB_URL = "jdbc:mysql://localhost:3306/audiopath?useSSL=true";
				static final String filepath = "/opt/tomcat9/webapps/Downloadfiles/data/";
				// 数据库的用户名与密码，需要根据自己的设置
				static final String USER = "root";
				static final String PASS = "5362191";
				static public  boolean updateDB(int audioid) {
					Connection conn = null;
					Statement stmt = null;
					Statement stmt1 = null;
					try{
						// 注册 JDBC 驱动
						Class.forName("com.mysql.jdbc.Driver");
					
						// 打开链接
						//System.out.println("连接数据库...");
						conn = DriverManager.getConnection(DB_URL,USER,PASS);
					
						//执行查询
						//System.out.println(" 实例化Statement��...");
						stmt = conn.createStatement();
						stmt1= conn.createStatement();
						String sql;
						//要进行一次判断，上传的语音id是否存在，存在则应复习updat
						sql = "select 1 from audiopath where id = "+audioid+" limit 1 ";
						ResultSet rs1 = stmt.executeQuery(sql);
						//如果存在，则更新
						if(rs1.next()){
							String sql2 = " update audiopath set path='"+filepath+"' where id="+audioid+"";
							stmt1.executeUpdate(sql2);
						}else{//不存在，则插入
							String sql3 = "INSERT INTO audiopath (id,path) VALUES("+audioid+",'"+filepath+"')";
							stmt1.executeUpdate(sql3);
						}
						// 完成后关闭
						rs1.close();
						stmt.close();
						stmt1.close();
						conn.close();
						return true;
					}catch(SQLException se){
						// 处理 JDBC 错误
						se.printStackTrace();
						return false;
					}catch(Exception e){
						// 处理 Class.forName 错误
						e.printStackTrace();
						return false;
					}finally{
						// 关闭资源
						try{
							if(stmt!=null) stmt.close();
						}catch(SQLException se2){
						}// 什么都不做
						try{
							if(conn!=null) conn.close();
						}catch(SQLException se){
							se.printStackTrace();
						}
					}
			}
}
