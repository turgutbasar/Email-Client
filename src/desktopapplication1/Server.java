/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;

/**
 *
 * @author kursat
 */
abstract public class Server {
    
    /*We have to add and implement more attributes but for now we do not know
     * any more attributes.
     */
    public Server(String hostname, String port) {
        this.hostname = hostname;
        this.port = Integer.parseInt(port);
    }
    
    abstract public void connect();
    
    abstract public void disconnect();

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    private String hostname;
    private int port;
}
