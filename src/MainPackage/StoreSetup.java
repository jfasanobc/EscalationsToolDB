package MainPackage;


import BCLibrary.StoreBasic;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreSetup extends javax.swing.JInternalFrame {
    private int testResults;
    private String storeURL;
    private String username;
    private String password;
    private boolean exemption;
    /**
     * Creates new form StoreSetup
     */
    public StoreSetup() {
        initComponents();
        testResults = -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rightClickActions = new javax.swing.JPopupMenu();
        undoAction = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        cutAction = new javax.swing.JMenuItem();
        copyAction = new javax.swing.JMenuItem();
        pasteAction = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        selectAllAction = new javax.swing.JMenuItem();
        apiPathLabel = new javax.swing.JLabel();
        apiPath = new javax.swing.JTextField();
        usernameLabel = new javax.swing.JLabel();
        userName = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passWord = new javax.swing.JTextField();
        testButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        resultsLabel = new javax.swing.JLabel();
        ssl = new javax.swing.JCheckBox();

        undoAction.setText("Undo");
        rightClickActions.add(undoAction);
        rightClickActions.add(jSeparator1);

        cutAction.setText("Cut");
        rightClickActions.add(cutAction);

        copyAction.setText("Copy");
        rightClickActions.add(copyAction);

        pasteAction.setText("Paste");
        pasteAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteActionActionPerformed(evt);
            }
        });
        rightClickActions.add(pasteAction);
        rightClickActions.add(jSeparator2);

        selectAllAction.setText("Select All");
        rightClickActions.add(selectAllAction);

        setClosable(true);

        apiPathLabel.setText("API Path");

        apiPath.setComponentPopupMenu(rightClickActions);
        apiPath.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                apiPathMouseClicked(evt);
            }
        });

        usernameLabel.setText("Username");

        userName.setComponentPopupMenu(rightClickActions);

        passwordLabel.setText("Password");

        passWord.setComponentPopupMenu(rightClickActions);

        testButton.setText("Test Credentials");
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save Credentials");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        resultsLabel.setText(" ");

        ssl.setText("Exempt Untrusted SSL?");
        ssl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sslActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resultsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameLabel)
                            .addComponent(apiPathLabel)
                            .addComponent(passwordLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ssl)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(testButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(saveButton))
                                .addComponent(apiPath, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(userName, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(passWord, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(resultsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(apiPathLabel)
                    .addComponent(apiPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ssl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(testButton))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
        System.out.println("Testing with " + exemption + " exemption");
        StoreBasic store = new StoreBasic(apiPath.getText(),userName.getText(),passWord.getText(), exemption);
        int statusCode;
        try {
            if (store.testConnection()) {
                resultsLabel.setText("The credentials are correct for the store.");
                if (testResults < 2)
                    testResults = 1;
            }
            else {
                statusCode = store.getStatusCode();
                if (statusCode == 401)
                    resultsLabel.setText("Please check the credentials.");
                else
                    resultsLabel.setText("Please check the credentials. You may need to set SSL Exemption");
                if (testResults < 2)
                    testResults = 0;
            }
        } catch (Exception ex) {
            statusCode = store.getStatusCode();
            System.out.println("Status Code: " + statusCode);
            if (statusCode == 404) 
                resultsLabel.setText("The API path returned a 404.");
            Logger.getLogger(StoreSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_testButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        System.out.println("Testing with " + exemption + " exemption");
        StoreBasic store = new StoreBasic(apiPath.getText(),userName.getText(),passWord.getText(), exemption);
        
        try {
            if (store.testConnection()) {
                storeURL = apiPath.getText();
                username = userName.getText();
                password = passWord.getText();
                testResults = 2;
                EscalationsDesktop desktop = (EscalationsDesktop) this.getParent().getParent().getParent().getParent().getParent();
                desktop.setConnectivityText("Yes", store.getAPILimit());
                
                desktop.setStoreURL(storeURL);
                desktop.setPassWord(password);
                desktop.setUserName(username);
                desktop.setExemption(exemption);
                desktop.makeStoreConnected();
                desktop.setConnection(store);
                this.doDefaultCloseAction();
            }
            else {
                int statusCode = store.getStatusCode();
                if (statusCode == 401)
                    resultsLabel.setText("Please check the credentials.");
                else if (statusCode == 404) 
                    resultsLabel.setText("The API path returned a 404.");
                else
                    resultsLabel.setText("Please check the credentials. You may need to set SSL Exemption");
                if (testResults < 2)
                    testResults = 0;
            }
        } catch (Exception ex) {
            Logger.getLogger(StoreSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void sslActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sslActionPerformed
        if (ssl.isSelected())
            exemption = true;
        else 
            exemption = false;
        System.out.println("Changed the exempt to: " + exemption);
    }//GEN-LAST:event_sslActionPerformed

    private void apiPathMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_apiPathMouseClicked
       
    }//GEN-LAST:event_apiPathMouseClicked

    private void pasteActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteActionActionPerformed
        
    }//GEN-LAST:event_pasteActionActionPerformed
    public String getStoreURL() {
        return storeURL;
    }
    public String getUserName() {
        return username;
    }
    public String getPassWord() {
        return password;
    }
    public int getTestResults() {
        return testResults;
    }
    public boolean getExemption() {
        return exemption;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apiPath;
    private javax.swing.JLabel apiPathLabel;
    private javax.swing.JMenuItem copyAction;
    private javax.swing.JMenuItem cutAction;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTextField passWord;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JMenuItem pasteAction;
    private javax.swing.JLabel resultsLabel;
    private javax.swing.JPopupMenu rightClickActions;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem selectAllAction;
    private javax.swing.JCheckBox ssl;
    private javax.swing.JButton testButton;
    private javax.swing.JMenuItem undoAction;
    private javax.swing.JTextField userName;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}