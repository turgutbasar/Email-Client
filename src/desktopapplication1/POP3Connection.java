/*
 * This Class is used to create an POP3Connection
 * and implement some utility for POP3(take messages, delete message, get mails 
 * count, get new mails count)
 */
package desktopapplication1;

import com.sun.mail.pop3.POP3SSLStore;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;

/**
 *
 * @author kursat and TheCodeGuru
 */
public class POP3Connection extends Server{

    public POP3Connection(String hostname, int port, String from, String password) {
        //To fill properties with default values
        super(hostname, port);
        this.from = from;
        this.password = password;
        session = null;
        store = null;
        msgs = null;
    }
    
    public void connect() throws Exception {
        //Create an empty instance of property object and then set values.
        Properties props = new Properties();
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.port", Integer.toString(getPort()));
        props.setProperty("mail.pop3.socketFactory.port", Integer.toString(getPort()));
        //Creates URL for connection
        url = new URLName("pop3", getHost(), getPort(), "", from, password);
        //Creates session
        session = Session.getInstance(props, null);
        store = new POP3SSLStore(session, url);
        //Connects to POP3 Server
        store.connect();
    }
    
    public void openFolder(String folderName) throws Exception {
        //Creates Folder object and ties it to folder
        folder = store.getFolder(folderName);
        if(folder == null)
            throw new Exception("Invalid folder Exception");
        //Opens folder for read-write
        folder.open(Folder.READ_WRITE);
        //Just take handles of messages, NOT WHOLE MESSAGE!
        msgs = folder.getMessages();
    }

    public void closeFolder() throws Exception {
        folder.close(true);
    }

    public int getMessageCount() throws Exception {
        return folder.getMessageCount();
    }

    public int getNewMessageCount() throws Exception {
        return folder.getNewMessageCount();
    }

    public void disconnect() throws Exception {
        store.close();
    }

    public void takeMessages(Date time) throws Exception {
        folder.fetch(msgs, new FetchProfile());
        //Envelope creation
        envelopes = new Envelope[msgs.length];
        for(int i = 0; i < msgs.length; i++)
            envelopes[i] = convertEnvelope(msgs[i], time);
    }
    
    /*public static int saveFile(File saveFile, Part part) throws Exception {

        BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(saveFile) );

        byte[] buff = new byte[2048];
        InputStream is = part.getInputStream();
        int ret = 0, count = 0;
        while( (ret = is.read(buff)) > 0 ){
            bos.write(buff, 0, ret);
            count += ret;
        }
        bos.close();
        is.close();
        return count;
    }*/
    
    /*public void markAsRead(int ID){
        try {
            msgs[ID].setFlag(Flags.Flag.SEEN, true);
        } catch (MessagingException ex) {
            Logger.getLogger(POP3Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    public void deleteMessages(int[] mailIDs){
        //deletion of chosen ids
        for(int i = 0; i < mailIDs.length; i++)
            deleteMessage(mailIDs[i]);
    }
    
    public void deleteMessage(int mailID){
        //deletion of chosen id
        try {
            msgs[mailID].setFlag(Flags.Flag.DELETED, true);
        } catch (MessagingException ex) {
            Logger.getLogger(POP3Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static Envelope convertEnvelope(Message m, Date time) throws Exception {
        //Body variable which holds body of mail
        String body="";
        //Useless
        //Is HTML variable
        Boolean html = false;
        //Folder varible which holds folder info of envelope
        //Fist variable initialization for recieved mail folder
        String folder = "RECIEVED";
        //From variable initialization and set folder info
        String[] from = new String[m.getFrom().length];
        for ( int i = 0 ; i  < from.length; ++i ){
            from[i] = m.getFrom()[i].toString();
            if ( from[i].contains(DesktopApplication1.config.getFrom()) ){
                folder = "SENT";
            }
        }
        //To variable initialization and set folder info
        String[] to = new String[m.getRecipients(Message.RecipientType.TO).length];
        for ( int i = 0 ; i  < to.length; ++i ){
            to[i] = m.getRecipients(Message.RecipientType.TO)[i].toString();
            if ( to[i].contains(DesktopApplication1.config.getFrom()) && folder.compareTo("SENT") == 0){
                folder = "BOTH";
            }
        }
        //CC variable initialization and set folder info
        String[] cc = null;
        if ( m.getRecipients(Message.RecipientType.CC) != null){
            cc = new String[m.getRecipients(Message.RecipientType.CC).length];
            for ( int i = 0 ; i  < cc.length; ++i ){
                cc[i] = m.getRecipients(Message.RecipientType.CC)[i].toString();
                if ( cc[i].contains(DesktopApplication1.config.getFrom()) && folder.compareTo("SENT") == 0)
                    folder = "BOTH";
            }
        }
        //Subject initialization
        String subject= m.getSubject();
        //Date initialization 
        Date dateofMail= m.getSentDate();
        //To return null because this mail will be handled after this process
        if ( time.after(dateofMail) )
            return null;
        //To take whole mail
        Object content = m.getContent();
        //If instance of mail content is Just String
        if(content instanceof String){
            body = (String)content;
        }
        else if(content instanceof Multipart)
        {
            //Multipart content
            Multipart mp = (Multipart)content;
            for (int j=0; j < mp.getCount(); j++)
            {
                //For selected part
                Part part = mp.getBodyPart(j);
                //Disposition of part
                String disposition = part.getDisposition();
                if (disposition == null) {
                    // Check if plain or html
                    MimeBodyPart mbp = (MimeBodyPart)part;
                    if (mbp.isMimeType("text/plain")) {
                        body += mbp.getContent().toString();
                    }
                    else if (mbp.isMimeType("TEXT/HTML")) {
                        body += mbp.getContent().toString();
                    }
                }
            }
        }
        return new Envelope(subject, body, html, folder, from, cc, to);
    }
    
    public Envelope[] getEnvlopes() {
        return envelopes;
    }
    
    private Session session;
    private POP3SSLStore store;
    private String password;
    private Folder folder;
    /*public static String numberOfFiles = null;
    public static int toCheck = 0;
    public static Writer output = null;*/
    URLName url;
    /*public static String receiving_attachments="D:\\download";*/
    private String from;
    private Envelope[] envelopes;
    private Message[] msgs;
    
}
