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
    }
    
    public void readConfiguration () {
        
        
        try {
            
            Properties configFile = new Properties();
            configFile.load(new FileInputStream("user.props"));
            
            setHost(configFile.getProperty("host"));
            setPort(configFile.getProperty("port"));
            setFrom(configFile.getProperty("from"));
            setPass(configFile.getProperty("password"));
            
            //configFile.list(System.out);
        
        } catch (Exception e) {
            //TODO: Handle Exception.
            System.out.println(e.getMessage());
        }
    }

    public void writeConfiguration () {
        try {
            Properties configFile = new Properties();
            configFile.load(new FileInputStream("user.props"));
            //configFile.list(System.out);
            
            
            configFile.put("host", getHost());
            configFile.put("port", getPort());
            configFile.put("from", getFrom());
            configFile.put("password", getPass());
            
            FileOutputStream out = new FileOutputStream("user.props");
            
            configFile.save(out, "/* properties updated */");
            
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    
    private String host;
    private String port;
    private String from;
    private String pass;
}
