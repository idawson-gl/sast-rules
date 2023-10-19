// License: LGPL-3.0 License (c) find-sec-bugs
// source: https://github.com/find-sec-bugs/find-sec-bugs/blob/master/findsecbugs-samples-java/src/test/java/testcode/endpoint/BasicHttpServlet.java
// hash: a7694d0

package endpoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class InsecureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        useParameters(req);

        resp.getWriter().print("<!--" + req.getContentType() + "-->");
        resp.getWriter().print("<h1>Welcome to " + req.getServerName());

        String sqlQuery = "UPDATE sessions(last_visit) VALUES(now()) WHERE where sid = '" + req.getRequestedSessionId() + "')";
        resp.getWriter().print("<!--" + req.getQueryString() + "-->");

        String referrer = req.getHeader("Referer"); //Should have a higher priority
        if (referrer != null && referrer.startsWith("http://company.ca")) {
            req.getHeader("Host");
            req.getHeader("User-Agent");
            req.getHeader("X-Requested-With");
        }
    }

    private void useParameters(HttpServletRequest req) {

        String username = (String) req.getParameter("username");

        String[] roles = (String[]) req.getParameterValues("roles");

        String []price = req.getParameterMap().get("hidden_price_value");

        Enumeration parameters = req.getParameterNames();
        boolean isAdmin = false;
        while (parameters.hasMoreElements()) {
            if (parameters.nextElement().equals("admin_mode")) {
                isAdmin = true;
                break;
            }
        }
    }
}
