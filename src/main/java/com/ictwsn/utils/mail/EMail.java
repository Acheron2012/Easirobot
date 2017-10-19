package com.ictwsn.utils.mail;

import com.ictwsn.utils.timer.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class EMail {

    public static Logger logger = LoggerFactory.getLogger(Job.class);
    private static Transport transport;

    public static final String smtphost = "smtp.163.com"; // 发送邮件服务器
    public static final String user = "ruoranhuang@163.com"; // 邮件服务器登录用户名
    public static final String password = "HeroVagabond2016";  // 邮件服务器登录密码
    public static final String from = "ruoranhuang@163.com"; // 发送人邮件地址

//    public static String oldName = "陈丽娟";

//    阿里云服务器需要打开25端口
    public static void mail(String user_name, List<String> address, String content) {

//        String to[] = new String[3];
//        //子女收件人地址
//        to[0] = "huangruoran@ict.ac.cn";
//        to[1] = "18813124313@163.com";
//        to[2] = "yufang2013@xs.ustb.edu.cn";

//        System.out.println(user_name);
//        System.out.println(address.toString());
//        System.out.println(content);

        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");//设置日期格式
        String date = df.format(new Date());
        String subject = user_name + "-老人语音邮件-" + date; // 邮件标题
        String body = user_name + "的子女：\n您好，\n\n" +
                "\t\t" + "您的老人为您发送语音：" + content
                + "\n\n祝好！\n" + "中科院计算所智能孝子团队";


        //邮件发送
        for (int i = 0; i < address.size(); i++) {
            if (address.get(i) != null && !address.get(i).equals(""))
                System.out.println(address.get(i));
            Send(smtphost, user, password, from, address.get(i), subject, body);
        }
    }

    public static void Send(String smtphost, String user, String password, String from, String to,
                            String subject, String body) {
        try {
            logger.info("开始发送邮件");
            //使用SSL加密的465端口，25端口阿里云无法使用
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            //设置邮件会话参数
            Properties props = new Properties();
            //邮箱的发送服务器地址
            props.setProperty("mail.smtp.host", smtphost);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            //邮箱发送服务器端口,这里设置为465端口
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");

//            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            props.setProperty("mail.smtp.socketFactory.fallback", "false");
//            //邮箱发送服务器端口,这里设置为465端口
//            props.setProperty("mail.smtp.port", "465");
//            props.setProperty("mail.smtp.socketFactory.port", "465");

            Session ssn = Session.getInstance(props, null);
            //获取到邮箱会话,利用匿名内部类的方式,将发送者邮箱用户名和密码授权给jvm
//            Session ssn = Session.getDefaultInstance(props, new Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(user, password);
//                }
//            });
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

    public static void sendEmil(String to, String message) {
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            //设置邮件会话参数
            Properties props = new Properties();
            //邮箱的发送服务器地址
            props.setProperty("mail.smtp.host", smtphost);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            //邮箱发送服务器端口,这里设置为465端口
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            //获取到邮箱会话,利用匿名内部类的方式,将发送者邮箱用户名和密码授权给jvm
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });
            //通过会话,得到一个邮件,用于发送
            Message msg = new MimeMessage(session);
            //设置发件人
            msg.setFrom(new InternetAddress(from));
            //设置收件人,to为收件人,cc为抄送,bcc为密送
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
//            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(to, false));
//            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(to, false));
            msg.setSubject("邮件主题");
            //设置邮件消息
            msg.setText(message);
            //设置发送的日期
            msg.setSentDate(new Date());

            //调用Transport的send方法去发送邮件
            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
//        List<String> add = new ArrayList<String>();
//        add.add("huangruoran@ict.ac.cn");
//        mail("陈丽娟", add, "下班回来记得买菜！");
        sendEmil("huangruoran@ict.ac.cn","我靠");
    }
}
