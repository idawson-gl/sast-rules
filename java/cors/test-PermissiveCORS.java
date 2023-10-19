// License: LGPL-3.0 License (c) find-sec-bugs
package cors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PermissiveCORS extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        falsePositiveCORS(resp);
        resp.getWriter().print(req.getSession().getAttribute("secret"));
    }

    private void falsePositiveCORS(HttpServletResponse resp) {
        resp.addHeader("Access-Control-Allow-Origin", "http://example.com"); // OK
    }

    // Overly permissive Cross-domain requests accepted
    public void addPermissiveCORS(HttpServletResponse resp) {
        resp.addHeader("Access-Control-Allow-Origin", "*");  // BAD
    }

    public void addPermissiveCORS2(HttpServletResponse resp) {
        resp.addHeader("access-control-allow-origin", "*");  // BAD
    }

    public void addWildcardsCORS(HttpServletResponse resp) {
        resp.addHeader("Access-Control-Allow-Origin", "*.example.com");  // BAD
    }

    public void addNullCORS(HttpServletResponse resp) {
        resp.addHeader("Access-Control-Allow-Origin", "null");  // BAD
    }

    public void setPermissiveCORS(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");  // BAD
    }

    public void setPermissiveCORSWithRequestVariable(HttpServletResponse resp, HttpServletRequest req) {
        resp.setHeader("Access-Control-Allow-Origin", req.getParameter("tainted"));  // BAD
    }

    public void setPermissiveCORSWithRequestVariable2(HttpServletResponse resp, HttpServletRequest req) {
        String header = req.getParameter("tainted");
        resp.addHeader("access-control-allow-origin", header);   // BAD
    }

}
