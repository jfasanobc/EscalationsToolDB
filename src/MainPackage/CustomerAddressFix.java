/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import BCLibrary.StoreBasic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class CustomerAddressFix {
    public static void main (String args[]) throws IOException, Exception {
        HashMap <String, String> states = new HashMap<>();
        states.put("AL","Alabama");
states.put("AK","Alaska");
states.put("AZ","Arizona");
states.put("AR","Arkansas");
states.put("CA","California");
states.put("CO","Colorado");
states.put("CT","Connecticut");
states.put("DE","Delaware");
states.put("DC","District of Columbia");
states.put("FL","Florida");
states.put("GA","Georgia");
states.put("HI","Hawaii");
states.put("ID","Idaho");
states.put("IL","Illinois");
states.put("IN","Indiana");
states.put("IA","Iowa");
states.put("KS","Kansas");
states.put("KY","Kentucky");
states.put("LA","Louisiana");
states.put("ME","Maine");
states.put("MD","Maryland");
states.put("MA","Massachusetts");
states.put("MI","Michigan");
states.put("MN","Minnesota");
states.put("MS","Mississippi");
states.put("MO","Missouri");
states.put("MT","Montana");
states.put("NE","Nebraska");
states.put("NV","Nevada");
states.put("NH","New Hampshire");
states.put("NJ","New Jersey");
states.put("NM","New Mexico");
states.put("NY","New York");
states.put("NC","North Carolina");
states.put("ND","North Dakota");
states.put("OH","Ohio");
states.put("OK","Oklahoma");
states.put("OR","Oregon");
states.put("PA","Pennsylvania");
states.put("RI","Rhode Island");
states.put("SC","South Carolina");
states.put("SD","South Dakota");
states.put("TN","Tennessee");
states.put("TX","Texas");
states.put("UT","Utah");
states.put("VT","Vermont");
states.put("VA","Virginia");
states.put("WA","Washington");
states.put("WV","West Virginia");
states.put("WI","Wisconsin");
states.put("WY","Wyoming");
        
        String storeURL = "";
        String userName = "";
        String apiToken = "";
        
        int page = 1;
        int jsonArrayLength = 0;
        JSONArray objectList;
        JSONArray addresses;
        JSONObject customer;
        JSONObject customerAddress;
        JSONObject changeStuff = new JSONObject();
        int numberOfAddresses = 0;
        StoreBasic store = new StoreBasic(storeURL, userName, apiToken);
        do {
            try {
                store.get("customers?limit=250&page=" + page);               
                String tempHolder = store.toString();
                objectList = new JSONArray(tempHolder);
                jsonArrayLength = objectList.length();
                for(int index = 0; index < objectList.length(); index++) {
                    //System.out.println("ID: " + objectList.getJSONObject(index).toString());
                    store.get("customers/" + objectList.getJSONObject(index).getInt("id") + "/addresses");
                    //store.toString();
                    if (store.getStatusCode() == 200) {
                        String tempAddresses = store.toString();
                        if(tempAddresses.startsWith("["))
                            addresses = new JSONArray(tempAddresses);
                        else
                            addresses = new JSONArray("[" + tempAddresses + "]");
                        for(int innerIndex = 0; innerIndex < addresses.length(); innerIndex++) {
                            customerAddress = addresses.getJSONObject(innerIndex);
                            //System.out.println(customerAddress);
                            if (customerAddress.getString("country").equals("US")) {
                                changeStuff.put("country", "United States");
                                if(states.containsKey(customerAddress.getString("state"))) {
                                    changeStuff.put("state", states.get(customerAddress.getString("state")));
                                    store.put("customers/" + objectList.getJSONObject(index).getInt("id") + "/addresses/" + customerAddress.getInt("id"), changeStuff);
                                    numberOfAddresses++;
                                    System.out.println(numberOfAddresses + " - Changing: ID " + customerAddress.getString("country") + " and " + customerAddress.getString("state") + " to the following: " + changeStuff.toString());
                            
                                }
                            }
                        }
                    }
                }
                if (jsonArrayLength == 250) page++;
            } catch (IOException ex) {
                
            }
        } while (jsonArrayLength == 250);
    }
}
