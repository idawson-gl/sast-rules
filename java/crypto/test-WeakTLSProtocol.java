// License: LGPL-3.0 License (c) find-sec-bugs
package crypto;

import java.security.NoSuchAlgorithmException;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.SSLContext;

public class WeakTLSProtocol {

    public static void main(String[] args) {
        new DefaultHttpClient(); // BAD

        try {
          SSLContext context1 = SSLContext.getInstance("SSL"); // BAD
          SSLContext context2 = SSLContext.getInstance("TLS"); // OK
        } catch (NoSuchAlgorithmException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }

}
