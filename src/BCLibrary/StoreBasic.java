/* 
  Julio Sevilla
  CLASS 
  ASSIGNMENT NAME
  DATE
  Developed on NetBeans IDE 8.0 (Build 201403101706)
  Java: 1.8.0_05
*/
package BCLibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;
import org.json.JSONObject;


public class StoreBasic implements Store {
    private final String username;
    private final String password;
    private final String storeURL;
    private final boolean sslExemption;
    private HashMap <String,String> requestHeaders;
    private int apiLimit;
    private int statusCode;
    private InputStream response;
    private HttpURLConnection connection;
    
    public StoreBasic(String storeURL, String username, String password) {
        
        this.storeURL = storeURL;
        this.username = username;
        this.password = password;
        this.apiLimit = 0;
        this.requestHeaders = new HashMap<>();
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("Content-Type", "application/json");
        this.sslExemption = false;
    }
    
    public StoreBasic(String storeURL, String username, String password, HashMap <String, String> requestHeaders) {
        this.storeURL = storeURL;
        this.username = username;
        this.password = password;
        this.apiLimit = 0;
        this.requestHeaders = requestHeaders;
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("Content-Type", "application/json");
        this.sslExemption = false;
    }
    
    public StoreBasic(String storeURL, String username, String password, boolean sslExemption) {
        this.storeURL = storeURL;
        this.username = username;
        this.password = password;
        this.apiLimit = 0;
        this.requestHeaders = new HashMap<>();
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("Content-Type", "application/json");
        this.sslExemption = sslExemption;
    }
    
    public StoreBasic(String storeURL, String username, String password, HashMap <String, String> requestHeaders, boolean sslExemption) {
        this.storeURL = storeURL;
        this.username = username;
        this.password = password;
        this.apiLimit = 0;
        this.requestHeaders = requestHeaders;
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("Content-Type", "application/json");
        this.sslExemption = sslExemption;
    }
    
    private HttpURLConnection createConnection(String url, String method, boolean exempted) throws MalformedURLException, IOException {
        if (exempted) {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                }
            };

