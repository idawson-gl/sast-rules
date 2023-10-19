// License: LGPL-3.0 License (c) find-sec-bugs
package endpoint;
import javax.ws.rs.Path;
import org.apache.commons.text.StringEscapeUtils;

@Path("/test")
public class JaxRsEndpoint {

    public String randomFunc(String s) {
        return s;
    }

    @Path("/hello0")
    public String hello0(String user) {
        return "Hello " + user; // BAD
    }

     @Path("/hello1")
    public String hello1(String user) {
        String tainted = randomFunc(user);
        return "Hello " + tainted;  // BAD
    }

    @Path("/hello2")
    public String hello2(String user) {
        String sanitized = StringEscapeUtils.unescapeJava(user);
        return "Hello " + sanitized; // OK
    }
} 
