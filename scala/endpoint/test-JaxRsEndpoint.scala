// License: LGPL-3.0 License (c) find-sec-bugs
package endpoint

import javax.ws.rs.Path
import org.apache.commons.text.StringEscapeUtils


@Path("/test")
class JaxRsEndpoint {
  def randomFunc(s: String) = s

  @Path("/hello0")
  def hello0(user: String) = "Hello " + user // BAD

  @Path("/hello1")
  def hello1(user: String) = {
    val tainted = randomFunc(user)
    "Hello " + tainted
  }

  @Path("/hello2")
  def hello2(user: String) = {
    val sanitized = StringEscapeUtils.unescapeJava(user)
    "Hello " + sanitized // OK
  }
  
  @Path("/hello2")
  def hello3(user: String) = {
    "Hello " + user
  }
}
