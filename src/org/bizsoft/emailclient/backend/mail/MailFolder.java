/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author CodeGuru
 */
public class MailFolder {

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public MailFolder(String name) {
        this.name = name;
        length = 0;
        envelopes = null;
    }
    
    public void fillFolder(){
        //First of all, we need to take mails from POP3
        POP3Connection p = new POP3Connection(DesktopApplication1.config.getPOP3Host(),Integer.parseInt(DesktopApplication1.config.getPOP3Port()),DesktopApplication1.config.getFrom(),DesktopApplication1.config.getPass());
        try {
            p.connect();
            p.openFolder("INBOX");
            //Last Update
            p.takeMessages(DesktopApplication1.config.getLastUpdate());
            envelopes = p.getEnvlopes();
            p.closeFolder();
            p.disconnect();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        //End of POP3 fill
        //Save envelopes to local folder
        for ( int i = 0 ; i < envelopes.length ; ++i ){
            //Date comparison
            if ( DesktopApplication1.config.getLastUpdate().after(envelopes[i].getDate()))
                continue;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            Document document;
            String fromText = "";
            for ( int j = 0 ; j < envelopes[i].getFrom().length ; ++j){
                fromText += envelopes[i].getFrom()[j];
                fromText += ";";
            }
            fromText = fromText.substring(0, fromText.length()-1);
            String ccText = "No CC";
            if ( envelopes[i].getCc() != null ){
                ccText = "";
                for ( int j = 0 ; j < envelopes[i].getCc().length ; ++j){
                    ccText += envelopes[i].getCc()[j];
                    ccText += ";";
                }
                ccText = ccText.substring(0, ccText.length()-1);
            }
            String toText = "";
            for ( int j = 0 ; j < envelopes[i].getTo().length ; ++j){
                toText += envelopes[i].getTo()[j];
                toText += ";";
            }
            toText = toText.substring(0, toText.length()-1);
            String timeText = "";
            timeText += Integer.toString(envelopes[i].getDate().getYear() + 1900)+"/";
            timeText += Integer.toString(envelopes[i].getDate().getMonth())+"/";
            timeText += Integer.toString(envelopes[i].getDate().getDay())+"-";
            timeText += Integer.toString(envelopes[i].getDate().getHours())+"-";
            timeText += Integer.toString(envelopes[i].getDate().getMinutes())+"-";
            timeText += Integer.toString(envelopes[i].getDate().getSeconds());
            try {
                docBuilder = docFactory.newDocumentBuilder();
                document = docBuilder.newDocument();
                Element rootElement = document.createElement("mail");
                document.appendChild(rootElement);
                Element from = document.createElement("from");
                from.appendChild(document.createTextNode(fromText));
		rootElement.appendChild(from);
                Element cc = document.createElement("cc");
                cc.appendChild(document.createTextNode(ccText));
		rootElement.appendChild(cc);
                Element to = document.createElement("to");
                to.appendChild(document.createTextNode(toText));
		rootElement.appendChild(to);
                Element time = document.createElement("time");
                time.appendChild(document.createTextNode(timeText));
		rootElement.appendChild(time);
                Element subject = document.createElement("subject");
                subject.appendChild(document.createTextNode(envelopes[i].getSubject()));
		rootElement.appendChild(subject);
                Element body = document.createElement("body");
                body.appendChild(document.createTextNode(envelopes[i].getBody()));
		rootElement.appendChild(body);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(DesktopApplication1.config.getMailsLocation()+name+"/"+Integer.toString(i)));
                transformer.transform(source, result);
                
                
            } catch (Exception ex) {
                System.out.println("XML Exception : " + ex.getMessage());
            }
            Calendar cal = Calendar.getInstance();
            DesktopApplication1.config.setLastUpdate(cal.getTime());
            DesktopApplication1.config.writeConfiguration();
        }
        if (envelopes.length == 0)
            return;
        //And take mails(local part of mails) from local folder
        File directory = new File(DesktopApplication1.config.getMailsLocation()+name);
        String files[] = directory.list();
        int l = 0;
        if ( files != null ){
            l = files.length;
        }
        for ( int i = 0 ; i < l ; ++i ){
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            Document document;  
            String subject;
            String body;
            String from[] = null;
            String cc[] = null;
            String to[] = null;
            Date time;
            try {
                builder = factory.newDocumentBuilder();
                document = builder.parse(directory+"/"+files[i]);
                from = (((Text)((Element)document.getElementsByTagName("from").item(0)).getChildNodes().item(0)).getNodeValue()).split(";");
                cc = (((Text)((Element)document.getElementsByTagName("cc").item(0)).getChildNodes().item(0)).getNodeValue()).split(";");
                to = (((Text)((Element)document.getElementsByTagName("to").item(0)).getChildNodes().item(0)).getNodeValue()).split(";");
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd-HH-mm-ss");
                time = df.parse(((Text)((Element)document.getElementsByTagName("time").item(0)).getChildNodes().item(0)).getNodeValue());
                subject = ((Text)((Element)document.getElementsByTagName("subject").item(0)).getChildNodes().item(0)).getNodeValue();
                body = ((Text)((Element)document.getElementsByTagName("body").item(0)).getChildNodes().item(0)).getNodeValue();
                //Date comparison
                if ( time == envelopes[i].getDate())
                    envelopes[i] = new Envelope(subject, body, false, body, from, cc, to, time);
                else{
                    File target = new File(directory+"/"+files[i]);
                    target.delete();
                    directory = new File(DesktopApplication1.config.getMailsLocation()+name);
                }
            } catch (Exception ex) {
                System.out.println("XML Exception : " + ex.getMessage());
            }
            Calendar cal = Calendar.getInstance();
            DesktopApplication1.config.setLastUpdate(cal.getTime());
            DesktopApplication1.config.writeConfiguration();
        }
    }
    
    private String name;
    private Envelope[] envelopes;
    private int length;
}
