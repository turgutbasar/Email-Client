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
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;

/**
 *
 * @author kursat
 */
public class POP3Connection extends Server{

    public POP3Connection(String hostname, int port, String from, String password) {
        //To fill properties with default values
        super(hostname, port);
        this.from = from;
        this.password = password;
        session = null;
        store = null;
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
        folder = (POP3Folder)store.getFolder(folderName);
        if(folder == null)
            throw new Exception("Invalid folder Exception");
        folder.open(2);
    }

    public void closeFolder()
    throws Exception
    {
        folder.close(false);
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

    public void printAllMessages()
    throws Exception
    {
        Message msgs[] = folder.getMessages();
        folder.fetch(msgs, new FetchProfile());
        for(int i = 0; i < msgs.length; i++)
            dumpEnvelope(msgs[i]);

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

    private static void dumpEnvelope(Message m) throws Exception
    {
        String body="";
        int size=0;
        
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
                } else if ((disposition != null) && (disposition.equals(Part.ATTACHMENT) || disposition.equals(Part.INLINE) || disposition.equals("ATTACHMENT") || disposition.equals("INLINE")) )
                {
                    // Check if plain
                    MimeBodyPart mbp = (MimeBodyPart)part;
                    if (mbp.isMimeType("text/plain")) {
                        body += (String)mbp.getContent();
                    }
                    else if (mbp.isMimeType("TEXT/HTML")) {
                        body += mbp.getContent().toString();
                    }
                }
            }
        }

    }
    private Session session;
    private POP3SSLStore store;
    private String password;
    private POP3Folder folder;
    /*public static String numberOfFiles = null;
    public static int toCheck = 0;
    public static Writer output = null;*/
    URLName url;
    /*public static String receiving_attachments="D:\\download";*/
    private String from;
    
}
