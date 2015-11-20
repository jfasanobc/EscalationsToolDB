/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.SwingWorker;
import org.json.JSONArray;
import org.json.JSONObject;
import Threads.ProgressThread;
import Threads.UpdateThread;
import BCLibrary.StoreBasic;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author julio
 */
public class OrphanedProducts extends javax.swing.JInternalFrame {
    private StoreBasic store;
    private EscalationsDesktop desktop;
    private ProgressThread newThread;
    private UpdateThread apiLimitThread;
    private int categoryCount;
    private int productsCount;
    private int percentage;
    private String currentTask;
    private Set<Integer> categoryIDs;
    private Set<Integer> productsToMove;
    private HashMap<Integer, String> productsToFix;
    private Connection database;
    private Statement statement;

    public OrphanedProducts() {
        initComponents();
    }
    
    public OrphanedProducts(StoreBasic store, EscalationsDesktop desktop) throws SQLException {
        this.store = store;
        this.desktop = desktop;
        this.categoryCount = 0;
        this.productsCount = 0;
        this.percentage = 0;
        categoryIDs = new TreeSet<>();
        productsToMove = new TreeSet<>();
        productsToFix = new HashMap<>();
        this.database = desktop.getDBConnection();
        this.statement = database.createStatement();
        initComponents();
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        orphanedProductsCheck = new javax.swing.JButton();
        orphanedProductsFix = new javax.swing.JButton();
        orphanedProductsProgress = new javax.swing.JProgressBar();
        orphanedProductsLabel = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Orphaned Products");

        orphanedProductsCheck.setText("Check");
        orphanedProductsCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orphanedProductsCheckActionPerformed(evt);
            }
        });

        orphanedProductsFix.setText("Fix");
        orphanedProductsFix.setEnabled(false);
        orphanedProductsFix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orphanedProductsFixActionPerformed(evt);
            }
        });

        orphanedProductsProgress.setStringPainted(true);

        orphanedProductsLabel.setText("Results posted here...");
        orphanedProductsLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(orphanedProductsProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(orphanedProductsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(orphanedProductsCheck, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(orphanedProductsFix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(orphanedProductsCheck)
                        .addGap(18, 18, 18)
                        .addComponent(orphanedProductsFix))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(orphanedProductsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orphanedProductsProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void orphanedProductsCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orphanedProductsCheckActionPerformed
        SwingWorker <Void, Void> worker = new SwingWorker<Void, Void>() {
        @Override
            protected Void doInBackground() throws Exception {
                //retrieve catgories
                int jsonArrayLength = 0;
                int page = 1;
                String results;
                JSONArray jsonArray;
                JSONObject jsonObject;
                newThread = new ProgressThread(orphanedProductsProgress, false);
                apiLimitThread = new UpdateThread(desktop.getConnectivity(),store);
                if (categoryCount == 0) {
                    try {
                        //grab count of categories
                        store.get("categories/count");
                        jsonObject = new JSONObject(store.toString());
                        categoryCount = jsonObject.getInt("count");
                        apiLimitThread.start();
                        newThread.start();
                    } catch (IOException ex) {
                        System.out.println("Issue with the following: " + ex.getMessage());
                    }
                    newThread.setCurrentTask("Gatering Categories");
                    do {
                        try {
                            store.get("categories?limit=249&page=" + page);
                            results = store.toString();
                            jsonArray = new JSONArray(results);
                            jsonArrayLength = jsonArray.length();
                            for(int index = 0; index < jsonArrayLength; index++) {
                                jsonObject = jsonArray.getJSONObject(index);
                                categoryIDs.add(jsonObject.getInt("id"));
                                newThread.setPercentage(((index + 1 + (249 * (page - 1))) * 100) / categoryCount);
                            }
                            if (jsonArrayLength == 249) page++;
                        } catch (IOException ex) {
                            System.out.println("Issue with the following: " + ex.getMessage());
                        }
                    } while (jsonArrayLength == 249);
                }
                newThread.setPercentage(0);
                newThread.toggle("on");
                //find products now but we will do everything on the fly
                page = 1;
                Integer catIDArray[] = categoryIDs.toArray(new Integer[categoryIDs.size()]);
                ArrayList <Integer> result = new ArrayList<>();
                try {
                    //grab count of categories
                    store.get("products/count");
                    jsonObject = new JSONObject(store.toString());
                    productsCount = jsonObject.getInt("count");
                    if(!apiLimitThread.isAlive()) 
                        apiLimitThread.start();
                    if (!newThread.isAlive())
                        newThread.start();
                } catch (IOException ex) {
                    System.out.println("Issue with the following: " + ex.getMessage());
                }
                newThread.setCurrentTask("Gathering Products");
                do {
                    try {
                        store.get("products?limit=249&page=" + page);
                        results = store.toString();
                        jsonArray = new JSONArray(results);
                        jsonArrayLength = jsonArray.length();
                        String sqlStatement;
                        for(int index = 0; index < jsonArrayLength; index++) {
                            jsonObject = jsonArray.getJSONObject(index);
                            int productID = jsonObject.getInt("id");
                            JSONArray categoriesAssigned = jsonObject.getJSONArray("categories");
                            result.clear();
                            newThread.setPercentage(((index + 1 + (249 * (page - 1))) * 100) / productsCount);
                            for(int counter = 0; counter < categoriesAssigned.length(); counter++) {
                                if (Arrays.binarySearch(catIDArray, categoriesAssigned.getInt(counter)) >= 0) {
                                    result.add(categoriesAssigned.getInt(counter));
                                }
                            }
                            if (result.size() < categoriesAssigned.length() && !result.isEmpty()) {
                                String catsToPush = "";
                                for(int counter = 0; counter < result.size(); counter++) {
                                    
                                    if (counter == result.size() - 2)
                                        catsToPush += result.get(counter);
                                    else
                                        catsToPush += result.get(counter) + ",";
                                }
                                productsToFix.put(productID, "{\"categories\":[" + catsToPush + "]}");
                                sqlStatement = "INSERT INTO moveProducts (productID, categoryAssigned) " +
                                               "VALUES(" + productID + ",'{\"categories\":[" + catsToPush + "]}');";
                                statement.executeUpdate(sqlStatement);
                            }
                            else if(result.isEmpty()) {
                                productsToMove.add(productID);
                                sqlStatement = "INSERT INTO orphanedProducts (productID) " +
                                               "VALUES(" + productID + ");";
                                statement.executeUpdate(sqlStatement);
                            }
                            else {}
                        }
                        if (jsonArrayLength == 249) page++;
                    } catch(IOException ex) {
                        System.out.println("Issue with the following: " + ex.getMessage());
                    }
                } while(jsonArrayLength == 249);
                percentage = 100;
                newThread.setPercentage(100);
                if (productsToMove.size() > 0 || productsToFix.size() > 0) {
                    orphanedProductsLabel.setText("<html>Found " + productsToMove.size() + (productsToMove.size() == 1 ? " product ": " products ") + "to move to Orphans cat<br>"
                                                  + "Found " + productsToFix.size() + (productsToFix.size() == 1 ? " product " : " products ") + "to fix categories");
                    orphanedProductsFix.setEnabled(true);
                }
                else if (productsToMove.isEmpty()&& productsToFix.isEmpty())
                    orphanedProductsLabel.setText("No orphaned products found");
                apiLimitThread.toggle("off");
                return null;
            }
        };
        worker.execute();
    }//GEN-LAST:event_orphanedProductsCheckActionPerformed

    private void orphanedProductsFixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orphanedProductsFixActionPerformed
        SwingWorker <Void, Void> worker = new SwingWorker<Void, Void>() {
        @Override
            protected Void doInBackground() throws Exception {
                orphanedProductsFix.setEnabled(false);
                String orphanedProductsSQL = "SELECT productID FROM orphanedProducts";
                String moveProductsSQL = "SELECT productID, categoryAssigned FROM moveProducts";
                
                ResultSet orphanedProducts = statement.executeQuery(orphanedProductsSQL);
                ResultSet moveProducts = statement.executeQuery(moveProductsSQL);
                orphanedProducts.last();
                moveProducts.last();
                int totalCount = moveProducts.getRow() + orphanedProducts.getRow();
                orphanedProducts.beforeFirst();
                moveProducts.beforeFirst();
                
                int current = 0;
                percentage = 0;
                //newThread = new Thread(new ProgressThread(orphanedProductsProgress));
                //apiLimitThread = new Thread(new UpdateThread(desktop.getConnectivity()));
                if (!apiLimitThread.isAlive())
                    apiLimitThread.start();
                if(!newThread.isAlive())
                    newThread.start();
                
                //first lets fixs some categories
                int orphanCategoryID = 0;
                Set <Integer> productKeys = productsToFix.keySet();
                currentTask = "Fixing Misplaced Products";
                while (moveProducts.next()) {
                    current++;
                    store.put("products/" + moveProducts.getInt("productID"), moveProducts.getString("categoryAssigned"));
                    percentage = ((current * 100) / totalCount);
                }
                statement.executeQuery("DELETE FROM moveProducts");
                //next lets move those that do not belong
                currentTask = "Reassigning Orphaned Products";
                JSONObject newCat = new JSONObject().put("name", "Orphans");
                newCat.put("is_visible", false);
                store.post("categories", newCat.toString());
                String response = store.toString();
                response = response.replace("[", "");
                response = response.replace("]","");
                JSONObject res = new JSONObject(response);
                if (store.getStatusCode() == 409) {
                    orphanCategoryID = res.getJSONObject("details").getInt("duplicate_category");
                    store.put("categories/" + orphanCategoryID, "{\"is_visible\":false}");
                }
                else {
                    orphanCategoryID = res.getInt("id");
                }
                for(Integer product: productsToMove) {
                    current++;
                    store.put("products/" + product, "{\"categories\":[" + orphanCategoryID + "]}");
                    percentage = ((current * 100) / totalCount);
                }
                statement.executeQuery("DELETE FROM orphanedProducts");
                percentage = 100;
                return null;
            }  
        };
        worker.execute();
    }//GEN-LAST:event_orphanedProductsFixActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton orphanedProductsCheck;
    private javax.swing.JButton orphanedProductsFix;
    private javax.swing.JLabel orphanedProductsLabel;
    private javax.swing.JProgressBar orphanedProductsProgress;
    // End of variables declaration//GEN-END:variables
}
