/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;

/**
 *
 * @author CodeGuru
 */
public class Envlope {

    public String getBody() {
        return body;
    }

    public String[] getCc() {
        return cc;
    }

    public String getFolder() {
        return folder;
    }

    public String[] getFrom() {
        return from;
    }

    public Boolean getIsHtml() {
        return isHtml;
    }

    public String getSubject() {
        return subject;
    }

    public String[] getTo() {
        return to;
    }

    public Envlope(String subject, String body, Boolean isHtml, String folder, String[] from, String[] cc, String[] to) {
        this.subject = subject;
        this.body = body;
        this.isHtml = isHtml;
        this.folder = folder;
        this.from = from;
        this.cc = cc;
        this.to = to;
    }
    private String subject;
    private String body;
    private Boolean isHtml;
    private String folder;
    private String[] from;
    private String[] cc;
    private String[] to;
}
