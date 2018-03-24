package cn.ouctechnology.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

public class MailUtil extends Thread {

    private static String host;
    private static String user;
    private static String pwd;
    private static String from;
    private static String to;
    private static String subject;
    private static String content;

    static {
        Properties p = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            p.load(classLoader.getResourceAsStream("mail.properties"));
            host = p.getProperty("mailHost");
            user = p.getProperty("mailUsername");
            pwd = p.getProperty("mailPassword");
            from = p.getProperty("mailFrom");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void sendMail(String to, String subject, String content) throws MessagingException {
        MailUtil.to = to;
        MailUtil.subject = subject;
        MailUtil.content = content;
        new MailUtil().start();
    }

    public void run() {
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            //设置邮件会话参数
            Properties props = new Properties();
            //邮箱的发送服务器地址
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            //邮箱发送服务器端口,这里设置为465端口
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            final String username = user;
            final String password = pwd;
            //获取到邮箱会话,利用匿名内部类的方式,将发送者邮箱用户名和密码授权给jvm
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            //通过会话,得到一个邮件,用于发送
            Message msg = new MimeMessage(session);
            //设置发件人
            msg.setFrom(new InternetAddress(from));
            //设置收件人,to为收件人,cc为抄送,bcc为密送
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(to, false));
            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(to, false));
            msg.setSubject(subject);
            //设置邮件消息
            msg.setText(content);
            //设置发送的日期
            msg.setSentDate(new Date());

            //调用Transport的send方法去发送邮件
            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

