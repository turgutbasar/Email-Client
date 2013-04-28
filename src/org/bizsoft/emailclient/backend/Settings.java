/*
 * This class is used for hold info which taken from settings.ini.
 * This class is an implementation of IPersistable interface.
 */

package org.bizsoft.emailclient.backend;

import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.bizsoft.emailclient.backend.persistance.IPersistable;

/**
 *
 * @author kursadyo and turgutbasar
 */

public final class Settings implements IPersistable {

    private String mSMTPHost;
    private String mSMTPPort;
    private String mPOP3Host;
    private String mPOP3Port;
    private String mFrom;
    private String mPass;
    private String mName;
    private String mMailsLocation;
    private String mContactsLocation;
    private Date mLastUpdate;
    private final String settingsFile = "settings.ini"; 
    
    public Settings() {
    }

    public boolean read() {
        try {
            Properties configFile = new Properties();
            //Taking properties from settings file
            configFile.load(new FileInputStream(settingsFile));
            mSMTPHost = configFile.getProperty("SMTP_Host");
            mSMTPPort = configFile.getProperty("SMTP_Port");
            mPOP3Host = configFile.getProperty("POP3_Host");
            mPOP3Port = configFile.getProperty("POP3_Port");
            mFrom = configFile.getProperty("From");
            mPass = configFile.getProperty("Password");
            mName = configFile.getProperty("Name");
            mMailsLocation = configFile.getProperty("Mails_Location");
            mContactsLocation = configFile.getProperty("Contacts_Location");
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd-HH-mm-ss");
            mLastUpdate = df.parse(configFile.getProperty("Last_Update"));
        } catch (Exception e) {
            System.out.println("Read Exception:" + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean write() {
        try {
            Properties configFile = new Properties();
            configFile.load(new FileInputStream(settingsFile));
            configFile.put("SMTP_Host", mSMTPHost);
            configFile.put("SMTP_Port", mSMTPPort);
            configFile.put("POP3_Host", mPOP3Host);
            configFile.put("POP3_Port", mPOP3Port);
            configFile.put("From", mFrom);
            configFile.put("Password", mPass);
            configFile.put("Name", mName);
            configFile.put("Mails_Location", mMailsLocation);
            configFile.put("Contacts_Location", mContactsLocation);
            // TODO : There are some wrong and deprecated usage of date formatting, fix it.
            configFile.put("Last_Update", Integer.toString(mLastUpdate.getYear()+1900)+"/"+Integer.toString(mLastUpdate.getMonth())+"/"+Integer.toString(mLastUpdate.getDay())+"-"+Integer.toString(mLastUpdate.getHours())+"-"+Integer.toString(mLastUpdate.getMinutes())+"-"+Integer.toString(mLastUpdate.getSeconds()));
            FileOutputStream out = new FileOutputStream(settingsFile);
            // TODO : There is a usage of ini save method that deprecated.
            configFile.save(out, "properties updated");
        }
        catch (Exception e) {
            System.out.println("Write Exception:" + e.getMessage());
            return false;
        }
        return true;
    }

    public String getSMTPHost() {
        return mSMTPHost;
    }

    public void setSMTPHost(String SMTPHost) {
        this.mSMTPHost = SMTPHost;
    }

    public String getSMTPPort() {
        return mSMTPPort;
    }

    public void setSMTPPort(String SMTPPort) {
        this.mSMTPPort = SMTPPort;
    }

    public String getPOP3Host() {
        return mPOP3Host;
    }

    public void setPOP3Host(String POP3Host) {
        this.mPOP3Host = POP3Host;
    }

    public String getPOP3Port() {
        return mPOP3Port;
    }

    public void setPOP3Port(String POP3Port) {
        this.mPOP3Port = POP3Port;
    }

    public String getFrom() {
        return mFrom;
    }

    public void setFrom(String from) {
        this.mFrom = from;
    }

    public String getPass() {
        return mPass;
    }

    public void setPass(String pass) {
        this.mPass = pass;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getMailsLocation() {
        return mMailsLocation;
    }

    public void setMailsLocation(String mailsLocation) {
        this.mMailsLocation = mailsLocation;
    }

    public String getContactsLocation() {
        return mContactsLocation;
    }

    public void setContactsLocation(String contactsLocation) {
        this.mContactsLocation = contactsLocation;
    }

    public Date getLastUpdate() {
        return mLastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.mLastUpdate = lastUpdate;
    }
    
}
