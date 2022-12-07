// License: LGPL-3.0 License (c) find-sec-bugs
package endpoint


import org.apache.commons.text.StringEscapeUtils
import javax.jws.WebMethod
import javax.jws.WebService


@WebService class JaxWsEndpoint {
  @WebMethod(operationName = "timestamp") def ping = System.currentTimeMillis // OK

  def randomFunc(s: Nothing) = s

  @WebMethod def hello0(user: Nothing) = "Hello " + user // BAD

  @WebMethod def hello1(user: Nothing) = {
    val tainted = randomFunc(user)
    "Hello " + tainted
  }

  @WebMethod def hello2(user: Nothing) = {
    val sanitized = StringEscapeUtils.unescapeJava(user)
    "Hello " + sanitized
  }

  def notAWebMethod = 8000
}
