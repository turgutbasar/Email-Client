/*
 * This class used as mail object.
 */
package org.bizsoft.emailclient.backend.mail;

import java.util.Date;

/**
 *
 * @author kursat and TheCodeGuru
 */
public class Envelope {

    public Envelope(String subject, String body, Boolean isHtml, String folder, String[] from, String[] cc, String[] to, Date date) {
        this.subject = subject;
        this.body = body;
        this.isHtml = isHtml;
        this.folder = folder;
        this.from = from;
        this.cc = cc;
        this.to = to;
        this.date = date;
    }
        
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

    private String subject;
    private String body;
    private Boolean isHtml;
    private String folder;
    private String[] from;
    private String[] cc;
    private String[] to;

    public Date getDate() {
        return date;
    }
    private Date date;
}
