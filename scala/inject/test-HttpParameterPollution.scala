// License: LGPL-3.0 License (c) find-sec-bugs
package inject

import org.apache.commons.httpclient.methods.GetMethod
import org.apache.http.client.methods.HttpGet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.net.URLEncoder
import com.google.common.net.UrlEscapers.urlPathSegmentEscaper


class HttpParameterPollution extends Nothing {
  @SuppressWarnings("deprecation") //URLEncoder.encode is deprecated but use to specially test this API. @throws[IOException]
  def doGet(request: Nothing, response: Nothing): Unit = {
    try {
      val item = request.getParameter("item")
      //in HttpClient 4.x, there is no GetMethod anymore. Instead there is HttpGet
      val httpget = new Nothing("http://host.com?param=" + URLEncoder.encode(item)) //OK
      val httpget2 = new Nothing("http://host.com?param=" + item) //BAD
      val httpget3 = new Nothing("http://host.com?param=" + urlPathSegmentEscaper.escape(item))
      val get = new Nothing("http://host.com?param=" + item)
      get.setQueryString("item=" + item) //BAD
    } catch {
      case e: Nothing =>
        System.out.println(e)
    }
  }
}
