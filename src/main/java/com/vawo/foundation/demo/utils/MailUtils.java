package com.vawo.foundation.demo.utils;

import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Properties;

@Component
public class MailUtils {
    private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);
    private final static String AUTH_CODE = "zrjwlrloctzbbhfi";
    private final static String MAIL_ADDR = "phuan520@qq.com";

    @Scheduled(cron = "0 * * * * ?")
    public void readMail() {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory"; //ssl加密,jdk1.8无法使用

        // 定义连接imap服务器的属性信息
        String port = "993";
        String imapServer = "imap.qq.com";
        String protocol = "imap";
        String username = MAIL_ADDR;
        String password = AUTH_CODE; // QQ邮箱的授权码

        //有些参数可能不需要
        Properties props = new Properties();
        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.transport.protocol", protocol); // 使用的协议
        props.setProperty("mail.imap.port", port);
        props.setProperty("mail.imap.socketFactory.port", port);

        // 获取连接
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);

        // 获取Store对象
        Store store = null;
        try {
            store = session.getStore(protocol);
            store.connect(imapServer, username, password); // 登陆认证

            // 通过imap协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
            Folder folder = store.getFolder("INBOX");// 获得用户的邮件帐户
            folder.open(Folder.READ_WRITE); // 设置对邮件帐户的访问权限

            int n = folder.getUnreadMessageCount();// 得到未读数量
            logger.info("[Info] >>>>> read ip email: " + n);

            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false); // false代表未读，true代表已读
            Message messages[] = folder.search(ft);
            for (Message message : messages) {
                String subject = message.getSubject();// 获得邮件主题
                Address from = message.getFrom()[0];// 获得发送者地址
                if (StringUtils.equals("ip", subject) || StringUtils.equals("Re: IP Address", subject)) {
                    System.out.println("邮件的主题为: " + subject);
                    System.out.println("发件人地址为: " + decodeText(from.toString()));
                    System.out.println("日期:" + message.getSentDate());
                    send(IpUtils.publicip());
                    //imap读取后邮件状态会变为已读,设为未读
                    message.setFlag(Flags.Flag.SEEN, true);
                } else {
                    //imap读取后邮件状态会变为已读,设为未读
                    message.setFlag(Flags.Flag.SEEN, false);
                }
            }
            folder.close(false);// 关闭邮件夹对象
            store.close(); // 关闭连接对象
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }

    private String decodeText(String text) throws UnsupportedEncodingException {
        if (text == null)
            return null;
        if (text.startsWith("=?GB") || text.startsWith("=?gb"))
            text = MimeUtility.decodeText(text);
        else
            text = new String(text.getBytes("ISO8859_1"));
        return text;
    }

    private void send(String ip) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "false");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress(MAIL_ADDR));
        // 设置收件人邮箱地址
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(MAIL_ADDR)});
        //message.setRecipient(Message.RecipientType.TO, new InternetAddress("xxx@qq.com"));//一个收件人
        // 设置邮件标题
        message.setSubject("IP Address");
        // 设置邮件内容
        String text = "http://%s:9999";
        message.setText(String.format(text, ip));
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect(MAIL_ADDR, AUTH_CODE);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}