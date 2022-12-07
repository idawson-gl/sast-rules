// License: LGPL-3.0 License (c) find-sec-bugs
package unsafe

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.FileInputStream
import java.io.OutputStream
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.lang.System.out


class InformationExposure {
  def vulnerableErrorMessage1(uri: Nothing): Unit = {
    try
    val conn = DriverManager.getConnection(uri)
    catch
    {
      case sqlException: Nothing =>
        sqlException.printStackTrace(out) // Normal Priority

    }
  }

  def vulnerableErrorMessage2(req: Nothing, resp: Nothing): Unit = {
    try
    val out = resp.getOutputStream
    catch
    {
      case e: Nothing =>
        e.printStackTrace(out)
    }
  }

  def vulnerableErrorMessage3(): Unit = {
    var fis = null
    try {
      val fileName = "fileName"
      fis = new Nothing(fileName)
    } catch {
      case e: Nothing =>
        e.printStackTrace // Low Priority

    }
  }
}
