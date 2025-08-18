package com.app.kyc.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class EmailUtil
{
   public static void sendEmail(String toEmail, String subject, String body)
   {
      try
      {
         final String username = "KYC-dev@itmaxglobal.com";
         final String password = "tMaRLvDfSyxp5hJC";
         Properties props = new Properties();
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.starttls.enable", "true");
         props.put("mail.smtp.host", "mail.smtp2go.com");
         props.put("mail.smtp.port", "2525");
         Session session = Session.getInstance(props, new javax.mail.Authenticator()
         {
            protected PasswordAuthentication getPasswordAuthentication()
            {
               return new PasswordAuthentication(username, password);
            }
         });
         MimeMessage msg = new MimeMessage(session);
         msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
         msg.addHeader("format", "flowed");
         msg.addHeader("Content-Transfer-Encoding", "8bit");
         msg.setFrom(new InternetAddress("KYC-dev@itmaxglobal.com", "NoReply-JD"));
         msg.setReplyTo(InternetAddress.parse("KYC-dev@itmaxglobal.com", false));
         msg.setSubject(subject, "UTF-8");
         msg.setText(body, "UTF-8");
         msg.setSentDate(new Date());
         msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
         Transport.send(msg);
      }
      catch(Exception e)
      {
         e.getMessage();
      }
   }
}
