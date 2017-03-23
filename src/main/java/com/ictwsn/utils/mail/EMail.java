package com.ictwsn.utils.mail;

import com.ictwsn.utils.timer.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class EMail {

    public static Logger logger = LoggerFactory.getLogger(Job.class);
    private static Transport transport;

    public static final String smtphost = "smtp.163.com"; // 发送邮件服务器
    public static final String user = "ruoranhuang@163.com"; // 邮件服务器登录用户名
    public static final String password = "HeroVagabond2016";  // 邮件服务器登录密码
    public static final String from = "ruoranhuang@163.com"; // 发送人邮件地址

    public static String oldName = "chenlijuan";

    public static void mail(String content) {
        String to[] = new String[1];
        //子女收件人地址
        to[0] = "huangruoran@ict.ac.cn";
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");//设置日期格式
        String date = df.format(new Date());
        String subject = oldName + "-老人语音邮件-" + date; // 邮件标题
        String body = oldName + "的子女：您好，\n\n" +
                "\t\t" + "您的老人为您发送语音：" + content
                + "\n\n祝好！\n" + "中科院计算所智能孝子团队";
        //邮件发送
        for (int i = 0; i < to.length; i++) {
            Send(smtphost, user, password, from, to[i], subject, body);
        }
    }

    public static void Send(String smtphost, String user, String password, String from, String to,
                            String subject, String body) {
        try {
            logger.info("开始发送邮件");
            Properties props = new Properties();
            props.put("mail.smtp.host", smtphost);
            props.put("mail.smtp.auth", "true");
            Session ssn = Session.getInstance(props, null);
            MimeMessage message = new MimeMessage(ssn);
            InternetAddress fromAddress = new InternetAddress(from);
            message.setFrom(fromAddress);
            InternetAddress toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            //不需要邮件附件时可直接设置它
            message.setText(body);

//            File f = new File("");
//            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
//            Multipart multipart = new MimeMultipart();
//            // 添加邮件正文
//            BodyPart contentPart = new MimeBodyPart();
//            contentPart.setContent(body, "text/html;charset=UTF-8");
//            multipart.addBodyPart(contentPart);
//            // 添加附件的内容2
//            BodyPart attachmentBodyPart = new MimeBodyPart();
//            DataSource source = new FileDataSource(f);
//            attachmentBodyPart.setDataHandler(new DataHandler(source));
//            // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
//            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
//            //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
//            //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
//            //MimeUtility.encodeWord可以避免文件名乱码
//            attachmentBodyPart.setFileName(MimeUtility.encodeWord(f.getName()));
//            multipart.addBodyPart(attachmentBodyPart);
//            // 将multipart对象放到message中
//            message.setContent(multipart);
//            // 保存邮件
//            message.saveChanges();

            transport = ssn.getTransport("smtp");
            transport.connect(smtphost, user, password);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            //transport.send(message);
            transport.close();
            logger.info("邮件发送完成");

        } catch (Exception m) {
            m.printStackTrace();
        } finally {
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
        mail("下班回来记得买菜！");
    }
}
