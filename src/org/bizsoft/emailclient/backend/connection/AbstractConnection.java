/*
 * This class is an abstract class for POP3 and SMTP protocols.
 */
package org.bizsoft.emailclient.backend.connection;

/**
 *
 * @author kursat and TheCodeGuru
 */
public abstract class AbstractConnection {
   
    protected String mHost;
    protected int mPort;
    protected String mUserMail;
    protected String mPassword;
    
    public AbstractConnection(String hostname, int port, String user, String password) {
        mUserMail = user;
        mPassword = password;
        mHost = hostname;
        mPort = port;
    }

    public String getHost() {
        return mHost;
    }

    public void setHost(String host) {
        mHost = host;
    }

    public int getPort() {
        return mPort;
    }

    public void setPort(int port) {
        mPort = port;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getUserMail() {
        return mUserMail;
    }

    public void setUserMail(String userMail) {
        mUserMail = userMail;
    }
}
