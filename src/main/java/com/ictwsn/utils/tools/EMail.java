package com.ictwsn.utils.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 黄若然
 * function 带附件的邮件发送
 *
 */

public class EMail {
	
	private static Logger logger = LoggerFactory.getLogger(EMail.class);
	private static Transport transport;
	
	public void mail(File f_data,File f_position) {
		 String smtphost = "smtp.163.com"; // 发送邮件服务器
	        String user = "huang@163.com"; // 邮件服务器登录用户名
	        String password = "***";  // 邮件服务器登录密码
	        String from = "***"; // 发送人邮件地址
        
        String to[] = new String [1];
        //发送人地址
        to[0] = "116951732@qq.com";
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
		String date = df.format(new Date());
		
        String subject = ""+date+"**数据"; // 邮件标题
        
        String body = "在此填写邮件内容";
        
        for(int i = 0;i<to.length;i++)
        {
        	Send(smtphost,user,password,from,to[i],subject,body,f_data,f_position);
        }
    }
    public static void Send(String smtphost, String user, String password, String from,  String to, 
			   String subject, String body,File attachment,File f_position)
    {
    	 try {
    		 	logger.info("开始发送邮件");
	            Properties props = new Properties();
	            props.put("mail.smtp.host", smtphost);
	            props.put("mail.smtp.auth","true");
	            Session ssn = Session.getInstance(props, null);

	            MimeMessage message = new MimeMessage(ssn);

	            InternetAddress fromAddress = new InternetAddress(from);
	            message.setFrom(fromAddress);
	            InternetAddress toAddress = new InternetAddress(to);
	            message.addRecipient(Message.RecipientType.TO, toAddress);
	            message.setSubject(subject);
	            //不需要邮件附件时可直接设置它
//	            message.setText(body);
	            
	         // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
	            Multipart multipart = new MimeMultipart();
	            
	            // 添加邮件正文
	            BodyPart contentPart = new MimeBodyPart();
	            contentPart.setContent(body, "text/html;charset=UTF-8");
//	            contentPart.setContent(body, "charset=UTF-8");
	            multipart.addBodyPart(contentPart);
	            // 添加附件的内容
	            if (attachment != null) {
	                BodyPart attachmentBodyPart = new MimeBodyPart();
	                DataSource source = new FileDataSource(attachment);
	                attachmentBodyPart.setDataHandler(new DataHandler(source));
	                
	                // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
	                // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
	                //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
	                //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
	                
	                //MimeUtility.encodeWord可以避免文件名乱码
	                attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
	                multipart.addBodyPart(attachmentBodyPart);
	            }
	            // 添加附件的内容2
	            if (f_position != null) {
	                BodyPart attachmentBodyPart = new MimeBodyPart();
	                DataSource source = new FileDataSource(f_position);
	                attachmentBodyPart.setDataHandler(new DataHandler(source));
	                
	                // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
	                // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
	                //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
	                //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
	                
	                //MimeUtility.encodeWord可以避免文件名乱码
	                attachmentBodyPart.setFileName(MimeUtility.encodeWord(f_position.getName()));
	                multipart.addBodyPart(attachmentBodyPart);
	            }
	           
	            // 将multipart对象放到message中
	            message.setContent(multipart);
	            // 保存邮件
	            message.saveChanges();
	             
	            transport = ssn.getTransport("smtp");
	            transport.connect(smtphost, user, password);
	            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
	            //transport.send(message);
	            transport.close();
	            logger.info("邮件发送完成");
	            
	        } catch(Exception m) {
	            m.printStackTrace();
	        }finally {
	            if (transport != null) {
	                    try {
							transport.close();
						} catch (MessagingException e) {
							e.printStackTrace();
						}
	            }
	        }
    }
    
    
    public static void main(String[] args) {
    	//获取临时文件夹位置
    	String path = Tools.getConfigureValue("demoPath");
	}
}
