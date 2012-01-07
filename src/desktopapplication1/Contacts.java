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
    
    public String [] getAllContacts () {
        //return emails;
        //TODO: Fix this
        
        String kamil = "turgutbasar@gmail.com; kursadyo@gmail.com; kursat.yigitoglu@gmail.com; naber@gmail.com";
        return kamil.split(";");
        
    }
    
    public void addContact ( String email ) {
        
    }
    
    public void deleteContact ( String email ) {
        
    }
    
    public void writeToFile () {
        
    }
    
    public void readFromFile () {
        
    }
    
    private String [] emails;
    
}
