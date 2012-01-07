/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;


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
        
        String names = "Başar Turgut; Kürşat Yiğitoğlu; Kürşat Yiğitoğlu2; Kamil";
        String emails = "turgutbasar@gmail.com; kursadyo@gmail.com; kursat.yigitoglu@gmail.com; naber@gmail.com";
        
        String [] namesArr =names.split(";");
        String [] emailsArr =emails.split(";");
        
        Contact [] myContactArr = new Contact[namesArr.length];
        
        
        for (int i = 0; i < namesArr.length; i++) {
            myContactArr[i] = new Contact( emailsArr[i], namesArr[i]);
        }
        
        return myContactArr;
        
    }
    
    public void addContact ( String email, String name ) {
        
    }
    
    public void deleteContact ( int id ) {
        
    }
    
    public void writeToFile () {
        
    }
    
    public void readFromFile () {
        
    }
    
    private Contact [] contacts;
    
}
