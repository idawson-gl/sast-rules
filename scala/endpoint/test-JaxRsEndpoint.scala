// License: LGPL-3.0 License (c) find-sec-bugs
package endpoint

import javax.ws.rs.Path
import org.apache.commons.text.StringEscapeUtils


@Path("/test") class JaxRsEndpoint {
  def randomFunc(s: Nothing) = s

  @Path("/hello0") def hello0(user: Nothing) = "Hello " + user // BAD

  @Path("/hello1") def hello1(user: Nothing) = {
    val tainted = randomFunc(user)
    "Hello " + tainted
  }

  @Path("/hello2") def hello2(user: Nothing) = {
    val sanitized = StringEscapeUtils.unescapeJava(user)
    "Hello " + sanitized // OK

  }
}
