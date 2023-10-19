// License: LGPL-3.0 License (c) find-sec-bugs
package cookie;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieUsage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Cookie cookie : req.getCookies()) {
            cookie.getName();
            cookie.getValue();
            cookie.getPath();
        }
    }

    public String getCookieName(HttpServletRequest req) {
        Cookie c = req.getCookies()[0];
        return c.getName();
    }

}
