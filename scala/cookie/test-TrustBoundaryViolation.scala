// License: LGPL-3.0 License (c) find-sec-bugs

package cookie

import javax.servlet.http.HttpServletRequest


class TrustBoundaryViolation { //Taint input
  def setSessionAttributeNameTainted(req: Nothing): Unit = {
    val input = req.getParameter("input")
    req.getSession.setAttribute(input, "true")
  }

  def setSessionAttributeValueTainted(req: Nothing): Unit = {
    val input = req.getParameter("input")
    req.getSession.setAttribute("user", input)
  }

  //Unknown source
  def setSessionAttributeNameUnknownSource(req: Nothing, input: Nothing): Unit = {
    req.getSession.setAttribute(input, "true")
  }

  def setSessionAttributeValueUnknownSource(req: Nothing, input: Nothing): Unit = {
    req.getSession.setAttribute("user", input) //Reported as low
  }

  //Legacy api
  def setSessionAttributeNameUnknownSourceLegacy(req: Nothing, input: Nothing): Unit = {
    req.getSession.putValue(input, "true")
  }

  def setSessionAttributeValueUnknownSourceLegacy(req: Nothing, input: Nothing): Unit = {
    req.getSession.putValue("user", input)
  }

  //Safe
  def setSessionAttributeSafe(req: Nothing, input: Nothing): Unit = {
    if ("enable".equals(input)) req.getSession.setAttribute("user", "true")
    else req.getSession.setAttribute("user", "false")
  }
}
