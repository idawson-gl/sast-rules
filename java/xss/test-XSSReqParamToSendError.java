// License: LGPL-3.0 License (c) find-sec-bugs
package xss;

import org.owasp.encoder.Encode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XSSReqParamToSendError extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String input1 = req.getParameter("input1");
        String sessionId = req.getRequestedSessionId();
        String queryString = req.getQueryString();

        String referrer = req.getHeader("Referer"); //Should have a higher priority
        if (referrer != null && referrer.startsWith("http://company.ca")) {
            // Header access
            String host = req.getHeader("Host");
            resp.sendError(404, host); // BAD
        }
        resp.sendError(404, queryString); // BAD
        resp.sendError(404, sessionId); // BAD
        resp.sendError(404, input1); // BAD
    }


    protected void ok(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String input1 = req.getHeader("input1");
        resp.sendError(200, Encode.forHtml(input1));  // OK
    }


    protected void ok2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String input1 = req.getParameter("input1");
        resp.sendError(200, Encode.forHtml(input1));  // OK
    }
}

