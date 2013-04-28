/*
 * This class implements SMTP connection
 * from AbstractConnection abstract class.
 */
package org.bizsoft.emailclient.backend.connection;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 *
 * @author kursat and turgutbasar
 */

public class SMTPConnection extends AbstractConnection{

    public SMTPConnection(String hostname, int port, String user, String password) {
        super(hostname, port, user, password);
    }
    
    public void sendMail(String subject, String text, String[] to, String [] cc) {
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.host", mHost);
            props.put("mail.smtp.user", mUserMail);
            props.put("mail.smtp.password", mPassword);
            props.put("mail.smtp.port", mPort);            
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mUserMail));
            // To get the array of TO addresses
            InternetAddress[] toAddresses = new InternetAddress[to.length];
            for( int i=0; i < to.length; i++ ) { 
                toAddresses[i] = new InternetAddress(to[i]);
            }
            for( int i=0; i < toAddresses.length; i++) { 
                message.addRecipient(Message.RecipientType.TO, toAddresses[i]);                
            }
            // To get the array CC addresses
            if (cc[0].contains("@")) {
                InternetAddress[] ccAddresses = new InternetAddress[cc.length];
                for (int i = 0; i < cc.length; i++) {                    
                    ccAddresses[i] = new InternetAddress(cc[i]);
                }  
                for (int i = 0; i < ccAddresses.length; i++) {                    
                    message.addRecipient(Message.RecipientType.CC, ccAddresses[i]);                    
                }
            }
            message.setSubject(subject);
            message.setText(text);
            Transport transport = session.getTransport("smtp");
            transport.connect(getHost(), getPort(), mUserMail, mPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            System.out.println( "SendMail Exception:" + e.getMessage());
        }
    }

}
