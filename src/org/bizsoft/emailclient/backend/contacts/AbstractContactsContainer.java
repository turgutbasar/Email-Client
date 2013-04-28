/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bizsoft.emailclient.backend.contacts;

import java.util.ArrayList;
import org.bizsoft.emailclient.backend.persistance.IPersistable;
import org.bizsoft.emailclient.backend.persistance.IReadable;

/**
 *
 * @author kursat and turgutbasar
 */

public abstract class AbstractContactsContainer implements IPersistable, IContactsContainer {
    
    protected ArrayList<Contact> mContacts;
    
    public AbstractContactsContainer() {
        mContacts = new ArrayList<Contact>();
        read();
    }

    public abstract boolean read();

    public abstract boolean write();

    public abstract boolean addContact(Contact contact);

    public abstract boolean addContacts(Contact[] contacts);

    public abstract boolean deleteContact(Contact contact);

    public abstract boolean deleteContacts(Contact[] contacts);

    public abstract boolean importContacts(IReadable source);
    
}
