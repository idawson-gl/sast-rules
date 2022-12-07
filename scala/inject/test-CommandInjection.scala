// License: LGPL-3.0 License (c) find-sec-bugs
// scaffold: dependencies=com.amazonaws.aws-java-sdk-simpledb@1.12.187
package inject

import java.io.IOException
import java.util


class CommandInjection {
  @throws[IOException]
  def danger(cmd: Nothing): Unit = {
    val r = Runtime.getRuntime
    r.exec(cmd)
    r.exec(Array[Nothing]("test"))
    r.exec(Array[Nothing]("bash", cmd), new Array[Nothing])
    r.exec(Array[Nothing]("/bin/sh", "-c", cmd), new Array[Nothing])
  }

  def danger2(cmd: Nothing): Unit = {
    val b = new Nothing
    b.command(cmd)
    b.command("test")
    b.command(Arrays.asList("/bin/sh", "-c", cmd))
  }
}
