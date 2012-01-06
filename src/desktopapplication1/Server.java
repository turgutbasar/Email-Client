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
    public Server(String hostname, int port) {
        this.host = hostname;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String hostname) {
        this.host = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    private String host;
    private int port;
}
