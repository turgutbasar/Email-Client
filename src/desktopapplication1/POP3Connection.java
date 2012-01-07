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
 * @author TheCodeGuru
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
    
    public void connect()
    throws Exception
    {
        //Create an empty instance of property object and then set values.
        Properties props = new Properties();
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.port", Integer.toString(getPort()));
        props.setProperty("mail.pop3.socketFactory.port", Integer.toString(getPort()));
        url = new URLName("pop3", getHost(), getPort(), "", from, password);
        //Creates URL for connection
        session = Session.getInstance(props, null);
        store = new POP3SSLStore(session, url);
        //Connects to POP3 Server
        store.connect();
    }
    
    public void openFolder(String folderName)
    throws Exception
    {
        //Creates POP3Folder object and ties it to folder
        folder = store.getFolder(folderName);
        if(folder == null)
            throw new Exception("Invalid folder Exception");
        folder.open(Folder.READ_WRITE);
    }

    public void closeFolder()
    throws Exception
    {
        folder.close(true);
    }

    public int getMessageCount()
    throws Exception
    {
        return folder.getMessageCount();
    }

    public int getNewMessageCount()
    throws Exception
    {
        return folder.getNewMessageCount();
    }

    public void disconnect()
    throws Exception
    {
        store.close();
    }

    public void takeAllMessages()
    throws Exception
    {
        msgs = folder.getMessages();
        folder.fetch(msgs, new FetchProfile());
        envelopes = new Envlope[msgs.length];
        for(int i = 0; i < msgs.length; i++)
            envelopes[i] = dumpEnvelope(msgs[i]);
    }
    
    public Envlope[] getEnvlopes() {
        return envelopes;
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
    public void markAsRead(int ID){
        try {
            msgs[ID].setFlag(Flags.Flag.DELETED, true);
        } catch (MessagingException ex) {
            Logger.getLogger(POP3Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteMessages(int[] mailIDs){
        //deletion of chosen ids
        for(int i = 0; i < mailIDs.length; i++)
            deleteEnvelope(mailIDs[i]);
    }
    public void deleteMessage(int mailID){
        //deletion of chosen id
        deleteEnvelope(mailID);
    }
    private void deleteEnvelope(int ID){
        try {
            msgs[ID].setFlag(Flags.Flag.DELETED, true);
        } catch (MessagingException ex) {
            Logger.getLogger(POP3Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static Envlope dumpEnvelope(Message m) throws Exception
    {
        String body="";
        Boolean html = false;
        String folder = "RECIEVED";
        String[] from = new String[m.getFrom().length];
        for ( int i = 0 ; i  < from.length; ++i ){
            from[i] = m.getFrom()[i].toString();
            if ( from[i].contains(DesktopApplication1.config.getFrom())){
                folder = "SENT";
            }
        }
        String[] to = new String[m.getRecipients(Message.RecipientType.TO).length];
        for ( int i = 0 ; i  < to.length; ++i ){
            to[i] = m.getRecipients(Message.RecipientType.TO)[i].toString();
            if ( to[i].contains(DesktopApplication1.config.getFrom()) && folder.compareTo("SENT") == 0){
                folder = "BOTH";
            }
        }
        String[] cc = null;
        if ( m.getRecipients(Message.RecipientType.CC) != null){
            cc = new String[m.getRecipients(Message.RecipientType.CC).length];
            for ( int i = 0 ; i  < cc.length; ++i ){
                cc[i] = m.getRecipients(Message.RecipientType.CC)[i].toString();
            }
        }
        String subject= m.getSubject();
        Date dateofMail= m.getSentDate();
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
                }/* else if ((disposition != null) && (disposition.equals(Part.ATTACHMENT) || disposition.equals(Part.INLINE) || disposition.equals("ATTACHMENT") || disposition.equals("INLINE")) )
                {
                    // Check if plain
                    MimeBodyPart mbp = (MimeBodyPart)part;
                    if (mbp.isMimeType("text/plain")) {
                        body += (String)mbp.getContent();
                    }
                    else if (mbp.isMimeType("TEXT/HTML")) {
                        body += mbp.getContent().toString();
                    }
                }*/
            }
        }
        return new Envlope(subject, body, html, folder, from, cc, to);
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
    private Envlope[] envelopes;
    private Message[] msgs;
    
}
