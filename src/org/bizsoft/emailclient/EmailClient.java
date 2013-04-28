/*
 * EmailClient.java
 */

package org.bizsoft.emailclient;

import org.bizsoft.emailclient.backend.Settings;
import org.bizsoft.emailclient.backend.contacts.ContactsContainerLocal;
import org.bizsoft.emailclient.frontend.MainScreen;

/**
 * The main class of the application.
 */
public class EmailClient {
    
    public static Settings config;
    public static ContactsContainerLocal contacts;

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        
        //Get Config
        config = new Settings();
        contacts = new ContactsContainerLocal();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
    }

}