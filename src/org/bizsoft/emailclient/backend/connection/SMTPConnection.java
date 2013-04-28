/*
 * This class implements SMTP connection
 * from Server abstract class.
 */
package desktopapplication1;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 *
 * @author kursat and TheCodeGuru
 */
public class SMTPConnection extends Server{

    public SMTPConnection(String hostname, int port, String from, String password) {
        super(hostname, port);
        this.from = from;
        this.password = password;
    }
    
    public void sendMail (String subject, String text, String[] to, String [] cc) {
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.host", this.getHost());
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", this.getPort());            
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
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
            transport.connect(getHost(), getPort(), from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            System.out.println( "SendMail Exception:" + e.getMessage());
        }
    }
    
    public String getFrom() {
        return from;
    }

    public String getPassword() {
        return password;
    }
    
    private String from;
    private String password;
    
}
