/*
 * This class is an abstract class for POP3 and SMTP protocols.
 */
package desktopapplication1;

/**
 *
 * @author kursat and TheCodeGuru
 */
abstract public class Server {
    
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
