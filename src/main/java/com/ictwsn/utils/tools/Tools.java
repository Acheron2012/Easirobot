package com.ictwsn.utils.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

@SuppressWarnings("restriction")
public class Tools {
	
	private static Logger logger = LoggerFactory.getLogger(Tools.class);
	
	//MD5加密
	public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		 //确定计算方法
		  MessageDigest md5=MessageDigest.getInstance("MD5");
		  BASE64Encoder base64en = new BASE64Encoder();
		 //加密后的字符串
		  String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
		  return newstr;
	}
	//SHA加密
	 public static String EncrypSHA(String info) throws NoSuchAlgorithmException{  
	        MessageDigest sha = MessageDigest.getInstance("SHA");  
	        byte[] srcBytes = info.getBytes();  
	        //使用srcBytes更新摘要  
	        sha.update(srcBytes);  
	        //完成哈希计算，得到result  
	        byte[] resultBytes = sha.digest();
	        return resultBytes.toString();  
	   }  
	//用于消除Mongodb中8个小时的时间误差
	public static Date changeToLocalChina(Date datetime)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(datetime);
		calendar.add(Calendar.HOUR, 8);
		datetime = calendar.getTime();
		return datetime;
	}
	//Date转为String
	public static String dateToString(Date datetime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(datetime);
	}
	//String转为Date
	public static Date stringToDate(String datetime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	//数字时间转为汉字时间
	public static String stringToChineseTime(String datetime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时");
			return formatter.format(sdf.parse(datetime));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	//数字日期转为汉字日期
	public static String stringToChineseDate(String datetime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
			return formatter.format(sdf.parse(datetime));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	//把字符串转化为东8区世家
	public static Date changeToDate8(String timestamp)
	{
		if(timestamp==null||timestamp.equals("")||timestamp.equals("null")) return null;
		timestamp = timestamp.replace("T", " ");//将里面的T替换成空格
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(timestamp);
			date = changeToLocalChina(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	//得到当前的东8区时间
	public static Date getNowDate8()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	//Properties操作
	public static String getConfigureValue(String key)
	{
		Properties property = new Properties();
		InputStream in = Tools.class.getResourceAsStream("/system.properties");
		if(in==null) logger.info("未读取到配置文件");
		try {
			property.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return property.getProperty(key);
	}

	//创建文件夹
	public static void createdDirectory(String path)
	{
		File file = new File(path);
		if(!file.exists()&&!file.isDirectory())
		{
			file.mkdirs();
			logger.info("{}不存在，已创建",path);
		}
	}


	//返回下载文件流
	public static void downloadAudioFile(InputStream inputStream, HttpServletResponse response) {
		response.setContentType("application/x-msdownload");

		byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(inputStream);
			OutputStream os = response.getOutputStream();
			int i = bis.read(buffer);
			while (i != -1) {
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}



	public static void main(String[] args) {
		/*try {
			System.out.println(EncrypSHA("easicloudjljajdfio324jll38hl3"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sdf.format(new Date()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}*/


//		System.out.println(getNowDate8());
	}
	
}
