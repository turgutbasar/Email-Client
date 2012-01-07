/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;

import com.sun.mail.pop3.POP3SSLStore;
import com.sun.mail.pop3.POP3Folder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import java.util.*;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import org.jdesktop.application.Application;

/**
 *
 * @author kursat
 */
public class POP3Connection extends Server{

    public POP3Connection(String hostname, int port, String from, String password) {
        super(hostname, port);
        this.from = from;
        this.password = password;
        connect();
        takeMessages();
    }
    
    public void connect(){
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties pop3Props = new Properties();
        pop3Props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
        pop3Props.setProperty("mail.pop3.port", "995");
        pop3Props.setProperty("mail.pop3.socketFactory.port", "995");
        URLName url = new URLName("pop3", "pop.gmail.com", 995, "", "turgutbashar@gmail.com", "123asd123");
        session = Session.getInstance(pop3Props, null);
        store = new POP3SSLStore(session, url);
        try {
            store.connect();
        } catch (MessagingException ex) {
            Logger.getLogger(POP3Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
                try {
            folder = (POP3Folder)store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message msgs[] = folder.getMessages();
            FetchProfile fp = new FetchProfile();
            folder.fetch(msgs, fp);
            
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }
    public void takeMessages()
    {

    }
    public void disconnect(){
        try {
            folder.close(false);
            store.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(POP3Folder folder) {
        this.folder = folder;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(POP3SSLStore store) {
        this.store = store;
    }

    public Message getMessages() {
        return messages;
    }

    public void setMessages(Message messages) {
        this.messages = messages;
    }
    private Message messages;
    private POP3SSLStore store;
    private POP3Folder folder;
    private String from;
    private String password;
    public Session session;
    
}
