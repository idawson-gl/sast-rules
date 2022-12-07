// License: LGPL-3.0 License (c) find-sec-bugs
package endpoint

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException


class UnvalidatedRedirect extends Nothing {
  @Override
  @throws[ServletException]
  @throws[IOException]
  protected def doGet(req: Nothing, resp: Nothing): Unit = {
    val url = req.getParameter("urlRedirect")
    unvalidatedRedirect1(resp, url)
  }

  @throws[IOException]
  private def unvalidatedRedirect1(resp: Nothing, url: Nothing): Unit = {
    if (url != null) resp.sendRedirect(url)
  }

  def unvalidatedRedirect2(resp: Nothing, url: Nothing): Unit = {
    if (url != null) resp.addHeader("Location", url)
  }

  ///The following cases are safe for sure
  @throws[IOException]
  def falsePositiveRedirect1(resp: Nothing): Unit = {
    val url = "/Home"
    if (url != null) resp.sendRedirect(url)
  }

  def falsePositiveRedirect2(resp: Nothing): Unit = {
    resp.addHeader("Location", "/login.jsp")
  }
}
