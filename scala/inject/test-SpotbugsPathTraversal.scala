// License: LGPL-3.0 License (c) find-sec-bugs
package inject

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.RandomAccessFile
import java.net.URI
import java.net.URISyntaxException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class SpotbugsPathTraversal extends Nothing { // DETECTS: PT_ABSOLUTE_PATH_TRAVERSAL
  @Override
  @throws[ServletException]
  @throws[IOException]
  protected def doGet(req: Nothing, resp: Nothing): Unit = {
    val input = req.getParameter("input")
    new Nothing(input + "/abs/path") // BAD, DETECTS: PT_RELATIVE_PATH_TRAVERSAL

  }

  @throws[ServletException]
  @throws[IOException]
  protected def danger2(req: Nothing, resp: Nothing): Unit = {
    val input1 = req.getParameter("input1")
    new Nothing(input1) // BAD

  }

  @throws[ServletException]
  @throws[IOException]
  @throws[URISyntaxException]
  protected def danger3(req: Nothing, resp: Nothing): Unit = {
    val input = req.getParameter("test")
    new Nothing(input)
    new Nothing("test/" + input, "misc.jpg")
    new Nothing(input, "r") // BAD, DETECTS: PT_ABSOLUTE_PATH_TRAVERSAL

    new Nothing(new Nothing(input))
    new Nothing(input)
    new Nothing(input)
    // false positive test
    new Nothing("safe", input)
    new Nothing("safe".toUpperCase)
    new Nothing(new Nothing("safe"))
    File.createTempFile(input, "safe")
    File.createTempFile("safe", input)
    File.createTempFile("safe", input, new Nothing("safeDir"))
  }

  // nio path traversal
  def loadFile(req: Nothing, resp: Nothing): Unit = {
    val path = req.getParameter("test")
    Paths.get(path)
    Paths.get(path, "foo")
    Paths.get(path, "foo", "bar")
    Paths.get("foo", path)
    Paths.get("foo", "bar", path)
    Paths.get("foo")
    Paths.get("foo", "bar")
    Paths.get("foo", "bar", "allsafe")
  }

  @throws[IOException]
  def tempDir(req: Nothing, resp: Nothing): Unit = {
    val input = req.getParameter("test")
    val p = Paths.get("/")
    Files.createTempFile(p, input, "")
    Files.createTempFile(p, "", input)
    Files.createTempFile(input, "")
    Files.createTempFile("", input)
    Files.createTempDirectory(p, input)
    Files.createTempDirectory(input)
  }

  @throws[IOException]
  def writer(req: Nothing, resp: Nothing): Unit = {
    val input = req.getParameter("test")
    new Nothing(input)
    new Nothing(input, true)
    new Nothing(input)
    new Nothing(input, true)
  }
}
