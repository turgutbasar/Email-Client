/*
 * This class is used for hold info which taken from settings.ini
 * and it holds a permanent file to know location of settings.ini.
 */
package desktopapplication1;

import java.util.*;
import java.io.*;

/**
 *
 * @author kursadyo and TheCodeGuru
 */
public class Configuration {

    public Configuration() {
        getFileLocationFromFile();
        readConfiguration();
    }

    public void readConfiguration () {

        try {
            Properties configFile = new Properties();
            //Taking properties from settings file
            configFile.load(new FileInputStream(getFileLocation()));
            setSMTPHost(configFile.getProperty("SMTP_Host"));
            setSMTPPort(configFile.getProperty("SMTP_Port"));
            setPOP3Host(configFile.getProperty("POP3_Host"));
            setPOP3Port(configFile.getProperty("POP3_Port"));
            setFrom(configFile.getProperty("From"));
            setPass(configFile.getProperty("Password"));
            setName(configFile.getProperty("Name"));
            setMailsLocation(configFile.getProperty("Mails_Location"));
            setContactsLocation(configFile.getProperty("Contacts_Location"));
        } catch (Exception e) {
            System.out.println("Read Exception:" + e.getMessage());
        }
    }

    public void writeConfiguration () {
        try {
            Properties configFile = new Properties();
            configFile.load(new FileInputStream(fileLocation));
            configFile.put("SMTP_Host", SMTPHost);
            configFile.put("SMTP_Port", SMTPPort);
            configFile.put("POP3_Host", POP3Host);
            configFile.put("POP3_Port", POP3Port);
            configFile.put("From", from);
            configFile.put("Password", pass);
            configFile.put("Name", name);
            configFile.put("Mails_Location", mailsLocation);
            configFile.put("Contacts_Location", contactsLocation);
            FileOutputStream out = new FileOutputStream(fileLocation);
            configFile.save(out, "properties updated");
        }
        catch (Exception e) {
            System.out.println("Write Exception:" + e.getMessage());
        }
    }
    
    private void setFileLocationToFile(){   
        try {
            Properties configFile = new Properties();
            configFile.load(new FileInputStream(permanentFL));
            configFile.put("File", fileLocation);
            FileOutputStream out = new FileOutputStream(permanentFL);
            configFile.save(out, "properties updated");
        }
        catch (Exception e) {
            System.out.println("Set File Location To File Exception:" + e.getMessage());
        }
    }
    
    private void getFileLocationFromFile(){     
        try {
            Properties configFile = new Properties();
            configFile.load(new FileInputStream(permanentFL));
            setFileLocation(configFile.getProperty("File"));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
        setFileLocationToFile();
    }

    public String getPOP3Host() {
        return POP3Host;
    }

    public String getPOP3Port() {
        return POP3Port;
    }

    public String getSMTPHost() {
        return SMTPHost;
    }

    public String getSMTPPort() {
        return SMTPPort;
    }

    public String getContactsLocation() {
        return contactsLocation;
    }

    public String getFrom() {
        return from;
    }

    public String getMailsLocation() {
        return mailsLocation;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public void setPOP3Host(String POP3Host) {
        this.POP3Host = POP3Host;
    }

    public void setPOP3Port(String POP3Port) {
        this.POP3Port = POP3Port;
    }

    public void setSMTPHost(String SMTPHost) {
        this.SMTPHost = SMTPHost;
    }

    public void setSMTPPort(String SMTPPort) {
        this.SMTPPort = SMTPPort;
    }

    public void setContactsLocation(String contactsLocation) {
        this.contactsLocation = contactsLocation;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMailsLocation(String mailsLocation) {
        this.mailsLocation = mailsLocation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    private String SMTPHost;
    private String SMTPPort;
    private String POP3Host;
    private String POP3Port;
    private String from;
    private String pass;
    private String name;
    //These will hold the variable location of files
    private String fileLocation;
    private String mailsLocation;
    private String contactsLocation;
    final private String permanentFL = "File_MailClient.dat";

}
