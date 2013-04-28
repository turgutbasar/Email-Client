/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bizsoft.emailclient.backend.contacts;

import org.bizsoft.emailclient.backend.persistance.IReadable;

/**
 *
 * @author CodeGuru
 */
public interface IContactsContainer {
    public boolean addContact(Contact contact);
    public boolean addContacts(Contact[] contacts);
    public boolean deleteContact(Contact contact);
    public boolean deleteContacts(Contact[] contacts);
    public boolean importContacts(IReadable source);
}
