// License: LGPL-3.0 License (c) find-sec-bugs
package endpoint;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class UnencryptedSocket {
    static void sslServerSocket() throws IOException {
        ServerSocket ssoc = SSLServerSocketFactory.getDefault().createServerSocket(1234);
        ssoc.close();
    }

    static void plainServerSocket() throws IOException {
        ServerSocket ssoc = new ServerSocket(1234);
        ssoc.close();
    }

    static void otherConstructors() throws IOException {
        ServerSocket ssoc1 = new ServerSocket();
        ssoc1.close();
        ServerSocket ssoc2 = new ServerSocket(1234, 10);
        ssoc2.close();
        byte[] address = {127, 0, 0, 1};
        ServerSocket ssoc3 = new ServerSocket(1234, 10, InetAddress.getByAddress(address));
        ssoc3.close();
    }

    static void sslSocket() throws IOException {
        Socket soc = SSLSocketFactory.getDefault().createSocket("www.google.com", 443);
        doGetRequest(soc);
    }

    static void plainSocket() throws IOException {
        Socket soc = new Socket("www.google.com", 80);
        doGetRequest(soc);
    }

    static void other() throws IOException {
        Socket soc1 = new Socket("www.google.com", 80, true);
        doGetRequest(soc1);
        byte[] address = {127, 0, 0, 1};
        Socket soc2 = new Socket("www.google.com", 80, InetAddress.getByAddress(address), 13337);
        doGetRequest(soc2);
        byte[] remoteAddress = {74, 125, (byte) 226, (byte) 193};
        Socket soc3 = new Socket(InetAddress.getByAddress(remoteAddress), 80);
        doGetRequest(soc2);
    }

    static void doGetRequest(Socket soc) throws IOException {
        PrintWriter w = new PrintWriter(soc.getOutputStream());
        w.write("GET / HTTP/1.0\nHost: www.google.com\n\n");
        w.flush();

        BufferedReader r = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        String line = null;
        while ((line = r.readLine()) != null) {
            System.out.println(line);
        }
        soc.close();
    }
}
