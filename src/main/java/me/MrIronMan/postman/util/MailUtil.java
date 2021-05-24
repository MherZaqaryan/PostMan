package me.MrIronMan.postman.util;

import me.MrIronMan.postman.PostMan;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;

import org.bukkit.configuration.file.FileConfiguration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import java.util.regex.Pattern;

public class MailUtil {

    private static final FileConfiguration config = PostMan.getConfiguration().getConfig();
    private static final String host = config.getString("Mail-Server.Host");
    private static final String port = config.getString("Mail-Server.Port");
    private static final String user = config.getString("Email-Account.Email");
    private static final String password = config.getString("Email-Account.Password");

    private static final Pattern Email = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
    }

    public static void send(String player, String email, String fileName) {
        Bukkit.getScheduler().runTaskAsynchronously(PostMan.getInstance(), () -> {
            Session session = createSession();
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject(subject(fileName));
                StringWriter writer = new StringWriter();
                IOUtils.copy(new FileInputStream(HtmlUtil.getHtmlFile(fileName)), writer);
                message.setContent(writer.toString().replace("%email%", email).replace("%player%", player), "text/html; charset=utf-8");
                Transport.send(message);

            } catch (MessagingException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void sendVerification(String player, String email, String code) {
        String fileName = "Verify.html";
        Bukkit.getScheduler().runTaskAsynchronously(PostMan.getInstance(), () -> {
            Session session = createSession();
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject(subject(fileName));
                StringWriter writer = new StringWriter();
                IOUtils.copy(new FileInputStream(HtmlUtil.getHtmlFile(fileName)), writer);
                message.setContent(writer.toString().replace("%code%", code).replace("%email%", email).replace("%player%", player), "text/html; charset=utf-8");
                Transport.send(message);

            } catch (MessagingException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static boolean check(String email) {
        if (email == null) {
            return false;
        }
        return Email.matcher(email).matches();
    }

    private static String subject(String mail) {
        if (PostMan.getInstance().getSQLData().getSubject(mail) == null) {
            return "Unknown Subject";
        }else {
            return PostMan.getInstance().getSQLData().getSubject(mail);
        }
    }

}