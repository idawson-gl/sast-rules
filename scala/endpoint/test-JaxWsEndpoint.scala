// License: LGPL-3.0 License (c) find-sec-bugs
package endpoint


import org.apache.commons.text.StringEscapeUtils
import javax.jws.WebMethod
import javax.jws.WebService


@WebService
class JaxWsEndpoint {
  @WebMethod(operationName = "timestamp")
  def ping = System.currentTimeMillis // OK

  def randomFunc(s: String) = s

  @WebMethod
  def hello0(user: String) = "Hello " + user // BAD

  @WebMethod
  def hello1(user: String) = {
    val tainted = randomFunc(user)
    "Hello " + tainted
  }

  @WebMethod
  def hello2(user: String) = {
    val sanitized = StringEscapeUtils.unescapeJava(user)
    "Hello " + sanitized
  }

  @WebMethod
  def hello3(user: String) = {
    "Hello " + user
  }

  def notAWebMethod = 8000
}
