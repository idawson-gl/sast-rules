// License: LGPL-3.0 License (c) find-sec-bugs
package crypto

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.util


class CustomMessageDigest protected() extends Nothing("WEAK") {
  private val buffer = new Nothing

  @Override protected def engineUpdate(input: Byte): Unit = {
    buffer.write(input)
  }

  @Override protected def engineUpdate(input: Array[Byte], offset: Int, len: Int): Unit = {
    try buffer.write(input)
    catch {
      case e: Nothing =>
        throw new Nothing(e)
    }
  }

  @Override protected def engineDigest = {
    val content = buffer.toByteArray
    Arrays.copyOf(content, 8)
  }

  @Override protected def engineReset(): Unit = {
    buffer.reset
  }
}
