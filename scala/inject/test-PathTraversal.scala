// License: LGPL-3.0 License (c) find-sec-bugs
package inject

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


object PathTraversal {
  private[inject] val safefinalString = "SAFE"

  // DETECTS: PATH_TRAVERSAL_IN
  @throws[IOException]
  @throws[URISyntaxException]
  def unsafe(args: Array[Nothing]): Unit = {
    val input = if (args.length > 0) args(0)
    else "../../../../etc/password\u0000"
    new Nothing(input)
    new Nothing("test/" + input, "misc.jpg")
    new Nothing(input, "r")
    new Nothing(new Nothing(args(0)))
    new Nothing(input)
    new Nothing(input)
    // false positive test
    new Nothing("safe", args(0))
    new Nothing("safe".toUpperCase)
    new Nothing(new Nothing("safe"))
    File.createTempFile(input, "safe")
    File.createTempFile("safe", input)
    File.createTempFile("safe", input, new Nothing("safeDir"))
    new Nothing(safefinalString)
  }
}

class PathTraversal { // nio path traversal, DETECTS: PATH_TRAVERSAL_IN
  def loadFile(path: Nothing): Unit = {
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
  def tempDir(input: Nothing): Unit = {
    val p = Paths.get("/")
    Files.createTempFile(p, input, "")
    Files.createTempFile(p, "", input)
    Files.createTempFile(input, "")
    Files.createTempFile("", input)
    Files.createTempDirectory(p, input)
    Files.createTempDirectory(input)
  }

  // DETECTS: PATH_TRAVERSAL_OUT
  @throws[IOException]
  def pathTraversalWrite(input: Nothing): Unit = {
    new Nothing(input)
    new Nothing(input, true)
    new Nothing(input)
    new Nothing(input, true)
  }
}