            // Install the all-trusting trust manager
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (NoSuchAlgorithmException | KeyManagementException e) {}
        }
        URL urlVar = new URL(storeURL + url);
        HttpURLConnection returnConnection = (HttpURLConnection) urlVar.openConnection();
        returnConnection.setRequestMethod(method);
        returnConnection.setRequestProperty("Authorization", this.getAuthentication());
        return returnConnection;
    }
    
    public String getAuthentication() throws UnsupportedEncodingException {
        return "Basic " + DatatypeConverter.printBase64Binary((username + ":" + password).getBytes("UTF-8"));
    }
    
    //GET, POST, PUT, DELETE
    @Override
    public boolean get(String url) throws Exception {
        try {
            connection = this.createConnection(url, "GET", this.sslExemption);
            for(String key: requestHeaders.keySet()) {
                connection.addRequestProperty(key, requestHeaders.get(key));
            }
            this.response = connection.getInputStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            this.statusCode = connection.getResponseCode();
            return true;
        } catch (SSLHandshakeException e) {
            this.response = connection.getErrorStream();
            this.statusCode = connection.getResponseCode();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return false;
        } catch (IOException e) {
            this.statusCode = connection.getResponseCode();
            this.response = connection.getErrorStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return false;
        }
    }
    
    @Override
    public boolean post(String url, String data) throws Exception{
        try {
            connection = this.createConnection(url, "POST", this.sslExemption);
            connection.setDoOutput(true);
            for(String key: requestHeaders.keySet()) {
                connection.addRequestProperty(key, requestHeaders.get(key));
            }
            try (OutputStreamWriter create = new OutputStreamWriter(connection.getOutputStream())) {
                create.write(data);
                create.flush();
            }
            this.response = connection.getInputStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            this.statusCode = connection.getResponseCode();
            return true;
        } catch(IOException e) {
            this.statusCode = connection.getResponseCode();
            this.response = connection.getErrorStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return false;
        } 
    }
    
    @Override
    public boolean post(String url, JSONObject data) throws Exception{
        try {
            connection = this.createConnection(url, "POST", this.sslExemption);
            connection.setDoOutput(true);
            for(String key: requestHeaders.keySet()) {
                connection.addRequestProperty(key, requestHeaders.get(key));
            }
            try (OutputStreamWriter create = new OutputStreamWriter(connection.getOutputStream())) {
                create.write(data.toString());
                create.flush();
            }
            this.response = connection.getInputStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            this.statusCode = connection.getResponseCode();
            return true;
        } catch(IOException e) {
            this.statusCode = connection.getResponseCode();
            this.response = connection.getErrorStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return false;
        } 
    }
    
    @Override
    public boolean put(String url, String data) throws Exception{
        try {
            connection = this.createConnection(url, "POST", this.sslExemption);
            connection.setDoOutput(true);
            for(String key: requestHeaders.keySet()) {
                connection.addRequestProperty(key, requestHeaders.get(key));
            }
            try (OutputStreamWriter create = new OutputStreamWriter(connection.getOutputStream())) {
                create.write(data);
                create.flush();
            }
            this.response = connection.getInputStream();
            this.statusCode = connection.getResponseCode();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return true;
        } catch(IOException e) {
            this.statusCode = connection.getResponseCode();
            this.response = connection.getErrorStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return false;
        } 
    }
    
    @Override
    public boolean put(String url, JSONObject data) throws Exception{
        try {
            connection = this.createConnection(url, "POST", this.sslExemption);
            connection.setDoOutput(true);
            for(String key: requestHeaders.keySet()) {
                connection.addRequestProperty(key, requestHeaders.get(key));
            }
            try (OutputStreamWriter create = new OutputStreamWriter(connection.getOutputStream())) {
                create.write(data.toString());
                create.flush();
            }
            this.response = connection.getInputStream();
            this.statusCode = connection.getResponseCode();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return true;
        } catch(IOException e) {
            this.statusCode = connection.getResponseCode();
            this.response = connection.getErrorStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return false;
        } 
    }
    
    @Override
    public boolean delete(String url) throws Exception{
        try {
            connection = this.createConnection(url, "DELETE",this.sslExemption);
            for(String key: requestHeaders.keySet()) {
                connection.addRequestProperty(key, requestHeaders.get(key));
            }
            this.response = connection.getInputStream();
            this.statusCode = connection.getResponseCode();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return true;
        } catch (IOException e) {
            this.statusCode = connection.getResponseCode();
            this.response = connection.getErrorStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return false;
        } 
    }
    
    @Override
    public boolean options(String url) throws Exception {
        try {
            connection = this.createConnection(url, "OPTIONS", this.sslExemption);
            for(String key: requestHeaders.keySet()) {
                connection.addRequestProperty(key, requestHeaders.get(key));
            }
            this.response = connection.getInputStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            this.statusCode = connection.getResponseCode();
            return true;
        } catch (SSLHandshakeException e) {
            this.response = connection.getErrorStream();
            this.statusCode = connection.getResponseCode();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return false;
        } catch (IOException e) {
            this.statusCode = connection.getResponseCode();
            this.response = connection.getErrorStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return false;
        }
    }
    
    @Override
    public boolean testConnection() throws Exception {
        try {
            connection = this.createConnection("time", "GET", this.sslExemption);
            for(String key: requestHeaders.keySet()) {
                connection.addRequestProperty(key, requestHeaders.get(key));
            }
            this.response = connection.getInputStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            this.statusCode = connection.getResponseCode();
            return true;
        } catch (IOException e) {
            this.statusCode = connection.getResponseCode();
            this.response = connection.getErrorStream();
            apiLimit = Integer.parseInt(connection.getHeaderField("X-BC-ApiLimit-Remaining"));
            return false;
        }
    }
    
    @Override
    public void addHeader(String key, String value) {
        this.requestHeaders.put(key, value);
        this.requestHeaders.put("Accept", "application/json");
        this.requestHeaders.put("Content-Type", "application/json");
    }
    
    @Override
    public void addHeader(HashMap <String, String> requestHeaders) {
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("Content-Type", "application/json");
        for(String key : requestHeaders.keySet())
            this.requestHeaders.put(key, requestHeaders.get(key));
    }
    
    @Override
    public void setHeader(String key, String value) {
        this.requestHeaders.put("Accept", "application/json");
        this.requestHeaders.put("Content-Type", "application/json");
        this.requestHeaders.put(key, value);
    }
    
    @Override
    public void setHeader(HashMap <String, String> requestHeaders) {
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("Content-Type", "application/json");
        this.requestHeaders = requestHeaders;
    }
    
    
    
    //overridden toString() this method will only allow the usage of the InputStream one time
    @Override
    public String toString() {
        String responseBody = "";
	try {
		StringBuilder body = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(this.response, "UTF-8"));
		String inputLine;
		while ((inputLine = reader.readLine()) != null) {
			body.append(inputLine);
		}
		reader.close();
		responseBody = body.toString();
	} catch (IOException e) {
		System.out.print(e.toString());
	}
        return responseBody;
    }
    @Override
    public String asString(InputStream response) {
        String responseBody = "";
	try {
		StringBuffer body = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
		String inputLine;
		while ((inputLine = reader.readLine()) != null) {
			body.append(inputLine);
		}
		reader.close();
		responseBody = body.toString();
	} catch (IOException e) {
		System.out.print(e.toString());
	}
        return responseBody;
    }
    
    @Override
    public int getStatusCode() {
        return this.statusCode;
    }
    
    @Override
    public int getAPILimit() {
        return this.apiLimit;
    }
    
    public class DeleteThread implements Runnable {
        private String URI;
        
        DeleteThread (String URI) {
            this.URI = URI;
        }
        
        @Override
        public void run() {
            try {
                try {
                    Random rand = new Random();
                    int randomNum = rand.nextInt(2) + 1;
                    System.out.println("DELETING " + URI + " (" + Thread.currentThread().getName() + " in " + randomNum + "secs)");
                    Thread.sleep(randomNum * 100);
                }
                catch (InterruptedException e) {
                    System.out.println("Trouble with the thread sleep on " + Thread.currentThread().getName() + " and URL " + URI);
                }
                delete(URI);
            } catch (IOException e) {
                System.out.println("Problem with " + URI + " with Status code " + e.toString() + " - " + apiLimit);
            } catch (Exception ex) {
                Logger.getLogger(StoreBasic.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
    public DeleteThread deleteThread(String URI) {
        return new DeleteThread(URI);
    }
}
