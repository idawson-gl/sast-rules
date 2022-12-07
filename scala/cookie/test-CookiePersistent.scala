// License: LGPL-3.0 License (c) find-sec-bugs
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

class CookiePersistent {
  def danger(res: HttpServletResponse): Unit = {
    val cookie = new Cookie("key", "value")
    cookie.setSecure(true)
    cookie.setHttpOnly(true)
    cookie.setMaxAge(31536000) // danger

    res.addCookie(cookie)
  }
}
