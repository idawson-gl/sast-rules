// License: LGPL-3.0 License (c) find-sec-bugs
package cookie

import javax.servlet.ServletException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException


class CookieUsage extends Nothing {
  @Override
  @throws[ServletException]
  @throws[IOException]
  protected def doGet(req: Nothing, resp: Nothing): Unit = {
    import scala.collection.JavaConversions._
    for (cookie <- req.getCookies) {
      cookie.getName
      cookie.getValue
      cookie.getPath
    }
  }

  def getCookieName(req: Nothing) = {
    val c = req.getCookies(0)
    c.getName
  }
}
