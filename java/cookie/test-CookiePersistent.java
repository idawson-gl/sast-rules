// License: LGPL-3.0 License (c) find-sec-bugs
package cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookiePersistent {

    public void danger(HttpServletResponse res) {
        Cookie cookie = new Cookie("key", "value");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        cookie.setMaxAge(60*60*24*365); // danger

        res.addCookie(cookie);
    }
}
