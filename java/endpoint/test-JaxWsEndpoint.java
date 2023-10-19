// License: LGPL-3.0 License (c) find-sec-bugs
package endpoint;


import org.apache.commons.text.StringEscapeUtils;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class JaxWsEndpoint {

    @WebMethod(operationName = "timestamp")
    public long ping() {
        return System.currentTimeMillis(); // OK
    }

    public String randomFunc(String s) {
        return s;
    }

    @WebMethod
    public String hello0(String user) {
        return "Hello " + user; // BAD
    }

    @WebMethod
    public String hello1(String user) {
        String tainted = randomFunc(user);
        return "Hello " + tainted;  // BAD
    }

    @WebMethod
    public String hello2(String user) {
        String sanitized = StringEscapeUtils.unescapeJava(user);
        return "Hello " + sanitized; // OK
    }

    public int notAWebMethod() {
        return 8000;
    }
}
