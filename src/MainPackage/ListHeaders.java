/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author julio
 */
public class ListHeaders {
    public static void main (String args[]) throws IOException {
        URL obj = new URL("http://notforcheating1.com");
	URLConnection conn = obj.openConnection();
 
	//get all headers
	Map<String, List<String>> map = conn.getHeaderFields();
	for (Map.Entry<String, List<String>> entry : map.entrySet()) {
		System.out.println(entry.getKey() + 
                 ": " + entry.getValue());
	}   
    }
}