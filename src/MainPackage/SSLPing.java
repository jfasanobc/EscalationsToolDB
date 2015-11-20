package MainPackage;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
 

public class SSLPing {
    public static void main(String[] args) {
        try {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("www.plilamps.com", 443);
 
            InputStream in = sslsocket.getInputStream();
            OutputStream out = sslsocket.getOutputStream();
 
            // Write a test byte to get a reaction :)
            out.write(1);
 
            while (in.available() > 0) {
                System.out.print(in.read());
            }
            System.out.println("Successfully connected");
 
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}