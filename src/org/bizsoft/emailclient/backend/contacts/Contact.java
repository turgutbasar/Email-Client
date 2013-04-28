/*
 * This class is designed to model a basic contact information.
 */

package org.bizsoft.emailclient.backend.contacts;

/**
 *
 * @author kursat and turgutbasar
 */

public class Contact {
    
    private String mEmail;
    private String mName;

    public Contact() {
        mEmail = "";
        mName = "";
    }

    public Contact(String email, String name) {
        mEmail = email;
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
      
}
