/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;

/**
 *
 * @author kursat
 */
public class Contact {

    public Contact() {
        this.email = "";
        this.name = "";
    }

    public Contact(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    private String email;
    private String name;
    
}
