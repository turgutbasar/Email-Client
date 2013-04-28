/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bizsoft.emailclient.backend.contacts;


import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.bizsoft.emailclient.EmailClient;
import org.bizsoft.emailclient.backend.persistance.IReadable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 *
 * @author kursat and turgutbasar
 */

public class ContactsContainerLocal extends AbstractContactsContainer{
    
    public Contact[] getAllContacts () {
        //return emails;
        //TODO: Fix this
        Contact[] tmp = new Contact[mContacts.size()];
        mContacts.toArray(tmp);
        return tmp;
        
    }
    
    public boolean write () {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document document;
        
        try {
            docBuilder = docFactory.newDocumentBuilder();
            document = docBuilder.newDocument();
            Element rootElement = document.createElement("contacts");
            
            document.appendChild(rootElement);
            
            Element contactElement;
            Element emailElement;
            Element nameElement;
            
            for (int i = 0; i < mContacts.size(); i++) {
                contactElement = document.createElement("contact");
                contactElement.setAttribute("id", Integer.toString(i));
                
                emailElement = document.createElement("email");
                emailElement.appendChild(document.createTextNode(mContacts.get(i).getEmail()));
                
                nameElement = document.createElement("name");
                nameElement.appendChild(document.createTextNode(mContacts.get(i).getName()));
                
                contactElement.appendChild(emailElement);
                contactElement.appendChild(nameElement);
                
                rootElement.appendChild(contactElement);
                
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(EmailClient.config.getContactsLocation()));
            transformer.transform(source, result);
            
        } catch (Exception e) {
        }
        return true;
    }
    
    public boolean read () {
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            Document document;  
            try {
                
                builder = factory.newDocumentBuilder();
                document = builder.parse(EmailClient.config.getContactsLocation());
                
                int length = document.getElementsByTagName("contact").getLength();
                
                for (int i = 0; i < length; i++) {
                    // for debug
                    System.out.println(document.getElementsByTagName("contact").item(i).getChildNodes().item(0).getChildNodes().item(0).getNodeValue());
                    System.out.println(document.getElementsByTagName("contact").item(i).getChildNodes().item(1).getChildNodes().item(0).getNodeValue());
                    addContact(new Contact(document.getElementsByTagName("contact").item(i).getChildNodes().item(0).getChildNodes().item(0).getNodeValue(), document.getElementsByTagName("contact").item(i).getChildNodes().item(1).getChildNodes().item(0).getNodeValue()));
                }
            
            } catch (Exception ex) {
                System.out.println("XML Exception : " + ex.getMessage());
            }
            //DesktopApplication1.config.writeConfiguration();
            return true;
    }
    
    @Override
    public boolean addContact(Contact contact) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addContacts(Contact[] contacts) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteContact(Contact contact) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteContacts(Contact[] contacts) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean importContacts(IReadable source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
