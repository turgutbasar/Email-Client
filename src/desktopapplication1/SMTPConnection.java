/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 *
 * @author kursat
 */
public class SMTPConnection extends Server{

    public SMTPConnection(String hostname, int port, String from, String password) {
        super(hostname, port);
        this.from = from;
        this.password = password;
    }
    
    public void sendMail (String subject, String text, String[] to, String [] cc) {
        try {
            
            System.out.println(to);
            
            String host = this.getHost();
            String from = this.getFrom();
            String pass = this.getPassword();
            int port = this.getPort();
            
            Properties props = System.getProperties();
            
            props.put("mail.smtp.host", this.getHost());
            props.put("mail.smtp.user", this.getFrom());
            props.put("mail.smtp.password", this.getPassword());
            //587 worked for gmail ////465
            props.put("mail.smtp.port", this.getPort()); 
            
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.getFrom()));

            
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
                System.out.println("CC ye girdi amık");
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
            transport.connect(host, port, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (Exception e) {
            System.out.println( "SendMail Exception:" + e.getMessage());
        }
    }
    
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    private String from;
    private String password;
    
}
