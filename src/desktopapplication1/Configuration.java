/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;

import java.util.*;
import java.io.*;

/**
 *
 * @author kursat
 */
public class Configuration {

    public Configuration() {
        getFileLocationFromFile();
        readConfiguration();
    }

    public String getPOP3Host() {
        return POP3Host;
    }

    public void setPOP3Host(String POP3Host) {
        this.POP3Host = POP3Host;
    }

    public String getPOP3Port() {
        return POP3Port;
    }

    public void setPOP3Port(String POP3Port) {
        this.POP3Port = POP3Port;
    }

    public String getSMTPHost() {
        return SMTPHost;
    }

    public void setSMTPHost(String SMTPHost) {
        this.SMTPHost = SMTPHost;
    }

    public String getSMTPPort() {
        return SMTPPort;
    }

    public void setSMTPPort(String SMTPPort) {
        this.SMTPPort = SMTPPort;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
        setFileLocationToFile();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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
            //configFile.list(System.out);
        } catch (Exception e) {
            //TODO: Handle Exception.
            System.out.println(e.getMessage());
        }
    }

    public void writeConfiguration () {
        //Not checkhed
        try {
            Properties configFile = new Properties();
            configFile.load(new FileInputStream(getFileLocation()));
            //configFile.list(System.out);
            configFile.put("SMTP_Host", getSMTPHost());
            configFile.put("SMTP_Port", getSMTPPort());
            configFile.put("POP3_Host", getPOP3Host());
            configFile.put("POP3_Port", getPOP3Port());
            configFile.put("From", getFrom());
            configFile.put("Password", getPass());
            FileOutputStream out = new FileOutputStream(getFileLocation());
            configFile.save(out, " properties updated ");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void setFileLocationToFile()
    {   
        //Not Checkhed
        try {
            Properties configFile = new Properties();
            configFile.load(new FileInputStream(permanentFL));
            configFile.put("File", getFileLocation());
            FileOutputStream out = new FileOutputStream(permanentFL);
            configFile.save(out, "/* properties updated /*");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void getFileLocationFromFile()
    {     
        try {
            Properties configFile = new Properties();
            configFile.load(new FileInputStream(permanentFL));
            setFileLocation(configFile.getProperty("File"));
            //configFile.put("File", getFileLocation());
            //FileOutputStream out = new FileOutputStream(permanentFL);
            //configFile.save(out, " properties updated ");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private String SMTPHost;
    private String SMTPPort;
    private String POP3Host;
    private String POP3Port;
    private String from;
    private String pass;
    //It will hold the variable location of settings file
    private String fileLocation;
    final private String permanentFL = "File_MailClient.dat";
    
}
