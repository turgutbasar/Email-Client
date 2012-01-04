/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;
import java.util.*;

/**
 *
 * @author kursat
 */
public class List extends Vector {

    public List(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    /*public void popFromServer()*/
    protected String location;
}
