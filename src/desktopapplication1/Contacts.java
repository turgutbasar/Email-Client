/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;


import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 *
 * @author kursat
 */
public class Contacts {

    public Contacts() {
        readFromFile();
    }
    
    public Contact[] getAllContacts () {
        //return emails;
        //TODO: Fix this
        
//        String names = "Başar Turgut; Kürşat Yiğitoğlu; Kürşat Yiğitoğlu2; Kamil";
//        String emails = "turgutbasar@gmail.com; kursadyo@gmail.com; kursat.yigitoglu@gmail.com; naber@gmail.com";
//        
//        String [] namesArr =names.split(";");
//        String [] emailsArr =emails.split(";");
//        
//        contacts = new Contact[namesArr.length];
//        
//        
//        for (int i = 0; i < namesArr.length; i++) {
//            contacts[i] = new Contact( emailsArr[i], namesArr[i]);
//        }
        
        return contacts;
        
    }
    
    public void addContact ( String email, String name ) {
        
    }
    
    public void deleteContact ( int id ) {
        
    }
    
    public void writeToFile () {
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
            
            for (int i = 0; i < this.contacts.length; i++) {
                contactElement = document.createElement("contact");
                contactElement.setAttribute("id", Integer.toString(i));
                
                emailElement = document.createElement("email");
                emailElement.appendChild(document.createTextNode(contacts[i].getEmail()));
                
                nameElement = document.createElement("name");
                nameElement.appendChild(document.createTextNode(contacts[i].getName()));
                
                contactElement.appendChild(emailElement);
                contactElement.appendChild(nameElement);
                
                rootElement.appendChild(contactElement);
                
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(DesktopApplication1.config.getContactsLocation()));
            transformer.transform(source, result);
            
        } catch (Exception e) {
        }
        
    }
    
    public void readFromFile () {
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            Document document;  
            try {
                
                builder = factory.newDocumentBuilder();
                document = builder.parse(DesktopApplication1.config.getContactsLocation());
                
                int length = document.getElementsByTagName("contact").getLength();
                
                System.out.println(length);
                contacts = new Contact[length];
                
                for (int i = 0; i < length; i++) {
                    // for debug
                    
                    
                    
                    System.out.println(document.getElementsByTagName("contact").item(i).getChildNodes().item(0).getChildNodes().item(0).getNodeValue());
                    System.out.println(document.getElementsByTagName("contact").item(i).getChildNodes().item(1).getChildNodes().item(0).getNodeValue());
                    
                    contacts[i].setEmail(document.getElementsByTagName("contact").item(i).getChildNodes().item(0).getChildNodes().item(0).getNodeValue());
                    contacts[i].setName(document.getElementsByTagName("contact").item(i).getChildNodes().item(1).getChildNodes().item(0).getNodeValue());
                }
            
            } catch (Exception ex) {
                System.out.println("XML Exception : " + ex.getMessage());
            }
            //DesktopApplication1.config.writeConfiguration();
    }
    
    public void readFromExternalContacsFile( String fileLocation ) {
        
    }
    
    private Contact [] contacts;
    
}
