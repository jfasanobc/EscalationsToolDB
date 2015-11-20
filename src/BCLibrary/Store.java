
package BCLibrary;

import java.io.InputStream;
import java.util.HashMap;
import org.json.JSONObject;

public interface Store {
        
    //GET, POST, PUT, DELETE
    boolean get(String url) throws Exception;
    //string version
    boolean post(String url, String data) throws Exception;
    boolean put(String url, String data) throws Exception;
    //JSONObject version
    boolean post(String url, JSONObject data) throws Exception;
    boolean put(String url, JSONObject data) throws Exception;
    boolean delete(String url) throws Exception;
    boolean options(String url) throws Exception;
    boolean testConnection() throws Exception;
    
    void addHeader(String key, String value);
    void addHeader(HashMap <String, String> requestHeaders);
    void setHeader(String key, String value);
    void setHeader(HashMap <String, String> requestHeaders);
    
    int getStatusCode();
    int getAPILimit();
    
    @Override
    //toString() can only be used one time, once used it will close. There are other avenues on how
    //to check if there has been an issue.
    String toString();
    String asString(InputStream response);
}
