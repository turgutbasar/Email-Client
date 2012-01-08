/*
 * DesktopApplication1View.java
 */

package desktopapplication1;

import java.awt.Dimension;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JFileChooser;

/**
 * The application's main frame.
 */
public class DesktopApplication1View extends FrameView {

    public DesktopApplication1View(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        
        updateContacts();

        //////////////////////////
        POP3Connection p = new POP3Connection(DesktopApplication1.config.getPOP3Host(),Integer.parseInt(DesktopApplication1.config.getPOP3Port()),DesktopApplication1.config.getFrom(),DesktopApplication1.config.getPass());
        Envelope[] envlopes = null;
        try {
            p.connect();
            p.openFolder("INBOX");
            p.takeMessages(new Date(2011, 01, 02));
            envlopes = p.getEnvlopes();
            p.closeFolder();
            p.disconnect();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        //////////////////////////
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = DesktopApplication1.getApplication().getMainFrame();
            aboutBox = new DesktopApplication1AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        DesktopApplication1.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new JPanel();
        topPanel = new JPanel();
        mailLabel = new JLabel();
        newMailButton = new JButton();
        TabbedPane = new JTabbedPane();
        receivedBoxPanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        receivedList = new JList();
        sentBoxPanel = new JPanel();
        jScrollPane2 = new JScrollPane();
        sentList = new JList();
        contactsPanel = new JPanel();
        jScrollPane3 = new JScrollPane();
        contactsList = new JList();
        deleteContactsButton = new JButton();
        nameLabel = new JLabel();
        addContactNameTextField = new JTextField();
        emailLabel = new JLabel();
        addContactEmailTextField = new JTextField();
        addContactButton = new JButton();
        contactsFileTextField = new JTextField();
        contactsFileLocationButton = new JButton();
        contactsFileLoadButton = new JButton();
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu();
        settingsMenuItem = new JMenuItem();
        JMenuItem exitMenuItem = new JMenuItem();
        editMenu = new JMenu();
        JMenu helpMenu = new JMenu();
        JMenuItem aboutMenuItem = new JMenuItem();
        statusPanel = new JPanel();
        JSeparator statusPanelSeparator = new JSeparator();
        statusMessageLabel = new JLabel();
        statusAnimationLabel = new JLabel();
        progressBar = new JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        ResourceMap resourceMap = Application.getInstance(DesktopApplication1.class).getContext().getResourceMap(DesktopApplication1View.class);
        topPanel.setBackground(resourceMap.getColor("topPanel.background")); // NOI18N
        topPanel.setName("topPanel"); // NOI18N

        mailLabel.setText(resourceMap.getString("mailLabel.text")); // NOI18N
        mailLabel.setName("mailLabel"); // NOI18N

        ActionMap actionMap = Application.getInstance(DesktopApplication1.class).getContext().getActionMap(DesktopApplication1View.class, this);
        newMailButton.setAction(actionMap.get("showNewMailDialog")); // NOI18N
        newMailButton.setText(resourceMap.getString("newMailButton.text")); // NOI18N
        newMailButton.setName("newMailButton"); // NOI18N

        GroupLayout topPanelLayout = new GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mailLabel, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED, 500, Short.MAX_VALUE)
                .addComponent(newMailButton)
                .addContainerGap())
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topPanelLayout.createParallelGroup(Alignment.LEADING)
                    .addComponent(newMailButton, Alignment.TRAILING)
                    .addComponent(mailLabel, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addContainerGap())
        );

        mailLabel.getAccessibleContext().setAccessibleDescription(resourceMap.getString("main_mailLabel.AccessibleContext.accessibleDescription")); // NOI18N

        TabbedPane.setName("TabbedPane"); // NOI18N

        receivedBoxPanel.setName("receivedBoxPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        receivedList.setName("receivedList"); // NOI18N
        jScrollPane1.setViewportView(receivedList);

        GroupLayout receivedBoxPanelLayout = new GroupLayout(receivedBoxPanel);
        receivedBoxPanel.setLayout(receivedBoxPanelLayout);
        receivedBoxPanelLayout.setHorizontalGroup(
            receivedBoxPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(receivedBoxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
                .addContainerGap())
        );
        receivedBoxPanelLayout.setVerticalGroup(
            receivedBoxPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(receivedBoxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabbedPane.addTab(resourceMap.getString("receivedBoxPanel.TabConstraints.tabTitle"), receivedBoxPanel); // NOI18N

        sentBoxPanel.setName("sentBoxPanel"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        sentList.setName("sentList"); // NOI18N
        jScrollPane2.setViewportView(sentList);

        GroupLayout sentBoxPanelLayout = new GroupLayout(sentBoxPanel);
        sentBoxPanel.setLayout(sentBoxPanelLayout);
        sentBoxPanelLayout.setHorizontalGroup(
            sentBoxPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(sentBoxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
                .addContainerGap())
        );
        sentBoxPanelLayout.setVerticalGroup(
            sentBoxPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(sentBoxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabbedPane.addTab(resourceMap.getString("sentBoxPanel.TabConstraints.tabTitle"), sentBoxPanel); // NOI18N

        contactsPanel.setMinimumSize(new Dimension(480, 480));
        contactsPanel.setName("contactsPanel"); // NOI18N
        contactsPanel.setPreferredSize(new Dimension(640, 480));

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        contactsList.setName("contactsList"); // NOI18N
        jScrollPane3.setViewportView(contactsList);

        deleteContactsButton.setAction(actionMap.get("onClickDeleteSelectedContactsButton")); // NOI18N
        deleteContactsButton.setText(resourceMap.getString("deleteContactsButton.text")); // NOI18N
        deleteContactsButton.setName("deleteContactsButton"); // NOI18N

        nameLabel.setText(resourceMap.getString("nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

        addContactNameTextField.setText(resourceMap.getString("addContactNameTextField.text")); // NOI18N
        addContactNameTextField.setName("addContactNameTextField"); // NOI18N

        emailLabel.setText(resourceMap.getString("emailLabel.text")); // NOI18N
        emailLabel.setName("emailLabel"); // NOI18N

        addContactEmailTextField.setText(resourceMap.getString("addContactEmailTextField.text")); // NOI18N
        addContactEmailTextField.setName("addContactEmailTextField"); // NOI18N

        addContactButton.setAction(actionMap.get("onClickAddContactsButton")); // NOI18N
        addContactButton.setText(resourceMap.getString("addContactButton.text")); // NOI18N
        addContactButton.setName("addContactButton"); // NOI18N

        contactsFileTextField.setText(resourceMap.getString("contactsFileTextField.text")); // NOI18N
        contactsFileTextField.setName("contactsFileTextField"); // NOI18N

        contactsFileLocationButton.setAction(actionMap.get("callFileChooser")); // NOI18N
        contactsFileLocationButton.setText(resourceMap.getString("contactsFileLocationButton.text")); // NOI18N
        contactsFileLocationButton.setName("contactsFileLocationButton"); // NOI18N

        contactsFileLoadButton.setAction(actionMap.get("onClickLoadContactFromFileButton")); // NOI18N
        contactsFileLoadButton.setText(resourceMap.getString("contactsFileLoadButton.text")); // NOI18N
        contactsFileLoadButton.setName("contactsFileLoadButton"); // NOI18N

        GroupLayout contactsPanelLayout = new GroupLayout(contactsPanel);
        contactsPanel.setLayout(contactsPanelLayout);
        contactsPanelLayout.setHorizontalGroup(
            contactsPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(contactsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contactsPanelLayout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
                    .addGroup(contactsPanelLayout.createSequentialGroup()
                        .addGroup(contactsPanelLayout.createParallelGroup(Alignment.LEADING)
                            .addGroup(contactsPanelLayout.createSequentialGroup()
                                .addGroup(contactsPanelLayout.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(nameLabel)
                                    .addGroup(contactsPanelLayout.createSequentialGroup()
                                        .addComponent(emailLabel)
                                        .addPreferredGap(ComponentPlacement.RELATED, 3, Short.MAX_VALUE)))
                                .addGap(20, 20, 20)
                                .addGroup(contactsPanelLayout.createParallelGroup(Alignment.LEADING)
                                    .addComponent(addContactNameTextField, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                    .addComponent(addContactEmailTextField, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(addContactButton, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                            .addComponent(deleteContactsButton, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE))
                        .addGap(163, 163, 163)
                        .addGroup(contactsPanelLayout.createParallelGroup(Alignment.LEADING)
                            .addGroup(contactsPanelLayout.createSequentialGroup()
                                .addComponent(contactsFileTextField, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(contactsFileLocationButton, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
                            .addComponent(contactsFileLoadButton, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))))
                .addContainerGap())
        );
        contactsPanelLayout.setVerticalGroup(
            contactsPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, contactsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(contactsPanelLayout.createParallelGroup(Alignment.TRAILING)
                    .addGroup(contactsPanelLayout.createSequentialGroup()
                        .addGroup(contactsPanelLayout.createParallelGroup(Alignment.TRAILING)
                            .addComponent(contactsFileLocationButton)
                            .addComponent(contactsFileTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(contactsFileLoadButton, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                    .addGroup(contactsPanelLayout.createSequentialGroup()
                        .addComponent(deleteContactsButton)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(contactsPanelLayout.createParallelGroup(Alignment.LEADING, false)
                            .addComponent(addContactButton, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                            .addGroup(contactsPanelLayout.createSequentialGroup()
                                .addGroup(contactsPanelLayout.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(nameLabel)
                                    .addComponent(addContactNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(contactsPanelLayout.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(emailLabel)
                                    .addComponent(addContactEmailTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );

        TabbedPane.addTab(resourceMap.getString("contactsPanel.TabConstraints.tabTitle"), contactsPanel); // NOI18N

        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(Alignment.LEADING)
            .addComponent(topPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(TabbedPane, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TabbedPane, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        settingsMenuItem.setAction(actionMap.get("showSettings")); // NOI18N
        settingsMenuItem.setText(resourceMap.getString("settingsMenuItem.text")); // NOI18N
        settingsMenuItem.setName("settingsMenuItem"); // NOI18N
        fileMenu.add(settingsMenuItem);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N
        menuBar.add(editMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        GroupLayout statusPanelLayout = new GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(Alignment.LEADING)
            .addComponent(statusPanelSeparator, GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(ComponentPlacement.RELATED, 611, Short.MAX_VALUE)
                .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void showSettings() {
        if (settingsPanel == null) {
            JFrame mainFrame = DesktopApplication1.getApplication().getMainFrame();
            settingsPanel = new SettingsDialog(mainFrame);
            settingsPanel.setLocationRelativeTo(mainFrame);
        }
        DesktopApplication1.getApplication().show(settingsPanel);
    }

    @Action
    public void showNewMailDialog() {
        if (newMailPanel == null) {
            JFrame mainFrame = DesktopApplication1.getApplication().getMainFrame();
            newMailPanel = new NewMailDialog(mainFrame);
            newMailPanel.setLocationRelativeTo(mainFrame);
        }
        DesktopApplication1.getApplication().show(newMailPanel);        
    }
    
//    public void updateContacts () {
//        contactsList.setModel(new AbstractListModel() {
//            String[] strings = DesktopApplication1.contacts.getAllContacts();
//            public int getSize() { return strings.length; }
//            public Object getElementAt(int i) { return strings[i]; }
//        });
//    }
    
    public void updateContacts () {
        
        final String [] names = new String[DesktopApplication1.contacts.getAllContacts().length];
        
        for (int i = 0; i < DesktopApplication1.contacts.getAllContacts().length; i++) {
            names[i] = DesktopApplication1.contacts.getAllContacts()[i].getName();
        }
        
        
        contactsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = names;
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
    }

    @Action
    public void onClickDeleteSelectedContactsButton() {
        int [] indices = contactsList.getSelectedIndices();    
        
        for (int i = 0; i < indices.length; i++) {
            DesktopApplication1.contacts.deleteContact(indices[i]);
        }
        
        updateContacts();
    }

    @Action
    public void onClickAddContactsButton() {
        if (addContactEmailTextField.getText().length() > 0 && addContactNameTextField.getText().length() > 0) {
            DesktopApplication1.contacts.addContact(addContactEmailTextField.getText(), addContactNameTextField.getText());
        }
    }

    @Action
    public void onClickLoadContactFromFileButton() {
        DesktopApplication1.contacts.readFromExternalContacsFile( contactsFileTextField.getText());
    }
    
    @Action
    public void callFileChooser() {
         
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle("chooserTitle");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        //
        // disable the "All files" option.
        //
        fileChooser.setAcceptAllFileFilterUsed(false);
        //   
        if (fileChooser.showOpenDialog(contactsPanel) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                +  fileChooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                +  fileChooser.getSelectedFile());
        }
        else {
            System.out.println("No Selection ");
        }
        
        contactsFileTextField.setText(fileChooser.getSelectedFile().getPath());
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JTabbedPane TabbedPane;
    private JButton addContactButton;
    private JTextField addContactEmailTextField;
    private JTextField addContactNameTextField;
    private JButton contactsFileLoadButton;
    private JButton contactsFileLocationButton;
    private JTextField contactsFileTextField;
    private JList contactsList;
    private JPanel contactsPanel;
    private JButton deleteContactsButton;
    private JMenu editMenu;
    private JLabel emailLabel;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JLabel mailLabel;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JLabel nameLabel;
    private JButton newMailButton;
    private JProgressBar progressBar;
    private JPanel receivedBoxPanel;
    private JList receivedList;
    private JPanel sentBoxPanel;
    private JList sentList;
    private JMenuItem settingsMenuItem;
    private JLabel statusAnimationLabel;
    private JLabel statusMessageLabel;
    private JPanel statusPanel;
    private JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    private JDialog settingsPanel;
    private JDialog newMailPanel;
    private DefaultListModel contactsListModel;

    private javax.swing.JFileChooser fileChooser;
}
