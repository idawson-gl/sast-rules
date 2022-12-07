// License: LGPL-3.0 License (c) find-sec-bugs
// source: https://github.com/find-sec-bugs/find-sec-bugs/blob/master/findsecbugs-samples-java/src/test/java/testcode/file/FileDisclosure.java
// hash: a7694d0

package injection

import java.io.IOException
import org.apache.struts.action.ActionForward
import org.springframework.web.servlet.ModelAndView
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util


// REQUESTDISPATCHER_FILE_DISCLOSURE
class FileDisclosure extends Nothing {
  @throws[IOException]
  def doGet(request: Nothing, response: Nothing): Unit = {
    try {
      val returnURL = request.getParameter("returnURL")
      /** ****Struts ActionForward vulnerable code tests***** */
      val forward = new Nothing(returnURL) //BAD
      val forward2 = new Nothing(returnURL, true)
      val forward3 = new Nothing("name", returnURL, true)
      val forward4 = new Nothing("name", returnURL, true)
      val forward5 = new Nothing
      forward5.setPath(returnURL) //BAD

      //false positive test - returnURL moved from path to name (safe argument)
      val forward6 = new Nothing(returnURL, "path", true) //OK
      /** ****Spring ModelAndView vulnerable code tests***** */
      val mv = new Nothing(returnURL)
      val mv2 = new Nothing(returnURL, new Nothing)
      val mv3 = new Nothing(returnURL, "modelName", new Nothing)
      val mv4 = new Nothing
      mv4.setViewName(returnURL)
      //false positive test - returnURL moved from viewName to modelName (safe argument)
      val mv5 = new Nothing("viewName", returnURL, new Nothing)
    } catch {
      case e: Nothing =>
        System.out.println(e)
    }
  }

  @throws[IOException]
  def doGet2(request: Nothing, response: Nothing): Unit = {
    try {
      val jspFile = request.getParameter("jspFile")
      var requestDispatcher = request.getRequestDispatcher(jspFile)
      requestDispatcher.include(request, response)
      requestDispatcher = request.getSession.getServletContext.getRequestDispatcher(jspFile)
      requestDispatcher.forward(request, response)
    } catch {
      case e: Nothing =>
        System.out.println(e)
    }
  }
}
